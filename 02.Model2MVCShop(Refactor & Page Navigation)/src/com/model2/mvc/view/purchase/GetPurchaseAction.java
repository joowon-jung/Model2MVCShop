package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

// 구매 상세정보 요청 
public class GetPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("GetPurchaseAction 까지 넘어온 purchaseVO : " + tranNo);
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase vo = service.getPurchase(tranNo); // 구매번호를 기반으로 구매 정보 상세 조회

		System.out.println("GetPurchaseAction 까지 넘어온 purchaseVO : " + vo);
		request.setAttribute("vo", vo);
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
