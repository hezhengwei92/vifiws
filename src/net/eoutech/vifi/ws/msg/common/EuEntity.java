package net.eoutech.vifi.ws.msg.common;

import java.util.Date;

public class EuEntity {

	private String support = "";
	private String domain = "";
	private byte subscribeNum = 0;
	private String checkCode = "1111";
	private String state = "";
	private Integer subId = 0;
	private Date subTime = null;
	private String cny = "";
	private String sipip = "";
	private Integer sipport = 5060;
	private String idxVPXId = "";

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public byte getSubscribeNum() {
		return subscribeNum;
	}

	public void setSubscribeNum(byte subscribeNum) {
		this.subscribeNum = subscribeNum;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	public Date getSubTime() {
		return subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}

	public String getCny() {
		return cny;
	}

	public void setCny(String cny) {
		this.cny = cny;
	}

	public String getSipip() {
		return sipip;
	}

	public void setSipip(String sipip) {
		this.sipip = sipip;
	}

	public Integer getSipport() {
		return sipport;
	}

	public void setSipport(Integer sipport) {
		this.sipport = sipport;
	}

	public String getIdxVPXId() {
		return idxVPXId;
	}

	public void setIdxVPXId(String idxVPXId) {
		this.idxVPXId = idxVPXId;
	}

}
