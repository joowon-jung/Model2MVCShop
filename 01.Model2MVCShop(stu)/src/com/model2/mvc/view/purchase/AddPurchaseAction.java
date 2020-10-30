package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

// ���� ��û
public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		ProductVO productVO =(ProductVO)session.getAttribute("productVO");
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		System.out.println("AddPurchaseAction ���� �� ���? : " + productVO);
		System.out.println("AddPurchaseAction ���� �� ���? : " + userVO);
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setBuyer(userVO);
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		// ���� ����
		
		
		return "forward:/purchase/";
	}

}
                                                            