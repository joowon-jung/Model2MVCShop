package com.model2.mvc.service.product.test;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.impl.ProductDaoImpl;

/*
 * FileName : MyBatisTestApp11.java 
 *  :: Persistence Layer unit Test : MyBatis + DAO
  */
public class ProductTestApp11 {
	
	///main method
	public static void main(String[] args) throws Exception{

		//==> SqlSessionFactoryBean.getSqlSession()을 이용 SqlSession instance GET
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		
		//==> UserDaoImpl11 생성 및 sqlSession instance setter injection
		ProductDaoImpl productDao = new ProductDaoImpl();
		productDao.setSqlSession(sqlSession);
		System.out.println("\n");
		
		//==> Test 용 User instance 생성  
		Product product = new Product("곶감", "맛있겠다", "20-11-15", 2000, "a.jpg");
		
//		//1. UserMapper10.addUser Test  :: users table age/grade/redDate 입력값 확인할것 : OK 
//		System.out.println(":: 1. addProduct(INSERT)  ? ");
//		productDao.insertProduct(product);
//		System.out.println("\n");
		
//		//2. UserMapper10.getUser Test :: 주몽 inert 확인 
//		System.out.println(":: 2. getProduct(SELECT)  ? ");
//		product.setProdNo(10137);
//		System.out.println(":: "+ productDao.findProduct(product.getProdNo()));
//		System.out.println("\n");
		
		//3. UserMapper10.uadateUser Test  :: users table age/grade/redDate 입력값 확인할것 : OK
		//                                                    :: 주몽 ==> 온달 수정
//		product.setProdDetail("투썸이 존맛");
//		product.setProdNo(10137);
//		System.out.println(":: 3. update(UPDATE)  ? ");
//		productDao.updateProduct(product);
//		System.out.println("\n");
		
		//4. UserMapper10.getUserList Test  :: Dynamic Query 확인 id=user04/name=온달 검색
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
		
		//==> Test 용 Search instance 생성 
		Search search = new Search();
		
		//1. UserMapper10.getUserList Test 
		System.out.println(":: 1. getUserList01(SELECT)  ? ");
		
		//2. UserMapper10.getUserList Test 
	 	search.setCurrentPage(1);
	 	search.setPageSize(6);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("0");
	 	
	 	productDao.getProductList(search);
		
		System.out.println(":: 2. getTotalCount  ? ");
		int a = productDao.getTotalCount(search);
		System.out.println(a);
	}//end of main
}//end of class