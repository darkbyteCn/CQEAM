package com.sino.rds.authority.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.authority.dao.RDSReportAuthorityDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebRadio;
import com.sino.rds.share.form.ReportAuthorityFrm;
import com.sino.rds.share.util.RDSOptionCreateService;
import com.sino.rds.share.util.RDSRadioCreator;

import java.sql.Connection;


public class RDSReportAuthorityService extends RDSBaseService {

    private RDSReportAuthorityDAO reportDAO = null;

    public RDSReportAuthorityService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        reportDAO = new RDSReportAuthorityDAO(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：保存数据模型
     *
     * @throws com.sino.base.exception.DataHandleException
     *          保存数据模型出错时抛出该异常
     */
    public void saveReportAuthority() throws DataHandleException {
        ReportAuthorityFrm frm = (ReportAuthorityFrm) dtoParameter;
        String primaryKey = frm.getPrimaryKey();
        boolean isNew = StrUtil.isEmpty(primaryKey);
        boolean operateResult = false;
        try {
            if (isNew) {
                primaryKey = reportDAO.getNextPrimaryKey();
                frm.setPrimaryKey(primaryKey);
                reportDAO.setDTOParameter(frm);
                reportDAO.createData();
            } else {
                reportDAO.updateData();
            }
            operateResult = true;
        } finally {
            if (!operateResult && isNew) {
                frm.clearPrimaryKey();
            }
        }
    }


    public ReportAuthorityFrm searchDataByPrimaryKey() throws QueryException {
        ReportAuthorityFrm frm = (ReportAuthorityFrm) reportDAO.searchDTOByPrimaryKey();
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
     * @param frm ReportAuthorityFrm
     * @throws com.sino.rds.foundation.exception.WebException
     *
     */
    public void produceWebComponent(ReportAuthorityFrm frm) throws WebException {
        WebRadio radio = RDSRadioCreator.getEnableRadio(frm.getEnabled());
        frm.setEnabledRadio(radio);
        RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
    }
}