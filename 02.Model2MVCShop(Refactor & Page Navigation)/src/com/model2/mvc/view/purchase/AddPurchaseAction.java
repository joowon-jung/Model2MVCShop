package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

// 구매 요청
public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(); // 로그인 되어있는 유저 정보 찾기 위함 
		User userVO = (User) session.getAttribute("user");
		
		// 구매한 상품 정보 찾기 위함
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService service = new ProductServiceImpl();
		Product productVO = service.getProduct(prodNo);
		
		System.out.println("AddPurchaseAction 에서 담긴 productVO : " + productVO);
		System.out.println("AddPurchaseAction 에서 담긴 userVO : " + userVO);
		
		Purchase purchaseVO = new Purchase();
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setTranCode("1");
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		
		PurchaseService pcService = new PurchaseServiceImpl();
		pcService.addPurchase(purchaseVO);
		
		System.out.println("AddPurchaseAction 에서 담긴 purchaseVO : " + purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
                                                            