package net.eoutech.base.tcpserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.utils.LogUtils;

import java.nio.ByteBuffer;

/**
 * Created by WangY on 2017/3/8 0008.
 */
public class EouClientEncoder extends MessageToByteEncoder<EouData> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, EouData eouData, ByteBuf byteBuf) throws Exception {
        LogUtils.info("-------------------CLIENT ENCODER---------------------");
        try {
            //定义buffer的缓存空间大小
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            //存放帧头
            buffer.put(EouData.getHead().getBytes());
            //存放通信主体
            buffer.put(eouData.getMessage(eouData.getSender(),eouData.getReceiver()));
            //存放循环码
            buffer.put(eouData.getCircle());
            //存放数据长度
            buffer.putShort(eouData.getLength());
            //存放包个数
            byte pkgNum = eouData.getPkgNum();
            buffer.put(pkgNum);
            for (int i = 0; i < pkgNum;i++) {
                //存放单包长度+存放动作标识+存放内容
                buffer.put(eouData.getMsgList().get(i).createByte());
            }
            //存放CRC的计算值
            buffer.putShort(eouData.getCrc());
            //存放帧尾
            buffer.put(EouData.getEnd().getBytes());

            buffer.flip();
            byteBuf.writeBytes(buffer);
            LogUtils.info("CLIENT Encode response message OK, length is " + buffer.limit());
        }catch (Exception e){
            LogUtils.info("CLIENT Encode message Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
