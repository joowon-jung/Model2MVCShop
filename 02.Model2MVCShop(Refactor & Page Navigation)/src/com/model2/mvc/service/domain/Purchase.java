package com.model2.mvc.service.domain;

import java.sql.Date;

public class Purchase {
	
	private int tranNo; // 구매 번호 
	private Product purchaseProd; // 구매 물품 정보 - ProductVO 
	private User buyer; // 구매자 정보 - UserVO 
	private String paymentOption; // 지불 방식 
	private String receiverName; // 받는 사람 이름 
	private String receiverPhone; // 받는 사람 전화번호 
	private String divyAddr; // 배송지 주소
	private String divyRequest; // 배송시 요구사항 
	private String tranCode; // 구매 상태 코드 - 구매완료 / 배송중 / 배송완료
	private Date orderDate; // 구매 일자 
	private String divyDate; // 배송 희망 일자 
	
	public Purchase(){
	}
	
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public String getDivyAddr() {
		return divyAddr;
	}
	public void setDivyAddr(String divyAddr) {
		this.divyAddr = divyAddr;
	}
	public String getDivyDate() {
		return divyDate;
	}
	public void setDivyDate(String divyDate) {
		this.divyDate = divyDate;
	}
	public String getDivyRequest() {
		return divyRequest;
	}
	public void setDivyRequest(String divyRequest) {
		this.divyRequest = divyRequest;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public Product getPurchaseProd() {
		return purchaseProd;
	}
	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	@Override
	public String toString() {
		return "Purchase [tranNo=" + tranNo + ", purchaseProd=" + purchaseProd + ", buyer=" + buyer + ", paymentOption="
				+ paymentOption + ", receiverName=" + receiverName + ", receiverPhone=" + receiverPhone + ", divyAddr="
				+ divyAddr + ", divyRequest=" + divyRequest + ", tranCode=" + tranCode + ", orderDate=" + orderDate
				+ ", divyDate=" + divyDate + "]";
	}
}