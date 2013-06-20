package com.sino.ams.plan.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsWorkPlanModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsWorkPlanModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class AmsWorkPlanModel extends BaseSQLProducer {

    private AmsWorkPlanDTO amsWorkPlan = null;
    private SfUserDTO SfUser = null;

    /**
     * 功能：工作计划管理 AMS_WORK_PLAN 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsWorkPlanDTO 本次操作的数据
     */
    public AmsWorkPlanModel(SfUserDTO userAccount, AmsWorkPlanDTO dtoParameter) {
        super(userAccount, dtoParameter);
        SfUser = userAccount;
        this.amsWorkPlan = (AmsWorkPlanDTO) dtoParameter;
    }

    /**
     * 功能：框架自动生成工作计划管理 AMS_WORK_PLAN数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = null;
        try {
            sqlStr = "INSERT INTO AMS_WORK_PLAN\n" +
                    "  (PLAN_ID,\n" +
                    "   PLAN_NAME,\n" +
                    "   PLAN_DESC,\n" +
                    "   EXECUTE_USER,\n" +
                    "   PLAN_STATUS,\n" +
                    "   CREATION_DATE,\n" +
                    "   CREATED_BY,\n" +
                    "   EXECUTE_TIME)\n" +
                    "VALUES\n" +
                    "  ( NEWID(), ?, ?, ?, ?, GETDATE(), ?, ?)";

            sqlArgs.add(amsWorkPlan.getPlanName());
            sqlArgs.add(amsWorkPlan.getPlanDesc());
            sqlArgs.add(amsWorkPlan.getExecuteUser());
            amsWorkPlan.setPlanStatus("1");
            sqlArgs.add(amsWorkPlan.getPlanStatus());
            sqlArgs.add(SfUser.getUserId());
           sqlArgs.add(amsWorkPlan.getExecuteTime());
       }
        catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工作计划管理 AMS_WORK_PLAN数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() throws SQLModelException{
        SQLModel sqlModel = new SQLModel();
        try {
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_WORK_PLAN\n" +
                "   SET PLAN_NAME        = ?,\n" +
                "       PLAN_DESC        = ?,\n" +
                "       EXECUTE_USER     = ?,\n" +
                "       LAST_UPDATE_DATE = GETDATE(),\n" +
                "       EXECUTE_TIME = ?,\n" +
                "       LAST_UPDATE_BY   = ?\n" +
                " WHERE PLAN_ID = ?";

        sqlArgs.add(amsWorkPlan.getPlanName());
        sqlArgs.add(amsWorkPlan.getPlanDesc());
        sqlArgs.add(amsWorkPlan.getExecuteUser());
        sqlArgs.add(amsWorkPlan.getExecuteTime());
        sqlArgs.add(SfUser.getUserId());
        sqlArgs.add(amsWorkPlan.getPlanId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
             } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    public SQLModel getDataRepealModel(String planId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_WORK_PLAN SET PLAN_STATUS = 2 WHERE PLAN_ID = ?";
        sqlArgs.add(planId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
   public SQLModel updateStatus(String planId){
       SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_WORK_PLAN SET PLAN_STATUS = 6 WHERE PLAN_ID = ?";
        sqlArgs.add(planId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
   }
    /**
     * 功能：框架自动生成工作计划管理 AMS_WORK_PLAN数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_WORK_PLAN"
                + " WHERE"
                + " PLAN_ID = ?";
        sqlArgs.add(amsWorkPlan.getPlanId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工作计划管理 AMS_WORK_PLAN数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT"
                + " AWP.PLAN_ID,"
                + " AWP.PLAN_NAME,"
                + " AWP.PLAN_DESC,"
                + " AWP.EXECUTE_TIME,"
                + " SU.USER_ID EXECUTE_USER,"
                + " SU.USERNAME EXECUTE_USER_NAME,"
                + " AWP.PLAN_STATUS"
                + " FROM"
                + " AMS_WORK_PLAN AWP,"
                + " SF_USER SU"
                + " WHERE"
                + " SU.USER_ID = AWP.EXECUTE_USER"
                + " AND AWP.PLAN_ID = ?";
        sqlArgs.add(amsWorkPlan.getPlanId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工作计划管理 AMS_WORK_PLAN多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " PLAN_ID,"
                + " PLAN_NAME,"
                + " PLAN_DESC,"
                + " EXECUTE_USER,"
                + " PLAN_STATUS,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_WORK_PLAN"
                + " WHERE"
                + "( " + SyBaseSQLUtil.isNull() + "  OR PLAN_ID LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR PLAN_NAME LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR PLAN_DESC LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR EXECUTE_USER LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR PLAN_STATUS LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
                + "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
        sqlArgs.add(amsWorkPlan.getPlanId());
        sqlArgs.add(amsWorkPlan.getPlanId());
        sqlArgs.add(amsWorkPlan.getPlanName());
        sqlArgs.add(amsWorkPlan.getPlanName());
        sqlArgs.add(amsWorkPlan.getPlanDesc());
        sqlArgs.add(amsWorkPlan.getPlanDesc());
        sqlArgs.add(amsWorkPlan.getExecuteUser());
        sqlArgs.add(amsWorkPlan.getExecuteUser());
        sqlArgs.add(amsWorkPlan.getPlanStatus());
        sqlArgs.add(amsWorkPlan.getPlanStatus());
        sqlArgs.add(amsWorkPlan.getCreationDate());
        sqlArgs.add(amsWorkPlan.getCreationDate());
        sqlArgs.add(amsWorkPlan.getCreatedBy());
        sqlArgs.add(amsWorkPlan.getCreatedBy());
        sqlArgs.add(amsWorkPlan.getLastUpdateDate());
        sqlArgs.add(amsWorkPlan.getLastUpdateDate());
        sqlArgs.add(amsWorkPlan.getLastUpdateBy());
        sqlArgs.add(amsWorkPlan.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 executeUser 构造查询数据SQL。
     * 框架自动生成数据工作计划管理 AMS_WORK_PLAN详细信息查询SQLModel，请根据实际需要修改。
     * @param executeUser String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByExecuteUserModel(int executeUser) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " PLAN_ID,"
                + " PLAN_NAME,"
                + " PLAN_DESC,"
                + " PLAN_STATUS,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_WORK_PLAN"
                + " WHERE"
                + " EXECUTE_USER = ?";
        sqlArgs.add(executeUser);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 createdBy 构造查询数据SQL。
     * 框架自动生成数据工作计划管理 AMS_WORK_PLAN详细信息查询SQLModel，请根据实际需要修改。
     * @param createdBy String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByCreatedByModel(int createdBy) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " PLAN_ID,"
                + " PLAN_NAME,"
                + " PLAN_DESC,"
                + " EXECUTE_USER,"
                + " PLAN_STATUS,"
                + " CREATION_DATE,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " AMS_WORK_PLAN"
                + " WHERE"
                + " CREATED_BY = ?";
        sqlArgs.add(createdBy);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 lastUpdateBy 构造查询数据SQL。
     * 框架自动生成数据工作计划管理 AMS_WORK_PLAN详细信息查询SQLModel，请根据实际需要修改。
     * @param lastUpdateBy String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByLastUpdateByModel(int lastUpdateBy) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " PLAN_ID,"
                + " PLAN_NAME,"
                + " PLAN_DESC,"
                + " EXECUTE_USER,"
                + " PLAN_STATUS,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE"
                + " FROM"
                + " AMS_WORK_PLAN"
                + " WHERE"
                + " LAST_UPDATE_BY = ?";
        sqlArgs.add(lastUpdateBy);

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
        if (foreignKey.equals("executeUser")) {
            sqlModel = getDataByExecuteUserModel(amsWorkPlan.getExecuteUser());
        } else if (foreignKey.equals("createdBy")) {
            sqlModel = getDataByCreatedByModel(amsWorkPlan.getCreatedBy());
        } else if (foreignKey.equals("lastUpdateBy")) {
            sqlModel = getDataByLastUpdateByModel(amsWorkPlan.getLastUpdateBy());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成工作计划管理 AMS_WORK_PLAN页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AWP.PLAN_ID,\n" +
                "       AWP.PLAN_NAME,\n" +
                "       EFV.VALUE PLAN_STATUS,\n" +
                "       AWP.CREATION_DATE,\n" +
                "       SU.USERNAME EXECUTE_USER,\n" +
                "       SU1.USERNAME CREATED_BY," +
                "       AWP.EXECUTE_TIME, " +
                "       AWP.PLAN_STATUS STATUSID\n" +
                "  FROM AMS_WORK_PLAN AWP, SF_USER SU, SF_USER SU1, ETS_FLEX_VALUES EFV\n" +
                " WHERE SU.USER_ID = AWP.EXECUTE_USER\n" +
                "   AND SU1.USER_ID = AWP.CREATED_BY\n" +
                "   AND AWP.PLAN_NAME LIKE dbo.NVL(?, AWP.PLAN_NAME)\n" +
                "   AND AWP.PLAN_STATUS = dbo.NVL(?, AWP.PLAN_STATUS)\n" +
                "   AND AWP.EXECUTE_USER = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, AWP.EXECUTE_USER)))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AWP.CREATION_DATE >= TO_DATE(?, 'YYYY-MM-DD'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AWP.CREATION_DATE < TO_DATE(?, 'YYYY-MM-DD')+1)\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = 18\n" +
                "   AND EFV.CODE = AWP.PLAN_STATUS\n"+
                "   ORDER BY AWP.EXECUTE_TIME DESC";

        sqlArgs.add(amsWorkPlan.getPlanName());
        sqlArgs.add(amsWorkPlan.getPlanStatus());
        sqlArgs.add(amsWorkPlan.getExecuteUser());
        sqlArgs.add(amsWorkPlan.getFromDate());
        sqlArgs.add(amsWorkPlan.getFromDate());
        sqlArgs.add(amsWorkPlan.getToDate());
        sqlArgs.add(amsWorkPlan.getToDate());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}