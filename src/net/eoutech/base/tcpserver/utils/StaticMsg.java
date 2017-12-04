package net.eoutech.base.tcpserver.utils;

import io.netty.channel.Channel;
import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.entity.EouFlashMsg;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WangY on 2017/3/20 0020.
 */
public class StaticMsg {
    //存储设备连接通道和设备号的映射
    private static Map<Channel, String> pipe2Vid = new ConcurrentHashMap<>();

    //存储设备号和设备连接通道的映射
    private static Map<String, Channel> vid2Pipe = new ConcurrentHashMap<>();

    //存储设备号和WS->AAA虚拟通道的映射
    private static Map<String, Channel> vid2Virtual = new ConcurrentHashMap<>();

    //存储WS->AAA虚拟通道和设备号的映射
    private static Map<Channel, String> virtual2Vid = new ConcurrentHashMap<>();

    //存储设备号和分配的卡槽的编号
    private static Map<String, Integer> vid2Slot = new ConcurrentHashMap<>();

    //存储设备号和目标服务的ip+port
    private static Map<String, String> vid2IpPort = new ConcurrentHashMap<>();

    //存储上网设备的MAC地址和连接到的安逸宝设备的设备号的对应
//    private static Map<String, String> mac2Vid = new ConcurrentHashMap<>();

    //存储通道和命令数据
    private static Map<Channel, List<EouFlashMsg>> ctx2List = new ConcurrentHashMap<>();

    //存储通道和通道是否处于可用状态，0：可以发送命令 1：设备正在检查是否更新 2：设备需要更新 3：设备正在上卡 4：已经可以上网了
    private static Map<Channel, Integer> ctx2Status = new ConcurrentHashMap<>();

    //存储通道和buffer的映射
    private static Map<Channel, ByteBuffer> ctx2Buf = new ConcurrentHashMap<>();

    //存储设备号和定时器名称的映射
    private static Map<String, String> vid2Job = new ConcurrentHashMap<>();

    //存储通道号和列表的映射，用来存日志使用
    private static Map<Channel, List<String>> ctx2Log = new ConcurrentHashMap<>();

    //存储设备号+循环码和订单号的映射
    private static Map<String, String> vid2Order = new ConcurrentHashMap<>();

    //存储设备号+循环码和数据帧的映射
    private static Map<String, EouData> vid2Data = new ConcurrentHashMap<>();

    //存储订单号和定时任务的映射
    private static Map<String, Timer> order2Timer = new ConcurrentHashMap<>();

    //存储设备号+循环码和执行次数的映射
    private static Map<String, Integer> vid2Times = new ConcurrentHashMap<>();

    //存储通道和数据的映射
    private static Map<Channel, EouData> ctx2Pluse = new ConcurrentHashMap<>();

    //循环换起始值
    private static int circle = -1;

    public static Map<Channel, String> getPipe2Vid() {
        return pipe2Vid;
    }

    public static Map<String, Channel> getVid2Pipe() {
        return vid2Pipe;
    }

    public static Map<String, Integer> getVid2Slot() {
        return vid2Slot;
    }

    public static Map<String, Channel> getVid2Virtual() {
        return vid2Virtual;
    }

    public static Map<Channel, String> getVirtual2Vid() {
        return virtual2Vid;
    }

    public static Map<String, String> getVid2IpPort() {
        return vid2IpPort;
    }

    public static Map<Channel, List<EouFlashMsg>> getCtx2List() {
        return ctx2List;
    }

    public static Map<Channel, Integer> getCtx2Status() {
        return ctx2Status;
    }

    public static Map<Channel, ByteBuffer> getCtx2Buf() {
        return ctx2Buf;
    }

    public static Map<String, String> getVid2Job() {
        return vid2Job;
    }

    public static Map<Channel, List<String>> getCtx2Log() {
        return ctx2Log;
    }

    public static Map<String, String> getVid2Order() {
        return vid2Order;
    }

    public static Map<String, EouData> getVid2Data() {
        return vid2Data;
    }

    public static Map<String, Timer> getOrder2Timer() {
        return order2Timer;
    }

    public static Map<String, Integer> getVid2Times() {
        return vid2Times;
    }

    public static Map<Channel, EouData> getCtx2Pluse() {
        return ctx2Pluse;
    }

    public static synchronized byte getCircleCode() {
        circle++;
        if (circle > 0xFF) {
            circle = 0;
        }
        return (byte) circle;
    }
}
