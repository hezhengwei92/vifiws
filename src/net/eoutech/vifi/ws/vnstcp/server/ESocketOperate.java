//package net.eoutech.vifi.ws.vnstcp.server;
//
//import com.alibaba.fastjson.JSON;
//import net.eoutech.annotation.MyAnno;
//import net.eoutech.utils.LogUtils;
//import net.eoutech.vifi.ws.msg.common.SipCodeEunm;
//import net.eoutech.vifi.ws.msg.req.VnsMsgReqGET;
//import net.eoutech.vifi.ws.msg.resp.VnsMsgRespGET;
//import net.eoutech.vifi.ws.vns.service.uuwifi.VnsAuthorGETService;
//import net.eoutech.vifi.ws.vnstcp.vnso.EuPacket;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.net.Socket;
//import java.nio.ByteBuffer;
//
//public class ESocketOperate extends Thread
//{
//  private ApplicationContext ac;
//  private static VnsAuthorGETService service;
//  private Socket socket;
//
//  public ESocketOperate(Socket socket)
//  {
//    this.socket = socket;
//
//    this.ac = new FileSystemXmlApplicationContext("classpath*:applicationContext.xml");
//    if (service == null) {
//      service = (VnsAuthorGETService)this.ac.getBean(VnsAuthorGETService.class);
//    }
//  }
//
//  public void run()
//  {
//    try
//    {
//      BufferedInputStream bf = new BufferedInputStream(this.socket.getInputStream());
//      BufferedOutputStream om = new BufferedOutputStream(this.socket.getOutputStream());
//      String fip = this.socket.getInetAddress().getHostAddress();
//
//      ByteBuffer buffer = ByteBuffer.allocate(10240);
//      buffer.clear();
//      byte[] tmp = new byte[1024];
//      int len = 0;
//
//      while ((len = bf.read(tmp)) != -1)
//      {
//        buffer.put(tmp, buffer.position(), buffer.position() + len);
//      }
//
//      buffer.flip();
//      EuPacket reqPkt = new EuPacket();
//      int sz = reqPkt.doParse(buffer);
//
//      EuPacket respPkt = new EuPacket();
//      respPkt.setHProtocol(reqPkt.getHProtocol());
//      respPkt.setHFlag(reqPkt.getHFlag());
//      respPkt.setHDirection(1);
//
//      if (sz > 0)
//      {
//        if (reqPkt.getHMsgtype() == 1)
//        {
//          VnsMsgReqGET req = (VnsMsgReqGET)makeReqMsg(reqPkt, VnsMsgReqGET.class);
//          LogUtils.info("RecvMsg(GET):" + JSON.toJSONString(req), new Object[0]);
//          int doRes;
//          VnsMsgRespGET resp;
//          if (req == null) {
//            LogUtils.info("req msg GET null.bad request", new Object[0]);
//            resp = new VnsMsgRespGET();
//            doRes = resp.setSipCode(SipCodeEunm.SIP_400_BAD_REQUEST);
//          } else {
//            req.setFip(fip);
//            resp = new VnsMsgRespGET(req);
////            doRes = service.doAuthorization(req, resp);
//          }
////          LogUtils.info("RespMsg(GET):{0}|doRes:{1}", new Object[] { resp.toJSONString().toString(), Integer.valueOf(doRes) });
//
//          respPkt.setHMsgtype(getHMsgType(resp.getSc().intValue()));
//          setRespTlvDatas(respPkt, resp);
//        }
//        else {
//          respPkt.setHMsgtype(44);
//        }
//
//      }
//
//      ByteBuffer respBuffer = respPkt.doPackage();
//      byte[] respByte = new byte[respBuffer.limit()];
//      respBuffer.get(respByte, 0, respByte.length);
//      LogUtils.info("socket server write byte size:{0}", new Object[] { Integer.valueOf(respByte.length) });
//      om.write(respByte);
//      om.flush();
//      this.socket.shutdownOutput();
//
//      om.close();
//      bf.close();
//
//      buffer.clear();
//      this.socket.close();
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
//  }
//
//  private <T> T makeReqMsg(EuPacket pkt, Class<T> c)
//  {
//    if (pkt == null) {
//      return null;
//    }
//    try
//    {
//      T t = c.newInstance();
//
//      Field[] fields = c.getDeclaredFields();
//      for (int i = 0; i < fields.length; i++) {
//        Field field = fields[i];
//        field.setAccessible(true);
//
//        MyAnno anno = (MyAnno)field.getAnnotation(MyAnno.class);
//        if (anno != null)
//        {
//          String type = field.getType().toString();
//          if (type.endsWith("String"))
//            field.set(t, pkt.getTLVStr(anno.tag()));
//          else if (type.endsWith("Integer")) {
//            field.set(t, Integer.valueOf(pkt.getTLVInt(anno.tag())));
//          }
//
//        }
//
//      }
//
//      return t;
//    } catch (Exception e) {
//      e.printStackTrace();
//    }return null;
//  }
//
//  private void setRespTlvDatas(EuPacket pkt, Object obj)
//    throws Exception
//  {
//    Class c = obj.getClass();
//    Field[] fields = c.getDeclaredFields();
//    for (Field f : fields) {
//      f.setAccessible(true);
//
//      MyAnno anno = (MyAnno)f.getAnnotation(MyAnno.class);
//      if (anno != null)
//      {
//        String methodName = getMethodName(f.getName());
//        Method method = c.getMethod(methodName, null);
//        Object value = method.invoke(obj, new Object[0]);
//
//        String type = f.getType().toString();
//        if (type.endsWith("String"))
//          pkt.putTLVStr(anno.tag(), value == null ? "" : String.valueOf(value));
//        else if ((type.endsWith("Integer")) || (type.endsWith("int")))
//          pkt.putTLVInt(anno.tag(), value == null ? -1 : ((Integer)value).intValue());
//      }
//    }
//  }
//
//  private String getMethodName(String fieldName)
//  {
//    return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//  }
//
//  private int getHMsgType(int sc) {
//    int code = 43;
//    if (sc == 400)
//      code = 47;
//    else if (sc == 403)
//      code = 43;
//    else if (sc == 500)
//      code = 50;
//    else if (sc == 0) {
//      code = 0;
//    }
//    return code;
//  }
//}