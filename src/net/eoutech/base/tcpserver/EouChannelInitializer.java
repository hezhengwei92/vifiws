package net.eoutech.base.tcpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.utils.LogUtils;

public class EouChannelInitializer extends ChannelInitializer<SocketChannel> {

//    private EouData data;
//
//    private String vid;
//
//    public EouChannelInitializer(String vid, EouData data) {
//        this.data = data;
//        this.vid = vid;
//        LogUtils.info("初始化构造器" + vid + "--------" + data.toString());
//    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
//		ch.pipeline().addLast(new EouMsgDecoder());
//		ch.pipeline().addLast(new IdleStateHandler(120,120,110));
        ch.pipeline().addLast(new EouThrDecoder());
        ch.pipeline().addLast(new EouClientEncoder());
        ch.pipeline().addLast(new ClientHandler());
    }

}
