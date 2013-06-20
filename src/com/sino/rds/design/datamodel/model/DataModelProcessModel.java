package com.sino.rds.design.datamodel.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;


public interface DataModelProcessModel extends RDSBaseSQLModel {

    public SQLModel getAllDataModel() ;

    public SQLModel getEnabledDataModel() ;

    public SQLModel getDataModelStatusUpdateModel();
}
