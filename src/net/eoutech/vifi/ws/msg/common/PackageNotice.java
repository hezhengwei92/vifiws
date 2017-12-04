package net.eoutech.vifi.ws.msg.common;

import com.alibaba.fastjson.JSON;

public class PackageNotice {

	private String msgType;
	private String msgDate;
	private String content;
	
	public PackageNotice(String msgType, String msgDate, String content) {
		super();
		this.msgType = msgType;
		this.msgDate = msgDate;
		this.content = content;
	}

	@Override
	public String toString() {
		return JSON.toJSONString( this ); 
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
