package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class DeptDiffReportModel extends AMSSQLProducer {
	public DeptDiffReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr =
				"SELECT DEPT_V.ORGANIZATION_ID,\n" +
				"       DEPT_V.COMPANY_CODE,\n" +
				"       DEPT_V.COMPANY,\n" +
				"       DEPT_V.DEPT_CODE,\n" +
				"       DEPT_V.DEPT_NAME,\n" +
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
				"FROM   (SELECT EOCM.ORGANIZATION_ID,\n" +
				"               EOCM.COMPANY_CODE,\n" +
				"               EOCM.COMPANY,\n" +
				"               AMD.DEPT_CODE,\n" +
				"               AMD.DEPT_NAME\n" +
				"        FROM   AMS_MIS_DEPT    AMD,\n" +
				"               ETS_OU_CITY_MAP EOCM\n" +
				"        WHERE  AMD.COMPANY_CODE = EOCM.COMPANY_CODE) DEPT_V,\n" +
				"       (SELECT EFA.ORGANIZATION_ID,\n" +
				"               ACDM.DEPT_CODE,\n" +
				"               COUNT(EFA.ASSET_ID) OWN_COUNT\n" +
				"        FROM   ETS_FA_ASSETS       EFA,\n" +
				"               AMS_COST_DEPT_MATCH ACDM\n" +
				"        WHERE  SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACDM.COST_CENTER_CODE\n" +
				"               AND\n" +
				"               EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE)\n" +
				"               AND EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION)\n" +
				"        GROUP  BY EFA.ORGANIZATION_ID,\n" +
				"                  ACDM.DEPT_CODE) OWN_V,\n" +
				"       (SELECT EOCM.ORGANIZATION_ID,\n" +
				"               EOCM.COMPANY_CODE,\n" +
				"               AMD.DEPT_CODE,\n" +
				"               COUNT(AACL3.BARCODE) SCANED_COUNT\n" +
				"        FROM   AMS_ASSETS_CHECK_LINE AACL3,\n" +
				"               AMS_ASSETS_CHECK_HEADER AACH,\n" +
				"               ETS_OBJECT EO,\n" +
				"               AMS_MIS_DEPT AMD,\n" +
				"               ETS_OU_CITY_MAP EOCM,\n" +
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
				"                          AACL3.SCAN_RESPONSIBILITY_DEPT) = AMD.DEPT_CODE\n" +
				"               AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
				"               AND AACL3.HEADER_ID = AACH.HEADER_ID\n" +
				"               AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
				"               AND\n" +
				"               EO.WORKORDER_OBJECT_CODE LIKE ISNULL(?, EO.WORKORDER_OBJECT_CODE)\n" +
				"               AND\n" +
				"               EO.WORKORDER_OBJECT_NAME LIKE ISNULL(?, EO.WORKORDER_OBJECT_NAME)\n" +
				"        GROUP  BY EOCM.ORGANIZATION_ID,\n" +
				"                  EOCM.COMPANY_CODE,\n" +
				"                  AMD.DEPT_CODE) SCANED_V,\n" +
				"       (SELECT EFA.ORGANIZATION_ID,\n" +
				"               ACDM.DEPT_CODE,\n" +
				"               COUNT(EFA.ASSET_ID) NOT_SCANED_COUNT\n" +
				"        FROM   ETS_FA_ASSETS       EFA,\n" +
				"               AMS_COST_DEPT_MATCH ACDM\n" +
				"        WHERE  SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACDM.COST_CENTER_CODE\n" +
				"               AND NOT EXISTS\n" +
				"         (SELECT NULL\n" +
				"                FROM   (SELECT DECODE(AACL3.SCAN_RESPONSIBILITY_DEPT,\n" +
				"                                      AACL3.RESPONSIBILITY_DEPT,\n" +
				"                                      AACL3.RESPONSIBILITY_DEPT,\n" +
				"                                      AACL3.SCAN_RESPONSIBILITY_DEPT) DEPT_CODE,\n" +
				"                               AACL3.BARCODE\n" +
				"                        FROM   AMS_ASSETS_CHECK_LINE   AACL3,\n" +
				"                               AMS_ASSETS_CHECK_HEADER AACH,\n" +
				"                               ETS_OBJECT              EO\n" +
				"                        WHERE  AACL3.HEADER_ID = AACH.HEADER_ID\n" +
				"                               AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
				"                               AND AACL3.SCAN_STATUS = 'Y'\n" +
				"                               AND (AACH.ORDER_STATUS = 'UPLOADED' OR\n" +
				"                               AACH.ORDER_STATUS = 'ARCHIEVED')\n" +
				"                               AND\n" +
				"                               ((AACH.UPLOAD_DATE >= ISNULL(?, AACH.UPLOAD_DATE) AND\n" +
				"                               AACH.UPLOAD_DATE <= ISNULL(?, AACH.UPLOAD_DATE)) OR\n" +
				"                               (AACH.ARCHIVED_DATE >=\n" +
				"                               ISNULL(?, AACH.ARCHIVED_DATE) AND\n" +
				"                               AACH.ARCHIVED_DATE <=\n" +
				"                               ISNULL(?, AACH.ARCHIVED_DATE)))\n" +
				"                               AND EO.WORKORDER_OBJECT_CODE LIKE\n" +
				"                               ISNULL(?, EO.WORKORDER_OBJECT_CODE)\n" +
				"                               AND EO.WORKORDER_OBJECT_NAME LIKE\n" +
				"                               ISNULL(?, EO.WORKORDER_OBJECT_NAME)) SCANED_TMP\n" +
				"                WHERE  EFA.TAG_NUMBER = SCANED_TMP.BARCODE\n" +
				"                       AND ACDM.DEPT_CODE = SCANED_TMP.DEPT_CODE)\n" +
				"               AND\n" +
				"               EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE)\n" +
				"               AND EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION)\n" +
				"        GROUP  BY EFA.ORGANIZATION_ID,\n" +
				"                  ACDM.DEPT_CODE) NOT_SCANED_V\n" +
				"WHERE  DEPT_V.ORGANIZATION_ID *= OWN_V.ORGANIZATION_ID\n" +
				"       AND DEPT_V.DEPT_CODE *= OWN_V.DEPT_CODE\n" +
				"       AND DEPT_V.ORGANIZATION_ID *= SCANED_V.ORGANIZATION_ID\n" +
				"       AND DEPT_V.DEPT_CODE *= SCANED_V.DEPT_CODE\n" +
				"       AND DEPT_V.ORGANIZATION_ID *= NOT_SCANED_V.ORGANIZATION_ID\n" +
				"       AND DEPT_V.DEPT_CODE *= NOT_SCANED_V.DEPT_CODE\n" +
				"       AND DEPT_V.ORGANIZATION_ID = ISNULL(?, DEPT_V.ORGANIZATION_ID)\n" +
				"       AND DEPT_V.DEPT_CODE = ISNULL(?, DEPT_V.DEPT_CODE)";
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getObjectName());

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getObjectName());

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getObjectName());

			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getObjectName());

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
