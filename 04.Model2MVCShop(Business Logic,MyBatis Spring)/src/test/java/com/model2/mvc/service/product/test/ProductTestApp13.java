package com.model2.mvc.service.product.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

/*
 * FileName : MyBatisTestApp13.java
  * :: Business Layer unit Test : Service + Persistence (Spring +mybatis + DAO)
  */
public class ProductTestApp13 {
	
	///main method
	public static void main(String[] args) throws Exception{

		ApplicationContext context =
			new ClassPathXmlApplicationContext(
												new String[] {	"/config/commonservice13.xml",
																"/config/productservice13.xml" });
		System.out.println("\n");

		//==> Bean/IoC Container ·Î ºÎÅÍ È¹µæÇÑ UserService ÀÎ½ºÅÏ½º È¹µæ
		ProductService productService = (ProductService)context.getBean("productServiceImpl");
		
		System.out.println("\n");
		
		//==> Test ¿ë User instance »ý¼º  
		Product product = new Product("°ù°¨", "¸ÀÀÖ°Ú´Ù", "20-11-15", 2000, "a.jpg");
				
		//1. addUser Test  
		System.out.println(":: 1. addProduct(INSERT)  ? ");
		productService.addProduct(product);
		System.out.println("\n");
//		
//		//2. getUser Test :: ÁÖ¸ù inert È®ÀÎ 
		System.out.println(":: 2. getProduct(SELECT)  ? ");
		product.setProdNo(10137);
		System.out.println(":: "+ productService.getProduct(product.getProdNo()));
		System.out.println("\n");
		//3. UserMapper10.uadateUser Test  :: users table age/grade/redDate ÀÔ·Â°ª È®ÀÎÇÒ°Í : OK
		//                                                    :: ÁÖ¸ù ==> ¿Â´Þ ¼öÁ¤
//		product.setProdDetail("Åõ½æÀÌ Á¸¸À");
		product.setProdNo(10138);
//		System.out.println(":: 3. update(UPDATE)  ? ");
//		productDao.updateProduct(product);
//		System.out.println("\n");
//		
////		4. UserMapper10.getUserList Test  :: Dynamic Query È®ÀÎ id=user04/name=¿Â´Þ °Ë»ö
//		System.out.println(":: 4. getUser(SELECT)  ? ");
//		System.out.println(":: "+ productDao.findProduct(product.getProdNo()));
//		System.out.println("\n");
//		
		//5. UserMapper10.removeUser Test
//		System.out.println(":: 5. Use10.removeUser (DELETE)  ? ");
//		productDao.deleteProduct(product.getProdNo());
//		System.out.println("\n");
//		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
//		System.out.println("\n");
		
		//==> Test ¿ë Search instance »ý¼º 
//		Search search = new Search();
//		
//		//1. UserMapper10.getUserList Test 
//		System.out.println(":: 1. getUserList01(SELECT)  ? ");
//		
//		//2. UserMapper10.getUserList Test 
//	 	search.setCurrentPage(1);
//	 	search.setPageSize(6);
//	 	search.setSearchCondition("2");
//	 	search.setSearchKeyword("0");
//	 	
//	 	productService.getProductList(search);
//		
//		System.out.println(":: 2. getTotalCount  ? ");
//		int a = productDao.getTotalCount(search);
//		System.out.println(a);
	}//end of main
}//end of class