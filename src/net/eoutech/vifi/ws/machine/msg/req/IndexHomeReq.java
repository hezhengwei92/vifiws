package net.eoutech.vifi.ws.machine.msg.req;

/**
 * Created by wei on 2017/11/27.
 */

public class IndexHomeReq {
    private String cmd;
    private String uid;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
