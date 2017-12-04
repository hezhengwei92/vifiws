package net.eoutech.vifi.ws.vns.service;

import net.eoutech.vifi.ws.msg.req.VwsReqCommon;
import net.eoutech.vifi.ws.msg.resp.VwsRespCommon;

public interface VwsAppServiceInterface {

	public abstract int doHandle (VwsReqCommon req, VwsRespCommon resp);

}
