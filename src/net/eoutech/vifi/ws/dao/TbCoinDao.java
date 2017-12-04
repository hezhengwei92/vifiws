package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbCoin;
import org.apache.ibatis.annotations.Update;

/**
 * Created by wei on 2017/11/27.
 */
public interface TbCoinDao {
    TbCoin selectCoinRecord(String sVid);

    @Update("update TbCoin set machine_coin_status = #{0} where uk_key_id = #{1}")
    int updateCoinInfo(String status, Integer keyId);

    int insertCoinInfo(TbCoin coin);
}
