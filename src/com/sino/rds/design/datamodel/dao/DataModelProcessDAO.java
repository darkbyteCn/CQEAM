package com.sino.rds.design.datamodel.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.datamodel.model.DataModelProcessModel;
import com.sino.rds.share.form.DataModelFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataModelProcessDAO extends RDSBaseDAO {

    public DataModelProcessDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String dataModelId = "";
        try {
            dataModelId = getStrNextSeq("RDS_DATA_MODEL_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return dataModelId;
    }

    /**
     * 功能：更新数据模型的有效状态
     *
     * @throws DataHandleException 更新状态出错时抛出该异常
     */
    public void updateDataModelStatus() throws DataHandleException {
        DataModelProcessModel modelProducer = (DataModelProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getDataModelStatusUpdateModel();
        executeUpdate(sqlModel);
    }

    public List<DataModelFrm> getAllDataModels() throws QueryException {
        DataModelProcessModel modelProducer = (DataModelProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAllDataModel();
        return searchListByModel(sqlModel, DataModelFrm.class);
    }


    public List<DataModelFrm> getEnabledDataModels() throws QueryException {
        DataModelProcessModel modelProducer = (DataModelProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getEnabledDataModel();
        return searchListByModel(sqlModel, DataModelFrm.class);
    }
}
