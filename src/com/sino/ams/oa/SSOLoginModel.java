package com.sino.ams.oa;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

public class SSOLoginModel {
	/**
	 * 功能：获取用户信息SQL
	 * 
	 * @return SQLModel
	 */
	public SQLModel getUserLoginModel(String oaName) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT" + " SU.USER_ID," + " SU.LOGIN_NAME,"
				+ " SU.PASSWORD," + " SU.USERNAME," + " SU.WORKNO,"
				+ " SU.OFFICE_TEL," + " SU.FAX," + " SU.MOBILE_PHONE,"
				+ " SU.END_DATE," + " SU.EMAIL," + " SU.DISPLAY_ORDER,"
				+ " SU.PASSWORD_DATE," + " SU.ORGANIZATION_ID,"
				+ " SU.IS_INNER," + " SU.CREATION_DATE," + " SU.CREATED_BY,"
				+ " SU.LAST_UPDATE_DATE," + " SU.LAST_UPDATE_BY,"
				+ " SU.EMPLOYEE_NUMBER," + " SU.ENABLED, "
				+ " SU.IS_MAINTAIN_USER," + " AMC.COMPANY_ID MAINTAIN_COMPANY,"
				+ " AMC.NAME MAINTAIN_COMPANY_NAME," + " AME.EMPLOYEE_ID,"
				+ " AME.EMPLOYEE_NUMBER," + " AMD.DEPT_CODE,"
				+ " AMD.DEPT_NAME," + " EOCM.COMPANY," + " EOCM.COMPANY_CODE,"
				+ " EOCM.BOOK_TYPE_CODE," + " EOCM.IS_TD,"
				+ " EOCM.BOOK_TYPE_NAME," + " EOCM.MATCH_ORGANIZATION_ID"
				+ " FROM" + " SF_USER              SU,"
				+ " AMS_MIS_EMPLOYEE     AME," + " AMS_MIS_DEPT         AMD,"
				+ " ETS_OU_CITY_MAP      EOCM," + " AMS_MAINTAIN_COMPANY AMC"
				+ " WHERE" + " SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
				+ " AND EOCM.COMPANY_CODE *=AME.COMPANY_CODE "
				+ " AND AME.DEPT_CODE *= AMD.DEPT_CODE"
				+ " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
				+ " AND SU.MAINTAIN_COMPANY *= AMC.COMPANY_ID"
				+ " AND ISNULL(SU.END_DATE, GETDATE()) >= GETDATE()"
				+ " AND EOCM.ENABLED = 'Y'"
				// + " AND SU.ENABLED = 'Y'"
				+ " AND UPPER(SU.OA_NAME) = UPPER(?)";
		sqlArgs.add(oaName);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPendingCountModel(String loginName) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sql = "SELECT "
				+ " COUNT(*)"
				+ " FROM SF_ACT_INFO    SAI,"
				+ "   SF_APPLICATION SA,"
				+ "     SF_PROCEDURE   SP,"
				+ "       SF_TASK        ST,"
				+ "      SF_USER        SU2"
				+ " WHERE"
				+ " ((SAI.SFACT_SIGN_USER = SU2.LOGIN_NAME AND NOT EXISTS"
				+ "  (SELECT NULL"
				+ "      FROM SF_DELEGATION SD"
				+ "     WHERE SU2.USER_ID = SD.USER_ID"
				+ "       AND SD.STATUS_CTL = 1"
				+ "       AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND"
				+ "   (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL)))) OR EXISTS"
				+ "  (SELECT NULL"
				+ "     FROM SF_DELEGATION SD, SF_USER SU3"
				+ "    WHERE SU2.USER_ID = SD.DELEGATE_TO"
				+ "      AND SU3.USER_ID = SD.USER_ID"
				+ "      AND SU3.LOGIN_NAME = SAI.SFACT_SIGN_USER"
				+ "      AND SD.STATUS_CTL = 1"
				+ "      AND ((GETDATE() >= SD.START_DATE OR SD.START_DATE IS NULL) AND"
				+ "    (GETDATE() <= SD.END_DATE OR SD.END_DATE IS NULL))))"
				+ " AND (SAI.SFACT_SIGN_STATUS = 1 AND SAI.SFACT_SUSPEND_FLAG <> 1)"
				+ " AND SAI.SFACT_COMPLETE_STATUS <> 1"
				+ "	 AND SAI.SFACT_APPDEF_ID = SA.APP_ID"
				+ " AND SAI.SFACT_PROC_ID = SP.PROCEDURE_ID"
				+ "	 AND ST.TASK_ID = SAI.SFACT_TASK_ID  "
				+ "	 AND SU2.LOGIN_NAME='"+loginName+"'";
		sqlModel.setSqlStr(sql);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
