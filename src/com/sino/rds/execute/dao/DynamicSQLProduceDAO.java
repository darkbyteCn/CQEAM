package com.sino.rds.execute.dao;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.GridQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.execute.model.DynamicSQLProduceModel;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.ReportExecuteFrm;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

public class DynamicSQLProduceDAO extends RDSBaseDAO {

    private ReportExecuteFrm executeFrm = null;
    private GridQuery gridSearch = null;

    public DynamicSQLProduceDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void setExecuteFrm(ReportExecuteFrm executeFrm) {
        this.executeFrm = executeFrm;
        DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
        modelProducer.setExecuteFrm(executeFrm);
    }

    public RowSet getReportData() throws QueryException {
        RowSet searchResult = null;
        try {
            DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getActualSQLModel();
            searchResult = searchRowSetByModel(sqlModel);
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return searchResult;
    }

    public RowSet getReportPageData() throws QueryException {
        RowSet searchResult = null;
        try {
            ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
            DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getActualSQLModel();
            HttpServletRequest request = executeFrm.getRequest();
            WebPageView webPage = new WebPageView(request, conn);
            if (StrUtil.isInteger(reportFrm.getPageSize())) {
                webPage.setPageSize(Integer.parseInt(reportFrm.getPageSize()));
            }
            webPage.setCountPages(reportFrm.getCountPages().equals(RDSDictionaryList.TRUE_VALUE));
            webPage.produceWebData(sqlModel);
            searchResult = (RowSet) request.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return searchResult;
    }

    public RowSet getAboveDimensionData() throws QueryException {
        RowSet searchResult = null;
        try {
            DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getAboveDimensionSQLModel();
            if (sqlModel != null) {
                searchResult = searchRowSetByModel(sqlModel);
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return searchResult;
    }

    public RowSet getLeftDimensionData() throws QueryException {
        RowSet searchResult = null;
        try {
            DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getLeftDimensionSQLModel();
            if (sqlModel != null) {
                searchResult = searchRowSetByModel(sqlModel);
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return searchResult;
    }

    public RowSet getBottomExpressionData() throws QueryException {
        RowSet searchResult = null;
        try {
            ReportDefineFrm rdf = (ReportDefineFrm)dtoParameter;
            String sumPosition = rdf.getSumPosition();
            if(sumPosition.equals(RDSDictionaryList.POSITION_BOTTOM)
                    ||sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)
                    ||sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)){
                DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
                SQLModel sqlModel = modelProducer.getBottomExpressionSQLModel();
                if (sqlModel != null) {
                    searchResult = searchRowSetByModel(sqlModel);
                }
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return searchResult;
    }

    public RowSet getVerticalExpressionData() throws QueryException {
        RowSet searchResult = null;
        try {
            ReportDefineFrm rdf = (ReportDefineFrm)dtoParameter;
            String sumPosition = rdf.getSumPosition();
            if(!sumPosition.equals(RDSDictionaryList.POSITION_BOTTOM)){
                DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
                SQLModel sqlModel = modelProducer.getVerticalExpressionSQLModel();
                if (sqlModel != null) {
                    searchResult = searchRowSetByModel(sqlModel);
                }
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return searchResult;
    }

    public void initDataExportor() throws QueryException {
        try {
            DynamicSQLProduceModel modelProducer = (DynamicSQLProduceModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getActualSQLModel();
            gridSearch = new GridQuery(sqlModel, conn);
            gridSearch.setPageSize(2000);
            gridSearch.executeQuery();
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
    }

    public boolean nextPage() throws QueryException {
        return gridSearch.nextPage();
    }

    public RowSet getSearchResult() throws QueryException {
        return gridSearch.getSearchResult();
    }

    public void freeResource() {
        if (gridSearch != null) {
            gridSearch.freeResource();
        }
    }
}
