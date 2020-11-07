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
	
	// ���Ÿ� ���� DBMS ����
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
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
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ���� 
		String sql = "select * from transaction where buyer_id = ? ";
		sql +=" order by tran_no";
		
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1, buyerId);
		
		ResultSet rs = stmt.executeQuery();
		
		rs.last(); // Ŀ���� ���� �ڷ�
		int total = rs.getRow();
		System.out.println("�ο��� ��:" + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>(); // key, value ���·� ����
		map.put("count", new Integer(total)); // Object Ÿ������ ���� �ϴϱ� Wrapper class
		
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
					vo.setTranCode("���ſϷ�");
				} else if (rs.getString("tran_status_code").trim().equals("2")) {
					vo.setTranCode("�����");
				} else if (rs.getString("tran_status_code").trim().equals("3")) {
					vo.setTranCode("��ۿϷ�");
				}
				vo.setOrderDate(rs.getDate("order_data"));
				vo.setDivyDate(rs.getString("dlvy_date"));
				
				list.add(vo); // ArrayList�� ProductVO �� �پ� ������ ����
				if (!rs.next()) { // ã�� ����� ������ 
					break; // for�� ��������
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	
	// �������� �� ��ȸ�� ���� DBMS ����
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
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
		
		PurchaseVO purchaseVO = null;
		while (rs.next()) { // ����� ������ 
			purchaseVO = new PurchaseVO(); // �ν��Ͻ� ����
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
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		
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
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
		int prodNo = purchaseVO.getPurchaseProd().getProdNo();
		System.out.println("update trancode dao prodno : " + prodNo);
		System.out.println("update trancode dao tranno : " + purchaseVO.getTranNo());
		
		String sql = "update transaction set tran_status_code = ? ";
		if (prodNo != 0) { // prodNo �� �˻��ϴ� �Ÿ� 
			sql += "where prod_no = ?";
		} else { // tranNo �� �˻��ϴ� �Ÿ� 
			sql += "where tran_no = ?";
		}
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getTranCode());
		
		if (prodNo != 0) { // prodNo �� �˻��ϴ� �Ÿ� 
			stmt.setInt(2, prodNo);
		} else { // tranNo �� �˻��ϴ� �Ÿ� 
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
}
