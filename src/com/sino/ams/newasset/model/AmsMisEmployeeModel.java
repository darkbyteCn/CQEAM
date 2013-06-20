package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: AmsMisEmployeeModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsMisEmployeeModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsMisEmployeeModel {
	private int orgId = -1;
	/**
	 * 功能：MIS员工表 AMS_MIS_EMPLOYEE 数据库SQL构造层构造函数
	 * @param orgId String 组织id
	 */
	public AmsMisEmployeeModel(int orgId) {
		this.orgId = orgId;
	}

	/**
	 * 功能：判断传入的组织是否合法
	 * @return SQLModel 返回SQLModel
	 */
	public SQLModel getIsOrgValidModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " 1"
						+ " FROM"
						+ " ETS_OU_CITY_MAP  EOCM"
						+ " WHERE"
						+ " EOCM.ORGANIZATION_ID = ?";
		sqlArgs.add(orgId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成MIS员工表 AMS_MIS_EMPLOYEE多条数据信息查询SQLModel，请根据实际需要修改。
	 * @param lastUpdateDate 上次更新日期
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPersonDept4PDAModel(String lastUpdateDate) throws
			SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT "
							+ " AME.EMPLOYEE_ID,"
							+ " AME.USER_NAME,"
							+ " AMD.DEPT_CODE,"
							+ " AMD.DEPT_NAME,"
							+ " AME.ENABLED"
							+ " FROM"
							+ " AMS_MIS_EMPLOYEE AME,"
							+ " AMS_MIS_DEPT     AMD,"
							+ " ETS_OU_CITY_MAP  EOCM"
							+ " WHERE"
							+ " AME.DEPT_CODE = AMD.DEPT_CODE"
							+ " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
							+ " AND EOCM.ORGANIZATION_ID = ?";
			sqlArgs.add(orgId);
			if (!StrUtil.isEmpty(lastUpdateDate)) {
				SimpleCalendar cal = new SimpleCalendar();
				cal.setCalendarValue(lastUpdateDate);
				sqlStr += " AND AMD.LAST_UPDATE_DATE >= ?";
				sqlArgs.add(cal);
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
