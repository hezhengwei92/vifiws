package net.eoutech.utils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mysql.jdbc.StringUtils;

public class EuReq {
	private String ip = ""; // from ip address
	private int port = 0; // from port

	private String title = "";
	private String content = "";
	private String appType = "APPADR";
	private String ver = "1.0";

	private Map<String, Object> kvs = new HashMap<String, Object>();
	
	public EuReq () {
	}

	public EuReq(HttpServletRequest request,int type) {
		if (ViFiStaticParam.DOGET == type) {   //
			initGetParams(request);
		}else if (ViFiStaticParam.DOFBKPOST == type) {
			initFeedbackParams(request);
		}
	}

	private void initGetParams(HttpServletRequest request) {
		
		try {
			this.ip = InternetProtocol.getRemoteAddr(request);
			this.port = request.getRemotePort();

			String requestQuery = request.getQueryString();
			requestQuery = URLDecoder.decode(requestQuery, "UTF-8");
			
			LogUtils.dbg("EuReq initGetParams info requestString = {0}", requestQuery);

			setMapParams(requestQuery);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.dbg("EuReq initGetParams exception \r\n{0}", EuStringUtil.myExceptionString(e));
		}
	}

	private void initFeedbackParams(HttpServletRequest request) {
		try {
			String requestQuery = request.getQueryString();
			requestQuery = URLDecoder.decode(requestQuery, "UTF-8");
			setMapParams(requestQuery);
			
			title = URLDecoder.decode(request.getParameter(ViFiStaticParam.TITLE), "UTF-8");
			content = URLDecoder.decode(request.getParameter(ViFiStaticParam.CONTENT), "UTF-8");
			
			MultipartHttpServletRequest multRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> fileList = multRequest.getFiles(ViFiStaticParam.FILE);
			if (fileList.size() != 0) {
				kvs.put(ViFiStaticParam.FILE, fileList.get(0));
			}
			
			LogUtils.dbg("EuReq initFeedbackParams post_info:title={0},content={1},fileSize={2}", title,content,fileList.size());
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.dbg("EuReq initFeedbackParams exception \r\n{0}", EuStringUtil.myExceptionString(e));
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public boolean containsKey(String key) {
		return kvs.containsKey(key);
	}
	
	public Object getObject (String key) {
		return (kvs.containsKey(key) ? kvs.get(key) : null);
	}

	public String getStr(String key) {
		return (kvs.containsKey(key) ? kvs.get(key).toString() : "");
	}

	public int getInt(String key) {
		try {
			return Integer.parseInt(getStr(key));
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * build testNG param
	 * @param requestStr  format:uid=&pwd=&pid=&cmd=...
	 */
	public void setMapParams (String requestStr) {
		
		if (!StringUtils.isNullOrEmpty(requestStr)) {
			String[] sz = requestStr.split("&");
			for (String s : sz) {
				String[] s1 = s.split("=");

				String value = "";
				if (s1.length == 2) {
					value = s1[1];
				}
				kvs.put(s1[0], value);
			}
		}
	}
}
