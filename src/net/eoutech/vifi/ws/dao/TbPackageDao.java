package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbPackage;

import java.util.List;

/**
 * Created by wei on 2017/11/24.
 */
public interface TbPackageDao {
    List<TbPackage> selectAll();
}
