package com.model2.mvc.service.domain;

import java.sql.Date;

public class Purchase {
	
	private int tranNo; // ���� ��ȣ 
	private Product purchaseProd; // ���� ��ǰ ���� - ProductVO 
	private User buyer; // ������ ���� - UserVO 
	private String paymentOption; // ���� ��� 
	private String receiverName; // �޴� ��� �̸� 
	private String receiverPhone; // �޴� ��� ��ȭ��ȣ 
	private String divyAddr; // ����� �ּ�
	private String divyRequest; // ��۽� �䱸���� 
	private String tranCode; // ���� ���� �ڵ� - ���ſϷ� / ����� / ��ۿϷ�
	private Date orderDate; // ���� ���� 
	private String divyDate; // ��� ��� ���� 
	
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