package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;

public interface ReportDefineBaseModel extends RDSBaseSQLModel {

    /**
     * 功能：获取生成下一个报表代码的SQL
     * @return 生成下一个报表代码的SQL
     */
    SQLModel getNextReportCodeModel();

}
