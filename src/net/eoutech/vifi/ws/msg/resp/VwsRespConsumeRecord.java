package net.eoutech.vifi.ws.msg.resp;

import java.util.Date;

/**
 * Created by wei on 2017/11/27.
 */
public class VwsRespConsumeRecord extends VwsRespCommon{
    private String date;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder().append("{");
//        sb.append( toIntJS( "code", code, "," ) ).append(toStrJS( "msg", msg, "," )).append(toStrJS( "startTime", startTime, "," ))
//                .append(toStrJS( "machineID", machineID, "," )).append(toIntJS( "consumeCoin", consumeCoin, "," ))
//                .append(toStrJS( "payType", payType, "," )).append(toIntJS( "payMomeny", payMomeny, "," ))
//                .append(toIntJS( "orderID", orderID, "}" ));
//        return sb.toString();
//    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        sb.append( toIntJS( "code", code, "," ) ).append(toStrJS( "msg", msg, "," )).append(toIntJS( "date", date, "}" ));
        return sb.toString();
    }
}
