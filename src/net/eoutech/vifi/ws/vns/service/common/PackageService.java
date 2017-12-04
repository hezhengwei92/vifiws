package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbMachineDao;
import net.eoutech.vifi.ws.dao.TbPackageDao;
import net.eoutech.vifi.ws.entity.TbMachine;
import net.eoutech.vifi.ws.entity.TbPackage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class PackageService {
    //@Autowired
    private TbPackageDao packageDao;
    @Resource(name="sqlSessionMaster")
    public void setWriteLocationDao(SqlSession sqlSession) {
        this.packageDao = sqlSession.getMapper(TbPackageDao.class);
    }

    public List<TbPackage> selectAll () {
        return packageDao.selectAll();
    }
}
