package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbUserRecordDao;
import net.eoutech.vifi.ws.entity.TbUserRecord;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class UserRecordService {
    private TbUserRecordDao userRecordDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.userRecordDao = sqlSession.getMapper(TbUserRecordDao.class);
    }

    public List<TbUserRecord> selectRecordById (String uid, int begin, int size) {
        return userRecordDao.selectRecordById(uid,begin,size);
    }

}
