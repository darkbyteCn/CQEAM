package com.sino.ams.others.cabel.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.others.cabel.dto.AmsCabelInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsCabelInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsCabelInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsCabelInfoModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：线缆类设备扩展信息(EAM) AMS_CABEL_INFO 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsCabelInfoDTO 本次操作的数据
     */
    public AmsCabelInfoModel(SfUserDTO userAccount, AmsCabelInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成线缆类设备扩展信息(EAM) AMS_CABEL_INFO数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " AMS_CABEL_INFO("
                    + " BARCODE,"
                    + " FROM_ADDRESS,"
                    + " TO_ADDRESS,"
                    + " SPREAD_TYPE,"
                    + " CABEL_USAGE,"
                    + " FROM_TUDE,"
                    + " TO_TUDE,"
                    + " CABEL_DEPTH,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_BY"
                    + ") VALUES ("
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            sqlArgs.add(amsCabelInfo.getBarcode());
            sqlArgs.add(amsCabelInfo.getFromAddress());
            sqlArgs.add(amsCabelInfo.getToAddress());
            sqlArgs.add(amsCabelInfo.getSpreadType());
            sqlArgs.add(amsCabelInfo.getCabelUsage());
            sqlArgs.add(amsCabelInfo.getFromTude());
            sqlArgs.add(amsCabelInfo.getToTude());
            sqlArgs.add(amsCabelInfo.getCabelDepth());
            sqlArgs.add(amsCabelInfo.getCreationDate());
            sqlArgs.add(amsCabelInfo.getCreatedBy());
            sqlArgs.add(amsCabelInfo.getLastUpdateDate());
            sqlArgs.add(amsCabelInfo.getLastUpdateBy());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成线缆类设备扩展信息(EAM) AMS_CABEL_INFO数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
            String sqlStr = "UPDATE AMS_CABEL_INFO"
                    + " SET"
                    + " FROM_ADDRESS = ?,"
                    + " TO_ADDRESS = ?,"
                    + " SPREAD_TYPE = ?,"
                    + " CABEL_USAGE = ?,"
                    + " FROM_TUDE = ?,"
                    + " TO_TUDE = ?,"
                    + " CABEL_DEPTH = ?,"
                    + " CREATION_DATE = ?,"
                    + " CREATED_BY = ?,"
                    + " LAST_UPDATE_DATE = ?,"
                    + " LAST_UPDATE_BY = ?"
                    + " WHERE"
                    + " BARCODE = ?";

            sqlArgs.add(amsCabelInfo.getFromAddress());
            sqlArgs.add(amsCabelInfo.getToAddress());
            sqlArgs.add(amsCabelInfo.getSpreadType());
            sqlArgs.add(amsCabelInfo.getCabelUsage());
            sqlArgs.add(amsCabelInfo.getFromTude());
            sqlArgs.add(amsCabelInfo.getToTude());
            sqlArgs.add(amsCabelInfo.getCabelDepth());
            sqlArgs.add(amsCabelInfo.getCreationDate());
            sqlArgs.add(amsCabelInfo.getCreatedBy());
            sqlArgs.add(amsCabelInfo.getLastUpdateDate());
            sqlArgs.add(amsCabelInfo.getLastUpdateBy());
            sqlArgs.add(amsCabelInfo.getBarcode());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成线缆类设备扩展信息(EAM) AMS_CABEL_INFO数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = "SELECT EII.BARCODE, /*条码*/" +
                " ESI.ITEM_CODE, /*Unshow设备代码*/" +
                " ESI.ITEM_CATEGORY, /*设备分类*/" +
                " ESI.ITEM_NAME, /*设备名称*/" +
                " ESI.ITEM_SPEC, /*设备型号*/" +
                " ACI.FROM_ADDRESS, /*起始地址*/" +
                " ACI.TO_ADDRESS, /*目的地址*/" +
                " ACI.SPREAD_TYPE, /*铺设方式*/" +
                " ACI.CABEL_USAGE, /*线缆用途*/" +
                " ACI.FROM_TUDE, /*始地点经纬度*/" +
                " ACI.TO_TUDE, /*止地点经纬度*/" +
                " ACI.CABEL_DEPTH, /*埋深*/" +
                " EII.ORGANIZATION_ID /*组织*/" +
                "FROM   ETS_ITEM_INFO   EII," +
                " ETS_SYSTEM_ITEM ESI," +
                " AMS_CABEL_INFO  ACI " +
                "WHERE  ESI.ITEM_CATEGORY = 'CABEL' AND " +
                " ESI.ITEM_CODE = EII.ITEM_CODE AND " +
                " EII.BARCODE *= ACI.BARCODE AND " +
//                " EII.ORGANIZATION_ID = ? AND " +
                " EII.BARCODE = ? ";
//        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成线缆类设备扩展信息(EAM) AMS_CABEL_INFO多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " BARCODE,"
                    + " FROM_ADDRESS,"
                    + " TO_ADDRESS,"
                    + " SPREAD_TYPE,"
                    + " CABEL_USAGE,"
                    + " FROM_TUDE,"
                    + " TO_TUDE,"
                    + " CABEL_DEPTH,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_BY"
                    + " FROM"
                    + " AMS_CABEL_INFO"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR BARCODE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR FROM_ADDRESS LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR TO_ADDRESS LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR SPREAD_TYPE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR CABEL_USAGE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR FROM_TUDE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR TO_TUDE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR CABEL_DEPTH LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
            sqlArgs.add(amsCabelInfo.getBarcode());
            sqlArgs.add(amsCabelInfo.getBarcode());
            sqlArgs.add(amsCabelInfo.getFromAddress());
            sqlArgs.add(amsCabelInfo.getFromAddress());
            sqlArgs.add(amsCabelInfo.getToAddress());
            sqlArgs.add(amsCabelInfo.getToAddress());
            sqlArgs.add(amsCabelInfo.getSpreadType());
            sqlArgs.add(amsCabelInfo.getSpreadType());
            sqlArgs.add(amsCabelInfo.getCabelUsage());
            sqlArgs.add(amsCabelInfo.getCabelUsage());
            sqlArgs.add(amsCabelInfo.getFromTude());
            sqlArgs.add(amsCabelInfo.getFromTude());
            sqlArgs.add(amsCabelInfo.getToTude());
            sqlArgs.add(amsCabelInfo.getToTude());
            sqlArgs.add(amsCabelInfo.getCabelDepth());
            sqlArgs.add(amsCabelInfo.getCabelDepth());
            sqlArgs.add(amsCabelInfo.getCreationDate());
            sqlArgs.add(amsCabelInfo.getCreationDate());
            sqlArgs.add(amsCabelInfo.getCreatedBy());
            sqlArgs.add(amsCabelInfo.getCreatedBy());
            sqlArgs.add(amsCabelInfo.getLastUpdateDate());
            sqlArgs.add(amsCabelInfo.getLastUpdateDate());
            sqlArgs.add(amsCabelInfo.getLastUpdateBy());
            sqlArgs.add(amsCabelInfo.getLastUpdateBy());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 barcodeNo 构造查询数据SQL。
     * 框架自动生成数据线缆类设备扩展信息(EAM) AMS_CABEL_INFO详细信息查询SQLModel，请根据实际需要修改。
     *
     * @param barcodeNo String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByBarcodeNoModel(String barcodeNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " FROM_ADDRESS,"
                + " TO_ADDRESS,"
                + " SPREAD_TYPE,"
                + " CABEL_USAGE,"
                + " FROM_TUDE,"
                + " TO_TUDE,"
                + " CABEL_DEPTH,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_CABEL_INFO"
                + " WHERE"
                + " BARCODE = ?";
        sqlArgs.add(barcodeNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键获取数据
     *
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        if (foreignKey.equals("barcodeNo")) {
            sqlModel = getDataByBarcodeNoModel(amsCabelInfo.getBarcode());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成线缆类设备扩展信息(EAM) AMS_CABEL_INFO页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = " SELECT * " +
                "FROM   (SELECT EII.BARCODE, /*条码*/ " +
                "        ESI.ITEM_CATEGORY, /*设备分类*/ " +
                "        ESI.ITEM_NAME, /*设备名称*/ " +
                "        ESI.ITEM_SPEC, /*设备型号*/ " +
                "        ACI.FROM_ADDRESS, /*起始地址*/ " +
                "        ACI.TO_ADDRESS, /*目的地址*/ " +
                "        ACI.SPREAD_TYPE, /*铺设方式*/ " +
                "        ACI.CABEL_USAGE, /*线缆用途*/ " +
                "        ST.NAME SPREAD_TYPE_NAME, /*铺设方式*/ " +
                "        CU.NAME CABEL_USAGE_NAME, /*线缆用途*/ " +
                "        ACI.CABEL_DEPTH /*埋深*/ " +
                "    FROM   ETS_ITEM_INFO EII, " +
                "        ETS_SYSTEM_ITEM ESI, " +
                "        AMS_CABEL_INFO ACI, " +
                "        (SELECT EFV.CODE  VALUE, " +
                "            EFV.VALUE NAME " +
                "        FROM   ETS_FLEX_VALUES    EFV, " +
                "            ETS_FLEX_VALUE_SET EFVS " +
                "        WHERE  EFVS.CODE = 'CABEL_USAGE' AND " +
                "            EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID) CU, " +
                "        (SELECT EFV.CODE  VALUE, " +
                "            EFV.VALUE NAME " +
                "        FROM   ETS_FLEX_VALUES    EFV, " +
                "            ETS_FLEX_VALUE_SET EFVS " +
                "        WHERE  EFVS.CODE = 'SPREAD_TYPE' AND " +
                "            EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID) ST " +
                "    WHERE  ESI.ITEM_CATEGORY = 'CABEL' AND " +
                "        ESI.ITEM_CODE = EII.ITEM_CODE AND " +
                "        EII.ORGANIZATION_ID =?  AND " +
                "        EII.BARCODE *= ACI.BARCODE AND " +
                "        ACI.SPREAD_TYPE *= ST.VALUE AND " +
                "        ACI.CABEL_USAGE *= CU.VALUE  ) T " +
                "WHERE ( " + SyBaseSQLUtil.isNull() + "  OR T.BARCODE LIKE ?) AND" +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR T.SPREAD_TYPE = ?) AND " +
                "      ( " + SyBaseSQLUtil.isNull() + "  OR T.CABEL_USAGE = ?) " +
                " ORDER BY T.CABEL_USAGE,T.SPREAD_TYPE";

        sqlArgs.add(sfUser.getOrganizationId());

        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlArgs.add(amsCabelInfo.getSpreadType());
        sqlArgs.add(amsCabelInfo.getSpreadType());
        sqlArgs.add(amsCabelInfo.getCabelUsage());
        sqlArgs.add(amsCabelInfo.getCabelUsage());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    //-------------------------------------------------------------------------------------------

    /**
     * 功能：检查 AMS_CABEL_INFO表中 BARCODE信息是否存在
     */
    public SQLModel getCheckInACIModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1 FROM AMS_CABEL_INFO ACI WHERE ACI.BARCODE = ?";
        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：检查 ETS_SYSTEM_ITEM 表中该设备类型信息是否存在
     */
    public SQLModel getCheckInESIModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1 FROM ETS_SYSTEM_ITEM ESI WHERE ESI.ITEM_NAME = ? AND ESI.ITEM_SPEC=? AND ESI.ITEM_CATEGORY  = ? ";
        sqlArgs.add(amsCabelInfo.getItemName());
        sqlArgs.add(amsCabelInfo.getItemSpec());
        sqlArgs.add(amsCabelInfo.getItemCategory());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：检查 ETS_SYSITEM_DISTRIBUTE 表中是否将该设备类型分配给本地市
     */
    public SQLModel getCheckInESDModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1 FROM ETS_SYSITEM_DISTRIBUTE ESD WHERE ESD.ITEM_CODE = ? AND ESD.ORGANIZATION_ID  = ? ";
        sqlArgs.add(amsCabelInfo.getItemCode());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：检查 ETS_ITEM_INFO 表中是否存在该设备
     */
    public SQLModel getCheckInEIIModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = ?";
        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：检查 ETS_ITEM_INFO 表中记录设备类型是否与现行设备类型一致
     */
    public SQLModel getCheckSameTypeInEIIModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = "SELECT 1 FROM ETS_ITEM_INFO EII,ETS_SYSTEM_ITEM ESI " +
                " WHERE  EII.ITEM_CODE = ESI.ITEM_CODE " +
                "AND  EII.BARCODE = ? AND ESI.ITEM_CODE = ? ";
        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlArgs.add(amsCabelInfo.getItemCode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    //-------------------------------------------------

    /**
     * 功能： 插入一条申请信息到 AMS_APPLY_SYSTEM_ITEM 表
     */
    public SQLModel getCreateInAASIModel() {
        SQLModel sqlModel = new SQLModel();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_APPLY_SYSTEM_ITEM " +
                "(ITEM_CODE , APPLY_OU,CREATION_DATE ,CREATED_BY )VALUES(?,?,GETDATE(),?)";
        sqlArgs.add(amsCabelInfo.getItemCode());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能： 插入一条分配信息到 ETS_SYSITEM_DISTRIBUTE 表
     */
    public SQLModel getCreateInESDModel() {
        SQLModel sqlModel = new SQLModel();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSITEM_DISTRIBUTE  " +
                "(SYSTEM_ID, ITEM_CODE, ORGANIZATION_ID, CREATION_DATE, CREATED_BY, IS_TMP)  " +
                "VALUES (NEWID() ,?,?,GETDATE(),?,'Y')";
        sqlArgs.add(amsCabelInfo.getItemCode());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能： 插入一条设备类型信息到  ETS_SYSTEM_ITEM 表
     */
    public SQLModel getCreateInESIModel() {
        SQLModel sqlModel = new SQLModel();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM\n " +
                "(ITEM_CODE,ITEM_NAME,ITEM_SPEC,ITEM_CATEGORY," +
                "MEMO ,ITEM_UNIT ,CREATED_BY," +
                "MASTER_ORGANIZATION_ID,BARCODE_NULLABLE,IS_TMP_CODE)VALUES\n " +
                "( NEWID() ,?,?,?," +
                "?,?,?," +
                "?,'Y','Y')";
        sqlArgs.add(amsCabelInfo.getItemName());
        sqlArgs.add(amsCabelInfo.getItemSpec());
        sqlArgs.add(amsCabelInfo.getItemCategory());
        sqlArgs.add("AmsCabelInfoModel自动生成");
        sqlArgs.add("条");
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add("82"); //MASTER_ORGANIZATION_ID
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能： 插入一条设备信息到   ETS_ITEM_INFO 表
     */

    public SQLModel getCreateInEIIModel() {
        SQLModel sqlModel = new SQLModel();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO ETS_ITEM_INFO " +
                " (SYSTEMID,BARCODE," +
                " ITEM_CODE,CREATION_DATE," +
                " CREATED_BY,ORGANIZATION_ID) " +
                " VALUES " +
                "( NEWID() ,? ,? ,GETDATE(),? ,?)";
        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlArgs.add(amsCabelInfo.getItemCode());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能： 更新一条设备信息到   ETS_ITEM_INFO 表
     */
    public SQLModel getUpdateInEIIModel() {
        SQLModel sqlModel = new SQLModel();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO  EII  " +
                " SET EII.ITEM_CODE = ? " +
                " WHERE EII.BARCODE = ? ";
        sqlArgs.add(amsCabelInfo.getItemCode());
        sqlArgs.add(amsCabelInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getItemCodeInESIModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) dtoParameter;
        String sqlStr = " SELECT ESI.ITEM_CODE " +
                " FROM ETS_SYSTEM_ITEM ESI " +
                " WHERE ESI.ITEM_NAME = ? AND ESI.ITEM_SPEC= ? AND ESI.ITEM_CATEGORY = ? ";
        sqlArgs.add(amsCabelInfo.getItemName());
        sqlArgs.add(amsCabelInfo.getItemSpec());
        sqlArgs.add(amsCabelInfo.getItemCategory());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}