package entity;

public class StoreCollects {
	private int collectID;
	private String productID;
	private int userID;
	public int getCollectID() {
		return collectID;
	}
	public void setCollectID(int collectID) {
		this.collectID = collectID;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	@Override
	public String toString() {
		return "StoreCollects [collectID=" + collectID + ", productID="
				+ productID + ", userID=" + userID + "]";
	}
	public StoreCollects() {
		super();
	}
	
	
}
