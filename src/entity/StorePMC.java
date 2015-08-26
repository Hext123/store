package entity;
/**
 * 物业管理
 * @author HEXT
 *
 */
public class StorePMC {
	private int pmcID;
	private String pmcLoginName;
	private String pmcLoginPwd;
	private String pmcName;
	public int getPmcID() {
		return pmcID;
	}
	public void setPmcID(int pmcID) {
		this.pmcID = pmcID;
	}
	public String getPmcLoginName() {
		return pmcLoginName;
	}
	public void setPmcLoginName(String pmcLoginName) {
		this.pmcLoginName = pmcLoginName;
	}
	public String getPmcLoginPwd() {
		return pmcLoginPwd;
	}
	public void setPmcLoginPwd(String pmcLoginPwd) {
		this.pmcLoginPwd = pmcLoginPwd;
	}
	public String getPmcName() {
		return pmcName;
	}
	public void setPmcName(String pmcName) {
		this.pmcName = pmcName;
	}
	@Override
	public String toString() {
		return "StorePMC [pmcID=" + pmcID + ", pmcLoginName=" + pmcLoginName
				+ ", pmcLoginPwd=" + pmcLoginPwd + ", pmcName=" + pmcName + "]";
	}
	
	
	
	
}
