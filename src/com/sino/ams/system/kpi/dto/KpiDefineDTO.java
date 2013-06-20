package com.sino.ams.system.kpi.dto;

import com.sino.base.dto.CheckBoxDTO;

public class KpiDefineDTO extends CheckBoxDTO{
	  private String kpiCode = "";            
	  private String kpiName = "";            
	  private String kpiDesc = "";            
	  private long kpiValue  ;           
	  private String creationDate = "";       
	  private int createdBy;          
	  private String lastUpdateDate = "";     
	  private int lastUpdateBy;       
	  private String kpiType = "";            
	  private String isEnable = "";
	public String getKpiCode() {
		return kpiCode;
	}
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public String getKpiDesc() {
		return kpiDesc;
	}
	public void setKpiDesc(String kpiDesc) {
		this.kpiDesc = kpiDesc;
	}
	public long getKpiValue() {
		return kpiValue;
	}
	public void setKpiValue(long kpiValue) {
		this.kpiValue = kpiValue;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public int getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(int lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getKpiType() {
		return kpiType;
	}
	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
	}
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}           
	  
	  

}

