package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {

	public PurchaseDAO() {
	}
	
	// 구매를 위한 DBMS 수행
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
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
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송 
		String sql = "select * from transaction where buyer_id = ? ";
		sql +=" order by tran_no";
		
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1, buyerId);
		
		ResultSet rs = stmt.executeQuery();
		
		rs.last(); // 커서를 제일 뒤로
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>(); // key, value 형태로 저장
		map.put("count", new Integer(total)); // Object 타입으로 들어가야 하니까 Wrapper class
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO vo = new PurchaseVO();
				vo.setTranNo(rs.getInt("tran_no"));
				vo.setPurchaseProd(pdService.getProduct(Integer.parseInt(rs.getString("prod_no"))));
				vo.setBuyer(usService.getUser(rs.getString("buyer_id")));
				vo.setPaymentOption(rs.getString("payment_option"));
				vo.setReceiverName(rs.getString("receiver_name"));
				vo.setReceiverPhone(rs.getString("receiver_phone"));
				vo.setDivyAddr(rs.getString("demailaddr"));
				vo.setDivyRequest(rs.getString("dlvy_request"));
				if (rs.getString("tran_status_code").trim().equals("1")) {
					vo.setTranCode("구매완료");
				} else if (rs.getString("tran_status_code").trim().equals("2")) {
					vo.setTranCode("배송중");
				} else if (rs.getString("tran_status_code").trim().equals("3")) {
					vo.setTranCode("배송완료");
				}
				vo.setOrderDate(rs.getDate("order_data"));
				vo.setDivyDate(rs.getString("dlvy_date"));
				
				list.add(vo); // ArrayList에 ProductVO 한 줄씩 뽑은거 담음
				if (!rs.next()) { // 찾을 결과가 없으면 
					break; // for문 빠져나옴
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	
	// 구매정보 상세 조회를 위한 DBMS 수행
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
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
		
		PurchaseVO purchaseVO = null;
		while (rs.next()) { // 결과가 있으면 
			purchaseVO = new PurchaseVO(); // 인스턴스 생성
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
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		
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
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		int prodNo = purchaseVO.getPurchaseProd().getProdNo();
		System.out.println("update trancode dao prodno : " + prodNo);
		System.out.println("update trancode dao tranno : " + purchaseVO.getTranNo());
		
		String sql = "update transaction set tran_status_code = ? ";
		if (prodNo != 0) { // prodNo 로 검색하는 거면 
			sql += "where prod_no = ?";
		} else { // tranNo 로 검색하는 거면 
			sql += "where tran_no = ?";
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getTranCode());
		
		if (prodNo != 0) { // prodNo 로 검색하는 거면 
			stmt.setInt(2, prodNo);
		} else { // tranNo 로 검색하는 거면 
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
}
