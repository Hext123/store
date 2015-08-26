package entity;
/**
 * 商品规格
 * @author HEXT
 *
 */
public class StoreProductSpec {
	private int psID;
	private String psName;
	public int getPsID() {
		return psID;
	}
	public void setPsID(int psID) {
		this.psID = psID;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	@Override
	public String toString() {
		return "StoreProductSpec [psID=" + psID + ", psName=" + psName + "]";
	}
	
}
