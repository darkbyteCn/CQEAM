package com.sino.rds.execute.service;

import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;

public class ExportPrepareService extends ExecuteFrmPrepareService {

    public ExportPrepareService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void initDynamicDAO(Connection dataSource) throws QueryException {
        super.initDynamicDAO(dataSource);
        produceDAO.initDataExportor();
    }

    public boolean nextPage() throws QueryException {
        return produceDAO.nextPage();
    }

    public void prepareReportData() throws QueryException {
        RowSet rows = produceDAO.getSearchResult();
        executeFrm.setSearchResult(rows);
    }

    public void freeResource() {
        produceDAO.freeResource();
    }
}