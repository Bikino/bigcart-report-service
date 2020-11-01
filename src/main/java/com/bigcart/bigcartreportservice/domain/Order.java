package com.bigcart.bigcartreportservice.domain;

import java.time.LocalDate;
import java.util.Set;


public class Order {
	private long id;
    private long userId;
    private double totalAmount;
    private long addressId;
    private long paymentId;
    private LocalDate creationDate;
    private Set<OrderDetail> orderDetails;
    
    public Order() {
		
	}
	public Order(long id, long userId, double totalAmount, long addressId, long paymentId, LocalDate creationDate,
			Set<OrderDetail> orderDetails) {
		super();
		this.id = id;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.addressId = addressId;
		this.paymentId = paymentId;
		this.creationDate = creationDate;
		this.orderDetails = orderDetails;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
    

}
