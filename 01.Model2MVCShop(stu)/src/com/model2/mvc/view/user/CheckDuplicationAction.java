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
		boolean result = service.checkDuplication(userId); // �ߺ�Ȯ�� ��� 

		// �ߺ�Ȯ�� ������� request Scope�� ���� 
		request.setAttribute("result", new Boolean(result)); // �ߺ�Ȯ�� ��� ���� 
		request.setAttribute("userId", userId); // ���̵� ���� 

		return "forward:/user/checkDuplication.jsp";
	}
}