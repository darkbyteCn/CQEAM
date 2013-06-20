package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class NewAssetsReportModel extends AMSSQLProducer {

	public NewAssetsReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造新增资产查询SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " EFA.TAG_NUMBER,"
							+ " EFA.ASSET_NUMBER,"
							+ " EFA.FA_CATEGORY1,"
							+ " EFA.FA_CATEGORY2,"
							+ " EFA.ASSETS_DESCRIPTION,"
							+ " EFA.MODEL_NUMBER,"
							+ " EFA.UNIT_OF_MEASURE,"
							+ " EFA.CURRENT_UNITS,"
							+ " EFA.ASSETS_CREATE_DATE,"
							+ " EFA.DATE_PLACED_IN_SERVICE,"
							+ " EFA.LIFE_IN_YEARS,"
							+ " EFA.ORIGINAL_COST,"
							+ " EFA.COST,"
							+ " EFA.DEPRN_COST,"
							+ " EFA.IMPAIR_RESERVE,"
							+ " EFA.SCRAP_VALUE,"
							+ " EFA.DEPRECIATION_ACCOUNT,"
							+ " EFA.ASSIGNED_TO_NAME,"
							+ " EFA.ASSIGNED_TO_NUMBER,"
							+ " EFA.ASSETS_LOCATION,"
							+ " EFA.ASSETS_LOCATION_CODE,"
							+ " EFA.PROJECT_NAME,"
							+ " EFA.MIS_PROJECT_NUMBER"
							+ " FROM"
							+ " ETS_FA_ASSETS   EFA,"
							+ " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EFA.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE"
							+ " AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
							+ " AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
							+ " AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)"
							+ " AND EXISTS ("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " AMS_ITEM_CATEGORY_MAP AICM"
							+ " WHERE"
							+ " EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE"
							+ " AND AICM.NEED_SCAN = 'Y')"
							+ " AND EFA.ORGANIZATION_ID = ?"
                            + " AND ( " + SyBaseSQLUtil.isNull() + "  OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?)"
                            + " AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "                EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION))";
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCheckLocationName());
            sqlArgs.add(dto.getCheckLocationName());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
