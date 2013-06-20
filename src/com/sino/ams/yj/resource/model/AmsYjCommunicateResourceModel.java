package com.sino.ams.yj.resource.model;

import java.util.*;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.*;
import com.sino.ams.yj.resource.dto.AmsYjCommunicateResourceDTO;

/**
 * <p>Title: AmsYjCommunicateResourceModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsYjCommunicateResourceModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-战备应急通信资源
 */

public class AmsYjCommunicateResourceModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjCommunicateResourceDTO 本次操作的数据
     */
    public AmsYjCommunicateResourceModel(SfUserDTO userAccount, AmsYjCommunicateResourceDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE数据插入SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                + " AMS_YJ_COMMUNICATE_RESOURCE("
                + " RESOURCE_ID,"
                + " DEPT_NAME,"
                + " EQUIPMENT_NAME,"
                + " RESOURCE_QTY,"
                + " LOCATION,"
                + " MODEL,"
                + " NORMAL_STATUS,"
                + " CHARGE_DEPT,"
                + " CHARGER,"
                + " CHARGER_MOBILE,"
                + " KEEPER,"
                + " KEEPER_MOBILE,"
                + " REMARK,"
                + " ORGANIZATION_ID,"
                + " CREATION_DATE,"
                + " CREATE_USER"
                + ") VALUES ("
                + " CONVERT(FLOAT,?), ?, ?, CONVERT(FLOAT,?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

        sqlArgs.add(amsYjCommunicateResource.getResourceId());
        sqlArgs.add(amsYjCommunicateResource.getDeptName());
        sqlArgs.add(amsYjCommunicateResource.getEquipmentName());
        sqlArgs.add(amsYjCommunicateResource.getResourceQty());
        sqlArgs.add(amsYjCommunicateResource.getLocation());
        sqlArgs.add(amsYjCommunicateResource.getModel());
        sqlArgs.add(amsYjCommunicateResource.getNormalStatus());
        sqlArgs.add(amsYjCommunicateResource.getChargeDept());
        sqlArgs.add(amsYjCommunicateResource.getCharger());
        sqlArgs.add(amsYjCommunicateResource.getChargerMobile());
        sqlArgs.add(amsYjCommunicateResource.getKeeper());
        sqlArgs.add(amsYjCommunicateResource.getKeeperMobile());
        sqlArgs.add(amsYjCommunicateResource.getRemark());
        sqlArgs.add(amsYjCommunicateResource.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：框架自动生成战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE数据更新SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_YJ_COMMUNICATE_RESOURCE"
                + " SET"
                + " DEPT_NAME = ?,"
                + " EQUIPMENT_NAME = ?,"
                + " RESOURCE_QTY = CONVERT(FLOAT,?),"
                + " LOCATION = ?,"
                + " MODEL = ?,"
                + " NORMAL_STATUS = ?,"
                + " CHARGE_DEPT = ?,"
                + " CHARGER = ?,"
                + " CHARGER_MOBILE = ?,"
                + " KEEPER = ?,"
                + " KEEPER_MOBILE = ?,"
                + " REMARK = ?,"
                + " ORGANIZATION_ID = ?,"
                + " LAST_UPDATE_DATE = GETDATE(),"
                + " LAST_UPDATE_USER = ?"
                + " WHERE RESOURCE_ID = CONVERT(FLOAT,?)";
        sqlArgs.add(amsYjCommunicateResource.getDeptName());
        sqlArgs.add(amsYjCommunicateResource.getEquipmentName());
        sqlArgs.add(amsYjCommunicateResource.getResourceQty());
        sqlArgs.add(amsYjCommunicateResource.getLocation());
        sqlArgs.add(amsYjCommunicateResource.getModel());
        sqlArgs.add(amsYjCommunicateResource.getNormalStatus());
        sqlArgs.add(amsYjCommunicateResource.getChargeDept());
        sqlArgs.add(amsYjCommunicateResource.getCharger());
        sqlArgs.add(amsYjCommunicateResource.getChargerMobile());
        sqlArgs.add(amsYjCommunicateResource.getKeeper());
        sqlArgs.add(amsYjCommunicateResource.getKeeperMobile());
        sqlArgs.add(amsYjCommunicateResource.getRemark());
        sqlArgs.add(amsYjCommunicateResource.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsYjCommunicateResource.getResourceId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：框架自动生成战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE数据删除SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_YJ_COMMUNICATE_RESOURCE";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE数据详细信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " AYCR.RESOURCE_ID,"
                + " AYCR.DEPT_NAME,"
                + " AYCR.EQUIPMENT_NAME,"
                + " AYCR.RESOURCE_QTY,"
                + " AYCR.LOCATION,"
                + " AYCR.MODEL,"
                + " AYCR.NORMAL_STATUS,"
                + " AYCR.CHARGE_DEPT,"
                + " AYCR.CHARGER,"
                + " AYCR.CHARGER_MOBILE,"
                + " AYCR.KEEPER,"
                + " AYCR.KEEPER_MOBILE,"
                + " AYCR.REMARK,"
                + " AYCR.ORGANIZATION_ID,"
                + " AYCR.CREATION_DATE,"
                + " AYCR.CREATE_USER,"
                + " AYCR.LAST_UPDATE_DATE,"
                + " AYCR.LAST_UPDATE_USER,"
                + " (SELECT COUNT(1) FROM AMS_YJ_COMVAN AYC WHERE AYC.RESOURCE_ID =AYCR.RESOURCE_ID) COMVAN,"
                + " (SELECT COUNT(1) FROM AMS_YJ_ISATPHONE AYI WHERE AYI.RESOURCE_ID =AYCR.RESOURCE_ID) ISATPHONE"
                + " FROM AMS_YJ_COMMUNICATE_RESOURCE AYCR"
                + " WHERE"
                + " AYCR.RESOURCE_ID = CONVERT(FLOAT,?)";

        sqlArgs.add(amsYjCommunicateResource.getResourceId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE多条数据信息查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " RESOURCE_ID,"
                    + " DEPT_NAME,"
                    + " EQUIPMENT_NAME,"
                    + " RESOURCE_QTY,"
                    + " LOCATION,"
                    + " MODEL,"
                    + " NORMAL_STATUS,"
                    + " CHARGE_DEPT,"
                    + " CHARGER,"
                    + " CHARGER_MOBILE,"
                    + " KEEPER,"
                    + " KEEPER_MOBILE,"
                    + " REMARK,"
                    + " ORGANIZATION_ID,"
                    + " CREATION_DATE,"
                    + " CREATE_USER,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_USER"
                    + " FROM"
                    + " AMS_YJ_COMMUNICATE_RESOURCE"
                    + " WHERE"
                    + " (? IS NULL OR RESOURCE_ID LIKE ?)"
                    + " AND (? IS NULL OR DEPT_NAME LIKE ?)"
                    + " AND (? IS NULL OR EQUIPMENT_NAME LIKE ?)"
                    + " AND (? IS NULL OR RESOURCE_QTY LIKE ?)"
                    + " AND (? IS NULL OR LOCATION LIKE ?)"
                    + " AND (? IS NULL OR MODEL LIKE ?)"
                    + " AND (? IS NULL OR NORMAL_STATUS LIKE ?)"
                    + " AND (? IS NULL OR CHARGE_DEPT LIKE ?)"
                    + " AND (? IS NULL OR CHARGER LIKE ?)"
                    + " AND (? IS NULL OR CHARGER_MOBILE LIKE ?)"
                    + " AND (? IS NULL OR KEEPER LIKE ?)"
                    + " AND (? IS NULL OR KEEPER_MOBILE LIKE ?)"
                    + " AND (? IS NULL OR REMARK LIKE ?)"
                    + " AND (? IS NULL OR ORGANIZATION_ID LIKE ?)"
                    + " AND (? IS NULL OR CREATION_DATE LIKE ?)"
                    + " AND (? IS NULL OR CREATE_USER LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
                    + " AND (? IS NULL OR LAST_UPDATE_USER LIKE ?)";
            sqlArgs.add(amsYjCommunicateResource.getResourceId());
            sqlArgs.add(amsYjCommunicateResource.getResourceId());
            sqlArgs.add(amsYjCommunicateResource.getDeptName());
            sqlArgs.add(amsYjCommunicateResource.getDeptName());
            sqlArgs.add(amsYjCommunicateResource.getEquipmentName());
            sqlArgs.add(amsYjCommunicateResource.getEquipmentName());
            sqlArgs.add(amsYjCommunicateResource.getResourceQty());
            sqlArgs.add(amsYjCommunicateResource.getResourceQty());
            sqlArgs.add(amsYjCommunicateResource.getLocation());
            sqlArgs.add(amsYjCommunicateResource.getLocation());
            sqlArgs.add(amsYjCommunicateResource.getModel());
            sqlArgs.add(amsYjCommunicateResource.getModel());
            sqlArgs.add(amsYjCommunicateResource.getNormalStatus());
            sqlArgs.add(amsYjCommunicateResource.getNormalStatus());
            sqlArgs.add(amsYjCommunicateResource.getChargeDept());
            sqlArgs.add(amsYjCommunicateResource.getChargeDept());
            sqlArgs.add(amsYjCommunicateResource.getCharger());
            sqlArgs.add(amsYjCommunicateResource.getCharger());
            sqlArgs.add(amsYjCommunicateResource.getChargerMobile());
            sqlArgs.add(amsYjCommunicateResource.getChargerMobile());
            sqlArgs.add(amsYjCommunicateResource.getKeeper());
            sqlArgs.add(amsYjCommunicateResource.getKeeper());
            sqlArgs.add(amsYjCommunicateResource.getKeeperMobile());
            sqlArgs.add(amsYjCommunicateResource.getKeeperMobile());
            sqlArgs.add(amsYjCommunicateResource.getRemark());
            sqlArgs.add(amsYjCommunicateResource.getRemark());
            sqlArgs.add(amsYjCommunicateResource.getOrganizationId());
            sqlArgs.add(amsYjCommunicateResource.getOrganizationId());
            sqlArgs.add(amsYjCommunicateResource.getCreationDate());
            sqlArgs.add(amsYjCommunicateResource.getCreationDate());
            sqlArgs.add(amsYjCommunicateResource.getCreateUser());
            sqlArgs.add(amsYjCommunicateResource.getCreateUser());
            sqlArgs.add(amsYjCommunicateResource.getLastUpdateDate());
            sqlArgs.add(amsYjCommunicateResource.getLastUpdateDate());
            sqlArgs.add(amsYjCommunicateResource.getLastUpdateUser());
            sqlArgs.add(amsYjCommunicateResource.getLastUpdateUser());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成战备应急通信资源 AMS_YJ_COMMUNICATE_RESOURCE页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " RESOURCE_ID,"
                    + " DEPT_NAME,"
                    + " EQUIPMENT_NAME,"
                    + " RESOURCE_QTY,"
                    + " LOCATION,"
                    + " MODEL,"
                    + " NORMAL_STATUS,"
                    + " CHARGE_DEPT,"
                    + " CHARGER,"
                    + " CHARGER_MOBILE,"
                    + " KEEPER,"
                    + " KEEPER_MOBILE,"
                    + " REMARK,"
                    + " ORGANIZATION_ID,"
                    + " CREATION_DATE,"
                    + " CREATE_USER,"
                    + " LAST_UPDATE_DATE,"
                    + " LAST_UPDATE_USER"
                    + " FROM"
                    + " AMS_YJ_COMMUNICATE_RESOURCE"
                    + " WHERE"
//                    + " (? IS NULL OR RESOURCE_ID LIKE ?)"
//                    + " AND (? IS NULL OR DEPT_NAME LIKE ?)"
                    + " ("+SyBaseSQLUtil.isNull()+" OR EQUIPMENT_NAME LIKE ?)"
//                    + " AND (? IS NULL OR RESOURCE_QTY LIKE ?)"
//                    + " AND (? IS NULL OR LOCATION LIKE ?)"
//                    + " AND (? IS NULL OR MODEL LIKE ?)"
//                    + " AND (? IS NULL OR NORMAL_STATUS LIKE ?)"
//                    + " AND (? IS NULL OR CHARGE_DEPT LIKE ?)"
//                    + " AND (? IS NULL OR CHARGER LIKE ?)"
//                    + " AND (? IS NULL OR CHARGER_MOBILE LIKE ?)"
//                    + " AND (? IS NULL OR KEEPER LIKE ?)"
//                    + " AND (? IS NULL OR KEEPER_MOBILE LIKE ?)"
//                    + " AND (? IS NULL OR REMARK LIKE ?)"
                    + " AND (? =-1 OR ORGANIZATION_ID = ?)"
//                    + " AND (? IS NULL OR CREATION_DATE LIKE ?)"
//                    + " AND (? IS NULL OR CREATE_USER LIKE ?)"
//                    + " AND (? IS NULL OR LAST_UPDATE_DATE LIKE ?)"
//                    + " AND (? IS NULL OR LAST_UPDATE_USER LIKE ?)"
                    ;
//            sqlArgs.add(amsYjCommunicateResource.getResourceId());
//            sqlArgs.add(amsYjCommunicateResource.getResourceId());
//            sqlArgs.add(amsYjCommunicateResource.getDeptName());
//            sqlArgs.add(amsYjCommunicateResource.getDeptName());
            sqlArgs.add(amsYjCommunicateResource.getEquipmentName());
            sqlArgs.add(amsYjCommunicateResource.getEquipmentName());
//            sqlArgs.add(amsYjCommunicateResource.getResourceQty());
//            sqlArgs.add(amsYjCommunicateResource.getResourceQty());
//            sqlArgs.add(amsYjCommunicateResource.getLocation());
//            sqlArgs.add(amsYjCommunicateResource.getLocation());
//            sqlArgs.add(amsYjCommunicateResource.getModel());
//            sqlArgs.add(amsYjCommunicateResource.getModel());
//            sqlArgs.add(amsYjCommunicateResource.getNormalStatus());
//            sqlArgs.add(amsYjCommunicateResource.getNormalStatus());
//            sqlArgs.add(amsYjCommunicateResource.getChargeDept());
//            sqlArgs.add(amsYjCommunicateResource.getChargeDept());
//            sqlArgs.add(amsYjCommunicateResource.getCharger());
//            sqlArgs.add(amsYjCommunicateResource.getCharger());
//            sqlArgs.add(amsYjCommunicateResource.getChargerMobile());
//            sqlArgs.add(amsYjCommunicateResource.getChargerMobile());
//            sqlArgs.add(amsYjCommunicateResource.getKeeper());
//            sqlArgs.add(amsYjCommunicateResource.getKeeper());
//            sqlArgs.add(amsYjCommunicateResource.getKeeperMobile());
//            sqlArgs.add(amsYjCommunicateResource.getKeeperMobile());
//            sqlArgs.add(amsYjCommunicateResource.getRemark());
//            sqlArgs.add(amsYjCommunicateResource.getRemark());
            sqlArgs.add(amsYjCommunicateResource.getOrganizationId());
            sqlArgs.add(amsYjCommunicateResource.getOrganizationId());
//            sqlArgs.add(amsYjCommunicateResource.getCreationDate());
//            sqlArgs.add(amsYjCommunicateResource.getCreationDate());
//            sqlArgs.add(amsYjCommunicateResource.getCreateUser());
//            sqlArgs.add(amsYjCommunicateResource.getCreateUser());
//            sqlArgs.add(amsYjCommunicateResource.getLastUpdateDate());
//            sqlArgs.add(amsYjCommunicateResource.getLastUpdateDate());
//            sqlArgs.add(amsYjCommunicateResource.getLastUpdateUser());
//            sqlArgs.add(amsYjCommunicateResource.getLastUpdateUser());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel getDeleteDataModel(String ids) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_YJ_COMMUNICATE_RESOURCE WHERE RESOURCE_ID IN (" + ids + ")";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
   
    /**
     * 统计报表
     */
    public SQLModel getExportModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjCommunicateResourceDTO amsYjCommunicateResource = (AmsYjCommunicateResourceDTO) dtoParameter;
        String sqlStr =
                "SELECT EOCM.COMPANY,\n" +
                        " AYCR.EQUIPMENT_NAME,\n" +
                        " SUM(AYCR.RESOURCE_QTY) QTY\n" +
                        "FROM   AMS_YJ_COMMUNICATE_RESOURCE AYCR,\n" +
                        "       ETS_OU_CITY_MAP             EOCM\n" +
                        "WHERE  AYCR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                        "   AND  (? =-1 OR AYCR.ORGANIZATION_ID=?)\n" +
                        "GROUP  BY EOCM.COMPANY,\n" +
                        "       AYCR.EQUIPMENT_NAME\n";

        sqlArgs.add(amsYjCommunicateResource.getOrganizationId());
        sqlArgs.add(amsYjCommunicateResource.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}