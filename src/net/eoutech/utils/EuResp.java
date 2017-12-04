package net.eoutech.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class EuResp {
	private int code = 500;
	private String reason = "NULL";
	private Map<String, Object> kvs = new HashMap<String, Object>();

	public EuResp() {
		// empty
	}

	public EuResp(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	public int getCode() {
		return this.code;
	}

	public String getReason() {
		return reason;
	}

	public Map<String, Object> getKvs() {
		return kvs;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setCodeReason(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	public void setValues(String key, Object values) {
		kvs.put(key, values);
	}

	public void setValues(String key, List<String> list) {
		kvs.put(key, list);
	}

	public void setValues(String key, Map<String, String> map) {
		kvs.put(key, map);
	}

	public String toJsonString() {
		
		JSONObject result = new JSONObject(kvs);
		result.put(ViFiStaticParam.CODE, code);
		result.put(ViFiStaticParam.REASON, reason);
		
		return result.toJSONString();
	}

	public String toXmlString() {
		// not support now
		return "<XML>Not Support Now</XML>";
	}
}
