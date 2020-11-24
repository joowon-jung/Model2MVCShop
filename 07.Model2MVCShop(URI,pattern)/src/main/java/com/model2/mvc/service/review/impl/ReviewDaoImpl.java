package com.model2.mvc.service.review.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.review.ReviewDao;

//==> 府轰包府 DAO CRUD 备泅
@Repository("reviewDaoImpl")
public class ReviewDaoImpl implements ReviewDao {

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public ReviewDaoImpl() {
		System.out.println(this.getClass());
	}
	
	///Method
	@Override
	public void insertReview(Review review) throws Exception {
		sqlSession.insert("ReviewMapper.insertReview", review);
	}

	@Override
	public Review findReview(int no) throws Exception {
		return sqlSession.selectOne("ReviewMapper.findReview", no);
	}
	
	@Override
	public void updateReview(Review review) throws Exception {
		sqlSession.update("ReviewMapper.updateReview", review);
	}

	@Override
	public List<Review> getReviewList(Search search) throws Exception {
		return sqlSession.selectList("ReviewMapper.getReviewList", search);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("ReviewMapper.getTotalCount", search);
	}

}
