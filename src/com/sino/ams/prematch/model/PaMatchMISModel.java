package com.sino.ams.prematch.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.prematch.dto.AmsPaMatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

public class PaMatchMISModel extends AMSSQLProducer {

	public PaMatchMISModel(SfUserDTO userAccount, AmsPaMatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取待匹配的MIS转资清单
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " APA.TAG_NUMBER,"
						+ " APA.ASSETS_DESCRIPTION,"
						+ " APA.MODEL_NUMBER,"
						+ " APA.PROJECT_NUMBER,"
						+ " APA.PROJECT_NAME,"
						+ " APA.ASSETS_LOCATION_CODE,"
						+ " APA.ASSETS_LOCATION,"
						+ " APA.ASSIGNED_TO_NUMBER,"
						+ " APA.ASSIGNED_TO_NAME,"
						+ " APA.DATE_PLACED_IN_SERVICE"
						+ " FROM"
						+ " AMS_PA_ASSETS APA"
						+ " WHERE"
						+ " APA.ORGANIZATION_ID = ?"
						+ " AND APA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, APA.ASSETS_LOCATION_CODE)"
						+ " AND APA.ASSETS_LOCATION LIKE dbo.NVL(?, APA.ASSETS_LOCATION)"
						+ " AND APA.PROJECT_NUMBER LIKE dbo.NVL(?, APA.PROJECT_NUMBER)"
						+ " AND APA.PROJECT_NAME LIKE dbo.NVL(?, APA.PROJECT_NAME)"
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
						+ " ETS_FA_ASSETS EFA"
						+ " WHERE"
						+ " APA.TAG_NUMBER = EFA.TAG_NUMBER)";

		List sqlArgs = new ArrayList();
		AmsPaMatchDTO dto = (AmsPaMatchDTO)dtoParameter;
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
