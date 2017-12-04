package net.eoutech.base.tcpserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.LogUtils;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
//    private EouData data;
//
//    private String vid;
//
//    public ClientHandler(String vid, EouData data) {
//        this.data = data;
//        this.vid = vid;
//        LogUtils.info("Client构造器" + vid + "--------" + data.toString());
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String e)
            throws Exception {
        LogUtils.info("channelRead0");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtils.info("CLIENT CHANNEL ACTIVE");
//        LogUtils.info("CLIENT CHANNEL ACTIVE {0}", this.data.toString());
        //存储设备号和虚拟通道的映射
//        StaticMsg.getVid2Virtual().put(vid, ctx.channel());
//        StaticMsg.getVirtual2Vid().put(ctx.channel(), vid);
//        System.out.println(ctx.channel());
//        ctx.writeAndFlush(this.data);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtils.info("CLIENT OUT LINK THE AAA Server,CLIENT IS {0}", ctx.channel());
//        FlumeRpcClientUtils.append("CLIENT OUT LINK THE AAA Server,CLIENT IS " + ctx.channel());
        if (StaticMsg.getVirtual2Vid().get(ctx.channel()) != null) {
            String vid = StaticMsg.getVirtual2Vid().get(ctx.channel());
            StaticMsg.getVid2Virtual().remove(vid);
            StaticMsg.getVirtual2Vid().remove(ctx.channel());
        }
        ctx.channel().close();
//		StaticMsg.getVirtual2Pipe().remove(ctx.channel());
    }

//	@Override
//	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
////        super.userEventTriggered(ctx, evt);
//
//		if (evt instanceof IdleStateEvent) {
//
//			IdleStateEvent event = (IdleStateEvent) evt;
//
//			if (event.state().equals(IdleState.READER_IDLE)) {
//				//未进行读操作
//				System.out.println("CLIENT_READER_IDLE");
//				LogUtils.info("CLIENT_READER_IDLE");
//				// 超时关闭channel
//				ctx.close();
//
//			} else if (event.state().equals(IdleState.WRITER_IDLE)) {
//				//未进行写操作
//				System.out.println("CLIENT_WRITER_IDLE");
//				LogUtils.info("CLIENT_WRITER_IDLE");
//				// 超时关闭channel
//				ctx.close();
//
//			} else if (event.state().equals(IdleState.ALL_IDLE)) {
//				System.out.println("CLIENT_ALL_IDLE");
//				LogUtils.info("CLIENT_ALL_IDLE");
//				ctx.writeAndFlush(OrderUtil.WakeData((byte)0,new byte[]{0x00}));
//			}
//		}
//	}

}
