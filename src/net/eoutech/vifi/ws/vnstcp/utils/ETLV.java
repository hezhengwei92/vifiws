package net.eoutech.vifi.ws.vnstcp.utils;

public class ETLV
{
  private String tag;
  private int length;
  private String value;

  public ETLV(String tag, int length, String value)
  {
    this.tag = tag;
    this.length = length;
    this.value = value;
  }

  public String getTag() {
    return this.tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public int getLength() {
    return this.length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}