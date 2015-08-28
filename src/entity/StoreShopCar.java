package entity;

public class StoreShopCar {
	String shopCarID;
	String productID;
	int bayCount;
	int userID;

	public String getShopCarID() {
		return shopCarID;
	}

	public void setShopCarID(String shopCarID) {
		this.shopCarID = shopCarID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getBayCount() {
		return bayCount;
	}

	public void setBayCount(int bayCount) {
		this.bayCount = bayCount;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "StoreShopCar [bayCount=" + bayCount + ", productID="
				+ productID + ", shopCarID=" + shopCarID + ", userID=" + userID
				+ "]";
	}

}
