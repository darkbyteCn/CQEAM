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
public class ItemResponReportModel extends AMSSQLProducer {

	public ItemResponReportModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = null;
		EtsWorkorderDTO dto = (EtsWorkorderDTO)dtoParameter;
		String exportType = dto.getExportType();
		if(exportType.equals(DictConstant.EXPORT_LOC_ITEM)){
			sqlModel = getResponItemsModel();
		} else if(exportType.equals(DictConstant.EXPORT_SCAN_ITEM)){
			sqlModel = getScanedItemsModel();
		} else if(exportType.equals(DictConstant.EXPORT_DIFF_ITEM)){
			sqlModel = getDiffItemsModel();
		}
		return sqlModel;
	}

	/**
	 * 功能：获取指定地点所有责任设备
	 * @return SQLModel
	 */
	public SQLModel getResponItemsModel() {
		SQLModel sqlModel = new SQLModel();
		EtsWorkorderDTO dto = (EtsWorkorderDTO)dtoParameter;
		String sqlStr = "SELECT"
						+ " EII.BARCODE,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " ESI.ITEM_UNIT,"
						+ " EO.WORKORDER_OBJECT_LOCATION"
						+ " FROM"
						+ " ETS_ITEM_INFO          EII,"
						+ " ETS_SYSTEM_ITEM        ESI,"
						+ " ETS_SYSITEM_DISTRIBUTE ESD,"
						+ " ETS_OBJECT             EO,"
						+ " AMS_OBJECT_ADDRESS     AOA"
						+ " WHERE"
						+ " EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND ESI.ITEM_CODE = ESD.ITEM_CODE"
						+ " AND ESD.ORGANIZATION_ID = EO.ORGANIZATION_ID"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EO.WORKORDER_OBJECT_NO = ?"
						+ " ORDER BY"
						+ " ESI.ITEM_CATEGORY,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取指定地点已巡检过的设备
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getScanedItemsModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT"
							+ " FROM"
							+ " ETS_SYSTEM_ITEM        ESI,"
							+ " ETS_SYSITEM_DISTRIBUTE ESD,"
							+ " ETS_ITEM_INFO          EII"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CODE = ESD.ITEM_CODE"
							+ " AND ESD.ORGANIZATION_ID = EII.ORGANIZATION_ID"
							+ " AND EII.BARCODE IN("
							+ " SELECT"
							+ " EWD.BARCODE"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_OBJECT_NO = ?"
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
							+ " ETS_WORKORDER EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE)))))"
							+ " ORDER BY"
							+ " ESI.ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC";
			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getWorkorderObjectNo());
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
	 * 功能：获取指定地点系统设备与最近一次巡检之间的差异情况
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDiffItemsModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " *"
							+ " FROM"
							+ " ((SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " '有' SYSTEM_EXISTS,"
							+ " (SELECT  CASE WHEN COUNT(1)=0 THEN '无' ELSE '有' END "
							+ " FROM"
							+ " (SELECT"
							+ " EII.BARCODE"
							+ " FROM"
							+ " ETS_ITEM_INFO EII"
							+ " WHERE"
							+ " EII.BARCODE IN ("
							+ " SELECT"
							+ " EWD.BARCODE"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_OBJECT_NO = ?"
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
							+ " ETS_WORKORDER EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE)))))) SCAN_V"
							+ " WHERE"
							+ " EII.BARCODE = SCAN_V.BARCODE) SCAN_EXISTS"
							+ " FROM"
							+ " ETS_ITEM_INFO          EII,"
							+ " ETS_SYSTEM_ITEM        ESI,"
							+ " ETS_SYSITEM_DISTRIBUTE ESD,"
							+ " ETS_OBJECT             EO,"
							+ " AMS_OBJECT_ADDRESS     AOA"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CODE = ESD.ITEM_CODE"
							+ " AND ESD.ORGANIZATION_ID = EO.ORGANIZATION_ID"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EO.WORKORDER_OBJECT_NO = ?"
							+ " UNION"
							+ " SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " (SELECT  CASE WHEN COUNT(1)=0 THEN '无' ELSE '有' END "
							+ " FROM"
							+ " ETS_ITEM_INFO      EII2,"
							+ " AMS_OBJECT_ADDRESS AOA2,"
							+ " ETS_OBJECT         EO2"
							+ " WHERE"
							+ " EII2.SYSTEMID = EII.SYSTEMID"
							+ " AND EII2.ADDRESS_ID = AOA2.ADDRESS_ID"
							+ " AND AOA2.OBJECT_NO = EO2.WORKORDER_OBJECT_NO"
							+ " AND EO2.WORKORDER_OBJECT_NO = ?) SYSTEM_EXISTS,"
							+ " '有' SCAN_EXISTS"
							+ " FROM"
							+ " ETS_SYSTEM_ITEM        ESI,"
							+ " ETS_SYSITEM_DISTRIBUTE ESD,"
							+ " ETS_ITEM_INFO          EII"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CODE = ESD.ITEM_CODE"
							+ " AND ESD.ORGANIZATION_ID = EII.ORGANIZATION_ID"
							+ " AND EII.BARCODE IN ("
							+ " SELECT"
							+ " EWD.BARCODE"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_OBJECT_NO = ?"
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
							+ " ETS_WORKORDER EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE))))))"
							+ " MINUS"
							+ " (SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " '有' SYSTEM_EXISTS,"
							+ " (SELECT  CASE WHEN COUNT(1)=0 THEN '无' ELSE '有' END "
							+ " FROM"
							+ " (SELECT"
							+ " EII.BARCODE"
							+ " FROM"
							+ " ETS_ITEM_INFO EII"
							+ " WHERE"
							+ " EII.BARCODE IN ("
							+ " SELECT"
							+ " EWD.BARCODE"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_OBJECT_NO = ?"
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
							+ " ETS_WORKORDER EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE)))))) SCAN_V"
							+ " WHERE"
							+ " EII.BARCODE = SCAN_V.BARCODE) SCAN_EXISTS"
							+ " FROM"
							+ " ETS_ITEM_INFO          EII,"
							+ " ETS_SYSTEM_ITEM        ESI,"
							+ " ETS_SYSITEM_DISTRIBUTE ESD,"
							+ " ETS_OBJECT             EO,"
							+ " AMS_OBJECT_ADDRESS     AOA"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CODE = ESD.ITEM_CODE"
							+ " AND ESD.ORGANIZATION_ID = EO.ORGANIZATION_ID"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EO.WORKORDER_OBJECT_NO = ?"
							+ " INTERSECT"
							+ " SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " (SELECT  CASE WHEN COUNT(1)=0 THEN '无' ELSE '有' END "
							+ " FROM"
							+ " ETS_ITEM_INFO      EII2,"
							+ " AMS_OBJECT_ADDRESS AOA2,"
							+ " ETS_OBJECT         EO2"
							+ " WHERE"
							+ " EII2.SYSTEMID = EII.SYSTEMID"
							+ " AND EII2.ADDRESS_ID = AOA2.ADDRESS_ID"
							+ " AND AOA2.OBJECT_NO = EO2.WORKORDER_OBJECT_NO"
							+ " AND EO2.WORKORDER_OBJECT_NO = ?) SYSTEM_EXISTS,"
							+ " '有' SCAN_EXISTS"
							+ " FROM"
							+ " ETS_SYSTEM_ITEM        ESI,"
							+ " ETS_SYSITEM_DISTRIBUTE ESD,"
							+ " ETS_ITEM_INFO          EII"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CODE = ESD.ITEM_CODE"
							+ " AND ESD.ORGANIZATION_ID = EII.ORGANIZATION_ID"
							+ " AND EII.BARCODE IN ("
							+ " SELECT"
							+ " EWD.BARCODE"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_OBJECT_NO = ?"
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
							+ " ETS_WORKORDER EW2"
							+ " WHERE"
							+ " EW2.WORKORDER_OBJECT_NO = EW.WORKORDER_OBJECT_NO"
							+ " AND EW2.WORKORDER_TYPE = ?"
							+ " AND (EW2.WORKORDER_FLAG = ? OR EW2.WORKORDER_FLAG = ?)"
							+ " AND ((EW2.UPLOAD_DATE >= dbo.NVL(?, EW2.UPLOAD_DATE)"
							+ " AND EW2.UPLOAD_DATE <= dbo.NVL(?, EW2.UPLOAD_DATE))"
							+ " OR (EW2.CHECKOVER_DATE >= dbo.NVL(?, EW2.CHECKOVER_DATE)"
							+ " AND EW2.CHECKOVER_DATE <= dbo.NVL(?, EW2.CHECKOVER_DATE))))))) TMP_V"
							+ " ORDER BY"
							+ " TMP_V .ITEM_CATEGORY,"
							+ " TMP_V .ITEM_NAME,"
							+ " TMP_V .ITEM_SPEC";
			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getWorkorderObjectNo());
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
			sqlArgs.add(dto.getWorkorderObjectNo());
			sqlArgs.add(dto.getWorkorderObjectNo());
			sqlArgs.add(dto.getWorkorderObjectNo());
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
			sqlArgs.add(dto.getWorkorderObjectNo());
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
			sqlArgs.add(dto.getWorkorderObjectNo());
			sqlArgs.add(dto.getWorkorderObjectNo());
			sqlArgs.add(dto.getWorkorderObjectNo());
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
