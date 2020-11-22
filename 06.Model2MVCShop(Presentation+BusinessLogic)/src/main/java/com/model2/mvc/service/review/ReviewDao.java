package com.model2.mvc.service.review;

import com.model2.mvc.service.domain.Review;

//==> ¸®ºä°ü¸®¿¡¼­ CRUD Ãß»óÈ­/Ä¸½¶È­ÇÑ DAO Interface Definition
public interface ReviewDao {
	
	public void insertReview(Review review) throws Exception;
	
	public Review findReview(int no) throws Exception;
	
}
