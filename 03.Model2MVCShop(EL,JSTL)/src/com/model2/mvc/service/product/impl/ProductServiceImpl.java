package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	
	public ProductServiceImpl() { // ������ 
		productDAO = new ProductDAO();
	}
	
	@Override
	// ��ǰ ����� ���� ����Ͻ� ���� ����
	public void addProduct(Product productVO) throws Exception {
		productDAO.insertProduct(productVO);
	}

	@Override
	// ��ǰ ���� ��ȸ�� ���� ����Ͻ� ���� ����
	public Product getProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	@Override
	// ��ǰ ��� ��ȸ�� ���� ����Ͻ� ���� ����
	public Map <String, Object> getProductList(Search searchVO) throws Exception {
		return productDAO.getProductList(searchVO);
	}

	@Override
	// ��ǰ ���� ������ ���� ����Ͻ� ���� ����
	public void updateProduct(Product productVO) throws Exception {
		productDAO.updateProduct(productVO);
	}
	
	@Override
	public void deleteProduct(int prodNo) throws Exception {
		productDAO.deleteProduct(prodNo);
	}

}
