package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

// ���� ��û
public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(); // �α��� �Ǿ��ִ� ���� ���� ã�� ���� 
		UserVO userVO = (UserVO) session.getAttribute("user");
		
		// ������ ��ǰ ���� ã�� ����
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductService service = new ProductServiceImpl();
		ProductVO productVO = service.getProduct(prodNo);
		
		System.out.println("AddPurchaseAction ���� ��� productVO : " + productVO);
		System.out.println("AddPurchaseAction ���� ��� userVO : " + userVO);
		
		PurchaseVO purchaseVO = new PurchaseVO();
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
		
		System.out.println("AddPurchaseAction ���� ��� purchaseVO : " + purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
                                                            