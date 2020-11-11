package com.model2.mvc.service.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class ReviewDAO {

	public ReviewDAO() {
		// TODO Auto-generated constructor stub
	}

	// 리뷰 등록을 위한 DBMS 수행
	public void insertReview(Review review) throws Exception {
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		String sql = "insert into review values "
				+ "(seq_transaction_review_no.nextval, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYY-MM-DD'))";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, review.getReviewProd().getProdNo());
		stmt.setString(2, review.getWriter().getUserId());
		stmt.setInt(3, review.getReviewPurchase().getTranNo());
		stmt.setString(4, review.getTitle());
		stmt.setString(5, review.getContents());
		
		// 3. 결과 확인
		int confirm = stmt.executeUpdate();

		if (confirm == 1) { // 디버깅 위함
			System.out.println("review insert 완료!");
			System.out.println(review);
		} else {
			System.out.println("eview insert 실패!");
		}
		
		con.close();
		
	}
	
	// 리뷰 보기를 위한 DBMS 수행 (tranNo로 찾을때도 있고, reviewNo로 찾을때도 있을듯)
	public Review findReview(int no) throws Exception {
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		PurchaseService psService = new PurchaseServiceImpl();
		
		// 1. 연결
		Connection con = DBUtil.getConnection();
		
		// 2. 쿼리 전송
		String sql = "select * from review where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, no);
		
		// 3. 결과 확인 
		ResultSet rs = stmt.executeQuery();
		
		Review review = null;
		while (rs.next()) {
			review = new Review();
			review.setReviewNo(rs.getInt(1));
			review.setReviewProd(pdService.getProduct(rs.getInt(2)));
			review.setWriter(usService.getUser(rs.getString(3)));
			review.setReviewPurchase(psService.getPurchase(rs.getInt(4)));
			review.setTitle(rs.getString(5));
			review.setContents(rs.getString(6));
			review.setWriteDate(rs.getDate(7));
		}
		con.close();

		return review;
	}
}
