package net.eoutech.vifi.ws.dao;

import net.eoutech.vifi.ws.entity.TbFeedback;
import org.springframework.stereotype.Service;

/**
 * Created by wei on 2017/11/27.
 */
@Service
public interface TbFeedbackDao {

    int insertFeedback(TbFeedback feedback);
}
