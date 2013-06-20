package com.sino.td.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.dto.AmsMactPropertyDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: jiangtao
 * Date: 2007-11-21
 * Time: 11:55:32
 * To change this template use File | Settings | File Templates.
 */
public class AmsMatchedTdListModel extends AMSSQLProducer {

	/**
	 * 功能：未匹配资产清单 数据库SQL构造层构造函数
	 *
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public AmsMatchedTdListModel(SfUserDTO userAccount, AmsMactPropertyDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成已匹配资产清单 查询 。
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsMactPropertyDTO dto = (AmsMactPropertyDTO)dtoParameter;
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " EII.BARCODE,"
							+ " dbo.NVL(EFA.MIS_TAG_NUMBER,EFA.TAG_NUMBER) TAG_NUMBER,"
							+ " ESI.ITEM_NAME,"
							+ " EFA.ASSETS_DESCRIPTION,"
							+ " ESI.ITEM_SPEC,"
							+ " EFA.MODEL_NUMBER,"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " EFA.ASSETS_LOCATION_CODE,"
							+ " EO.WORKORDER_OBJECT_LOCATION,"
							+ " EFA.ASSETS_LOCATION,"
							+ " AME.USER_NAME,"
							+ " EFA.ASSIGNED_TO_NAME,"
							+ " ACCV1.COST_CENTER_NAME COST_NAME_AMS,"
							+ " ACCV2.COST_CENTER_NAME COST_NAME_MIS"
							+ " FROM"
							+ " AMS_COST_CENTER_V   ACCV1,"
							+ " AMS_COST_CENTER_V   ACCV2,"
							+ " AMS_MIS_EMPLOYEE    AME,"
							+ " ETS_ITEM_MATCH_TD      EIM,"
							+ " ETS_FA_ASSETS_TD       EFA,"
							+ " ETS_ITEM_INFO       EII,"
							+ " AMS_OBJECT_ADDRESS  AOA,"
							+ " ETS_OBJECT          EO,"
							+ " AMS_COST_DEPT_MATCH ACDM,"
							+ " ETS_SYSTEM_ITEM     ESI"
							+ " WHERE"
							+ " EIM.SYSTEMID = EII.SYSTEMID"
							+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
							+ " AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)"
							+ " AND EII.RESPONSIBILITY_DEPT *= ACDM.DEPT_CODE"
							+ " AND ACDM.COST_CENTER_CODE *= ACCV1.COST_CENTER_CODE"
							+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
							+ " AND EIM.ASSET_ID = EFA.ASSET_ID"
							+ " AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) *= ACCV2.COST_CENTER_CODE"
							+ " AND ((" + SyBaseSQLUtil.isNull() + " OR ACCV1.COST_CENTER_CODE LIKE ?)"
							+ " OR (" + SyBaseSQLUtil.isNull() + " OR ACCV1.COST_CENTER_NAME LIKE ?)"
							+ " OR (" + SyBaseSQLUtil.isNull() + " OR ACCV2.COST_CENTER_CODE LIKE ?)"
							+ " OR (" + SyBaseSQLUtil.isNull() + " OR ACCV2.COST_CENTER_NAME LIKE ?))"
							+ " AND (ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) OR (" + SyBaseSQLUtil.isNull() + " OR ESI.ITEM_SPEC LIKE ?))"
							+ " AND (EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE) OR EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER) OR (" + SyBaseSQLUtil.isNull() + " OR EFA.MIS_TAG_NUMBER LIKE ?))"
							+ " AND ((EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)) OR"
							+ " (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)))"
							+ " AND EO.ORGANIZATION_ID = ?"
							+ " ORDER BY"
							+ " EO.WORKORDER_OBJECT_CODE DESC";

			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());

			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getBarcode());

			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(dto.getWorkorderObjectName());
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sqlModel;
	}

}
