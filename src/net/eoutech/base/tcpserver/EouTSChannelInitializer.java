package net.eoutech.base.tcpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class EouTSChannelInitializer extends ChannelInitializer<SocketChannel> {

    private EouHandler eouHandler;

    public EouTSChannelInitializer(EouHandler eouHandler) {
        this.eouHandler = eouHandler;
    }

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new EouFourDecoder());
        pipeline.addLast("encoder", new EouMsgEncoder());
        pipeline.addLast("handler", new EouMsgServerHandler(this.eouHandler));
    }
}
