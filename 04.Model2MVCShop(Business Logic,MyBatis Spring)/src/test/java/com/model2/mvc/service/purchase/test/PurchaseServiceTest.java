package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

/*
 *	FileName :  PurchaseServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {
	
	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	//@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		User user = new User();
		Product product = new Product();
		
		user.setUserId("user04");
		purchase.setBuyer(user);
		
		product.setProdNo(10143);
		purchase.setPurchaseProd(product);
		
		purchase.setReceiverName("�ֿ�");
		
		purchaseService.addPurchase(purchase);
		
		purchase = purchaseService.getPurchase(10081);
		
		Assert.assertEquals("�ֿ�", purchase.getReceiverName());
		
	} 

	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase(10041);
				
		Assert.assertEquals("SCOTT", purchase.getReceiverName());
		
		System.out.println(purchase);
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(10081);
		
		purchase.setPaymentOption("1");
		purchase.setReceiverName("���ֿ�");
		purchase.setReceiverPhone("1004");
		purchase.setDivyAddr("����");
		purchase.setDivyRequest("������!");
		purchase.setDivyDate("20-11-21");
		
		purchaseService.updatePurchase(purchase);
		
		Purchase purchase2 = purchaseService.getPurchase(10081);
		Assert.assertEquals("���ֿ�", purchase2.getReceiverName());
		
	}
	
	//@Test
	public void testUpdateTranCode() throws Exception {
		
		Purchase purchase = new Purchase();
		Product product = new Product();
		
		// prodNo�� ã�Ƽ� ������Ʈ �� ��
//		product.setProdNo(10143);
//		purchase.setPurchaseProd(product); 
//		purchase.setTranCode("2"); // �����
//		
//		purchaseService.updateTranCode(purchase);
//		
//		Purchase purchase2 = purchaseService.getPurchase(10081);
//		Assert.assertEquals("�����", purchase2.getTranCode());
//		
//		System.out.println(purchase2);
		
		// tranNo�� ã�Ƽ� ������Ʈ �� ��
		Purchase purchase3 = new Purchase();
		Product product3 = new Product();
		
		purchase3.setTranNo(10081);
		purchase3.setTranCode("3");
		purchase3.setPurchaseProd(product3);
		
		purchaseService.updateTranCode(purchase3);
		
		purchase3 = purchaseService.getPurchase(10081);
		Assert.assertEquals("��ۿϷ�", purchase3.getTranCode());
	}
	
	@Test
	public void testGetPurchaseList() throws Exception {
		
		Search search = new Search();
		int currentPage=1;
		search.setCurrentPage(currentPage);
		search.setPageSize(3);
		
		Map <String, Object> map = purchaseService.getPurchaseList(search, "user04");
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());
		
		System.out.println(list);
		
		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

}
