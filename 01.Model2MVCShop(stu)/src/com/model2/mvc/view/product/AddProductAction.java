package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
	
/*		
 * 		[ 상품 등록 요청 ]
 * 		유저 요청 URL : /addProduct.do
 * 		Action Class : AddProductAction
 */
public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductVO productVO = new ProductVO(); // 인스턴스 생성
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		
		System.out.println("클라이언트가 요청한 상품 정보 잘 담김 : " + productVO);
		
		ProductService service = new ProductServiceImpl();
		
		service.addProduct(productVO);
		
		request.setAttribute("vo", productVO); // request 에 담아서
		
		return "forward:/product/readProduct.jsp"; 	// 페이지 이동
													// 추가등록 버튼 - addProductView.jsp	로 이동
													// 확인 버튼 - /listProduct.do?menu=manage URL로 이동
	}

}
