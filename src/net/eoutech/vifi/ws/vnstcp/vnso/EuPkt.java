package net.eoutech.vifi.ws.vnstcp.vnso;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class EuPkt
{
  private int protocol;
  private int flag;
  private int direction;
  private int msgtype;
  private int datalen;
  private int sessid;
  private Map<Integer, byte[]> data = new HashMap();

  public String toStr() {
    return "protocol=" + this.protocol + ",flag=" + this.flag + ",direction=" + this.direction + ",msgtype=" + this.msgtype + 
      ",datalen=" + this.datalen + ",sessid=" + this.sessid;
  }

  public int parse(byte[] buf, int len) {
    int i = 0;

    byte b1 = buf[(i++)];
    this.protocol = ((0xF0 & b1) >> 4);
    this.flag = (0xF & b1);

    byte b2 = buf[(i++)];
    this.direction = ((0x80 & b2) >> 7);
    this.msgtype = (0x7F & b2);

    this.datalen = (0xFF & buf[(i++)] | 0xFF00 & buf[(i++)] << 8);
    this.sessid = 
      (0xFF & buf[(i++)] | 0xFF00 & buf[(i++)] << 8 | 0xFF0000 & buf[(i++)] << 16 | 
      0xFF000000 & buf[(i++)] << 24);

    if (this.datalen != len - 8)
    {
      return -1;
    }

    while (i < len)
    {
      int t = 0xFF & buf[(i++)] | 0xFF00 & buf[(i++)] << 8;
      int l = 0xFF & buf[(i++)] | 0xFF00 & buf[(i++)] << 8;

      byte[] v = new byte[l];
      System.arraycopy(buf, i, v, 0, l);
      i += l;
      this.data.put(Integer.valueOf(t), v);
    }

    return this.data.size();
  }

  public int getTLVInt(int key) {
    if (this.data.containsKey(Integer.valueOf(key))) {
      byte[] v = (byte[])this.data.get(Integer.valueOf(key));
      if (v.length >= 4) {
        return 0xFF & v[0] | 0xFF00 & v[1] << 8 | 0xFF0000 & v[2] << 16 | 0xFF000000 & v[3] << 24;
      }
    }
    return -1;
  }

  public String getTLVStr(int key) {
    if (this.data.containsKey(Integer.valueOf(key))) {
      byte[] v = (byte[])this.data.get(Integer.valueOf(key));
      if (v.length > 0) {
        return new String(v, Charset.forName("GBK"));
      }
    }
    return "";
  }

  public int getProtocol() {
    return this.protocol;
  }

  public void setProtocol(int protocol) {
    this.protocol = protocol;
  }

  public int getFlag() {
    return this.flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public int getDirection() {
    return this.direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }

  public int getMsgtype() {
    return this.msgtype;
  }

  public void setMsgtype(int msgtype) {
    this.msgtype = msgtype;
  }

  public int getDatalen() {
    return this.datalen;
  }

  public void setDatalen(int datalen) {
    this.datalen = datalen;
  }

  public int getSessid() {
    return this.sessid;
  }

  public void setSessid(int sessid) {
    this.sessid = sessid;
  }

  public Map<Integer, byte[]> getData() {
    return this.data;
  }

  public void setData(Map<Integer, byte[]> data) {
    this.data = data;
  }
}