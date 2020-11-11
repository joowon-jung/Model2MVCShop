package com.model2.mvc.service.domain;

import java.sql.Date;

public class Review {

	private int reviewNo; // ���� ��ȣ
	private Product reviewProd; // ���� ��ǰ ���� - Product
	private User writer; // ���� �� ��� ���� - User
	private Purchase reviewPurchase; // ���� ���� - Purchase
	private String title; // ����
	private String contents; // ����
	private Date writeDate; // �ۼ� ����

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public Product getReviewProd() {
		return reviewProd;
	}

	public void setReviewProd(Product reviewProd) {
		this.reviewProd = reviewProd;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public Purchase getReviewPurchase() {
		return reviewPurchase;
	}

	public void setReviewPurchase(Purchase reviewPurchase) {
		this.reviewPurchase = reviewPurchase;
	}

	@Override
	public String toString() {
		return "Review [reviewNo=" + reviewNo + ", reviewProd=" + reviewProd + ", writer=" + writer
				+ ", reviewPurchase=" + reviewPurchase + ", title=" + title + ", contents=" + contents + ", writeDate="
				+ writeDate + "]";
	}

}
