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

public class NewAssetsIdenticalModel extends AMSSQLProducer {

	public NewAssetsIdenticalModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：构造扫描资产和新增资产条码相符的SQL
	 * @return SQLModel
	 * @throws SQLModelException
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
                    "FROM   ETS_FA_ASSETS EFA, ETS_OU_CITY_MAP EOCM\n" +
                    "WHERE  EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "AND    EFA.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE\n" +
                    "AND    EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "AND    EFA.ASSETS_CREATE_DATE < ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "AND    (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "AND    EXISTS (SELECT NULL\n" +
                    "        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "        WHERE  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "        AND    AICM.NEED_SCAN = 'Y')\n" +
                    "AND    EXISTS\n" +
                    " (SELECT NULL\n" +
                    "        FROM   ETS_SYSTEM_ITEM    ESI,\n" +
                    "               AMS_MIS_EMPLOYEE   AME,\n" +
                    "               AMS_OBJECT_ADDRESS AOA,\n" +
                    "               ETS_OBJECT         EO,\n" +
                    "               ETS_ITEM_INFO      EII\n" +
                    "        WHERE  EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "        AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "        AND    EII.ITEM_CODE *= ESI.ITEM_CODE\n" +
                    "        AND    EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID\n" +
                    "        AND    (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80)\n" +
                    "        AND    EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                FROM   (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "                        FROM   AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                               AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                        WHERE  AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                        AND    AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                        AND    ((AACL.ARCHIVE_STATUS = '0' AND\n" +
                    "                              AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                              (AACL.ARCHIVE_STATUS = '1' AND\n" +
                    "                              AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                        AND    AACH.ORGANIZATION_ID = ISNULL(?, AACH.ORGANIZATION_ID)) TMP_1\n" +
                    "                WHERE  EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                    "                AND    EII.BARCODE = TMP_1.BARCODE)\n" +
                    "        AND    EII.ORGANIZATION_ID = ?\n" +
                    "        AND    EFA.TAG_NUMBER = EII.BARCODE\n" +
                    "        AND    EFA.ASSETS_DESCRIPTION = ESI.ITEM_NAME\n" +
                    "        AND    ISNULL(EFA.MODEL_NUMBER, '1') = ISNULL(ESI.ITEM_SPEC, '1')\n" +
                    "        AND    EFA.ASSETS_LOCATION_CODE = EO.WORKORDER_OBJECT_CODE\n" +
                    "        AND    EFA.ASSIGNED_TO_NUMBER = AME.EMPLOYEE_NUMBER)\n" +
                    "AND    EFA.ORGANIZATION_ID = ?\n" +
                    "AND    ( " + SyBaseSQLUtil.isNull() + "  OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?)\n" +
                    "AND    (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE) OR\n" +
                    "      EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION))";

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());
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
