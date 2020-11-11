package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

// ���Ÿ� ���� ȭ�� ��û 
public class AddPurchaseViewAction extends Action {

	// getProduct.jsp ���� ���� ��ư ������ ����� �� 
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo); // ������ ��ǰ ��ȣ�� �ش��ϴ� ��ǰ ã�ƿ� 
		
		request.setAttribute("product", product); // ��Ƽ� forward
		
		return "forward:/purchase/addPurchaseView.jsp";
		
	}

}