package com.sino.rds.execute.service;

import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.execute.helper.PageHtmlProducer;
import com.sino.rds.execute.helper.PageHtmlProducerFactory;
import com.sino.rds.execute.helper.commonlist.CommonDataPatternProcessor;
import com.sino.rds.execute.helper.intersect.IntersectDataPatternProcessor;
import com.sino.rds.execute.helper.intersect.IntersectProducerFactory;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReportExecuteService extends RDSBaseService {
    private HttpServletRequest req = null;
    private ReportDefineFrm reportFrm = null;
    private ReportExecuteFrm executeFrm = null;
    private ExecuteResultFrm resultFrm = null;

    public ReportExecuteService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        resultFrm = new ExecuteResultFrm();
    }

    public void setRequest(HttpServletRequest req) {
        this.req = req;
    }

    public ExecuteResultFrm getReportExecuteResult() throws ReportException {
        try {
            constructActualSQL();
            if (reportFrm != null) {
                prepareReport();
                processIntersectData();
                formatReportPattern();
                setReportResult();
                freeResource();
            } else {
                prodMessage("NOT_FOUND_REPORT");
                message.setIsError(true);
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return resultFrm;
    }

    private void prepareReport() throws QueryException {
        Connection dataSource = null;
        try {
            SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
            SearchPrepareService prepareService = new SearchPrepareService(userAccount, reportFrm, conn);
            prepareService.setRequest(req);
            prepareService.setSearchFrm(frm);
            prepareService.prepareExecuteFrm();

            dataSource = getDataSource();

            prepareService.initDynamicDAO(dataSource);
            prepareService.prepareReportData();
            executeFrm = prepareService.getExecuteFrm();
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        } finally {
            try {
                if (dataSource != null) {
                    dataSource.close();
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
    }


    private Connection getDataSource() throws DatabaseException {
        Connection dataSource = null;
        try {
            DBConnectionFrm dbcFrm = new DBConnectionFrm();
            dbcFrm.setConnectionId(reportFrm.getConnectionId());
            DBConnectionDAO connectionDAO = new DBConnectionDAO(userAccount, dbcFrm, conn);
            dbcFrm = connectionDAO.searchDTOByPrimaryKey();
            dataSource = dbcFrm.getDBConnection();
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DatabaseException(ex.getMessage());
        }
        return dataSource;
    }

    private void processIntersectData() throws ReportException {
        ReportDefineFrm definedReport = executeFrm.getDefinedReport();
        if (definedReport.isIntersectReport()) {
            IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
            IntersectProducer rptProducer = IntersectProducerFactory.getProducer(intersectFrm);
            rptProducer.processIntersectData();
            processIntersectDrillDown(intersectFrm);
            resultFrm.setSumPosition(definedReport.getSumPosition());
            resultFrm.setSupportSubSummary(definedReport.getSupportSubSummary());
        }
    }

    private void processIntersectDrillDown(IntersectReportExecuteFrm intersectFrm){
        int startColumn = -1;
        List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
        if(leftCategories != null && !leftCategories.isEmpty()){
            startColumn = leftCategories.size();
        }
        ReportDefineFrm definedReport = intersectFrm.getDefinedReport();
        String reportType = definedReport.getReportType();
        if(!reportType.equals(RDSDictionaryList.REPORT_TYPE_LIST)){
            String sumPosition = definedReport.getSumPosition();
            if(sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)
                    || sumPosition.equals(RDSDictionaryList.POSITION_LEFT)){
                startColumn += intersectFrm.getDataFrmCount();
            }
        }
        resultFrm.setDrillStartColumn(String.valueOf(startColumn));
    }

    private void formatReportPattern() throws ReportException {
        try {
            ReportDefineFrm definedReport = executeFrm.getDefinedReport();
            if (definedReport.isIntersectReport()) {
                IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
                IntersectDataPatternProcessor.formatReportData(intersectFrm);
            } else {
                CommonDataPatternProcessor.formatReportData(executeFrm);
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        }
    }

    private void setReportResult() throws ReportException {
        if (reportFrm != null) {
            produceReportInfo();
            produceLeftCategory();
            produceReportHTML();
        }
    }

    private void constructActualSQL() throws DataHandleException {
        ReportDefineFrm dto = new ReportDefineFrm();
        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        dto.setReportCode(frm.getReportCode());
        dto.setReportId(frm.getReportId());
        dto.setLookUpCode(frm.getLookUpCode());
        dto.setLookUpId(frm.getLookUpId());
        ActualSQLConstructService constructService = new ActualSQLConstructService(userAccount, dto, conn);
        constructService.constructReportActualSQL();
        reportFrm = (ReportDefineFrm) constructService.getDTOParameter();
        if (reportFrm != null) {
            reportFrm.setAct(frm.getAct());
        }
        if (!frm.getLookUpId().equals("") || !frm.getLookUpCode().equals("")) {
            resultFrm.setFromLookUp("Y");
        }
    }

    private void produceReportInfo() {
        LookUpDefineFrm lookUpFrm = executeFrm.getLookUpFrm();
        if (lookUpFrm != null) {
            resultFrm.setReportName(lookUpFrm.getLookUpTitle());
        } else {
            resultFrm.setReportName(reportFrm.getReportName());
        }
        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        String preview = frm.getPreview();
        if (!StrUtil.isEmpty(preview) && preview.equals("Y")) {
            resultFrm.setReportName(resultFrm.getReportName() + "‘À––‘§¿¿");
        }
        resultFrm.setReportType(reportFrm.getReportType());
    }

    private void produceReportHTML() throws ReportException {
        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        String act = frm.getAct();
        if (act.equals(WebActionConstant.QUERY_ACTION) || act.equals("")) {
            PageHtmlProducer htmlProducer = PageHtmlProducerFactory.getPageHtmlProducer(userAccount, conn, executeFrm);
            htmlProducer.setHeadDIVTop(73);
            if (reportFrm != null) {
                resultFrm.setParameterHTML(htmlProducer.getParameterHTML().toString());
                resultFrm.setHiddenHTML(htmlProducer.getHiddenHTML().toString());
                resultFrm.setReportDataHTML(htmlProducer.getReportDataHTML().toString());
            } else {
                resultFrm.setReportDataHTML(htmlProducer.getUndefinedReport().toString());
            }
        }
    }

    private void produceLeftCategory() {
        if (reportFrm.isIntersectReport()) {
            IntersectReportExecuteFrm frm = (IntersectReportExecuteFrm) executeFrm;
            List<ReportCategoryFrm> definedCategories = frm.getDefinedCategories();
            if (definedCategories != null && !definedCategories.isEmpty()) {
                int leftCount = 0;
                for (ReportCategoryFrm reportCategory : definedCategories) {
                    String viewLocation = reportCategory.getViewLocation();
                    if (viewLocation.equals(RDSDictionaryList.VIEW_LOCATION_LEFT)) {
                        leftCount++;
                    }
                }
                resultFrm.setLeftCategorySize(String.valueOf(leftCount));
            }
        }
    }

    private void freeResource() {
        if (executeFrm != null) {
            executeFrm.freeResource();
        }
        reportFrm = null;
        executeFrm = null;
    }

    public File getExportReport() throws ReportException {
        ReportExportService service = ReportExportServiceFactory.getExportService(userAccount, dtoParameter, conn);
        return service.getExportReport();
    }
}