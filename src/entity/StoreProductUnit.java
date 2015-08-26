package entity;

/**
 * 商品单位
 * 
 * @author HEXT
 * 
 */
public class StoreProductUnit {
	private int puID;
	private String puName;

	public int getPuID() {
		return puID;
	}

	public void setPuID(int puID) {
		this.puID = puID;
	}

	public String getPuName() {
		return puName;
	}

	public void setPuName(String puName) {
		this.puName = puName;
	}

	@Override
	public String toString() {
		return "StoreProductUnit [puID=" + puID + ", puName=" + puName + "]";
	}

}
