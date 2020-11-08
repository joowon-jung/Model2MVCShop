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
	// 구매를 위한 비즈니스 수행 
	public void addPurchase(Purchase purchaseVO) throws Exception {
		purchaseDAO.insertPurchase(purchaseVO);
	}

	@Override
	// 구매정보 상세 조회를 위한 비즈니스 수행 
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 구매목록 보기를 위한 비즈니스 수행 
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(searchVO, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 구매정보 수정을 위한 비즈니스 수행
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		purchaseDAO.updatePurchase(purchaseVO);

	}

	@Override
	// 구매 상태 코드 수정을 위한 비즈니스 수행 
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		purchaseDAO.updateTranCode(purchaseVO);
	}

}
