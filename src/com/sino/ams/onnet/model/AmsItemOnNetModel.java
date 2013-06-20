package com.sino.ams.onnet.model;

import java.util.ArrayList;
import java.util.List;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.onnet.dto.AmsItemOnNetDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemOnNetModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public AmsItemOnNetModel(SfUserDTO userAccount, AmsItemOnNetDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
              String sqlStr = " INSERT INTO "
		                    + " AMS_ITEM_ON_NET("
		                    + " ID,"
		                    + " PART_NO,"
		                    + " QUANTITY,"
		                    + " ORGANIZATION_ID," 
		                    + " REMARK,"
		                    + " LAST_UPDATE_DATE,"
		                    + " LAST_UPDATE_USER"
		                    + " ) VALUES ( "
		                    + " CONVERT(VARCHAR,getNextSeq('AMS_ITEM_ON_NET')), ?, ?, ?, ?, ?, ?)";
            sqlArgs.add(amsItemOnNet.getPartNo());
            sqlArgs.add(amsItemOnNet.getQuantity());
            sqlArgs.add(amsItemOnNet.getOrganizationId());
            sqlArgs.add(amsItemOnNet.getRemark());
            sqlArgs.add(amsItemOnNet.getLastUpdateDate());
            sqlArgs.add(sfUser.getUserId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_ON_NET"
                + " SET"
                + " QUANTITY = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ID = ?";
        sqlArgs.add(amsItemOnNet.getQuantity());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsItemOnNet.getId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_ITEM_ON_NET";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
        String sqlStr = " SELECT " +
                " AION.ID,\n" +
                " AION.ORGANIZATION_ID,\n" +
                " dbo.APP_GET_ORGNIZATION_NAME(AION.ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                " AION.PART_NO, \n" +//备件部件号
                " AMSC.ITEM_NAME,\n" +
                " AMSC.ITEM_SPEC,\n" +
                " AMSC.SPARE_USAGE,\n" +
                " dbo.APP_GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME,\n" +
                " AMSC.ITEM_CATEGORY,\n"+
                " CONVERT(INT, AION.QUANTITY) QUANTITY\n" +
                " FROM \n" +
                " AMS_ITEM_ON_NET AION,\n " +
                " AMS_SPARE_CATEGORY AMSC\n" +
                " WHERE \n" +
                " AION.PART_NO = AMSC.BARCODE\n" +
                " AND AION.ID = ?";
        sqlArgs.add(amsItemOnNet.getId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " ID,"
                    + " PART_NO,"
                    + " QUANTITY,"
                    + " ORGANIZATION_ID,"
                    + " dbo.APP_GET_ORGNIZATION_NAME(AION.ORGANIZATION_ID) ORGNIZATION_NAME,\n"
                    + " REMARK,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_USER"
                    + " FROM"
                    + " AMS_ITEM_ON_NET"
                    + " WHERE"
                    + " ( " + SyBaseSQLUtil.isNull() + "  OR ID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR PART_NO LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR QUANTITY LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ORGANIZATION_ID LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_USER LIKE ?)";
            sqlArgs.add(amsItemOnNet.getId());
            sqlArgs.add(amsItemOnNet.getId());
            sqlArgs.add(amsItemOnNet.getPartNo());
            sqlArgs.add(amsItemOnNet.getPartNo());
            sqlArgs.add(amsItemOnNet.getQuantity());
            sqlArgs.add(amsItemOnNet.getQuantity());
            sqlArgs.add(amsItemOnNet.getOrganizationId());
            sqlArgs.add(amsItemOnNet.getOrganizationId());
            sqlArgs.add(amsItemOnNet.getRemark());
            sqlArgs.add(amsItemOnNet.getRemark());
            sqlArgs.add(amsItemOnNet.getLastUpdateDate());
            sqlArgs.add(amsItemOnNet.getLastUpdateDate());
            sqlArgs.add(amsItemOnNet.getLastUpdateUser());
            sqlArgs.add(amsItemOnNet.getLastUpdateUser());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
        String sqlStr = "SELECT "
                        + " AION.ID,\n"
                        + " AION.ORGANIZATION_ID,\n"
                        + " dbo.APP_GET_ORGNIZATION_NAME(AION.ORGANIZATION_ID) ORGNIZATION_NAME,\n"
                        + " AION.PART_NO, \n"
                        + " AMSC.ITEM_NAME,\n"
                        + " AMSC.ITEM_SPEC,\n"
                        + " AMSC.SPARE_USAGE,\n"
                        + " dbo.APP_GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME,\n" 
                        + " AMSC.ITEM_CATEGORY,\n"
                        + " AION.QUANTITY\n"
                        + " FROM "
                        + " AMS_ITEM_ON_NET AION, AMS_SPARE_CATEGORY AMSC\n"
                        + " WHERE \n"
                        + " AION.PART_NO = AMSC.BARCODE\n"
                        + " AND ( AION.PART_NO LIKE dbo.NVL(?,AION.PART_NO))\n"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_NAME LIKE ?)\n"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_SPEC LIKE ?)\n"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.VENDOR_ID = ?)\n"
                        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.SPARE_USAGE LIKE ?)\n"
                        + " AND ( ? = -1  OR AION.ORGANIZATION_ID = ?)";
        sqlArgs.add(amsItemOnNet.getPartNo());
        sqlArgs.add(amsItemOnNet.getItemName());
        sqlArgs.add(amsItemOnNet.getItemName());
        sqlArgs.add(amsItemOnNet.getItemSpec());
        sqlArgs.add(amsItemOnNet.getItemSpec());
        sqlArgs.add(amsItemOnNet.getVendorId());
        sqlArgs.add(amsItemOnNet.getVendorId());
        sqlArgs.add(amsItemOnNet.getSpareUsage());
        sqlArgs.add(amsItemOnNet.getSpareUsage());
        sqlArgs.add(amsItemOnNet.getOrganizationId());
        sqlArgs.add(amsItemOnNet.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCount() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
        String sqlStr = "SELECT SUM(AION.QUANTITY) TATOL_COUNT\n" 
				        + " FROM \n"
				        + " AMS_ITEM_ON_NET AION,\n " 
				        + " AMS_SPARE_CATEGORY AMSC\n"
				        + " WHERE "
				        + " AION.PART_NO = AMSC.BARCODE"
				        + " AND ( AION.PART_NO LIKE dbo.NVL(?,AION.PART_NO))\n"
				        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_NAME LIKE ?)\n"
				        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.ITEM_SPEC LIKE ?)\n"
				        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.VENDOR_ID = ?)\n"
				        + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMSC.SPARE_USAGE LIKE ?)\n"
				        + " AND ( ? = -1  OR AION.ORGANIZATION_ID = ?)";
				sqlArgs.add(amsItemOnNet.getPartNo());
				sqlArgs.add(amsItemOnNet.getItemName());
				sqlArgs.add(amsItemOnNet.getItemName());
				sqlArgs.add(amsItemOnNet.getItemSpec());
				sqlArgs.add(amsItemOnNet.getItemSpec());
				sqlArgs.add(amsItemOnNet.getVendorId());
				sqlArgs.add(amsItemOnNet.getVendorId());
				sqlArgs.add(amsItemOnNet.getSpareUsage());
				sqlArgs.add(amsItemOnNet.getSpareUsage());
				sqlArgs.add(amsItemOnNet.getOrganizationId());
				sqlArgs.add(amsItemOnNet.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }    

    public SQLModel getUpdateOnNetModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_ON_NET"
                + " SET"
                + " QUANTITY = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE"
                + " ID = ?";
        sqlArgs.add(amsItemOnNet.getQuantity());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsItemOnNet.getId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel insertPartErrorModel(String barcode, String partError) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_ON_NET_IMPORT"
                + " SET"
                + " PART_NO_ERROR = ?"
