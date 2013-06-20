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

public class NewAssetsMonitorIdenticalModel extends AMSSQLProducer {

	public NewAssetsMonitorIdenticalModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造扫描资产和新增资产条码相符的SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                    "       EOCM.COMPANY,\n" +
                    "       EFA.TAG_NUMBER,\n" +
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
                    "       EFA.MIS_PROJECT_NUMBER,\n" +
                    "       (SELECT AMD.DEPT_NAME\n" +
                    "        FROM   AMS_MIS_DEPT AMD, ETS_ITEM_INFO EII\n" +
                    "        WHERE  EFA.TAG_NUMBER = EII.BARCODE\n" +
                    "        AND    EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "        AND    EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "        AND    EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE) DEPT_NAME\n" +
                    " FROM ETS_ITEM_INFO EII, ETS_FA_ASSETS EFA, ETS_OU_CITY_MAP EOCM\n" +
                    " WHERE EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "   AND EFA.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE\n" +
                    "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "   AND EFA.TAG_NUMBER = EII.BARCODE\n" +
                    "   AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "                          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                               AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                           AND ((AACL.ARCHIVE_STATUS = '0' AND\n" +
                    "                               AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                               (AACL.ARCHIVE_STATUS = '1' AND\n" +
                    "                               AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                           AND AACH.ARCHIVED_DATE >=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   AACH.ARCHIVED_DATE)\n" +
                    "                           AND AACH.ARCHIVED_DATE <=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   AACH.ARCHIVED_DATE)\n" +
                    "                           AND AACH.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, AACH.ORGANIZATION_ID)\n" +
                    "                        UNION\n" +
                    "                        SELECT DISTINCT EW.ORGANIZATION_ID, EWD.BARCODE\n" +
                    "                          FROM ETS_WORKORDER       EW,\n" +
                    "                               ETS_WORKORDER_BATCH EWB,\n" +
                    "                               ETS_WORKORDER_DTL   EWD\n" +
                    "                         WHERE EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                    "                           AND EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
                    "                           AND EWD.ITEM_STATUS <> 6\n" +
                    "                           AND EW.WORKORDER_FLAG IN (13, 14)\n" +
                    "                           AND EW.START_DATE >=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   EW.START_DATE)\n" +
                    "                           AND EW.START_DATE <=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   EW.START_DATE)\n" +
                    "                           AND EW.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, EW.ORGANIZATION_ID)) TMP_1\n" +
                    "         WHERE EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                    "           AND EII.BARCODE = TMP_1.BARCODE)\n" +
                    "   AND EXISTS\n" +
                    " (SELECT NULL\n" +
                    "          FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    "         WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "           AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "           AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "  AND EII.ORGANIZATION_ID = ?\n" +
                    "  AND ( " + SyBaseSQLUtil.isNull() + "  OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?)\n" +
                    "  AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "      EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION))" +
                    " ORDER BY EFA.TAG_NUMBER";

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());

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