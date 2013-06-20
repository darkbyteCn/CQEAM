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

public class NewAssetsCheckModel extends AMSSQLProducer {

	public NewAssetsCheckModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr = "SELECT TOTAL_V.ORGANIZATION_ID,\n" +
                    "       TOTAL_V.COMPANY,\n" +
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
                    "FROM   (SELECT EOCM.ORGANIZATION_ID, EOCM.COMPANY, COUNT(1) TOTAL_COUNT\n" +
                    "        FROM   ETS_FA_ASSETS EFA, ETS_OU_CITY_MAP EOCM\n" +
                    "        WHERE  EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "        AND    EFA.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE\n" +
                    "        AND    EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "        AND    (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "        AND    EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                WHERE  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                AND    AICM.NEED_SCAN = 'Y')\n" +
                    "        AND    EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n" +
                    "        GROUP  BY EOCM.ORGANIZATION_ID, EOCM.COMPANY) TOTAL_V,\n" +
                    "       (SELECT EII.ORGANIZATION_ID, COUNT(1) SCANED_COUNT\n" +
                    "        FROM   ETS_ITEM_INFO EII\n" +
                    "        WHERE  EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                FROM   ETS_FA_ASSETS EFA, AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                WHERE  EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "                AND    EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "                AND    EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "                AND    EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                AND    AICM.NEED_SCAN = 'Y'\n" +
                    "                AND    (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "                AND    EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "                AND    EFA.ASSETS_CREATE_DATE < ISNULL(?, EFA.ASSETS_CREATE_DATE))\n" +
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
                    "        AND    EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                FROM   AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    "                WHERE  EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "                AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                AND    (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "        AND    EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                    "        GROUP  BY EII.ORGANIZATION_ID) SCANED_V,\n" +
                    "       (SELECT EII.ORGANIZATION_ID, COUNT(1) IDENTICAL_COUNT\n" +
                    "        FROM   ETS_SYSTEM_ITEM    ESI,\n" +
                    "               ETS_OBJECT         EO,\n" +
                    "               AMS_OBJECT_ADDRESS AOA,\n" +
                    "               AMS_MIS_EMPLOYEE   AME,\n" +
                    "               ETS_ITEM_INFO      EII\n" +
                    "        WHERE  EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                    "        AND    EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "        AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "        AND    EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                    "        AND    EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                FROM   ETS_FA_ASSETS EFA, AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                WHERE  EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "                AND    EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "                AND    EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "                AND    EFA.ASSETS_DESCRIPTION = ESI.ITEM_NAME\n" +
                    "                AND    ISNULL(EFA.MODEL_NUMBER, '1') = ISNULL(ESI.ITEM_SPEC, '1')\n" +
                    "                AND    ((EFA.ASSIGNED_TO_NUMBE " + SyBaseSQLUtil.isNull() + "  AND\n" +
                    "                      EII.RESPONSIBILITY_USE " + SyBaseSQLUtil.isNull() + " ) OR\n" +
                    "                      (EFA.ASSIGNED_TO_NUMBER = AME.EMPLOYEE_NUMBER))\n" +
                    "                AND    EO.WORKORDER_OBJECT_CODE = EFA.ASSETS_LOCATION_CODE\n" +
                    "                AND    EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                AND    AICM.NEED_SCAN = 'Y'\n" +
                    "                AND    (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                    "                AND    EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "                AND    EFA.ASSETS_CREATE_DATE < ISNULL(?, EFA.ASSETS_CREATE_DATE))\n" +
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
                    "        AND    EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                    "        AND    (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80)\n" +
                    "        GROUP  BY EII.ORGANIZATION_ID) IDENTICAL_V\n" +
                    "WHERE  TOTAL_V.ORGANIZATION_ID *= SCANED_V.ORGANIZATION_ID\n" +
                    "AND    TOTAL_V.ORGANIZATION_ID *= IDENTICAL_V.ORGANIZATION_ID";

			List sqlArgs = new ArrayList();

			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
