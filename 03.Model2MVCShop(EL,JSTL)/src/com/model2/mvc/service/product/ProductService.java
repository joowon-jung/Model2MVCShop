package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductService {
	
	// 상품 등록을 위한 비즈니스 로직 실행
	public void addProduct(Product productVO) throws Exception;

	// 상품 정보 조회를 위한 비즈니스 로직 실행
	public Product getProduct(int prodNo) throws Exception;

	// 상품 목록 조회를 위한 비즈니스 로직 실행
	public Map<String,Object> getProductList(Search searchVO) throws Exception;

	// 상품 정보 수정을 위한 비즈니스 로직 실행 
	public void updateProduct(Product productVO) throws Exception;
	
	// 상품 삭제를 위한 비즈니스 로직 실행 
	public void deleteProduct(int prodNo) throws Exception;
	
}