package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class CheckDuplicationAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");

		UserService service = new UserServiceImpl();
		boolean result = service.checkDuplication(userId); // 중복확인 결과 

		// 중복확인 결과값을 request Scope에 담음 
		request.setAttribute("result", new Boolean(result)); // 중복확인 결과 담음 
		request.setAttribute("userId", userId); // 아이디 담음 

		return "forward:/user/checkDuplication.jsp";
	}
}