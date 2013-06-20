package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsFaChgHistoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: AmsFaChgHistoryModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsFaChgHistoryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsFaChgHistoryModel extends AMSSQLProducer {

    /**
     * 功能：固定资产变更表(EAM) AMS_FA_CHG_HISTORY 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsFaChgHistoryDTO 本次操作的数据
     */
    public AmsFaChgHistoryModel(SfUserDTO userAccount,
                                AmsFaChgHistoryDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成固定资产变更表(EAM) AMS_FA_CHG_HISTORY数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsFaChgHistoryDTO dto = (AmsFaChgHistoryDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " AMS_FA_CHG_HISTORY("
                        + " CHG_LOG_ID,"
                        + " BARCODE,"
                        + " FROM_ORGANIZATION_ID,"
                        + " TO_ORGANIZATION_ID,"
                        + " FROM_DEPT,"
                        + " TO_DEPT,"
                        + " TRANS_ID,"
                        + " FROM_OBJECT_NO,"
                        + " TO_OBJECT_NO,"
                        + " FROM_PERSON,"
                        + " TO_PERSON,"
                        + " FROM_STATUS,"
                        + " TO_STATUS,"
                        + " CREATED_BY"
                        + ") VALUES ("
                        +
                "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getFromOrganizationId());
        sqlArgs.add(dto.getToOrganizationId());
        sqlArgs.add(dto.getFromDept());
        sqlArgs.add(dto.getToDept());
        sqlArgs.add(dto.getTransId());
        sqlArgs.add(dto.getFromObjectNo());
        sqlArgs.add(dto.getToObjectNo());
        sqlArgs.add(dto.getFromPerson());
        sqlArgs.add(dto.getToPerson());
        sqlArgs.add(dto.getFromStatus());
        sqlArgs.add(dto.getToStatus());
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成固定资产变更表(EAM) AMS_FA_CHG_HISTORY数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsFaChgHistoryDTO dto = (AmsFaChgHistoryDTO) dtoParameter;
        String sqlStr = "SELECT "
                        + " CHG_LOG_ID,"
                        + " BARCODE,"
                        + " FROM_ORGANIZATION_ID,"
                        + " TO_ORGANIZATION_ID,"
                        + " FROM_DEPT,"
                        + " TO_DEPT,"
                        + " TRANS_ID,"
                        + " FROM_OBJECT_NO,"
                        + " TO_OBJECT_NO,"
                        + " FROM_PERSON,"
                        + " TO_PERSON,"
                        + " FROM_STATUS,"
                        + " TO_STATUS,"
                        + " CREATION_DATE,"
                        + " CREATED_BY"
                        + " FROM"
                        + " AMS_FA_CHG_HISTORY"
                        + " WHERE"
                        + " CHG_LOG_ID = ?";
        sqlArgs.add(dto.getChgLogId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：根据外键关联字段 barcode 构造查询数据SQL。
     * 框架自动生成数据固定资产变更表(EAM) AMS_FA_CHG_HISTORY详细信息查询SQLModel，请根据实际需要修改。
     * @param barcode String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByBarcodeModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                        + " BARCODE,"
                        + " CHG_LOG_ID,"
                        + " FROM_ORGANIZATION_ID,"
                        + " TO_ORGANIZATION_ID,"
                        + " FROM_DEPT,"
                        + " TO_DEPT,"
                        + " TRANS_ID,"
                        + " FROM_OBJECT_NO,"
                        + " TO_OBJECT_NO,"
                        + " FROM_PERSON,"
                        + " TO_PERSON,"
                        + " FROM_STATUS,"
                        + " TO_STATUS,"
                        + " CREATION_DATE,"
                        + " CREATED_BY"
                        + " FROM"
                        + " AMS_FA_CHG_HISTORY"
                        + " WHERE"
                        + " BARCODE = ?";
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 transId 构造查询数据SQL。
     * 框架自动生成数据固定资产变更表(EAM) AMS_FA_CHG_HISTORY详细信息查询SQLModel，请根据实际需要修改。
     * @param transId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                        + " CHG_LOG_ID,"
                        + " BARCODE,"
                        + " FROM_ORGANIZATION_ID,"
                        + " TO_ORGANIZATION_ID,"
                        + " FROM_DEPT,"
                        + " TO_DEPT,"
                        + " FROM_OBJECT_NO,"
                        + " TO_OBJECT_NO,"
                        + " FROM_PERSON,"
                        + " TO_PERSON,"
                        + " FROM_STATUS,"
                        + " TO_STATUS,"
                        + " CREATION_DATE,"
                        + " CREATED_BY"
                        + " FROM"
                        + " AMS_FA_CHG_HISTORY"
                        + " WHERE"
                        + " TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键获取数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        AmsFaChgHistoryDTO dto = (AmsFaChgHistoryDTO) dtoParameter;
        if (foreignKey.equals("barcode")) {
            sqlModel = getDataByBarcodeModel(dto.getBarcode());
        } else if (foreignKey.equals("transId")) {
            sqlModel = getDataByTransIdModel(dto.getTransId());
        }
        return sqlModel;
    }


    /**
     * 功能：构造资产调拨历史记录
     * @return SQLModel
     */
    public SQLModel getAssetsTransferLogModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsFaChgHistoryDTO dto = (AmsFaChgHistoryDTO) dtoParameter;
        String sqlStr = "INSERT INTO"
                        + " AMS_FA_CHG_HISTORY("
                        + " CHG_LOG_ID,"
                        + " BARCODE,"
                        + " BARCODE,"
                        + " TRANS_ID,"
                        + " FROM_ORGANIZATION_ID,"
                        + " TO_ORGANIZATION_ID,"
                        + " FROM_DEPT,"
                        + " TO_DEPT,"
                        + " FROM_OBJECT_NO,"
                        + " TO_OBJECT_NO,"
                        + " FROM_PERSON,"
                        + " TO_PERSON,"
                        + " FROM_STATUS,"
                        + " TO_STATUS,"
                        + " CREATED_BY"
                        + " ) ("
                        + " SELECT"
                        + "  NEWID() ,"
                        + " EFA.BARCODE,"
                        + " EFA.BARCODE,"
                        + " AATH.TRANS_ID,"
                        + " AATH.ORGANIZATION_ID,"
                        + " EOCM.ORGANIZATION_ID,"
                        + " AATH.FROM_DEPT,"
                        + " AATH.TO_DEPT,"
                        + " EO.WORKORDER_OBJECT_NO,"
                        + " AATL.ASSIGNED_TO_LOCATION,"
                        + " EFA.ASSIGNED_TO_NUMBER,"
                        + " AATL.RESPONSIBILITY_USER,"
                        + " ?,"
                        + " ?,"
                        + " ?"
                        + " FROM"
                        + " ETS_FA_ASSETS       EFA,"
                        + " AMS_ASSETS_TRANS_HEADER  AATH,"
                        + " ETS_OBJECT          EO,"
                        + " AMS_MIS_DEPT        AMD,"
                        + " ETS_OU_CITY_MAP     EOCM,"
                        + " AMS_ASSETS_TRANS_LINE  AATL"
                        + " WHERE"
                        + " EFA.BARCODE = AATL.BARCODE"
                        + " AND AATL.TRANS_ID = AATH.TRANS_ID"
                        +
                " AND EFA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION"
                        + " AND EO.ORGANIZATION_ID = AATH.ORGANIZATION_ID"
                        + " AND AATH.TO_DEPT = AMD.DEPT_CODE"
                        + " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
                        + " AND AATH.TRANS_ID = ?"
                        + " AND AATL.BARCODE = ?)";
        sqlArgs.add(AssetsDictConstant.ASSETS_STS_NORMAL);
        sqlArgs.add(AssetsDictConstant.ASSETS_STS_NORMAL);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getTransId());
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造资产报废历史记录
     * @return SQLModel
     */
    public SQLModel getAssetsDiscardLogModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsFaChgHistoryDTO dto = (AmsFaChgHistoryDTO) dtoParameter;
        String sqlStr = "INSERT INTO"
                        + " AMS_FA_CHG_HISTORY("
                        + " CHG_LOG_ID,"
                        + " BARCODE,"
                        + " BARCODE,"
                        + " TRANS_ID,"
                        + " FROM_ORGANIZATION_ID,"
                        + " FROM_DEPT,"
                        + " FROM_OBJECT_NO,"
                        + " FROM_PERSON,"
                        + " FROM_STATUS,"
                        + " TO_STATUS,"
                        + " CREATED_BY"
                        + " ) ("
                        + " SELECT"
                        + "  NEWID() ,"
                        + " EFA.BARCODE,"
                        + " EFA.BARCODE,"
                        + " AATH.TRANS_ID,"
                        + " AATH.ORGANIZATION_ID,"
                        + " AATH.FROM_DEPT,"
                        + " EO.WORKORDER_OBJECT_NO,"
                        + " EFA.ASSIGNED_TO_NUMBER,"
                        + " ?,"
                        + " ?,"
                        + " ?"
                        + " FROM"
                        + " ETS_FA_ASSETS       EFA,"
                        + " AMS_ASSETS_TRANS_HEADER  AATH,"
                        + " ETS_OBJECT          EO,"
                        + " AMS_ASSETS_TRANS_LINE  AATL"
                        + " WHERE"
                        + " EFA.BARCODE = AATL.BARCODE"
                        + " AND AATL.TRANS_ID = AATH.TRANS_ID"
                        +
                " AND EFA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION"
                        + " AND EO.ORGANIZATION_ID = AATH.ORGANIZATION_ID"
                        + " AND AATH.TRANS_ID = ?"
                        + " AND AATL.BARCODE = ?)";
        sqlArgs.add(AssetsDictConstant.ASSETS_STS_NORMAL);
        sqlArgs.add(AssetsDictConstant.ASSETS_STS_DISCARD);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getTransId());
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：构造资产处置历史记录
     * @return SQLModel
     */
    public SQLModel getAssetsClearLogModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsFaChgHistoryDTO dto = (AmsFaChgHistoryDTO) dtoParameter;
        String sqlStr = "INSERT INTO"
                        + " AMS_FA_CHG_HISTORY("
                        + " CHG_LOG_ID,"
                        + " BARCODE,"
                        + " BARCODE,"
                        + " TRANS_ID,"
                        + " FROM_ORGANIZATION_ID,"
                        + " FROM_DEPT,"
                        + " FROM_OBJECT_NO,"
                        + " FROM_PERSON,"
                        + " FROM_STATUS,"
                        + " TO_STATUS,"
                        + " CREATED_BY"
                        + " ) ("
                        + " SELECT"
                        + "  NEWID(),"
                        + " EFA.BARCODE,"
                        + " EFA.BARCODE,"
                        + " AATH.TRANS_ID,"
                        + " AATH.ORGANIZATION_ID,"
                        + " AATH.FROM_DEPT,"
                        + " EO.WORKORDER_OBJECT_NO,"
                        + " EFA.ASSIGNED_TO_NUMBER,"
                        + " ?,"
                        + " ?,"
                        + " ?"
                        + " FROM"
                        + " ETS_FA_ASSETS       EFA,"
                        + " AMS_ASSETS_TRANS_HEADER  AATH,"
                        + " ETS_OBJECT          EO,"
                        + " AMS_ASSETS_TRANS_LINE  AATL"
                        + " WHERE"
                        + " EFA.BARCODE = AATL.BARCODE"
                        + " AND AATL.TRANS_ID = AATH.TRANS_ID"
                        +
                " AND EFA.ASSETS_LOCATION = EO.WORKORDER_OBJECT_LOCATION"
                        + " AND EO.ORGANIZATION_ID = AATH.ORGANIZATION_ID"
                        + " AND AATH.TRANS_ID = ?"
                        + " AND AATL.BARCODE = ?)";
        sqlArgs.add(AssetsDictConstant.ASSETS_STS_NORMAL);
        sqlArgs.add(AssetsDictConstant.ASSETS_STS_CLEAR);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getTransId());
        sqlArgs.add(dto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
