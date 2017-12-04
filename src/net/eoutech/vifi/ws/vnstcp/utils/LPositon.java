package net.eoutech.vifi.ws.vnstcp.utils;

public class LPositon
{
  private int _vL;
  private int _position;

  public LPositon(int _vL, int position)
  {
    this._vL = _vL;
    this._position = position;
  }

  public int get_vL() {
    return this._vL;
  }

  public void set_vL(int _vL) {
    this._vL = _vL;
  }

  public int get_position() {
    return this._position;
  }

  public void set_position(int _position) {
    this._position = _position;
  }
}