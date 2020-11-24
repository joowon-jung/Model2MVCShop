package com.model2.mvc.service.review;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;

//==> ����������� CRUD �߻�ȭ/ĸ��ȭ�� DAO Interface Definition
public interface ReviewDao {
	
	public void insertReview(Review review) throws Exception;
	
	public Review findReview(int no) throws Exception;
	
	public void updateReview(Review review) throws Exception;
	
	// SELECT LIST
	public List <Review> getReviewList(Search search) throws Exception;
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception;
	
}
