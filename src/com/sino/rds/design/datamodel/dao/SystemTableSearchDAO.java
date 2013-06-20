package com.sino.rds.design.datamodel.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.datamodel.model.SystemTableSearchModel;
import com.sino.rds.share.form.SystemAllTableFrm;

import java.sql.Connection;
import java.util.List;

public class SystemTableSearchDAO extends RDSBaseDAO {

    public SystemTableSearchDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：获取系统指定用户的所有表
     *
     * @return 系统指定用户的所有表
     * @throws QueryException
     */
    public List<SystemAllTableFrm> getUserAllTables() throws QueryException {
        SystemTableSearchModel modelProducer = (SystemTableSearchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getUserTableOptionsModel();
        return searchListByModel(sqlModel, SystemAllTableFrm.class);
    }
}
