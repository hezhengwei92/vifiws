package net.eoutech.vifi.ws.vns.service.common;

import net.eoutech.vifi.ws.dao.TbFeedbackDao;
import net.eoutech.vifi.ws.entity.TbFeedback;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeedbackService {

	//@Autowired
	private TbFeedbackDao feedbackDao;
	@Resource(name="sqlSessionMaster")
	public void setWriteFeedbackDao(SqlSession sqlSession) {
		this.feedbackDao = sqlSession.getMapper(TbFeedbackDao.class);
	}

	public int insertFeedback ( TbFeedback feedback ) {
		return feedbackDao.insertFeedback( feedback );
	}


}
