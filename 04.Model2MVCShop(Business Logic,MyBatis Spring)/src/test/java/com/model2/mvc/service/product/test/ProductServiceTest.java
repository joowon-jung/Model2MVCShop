package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;

/*
 *	FileName :  ProductServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {
	
	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("천혜향");
		product.setProdDetail("제주도 귤 먹고싶다");
		product.setManuDate("2020-11-15");
		product.setPrice(20000);
		product.setFileName("mandarin.jpg");
		
		productService.addProduct(product);
		
		product = productService.getProduct(10143); // 번호 바꿔가면서 해보기
		
		// ==> console 확인
		// System.out.println(product);

		// ==> API 확인
		Assert.assertEquals("천혜향", product.getProdName());
		Assert.assertEquals("제주도 귤 먹고싶다", product.getProdDetail());
		Assert.assertEquals("2020-11-15", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("mandarin.jpg", product.getFileName());
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		
		product = productService.getProduct(10143);
		
		// ==> console 확인
		//System.out.println(product);
		
		// ==> API 확인
		Assert.assertEquals("천혜향", product.getProdName());
		Assert.assertEquals("제주도 귤 먹고싶다", product.getProdDetail());
		Assert.assertEquals("2020-11-15", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("mandarin.jpg", product.getFileName());
		
		Assert.assertNotNull(productService.getProduct(10012));
		Assert.assertNotNull(productService.getProduct(10002));
	}
	
	//@Test
	public void testUpdateProduct() throws Exception {
		
		Product product = productService.getProduct(10143);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("천혜향", product.getProdName());
		Assert.assertEquals("제주도 귤 먹고싶다", product.getProdDetail());
		Assert.assertEquals("2020-11-15", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("mandarin.jpg", product.getFileName());
		
		product.setProdName("레드향");
		product.setProdDetail("제주도 가고싶다");
		product.setManuDate("2020-12-25");
		product.setPrice(30000);
		product.setFileName("mandarin.jpg");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10143);
		Assert.assertNotNull(product);
		
		//==> console 확인
		//System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals("레드향", product.getProdName());
		Assert.assertEquals("제주도 가고싶다", product.getProdDetail());
		Assert.assertEquals("2020-12-25", product.getManuDate());
		Assert.assertEquals(30000, product.getPrice());
		Assert.assertEquals("mandarin.jpg", product.getFileName());
	}
	
	//@Test 
	public void testDeleteProduct() throws Exception {
		
		productService.deleteProduct(10144);
		
		Product product = productService.getProduct(10144);
		
		Assert.assertNotNull(product);
	}
	
	//@Test
	public void testGetProductListAll() throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map <String, Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	
	@Test
	public void testGetProductListByProdNo() throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10143");
	 	Map <String, Object> map = productService.getProductList(search);
	 	
	 	List <Object> list = (List <Object>) map.get("list");
	 	Assert.assertEquals(3, list.size()); // 상품 번호로 검색한 거니까 한개 일 것 
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List <Object>) map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	
	//@Test
	public void testGetProductListByProdName() throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("a");
	 	
	 	Map <String, Object> map = productService.getProductList(search);
	 	
	 	List <Object> list = (List <Object>) map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	}
	
	//@Test
	public void testGetProductListByProdPrice() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("2");
		search.setSearchKeyword("12");

		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("2");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console 확인
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

}
