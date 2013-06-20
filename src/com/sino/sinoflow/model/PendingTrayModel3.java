package com.sino.sinoflow.model;


import java.util.*;

import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.*;
import com.sino.sinoflow.dto.SfActInfoDTO;


/**
 * <p>Title: SfActInfoModel</p>
 * <p>Description:程序自动生成SQL构造器“SfActInfoModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class PendingTrayModel3 extends BaseSQLProducer {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：流转过程，在办流转信息 SF_ACT_INFO 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfActInfoDTO 本次操作的数据
	 */
	public PendingTrayModel3(SfUserBaseDTO userAccount, SfActInfoDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成流转过程，在办流转信息 SF_ACT_INFO页面翻页查询SQLModel，请根据实际需要修改。
     * @param keyword  关键字
     * @param subject 主题
     * @param createby 承办人
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel(String keyword, String subject, String others, String createby, String fromDate, String toDate, String sortTypeStr) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//        try {
            List sqlArgs = new ArrayList();
//            SfActInfoDTO sfActInfo = (SfActInfoDTO)dtoParameter;
/*
            String sqlStr = "SELECT "
                    + " SAI.SFACT_DOC_TYPE,"
                    + " SAI.SFACT_ACT_ID,"
                    + " SAI.SFACT_PROC_NAME,"
                    + " SAI.SFACT_APPL_MSG,"
                    + " SAI.SFACT_SIGN_DATE,"
                    + " SAI.SFACT_SIGN_DUE_DATE,"
                    + " SAI.SFACT_FROM_DATE,"
                    + " SAI.SFACT_CASE_ID,"
                    + " SAI.SFACT_PROC_ID,"
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
                    + " SAI.SFACT_DELIVERY_PRIORITY,"
                    + " SAI.SFACT_CASE_LOCK_STATUS,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_1,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_2,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_3,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_4,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_5,"
                    + " SAI.SFACT_HANDLER,"
                    + " SAI.SFACT_HANDLER_GROUP,"
                    + " SAI.SFACT_PLUS_GROUP,"
                    + " SAI.SFACT_TASK_GROUP,"
                    + " SAI.SFACT_TASK_NAME,"
                    + " SA.CATEGORY_NAME,"
                    + " SA.APP_NAME,"
                    + " SA.PROJECT_NAME,"
                    + " SA.WINDOW_TYPE,"
                    + " SA.ALLOW_OPERATION,"
                    + " SA.FINISH_MESSAGE,"
                    + " SP.TRAY_TYPE,"
                    + " SAI.SFACT_COMMENT_QTY,"
                    + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                    + " SAI.SFACT_COMMENT_TYPE,"
                    + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER,"
                    + " dbo.SFK_GET_REALNAMES(STR_REPLACE(SAI.SFACT_FROM_TASK_USER, ';SYSTEM', '')) SFACT_FROM_TASK_USER"
                    + " FROM"
                    + " SF_ACT_INFO SAI, SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST, SF_USER SU2"
                    + " WHERE"
                    + " UPPER(SU2.LOGIN_NAME) = UPPER(?)"
                    + " AND ((SAI.SFACT_SIGN_USER = SU2.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU2.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL)))) OR"
                    + "   EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU2.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL))))"
                    + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                    + " AND SAI.SFACT_COMPLETE_STATUS <> 1"
                    + " AND SAI.SFACT_APPDEF_ID = SA.APP_ID"
                    + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                    + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                    + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_1 LIKE ?)"
                    + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_2 LIKE ?)"
                    + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_3 LIKE ?)"
                    + " AND (? = '' OR SAI.SFACT_FROM_DATE >= ?)"
                    + " AND (? = '' OR SAI.SFACT_FROM_DATE <= ?)"
                    + " AND (? = '' OR dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) LIKE ?)";
*/

        String sqlStr = "SELECT "
                 + " MID_TAB.SFACT_DOC_TYPE,"
                 + " MID_TAB.SFACT_ACT_ID,"
                 + " MID_TAB.SFACT_PROC_NAME,"
                 + " MID_TAB.SFACT_FROM_DATE,"
                 + " MID_TAB.WINDOW_TYPE,"
                 + " MID_TAB.SFACT_APPL_MSG,"
                 + " MID_TAB.SFACT_SIGN_DATE,"
                 + " MID_TAB.SFACT_SIGN_DUE_DATE,"
                 + " MID_TAB.SFACT_CASE_ID,"
                 + " MID_TAB.SFACT_PROC_ID,"
                 + " MID_TAB.SFACT_APPL_COLUMN_1,"
                 + " MID_TAB.SFACT_APPL_COLUMN_2,"
                 + " MID_TAB.SFACT_APPL_COLUMN_3,"
                 + " MID_TAB.SFACT_DELIVERY_PRIORITY,"
                 + " MID_TAB.SFACT_HANDLER,"
                 + " MID_TAB.SFACT_HANDLER_GROUP,"
                 + " MID_TAB.SFACT_PLUS_GROUP,"
                 + " MID_TAB.SFACT_TASK_GROUP,"
                 + " MID_TAB.SFACT_TASK_NAME,"
                 + " MID_TAB.PROJECT_NAME,"
                 + " MID_TAB.ALLOW_OPERATION,"
                 + " MID_TAB.FINISH_MESSAGE,"
                 + " MID_TAB.TRAY_TYPE,"
                 + " MID_TAB.SFACT_COMMENT_QTY,"
                 + " MID_TAB.SFACT_COMMENT_APPL_MSG,"
                 + " MID_TAB.SFACT_COMMENT_TYPE,"
                 + " MID_TAB.SFACT_COMPOSE_USER,"
                 + " MID_TAB.CATEGORY_NAME,"
                 + " MID_TAB.SFACT_FROM_TASK_USER"
                 + " FROM"
                 + " (SELECT"
                 + " SAI.SFACT_DOC_TYPE,"
                 + " SAI.SFACT_ACT_ID,"
                 + " SAI.SFACT_PROC_NAME,"
                 + " SAI.SFACT_FROM_DATE,"
                 + " SAI.SFACT_APPL_MSG,"
                 + " SAI.SFACT_SIGN_DATE,"
                 + " SAI.SFACT_SIGN_DUE_DATE,"
                 + " SAI.SFACT_CASE_ID,"
                 + " SAI.SFACT_PROC_ID,"
                 + " SAI.SFACT_APPL_COLUMN_1,"
                 + " SAI.SFACT_APPL_COLUMN_2,"
                 + " SAI.SFACT_APPL_COLUMN_3,"
                 + " SAI.SFACT_DELIVERY_PRIORITY,"
                 + " SAI.SFACT_HANDLER,"
                 + " SAI.SFACT_HANDLER_GROUP,"
                 + " SAI.SFACT_PLUS_GROUP,"
                 + " SAI.SFACT_TASK_GROUP,"
                 + " SAI.SFACT_TASK_NAME,"
                 + " SA.CATEGORY_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAI.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAI.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER,"
                 + " dbo.SFK_GET_REALNAMES(STR_REPLACE(SAI.SFACT_FROM_TASK_USER, ';SYSTEM', '')) SFACT_FROM_TASK_USER"
                 + " FROM"
                 + " SF_ACT_INFO SAI,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST"
                 + " WHERE"
                 + " SAI.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                 + " AND (SAI.SFACT_SIGN_STATUS <> 1 OR SAI.SFACT_SIGN_STATUS IS NULL)"
                 + " AND ((dbo.SFK_GROUP_IN_LIST(SAI.SFACT_TASK_USERS,?) <> 0 AND dbo.SFK_GET_DELEGATE_USER(?) = '')"
                 + " 	OR dbo.SFK_GROUPS_IN_LIST(SAI.SFACT_TASK_USERS,dbo.SFK_GET_ASSIGN_USER(?)) <> 0)"

                 + " UNION ALL"

                 + " SELECT"
                 + " SAI.SFACT_DOC_TYPE,"
                 + " SAI.SFACT_ACT_ID,"
                 + " SAI.SFACT_PROC_NAME,"
                 + " SAI.SFACT_FROM_DATE,"
                 + " SAI.SFACT_APPL_MSG,"
                 + " SAI.SFACT_SIGN_DATE,"
                 + " SAI.SFACT_SIGN_DUE_DATE,"
                 + " SAI.SFACT_CASE_ID,"
                 + " SAI.SFACT_PROC_ID,"
                 + " SAI.SFACT_APPL_COLUMN_1,"
                 + " SAI.SFACT_APPL_COLUMN_2,"
                 + " SAI.SFACT_APPL_COLUMN_3,"
                 + " SAI.SFACT_DELIVERY_PRIORITY,"
                 + " SAI.SFACT_HANDLER,"
                 + " SAI.SFACT_HANDLER_GROUP,"
                 + " SAI.SFACT_PLUS_GROUP,"
                 + " SAI.SFACT_TASK_GROUP,"
                 + " SAI.SFACT_TASK_NAME,"
                 + " SA.CATEGORY_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAI.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAI.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER,"
                 + " dbo.SFK_GET_REALNAMES(STR_REPLACE(SAI.SFACT_FROM_TASK_USER, ';SYSTEM', '')) SFACT_FROM_TASK_USER"
                 + " FROM"
                 + " SF_ACT_INFO SAI, SF_USER SU2,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST"
                 + " WHERE"
                 + " SAI.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                 + " AND UPPER(SU2.LOGIN_NAME) = UPPER(?)"
                 + " AND ((SAI.SFACT_SIGN_USER = SU2.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU2.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL OR SD.START_DATE = '') AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL OR SD.END_DATE = '')))) OR"
                 + " EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU2.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL OR SD.START_DATE = '') AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL OR SD.END_DATE = ''))))"
                 + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                 + " AND SAI.SFACT_COMPLETE_STATUS <> 1"

                 + " UNION ALL"

                 + " SELECT "
                 + " SAAV.SFACT_DOC_TYPE,"
                 + " SAAV.SFACT_ACT_ID,"
                 + " SAAV.SFACT_PROC_NAME,"
                 + " SAAV.SFACT_FROM_DATE,"
                 + " '抄送'  SFACT_APPL_MSG,"
                 + " SAAV.SFACT_SIGN_DATE,"
                 + " SAAV.SFACT_SIGN_DUE_DATE,"
                 + " SAAV.SFACT_CASE_ID,"
                 + " SAAV.SFACT_PROC_ID,"
                 + " SAAV.SFACT_APPL_COLUMN_1,"
                 + " SAAV.SFACT_APPL_COLUMN_2,"
                 + " SAAV.SFACT_APPL_COLUMN_3,"
                 + " SAAV.SFACT_DELIVERY_PRIORITY,"
                 + " SAAV.SFACT_HANDLER,"
                 + " SAAV.SFACT_HANDLER_GROUP,"
                 + " SAAV.SFACT_PLUS_GROUP,"
                 + " SAAV.SFACT_TASK_GROUP,"
                 + " SAAV.SFACT_TASK_NAME,"
                 + " SA.CATEGORY_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAAV.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAAV.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAAV.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER,"
                 + " dbo.SFK_GET_REALNAMES(SAC.FROM_USER) SFACT_FROM_TASK_USER"
                 + " FROM"
                 + " SF_ACT_COPY SAC,"
                 + " SF_ACT_ALL_V SAAV,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST"
                 + " WHERE"

                 + " SAAV.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAAV.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAAV.SFACT_TASK_ID"
                 + " AND UPPER(SAC.TASK_USER) = UPPER(?)"
                 + " AND ( SAC.ACT_ID = SAAV.SFACT_ACT_ID )"
                 + " AND (SAC.STATUS <> 2)"
                 + " ) MID_TAB"

                 + " WHERE "
                 + " (? = '' OR MID_TAB.SFACT_APPL_COLUMN_1 LIKE ? )"
                 + " AND (? = '' OR MID_TAB.SFACT_APPL_COLUMN_2 LIKE ? )"
                 + " AND (? = '' OR MID_TAB.SFACT_APPL_COLUMN_3 LIKE ? )"
                + " AND (? = '' OR MID_TAB.SFACT_FROM_DATE >= ?)"
                + " AND (? = '' OR MID_TAB.SFACT_FROM_DATE <= ?)"
                + " AND (? = '' OR dbo.SFK_GET_REALNAMES(MID_TAB.SFACT_COMPOSE_USER) LIKE ?)";

        if(sortTypeStr != null && !sortTypeStr.equals("")) {
            sqlStr += " ORDER BY " + sortTypeStr;
        }
        sqlStr += " AT ISOLATION READ UNCOMMITTED";

            sqlModel.setSqlStr(sqlStr);
            sqlArgs.add(sfUser.getLoginName());
            sqlArgs.add(sfUser.getLoginName());
            sqlArgs.add(sfUser.getLoginName());
            sqlArgs.add(sfUser.getLoginName());
            sqlArgs.add(sfUser.getLoginName());
            if(keyword == null || keyword.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + keyword + "%");
                sqlArgs.add("%" + keyword + "%");
            }
            if(subject == null || subject.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + subject + "%");
                sqlArgs.add("%" + subject + "%");
            }
            if(others == null || others.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + others + "%");
                sqlArgs.add("%" + others + "%");
            }
        sqlArgs.add(fromDate);
        sqlArgs.add(fromDate);
        if(toDate == null || toDate.equals("")) {
            sqlArgs.add(toDate);
            sqlArgs.add(toDate);
        } else {
            sqlArgs.add(toDate + " 23:59:59");
            sqlArgs.add(toDate + " 23:59:59");
        }
            if(createby == null || createby.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + createby + "%");
                sqlArgs.add("%" + createby + "%");
            }
            sqlModel.setArgs(sqlArgs);
