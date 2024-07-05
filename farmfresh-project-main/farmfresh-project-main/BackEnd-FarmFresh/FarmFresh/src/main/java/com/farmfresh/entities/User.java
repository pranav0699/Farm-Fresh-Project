package com.farmfresh.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(unique = true, length = 50)
	private String email;

	@Column(length = 30)
	private String password;

	@Column(name = "phone_no", length = 15)
	private String phoneNo;

	@Column(name = "Address", length = 200)
	private String address;

	@Column(length = 20)
	private String firstname;

	@Column(length = 20)
	private String lastname;

	@Column(name = "is_admin")
	private boolean isadmin;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Orders> orders;

	public User() {
		System.out.println("User Constructor invoked");
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public User(String email, String password, String phoneNo, String address, String firstname, String lastname,
			boolean isadmin) {
		super();
		this.email = email;
		this.password = password;
		this.phoneNo = phoneNo;
		this.address = address;
		this.firstname = firstname;
		this.lastname = lastname;
		this.isadmin = isadmin;
	}
	
	

	public User(Integer userId, String email, String password, String phoneNo, String address, String firstname,
			String lastname, boolean isadmin) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.phoneNo = phoneNo;
		this.address = address;
		this.firstname = firstname;
		this.lastname = lastname;
		this.isadmin = isadmin;
	}
	
	public User(Integer userId, String email, String password, String phoneNo, String address, String firstname,
			String lastname) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.phoneNo = phoneNo;
		this.address = address;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isIsadmin() {
		return isadmin;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", phoneNo=" + phoneNo
				+ ", address=" + address + ", firstname=" + firstname + ", lastname=" + lastname + ", orders=" + orders
				+ "]";
	}

}
