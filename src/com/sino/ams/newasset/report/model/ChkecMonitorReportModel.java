package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class ChkecMonitorReportModel extends AMSSQLProducer {

	public ChkecMonitorReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：返回页面翻页查询时所需要的SQLModel
	 * <B>默认为空实现。可由具体应用选择是否需要实现</B>
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " AMC.COMPANY_ID,"
							+ " AMC.NAME MAINTAIN_COMPANY_NAME,"
							+ " ISNULL(T_RES.RESPONSIBILITY_COUNT, 0) RESPONSIBILITY_COUNT,"
							+ " ISNULL(S_RES.SCAN_OVER_COUNT, 0) SCAN_OVER_COUNT,"
							+ " ISNULL(T_RES.RESPONSIBILITY_COUNT, 0) - ISNULL(S_RES.SCAN_OVER_COUNT, 0) NOT_SCAN_COUNT,"
							+ " ROUND((ISNULL(S_RES.SCAN_OVER_COUNT, 0) / ISNULL(T_RES.RESPONSIBILITY_COUNT, 1)) * 100, 2) || '%' SCAN_OVER_RATE"
							+ " FROM"
							+ " AMS_MAINTAIN_COMPANY AMC,"
							+ " (SELECT"
							+ " AMR.COMPANY_ID,"
							+ " COUNT(1) RESPONSIBILITY_COUNT"
							+ " FROM"
							+ " AMS_MAINTAIN_RESPONSIBILITY AMR,"
							+ " ETS_OBJECT                  EO"
							+ " WHERE"
							+ " AMR.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " GROUP BY"
							+ " AMR.COMPANY_ID) T_RES,"
							+ " (SELECT"
							+ " AMR.COMPANY_ID,"
							+ " COUNT(1) SCAN_OVER_COUNT"
							+ " FROM"
							+ " AMS_MAINTAIN_RESPONSIBILITY AMR,"
							+ " ETS_OBJECT                  EO"
							+ " WHERE"
							+ " AMR.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EXISTS"
							+ " (SELECT"
							+ " NULL"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
							+ " AND (AACH.ORDER_STATUS = ? OR AACH.ORDER_STATUS = ?)"
							+ " AND ((AACH.UPLOAD_DATE >= ISNULL(?, AACH.UPLOAD_DATE)"
							+ " AND AACH.UPLOAD_DATE <= ISNULL(?, AACH.UPLOAD_DATE))"
							+ " OR (AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)"
							+ " AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE))))"
							+ " GROUP BY"
							+ " AMR.COMPANY_ID) S_RES"
							+ " WHERE"
							+ " AMC.COMPANY_ID *= T_RES.COMPANY_ID"
							+ " AND T_RES.COMPANY_ID *= S_RES.COMPANY_ID"
							+ " AND AMC.COMPANY_ID = ISNULL(?, AMC.COMPANY_ID)"
							+ " AND AMC.ORGANIZATION_ID = ?";
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;

			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getMaintainCompany());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
