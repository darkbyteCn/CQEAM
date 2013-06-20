package com.sino.rds.design.report.service;

import com.sino.base.constant.WorldConstant;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.report.dao.FixedCategoryDAO;
import com.sino.rds.design.report.dao.ReportCategoryProcessDAO;
import com.sino.rds.design.report.dao.ReportDefineProcessDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.share.form.FixedCategoryFrm;
import com.sino.rds.share.form.FixedCategoryProcessFrm;
import com.sino.rds.share.form.ReportCategoryFrm;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.util.RDSOptionCreateService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class FixedCategoryService extends RDSBaseService {

    public FixedCategoryService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public FixedCategoryProcessFrm searchDataByPrimaryKey() throws QueryException {
        FixedCategoryProcessFrm frm = null;
        try {
            FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
            ReportDefineFrm report = searchReportByPrimaryKey();
            if (report != null) {
                frm = new FixedCategoryProcessFrm();
                frm.setReport(report);
                List<FixedCategoryFrm> fixedCategories = searchFixedCategories();
                frm.setFixedCategories(fixedCategories);
                frm.setAct(dto.getAct());
            } else {
                frm = (FixedCategoryProcessFrm) dtoParameter;
            }
            produceWebComponent(frm);
            setDTOParameter(frm);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return frm;
    }

    private void produceWebComponent(FixedCategoryProcessFrm frm) throws QueryException {
        WebOptions options = getReportOptions();
        frm.setReportOptions(options);
        options = getCategoryOptions();
        frm.setCategoryOptions(options);
        processParentIdOptions(frm);
    }

    private WebOptions getReportOptions() throws QueryException {
        WebOptions options = null;
        try {
            RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
            FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
            options = optionCreator.getFixedCategoryReportOptions(dto.getReportId());
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return options;
    }

    private ReportDefineFrm searchReportByPrimaryKey() throws QueryException {
        FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
        ReportDefineFrm reportParameter = new ReportDefineFrm();
        reportParameter.setReportId(dto.getReportId());
        ReportDefineProcessDAO reportDAO = new ReportDefineProcessDAO(userAccount, reportParameter, conn);
        return reportDAO.searchDTOByPrimaryKey();
    }

    private WebOptions getCategoryOptions() throws QueryException {
        WebOptions options = null;
        try {
            RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
            FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
            List<ReportCategoryFrm> categories = searchReportCategories();
            options = optionCreator.getCategoryOptions(categories, dto.getCategoryId());
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return options;
    }

    private void processParentIdOptions(FixedCategoryProcessFrm frm) throws QueryException {
        List<FixedCategoryFrm> fixedCategories = frm.getFixedCategories();
        if (fixedCategories != null && !fixedCategories.isEmpty()) {
            WebOptions options = getParentValueOptions();
            for (FixedCategoryFrm fixedCategory : fixedCategories) {
                String parentId = fixedCategory.getParentId();
                options.setSelectedValue(parentId);
                fixedCategory.setParentIdOptions(options);                
            }
        }
    }

    private List<ReportCategoryFrm> searchReportCategories() throws QueryException {
        FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
        ReportCategoryFrm frm = new ReportCategoryFrm();
        frm.setReportId(dto.getReportId());
        ReportCategoryProcessDAO categoryDAO = new ReportCategoryProcessDAO(userAccount, frm, conn);
        return categoryDAO.searchListByForeignKey("reportId");
    }

    private List<FixedCategoryFrm> searchFixedCategories() throws QueryException {
        FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
        FixedCategoryFrm frm = new FixedCategoryFrm();
        frm.setCategoryId(dto.getCategoryId());
        FixedCategoryDAO fixedDAO = new FixedCategoryDAO(userAccount, frm, conn);
        return fixedDAO.searchListByForeignKey("categoryId");
    }

    public void saveFixedCategories() throws DataHandleException {
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
            List<FixedCategoryFrm> fixedCategories = dto.getFixedCategories();
            if (fixedCategories != null && !fixedCategories.isEmpty()) {
                FixedCategoryDAO fixedDAO = new FixedCategoryDAO(userAccount, null, conn);
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                for (FixedCategoryFrm fixedCategory : fixedCategories) {
                    if (StrUtil.isEmpty(fixedCategory.getDefineValue())) {
                        continue;
                    }
                    fixedCategory.setCategoryId(dto.getCategoryId());
                    if (fixedCategory.isPrimaryKeyEmpty()) {
                        String primaryKey = fixedDAO.getNextPrimaryKey();
                        fixedCategory.setDefineId(primaryKey);
                        fixedDAO.setDTOParameter(fixedCategory);
                        fixedDAO.createData();
                    } else {
                        fixedDAO.setDTOParameter(fixedCategory);
                        fixedDAO.updateData();
                    }
                }
                operateResult = true;
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage("SAVE_FIXED_CATEGORY_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("SAVE_FIXED_CATEGORY_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(ex);
            }
        }
    }

    public String getDefinedCategoryValueXML() throws QueryException {
        StringBuilder definedXML = new StringBuilder();
        List<FixedCategoryFrm> fields = searchFixedCategories();
        if (fields != null && !fields.isEmpty()) {
            definedXML.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
            definedXML.append(WorldConstant.ENTER_CHAR);
            definedXML.append(WorldConstant.TAB_CHAR);
            definedXML.append("<lines>");
            String[] fieldNames = {
                    "defineId", "categoryId", "fieldName",
                    "fieldDesc", "viewLocation", "viewLocationName",
                    "categoryLevel", "defineValue", "sortNumber",
                    "parentId", "parentValue"
            };
            for (FixedCategoryFrm field : fields) {
                definedXML.append(WorldConstant.ENTER_CHAR);
                definedXML.append(WorldConstant.TAB_CHAR);
                definedXML.append(WorldConstant.TAB_CHAR);
                definedXML.append("<line>");
                for (String fieldName : fieldNames) {
                    String fieldValue = StrUtil.nullToString(field.getProperty(fieldName));
                    definedXML.append(WorldConstant.ENTER_CHAR);
                    definedXML.append(WorldConstant.TAB_CHAR);
                    definedXML.append(WorldConstant.TAB_CHAR);
                    definedXML.append(WorldConstant.TAB_CHAR);
                    definedXML.append("<");
                    definedXML.append(fieldName);
                    definedXML.append(">");
                    definedXML.append(fieldValue);
                    definedXML.append("</");
                    definedXML.append(fieldName);
                    definedXML.append(">");
                }
                definedXML.append(WorldConstant.ENTER_CHAR);
                definedXML.append(WorldConstant.TAB_CHAR);
                definedXML.append(WorldConstant.TAB_CHAR);
                definedXML.append("</line>");
            }
            definedXML.append(WorldConstant.ENTER_CHAR);
            definedXML.append(WorldConstant.TAB_CHAR);
            definedXML.append("</lines>");
        }
        return definedXML.toString();
    }

    public WebOptions getParentValueOptions() throws QueryException {
        WebOptions parentValueOptions = null;
        try {
            FixedCategoryProcessFrm dto = (FixedCategoryProcessFrm) dtoParameter;
            FixedCategoryFrm frm = new FixedCategoryFrm();
            frm.setCategoryId(dto.getCategoryId());
            FixedCategoryDAO fixedDAO = new FixedCategoryDAO(userAccount, frm, conn);
            List<FixedCategoryFrm> parentValueFrms = fixedDAO.searchParentValueFrms();
            RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
            parentValueOptions = optionCreator.getParentValueOptions(parentValueFrms, "");
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return parentValueOptions;
    }
}