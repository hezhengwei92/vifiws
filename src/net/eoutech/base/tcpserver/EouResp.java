package net.eoutech.base.tcpserver;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/9 0009.
 */
public class EouResp {

    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    protected StringBuilder toStrJS(String name, Object value, String ending ) {
        if ( value == null ) {
            value = "";
        }
        return new StringBuilder( "\"" ).append( name ).append( "\":\"" ).append( value ).append( "\"" ).append( ending );
    }

}
