package com.model2.mvc.service.domain;

import java.sql.Date;


public class Product {
	
	private int prodNo; // ��ǰ ��ȣ | prod_no
	private String prodName; // ��ǰ�� | prod_name
	private String prodDetail; // ��ǰ �� ���� | prod_detail
	private String manuDate; // ��ǰ ���� ���� | manufacture_day
	private int price; // ��ǰ ���� | price
	private String fileName; // ��ǰ �̹��� ���� �̸� | image_file 
	private Date regDate; // java.sql.Data Ÿ���� ��ǰ ��� ���� | reg_date 
	private String proTranCode;
	private Purchase proPurchase; // ��ǰ�������� ��ǰ�� �������� �������� �߰� �Ϸ��� 
	
	public Product(){
	}
	
	// 10-14 ���� �׽�Ʈ�� 
	public Product(String prodName, String prodDetail, String manuDate, int price, String fileName) {
		super();
		this.prodName = prodName;
		this.prodDetail = prodDetail;
		this.manuDate = manuDate;
		this.price = price;
		this.fileName = fileName;
	}
	
	public String getProTranCode() {
		return proTranCode;
	}
	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getManuDate() {
		return manuDate;
	}
	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getProdDetail() {
		return prodDetail;
	}
	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getProdNo() {
		return prodNo;
	}
	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Purchase getProPurchase() {
		return proPurchase;
	}

	public void setProPurchase(Purchase proPurchase) {
		this.proPurchase = proPurchase;
	}

	@Override
	public String toString() {
		return "Product [prodNo=" + prodNo + ", prodName=" + prodName + ", prodDetail=" + prodDetail + ", manuDate="
				+ manuDate + ", price=" + price + ", fileName=" + fileName + ", regDate=" + regDate + ", proTranCode="
				+ proTranCode + ", proPurchase=" + proPurchase + "]";
	}
	
}