package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;

public interface ReportViewCopyModel extends RDSBaseSQLModel {

    /**
     * 功能：获取报表数据字段复制的SQL
     * @return  报表数据字段复制的SQL
     */
    SQLModel getReportViewCopyModel();

}
