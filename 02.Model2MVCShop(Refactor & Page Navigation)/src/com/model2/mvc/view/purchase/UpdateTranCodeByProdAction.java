package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Purchase purchaseVO = new Purchase();
		Product productVO = new Product();
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		productVO.setProdNo(prodNo);
		purchaseVO.setPurchaseProd(productVO); 
		purchaseVO.setTranCode("2"); // πËº€¡ﬂ
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchaseVO);
		
		return "forward:/listProduct.do?menu=manage";
	}

}
