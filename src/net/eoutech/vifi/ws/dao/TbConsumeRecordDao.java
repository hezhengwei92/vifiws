package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbConsumeRecord;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by wei on 2017/11/24.
 */
public interface TbConsumeRecordDao {

    /**
     * 用户通过idxUserId获取消费记录,从limit条往后size条信息
     * @param uid
     * @param begin
     * @param size
     * @return
     */

    List<TbConsumeRecord> selectById(String uid,Integer begin,Integer size);

    @Update("update tbConsumeRecord set cost_status = #{0} where uk_order_id = #{1}")
    int updateRecordStatus(int status, String sOrderId);

    TbConsumeRecord selectRecordByOrderId(String sOrderId);

    int insetRecord(TbConsumeRecord consumeRecord);

}
