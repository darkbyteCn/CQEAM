package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;

public interface ReportDefineProcessModel extends ReportDefineBaseModel {

    SQLModel getReportStatusUpdateModel();

    SQLModel getTargetReportModel();

    SQLModel getActualSQLConfirmModel();

    SQLModel getActualSQLHasUpdatedModel();

    SQLModel getActualSQLModifiedFlagModel();

    SQLModel getModifiedFlagByDataModelIdModel();
}
