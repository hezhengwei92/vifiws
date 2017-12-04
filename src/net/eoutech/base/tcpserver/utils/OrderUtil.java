package net.eoutech.base.tcpserver.utils;

import net.eoutech.base.tcpserver.entity.EouData;
import net.eoutech.base.tcpserver.entity.MsgContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by WangY on 2017/5/24 0024.
 * 对于设备操作的各种命令
 */
public class OrderUtil {
    /**
     * @param sender   发送者
     * @param reveiver 接收者
     * @param circle   循环码
     * @param pkgNum   包个数
     * @param action   动作
     * @param con      内容
     * @return EouData对象
     */
    private static EouData OrderBase(byte sender, byte reveiver, byte circle, byte pkgNum, byte action, byte[] con) {
        EouData data = new EouData();
        data.setSender(sender);
        data.setReceiver(reveiver);
        data.setCircle(circle);
        data.setPkgNum(pkgNum);
        List<MsgContent> list = Collections.synchronizedList(new ArrayList<MsgContent>());
        int length = 0;
        for (int i = 0; i < pkgNum; i++) {
            MsgContent msgContent = new MsgContent();
            msgContent.setPkgLen((short) (con.length + 1));
            msgContent.setAction(action);
            msgContent.setContent(con);
            list.add(msgContent);
            length += msgContent.createByte().length;
        }
        data.setMsgList(list);
        data.setLength((short) (length + 1));
        data.setCrc(EouData.creatCRC(data));
        return data;
    }

    /**
     * 云端命令设备关机
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData DevicePosition(byte circle) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x0B;
        byte[] content = new byte[]{0x00};
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 重置设备
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData ResetDevice(byte circle) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x08;
        byte[] content = new byte[]{0x00};
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 执行APP
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData RebootDevice(byte circle) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x07;
        byte[] content = new byte[]{0x00};
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 初始化设备上卡
     *
     * @param circle  循环码
     * @param content 分配卡的ATR信息
     * @return EouData对象
     */
    public static EouData InitializeDevice(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x0C;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 修改设备密码
     *
     * @param circle  循环码
     * @param content 修改的密码
     * @return EouData对象
     */
    public static EouData ChangePassword(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x09;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 更新固件
     *
     * @param circle  循环码
     * @param content 文件中读到的固件更新命令
     * @return EouData对象
     */
    public static EouData UpdateFirmware(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x0D;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 允许此MAC地址上网
     *
     * @param circle  循环码
     * @param content MAC地址
     * @return EouData对象
     */
    public static EouData AllowMAC(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x0A;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 查询流量
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData SearchFlow(byte circle) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x11;
        byte[] content = new byte[]{0x00};
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 复位卡池中的卡
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData RestCard(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x04;
        byte pkgNUm = 0x01;
        byte action = 0x1F;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 心跳使用
     *
     * @param circle  循环码
     * @param content 发送内容
     * @return EouData对象
     */
    public static EouData WakeData(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x10;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 计算流量
     *
     * @param circle  循环码
     * @param content 发送内容
     * @return EouData对象
     */
    public static EouData FlowCount(byte circle, byte[] content) {
        byte sender = 0x08;
        byte receiver = 0x04;
        byte pkgNUm = 0x01;
        byte action = 0x01;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 从白名单中移除一个设备的MAC地址
     *
     * @param circle  循环码
     * @param content 发送内容
     * @return EouData对象
     */
    public static EouData RemoveDevice(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x06;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 回复收到流量的命令
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData ResponseFlow(byte circle) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x13;
        byte[] content = new byte[]{0x00};
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 通知设备发送复位命令
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData ReLink(byte circle) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x15;
        byte[] content = new byte[]{0x00};
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 通知设备发送USIM信息
     *
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData UpdateUSIM(byte circle) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x16;
        byte[] content = new byte[]{0x00};
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 更新文件
     *
     * @param con    发送内容
     * @param circle 循环码
     * @return EouData对象
     */
    public static EouData UpdateVer(byte[] con, byte circle) {
        EouData data = new EouData();
        data.setSender((byte) 4);
        data.setReceiver((byte) 1);
        data.setCircle(circle);
        data.setLength((short) ((con[0] << 8) + con[1]));
        data.setPkgNum(con[2]);
        List<MsgContent> list = Collections.synchronizedList(new ArrayList<MsgContent>());
        MsgContent msgContent = new MsgContent();
        msgContent.setPkgLen((short) ((con[3] << 8) + con[4]));
        msgContent.setAction(con[5]);
        byte[] newCon = new byte[con.length - 6];
        for (int i = 0; i < newCon.length; i++) {
            newCon[i] = con[i + 6];
        }
        msgContent.setContent(newCon);
        list.add(msgContent);
        data.setMsgList(list);
        data.setCrc(EouData.creatCRC(data));
        return data;
    }

    /**
     * 通知设备移除此批次
     *
     * @param circle  循环码
     * @param content 发送内容
     * @return EouData对象
     */
    public static EouData RemoveBatch(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x17;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    /**
     * 通知设备WiFi名称
     *
     * @param circle  循环码
     * @param content 发送内容
     * @return EouData对象
     */
    public static EouData WIFIName(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x18;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    public static EouData ResponseCode(byte circle,byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x01;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    public static EouData StartDevice(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x02;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }

    public static EouData ResponsePulse(byte circle, byte[] content) {
        byte sender = 0x04;
        byte receiver = 0x01;
        byte pkgNUm = 0x01;
        byte action = 0x03;
        return OrderBase(sender, receiver, circle, pkgNUm, action, content);
    }
}
