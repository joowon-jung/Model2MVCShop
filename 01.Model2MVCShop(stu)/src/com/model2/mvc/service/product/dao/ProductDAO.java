package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ProductDAO {

	public ProductDAO() {
	}

	// ��ǰ ���� ����� ���� DBMS ����
	public void insertProduct(ProductVO productVO) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ���� 
		String sql = "insert into product values "
				+ "(seq_product_prod_no.nextval, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD')) ";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		// 3. ��� Ȯ��
		int confirm = stmt.executeUpdate();
		
		if (confirm == 1) { // ����� ���� 
			System.out.println("product insert �Ϸ�!");
			System.out.println(productVO);
		} else {
			System.out.println("product insert ����!");
		}
		
		con.close();	
	}
	
	// ��ǰ ���� ��ȸ�� ���� DBMS ����
	public ProductVO findProduct(int prodNo) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
		String sql = "select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		// 3. ��� Ȯ�� 
		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO(); // �ν��Ͻ� ����
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("reg_date"));
		}
		
		con.close();

		return productVO;
	}
	
	// ��ǰ ��� ��ȸ�� ���� DBMS ����
	public HashMap <String, Object> getProductList(SearchVO searchVO) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ���� 
		String sql = "select p.*, nvl(t.tran_status_code, 0) from product p, transaction t where p.prod_no = t.prod_no(+) ";
		if (searchVO.getSearchCondition() != null) { // searchCondition : ��ǰ��ȣ / ��ǰ�� / ��ǰ���� ����
			if (searchVO.getSearchCondition().equals("0")) { // 0 : ��ǰ��ȣ�� �˻�
				sql += " and p.prod_no like '%' || '" + searchVO.getSearchKeyword() + "' || '%'";
			} else if (searchVO.getSearchCondition().equals("1")) { // 1 : ��ǰ������ �˻�
				sql += " and p.prod_name like '%' || '" + searchVO.getSearchKeyword() + "' || '%'";
			} else if (searchVO.getSearchCondition().equals("2")) { // 2 : ��ǰ�������� �˻�
				sql += " and p.price like '%' || '" + searchVO.getSearchKeyword() + "' || '%'";
			}
		}
		sql += " order by p.prod_no";
		
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		// ResultSet.TYPE_SCROLL_INSENSITIVE : �ٸ� ���ø����̼ǿ� ���� �����ͺ��̽��� �ش� ���ڵ尡 �����Ǹ� ResultSet�� ���ڵ忡�� �ݿ�
		// ResultSet.CONCUR_UPDATABLE : ������ ������ �����ϵ��� �Ѵ�.
		ResultSet rs = stmt.executeQuery();
		
		rs.last(); // Ŀ���� ���� �ڷ�
		int total = rs.getRow();
		System.out.println("�ο��� ��:" + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>(); // key, value ���·� ����
		map.put("count", new Integer(total)); // Object Ÿ������ ���� �ϴϱ� Wrapper class
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt(1));
				vo.setProdName(rs.getString(2));
				vo.setProdDetail(rs.getString(3));
				vo.setManuDate(rs.getString(4));
				vo.setPrice(rs.getInt(5));
				vo.setFileName(rs.getString(6));
				vo.setRegDate(rs.getDate(7));
				
				vo.setProTranCode("�Ǹ���");
				
				if(rs.getString(8).trim().equals("1")) {
					vo.setProTranCode("���ſϷ�");
				} else if (rs.getString(8).trim().equals("2")) {
					vo.setProTranCode("�����");
				} else if (rs.getString(8).trim().equals("3")) {
					vo.setProTranCode("��ۿϷ�");
				} 
				
				list.add(vo); // ArrayList�� ProductVO �� �پ� ������ ����
				if (!rs.next()) { // ã�� ����� ������ 
					break; // for�� ��������
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	
	// ��ǰ ���� ���� 
	public void updateProduct(ProductVO productVO) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ���� 
		String sql = "update product set prod_name = ?, prod_detail = ?, manufacture_day = ?, price = ?, image_file = ? "
					+ "where prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		
		// 3. ��� Ȯ��
		int confirm = stmt.executeUpdate();
		if (confirm == 1) { // ����� ���� 
			System.out.println("product update �Ϸ�!");
			System.out.println(productVO);
		} else {
			System.out.println("product update ����!");
		}
		
		con.close();
	}
	
}
