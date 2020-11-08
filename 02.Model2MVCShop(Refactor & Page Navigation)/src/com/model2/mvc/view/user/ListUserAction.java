package com.model2.mvc.view.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class ListUserAction extends Action {

	@Override
	public String execute(HttpServletRequest request,	HttpServletResponse response) throws Exception {

		Search search=new Search();
		
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize")); // 3
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 5
		search.setPageSize(pageSize);
		
		// Business logic 수행
		UserService userService=new UserServiceImpl();
		Map<String , Object> map=userService.getUserList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); 이렇게 두개가 담겨 있음 ! 
		// 레이어간 디커플링 관계로 바꾸기 위해 HashMap 사용하지 않고 Map 사용하여 인터페이스 기반 프로그래밍
		
		Page resultPage	= // 페이지 나누는 것을 따로 추상화 & 캡슐화 한 Page 클래스 이용 
					new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		// Model 과 View 연결
		request.setAttribute("list", map.get("list")); // 페이지 클릭했을 때 나타나는 유저 정보가 담겨있을 것
		request.setAttribute("resultPage", resultPage); // 화면상의 페이지 정보가 다 담겨있음 
		request.setAttribute("search", search); // 검색 정보가 담겨있음 
		
		return "forward:/user/listUser.jsp";
	}
}