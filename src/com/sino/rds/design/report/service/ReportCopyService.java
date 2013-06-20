package com.sino.rds.design.report.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.report.dao.ReportCategoryProcessDAO;
import com.sino.rds.design.report.dao.ReportDefineCopyDAO;
import com.sino.rds.design.report.dao.ReportParameterProcessDAO;
import com.sino.rds.design.report.dao.ReportViewProcessDAO;
import com.sino.rds.share.form.ReportCategoryFrm;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.ReportParameterFrm;
import com.sino.rds.share.form.ReportViewFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ReportCopyService extends RDSBaseService {

    private ReportDefineCopyDAO defineDAO = null;
    private ReportViewProcessDAO viewDAO = null;
    private ReportParameterProcessDAO parameterDAO = null;
    private ReportCategoryProcessDAO categoryDAO = null;

    private ReportDefineFrm srcReport = null;
    private String newReportId = "";


    public ReportCopyService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        initCopyProcessor();
    }

    private void initCopyProcessor() {
        defineDAO = new ReportDefineCopyDAO(userAccount, null, conn);
        viewDAO = new ReportViewProcessDAO(userAccount, null, conn);
        parameterDAO = new ReportParameterProcessDAO(userAccount, null, conn);
        categoryDAO = new ReportCategoryProcessDAO(userAccount, null, conn);
        setPrimaryDAO(defineDAO);
    }

    public boolean copyReport() {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            ReportDefineFrm rptFrm = (ReportDefineFrm) dtoParameter;
            String reportIds = rptFrm.getReportIds();
            if (reportIds.length() > 0) {
                String[] reportIdArr = StrUtil.splitStr(reportIds, ",");
                for (String reportId : reportIdArr) {
                    produceSrcReport(reportId);
                    copyReportDefine();
                    copyReportView(reportId);
                    copyReportParameter(reportId);
                    if (srcReport.isIntersectReport()) {
                        copyReportCategory(reportId);
                    }
                }
            }
            operateResult = true;
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage("REPORT_COPY_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("REPORT_COPY_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
            freeResource();
        }
        return operateResult;
    }

    /**
     * 功能：生成源报表定义对象
     *
     * @param reportId 源报表ID
     * @throws QueryException 生成源报表定义对象出错时抛出该异常
     */
    private void produceSrcReport(String reportId) throws QueryException {
        ReportDefineFrm report = new ReportDefineFrm();
        report.setReportId(reportId);
        defineDAO.setDTOParameter(report);
        srcReport = defineDAO.searchDTOByPrimaryKey();
    }


    /**
     * 功能：复制报表定义
     *
     * @throws DataHandleException 复制报表定义出错时抛出该异常
     */
    private void copyReportDefine() throws DataHandleException {
        try {
            newReportId = defineDAO.getNextPrimaryKey();
            srcReport.setReportId(newReportId);
            srcReport.setReportCode(defineDAO.getNextReportCode());
            srcReport.setReportName(srcReport.getReportName() + "_副本");
            defineDAO.setDTOParameter(srcReport);
            defineDAO.copyReport();
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：复制报表显示字段
     *
     * @param reportId 源报表ID
     * @throws DataHandleException 复制报表显示字段出错时抛出该异常
     */
    private void copyReportView(String reportId) throws DataHandleException {
        try {
            ReportViewFrm reportArg = new ReportViewFrm();
            reportArg.setReportId(reportId);
            viewDAO.setDTOParameter(reportArg);
            List<ReportViewFrm> frms = viewDAO.searchListByForeignKey("reportId");
            if (frms != null) {
                for (ReportViewFrm frm : frms) {
                    frm.setViewId(viewDAO.getNextPrimaryKey());
                    frm.setReportId(newReportId);
                    viewDAO.setDTOParameter(frm);
                    viewDAO.createData();
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：复制报表参数字段
     * @param reportId 源报表ID
     *
     * @throws DataHandleException 复制报表参数字段出错时抛出该异常
     */
    private void copyReportParameter(String reportId) throws DataHandleException {
        try {
            ReportParameterFrm reportArg = new ReportParameterFrm();
            reportArg.setReportId(reportId);
            parameterDAO.setDTOParameter(reportArg);
            List<ReportParameterFrm> frms = parameterDAO.searchListByForeignKey("reportId");
            if (frms != null) {
                for (ReportParameterFrm frm : frms) {
                    frm.setParameterId(parameterDAO.getNextPrimaryKey());
                    frm.setReportId(newReportId);
                    parameterDAO.setDTOParameter(frm);
                    parameterDAO.createData();
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：复制报表分组字段
     * @param reportId 源报表ID
     *
     * @throws DataHandleException 复制报表分组字段出错时抛出该异常
     */
    private void copyReportCategory(String reportId) throws DataHandleException {
        try {
            ReportCategoryFrm reportArg = new ReportCategoryFrm();
            reportArg.setReportId(reportId);
            categoryDAO.setDTOParameter(reportArg);
            List<ReportCategoryFrm> frms = categoryDAO.searchListByForeignKey("reportId");
            if (frms != null) {
                for (ReportCategoryFrm frm : frms) {
                    frm.setCategoryId(categoryDAO.getNextPrimaryKey());
                    frm.setReportId(newReportId);
                    categoryDAO.setDTOParameter(frm);
                    categoryDAO.createData();
                }
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    /**
     * 功能：释放资源
     */
    private void freeResource() {
        defineDAO = null;
        viewDAO = null;
        parameterDAO = null;
        categoryDAO = null;
        srcReport = null;
        newReportId = "";
    }
}