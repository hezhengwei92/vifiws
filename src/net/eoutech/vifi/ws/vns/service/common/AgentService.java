package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbAdminDao;
import net.eoutech.vifi.ws.dao.TbAgentDao;
import net.eoutech.vifi.ws.entity.TbAgent;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class AgentService {
    private TbAgentDao readAgentDao;
    private TbAgentDao writeAgentDao;

    @Resource(name = "sqlSessionSlave")
    public void setReadMachineDao(SqlSession sqlSession) {
        this.readAgentDao = sqlSession.getMapper(TbAgentDao.class);
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteMachineDao(SqlSession sqlSession) {
        this.writeAgentDao = sqlSession.getMapper(TbAgentDao.class);
    }

    public int insertAgent(TbAgent agent){
        return writeAgentDao.insertAgent(agent);
    }

    public TbAgent selectAgentByOpenId(String openId){
        return readAgentDao.selectAgentByOpenId(openId);
    }

    public int updatePwdByOpenId(String pwd,String openId){
        return this.writeAgentDao.updatePwdByOpenId(pwd,openId);
    }
}
