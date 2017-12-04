package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbConsumeRecordDao;
import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class ConsumeRecordService {
    private TbConsumeRecordDao readConsumeRecordDao;
    private TbConsumeRecordDao writeConsumeRecordDao;

    @Resource(name = "sqlSessionSlave")
    public void setReadConsumeRecordDao(SqlSession sqlSession) {
        this.readConsumeRecordDao = sqlSession.getMapper(TbConsumeRecordDao.class);
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteConsumeRecordDao(SqlSession sqlSession) {
        this.writeConsumeRecordDao = sqlSession.getMapper(TbConsumeRecordDao.class);
    }

    public List<TbConsumeRecord> selectById(String uid, int begin, int size) {
        return readConsumeRecordDao.selectById(uid, begin, size);
    }

    public int updateRecordStatus(int status, String sOrderId) {
        return writeConsumeRecordDao.updateRecordStatus(status, sOrderId);
    }
    public TbConsumeRecord selectRecordByOrderId(String orderID){
        return readConsumeRecordDao.selectRecordByOrderId(orderID);
    }
    public int insetRecord(TbConsumeRecord consumeRecord){
        return writeConsumeRecordDao.insetRecord(consumeRecord);
    }

}
