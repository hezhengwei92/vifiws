package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbAdminDao;
import net.eoutech.vifi.ws.entity.TbAdmin;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class AdminService {
    private TbAdminDao adminDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.adminDao = sqlSession.getMapper(TbAdminDao.class);
    }

//    public List<TbAdmin> queryById (String uid, int begin, int size) {
//
//        return adminDao.queryById(uid,begin,size);
//    }
}
