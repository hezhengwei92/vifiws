package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbSaleDao;
import net.eoutech.vifi.ws.entity.TbSale;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/12/2.
 */
@Service
public class SaleService {

    private TbSaleDao readSaleDao;
    private TbSaleDao writeSaleDao;

    @Resource(name = "sqlSessionSlave")
    public void setReadUserDao(SqlSession sqlSession) {
        this.readSaleDao = sqlSession.getMapper(TbSaleDao.class);
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteUserDao(SqlSession sqlSession) {
        this.writeSaleDao = sqlSession.getMapper(TbSaleDao.class);
    }

    public TbSale selectByMachine(String machineID){
        return readSaleDao.selectByMachine(machineID);
    }

    public int updateSale(TbSale sale){
        return this.writeSaleDao.updateSale(sale);
    }
}
