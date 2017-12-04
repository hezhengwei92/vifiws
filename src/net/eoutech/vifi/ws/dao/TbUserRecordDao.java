package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbUserRecord;

import java.util.List;

/**
 * Created by wei on 2017/11/24.
 */
public interface TbUserRecordDao {

    /**
     * 用户通过idxUeerId获取充值记录,从limit条往后size条信息
     * @param idxUserId
     * @param limit
     * @param size
     * @return
     */
    List<TbUserRecord> selectRecordById(String idxUserId, int limit, int size);

}
