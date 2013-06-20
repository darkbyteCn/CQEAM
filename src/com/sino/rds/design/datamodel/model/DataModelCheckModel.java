package com.sino.rds.design.datamodel.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;


public interface DataModelCheckModel extends RDSBaseSQLModel {

    public SQLModel getModelCheckModel();
}
