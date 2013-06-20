package com.sino.rds.design.datamodel.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DatabaseException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.design.datamodel.dao.DataModelCheckDAO;
import com.sino.rds.design.datamodel.dao.DataModelProcessDAO;
import com.sino.rds.design.datamodel.dao.ModelFieldProcessDAO;
import com.sino.rds.design.report.dao.ReportDefineProcessDAO;
import com.sino.rds.design.report.dao.ReportViewProcessDAO;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.component.WebRadio;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.*;
import com.sino.rds.share.util.RDSOptionCreateService;
import com.sino.rds.share.util.RDSRadioCreator;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataModelProcessService extends RDSBaseService {

    private DataModelProcessDAO dataModelDAO = null;

    public DataModelProcessService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        dataModelDAO = new DataModelProcessDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(dataModelDAO);
    }

    /**
     * 功能：保存数据模型
     *
     * @throws DataHandleException 保存数据模型出错时抛出该异常
     */
    public void saveDataModel() throws DataHandleException {
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        String primaryKey = frm.getPrimaryKey();
        boolean isNew = StrUtil.isEmpty(primaryKey);
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if (isNew) {
                primaryKey = dataModelDAO.getNextPrimaryKey();
                frm.setPrimaryKey(primaryKey);
                dataModelDAO.setDTOParameter(frm);
                dataModelDAO.createData();
            } else {
                dataModelDAO.updateData();
                processModelField();
                updateActualSQLUpdatedFlag();
            }
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            try {
                if (!operateResult && isNew) {
                    frm.clearPrimaryKey();
                }
                if (operateResult) {
                    conn.commit();
                    prodMessage("SAVE_MODEL_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("SAVE_MODEL_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
            } catch (Throwable ex) {
                Logger.logError(ex);
                prodMessage(ex);
                message.setIsError(true);
            }
        }
    }

    private void updateActualSQLUpdatedFlag() throws DataHandleException {
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        ReportDefineFrm report = new ReportDefineFrm();
        report.setModelId(frm.getModelId());
        ReportDefineProcessDAO reportDAO = new ReportDefineProcessDAO(userAccount, report, conn);
        reportDAO.updateModifiedFlagByDataModelId();
    }

    /**
     * 功能：处理字段。当模型修改时，涉及到字段的变更，需要对模型字段、报表数据字段、报表参数字段、报表分组字段通通清理
     * @throws DatabaseException
     */
    private void processModelField() throws DatabaseException {
        DataModelParseService parseService = new DataModelParseService(userAccount, dtoParameter, conn);
        List<Field> fields = parseService.parseSQLObject();

    }

    private void  deleteModelField(List<Field> fields) throws DataHandleException {
        DataModelFrm dto = (DataModelFrm) dtoParameter;
        ModelFieldFrm frm = new ModelFieldFrm();
        frm.setModelId(dto.getModelId());
        ModelFieldProcessDAO fieldProcessDAO = new ModelFieldProcessDAO(userAccount, frm, conn);
        fieldProcessDAO.deleteFieldsByModelFields(fields);
    }

    private void  deleteReportViewField(List<Field> fields) throws DataHandleException {
        DataModelFrm dto = (DataModelFrm) dtoParameter;
        ReportViewFrm rvf = new ReportViewFrm();
        rvf.setModelId(dto.getModelId());
        ReportViewProcessDAO fieldProcessDAO = new ReportViewProcessDAO(userAccount, rvf, conn);
        fieldProcessDAO.deleteFieldsByModelFields(fields);
    }



    public void enableDataModel() throws DataHandleException {
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.TRUE_VALUE);
        dataModelDAO.setDTOParameter(frm);
        dataModelDAO.updateDataModelStatus();
    }

    public void disableDataModel() throws DataHandleException {
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        frm.setEnabled(RDSDictionaryList.FALSE_VALUE);
        dataModelDAO.setDTOParameter(frm);
        dataModelDAO.updateDataModelStatus();
    }

    public DataModelFrm searchDataByPrimaryKey() throws QueryException {
        DataModelFrm frm = null;
        try {
            DataModelFrm dto = (DataModelFrm) dtoParameter;
            if (dto.isPrimaryKeyEmpty()) {
                frm = dto;
            } else {
                frm = dataModelDAO.searchDTOByPrimaryKey();
            }
            produceWebComponent(frm);
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return frm;
    }

    /**
     * 功能：生成Web组件
     *
     * @param frm DataModelFrm
     * @throws WebException
     */
    public void produceWebComponent(DataModelFrm frm) throws WebException {
        String enabled = frm.getEnabled();
        if(enabled.equals("")){
            enabled = RDSDictionaryList.TRUE_VALUE;
        }
        WebRadio radio = RDSRadioCreator.getEnableRadio(enabled);
        frm.setEnabledRadio(radio);
        RDSOptionCreateService optionCreator = new RDSOptionCreateService(userAccount, conn);
        String dictionaryCode = RDSDictionaryList.DATA_SRC_TYPE;
        String dataSrcType = frm.getDataSrcType();
        WebOptions options = optionCreator.getDictionaryOptions(dictionaryCode, dataSrcType);
        frm.setDataSrcOptions(options);
        frm.setConnectionOptions(optionCreator.getConnectionOptions(frm.getConnectionId()));
    }

    /**
     * 功能：删除为检查SQL查询语句是否合法而创建的临时视图
     *
     * @return 返回错误信息，如果返回为空，则校验合法，否则不合法。
     */
    public String checkModelSQL() {
        String errorMessage = "";
        Connection dataSource = null;
        try {
            DataModelFrm frm = (DataModelFrm) dtoParameter;
            DBConnectionFrm dbFrm = new DBConnectionFrm();
            dbFrm.setConnectionId(frm.getConnectionId());
            DBConnectionDAO connectionDAO = new DBConnectionDAO(userAccount, dbFrm, conn);
            dbFrm = connectionDAO.searchDTOByPrimaryKey();
            if (dbFrm != null) {
                dataSource = dbFrm.getDBConnection();
                DataModelCheckDAO modelCheckDAO = new DataModelCheckDAO(userAccount, dbFrm, dataSource);
                modelCheckDAO.setDTOParameter(frm);
                errorMessage = modelCheckDAO.checkModelSQL();
            }
        } catch (Throwable ex) {
            errorMessage += getExMessage(ex);
        } finally {
            try {
                if (dataSource != null) {
                    dataSource.close();
                }
            } catch (SQLException ex) {
                errorMessage += getExMessage(ex);
            }
        }
        return errorMessage;
    }

    private String getExMessage(Throwable ex){
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintWriter(buf, true));
        return buf.toString();
    }
}
