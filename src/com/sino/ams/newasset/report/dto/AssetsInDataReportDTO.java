package com.sino.ams.newasset.report.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-16
 * Time: 11:26:37
 * To change this template use File | Settings | File Templates.
 */
public class AssetsInDataReportDTO extends CommonRecordDTO {
    private String period = "";//会计期间
    private String managerGuideType = "";//管理指标类报表类型
    private int organizationId;

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
