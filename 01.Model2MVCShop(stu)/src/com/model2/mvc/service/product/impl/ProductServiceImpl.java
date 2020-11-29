package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	
	public ProductServiceImpl() { // 생성자 
		productDAO = new ProductDAO();
	}
	
	@Override
	// 상품 등록을 위한 비즈니스 로직 실행
	public void addProduct(ProductVO productVO) throws Exception {
		productDAO.insertProduct(productVO);
	}

	@Override
	// 상품 정보 조회를 위한 비즈니스 로직 실행
	public ProductVO getProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	@Override
	// 상품 목록 조회를 위한 비즈니스 로직 실행
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		return productDAO.getProductList(searchVO);
	}

	@Override
	// 상품 정보 수정을 위한 비즈니스 로직 실행
	public void updateProduct(ProductVO productVO) throws Exception {
		productDAO.updateProduct(productVO);
	}

}
