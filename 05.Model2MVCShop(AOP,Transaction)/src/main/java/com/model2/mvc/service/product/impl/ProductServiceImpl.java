package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	///Constructor
	public ProductServiceImpl() { 
		System.out.println(this.getClass());
	}
	
	@Override
	// 상품 등록을 위한 비즈니스 로직 실행
	public void addProduct(Product product) throws Exception {
		productDao.insertProduct(product);
	}

	@Override
	// 상품 정보 조회를 위한 비즈니스 로직 실행
	public Product getProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
	}

	@Override
	// 상품 목록 조회를 위한 비즈니스 로직 실행
	public Map <String, Object> getProductList(Search search) throws Exception {
		List <Product> list = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		Map <String, Object> map = new HashMap <String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount)); // wrapper class 사용 이유? 
		
		return map;
	}

	@Override
	// 상품 정보 수정을 위한 비즈니스 로직 실행
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}
	
	@Override
	// 상품 삭제를 위한 비즈니스 로직 실행 
	public void deleteProduct(int prodNo) throws Exception {
		productDao.deleteProduct(prodNo);
	}

}