//        } catch (CalendarException ex) {
//            Logger.logError(ex);
//            throw new SQLModelException(ex);
//        }
        return sqlModel;
	}

    /**
     * 功能：框架自动生成流转过程，在办流转信息 SF_ACT_INFO页面翻页查询SQLModel，请根据实际需要修改。
     * @param userId 用户 ID
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getPendingCountModel(int userId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
/*
        String sqlStr = "SELECT  COUNT(1) COUNTNO"
                    + " FROM"
                    + " SF_ACT_INFO SAI, SF_USER SU, SF_APPLICATION SA, SF_TASK ST"
                    + " WHERE"
                    + " SU.USER_ID = ?"
                    + " AND SAI.SFACT_APPDEF_ID = SA.APP_ID"
                    + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                    + " AND ((SAI.SFACT_SIGN_USER = SU.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL)))) OR"
                    + "   EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL))))"
                    + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                    + " AND SAI.SFACT_COMPLETE_STATUS <> '1' AT ISOLATION READ UNCOMMITTED";
*/
        String sqlStr = "SELECT  COUNT(1) COUNTNO"
                 + " MID_TAB.SFACT_ACT_ID,"
                 + " MID_TAB.SFACT_PROC_NAME,"
                 + " MID_TAB.SFACT_FROM_DATE,"
                 + " MID_TAB.WINDOW_TYPE,"
                 + " MID_TAB.SFACT_APPL_MSG,"
                 + " MID_TAB.SFACT_SIGN_DATE,"
                 + " MID_TAB.SFACT_SIGN_DUE_DATE,"
                 + " MID_TAB.SFACT_CASE_ID,"
                 + " MID_TAB.SFACT_PROC_ID,"
                 + " MID_TAB.SFACT_APPL_COLUMN_1,"
                 + " MID_TAB.SFACT_APPL_COLUMN_2,"
                 + " MID_TAB.SFACT_APPL_COLUMN_3,"
                 + " MID_TAB.SFACT_DELIVERY_PRIORITY,"
                 + " MID_TAB.SFACT_HANDLER,"
                 + " MID_TAB.SFACT_HANDLER_GROUP,"
                 + " MID_TAB.SFACT_PLUS_GROUP,"
                 + " MID_TAB.SFACT_TASK_GROUP,"
                 + " MID_TAB.SFACT_TASK_NAME,"
                 + " MID_TAB.PROJECT_NAME,"
                 + " MID_TAB.ALLOW_OPERATION,"
                 + " MID_TAB.FINISH_MESSAGE,"
                 + " MID_TAB.TRAY_TYPE,"
                 + " MID_TAB.SFACT_COMMENT_QTY,"
                 + " MID_TAB.SFACT_COMMENT_APPL_MSG,"
                 + " MID_TAB.SFACT_COMMENT_TYPE,"
                 + " MID_TAB.SFACT_COMPOSE_USER"
                 + " FROM"
                 + " (SELECT"
                 + " SAI.SFACT_ACT_ID,"
                 + " SAI.SFACT_PROC_NAME,"
                 + " SAI.SFACT_FROM_DATE,"
                 + " SAI.SFACT_APPL_MSG,"
                 + " SAI.SFACT_SIGN_DATE,"
                 + " SAI.SFACT_SIGN_DUE_DATE,"
                 + " SAI.SFACT_CASE_ID,"
                 + " SAI.SFACT_PROC_ID,"
                 + " SAI.SFACT_APPL_COLUMN_1,"
                 + " SAI.SFACT_APPL_COLUMN_2,"
                 + " SAI.SFACT_APPL_COLUMN_3,"
                 + " SAI.SFACT_DELIVERY_PRIORITY,"
                 + " SAI.SFACT_HANDLER,"
                 + " SAI.SFACT_HANDLER_GROUP,"
                 + " SAI.SFACT_PLUS_GROUP,"
                 + " SAI.SFACT_TASK_GROUP,"
                 + " SAI.SFACT_TASK_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAI.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAI.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER"
                 + " FROM"
                 + " SF_ACT_INFO SAI,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST, SF_USER SU"
                 + " WHERE"
                 + " SU.USER_ID = ?"
                 + " AND SAI.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                 + " AND (SAI.SFACT_SIGN_STATUS <> 1 OR SAI.SFACT_SIGN_STATUS IS NULL)"
                 + " AND ((dbo.SFK_GROUP_IN_LIST(SAI.SFACT_TASK_USERS, SU.LOGIN_NAME) <> 0 AND dbo.SFK_GET_DELEGATE_USER(SU.LOGIN_NAME) = '')"
                 + " 	OR dbo.SFK_GROUPS_IN_LIST(SAI.SFACT_TASK_USERS,dbo.SFK_GET_ASSIGN_USER(SU.LOGIN_NAME)) <> 0)"

                 + " UNION ALL"

                 + " SELECT"
                 + " SAI.SFACT_ACT_ID,"
                 + " SAI.SFACT_PROC_NAME,"
                 + " SAI.SFACT_FROM_DATE,"
                 + " SAI.SFACT_APPL_MSG,"
                 + " SAI.SFACT_SIGN_DATE,"
                 + " SAI.SFACT_SIGN_DUE_DATE,"
                 + " SAI.SFACT_CASE_ID,"
                 + " SAI.SFACT_PROC_ID,"
                 + " SAI.SFACT_APPL_COLUMN_1,"
                 + " SAI.SFACT_APPL_COLUMN_2,"
                 + " SAI.SFACT_APPL_COLUMN_3,"
                 + " SAI.SFACT_DELIVERY_PRIORITY,"
                 + " SAI.SFACT_HANDLER,"
                 + " SAI.SFACT_HANDLER_GROUP,"
                 + " SAI.SFACT_PLUS_GROUP,"
                 + " SAI.SFACT_TASK_GROUP,"
                 + " SAI.SFACT_TASK_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAI.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAI.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER"
                 + " FROM"
                 + " SF_ACT_INFO SAI, SF_USER SU2,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST"
                 + " WHERE"
                 + " SU2.USER_ID = ?"
                 + " AND SAI.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                 + " AND ((SAI.SFACT_SIGN_USER = SU2.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU2.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL OR SD.START_DATE = '') AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL OR SD.END_DATE = '')))) OR"
                 + " EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU2.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL OR SD.START_DATE = '') AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL OR SD.END_DATE = ''))))"
                 + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                 + " AND SAI.SFACT_COMPLETE_STATUS <> 1"

                 + " UNION ALL"

                 + " SELECT "
                 + " SAAV.SFACT_ACT_ID,"
                 + " SAAV.SFACT_PROC_NAME,"
                 + " SAAV.SFACT_FROM_DATE,"
                 + " '抄送'  SFACT_APPL_MSG,"
                 + " SAAV.SFACT_SIGN_DATE,"
                 + " SAAV.SFACT_SIGN_DUE_DATE,"
                 + " SAAV.SFACT_CASE_ID,"
                 + " SAAV.SFACT_PROC_ID,"
                 + " SAAV.SFACT_APPL_COLUMN_1,"
                 + " SAAV.SFACT_APPL_COLUMN_2,"
                 + " SAAV.SFACT_APPL_COLUMN_3,"
                 + " SAAV.SFACT_DELIVERY_PRIORITY,"
                 + " SAAV.SFACT_HANDLER,"
                 + " SAAV.SFACT_HANDLER_GROUP,"
                 + " SAAV.SFACT_PLUS_GROUP,"
                 + " SAAV.SFACT_TASK_GROUP,"
                 + " SAAV.SFACT_TASK_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAAV.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAAV.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAAV.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER"
                 + " FROM"
                 + " SF_ACT_COPY SAC,"
                 + " SF_ACT_ALL_V SAAV,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST, SF_USER SU"
                 + " WHERE"
                 + " SU.USER_ID = ?"
                 + " AND SAAV.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAAV.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAAV.SFACT_TASK_ID"
                 + " AND UPPER(SAC.TASK_USER) = UPPER(SU.LOGIN_NAME)"
                 + " AND ( SAC.ACT_ID = SAAV.SFACT_ACT_ID )"
                 + " AND (SAC.STATUS <> 2)"
                 + " ) MID_TAB";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(userId);
        sqlArgs.add(userId);
        sqlArgs.add(userId);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPendingCountModel(String login) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
