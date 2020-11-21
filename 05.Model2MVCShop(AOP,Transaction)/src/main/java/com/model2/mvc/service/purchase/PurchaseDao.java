package com.model2.mvc.service.purchase;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

//==> ���Ű������� CRUD �߻�ȭ/ĸ��ȭ�� DAO Interface Definition
public interface PurchaseDao {

	// INSERT (����) 
	public void insertPurchase(Purchase purchase) throws Exception;
	
	// SELECT ONE (�������� �� ��ȸ) 
	public Purchase findPurchase(int tranNo) throws Exception;
	
	// UPDATE (�������� ����)
	public void updatePurchase(Purchase purchase) throws Exception;
	
	// UPDATE (���� ���� �ڵ� ����)
	public void updateTranCode(Purchase purchase) throws Exception;
	
	// SELECT LIST
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;
	
	// �Խ��� Page ó���� ���� ��üRow(totalCount)  return
	public int getTotalCount(String buyerId) throws Exception;
}
