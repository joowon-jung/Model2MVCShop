package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

//==> 구매관리에서 CRUD 추상화/캡슐화한 DAO Interface Definition
public interface PurchaseDao {

	// INSERT (구매) 
	public void insertPurchase(Purchase purchase) throws Exception;
	
	// SELECT ONE (구매정보 상세 조회) 
	public Purchase findPurchase(int tranNo) throws Exception;
	
	// UPDATE (구매정보 수정)
	public void updatePurchase(Purchase purchase) throws Exception;
	
	// UPDATE (구매 상태 코드 수정)
	public void updateTranCode(Purchase purchase) throws Exception;
	
	// SELECT LIST
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;
	
	// 게시판 Page 처리를 위한 전체Row(totalCount)  return
	public int getTotalCount(String buyerId) throws Exception;
}
