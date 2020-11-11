package com.model2.mvc.view.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.review.ReviewService;
import com.model2.mvc.service.review.impl.ReviewServiceImpl;

public class AddReviewAction extends Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(); // �α��� �Ǿ��ִ� ���� ���� ã�� ���� 
		User user = (User) session.getAttribute("user");
		
		// ���� �ۼ��� ��ǰ ���� ã�� ����
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		
		// ���� �ۼ��� �ֹ� ���� ã�� ����
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		PurchaseService psService = new PurchaseServiceImpl();
		Purchase purchase = psService.getPurchase(tranNo);
		
		Review review = new Review();
		review.setReviewProd(product);
		review.setWriter(user);
		review.setReviewPurchase(purchase);
		review.setTitle(request.getParameter("reviewTitle"));
		review.setContents(request.getParameter("reviewContents"));
		
		System.out.println("AddReviewAction ���� ��� review : " + review);
		
		ReviewService rvService = new ReviewServiceImpl();
		rvService.addReview(review);
		
		request.setAttribute("review", review);
		request.setAttribute("purchase", purchase);
		request.setAttribute("product", product);
		
		return "forward:/listPurchase.do?";
	}

}
