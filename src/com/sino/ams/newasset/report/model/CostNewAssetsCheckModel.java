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

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-25
 * Time: 16:12:05
 * To change this template use File | Settings | File Templates.
 */
public class CostNewAssetsCheckModel extends AMSSQLProducer {

	public CostNewAssetsCheckModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
        try {
			String sqlStr = "SELECT ACCT.COST_CENTER_CODE,\n" +
                    "       ACCT.COST_CENTER_NAME,\n" +
                    "       ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
                    "       ISNULL(SCANED_V.SCANED_COUNT, 0) SCANED_COUNT,\n" +
                    "       ISNULL(TOTAL_V.TOTAL_COUNT, 0)-ISNULL(SCANED_V.SCANED_COUNT, 0) NOT_SCANED_COUNT,\n" +
                    "       ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) IDENTICAL_COUNT,\n" +
                    "       (CASE CHARINDEX(ROUND(100 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) /\n" +
                    "                     ISNULL(SCANED_V.SCANED_COUNT, 1),\n" +
                    "                     2) || '%',\n" +
                    "               '.')\n" +
                    "         WHEN 1 THEN\n" +
                    "          '0' || ROUND(100 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) /\n" +
                    "                       ISNULL(SCANED_V.SCANED_COUNT, 1),\n" +
                    "                       2) || '%'\n" +
                    "         ELSE\n" +
                    "          ROUND(100 * ISNULL(IDENTICAL_V.IDENTICAL_COUNT, 0) /\n" +
                    "                ISNULL(SCANED_V.SCANED_COUNT, 1),\n" +
                    "                2) || '%'\n" +
                    "       END) IDENTICAL_RATE,\n" +
                    "       (CASE CHARINDEX(ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) /\n" +
                    "                     ISNULL(TOTAL_V.TOTAL_COUNT, 1),\n" +
                    "                     2) || '%',\n" +
                    "               '.')\n" +
                    "         WHEN 1 THEN\n" +
                    "          '0' || ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) /\n" +
                    "                       ISNULL(TOTAL_V.TOTAL_COUNT, 1),\n" +
                    "                       2) || '%'\n" +
                    "         ELSE\n" +
                    "          ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) /\n" +
                    "                ISNULL(TOTAL_V.TOTAL_COUNT, 1),\n" +
                    "                2) || '%'\n" +
                    "       END) SCANED_RATE\n" +
                    "FROM   AMS_COST_CENTER_TMP ACCT,\n" +
                    "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                    "               COUNT(1) TOTAL_COUNT\n" +
                    "        FROM   ETS_FA_ASSETS EFA\n" +
                    "        WHERE  EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "        AND    ( " + SyBaseSQLUtil.isNull() + "  OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?)\n" +
                    "        AND    EFA.ORGANIZATION_ID = ?\n" +
                    "        AND    EXISTS (SELECT NULL\n" +
                    "                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                WHERE  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                AND    AICM.NEED_SCAN = 'Y')\n" +
                    "        GROUP  BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) TOTAL_V,\n" +
                    "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                    "               COUNT(1) SCANED_COUNT\n" +
                    "        FROM   ETS_FA_ASSETS EFA\n" +
                    "        WHERE  EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "        AND    (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "        AND    EXISTS\n" +
                    "         (SELECT 1\n" +
                    "                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                WHERE  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                AND    AICM.NEED_SCAN = 'Y')\n" +
                    "        AND    EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    ( " + SyBaseSQLUtil.isNull() + "  OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?)\n" +
                    "        AND    EFA.ORGANIZATION_ID = ?\n" +
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
                    "                WHERE  EFA.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                    "                AND    EFA.TAG_NUMBER = TMP_1.BARCODE)\n" +
                    "        AND    EXISTS\n" +
                    "         (SELECT 1\n" +
                    "                FROM   ETS_ITEM_INFO EII, ETS_OBJECT EO, AMS_OBJECT_ADDRESS AOA\n" +
                    "                WHERE  EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "                AND    EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "                AND    EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "                AND    AOA.BOX_NO = '0000'\n" +
                    "                AND    AOA.NET_UNIT = '0000'\n" +
                    "                AND    (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "        GROUP  BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) SCANED_V,\n" +
                    "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CENTER_CODE,\n" +
                    "               COUNT(1) IDENTICAL_COUNT\n" +
                    "        FROM   ETS_FA_ASSETS EFA\n" +
                    "        WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "        AND    EXISTS (SELECT 1\n" +
                    "                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                WHERE  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                AND    AICM.NEED_SCAN = 'Y')\n" +
                    "        AND    EXISTS\n" +
                    "         (SELECT 1\n" +
                    "                FROM   AMS_ASSETS_CHECK_HEADER AACH, AMS_ASSETS_CHECK_LINE AACL\n" +
                    "                WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
                    "                AND    AACL.BARCODE = EFA.TAG_NUMBER\n" +
                    "                AND    AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                AND    EFA.ORGANIZATION_ID = AACH.ORGANIZATION_ID)\n" +
                    "        AND    EXISTS\n" +
                    "         (SELECT 1\n" +
                    "                FROM   ETS_SYSTEM_ITEM    ESI,\n" +
                    "                       ETS_OBJECT         EO,\n" +
                    "                       AMS_OBJECT_ADDRESS AOA,\n" +
                    "                       AMS_MIS_EMPLOYEE   AME,\n" +
                    "                       ETS_ITEM_INFO      EII\n" +
                    "                WHERE  EFA.TAG_NUMBER = EII.BARCODE\n" +
                    "                AND    EFA.ASSETS_DESCRIPTION = ESI.ITEM_NAME\n" +
                    "                AND    ISNULL(EFA.MODEL_NUMBER, '1') = ISNULL(ESI.ITEM_SPEC, '1')\n" +
                    "                AND    ((EFA.ASSIGNED_TO_NUMBE " + SyBaseSQLUtil.isNull() + "  AND\n" +
                    "                      EII.RESPONSIBILITY_USE " + SyBaseSQLUtil.isNull() + " ) OR\n" +
                    "                      (EFA.ASSIGNED_TO_NUMBER = AME.EMPLOYEE_NUMBER))\n" +
                    "                AND    EO.WORKORDER_OBJECT_CODE = EFA.ASSETS_LOCATION_CODE\n" +
                    "                AND    EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "                AND    EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "                AND    EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "                AND    EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                    "                AND    AOA.BOX_NO = '0000'\n" +
                    "                AND    AOA.NET_UNIT = '0000'\n" +
                    "                AND    (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "        AND    EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    ( " + SyBaseSQLUtil.isNull() + "  OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?)\n" +
                    "        AND    EFA.ORGANIZATION_ID = ?\n" +
                    "        GROUP  BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) IDENTICAL_V\n" +
                    "WHERE  ACCT.COST_CENTER_CODE *= TOTAL_V.COST_CENTER_CODE\n" +
                    "AND    ACCT.COST_CENTER_CODE *= SCANED_V.COST_CENTER_CODE\n" +
                    "AND    ACCT.COST_CENTER_CODE *= IDENTICAL_V.COST_CENTER_CODE\n" +
                    "AND    (ISNULL(TOTAL_V.TOTAL_COUNT, 0) > 0 OR ISNULL(SCANED_V.SCANED_COUNT, 0) > 0)";
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(userAccount.getOrganizationId());
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(userAccount.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
