package net.eoutech.base.tcpserver.entity;

import java.util.HashMap;
import java.util.Map;

public class EouMsg {

	private byte hPro;
    private byte hFlag;
    private byte hMogic;
    private byte hMsgtype;
    private byte hCode;
    private int hSessid;
    private short hSn;
    private short hLen;
    private Map<Byte, Object> datas = new HashMap<Byte, Object>();
    private String remoteIP;
    private int remotePort;

	//根据日志列出的字段(登录返回)
//	private String action;
//	private Integer code;
	private String desc;
//	private Integer exp;
//	private Integer seq;重复
//	private Integer update_flag;
	private String vns_ip;
	private Integer port;
	private String vns_pro;

	//根据日志列出的字段（获取返回）
	private String msg;
	private Integer code;
	private String decs;
	private Integer seq;
	private String vid;
	private String sid;
	private Integer exp;
	private Integer ven;
	private String dp_apn;
	private String dp_num;
	private String dp_usr;
	private String dp_pwd;
	private String action;
	private Integer update_flag;
	private String dver;
	private String update_url;
	private Map<String,Object> set;
	private String vsw_pro;
	private String vsw_ip;
	private String vsw_port;

	//根据日志列出的字段（登录请求）
	private String icc;
	private Integer net;
	private String non;
//	private String opi;重复
//	private String rsp;重复
//	private Integer seq;重复
	private String ver;
//	private String vid;重复

	//根据日志列出的字段（获取请求）
//	private String dver;
	private Integer cellid;
	private String iccid;
	private String imsi;
	private Integer lac;
	private Integer lfc;
	private Integer mcc;
	private Integer mnc;
	private Integer opi;
	private String rsp;
//	private Integer seq;
//	private String sid;
	private String tgt;
//	private String vid;


	public EouMsg() {
	}

	public EouMsg(byte pro, byte flag, byte msgtype, int sessionid, short sn, short len) {
        this.hPro = pro;
        this.hFlag = flag;
        this.hMogic = -118;
        this.hMsgtype = msgtype;
        this.hCode = 0;
        this.hSessid = sessionid;
        this.hSn = sn;
        this.hLen = len;
        this.datas.clear();
    }
    
    public EouMsg(EouMsg req) {
        this.hPro = req.hPro;
        this.hFlag = req.hFlag;
        this.hMogic = -118;
        this.hMsgtype = (byte)(req.hMsgtype | 128);
        this.hCode = 0;
        this.hSessid = req.hSessid;
        this.hSn = req.hSn;
        this.hLen = 0;
        this.datas.clear();
    }

	public byte gethPro() {
		return this.hPro;
	}

	public byte gethFlag() {
		return this.hPro;
	}

	public byte gethMogic() {
		return this.hMogic;
	}

	public byte gethMsgtype() {
		return this.hMsgtype;
	}

	public int getMsgtype() {
		return this.hMsgtype & 255;
	}

	public void setReqMsgtype(int reqMsgtype) {
		this.hMsgtype = (byte)reqMsgtype;
	}

	public byte gethCode() {
		return this.hCode;
	}

	public void sethCode(byte hCode) {
		this.hCode = hCode;
	}

	public void setCode(int code) {
		this.hCode = (byte)code;
	}

	public int gethSessid() {
		return this.hSessid;
	}

	public void sethSessid(int hSessid) {
		this.hSessid = hSessid;
	}

	public short gethSn() {
		return this.hSn;
	}

	public short gethLen() {
		return this.hLen;
	}

	public void putTLVData(Byte t, Object v) {
		this.datas.put(t, v);
	}

	public Map<Byte, Object> getTLVDatas() {
		return this.datas;
	}

	public String getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Map<Byte, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<Byte, Object> datas) {
		this.datas = datas;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getUpdate_flag() {
		return update_flag;
	}

	public void setUpdate_flag(Integer update_flag) {
		this.update_flag = update_flag;
	}

	public String getVns_ip() {
		return vns_ip;
	}

	public void setVns_ip(String vns_ip) {
		this.vns_ip = vns_ip;
	}

	public String getVns_pro() {
		return vns_pro;
	}

	public void setVns_pro(String vns_pro) {
		this.vns_pro = vns_pro;
	}

	//拼接字符型的数据
	protected StringBuilder toStrJS( String name, Object value, String ending ) {
		if ( value == null ) {
			value = "";
		}
		return new StringBuilder( "\"" ).append( name ).append( "\":\"" ).append( value ).append( "\"" ).append( ending );
	}

	//拼接整型的数据
	protected StringBuilder toIntJS( String name, Object value, String ending ) {
		if ( value == null ) {
			value = "0";
		}
		return new StringBuilder( "\"" ).append( name ).append( "\":" ).append( value ).append( ending );
	}
}
