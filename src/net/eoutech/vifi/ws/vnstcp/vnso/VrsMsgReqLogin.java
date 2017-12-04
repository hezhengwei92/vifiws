package net.eoutech.vifi.ws.vnstcp.vnso;

import net.eoutech.annotation.MyAnno;

public class VrsMsgReqLogin
{

  @MyAnno(tag=51)
  private String vid;

  @MyAnno(tag=52)
  private Integer seq;

  @MyAnno(tag=53)
  private String non;

  @MyAnno(tag=54)
  private String rsp;

  @MyAnno(tag=55)
  private Integer net;

  @MyAnno(tag=56)
  private String ver;

  @MyAnno(tag=57)
  private String icc;

  @MyAnno(tag=58)
  private String opi;

  public String getVid()
  {
    return this.vid;
  }

  public void setVid(String vid) {
    this.vid = vid;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public String getNon() {
    return this.non;
  }

  public void setNon(String non) {
    this.non = non;
  }

  public String getRsp() {
    return this.rsp;
  }

  public void setRsp(String rsp) {
    this.rsp = rsp;
  }

  public Integer getNet() {
    return this.net;
  }

  public void setNet(Integer net) {
    this.net = net;
  }

  public String getVer() {
    return this.ver;
  }

  public void setVer(String ver) {
    this.ver = ver;
  }

  public String getIcc() {
    return this.icc;
  }

  public void setIcc(String icc) {
    this.icc = icc;
  }

  public String getOpi() {
    return this.opi;
  }

  public void setOpi(String opi) {
    this.opi = opi;
  }
}