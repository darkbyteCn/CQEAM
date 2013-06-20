package com.sino.ams.match.amselementmatch.dto;

import com.sino.base.dto.CheckBoxDTO;

public class AmsElementMatchDTO extends CheckBoxDTO{
	
	public AmsElementMatchDTO() {
		super();
	}
	
//资产目录中的属性
	//资产目录ID
	private String contentId = "";
	
	//资产目录代码
	private String contentCode = "";
	
	//资产目录描述
	private String contentName = "";
	

//逻辑网络元素属性
	//逻辑网络元素ID
	private String amsLneId = "";
	//private int amsLneId;
	
	//网络专业1
	private String netCategory1= "";
	
	//网络专业2
	private String netCategory2= "";
	
	//专业1編碼
	private String netCategory1Code= "";
	
	//专业2編碼
	private String netCategory2Code= "";
	
	//网元编码
	private String netUnitCode = "";
	
	//逻辑网络元素
	private String logNetEle = "";
	
	//英文简称
	private String engAb = "";
	
//投资分类属性
	//投资分类属性ID
	private String amsCexId = "";
	
	//投资大类
	private String investCategory1 = "";
	
	//投资种类
	private String investCategory2 = "";
	
	//投资分类代码
	private String investCatCode = "";
	
	//投资分类名称
	private String investCatName = "";
	

//业务平台属性
	//业务平台属性ID
	private String amsOpeId = "";
	
	//业务平台编码
	private String opeCode = "";
	
	//业务平台名称
	private String opeName = "";
	

//网络层次属性
	//网络层次属性ID
//	private String amsLneId = "";
	
	//网络层次编码
	private String lneCode = "";
	
	//网络层次名称
	private String lneName = "";
	
	//访问类型
	private String accessType = "";
	
	//是否有效
	private String enabled = "";
	
	//成本属性
	private String costType = "";
	
	
	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	private String[] contentCodes = new String[]{};

	public String getAmsCexId() {
		return amsCexId;
	}

	public void setAmsCexId(String amsCexId) {
		this.amsCexId = amsCexId;
	}

	public String getAmsLneId() {
		return amsLneId;
	}

	public void setAmsLneId(String amsLneId) {
		this.amsLneId = amsLneId;
	}

	public String getAmsOpeId() {
		return amsOpeId;
	}

	public void setAmsOpeId(String amsOpeId) {
		this.amsOpeId = amsOpeId;
	}

	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getInvestCatCode() {
		return investCatCode;
	}

	public void setInvestCatCode(String investCatCode) {
		this.investCatCode = investCatCode;
	}

	public String getInvestCategory1() {
		return investCategory1;
	}

	public void setInvestCategory1(String investCategory1) {
		this.investCategory1 = investCategory1;
	}

	public String getInvestCategory2() {
		return investCategory2;
	}

	public void setInvestCategory2(String investCategory2) {
		this.investCategory2 = investCategory2;
	}

	public String getInvestCatName() {
		return investCatName;
	}

	public void setInvestCatName(String investCatName) {
		this.investCatName = investCatName;
	}

	public String getLneCode() {
		return lneCode;
	}

	public void setLneCode(String lneCode) {
		this.lneCode = lneCode;
	}

	public String getLneName() {
		return lneName;
	}

	public void setLneName(String lneName) {
		this.lneName = lneName;
	}

	public String getLogNetEle() {
		return logNetEle;
	}

	public void setLogNetEle(String logNetEle) {
		this.logNetEle = logNetEle;
	}

	public String getNetCategory1() {
		return netCategory1;
	}

	public void setNetCategory1(String netCategory1) {
		this.netCategory1 = netCategory1;
	}

	public String getNetCategory2() {
		return netCategory2;
	}

	public void setNetCategory2(String netCategory2) {
		this.netCategory2 = netCategory2;
	}

	public String getNetUnitCode() {
		return netUnitCode;
	}

	public void setNetUnitCode(String netUnitCode) {
		this.netUnitCode = netUnitCode;
	}

	public String getOpeCode() {
		return opeCode;
	}

	public void setOpeCode(String opeCode) {
		this.opeCode = opeCode;
	}

	public String getOpeName() {
		return opeName;
	}

	public void setOpeName(String opeName) {
		this.opeName = opeName;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String[] getContentCodes() {
		return contentCodes;
	}

	public void setContentCodes(String[] contentCodes) {
		this.contentCodes = contentCodes;
	}

	public String getEngAb() {
		return engAb;
	}

	public void setEngAb(String engAb) {
		this.engAb = engAb;
	}
	public String getNetCategory1Code() {
		return netCategory1Code;
	}

	public void setNetCategory1Code(String netCategory1Code) {
		this.netCategory1Code = netCategory1Code;
	}

	public String getNetCategory2Code() {
		return netCategory2Code;
	}

	public void setNetCategory2Code(String netCategory2Code) {
		this.netCategory2Code = netCategory2Code;
	}
	
	
}
