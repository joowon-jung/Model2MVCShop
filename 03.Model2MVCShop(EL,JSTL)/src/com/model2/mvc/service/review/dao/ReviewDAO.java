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

	// ���� ����� ���� DBMS ����
	public void insertReview(Review review) throws Exception {
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
		String sql = "insert into review values "
				+ "(seq_transaction_review_no.nextval, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYY-MM-DD'))";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, review.getReviewProd().getProdNo());
		stmt.setString(2, review.getWriter().getUserId());
		stmt.setInt(3, review.getReviewPurchase().getTranNo());
		stmt.setString(4, review.getTitle());
		stmt.setString(5, review.getContents());
		
		// 3. ��� Ȯ��
		int confirm = stmt.executeUpdate();

		if (confirm == 1) { // ����� ����
			System.out.println("review insert �Ϸ�!");
			System.out.println(review);
		} else {
			System.out.println("eview insert ����!");
		}
		
		con.close();
		
	}
	
	// ���� ���⸦ ���� DBMS ���� (tranNo�� ã������ �ְ�, reviewNo�� ã������ ������)
	public Review findReview(int no) throws Exception {
		
		ProductService pdService = new ProductServiceImpl();
		UserService usService = new UserServiceImpl();
		PurchaseService psService = new PurchaseServiceImpl();
		
		// 1. ����
		Connection con = DBUtil.getConnection();
		
		// 2. ���� ����
		String sql = "select * from review where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, no);
		
		// 3. ��� Ȯ�� 
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
