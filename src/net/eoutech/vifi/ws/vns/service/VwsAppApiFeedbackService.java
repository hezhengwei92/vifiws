package net.eoutech.vifi.ws.vns.service;

import net.eoutech.utils.LogUtils;
import net.eoutech.vifi.ws.entity.TbFeedback;
import net.eoutech.vifi.ws.msg.req.VwsReqFeedback;
import net.eoutech.vifi.ws.msg.resp.VwsRespFeedback;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wei on 2017/11/30.
 */
@Service
public class VwsAppApiFeedbackService extends VwsAppServiceCommon<VwsReqFeedback, VwsRespFeedback>{
    @Override
    public int handle() {
        try {
            TbFeedback feedback=new TbFeedback();
            feedback.setIdxUserId(req.getUid());
            feedback.setBackContact(req.getPhoneNumber());
            feedback.setBackTitle(req.getTitle());
            feedback.setBackContent(req.getContent());
//        feedback.set
            feedback.setCreateTime(new Date());
            feedback.setCreateBy(req.getUid());
            int status=feedbackService.insertFeedback(feedback);
            LogUtils.info(req.getPhoneNumber()+"申诉原因："+req.getContent());
        }catch (Exception e){
            LogUtils.error(e.getMessage());
        }

        return 0;
    }
}
