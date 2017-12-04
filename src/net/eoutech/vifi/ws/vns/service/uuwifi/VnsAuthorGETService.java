//package net.eoutech.vifi.ws.vns.service.uuwifi;
//
//import net.eoutech.utils.EuStringUtil;
//import net.eoutech.utils.LogUtils;
//import net.eoutech.vifi.ws.entity.TbCardStatus;
//import net.eoutech.vifi.ws.entity.TbResidualFlow;
//import net.eoutech.vifi.ws.msg.common.SipCodeEunm;
//import net.eoutech.vifi.ws.msg.req.VnsMsgReqGET;
//import net.eoutech.vifi.ws.msg.resp.VnsMsgRespGET;
//import net.eoutech.vifi.ws.vns.service.common.CardStatusService;
//import net.eoutech.vifi.ws.vns.service.common.ConfigureService;
//import net.eoutech.vifi.ws.vns.service.common.ResidualFlowService;
//import net.eoutech.vifi.ws.vns.service.common.UserService;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.MessageFormat;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by SUU on 2016/5/25.
// */
//@Service
//public class VnsAuthorGETService {
//
//	private static Map< String, Integer > debugIdts = new ConcurrentHashMap< String, Integer >();
//
//	@Autowired
//	private VaUUWiFiService uuwifiService;
//	@Autowired
//	private ConfigureService configureService;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private ResidualFlowService residualFlowService;
//	@Autowired
//	private CardStatusService cardStatusService;
//
//    public synchronized int doAuthorization( VnsMsgReqGET req, VnsMsgRespGET resp ) {
//
//    	try {
//
//		    String vid = req.getVid();
//
//		    VaViFiDevice vifiDevice = uuwifiService.queryByVID( vid );//查询设备是否存在
//		    if ( vifiDevice == null ) {
//		        myInfo( "GET:not found vifi device.vid:{0}", vid );
//		        return resp.setSipCode( SipCodeEunm.SIP_400_BAD_REQUEST );
//		    }
//		    if ( !"E".equals( vifiDevice.getDevState() ) ) {//设备状态
//		        myInfo( "GET:vifi device state disable.vid:{0},state:{1}", vid, vifiDevice.getDevState() );
//		        return resp.setSipCode( SipCodeEunm.SIP_403_FORBIDDEN );
//		    }
//
//		    debugIdts.put( "vid", vifiDevice.getDebugIdt() );
//		    // 设置设备日志配置
//		    if ( 1 == vifiDevice.getDebugIdt() ) {
//		    	String logid = req.getVid().length() > 16 ? req.getVid().substring( req.getVid().length() - 16, req.getVid().length() ) : req.getVid();//日志id由设备id生成
//		    	int logidt = 7;
//		        resp.setLogCfg( configureService.getUUWiFiLogCfg( logidt, logid ) );//返回设备配置日志
//		        myInfo( "GET:set vifi debug info,logid:{0},logidt:{1}", logid, logidt );
//		    }
//
//			TbCardStatus cardStatus = cardStatusService.selectByVifiId(req.getVid());
//
//			if (cardStatus == null){
//				myInfo( "GET:cardstatus not been exists:{0}", vid );
//				return resp.setSipCode( SipCodeEunm.SIP_403_FORBIDDEN );
//			}
//
//			if (cardStatus.getStatus().equals("1")){//下卡状态
//				myInfo( "GET:vifi device not been start:{0}", vid );
//				return resp.setSipCode( SipCodeEunm.SIP_403_FORBIDDEN );
//			}else if(cardStatus.getStatus().equals("0") || cardStatus.getStatus().equals("2") ){
//				myInfo( "GET:vifi device has been start:{0}", vid );
//			}
//
////		    String uid = vifiDevice.getUserId();//通过设备获取绑定的uid
//			String uid = cardStatus.getIdxUserId();
//			myInfo( "GET:the user is:{0}", uid);
//		    String agentId = StringUtils.isEmpty( vifiDevice.getAgentId() ) ? "eu.eout" : vifiDevice.getAgentId();//判断代理人id，没有的话就用默认的
//		    if ( StringUtils.isEmpty( uid ) ) {//判断用户id是否为空
//		        myInfo( "GET:vifi device not bind user.vid:{0}", vid );
//		        return resp.setSipCode( SipCodeEunm.SIP_404_DEVICE_NOT_BIND_USER );
//		    }
//
//		    // 根据mcc获取country code
//		    String areaId = uuwifiService.getAreaIdByMcc( req.getMcc(), req.getFip() );
//
//		    VaUser user = uuwifiService.queryUserByUserID( uid, areaId, "D", vid );
////			VaUser user = uuwifiService.queryUserByUserID( uid, vid );
//		    if ( user == null ) {
//		        myInfo( "GET:vifi device not found bind user.vid:{0},uid:{1}", vid, uid );
//		        return resp.setSipCode( SipCodeEunm.SIP_404_DEVICE_NOT_BIND_USER );
//		    } else if ( user.outMaxTodayData() ) {
//		        myInfo( "GET:user outOf today Max data.uid:{0},todayData:{1},todayMaxData:{2}", uid, user.getTodayUUWiFiData(), user.getTodayMaxData() );
//		        return resp.setSipCode( SipCodeEunm.SIP_403_USER_OUTOF_MAXDATA );
//		    } else if ( user.outMaxMonthData() ) {
//		        myInfo( "GET:user outOf this month max data.uid:{0},monthData:{1},monthMaxData:{2}", uid, user.getMonthUUWiFiData(), user.getMonthMaxData() );
//		        return resp.setSipCode( SipCodeEunm.SIP_403_USER_OUTOF_MAXDATA );
//		    }
//
//			String state = userService.selectUserState(uid);
//			if (state != null) {
//				if (state.equals("10")) {
//					myInfo("GET:user is not online.uid:{0}", uid);
//					return resp.setSipCode(SipCodeEunm.SIP_404_USER_OFFLINE);
//				} else if (state.equals("11")) {
//					myInfo("GET:user is online.uid:{0}", uid);
//				}
//			}
//
//			Double dataTraffic = 0.00;
//			List<TbResidualFlow> flowList = residualFlowService.getAllMessage(uid);
//			if (flowList.size()<=0){
//				myInfo("GET:USER_PAYMENT_NOT_ENOUGH.uid:{0}", uid);
//				return resp.setSipCode(SipCodeEunm.SIP_402_USER_PAYMENT_NOT_ENOUGH);
//			}
//			for (int i=0;i <flowList.size();i++){
//				Double flow = flowList.get(i).getResidualFlow();
//				if (flow > 0){
//					myInfo("GET:USE FLOW ISEXIST.uid:{0}", uid);
//					dataTraffic = flow;
//					break;
//				}
//				if ( i==flowList.size()-1 && flow == 0 ){
//					myInfo("GET:USER_PAYMENT_NOT_ENOUGH.uid:{0}", uid);
//					return resp.setSipCode(SipCodeEunm.SIP_402_USER_PAYMENT_NOT_ENOUGH);
//				}
//			}
//
//		    // SIMP GET
//		    if ( "SIM".equals( req.getTgt() ) ) {
//
//		        if ( StringUtils.isEmpty( areaId ) ) {
//		            myInfo( "GET:not found UUWiFiArea by mcc,not support area/country,mcc:{0}", req.getMcc() );
//		            return resp.setSipCode( SipCodeEunm.SIP_488_NOT_ACCEPTABLE_HERE );
//		        }
//
//		        if ( user.getDailyRentalID() == 0 && user.getTotalSuiteValue() <= 0 ) {
//
//		            // find rate
//		            VaRate rate = uuwifiService.getRateByAreaId( areaId, agentId );
//		            if ( rate == null ) {
//		                myInfo( "GET:not found rate.areaId:{0},agentId:{1}", areaId, agentId );
//		                return resp.setSipCode( SipCodeEunm.SIP_488_NOT_ACCEPTABLE_HERE );
//		            }
//
//		            /*if ( user.getBalance() < rate.getPrice() ) {
//		                myInfo( "GET:user payment not enough.userId:{0},userSuiteValue:{1},userBalance:{2},rateId:{3},ratePrice:{4}", user.getUid(), user.getTotalSuiteValue(), user.getBalance(), rate.getRateId(), rate.getPrice() );
//		                return resp.setSipCode( SipCodeEunm.SIP_402_USER_PAYMENT_NOT_ENOUGH );
//		            }*/
//					if ( dataTraffic <= 0 ) {
//						myInfo( "GET:user payment not enough.userId:{0},userSuiteValue:{1},userBalance:{2},rateId:{3},ratePrice:{4}", user.getUid(), user.getTotalSuiteValue(), user.getBalance(), rate.getRateId(), rate.getPrice() );
//						return resp.setSipCode( SipCodeEunm.SIP_402_USER_PAYMENT_NOT_ENOUGH );
//					}
//
//		        }
//
//		    }
//
//		    String sid = uuwifiService.getSessionId( vid, req.getTgt() );//通过设备id和设备内卡的类型获取session的id
//		    VaSession session = uuwifiService.getSessionBySID( sid );
//		    if ( uuwifiService.checkSession( session ) && req.getLfc() != null && 0 == req.getLfc() ) {  // 检查session TODO 并且判断是否需要换卡
//
//		        if ( "G".equals( session.getType() ) && !req.getIccid().equals( session.getSimCard().getIccid() ) ) {
//		            session.getSimCard().setIccid( req.getIccid() );
//		            session.getSimCard().setImsi( req.getImsi() );
//		            session.getGoIPPort().setIccid( req.getIccid() );
//		            session.getGoIPPort().setImsi( req.getImsi() );
//		            uuwifiService.updateGoIPPort( session.getGoIPPort() );
//		        }
//		        uuwifiService.redistributionSession( session );
//
//		    } else {
//
//		        // 先关闭原来的session
//		    	uuwifiService.closeSession( sid, true );
//
//		        myInfo( "GET:not found active session,get new session.vid:{0},tgt:{1},mcc:{2},fip:{3},lfc:{4}", req.getVid(), req.getTgt(), req.getMcc(), req.getFip(), req.getLfc() );
//
//		        Map< String, Object > res = uuwifiService.getNewSession( req.getVid(), req.getTgt(), areaId, req.getIccid(), req.getImsi(), req.getLfc() == null ? 0 : req.getLfc() );
//		        int errorCode = (Integer)res.get( "errorCode" );
//		        if ( errorCode < 0 ) {
//		            myInfo( "GET:no support country or area,errorCode:{0}", errorCode );
//		            return resp.setSipCode( SipCodeEunm.SIP_403_FORBIDDEN );
//		        } else if ( errorCode > 0 ) {
//		            myInfo( "GET:not found enable sim card or goip port.errorCode:{0}", errorCode );
//
//		            uuwifiService.cleanTimeoutSession();
//		            res = uuwifiService.getNewSession( req.getVid(), req.getTgt(), areaId, req.getIccid(), req.getImsi(), req.getLfc() );
//		            errorCode = (Integer)res.get( "errorCode" );
//		            if ( errorCode < 0 ) {
//		                myInfo( "GET:no support country or area,errorCode:{0}", errorCode );
//		                return resp.setSipCode( SipCodeEunm.SIP_403_FORBIDDEN );
//		            } else if ( errorCode > 0 ) {
//		                myInfo( "GET:not found any enable sim card or goip port.errorCode:{0}", errorCode );
//
//		                // TODO 查询拿卡失败的原因
//
//		                return resp.setSipCode( SipCodeEunm.SIP_486_BUSY_HERE );
//		            }
//		        }
//		        session = ( VaSession )res.get( "session" );
//		        // insert into database and update table simpport
//		        uuwifiService.distributeSession( session );
//		    }
//
//		    // 设置resp参数
//			resp.setSid( session.getId() );
//			resp.setExp( session.getExpire() );
//			resp.setVsw_ip( "180.153.59.29" );
//			resp.setVsw_port( session.getVswServer().getPort() );
//			resp.setVsw_pro( session.getVswServer().getPro() );
//			resp.setApn( session.getSimCard().getApn() );
//			resp.setNum( session.getSimCard().getDialnumber() );
//			resp.setUsr( session.getSimCard().getDialuid() );
//			resp.setPwd( session.getSimCard().getDialpwd() );
//			resp.setActions( session.getSimCard().getExActions() );
//			resp.setCmd( uuwifiService.queryDeviceCMD( vid ) );
//
//		    //切换卡状态到已连接
//			if (cardStatus.getStatus().equals("0")){
//				myInfo( "GET:card status will be change:{0}", cardStatus.getStatus() );
//				cardStatusService.updateCardStatusAndUserId(uid,req.getVid(),"2");
//			}
//			myInfo( "GET:the device will be link success:{0}", cardStatusService.selectByVifiId(req.getVid()).getStatus() );
//			//修改设备最后在线信息
//		    uuwifiService.updateDeviceLastOnlineInfo( vid, req.getFip(), areaId );
//
//		    return 0;
//
//    	} catch (Exception e) {
//			e.printStackTrace();
//			myInfo( "vns uuwifi get throw exception:" + EuStringUtil.myExceptionString( e ) );
//			return resp.setSipCode( SipCodeEunm.SIP_500_INTERNAL_SERVER_ERROR );
//		}
//    }
//
//    /**
//     * **********************vsw日志**************
//     */
//    private void myInfo( String pattern, Object... args ) {
//        LogUtils.log( LogUtils.INFO, LogUtils.INFO, buildLogParams( pattern, args ) );
//    }
//
//    private String[] buildLogParams ( String pattern, Object... args ) {
//
//        return new String[]{ MessageFormat.format( pattern, args ) };
//    }
//
//}
