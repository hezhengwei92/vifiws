package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbDictionary;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public interface TbDictionaryDao {


    /**
     * 根据key的值取出对应的value；
     * @param key
     * @return
     */
    String selectByKey(String key);


    /**
     * 跟新对应key的value
     * @param key
     * @param value
     * @return
     */
    @Update("update tbdictionary set key_value=#{1} where uk_key_word=#{0}")
    int updateValueByKey(String key , String value); //// TODO: 2017/11/29

    TbDictionary selectDictionaryByKey(String key);
}
