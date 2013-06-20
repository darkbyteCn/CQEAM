package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;

public interface ReportParameterCopyModel extends RDSBaseSQLModel {

    /**
     * 功能：获取报表参数字段复制的SQL
     * @return  报表参数字段复制的SQL
     */
    SQLModel getReportParameterCopyModel();

}
