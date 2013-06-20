package com.sino.rds.design.report.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.HandlerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.report.dao.ReportCategoryProcessDAO;
import com.sino.rds.design.report.dao.ReportDefineProcessDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.component.WebRadio;
import com.sino.rds.foundation.web.util.option.OptionProduceRule;
import com.sino.rds.foundation.web.util.option.OptionProducer;
import com.sino.rds.foundation.web.util.option.OptionProducerFactory;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.ReportCategoryFrm;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.util.RDSOptionCreateService;
import com.sino.rds.share.util.RDSRadioCreator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ReportDefineProcessService extends RDSBaseService {

    private ReportDefineProcessDAO reportDAO = null;

    public ReportDefineProcessService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        reportDAO = new ReportDefineProcessDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(reportDAO);
    }

    /**
     * 功能：保存数据模型
     *
     * @throws com.sino.base.exception.DataHandleException
     *          保存数据模型出错时抛出该异常
     */
    public void saveReport() throws DataHandleException {
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        String primaryKey = frm.getPrimaryKey();
        boolean isNew = StrUtil.isEmpty(primaryKey);
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if (isNew) {
                primaryKey = reportDAO.getNextPrimaryKey();
                frm.setPrimaryKey(primaryKey);
                reportDAO.setDTOParameter(frm);
                reportDAO.createData();
            } else {
                updateReport();
            }
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } finally {
            try {
                if (!operateResult && isNew) {
                    frm.clearPrimaryKey();
                }
                if (operateResult) {
                    conn.commit();
                    prodMessage("SAVE_REPORT_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("SAVE_REPORT_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(ex);
            }
        }
    }

    private void updateReport() throws DataHandleException {
        try {
            ReportDefineFrm oldReport = reportDAO.searchDTOByPrimaryKey();
            ReportDefineFrm newReport = (ReportDefineFrm) dtoParameter;
            String oldType = oldReport.getReportType();
            String newType = newReport.getReportType();
            if (!oldType.equals(RDSDictionaryList.REPORT_TYPE_LIST) && newType.equals(RDSDictionaryList.REPORT_TYPE_LIST)) {
                ReportCategoryFrm frm = new ReportCategoryFrm();
                frm.setReportId(newReport.getReportId());
                ReportCategoryProcessDAO categoryDAO = new ReportCategoryProcessDAO(userAccount, frm, conn);
                categoryDAO.DeleteByForeignKey("reportId");
            }
            reportDAO.updateData();
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        }
    }

    public void enableReport() throws DataHandleException {
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.TRUE_VALUE);
        reportDAO.setDTOParameter(frm);
        reportDAO.updateReportStatus();
    }

    public void disableReport() throws DataHandleException {
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.FALSE_VALUE);
        reportDAO.setDTOParameter(frm);
        reportDAO.updateReportStatus();
    }

    public ReportDefineFrm searchDataByPrimaryKey() throws QueryException {
        ReportDefineFrm frm = reportDAO.searchDTOByPrimaryKey();
        try {
            if (frm == null) {
                frm = (ReportDefineFrm) dtoParameter;
                frm.setReportCode(reportDAO.getNextReportCode());
            } else {
                frm.setAct(((ReportDefineFrm) dtoParameter).getAct());
                setDTOParameter(frm);
            }
            if (frm.getReportWidth().equals("")) {
                frm.setReportWidth("100%");
            }
            produceWebComponent();
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return frm;
    }

    public ReportDefineFrm searchReportHeader() throws QueryException {
        return reportDAO.searchDTOByPrimaryKey();
    }

    /**
     * 功能：生成Web组件
     *
     * @throws WebException 生成页面Web组件出错时抛出该异常
     */
    public void produceWebComponent() throws WebException {
        RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        prodEnabledRadio(frm);
        prodDataModelOptions(frm, optionCreator);
        prodReportTypeOptions(frm, optionCreator);
        String act = frm.getAct();
        if (act.equals(RDSActionConstant.DETAIL_ACTION)) {
            RDSRadioCreator radioCreator = new RDSRadioCreator(userAccount, conn);
            prodSupportDrillRadio(frm, radioCreator);
            prodSupportSubSummaryRadio(frm, radioCreator);
            prodDrillTypeRadio(frm, radioCreator);
            prodDisplayTypeRadio(frm, radioCreator);
            prodCountPagesRadio(frm, radioCreator);
            prodTargetReportOptions(frm);
            prodSumPositionOptions(frm, optionCreator);
        }
    }

    private void prodEnabledRadio(ReportDefineFrm frm) throws WebException {
        WebRadio radio = RDSRadioCreator.getEnableRadio(frm.getEnabled());
        String enabled = frm.getEnabled();
        String act = frm.getAct();
        if (enabled.equals("") && act.equals(RDSActionConstant.DETAIL_ACTION)) {
            enabled = RDSDictionaryList.TRUE_VALUE;
        }
        radio.setCheckedValue(enabled);
        frm.setEnabledRadio(radio);
    }

    private void prodSupportDrillRadio(ReportDefineFrm frm, RDSRadioCreator radioCreator) throws WebException {
        try {
            String code = RDSDictionaryList.SUPPORT_DRILL_DOWN;
            EventHandlers handlers = new EventHandlers();
            EventHandler handler = new EventHandler();
            handler.setEventName("onclick");
            handler.setFunName("do_SupportDrillChange");
            handlers.addHandler(handler);
            WebRadio radio = radioCreator.getDictionaryRadio(code, frm.getDrillDownType(), handlers);
            String checkedValue = frm.getSupportDrillDown();
            if (checkedValue.equals("")) {
                checkedValue = RDSDictionaryList.FALSE_VALUE;
            }
            radio.setCheckedValue(checkedValue);
            frm.setSupportDrillRadio(radio);
        } catch (HandlerException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
    }

    private void prodDrillTypeRadio(ReportDefineFrm frm, RDSRadioCreator radioCreator) throws WebException {
        try {
            String code = RDSDictionaryList.DRILL_DOWN_TYPE;
            EventHandlers handlers = new EventHandlers();
            EventHandler handler = new EventHandler();
            handler.setEventName("onclick");
            handler.setFunName("do_DrillTypeChange");
            handlers.addHandler(handler);
            WebRadio radio = radioCreator.getDictionaryRadio(code, frm.getDrillDownType(), handlers);
            radio.setCheckedValue(frm.getDrillDownType());
            frm.setDrillTypeRadio(radio);
        } catch (HandlerException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
    }

    private void prodReportTypeOptions(ReportDefineFrm frm, RDSOptionCreateService optionCreator) throws WebException {
        WebOptions options = optionCreator.getDictionaryOptions(RDSDictionaryList.REPORT_TYPE, frm.getReportType());
        frm.setReportTypeOptions(options);
    }

    private void prodSumPositionOptions(ReportDefineFrm frm, RDSOptionCreateService optionCreator) throws WebException {
        WebOptions options = optionCreator.getDictionaryOptions(RDSDictionaryList.SUM_POSITION, frm.getSumPosition());
        frm.setSumPositionOptions(options);
    }

    private void prodDataModelOptions(ReportDefineFrm frm, RDSOptionCreateService optionCreator) throws WebException {
        WebOptions options = optionCreator.getAllDataModelOptions(frm.getModelId());
        frm.setModelOptions(options);
    }

    private void prodDisplayTypeRadio(ReportDefineFrm frm, RDSRadioCreator radioCreator) throws WebException {
        try {
            EventHandlers handlers = new EventHandlers();
            EventHandler handler = new EventHandler();
            handler.setEventName("onPropertyChange");
            handler.setFunName("do_DisplayTypeChange");
            handlers.addHandler(handler);
            String reportType = frm.getReportType();
            WebRadio radio = radioCreator.getDictionaryRadio(RDSDictionaryList.DISPLAY_TYPE, frm.getDisplayType(), handlers);
            radio.setDisabled(reportType.length() > 0);
            frm.setDisplayTypeRadio(radio);
        } catch (HandlerException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
    }

    private void prodSupportSubSummaryRadio(ReportDefineFrm frm, RDSRadioCreator radioCreator) throws WebException {
        WebRadio radio = radioCreator.getDictionaryRadio(RDSDictionaryList.SUPPORT_SUB_SUMMARY, frm.getSupportSubSummary());
        frm.setSupportSubSummaryRadio(radio);
    }

    private void prodCountPagesRadio(ReportDefineFrm frm, RDSRadioCreator radioCreator) throws WebException {
        WebRadio radio = radioCreator.getDictionaryRadio(RDSDictionaryList.COUNT_PAGES, frm.getCountPages());
        frm.setCountPagesRadio(radio);
    }

    private void prodTargetReportOptions(ReportDefineFrm frm) throws WebException {
        try {
            List<ReportDefineFrm> targetReports = reportDAO.getTargetReports();
            OptionProduceRule optRule = new OptionProduceRule();
            optRule.setValueField("reportId");
            optRule.setDescField("reportName");
            optRule.setDataSource(targetReports);
            optRule.setSelectedValue(frm.getDrillDownReport());
            optRule.setAddBlank(true);
            OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
            frm.setDrillDownReportOptions(optProducer.getOptions());
        } catch (QueryException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
    }

    public boolean copyReport(){
        boolean operateResult = false;

        return operateResult;
    }
}