/*
        String sqlStr = "SELECT COUNT(1) COUNTNO"
                    + " FROM"
                    + " SF_ACT_INFO SAI, SF_USER SU, SF_APPLICATION SA, SF_TASK ST"
                    + " WHERE"
                    + " SU.LOGIN_NAME = ?"
                    + " AND SAI.SFACT_APPDEF_ID = SA.APP_ID"
                    + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                    + " AND ((SAI.SFACT_SIGN_USER = SU.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL)))) OR"
                    + "   EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL))))"
                    + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                    + " AND SAI.SFACT_COMPLETE_STATUS <> 1 AT ISOLATION READ UNCOMMITTED";
*/
        String sqlStr = "SELECT  COUNT(1) COUNTNO"
                 + " FROM"
                 + " (SELECT"
                 + " SAI.SFACT_ACT_ID,"
                 + " SAI.SFACT_PROC_NAME,"
                 + " SAI.SFACT_FROM_DATE,"
                 + " SAI.SFACT_APPL_MSG,"
                 + " SAI.SFACT_SIGN_DATE,"
                 + " SAI.SFACT_SIGN_DUE_DATE,"
                 + " SAI.SFACT_CASE_ID,"
                 + " SAI.SFACT_PROC_ID,"
                 + " SAI.SFACT_APPL_COLUMN_1,"
                 + " SAI.SFACT_APPL_COLUMN_2,"
                 + " SAI.SFACT_APPL_COLUMN_3,"
                 + " SAI.SFACT_DELIVERY_PRIORITY,"
                 + " SAI.SFACT_HANDLER,"
                 + " SAI.SFACT_HANDLER_GROUP,"
                 + " SAI.SFACT_PLUS_GROUP,"
                 + " SAI.SFACT_TASK_GROUP,"
                 + " SAI.SFACT_TASK_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAI.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAI.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER"
                 + " FROM"
                 + " SF_ACT_INFO SAI,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST"
                 + " WHERE"
                 + " SAI.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                 + " AND (SAI.SFACT_SIGN_STATUS <> 1 OR SAI.SFACT_SIGN_STATUS IS NULL)"
                 + " AND ((dbo.SFK_GROUP_IN_LIST(SAI.SFACT_TASK_USERS,?) <> 0 AND dbo.SFK_GET_DELEGATE_USER(?) = '')"
                 + " 	OR dbo.SFK_GROUPS_IN_LIST(SAI.SFACT_TASK_USERS,dbo.SFK_GET_ASSIGN_USER(?)) <> 0)"

                 + " UNION ALL"

                 + " SELECT"
                 + " SAI.SFACT_ACT_ID,"
                 + " SAI.SFACT_PROC_NAME,"
                 + " SAI.SFACT_FROM_DATE,"
                 + " SAI.SFACT_APPL_MSG,"
                 + " SAI.SFACT_SIGN_DATE,"
                 + " SAI.SFACT_SIGN_DUE_DATE,"
                 + " SAI.SFACT_CASE_ID,"
                 + " SAI.SFACT_PROC_ID,"
                 + " SAI.SFACT_APPL_COLUMN_1,"
                 + " SAI.SFACT_APPL_COLUMN_2,"
                 + " SAI.SFACT_APPL_COLUMN_3,"
                 + " SAI.SFACT_DELIVERY_PRIORITY,"
                 + " SAI.SFACT_HANDLER,"
                 + " SAI.SFACT_HANDLER_GROUP,"
                 + " SAI.SFACT_PLUS_GROUP,"
                 + " SAI.SFACT_TASK_GROUP,"
                 + " SAI.SFACT_TASK_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAI.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAI.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER"
                 + " FROM"
                 + " SF_ACT_INFO SAI, SF_USER SU2,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST"
                 + " WHERE"
                 + " SAI.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                 + " AND UPPER(SU2.LOGIN_NAME) = UPPER(?)"
                 + " AND ((SAI.SFACT_SIGN_USER = SU2.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU2.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL OR SD)))) OR"
                 + " EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU2.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL OR SD.END_DATE = ''))))"
                 + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                 + " AND SAI.SFACT_COMPLETE_STATUS <> 1"

                 + " UNION ALL"

                 + " SELECT "
                 + " SAAV.SFACT_ACT_ID,"
                 + " SAAV.SFACT_PROC_NAME,"
                 + " SAAV.SFACT_FROM_DATE,"
                 + " '抄送'  SFACT_APPL_MSG,"
                 + " SAAV.SFACT_SIGN_DATE,"
                 + " SAAV.SFACT_SIGN_DUE_DATE,"
                 + " SAAV.SFACT_CASE_ID,"
                 + " SAAV.SFACT_PROC_ID,"
                 + " SAAV.SFACT_APPL_COLUMN_1,"
                 + " SAAV.SFACT_APPL_COLUMN_2,"
                 + " SAAV.SFACT_APPL_COLUMN_3,"
                 + " SAAV.SFACT_DELIVERY_PRIORITY,"
                 + " SAAV.SFACT_HANDLER,"
                 + " SAAV.SFACT_HANDLER_GROUP,"
                 + " SAAV.SFACT_PLUS_GROUP,"
                 + " SAAV.SFACT_TASK_GROUP,"
                 + " SAAV.SFACT_TASK_NAME,"
                 + " SA.PROJECT_NAME,"
                 + " SA.ALLOW_OPERATION,"
                 + " SA.FINISH_MESSAGE,"
                 + " SP.TRAY_TYPE,"
                 + " SA.WINDOW_TYPE,"
                 + " SAAV.SFACT_COMMENT_QTY,"
                 + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                 + " SAAV.SFACT_COMMENT_TYPE,"
                 + " dbo.SFK_GET_REALNAMES(SAAV.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER"
                 + " FROM"
                 + " SF_ACT_COPY SAC,"
                 + " SF_ACT_ALL_V SAAV,SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST"
                 + " WHERE"

                 + " SAAV.SFACT_APPDEF_ID = SA.APP_ID"
                 + " AND SAAV.SFACT_PROC_ID = SP.PROCEDURE_ID"
                 + " AND ST.TASK_ID = SAAV.SFACT_TASK_ID"
                 + " AND UPPER(SAC.TASK_USER) = UPPER(?)"
                 + " AND ( SAC.ACT_ID = SAAV.SFACT_ACT_ID )"
                 + " AND (SAC.STATUS <> 2)"
                 + " ) MID_TAB";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(login);
        sqlArgs.add(login);
        sqlArgs.add(login);
        sqlArgs.add(login);
        sqlArgs.add(login);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getCheckModel(String keyword, String subject, String others, String createby, int sortType) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//        try {
            List sqlArgs = new ArrayList();
