package net.eoutech.vifi.ws.msg.resp;

/**
 * Created by wei on 2017/11/28.
 */
public class VwsRespPackage extends VwsRespCommon{
    private String data;
    public String getDate() {
        return data;
    }

    public void setDate(String data) {
        this.data = data;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        sb.append( toIntJS( "code", code, "," ) ).append(toStrJS( "msg", msg, "," )).append(toIntJS( "data", data, "}" ));
        return sb.toString();
    }
}
