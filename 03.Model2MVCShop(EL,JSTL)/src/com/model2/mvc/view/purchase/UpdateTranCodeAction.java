package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

// ���� �����ڵ� ���� ��û 
public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Purchase purchaseVO = new Purchase();
		Product productVO = new Product();
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		String tranCode = request.getParameter("tranCode");
		purchaseVO.setTranNo(tranNo);
		purchaseVO.setTranCode(tranCode);
		purchaseVO.setPurchaseProd(productVO); // DAO �ȿ��� Null�� �� ���� ���� 
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		
		return "forward:/listPurchase.do?";
	}

}
