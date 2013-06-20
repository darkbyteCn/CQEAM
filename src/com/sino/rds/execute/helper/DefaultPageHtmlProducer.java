package com.sino.rds.execute.helper;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReportException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.execute.service.ParameterProcessService;
import com.sino.rds.share.form.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

public abstract class DefaultPageHtmlProducer extends RDSBaseService implements PageHtmlProducer {

    protected ReportExecuteFrm executeFrm = null;
    protected String reportWidth = "100%";
    protected int headDIVTop = 45;
    protected int dataDIVTop = 69;

    public DefaultPageHtmlProducer(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void setExecuteFrm(ReportExecuteFrm executeFrm) {
        this.executeFrm = executeFrm;
        if (executeFrm.getDefinedReport() != null) {
            this.reportWidth = executeFrm.getDefinedReport().getReportWidth();
        }
    }

    public void setHeadDIVTop(int headDIVTop) {
        if (headDIVTop > 0) {
            this.headDIVTop = headDIVTop;
            this.dataDIVTop = headDIVTop + 24;
        }
    }

    protected String getNavigatorHTML() {
        StringBuilder navigatorHTML = new StringBuilder();
        navigatorHTML.append("<div id=\"pageNaviDiv\" style=\"position:absolute;top:296px;left:0;width:100%\">");
        Object pageHTML = executeFrm.getRequest().getAttribute(QueryConstant.SPLIT_PAGE_HTML);
        navigatorHTML.append(StrUtil.nullToString(pageHTML));
        navigatorHTML.append("</div>");
        return navigatorHTML.toString();
    }

    /**
     * 功能：获取未定义的报表提示
     *
     * @return 未定义的报表提示
     */
    public StringBuilder getUndefinedReport() {
        SearchParameterFrm frm = executeFrm.getSearchFrm();
        StringBuilder reportHTML = new StringBuilder();
        reportHTML.append("代码为");
        if (StrUtil.isEmpty(frm.getReportCode())) {
            reportHTML.append("空");
        } else {
            reportHTML.append(frm.getReportCode());
        }
        reportHTML.append("或者Id为");
        if (StrUtil.isEmpty(frm.getReportId())) {
            reportHTML.append("空");
        } else {
            reportHTML.append(frm.getReportId());
        }
        reportHTML.append("报表未定义");
        return reportHTML;
    }


    /**
     * 功能：获取报表展示的查询参数的页面HTML
     */
    public StringBuilder getParameterHTML() throws ReportException {
        StringBuilder parameterHTML = new StringBuilder();
        try {
            ParameterProcessService paramService = new ParameterProcessService(userAccount, dtoParameter, conn);
            paramService.setParameters(executeFrm.getDefinedParameters());
            parameterHTML.append(paramService.getParameterHTML());
        } catch (QueryException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return parameterHTML;
    }

    public StringBuilder getHiddenHTML() throws ReportException {
        StringBuilder hiddenHTML = new StringBuilder();
        hiddenHTML.append(getDrillDownHTML());
        hiddenHTML.append(getCategoryHTML());
        return hiddenHTML;
    }

    private StringBuilder getDrillDownHTML() {
        StringBuilder drillDownHTML = new StringBuilder();
        ReportDefineFrm reportFrm = executeFrm.getDefinedReport();
        if (reportFrm != null) {
            drillDownHTML.append("<input type=\"hidden\" name=\"drillDownType\" id=\"drillDownType\" value=\"");
            drillDownHTML.append(reportFrm.getDrillDownType());
            drillDownHTML.append("\">");
            drillDownHTML.append(WorldConstant.ENTER_CHAR);
            drillDownHTML.append("<input type=\"hidden\" name=\"drillDownUrl\" id=\"drillDownUrl\" value=\"");
            drillDownHTML.append(reportFrm.getDrillDownUrl());
            drillDownHTML.append("\">");
            drillDownHTML.append(WorldConstant.ENTER_CHAR);
            drillDownHTML.append("<input type=\"hidden\" name=\"drillDownParameters\" id=\"drillDownParameters\" value=\"");
            drillDownHTML.append(reportFrm.getDrillDownParameters());
            drillDownHTML.append("\">");
            drillDownHTML.append(WorldConstant.ENTER_CHAR);
            drillDownHTML.append("<input type=\"hidden\" name=\"drillDownReport\" id=\"drillDownReport\" value=\"");
            drillDownHTML.append(reportFrm.getDrillDownReport());
            drillDownHTML.append("\">");
        }
        return drillDownHTML;
    }

    private StringBuilder getCategoryHTML() {
        StringBuilder categoryHTML = new StringBuilder();
        ReportDefineFrm reportFrm = executeFrm.getDefinedReport();
        if (reportFrm.isIntersectReport()) {
            IntersectReportExecuteFrm frm = (IntersectReportExecuteFrm) executeFrm;
            List<ReportCategoryFrm> definedCategorys = frm.getDefinedCategories();
            if (definedCategorys != null && !definedCategorys.isEmpty()) {
                for (ReportCategoryFrm reportCategory : definedCategorys) {
                    categoryHTML.append("<input type=\"hidden\" name=\"");
                    categoryHTML.append(reportCategory.getFieldName());
                    categoryHTML.append("\" id=\"");
                    categoryHTML.append(reportCategory.getFieldName());
                    categoryHTML.append("\" viewLocation=\"");
                    categoryHTML.append(reportCategory.getViewLocation());
                    categoryHTML.append("\" categoryLevel=\"");
                    categoryHTML.append(reportCategory.getCategoryLevel());
                    categoryHTML.append("\" displayFlag=\"");
                    categoryHTML.append(reportCategory.getDisplayFlag());
                    categoryHTML.append("\">");
                    categoryHTML.append(WorldConstant.ENTER_CHAR);
                }
                StringBuilder hiddeenHTML = new StringBuilder();
                hiddeenHTML.append(categoryHTML);
            }
        }
        return categoryHTML;
    }

    public StringBuilder getReportDataHTML() throws ReportException {
        StringBuilder searchResultHTML = new StringBuilder();
        try {
            searchResultHTML.append(getDataHTML());
            ReportDefineFrm definedReport = executeFrm.getDefinedReport();
            if (!definedReport.isDisplayAll() || executeFrm.isFromLook()) {
                searchResultHTML.append(getNavigatorHTML());
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            searchResultHTML.append("构造报表展示失败，原因如下：");
            searchResultHTML.append(WorldConstant.ENTER_CHAR);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintWriter(buf, true));
            searchResultHTML.append(buf.toString());
        }
        return searchResultHTML;
    }

    private StringBuilder getDataHTML() throws ReportException {
        StringBuilder dataHTML = new StringBuilder();
        try {
            dataHTML.append(getHeadHTML());
            SearchParameterFrm frm = executeFrm.getSearchFrm();
            String act = frm.getAct();
            if(act.equals(WebActionConstant.QUERY_ACTION)){
                dataHTML.append(getBodyHTML());
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ReportException(ex);
        }
        return dataHTML;
    }

    protected abstract String getHeadHTML() throws ContainerException;

    protected abstract String getBodyHTML() throws ContainerException;
}