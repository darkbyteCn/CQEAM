package com.sino.ams.yearchecktaskmanager.dto;


import com.sino.ams.appbase.dto.AMSBaseDTO;
/**
 * ��Ӧ�̵������׼�ձ�
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskHeaderDTO extends AMSBaseDTO{
	
	private String headerId="";//
	private String taskName = "";//��������
	private String assetsType = "";//�ʲ����� ��ʵ���ʲ� ʵ������ ʵ�ط����� 3��
    private String nonAddressDistributeMethod = "";//��ʵ���ʲ��Ĺ����·���ʽ
    private String parentOrderNumber = "";//��������
    
    private String nonAddressCategory="";//��ʵ���ʲ�����
	
    private String parentOrderName="";//����������
    private String parentOrderType="";//����������
    private String parentOrderTypeName="";//��������������
    
    private String assetsBigClass="";//�ʲ����࣬����MIS��TD��ͨ��TF����ͨTT
    private String remark = "";//��ע
    
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
