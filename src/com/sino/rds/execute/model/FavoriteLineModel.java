package com.sino.rds.execute.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.rds.appbase.model.RDSBaseSQLModel;

public interface FavoriteLineModel extends RDSBaseSQLModel {

    SQLModel getFavoriteLineCreateModel(String[] reportIdArr);
}
