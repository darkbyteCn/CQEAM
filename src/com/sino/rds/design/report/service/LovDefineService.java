package com.sino.rds.design.report.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.report.dao.LovDefineDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebRadio;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.LovDefineFrm;
import com.sino.rds.share.util.RDSOptionCreateService;
import com.sino.rds.share.util.RDSRadioCreator;

import java.sql.Connection;


public class LovDefineService extends RDSBaseService {

    private LovDefineDAO defineDAO = null;

    public LovDefineService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        defineDAO = new LovDefineDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(defineDAO);
    }

    /**
     * 功能：保存数据模型
     *
     * @throws com.sino.base.exception.DataHandleException
     *          保存数据模型出错时抛出该异常
     */
    public void saveLovDefine() throws DataHandleException {
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;
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
                prodMessage("SAVE_LOV_SUCCESS");
            } else {
                prodMessage("SAVE_LOV_FAILURE");
                message.setIsError(true);
            }
        }
    }

    public void enableLOV() throws DataHandleException {
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.TRUE_VALUE);
        defineDAO.setDTOParameter(frm);
        defineDAO.updateLOVStatus();
    }

    public void disableLOV() throws DataHandleException {
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.FALSE_VALUE);
        defineDAO.setDTOParameter(frm);
        defineDAO.updateLOVStatus();
    }

    public LovDefineFrm searchDataByPrimaryKey() throws QueryException {
        LovDefineFrm frm = (LovDefineFrm) defineDAO.searchDTOByPrimaryKey();
        try {
            if (frm == null) {
                frm = (LovDefineFrm) dtoParameter;
            } else {
                frm.setAct(((LovDefineFrm) dtoParameter).getAct());
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
     * @throws com.sino.rds.foundation.exception.WebException 生成页面Web组件出错时抛出该异常
     */
    public void produceWebComponent() throws WebException {
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;
        String enabled = frm.getEnabled();
        String act = frm.getAct();
        if (enabled.equals("") && act.equals(RDSActionConstant.DETAIL_ACTION)) {
            enabled = RDSDictionaryList.TRUE_VALUE;
        }
        WebRadio radio = RDSRadioCreator.getEnableRadio(enabled);
        frm.setEnabledRadio(radio);

        radio = RDSRadioCreator.getAddBlankRadio(frm.getAddBlank());
        frm.setAddBlankRadio(radio);

        RDSRadioCreator radioProducer = new RDSRadioCreator(userAccount, conn);
        radio = radioProducer.getLovTypeRadio(frm.getLovType());
        frm.setLovTypeRadio(radio);

        RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
        frm.setConnectionOptions(optionCreator.getConnectionOptions(frm.getConnectionId()));
    }
}