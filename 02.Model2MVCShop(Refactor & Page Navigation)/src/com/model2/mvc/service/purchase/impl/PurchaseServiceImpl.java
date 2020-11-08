package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;

public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}
	
	@Override
	// ���Ÿ� ���� ����Ͻ� ���� 
	public void addPurchase(Purchase purchaseVO) throws Exception {
		purchaseDAO.insertPurchase(purchaseVO);
	}

	@Override
	// �������� �� ��ȸ�� ���� ����Ͻ� ���� 
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// ���Ÿ�� ���⸦ ���� ����Ͻ� ���� 
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(searchVO, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// �������� ������ ���� ����Ͻ� ����
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		purchaseDAO.updatePurchase(purchaseVO);

	}

	@Override
	// ���� ���� �ڵ� ������ ���� ����Ͻ� ���� 
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		purchaseDAO.updateTranCode(purchaseVO);
	}

}
