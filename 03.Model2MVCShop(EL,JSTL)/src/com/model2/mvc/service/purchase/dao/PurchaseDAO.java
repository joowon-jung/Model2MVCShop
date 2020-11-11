package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {

	public PurchaseDAO() {
	}
	
	// 구매를 위한 DBMS 수행
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		String sql = "insert into transaction values "
				+ "(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?, "
				+ "?, to_char(sysdate, 'YYYYMMDD'), ?) ";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDivyDate());
		
		// 3. 결과 확인
		int confirm = stmt.executeUpdate();
		
		if (confirm == 1) { // 디버깅 위함 
			System.out.println("purchase insert 완료!");
			System.out.println(purchaseVO);
		} else {
			System.out.println("product insert 실패!");
		}
		
		con.close();
		
	}
	
	// 구매목록 보기를 위한 DBMS 수행
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		// 구체적인 HashMap 선언하지 않고 Map 으로 인터페이스 기반 프로그래밍 
		Map<String , Object>  map = new HashMap<String, Object>();
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송 
		String sql = "select t.*, NVL(r.review_no,0) "
				+ "from transaction t, review r "
				+ "where t.buyer_id = '"+buyerId+"' "
				+ "and t.tran_no = r.tran_no(+) "
				+" order by t.order_data desc";
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql); // 위 sql문 실행 결과가 몇줄인지 메소드 활용하여 담음 
		
		// ==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search); // 현재 페이지에 대한 정보만 select 하기 위해 sql문 다시 구성
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		List<Purchase> list = new ArrayList<Purchase>(); // List로 받아서 인터페이스 프로그래밍 
		
		while(rs.next()) {
			Purchase vo = new Purchase();
			vo.setTranNo(rs.getInt(1));
			vo.setPurchaseProd(pdService.getProduct(Integer.parseInt(rs.getString(2))));
			vo.setBuyer(usService.getUser(rs.getString(3)));
			vo.setPaymentOption(rs.getString(4));
			vo.setReceiverName(rs.getString(5));
			vo.setReceiverPhone(rs.getString(6));
			vo.setDivyAddr(rs.getString(7));
			vo.setDivyRequest(rs.getString(8));
			if (rs.getString(9).trim().equals("1")) {
				vo.setTranCode("구매완료");
			} else if (rs.getString(9).trim().equals("2")) {
				vo.setTranCode("배송중");
			} else if (rs.getString(9).trim().equals("3")) {
				vo.setTranCode("배송완료");
			}
			vo.setOrderDate(rs.getDate(10));
			vo.setDivyDate(rs.getString(11));
			vo.setReviewNo(rs.getInt(12));

			list.add(vo); // ArrayList에 ProductVO 한 줄씩 뽑은거 담음
		}

		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount)); // 검색한 유저 정보가 몇개인지랑
		// ==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list); // 현재 페이지에 대한 유저 정보만 찾은걸 같이 map 에 담아서 보냄!

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	// 구매정보 상세 조회를 위한 DBMS 수행
	public Purchase findPurchase(int tranNo) throws Exception {
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		String sql = "select * from transaction where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		// 3. 결과 확인 
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchaseVO = null;
		while (rs.next()) { // 결과가 있으면 
			purchaseVO = new Purchase(); // 인스턴스 생성
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setPurchaseProd(pdService.getProduct(Integer.parseInt(rs.getString("prod_no"))));
			purchaseVO.setBuyer(usService.getUser(rs.getString("buyer_id")));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			if (rs.getString("tran_status_code").trim().equals("1")) {
				purchaseVO.setTranCode("구매완료");
			} else if (rs.getString("tran_status_code").trim().equals("2")) {
				purchaseVO.setTranCode("배송중");
			} else if (rs.getString("tran_status_code").trim().equals("3")) {
				purchaseVO.setTranCode("배송완료");
			}
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
		}
		
		con.close();

		return purchaseVO;
	}
	
	// 구매정보 수정을 위한 DBMS 수행
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송 
		String sql = "update transaction set payment_option = ?, receiver_name = ?, receiver_phone = ?, demailaddr = ?, dlvy_request = ?, "
						+ "dlvy_date = cast(to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.FF') as date) " 
						+ "where tran_no = ? ";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		
		// 3. 결과 확인
		int confirm = stmt.executeUpdate();
		if (confirm == 1) { // 디버깅 위함
			System.out.println("purchase update 완료!");
		} else {
			System.out.println("purchase update 실패!");
		}
		
		con.close();
	}
	
	// 구매 상태 코드 수정을 위한 DBMS 수행
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		int prodNo = purchaseVO.getPurchaseProd().getProdNo();
		System.out.println("update trancode dao prodno : " + prodNo);
		System.out.println("update trancode dao tranno : " + purchaseVO.getTranNo());
		
		String sql = "update transaction set tran_status_code = ? ";
		if (prodNo != 0) { // prodNo 로 찾아서 업데이트 하는 거면 
			sql += "where prod_no = ?";
		} else { // tranNo 로 찾아서 업데이트 하는 거면 
			sql += "where tran_no = ?";
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getTranCode());
		
		if (prodNo != 0) { // prodNo 로 찾아서 업데이트 하는 거면 
			stmt.setInt(2, prodNo);
		} else { // tranNo 로 찾아서 업데이트 하는 거면 
			stmt.setInt(2, purchaseVO.getTranNo());
		}
		
		// 3. 결과 확인
		System.out.println(stmt.executeUpdate());
		int confirm = stmt.executeUpdate();
		if (confirm == 1) { // 디버깅 위함
			System.out.println("trancode update 완료!");
		} else {
			System.out.println("trancode update 실패!");
		}
		
		con.close();
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount) return
	private int getTotalCount(String sql) throws Exception { // getUserList에서 sql문을 인자값으로 주고 있음

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		// select count(*)
		// from ( SELECT user_id , user_name , email FROM users ORDER BY user_id )
		// countTable
		// 이런 식으로 몇개의 유저정보가 찾아지는지 카운팅 하고 있음!

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) { // 결과가 있으면
			totalCount = rs.getInt(1); // 첫번째 결과 값을 (어차피 갯수 세는거니까 결과가 하나임)
										// int형으로 totalCount에 넣으라는 뜻
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// [ 게시판 currentPage Row 만 return ]
	// * ROWNUM 활용하여 효율적인 query문 날리기 위한 메소드
	// 한 페이지에 3줄씩만 나오니까 효율적으로 3줄만 찾게끔 하기 -> 지금까지는 처음부터 끝까지 다 찾았었음
	private String makeCurrentPageSql(String sql, Search search) { // getUserList에서 sql문과 search를 인자값으로 주고 있음
		sql = "SELECT * " + "FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		// select *
		// from ( select inner_table.*, rownum as row_seq
		// from ( SELECT user_id , user_name , email FROM users ORDER BY user_id )
		// inner_table
		// where rownum <= 3, 6 )
		// where row_seq between 1 and 3, 4 and 6 이런식으로 될 것 ! rownum 을 사용해서 한 페이지에 123 /
		// 456 / 789 만 찾게끔 해주고 있음

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}
}
