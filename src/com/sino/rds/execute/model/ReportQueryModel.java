package com.sino.rds.execute.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;

public interface ReportQueryModel extends RDSBaseSQLModel {

    SQLModel getMyFavoriteReportModel();
}
