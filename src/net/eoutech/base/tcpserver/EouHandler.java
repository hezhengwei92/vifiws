package net.eoutech.base.tcpserver;

import io.netty.channel.ChannelHandlerContext;
import net.eoutech.base.tcpserver.entity.MsgContent;

public interface EouHandler {
    void doMsgHandle(ChannelHandlerContext ctx, MsgContent req, MsgContent resp, byte circle);
}
