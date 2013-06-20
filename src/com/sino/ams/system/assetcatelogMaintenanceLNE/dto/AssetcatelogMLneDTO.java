package com.sino.ams.system.assetcatelogMaintenanceLNE.dto;

import com.sino.ams.bean.CommonRecordDTO;

public class AssetcatelogMLneDTO extends CommonRecordDTO{
	 private String contentCode="";
	 private String contentName="";
	 private String nleName="";
	 private String nleCode="";
	 private String matchCode="";
	 private String matchType="";
	 private String id="";
	 private String act="";
	 private String matchDesc="";
	 private String lneName="";
	 private String lneCode="";
	 private String naCode="";
	 private String value="";
		
		
	 public String getLneName() {
		return lneName;
	}
	public void setLneName(String lneName) {
		this.lneName = lneName;
	}
	public String getLneCode() {
		return lneCode;
	}
	public void setLneCode(String lneCode) {
		this.lneCode = lneCode;
	}
	public String getNaCode() {
		return naCode;
	}
	public void setNaCode(String naCode) {
		this.naCode = naCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMatchDesc() {
		return matchDesc;
	}
	public void setMatchDesc(String matchDesc) {
		this.matchDesc = matchDesc;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getNleName() {
		return nleName;
	}
	public void setNleName(String nleName) {
		this.nleName = nleName;
	}
	public String getNleCode() {
		return nleCode;
	}
	public void setNleCode(String nleCode) {
		this.nleCode = nleCode;
	}
}
