package com.sino.ams.others.cabel.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.others.cabel.dto.AmsCabelInfoDTO;
import com.sino.ams.others.cabel.model.AmsCabelInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
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


/**
 * <p>Title: AmsCabelInfoDAO</p>
 * <p>Description:程序自动生成服务程序“AmsCabelInfoDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsCabelInfoDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：线缆类设备扩展信息(EAM) AMS_CABEL_INFO 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsCabelInfoDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AmsCabelInfoDAO(SfUserDTO userAccount, AmsCabelInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsCabelInfoDTO dtoPara = (AmsCabelInfoDTO) dtoParameter;
        super.sqlProducer = new AmsCabelInfoModel((SfUserDTO) userAccount, dtoPara);
    }


    /**
     * 功能：检查 AMS_CABEL_INFO表中 BARCODE信息是否存在
     */
/*    public boolean checkBarcodeExist(String barcode) throws QueryException {
        boolean hasBeen;
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCheckBarcodeExistModel(barcode);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        hasBeen = sq.hasResult();
        return hasBeen;
    }
    */
    //-----------------------------------------------------------------------------------------
    //--check
    public boolean checkInACI() throws QueryException {
        boolean hasBeen;
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCheckInACIModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        hasBeen = sq.hasResult();
        return hasBeen;
    }

    public boolean checkInESI() throws QueryException {
        boolean hasBeen;
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCheckInESIModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        hasBeen = sq.hasResult();
        return hasBeen;
    }

    public boolean checkInESD() throws QueryException {
        boolean hasBeen;
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCheckInESDModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        hasBeen = sq.hasResult();
        return hasBeen;
    }

    public boolean checkInEII() throws QueryException {
        boolean hasBeen;
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCheckInEIIModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        hasBeen = sq.hasResult();
        return hasBeen;
    }

    public boolean checkSameTypeInEII() throws QueryException {
        boolean hasBeen;
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCheckSameTypeInEIIModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        hasBeen = sq.hasResult();
        return hasBeen;
    }

    //-----------------------------------------------------------------------------------------
    //--create
    public void createInESD() throws DataHandleException {
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCreateInESDModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void createInESI() throws DataHandleException {
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCreateInESIModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void createInAASI() throws DataHandleException {
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCreateInAASIModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void createInEII() throws DataHandleException {
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getCreateInEIIModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    public void updateInEII() throws DataHandleException {
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getUpdateInEIIModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //-----------------------------------------------------------------------------------------
    //--getItemCode
    public String getItemCodeInESI() throws QueryException, ContainerException {
        AmsCabelInfoModel modelClass = (AmsCabelInfoModel) sqlProducer;
        SQLModel sqlModel = modelClass.getItemCodeInESIModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        return rs.getRow(0).getStrValue(0);
    }

    //-----------------------------------------------------------------------------------------
    //--submit
    public void submit() throws DataHandleException {
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            //检查&&更新设备类型信息
            if (!checkInESI()) {                       //不存在设备类型信息
                createInESI();                         //创建设备分类信息
                String itemCode = getItemCodeInESI();
                AmsCabelInfoDTO amsCabelInfoDTO = (AmsCabelInfoDTO) dtoParameter;
                amsCabelInfoDTO.setItemCode(itemCode);
                setDTOParameter(amsCabelInfoDTO);      //更新DTO中ItemCode的数据
                createInAASI();                        //创建新增设备分类申请
                createInESD();                         //创建设备分类分配信息
            } else if (!checkInESD()) {               //存在设备类型信息,不属于本地市
                createInESD();                         //创建设备分配信息
            }
            //检查&&更新设备信息
            if (!checkInACI()) {                          //不存在线缆信息
                if (!checkInEII()) {                      //不存在条码信息
                    createInEII();                        //创建条码信息
                } else if (!checkSameTypeInEII()) {      //存在条码设备，型号不一致
                    updateInEII();                        //更新条码信息
                }
                createData();                             //创建线缆信息
            } else {                                                     //如果线缆信息存在
                updateData();                             //  更新线缆信息
            }
            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            Logger.logError(e);
        } finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }

    }
}