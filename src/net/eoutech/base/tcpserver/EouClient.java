package net.eoutech.base.tcpserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.EuFileUtil;
import net.eoutech.utils.LogUtils;
import net.eoutech.utils.RedisClusterClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class EouClient {
    //    private static Bootstrap bootstrap;
//    private static EventLoopGroup worker;
//
//    static {
//        try {
//            // 服务类
//            bootstrap = new Bootstrap();
//            LogUtils.info("服务类设置完毕");
//
//            // worker
//            worker = new NioEventLoopGroup(4, new DefaultThreadFactory("client", true));
//            LogUtils.info("工人设置完毕");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtils.info("EouClient static Exception " + e.getCause().getMessage());
//        }
//    }
    private static EuFileUtil fileUtil = EuFileUtil.getInstance();

    public static Bootstrap bootstrap = getBootstrap();

    /**
     * 初始化Bootstrap
     */
    public static final Bootstrap getBootstrap() {
        EventLoopGroup worker = new NioEventLoopGroup(4, new DefaultThreadFactory("client", true));
        // 服务类
        Bootstrap bootstrap = new Bootstrap();
        // 设置线程池
        bootstrap.group(worker);
        // 设置socket工厂
        bootstrap.channel(NioSocketChannel.class);

        // 设置管道
        bootstrap.handler(new EouChannelInitializer());
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        return bootstrap;
    }

    public static Channel run(String vid) {
        String server = StaticMsg.getVid2IpPort().get(vid);
        if (server == null || "".equals(server)) {
            LogUtils.info("缓存中没有获取到目标服务器地址和端口");
            String oServer = (String) RedisClusterClient.GetValue(vid);
            if (oServer != null) {
                server = oServer;
            } else {
                LogUtils.info("Redis中不存在目标服务器");
                server = fileUtil.getDispositionValue("server.aim.ip.port");
            }
        }
        LogUtils.info("server:" + server);
        String serverIp = server.substring(0, server.indexOf(":"));
        Integer serverPort = Integer.parseInt(server.substring(server.indexOf(":") + 1, server.length()));
        LogUtils.info("serverIp:" + serverIp + "---serverPort:" + serverPort);
        Channel channel = null;
        try {

            ChannelFuture connect = bootstrap.connect(serverIp, serverPort).sync();

            if (connect.isSuccess()) {
                LogUtils.info("成功连上了AAA服务");
                channel = connect.channel();
                StaticMsg.getVid2Virtual().put(vid, connect.channel());
                StaticMsg.getVirtual2Vid().put(connect.channel(), vid);
            }
//            connect.addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                    //信息完全传递成功之后才会退出虚拟客户端
//                    if (channelFuture.isDone() && channelFuture.isSuccess()) {
//                        LogUtils.info("数据传递成功Client");
//                    }
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.info("连接" + serverIp + ":" + serverPort + "失败");
            LogUtils.info("EouClient run Exception " + e.getMessage());
//        } finally {
//            worker.shutdownGracefully();
//            LogUtils.info("优雅的释放客户端");
        }
        return channel;
    }
   /* public static void run(String vid, EouData data) {
        try {
            // 服务类
            Bootstrap bootstrap = new Bootstrap();
            LogUtils.info("服务类设置完毕");

            // worker
            EventLoopGroup worker = new NioEventLoopGroup(4, new DefaultThreadFactory("client", true));
            LogUtils.info("工人设置完毕");
            LogUtils.info("===========" + (StaticMsg.getVid2IpPort().get(vid) == null));
            String server = StaticMsg.getVid2IpPort().get(vid);
            if (server == null || "".equals(server)) {
                LogUtils.info("缓存中没有获取到目标服务器地址和端口");
                String oServer = (String) RedisClusterClient.get(vid);
                if (oServer != null) {
                    server = oServer;
                } else {
                    LogUtils.info("Redis中不存在目标服务器");
                    server = fileUtil.getDispositionValue("server.aim.ip.port");
                }
            }
            LogUtils.info("server:" + server);
            String serverIp = server.substring(0, server.indexOf(":"));
            Integer serverPort = Integer.parseInt(server.substring(server.indexOf(":") + 1, server.length()));
            LogUtils.info("serverIp:" + serverIp + "---serverPort:" + serverPort);

            // 设置线程池
            bootstrap.group(worker);
            LogUtils.info("线程池设置完毕");

            // 设置socket工厂
            bootstrap.channel(NioSocketChannel.class);
            LogUtils.info("工厂设置完毕");

            // 设置管道
            bootstrap.handler(new EouChannelInitializer(vid, data));
            LogUtils.info("管道设置完毕");

            ChannelFuture connect = bootstrap.connect(serverIp, serverPort).sync();
            if (connect.isSuccess()) {
                LogUtils.info("成功连上了AAA服务器");
            }

            connect.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    //信息完全传递成功之后才会退出虚拟客户端
                    if (channelFuture.isDone() && channelFuture.isSuccess()) {
                        LogUtils.info("数据传递成功");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.info("EouClient run Exception " + e.getCause().getMessage());
//        }finally {
//            worker.shutdownGracefully();
//            LogUtils.info("优雅的释放客户端");
        }
    }*/
}
