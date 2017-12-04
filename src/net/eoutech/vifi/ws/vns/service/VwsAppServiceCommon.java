package net.eoutech.vifi.ws.vns.service;

import net.eoutech.utils.EuFileUtil;
import net.eoutech.vifi.ws.vns.service.common.*;
import net.eoutech.vifi.ws.msg.req.VwsReqCommon;
import net.eoutech.vifi.ws.msg.resp.VwsRespCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class VwsAppServiceCommon<Q extends VwsReqCommon, P extends VwsRespCommon> implements VwsAppServiceInterface {

    protected Q req;
    protected P resp;

//	protected String uid;
//	protected String pid;
//	protected String cmd;
//	protected String pass;

//	protected String appId;

    //	protected TbUser user;
//	protected EuEntity euEntity;
    protected EuFileUtil fileUtil = EuFileUtil.getInstance();


    @Autowired
    protected MachineService machineService;//机器
    @Autowired
    protected ConsumeRecordService consumeRecordService;//用户消费记录
    @Autowired
    protected UserRecordService userRecordService;//用户充值记录
    @Autowired
    protected UserService userService;//用户
    @Autowired
    protected PackageService packageService;//套餐
    @Autowired
    protected CoinService coinService;//设备投币情况
    @Autowired
    protected DictionaryService dictionaryService;//
    @Autowired
    protected FeedbackService feedbackService;//反馈
    @Autowired
    protected AgentService agentService;//代理商 商户
    @Autowired
    protected SaleService saleService;


    @Override
    @Transactional
    public synchronized int doHandle(VwsReqCommon req, VwsRespCommon resp) {
        this.req = (Q) req;
        this.resp = (P) resp;
        return handle();
    }

    public abstract int handle() ;

//    public abstract int init();
}
