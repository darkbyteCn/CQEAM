package com.sino.ams.newasset.report.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-3
 * Time: 17:18:30
 * To change this template use File | Settings | File Templates.
 */
public class SpecialAssetsReportDTO extends CommonRecordDTO {
    private String assetsSpecies = ""; //资产类
    private String assetsNape = ""; //资产项
    private String sumNape = ""; //项合计
    private String sumCost = ""; //价值合计
    private String sumTotal = ""; //总计
    private String assetsRate = "";//百分比
    private String contentCode = "";
    private String contentName = "";
    private String areaAssetsType = "";
    private int organizationId;
    private String specialAssetsType = "";
    private String countyCode = "";


    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    private String matchAssetsType = "";//盘点资产账实相符率
    private String responsibilityDept = "";

    private String loseAssetsType = "";//资产盘亏率

    public String getLoseAssetsType() {
        return loseAssetsType;
    }

    public void setLoseAssetsType(String loseAssetsType) {
        this.loseAssetsType = loseAssetsType;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getMatchAssetsType() {
        return matchAssetsType;
    }

    public void setMatchAssetsType(String matchAssetsType) {
        this.matchAssetsType = matchAssetsType;
    }

    public String getSpecialAssetsType() {
        return specialAssetsType;
    }

    public void setSpecialAssetsType(String specialAssetsType) {
        this.specialAssetsType = specialAssetsType;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getAreaAssetsType() {
        return areaAssetsType;
    }

    public void setAreaAssetsType(String areaAssetsType) {
        this.areaAssetsType = areaAssetsType;
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

    public String getAssetsRate() {
        return assetsRate;
    }

    public void setAssetsRate(String assetsRate) {
        this.assetsRate = assetsRate;
    }

    public String getAssetsSpecies() {
        return assetsSpecies;
    }

    public void setAssetsSpecies(String assetsSpecies) {
        this.assetsSpecies = assetsSpecies;
    }

    public String getAssetsNape() {
        return assetsNape;
    }

    public void setAssetsNape(String assetsNape) {
        this.assetsNape = assetsNape;
    }

    public String getSumNape() {
        return sumNape;
    }

    public void setSumNape(String sumNape) {
        this.sumNape = sumNape;
    }

    public String getSumCost() {
        return sumCost;
    }

    public void setSumCost(String sumCost) {
        this.sumCost = sumCost;
    }

    public String getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(String sumTotal) {
        this.sumTotal = sumTotal;
    }
}
