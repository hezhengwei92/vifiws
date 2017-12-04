package net.eoutech.vifi.ws.msg.req;

/**
 * Created by wei on 2017/11/27.
 */
public class VwsReqConsumerRecord extends VwsReqCommon {
    private Integer begin;
    private Integer size;

    @Override
    public VwsEnumReqType getRequestType() {
        return VwsEnumReqType.myValueOf ( cmd );
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    @Override
    public String toString() {

        return "uid=" + uid + ",cmd=" + cmd + ",begin=" + begin
                + ",size=" + size;
    }

}
