package com.sino.rds.execute.service;

import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.share.form.ReportDefineFrm;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

public class SearchPrepareService extends ExecuteFrmPrepareService {

    public SearchPrepareService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void setRequest(HttpServletRequest request) {
        executeFrm.setRequest(request);
    }

    public void prepareReportData() throws QueryException {
        String act = searchFrm.getAct();
        if (act.equals("")) {
            return;
        }
        produceReportDimension();
        produceReportExpression();
        executeFrm.setSearchResult(getSearchResult());
    }

    private RowSet getSearchResult() throws QueryException {
        RowSet searchResult = null;
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        boolean fromLook = fromLook();
        if (reportFrm.isDisplayAll() && !fromLook) {
            searchResult = produceDAO.getReportData();
        } else {
            searchResult = produceDAO.getReportPageData();
        }
        return searchResult;
    }
}