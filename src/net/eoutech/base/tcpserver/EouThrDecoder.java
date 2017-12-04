package net.eoutech.base.tcpserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.utils.StaticMsg;
import net.eoutech.utils.DataProcess;
import net.eoutech.utils.LogUtils;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by WangY on 2017/6/6 0006.
 */
public class EouThrDecoder extends ByteToMessageDecoder {
    private ByteBuffer buffer = null;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final char[] cha = new char[]{'S', 'O', 'N', 'P'};
    private List<String> arrList = null;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        LogUtils.info("进入SERVER DECODER，START" + " 所用通道" + ctx.channel());
        if (StaticMsg.getCtx2Log().get(ctx.channel()) == null) {
            arrList = new ArrayList<>();
        } else {
            arrList = StaticMsg.getCtx2Log().get(ctx.channel());
        }
        arrList.add("进入SERVER DECODER，START" + " 所用通道" + ctx.channel());
        try {
            if (in == null) {
                System.out.println("ByteBuf is NULL");
                LogUtils.info("ByteBuf is NULL");
                LogUtils.info("バッファは、データではない");
                return;
            } else {
                byte[] arr = new byte[in.readableBytes()];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = in.getByte(i);
                }
                LogUtils.info("数据：" + Arrays.toString(arr));
                LogUtils.info("ButeBuf has the right data" + in);
                arrList.add("ButeBuf has the right data" + in);
                if (StaticMsg.getCtx2Buf().get(ctx.channel()) == null) {
                    buffer = ByteBuffer.allocate(2048);
                    LogUtils.info("SERVER THR 新 buffer");
                } else {
                    LogUtils.info("SERVER THR 老 buffer");
                    buffer = StaticMsg.getCtx2Buf().get(ctx.channel());
                    if (buffer.limit() == 0) {
                        buffer.limit(buffer.capacity());
                    }
                    if (buffer.position() == buffer.limit()) {
                        buffer.clear();
                    }
                }
                arrList.add("buffer======" + buffer);
                LogUtils.info("buffer======" + buffer);
                byte temp;
                int count = 0;
                boolean flag = true;

                int size = in.readableBytes();
                boolean passport = false;
                Integer position = buffer.position();
                if (position > 0) {
                    passport = true;
                }
                LogUtils.info("size=" + size + " -----passport:" + passport);
                arrList.add("size=" + size + " -----passport:" + passport);
                for (int i = 0; i < size; i++) {//把所有数据读到buffer中
                    buffer.put(in.readByte());
                }
                size = buffer.position();
                LogUtils.info("000000000000" + buffer);
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                do {
                    buffer.flip();
                    int lengths = size - count;
                    if (size > 14) {//长度不够，无需处理
                        if (cha[0] == (char) buffer.get(count)) {//处理ByteBuf中的数据，或者ByteBuffer中有数据需要处理
                            if ((cha[1] == (char) buffer.get(count + 1) && cha[2] == (char) buffer.get(count + 2) && cha[3] == (char) buffer.get(count + 3))) {
                                for (int i = 0;i<count;i++){
                                    buffer.get();
                                }
                                byte msg = buffer.get(4 + count);
                                if (buffer.limit() != buffer.capacity()) {
                                    for (int i = 0; i < lengths; i++) {//把ByteBuf中的数据放入ByteBuffer中
                                        temp = buffer.get();
                                        if ((temp & 0xFF) == 0xFA && ((msg >> 4) & 0x0F) == 0x01 && buffer.position() < buffer.limit()) {//处理特殊字符
                                            byteBuffer.put((byte) ~buffer.get());
                                            lengths--;//特殊字符处理，长度需要减掉1
                                        } else {
                                            byteBuffer.put(temp);
                                        }
                                    }
                                    buffer.clear();
                                }
                                LogUtils.info("buffer7:" + buffer);
                                LogUtils.info("buffer7:" + byteBuffer);
                                byteBuffer.flip();
                                if (lengths >= 8) {
                                    int highLen = DataProcess.byteToInt(byteBuffer.get(6));
                                    int lowLen = DataProcess.byteToInt(byteBuffer.get(7));
                                    // 获取包组数据的总长度
                                    int length = (short) ((highLen << 8) + lowLen);

                                    int dataLen = length + 14;

                                    boolean dataFlag = true;
                                    LogUtils.info("buffer8:" + byteBuffer);// + Arrays.toString(buffer.array());
                                    arrList.add("buffer8:" + byteBuffer);
                                    if (dataLen > lengths) {// 数据不完整，等接下来的数据
                                        buffer.put(byteBuffer);
                                        LogUtils.info("buffer9:" + buffer);
                                        arrList.add("buffer9:" + buffer);
                                        StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                                        dataFlag = false;
                                        flag = false;
                                    }
                                    LogUtils.info("buffer10:" + buffer);
                                    arrList.add("buffer10:" + buffer);
                                    if (dataFlag) {
                                        byte[] bytes = new byte[dataLen];

                                        Integer readIndex = byteBuffer.position();
                                        Integer writeIndex = byteBuffer.limit();
                                        LogUtils.info("read:{0} and write:{1}", readIndex, in.writerIndex());

                                        for (int i = 0; i < dataLen; i++) {//把符合长度的数据放到数组中
                                            bytes[i] = byteBuffer.get();
                                        }

                                        LogUtils.info("buffer11:" + buffer);
                                        LogUtils.info("Arrays:" + Arrays.toString(bytes));
                                        arrList.add("buffer11:" + buffer + "-----Arrays:" + Arrays.toString(bytes));
                                        if (dataLen < writeIndex) {//数据处理完后还有多余的数据要处理
                                            byteBuffer.compact();
                                            LogUtils.info("buffer12:" + byteBuffer);
                                            arrList.add("buffer12:" + byteBuffer);
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
                                            // 获取CRC校验的高位值
                                            int crcHigh = DataProcess.byteToInt(bytes[bytes.length - 6]);
                                            // 获取CRC校验的地位值
                                            int crcLow = DataProcess.byteToInt(bytes[bytes.length - 5]);
                                            // 计算CRC的值
                                            int crc = (crcHigh << 8) + crcLow;
                                            // 验证CRC的计算值和传递值是否正确
                                            if (checkCRC == crc) {
                                                LogUtils.info("Time:({0}),Data:({1})", format.format(new Date()), Arrays.toString(bytes));
                                                arrList.add("Time:(" + format.format(new Date()) + "),Data:(" + Arrays.toString(bytes) + ")");
                                                EouData data = new EouData(bytes);
                                                list.add(data);
                                                LogUtils.info("buffer13:" + buffer);
                                                arrList.add("buffer13:" + buffer);
                                                byteBuffer.flip();
                                                buffer.put(byteBuffer);
                                                LogUtils.info("buffer14:" + buffer);
                                                if (dataLen == writeIndex) {
                                                    size = 0;
                                                    flag = false;
                                                    buffer.clear();
                                                } else {
                                                    size = byteBuffer.limit();
                                                }
                                                byteBuffer.clear();
                                                StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                                            } else {
                                                LogUtils.info("BUFFER ByteBuf has the wrong crc");
                                                arrList.add("BUFFER ByteBuf has the wrong crc");
                                                flag = false;
                                            }
                                        } else {
                                            LogUtils.info("Time:" + format.format(new Date()) + ",Data:" + Arrays.toString(bytes));
                                            in.clear();
                                            buffer.clear();
                                            LogUtils.info("^^^^^^^^^^^BUFFER数据错误^^^^^^^^^^^^");
                                            arrList.add("^^^^^^^^^^^BUFFER数据错误^^^^^^^^^^^^");
                                            StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                                            return;
                                        }
                                    }
                                } else {
                                    buffer.position(buffer.limit());
                                    buffer.limit(buffer.capacity());
                                    LogUtils.info("buffer14:" + buffer + "-----Length is not enough");
                                    arrList.add("buffer14:" + buffer + "-----Length is not enough");
                                    StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                                    flag = false;
                                }
                            } else {//读到S后，后三位读出不能组成SONP
                                LogUtils.info("---------------BUFFER循环读S------------------");
                                arrList.add("---------------BUFFER循环读S------------------");
                                count++;
                                if (count == size - 2) {
                                    LogUtils.info("BUFFER Cant read the ONP---" + in);
                                    arrList.add("BUFFER Cant read the ONP---" + in);
                                    buffer.clear();
                                    flag = false;
                                    StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                                }
                                buffer.compact();
                            }
                        } else {//读不到S
                            count++;
                            if (count == size - 1) {
                                LogUtils.info("BUFFER Cant read the S---" + in);
                                arrList.add("BUFFER Cant read the S---" + in);
                                buffer.clear();
                                flag = false;
                                StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                            }
                            buffer.compact();
                        }
                    } else if (size == 0) {
                        LogUtils.dbg("BUFFER We have no date to process--数据接收解析成功");
                        arrList.add("BUFFER We have no date to process--数据接收解析成功");
                        flag = false;
                    } else {//数据不正确
                        LogUtils.dbg("BUFFER We have no enough date to process--" + buffer);
                        arrList.add("BUFFER We have no enough date to process--" + buffer);
                        buffer.compact();
                        StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                        flag = false;
//                        }
                    }
                } while (flag);
                LogUtils.info("size:({0})", list.size());
                arrList.add("size:" + list.size());
                try {
//                    FlumeRpcClientUtils.appendBatch(arrList);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.info("FlumeRpcClientUtils异常" + e.getMessage());
                }
                arrList.clear();//清空传递出去的内容
                StaticMsg.getCtx2Log().put(ctx.channel(), arrList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.info("EouSecDecoder异常：" + e.getMessage());
        }
    }
}
