package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbDictionaryDao;
import net.eoutech.vifi.ws.dao.TbPlaceDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class PlaceService {
    private TbPlaceDao placeDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.placeDao = sqlSession.getMapper(TbPlaceDao.class);
    }
}
