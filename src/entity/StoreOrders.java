package entity;

import java.util.Date;

public class StoreOrders {
	private String orderID;
	private int userID;
	private Date orderDate;
	private String orderConsignee;
	private String orderAddress;
	private String orderTelephone;
	private double orderSumPrice;
	private int orderState;
	private String orderDesc;
	private int pmcID;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderConsignee() {
		return orderConsignee;
	}

	public void setOrderConsignee(String orderConsignee) {
		this.orderConsignee = orderConsignee;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOrderTelephone() {
		return orderTelephone;
	}

	public void setOrderTelephone(String orderTelephone) {
		this.orderTelephone = orderTelephone;
	}

	public double getOrderSumPrice() {
		return orderSumPrice;
	}

	public void setOrderSumPrice(double orderSumPrice) {
		this.orderSumPrice = orderSumPrice;
	}

	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public int getPmcID() {
		return pmcID;
	}

	public void setPmcID(int pmcID) {
		this.pmcID = pmcID;
	}

	
}
