package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
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
public class CompItemModel extends AMSSQLProducer {

	public CompItemModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取设备巡检结果报表
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
		String exportType = dto.getExportType();
		if(exportType.equals(DictConstant.OWN_ITEM)){
			sqlModel = getCompOwnItemModel();
		} else if(exportType.equals(DictConstant.SCAN_ITEM_Y)){
			sqlModel = getCompScanedItemModel();
		} else if(exportType.equals(DictConstant.SCAN_ITEM_N)){
			sqlModel = getCompNotScanedItemModel();
		}
		return sqlModel;
	}

	/**
	 * 功能：获取公司指定专业的所有设备
	 * @return SQLModel
	 */
	public SQLModel getCompOwnItemModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " EII.BARCODE,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " ESI.ITEM_UNIT,"
						+ " AME.USER_NAME,"
						+ " AMD.DEPT_NAME,"
						+ " EO.WORKORDER_OBJECT_LOCATION"
						+ " FROM"
						+ " ETS_ITEM_INFO      EII,"
						+ " ETS_SYSTEM_ITEM    ESI,"
						+ " AMS_OBJECT_ADDRESS AOA,"
						+ " ETS_OBJECT         EO,"
						+ " AMS_MIS_DEPT       AMD,"
						+ " AMS_MIS_EMPLOYEE   AME"
						+ " WHERE"
						+ " EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
						+ " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') "
						+ " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE='') "
						+ " AND EII.ORGANIZATION_ID = ?"
						+ " AND ESI.ITEM_CATEGORY = ?"
						+ " ORDER BY"
						+ " ESI.ITEM_CATEGORY,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC";
		List sqlArgs = new ArrayList();
		EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getItemCategory());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取公司指定专业已巡检的设备
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getCompScanedItemModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr = "SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " AME.USER_NAME,"
							+ " AMD.DEPT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION"
							+ " FROM"
							+ " ETS_ITEM_INFO      EII,"
							+ " ETS_SYSTEM_ITEM    ESI,"
							+ " AMS_OBJECT_ADDRESS AOA,"
							+ " ETS_OBJECT         EO,"
							+ " AMS_MIS_DEPT       AMD,"
							+ " AMS_MIS_EMPLOYEE   AME"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
							+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
							+ " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') "
							+ " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE='') "
							+ " AND EII.ORGANIZATION_ID = ?"
							+ " AND ESI.ITEM_CATEGORY = ?"
							+ " AND EXISTS("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EII.BARCODE = EWD.BARCODE"
							+ " AND EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE)))"
							+ " AND EW.WORKORDER_NO = ("
							+ " SELECT"
							+ " MAX(EW2.WORKORDER_NO)"
							+ " FROM"
							+ " ETS_WORKORDER     EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE)))"
							+ " AND EW2.ORGANIZATION_ID = EW.ORGANIZATION_ID))"
							+ " ORDER BY"
							+ " ESI.ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC";
			List sqlArgs = new ArrayList();
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
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
	 * 功能：获取公司指定专业未巡检的设备
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getCompNotScanedItemModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr = "SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " AME.USER_NAME,"
							+ " AMD.DEPT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION"
							+ " FROM"
							+ " ETS_ITEM_INFO      EII,"
							+ " ETS_SYSTEM_ITEM    ESI,"
							+ " AMS_OBJECT_ADDRESS AOA,"
							+ " ETS_OBJECT         EO,"
							+ " AMS_MIS_DEPT       AMD,"
							+ " AMS_MIS_EMPLOYEE   AME"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
							+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
							+ " AND (EO.DISABLE_DATE IS NULL OR EO.DISABLE_DATE='') "
							+ " AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE='') "
							+ " AND EII.ORGANIZATION_ID = ?"
							+ " AND ESI.ITEM_CATEGORY = ?"
							+ " AND NOT EXISTS("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EII.BARCODE = EWD.BARCODE"
							+ " AND EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE)))"
							+ " AND EW.WORKORDER_NO = ("
							+ " SELECT"
							+ " MAX(EW2.WORKORDER_NO)"
							+ " FROM"
							+ " ETS_WORKORDER     EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE)))"
							+ " AND EW2.ORGANIZATION_ID = EW.ORGANIZATION_ID))"
							+ " ORDER BY"
							+ " ESI.ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC";
			List sqlArgs = new ArrayList();
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
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
