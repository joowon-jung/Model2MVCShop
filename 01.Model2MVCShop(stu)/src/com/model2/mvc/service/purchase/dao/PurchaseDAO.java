package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;

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
		String sql = "";
		
	}
}