//            SfActInfoDTO sfActInfo = (SfActInfoDTO)dtoParameter;
            String sqlStr = "SELECT "
                    + " SAI.SFACT_DOC_TYPE,"
                    + " SAI.SFACT_ACT_ID,"
                    + " SAI.SFACT_PROC_NAME,"
                    + " SAI.SFACT_APPL_MSG,"
                    + " SAI.SFACT_SIGN_DATE,"
                    + " SAI.SFACT_SIGN_DUE_DATE,"
                    + " SAI.SFACT_CASE_ID,"
                    + " SAI.SFACT_PROC_ID,"
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
                    + " SAI.SFACT_DELIVERY_PRIORITY,"
                    + " SAI.SFACT_CASE_LOCK_STATUS,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_1,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_2,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_3,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_4,"
                    + " SAI.SFACT_TASK_ATTRIBUTE_5,"
                    + " SAI.SFACT_HANDLER,"
                    + " SAI.SFACT_HANDLER_GROUP,"
                    + " SAI.SFACT_PLUS_GROUP,"
                    + " SAI.SFACT_TASK_GROUP,"
                    + " SA.CATEGORY_NAME,"
                    + " SA.APP_NAME,"
                    + " SA.PROJECT_NAME,"
                    + " SA.WINDOW_TYPE,"
                    + " SA.ALLOW_OPERATION,"
                    + " SA.FINISH_MESSAGE,"
                    + " SP.TRAY_TYPE,"
                    + " SAI.SFACT_COMMENT_QTY,"
                    + " ST.REVIEW_DESC SFACT_COMMENT_APPL_MSG,"
                    + " SAI.SFACT_COMMENT_TYPE,"
                    + " dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) SFACT_COMPOSE_USER"
                    + " FROM"
                    + " SF_ACT_INFO SAI, SF_APPLICATION SA, SF_PROCEDURE SP, SF_TASK ST, SF_USER SU2"
                    + " WHERE"
                    + " UPPER(SU2.LOGIN_NAME) = UPPER(?)"
                    + " AND ((SAI.SFACT_SIGN_USER = SU2.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU2.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL)))) OR"
                    + "   EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU2.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL))))"
                    + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                    + " AND SAI.SFACT_COMPLETE_STATUS <> 1"
                    + " AND SAI.SFACT_APPDEF_ID = SA.APP_ID"
                    + " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
                    + " AND ST.TASK_ID = SAI.SFACT_TASK_ID"
                    + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_1 LIKE ?)"
                    + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_2 LIKE ?)"
                    + " AND (? = '' OR SAI.SFACT_APPL_COLUMN_3 LIKE ?)"
                    + " AND (? = '' OR dbo.SFK_GET_REALNAMES(SAI.SFACT_COMPOSE_USER) LIKE ?)"
                    + " AND (SAI.SFACT_TASK_ATTRIBUTE_5 <> '' AND SAI.SFACT_TASK_ATTRIBUTE_5 LIKE '%:1')";
            switch(sortType) {
                case 0: default:
                    sqlStr += " ORDER BY SAI.SFACT_PROC_NAME, SAI.SFACT_APPL_MSG, ";
                    break;
                case 1:
                    sqlStr += " ORDER BY SA.APP_NAME, SAI.SFACT_APPL_MSG, ";
                    break;
                case 2:
                    sqlStr += " ORDER BY  SA.CATEGORY_NAME, SAI.SFACT_APPL_MSG, ";
                    break;
            }
            sqlStr += " SAI.SFACT_SIGN_DATE, SAI.SFACT_APPL_COLUMN_1, SAI.SFACT_APPL_COLUMN_2 AT ISOLATION READ UNCOMMITTED";

            sqlModel.setSqlStr(sqlStr);
            sqlArgs.add(sfUser.getLoginName());
            if(keyword == null || keyword.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + keyword + "%");
                sqlArgs.add("%" + keyword + "%");
            }
            if(subject == null || subject.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + subject + "%");
                sqlArgs.add("%" + subject + "%");
            }
            if(others == null || others.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + others + "%");
                sqlArgs.add("%" + others + "%");
            }
            if(createby == null || createby.equals("")) {
                sqlArgs.add("");
                sqlArgs.add("");
            } else {
                sqlArgs.add("%" + createby + "%");
                sqlArgs.add("%" + createby + "%");
            }
            sqlModel.setArgs(sqlArgs);
