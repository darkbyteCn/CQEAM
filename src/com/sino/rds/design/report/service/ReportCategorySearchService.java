package com.sino.rds.design.report.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.report.dao.ReportCategorySearchDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.component.WebRadio;
import com.sino.rds.share.form.ReportCategoryFrm;
import com.sino.rds.share.util.RDSOptionCreateService;
import com.sino.rds.share.util.RDSRadioCreator;

import java.sql.Connection;


public class ReportCategorySearchService extends RDSBaseService {

    private ReportCategorySearchDAO searchDAO = null;

    public ReportCategorySearchService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        searchDAO = new ReportCategorySearchDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(searchDAO);
    }

    public ReportCategoryFrm searchDataByPrimaryKey() throws QueryException {
        ReportCategoryFrm frm = (ReportCategoryFrm) searchDAO.searchDTOByPrimaryKey();
        try {
            if (frm != null) {
                produceWebComponent(frm);
            }
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return frm;
    }

    /**
     * 功能：生成Web组件
     *
     * @param frm ReportViewFrm
     * @throws com.sino.rds.foundation.exception.WebException
     *
     */
    public void produceWebComponent(ReportCategoryFrm frm) throws WebException {
        WebRadio radio = RDSRadioCreator.getEnableRadio(frm.getEnabled());
        frm.setEnabledRadio(radio);
        RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
        WebOptions options = optionCreator.getAllReportOptions(frm.getReportId());
        frm.setReportOptions(options);
    }
}
