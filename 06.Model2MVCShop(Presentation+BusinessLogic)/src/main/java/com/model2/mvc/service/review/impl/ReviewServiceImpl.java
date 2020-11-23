package com.model2.mvc.service.review.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.review.ReviewDao;
import com.model2.mvc.service.review.ReviewService;

//==> 리뷰관리 서비스 구현
@Service("reviewServiceImpl")
public class ReviewServiceImpl implements ReviewService {

	///Field
	@Autowired
	@Qualifier("reviewDaoImpl")
	private ReviewDao reviewDao;
	
	public void setReviewDao(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}
	
	///Constructor
	public ReviewServiceImpl() {
		System.out.println(this.getClass());
	}
	
	@Override
	// 리뷰 등록을 위한 비즈니스 수행 
	public void addReview(Review review) throws Exception {
		reviewDao.insertReview(review);
	}

	@Override
	// 구매 번호나 리뷰 번호로 리뷰 찾아오는 비즈니스 수행
	public Review getReview(int no) throws Exception {
		return reviewDao.findReview(no);
	}

	@Override
	public Map<String, Object> getReviewList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReview(Review review) throws Exception {
		reviewDao.updateReview(review);
	}

}
