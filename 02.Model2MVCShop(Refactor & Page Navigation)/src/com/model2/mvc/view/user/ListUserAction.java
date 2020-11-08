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
		
		// web.xml  meta-data �� ���� ��� ���� 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize")); // 3
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 5
		search.setPageSize(pageSize);
		
		// Business logic ����
		UserService userService=new UserServiceImpl();
		Map<String , Object> map=userService.getUserList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); �̷��� �ΰ��� ��� ���� ! 
		// ���̾ ��Ŀ�ø� ����� �ٲٱ� ���� HashMap ������� �ʰ� Map ����Ͽ� �������̽� ��� ���α׷���
		
		Page resultPage	= // ������ ������ ���� ���� �߻�ȭ & ĸ��ȭ �� Page Ŭ���� �̿� 
					new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::"+resultPage);
		
		// Model �� View ����
		request.setAttribute("list", map.get("list")); // ������ Ŭ������ �� ��Ÿ���� ���� ������ ������� ��
		request.setAttribute("resultPage", resultPage); // ȭ����� ������ ������ �� ������� 
		request.setAttribute("search", search); // �˻� ������ ������� 
		
		return "forward:/user/listUser.jsp";
	}
}