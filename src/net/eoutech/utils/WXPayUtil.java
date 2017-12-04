package net.eoutech.utils;


import com.alibaba.fastjson.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import  net.eoutech.utils.WXPayConstants.SignType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by admin on 2017/7/15.
 */
public class WXPayUtil {
    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }
    /**获取ip地址*/

    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
        return generateSignedXml(data, key, SignType.MD5);
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key, SignType signType) throws Exception {
        String sign = generateSignature(data, key, signType);
        data.put(WXPayConstants.FIELD_SIGN, sign);
        return mapToXml(data);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, SignType.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (SignType.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }


    /**
     * 通过内容获取prepay_id*/

    public static Map<String,String> getorder(String url, String ip , String price, String orderId, String openId) throws Exception {


        String xml ="<xml>\n" +
                "   <appid>"+WXPayConstants.appid+"</appid>\n" +
                "<attach>支付测试</attach>\n" +
                "<body>H5支付测试</body>\n" +
                "   <mch_id>"+WXPayConstants.mchId+"</mch_id>\n" +
                "   <nonce_str>" + WXPayUtil.generateNonceStr() + "</nonce_str>\n" +
                "   <notify_url>" + url + "</notify_url>\n" +
                "   <openid>"+openId+"</openid>\n" +
                "   <out_trade_no>"+WXPayUtil.getCurrentTimestampMs()+"</out_trade_no>\n" +
                "   <spbill_create_ip>"+ip+"</spbill_create_ip>\n" +
                "   <total_fee>"+price+"</total_fee>\n" +
                "   <trade_type>"+WXPayConstants.tradeType+"</trade_type>\n" +
                "<scene_info>"+"瀚隆娃娃机支付"+"</scene_info>\n" +
                "</xml>";
/*
        String xml = "<xml>\n" +
                "   <appid>"+WXPayConstants.appid+"</appid>\n" +
                "   <attach>"+orderId+"</attach>\n" +
                "   <body>"+orderId+"</body>\n" +
                "   <device_info> "+orderId+"</device_info>\n" +
                "   <mch_id>"+WXPayConstants.mchId+"</mch_id>\n" +
                // "   <detail><![CDATA[{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"苹果手机\" } ] }]]></detail>\n" +
                "   <nonce_str>" + WXPayUtil.generateNonceStr() + "</nonce_str>\n" +
                "   <notify_url>" + url + "</notify_url>\n" +
                "   <openid>"+uid+"</openid>\n" +
                "   <out_trade_no>"+WXPayUtil.getCurrentTimestampMs()+"</out_trade_no>\n" +
                "   <spbill_create_ip>"+ip+"</spbill_create_ip>\n" +
                "   <total_fee>"+price+"</total_fee>\n" +
                "   <trade_type>JSAPI</trade_type>\n" +
                "</xml>";*/

        Map<String,String> map =  WXPayUtil.xmlToMap(xml);
        String xmlString =   WXPayUtil.generateSignedXml(map,WXPayConstants.key);
        String url2 = WXPayConstants.url;
        System.out.println("处理order部分的map是"+map);
        String res =  HttpRequestUtil.httpsRequest(url2, xmlString);
        System.out.println(res);
        return   WXPayUtil.xmlToMap(res);
    }

    /**
     * 通过code 获取openId
     * @param code
     * @return
     */
    public static JSONObject getSnsapiBase(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid="+ WXPayConstants.appid +
               // "appid="+appid+
                "&secret=" + WXPayConstants.AppSecret +
                "&code=" + code.trim() +
                "&grant_type=authorization_code";
        System.out.println(url.trim());
        JSONObject jsonObject = JSONObject.parseObject(HttpRequestUtil.getURLContent(url).trim());
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static String getOpenId(String code){
        JSONObject user = getSnsapiBase(code);
        return (String) user.get("openid");
    }


    public static JSONObject getUid(String code){
        JSONObject j =  getSnsapiBase(code);
        String url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" +j.get("access_token")+
                "&openid=" +j.get("openid")+
                "&lang=zh_CN ";
        JSONObject jsonObject = JSONObject.parseObject(HttpRequestUtil.getURLContent(url).trim());

        return jsonObject;
    }

    public static void main(String[] args) {
        /*String openid = getOpenId("001of55K1Bk2T501pv8K19r75K1of55z");
        System.out.println(openid);*/

        JSONObject baseUser = getUid("0016gvnX1yIyBT0rEKlX14NAnX16gvn1");
        System.out.println(baseUser);
    }





}
