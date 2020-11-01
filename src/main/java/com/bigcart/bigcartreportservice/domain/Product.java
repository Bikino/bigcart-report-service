package com.bigcart.bigcartreportservice.domain;

public class Product {
	
	// Generate Getters and Setters...
	 private Integer productId;
	    private String name;
	    private Integer categoryId;
	    private String description;
	    private String specs;
	    private Boolean isApproved;

		public Product() {
			
			
		}
		public Product(Integer productId, String name, Integer categoryId, String description, String specs,
				Boolean isApproved) {
			super();
			this.productId = productId;
			this.name = name;
			this.categoryId = categoryId;
			this.description = description;
			this.specs = specs;
			this.isApproved = isApproved;
			
		}
		public Integer getProductId() {
			return productId;
		}
		public void setProductId(Integer productId) {
			this.productId = productId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getSpecs() {
			return specs;
		}
		public void setSpecs(String specs) {
			this.specs = specs;
		}
		public Boolean getIsApproved() {
			return isApproved;
		}
		public void setIsApproved(Boolean isApproved) {
			this.isApproved = isApproved;
		}
}
