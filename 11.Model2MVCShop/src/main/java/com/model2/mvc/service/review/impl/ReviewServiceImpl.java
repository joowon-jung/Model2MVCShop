package com.model2.mvc.service.review.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.review.ReviewDao;
import com.model2.mvc.service.review.ReviewService;
import com.model2.mvc.service.user.UserDao;

//==> 리뷰관리 서비스 구현
@Service("reviewServiceImpl")
public class ReviewServiceImpl implements ReviewService {

	///Field
	@Autowired
	@Qualifier("reviewDaoImpl")
	private ReviewDao reviewDao;
	
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
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
	// 리뷰 조회수 증가 비즈니스 수행 
	public void updateCount(int no) throws Exception {
		reviewDao.updateCount(no); // 조회수 증가 
	}

	@Override
	public Map<String, Object> getReviewList(Search search) throws Exception {
		List <Review> list = reviewDao.getReviewList(search);
		
		// 상품 전체를 아예 purchaseprod에 넣으려고 List 하나 더 만들었음 
		List <Review> reviewListIncludeProd = new ArrayList <Review>();
		
		for (int i = 0; i < list.size(); i++) {
			Review review = (Review)list.get(i);
			
			review.setReviewProd(productDao.findProduct(review.getReviewProd().getProdNo()));
			reviewListIncludeProd.add(review);
		}
		
		int totalCount = reviewDao.getTotalCount(search);
		
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("list", reviewListIncludeProd);
		map.put("totalCount", new Integer(totalCount)); // Object 타입만 받을수 있으니까 Wrapper Class 사용 
		
		return map;
	}

	@Override
	public void updateReview(Review review) throws Exception {
		reviewDao.updateReview(review);
	}

}
