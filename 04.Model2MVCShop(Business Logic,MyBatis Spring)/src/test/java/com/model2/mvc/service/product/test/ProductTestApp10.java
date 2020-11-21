package com.model2.mvc.service.product.test;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public class ProductTestApp10 {
	
	///main method
	public static void main(String[] args) throws Exception{
	
		//==> SqlSessionFactoryBean.getSqlSession()�� �̿� SqlSession instance GET
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		System.out.println("\n");
		
		//==> Test �� User instance ����  
		Product product = new Product("����", "���ְڴ�", "20-11-15", 2000, "a.jpg");
		
//		//1. UserMapper10.addUser Test  :: users table age/grade/redDate �Է°� Ȯ���Ұ� : OK 
//		System.out.println(":: 1. addProduct(INSERT)  ? ");
//		System.out.println(":: "+ sqlSession.insert("ProductMapper.insertProduct",product));
//		System.out.println("\n");
//		
//		//2. UserMapper10.getUser Test :: �ָ� inert Ȯ�� 
//		System.out.println(":: 2. getProduct(SELECT)  ? ");
//		product.setProdNo(10134);
//		System.out.println(":: "+sqlSession.selectOne("ProductMapper.findProduct",product.getProdNo()));
//		System.out.println("\n");
//		
//		//3. UserMapper10.uadateUser Test  :: users table age/grade/redDate �Է°� Ȯ���Ұ� : OK
//		//                                                    :: �ָ� ==> �´� ����
//		product.setProdDetail("������ ����");
//		System.out.println(":: 3. update(UPDATE)  ? ");
//		System.out.println(":: "+ sqlSession.update("ProductMapper.updateProduct",product));
//		System.out.println("\n");
//		
//		//4. UserMapper10.getUserList Test  :: Dynamic Query Ȯ�� id=user04/name=�´� �˻�
//		System.out.println(":: 4. getUser(SELECT)  ? ");
//		System.out.println(":: "+sqlSession.selectOne("ProductMapper.findProduct",product.getProdNo()) );
//		System.out.println("\n");
//		
//		//5. UserMapper10.removeUser Test
//		System.out.println(":: 5. Use10.removeUser (DELETE)  ? ");
//		System.out.println(":: "+sqlSession.delete("ProductMapper.deleteProduct",product.getProdNo()) );
//		System.out.println("\n");
//		System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
//		System.out.println("\n");
		
		//==> Test �� Search instance ���� 
		Search search = new Search();
		
		//1. UserMapper10.getUserList Test 
		System.out.println(":: 1. getUserList01(SELECT)  ? ");
		
		//2. UserMapper10.getUserList Test 
	 	search.setCurrentPage(1);
	 	search.setPageSize(6);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("0");
	 	SqlSessionFactoryBean.printList( sqlSession.selectList("ProductMapper.getProductList",search) );
		
		System.out.println(":: 2. getTotalCount  ? ");
		int a = sqlSession.selectOne("ProductMapper.getTotalCount", search);
		System.out.println(a);
		
	}//end of main
}//end of class