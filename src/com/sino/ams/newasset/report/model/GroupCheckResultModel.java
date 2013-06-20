package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

public class GroupCheckResultModel extends AMSSQLProducer {

	public GroupCheckResultModel(SfUserDTO userAccount,
			AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：按部门统计巡检地点数
	 * 
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = " SELECT"
					+ " AMD.DEPT_CODE,"
					+ " AMD.DEPT_NAME,"
					+ " count(distinct ADDRESS1_COUNT) ADDRESS_COUNT," 
					+ " count(distinct SCAN1_COUNT) SCAN_COUNT," 
					+ " count(distinct NOSCAN1_COUNT) NOSCAN_COUNT"
					+ " FROM"
					+ " AMS_MIS_DEPT AMD,"
					+ " (SELECT DISTINCT"
					+ " ISNULL(AACL.SCAN_RESPONSIBILITY_DEPT, AACL.RESPONSIBILITY_DEPT) DEPT_CODE,"
					+ " EO.WORKORDER_OBJECT_NO ADDRESS1_COUNT,"
//					+ " case when AACH.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED','DISTRIBUTED','SAVE_TEMP','DOWNLOADED','CANCELED') then EO.WORKORDER_OBJECT_NO end ADDRESS1_COUNT,"
					+ " case when AACH.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED') then EO.WORKORDER_OBJECT_NO end SCAN1_COUNT,"
					+ " case when AACH.ORDER_STATUS IN ('DISTRIBUTED','SAVE_TEMP','DOWNLOADED','CANCELED') then EO.WORKORDER_OBJECT_NO end NOSCAN1_COUNT"
					+ " FROM"
					+ " ETS_OBJECT              EO,"
					+ " AMS_ASSETS_CHECK_HEADER AACH,"
					+ " AMS_ASSETS_CHECK_LINE   AACL"
					+ " WHERE"
					+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
					+ " AND AACH.HEADER_ID = AACL.HEADER_ID"
					+ " AND ((AACH.UPLOAD_DATE >= ISNULL(?, AACH.UPLOAD_DATE)"
					+ " AND AACH.UPLOAD_DATE <= ISNULL(?, AACH.UPLOAD_DATE))"
					+ " OR (AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)"
					+ " AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)))) TMP_V"
					+ " WHERE" + " AMD.DEPT_CODE *= TMP_V.DEPT_CODE"
					+ " AND AMD.COMPANY_CODE = ?"
					+ " AND AMD.DEPT_CODE = ISNULL(LTRIM(?), AMD.DEPT_CODE)"
					+ " GROUP BY" + " AMD.DEPT_CODE," + " AMD.DEPT_NAME";
			// "SELECT"
			// + " AMD.DEPT_CODE,"
			// + " AMD.DEPT_NAME,"
			// + " COUNT(TMP_V.WORKORDER_OBJECT_NO) SCAN_COUNT,"
			// + " COUNT(TMP_V.WORKORDER_OBJECT_NO) NOSCAN_COUNT,"
			// + " COUNT(TMP_V.WORKORDER_OBJECT_NO) ADDRESS_COUNT"
			// + " FROM"
			// + " AMS_MIS_DEPT AMD,"
			// + " (SELECT DISTINCT"
			// +
			// " ISNULL(AACL.SCAN_RESPONSIBILITY_DEPT, AACL.RESPONSIBILITY_DEPT) DEPT_CODE,"
			// + " EO.WORKORDER_OBJECT_NO"
			// + " FROM"
			// + " ETS_OBJECT              EO,"
			// + " AMS_ASSETS_CHECK_HEADER AACH,"
			// + " AMS_ASSETS_CHECK_LINE   AACL"
			// + " WHERE"
			// + " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
			// + " AND AACH.HEADER_ID = AACL.HEADER_ID"
			// +
			// " AND AACH.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED','DISTRIBUTED','SAVE_TEMP','DOWNLOADED','CANCELED')"
			// + " AND ((AACH.UPLOAD_DATE >= ISNULL(?, AACH.UPLOAD_DATE)"
			// + " AND AACH.UPLOAD_DATE <= ISNULL(?, AACH.UPLOAD_DATE))"
			// + " OR (AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)"
			// +
			// " AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)))) TMP_V"
			// + " WHERE"
			// + " AMD.DEPT_CODE *= TMP_V.DEPT_CODE"
			// + " AND AMD.COMPANY_CODE = ?"
			// + " AND AMD.DEPT_CODE = ISNULL(LTRIM(?), AMD.DEPT_CODE)"
			// + " GROUP BY"
			// + " AMD.DEPT_CODE,"
			// + " AMD.DEPT_NAME";
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}

			sqlArgs.add(userAccount.getCompanyCode());
			sqlArgs.add(dto.getCheckDept());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}

		return sqlModel;
	}
}
