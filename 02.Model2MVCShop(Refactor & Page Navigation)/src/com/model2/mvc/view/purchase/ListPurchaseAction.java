package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

// 구매목록 요청 
public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(); // 로그인 되어있는 유저 정보 찾기 위함 
		User user = (User) session.getAttribute("user");
		
		Search search = new Search();
		
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);

		// web.xml meta-data 로 부터 상수 추출
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize")); // 3
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 5
		search.setPageSize(pageSize);
		
		// Business logic 수행
		PurchaseService service = new PurchaseServiceImpl();
		Map <String, Object> map = service.getPurchaseList(search, user.getUserId());

		Page resultPage	= // 페이지 나누는 것을 따로 추상화 & 캡슐화 한 Page 클래스 이용 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
	
		// request 에 담아서 Model 과 View 연결
		request.setAttribute("list", map.get("list")); // 페이지 클릭했을 때 나타나는 purchase 정보가 담겨있을 것
		request.setAttribute("resultPage", resultPage); // 화면상의 페이지 정보가 다 담겨있음 
		request.setAttribute("search", search); // 검색 정보가 담겨있음 
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
