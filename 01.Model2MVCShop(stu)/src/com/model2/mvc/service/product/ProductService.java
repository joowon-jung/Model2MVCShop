package com.model2.mvc.service.product;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.vo.ProductVO;


public interface ProductService {
	
	// ��ǰ ����� ���� ����Ͻ� ���� ����
	public void addProduct(ProductVO productVO) throws Exception;

	// ��ǰ ���� ��ȸ�� ���� ����Ͻ� ���� ����
	public ProductVO getProduct(int prodNo) throws Exception;

	// ��ǰ ��� ��ȸ�� ���� ����Ͻ� ���� ����
	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception;

	// ��ǰ ���� ������ ���� ����Ͻ� ���� ���� 
	public void updateProduct(ProductVO productVO) throws Exception;
	
}