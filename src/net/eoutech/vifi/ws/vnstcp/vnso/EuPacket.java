package net.eoutech.vifi.ws.vnstcp.vnso;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class EuPacket
{
  private int hProtocol = 1;
  private int hFlag = 0;
  private int hDirection = 1;
  private int hMsgtype;
  private int hDatalen;
  private int hSessid;
  private Map<Integer, byte[]> tlvDatas = new HashMap();

  public int doParse(ByteBuffer buf)
  {
    try {
      byte b1 = buf.get();
      this.hProtocol = ((0xF0 & b1) >> 4);
      this.hFlag = (0xF & b1);

      byte b2 = buf.get();
      this.hDirection = ((0x80 & b2) >> 7);
      this.hMsgtype = (0x7F & b2);

      this.hDatalen = (0xFF & buf.get() | 0xFF00 & buf.get() << 8);
      this.hSessid = 
        (0xFF & buf.get() | 0xFF00 & buf.get() << 8 | 0xFF0000 & buf.get() << 16 | 
        0xFF000000 & buf.get() << 24);

      if (this.hDatalen != buf.limit() - buf.position())
      {
        return -1;
      }

      while (buf.limit() - buf.position() >= 4) {
        int t = 0xFF & buf.get() | 0xFF00 & buf.get() << 8;
        int l = 0xFF & buf.get() | 0xFF00 & buf.get() << 8;
        byte[] v = new byte[l];
        if (buf.limit() - buf.position() >= l) {
          buf.get(v, 0, l);
          this.tlvDatas.put(Integer.valueOf(t), v);
        }
      }

      return this.tlvDatas.size();
    } catch (Exception e) {
    }
    return -1;
  }

  public ByteBuffer doPackage()
  {
    ByteBuffer pkg = ByteBuffer.allocate(1024);

    byte pv = (byte)(0xF & this.hProtocol);
    byte fl = (byte)(0xF & this.hFlag);
    pkg.put((byte)(pv << 4 | fl));

    pkg.put((byte)((byte)(0xFF & this.hMsgtype) | 0x80));

    int dl = 0;
    dl += 4 * this.tlvDatas.size();
    for (byte[] v : this.tlvDatas.values()) {
      dl += v.length;
    }
    pkg.put((byte)(dl & 0xFF));
    pkg.put((byte)((dl & 0xFF00) >> 8));

    pkg.put((byte)(this.hSessid & 0xFF));
    pkg.put((byte)((this.hSessid & 0xFF00) >> 8));
    pkg.put((byte)((this.hSessid & 0xFF0000) >> 16));
    pkg.put((byte)((this.hSessid & 0xFF000000) >> 24));

    for (Map.Entry entry : this.tlvDatas.entrySet()) {
      int t = ((Integer)entry.getKey()).intValue();
      byte[] v = (byte[])entry.getValue();
      int l = v.length;
      pkg.put((byte)(t & 0xFF));
      pkg.put((byte)((t & 0xFF00) >> 8));
      pkg.put((byte)(l & 0xFF));
      pkg.put((byte)((l & 0xFF00) >> 8));
      pkg.put(v, 0, l);
    }

    pkg.flip();
    return pkg;
  }

  public int putTLVInt(int key, int val) {
    byte[] v = new byte[4];
    v[0] = ((byte)(val & 0xFF));
    v[1] = ((byte)((val & 0xFF00) >> 8));
    v[2] = ((byte)((val & 0xFF0000) >> 16));
    v[3] = ((byte)((val & 0xFF000000) >> 24));
    this.tlvDatas.put(Integer.valueOf(key), v);
    return this.tlvDatas.size();
  }

  public int putTLVStr(int key, String val) {
    Charset charset = Charset.forName("GBK");
    byte[] v = val.getBytes(charset);
    this.tlvDatas.put(Integer.valueOf(key), v);
    return this.tlvDatas.size();
  }

  public int delTLVVal(int key) {
    if (this.tlvDatas.containsKey(Integer.valueOf(key))) {
      this.tlvDatas.remove(Integer.valueOf(key));
      return this.tlvDatas.size();
    }
    return -1;
  }

  public int getTLVInt(int key) {
    if (this.tlvDatas.containsKey(Integer.valueOf(key))) {
      byte[] v = (byte[])this.tlvDatas.get(Integer.valueOf(key));
      if (v.length >= 4) {
        return 0xFF & v[0] | 0xFF00 & v[1] << 8 | 0xFF0000 & v[2] << 16 | 0xFF000000 & v[3] << 24;
      }
    }
    return -1;
  }

  public String getTLVStr(int key) {
    if (this.tlvDatas.containsKey(Integer.valueOf(key))) {
      byte[] v = (byte[])this.tlvDatas.get(Integer.valueOf(key));
      if (v.length > 0) {
        return new String(v, Charset.forName("GBK"));
      }
    }
    return "";
  }

  public int getHProtocol() {
    return this.hProtocol;
  }

  public void setHProtocol(int protocol) {
    this.hProtocol = protocol;
  }

  public int getHFlag() {
    return this.hFlag;
  }

  public void setHFlag(int flag) {
    this.hFlag = flag;
  }

  public int getHDirection() {
    return this.hDirection;
  }

  public void setHDirection(int direction) {
    this.hDirection = direction;
  }

  public int getHMsgtype() {
    return this.hMsgtype;
  }

  public void setHMsgtype(int msgtype) {
    this.hMsgtype = msgtype;
  }

  public int getHSessid() {
    return this.hSessid;
  }

  public void setHSessid(int sessid) {
    this.hSessid = sessid;
  }

  public int getDatalen() {
    return this.hDatalen;
  }

  public String toString() {
    return "protocol=" + this.hProtocol + ",flag=" + this.hFlag + ",direction=" + this.hDirection + ",msgtype=" + this.hMsgtype + 
      ",datalen=" + this.hDatalen + ",sessid=" + this.hSessid;
  }
}