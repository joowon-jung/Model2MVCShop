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
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {
	
	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
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
		
		purchase.setReceiverName("주원");
		
		purchaseService.addPurchase(purchase);
		
		purchase = purchaseService.getPurchase(10081);
		
		Assert.assertEquals("주원", purchase.getReceiverName());
		
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
		purchase.setReceiverName("정주원");
		purchase.setReceiverPhone("1004");
		purchase.setDivyAddr("지구");
		purchase.setDivyRequest("빨리요!");
		purchase.setDivyDate("20-11-21");
		
		purchaseService.updatePurchase(purchase);
		
		Purchase purchase2 = purchaseService.getPurchase(10081);
		Assert.assertEquals("정주원", purchase2.getReceiverName());
		
	}
	
	//@Test
	public void testUpdateTranCode() throws Exception {
		
		Purchase purchase = new Purchase();
		Product product = new Product();
		
		// prodNo로 찾아서 업데이트 할 때
//		product.setProdNo(10143);
//		purchase.setPurchaseProd(product); 
//		purchase.setTranCode("2"); // 배송중
//		
//		purchaseService.updateTranCode(purchase);
//		
//		Purchase purchase2 = purchaseService.getPurchase(10081);
//		Assert.assertEquals("배송중", purchase2.getTranCode());
//		
//		System.out.println(purchase2);
		
		// tranNo로 찾아서 업데이트 할 때
		Purchase purchase3 = new Purchase();
		Product product3 = new Product();
		
		purchase3.setTranNo(10081);
		purchase3.setTranCode("3");
		purchase3.setPurchaseProd(product3);
		
		purchaseService.updateTranCode(purchase3);
		
		purchase3 = purchaseService.getPurchase(10081);
		Assert.assertEquals("배송완료", purchase3.getTranCode());
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
