package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class LocResReportModel extends AMSSQLProducer {

	public LocResReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = null;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		String exportType = dto.getExportType();
		if(exportType.equals(DictConstant.EXPORT_RES_LOC)){
			sqlModel = getResponLocationsModel();
		} else if(exportType.equals(DictConstant.EXPORT_SCAN_LOC_Y)){
			sqlModel = getScanedLocationsModel();
		} else if(exportType.equals(DictConstant.EXPORT_SCAN_LOC_N)){
			sqlModel = getNotScanedLocationsModel();
		}
		return sqlModel;
	}

	/**
	 * 功能：获取所有责任地点
	 * @return SQLModel
	 */
	public SQLModel getResponLocationsModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " EO.WORKORDER_OBJECT_NO,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
						+ " AMC.NAME MAINTAIN_COMPANY_NAME"
						+ " FROM"
						+ " ETS_OBJECT                  EO,"
						+ " AMS_MAINTAIN_RESPONSIBILITY AMR,"
						+ " AMS_MAINTAIN_COMPANY        AMC"
						+ " WHERE"
						+ " EO.WORKORDER_OBJECT_NO = AMR.OBJECT_NO"
						+ " AND AMR.COMPANY_ID = AMC.COMPANY_ID"
						+ " AND AMC.COMPANY_ID = ?"
						+ " AND EO.OBJECT_CATEGORY = ISNULL(?, EO.OBJECT_CATEGORY)"
						+ " ORDER BY"
						+ " OBJECT_CATEGORY";
		List sqlArgs = new ArrayList();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		sqlArgs.add(dto.getMaintainCompany());
		sqlArgs.add(dto.getObjectCategory());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取某代维公司已经巡检的责任地点
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getScanedLocationsModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " EO.WORKORDER_OBJECT_NO,"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
							+ " AMC.NAME MAINTAIN_COMPANY"
							+ " FROM"
							+ " ETS_OBJECT                  EO,"
							+ " AMS_MAINTAIN_RESPONSIBILITY AMR,"
							+ " AMS_MAINTAIN_COMPANY        AMC"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = AMR.OBJECT_NO"
							+ " AND AMR.COMPANY_ID = AMC.COMPANY_ID"
							+ " AND AMC.COMPANY_ID = ?"
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
							+ " ORDER BY"
							+ " EO.OBJECT_CATEGORY";
			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getMaintainCompany());

			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取某代维公司未经巡检的责任地点
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getNotScanedLocationsModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " EO.WORKORDER_OBJECT_NO,"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
							+ " AMC.NAME MAINTAIN_COMPANY"
							+ " FROM"
							+ " ETS_OBJECT                  EO,"
							+ " AMS_MAINTAIN_RESPONSIBILITY AMR,"
							+ " AMS_MAINTAIN_COMPANY        AMC"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = AMR.OBJECT_NO"
							+ " AND AMR.COMPANY_ID = AMC.COMPANY_ID"
							+ " AND AMC.COMPANY_ID = ?"
							+ " AND NOT EXISTS"
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
							+ " ORDER BY"
							+ " EO.OBJECT_CATEGORY";

			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getMaintainCompany());

			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
