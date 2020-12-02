package com.model2.mvc.service.purchase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}
	
	@Override
	// ���Ÿ� ���� ����Ͻ� ���� 
	public void addPurchase(Purchase purchaseVO) throws Exception {
		purchaseDao.insertPurchase(purchaseVO);
	}

	@Override
	// �������� �� ��ȸ�� ���� ����Ͻ� ���� 
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.findPurchase(tranNo);
	}

//	@Override
//	public Purchase getPurchase2(int ProdNo) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	// ���Ÿ�� ���⸦ ���� ����Ͻ� ���� 
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		Map<String, Object> map = new HashMap <String, Object>();
		
		List <Purchase> purchaseList = purchaseDao.getPurchaseList(search, buyerId);
		
		// ��ǰ ��ü�� �ƿ� purchaseprod�� �������� List �ϳ� �� ������� 
		List <Purchase> purchaseListIncludeProd = new ArrayList<Purchase>();
		
		for (int i = 0; i < purchaseList.size(); i++) {
			Purchase purchase = (Purchase)purchaseList.get(i);
			
			purchase.setPurchaseProd(productDao.findProduct(purchase.getPurchaseProd().getProdNo()));
			purchaseListIncludeProd.add(purchase);
		}

		map.put("list", purchaseListIncludeProd);
		map.put("totalCount", new Integer(purchaseDao.getTotalCount(buyerId)));
		
		return map;
	}

//	@Override
//	public Map<String, Object> getSaleList(Search search) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	// �������� ������ ���� ����Ͻ� ����
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	// ���� ���� �ڵ� ������ ���� ����Ͻ� ���� 
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}

}
