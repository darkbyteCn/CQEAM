package com.sino.rds.design.datamodel.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.ModelFieldProcessDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.component.WebRadio;
import com.sino.rds.share.form.ModelFieldFrm;
import com.sino.rds.share.util.RDSOptionCreateService;
import com.sino.rds.share.util.RDSRadioCreator;

import java.sql.Connection;


public class DataFieldSearchService extends RDSBaseService {

    private ModelFieldProcessDAO modelFieldDAO = null;

    public DataFieldSearchService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        modelFieldDAO = new ModelFieldProcessDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(modelFieldDAO);
    }

    public ModelFieldFrm searchDataByPrimaryKey() throws QueryException {
        ModelFieldFrm frm = (ModelFieldFrm) modelFieldDAO.searchDTOByPrimaryKey();
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
     * @param frm ModelFieldFrm
     * @throws com.sino.rds.foundation.exception.WebException
     *
     */
    public void produceWebComponent(ModelFieldFrm frm) throws WebException {
        WebRadio radio = RDSRadioCreator.getEnableRadio(frm.getEnabled());
        frm.setEnabledRadio(radio);
        RDSOptionCreateService optionProducer = new RDSOptionCreateService(userAccount, conn);
        WebOptions options = optionProducer.getAllDataModelOptions(frm.getModelId());
        frm.setModelOptions(options);
    }
}
