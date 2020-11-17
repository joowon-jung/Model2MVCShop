package com.model2.mvc.service.user.test;

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
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)

//==> Meta-Data �� �پ��ϰ� Wiring ����...
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
																	"classpath:config/context-aspect.xml",
																	"classpath:config/context-mybatis.xml",
																	"classpath:config/context-transaction.xml" })
public class ProductServiceTest {
	
	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("õ����");
		product.setProdDetail("���ֵ� �� �԰�ʹ�");
		product.setManuDate("2020-11-15");
		product.setPrice(20000);
		product.setFileName("mandarin.jpg");
		
		productService.addProduct(product);
		
		product = productService.getProduct(10143); // ��ȣ �ٲ㰡�鼭 �غ���
		
		// ==> console Ȯ��
		// System.out.println(product);

		// ==> API Ȯ��
		Assert.assertEquals("õ����", product.getProdName());
		Assert.assertEquals("���ֵ� �� �԰�ʹ�", product.getProdDetail());
		Assert.assertEquals("2020-11-15", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("mandarin.jpg", product.getFileName());
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		
		product = productService.getProduct(10143);
		
		// ==> console Ȯ��
		//System.out.println(product);
		
		// ==> API Ȯ��
		Assert.assertEquals("õ����", product.getProdName());
		Assert.assertEquals("���ֵ� �� �԰�ʹ�", product.getProdDetail());
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
		
		Assert.assertEquals("õ����", product.getProdName());
		Assert.assertEquals("���ֵ� �� �԰�ʹ�", product.getProdDetail());
		Assert.assertEquals("2020-11-15", product.getManuDate());
		Assert.assertEquals(20000, product.getPrice());
		Assert.assertEquals("mandarin.jpg", product.getFileName());
		
		product.setProdName("������");
		product.setProdDetail("���ֵ� ����ʹ�");
		product.setManuDate("2020-12-25");
		product.setPrice(30000);
		product.setFileName("mandarin.jpg");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10143);
		Assert.assertNotNull(product);
		
		//==> console Ȯ��
		//System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals("������", product.getProdName());
		Assert.assertEquals("���ֵ� ����ʹ�", product.getProdDetail());
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
	
	@Test
	public void testGetProductListAll() throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map <String, Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
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
	
	//@Test
	public void testGetProductListByProdNo() throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10143");
	 	Map <String, Object> map = productService.getProductList(search);
	 	
	 	List <Object> list = (List <Object>) map.get("list");
	 	Assert.assertEquals(3, list.size()); // ��ǰ ��ȣ�� �˻��� �Ŵϱ� �Ѱ� �� �� 
	 	
	 	//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List <Object>) map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
	 	//==> console Ȯ��
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
	 	
	 	//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
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

		// ==> console Ȯ��
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("2");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console Ȯ��
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

}
