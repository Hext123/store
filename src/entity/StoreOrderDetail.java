package entity;

public class StoreOrderDetail {
	private int orderDetailID;
	private String orderID;
	private String productID;
	private double productPrice;
	private int buyCount;

	public int getOrderDetailID() {
		return orderDetailID;
	}

	public void setOrderDetailID(int orderDetailID) {
		this.orderDetailID = orderDetailID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	@Override
	public String toString() {
		return "StoreOrderDetail [buyCount=" + buyCount + ", orderDetailID="
				+ orderDetailID + ", orderID=" + orderID + ", productID="
				+ productID + ", productPrice=" + productPrice + "]";
	}

}
