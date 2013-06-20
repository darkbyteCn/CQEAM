package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-25
 * Time: 21:08:15
 * To change this template use File | Settings | File Templates.
 */
public class NewAssetsReportDeptModel extends AMSSQLProducer {

	public NewAssetsReportDeptModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造新增资产查询SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        try { 
            String sqlStr = "SELECT EFA.TAG_NUMBER,\n" +
                    "       EFA.ASSET_NUMBER,\n" +
                    "       EFA.FA_CATEGORY1,\n" +
                    "       EFA.FA_CATEGORY2,\n" +
                    "       EFA.ASSETS_DESCRIPTION,\n" +
                    "       EFA.MODEL_NUMBER,\n" +
                    "       EFA.UNIT_OF_MEASURE,\n" +
                    "       EFA.CURRENT_UNITS,\n" +
                    "       EFA.ASSETS_CREATE_DATE,\n" +
                    "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                    "       EFA.LIFE_IN_YEARS,\n" +
                    "       EFA.ORIGINAL_COST,\n" +
                    "       EFA.COST,\n" +
                    "       EFA.DEPRN_COST,\n" +
                    "       EFA.IMPAIR_RESERVE,\n" +
                    "       EFA.SCRAP_VALUE,\n" +
                    "       EFA.DEPRECIATION_ACCOUNT,\n" +
                    "       EFA.ASSIGNED_TO_NAME,\n" +
                    "       EFA.ASSIGNED_TO_NUMBER,\n" +
                    "       EFA.ASSETS_LOCATION,\n" +
                    "       EFA.ASSETS_LOCATION_CODE,\n" +
                    "       EFA.PROJECT_NAME,\n" +
                    "       EFA.MIS_PROJECT_NUMBER\n" +
                    "FROM   ETS_FA_ASSETS       EFA,\n" +
                    "       AMS_COST_CENTER_TMP ACCT\n" +
                    "WHERE  ACCT.COST_CENTER_CODE = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)\n" +
                    "       AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "       AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "       AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "       AND EXISTS\n" +
                    " (SELECT NULL\n" +
                    "        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "        WHERE  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "               AND AICM.NEED_SCAN = 'Y')\n" +
                    "       AND ACCT.COST_CENTER_CODE = ?\n" +
                    "       AND EFA.ORGANIZATION_ID = ?\n" +
                    "       AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "       EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION))";
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getCostCode());
            sqlArgs.add(userAccount.getOrganizationId());
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
