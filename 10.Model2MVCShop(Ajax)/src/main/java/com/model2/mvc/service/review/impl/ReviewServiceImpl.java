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

//==> ������� ���� ����
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
	// ���� ����� ���� ����Ͻ� ���� 
	public void addReview(Review review) throws Exception {
		reviewDao.insertReview(review);
	}

	@Override
	// ���� ��ȣ�� ���� ��ȣ�� ���� ã�ƿ��� ����Ͻ� ����
	public Review getReview(int no) throws Exception {
		return reviewDao.findReview(no);
	}
	
	@Override
	// ���� ��ȸ�� ���� ����Ͻ� ���� 
	public void updateCount(int no) throws Exception {
		reviewDao.updateCount(no); // ��ȸ�� ���� 
	}

	@Override
	public Map<String, Object> getReviewList(Search search) throws Exception {
		List <Review> list = reviewDao.getReviewList(search);
		
		// ��ǰ ��ü�� �ƿ� purchaseprod�� �������� List �ϳ� �� ������� 
		List <Review> reviewListIncludeProd = new ArrayList <Review>();
		
		for (int i = 0; i < list.size(); i++) {
			Review review = (Review)list.get(i);
			
			review.setReviewProd(productDao.findProduct(review.getReviewProd().getProdNo()));
			reviewListIncludeProd.add(review);
		}
		
		int totalCount = reviewDao.getTotalCount(search);
		
		Map<String, Object> map = new Hashtable<String, Object>();
		map.put("list", reviewListIncludeProd);
		map.put("totalCount", new Integer(totalCount)); // Object Ÿ�Ը� ������ �����ϱ� Wrapper Class ��� 
		
		return map;
	}

	@Override
	public void updateReview(Review review) throws Exception {
		reviewDao.updateReview(review);
	}

}
