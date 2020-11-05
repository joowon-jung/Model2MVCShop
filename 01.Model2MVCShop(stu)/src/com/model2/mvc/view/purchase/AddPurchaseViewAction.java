package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

// 구매를 위한 화면 요청 
public class AddPurchaseViewAction extends Action {

	// getProduct.jsp 에서 구매 버튼 누르면 여기로 옴 
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		
		ProductService service = new ProductServiceImpl();
		ProductVO productVO = service.getProduct(prodNo); // 구매할 상품 번호에 해당하는 상품 찾아옴 
		
		request.setAttribute("productVO", productVO); // 담아서 forward
		
		return "forward:/purchase/addPurchaseView.jsp";
		
	}

}
