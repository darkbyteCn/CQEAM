package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.foundation.db.structure.Field;

import java.util.List;

public interface ReportParameterProcessModel extends RDSBaseSQLModel {
    SQLModel getReportParameterDeleteByIdsModel();

    SQLModel getAvailableFieldsByReportIdModel();

    SQLModel getAlreadyFieldsByReportIdModel();

    SQLModel getSelectedFieldsModel();

    SQLModel getHasFieldByReportId();

    SQLModel getFieldDeleteModelByFields(List<Field> fields);
}
