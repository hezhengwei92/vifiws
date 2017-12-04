package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbAgent;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by wei on 2017/11/24.
 */
public interface TbAgentDao {

    TbAgent selectAgentByOpenId(String openId);


    int insertAgent(TbAgent agent);


    @Update("update tbagent set agent_password=#{0} where open_id=#{1}")
    int updatePwdByOpenId(String pwd,String openId);

}



