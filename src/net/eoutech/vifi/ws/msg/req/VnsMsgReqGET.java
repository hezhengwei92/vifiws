package net.eoutech.vifi.ws.msg.req;

import org.apache.commons.lang.StringUtils;

import net.eoutech.annotation.MyAnno;

/**
 * Created by SUU on 2016/3/8.
 */
public class VnsMsgReqGET {

	private String fip;
	private String proto;
	private String msg;
	// 新增
	private Integer mnc = 0;
	private String imsi = "";
	
	@MyAnno( tag = 83 )
	private String vid;
	@MyAnno( tag = 78 )
	private int seq;
	@MyAnno( tag = 84 )
	private String sid;
	@MyAnno( tag = 79 )
	private String tgt;
	@MyAnno( tag = 72 )
	private String iccid = "";
	@MyAnno( tag = 71 )
	private Integer cellid = 0;
	@MyAnno( tag = 73 )
	private Integer lfc = 0;
	@MyAnno( tag = 74 )
	private Integer lac = 0;
	@MyAnno( tag = 75 )
	private Integer mcc = 0;
	@MyAnno( tag = 76 )
	private Integer opi = 0;
	@MyAnno( tag = 77 )
	private String rsp;
	@MyAnno( tag = 59 )
	private String dver;

	public boolean checkReqData() {
		if (StringUtils.isBlank(vid) || StringUtils.isBlank(tgt)) {
			return false;
		}
		if ( "MOD".equals( tgt ) ) {
			this.tgt = "GOIP";
		}
		if (!"GOIP".equals(tgt) && !"SIM".equals(tgt)) {
			return false;
		}
		return true;
	}

	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	public String getProto() {
		return proto;
	}

	public void setProto(String proto) {
		this.proto = proto;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTgt() {
		return tgt;
	}

	public void setTgt(String tgt) {
		this.tgt = tgt;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Integer getOpi() {
		return opi;
	}

	public void setOpi(Integer opi) {
		this.opi = opi;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getRsp() {
		return rsp;
	}

	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public Integer getMcc() {
		return mcc;
	}

	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getMnc() {
		return mnc;
	}

	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}

	public Integer getCellid() {
		return cellid;
	}

	public void setCellid(Integer cellid) {
		this.cellid = cellid;
	}

	public Integer getLac() {
		return lac;
	}

	public void setLac(Integer lac) {
		this.lac = lac;
	}

	public Integer getLfc() {
		return lfc;
	}

	public void setLfc(Integer lfc) {
		this.lfc = lfc;
	}

	public String getDver() {
		return dver;
	}

	public void setDver(String dver) {
		this.dver = dver;
	}
	
}
