package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbWithdrawalsDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/28.
 */
@Service
public class WithdrawalsService {
    private TbWithdrawalsDao withdrawalsDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.withdrawalsDao = sqlSession.getMapper(TbWithdrawalsDao.class);
    }
}
