package com.sino.rds.design.report.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.design.report.model.ReportDefineCopyModel;

public class SybaseReportDefineCopyModel extends SybaseReportDefineBaseModel implements ReportDefineCopyModel {

    public SybaseReportDefineCopyModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：获取报表定义的复制SQL
     * @return  报表定义的复制SQL
     */
    public SQLModel getReportDefineCopyModel(){
        return getDataCreateModel();
    }
}
