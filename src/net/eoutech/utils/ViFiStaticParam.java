package net.eoutech.utils;

//import net.eoutech.vifi.ws.msg.resp.UUWiFiLogCfg;

public class ViFiStaticParam {
	public static int UU_SESSION_ID = 0;
//	public static UUWiFiLogCfg UUWIFI_LOG_CONFIGURE = null;
	public static final Integer UUWIFI_LOG_IDT = 7;
    public static final String UUWIFI_LOG_IP = "UUWIFI_LOG_IP";
    public static final String UUWIFI_LOG_PORT = "UUWIFI_LOG_PORT";
    public static final String UUWIFI_LOG_ID = "";
    public static final String UUWIFI_LOG_PRO = "UDP";
	
	public static final String OFFLINE = "N";
	public static final String STATUS_NO_SUPPORT_RGT = "0";
	public static int initKeyUserID = 0;
	public static int initWechatId = 0;
	public static int initCRTAppID = 0;
	public static int initExpire = 0;

	public static final String UUWIFI_ALIPAY_TYPE = "ALIPAY";
	public static final String UUWIFI_WEIXIN_TYPE = "WXPAY";//WEIXIN
	public static final String UUWIFI_ACTPAY_TYPE = "ACCOUNT";
	
	
	public static final String UUWIFI_PAY_SUCCESS = "SUCCESS";
	
	public static final String RESP_BLACK_LIST = "{\"code\":403,\"reason\":\"WSRESP_403_BLACKLIST\"}";
	
	/**
	 * ccode
	 */
	public static final String CHINA_CCODE = "86";
	
	/**
	 * send message
	 */
	public static final String DEFAULT_SMS_LANG = "en_US";
	public static final String DEFAULT_SMS_CODE = "#{CODE}";
	
	public static final String FB_UID = "pms";
	public static final String FB_PWD = "pms123";
	
	/**
	 * public parameter
	 */
	public static final String UID = "uid";
	public static final String PWD = "pwd";
	public static final String PID = "pid"; 
	public static final String CMD = "cmd";
	public static final String DEV = "dev";
	public static final String VER = "ver";
	public static final String CODE = "code";
	public static final String BALANCE = "balance";
	public static final String APPTYPE = "appType";
	public static final String APPID = "appId";
	
	/**
	 * register parameter
	 */
	public static final String RGT = "RGT";
	public static final String CCODE = "ccode";
	public static final String PHONENUM = "phonenum";
	public static final String SMSCK = "smsck";
	public static final String LANG = "lang";
	public static final String OS = "os";
	
	/**
	 * login parameter
	 */
	public static final String IN = "IN";
	public static final String SIPPORT = "sipport";
	public static final String SIPIP = "sipip";
	public static final String NEWVER = "newver";
	public static final String PARAMETERS = "PARAMETERS";
	public static final String PASSWORD = "password";
	public static final String PHONENUMBER = "phonenumber";
	public static final String DOMAIN = "domain";
	public static final String APPADR = "APPADR";
	public static final String APPIOS = "APPIOS";
	public static final String SUBSCRIBENUM = "subscribeNum";
	public static final String SIPEXPIRE = "sipExpire";
	public static final String USERTYPE = "userType";
	
	/**
	 * logout paramter
	 */
	public static final String OUT = "OUT";
	public static final String ACT = "act";
	
	/**
	 * updatePass paramter
	 */
	public static final String UPDATEPASS = "PWD";
	public static final String NPWD = "NPWD";
	
	/**
	 * feedback parameter
	 */
	public static final String FBK = "FBK";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String FILE = "file";
	
	public static final String W = "W";
	public static final String P = "P";
	public static final String C = "C";
	
	public static final String QUERYTYPE = "queryType";
	public static final String FEEDBACKID = "feedbackId";
	public static final String EXTID = "extId";
	public static final String EXTST = "extSt";

	/**
	 * resetPass paramter
	 */
	public static final String RST = "RST";
	
	
	/**
	 * balance paramter
	 */
	public static final String BLN = "BLN";
	public static final String REWARD = "reward";
	public static final String CURRENCY = "currency";
	public static final String TD = "td";
	public static final String TC = "tc";
	public static final String FCD = "fcd";
	
	/**
	 * bind paramter
	 */
	public static final String MYACT = "myact";
	public static final String APP_FORCE_BIND = "y";
	public static final String APP_UNFORCE_BIND = "n";
	public static final String BIND = "bind";
	
	
	/**
	 * http paramter
	 */
	public static final int DOGET = 0;
	public static final int DOFBKPOST = 1;
	
	
	/**
	 * SIP Configure
	 */
	public static final String SIP_EXPIRE_DFT = "SIP_EXPIRE_DFT";
	public static final String SIP_EXPIRE_WIFI = "SIP_EXPIRE_WIFI";
	
	
	/**
	 * tbVersion state
	 */
	public static final String VSTATE = "V";   //有效
	public static final String USTATE = "U";   //有效，并有升级版本
	public static final String ASTATE = "A";   //无效，强制用户升级
	public static final String NEWESTVER = "0";
	
	public static final String VERSION = "version";
	public static final String STATE = "state";
	public static final String DOWNLOADURL = "downloadURL";
	
	/**
	 * tbCDR parameter
	 * keyCDRID,idxUserId,cdrType,direction,caller,callee,StartTime,StopTime,cost
	 */
	public static final String CDRID = "id";
	public static final String CDRTYPE = "cdrType";
	public static final String DIRECTION = "direction";
	public static final String CALLER = "caller";
	public static final String CALLEE = "callee";
	public static final String STARTTIME = "startTime";
	public static final String STOPTIME = "stopTime";
	public static final String COST = "cost";
	
	public static final String CDRS = "cdrs";
	
	/**
	 * tbCountSrcIP
	 */
	public static final int IDXSERVTYPE_WS = 101;
	
	
	/** request parameter */
	public static final String VID = "vid";
	public static final String NON = "non";
	public static final String RSP = "rsp";
	public static final String NET = "net";
	public static final String ICC = "icc";
	public static final String OPI = "opi";
	
	public static final String ERY = "ery";
	public static final String DATA = "data";
	
	
	public static final String KEY = "key";
	public static final String AID = "aid";
	
	/** response parameter */
	public static final String DESC = "desc";
	public static final String VNS = "vns";
	public static final String ODT = "odt";
	public static final String VSWIP = "vswip";
	public static final String VSWPT = "vswpt";
	public static final String GOIPID = "goipID";
	public static final String SIMPID = "simpID";
	public static final String VPXIP = "vpxip";
	public static final String VPXPT = "vpxpt";
	public static final String REASON = "reason";
	
	/** response or request */
	public static final String SEQ = "seq";
	
	
	/** others */
	public static final String POINT = "\\.";
	public static final String ERROR_PARAM = "{\"code\":-100,\"reason\":\"error param\"}";
	
}
