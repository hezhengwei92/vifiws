package net.eoutech.vifi.ws.msg.resp;

public class VwsRespCommon {

	//操作对应的结果码
	protected Integer code = VwsEnumCode.WSRESP_200_SUCCESS.code;
	//结果码对应的原因
	protected String msg;
	protected String reason = VwsEnumCode.WSRESP_200_SUCCESS.reason;

	public int setCodeReason(VwsEnumCode vwsEnumCode) {
		this.code = vwsEnumCode.code;
		this.reason = vwsEnumCode.reason;
		return code;
	}

	public Integer getCode() {
		return code;
	}

	public int setCode(Integer code) {
		this.code = code;
		return code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toString() {
		return "{\"code\":" + code + ",\"msg\":\"" + msg + "\"}";
	}

	protected StringBuilder toStrJS( String name, Object value, String ending ) {
        if ( value == null ) {
            value = "";
        }
        return new StringBuilder( "\"" ).append( name ).append( "\":\"" ).append( value ).append( "\"" ).append( ending );
    }

    protected StringBuilder toIntJS( String name, Object value, String ending ) {
        if ( value == null ) {
            value = "0";
        }
        return new StringBuilder( "\"" ).append( name ).append( "\":" ).append( value ).append( ending );
    }
}
