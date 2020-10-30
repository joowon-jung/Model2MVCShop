package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseDAO {

	public PurchaseDAO() {
	}
	
	// 구매를 위한 DBMS 수행
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		String sql = "";
		
	}
}
