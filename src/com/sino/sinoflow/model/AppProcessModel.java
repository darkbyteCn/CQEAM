package com.sino.sinoflow.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfActInfoDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-3-28
 * Time: 15:57:10
 * To change this template use File | Settings | File Templates.
 */
public class AppProcessModel extends BaseSQLProducer {

	private SfUserBaseDTO sfUser = null;
    private SfActInfoDTO sfActInfo = null;

    /**
	 * 功能：流转过程，在办流转信息 SF_ACT_INFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfActInfoDTO 本次操作的数据
	 */
	public AppProcessModel(SfUserBaseDTO userAccount, SfActInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
        sfActInfo = dtoParameter;
        sfUser = userAccount;
	}

    /**
     * 功能：框架自动生成流转过程，在办流转信息 SF_ACT_INFO数据详细信息查询SQLModel，请根据实际需要修改。
     * @param appName 应用名称
     * @param id 应用id
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getActInfoModel(String appName, String id) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " SAI.SFACT_ACT_ID,"
            + " SAI.SFACT_APPDEF_ID,"
            + " SAI.SFACT_URL,"
            + " SAI.SFACT_APPL_ID,"
            + " SAI.SFACT_DOC_TYPE,"
            + " SAI.SFACT_CASE_ID,"
            + " SAI.SFACT_PRJ_FILE_ID,"
            + " SAI.SFACT_PROJ_NAME,"
            + " SAI.SFACT_PROC_ID,"
            + " SAI.SFACT_PROC_NAME,"
            + " SAI.SFACT_PROC_DESC,"
            + " SAI.SFACT_TASK_ID,"
            + " SAI.SFACT_TASK_NAME,"
            + " SAI.SFACT_TASK_DESC,"
            + " SAI.SFACT_TASK_GROUP,"
            + " SAI.SFACT_TASK_ROLE,"
            + " SAI.SFACT_TASK_TYPE,"
            + " SAI.SFACT_TASK_DURATION,"
            + " SAI.SFACT_TASK_WORK_TYPE,"
            + " SAI.SFACT_TASK_API_NAME,"
            + " SAI.SFACT_TASK_URL,"
            + " SAI.SFACT_TASK_ATTRIBUTE_1,"
            + " SAI.SFACT_TASK_ATTRIBUTE_2,"
            + " SAI.SFACT_TASK_ATTRIBUTE_3,"
            + " SAI.SFACT_TASK_ATTRIBUTE_4,"
            + " SAI.SFACT_TASK_ATTRIBUTE_5,"
            + " SAI.SFACT_TASK_DIV_RIGHT,"
            + " SAI.SFACT_TASK_HIDDEN,"
            + " SAI.SFACT_TASK_CTL,"
            + " SAI.SFACT_TASK_CYCLE_TYPE,"
            + " SAI.SFACT_TASK_CYCLE_URL,"
            + " SAI.SFACT_TASK_COMMENT_GROUP,"
            + " SAI.SFACT_TASK_COMMENT_ROLE,"
            + " SAI.SFACT_TASK_COMMENT_INFO,"
            + " SAI.SFACT_TASK_COMMENT_URL,"
            + " SAI.SFACT_HANDLER_GROUP,"
            + " SAI.SFACT_PLUS_GROUP,"
            + " SAI.SFACT_TASK_COMMENT_DIV,"
            + " SAI.SFACT_TASK_COMMENT_HIDE,"
            + " SAI.SFACT_ACT_STATUS,"
            + " SAI.SFACT_APPL_MSG,"
            + " SAI.SFACT_COMMENT_APPL_MSG,"
            + " SAI.SFACT_COMMENT_QTY,"
            + " SAI.SFACT_COMMENT_USERS,"
            + " SAI.SFACT_COMMENT_TYPE,"
            + " SAI.SFACT_COMPLETE_DATE,"
            + " SAI.SFACT_COMPLETE_REAL_DURATION,"
            + " SAI.SFACT_COMPLETE_STATUS,"
            + " SAI.SFACT_COMPLETE_USER,"
            + " SAI.SFACT_COMPLETE_WORK_DURATION,"
            + " SAI.SFACT_COMPOSE_USER,"
            + " SAI.SFACT_CREATE_DT,"
            + " SAI.SFACT_CYCLE_TYPE,"
            + " SAI.SFACT_CYCLE_QTY,"
            + " SAI.SFACT_CYCLE_USERS,"
            + " SAI.SFACT_DELIVERY_PRIORITY,"
            + " SAI.SFACT_TASK_USERS,"
            + " SAI.SFACT_USER_MSG,"
            + " SAI.SFACT_FROM_ACT_ID,"
            + " SAI.SFACT_FROM_DATE,"
            + " SAI.SFACT_FROM_PROC_NAME,"
            + " SAI.SFACT_FROM_PROJ_NAME,"
            + " SAI.SFACT_FROM_TASK_ID,"
            + " SAI.SFACT_FROM_TASK_USER,"
            + " SAI.SFACT_HANDLER,"
            + " SAI.SFACT_LAG_REAL,"
            + " SAI.SFACT_LAG_WORK,"
            + " SAI.SFACT_LEAD_DAY_MODE,"
            + " SAI.SFACT_NEXT_PROC_NAME,"
            + " SAI.SFACT_NEXT_TASK_ID,"
            + " SAI.SFACT_NEXT_TASK_NAME,"
            + " SAI.SFACT_PICK_DATE,"
            + " SAI.SFACT_PICK_STATUS,"
            + " SAI.SFACT_PICK_USER,"
            + " SAI.SFACT_SIGN_DATE,"
            + " SAI.SFACT_SIGN_DUE_DATE,"
            + " SAI.SFACT_SIGN_STATUS,"
            + " SAI.SFACT_SIGN_USER,"
            + " SAI.SFACT_SORT_COLUMN_1,"
            + " SAI.SFACT_SORT_COLUMN_2,"
            + " SAI.SFACT_SORT_COLUMN_3,"
            + " SAI.SFACT_APPL_COLUMN_1,"
            + " SAI.SFACT_APPL_COLUMN_2,"
            + " SAI.SFACT_APPL_COLUMN_3,"
            + " SAI.SFACT_APPL_COLUMN_4,"
            + " SAI.SFACT_APPL_COLUMN_5,"
            + " SAI.SFACT_APPL_COLUMN_6,"
            + " SAI.SFACT_APPL_COLUMN_7,"
            + " SAI.SFACT_APPL_COLUMN_8,"
            + " SAI.SFACT_APPL_COLUMN_9,"
            + " SAI.SFACT_SUSPEND_DESC,"
            + " SAI.SFACT_SUSPEND_FLAG,"
            + " SAI.SFACT_SPLIT_TASK_ID,"
            + " SAI.SFACT_PAUSE_ID,"
            + " SAI.SFACT_CASE_LOCK_DATE,"
            + " SAI.SFACT_CASE_LOCK_STATUS,"
            + " SAI.SFACT_CASE_LOCK_USER,"
            + " SAI.SFACT_ASSIST_FLOW_NUM,"
            + " SAI.SFACT_ASSIST_FLOW_ACT,"
            + " SAI.SFACT_PARALLEL_FLOW_ACT,"
            + " SAI.SFACT_FROM_CASE_ID,"
            + " SAI.SFACT_RETURN_TIME,"
            + " SAI.SFACT_RETURN_ACT_ID"
            + " FROM"
            + " SF_ACT_INFO SAI, SF_APPLICATION SA"
            + " WHERE"
            + " SA.APP_NAME = ?"
            + " AND SAI.SFACT_APPL_ID = ?"
            + " AND SAI.SFACT_APPDEF_ID = SA.APP_ID";
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(appName);
        sqlArgs.add(id);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel getAppNameModel(String appName, String id) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT T.PROCEDURE_NAME FROM SF_PROCEDURE T WHERE T.PROCEDURE_ID = ? " ;
         sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(appName);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
     }
}