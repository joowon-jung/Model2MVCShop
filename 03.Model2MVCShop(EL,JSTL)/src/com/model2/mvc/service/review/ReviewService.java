package com.model2.mvc.service.review;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;

public interface ReviewService {

	public void addReview(Review review) throws Exception;
	
	public Review getReview(int no) throws Exception;
	
	public Map<String,Object> getReviewList(Search searchVO, String writerId) throws Exception;
	
	public void updateReview(Review review) throws Exception;

}
