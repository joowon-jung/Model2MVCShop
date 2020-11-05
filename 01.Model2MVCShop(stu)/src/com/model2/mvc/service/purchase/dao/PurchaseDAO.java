package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

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
}
