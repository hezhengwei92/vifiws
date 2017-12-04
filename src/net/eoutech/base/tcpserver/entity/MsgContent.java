package net.eoutech.base.tcpserver.entity;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public class MsgContent {
    private short pkgLen;//单包长度
    private byte action;//动作判断标识
    private byte[] content;//具体内容

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte getAction() {
        return action;
    }

    public void setAction(byte action) {
        this.action = action;
    }

    public short getPkgLen() {
        return pkgLen;
    }

    public void setPkgLen(short pkgLen) {
        this.pkgLen = pkgLen;
    }

    public byte[] createByte(){
        byte[] bytes = new byte[pkgLen+2];
        bytes[0] = (byte)((pkgLen >> 8)&0xff);
        bytes[1] = (byte)(pkgLen & 0x00ff);
        bytes[2] = action;
        for (int i = 0; i < content.length; i++) {
            bytes[i+3] = content[i];
        }
        return bytes;
    }

}
