package net.eoutech.utils;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.StringUtils;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class EuStringUtil {

	/**
	 * 判断版本号，是否有新版本
	 * @param clientVer  客户端版本
	 * @param serverVer  服务器端版本
	 * @return
	 */
	public boolean hasNewVersion(String clientVer,String serverVer){
		if (clientVer == null || serverVer == null) {
			return false;
		}
		if (serverVer != null) {
			String[] server = serverVer.split(ViFiStaticParam.POINT);
			String[] clientSide = clientVer.split(ViFiStaticParam.POINT);
			if (server == null || clientSide == null) {
				
			}else {
				int min = server.length > clientSide.length ? clientSide.length : server.length;
				for (int i = 0; i < min; i++) {
					if (Integer.parseInt(server[i]) > Integer.parseInt(clientSide[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
     * 注意在使用中split点是需要转译的
     * @param agentId
     * @return
     */
    public static List< String > domainStringFormat ( String agentId ) {
        if ( org.apache.commons.lang.StringUtils.isEmpty( agentId ) || agentId.split( "\\." ).length - 1 <= 0 ) {
            return null;
        }
        List< String > list = new ArrayList< String >();

        String firstAgent = agentId.substring( 0, agentId.indexOf( "." ) );
        do {
            list.add( agentId );

            int lastPoint = agentId.lastIndexOf( "." );
            agentId = agentId.substring( 0, lastPoint );
        } while ( agentId.indexOf( ".", firstAgent.length() ) != -1 );

        return list;
    }
	
	public static int exsit(String[] values, String value) {
		if (value == null || values == null) {
			return -1;
		}
		for (int i = 0; i < values.length; i++) {
			String string = values[i];
			if (value.equals(string.trim())) {
				return i;
			}
		}
		return -1;
	}
	
	public static String myExceptionString(Exception e) {
		String myexception = "Exception:" + e.toString();
		StackTraceElement[] stack = e.getStackTrace();
		for (int i = 0; i < 6 && i < stack.length; i++) {
			String firstStack = stack[i].toString();
			int pos = firstStack.indexOf('(');
			myexception += (pos > 0) ? firstStack.substring(pos)
					: (" " + firstStack);
		}

		return myexception;
	}
	
	public static String buildRandomStr(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static String buildPayRandomStr(int length) {
		String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static Map<String, String> getPass(String passValue){
		Map<String, String> map = new HashMap<String, String>();
		if(passValue == null || "".equals(passValue)){
			return null;
		}
		
		String [] sz = passValue.split("\\|");
		
		for (int i = 0; i < sz.length; i++) {
			
			String sz2 [] = sz[i].split(":");
			
			String pass = "";
			if(sz2.length == 2){
				pass = sz2[1];
			}
			map.put(sz2[0], pass);
		}
		
		return map;
	}
	
	public static Map<String, String> getRequestParams (HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String requestQuery = request.getQueryString(); 
		try {
			requestQuery = URLDecoder.decode(requestQuery,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String [] sz = requestQuery.split("&");
		for (String s : sz) {
			String [] s1 = s.split("=");
			
			String value = "";
			if (s1.length == 2) {
				value = s1[1];
			}
			map.put(s1[0], value);
		}
		return map;
	}
	
	public static String getRequestURL (String url) {
		String model = "http://";
		
		if(!StringUtils.isNullOrEmpty(url)){
			return url.substring(url.indexOf(model)+model.length(), url.lastIndexOf(":"));
		}
		return "";
	}


	/**
	 * 生成订单号
	 *
	 * @return 生成的订单号
	 */
	public static String createOrderID() {
		StringBuffer buffer = new StringBuffer("YYL");
		SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
		Date date = new Date();
		buffer.append(sf.format(date));
		buffer.append(EuStringUtil.buildRandomStr(4));
		return buffer.toString();
	}
	/**
	 * 生成用户编号 纯12位数字
	 */
	public static String createUID() {
		StringBuffer buffer = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		buffer.append(sf.format(date));
		buffer.append(EuStringUtil.buildRandomStr(4));
		return buffer.toString();
	}

	public static String  createJson(Map<String,String[]> reqStr){
		String reqJson = JSON.toJSONString(reqStr);
		reqJson = reqJson.replace("[","");
		reqJson = reqJson.replace("]","");
		return reqJson;
	}


}
