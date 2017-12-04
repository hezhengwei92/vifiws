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
import java.util.*;

/**
 * Created by WangY on 2017/6/6 0006.
 */
public class EouFourDecoder extends ByteToMessageDecoder {
    private ByteBuffer buffer = null;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final char[] cha = new char[]{'S', 'O', 'N', 'P'};
    private static final byte[] SE = new byte[]{0x53, 0x4F, 0x4E, 0x50, 0x45};
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
                    LogUtils.info("SERVER FOUR 新 buffer");
                } else {
                    LogUtils.info("SERVER FOUR 老 buffer");
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
                LogUtils.info("000000000000" + buffer);
                do {
                    buffer.flip();
                    size = buffer.limit();
                    if (size > 14) {//长度不够，无需处理
                        if (cha[0] == (char) buffer.get(count)) {//处理ByteBuf中的数据，或者ByteBuffer中有数据需要处理
                            if ((cha[1] == (char) buffer.get(count + 1) && cha[2] == (char) buffer.get(count + 2) && cha[3] == (char) buffer.get(count + 3))) {
                                for (int i = 0; i < count; i++) {
                                    buffer.get();//废掉的数据
                                }
                                FrameData frameData = getEouData(ctx, buffer);
                                if (frameData.getDoOrNot()) {//完整数据再处理
                                    ByteBuffer byteBuffer = frameData.getByteBuffer();
                                    int lengths = byteBuffer.limit();
                                    int move = 0;
                                    byte[] bytes = new byte[lengths];
                                    byte msg = byteBuffer.get(4 + count);
                                    for (int i = 0; i < lengths; i++) {
                                        temp = byteBuffer.get();
                                        if ((temp & 0xFF) == 0xFA && ((msg >> 4) & 0x0F) == 0x01) {//处理特殊字符
                                            bytes[i] = (byte) ~byteBuffer.get();
                                            lengths--;//特殊字符处理，长度需要减掉1
                                            move++;
                                        } else {
                                            bytes[i] = temp;
                                        }
                                    }
                                    LogUtils.info("buffer1" + buffer);
                                    LogUtils.info(Arrays.toString(bytes));

                                    String head = "";
                                    String end = "";
                                    for (int i = 0; i < 4; i++) {// 获取读到数据的头部
                                        head += (char) bytes[i];
                                    }

                                    for (int i = bytes.length - 4 - move; i < bytes.length - move; i++) {// 获取读到数据的尾部
                                        end += (char) bytes[i];
                                    }

                                    if ("SONP".equals(head) && "EONP".equals(end)) {// 判断数据是不是完整的
                                        int checkCRC = DataProcess.CRC_XModem(bytes, 4, bytes.length - 10 - move);// 获取校验的CRC的值
                                        // 获取CRC校验的高位值
                                        int crcHigh = DataProcess.byteToInt(bytes[bytes.length - 6 - move]);
                                        // 获取CRC校验的地位值
                                        int crcLow = DataProcess.byteToInt(bytes[bytes.length - 5 - move]);
                                        // 计算CRC的值
                                        int crc = (crcHigh << 8) + crcLow;
                                        // 验证CRC的计算值和传递值是否正确
                                        if (checkCRC == crc) {
                                            LogUtils.info("Time:({0}),Data:({1})", format.format(new Date()), Arrays.toString(bytes));
                                            arrList.add("Time:(" + format.format(new Date()) + "),Data:(" + Arrays.toString(bytes) + ")");
                                            EouData data = new EouData(bytes, move);
                                            list.add(data);
                                            LogUtils.info("buffer12:" + buffer);
                                            arrList.add("buffer13:" + buffer);
                                            LogUtils.info("buffer14:" + buffer);
                                            StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                                        } else {
                                            LogUtils.info("BUFFER ByteBuf has the wrong crc");
                                            arrList.add("BUFFER ByteBuf has the wrong crc");
                                            flag = false;
                                        }
                                    } else {
                                        LogUtils.info("Time:" + format.format(new Date()) + ",Data:" + Arrays.toString(bytes));
                                        LogUtils.info("^^^^^^^^^^^BUFFER数据错误^^^^^^^^^^^^");
                                        arrList.add("^^^^^^^^^^^BUFFER数据错误^^^^^^^^^^^^");
                                        return;
                                    }
                                } else {
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
                                } else {
                                    buffer.compact();
                                }
                            }
                        } else {//读不到S
                            count++;
                            if (count == size - 1) {
                                LogUtils.info("BUFFER Cant read the S---" + in);
                                arrList.add("BUFFER Cant read the S---" + in);
                                buffer.clear();
                                flag = false;
                                StaticMsg.getCtx2Buf().put(ctx.channel(), buffer);
                            } else {
                                buffer.compact();
                            }
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

    private static FrameData getEouData(ChannelHandlerContext ctx, ByteBuffer byteBuffer) {
        int length = 0;
        boolean flag = false;
        FrameData frameData = new FrameData();
        for (int i = 0; i < byteBuffer.limit() - 3; i++) {
            if (byteBuffer.get(i) == SE[4] && byteBuffer.get(i + 1) == SE[1] && byteBuffer.get(i + 2) == SE[2] && byteBuffer.get(i + 3) == SE[3]) {
                length = i + 4;
                flag = true;
                System.out.println("找到了" + length);
                break;
            }
        }
        if (flag) {
            ByteBuffer buffer = ByteBuffer.allocate(512);
            for (int i = 0; i < length; i++) {
                buffer.put(byteBuffer.get());
            }
            buffer.flip();
            byteBuffer.compact();
            StaticMsg.getCtx2Buf().put(ctx.channel(), byteBuffer);
            frameData.setDoOrNot(true);
            frameData.setByteBuffer(buffer);
            return frameData;
        } else {
            byteBuffer.compact();
            StaticMsg.getCtx2Buf().put(ctx.channel(), byteBuffer);
            System.out.println("没找到");
            frameData.setDoOrNot(false);
            frameData.setByteBuffer(byteBuffer);
        }
        return frameData;
    }
}

class FrameData {
    private Boolean doOrNot;
    private ByteBuffer byteBuffer;

    public Boolean getDoOrNot() {
        return doOrNot;
    }

    public void setDoOrNot(Boolean doOrNot) {
        this.doOrNot = doOrNot;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}