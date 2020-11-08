package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductDAO {

	public ProductDAO() {
	}

	// ��ǰ ���� ����� ���� DBMS ����
	public void insertProduct(Product productVO) throws Exception {
		
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
	public Product findProduct(int prodNo) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
		String sql = "select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		// 3. ��� Ȯ�� 
		ResultSet rs = stmt.executeQuery();
		
		Product productVO = null;
		while (rs.next()) {
			productVO = new Product(); // �ν��Ͻ� ����
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
	public Map <String, Object> getProductList(Search searchVO) throws Exception {
		
		// ��ü���� HashMap �������� �ʰ� Map ���� �������̽� ��� ���α׷��� 
		Map<String , Object>  map = new HashMap<String, Object>();
				
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
		
		// ==> totalCount ������ 
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO ���� totalCount : " + totalCount); // ����� 
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, searchVO);
		
		// 3. ��� Ȯ�� 
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(searchVO);
		
		List<Product> list = new ArrayList<Product>(); // List�� �޾Ƽ� �������̽� ���α׷��� 
		
		while (rs.next()) {  // ����� ������ ArrayList�� Product ���� ���پ� �����Ѵٰ� �����ϱ�
			Product product = new Product();
			product.setProdNo(rs.getInt(1));
			product.setProdName(rs.getString(2));
			product.setProdDetail(rs.getString(3));
			product.setManuDate(rs.getString(4));
			product.setPrice(rs.getInt(5));
			product.setFileName(rs.getString(6));
			product.setRegDate(rs.getDate(7));
			
			product.setProTranCode("�Ǹ���");
			if(rs.getString(8).trim().equals("1")) {
				product.setProTranCode("���ſϷ�");
			} else if (rs.getString(8).trim().equals("2")) {
				product.setProTranCode("�����");
			} else if (rs.getString(8).trim().equals("3")) {
				product.setProTranCode("��ۿϷ�");
			} 
			
			list.add(product);
		}
		
		// ==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount)); // �˻��� ��ǰ ������ �������
		// ==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list); // ���� �������� ���� ��ǰ ������ ã���� ���� map �� ��Ƽ� ����!

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	// ���� ���� ���� 
	public void updateProduct(Product productVO) throws Exception {
		
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
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount) return
	private int getTotalCount(String sql) throws Exception { // getProductList���� sql���� ���ڰ����� �ְ� ����

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		// select count(*)
		// from ( select * from product order by prod_no )
		// countTable
		// �̷� ������ ��� ��ǰ ������ ã�������� ī���� �ϰ� ����!

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) { // ����� ������
			totalCount = rs.getInt(1); 	// ù��° ��� ���� (������ ���� ���°Ŵϱ� ����� �ϳ���)
										// int������ totalCount�� ���� 
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}
	
	// [ �Խ��� currentPage Row �� return ]
	// * ROWNUM Ȱ���Ͽ� ȿ������ query�� ������ ���� �޼ҵ�
	// �� �������� 3�پ��� �����ϱ� ȿ�������� 3�ٸ� ã�Բ� �ϱ� -> ���ݱ����� ó������ ������ �� ã�Ҿ���
	private String makeCurrentPageSql(String sql, Search search) { // getProductList���� sql���� search�� ���ڰ����� �ְ� ����
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
								" 	FROM (	"+sql+" ) inner_table "+
								"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();

		// select *
		// from ( select inner_table.*, rownum as row_seq
		// 		  from ( select * from product order by prod_no ) inner_table
		// 		  where rownum <= 3 / 6 )
		// where row_seq between 1 and 3 / 4 and 6 �̷������� �� �� !  
		// rownum �� ����ؼ� �� �������� 123 / 456 / 789 �� ã�Բ� ���ְ� ����

		System.out.println("ProductDAO ���� makeCurrentSql : " + sql);

		return sql;
	}
}
