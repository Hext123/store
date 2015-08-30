package entity;

import java.util.Date;

/**
 * ÉÌÆ·
 * @author HEXT
 *
 */
public class StoreProducts {

	private String productID;
	private String productName;
	private String productImages;
	private double productPrice;
	private double productRealityPrice;
	private int ptID;
	private int puID;
	private int pcID;
	private int psID;
	private int productSaleCount;
	private int productStock;
	private String productDesc;
	private int pmcID;
	private Date productDate;
	private int productState;
	
	//-------------------¸¨Öú×Ö¶Î----------------

	private String ptName;
	private String puName;
	private String pcName;
	private String psName;
	private String pmcName;

	private String order;
	private int desc;

	private String bayCount;
	private String shopCarID;

	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getDesc() {
		return desc;
	}
	public void setDesc(int desc) {
		this.desc = desc;
	}
	
	
	public String getShopCarID() {
		return shopCarID;
	}
	public void setShopCarID(String shopCarID) {
		this.shopCarID = shopCarID;
	}
	public String getBayCount() {
		return bayCount;
	}
	public void setBayCount(String bayCount) {
		this.bayCount = bayCount;
	}
	public Date getProductDate() {
		return productDate;
	}
	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImages() {
		return productImages;
	}
	public void setProductImages(String productImages) {
		this.productImages = productImages;
	}

	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public double getProductRealityPrice() {
		return productRealityPrice;
	}
	public void setProductRealityPrice(double productRealityPrice) {
		this.productRealityPrice = productRealityPrice;
	}
	public int getPtID() {
		return ptID;
	}
	public void setPtID(int ptID) {
		this.ptID = ptID;
	}
	public int getPuID() {
		return puID;
	}
	public void setPuID(int puID) {
		this.puID = puID;
	}
	public int getPcID() {
		return pcID;
	}
	public void setPcID(int pcID) {
		this.pcID = pcID;
	}
	public int getPsID() {
		return psID;
	}
	public void setPsID(int psID) {
		this.psID = psID;
	}
	public int getProductSaleCount() {
		return productSaleCount;
	}
	public void setProductSaleCount(int productSaleCount) {
		this.productSaleCount = productSaleCount;
	}
	public int getProductStock() {
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public int getPmcID() {
		return pmcID;
	}
	public void setPmcID(int pmcID) {
		this.pmcID = pmcID;
	}
	
	public int getProductState() {
		return productState;
	}
	public void setProductState(int productState) {
		this.productState = productState;
	}
	public String getPtName() {
		return ptName;
	}
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	public String getPuName() {
		return puName;
	}
	public void setPuName(String puName) {
		this.puName = puName;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	public String getPmcName() {
		return pmcName;
	}
	public void setPmcName(String pmcName) {
		this.pmcName = pmcName;
	}

	
	
	
	
	public StoreProducts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StoreProducts(String productName, int ptID, int pmcID,
			int productState, String order, int desc) {
		super();
		this.productName = productName;
		this.ptID = ptID;
		this.pmcID = pmcID;
		this.productState = productState;
		this.order = order;
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "StoreProducts [bayCount=" + bayCount + ", desc=" + desc
				+ ", order=" + order + ", pcID=" + pcID + ", pcName=" + pcName
				+ ", pmcID=" + pmcID + ", pmcName=" + pmcName
				+ ", productDate=" + productDate + ", productDesc="
				+ productDesc + ", productID=" + productID + ", productImages="
				+ productImages + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", productRealityPrice="
				+ productRealityPrice + ", productSaleCount="
				+ productSaleCount + ", productState=" + productState
				+ ", productStock=" + productStock + ", psID=" + psID
				+ ", psName=" + psName + ", ptID=" + ptID + ", ptName="
				+ ptName + ", puID=" + puID + ", puName=" + puName
				+ ", shopCarID=" + shopCarID + "]";
	}
	


	
}
