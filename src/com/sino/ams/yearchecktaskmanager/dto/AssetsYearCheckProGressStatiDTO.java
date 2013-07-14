package com.sino.ams.yearchecktaskmanager.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;

public class AssetsYearCheckProGressStatiDTO extends AMSBaseDTO {
	
	private String statId="";
	private String companyCode = "";
	private String company = "";
	private int organizationId;
	private String respDeptCode = "";//���β���CODE
	private String respDeptName = "";//���β�������
	private int sumAssets ;//�ʲ�����
	private int sumCheckAssets;//���̵��ʲ�����
	private String PercentByAssets ="";//���ʲ���ɵİٷֱ�
	private int sumWorkOrderObjectCode ;//�ص�����
	private int sumCheckWorkOrderObjectCode;//���̵�ص�����
	private String PercentByObject = "";//���ص���ɵİٷֱ�
	
	private String organizationOpt = "";//OU ѡ��
	
	public String getPercentByAssets() {
		return PercentByAssets;
	}
	public void setPercentByAssets(String percentByAssets) {
		PercentByAssets = percentByAssets;
	}
	public String getPercentByObject() {
		return PercentByObject;
	}
	public void setPercentByObject(String percentByObject) {
		PercentByObject = percentByObject;
	}
	public String getOrganizationOpt() {
		return organizationOpt;
	}
	public void setOrganizationOpt(String organizationOpt) {
		this.organizationOpt = organizationOpt;
	}
	public String getStatId() {
		return statId;
	}
	public void setStatId(String statId) {
		this.statId = statId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public String getRespDeptCode() {
		return respDeptCode;
	}
	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}
	public String getRespDeptName() {
		return respDeptName;
	}
	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}
	public int getSumAssets() {
		return sumAssets;
	}
	public void setSumAssets(int sumAssets) {
		this.sumAssets = sumAssets;
	}
	public int getSumCheckAssets() {
		return sumCheckAssets;
	}
	public void setSumCheckAssets(int sumCheckAssets) {
		this.sumCheckAssets = sumCheckAssets;
	}
	public int getSumWorkOrderObjectCode() {
		return sumWorkOrderObjectCode;
	}
	public void setSumWorkOrderObjectCode(int sumWorkOrderObjectCode) {
		this.sumWorkOrderObjectCode = sumWorkOrderObjectCode;
	}
	public int getSumCheckWorkOrderObjectCode() {
		return sumCheckWorkOrderObjectCode;
	}
	public void setSumCheckWorkOrderObjectCode(int sumCheckWorkOrderObjectCode) {
		this.sumCheckWorkOrderObjectCode = sumCheckWorkOrderObjectCode;
	}
	
	
}
