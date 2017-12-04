package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbAssetDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/28.
 */
@Service
public class AssetService {
    private TbAssetDao assetDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.assetDao = sqlSession.getMapper(TbAssetDao.class);
    }
}
