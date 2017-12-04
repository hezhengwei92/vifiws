package net.eoutech.utils;

/**
 * Created by admin on 2017/7/15.
 */
public class WXRefund {
    /*private EuFileUtil fileUtil = EuFileUtil.getInstance();
    private String AppID=fileUtil.getDispositionValue("uuwifi.weixin.APPID");//APPID
    private String MchID =fileUtil.getDispositionValue("uuwifi.weixin.PARTNER");//商户号
    private String Key=fileUtil.getDispositionValue("uuwifi.weixin.KEY");//密钥
    private String url=fileUtil.getDispositionValue("uuwifi.weixin.refund_order_url");//微信退款请求路径

    //参数为 退款单号  退款原总额  需退款金额
    public Map<String,String> doRefund(String out_trade_no,String total_fee,String refund_fee)throws Exception{
        HashMap<String,String> data=new HashMap<String,String>();
        data.put("out_trade_no",out_trade_no);//商户订单号
        data.put("out_refund_no",out_trade_no);//商户退款单号 唯一
        data.put("total_fee",total_fee);//订单总金额 单位为分
        data.put("refund_fee", refund_fee);//退款金额 单位为分 可以做部分退款
        data.put("refund_fee_type","CNY");//货币单位
        data.put("op_user_id",MchID);//操作员账号 默认为商户号

        HashMap<String,String> reqData=data;
        data.put("appid",AppID);//appid
        data.put("mch_id",MchID);//商户号
        data.put("nonce_str",UUID.randomUUID().toString().replace("-","").substring(0,32));//随机数生成算法
        data.put("sign_type","HMAC-SHA256");//签名类型
        data.put("sign", WXPayUtil.generateSignature(reqData, Key, "HMACSHA256"));//签名算法：一些随机的值 提供key和算法
        Map<String,String> respMap=this.refund(data);
        return respMap;
    }
    public Map<String, String> refund(Map<String, String> reqData) throws Exception {
        String reqBody=WXPayUtil.mapToXml(reqData);
        String resp=requestOnce(reqBody);
        return this.processResponseXml(resp);
    }

    private String requestOnce(String data)throws Exception {
        BasicHttpClientConnectionManager connManager;
        char[] password=MchID.toCharArray();
        //InputStream certStream=new WXPayUtil().getCertData();
        InputStream certStream=null;
        KeyStore ks=KeyStore.getInstance("PKCS12");
        //存储证书和密码
        ks.load(certStream,password);
        //实例化密钥库&初始化密钥工厂
        KeyManagerFactory kmf=KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks,password);
        //创建SSLContext
        SSLContext sslContext=SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(),null,new SecureRandom());
        SSLConnectionSocketFactory sslConnectionSocketFactory=new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1"},
                null,
                new DefaultHostnameVerifier()
        );
        connManager=new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https",sslConnectionSocketFactory)
                        .build(),
                null,
                null,
                null
        );
        HttpClient httpClient= HttpClientBuilder.create().setConnectionManager(connManager).build();
        HttpPost httpPost=new HttpPost(url);
        //设置 读取和连接时间  单位毫秒
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6*1000).setConnectTimeout(8*1000).build();
        httpPost.setConfig(requestConfig);
        StringEntity postEntity=new StringEntity(data,"UTF-8");
        httpPost.addHeader("Content-Type","text/xml");
        httpPost.addHeader("User-Agent","wxpay sdk java v1.0"+MchID);
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse=httpClient.execute(httpPost);
        HttpEntity httpEntity=httpResponse.getEntity();

        return EntityUtils.toString(httpEntity,"UTF-8");
    }

    *//**
     * 处理 HTTPS API返回数据，转换成Map对象。return_code为SUCCESS时，验证签名。
     * @param xmlStr API返回的XML格式数据
     * @return Map类型数据
     * @throws Exception
     *//*
    public Map<String, String> processResponseXml(String xmlStr) throws Exception {
        String RETURN_CODE = "return_code";
        String return_code;
        Map<String, String> respData = WXPayUtil.xmlToMap(xmlStr);
        if (respData.containsKey(RETURN_CODE)) {
            return_code = respData.get(RETURN_CODE);
        }
        else {
            throw new Exception(String.format("No `return_code` in XML: %s", xmlStr));
        }
        if (return_code.equals("FAIL")) {
            return respData;
        }
        else if (return_code.equals("SUCCESS")) {
            return respData;
        }
        else {
            throw new Exception(String.format("return_code value %s is invalid in XML: %s", return_code, xmlStr));
        }
    }*/
}
