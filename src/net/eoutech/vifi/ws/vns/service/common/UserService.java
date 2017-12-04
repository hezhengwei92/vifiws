package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbUserDao;
import net.eoutech.vifi.ws.entity.TbUser;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class UserService {
    private TbUserDao readUserDao;
    private TbUserDao writeUserDao;

    @Resource(name = "sqlSessionSlave")
    public void setReadUserDao(SqlSession sqlSession) {
        this.readUserDao = sqlSession.getMapper(TbUserDao.class);
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteUserDao(SqlSession sqlSession) {
        this.writeUserDao = sqlSession.getMapper(TbUserDao.class);
    }

    public TbUser selectUserByUserId(String uid) {
        return readUserDao.selectUserByUserId(uid);
    }

    public String selectUidByThirdUserId(String thirdUserID){
        return readUserDao.selectUidByThirdUserId(thirdUserID);
    }

    public String selctUidByThirdOpenId(String ThirdOpenId){
        return readUserDao.selctUidByThirdOpenId(ThirdOpenId);
    }

    public TbUser selectUserByPhoneNumber(String phoneNumber){
        return readUserDao.selectUserByPhoneNumber(phoneNumber);
    }

    public int updateUserCurrency(int currency, String userId) {
        return writeUserDao.updateUserCurrency(currency, userId);
    }

    public int updateByUID(String uid, int userCurrency) {
        return writeUserDao.updateByUID(uid, userCurrency);
    }

    public int insertUser(TbUser user){
        return writeUserDao.insertUser(user);
    }



}
