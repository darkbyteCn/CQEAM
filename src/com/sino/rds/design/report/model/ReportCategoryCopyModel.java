package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;

public interface ReportCategoryCopyModel extends RDSBaseSQLModel {

    /**
     * 功能：获取报表分组字段复制的SQL
     * @return  报表分组字段复制的SQL
     */
    SQLModel getReportCategoryCopyModel();

}
