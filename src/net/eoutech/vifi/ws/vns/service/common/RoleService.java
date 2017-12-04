package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbRoleDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/27.
 */

@Service
public class RoleService {
    private TbRoleDao roleDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.roleDao = sqlSession.getMapper(TbRoleDao.class);
    }

}
