package com.model2.mvc.service.review.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.review.ReviewDao;

//==> 회원관리 DAO CRUD 구현
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

}
