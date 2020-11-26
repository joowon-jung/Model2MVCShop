package com.model2.mvc.service.review;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;

//==> 리뷰관리에서 서비스할 내용 추상화/캡슐화한 Service  Interface Definition  
public interface ReviewService {

	// 리뷰 추가
	public void addReview(Review review) throws Exception;
	
	// 리뷰 보기
	public Review getReview(int no) throws Exception;
	
	// 리뷰 조회수 
	public void updateCount(int no) throws Exception;
	
	// 리뷰 리스트
	public Map<String,Object> getReviewList(Search search) throws Exception;
	
	// 리뷰 수정 
	public void updateReview(Review review) throws Exception;

}
