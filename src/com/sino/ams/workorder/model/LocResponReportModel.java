package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LocResponReportModel extends AMSSQLProducer {

	public LocResponReportModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = null;
		EtsWorkorderDTO dto = (EtsWorkorderDTO)dtoParameter;
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
						+ " AND EO.OBJECT_CATEGORY = dbo.NVL(?, EO.OBJECT_CATEGORY)"
						+ " ORDER BY"
						+ " OBJECT_CATEGORY";
		List sqlArgs = new ArrayList();
		EtsWorkorderDTO dto = (EtsWorkorderDTO)dtoParameter;
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
							+ " AND EO.OBJECT_CATEGORY = dbo.NVL(?, EO.OBJECT_CATEGORY)"
							+ " AND EXISTS"
							+ " (SELECT"
							+ " NULL"
							+ " FROM"
							+ " ETS_WORKORDER EW"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE))))"
							+ " ORDER BY"
							+ " OBJECT_CATEGORY";
			List sqlArgs = new ArrayList();
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(dto.getMaintainCompany());
			sqlArgs.add(dto.getObjectCategory());
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
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
							+ " AND EO.OBJECT_CATEGORY = dbo.NVL(?, EO.OBJECT_CATEGORY)"
							+ " AND NOT EXISTS"
							+ " (SELECT"
							+ " NULL"
							+ " FROM"
							+ " ETS_WORKORDER EW"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE))))"
							+ " ORDER BY"
							+ " OBJECT_CATEGORY";
			List sqlArgs = new ArrayList();
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(dto.getMaintainCompany());
			sqlArgs.add(dto.getObjectCategory());
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
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
