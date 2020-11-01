package com.bigcart.bigcartreportservice.domain;

import java.util.Date;

public class BuyerDTO {
	
	 private int id ;
	    private String firstName;

	    private String lastName;

	    private String email;

	    private String phone;

	    private Date creationDateTime;
	    private String addresses;
	    private Date dateOfBirth ;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Date getCreationDateTime() {
			return creationDateTime;
		}
		public void setCreationDateTime(Date creationDateTime) {
			this.creationDateTime = creationDateTime;
		}
		public String getAddresses() {
			return addresses;
		}
		public void setAddresses(String addresses) {
			this.addresses = addresses;
		}
		public Date getDateOfBirth() {
			return dateOfBirth;
		}
		public void setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
		public BuyerDTO(int id, String firstName, String lastName, String email, String phone, Date creationDateTime,
				String addresses, Date dateOfBirth) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.phone = phone;
			this.creationDateTime = creationDateTime;
			this.addresses = addresses;
			this.dateOfBirth = dateOfBirth;
		}
		public BuyerDTO() {
			
		}
	    

}
