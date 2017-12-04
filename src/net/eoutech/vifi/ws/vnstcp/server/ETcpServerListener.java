package net.eoutech.vifi.ws.vnstcp.server;

import net.eoutech.base.tcpserver.EouTCPServer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ETcpServerListener
  implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent e)
  {
  }

  public void contextInitialized(ServletContextEvent e)
  {
    ServletContext servletContext = e.getServletContext();
    int port = Integer.parseInt(servletContext.getInitParameter("socketPort"));

    ESocketThreadNew socket = new ESocketThreadNew();//ESocketThread socket = new ESocketThread();
    final EouTCPServer server = new EouTCPServer(port, socket);

    new Thread(new Runnable()
    {
      public void run()
      {
        server.run();
      }
    }).start();
  }
}