package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class CheckReportModel extends AMSSQLProducer {

	public CheckReportModel(SfUserDTO userAccount, AmsAssetsCheckBatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：获取盘点统计报表SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
							"       EOCM.COMPANY,\n" +
							"       ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
							"       ISNULL(SCANED_V.SCANED_COUNT, 0) SCANED_COUNT,\n" +
							"       (ISNULL(TOTAL_V.TOTAL_COUNT, 0) - ISNULL(SCANED_V.SCANED_COUNT, 0)) NOT_SCANED_COUNT,\n" +
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
							"       END) SCAN_RATE\n" +
							"FROM   ETS_OU_CITY_MAP EOCM,\n" +
							"       (SELECT EFA.ORGANIZATION_ID,\n" +
							"               COUNT(EFA.ASSET_ID) TOTAL_COUNT\n" +
							"        FROM   ETS_FA_ASSETS EFA\n" +
							"        WHERE  EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
							"               AND NOT EXISTS (SELECT NULL\n" +
							"                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
							"                WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
							"                       AND AICM.NEED_SCAN = 'N')\n" +
							"               AND EXISTS\n" +
							"         (SELECT NULL\n" +
							"                FROM   AMS_COST_CENTER_TMP ACCT\n" +
							"                WHERE  EFA.ORGANIZATION_ID = ACCT.ORGANIZATION_ID\n" +
							"                       AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
							"                       ACCT.COST_CENTER_CODE)\n" +
							"               AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
							"        GROUP  BY EFA.ORGANIZATION_ID) TOTAL_V,\n" +
							"       (SELECT AACL3.SCAN_ORGANIZATION_ID ORGANIZATION_ID,\n" +
							"               COUNT(AACL3.BARCODE) SCANED_COUNT\n" +
							"        FROM   AMS_ASSETS_CHECK_LINE AACL3,\n" +
							"               AMS_COST_DEPT_MATCH   ACDM,\n" +
							"               AMS_COST_CENTER_TMP   ACCT\n" +
							"        WHERE  AACL3.SCAN_RESPONSIBILITY_DEPT = ACDM.DEPT_CODE\n" +
							"               AND ACDM.COST_CENTER_CODE = ACCT.COST_CENTER_CODE\n" +
							"               AND AACL3.SCAN_ORGANIZATION_ID = ACCT.ORGANIZATION_ID\n" +
							"               AND EXISTS\n" +
							"         (SELECT NULL\n" +
							"                FROM   (SELECT AACL.ROWID\n" +
							"                        FROM   AMS_ASSETS_CHECK_LINE   AACL,\n" +
							"                               AMS_ASSETS_CHECK_HEADER AACH\n" +
							"                        WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
							"                               AND AACH.HEADER_ID =\n" +
							"                               (SELECT MAX(AACH2.HEADER_ID)\n" +
							"                                    FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
							"                                           AMS_ASSETS_CHECK_HEADER AACH2\n" +
							"                                    WHERE  AACL.BARCODE = AACL2.BARCODE\n" +
							"                                           AND AACL2.SCAN_STATUS = 'Y'\n" +
							"                                           AND AACL2.HEADER_ID = AACH2.HEADER_ID\n" +
							"                                           AND AACH2.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED')\n" +
							"                                           AND ((AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE) AND AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
							"                                           (AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE) AND AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE))))) TMP_1\n" +
							"                WHERE  AACL3.ROWID = TMP_1.ROWID)\n" +
							"                       AND ACCT.ORGANIZATION_ID = ISNULL(?, ACCT.ORGANIZATION_ID)\n" +
							"        GROUP  BY AACL3.SCAN_ORGANIZATION_ID) SCANED_V\n" +
							"WHERE  EOCM.ORGANIZATION_ID *= TOTAL_V.ORGANIZATION_ID\n" +
							"       AND EOCM.ORGANIZATION_ID *= SCANED_V.ORGANIZATION_ID\n" +
							"       AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)";

			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
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

	/**
	 * 功能：获取特定OU下的成本中心盘点情况
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getCostCheckModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr =
				"SELECT COST_CENTER_CODE,\n" +
				"       COST_CENTER_NAME,\n" +
				"       ISNULL(TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
				"       ISNULL(SCANED_COUNT, 0) SCANED_COUNT,\n" +
				"       ISNULL(TOTAL_COUNT, 0) - ISNULL(SCANED_COUNT, 0) NOT_SCANED_COUNT,\n" +
				"       SCAN_RATE\n" +
				"FROM   (SELECT ACCT.COST_CENTER_CODE,\n" +
				"               ACCT.COST_CENTER_NAME,\n" +
				"               TOTAL_COUNT,\n" +
				"               ISNULL(SCANED_V.SCANED_COUNT, 0) SCANED_COUNT,\n" +
				"               (CASE CHARINDEX(ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) /\n" +
				"                             ISNULL(TOTAL_V.TOTAL_COUNT, 1),\n" +
				"                             2) || '%',\n" +
				"                       '.')\n" +
				"                 WHEN 1 THEN\n" +
				"                  '0' || ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) /\n" +
				"                               ISNULL(TOTAL_V.TOTAL_COUNT, 1),\n" +
				"                               2) || '%'\n" +
				"                 ELSE\n" +
				"                  ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) /\n" +
				"                        ISNULL(TOTAL_V.TOTAL_COUNT, 1),\n" +
				"                        2) || '%'\n" +
				"               END) SCAN_RATE\n" +
				"        FROM   AMS_COST_CENTER_TMP ACCT,\n" +
				"               (SELECT ACCT.ACTUAL_COST_CENTER COST_CENTER_CODE,\n" +
				"                       COUNT(EFA.ASSET_ID) TOTAL_COUNT\n" +
				"                FROM   AMS_COST_CENTER_TMP   ACCT,\n" +
				"                       ETS_FA_ASSETS         EFA,\n" +
				"                       AMS_ITEM_CATEGORY_MAP AICM\n" +
				"                WHERE  SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
				"                       ACCT.COST_CENTER_CODE\n" +
				"                       AND EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
				"                       AND AICM.NEED_SCAN = 'Y'\n" +
				"                       AND EFA.ORGANIZATION_ID = ?\n" +
				"                       AND ACCT.ORGANIZATION_ID = ?\n" +
				"                GROUP  BY ACCT.ACTUAL_COST_CENTER) TOTAL_V,\n" +
				"               (SELECT ACCT.ACTUAL_COST_CENTER COST_CENTER_CODE,\n" +
				"                       COUNT(AACL3.BARCODE) SCANED_COUNT\n" +
				"                FROM   AMS_ASSETS_CHECK_LINE AACL3,\n" +
				"                       AMS_COST_DEPT_MATCH   ACDM,\n" +
				"                       AMS_COST_CENTER_TMP   ACCT\n" +
				"                WHERE  AACL3.SCAN_RESPONSIBILITY_DEPT = ACDM.DEPT_CODE\n" +
				"                       AND ACDM.COST_CENTER_CODE = ACCT.COST_CENTER_CODE\n" +
				"                       AND AACL3.SCAN_ORGANIZATION_ID = ACCT.ORGANIZATION_ID\n" +
				"                       AND EXISTS\n" +
				"                 (SELECT NULL\n" +
				"                        FROM   (SELECT AACL.ROWID\n" +
				"                                FROM   AMS_ASSETS_CHECK_LINE   AACL,\n" +
				"                                       AMS_ASSETS_CHECK_HEADER AACH\n" +
				"                                WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
				"                                       AND\n" +
				"                                       AACH.HEADER_ID =\n" +
				"                                       (SELECT MAX(AACH2.HEADER_ID)\n" +
				"                                        FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
				"                                               AMS_ASSETS_CHECK_HEADER AACH2\n" +
				"                                        WHERE  AACL.BARCODE = AACL2.BARCODE\n" +
				"                                               AND AACL2.SCAN_STATUS = 'Y'\n" +
				"                                               AND AACL2.HEADER_ID = AACH2.HEADER_ID\n" +
				"                                               AND AACH2.ORDER_STATUS IN\n" +
				"                                               ('UPLOADED', 'ARCHIEVED')\n" +
				"                                               AND\n" +
				"                                               ((AACH2.UPLOAD_DATE <=\n" +
				"                                               ISNULL(?, AACH2.UPLOAD_DATE) AND\n" +
				"                                               AACH2.UPLOAD_DATE >=\n" +
				"                                               ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
				"                                               (AACH2.ARCHIVED_DATE <=\n" +
				"                                               ISNULL(?, AACH2.ARCHIVED_DATE) AND\n" +
				"                                               AACH2.ARCHIVED_DATE >=\n" +
				"                                               ISNULL(?, AACH2.ARCHIVED_DATE))))) TMP_1\n" +
				"                        WHERE  AACL3.ROWID = TMP_1.ROWID)\n" +
				"                       AND ACCT.ORGANIZATION_ID = ?\n" +
				"                GROUP  BY ACCT.ACTUAL_COST_CENTER) SCANED_V\n" +
				"        WHERE  ACCT.COST_CENTER_CODE *= TOTAL_V.COST_CENTER_CODE\n" +
				"               AND ACCT.COST_CENTER_CODE *= SCANED_V.COST_CENTER_CODE) TMP_V\n" +
				"WHERE  TMP_V.TOTAL_COUNT > 0\n" +
				"       OR TMP_V.SCANED_COUNT > 0";

			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
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
