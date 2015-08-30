package entity;

public class StoreAddress {
	private int addressID;
	private String provinceCityDistrict;
	private String addressDetail;
	private String consignee;
	private String telephone;
	private int userID;

	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public String getProvinceCityDistrict() {
		return provinceCityDistrict;
	}

	public void setProvinceCityDistrict(String provinceCityDistrict) {
		this.provinceCityDistrict = provinceCityDistrict;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "StoreAddress [addressDetail=" + addressDetail + ", addressID="
				+ addressID + ", consignee=" + consignee
				+ ", provinceCityDistrict=" + provinceCityDistrict
				+ ", telephone=" + telephone + ", userID=" + userID + "]";
	}

}
