package com.model2.mvc.service.review.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.review.ReviewService;
import com.model2.mvc.service.review.dao.ReviewDAO;

public class ReviewServiceImpl implements ReviewService {

	private ReviewDAO reviewDAO;
	
	public ReviewServiceImpl() {
		reviewDAO = new ReviewDAO();
	}
	
	@Override
	// ���� ����� ���� ����Ͻ� ���� 
	public void addReview(Review review) throws Exception {
		reviewDAO.insertReview(review);
	}

	@Override
	// ���� ��ȣ�� ���� ��ȣ�� ���� ã�ƿ��� ����Ͻ� ����
	public Review getReview(int no) throws Exception {
		return reviewDAO.findReview(no);
	}

	@Override
	public Map<String, Object> getReviewList(Search searchVO, String writerId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReview(Review review) throws Exception {
		// TODO Auto-generated method stub

	}

}
