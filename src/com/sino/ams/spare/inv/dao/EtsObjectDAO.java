package com.sino.ams.spare.inv.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.spare.inv.model.EtsObjectModel;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class EtsObjectDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    public EtsObjectDAO(SfUserDTO userAccount, EtsObjectDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsObjectDTO dtoPara = (EtsObjectDTO) dtoParameter;
        super.sqlProducer = new EtsObjectModel((SfUserDTO) userAccount, dtoPara);
    }

    public void createData() throws DataHandleException {
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            super.createData();
            EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
            SQLModel sqlModel = etsObjectModel.getAddressCreateModel();
            DBOperator.updateRecord(sqlModel, conn);
            conn.commit();
            hasError = false;
            prodMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
            getMessage().addParameterValue("仓库");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                throw new DataHandleException(ex1);
            }
        }
        getMessage().addParameterValue("AMS对象地点表");
    }

    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("资产地点表");
    }

    //执行批量失效操作
    public void disabledData(String[] workorderObjectNos) throws DataHandleException {
        EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
        SQLModel sqlModel = etsObjectModel.getDisabledModel(workorderObjectNos);
        DBOperator.updateRecord(sqlModel, conn);
    }

    //执行批量生效效操作
    public void efficientData(String[] workorderObjectNos) throws DataHandleException {
        EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
        SQLModel sqlModel = etsObjectModel.getEfficientModel(workorderObjectNos);
        DBOperator.updateRecord(sqlModel, conn);
    }

    //执行生效操作
    public void inureData() throws DataHandleException {
        EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
        SQLModel sqlModel = etsObjectModel.getInureModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //执行失效操作
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("资产地点");

    }

    //检查同组织下是否有相同类型仓库
    public String checkCategory(String objCategory) throws QueryException, ContainerException {

        EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
        SQLModel sqlModel = etsObjectModel.getTypeObjHasBeenModel(objCategory);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        if (!sq.hasResult()) {
            return "";
        } else {
            RowSet rs = sq.getSearchResult();
            Row row = rs.getRow(0);
            return row.getStrValue(0);
        }
    }

    //检查同组织下是否有相同类型仓库
    public boolean checkCode(String objCode) throws QueryException {
        boolean hasbeen = false;
        EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
        SQLModel sqlModel = etsObjectModel.getCodeHasBeenModel(objCode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        hasbeen = sq.hasResult();
        return hasbeen;
    }

    //执行校验该地点是否存在设备
    public boolean doVerifyWorkBarcode(String[] workorderObjectNos) throws QueryException {
       boolean success = false;
       EtsObjectModel etsObjectModel = (EtsObjectModel) sqlProducer;
       SQLModel sqlModel = etsObjectModel.getVerifyBarcode(workorderObjectNos);

       SimpleQuery sq = new SimpleQuery(sqlModel, conn);
       sq.executeQuery();
       success = sq.hasResult();

       return success;
    }
}
