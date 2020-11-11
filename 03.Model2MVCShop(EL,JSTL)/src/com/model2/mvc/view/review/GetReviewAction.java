package com.model2.mvc.view.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.review.ReviewService;
import com.model2.mvc.service.review.impl.ReviewServiceImpl;

public class GetReviewAction extends Action {

	// 리뷰 가져오기 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
	
		ReviewService service = new ReviewServiceImpl();
		Review review = service.getReview(tranNo);
		
		request.setAttribute("review", review);
				
		return "forward:/review/getReview.jsp";
	}

}
