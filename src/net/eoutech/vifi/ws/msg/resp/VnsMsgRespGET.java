package net.eoutech.vifi.ws.msg.resp;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import net.eoutech.annotation.MyAnno;
import net.eoutech.vifi.ws.msg.common.AsCodeEnum;
import net.eoutech.vifi.ws.msg.common.SipCodeEunm;
import net.eoutech.vifi.ws.msg.req.VnsMsgReqGET;

/**
 * Created by SUU on 2016/3/8.
 */
public class VnsMsgRespGET {
	
	private String msg = "GET-ACK";
	private Integer sc = AsCodeEnum.AS_SUCCESS.code;
	
    @MyAnno( tag = 81 )
    private String sr = AsCodeEnum.AS_SUCCESS.desc;
    @MyAnno( tag = 82 )
    private int seq;
    @MyAnno( tag = 83 )
    private String vid;
    @MyAnno( tag = 84 )
    private String sid;
    @MyAnno( tag = 85 )
    private int exp;
    @MyAnno( tag = 86 )
    private int ven;
    @MyAnno( tag = 87 )
    private String apn;
    @MyAnno( tag = 88 )
    private String num;
    @MyAnno( tag = 89 )
    private String usr;
    @MyAnno( tag = 90 )
    private String pwd;
    @MyAnno( tag = 91 )
    private String actions = "";
    @MyAnno( tag = 92 )
    private String vsw_pro;
    @MyAnno( tag = 93 )
    private String vsw_ip;
    @MyAnno( tag = 94 )
    private int vsw_port;
    @MyAnno( tag = 95 )
    private int log;
    @MyAnno( tag = 96 )
    private String log_id;
    @MyAnno( tag = 97 )
    private String log_ip;
    @MyAnno( tag = 98 )
    private int log_port;
    @MyAnno( tag = 99 )
    private String log_pro;
    @MyAnno( tag = 67 )
    private int update_flag;
    @MyAnno( tag = 59 )
    private String dver;
    @MyAnno( tag = 68 )
    private String update_url;
    
    private List< Map< String, String > > cmd;

    // 新增日志返回接口
    private UUWiFiLogCfg logCfg;
    
    public VnsMsgRespGET () {}

    public VnsMsgRespGET( VnsMsgReqGET req ) {
        this.seq = req.getSeq();
        this.vid = req.getVid();
        this.ven = 1;
    }

    public Integer getSc() {
		return sc;
	}

	public void setSc(Integer sc) {
		this.sc = sc;
	}

	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public int getSeq() {
        return seq;
    }

    public void setSeq( int seq ) {
        this.seq = seq;
    }

    public String getVid() {
        return vid;
    }

