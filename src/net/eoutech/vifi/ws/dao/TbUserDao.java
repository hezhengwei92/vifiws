package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbUserDao {

    TbUser selectUserByUserId(String uid);

    @Select("select * from TbUser where idxUserPhone=#{0}")
    TbUser selectUserByPhoneNumber(String phoneNumber);

    @Update("update tbUser set user_currency = #{0} where uk_user_id = #{1}")
    int updateUserCurrency(int currency, String userId);

    @Update("update tbUser set user_currency = #{1} where uk_user_id = #{0}")
    int updateByUID(String uid,int userCurrency);

    int insertUser(TbUser tbUser);


    /**
     * 通过第三方的uid获取对应用户的UID
     * @param thirdUserId
     * @return
     */
    String selectUidByThirdUserId(String thirdUserId);

    /**
     * 通过用户的OpenId获取对应用户的UID
     * @param thirdOpenId
     * @return
     */
    String selctUidByThirdOpenId(String thirdOpenId);

}