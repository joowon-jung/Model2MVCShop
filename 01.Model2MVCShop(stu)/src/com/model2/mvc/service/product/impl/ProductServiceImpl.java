package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService {

	private ProductDAO productDAO;
	
	public ProductServiceImpl() { // ������ 
		productDAO = new ProductDAO();
	}
	
	@Override
	// ��ǰ ����� ���� ����Ͻ� ���� ����
	public void addProduct(ProductVO productVO) throws Exception {
		productDAO.insertProduct(productVO);
	}

	@Override
	// ��ǰ ���� ��ȸ�� ���� ����Ͻ� ���� ����
	public ProductVO getProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	@Override
	// ��ǰ ��� ��ȸ�� ���� ����Ͻ� ���� ����
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		return productDAO.getProductList(searchVO);
	}

	@Override
	// ��ǰ ���� ������ ���� ����Ͻ� ���� ����
	public void updateProduct(ProductVO productVO) throws Exception {
		productDAO.updateProduct(productVO);
	}

}
