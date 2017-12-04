package net.eoutech.base.tcpserver.entity;

/**
 * Created by WangY on 2017/3/16 0016.
 * 不需要解析的byte数组内容
 */
public class EouByteContent extends EouMsg{
    private byte[] byteContent;

    public EouByteContent(byte[] byteContent) {
        this.byteContent = byteContent;
    }

    public byte[] getByteContent() {
        return byteContent;
    }

    public void setByteContent(byte[] byteContent) {
        this.byteContent = byteContent;
    }
}
