package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Search search = new Search();
		
		int currentPage = 1; // jsp를 거치지 않고 .do를 통해 여기로 오니까 첫 페이지를 1이라고 지정 
		
		if (request.getParameter("currentPage") != null) { // jsp를 거치고 다시 온거면 현재 페이지가 있을 것
			currentPage = Integer.parseInt(request.getParameter("currentPage")); // 받아옴
		}
		
		String menu = request.getParameter("menu"); // manage & search 나누기 위함
		System.out.println("menu : " + menu); // 디버깅
		
		search.setCurrentPage(currentPage); // CurrentPage : 현재 페이지
		search.setSearchCondition(request.getParameter("searchCondition")); // SearchCondition : 상품명, 가격, 상품번호
		search.setSearchKeyword(request.getParameter("searchKeyword")); // SearchKeyword : 검색한 키워드 
		
		// web.xml meta-data 로 부터 값 받아옴. pageSize : 한 페이지에 목록 몇개, pageUnit : 몇 페이지씩 나눌건지 
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize")); // 3으로 web.xml에서 지정
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 5로 web.xml에서 지정
		search.setPageSize(pageSize); 
		
		// Business logic 수행
		ProductService service = new ProductServiceImpl();
		Map <String, Object> map = service.getProductList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); 이렇게 두개가 담겨 있음 ! 
		// 레이어간 디커플링 관계로 바꾸기 위해 HashMap 사용하지 않고 Map 사용하여 인터페이스 기반 프로그래밍
		
		Page resultPage	= // 페이지 나누는 것을 따로 추상화 & 캡슐화 한 Page 클래스 이용 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		// request 에 담아서 Model 과 View 연결
		request.setAttribute("menu", menu);
		
		request.setAttribute("list", map.get("list")); // 페이지 클릭했을 때 나타나는 제품 정보가 담겨있을 것
		request.setAttribute("resultPage", resultPage); // 화면상의 페이지 정보가 다 담겨있음 
		request.setAttribute("search", search); // 검색 정보가 담겨있음 
		
		return "forward:/product/listProduct.jsp"; // listProduct.jsp로 forward
	}

}
