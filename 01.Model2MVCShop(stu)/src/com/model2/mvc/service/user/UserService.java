package com.model2.mvc.service.user;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.user.vo.UserVO;

/*
 	< Business Logic Layer �� ���ȭ >
 	: Persistence layer (Dao) / service layer (Service, Serviceimpl) �� �����ؼ� ����
 	
 	* �̷��� ���ȭ ���� �ʰ� �ٷ� ~~~.Action���� �ٷ� Dao ����ؼ� �޾ƿ´ٸ�? 
 		: Dao ���� �޼ҵ尡 �ϳ��� �߰� �� �� ���� ~~~.Action�� �����ľ� �Ѵ�
 	* ���ȭ ���Ѽ� �������̽�(Service) ��� ���α׷����� �ϸ�?
 		: Dao ���� �޼ҵ尡 �ϳ��� �߰� �Ǵ��� => ~~~.Action������ UserService�� �޾ƿ��� �ֱ� ������
 		���� �ǵ帱�� ����! ���� ~~~.Action���� ����� �͵��� Serviceimpl ���� ���ְ� �ִ� 
 	
 */
public interface UserService {

	public void addUser(UserVO userVO) throws Exception;

	public UserVO loginUser(UserVO userVO) throws Exception;

	public UserVO getUser(String userId) throws Exception;

	public HashMap<String, Object> getUserList(SearchVO searchVO) throws Exception;

	public void updateUser(UserVO userVO) throws Exception;

	public boolean checkDuplication(String userId) throws Exception;

}