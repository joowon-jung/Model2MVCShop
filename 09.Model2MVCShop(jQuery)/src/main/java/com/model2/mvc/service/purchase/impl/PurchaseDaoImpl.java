package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

//==> ���Ű��� DAO CRUD ����
//�����ͺ��̽��� �������� ����� ����ϴ� �۽ý��Ͻ� ������ ����� PurchaseDao �������̽� ���� 

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}

	@Override
	public void insertPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.insertPurchase", purchase);
	}

	@Override
	public Purchase findPurchase(int tranNo) throws Exception {
		return (Purchase) sqlSession.selectOne("PurchaseMapper.findPurchase" , tranNo);
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}

	@Override
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap <String, Object>();
		
		map.put("buyerId", buyerId);
		map.put("search", search);
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	@Override
	public int getTotalCount(String buyerId) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId);
	}

}
