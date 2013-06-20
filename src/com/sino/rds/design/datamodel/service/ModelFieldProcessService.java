package com.sino.rds.design.datamodel.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DataModelProcessDAO;
import com.sino.rds.design.datamodel.dao.ModelFieldProcessDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.DataModelFrm;
import com.sino.rds.share.form.ModelFieldFrm;
import com.sino.rds.share.util.RDSOptionCreateService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ModelFieldProcessService extends RDSBaseService {

    private DataModelProcessDAO dataModelDAO = null;
    private ModelFieldProcessDAO modelFieldDAO = null;

    public ModelFieldProcessService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        dataModelDAO = new DataModelProcessDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(dataModelDAO);
        modelFieldDAO = new ModelFieldProcessDAO(userAccount, null, conn);
    }

    /**
     * 功能：保存数据模型
     *
     * @throws com.sino.base.exception.DataHandleException
     *          保存数据模型出错时抛出该异常
     */
    public void saveModelField() throws DataHandleException {
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        String primaryKey = frm.getPrimaryKey();
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            List<ModelFieldFrm> fields = frm.getFields();
            for (ModelFieldFrm field : fields) {
                field.setModelId(primaryKey);
                if (field.isPrimaryKeyEmpty()) {
                    field.setPrimaryKey(modelFieldDAO.getNextPrimaryKey());
                    modelFieldDAO.setDTOParameter(field);
                    modelFieldDAO.createData();
                } else {
                    modelFieldDAO.setDTOParameter(field);
                    modelFieldDAO.updateData();
                }
            }
            operateResult = true;
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage("SAVE_MODEL_FIELD_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("SAVE_MODEL_FIELD_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(ex);
            }
        }
    }

    public DataModelFrm searchDataByPrimaryKey() throws QueryException {
        DataModelFrm frm = null;
        try {
            DataModelFrm dto = (DataModelFrm) dtoParameter;
            if (!dto.isPrimaryKeyEmpty()) {
                frm = (DataModelFrm) dataModelDAO.searchDTOByPrimaryKey();
            } else {
                frm = (DataModelFrm) dtoParameter;
            }
            if (frm != null) {
                setDTOParameter(frm);
                List<ModelFieldFrm> fields = searchFieldsByModelId();
                frm.setFields(fields);
                produceWebComponent();
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
     * @throws com.sino.rds.foundation.exception.WebException
     *
     */
    private void produceWebComponent() throws WebException {
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        RDSOptionCreateService optionProducer = new RDSOptionCreateService(userAccount, conn);
        WebOptions options = optionProducer.getAllDataModelOptions(frm.getModelId());
        frm.setModelOptions(options);
        List<ModelFieldFrm> fields = frm.getFields();
        if (fields != null && !fields.isEmpty()) {
            for (ModelFieldFrm field : fields) {
                if (field.getFieldId().equals("")) {
                    field.setEnabled(RDSDictionaryList.TRUE_VALUE);
                }
                options = RDSOptionCreateService.getEnableOptions(field.getEnabled());
                field.setEnabledOptions(options);
            }
        }
    }

    /**
     * 功能：补充数据模型字段数据
     *
     * @return 字段列表
     * @throws QueryException 查询字段列表出错时抛出该异常
     */
    private List<ModelFieldFrm> searchFieldsByModelId() throws QueryException {
        List<ModelFieldFrm> modelFields = null;
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        if (!frm.isPrimaryKeyEmpty()) {

            DataModelParseService parser = new DataModelParseService(userAccount, frm, conn);
            modelFields = parser.getModelFields();
            if (modelFields != null && !modelFields.isEmpty()) {
                ModelFieldFrm field = new ModelFieldFrm();
                field.setModelId(frm.getModelId());
                modelFieldDAO.setDTOParameter(field);
                List<ModelFieldFrm> definedFields = modelFieldDAO.searchListByForeignKey("modelId");
                if (definedFields != null && !definedFields.isEmpty()) {
                    for (ModelFieldFrm definedField : definedFields) {
                        for (int i = 0; i < modelFields.size(); i++) {
                            ModelFieldFrm modelField = modelFields.get(i);
                            if (modelField.getFieldName().equals(definedField.getFieldName())) {
                                modelFields.set(i, definedField);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return modelFields;
    }

    public String hasModelField() {
        String operateResult = "N";
        try {
            DataModelFrm frm = (DataModelFrm) dtoParameter;
            ModelFieldFrm field = new ModelFieldFrm();
            field.setModelId(frm.getModelId());
            modelFieldDAO.setDTOParameter(field);
            if (modelFieldDAO.hasModelField()) {
                operateResult = "Y";
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return operateResult;
    }
}