    public void setVid( String vid ) {
        this.vid = vid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid( String sid ) {
        this.sid = sid;
    }

    public int getExp() {
        return exp;
    }

    public void setExp( int exp ) {
        this.exp = exp;
    }

    public String getVsw_ip() {
        return vsw_ip;
    }

    public void setVsw_ip( String vsw_ip ) {
        this.vsw_ip = vsw_ip;
    }

    public int getVsw_port() {
        return vsw_port;
    }

    public void setVsw_port( int vsw_port ) {
        this.vsw_port = vsw_port;
    }

    public String getVsw_pro() {
        return vsw_pro;
    }

    public void setVsw_pro( String vsw_pro ) {
        this.vsw_pro = vsw_pro;
    }

    public int getVen() {
        return ven;
    }

    public void setVen( int ven ) {
        this.ven = ven;
    }

    public String getApn() {
        return apn;
    }

    public void setApn( String apn ) {
        this.apn = apn;
    }

    public String getNum() {
        return num;
    }

    public void setNum( String num ) {
        this.num = num;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr( String usr ) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd( String pwd ) {
        this.pwd = pwd;
    }

    public String getActions() {
        return actions;
    }

    public void setActions( String actions ) {
        this.actions = actions;
    }

    public List< Map< String, String > > getCmd() {
        return cmd;
    }

    public void setCmd( List< Map< String, String > > cmd ) {
        this.cmd = cmd;
    }

    public UUWiFiLogCfg getLogCfg() {
        return logCfg;
    }

    public void setLogCfg( UUWiFiLogCfg logCfg ) {
        this.logCfg = logCfg;
    }
    
    public int setSipCode( SipCodeEunm sipCode ) {
        this.sc = sipCode.code;
        this.sr = sipCode.toString();
        return sc;
    }
    
    public int getLog() {
		return logCfg == null ? -1 : logCfg.getLog();
	}

	public void setLog(int log) {
		this.log = log;
	}

	public String getLog_id() {
		return logCfg == null ? "" : logCfg.getLog_id();
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getLog_ip() {
		return logCfg == null ? "" : logCfg.getLog_ip();
	}

	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}

	public int getLog_port() {
		return logCfg == null ? -1 : logCfg.getLog_port();
	}

	public void setLog_port(int log_port) {
		this.log_port = log_port;
	}

	public String getLog_pro() {
		return logCfg == null ? "" : logCfg.getLog_pro();
	}

	public void setLog_pro(String log_pro) {
		this.log_pro = log_pro;
	}

	private StringBuilder toStrJS( String name, Object value, String ending ) {
        if ( value == null ) {
            value = "";
        }
        return new StringBuilder( "\"" ).append( name ).append( "\":\"" ).append( value ).append( "\"" ).append( ending );
    }

    private StringBuilder toIntJS( String name, Object value, String ending ) {
        if ( value == null ) {
            value = "0";
        }
        return new StringBuilder( "\"" ).append( name ).append( "\":" ).append( value ).append( ending );
    }

    private StringBuilder toObjJS ( String name, Object value, String ending ) {
        return new StringBuilder( "\"" ).append( name ).append( "\":" ).append( JSON.toJSONString( value ) ).append( ending );
    }
    
    public int getUpdate_flag() {
		return update_flag;
	}

	public void setUpdate_flag(int update_flag) {
		this.update_flag = update_flag;
	}

	public String getDver() {
		return dver;
	}

	public void setDver(String dver) {
		this.dver = dver;
	}

	public String getUpdate_url() {
		return update_url;
	}

	public void setUpdate_url(String update_url) {
		this.update_url = update_url;
	}

	public StringBuilder toJSONString() {
    	StringBuilder json = new StringBuilder( "{" )
                 .append( toStrJS( "msg", msg, "," ))
                 .append( toIntJS("code", sc, ","))
                 .append(toStrJS("decs", sr, ",")).append(toIntJS("seq", seq, ","))
                .append(toStrJS("vid", vid, ",")).append(toStrJS("sid", sid, ",")).append(toIntJS("exp", exp, ","))
                .append(toIntJS("ven", ven, ",")).append(toStrJS("dp_apn", apn, ","))
                .append(toStrJS("dp_num", num, ",")).append(toStrJS("dp_usr", usr, ","))
                .append(toStrJS("dp_pwd", pwd, ",")).append(toStrJS("action", actions, ","))
                .append(toIntJS("update_flag", update_flag, ",")).append( toStrJS("dver", dver, ",")).append(toStrJS("update_url", update_url, ","));

        if ( cmd != null && cmd.size() != 0 ) {
            for (Map<String, String> map : cmd) {

                for ( Map.Entry< String, String > entry : map.entrySet() ) {
                    json.append( toStrJS( entry.getKey(), entry.getValue(), "" ) );
                }

            }
        }

        if ( logCfg == null ) {
        	logCfg = new UUWiFiLogCfg( "", 0, "" );
        	logCfg.setLog( 0 );
        	logCfg.setLog_id( "" );
            json.append( toObjJS( "set", logCfg , "," ) );
        } else {
            json.append( toObjJS( "set", logCfg, "," ) );
        }

        json.append( toStrJS( "vsw_pro", vsw_pro, "," ) ).append(toStrJS("vsw_ip", vsw_ip, ",")).append(toIntJS("vsw_port", vsw_port, "}"));
        return json;
    }
}
