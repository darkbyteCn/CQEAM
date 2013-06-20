package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfApplicationDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfApplicationModel</p>
 * <p>Description:程序自动生成SQL构造器“SfApplicationModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfApplicationModel extends BaseSQLProducer {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：应用信息 SF_APPLICATION 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfApplicationDTO 本次操作的数据
	 */
	public SfApplicationModel(SfUserBaseDTO userAccount, SfApplicationDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成应用信息 SF_APPLICATION数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SfApplicationDTO sfApplication = (SfApplicationDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " SF_APPLICATION("
                + " IS_FLOW_PROCESS,"
                + " PROJECT_NAME,"
				+ " PROCEDURE_NAME,"
                + " GROUP_NAME,"
                + " ROLE_NAME,"
                + " CATEGORY_NAME,"
				+ " APP_NAME,"
                + " URL,"
                + " APP_DATA_CLASS,"
				+ " APP_DATA_SQLMODEL,"
				+ " WINDOW_TYPE,"
				+ " FINISH_MESSAGE,"
				+ " CONFIRM_FINISH,"
				+ " ALLOW_OPERATION,"
				+ " SORT_COLUMN1,"
				+ " SORT_COLUMN2,"
				+ " SORT_COLUMN3,"
				+ " APP_COLUMN1,"
				+ " APP_COLUMN2,"
				+ " APP_COLUMN3,"
				+ " APP_COLUMN4,"
				+ " APP_COLUMN5,"
				+ " APP_COLUMN6,"
				+ " APP_COLUMN7,"
				+ " APP_COLUMN8,"
				+ " APP_COLUMN9,"
				+ " LINK_PROJECT_NAME_FIELD,"
				+ " LINK_PROJECT_DESC_FIELD,"
				+ " LINK_PROCEDURE_NAME_FIELD,"
				+ " LINK_PROCEDURE_DESC_FIELD,"
				+ " LINK_TASK_NAME_FIELD,"
				+ " LINK_TASK_DESC_FIELD,"
				+ " LINK_TAKS_DURATION_FIELD,"
				+ " LINK_GROUP_FIELD,"
				+ " LINK_ROLE_FILED,"
				+ " LINK_USERS_FIELD,"
				+ " LINK_FROM_PROJECT_FIELD,"
				+ " LINK_FORM_PROCEDURE_FIELD,"
				+ " LINK_FROM_USER_FIELD,"
				+ " LINK_FORM_DATE_FIELD,"
				+ " LINK_FORM_TASK_ID_FIELD,"
				+ " LINK_HANDLE_GROUP_FIELD,"
				+ " LINK_HANDLER_FIELD,"
				+ " LINK_SIGN_STATUS_FIELD,"
				+ " LINK_SIGN_DATE_FIELD,"
				+ " LINK_TASK_DUE_FIELD,"
				+ " LINK_REVIEW_GROUP_FIELD,"
				+ " LINK_REVIEW_ROLE_FIELD,"
				+ " LINK_REVIEW_USERS_FIELD,"
				+ " LINK_REVIEW_STATUS_FIELD,"
				+ " LINK_DELIVERY_PRIORITY_FIELD,"
				+ " LINK_USER_MESSAGE_FIELD,"
				+ " LINK_NEXT_CYCLE_PROP_FIELD,"
				+ " VIEW_IN_LIST"
				+ ") VALUES ("
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            sqlArgs.add(sfApplication.getIsFlowProcess());
            sqlArgs.add(sfApplication.getProjectName());
			sqlArgs.add(sfApplication.getProcedureName());
            sqlArgs.add(sfApplication.getGroupName());
            sqlArgs.add(sfApplication.getRoleName());
            sqlArgs.add(sfApplication.getCategoryName());
			sqlArgs.add(sfApplication.getAppName());
            sqlArgs.add(sfApplication.getUrl());
            sqlArgs.add(sfApplication.getAppDataClass());
			sqlArgs.add(sfApplication.getAppDataSqlmodel());
			sqlArgs.add(sfApplication.getWindowType());
			sqlArgs.add(sfApplication.getFinishMessage());
			sqlArgs.add(sfApplication.getConfirmFinish());
			sqlArgs.add(sfApplication.getAllowOperation());
			sqlArgs.add(sfApplication.getSortColumn1());
			sqlArgs.add(sfApplication.getSortColumn2());
			sqlArgs.add(sfApplication.getSortColumn3());
			sqlArgs.add(sfApplication.getAppColumn1());
			sqlArgs.add(sfApplication.getAppColumn2());
			sqlArgs.add(sfApplication.getAppColumn3());
			sqlArgs.add(sfApplication.getAppColumn4());
			sqlArgs.add(sfApplication.getAppColumn5());
			sqlArgs.add(sfApplication.getAppColumn6());
			sqlArgs.add(sfApplication.getAppColumn7());
			sqlArgs.add(sfApplication.getAppColumn8());
			sqlArgs.add(sfApplication.getAppColumn9());
			sqlArgs.add(sfApplication.getLinkProjectNameField());
			sqlArgs.add(sfApplication.getLinkProjectDescField());
			sqlArgs.add(sfApplication.getLinkProcedureNameField());
			sqlArgs.add(sfApplication.getLinkProcedureDescField());
			sqlArgs.add(sfApplication.getLinkTaskNameField());
			sqlArgs.add(sfApplication.getLinkTaskDescField());
			sqlArgs.add(sfApplication.getLinkTaksDurationField());
			sqlArgs.add(sfApplication.getLinkGroupField());
			sqlArgs.add(sfApplication.getLinkRoleFiled());
			sqlArgs.add(sfApplication.getLinkUsersField());
			sqlArgs.add(sfApplication.getLinkFromProjectField());
			sqlArgs.add(sfApplication.getLinkFormProcedureField());
			sqlArgs.add(sfApplication.getLinkFromUserField());
			sqlArgs.add(sfApplication.getLinkFormDateField());
			sqlArgs.add(sfApplication.getLinkFormTaskIdField());
			sqlArgs.add(sfApplication.getLinkHandleGroupField());
			sqlArgs.add(sfApplication.getLinkHandlerField());
			sqlArgs.add(sfApplication.getLinkSignStatusField());
			sqlArgs.add(sfApplication.getLinkSignDateField());
			sqlArgs.add(sfApplication.getLinkTaskDueField());
			sqlArgs.add(sfApplication.getLinkReviewGroupField());
			sqlArgs.add(sfApplication.getLinkReviewRoleField());
			sqlArgs.add(sfApplication.getLinkReviewUsersField());
			sqlArgs.add(sfApplication.getLinkReviewStatusField());
			sqlArgs.add(sfApplication.getLinkDeliveryPriorityField());
			sqlArgs.add(sfApplication.getLinkUserMessageField());
			sqlArgs.add(sfApplication.getLinkNextCyclePropField());
			sqlArgs.add(sfApplication.getViewInList());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			Logger.logError(ex);
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成应用信息 SF_APPLICATION数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SfApplicationDTO sfApplication = (SfApplicationDTO)dtoParameter;
			String sqlStr = "UPDATE SF_APPLICATION"
				+ " SET"
                + " IS_FLOW_PROCESS = ?,"
                + " PROJECT_NAME = ?,"
				+ " PROCEDURE_NAME = ?,"
                + " GROUP_NAME = ?,"
                + " ROLE_NAME = ?,"
				+ " CATEGORY_NAME = ?,"
				+ " APP_NAME = ?,"
                + " URL = ?,"
                + " APP_DATA_CLASS = ?,"
				+ " APP_DATA_SQLMODEL = ?,"
				+ " WINDOW_TYPE = ?,"
				+ " FINISH_MESSAGE = ?,"
				+ " CONFIRM_FINISH = ?,"
				+ " ALLOW_OPERATION = ?,"
				+ " SORT_COLUMN1 = ?,"
				+ " SORT_COLUMN2 = ?,"
				+ " SORT_COLUMN3 = ?,"
				+ " APP_COLUMN1 = ?,"
				+ " APP_COLUMN2 = ?,"
				+ " APP_COLUMN3 = ?,"
				+ " APP_COLUMN4 = ?,"
				+ " APP_COLUMN5 = ?,"
				+ " APP_COLUMN6 = ?,"
				+ " APP_COLUMN7 = ?,"
				+ " APP_COLUMN8 = ?,"
				+ " APP_COLUMN9 = ?,"
				+ " LINK_PROJECT_NAME_FIELD = ?,"
				+ " LINK_PROJECT_DESC_FIELD = ?,"
				+ " LINK_PROCEDURE_NAME_FIELD = ?,"
				+ " LINK_PROCEDURE_DESC_FIELD = ?,"
				+ " LINK_TASK_NAME_FIELD = ?,"
				+ " LINK_TASK_DESC_FIELD = ?,"
				+ " LINK_TAKS_DURATION_FIELD = ?,"
				+ " LINK_GROUP_FIELD = ?,"
				+ " LINK_ROLE_FILED = ?,"
				+ " LINK_USERS_FIELD = ?,"
				+ " LINK_FROM_PROJECT_FIELD = ?,"
				+ " LINK_FORM_PROCEDURE_FIELD = ?,"
				+ " LINK_FROM_USER_FIELD = ?,"
				+ " LINK_FORM_DATE_FIELD = ?,"
				+ " LINK_FORM_TASK_ID_FIELD = ?,"
				+ " LINK_HANDLE_GROUP_FIELD = ?,"
				+ " LINK_HANDLER_FIELD = ?,"
				+ " LINK_SIGN_STATUS_FIELD = ?,"
				+ " LINK_SIGN_DATE_FIELD = ?,"
				+ " LINK_TASK_DUE_FIELD = ?,"
				+ " LINK_REVIEW_GROUP_FIELD = ?,"
				+ " LINK_REVIEW_ROLE_FIELD = ?,"
				+ " LINK_REVIEW_USERS_FIELD = ?,"
				+ " LINK_REVIEW_STATUS_FIELD = ?,"
				+ " LINK_DELIVERY_PRIORITY_FIELD = ?,"
				+ " LINK_USER_MESSAGE_FIELD = ?,"
				+ " LINK_NEXT_CYCLE_PROP_FIELD = ?,"
				+ " VIEW_IN_LIST = ?"
				+ " WHERE"
				+ " APP_ID = ?";

            sqlArgs.add(sfApplication.getIsFlowProcess());
            sqlArgs.add(sfApplication.getProjectName());
			sqlArgs.add(sfApplication.getProcedureName());
            sqlArgs.add(sfApplication.getGroupName());
            sqlArgs.add(sfApplication.getRoleName());
            sqlArgs.add(sfApplication.getCategoryName());
			sqlArgs.add(sfApplication.getAppName());
            sqlArgs.add(sfApplication.getUrl());
            sqlArgs.add(sfApplication.getAppDataClass());
			sqlArgs.add(sfApplication.getAppDataSqlmodel());
			sqlArgs.add(sfApplication.getWindowType());
			sqlArgs.add(sfApplication.getFinishMessage());
			sqlArgs.add(sfApplication.getConfirmFinish());
			sqlArgs.add(sfApplication.getAllowOperation());
			sqlArgs.add(sfApplication.getSortColumn1());
			sqlArgs.add(sfApplication.getSortColumn2());
			sqlArgs.add(sfApplication.getSortColumn3());
			sqlArgs.add(sfApplication.getAppColumn1());
			sqlArgs.add(sfApplication.getAppColumn2());
			sqlArgs.add(sfApplication.getAppColumn3());
			sqlArgs.add(sfApplication.getAppColumn4());
			sqlArgs.add(sfApplication.getAppColumn5());
			sqlArgs.add(sfApplication.getAppColumn6());
			sqlArgs.add(sfApplication.getAppColumn7());
			sqlArgs.add(sfApplication.getAppColumn8());
			sqlArgs.add(sfApplication.getAppColumn9());
			sqlArgs.add(sfApplication.getLinkProjectNameField());
			sqlArgs.add(sfApplication.getLinkProjectDescField());
			sqlArgs.add(sfApplication.getLinkProcedureNameField());
			sqlArgs.add(sfApplication.getLinkProcedureDescField());
			sqlArgs.add(sfApplication.getLinkTaskNameField());
			sqlArgs.add(sfApplication.getLinkTaskDescField());
			sqlArgs.add(sfApplication.getLinkTaksDurationField());
			sqlArgs.add(sfApplication.getLinkGroupField());
			sqlArgs.add(sfApplication.getLinkRoleFiled());
			sqlArgs.add(sfApplication.getLinkUsersField());
			sqlArgs.add(sfApplication.getLinkFromProjectField());
			sqlArgs.add(sfApplication.getLinkFormProcedureField());
			sqlArgs.add(sfApplication.getLinkFromUserField());
			sqlArgs.add(sfApplication.getLinkFormDateField());
			sqlArgs.add(sfApplication.getLinkFormTaskIdField());
			sqlArgs.add(sfApplication.getLinkHandleGroupField());
			sqlArgs.add(sfApplication.getLinkHandlerField());
			sqlArgs.add(sfApplication.getLinkSignStatusField());
			sqlArgs.add(sfApplication.getLinkSignDateField());
			sqlArgs.add(sfApplication.getLinkTaskDueField());
			sqlArgs.add(sfApplication.getLinkReviewGroupField());
			sqlArgs.add(sfApplication.getLinkReviewRoleField());
			sqlArgs.add(sfApplication.getLinkReviewUsersField());
			sqlArgs.add(sfApplication.getLinkReviewStatusField());
			sqlArgs.add(sfApplication.getLinkDeliveryPriorityField());
			sqlArgs.add(sfApplication.getLinkUserMessageField());
			sqlArgs.add(sfApplication.getLinkNextCyclePropField());
			sqlArgs.add(sfApplication.getViewInList());
			sqlArgs.add(sfApplication.getAppId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			Logger.logError(ex);
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成应用信息 SF_APPLICATION数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApplicationDTO sfApplication = (SfApplicationDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_APPLICATION"
				+ " WHERE"
				+ " APP_ID = ?";
			sqlArgs.add(sfApplication.getAppId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成应用信息 SF_APPLICATION数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfApplicationDTO sfApplication = (SfApplicationDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SPV.PROJECT_ID,"
			+ " SA.APP_ID,"
            + " SA.IS_FLOW_PROCESS,"
            + " SA.PROJECT_NAME,"
			+ " SA.PROCEDURE_NAME,"
            + " SA.GROUP_NAME,"
            + " SA.ROLE_NAME,"
			+ " SA.CATEGORY_NAME,"
			+ " SA.APP_NAME,"
//            + " SP.DEFAULT_URL URL,"
            + " dbo.NVL(SA.URL, SP.DEFAULT_URL) URL,"
            + " SA.APP_DATA_CLASS,"
			+ " SA.APP_DATA_SQLMODEL,"
			+ " SA.WINDOW_TYPE,"
			+ " SA.FINISH_MESSAGE,"
			+ " SA.CONFIRM_FINISH,"
			+ " SA.ALLOW_OPERATION,"
			+ " SA.SORT_COLUMN1,"
			+ " SA.SORT_COLUMN2,"
			+ " SA.SORT_COLUMN3,"
			+ " SA.APP_COLUMN1,"
			+ " SA.APP_COLUMN2,"
			+ " SA.APP_COLUMN3,"
			+ " SA.APP_COLUMN4,"
			+ " SA.APP_COLUMN5,"
			+ " SA.APP_COLUMN6,"
			+ " SA.APP_COLUMN7,"
			+ " SA.APP_COLUMN8,"
			+ " SA.APP_COLUMN9,"
			+ " SA.LINK_PROJECT_NAME_FIELD,"
			+ " SA.LINK_PROJECT_DESC_FIELD,"
			+ " SA.LINK_PROCEDURE_NAME_FIELD,"
			+ " SA.LINK_PROCEDURE_DESC_FIELD,"
			+ " SA.LINK_TASK_NAME_FIELD,"
			+ " SA.LINK_TASK_DESC_FIELD,"
			+ " SA.LINK_TAKS_DURATION_FIELD,"
			+ " SA.LINK_GROUP_FIELD,"
			+ " SA.LINK_ROLE_FILED,"
			+ " SA.LINK_USERS_FIELD,"
			+ " SA.LINK_FROM_PROJECT_FIELD,"
			+ " SA.LINK_FORM_PROCEDURE_FIELD,"
			+ " SA.LINK_FROM_USER_FIELD,"
			+ " SA.LINK_FORM_DATE_FIELD,"
			+ " SA.LINK_FORM_TASK_ID_FIELD,"
			+ " SA.LINK_HANDLE_GROUP_FIELD,"
			+ " SA.LINK_HANDLER_FIELD,"
			+ " SA.LINK_SIGN_STATUS_FIELD,"
			+ " SA.LINK_SIGN_DATE_FIELD,"
			+ " SA.LINK_TASK_DUE_FIELD,"
			+ " SA.LINK_REVIEW_GROUP_FIELD,"
			+ " SA.LINK_REVIEW_ROLE_FIELD,"
			+ " SA.LINK_REVIEW_USERS_FIELD,"
			+ " SA.LINK_REVIEW_STATUS_FIELD,"
			+ " SA.LINK_DELIVERY_PRIORITY_FIELD,"
			+ " SA.LINK_USER_MESSAGE_FIELD,"
			+ " SA.LINK_NEXT_CYCLE_PROP_FIELD,"
			+ " SA.VIEW_IN_LIST,"
            + " SP.TRAY_TYPE"
            + " FROM"
			+ " SF_APPLICATION SA,SF_PROJECT_V SPV, SF_PROCEDURE SP"
			+ " WHERE"
			+ " SA.APP_ID = ? AND SPV.PROJECT_NAME = SA.PROJECT_NAME"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " AND SP.PROCEDURE_NAME = SA.PROCEDURE_NAME";
        sqlArgs.add(sfApplication.getAppId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成应用信息 SF_APPLICATION多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			SfApplicationDTO sfApplication = (SfApplicationDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " APP_ID,"
                + " IS_FLOW_PROCESS,"
                + " PROJECT_NAME,"
				+ " PROCEDURE_NAME,"
                + " GROUP_NAME,"
                + " ROLE_NAME,"
				+ " CATEGORY_NAME,"
				+ " APP_NAME,"
                + " URL,"
                + " APP_DATA_CLASS,"
				+ " APP_DATA_SQLMODEL,"
				+ " WINDOW_TYPE,"
				+ " FINISH_MESSAGE,"
				+ " CONFIRM_FINISH,"
				+ " ALLOW_OPERATION,"
				+ " SORT_COLUMN1,"
				+ " SORT_COLUMN2,"
				+ " SORT_COLUMN3,"
				+ " APP_COLUMN1,"
				+ " APP_COLUMN2,"
				+ " APP_COLUMN3,"
				+ " APP_COLUMN4,"
				+ " APP_COLUMN5,"
				+ " APP_COLUMN6,"
				+ " APP_COLUMN7,"
				+ " APP_COLUMN8,"
				+ " APP_COLUMN9,"
				+ " LINK_PROJECT_NAME_FIELD,"
				+ " LINK_PROJECT_DESC_FIELD,"
				+ " LINK_PROCEDURE_NAME_FIELD,"
				+ " LINK_PROCEDURE_DESC_FIELD,"
				+ " LINK_TASK_NAME_FIELD,"
				+ " LINK_TASK_DESC_FIELD,"
				+ " LINK_TAKS_DURATION_FIELD,"
				+ " LINK_GROUP_FIELD,"
				+ " LINK_ROLE_FILED,"
				+ " LINK_USERS_FIELD,"
				+ " LINK_FROM_PROJECT_FIELD,"
				+ " LINK_FORM_PROCEDURE_FIELD,"
				+ " LINK_FROM_USER_FIELD,"
				+ " LINK_FORM_DATE_FIELD,"
				+ " LINK_FORM_TASK_ID_FIELD,"
				+ " LINK_HANDLE_GROUP_FIELD,"
				+ " LINK_HANDLER_FIELD,"
				+ " LINK_SIGN_STATUS_FIELD,"
				+ " LINK_SIGN_DATE_FIELD,"
				+ " LINK_TASK_DUE_FIELD,"
				+ " LINK_REVIEW_GROUP_FIELD,"
				+ " LINK_REVIEW_ROLE_FIELD,"
				+ " LINK_REVIEW_USERS_FIELD,"
				+ " LINK_REVIEW_STATUS_FIELD,"
				+ " LINK_DELIVERY_PRIORITY_FIELD,"
				+ " LINK_USER_MESSAGE_FIELD,"
				+ " LINK_NEXT_CYCLE_PROP_FIELD"
				+ " FROM"
				+ " SF_APPLICATION"
				+ " WHERE"
				+ " (? <= 0 OR APP_ID = ?)"
                + " AND (? = '' OR IS_FLOW_PROCESS = ?)"
                + " AND (? = '' OR PROJECT_NAME LIKE ?)"
				+ " AND (? = '' OR PROCEDURE_NAME LIKE ?)"
                + " AND (? = '' OR GROUP_NAME LIKE ?)"
                + " AND (? = '' OR ROLE_NAME LIKE ?)"
                + " AND (? = '' OR CATEGORY_NAME LIKE ?)"
				+ " AND (? = '' OR APP_NAME LIKE ?)"
                + " AND (? = '' OR URL LIKE ?)"
                + " AND (? = '' OR APP_DATA_CLASS LIKE ?)"
				+ " AND (? = '' OR APP_DATA_SQLMODEL LIKE ?)"
				+ " AND (? <= 0 OR WINDOW_TYPE = ?)"
				+ " AND (? <= 0 OR FINISH_MESSAGE = ?)"
				+ " AND (? <= 0 OR CONFIRM_FINISH = ?)"
				+ " AND (? <= 0 OR ALLOW_OPERATION = ?)"
				+ " AND (? = '' OR SORT_COLUMN1 LIKE ?)"
				+ " AND (? = '' OR SORT_COLUMN2 LIKE ?)"
				+ " AND (? = '' OR SORT_COLUMN3 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN1 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN2 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN3 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN4 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN5 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN6 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN7 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN8 LIKE ?)"
				+ " AND (? = '' OR APP_COLUMN9 LIKE ?)"
				+ " AND (? = '' OR LINK_PROJECT_NAME_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_PROJECT_DESC_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_PROCEDURE_NAME_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_PROCEDURE_DESC_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_TASK_NAME_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_TASK_DESC_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_TAKS_DURATION_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_GROUP_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_ROLE_FILED LIKE ?)"
				+ " AND (? = '' OR LINK_USERS_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_FROM_PROJECT_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_FORM_PROCEDURE_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_FROM_USER_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_FORM_DATE_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_FORM_TASK_ID_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_HANDLE_GROUP_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_HANDLER_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_SIGN_STATUS_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_SIGN_DATE_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_TASK_DUE_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_REVIEW_GROUP_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_REVIEW_ROLE_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_REVIEW_USERS_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_REVIEW_STATUS_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_DELIVERY_PRIORITY_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_USER_MESSAGE_FIELD LIKE ?)"
				+ " AND (? = '' OR LINK_NEXT_CYCLE_PROP_FIELD LIKE ?)";
			sqlArgs.add(sfApplication.getAppId());
			sqlArgs.add(sfApplication.getAppId());
            sqlArgs.add(sfApplication.getIsFlowProcess());
            sqlArgs.add(sfApplication.getIsFlowProcess());
            sqlArgs.add(sfApplication.getProjectName());
			sqlArgs.add(sfApplication.getProjectName());
			sqlArgs.add(sfApplication.getProcedureName());
			sqlArgs.add(sfApplication.getProcedureName());
            sqlArgs.add(sfApplication.getGroupName());
            sqlArgs.add(sfApplication.getGroupName());
            sqlArgs.add(sfApplication.getRoleName());
            sqlArgs.add(sfApplication.getRoleName());
            sqlArgs.add(sfApplication.getCategoryName());
			sqlArgs.add(sfApplication.getCategoryName());
			sqlArgs.add(sfApplication.getAppName());
			sqlArgs.add(sfApplication.getAppName());
            sqlArgs.add(sfApplication.getUrl());
            sqlArgs.add(sfApplication.getUrl());
            sqlArgs.add(sfApplication.getAppDataClass());
			sqlArgs.add(sfApplication.getAppDataClass());
			sqlArgs.add(sfApplication.getAppDataSqlmodel());
			sqlArgs.add(sfApplication.getAppDataSqlmodel());
			sqlArgs.add(sfApplication.getWindowType());
			sqlArgs.add(sfApplication.getWindowType());
			sqlArgs.add(sfApplication.getFinishMessage());
			sqlArgs.add(sfApplication.getFinishMessage());
			sqlArgs.add(sfApplication.getConfirmFinish());
			sqlArgs.add(sfApplication.getConfirmFinish());
			sqlArgs.add(sfApplication.getAllowOperation());
			sqlArgs.add(sfApplication.getAllowOperation());
			sqlArgs.add(sfApplication.getSortColumn1());
			sqlArgs.add(sfApplication.getSortColumn1());
			sqlArgs.add(sfApplication.getSortColumn2());
			sqlArgs.add(sfApplication.getSortColumn2());
			sqlArgs.add(sfApplication.getSortColumn3());
			sqlArgs.add(sfApplication.getSortColumn3());
			sqlArgs.add(sfApplication.getAppColumn1());
			sqlArgs.add(sfApplication.getAppColumn1());
			sqlArgs.add(sfApplication.getAppColumn2());
			sqlArgs.add(sfApplication.getAppColumn2());
			sqlArgs.add(sfApplication.getAppColumn3());
			sqlArgs.add(sfApplication.getAppColumn3());
			sqlArgs.add(sfApplication.getAppColumn4());
			sqlArgs.add(sfApplication.getAppColumn4());
			sqlArgs.add(sfApplication.getAppColumn5());
			sqlArgs.add(sfApplication.getAppColumn5());
			sqlArgs.add(sfApplication.getAppColumn6());
			sqlArgs.add(sfApplication.getAppColumn6());
			sqlArgs.add(sfApplication.getAppColumn7());
			sqlArgs.add(sfApplication.getAppColumn7());
			sqlArgs.add(sfApplication.getAppColumn8());
			sqlArgs.add(sfApplication.getAppColumn8());
			sqlArgs.add(sfApplication.getAppColumn9());
			sqlArgs.add(sfApplication.getAppColumn9());
			sqlArgs.add(sfApplication.getLinkProjectNameField());
			sqlArgs.add(sfApplication.getLinkProjectNameField());
			sqlArgs.add(sfApplication.getLinkProjectDescField());
			sqlArgs.add(sfApplication.getLinkProjectDescField());
			sqlArgs.add(sfApplication.getLinkProcedureNameField());
			sqlArgs.add(sfApplication.getLinkProcedureNameField());
			sqlArgs.add(sfApplication.getLinkProcedureDescField());
			sqlArgs.add(sfApplication.getLinkProcedureDescField());
			sqlArgs.add(sfApplication.getLinkTaskNameField());
			sqlArgs.add(sfApplication.getLinkTaskNameField());
			sqlArgs.add(sfApplication.getLinkTaskDescField());
			sqlArgs.add(sfApplication.getLinkTaskDescField());
			sqlArgs.add(sfApplication.getLinkTaksDurationField());
			sqlArgs.add(sfApplication.getLinkTaksDurationField());
			sqlArgs.add(sfApplication.getLinkGroupField());
			sqlArgs.add(sfApplication.getLinkGroupField());
			sqlArgs.add(sfApplication.getLinkRoleFiled());
			sqlArgs.add(sfApplication.getLinkRoleFiled());
			sqlArgs.add(sfApplication.getLinkUsersField());
			sqlArgs.add(sfApplication.getLinkUsersField());
			sqlArgs.add(sfApplication.getLinkFromProjectField());
			sqlArgs.add(sfApplication.getLinkFromProjectField());
			sqlArgs.add(sfApplication.getLinkFormProcedureField());
			sqlArgs.add(sfApplication.getLinkFormProcedureField());
			sqlArgs.add(sfApplication.getLinkFromUserField());
			sqlArgs.add(sfApplication.getLinkFromUserField());
			sqlArgs.add(sfApplication.getLinkFormDateField());
			sqlArgs.add(sfApplication.getLinkFormDateField());
			sqlArgs.add(sfApplication.getLinkFormTaskIdField());
			sqlArgs.add(sfApplication.getLinkFormTaskIdField());
			sqlArgs.add(sfApplication.getLinkHandleGroupField());
			sqlArgs.add(sfApplication.getLinkHandleGroupField());
			sqlArgs.add(sfApplication.getLinkHandlerField());
			sqlArgs.add(sfApplication.getLinkHandlerField());
			sqlArgs.add(sfApplication.getLinkSignStatusField());
			sqlArgs.add(sfApplication.getLinkSignStatusField());
			sqlArgs.add(sfApplication.getLinkSignDateField());
			sqlArgs.add(sfApplication.getLinkSignDateField());
			sqlArgs.add(sfApplication.getLinkTaskDueField());
			sqlArgs.add(sfApplication.getLinkTaskDueField());
			sqlArgs.add(sfApplication.getLinkReviewGroupField());
			sqlArgs.add(sfApplication.getLinkReviewGroupField());
			sqlArgs.add(sfApplication.getLinkReviewRoleField());
			sqlArgs.add(sfApplication.getLinkReviewRoleField());
			sqlArgs.add(sfApplication.getLinkReviewUsersField());
			sqlArgs.add(sfApplication.getLinkReviewUsersField());
			sqlArgs.add(sfApplication.getLinkReviewStatusField());
			sqlArgs.add(sfApplication.getLinkReviewStatusField());
			sqlArgs.add(sfApplication.getLinkDeliveryPriorityField());
			sqlArgs.add(sfApplication.getLinkDeliveryPriorityField());
			sqlArgs.add(sfApplication.getLinkUserMessageField());
			sqlArgs.add(sfApplication.getLinkUserMessageField());
			sqlArgs.add(sfApplication.getLinkNextCyclePropField());
			sqlArgs.add(sfApplication.getLinkNextCyclePropField());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			Logger.logError(ex);
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

    /**
     * 功能：得到使用者有权限运行的流程与非流程应用
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            
            String sqlStr = "SELECT "
                + " CONVERT(INT,SA.APP_ID) APP_ID,"
                + " SA.IS_FLOW_PROCESS,"
                + " SA.PROJECT_NAME,"
                + " SA.PROCEDURE_NAME,"
                + " SA.GROUP_NAME,"
                + " SA.ROLE_NAME,"
                + " SA.CATEGORY_NAME,"
                + " SA.APP_NAME,"
                + " SA.URL,"
                + " SA.APP_DATA_CLASS,"
                + " SA.APP_DATA_SQLMODEL,"
                + " SA.WINDOW_TYPE,"
                + " SA.FINISH_MESSAGE,"
                + " SA.CONFIRM_FINISH,"
                + " SA.ALLOW_OPERATION,"
                + " SA.SORT_COLUMN1,"
                + " SA.SORT_COLUMN2,"
                + " SA.SORT_COLUMN3,"
                + " SA.APP_COLUMN1,"
                + " SA.APP_COLUMN2,"
                + " SA.APP_COLUMN3,"
                + " SA.APP_COLUMN4,"
                + " SA.APP_COLUMN5,"
                + " SA.APP_COLUMN6,"
                + " SA.APP_COLUMN7,"
                + " SA.APP_COLUMN8,"
                + " SA.APP_COLUMN9,"
                + " SA.LINK_PROJECT_NAME_FIELD,"
                + " SA.LINK_PROJECT_DESC_FIELD,"
                + " SA.LINK_PROCEDURE_NAME_FIELD,"
                + " SA.LINK_PROCEDURE_DESC_FIELD,"
                + " SA.LINK_TASK_NAME_FIELD,"
                + " SA.LINK_TASK_DESC_FIELD,"
                + " SA.LINK_TAKS_DURATION_FIELD,"
                + " SA.LINK_GROUP_FIELD,"
                + " SA.LINK_ROLE_FILED,"
                + " SA.LINK_USERS_FIELD,"
                + " SA.LINK_FROM_PROJECT_FIELD,"
                + " SA.LINK_FORM_PROCEDURE_FIELD,"
                + " SA.LINK_FROM_USER_FIELD,"
                + " SA.LINK_FORM_DATE_FIELD,"
                + " SA.LINK_FORM_TASK_ID_FIELD,"
                + " SA.LINK_HANDLE_GROUP_FIELD,"
                + " SA.LINK_HANDLER_FIELD,"
                + " SA.LINK_SIGN_STATUS_FIELD,"
                + " SA.LINK_SIGN_DATE_FIELD,"
                + " SA.LINK_TASK_DUE_FIELD,"
                + " SA.LINK_REVIEW_GROUP_FIELD,"
                + " SA.LINK_REVIEW_ROLE_FIELD,"
                + " SA.LINK_REVIEW_USERS_FIELD,"
                + " SA.LINK_REVIEW_STATUS_FIELD,"
                + " SA.LINK_DELIVERY_PRIORITY_FIELD,"
                + " SA.LINK_USER_MESSAGE_FIELD,"
                + " SA.LINK_NEXT_CYCLE_PROP_FIELD"
                + " FROM"
                + " SF_APPLICATION SA"
                + " WHERE EXISTS( SELECT SUA.USER_ID FROM SF_USER_AUTHORITY SUA WHERE"
                + " SUA.USER_ID = ?"
                + " AND (SUA.ROLE_NAME = '*' OR dbo.SFK_IS_SAME_GROUP_WITH_MASK(SA.GROUP_NAME,SUA.GROUP_NAME) = 1)"
                + " AND SUA.ROLE_NAME = SA.ROLE_NAME"
                + " AND SUA.PROJECT_NAME = SA.PROJECT_NAME"
                + " AND UPPER(SA.VIEW_IN_LIST) = 'Y')"
                + " ORDER BY"
                + " SA.CATEGORY_NAME";


            sqlArgs.add(sfUser.getUserId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
    
    /**
     * 功能：为页面查看使用
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageModel(){
    	SfApplicationDTO sfApplication = (SfApplicationDTO)dtoParameter;
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
			+ " APP_ID,"
			+ " APP_NAME,"
            + " IS_FLOW_PROCESS,"
            + " GROUP_NAME,"
            + " ROLE_NAME,"
			+ " CATEGORY_NAME,"
			+ " WINDOW_TYPE"
			+ " FROM"
			+ " SF_APPLICATION"
			+ " WHERE "
			+ " (? <= 0 OR APP_ID = ?)"
			+ " AND (? = '' OR APP_NAME LIKE ?)"
			+ " AND (? = '' OR IS_FLOW_PROCESS = ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? = '' OR ROLE_NAME LIKE ?)"
			+ " AND (? = '' OR CATEGORY_NAME LIKE ?)"
			+ " AND (? <= 0 OR WINDOW_TYPE = ?)"
			+ " ORDER BY APP_NAME";
        sqlArgs.add(sfApplication.getAppId());
        sqlArgs.add(sfApplication.getAppId());
        sqlArgs.add(sfApplication.getAppName());
        sqlArgs.add(sfApplication.getAppName());
        sqlArgs.add(sfApplication.getIsFlowProcess());
        sqlArgs.add(sfApplication.getIsFlowProcess());
        sqlArgs.add(sfApplication.getGroupName());
        sqlArgs.add(sfApplication.getGroupName());
        sqlArgs.add(sfApplication.getRoleName());
        sqlArgs.add(sfApplication.getRoleName());
        sqlArgs.add(sfApplication.getCategoryName());
        sqlArgs.add(sfApplication.getCategoryName());
        sqlArgs.add(sfApplication.getWindowType());
        sqlArgs.add(sfApplication.getWindowType());
        
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }
    
    /**
     * 功能：删除应用
     * @param ids 应用 ID
     * @return SQLModel 
     */
    public SQLModel delApp(String[] ids){
	    String str = "";
		for (int i = 0; i < ids.length; i++) {
			str += ids[i]+",";
		}
		str = str.substring(0,str.length()-1);
    	SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE" 
        		+ " FROM "
        		+ " SF_APPLICATION"
        		+ " WHERE"
        		+ " APP_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }

    /**
     * 功能：得到使用者有权限运行的流程与非流程应用
     * @param id 应用 id
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getAppInfoById(int id) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();

/*
            String sqlStr = "SELECT *"
                + " FROM"
                + " SF_APPLICATION"
                + " WHERE"
                + " APP_ID = ?";
*/
        String sqlStr = "SELECT "
            + " SA.APP_ID,"
            + " SA.IS_FLOW_PROCESS,"
            + " SA.PROJECT_NAME,"
            + " SA.PROCEDURE_NAME,"
            + " SA.GROUP_NAME,"
            + " SA.ROLE_NAME,"
            + " SA.CATEGORY_NAME,"
            + " SA.APP_NAME,"
//            + " SP.DEFAULT_URL URL,"
//            + " dbo.NVL(SA.URL, SP.DEFAULT_URL) URL,"
            + " (CASE WHEN SA.URL <> NULL THEN SA.URL ELSE SP.DEFAULT_URL END) URL, "
            + " SA.APP_DATA_CLASS,"
            + " SA.APP_DATA_SQLMODEL,"
            + " SA.WINDOW_TYPE,"
            + " SA.FINISH_MESSAGE,"
            + " SA.CONFIRM_FINISH,"
            + " SA.ALLOW_OPERATION,"
            + " SA.SORT_COLUMN1,"
            + " SA.SORT_COLUMN2,"
            + " SA.SORT_COLUMN3,"
            + " SA.APP_COLUMN1,"
            + " SA.APP_COLUMN2,"
            + " SA.APP_COLUMN3,"
            + " SA.APP_COLUMN4,"
            + " SA.APP_COLUMN5,"
            + " SA.APP_COLUMN6,"
            + " SA.APP_COLUMN7,"
            + " SA.APP_COLUMN8,"
            + " SA.APP_COLUMN9,"
            + " SA.LINK_PROJECT_NAME_FIELD,"
            + " SA.LINK_PROJECT_DESC_FIELD,"
            + " SA.LINK_PROCEDURE_NAME_FIELD,"
            + " SA.LINK_PROCEDURE_DESC_FIELD,"
            + " SA.LINK_TASK_NAME_FIELD,"
            + " SA.LINK_TASK_DESC_FIELD,"
            + " SA.LINK_TAKS_DURATION_FIELD,"
            + " SA.LINK_GROUP_FIELD,"
            + " SA.LINK_ROLE_FILED,"
            + " SA.LINK_USERS_FIELD,"
            + " SA.LINK_FROM_PROJECT_FIELD,"
            + " SA.LINK_FORM_PROCEDURE_FIELD,"
            + " SA.LINK_FROM_USER_FIELD,"
            + " SA.LINK_FORM_DATE_FIELD,"
            + " SA.LINK_FORM_TASK_ID_FIELD,"
            + " SA.LINK_HANDLE_GROUP_FIELD,"
            + " SA.LINK_HANDLER_FIELD,"
            + " SA.LINK_SIGN_STATUS_FIELD,"
            + " SA.LINK_SIGN_DATE_FIELD,"
            + " SA.LINK_TASK_DUE_FIELD,"
            + " SA.LINK_REVIEW_GROUP_FIELD,"
            + " SA.LINK_REVIEW_ROLE_FIELD,"
            + " SA.LINK_REVIEW_USERS_FIELD,"
            + " SA.LINK_REVIEW_STATUS_FIELD,"
            + " SA.LINK_DELIVERY_PRIORITY_FIELD,"
            + " SA.LINK_USER_MESSAGE_FIELD,"
            + " SA.LINK_NEXT_CYCLE_PROP_FIELD,"
            + " SA.VIEW_IN_LIST,"
            + " SP.TRAY_TYPE,"
            + " SP.DEFAULT_URL PROC_URL"
            + " FROM"
            + " SF_APPLICATION SA,SF_PROJECT_V SV,SF_PROCEDURE SP"
            + " WHERE"
            + " SA.APP_ID = ? AND SV.PROJECT_NAME = SA.PROJECT_NAME"
            + " AND SP.PROJECT_ID = SV.PROJECT_ID"
            + " AND SP.PROCEDURE_NAME = SA.PROCEDURE_NAME";

            sqlArgs.add(id);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：得到使用者有权限运行的流程与非流程应用
     * @param appName 应用名称
     * @param catName 应用分类名称
     * @param projName 工程名称
     * @param procName 过程名称
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getAppInfoByName(String appName, String catName, String projName, String procName)
            throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();

            String sqlStr = "SELECT"
                + " SA.APP_ID,"
                + " SA.IS_FLOW_PROCESS,"
                + " SA.PROJECT_NAME,"
                + " SA.PROCEDURE_NAME,"
                + " SA.GROUP_NAME,"
                + " SA.ROLE_NAME,"
                + " SA.CATEGORY_NAME,"
                + " SA.APP_NAME,"
//                + " SP.DEFAULT_URL URL,"
                + " dbo.NVL(SA.URL, SP.DEFAULT_URL) URL,"
                + " SA.APP_DATA_CLASS,"
                + " SA.APP_DATA_SQLMODEL,"
                + " SA.WINDOW_TYPE,"
                + " SA.FINISH_MESSAGE,"
                + " SA.CONFIRM_FINISH,"
                + " SA.ALLOW_OPERATION,"
                + " SA.SORT_COLUMN1,"
                + " SA.SORT_COLUMN2,"
                + " SA.SORT_COLUMN3,"
                + " SA.APP_COLUMN1,"
                + " SA.APP_COLUMN2,"
                + " SA.APP_COLUMN3,"
                + " SA.APP_COLUMN4,"
                + " SA.APP_COLUMN5,"
                + " SA.APP_COLUMN6,"
                + " SA.APP_COLUMN7,"
                + " SA.APP_COLUMN8,"
                + " SA.APP_COLUMN9,"
                + " SA.LINK_PROJECT_NAME_FIELD,"
                + " SA.LINK_PROJECT_DESC_FIELD,"
                + " SA.LINK_PROCEDURE_NAME_FIELD,"
                + " SA.LINK_PROCEDURE_DESC_FIELD,"
                + " SA.LINK_TASK_NAME_FIELD,"
                + " SA.LINK_TASK_DESC_FIELD,"
                + " SA.LINK_TAKS_DURATION_FIELD,"
                + " SA.LINK_GROUP_FIELD,"
                + " SA.LINK_ROLE_FILED,"
                + " SA.LINK_USERS_FIELD,"
                + " SA.LINK_FROM_PROJECT_FIELD,"
                + " SA.LINK_FORM_PROCEDURE_FIELD,"
                + " SA.LINK_FROM_USER_FIELD,"
                + " SA.LINK_FORM_DATE_FIELD,"
                + " SA.LINK_FORM_TASK_ID_FIELD,"
                + " SA.LINK_HANDLE_GROUP_FIELD,"
                + " SA.LINK_HANDLER_FIELD,"
                + " SA.LINK_SIGN_STATUS_FIELD,"
                + " SA.LINK_SIGN_DATE_FIELD,"
                + " SA.LINK_TASK_DUE_FIELD,"
                + " SA.LINK_REVIEW_GROUP_FIELD,"
                + " SA.LINK_REVIEW_ROLE_FIELD,"
                + " SA.LINK_REVIEW_USERS_FIELD,"
                + " SA.LINK_REVIEW_STATUS_FIELD,"
                + " SA.LINK_DELIVERY_PRIORITY_FIELD,"
                + " SA.LINK_USER_MESSAGE_FIELD,"
                + " SA.LINK_NEXT_CYCLE_PROP_FIELD,"
                + " SA.VIEW_IN_LIST,"
                + " SP.TRAY_TYPE,"
                + " SP.DEFAULT_URL PROC_URL"
                + " FROM"
                + " SF_APPLICATION SA, SF_PROJECT_V SV, SF_PROCEDURE SP"
                + " WHERE"
                + " SA.APP_NAME = ?"
                + " AND (? = '' OR SA.CATEGORY_NAME = ?)"
                + " AND (? = '' OR SA.PROJECT_NAME = ?)"
                + " AND (? = '' OR SA.PROCEDURE_NAME = ?)"
                + " AND SV.PROJECT_NAME = SA.PROJECT_NAME"
                + " AND SP.PROJECT_ID = SV.PROJECT_ID"
                + " AND SP.PROCEDURE_NAME = SA.PROCEDURE_NAME";

            sqlArgs.add(appName);
            sqlArgs.add(catName);
            sqlArgs.add(catName);
            sqlArgs.add(projName);
            sqlArgs.add(projName);
            sqlArgs.add(procName);
            sqlArgs.add(procName);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}