package net.eoutech.base.tcpserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.utils.DataProcess;
import net.eoutech.utils.LogUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by WangY on 2017/3/8 0008.
 */
public class EouEncryptionEncoder extends MessageToByteEncoder<EouData> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, EouData eouData, ByteBuf byteBuf) throws Exception {
        LogUtils.info("-------------------SERVER ENCODER---------------------");
//        FlumeRpcClientUtils.append("-------------------SERVER ENCODER---------------------");
        try {
            byte[] keyWords = DataProcess.shortToBytes(eouData.getCrc());
            byte key = keyWords[0];
            LogUtils.info("加密的值:" + key);
            //定义buffer的缓存空间大小
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            //存放帧头
            buffer.put(EouData.getHead().getBytes());
            //存放通信主体
            buffer.put(eouData.getMessage(eouData.getSender(), eouData.getReceiver()));
            byte[] encryption = new byte[eouData.getLength() + 3];
            encryption[0] = (byte) (eouData.getCircle() ^ key);
            byte[] length = DataProcess.shortToBytes(eouData.getLength());
            encryption[1] = (byte) (length[1] ^ key);
            encryption[2] = (byte) (length[0] ^ key);
            //存放包个数
            byte pkgNum = eouData.getPkgNum();
            encryption[3] = (byte) (pkgNum ^ key);
            byte[] content = eouData.getMsgList().get(0).createByte();
            //存放单包长度+存放动作标识+存放内容，并且加密
            for (int j = 0; j < content.length; j++) {
                encryption[4 + j] = (byte) (content[j] ^ key);
            }
            LogUtils.info("加密后的数组：" + Arrays.toString(encryption));
            buffer.put(encryption);
            //存放CRC的计算值
            buffer.putShort(eouData.getCrc());
            //存放帧尾
            buffer.put(EouData.getEnd().getBytes());

            buffer.flip();
            byteBuf.writeBytes(buffer);
            String print = "";
            for (int i = 0; i < buffer.limit(); i++) {
                print = print + DataProcess.IntToHex(DataProcess.byteToInt(buffer.get(i))) + " ";
            }
            LogUtils.info("加密后的字符串：" + print);
            LogUtils.info("SERVER Encode response message OK, length is " + buffer.limit());
//            FlumeRpcClientUtils.append("SERVER Encode response message OK, length is " + buffer.limit());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.info("SERVER Encode message Exception:" + e.getMessage());
//            FlumeRpcClientUtils.append("SERVER Encode message Exception:" + e.getMessage());
        }
    }
}
