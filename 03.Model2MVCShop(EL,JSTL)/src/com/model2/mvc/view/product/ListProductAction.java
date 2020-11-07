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
		
		int currentPage = 1; // jsp�� ��ġ�� �ʰ� .do�� ���� ����� ���ϱ� ù �������� 1�̶�� ���� 
		
		if (request.getParameter("currentPage") != null) { // jsp�� ��ġ�� �ٽ� �°Ÿ� ���� �������� ���� ��
			currentPage = Integer.parseInt(request.getParameter("currentPage")); // �޾ƿ�
		}
		
		String menu = request.getParameter("menu"); // manage & search ������ ����
		System.out.println("menu : " + menu); // �����
		
		search.setCurrentPage(currentPage); // CurrentPage : ���� ������
		search.setSearchCondition(request.getParameter("searchCondition")); // SearchCondition : ��ǰ��, ����, ��ǰ��ȣ
		search.setSearchKeyword(request.getParameter("searchKeyword")); // SearchKeyword : �˻��� Ű���� 
		
		// web.xml meta-data �� ���� �� �޾ƿ�. pageSize : �� �������� ��� �, pageUnit : �� �������� �������� 
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize")); // 3���� web.xml���� ����
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 5�� web.xml���� ����
		search.setPageSize(pageSize); 
		
		// Business logic ����
		ProductService service = new ProductServiceImpl();
		Map <String, Object> map = service.getProductList(search);
		// map.put("totalCount", new Integer(totalCount));
		// map.put("list", list)); �̷��� �ΰ��� ��� ���� ! 
		// ���̾ ��Ŀ�ø� ����� �ٲٱ� ���� HashMap ������� �ʰ� Map ����Ͽ� �������̽� ��� ���α׷���
		
		Page resultPage	= // ������ ������ ���� ���� �߻�ȭ & ĸ��ȭ �� Page Ŭ���� �̿� 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		// request �� ��Ƽ� Model �� View ����
		request.setAttribute("menu", menu);
		
		request.setAttribute("list", map.get("list")); // ������ Ŭ������ �� ��Ÿ���� ��ǰ ������ ������� ��
		request.setAttribute("resultPage", resultPage); // ȭ����� ������ ������ �� ������� 
		request.setAttribute("search", search); // �˻� ������ ������� 
		
		return "forward:/product/listProduct.jsp"; // listProduct.jsp�� forward
	}

}
