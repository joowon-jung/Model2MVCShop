package com.model2.mvc.service.review;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;

//==> ����������� ������ ���� �߻�ȭ/ĸ��ȭ�� Service  Interface Definition  
public interface ReviewService {

	// ���� �߰�
	public void addReview(Review review) throws Exception;
	
	// ���� ����
	public Review getReview(int no) throws Exception;
	
	// ���� ��ȸ�� 
	public void updateCount(int no) throws Exception;
	
	// ���� ����Ʈ
	public Map<String,Object> getReviewList(Search search) throws Exception;
	
	// ���� ���� 
	public void updateReview(Review review) throws Exception;

}
