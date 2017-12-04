package net.eoutech.vifi.ws.machine.service;

import net.eoutech.vifi.ws.dao.TbUserDao;
import net.eoutech.vifi.ws.entity.TbUser;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class IndexHomeService {
    private TbUserDao writeUserDao;
    private TbUserDao readUserDao;


    @Resource(name = "sqlSessionMaster")
    public void setWriteUserDao(SqlSession sqlSession) {
        this.writeUserDao = sqlSession.getMapper(TbUserDao.class);
    }

    @Resource(name = "sqlSessionSlave")
    public void setReadUerDao(SqlSession sqlSession) {
        this.readUserDao = sqlSession.getMapper(TbUserDao.class);
    }

    public TbUser selectUserByUserId(String uid){
        TbUser user=readUserDao.selectUserByUserId(uid);
        return user;
    }


}
