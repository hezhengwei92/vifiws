package net.eoutech.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class HttpRequestUtils {
    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
//    public static JSONObject httpPost(String url,JSONObject jsonParam){
//        return httpPost(url, jsonParam, false);
//    }
 
    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
//    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
//        //post请求返回结果
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        JSONObject jsonResult = null;
//        HttpPost method = new HttpPost(url);
//        try {
//            if (null != jsonParam) {
//                //解决中文乱码问题
//                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
//                entity.setContentEncoding("UTF-8");
//                entity.setContentType("application/json");
//                method.setEntity(entity);
//            }
//            HttpResponse result = httpClient.execute(method);
//            System.out.println(result+"------------");
//            url = URLDecoder.decode(url, "UTF-8");
//            /**请求发送成功，并得到响应**/
//            if (result.getStatusLine().getStatusCode() == 200) {
//                String str = "";
//                try {
//                    /**读取服务器返回过来的json字符串数据**/
//                    str = EntityUtils.toString(result.getEntity());
//                    if (noNeedResponse) {
//                        return null;
//                    }
//                    /**把json字符串转换成json对象**/
//                    jsonResult = JSONObject.fromObject(str);
//                } catch (Exception e) {
//                    System.out.println("post请求提交失败:" + url);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("post请求提交失败:" + url);
//        }
//        return jsonResult;
//    }
 
	/**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    private static String post(String url,String reqEncoding,String respEncoding,Map<String, String> paramMap) {
        String resStr = "";
        // 创建httppost
        HttpClient httpclient=new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                url);
        // 创建参数队列
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if(paramMap!=null&&paramMap.keySet()!=null){
             for(String key:paramMap.keySet()){
                 parameters.add(new BasicNameValuePair(key,paramMap.get(key)));
             }
         }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(parameters, reqEncoding);
            httppost.setEntity(uefEntity);
            HttpResponse response;
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resStr = EntityUtils.toString(entity,respEncoding);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
           // httpclient.getConnectionManager().shutdown();
        }
        return resStr;
    }

    /**
     * 跳转到另外的服务
     *
     * @param connectIp 需要连接的IP
     * @param appPort 需要连接的端口
     * @param map 需要请求的数据
     * @return 返回执行的结果
     */
    public static String jumpResult(String connectIp,Integer appPort,Map<String,String> map){
        String url = "http://"+connectIp.substring(0,connectIp.indexOf(":"))+":"+appPort+"/vrsws/vappws.json";
        String result = post(url, "UTF-8","UTF-8",map);
        return result;
    }

    public static byte[] jumpTest(String connectIp,Integer appPort,Map<String,String> map){
        String url = "http://"+connectIp+":"+appPort+"/vrsws/auth";
        String result = post(url, "UTF-8","UTF-8",map);
        return result.getBytes();
    }
    
    
    
    
 
//    /**
//     * 发送get请求
//     * @param url    路径
//     * @return
//     */
//    public static JSONObject httpGet(String url){
//        //get请求返回结果
//        JSONObject jsonResult = null;
//        try {
//            DefaultHttpClient client = new DefaultHttpClient();
//            //发送get请求
//            HttpGet request = new HttpGet(url);
//            HttpResponse response = client.execute(request);
// 
//            /**请求发送成功，并得到响应**/
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                /**读取服务器返回过来的json字符串数据**/
//                String strResult = EntityUtils.toString(response.getEntity());
//                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.fromObject(strResult);
//                url = URLDecoder.decode(url, "UTF-8");
//            } else {
//                System.out.println("get请求提交失败:" + url);
//            }
//        } catch (IOException e) {
//            System.out.println("get请求提交失败:" + url);
//        }
//        return jsonResult;
//    }
}
