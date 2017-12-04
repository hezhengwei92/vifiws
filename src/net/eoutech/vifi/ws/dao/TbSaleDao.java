package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbSale;

/**
 * Created by wei on 2017/12/1.
 */
public interface TbSaleDao {

    TbSale selectByMachine(String machineID);

    int updateSale(TbSale sale);
}
