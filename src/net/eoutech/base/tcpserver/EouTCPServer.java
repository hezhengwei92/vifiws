package net.eoutech.base.tcpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import net.eoutech.utils.LogUtils;

public class EouTCPServer {
    private int port;
    private EouHandler eouHandler;

    public EouTCPServer(int port, EouHandler eouHandler) {
        this.port = port;
        this.eouHandler = eouHandler;
    }

    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(2, new DefaultThreadFactory("serverBoss", true));
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(4, new DefaultThreadFactory("serverWorker", true));

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup);
            sb.channel(NioServerSocketChannel.class);
            (sb.childHandler(new EouTSChannelInitializer(this.eouHandler)).option(ChannelOption.SO_BACKLOG, Integer.valueOf(128))).childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));
            ChannelFuture f = sb.bind(this.port).sync();
            EouLogger.dbg(new Object[]{"EouTcp Server start listen at tcp port:" + this.port});
            LogUtils.info("EouTcp Server start listen at tcp port:{0}", this.port);
            f.channel().closeFuture().sync();
        } catch (InterruptedException var8) {
            var8.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            LogUtils.info("优雅的释放服务端");
        }

    }
}
