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

// ���� ��û
public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(); // �α��� �Ǿ��ִ� ���� ���� ã�� ���� 
		User user = (User) session.getAttribute("user");
		
		// ������ ��ǰ ���� ã�� ����
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		
		System.out.println("AddPurchaseAction ���� ��� productVO : " + product);
		System.out.println("AddPurchaseAction ���� ��� userVO : " + user);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setTranCode("1");
		purchase.setDivyDate(request.getParameter("receiverDate"));
		
		PurchaseService pcService = new PurchaseServiceImpl();
		pcService.addPurchase(purchase);
		
		System.out.println("AddPurchaseAction ���� ��� purchase : " + purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
                                                            