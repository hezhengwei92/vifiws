package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbCoinDao;
import net.eoutech.vifi.ws.dao.TbMachineDao;
import net.eoutech.vifi.ws.entity.TbCoin;
import net.eoutech.vifi.ws.entity.TbMachine;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wei on 2017/11/25.
 */
@Service
public class MachineService {
    private TbMachineDao readMachineDao;
    private TbMachineDao writeMachineDao;

    @Resource(name = "sqlSessionSlave")
    public void setReadMachineDao(SqlSession sqlSession) {
        this.readMachineDao = sqlSession.getMapper(TbMachineDao.class);
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteMachineDao(SqlSession sqlSession) {
        this.writeMachineDao = sqlSession.getMapper(TbMachineDao.class);
    }

    public int updateMachine(TbMachine machine){
        return this.writeMachineDao.updateMachine(machine);
    }

    public TbMachine selectMachineByVidAndUid(String vid,String uid){
        return this.readMachineDao.selectMachineByVidAndUid(vid,uid);
    }

    private TbCoinDao readCoinDao;
    private TbCoinDao writeCoinDao;

    @Resource(name = "sqlSessionSlave")
    public void setReadCoinDao(SqlSession sqlSession) {
        this.readCoinDao = sqlSession.getMapper(TbCoinDao.class);
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteCoinDao(SqlSession sqlSession) {
        this.writeCoinDao = sqlSession.getMapper(TbCoinDao.class);
    }

    public TbMachine selectMachineByVid(String vid) {
        return readMachineDao.selectMachineByVid(vid);
    }

    public List<TbMachine> selectAll(){
        return readMachineDao.selectAll();
    }

    public List<TbMachine> selectMachineByPositionCode(String positionCode){
        return readMachineDao.selectMachineByPositionCode(positionCode);
    }
    public int updateMachineInfo(String sMsgType, String sVid) {
        return writeMachineDao.updateMachineInfo(sMsgType, sVid);
    }

    public int insertCoinInfo(String sVid, Integer iCoin, Integer keyId) {
        if (keyId >= 0) {
            writeCoinDao.updateCoinInfo("LAST",keyId);
        }
        TbCoin coin = new TbCoin();
        coin.setIdxMachineId(sVid);
        coin.setMachineCoinNumber(iCoin);
        coin.setMachineCoinStatus("ADD");
        coin.setCreateTime(new Date());
        writeCoinDao.insertCoinInfo(coin);
        return 0;
    }

    public TbCoin selectCoinRecord(String sVid) {
        return readCoinDao.selectCoinRecord(sVid);
    }
}
