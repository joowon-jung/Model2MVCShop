package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductService {
	
	// ��ǰ ����� ���� ����Ͻ� ���� ����
	public void addProduct(Product productVO) throws Exception;

	// ��ǰ ���� ��ȸ�� ���� ����Ͻ� ���� ����
	public Product getProduct(int prodNo) throws Exception;

	// ��ǰ ��� ��ȸ�� ���� ����Ͻ� ���� ����
	public Map<String,Object> getProductList(Search searchVO) throws Exception;

	// ��ǰ ���� ������ ���� ����Ͻ� ���� ���� 
	public void updateProduct(Product productVO) throws Exception;
	
}