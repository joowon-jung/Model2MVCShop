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

	// 상품 정보 등록을 위한 DBMS 수행
	public void insertProduct(Product productVO) throws Exception {
		
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
	public Product findProduct(int prodNo) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		String sql = "select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		// 3. 결과 확인 
		ResultSet rs = stmt.executeQuery();
		
		Product productVO = null;
		while (rs.next()) {
			productVO = new Product(); // 인스턴스 생성
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
	public Map <String, Object> getProductList(Search searchVO) throws Exception {
		
		// 구체적인 HashMap 선언하지 않고 Map 으로 인터페이스 기반 프로그래밍 
		Map<String , Object>  map = new HashMap<String, Object>();
				
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
		
		// ==> totalCount 얻어오기 
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO 안의 totalCount : " + totalCount); // 디버깅 
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, searchVO);
		
		// 3. 결과 확인 
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(searchVO);
		
		List<Product> list = new ArrayList<Product>(); // List로 받아서 인터페이스 프로그래밍 
		
		while (rs.next()) {  // 결과가 있으면 ArrayList에 Product 정보 한줄씩 저장한다고 생각하기
			Product product = new Product();
			product.setProdNo(rs.getInt(1));
			product.setProdName(rs.getString(2));
			product.setProdDetail(rs.getString(3));
			product.setManuDate(rs.getString(4));
			product.setPrice(rs.getInt(5));
			product.setFileName(rs.getString(6));
			product.setRegDate(rs.getDate(7));
			
			product.setProTranCode("판매중");
			if(rs.getString(8).trim().equals("1")) {
				product.setProTranCode("구매완료");
			} else if (rs.getString(8).trim().equals("2")) {
				product.setProTranCode("배송중");
			} else if (rs.getString(8).trim().equals("3")) {
				product.setProTranCode("배송완료");
			} 
			
			list.add(product);
		}
		
		// ==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount)); // 검색한 제품 정보가 몇개인지랑
		// ==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list); // 현재 페이지에 대한 제품 정보만 찾은걸 같이 map 에 담아서 보냄!

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	// 유저 정보 수정 
	public void updateProduct(Product productVO) throws Exception {
		
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
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount) return
	private int getTotalCount(String sql) throws Exception { // getProductList에서 sql문을 인자값으로 주고 있음

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		// select count(*)
		// from ( select * from product order by prod_no )
		// countTable
		// 이런 식으로 몇개의 상품 정보가 찾아지는지 카운팅 하고 있음!

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) { // 결과가 있으면
			totalCount = rs.getInt(1); 	// 첫번째 결과 값을 (어차피 갯수 세는거니까 결과가 하나임)
										// int형으로 totalCount에 넣음 
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}
	
	// [ 게시판 currentPage Row 만 return ]
	// * ROWNUM 활용하여 효율적인 query문 날리기 위한 메소드
	// 한 페이지에 3줄씩만 나오니까 효율적으로 3줄만 찾게끔 하기 -> 지금까지는 처음부터 끝까지 다 찾았었음
	private String makeCurrentPageSql(String sql, Search search) { // getProductList에서 sql문과 search를 인자값으로 주고 있음
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
								" 	FROM (	"+sql+" ) inner_table "+
								"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();

		// select *
		// from ( select inner_table.*, rownum as row_seq
		// 		  from ( select * from product order by prod_no ) inner_table
		// 		  where rownum <= 3 / 6 )
		// where row_seq between 1 and 3 / 4 and 6 이런식으로 될 것 !  
		// rownum 을 사용해서 한 페이지에 123 / 456 / 789 만 찾게끔 해주고 있음

		System.out.println("ProductDAO 안의 makeCurrentSql : " + sql);

		return sql;
	}
}
