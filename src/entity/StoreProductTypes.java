package entity;
/**
 * 商品类型
 * @author HEXT
 *
 */
public class StoreProductTypes {
	private int ptID;
	private int ptParentID;
	private String ptIconUrl;
	private String ptName;
	private String ptDesc;
	private int pmcID;
	public int getPtID() {
		return ptID;
	}
	public void setPtID(int ptID) {
		this.ptID = ptID;
	}
	public int getPtParentID() {
		return ptParentID;
	}
	public void setPtParentID(int ptParentID) {
		this.ptParentID = ptParentID;
	}
	public String getPtIconUrl() {
		return ptIconUrl;
	}
	public void setPtIconUrl(String ptIconUrl) {
		this.ptIconUrl = ptIconUrl;
	}
	public String getPtName() {
		return ptName;
	}
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	public String getPtDesc() {
		return ptDesc;
	}
	public void setPtDesc(String ptDesc) {
		this.ptDesc = ptDesc;
	}
	
	public int getPmcID() {
		return pmcID;
	}
	public void setPmcID(int pmcID) {
		this.pmcID = pmcID;
	}
	@Override
	public String toString() {
		return "StoreProductTypes [pmcID=" + pmcID + ", ptDesc=" + ptDesc
				+ ", ptID=" + ptID + ", ptIconUrl=" + ptIconUrl + ", ptName="
				+ ptName + ", ptParentID=" + ptParentID + "]";
	}
	public StoreProductTypes(int ptParentID, int pmcID) {
		super();
		this.ptParentID = ptParentID;
		this.pmcID = pmcID;
	}
	public StoreProductTypes() {
		super();
	}


	
	
	
}
