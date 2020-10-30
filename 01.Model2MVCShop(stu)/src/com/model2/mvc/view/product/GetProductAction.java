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
		System.out.println("GetProductAction�� ��� menu : " + menu);
		
		ProductService service = new ProductServiceImpl();
		ProductVO vo = service.getProduct(Integer.parseInt(prodNo));
		
		// request �� ��Ƽ�
		request.setAttribute("vo", vo);
		System.out.println("��� vo : " + vo);
		
		request.setAttribute("menu", menu);
		
		
		if (request.getParameter("menu").equals("search")) { // ��ǰ �˻� ������ => ��ǰ �󼼺��� ������ => ���� �� ��ǰ�� ���
			
			Cookie[] cookies = request.getCookies();  // ���� ��Ű �޾ƿ� (���� ���̵� ����Ǿ��ִ� ��Ű �����ϱ� �ݵ�� �Ѱ��̻� ���� ��)
			
			int count = 0;
			
			for (int i = 0; i < cookies.length; i++) { // ��Ű �ϳ��� �޾ƿ��� ���� for��
				
				if (cookies[i].getName().equals("history")) { // ��Ű �̸��� history �ΰ� ������
					count++;
				} 
				
				if (count == 1) {
					// ������ ��Ű value�� , ���� �����ؼ� �߰�
					System.out.println("��Ű�� �����");
					System.out.println(cookies[i].getValue() + "," + prodNo);
					response.addCookie(new Cookie("history", cookies[i].getValue() + URLEncoder.encode("," + prodNo)));
					cookies[i].setMaxAge(0);
					break;
				} else {
					response.addCookie(new Cookie("history", prodNo));
				}
			}
			
		}
		
		if (menu != null && menu.equals("manage")) { // menu : manage �̸�
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu="+menu;
		}
		
		// menu : search �̸�
		return "forward:/product/getProduct.jsp"; // ������ �̵�
	}

}
