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
		String resources=getServletConfig().getInitParameter("resources"); //meta-data 가져옴
		mapper=RequestMapping.getInstance(resources); //properties 파일을 파싱함 
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
														throws ServletException, IOException {
		
		System.out.println("[ service 메소드 시작 ]"); // 디버깅
		
		
		String url = request.getRequestURI(); // ex) 프로젝트 안의 getRequestURI : '/getUser.jsp'
		String contextPath = request.getContextPath(); // 프로젝트 안의 Contextpath : '/'
		String path = url.substring(contextPath.length()); // 파싱
		System.out.println("path : "  + path); // 주소 가져옴 => 어떤 Action 클래스로 넘어가지는지에 이용됨
		
		try{ // (?)
			Action action = mapper.getAction(path);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response); // ~~~.Action 의 return 결과가 여기에 담김
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			if(resultPage.startsWith("forward:")) // forward: 면
				HttpUtil.forward(request, response, result); // forward 함
			else								  // redirect: 면
				HttpUtil.redirect(response, result); // send.redirect 함
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("[ service 메소드 끝 ]"); // 디버깅
	}
}