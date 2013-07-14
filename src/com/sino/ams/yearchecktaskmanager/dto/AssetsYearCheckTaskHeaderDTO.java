package com.sino.ams.yearchecktaskmanager.dto;


import com.sino.ams.appbase.dto.AMSBaseDTO;
/**
 * 对应盘点任务基准日表
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskHeaderDTO extends AMSBaseDTO{
	
	private String headerId="";//
	private String taskName = "";//任务名称
	private String assetsType = "";//资产类型 非实地资产 实地无线 实地非无线 3种
    private String nonAddressDistributeMethod = "";//非实地资产的工单下发方式
    private String parentOrderNumber = "";//父任务编号
    
    private String nonAddressCategory="";//非实地资产种类
	
    private String parentOrderName="";//父任务名称
    private String parentOrderType="";//父任务类型
    private String parentOrderTypeName="";//父任务类型名称
    
    private String assetsBigClass="";//资产大类，上市MIS，TD，通服TF，铁通TT
    private String remark = "";//备注
    
    //-------------------------------------------
    
    
    
	public String getTaskName() {
		return taskName;
	}
	public String getAssetsBigClass() {
		return assetsBigClass;
	}
	public void setAssetsBigClass(String assetsBigClass) {
		this.assetsBigClass = assetsBigClass;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getParentOrderTypeName() {
		return parentOrderTypeName;
	}
	public void setParentOrderTypeName(String parentOrderTypeName) {
		this.parentOrderTypeName = parentOrderTypeName;
	}
	public String getParentOrderName() {
		return parentOrderName;
	}
	public void setParentOrderName(String parentOrderName) {
		this.parentOrderName = parentOrderName;
	}
	public String getParentOrderType() {
		return parentOrderType;
	}
	public void setParentOrderType(String parentOrderType) {
		this.parentOrderType = parentOrderType;
	}
	public String getNonAddressCategory() {
		return nonAddressCategory;
	}
	public void setNonAddressCategory(String nonAddressCategory) {
		this.nonAddressCategory = nonAddressCategory;
	}
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getAssetsType() {
		return assetsType;
	}
	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}
	public String getNonAddressDistributeMethod() {
		return nonAddressDistributeMethod;
	}
	public void setNonAddressDistributeMethod(String nonAddressDistributeMethod) {
		this.nonAddressDistributeMethod = nonAddressDistributeMethod;
	}
	public String getParentOrderNumber() {
		return parentOrderNumber;
	}
	public void setParentOrderNumber(String parentOrderNumber) {
		this.parentOrderNumber = parentOrderNumber;
	}
	public String toString(){
		return "[taskName="+this.taskName+",assetsType="+this.assetsType+",nonAddressDistributeMethod="+
		 this.nonAddressDistributeMethod+",nonAddressCategory="+nonAddressCategory+",parentOrderNumber="+
		 this.parentOrderNumber+",parentOrderName="+parentOrderName+",parentOrderType="+parentOrderType+"]";
	}
}
