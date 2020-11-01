package com.bigcart.bigcartreportservice.domain;

public class OrderDetail {
	  private long id;
	    private String name;
	    private double price;
	    private int quantity;
	    
	    
		public OrderDetail() {
			super();
		}
		public OrderDetail(long id, String name, double price, int quantity) {
			super();
			this.id = id;
			this.name = name;
			this.price = price;
			this.quantity = quantity;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	    
	    
	    
}
