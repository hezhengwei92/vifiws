//package net.eoutech.vifi.ws.vnstcp.server;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import net.eoutech.utils.LogUtils;
//
//public class ESocketThread2 extends Thread
//{
//  private boolean bDone = true;
//  private ServerSocket server;
//
//  public ESocketThread2(int port)
//  {
//    try
//    {
//      LogUtils.info("server socket open on port : " + port, new Object[0]);
//      this.server = new ServerSocket(port);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  public void run()
//  {
//    try {
//      while (this.bDone) {
//        Socket socket = this.server.accept();
//
//        LogUtils.info("has a socket connected.ip:" + socket.getInetAddress().getHostAddress(), new Object[0]);
//        if ((socket != null) && (!socket.isClosed())) {
//          socket.setSoTimeout(30000);
//          new ESocketOperate(socket).start();
//        }
//      }
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
//  }
//
//  public void close() {
//    try {
//      if ((this.server != null) && (!this.server.isClosed())) {
//        this.server.close();
//        this.bDone = false;
//      }
//    }
//    catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//}