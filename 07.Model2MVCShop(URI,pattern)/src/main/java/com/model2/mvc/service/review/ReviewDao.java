package com.model2.mvc.service.review;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;

//==> 리뷰관리에서 CRUD 추상화/캡슐화한 DAO Interface Definition
public interface ReviewDao {
	
	public void insertReview(Review review) throws Exception;
	
	public Review findReview(int no) throws Exception;
	
	public void updateReview(Review review) throws Exception;
	
	// SELECT LIST
	public List <Review> getReviewList(Search search) throws Exception;
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception;
	
}
