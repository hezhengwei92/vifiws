package net.eoutech.vifi.ws.msg.req;

import net.eoutech.utils.LogUtils;

public enum VwsEnumReqType {



 	UNKNOWN;
	
	public static VwsEnumReqType myValueOf ( String cmd ) {
		try {
			return VwsEnumReqType.valueOf( cmd );
		} catch (Exception e) {
			LogUtils.error( "VwsEnumReqType parse exception:"+ cmd + "|UNKNOWN REQUEST TYPE");
			return VwsEnumReqType.UNKNOWN;
		}
	}
	
}
