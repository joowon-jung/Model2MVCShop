package com.model2.mvc.service.domain;

import java.sql.Date;


public class Product {
	
	private int prodNo; // 상품 번호 | prod_no
	private String prodName; // 상품명 | prod_name
	private String prodDetail; // 상품 상세 정보 | prod_detail
	private String manuDate; // 상품 제조 일자 | manufacture_day
	private int price; // 상품 가격 | price
	private String fileName; // 상품 이미지 파일 이름 | image_file 
	private Date regDate; // java.sql.Data 타입의 상품 등록 일자 | reg_date 
	private String proTranCode;
	
	public Product(){
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

	@Override
	public String toString() {
		return "Product [prodNo=" + prodNo + ", prodName=" + prodName + ", prodDetail=" + prodDetail + ", manuDate="
				+ manuDate + ", price=" + price + ", fileName=" + fileName + ", regDate=" + regDate + ", proTranCode="
				+ proTranCode + "]";
	}
}