package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbDictionaryDao;
import net.eoutech.vifi.ws.entity.TbDictionary;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public class DictionaryService {
    private TbDictionaryDao readDictionaryDao;
    private TbDictionaryDao writeDictionaryDao;

    @Resource(name = "sqlSessionSlave")
    public void setReadDictionaryDao(SqlSession sqlSession) {
        this.readDictionaryDao = sqlSession.getMapper(TbDictionaryDao.class);
    }

    @Resource(name = "sqlSessionMaster")
    public void setWriteDictionaryDao(SqlSession sqlSession) {
        this.writeDictionaryDao = sqlSession.getMapper(TbDictionaryDao.class);
    }

    public TbDictionary selectDictionaryByKey(String key) {
        return readDictionaryDao.selectDictionaryByKey(key);
    }

    public String selectByKey(String keyWord){
        return readDictionaryDao.selectByKey(keyWord);
    }

    public int updateValueByKey(String key,String value){
        return writeDictionaryDao.updateValueByKey(key,value);
    }
}
