package com.sino.ams.spare.version.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.version.dto.AmsItemVersionDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemVersionModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemVersionModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsItemVersionModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：备件版本变动表 AMS_ITEM_VERSION 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsItemVersionDTO 本次操作的数据
     */
    public AmsItemVersionModel(SfUserDTO userAccount, AmsItemVersionDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成备件版本变动表 AMS_ITEM_VERSION数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " AMS_ITEM_VERSION("
                    + " BARCODE,"
                    + " ITEM_CODE,"
                    + " VENDOR_BARCODE,"
                    + " VERSION_NO,"
                    + " ORGANIZATION_ID,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_BY,"
                    + " TRANS_ID,"
                    + " REMARK,"
                    + " VERSION_ID"
                    + ") VALUES ("
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, AMS_ITEM_VERSION_S.NEXTVAL)";

            sqlArgs.add(amsItemVersion.getBarcode());
            sqlArgs.add(amsItemVersion.getItemCode());
            sqlArgs.add(amsItemVersion.getVendorBarcode());
            sqlArgs.add(amsItemVersion.getVersionNo());
            sqlArgs.add(amsItemVersion.getOrganizationId());
            sqlArgs.add(amsItemVersion.getCreationDate());
            sqlArgs.add(amsItemVersion.getCreatedBy());
            sqlArgs.add(amsItemVersion.getLastUpdateDate());
            sqlArgs.add(amsItemVersion.getLastUpdateBy());
            sqlArgs.add(amsItemVersion.getTransId());
            sqlArgs.add(amsItemVersion.getRemark());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件版本变动表 AMS_ITEM_VERSION数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
            String sqlStr = "UPDATE AMS_ITEM_VERSION"
                    + " SET"
                    + " BARCODE = ?,"
                    + " ITEM_CODE = ?,"
                    + " VENDOR_BARCODE = ?,"
                    + " VERSION_NO = ?,"
                    + " ORGANIZATION_ID = ?,"
                    + " CREATION_DATE = ?,"
                    + " CREATED_BY = ?,"
                    + " LAST_UPDATE_DATE = ?,"
                    + " LAST_UPDATE_BY = ?,"
                    + " TRANS_ID = ?,"
                    + " REMARK = ?,"
                    + " WHERE"
                    + " VERSION_ID = ?";

            sqlArgs.add(amsItemVersion.getBarcode());
            sqlArgs.add(amsItemVersion.getItemCode());
            sqlArgs.add(amsItemVersion.getVendorBarcode());
            sqlArgs.add(amsItemVersion.getVersionNo());
            sqlArgs.add(amsItemVersion.getOrganizationId());
            sqlArgs.add(amsItemVersion.getCreationDate());
            sqlArgs.add(amsItemVersion.getCreatedBy());
            sqlArgs.add(amsItemVersion.getLastUpdateDate());
            sqlArgs.add(amsItemVersion.getLastUpdateBy());
            sqlArgs.add(amsItemVersion.getTransId());
            sqlArgs.add(amsItemVersion.getRemark());
            sqlArgs.add(amsItemVersion.getVersionId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件版本变动表 AMS_ITEM_VERSION数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_ITEM_VERSION"
                + " WHERE"
                + " VERSION_ID = ?";
        sqlArgs.add(amsItemVersion.getVersionId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件版本变动表 AMS_ITEM_VERSION数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " BARCODE,"
                + " ITEM_CODE,"
                + " VENDOR_BARCODE,"
                + " VERSION_NO,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " TRANS_ID,"
                + " REMARK,"
                + " VERSION_ID"
                + " FROM"
                + " AMS_ITEM_VERSION"
                + " WHERE"
                + " VERSION_ID = ?";
        sqlArgs.add(amsItemVersion.getVersionId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件版本变动表 AMS_ITEM_VERSION多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " BARCODE,"
                    + " ITEM_CODE,"
                    + " VENDOR_BARCODE,"
                    + " VERSION_NO,"
                    + " ORGANIZATION_ID,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_BY,"
                    + " TRANS_ID,"
                    + " REMARK,"
                    + " VERSION_ID"
                    + " FROM"
                    + " AMS_ITEM_VERSION"
                    + " WHERE"
                    + " (? IS NULL OR BARCODE LIKE ?)"
                    + " AND (? IS NULL OR ITEM_CODE LIKE ?)"
                    + " AND (? IS NULL OR VENDOR_BARCODE LIKE ?)"
                    + " AND (? IS NULL OR VERSION_NO LIKE ?)"
                    + " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
                    + " AND (? IS NULL OR CREATION_DATE LIKE ?)"
                    + " AND (? IS NULL OR CREATED_BY LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_BY LIKE ?)"
                    + " AND (? IS NULL OR TRANS_ID LIKE ?)"
                    + " AND (? IS NULL OR REMARK LIKE ?)"
                    + " AND (? IS NULL OR VERSION_ID LIKE ?)";
            sqlArgs.add(amsItemVersion.getBarcode());
            sqlArgs.add(amsItemVersion.getBarcode());
            sqlArgs.add(amsItemVersion.getItemCode());
            sqlArgs.add(amsItemVersion.getItemCode());
            sqlArgs.add(amsItemVersion.getVendorBarcode());
            sqlArgs.add(amsItemVersion.getVendorBarcode());
            sqlArgs.add(amsItemVersion.getVersionNo());
            sqlArgs.add(amsItemVersion.getVersionNo());
            sqlArgs.add(amsItemVersion.getOrganizationId());
            sqlArgs.add(amsItemVersion.getOrganizationId());
            sqlArgs.add(amsItemVersion.getCreationDate());
            sqlArgs.add(amsItemVersion.getCreationDate());
            sqlArgs.add(amsItemVersion.getCreatedBy());
            sqlArgs.add(amsItemVersion.getCreatedBy());
            sqlArgs.add(amsItemVersion.getLastUpdateDate());
            sqlArgs.add(amsItemVersion.getLastUpdateDate());
            sqlArgs.add(amsItemVersion.getLastUpdateBy());
            sqlArgs.add(amsItemVersion.getLastUpdateBy());
            sqlArgs.add(amsItemVersion.getTransId());
            sqlArgs.add(amsItemVersion.getTransId());
            sqlArgs.add(amsItemVersion.getRemark());
            sqlArgs.add(amsItemVersion.getRemark());
            sqlArgs.add(amsItemVersion.getVersionId());
            sqlArgs.add(amsItemVersion.getVersionId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 barcode 构造查询数据SQL。
     * 框架自动生成数据备件版本变动表 AMS_ITEM_VERSION详细信息查询SQLModel，请根据实际需要修改。
     *
     * @param barcode String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByBarcodeModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " ITEM_CODE,"
                + " VENDOR_BARCODE,"
                + " VERSION_NO,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " TRANS_ID,"
                + " REMARK,"
                + " VERSION_ID"
                + " FROM"
                + " AMS_ITEM_VERSION"
                + " WHERE"
                + " BARCODE = ?";
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 itemCode 构造查询数据SQL。
     * 框架自动生成数据备件版本变动表 AMS_ITEM_VERSION详细信息查询SQLModel，请根据实际需要修改。
     *
     * @param itemCode String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByItemCodeModel(String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " BARCODE,"
                + " VENDOR_BARCODE,"
                + " VERSION_NO,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " TRANS_ID,"
                + " REMARK,"
                + " VERSION_ID"
                + " FROM"
                + " AMS_ITEM_VERSION"
                + " WHERE"
                + " ITEM_CODE = ?";
        sqlArgs.add(itemCode);

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
        AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
        if (foreignKey.equals("barcode")) {
            sqlModel = getDataByBarcodeModel(amsItemVersion.getBarcode());
        } else if (foreignKey.equals("itemCode")) {
            sqlModel = getDataByItemCodeModel(amsItemVersion.getItemCode());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 barcode 构造数据删除SQL。
     * 框架自动生成数据备件版本变动表 AMS_ITEM_VERSION数据删除SQLModel，请根据实际需要修改。
     *
     * @param barcode String
     * @return SQLModel 返回数据删除用SQLModel
     */
    private SQLModel getDeleteByBarcodeModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " ITEM_CODE,"
                + " VENDOR_BARCODE,"
                + " VERSION_NO,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " TRANS_ID,"
                + " REMARK,"
                + " VERSION_ID"
                + " FROM"
                + " AMS_ITEM_VERSION"
                + " WHERE"
                + " BARCODE = ?";
        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 itemCode 构造数据删除SQL。
     * 框架自动生成数据备件版本变动表 AMS_ITEM_VERSION数据删除SQLModel，请根据实际需要修改。
     *
     * @param itemCode String
     * @return SQLModel 返回数据删除用SQLModel
     */
    private SQLModel getDeleteByItemCodeModel(String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " BARCODE,"
                + " VENDOR_BARCODE,"
                + " VERSION_NO,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " TRANS_ID,"
                + " REMARK,"
                + " VERSION_ID"
                + " FROM"
                + " AMS_ITEM_VERSION"
                + " WHERE"
                + " ITEM_CODE = ?";
        sqlArgs.add(itemCode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键字段删除数据
     *
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
        if (foreignKey.equals("barcode")) {
            sqlModel = getDeleteByBarcodeModel(amsItemVersion.getBarcode());
        } else if (foreignKey.equals("itemCode")) {
            sqlModel = getDeleteByItemCodeModel(amsItemVersion.getItemCode());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件版本变动表 AMS_ITEM_VERSION页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " BARCODE,"
                    + " ITEM_CODE,"
                    + " VENDOR_BARCODE,"
                    + " VERSION_NO,"
                    + " ORGANIZATION_ID,"
                    + " CREATION_DATE,"
                    + " CREATED_BY,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_BY,"
                    + " TRANS_ID,"
                    + " REMARK,"
                    + " VERSION_ID"
                    + " FROM"
                    + " AMS_ITEM_VERSION"
                    + " WHERE"
                    + " (? IS NULL OR BARCODE LIKE ?)"
                    + " AND (? IS NULL OR ITEM_CODE LIKE ?)"
                    + " AND (? IS NULL OR VENDOR_BARCODE LIKE ?)"
                    + " AND (? IS NULL OR VERSION_NO LIKE ?)"
                    + " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
                    + " AND (? IS NULL OR CREATION_DATE LIKE ?)"
                    + " AND (? IS NULL OR CREATED_BY LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_BY LIKE ?)"
                    + " AND (? IS NULL OR TRANS_ID LIKE ?)"
                    + " AND (? IS NULL OR REMARK LIKE ?)"
                    + " AND (? IS NULL OR VERSION_ID LIKE ?)";
            sqlArgs.add(amsItemVersion.getBarcode());
            sqlArgs.add(amsItemVersion.getBarcode());
            sqlArgs.add(amsItemVersion.getItemCode());
            sqlArgs.add(amsItemVersion.getItemCode());
            sqlArgs.add(amsItemVersion.getVendorBarcode());
            sqlArgs.add(amsItemVersion.getVendorBarcode());
            sqlArgs.add(amsItemVersion.getVersionNo());
            sqlArgs.add(amsItemVersion.getVersionNo());
            sqlArgs.add(amsItemVersion.getOrganizationId());
            sqlArgs.add(amsItemVersion.getOrganizationId());
            sqlArgs.add(amsItemVersion.getCreationDate());
            sqlArgs.add(amsItemVersion.getCreationDate());
            sqlArgs.add(amsItemVersion.getCreatedBy());
            sqlArgs.add(amsItemVersion.getCreatedBy());
            sqlArgs.add(amsItemVersion.getLastUpdateDate());
            sqlArgs.add(amsItemVersion.getLastUpdateDate());
            sqlArgs.add(amsItemVersion.getLastUpdateBy());
            sqlArgs.add(amsItemVersion.getLastUpdateBy());
            sqlArgs.add(amsItemVersion.getTransId());
            sqlArgs.add(amsItemVersion.getTransId());
            sqlArgs.add(amsItemVersion.getRemark());
            sqlArgs.add(amsItemVersion.getRemark());
            sqlArgs.add(amsItemVersion.getVersionId());
            sqlArgs.add(amsItemVersion.getVersionId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    //---------------------------------------------------------------------

    /**
     * 功能：查询是否存在某设备类型
     * @return String 存在为1，重复大于1，不存在为0.
     */
    public SQLModel getHasItemModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
        String sqlStr = "SELECT 1                     \n" +
                " FROM   ETS_SYSTEM_ITEM ESI         \n" +
                " WHERE  ESI.ITEM_NAME = ? AND       \n" +
                "       ESI.ITEM_SPEC = ? AND        \n" +
                "       ESI.ITEM_CATEGORY = ?   AND  \n" +
                "       ROWNUM < 2";
        sqlArgs.add(amsItemVersion.getItemCode());
        sqlArgs.add(amsItemVersion.getItemSpec());
        sqlArgs.add(amsItemVersion.getItemCategory());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

	/**
	 * 功能：插入某设备类型
	 * @return SQLModel
	 */
	public SQLModel getCreateSysItemModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemVersionDTO amsItemVersion = (AmsItemVersionDTO) dtoParameter;
        String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM \n" +
                "       (ITEM_CODE,ITEM_NAME,             /*设备代码，设备名称*/\n" +
                "        ITEM_SPEC,ITEM_CATEGORY,         /*设备型号,设备类别*/\n" +
                "        ENABLED,MEMO,                    /*是否有效，注释*/\n" +
                "        MASTER_ORGANIZATION_ID)          /*主组织代码*/\n" +
                " VALUES " +
                "        ( ETS_SYSTEM_ITEM_S.NEXTVAL,?,?,?,'Y',?,86)";
        sqlArgs.add(amsItemVersion.getItemName());
        sqlArgs.add(amsItemVersion.getItemSpec());
        sqlArgs.add(amsItemVersion.getItemCategory());
        sqlArgs.add(amsItemVersion.getRemark());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}
