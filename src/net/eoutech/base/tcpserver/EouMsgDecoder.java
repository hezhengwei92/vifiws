package net.eoutech.base.tcpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.utils.DataProcess;
import net.eoutech.utils.LogUtils;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WangY on 2017/3/8 0008.
 */
public class EouMsgDecoder extends ByteToMessageDecoder {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);
    private static int start = 0;
    private static Map<Byte, Byte> msg2Circle = new ConcurrentHashMap<>();
    private static byte msg = 0;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        LogUtils.info("进入DECODER，START");
        if (in == null) {// 判断有没有收到数据
            System.out.println("ByteBuf is NULL");
            LogUtils.info("ByteBuf is NULL");
            LogUtils.info("バッファは、データではない");
            return;
//        } else if (in.readableBytes() < 14) {// 判断收到数据的长度符不符合要求
//            System.out.println("ByteBuf has the wrong size");
//            LogUtils.info("ByteBuf has the wrong size");
//            LogUtils.dbg("バッファは、間違ったデータ長を受信しました");
//            return;
        } else {
            byte temp;

            int wid = in.writerIndex();
            System.out.println("wid:"+wid);
            LogUtils.info("wid:"+wid);

//            if (msg2Circle.get(in.getByte(4)) == null) {
//                msg2Circle.put(in.getByte(4), in.getByte(5));
//            }else {
//                byte circle = msg2Circle.get(in.getByte(4));
//                if (circle <= in.getByte(5)){
//                    in.clear();
//                    return;
//                }
//            }


//            System.out.println(buffer);
            if (start != 0) {
                buffer.position(start);
                buffer.limit(buffer.capacity());
            }
//            System.out.println(buffer);
            LogUtils.info("in:"+in);
//            System.out.println("in:"+in);
//            System.out.println(Arrays.toString(in.array()));

            LogUtils.info("msg:"+msg);
            if (msg == 0){
                msg = in.getByte(4);
            }

            System.out.println("message body "+msg);

            for (int i = start; i < wid + start; i++) {
                temp = in.readByte();
                if ((temp & 0xFF) == 0xFA && ((msg >> 4) & 0x0F) == 0x01 && i != wid + start - 1) {//处理特殊字符
                    buffer.put((byte) ~in.readByte());
                    wid--;
                } else {
                    buffer.put(temp);
                }
            }

//            System.out.println("buffer:" + buffer);
            buffer.flip();
//            LogUtils.info("000000000000000000000000"+Arrays.toString(buffer.array()));
//            System.out.println("000000000000000000000000" + buffer);
//            LogUtils.info("000000000000000000000000" + Arrays.toString(buffer.array()));
//            LogUtils.info("-------------"+Arrays.toString(buffer.array()));

            ByteBuf buf = Unpooled.copiedBuffer(buffer);

            System.out.println("BUF:"+buf);
//            System.out.println("BUF:"+Arrays.toString(buf.array()));
            LogUtils.info("BUF:"+Arrays.toString(buffer.array())+"====wid:"+wid);

            LogUtils.info("ButeBuf has the right data");
            LogUtils.info("バッファは、正しいデータを受信しました");
            int count = 0;
            boolean flag = true;
            char[] cha = new char[]{'S', 'O', 'N', 'P'};
            int size = buf.readableBytes();
            do {
//                buffer.flip();
                if (size > 14) {
                    if (cha[0] == (char) buf.getByte(count)) {
                        if (cha[1] == (char) buf.getByte(count + 1) && cha[2] == (char) buf.getByte(count + 2) && cha[3] == (char) buf.getByte(count + 3)) {
                            if (size - count >= 8) {
                                int highLen = DataProcess.byteToInt(buf.getByte(count + 6));
                                int lowLen = DataProcess.byteToInt(buf.getByte(count + 7));
                                // 获取包组数据的总长度
                                int length = (short) ((highLen << 8) + lowLen);

                                int dataLen = length + 14;
                                if (dataLen > size) {// 数据不完整，等接下来的数据
//                                    buffer.put(in.nioBuffer());
//                                    buffer.compact();
//                                    in.clear();
                                    msg = buf.getByte(4);
                                    start = size;
//                                    buffer.clear();
                                    return;
                                }
                                buffer.clear();
                                for (int i = 0; i < dataLen; i++) {
                                    buffer.put(buf.readByte());
                                }
                                size -= dataLen;

                                buffer.flip();
                                LogUtils.info("NewBuffer:" + Arrays.toString(buffer.array()));

                                byte[] bytes = new byte[dataLen];
                                for (int i = 0; i < dataLen; i++) {
                                    bytes[i] = buffer.get();
                                }

                                LogUtils.info("-------------------"+Arrays.toString(bytes));

                                String head = "";
                                String end = "";
                                for (int i = 0; i < 4; i++) {// 获取读到数据的头部
                                    head += (char) bytes[i];
                                }

                                for (int i = bytes.length - 4; i < bytes.length; i++) {// 获取读到数据的尾部
                                    end += (char) bytes[i];
                                }

                                if ("SONP".equals(head) && "EONP".equals(end)) {// 判断数据是不是完整的
                                    int checkCRC = DataProcess.CRC_XModem(bytes, 4, bytes.length - 10);// 获取校验的CRC的值
                                    System.out.println(checkCRC);
                                    // 获取CRC校验的高位值
                                    int crcHigh = DataProcess.byteToInt(bytes[bytes.length - 6]);
                                    // 获取CRC校验的地位值
                                    int crcLow = DataProcess.byteToInt(bytes[bytes.length - 5]);
                                    // 计算CRC的值
                                    int crc = (crcHigh << 8) + crcLow;
                                    System.out.println(crc);
                                    // 验证CRC的计算值和传递值是否正确
                                    if (checkCRC == crc) {
                                        LogUtils.info("Time:({0}),Data:({1})", format.format(new Date()), Arrays.toString(bytes));
                                        EouData data = new EouData(bytes);
                                        list.add(data);
                                        buffer.clear();
                                        in.clear();
                                    } else {
                                        System.out.println("ByteBuf has the wrong crc");
                                        LogUtils.info("ByteBuf has the wrong crc");
                                        flag = false;
                                    }
                                    start = 0;
                                    msg = 0;
                                } else {
                                    in.clear();
                                    buffer.clear();
                                    msg = 0;
                                    start = 0;
                                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^");
                                    LogUtils.info("^^^^^^^^^^^^^^^^^^^^^^^");
                                    return;
                                }
                            } else {
                                System.out.println("00000000000000000000000000000000");
                                LogUtils.info("00000000000000000000000000000000");
                                /*byte[] leftData = new byte[size - count];
                                for (int i = 0; i < leftData.length; i++) {
                                    leftData[i] = buffer.get();
                                }
                                buffer.compact();
                                buffer.put(leftData);*/
                                return;
//                                buffer.put(in.nioBuffer());
                            }
                        } else {//读到S后，后三位读出不能组成SONP
                            System.out.println("---------------------------------");
                            LogUtils.info("---------------------------------");
                            count++;
                            if (count == size - 2) {
                                in.clear();
                                flag = false;
                                System.out.println("Cant read the ONP");
                                LogUtils.info("Cant read the ONP");
                            }
                        }
                    } else {//读不到S
                        /*if (count == buffer.limit() - 3) {
                            buffer.clear();
                            flag = false;
                            in.clear();
                            return;
                        }
                            count++;
                            buffer.compact();*/

                        count++;
                        if (count == size - 2) {
                            in.clear();
                            flag = false;
                            System.out.println("Cant read the S");
                            LogUtils.info("Cant read the S");
                            buffer.clear();
//                            in.clear();
                            msg = 0;
                            start = 0;
                        }
                    }
                } else if (size == 0) {
                    System.out.println("size:" + size + "+++++++++++++++++++++++++++++");
                    LogUtils.dbg("We have no date to process");
                    System.out.println("We have no date to process");
                    buffer.clear();
                    in.clear();
                    start = 0;
                    flag = false;
                } else {//数据不正确
                    System.out.println("size:" + size);
                    LogUtils.dbg("We have no enough date to process");
                    System.out.println("We have no enough date to process");
                    flag = false;
//                    buffer.compact();
                }
            } while (flag);
            System.out.println("size:" + list.size());
            LogUtils.info("size:({0})", list.size());
            /*System.out.println(Arrays.toString(buffer.array()));

            int count = 0;
            boolean flag = true;
            char[] cha = new char[]{'S', 'O', 'N', 'P'};
            do {
                buffer.flip();
                int size = buffer.limit();
                if (size > 14) {
                    if (cha[0] == (char) buffer.get(count)) {
                        if (cha[1] == (char) buffer.get(count + 1) && cha[2] == (char) buffer.get(count + 2) && cha[3] == (char) buffer.get(count + 3)) {
                            if (size - count >= 8){
                                int highLen = DataProcess.byteToInt(buffer.get(count + 6));
                                int lowLen = DataProcess.byteToInt(buffer.get(count + 7));
                                // 获取包组数据的总长度
                                int length = (short) ((highLen << 8) + lowLen);

                                int dataLen = length + 14;
                                if (dataLen > size) {// 数据不完整，等接下来的数据
                                    buffer.compact();
                                    in.clear();
                                    return;
                                }

                                byte[] bytes = new byte[dataLen];
                                for (int i = 0; i < dataLen; i++) {
                                    bytes[i] = buffer.get();
                                }

                                if (size > dataLen) {// 有无数据需要放入缓冲区
                                    byte[] leftData = new byte[size - dataLen];
                                    for (int i = 0; i < leftData.length; i++) {
                                        leftData[i] = buffer.get();
                                    }
                                    buffer.compact();
                                    buffer.put(leftData);
                                }

                                if (size == dataLen) {// 有无数据需要放入缓冲区
                                    System.out.println(buffer);
                                    buffer.compact();
                                    System.out.println(buffer);
                                }

                                String head = "";
                                String end = "";
                                for (int i = 0; i < 4; i++) {// 获取读到数据的头部
                                    head += (char) bytes[i];
                                }

                                for (int i = bytes.length - 4; i < bytes.length; i++) {// 获取读到数据的尾部
                                    end += (char) bytes[i];
                                }

                                if ("SONP".equals(head) && "EONP".equals(end)) {// 判断数据是不是完整的
                                    int checkCRC = DataProcess.CRC_XModem(bytes, 4, bytes.length - 10);// 获取校验的CRC的值
                                    System.out.println(checkCRC);
                                    // 获取CRC校验的高位值
                                    int crcHigh = DataProcess.byteToInt(bytes[bytes.length - 6]);
                                    // 获取CRC校验的地位值
                                    int crcLow = DataProcess.byteToInt(bytes[bytes.length - 5]);
                                    // 计算CRC的值
                                    int crc = (crcHigh << 8) + crcLow;
                                    System.out.println(crc);
                                    // 验证CRC的计算值和传递值是否正确
                                    if (checkCRC == crc) {
                                        LogUtils.info("Time:({0}),Data:({1})", format.format(new Date()), Arrays.toString(bytes));
                                        EouData data = new EouData(bytes);
                                        list.add(data);
                                        in.clear();
                                    } else {
                                        System.out.println("ByteBuf has the wrong crc");
                                        LogUtils.info("ByteBuf has the wrong crc");
                                        flag = false;
                                    }
                                }
                            }else {
                                byte[] leftData = new byte[size - count];
                                for (int i = 0; i < leftData.length; i++) {
                                    leftData[i] = buffer.get();
                                }
                                buffer.compact();
                                buffer.put(leftData);
                            }

                        } else {//读到S后，后三位读出不能组成SONP
                            count++;
                        }
                    } else {//读不到S
                        if (count == buffer.limit() - 3) {
                            buffer.clear();
                            flag = false;
                        }
                        count++;
                    }
                } else {//数据不正确
                    LogUtils.dbg("We have no date to process");
                    flag = false;
                    buffer.compact();
                }
            } while (flag);
            System.out.println(list.size());*/
        }
    }
}
