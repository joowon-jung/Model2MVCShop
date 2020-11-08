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

// ���Ÿ�� ��û 
public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(); // �α��� �Ǿ��ִ� ���� ���� ã�� ���� 
		User user = (User) session.getAttribute("user");
		
		Search search = new Search();
		
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);

		// web.xml meta-data �� ���� ��� ����
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize")); // 3
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 5
		search.setPageSize(pageSize);
		
		// Business logic ����
		PurchaseService service = new PurchaseServiceImpl();
		Map <String, Object> map = service.getPurchaseList(search, user.getUserId());

		Page resultPage	= // ������ ������ ���� ���� �߻�ȭ & ĸ��ȭ �� Page Ŭ���� �̿� 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
	
		// request �� ��Ƽ� Model �� View ����
		request.setAttribute("list", map.get("list")); // ������ Ŭ������ �� ��Ÿ���� purchase ������ ������� ��
		request.setAttribute("resultPage", resultPage); // ȭ����� ������ ������ �� ������� 
		request.setAttribute("search", search); // �˻� ������ ������� 
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
