package com.sino.ams.newasset.report.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-14
 * Time: 16:50:02
 * To change this template use File | Settings | File Templates.
 * Changed by 李轶 at 2009-09-21， because add 条码粘贴覆盖率
 */
public class ReportInDataDTO extends CommonRecordDTO {
    private String reportId = "";//ID
    private String period = "" ;//会计期间
    private String managerGuideType = "";//管理指标类报表类型
    private int organizationId;//OU
    private String orgName="";//OU
    private int projectTrunAssets = 0;//考核期内工程转资额
    private int projectSumAssets = 0;//工程累计投入总额
    private int noTimelyReportNum = 0 ;//未及时上报次数
    private int assetsmentReportNum = 0;//考核期应上报次数
    private int assetsmentFalseNum = 0;//考核期内发生的转资资产不准确的数量
    private int assetsmentAssetsSum = 0;//考核期内转资资产总量
    private int completeCheckNum = 0;//实际完成的资产实物管理抽查盘点任务工单数量
    
    private int planCheckNum = 0;//计划规定的资产抽查盘点任务工单总数
    private int accountMatchCase = 0;//抽查中账实相符的资产数量
    private int checkAssetsSum = 0;//抽查资产总数量
    private int assetsCopNum = 0;//考核期内已完成的日常巡检盘点的工单数
    private int assetsCopSum = 0;//考核期间内计划的日常巡检盘点工单总数
    private int assetsMatchCase = 0;//盘点中账实相符的资产数量
    private int assetsCheckSum = 0;//盘点资产总数量
    private int accurateErrorNumber = 0;//核算相关的差错次数

    //Add by 李轶 at 2009-09-21
    private int curValue;   //粘贴条码的抽查设备数量
    private int totalValue;   //抽查设备总数
    private String companyCode = "";
    private String kpiCode = "";    //指标代码
    private String kpiName = "";    //指标名称
    private boolean isKpi = false;      //是否KPI指标
    private String value = ""; //值

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getKpiCode() {
        return kpiCode;
    }

    public void setKpiCode(String kpiCode) {
        this.kpiCode = kpiCode;
    }
 

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

     

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getManagerGuideType() {
        return managerGuideType;
    }

    public void setManagerGuideType(String managerGuideType) {
        this.managerGuideType = managerGuideType;
    }

 

    public int getCurValue() {
        return curValue;
    }

    public void setCurValue(int curValue) {
        this.curValue = curValue;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    /**
     * Function:       获取是否KPI指标
     * @return         String类型，‘Y’表示属于KPI指标，‘N’表示不属于KPI指标
     * Add by liyi at 2009-09-25, because this used in KPI 
     */
    public boolean getKpi() {
        return isKpi;
    }

    /**
     * Function:        设置是否属于KPI指标
     * @param kpi       String类型，‘Y’表示属于KPI指标，‘N’表示不属于KPI指标
     */
    public void setKpi(boolean kpi) {
        isKpi = kpi;
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getProjectTrunAssets() {
		return projectTrunAssets;
	}

	public void setProjectTrunAssets(int projectTrunAssets) {
		this.projectTrunAssets = projectTrunAssets;
	}

	public int getProjectSumAssets() {
		return projectSumAssets;
	}

	public void setProjectSumAssets(int projectSumAssets) {
		this.projectSumAssets = projectSumAssets;
	}

	public int getNoTimelyReportNum() {
		return noTimelyReportNum;
	}

	public void setNoTimelyReportNum(int noTimelyReportNum) {
		this.noTimelyReportNum = noTimelyReportNum;
	}

	public int getAssetsmentReportNum() {
		return assetsmentReportNum;
	}

	public void setAssetsmentReportNum(int assetsmentReportNum) {
		this.assetsmentReportNum = assetsmentReportNum;
	}

	public int getAssetsmentFalseNum() {
		return assetsmentFalseNum;
	}

	public void setAssetsmentFalseNum(int assetsmentFalseNum) {
		this.assetsmentFalseNum = assetsmentFalseNum;
	}

	public int getAssetsmentAssetsSum() {
		return assetsmentAssetsSum;
	}

	public void setAssetsmentAssetsSum(int assetsmentAssetsSum) {
		this.assetsmentAssetsSum = assetsmentAssetsSum;
	}

	public int getCompleteCheckNum() {
		return completeCheckNum;
	}

	public void setCompleteCheckNum(int completeCheckNum) {
		this.completeCheckNum = completeCheckNum;
	}

	public int getPlanCheckNum() {
		return planCheckNum;
	}

	public void setPlanCheckNum(int planCheckNum) {
		this.planCheckNum = planCheckNum;
	}

	public int getAccountMatchCase() {
		return accountMatchCase;
	}

	public void setAccountMatchCase(int accountMatchCase) {
		this.accountMatchCase = accountMatchCase;
	}

	public int getCheckAssetsSum() {
		return checkAssetsSum;
	}

	public void setCheckAssetsSum(int checkAssetsSum) {
		this.checkAssetsSum = checkAssetsSum;
	}

	public int getAssetsCopNum() {
		return assetsCopNum;
	}

	public void setAssetsCopNum(int assetsCopNum) {
		this.assetsCopNum = assetsCopNum;
	}

	public int getAssetsCopSum() {
		return assetsCopSum;
	}

	public void setAssetsCopSum(int assetsCopSum) {
		this.assetsCopSum = assetsCopSum;
	}

	public int getAssetsMatchCase() {
		return assetsMatchCase;
	}

	public void setAssetsMatchCase(int assetsMatchCase) {
		this.assetsMatchCase = assetsMatchCase;
	}

	public int getAssetsCheckSum() {
		return assetsCheckSum;
	}

	public void setAssetsCheckSum(int assetsCheckSum) {
		this.assetsCheckSum = assetsCheckSum;
	}

	public int getAccurateErrorNumber() {
		return accurateErrorNumber;
	}

	public void setAccurateErrorNumber(int accurateErrorNumber) {
		this.accurateErrorNumber = accurateErrorNumber;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
