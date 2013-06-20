package com.sino.ams.prematch.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.prematch.dto.AmsPaMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

public class PaMatchAMSModel extends AMSSQLProducer {

	public PaMatchAMSModel(SfUserDTO userAccount, AmsPaMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取待匹配的EAM系统实物
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " EII.SYSTEMID,"
						+ " EII.BARCODE,"
						+ " ESI.ITEM_CATEGORY,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EPPA.SEGMENT1 PROJECT_NUMBER,"
						+ " EPPA.NAME PROJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " AME.USER_NAME,"
						+ " AME.EMPLOYEE_NUMBER,"
						+ " EII.START_DATE"
						+ " FROM"
						+ " ETS_ITEM_INFO       EII,"
						+ " ETS_SYSTEM_ITEM     ESI,"
						+ " AMS_OBJECT_ADDRESS  AOA,"
						+ " ETS_OBJECT          EO,"
						+ " AMS_MIS_EMPLOYEE    AME,"
						+ " ETS_PA_PROJECTS_ALL EPPA"
						+ " WHERE"
						+ " ESI.ITEM_CODE = EII.ITEM_CODE"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EII.PROJECTID = EPPA.PROJECT_ID"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND EII.ORGANIZATION_ID = ?"
						+ " AND EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE)"
						+ " AND EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME)"
						+ " AND EPPA.SEGMENT1 LIKE dbo.NVL(?, EPPA.SEGMENT1)"
						+ " AND EPPA.NAME LIKE dbo.NVL(?, EPPA.NAME)"
						+ " AND EII.PARENT_BARCODE " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND  " + SyBaseSQLUtil.isNotNull("EII.PROJECTID") + " "
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_PA_MATCH APM"
						+ " WHERE"
						+ " EII.SYSTEMID = APM.SYSTEM_ID)"
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_ITEM_MATCH EIM"
						+ " WHERE"
						+ " EII.SYSTEMID = EIM.SYSTEMID)";
		AmsPaMatchDTO dto = (AmsPaMatchDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getWorkorderObjectCode());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getProjectNumber());
		sqlArgs.add(dto.getProjectName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
