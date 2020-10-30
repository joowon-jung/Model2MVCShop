package com.model2.mvc.view.product;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String prodNo = request.getParameter("prodNo");
		String menu = request.getParameter("menu");
		System.out.println("GetProductAction에 담긴 menu : " + menu);
		
		ProductService service = new ProductServiceImpl();
		ProductVO vo = service.getProduct(Integer.parseInt(prodNo));
		
		// request 에 담아서
		request.setAttribute("vo", vo);
		System.out.println("담긴 vo : " + vo);
		
		request.setAttribute("menu", menu);
		
		
		if (request.getParameter("menu").equals("search")) { // 상품 검색 페이지 => 상품 상세보기 누르면 => 내가 본 상품에 담김
			
			Cookie[] cookies = request.getCookies();  // 현재 쿠키 받아옴 (세션 아이디 저장되어있는 쿠키 있으니까 반드시 한개이상 있을 것)
			
			int count = 0;
			
			for (int i = 0; i < cookies.length; i++) { // 쿠키 하나씩 받아오기 위한 for문
				
				if (cookies[i].getName().equals("history")) { // 쿠키 이름이 history 인게 있으면
					count++;
				} 
				
				if (count == 1) {
					// 기존의 쿠키 value에 , 으로 구분해서 추가
					System.out.println("쿠키에 담겼음");
					System.out.println(cookies[i].getValue() + "," + prodNo);
					response.addCookie(new Cookie("history", cookies[i].getValue() + URLEncoder.encode("," + prodNo)));
					cookies[i].setMaxAge(0);
					break;
				} else {
					response.addCookie(new Cookie("history", prodNo));
				}
			}
			
		}
		
		if (menu != null && menu.equals("manage")) { // menu : manage 이면
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu="+menu;
		}
		
		// menu : search 이면
		return "forward:/product/getProduct.jsp"; // 페이지 이동
	}

}
