package com.model2.mvc.service.user;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.user.vo.UserVO;

/*
 	< Business Logic Layer 의 모듈화 >
 	: Persistence layer (Dao) / service layer (Service, Serviceimpl) 를 구별해서 개발
 	
 	* 이렇게 모듈화 하지 않고 바로 ~~~.Action에서 바로 Dao 사용해서 받아온다면? 
 		: Dao 안의 메소드가 하나씩 추가 될 때 마다 ~~~.Action을 뜯어고쳐야 한다
 	* 모듈화 시켜서 인터페이스(Service) 기반 프로그래밍을 하면?
 		: Dao 안의 메소드가 하나씩 추가 되더라도 => ~~~.Action에서는 UserService로 받아오고 있기 때문에
 		딱히 건드릴게 없음! 원래 ~~~.Action에서 해줬던 것들을 Serviceimpl 에서 해주고 있다 
 	
 */
public interface UserService {

	public void addUser(UserVO userVO) throws Exception;

	public UserVO loginUser(UserVO userVO) throws Exception;

	public UserVO getUser(String userId) throws Exception;

	public HashMap<String, Object> getUserList(SearchVO searchVO) throws Exception;

	public void updateUser(UserVO userVO) throws Exception;

	public boolean checkDuplication(String userId) throws Exception;

}