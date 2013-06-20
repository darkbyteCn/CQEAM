package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.dto.OverplusEquipmentDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;

/**
 * User: 李轶
 * Date: 2009-6-14
 * Time: 12:38:20
 * Function:	盘盈资产统计
 */

public class InventoryProfitAssetsReportModel extends AMSSQLProducer {

	public InventoryProfitAssetsReportModel(SfUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：获取未匹配的EAM设备SQL
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		OverplusEquipmentDTO dto = (OverplusEquipmentDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EII.BARCODE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " ACCV.COST_CENTER_NAME"
						+ " FROM"
						+ " ETS_SYSTEM_ITEM     ESI,"
						+ " ETS_ITEM_INFO       EII,"
						+ " ETS_OBJECT          EO,"
						+ " AMS_OBJECT_ADDRESS  AOA,"
						+ " AMS_COST_DEPT_MATCH ACDM,"
						+ " AMS_COST_CENTER_V   ACCV"
						+ " WHERE"
						+ " ESI.ITEM_CODE = EII.ITEM_CODE"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EII.RESPONSIBILITY_DEPT *= ACDM.DEPT_CODE"
						+ " AND ACDM.COST_CENTER_CODE *= ACCV.COST_CENTER_CODE"
						+ " AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)"
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_ITEM_MATCH EIM"
						+ " WHERE"
						+ " EIM.SYSTEMID = EII.SYSTEMID)"
						+ " AND NOT EXISTS ("
									+ " SELECT"
											+ " NULL"
									+ " FROM"
									+ " 	ETS_NO_MATCH ENM"
									+ " WHERE"
									+ " 	ENM.SYSTEMID = EII.SYSTEMID)"
                        + " AND EII.FINANCE_PROP = 'UNKNOW'"
                        + " AND ESI.ITEM_NAME LIKE ISNULL(?, ESI.ITEM_NAME)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)"
						+ " AND (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(?, EO.WORKORDER_OBJECT_CODE)"
						+ "  OR EO.WORKORDER_OBJECT_NAME LIKE ISNULL(?, EO.WORKORDER_OBJECT_NAME))"
						+ " AND (( " + SyBaseSQLUtil.isNull() + "  OR ACCV.COST_CENTER_NAME LIKE ?)"
						+ "  OR ( " + SyBaseSQLUtil.isNull() + "  OR ACCV.COST_CENTER_CODE LIKE ?))"
						+ " AND EII.ORGANIZATION_ID = ?";

		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());

		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(dto.getCostCenterName());
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
