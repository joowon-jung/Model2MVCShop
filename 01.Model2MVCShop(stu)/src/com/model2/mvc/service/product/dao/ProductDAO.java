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

	// 상품 정보 등록을 위한 DBMS 수행
	public void insertProduct(ProductVO productVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송 
		String sql = "insert into product values "
				+ "(seq_product_prod_no.nextval, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD')) ";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		// 3. 결과 확인
		int confirm = stmt.executeUpdate();
		
		if (confirm == 1) { // 디버깅 위함 
			System.out.println("product insert 완료!");
			System.out.println(productVO);
		} else {
			System.out.println("product insert 실패!");
		}
		
		con.close();	
	}
	
	// 상품 정보 조회를 위한 DBMS 수행
	public ProductVO findProduct(int prodNo) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		String sql = "select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		// 3. 결과 확인 
		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO(); // 인스턴스 생성
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
	
	// 상품 목록 조회를 위한 DBMS 수행
	public HashMap <String, Object> getProductList(SearchVO searchVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송 
		String sql = "select p.*, nvl(t.tran_status_code, 0) from product p, transaction t where p.prod_no = t.prod_no(+) ";
		if (searchVO.getSearchCondition() != null) { // searchCondition : 상품번호 / 상품명 / 상품가격 구분
			if (searchVO.getSearchCondition().equals("0")) { // 0 : 상품번호로 검색
				sql += " and p.prod_no like '%' || '" + searchVO.getSearchKeyword() + "' || '%'";
			} else if (searchVO.getSearchCondition().equals("1")) { // 1 : 상품명으로 검색
				sql += " and p.prod_name like '%' || '" + searchVO.getSearchKeyword() + "' || '%'";
			} else if (searchVO.getSearchCondition().equals("2")) { // 2 : 상품가격으로 검색
				sql += " and p.price like '%' || '" + searchVO.getSearchKeyword() + "' || '%'";
			}
		}
		sql += " order by p.prod_no";
		
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		// ResultSet.TYPE_SCROLL_INSENSITIVE : 다른 어플리케이션에 의해 데이터베이스의 해당 레코드가 수정되면 ResultSet의 레코드에도 반영
		// ResultSet.CONCUR_UPDATABLE : 데이터 변경이 가능하도록 한다.
		ResultSet rs = stmt.executeQuery();
		
		rs.last(); // 커서를 제일 뒤로
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);
		
		HashMap<String,Object> map = new HashMap<String,Object>(); // key, value 형태로 저장
		map.put("count", new Integer(total)); // Object 타입으로 들어가야 하니까 Wrapper class
		
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
				
				vo.setProTranCode("판매중");
				
				if(rs.getString(8).trim().equals("1")) {
					vo.setProTranCode("구매완료");
				} else if (rs.getString(8).trim().equals("2")) {
					vo.setProTranCode("배송중");
				} else if (rs.getString(8).trim().equals("3")) {
					vo.setProTranCode("배송완료");
				} 
				
				list.add(vo); // ArrayList에 ProductVO 한 줄씩 뽑은거 담음
				if (!rs.next()) { // 찾을 결과가 없으면 
					break; // for문 빠져나옴
				}
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
	
	// 상품 정보 수정 
	public void updateProduct(ProductVO productVO) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송 
		String sql = "update product set prod_name = ?, prod_detail = ?, manufacture_day = ?, price = ?, image_file = ? "
					+ "where prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		
		// 3. 결과 확인
		int confirm = stmt.executeUpdate();
		if (confirm == 1) { // 디버깅 위함 
			System.out.println("product update 완료!");
			System.out.println(productVO);
		} else {
			System.out.println("product update 실패!");
		}
		
		con.close();
	}
	
}
