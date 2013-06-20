package com.sino.ams.newasset.report.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.newasset.bean.AssetsReportDateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-4
 * Time: 13:30:11
 * To change this template use File | Settings | File Templates.
 */
public class DeptAssetsReportDTO extends CommonRecordDTO {
	
	public DeptAssetsReportDTO() {
//		this.accountingPeriod = new SimpleCalendar();
	}
	
    private String company = "";
    private String deptName = "";
    private String itemQty = "";
    private String sumCost = "";
    private String assetsRate = "";
    private String responsibilityDept = "";
    private int organizationId;
//    private String month = "";
//    private String year = "";
    private String deptAssetsType = "";
    private String countyCode = "";
//    private String periodName = "";//会计期间
//    private SimpleCalendar accountingPeriod = null;//会计期间
    
    private AssetsReportDateUtil ardu = null;

//    public String getPeriodName(){
//		return periodName; 
//	}
    
//    public void setPeriodName() {
//		this.periodName = ardu.getPeriodName(accountingPeriod);
//	}

//	public void setPeriodName(String periodName) {
//		this.periodName = periodName;
//	}

	public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getDeptAssetsType() {
        return deptAssetsType;
    }

    public void setDeptAssetsType(String deptAssetsType) {
        this.deptAssetsType = deptAssetsType;
    }

//    public String getMonth() {
//        return month;
//    }

//    public void setMonth(String month) {
//        this.month = month;
//    }
    
//    public void setMonth() {
//    	this.month = ardu.getMonth(accountingPeriod);
//    }

//    public String getYear() {
//        return year;
//    }

//    public void setYear(String year) {
//        this.year = year;
//    }
    
//    public void setYear() {
//    	this.year = ardu.getYear(accountingPeriod);
//  }

    private String projectName = "";

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getSumCost() {
        return sumCost;
    }

    public void setSumCost(String sumCost) {
        this.sumCost = sumCost;
    }

    public String getAssetsRate() {
        return assetsRate;
    }

    public void setAssetsRate(String assetsRate) {
        this.assetsRate = assetsRate;
    }

    
    /**
     * 
     * Function:		获取会计期间
     * @return			SimpleCalendar
     * @author  		李轶
     * @throws 			CalendarException 
     * @Version 		0.1
     * @Date:   		May 31, 2009
     */
//	public SimpleCalendar getAccountingPeriod() throws CalendarException {
//		accountingPeriod.setCalPattern(getCalPattern());
//		return accountingPeriod;
//	}

	/**
	 * Function:		设置会计期间
	 * @param 			accountingPeriod
	 * @throws 			CalendarException
	 * @author  		李轶
	 * @Version 		0.1
	 * @Date:   		May 31, 2009
	 */
//	public void setAccountingPeriod(String accountingPeriod) throws CalendarException {
//		this.accountingPeriod.setCalendarValue(accountingPeriod);
//	}

	
}
