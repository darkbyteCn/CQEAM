package com.sino.ams.newasset.report.dto;

import com.sino.ams.bean.CommonRecordDTO;

public class KpiInputDTO extends CommonRecordDTO{
		private String period="";
		private String companyCode;
		private String periodType="";
		private String kpiType;
		private float value;
		private int createdBy;
		private String createdDate;
		private int lastUpdateBy;
		private String periodTypes;
		private String indexType="";
		private String assetsType="";
		
		
		
		public void setPeriodTypes(String periodTypes) {
			this.periodTypes = periodTypes;
		}
		public String getIndexType() {
			return indexType;
		}
		public void setIndexType(String indexType) {
			this.indexType = indexType;
		}
		public String getAssetsType() {
			return assetsType;
		}
		public void setAssetsType(String assetsType) {
			this.assetsType = assetsType;
		}
		public String getPeriodTypes() {
			return periodTypes;
		}
		public void setPeriodtypes(String periodTypes) {
			this.periodTypes = periodTypes;
		}
		public void setLastUpdateBy(int lastUpdateBy) {
			this.lastUpdateBy = lastUpdateBy;
		}
		public String getPeriod() {
			return period;
		}
		public void setPeriod(String period) {
			this.period = period;
		}
		public String getCompanyCode() {
			return companyCode;
		}
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		public String getPeriodType() {
			return periodType;
		}
		public void setPeriodType(String periodType) {
			this.periodType = periodType;
		}
		public String getKpiType() {
			return kpiType;
		}
		public void setKpiType(String kpiType) {
			this.kpiType = kpiType;
		}
		public float getValue() {
			return value;
		}
		public void setValue(float value) {
			this.value = value;
		}
		public int getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(int createdBy) {
			this.createdBy = createdBy;
		}
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public int getLastUpdateBy() {
			return lastUpdateBy;
		}
}
