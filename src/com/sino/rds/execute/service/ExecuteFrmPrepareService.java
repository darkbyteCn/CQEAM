package com.sino.rds.execute.service;

import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.report.dao.LookUpDefineDAO;
import com.sino.rds.execute.dao.CategorySearchDAO;
import com.sino.rds.execute.dao.DynamicSQLProduceDAO;
import com.sino.rds.execute.dao.ParameterSearchDAO;
import com.sino.rds.execute.dao.ViewSearchDAO;
import com.sino.rds.share.form.*;

import java.sql.Connection;
import java.util.List;

public abstract class ExecuteFrmPrepareService extends RDSBaseService {
    protected SearchParameterFrm searchFrm = null;

    protected ReportExecuteFrm executeFrm = null;
    protected DynamicSQLProduceDAO produceDAO = null;

    public ExecuteFrmPrepareService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        initExecuteFrm();
    }

    public void initDynamicDAO(Connection dataSource) throws QueryException{
        produceDAO = new DynamicSQLProduceDAO(userAccount, dtoParameter, dataSource);
        produceDAO.setExecuteFrm(executeFrm);
    }

    private void initExecuteFrm() {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        if (reportFrm.isIntersectReport()) {
            executeFrm = new IntersectReportExecuteFrm();
        } else {
            executeFrm = new ReportExecuteFrm();
        }
        executeFrm.setUserAccount(userAccount);
        executeFrm.setDefinedReport(reportFrm);
    }

    public void setSearchFrm(SearchParameterFrm searchFrm) {
        this.searchFrm = searchFrm;
        executeFrm.setSearchFrm(searchFrm);
    }

    public void prepareExecuteFrm() throws QueryException {
        try {
            processLookUp();
            produceDefinedParameters();
            produceDefinedCategories();
            produceDefinedViews();
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
    }

    private void produceDefinedParameters() throws QueryException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        ReportParameterFrm searchPara = new ReportParameterFrm();
        searchPara.setReportId(reportFrm.getReportId());
        ParameterSearchDAO parameterDAO = new ParameterSearchDAO(userAccount, searchPara, conn);
        parameterDAO.setDTOClassName(ReportParameterFrm.class.getName());
        List<ReportParameterFrm> parameters = parameterDAO.searchListByForeignKey("reportId");
        executeFrm.setDefinedParameters(parameters);
    }

    private void produceDefinedViews() throws QueryException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        ReportViewFrm searchPara = new ReportViewFrm();
        searchPara.setReportId(reportFrm.getReportId());
        ViewSearchDAO viewDAO = new ViewSearchDAO(userAccount, searchPara, conn);
        viewDAO.setDTOClassName(ReportViewFrm.class.getName());
        List<ReportViewFrm> views = viewDAO.searchListByForeignKey("reportId");

        executeFrm.setDefinedViews(views);
    }

    private void produceDefinedCategories() throws QueryException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        if (reportFrm.isIntersectReport()) {
            ReportCategoryFrm searchPara = new ReportCategoryFrm();
            searchPara.setReportId(reportFrm.getReportId());
            CategorySearchDAO categoryDAO = new CategorySearchDAO(userAccount, searchPara, conn);
            categoryDAO.setDTOClassName(ReportCategoryFrm.class.getName());
            List<ReportCategoryFrm> categorys = categoryDAO.searchListByForeignKey("reportId");
            ((IntersectReportExecuteFrm) executeFrm).setDefinedCategories(categorys);
        }
    }

    public void produceReportDimension() throws QueryException {
        String act = searchFrm.getAct();
        if (act.equals("")) {
            return;
        }
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        if (reportFrm.isIntersectReport()) {
            IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
            RowSet searchResult = produceDAO.getAboveDimensionData();
            if (searchResult != null) {
                intersectFrm.setAboveDimensions(searchResult);
            }
            searchResult = produceDAO.getLeftDimensionData();
            if (searchResult != null) {
                intersectFrm.setLeftDimensions(searchResult);
            }
        }
    }

    public void produceReportExpression() throws QueryException {
        String act = searchFrm.getAct();
        if (act.equals("")) {
            return;
        }
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        if (reportFrm.isIntersectReport()) {
            IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
            RowSet searchResult = produceDAO.getBottomExpressionData();
            if (searchResult != null) {
                intersectFrm.setBottomExpressions(searchResult);
            }
            searchResult = produceDAO.getVerticalExpressionData();
            if (searchResult != null) {
                intersectFrm.setVerticalExpressions(searchResult);
            }
        }
    }

    protected boolean fromLook() {
        String lookUpId = searchFrm.getParameter("lookUpId");
        String lookUpCode = searchFrm.getParameter("lookUpCode");
        return (!lookUpId.equals("") || !lookUpCode.equals(""));
    }

    /**
     * 功能：根据LookUp参数查询Report的ID以及代码
     *
     * @throws com.sino.base.exception.QueryException
     *          查询出错时抛出该异常
     */
    private void processLookUp() throws QueryException {
        if (fromLook()) {
            LookUpDefineFrm lookParameter = new LookUpDefineFrm();
            lookParameter.setLookUpId(searchFrm.getParameter("lookUpId"));
            lookParameter.setLookUpCode(searchFrm.getParameter("lookUpCode"));
            LookUpDefineDAO lookUpDAO = new LookUpDefineDAO(userAccount, lookParameter, conn);
            LookUpDefineFrm lookUpFrm = lookUpDAO.searchDTOByPrimaryKey();
            searchFrm.addParameter("reportId", lookUpFrm.getReportId());
            searchFrm.addParameter("reportCode", lookUpFrm.getReportCode());
            executeFrm.setLookUpFrm(lookUpFrm);
        }
    }

    public ReportExecuteFrm getExecuteFrm() {
        return executeFrm;
    }
}