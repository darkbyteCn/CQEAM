package com.sino.rds.design.report.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.foundation.db.structure.Field;

import java.util.List;

public interface ReportCategoryProcessModel extends RDSBaseSQLModel {
    SQLModel getReportCategoryDeleteByIdsModel();

    SQLModel getAvailableFieldsByReportIdModel();

    SQLModel getAboveFieldsByReportIdModel();

    SQLModel getLeftFieldsByReportIdModel();

    SQLModel getSelectedFieldsModel();

    SQLModel getDeleteByViewIdsModel();

    SQLModel getHasFieldByReportId();

    SQLModel getFieldDeleteModelByFields(List<Field> fields);
}
