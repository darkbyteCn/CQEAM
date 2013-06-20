package com.sino.rds.design.datamodel.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.foundation.db.structure.Field;

import java.util.List;

public interface ModelFieldProcessModel extends RDSBaseSQLModel {

    SQLModel getDataByModelId();

    SQLModel getHasFieldByModelId();

    SQLModel getFieldDeleteModelByFields(List<Field> fields);
}
