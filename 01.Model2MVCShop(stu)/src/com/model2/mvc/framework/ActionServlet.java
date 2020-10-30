package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		// 
		String resources=getServletConfig().getInitParameter("resources"); //meta-data ������
		mapper=RequestMapping.getInstance(resources); //properties ������ �Ľ��� 
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
														throws ServletException, IOException {
		
		System.out.println("[ service �޼ҵ� ���� ]"); // �����
		
		
		String url = request.getRequestURI(); // ex) ������Ʈ ���� getRequestURI : '/getUser.jsp'
		String contextPath = request.getContextPath(); // ������Ʈ ���� Contextpath : '/'
		String path = url.substring(contextPath.length()); // �Ľ�
		System.out.println("path : "  + path); // �ּ� ������ => � Action Ŭ������ �Ѿ�������� �̿��
		
		try{ // (?)
			Action action = mapper.getAction(path);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response); // ~~~.Action �� return ����� ���⿡ ���
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			if(resultPage.startsWith("forward:")) // forward: ��
				HttpUtil.forward(request, response, result); // forward ��
			else								  // redirect: ��
				HttpUtil.redirect(response, result); // send.redirect ��
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("[ service �޼ҵ� �� ]"); // �����
	}
}