package com.sino.ams.prematch.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.prematch.dto.AmsPaMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

public class AutoMatchModel extends AMSSQLProducer {

	public AutoMatchModel(SfUserDTO userAccount, AmsPaMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取待匹配的EAM系统实物
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		AmsPaMatchDTO dto = (AmsPaMatchDTO) dtoParameter;
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT"
						+ " EII.SYSTEMID,"
						+ " EII.BARCODE,"
						+ " APA.TAG_NUMBER,"
						+ " ESI.ITEM_NAME,"
						+ " APA.ASSETS_DESCRIPTION,"
						+ " ESI.ITEM_SPEC,"
						+ " APA.MODEL_NUMBER,"
						+ " EPPA.SEGMENT1 PROJECT_NUMBER_AMS,"
						+ " APA.PROJECT_NUMBER,"
						+ " EPPA.NAME PROJECT_NAME_AMS,"
						+ " APA.PROJECT_NAME,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " APA.ASSETS_LOCATION_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " APA.ASSETS_LOCATION,"
						+ " AME.EMPLOYEE_NUMBER,"
						+ " APA.ASSIGNED_TO_NUMBER,"
						+ " AME.USER_NAME,"
						+ " APA.ASSIGNED_TO_NAME,"
						+ " EII.START_DATE,"
						+ " APA.DATE_PLACED_IN_SERVICE"
						+ " FROM"
						+ " ETS_ITEM_INFO       EII,"
						+ " ETS_SYSTEM_ITEM     ESI,"
						+ " AMS_OBJECT_ADDRESS  AOA,"
						+ " ETS_OBJECT          EO,"
						+ " AMS_MIS_EMPLOYEE    AME,"
						+ " ETS_PA_PROJECTS_ALL EPPA,"
						+ " AMS_PA_ASSETS       APA"
						+ " WHERE"
						+ " ESI.ITEM_CODE = EII.ITEM_CODE"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EII.PROJECTID = EPPA.PROJECT_ID"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND EII.BARCODE = APA.TAG_NUMBER"
						+ " AND EII.ORGANIZATION_ID = APA.ORGANIZATION_ID"
						+ " AND EII.ORGANIZATION_ID = ?"
						+ " AND EII.PARENT_BARCODE " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND  " + SyBaseSQLUtil.isNotNull("EII.PROJECTID") + " "
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_PA_MATCH APM"
						+ " WHERE"
						+ " APA.TAG_NUMBER = APM.TAG_NUMBER"
						+ " OR EII.SYSTEMID = APM.SYSTEM_ID)"
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_ITEM_MATCH EIM"
						+ " WHERE"
						+ " EII.SYSTEMID = EIM.SYSTEMID)"
						+ " AND APA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, APA.ASSETS_LOCATION_CODE)"
						+ " AND APA.ASSETS_LOCATION LIKE dbo.NVL(?, APA.ASSETS_LOCATION)"
						+ " AND APA.PROJECT_NUMBER LIKE dbo.NVL(?, APA.PROJECT_NUMBER)"
						+ " AND APA.PROJECT_NAME LIKE dbo.NVL(?, APA.PROJECT_NAME)"

						+ " UNION"

						+ " SELECT"
						+ " TO_NUMBER(NULL) SYSTEMID,"
						+ " APA.TAG_NUMBER BARCODE,"
						+ " APA.TAG_NUMBER,"
						+ " APA.ASSETS_DESCRIPTION ITEM_NAME,"
						+ " APA.ASSETS_DESCRIPTION,"
						+ " APA.MODEL_NUMBER ITEM_SPEC,"
						+ " APA.MODEL_NUMBER,"
						+ " APA.PROJECT_NUMBER PROJECT_NUMBER_AMS,"
						+ " APA.PROJECT_NUMBER,"
						+ " APA.PROJECT_NAME PROJECT_NAME_AMS,"
						+ " APA.PROJECT_NAME,"
						+ " APA.ASSETS_LOCATION_CODE WORKORDER_OBJECT_CODE,"
						+ " APA.ASSETS_LOCATION_CODE,"
						+ " APA.ASSETS_LOCATION WORKORDER_OBJECT_NAME,"
						+ " APA.ASSETS_LOCATION,"
						+ " APA.ASSIGNED_TO_NUMBER EMPLOYEE_NUMBER,"
						+ " APA.ASSIGNED_TO_NUMBER,"
						+ " APA.ASSIGNED_TO_NAME USER_NAME,"
						+ " APA.ASSIGNED_TO_NAME,"
						+ " APA.DATE_PLACED_IN_SERVICE START_DATE,"
						+ " APA.DATE_PLACED_IN_SERVICE"
						+ " FROM"
						+ " AMS_PA_ASSETS         APA,"
						+ " AMS_ITEM_CATEGORY_MAP AICM"
						+ " WHERE"
						+ " APA.SEGMENT2 = AICM.FA_CATEGORY_CODE"
						+ " AND AICM.NEED_SCAN = 'N'"
						+ " AND APA.ORGANIZATION_ID = ?"

						+ " AND NOT EXISTS ("//不存在于ETS_ITEM_INFO表中
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_ITEM_INFO EII"
						+ " WHERE"
						+ " APA.TAG_NUMBER = EII.BARCODE)"

						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_PA_MATCH APM"
						+ " WHERE"
						+ " APA.TAG_NUMBER = APM.TAG_NUMBER)"

						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_ITEM_MATCH EIM,"
						+ " ETS_FA_ASSETS  EFA"
						+ " WHERE"
						+ " EIM.ASSET_ID = EFA.ASSET_ID"
						+ " AND EFA.TAG_NUMBER = APA.TAG_NUMBER)"
						+ " AND APA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, APA.ASSETS_LOCATION_CODE)"
						+ " AND APA.ASSETS_LOCATION LIKE dbo.NVL(?, APA.ASSETS_LOCATION)"
						+ " AND APA.PROJECT_NUMBER LIKE dbo.NVL(?, APA.PROJECT_NUMBER)"
						+ " AND APA.PROJECT_NAME LIKE dbo.NVL(?, APA.PROJECT_NAME)";

		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getAssetsLocationCode());
		sqlArgs.add(dto.getAssetsLocation());
		sqlArgs.add(dto.getProjectNumber());
		sqlArgs.add(dto.getProjectName());

		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getAssetsLocationCode());
		sqlArgs.add(dto.getAssetsLocation());
		sqlArgs.add(dto.getProjectNumber());
		sqlArgs.add(dto.getProjectName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
