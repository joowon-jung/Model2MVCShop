package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddUserAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserVO userVO = new UserVO();
		userVO.setUserId(request.getParameter("userId"));
		userVO.setPassword(request.getParameter("password"));
		userVO.setUserName(request.getParameter("userName"));
		userVO.setSsn(request.getParameter("ssn"));

		userVO.setAddr(request.getParameter("addr"));
		userVO.setPhone(request.getParameter("phone"));
		userVO.setEmail(request.getParameter("email"));

		System.out.println(userVO); // 디버깅 위함 

		UserService service = new UserServiceImpl(); // 왜 UserServiceImpl() 로 인스턴스 생성? 
													 // UserService 의 추상메소드들을 Impl에서 구현하고 있기 때문에
		service.addUser(userVO); // addUser 에서 하는 일 : userDAO.insertUser(userVO);

		return "redirect:/user/loginView.jsp"; // URI 정보를 String으로 리턴하고 있음 
	}
}