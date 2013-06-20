package com.sino.rds.design.report.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.report.dao.LookUpDefineDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.component.WebRadio;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.LookUpDefineFrm;
import com.sino.rds.share.form.ModelFieldFrm;
import com.sino.rds.share.util.RDSOptionCreateService;
import com.sino.rds.share.util.RDSRadioCreator;

import java.sql.Connection;
import java.util.List;


public class LookUpDefineService extends RDSBaseService {

    private LookUpDefineDAO defineDAO = null;

    public LookUpDefineService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        defineDAO = new LookUpDefineDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(defineDAO);
    }

    /**
     * 功能：保存数据模型
     *
     * @throws com.sino.base.exception.DataHandleException
     *          保存数据模型出错时抛出该异常
     */
    public void saveLookUpDefine() throws DataHandleException {
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;
        String primaryKey = frm.getPrimaryKey();
        boolean isNew = StrUtil.isEmpty(primaryKey);
        boolean operateResult = false;
        try {
            if (isNew) {
                primaryKey = defineDAO.getNextPrimaryKey();
                frm.setPrimaryKey(primaryKey);
                defineDAO.setDTOParameter(frm);
                defineDAO.createData();
            } else {
                defineDAO.updateData();
            }
            operateResult = true;
        } finally {
            if (!operateResult && isNew) {
                frm.clearPrimaryKey();
            }
            if(operateResult){
                prodMessage("SAVE_LOOKUP_SUCCESS");
            } else {
                prodMessage("SAVE_LOOKUP_FAILURE");
                message.setIsError(true);
            }
        }
    }

    public void enableLookUp() throws DataHandleException {
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.TRUE_VALUE);
        defineDAO.setDTOParameter(frm);
        defineDAO.updateLookUpStatus();
    }

    public void disableLookUp() throws DataHandleException {
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.FALSE_VALUE);
        defineDAO.setDTOParameter(frm);
        defineDAO.updateLookUpStatus();
    }

    public LookUpDefineFrm searchDataByPrimaryKey() throws QueryException {
        LookUpDefineFrm frm = (LookUpDefineFrm) defineDAO.searchDTOByPrimaryKey();
        try {
            if (frm == null) {
                frm = (LookUpDefineFrm) dtoParameter;
            } else {
                frm.setAct(((LookUpDefineFrm) dtoParameter).getAct());
                setDTOParameter(frm);
            }
            produceWebComponent();
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return frm;
    }

    /**
     * 功能：生成Web组件
     *
     * @throws com.sino.rds.foundation.exception.WebException
     *          生成页面Web组件出错时抛出该异常
     */
    public void produceWebComponent() throws WebException {
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;
        String enabled = frm.getEnabled();
        String act = frm.getAct();
        if (enabled.equals("") && act.equals(RDSActionConstant.DETAIL_ACTION)) {
            enabled = RDSDictionaryList.TRUE_VALUE;
        }
        WebRadio radio = RDSRadioCreator.getEnableRadio(enabled);
        frm.setEnabledRadio(radio);
        RDSOptionCreateService optionProducer = new RDSOptionCreateService(userAccount, conn);
        WebOptions options = optionProducer.getAllReportOptions(frm.getReportId());
        frm.setReportOptions(options);

        options = getReportFields();
        options.setSelectedValue(frm.getReturnField());
        frm.setReturnFieldOptions(options);
    }

    public WebOptions getReportFields() throws WebException {
        WebOptions fieldOptions = null;
        try {
            defineDAO.setDTOParameter(dtoParameter);
            List<ModelFieldFrm> fields = defineDAO.getField4OptionsByReportId();
            RDSOptionCreateService optionProducer = new RDSOptionCreateService(userAccount, conn);
            fieldOptions = optionProducer.getFieldsOptions(fields);
        } catch (QueryException ex) {
            ex.printLog();
            throw new WebException(ex);
        }
        return fieldOptions;
    }
}