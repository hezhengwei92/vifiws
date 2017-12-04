package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbCoinDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class CoinService {
    private TbCoinDao coinDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.coinDao = sqlSession.getMapper(TbCoinDao.class);
    }
}
