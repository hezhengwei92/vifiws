package net.eoutech.vifi.ws.msg.resp;

/**
 * Created by wei on 2017/11/28.
 */
public class VwsRespOrderVerfy  extends VwsRespCommon{
    private Integer isOK;

    public Integer getIsOK() {
        return isOK;
    }

    public void setIsOK(Integer isOK) {
        this.isOK = isOK;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        sb.append( toIntJS( "code", code, "," ) ).append(toStrJS( "msg", msg, "," )).append(toIntJS( "isOK", isOK, "}" ));
        return sb.toString();
    }
}
