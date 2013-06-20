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
public class GroupScanModel extends AMSSQLProducer {

	public GroupScanModel(SfUserDTO userAccount, EtsWorkorderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取部门巡检地点明细SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getLocationsModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " EO.WORKORDER_OBJECT_NO,"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
							+ " EO.WORKORDER_OBJECT_LOCATION"
							+ " FROM"
							+ " ETS_OBJECT EO"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO IN ("
							+ " SELECT"
							+ " EW.WORKORDER_OBJECT_NO"
							+ " FROM"
							+ " ETS_WORKORDER EW"
							+ " WHERE"
							+ " EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND EW.GROUP_ID = ?"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE))))";
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
			sqlArgs.add(dto.getGroupId());
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
	 * 功能：获取部门巡检设备明细SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getItemsModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " EII.BARCODE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " AMD.DEPT_NAME,"
							+ " AME.USER_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION"
							+ " FROM"
							+ " ETS_ITEM_INFO      EII,"
							+ " ETS_SYSTEM_ITEM    ESI,"
							+ " AMS_MIS_DEPT       AMD,"
							+ " AMS_MIS_EMPLOYEE   AME,"
							+ " ETS_OBJECT         EO,"
							+ " AMS_OBJECT_ADDRESS AOA"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
							+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
							+ " AND EII.BARCODE IN ("
							+ " SELECT"
							+ " EWD.BARCODE"
							+ " FROM"
							+ " ETS_WORKORDER_DTL EWD,"
							+ " ETS_WORKORDER     EW"
							+ " WHERE"
							+ " EWD.WORKORDER_NO = EW.WORKORDER_NO"
							+ " AND EW.WORKORDER_TYPE = ?"
							+ " AND (EW.WORKORDER_FLAG = ? OR EW.WORKORDER_FLAG = ?)"
							+ " AND EW.GROUP_ID = ?"
							+ " AND EW.WORKORDER_OBJECT_NO = ?"
							+ " AND ((EW.UPLOAD_DATE >= dbo.NVL(?, EW.UPLOAD_DATE)"
							+ " AND EW.UPLOAD_DATE <= dbo.NVL(?, EW.UPLOAD_DATE))"
							+ " OR (EW.CHECKOVER_DATE >= dbo.NVL(?, EW.CHECKOVER_DATE)"
							+ " AND EW.CHECKOVER_DATE <= dbo.NVL(?, EW.CHECKOVER_DATE))))";
			EtsWorkorderDTO dto = (EtsWorkorderDTO) dtoParameter;
			sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
			sqlArgs.add(DictConstant.WORKORDER_STATUS_UPLOADED);
			sqlArgs.add(DictConstant.WOR_STATUS_ARCHIVED);
			sqlArgs.add(dto.getGroupId());
			sqlArgs.add(dto.getWorkorderObjectNo());
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
