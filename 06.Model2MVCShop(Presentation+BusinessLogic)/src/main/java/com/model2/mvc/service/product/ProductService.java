package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

// 상품관리 서비스 비즈니스 로직을 추상화한 interface
// Component : 인터페이스로 Encapsulation 된 재사용 가능한 lib 
public interface ProductService {
	
	// 상품 등록
	public void addProduct(Product product) throws Exception;

	// 상품 정보 조회
	public Product getProduct(int prodNo) throws Exception;

	// 상품 목록 리스트 
	public Map<String,Object> getProductList(Search search) throws Exception;

	// 상품 정보 수정
	public void updateProduct(Product product) throws Exception;
	
	// 상품 삭제
	public void deleteProduct(int prodNo) throws Exception;
	
}