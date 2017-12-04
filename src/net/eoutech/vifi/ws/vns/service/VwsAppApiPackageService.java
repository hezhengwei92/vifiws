package net.eoutech.vifi.ws.vns.service;

import com.alibaba.fastjson.JSON;
import net.eoutech.vifi.ws.entity.TbPackage;
import net.eoutech.vifi.ws.msg.req.VwsReqPackage;
import net.eoutech.vifi.ws.msg.resp.VwsRespPackage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wei on 2017/11/28.
 */
@Service
public class VwsAppApiPackageService extends VwsAppServiceCommon<VwsReqPackage, VwsRespPackage> {
    @Override
    public int handle() {
        List<TbPackage> packages = packageService.selectAll();
        TbPackage tbPackage = null;
        List<Map<String, Object>> packageList = new ArrayList<>();

        if (null != packages && packages.size() > 0) {
            for (int i = 0; i < packages.size(); i++) {
                tbPackage = packages.get(i);
                Map<String, Object> packageMap = new HashMap<>();
                packageMap.put("packageId", tbPackage.getUkPackageId());//套餐编号
                packageMap.put("packagePrice", tbPackage.getPackagePrice() / 100);//套餐价格，单位 分
                packageMap.put("packageCurrency", tbPackage.getPackagePrice());//套餐游戏币个数
                packageMap.put("packageDescription", tbPackage.getPackageDescription());//套餐描述
                packageList.add(packageMap);
            }
        } else {
            resp.setMsg("Noting Package");
            resp.setCode(433);
        }
        String data = JSON.toJSONString(packageList);
        resp.setDate(data);
        resp.setMsg("success");
        resp.setCode(100);
        return 0;
    }
}
