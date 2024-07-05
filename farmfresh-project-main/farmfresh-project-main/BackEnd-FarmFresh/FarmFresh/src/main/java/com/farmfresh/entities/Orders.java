package com.farmfresh.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "payment_status", length = 1)
	private boolean paymentStatus;

	@Column(name = "delivery_status", length = 1)
	private boolean deliveryStatus;

	@OneToMany(mappedBy = "orders")
	@JsonIgnore
	private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "place_order_date")
	private Date placeOrderDate;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	public Orders() {
		System.out.println("Orders Constructor invoked");
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public boolean isDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getPlaceOrderDate() {
		return placeOrderDate;
	}

	public void setPlaceOrderDate(Date placeOrderDate) {
		this.placeOrderDate = placeOrderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", paymentStatus=" + paymentStatus + ", deliveryStatus=" + deliveryStatus
				+ ", orderDetails=" + orderDetails + ", user=" + user + ", placeOrderDate=" + placeOrderDate
				+ ", deliveryDate=" + deliveryDate + "]";
	}

}
