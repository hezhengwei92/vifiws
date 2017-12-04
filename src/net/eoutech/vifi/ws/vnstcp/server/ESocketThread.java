//package net.eoutech.vifi.ws.vnstcp.server;
//
//import com.alibaba.fastjson.JSON;
//import net.eoutech.base.tcpserver.entity.EouMsg;
//import net.eoutech.utils.EReflectUtils;
//import net.eoutech.utils.EuStringUtil;
//import net.eoutech.utils.LogUtils;
//import net.eoutech.vifi.ws.msg.common.SipCodeEunm;
//import net.eoutech.vifi.ws.msg.req.VnsMsgReqGET;
//import net.eoutech.vifi.ws.msg.resp.VnsMsgRespGET;
//import net.eoutech.vifi.ws.vns.service.uuwifi.VnsAuthorGETService;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//
//public class ESocketThread {// implements EouHandler
//
//	private ApplicationContext ac;
//	private static VnsAuthorGETService service;
//
//	public ESocketThread () {
//		ac = new FileSystemXmlApplicationContext( "classpath*:applicationContext.xml" );
//		if ( ESocketThread.service == null ) {
//			ESocketThread.service = ac.getBean( VnsAuthorGETService.class );
//		}
//	}
//
//	//@Override
//	public void doMsgHandle(EouMsg req, EouMsg resp) {
//		// 取消息类型
//		try {
//			int msgType = req.gethMsgtype();
//			LogUtils.info( "tcp server receive msg type:{0}|socket req:{1}", msgType, req.toString() );
//			if ( msgType == 3 ) {  // 服务寻址GET
////				LogUtils.info("get important params,vid:{0},tgt:{1},mcc:{2},opi:{3},ip:{4}", req.getTLVStr(83), req.getTLVStr(79), req.getTLVInt(75), req.getTLVInt(76), req.getRemoteIP());
//
//				VnsMsgReqGET vnsReq = EReflectUtils.makeReqMsg( req, VnsMsgReqGET.class );
//				LogUtils.info("RecvMsg(GET):" + JSON.toJSONString( vnsReq ) );
//				VnsMsgRespGET vnsResp;
//				int doRes;
//				if ( vnsReq == null ) {
//					LogUtils.info("req msg GET null.bad request");
//					vnsResp = new VnsMsgRespGET();
//					doRes = vnsResp.setSipCode(SipCodeEunm.SIP_400_BAD_REQUEST);
//				} else {
//					LogUtils.info("tcp connected client ip:"+req.getRemoteIP());
//					vnsReq.setFip( req.getRemoteIP() );
//					vnsResp = new VnsMsgRespGET( vnsReq );
////					doRes = service.doAuthorization( vnsReq, vnsResp );
//				}
////				LogUtils.info( "RespMsg(GET):{0}|doRes:{1}", vnsResp.toJSONString().toString(), doRes );
//
//				// 设置返回的code
//				resp.setCode( getRespCode( vnsResp.getSc() ) );
//				EReflectUtils.setRespTlvDatas( resp, vnsResp );
//
//			} else {
//
//				LogUtils.info( "receive package bad msgType=" + msgType );
//				resp.setCode( 44 );
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			LogUtils.error( "esocket thread throw exception:" + EuStringUtil.myExceptionString( e ) );
//			resp.setCode( 50 );
//		}
//
//		LogUtils.info( "send bytes:" + resp.toString() );
//
//	}
//
//	private int getRespCode ( int sc ) {
//		int code = 43;
//		if ( sc == 400 ) {
//			code = 47;
//		} else if ( sc == 403 ) {
//			code = 43;
//		} else if ( sc == 500 ) {
//			code = 50;
//		} else if ( sc == 0) {
//			code = 0;
//		}
//		return code;
//
//	}
//
//
//
//}
