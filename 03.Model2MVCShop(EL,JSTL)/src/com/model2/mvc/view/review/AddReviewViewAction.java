package com.model2.mvc.view.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddReviewViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo); // 리뷰 작성할 상품 번호에 해당하는 상품 찾아옴 
		
		PurchaseService psService = new PurchaseServiceImpl();
		Purchase purchase = psService.getPurchase(tranNo);
		
		request.setAttribute("product", product); // 담아서 forward
		request.setAttribute("purchase", purchase); // 담아서 forward
		
		return "forward:/review/addReviewView.jsp";
		
	}

}
