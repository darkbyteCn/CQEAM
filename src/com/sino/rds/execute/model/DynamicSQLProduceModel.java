package com.sino.rds.execute.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ReflectException;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.share.form.ReportExecuteFrm;

public interface DynamicSQLProduceModel extends RDSBaseSQLModel {
    void setExecuteFrm(ReportExecuteFrm executeFrm);

    SQLModel getActualSQLModel() throws ReflectException;

    SQLModel getAboveDimensionSQLModel() throws ReflectException;

    SQLModel getLeftDimensionSQLModel() throws ReflectException;

    SQLModel getBottomExpressionSQLModel() throws ReflectException;

    SQLModel getVerticalExpressionSQLModel() throws ReflectException;
}
