package com.sino.ams.yj.ensure.model;


import java.util.*;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.*;
import com.sino.base.util.ArrUtil;
import com.sino.ams.yj.ensure.dto.AmsYjCommunicateEnsureDTO;


/**
 * <p>Title: AmsYjCommunicateEnsureModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsYjCommunicateEnsureModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * User: wangzp
 * Date: 2011-09-20
 * Function:应急管理-应急通信保障情况
 */


public class AmsYjCommunicateEnsureModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    /**
     * 功能：应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsYjCommunicateEnsureDTO 本次操作的数据
     */
    public AmsYjCommunicateEnsureModel(SfUserDTO userAccount, AmsYjCommunicateEnsureDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = (AmsYjCommunicateEnsureDTO) dtoParameter;
            String sqlStr = "INSERT INTO "
                    + " AMS_YJ_COMMUNICATE_ENSURE("
                    + " COMMUNICATE_ID,"
                    + " DEPT_NAME,"
                    + " ENSURE_NAME,"
                    + " EVENT_TYPE,"
                    + " ENSURE_DATE_FROM,"
                    + " ENSURE_DATE_TO,"
                    + " ENSURE_LOCATION,"
                    + " MANPOWER_QTY,"
                    + " MANPOWER_TIMES,"
                    + " COMVAN_QTY,"
                    + " COMVAN_TIMES,"
                    + " EQUIPMENT_QTY,"
                    + " EQUIPMENT_UNIT,"
                    + " BLOCK_DEGREE,"
                    + " LOSS_CONDITION,"
                    + " ENSURE_MEASURE,"
                    + " RECOVER_SITUATION,"
                    + " GOVERNMENT_EVALUATE,"
                    + " REASON_AFFECT,"
                    + " QUESTION,"
                    + " GUARD_MEASURE,"
                    + " ORGANIZATION_ID,"
                    + " CREATION_DATE,"
                    + " CREATE_USER"
                    + ") VALUES ("
                    + " CONVERT(FLOAT,?) ,?, ?, ?, ?, ?, ?, CONVERT(FLOAT,?) , CONVERT(FLOAT,?), CONVERT(FLOAT,?), CONVERT(FLOAT,?), CONVERT(FLOAT,?), CONVERT(FLOAT,?), ?, ?, ?, ?,?, ?, ?, ?, ?, GETDATE(), ?)";

            sqlArgs.add(amsYjCommunicateEnsure.getCommunicateId());
            sqlArgs.add(amsYjCommunicateEnsure.getDeptName());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureName());
            sqlArgs.add(amsYjCommunicateEnsure.getEventType());
            if(!amsYjCommunicateEnsure.getEnsureDateFrom().getCalendarValue().equals("")){
            	sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateFrom());
            }else{
            	sqlArgs.add(null);
            }
            if(!amsYjCommunicateEnsure.getEnsureDateTo().getCalendarValue().equals("")){
            	   sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateTo());
            }else{
            	sqlArgs.add(null);
            }
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureLocation());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerQty());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanQty());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentQty());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentUnit());
            sqlArgs.add(amsYjCommunicateEnsure.getBlockDegree());
            sqlArgs.add(amsYjCommunicateEnsure.getLossCondition());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureMeasure());
            sqlArgs.add(amsYjCommunicateEnsure.getRecoverSituation());
            sqlArgs.add(amsYjCommunicateEnsure.getGovernmentEvaluate());
            sqlArgs.add(amsYjCommunicateEnsure.getReasonAffect());
            sqlArgs.add(amsYjCommunicateEnsure.getQuestion());
            sqlArgs.add(amsYjCommunicateEnsure.getGuardMeasure());
            sqlArgs.add(amsYjCommunicateEnsure.getOrganizationId());
            sqlArgs.add(sfUser.getUserId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = (AmsYjCommunicateEnsureDTO) dtoParameter;
            String sqlStr = "UPDATE AMS_YJ_COMMUNICATE_ENSURE"
                    + " SET"
                    + " DEPT_NAME = ?,"
                    + " ENSURE_NAME = ?,"
                    + " EVENT_TYPE = ?,"
                    + " ENSURE_DATE_FROM = ?,"
                    + " ENSURE_DATE_TO = ?,"
                    + " ENSURE_LOCATION = ?,"
                    + " MANPOWER_QTY = CONVERT(FLOAT,?),"
                    + " MANPOWER_TIMES = CONVERT(FLOAT,?),"
                    + " COMVAN_QTY = CONVERT(FLOAT,?),"
                    + " COMVAN_TIMES = CONVERT(FLOAT,?),"
                    + " EQUIPMENT_QTY = CONVERT(FLOAT,?),"
                    + " EQUIPMENT_UNIT = CONVERT(FLOAT,?),"
                    + " BLOCK_DEGREE = ?,"
                    + " LOSS_CONDITION = ?,"
                    + " ENSURE_MEASURE = ?,"
                    + " RECOVER_SITUATION = ?,"
                    + " GOVERNMENT_EVALUATE = ?,"
                    + " REASON_AFFECT = ?,"
                    + " QUESTION = ?,"
                    + " GUARD_MEASURE = ?"
                    + " WHERE COMMUNICATE_ID = CONVERT(FLOAT,?)";
            sqlArgs.add(amsYjCommunicateEnsure.getDeptName());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureName());
            sqlArgs.add(amsYjCommunicateEnsure.getEventType());
            
            if(!amsYjCommunicateEnsure.getEnsureDateFrom().getCalendarValue().equals("")){
            	sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateFrom());
            }else{
            	sqlArgs.add(null);
            }
            if(!amsYjCommunicateEnsure.getEnsureDateTo().getCalendarValue().equals("")){
            	   sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateTo());
            }else{
            	sqlArgs.add(null);
            }
            
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureLocation());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerQty());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanQty());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentQty());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentUnit());
            sqlArgs.add(amsYjCommunicateEnsure.getBlockDegree());
            sqlArgs.add(amsYjCommunicateEnsure.getLossCondition());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureMeasure());
            sqlArgs.add(amsYjCommunicateEnsure.getRecoverSituation());
            sqlArgs.add(amsYjCommunicateEnsure.getGovernmentEvaluate());
            sqlArgs.add(amsYjCommunicateEnsure.getReasonAffect());
            sqlArgs.add(amsYjCommunicateEnsure.getQuestion());
            sqlArgs.add(amsYjCommunicateEnsure.getGuardMeasure());
            sqlArgs.add(amsYjCommunicateEnsure.getCommunicateId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = (AmsYjCommunicateEnsureDTO) dtoParameter;
        String sqlStr = "DELETE FROM"
                + " AMS_YJ_COMMUNICATE_ENSURE";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = (AmsYjCommunicateEnsureDTO) dtoParameter;
        String sqlStr = "SELECT "
                + " COMMUNICATE_ID,"
                + " DEPT_NAME,"
                + " ENSURE_NAME,"
                + " EVENT_TYPE,"
                + " ENSURE_DATE_FROM,"
                + " ENSURE_DATE_TO,"
                + " ENSURE_LOCATION,"
                + " MANPOWER_QTY,"
                + " MANPOWER_TIMES,"
                + " COMVAN_QTY,"
                + " COMVAN_TIMES,"
                + " EQUIPMENT_QTY,"
                + " EQUIPMENT_UNIT,"
                + " BLOCK_DEGREE,"
                + " LOSS_CONDITION,"
                + " ENSURE_MEASURE,"
                + " RECOVER_SITUATION,"
                + " GOVERNMENT_EVALUATE,"
                + " REASON_AFFECT,"
                + " QUESTION,"
                + " ORGANIZATION_ID,"
                + " GUARD_MEASURE"
                + " FROM AMS_YJ_COMMUNICATE_ENSURE"
                + " WHERE"
                + " COMMUNICATE_ID = CONVERT(FLOAT,?)";

        sqlArgs.add(amsYjCommunicateEnsure.getCommunicateId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getMuxDataModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = (AmsYjCommunicateEnsureDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " COMMUNICATE_ID,"
                    + " DEPT_NAME,"
                    + " ENSURE_NAME,"
                    + " EVENT_TYPE,"
                    + " ENSURE_DATE_FROM,"
                    + " ENSURE_DATE_TO,"
                    + " ENSURE_LOCATION,"
                    + " MANPOWER_QTY,"
                    + " MANPOWER_TIMES,"
                    + " COMVAN_QTY,"
                    + " COMVAN_TIMES,"
                    + " EQUIPMENT_QTY,"
                    + " EQUIPMENT_UNIT,"
                    + " BLOCK_DEGREE,"
                    + " LOSS_CONDITION,"
                    + " ENSURE_MEASURE,"
                    + " RECOVER_SITUATION,"
                    + " GOVERNMENT_EVALUATE,"
                    + " REASON_AFFECT,"
                    + " QUESTION,"
                    + " GUARD_MEASURE"
                    + " FROM"
                    + " AMS_YJ_COMMUNICATE_ENSURE"
                    + " WHERE"
                    + " ("+ SyBaseSQLUtil.isNull() +" OR COMMUNICATE_ID LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR DEPT_NAME LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR ENSURE_NAME LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR EVENT_TYPE LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR ENSURE_DATE_FROM LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR ENSURE_DATE_TO LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR ENSURE_LOCATION LIKE ?)"
                    + " AND (? = -1 OR MANPOWER_QTY = ?)"
                    + " AND (? = -1 OR MANPOWER_TIMES = ?)"
                    + " AND (? = -1 OR COMVAN_QTY = ?)"
                    + " AND (? = -1 OR COMVAN_TIMES = ?)"
                    + " AND (? = -1 OR EQUIPMENT_QTY = ?)"
                    + " AND (? = -1 OR EQUIPMENT_UNIT = ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR BLOCK_DEGREE LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR LOSS_CONDITION LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR ENSURE_MEASURE LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR RECOVER_SITUATION LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR GOVERNMENT_EVALUATE LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR REASON_AFFECT LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR QUESTION LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR GUARD_MEASURE LIKE ?)";
            sqlArgs.add(amsYjCommunicateEnsure.getCommunicateId());
            sqlArgs.add(amsYjCommunicateEnsure.getCommunicateId());
            sqlArgs.add(amsYjCommunicateEnsure.getDeptName());
            sqlArgs.add(amsYjCommunicateEnsure.getDeptName());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureName());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureName());
            sqlArgs.add(amsYjCommunicateEnsure.getEventType());
            sqlArgs.add(amsYjCommunicateEnsure.getEventType());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateFrom());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateFrom());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateTo());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateTo());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureLocation());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureLocation());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerQty());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerQty());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getManpowerTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanQty());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanQty());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getComvanTimes());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentQty());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentQty());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentUnit());
            sqlArgs.add(amsYjCommunicateEnsure.getEquipmentUnit());
            sqlArgs.add(amsYjCommunicateEnsure.getBlockDegree());
            sqlArgs.add(amsYjCommunicateEnsure.getBlockDegree());
            sqlArgs.add(amsYjCommunicateEnsure.getLossCondition());
            sqlArgs.add(amsYjCommunicateEnsure.getLossCondition());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureMeasure());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureMeasure());
            sqlArgs.add(amsYjCommunicateEnsure.getRecoverSituation());
            sqlArgs.add(amsYjCommunicateEnsure.getRecoverSituation());
            sqlArgs.add(amsYjCommunicateEnsure.getGovernmentEvaluate());
            sqlArgs.add(amsYjCommunicateEnsure.getGovernmentEvaluate());
            sqlArgs.add(amsYjCommunicateEnsure.getReasonAffect());
            sqlArgs.add(amsYjCommunicateEnsure.getReasonAffect());
            sqlArgs.add(amsYjCommunicateEnsure.getQuestion());
            sqlArgs.add(amsYjCommunicateEnsure.getQuestion());
            sqlArgs.add(amsYjCommunicateEnsure.getGuardMeasure());
            sqlArgs.add(amsYjCommunicateEnsure.getGuardMeasure());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成应急通信保障事件信息表 AMS_YJ_COMMUNICATE_ENSURE页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsYjCommunicateEnsureDTO amsYjCommunicateEnsure = (AmsYjCommunicateEnsureDTO) dtoParameter;
            String sqlStr = "SELECT "
                    + " CONVERT(VARCHAR,COMMUNICATE_ID) COMMUNICATE_ID,"
                    + " DEPT_NAME,"
                    + " ENSURE_NAME,"
                    + " EVENT_TYPE,"
                    + " ENSURE_DATE_FROM,"
                    + " ENSURE_DATE_TO,"
                    + " ENSURE_LOCATION,"
                    + " MANPOWER_QTY,"
                    + " MANPOWER_TIMES,"
                    + " COMVAN_QTY,"
                    + " COMVAN_TIMES,"
                    + " EQUIPMENT_QTY,"
                    + " EQUIPMENT_UNIT,"
                    + " BLOCK_DEGREE,"
                    + " LOSS_CONDITION,"
                    + " ENSURE_MEASURE,"
                    + " RECOVER_SITUATION,"
                    + " GOVERNMENT_EVALUATE,"
                    + " REASON_AFFECT,"
                    + " QUESTION,"
                    + " ORGANIZATION_ID,"
                    + " GUARD_MEASURE"
                    + " FROM"
                    + " AMS_YJ_COMMUNICATE_ENSURE"
                    + " WHERE"
                    + "  ("+ SyBaseSQLUtil.isNull() +" OR DEPT_NAME LIKE ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR ENSURE_DATE_FROM >= ?)"
                    + " AND ("+ SyBaseSQLUtil.isNull() +" OR ENSURE_DATE_TO <= ?)"
                    + " AND (? = -1 OR ORGANIZATION_ID = ?)"
                    + " ORDER BY "                                 
                    + " COMMUNICATE_ID";
