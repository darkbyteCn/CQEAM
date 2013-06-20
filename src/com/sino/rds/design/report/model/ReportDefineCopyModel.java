package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;

public interface ReportDefineCopyModel extends ReportDefineBaseModel {


    /**
     * 功能：获取报表定义的复制SQL
     * @return  报表定义的复制SQL
     */
    SQLModel getReportDefineCopyModel();

}
