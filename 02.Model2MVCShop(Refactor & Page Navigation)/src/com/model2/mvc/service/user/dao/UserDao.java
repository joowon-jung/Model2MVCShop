package com.model2.mvc.service.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;


public class UserDao {
	
	///Field
	
	///Constructor
	public UserDao() {
	}

	///Method
	public void insertUser(User user) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = 	"INSERT "+ 
								"INTO USERS "+ 
								"VALUES (?,?,?,'user',?,?,?,?,SYSDATE)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, user.getUserId());
		pStmt.setString(2, user.getUserName());
		pStmt.setString(3, user.getPassword());
		pStmt.setString(4, user.getSsn());
		pStmt.setString(5, user.getPhone());
		pStmt.setString(6, user.getAddr());
		pStmt.setString(7, user.getEmail());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
	}

	public User findUser(String userId) throws Exception {
		
		Connection con = DBUtil.getConnection();
			
		String sql = 	"SELECT "+
								"user_id ,  user_name ,  password , role , cell_phone ,  addr ,  email , reg_date " + 
								"FROM users WHERE user_id = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, userId);

		ResultSet rs = pStmt.executeQuery();

		User user = null;

		while (rs.next()) {
			user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setPassword(rs.getString("password"));
			user.setRole(rs.getString("role"));
			user.setPhone(rs.getString("cell_phone"));
			user.setAddr(rs.getString("addr"));
			user.setEmail(rs.getString("email"));
			user.setRegDate(rs.getDate("reg_date"));
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		return user;
	}

	public Map<String , Object> getUserList(Search search) throws Exception {
		
		// ��ü���� HashMap �������� �ʰ� Map ���� �������̽� ��� ���α׷��� 
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query ����
		String sql = "SELECT user_id ,  user_name , email  FROM  users ";
		
		if (search.getSearchCondition() != null) { // �˻��ߴ� �� ������ �˻� �ߴ� ���ǿ� ���缭 ������ �ۼ� 
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) { // ���̵�� �˻��� 
				sql += " where USER_ID like '%' || '" + search.getSearchKeyword()
				+ "' || '%'";
			} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) { // ȸ�������� �˻��� 
				sql += " where USER_NAME like '%' || '" + search.getSearchKeyword()
				+ "' || '%'";
			}
		}
		sql += " ORDER BY user_id"; // ���� ���̵� �������� ���� 
		
		System.out.println("UserDAO::Original SQL :: " + sql); // ���� sql �� Ȯ���ϱ� ���� �����
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql); // �� sql�� ���� ����� �������� �޼ҵ� Ȱ���Ͽ� ���� 
		System.out.println("UserDAO :: totalCount  :: " + totalCount); // ����� 
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search); // ���� �������� ���� ������ select �ϱ� ���� sql�� �ٽ� ����
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<User> list = new ArrayList<User>(); // List�� �޾Ƽ� �������̽� ���α׷��� 
		
		while(rs.next()){ // ����� ������ ArrayList�� User���� ���پ� �����Ѵٰ� �����ϱ�
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setEmail(rs.getString("email"));
			list.add(user);
		}
		
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount)); // �˻��� ���� ������ �������
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list); // ���� �������� ���� ���� ������ ã���� ���� map �� ��Ƽ� ����!

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	public void updateUser(User vo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = 	"UPDATE users "+
								"SET user_name = ?, cell_phone = ? , addr = ? , email = ? "+ 
								"WHERE user_id = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, vo.getUserName());
		pStmt.setString(2, vo.getPhone());
		pStmt.setString(3, vo.getAddr());
		pStmt.setString(4, vo.getEmail());
		pStmt.setString(5, vo.getUserId());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception { // getUserList���� sql���� ���ڰ����� �ְ� ���� 
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		// select count(*)
		// from ( SELECT user_id ,  user_name , email  FROM  users  ORDER BY user_id ) countTable
		// �̷� ������ ��� ���������� ã�������� ī���� �ϰ� ����!
		
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){ // ����� ������ 
			totalCount = rs.getInt(1); // ù��° ��� ���� (������ ���� ���°Ŵϱ� ����� �ϳ���)
									   // int������ totalCount�� ������� �� 
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// [ �Խ��� currentPage Row ��  return ]
	// * ROWNUM Ȱ���Ͽ� ȿ������ query�� ������ ���� �޼ҵ�
	// �� �������� 3�پ��� �����ϱ� ȿ�������� 3�ٸ� ã�Բ� �ϱ� -> ���ݱ����� ó������ ������ �� ã�Ҿ���
	private String makeCurrentPageSql(String sql , Search search){ // getUserList���� sql���� search�� ���ڰ����� �ְ� ���� 
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
	// select * 
	// from 	( select inner_table.*, rownum as row_seq
	// 			from ( SELECT user_id ,  user_name , email  FROM  users  ORDER BY user_id ) inner_table
	//			where rownum <= 3, 6 )
	// where row_seq between 1 and 3, 4 and 6 �̷������� �� �� ! rownum �� ����ؼ� �� �������� 123 / 456 / 789 �� ã�Բ� ���ְ� ���� 
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}