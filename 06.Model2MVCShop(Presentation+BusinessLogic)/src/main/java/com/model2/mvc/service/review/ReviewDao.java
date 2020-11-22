package com.model2.mvc.service.review;

import com.model2.mvc.service.domain.Review;

//==> ����������� CRUD �߻�ȭ/ĸ��ȭ�� DAO Interface Definition
public interface ReviewDao {
	
	public void insertReview(Review review) throws Exception;
	
	public Review findReview(int no) throws Exception;
	
}
