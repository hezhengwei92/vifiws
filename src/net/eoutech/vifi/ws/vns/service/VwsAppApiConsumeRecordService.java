package net.eoutech.vifi.ws.vns.service;

import com.alibaba.fastjson.JSON;
import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import net.eoutech.vifi.ws.entity.TbUser;
import net.eoutech.vifi.ws.entity.TbUserRecord;
import net.eoutech.vifi.ws.msg.req.VwsReqConsumerRecord;
import net.eoutech.vifi.ws.msg.resp.VwsRespConsumeRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class VwsAppApiConsumeRecordService extends VwsAppServiceCommon<VwsReqConsumerRecord, VwsRespConsumeRecord> {
    @Override
    public int handle() {
        List<TbConsumeRecord> tbConsumeRecordS = consumeRecordService.selectById(req.getUid(), req.getBegin(), 6);

        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        int i = 0;
        for (TbConsumeRecord consume : tbConsumeRecordS) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ID", i++);
            map.put("startTime", consume.getCreateTime());
            map.put("machineID", consume.getIdxMachineId());
            map.put("consumeCoin", consume.getCostCurrency());
            map.put("payType", "WEIXIN");
            map.put("payMomeny", consume.getCostCurrency());
            map.put("orderID", "123456");
            list.add(map);
        }
        String data = JSON.toJSONString(list);
        resp.setDate(data);
        resp.setMsg("ok");
        return 0;
    }


}
