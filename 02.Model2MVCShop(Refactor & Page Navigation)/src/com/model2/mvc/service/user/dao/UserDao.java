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
		
		// 구체적인 HashMap 선언하지 않고 Map 으로 인터페이스 기반 프로그래밍 
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성
		String sql = "SELECT user_id ,  user_name , email  FROM  users ";
		
		if (search.getSearchCondition() != null) { // 검색했던 게 있으면 검색 했던 조건에 맞춰서 쿼리문 작성 
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) { // 아이디로 검색시 
				sql += " where USER_ID like '%' || '" + search.getSearchKeyword()
				+ "' || '%'";
			} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) { // 회원명으로 검색시 
				sql += " where USER_NAME like '%' || '" + search.getSearchKeyword()
				+ "' || '%'";
			}
		}
		sql += " ORDER BY user_id"; // 유저 아이디를 기준으로 정렬 
		
		System.out.println("UserDAO::Original SQL :: " + sql); // 현재 sql 문 확인하기 위해 디버깅
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql); // 위 sql문 실행 결과가 몇줄인지 메소드 활용하여 담음 
		System.out.println("UserDAO :: totalCount  :: " + totalCount); // 디버깅 
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search); // 현재 페이지에 대한 정보만 select 하기 위해 sql문 다시 구성
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<User> list = new ArrayList<User>(); // List로 받아서 인터페이스 프로그래밍 
		
		while(rs.next()){ // 결과가 있으면 ArrayList에 User정보 한줄씩 저장한다고 생각하기
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setUserName(rs.getString("user_name"));
			user.setEmail(rs.getString("email"));
			list.add(user);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount)); // 검색한 유저 정보가 몇개인지랑
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list); // 현재 페이지에 대한 유저 정보만 찾은걸 같이 map 에 담아서 보냄!

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
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception { // getUserList에서 sql문을 인자값으로 주고 있음 
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		// select count(*)
		// from ( SELECT user_id ,  user_name , email  FROM  users  ORDER BY user_id ) countTable
		// 이런 식으로 몇개의 유저정보가 찾아지는지 카운팅 하고 있음!
		
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){ // 결과가 있으면 
			totalCount = rs.getInt(1); // 첫번째 결과 값을 (어차피 갯수 세는거니까 결과가 하나임)
									   // int형으로 totalCount에 넣으라는 뜻 
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// [ 게시판 currentPage Row 만  return ]
	// * ROWNUM 활용하여 효율적인 query문 날리기 위한 메소드
	// 한 페이지에 3줄씩만 나오니까 효율적으로 3줄만 찾게끔 하기 -> 지금까지는 처음부터 끝까지 다 찾았었음
	private String makeCurrentPageSql(String sql , Search search){ // getUserList에서 sql문과 search를 인자값으로 주고 있음 
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
	// select * 
	// from 	( select inner_table.*, rownum as row_seq
	// 			from ( SELECT user_id ,  user_name , email  FROM  users  ORDER BY user_id ) inner_table
	//			where rownum <= 3, 6 )
	// where row_seq between 1 and 3, 4 and 6 이런식으로 될 것 ! rownum 을 사용해서 한 페이지에 123 / 456 / 789 만 찾게끔 해주고 있음 
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}