package net.eoutech.vifi.ws.vns.service;

import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import net.eoutech.vifi.ws.msg.req.VwsReqOrderVerfy;
import net.eoutech.vifi.ws.msg.resp.VwsRespOrderVerfy;
import org.springframework.stereotype.Service;

/**
 * Created by wei on 2017/11/28.
 */
@Service
public class VwsAppApiOrderVerfyService extends VwsAppServiceCommon<VwsReqOrderVerfy, VwsRespOrderVerfy> {
    @Override
    public int handle() {

        TbConsumeRecord consumeRecord = consumeRecordService.selectRecordByOrderId(req.getOrderID());
        if (null == consumeRecord) {
            resp.setCode(200);
            resp.setMsg("无此消费记录，请确认");
            resp.setIsOK(2);
            return 0;
        }
        //机器是否启动成功  0 失败   1 成功
        if (consumeRecord.getCostStatus() == 1) {
            resp.setCode(200);
            resp.setMsg("启动成功");
            resp.setIsOK(1);
            return 0;
        } else {
            resp.setCode(200);
            resp.setMsg("启动中");
            resp.setIsOK(0);
        }

        return 0;
    }
}
