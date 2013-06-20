package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class CostDiffReportModel extends AMSSQLProducer {
	public CostDiffReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr =
						 "SELECT EOCM.ORGANIZATION_ID,\n" +
						 "       EOCM.COMPANY_CODE,\n" +
						 "       EOCM.COMPANY,\n" +
						 "       ACCV.COST_CENTER_CODE COST_CODE,\n" +
						 "       ACCV.COST_CENTER_NAME COST_NAME,\n" +
						 "       ISNULL(OWN_V.OWN_COUNT, 0) OWN_COUNT,\n" +
						 "       ISNULL(SCANED_V.SCANED_COUNT, 0) SCANED_COUNT,\n" +
						 "       ISNULL(NOT_SCANED_V.NOT_SCANED_COUNT, 0) NOT_SCANED_COUNT,\n" +
						 "       (CASE CHARINDEX(ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) /\n" +
						 "                     ISNULL(OWN_V.OWN_COUNT, 1),\n" +
						 "                     2) || '%',\n" +
						 "               '.')\n" +
						 "         WHEN 1 THEN\n" +
						 "          '0' ||\n" +
						 "          ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) / ISNULL(OWN_V.OWN_COUNT, 1),\n" +
						 "                2) || '%'\n" +
						 "         ELSE\n" +
						 "          ROUND(100 * ISNULL(SCANED_V.SCANED_COUNT, 0) / ISNULL(OWN_V.OWN_COUNT, 1),\n" +
						 "                2) || '%'\n" +
						 "       END) SCAN_RATE\n" +
						 "FROM   AMS_COST_CENTER_V ACCV,\n" +
						 "       ETS_OU_CITY_MAP EOCM,\n" +
						 "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CODE,\n" +
						 "               COUNT(EFA.ASSET_ID) OWN_COUNT\n" +
						 "        FROM   ETS_FA_ASSETS EFA\n" +
						 "        GROUP  BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) OWN_V,\n" +
						 "       (SELECT ACDM.COST_CENTER_CODE COST_CODE,\n" +
						 "               COUNT(AACL3.BARCODE) SCANED_COUNT\n" +
						 "        FROM   AMS_ASSETS_CHECK_LINE AACL3,\n" +
						 "               AMS_COST_DEPT_MATCH ACDM,\n" +
						 "               (SELECT AACL.ROWID\n" +
						 "                FROM   AMS_ASSETS_CHECK_LINE   AACL,\n" +
						 "                       AMS_ASSETS_CHECK_HEADER AACH\n" +
						 "                WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
						 "                       AND\n" +
						 "                       AACH.HEADER_ID =\n" +
						 "                       (SELECT MAX(AACH2.HEADER_ID)\n" +
						 "                        FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
						 "                               AMS_ASSETS_CHECK_HEADER AACH2\n" +
						 "                        WHERE  AACL.BARCODE = AACL2.BARCODE\n" +
						 "                               AND AACL2.SCAN_STATUS = 'Y'\n" +
						 "                               AND AACL2.HEADER_ID = AACH2.HEADER_ID\n" +
						 "                               AND (AACH2.ORDER_STATUS = 'UPLOADED' OR\n" +
						 "                               AACH2.ORDER_STATUS = 'ARCHIEVED')\n" +
						 "                               AND\n" +
						 "                               ((AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE) AND\n" +
						 "                               AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
						 "                               (AACH2.ARCHIVED_DATE >=\n" +
						 "                               ISNULL(?, AACH2.ARCHIVED_DATE) AND\n" +
						 "                               AACH2.ARCHIVED_DATE <=\n" +
						 "                               ISNULL(?, AACH2.ARCHIVED_DATE))))) TMP_1\n" +
						 "        WHERE  AACL3.ROWID = TMP_1.ROWID\n" +
						 "               AND DECODE(AACL3.SCAN_RESPONSIBILITY_DEPT,\n" +
						 "                          AACL3.RESPONSIBILITY_DEPT,\n" +
						 "                          AACL3.RESPONSIBILITY_DEPT,\n" +
						 "                          AACL3.SCAN_RESPONSIBILITY_DEPT) = ACDM.DEPT_CODE\n" +
						 "        GROUP  BY ACDM.COST_CENTER_CODE) SCANED_V,\n" +
						 "       (SELECT SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) COST_CODE,\n" +
						 "               COUNT(EFA.ASSET_ID) NOT_SCANED_COUNT\n" +
						 "        FROM   ETS_FA_ASSETS EFA\n" +
						 "        WHERE  NOT EXISTS\n" +
						 "         (SELECT NULL\n" +
						 "                FROM   (SELECT ACDM.COST_CENTER_CODE COST_CODE,\n" +
						 "                               AACL3.BARCODE\n" +
						 "                        FROM   AMS_ASSETS_CHECK_LINE AACL3,\n" +
						 "                               AMS_COST_DEPT_MATCH ACDM,\n" +
						 "                               (SELECT AACL.ROWID\n" +
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
						 "                                               AND (AACH2.ORDER_STATUS = 'UPLOADED' OR\n" +
						 "                                               AACH2.ORDER_STATUS = 'ARCHIEVED')\n" +
						 "                                               AND ((AACH2.UPLOAD_DATE >=\n" +
						 "                                               ISNULL(?, AACH2.UPLOAD_DATE) AND\n" +
						 "                                               AACH2.UPLOAD_DATE <=\n" +
						 "                                               ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
						 "                                               (AACH2.ARCHIVED_DATE >=\n" +
						 "                                               ISNULL(?, AACH2.ARCHIVED_DATE) AND\n" +
						 "                                               AACH2.ARCHIVED_DATE <=\n" +
						 "                                               ISNULL(?, AACH2.ARCHIVED_DATE))))) TMP_1\n" +
						 "                        WHERE  AACL3.ROWID = TMP_1.ROWID\n" +
						 "                               AND DECODE(AACL3.SCAN_RESPONSIBILITY_DEPT,\n" +
						 "                                          AACL3.RESPONSIBILITY_DEPT,\n" +
						 "                                          AACL3.RESPONSIBILITY_DEPT,\n" +
						 "                                          AACL3.SCAN_RESPONSIBILITY_DEPT) =\n" +
						 "                               ACDM.DEPT_CODE) TMP_SCANED\n" +
						 "                WHERE  EFA.TAG_NUMBER = TMP_SCANED.BARCODE\n" +
						 "                       AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) =\n" +
						 "                       TMP_SCANED.COST_CODE)\n" +
						 "        GROUP  BY SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)) NOT_SCANED_V\n" +
						 "WHERE  ACCV.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
						 "       AND ACCV.COST_CENTER_CODE *= OWN_V.COST_CODE\n" +
						 "       AND ACCV.COST_CENTER_CODE *= SCANED_V.COST_CODE\n" +
						 "       AND ACCV.COST_CENTER_CODE *= NOT_SCANED_V.COST_CODE\n" +
						 "       AND ACCV.ORGANIZATION_ID = ?\n" +
						 "       AND ACCV.COST_CENTER_CODE = ISNULL(?, ACCV.COST_CENTER_CODE)";
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getCostCode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