//                    +  " DEPT_NAME,"
//                    + " ENSURE_DATE_FROM,"
//                    +  " ENSURE_DATE_TO";

            sqlArgs.add(amsYjCommunicateEnsure.getDeptName());
            sqlArgs.add(amsYjCommunicateEnsure.getDeptName());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateFrom());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateFrom());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateTo());
            sqlArgs.add(amsYjCommunicateEnsure.getEnsureDateTo());
            sqlArgs.add(amsYjCommunicateEnsure.getOrganizationId());
            sqlArgs.add(amsYjCommunicateEnsure.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
         }
         catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    public SQLModel getDeleteAllData(String ids) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_YJ_COMMUNICATE_ENSURE" +
                " WHERE COMMUNICATE_ID IN (" +ids + ")";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    
    /**
     * 统计
     */
    public SQLModel getExportModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsYjCommunicateEnsureDTO ensureDTO = (AmsYjCommunicateEnsureDTO) dtoParameter;
        String sqlStr =
                "SELECT AYCR.DEPT_NAME,\n" +
                        "       dbo.YEP_GET_DEPT_EVENTS(AYCR.DEPT_NAME, '政治经济事件类') EVENT_1,\n" +
                        "       dbo.YEP_GET_DEPT_EVENTS(AYCR.DEPT_NAME, '节假日保障类') EVENT_2,\n" +
                        "       dbo.YEP_GET_DEPT_EVENTS(AYCR.DEPT_NAME, '自然灾害类') EVENT_3,\n" +
                        "       dbo.YEP_GET_DEPT_EVENTS(AYCR.DEPT_NAME, '事故灾难类') EVENT_4,\n" +
                        "       dbo.YEP_GET_DEPT_EVENTS(AYCR.DEPT_NAME, '公共卫生事件类') EVENT_5,\n" +
                        "       dbo.YEP_GET_DEPT_EVENTS(AYCR.DEPT_NAME, '社会安全事件类') EVENT_6,\n" +
                        "       SUM(AYCR.MANPOWER_QTY) ITEM_1,\n" +
                        "       SUM(AYCR.MANPOWER_TIMES) ITEM_2,\n" +
                        "       SUM(AYCR.COMVAN_QTY) ITEM_3,\n" +
                        "       SUM(AYCR.COMVAN_TIMES) ITEM_4,\n" +
                        "       SUM(AYCR.EQUIPMENT_QTY) ITEM_5,\n" +
                        "       SUM(AYCR.EQUIPMENT_UNIT) ITEM_6\n" +
                        "  FROM AMS_YJ_COMMUNICATE_ENSURE AYCR\n" +
                        "  WHERE ? =-1 OR AYCR.ORGANIZATION_ID=?\n" +
                        " GROUP BY AYCR.DEPT_NAME\n" +
                        " UNION ALL\n" +
                        " SELECT '合计' DEPT_NAME,\n" +
                        "      dbo.YEP_GET_ORG_EVENTS(?, '政治经济事件类') EVENT_1,\n" +
                        "      dbo.YEP_GET_ORG_EVENTS(?, '节假日保障类') EVENT_2,\n" +
                        "      dbo.YEP_GET_ORG_EVENTS(?, '自然灾害类') EVENT_3,\n" +
                        "      dbo.YEP_GET_ORG_EVENTS(?, '事故灾难类') EVENT_4,\n" +
                        "      dbo.YEP_GET_ORG_EVENTS(?, '公共卫生事件类') EVENT_5,\n" +
                        "      dbo.YEP_GET_ORG_EVENTS(?, '社会安全事件类') EVENT_6,\n" +
                        "      SUM(AYCR.MANPOWER_QTY) ITEM_1,\n" +
                        "      SUM(AYCR.MANPOWER_TIMES) ITEM_2,\n" +
                        "      SUM(AYCR.COMVAN_QTY) ITEM_3,\n" +
                        "      SUM(AYCR.COMVAN_TIMES) ITEM_4,\n" +
                        "      SUM(AYCR.EQUIPMENT_QTY) ITEM_5,\n" +
                        "      SUM(AYCR.EQUIPMENT_UNIT) ITEM_6\n" +
                        " FROM AMS_YJ_COMMUNICATE_ENSURE AYCR\n" +
                        "  WHERE ? =-1 OR AYCR.ORGANIZATION_ID=?\n";


        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());
        sqlArgs.add(ensureDTO.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

}