//        } catch (CalendarException ex) {
//            Logger.logError(ex);
//            throw new SQLModelException(ex);
//        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成流转过程，在办流转信息 SF_ACT_INFO页面翻页查询SQLModel，请根据实际需要修改。
     * @param userId 用户 ID
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getCheckCountModel(int userId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT /*+ USE_HASH(SAI, SU) */ COUNT(1) COUNTNO"
                    + " FROM"
                    + " SF_ACT_INFO SAI, SF_USER SU"
                    + " WHERE"
                    + " SU.USER_ID = ?"
                    + " AND ((SAI.SFACT_SIGN_USER = SU.LOGIN_NAME AND NOT EXISTS (SELECT NULL FROM SF_DELEGATION SD WHERE SU.USER_ID = SD.USER_ID AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL)))) OR"
                    + "   EXISTS (SELECT NULL FROM SF_DELEGATION SD, SF_USER SU3 WHERE SU.USER_ID = SD.DELEGATE_TO AND SU3.USER_ID = SD.USER_ID AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER AND SD.STATUS_CTL = 1 AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL))))"
                    + " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
                    + " AND SAI.SFACT_COMPLETE_STATUS <> 1"
                    + " AND (SAI.SFACT_TASK_ATTRIBUTE_5 <> '' AND SAI.SFACT_TASK_ATTRIBUTE_5 LIKE '%:1') AT ISOLATION READ UNCOMMITTED";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(userId);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}