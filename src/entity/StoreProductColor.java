package entity;
/**
 * ÉÌÆ·ÑÕÉ«
 * @author HEXT
 *
 */
public class StoreProductColor {
	private int pcID;
	private String pcName;
	public int getPcID() {
		return pcID;
	}
	public void setPcID(int pcID) {
		this.pcID = pcID;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	@Override
	public String toString() {
		return "StoreProductColor [pcID=" + pcID + ", pcName=" + pcName + "]";
	}
	
	
	

}
