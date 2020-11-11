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
	
	// ���Ÿ� ���� DBMS ����
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
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
		
		// 3. ��� Ȯ��
		int confirm = stmt.executeUpdate();
		
		if (confirm == 1) { // ����� ���� 
			System.out.println("purchase insert �Ϸ�!");
			System.out.println(purchaseVO);
		} else {
			System.out.println("product insert ����!");
		}
		
		con.close();
		
	}
	
	// ���Ÿ�� ���⸦ ���� DBMS ����
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		// ��ü���� HashMap �������� �ʰ� Map ���� �������̽� ��� ���α׷��� 
		Map<String , Object>  map = new HashMap<String, Object>();
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ���� 
		String sql = "select t.*, NVL(r.review_no,0) "
				+ "from transaction t, review r "
				+ "where t.buyer_id = '"+buyerId+"' "
				+ "and t.tran_no = r.tran_no(+) "
				+" order by t.order_data desc";
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql); // �� sql�� ���� ����� �������� �޼ҵ� Ȱ���Ͽ� ���� 
		
		// ==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search); // ���� �������� ���� ������ select �ϱ� ���� sql�� �ٽ� ����
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		List<Purchase> list = new ArrayList<Purchase>(); // List�� �޾Ƽ� �������̽� ���α׷��� 
		
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
				vo.setTranCode("���ſϷ�");
			} else if (rs.getString(9).trim().equals("2")) {
				vo.setTranCode("�����");
			} else if (rs.getString(9).trim().equals("3")) {
				vo.setTranCode("��ۿϷ�");
			}
			vo.setOrderDate(rs.getDate(10));
			vo.setDivyDate(rs.getString(11));
			vo.setReviewNo(rs.getInt(12));

			list.add(vo); // ArrayList�� ProductVO �� �پ� ������ ����
		}

		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount)); // �˻��� ���� ������ �������
		// ==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list); // ���� �������� ���� ���� ������ ã���� ���� map �� ��Ƽ� ����!

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	// �������� �� ��ȸ�� ���� DBMS ����
	public Purchase findPurchase(int tranNo) throws Exception {
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
		String sql = "select * from transaction where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		// 3. ��� Ȯ�� 
		ResultSet rs = stmt.executeQuery();
		
		Purchase purchaseVO = null;
		while (rs.next()) { // ����� ������ 
			purchaseVO = new Purchase(); // �ν��Ͻ� ����
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setPurchaseProd(pdService.getProduct(Integer.parseInt(rs.getString("prod_no"))));
			purchaseVO.setBuyer(usService.getUser(rs.getString("buyer_id")));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			if (rs.getString("tran_status_code").trim().equals("1")) {
				purchaseVO.setTranCode("���ſϷ�");
			} else if (rs.getString("tran_status_code").trim().equals("2")) {
				purchaseVO.setTranCode("�����");
			} else if (rs.getString("tran_status_code").trim().equals("3")) {
				purchaseVO.setTranCode("��ۿϷ�");
			}
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
		}
		
		con.close();

		return purchaseVO;
	}
	
	// �������� ������ ���� DBMS ����
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ���� 
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
		
		// 3. ��� Ȯ��
		int confirm = stmt.executeUpdate();
		if (confirm == 1) { // ����� ����
			System.out.println("purchase update �Ϸ�!");
		} else {
			System.out.println("purchase update ����!");
		}
		
		con.close();
	}
	
	// ���� ���� �ڵ� ������ ���� DBMS ����
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
		int prodNo = purchaseVO.getPurchaseProd().getProdNo();
		System.out.println("update trancode dao prodno : " + prodNo);
		System.out.println("update trancode dao tranno : " + purchaseVO.getTranNo());
		
		String sql = "update transaction set tran_status_code = ? ";
		if (prodNo != 0) { // prodNo �� ã�Ƽ� ������Ʈ �ϴ� �Ÿ� 
			sql += "where prod_no = ?";
		} else { // tranNo �� ã�Ƽ� ������Ʈ �ϴ� �Ÿ� 
			sql += "where tran_no = ?";
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getTranCode());
		
		if (prodNo != 0) { // prodNo �� ã�Ƽ� ������Ʈ �ϴ� �Ÿ� 
			stmt.setInt(2, prodNo);
		} else { // tranNo �� ã�Ƽ� ������Ʈ �ϴ� �Ÿ� 
			stmt.setInt(2, purchaseVO.getTranNo());
		}
		
		// 3. ��� Ȯ��
		System.out.println(stmt.executeUpdate());
		int confirm = stmt.executeUpdate();
		if (confirm == 1) { // ����� ����
			System.out.println("trancode update �Ϸ�!");
		} else {
			System.out.println("trancode update ����!");
		}
		
		con.close();
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount) return
	private int getTotalCount(String sql) throws Exception { // getUserList���� sql���� ���ڰ����� �ְ� ����

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		// select count(*)
		// from ( SELECT user_id , user_name , email FROM users ORDER BY user_id )
		// countTable
		// �̷� ������ ��� ���������� ã�������� ī���� �ϰ� ����!

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) { // ����� ������
			totalCount = rs.getInt(1); // ù��° ��� ���� (������ ���� ���°Ŵϱ� ����� �ϳ���)
										// int������ totalCount�� ������� ��
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// [ �Խ��� currentPage Row �� return ]
	// * ROWNUM Ȱ���Ͽ� ȿ������ query�� ������ ���� �޼ҵ�
	// �� �������� 3�پ��� �����ϱ� ȿ�������� 3�ٸ� ã�Բ� �ϱ� -> ���ݱ����� ó������ ������ �� ã�Ҿ���
	private String makeCurrentPageSql(String sql, Search search) { // getUserList���� sql���� search�� ���ڰ����� �ְ� ����
		sql = "SELECT * " + "FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		// select *
		// from ( select inner_table.*, rownum as row_seq
		// from ( SELECT user_id , user_name , email FROM users ORDER BY user_id )
		// inner_table
		// where rownum <= 3, 6 )
		// where row_seq between 1 and 3, 4 and 6 �̷������� �� �� ! rownum �� ����ؼ� �� �������� 123 /
		// 456 / 789 �� ã�Բ� ���ְ� ����

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}
}