//                + " OU_ERROR = ?,"
//                + " QUANTITY_ERROR = ?"
                + " WHERE"
                + " PART_NO = ?";

        sqlArgs.add(partError);
//        sqlArgs.add(ouError);
//        sqlArgs.add(qtyError);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel insertOUErrorModel(String barcode,String ouError) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_ON_NET_IMPORT"
                + " SET"
                + " OU_ERROR = ?"
                + " WHERE"
                + " PART_NO = ?";

        sqlArgs.add(ouError);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


     /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel insertQtyErrorModel(String barcode, String qtyError) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                + " AMS_ON_NET_IMPORT"
                + " SET"
                + " QUANTITY_ERROR = ?"
                + " WHERE"
                + " PART_NO = ?";

        sqlArgs.add(qtyError);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel noBarModel(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_SPARE_CATEGORY AMSC WHERE AMSC.BARCODE =?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel hasOnNetModel(String barcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT 1\n" +
                        " FROM " +
                        " AMS_ITEM_ON_NET AION\n" +
                        " WHERE" +
                        " AION.BARCODE =?\n";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel hasErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
        String sqlStr = "SELECT 1\n"
                        + " FROM "
                        + " AMS_ON_NET_IMPORT AONI\n"
                        + " WHERE "
                        + " ( " + SyBaseSQLUtil.isNotNull("AONI.PART_NO_ERROR") + " \n"
                        + " OR  " + SyBaseSQLUtil.isNotNull("AONI.OU_ERROR") + " \n"
                        + " OR  " + SyBaseSQLUtil.isNotNull("AONI.QUANTITY_ERROR") + " )";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：插入到接口表。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel insertImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemOnNetDTO amsItemOnNet = (AmsItemOnNetDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_ON_NET_IMPORT("
                + " PART_NO,"
                + " QUANTITY,"
                + " ORGANIZATION_ID,"
                + " REMARK"
                + ") VALUES ("
                + " ?, CONVERT(VARCHAR,?), CONVERT(VARCHAR,?), ?)";
        sqlArgs.add(amsItemOnNet.getPartNo());
        sqlArgs.add(amsItemOnNet.getQuantity());
        sqlArgs.add(amsItemOnNet.getOrganizationId());
        sqlArgs.add(amsItemOnNet.getRemark());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：框架自动生成设备在网数量 AMS_ITEM_ON_NET数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel hasOrgIdModel(int organizationId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                        " FROM " +
                        " ETS_OU_CITY_MAP EOCM\n" +
                        " WHERE " +
                        " EOCM.ORGANIZATION_ID =?\n";
        sqlArgs.add(organizationId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：校验在 AMS_ITEM_ON_NET 中是否存在part_no。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel hasModel(String organizationId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_ITEM_ON_NET AION WHERE AION.PART_NO = ? AND AION.ORGANIZATION_ID = ? ";
        sqlArgs.add(organizationId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    /**
     * 功能：表 AMS_ON_NET_IMPORT 的查询sql。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getQueryImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " AONI.PART_NO,\n"
                + " AONI.QUANTITY,\n"
                + " AONI.ORGANIZATION_ID,\n"
                + " AONI.COMPANY,\n"
                + " AONI.REMARK,\n"
                + " AONI.PART_NO_ERROR,\n"
                + " AONI.OU_ERROR,\n"
                + " AONI.QUANTITY_ERROR\n"
                + " FROM "
                + " AMS_ON_NET_IMPORT AONI";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：导入数据从接口表到正式表。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getImpOnNetModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO " +
                " AMS_ITEM_ON_NET AION\n" +
                " (AION.ID," +
                " AION.PART_NO, " +
                " AION.QUANTITY, " +
                " AION.REMARK)\n" +
                " (SELECT " +
                "  NEWID() ,\n" +
                " AONI.PART_NO,\n" +
                " AONI.QUANTITY,\n" +
                " AONI.REMARK\n" +
                " FROM AMS_ON_NET_IMPORT AONI)";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE  FROM AMS_ON_NET_IMPORT ";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

  /**
     * 功能：校验AMS_ITEM_ON_NET 中是否有重复的数据 （parNo和 orgId）。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel doubleModel(String barcode,int organizationId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM (SELECT COUNT(*) UM\n" +
                "          FROM AMS_ITEM_ON_NET AONI\n" +
                "         WHERE AONI.PART_NO = ?\n" +
                "           AND AONI.ORGANIZATION_ID = ?\n" +
                "         GROUP BY AONI.PART_NO) ACC\n" +
                " WHERE ACC.UM > 1";
         sqlArgs.add(barcode);
         sqlArgs.add(organizationId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
 
}