package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
	
/*		
 * 		[ ��ǰ ��� ��û ]
 * 		���� ��û URL : /addProduct.do
 * 		Action Class : AddProductAction
 */
public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductVO productVO = new ProductVO(); // �ν��Ͻ� ����
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		
		System.out.println("Ŭ���̾�Ʈ�� ��û�� ��ǰ ���� �� ��� : " + productVO);
		
		ProductService service = new ProductServiceImpl();
		
		service.addProduct(productVO);
		
		request.setAttribute("vo", productVO); // request �� ��Ƽ�
		
		return "forward:/product/readProduct.jsp"; 	// ������ �̵�
													// �߰���� ��ư - addProductView.jsp	�� �̵�
													// Ȯ�� ��ư - /listProduct.do?menu=manage URL�� �̵�
	}

}
