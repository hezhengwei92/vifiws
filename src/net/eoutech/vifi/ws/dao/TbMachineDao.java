package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbMachine;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by wei on 2017/11/24.
 */
public interface TbMachineDao {

    TbMachine selectMachineByVid(String vid);

    TbMachine selectMachineByVidAndUid(String vid,String uid);

    List<TbMachine> selectMachineByPositionCode(String positionCode);


    //更新Machine都可用
    int updateMachine(TbMachine machine);

    @Update("update tbMachine set machine_type = #{0} where uk_machine_id = #{1}")
    int updateMachineInfo(String sMsgType, String sVid);


    /**
     * 获取所有的设备列表
     * @return
     */
    List<TbMachine> selectAll();


}
