package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class CostDiffDtlReportModel extends AMSSQLProducer {

	public CostDiffDtlReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造查询账上资产SQL
	 * @return SQLModel
	 */
	public SQLModel getOwnAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EFA.TAG_NUMBER,"
						+ " EFA.FA_CATEGORY1,"
						+ " EFA.FA_CATEGORY2,"
						+ " EFA.SEGMENT2,"
						+ " EFA.ASSETS_DESCRIPTION,"
						+ " EFA.MODEL_NUMBER,"
						+ " EFA.CURRENT_UNITS,"
						+ " EFA.UNIT_OF_MEASURE,"
						+ " EFA.ASSETS_LOCATION_CODE,"
						+ " EFA.ASSETS_LOCATION,"
						+ " EFA.ASSIGNED_TO_NUMBER,"
						+ " EFA.ASSIGNED_TO_NAME,"
						+ " EFA.MIS_PROJECT_NUMBER,"
						+ " EFA.PROJECT_NAME,"
						+ " EFA.ORIGINAL_COST,"
						+ " EFA.COST,"
						+ " EFA.LIFE_IN_YEARS,"
						+ " EFA.ASSETS_CREATE_DATE,"
						+ " EFA.DATE_PLACED_IN_SERVICE,"
						+ " EFA.DEPRN_RESERVE,"
						+ " EFA.DEPRN_COST,"
						+ " EFA.SCRAP_VALUE"
						+ " FROM"
						+ " ETS_FA_ASSETS EFA"
						+ " WHERE"
						+ " SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?"
//						+ " AND EFA.ORGANIZATION_ID = ?"//数据存在成本中心与OU组织不一致的问题
						;
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getCostCode());
//		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：构造查询已扫描设备SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getScanedItemsModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr =
				"SELECT AACL3.BARCODE,\n" +
				"       AMS_PUB_PKG.GET_FLEX_VALUE(AACL3.SCAN_ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
				"       AACL3.SCAN_ITEM_NAME ITEM_NAME,\n" +
				"       AACL3.SCAN_ITEM_SPEC ITEM_SPEC,\n" +
				"       AACL3.SCAN_START_DATE START_DATE,\n" +
				"       EO.WORKORDER_OBJECT_CODE,\n" +
				"       EO.WORKORDER_OBJECT_NAME,\n" +
				"       AME.USER_NAME,\n" +
				"       AME.EMPLOYEE_NUMBER,\n" +
				"       AMD.DEPT_NAME,\n" +
				"       AACL3.SCAN_MAINTAIN_USER\n" +
				"FROM   AMS_ASSETS_CHECK_LINE AACL3,\n" +
				"       AMS_ASSETS_CHECK_HEADER AACH,\n" +
				"       ETS_OBJECT EO,\n" +
				"       AMS_MIS_EMPLOYEE AME,\n" +
				"       AMS_MIS_DEPT AMD,\n" +
				"       AMS_COST_DEPT_MATCH ACDM,\n" +
				"       (SELECT AACL.ROWID\n" +
				"        FROM   AMS_ASSETS_CHECK_LINE   AACL,\n" +
				"               AMS_ASSETS_CHECK_HEADER AACH\n" +
				"        WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
				"               AND\n" +
				"               AACH.HEADER_ID =\n" +
				"               (SELECT MAX(AACH2.HEADER_ID)\n" +
				"                FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
				"                       AMS_ASSETS_CHECK_HEADER AACH2\n" +
				"                WHERE  AACL.BARCODE = AACL2.BARCODE\n" +
				"                       AND AACL2.SCAN_STATUS = 'Y'\n" +
				"                       AND AACL2.HEADER_ID = AACH2.HEADER_ID\n" +
				"                       AND (AACH2.ORDER_STATUS = 'UPLOADED' OR\n" +
				"                       AACH2.ORDER_STATUS = 'ARCHIEVED')\n" +
				"                       AND\n" +
				"                       ((AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE) AND\n" +
				"                       AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
				"                       (AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE) AND\n" +
				"                       AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE))))) TMP_1\n" +
				"WHERE  AACL3.ROWID = TMP_1.ROWID\n" +
				"       AND AACL3.HEADER_ID = AACH.HEADER_ID\n" +
				"       AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
				"       AND DECODE(AACL3.SCAN_RESPONSIBILITY_DEPT,\n" +
				"                  AACL3.RESPONSIBILITY_DEPT,\n" +
				"                  AACL3.RESPONSIBILITY_DEPT,\n" +
				"                  AACL3.SCAN_RESPONSIBILITY_DEPT) = AMD.DEPT_CODE\n" +
				"       AND DECODE(AACL3.SCAN_RESPONSIBILITY_USER,\n" +
				"                  AACL3.RESPONSIBILITY_USER,\n" +
				"                  AACL3.RESPONSIBILITY_USER,\n" +
				"                  AACL3.SCAN_RESPONSIBILITY_USER) = AME.EMPLOYEE_ID\n" +
				"       AND AMD.DEPT_CODE = ACDM.DEPT_CODE\n" +
				"       AND AMD.COMPANY_CODE = ?\n" +
				"       AND ACDM.COST_CENTER_CODE = ?";

			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(userAccount.getCompanyCode());
			sqlArgs.add(dto.getCostCode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	/**
	 * 功能：构造查询未扫描资产SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getNotScanedAssetsModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			String sqlStr =
				"SELECT EFA.TAG_NUMBER,\n" +
				"       EFA.FA_CATEGORY1,\n" +
				"       EFA.FA_CATEGORY2,\n" +
				"       EFA.SEGMENT2,\n" +
				"       EFA.ASSETS_DESCRIPTION,\n" +
				"       EFA.MODEL_NUMBER,\n" +
				"       EFA.CURRENT_UNITS,\n" +
				"       EFA.UNIT_OF_MEASURE,\n" +
				"       EFA.ASSETS_LOCATION_CODE,\n" +
				"       EFA.ASSETS_LOCATION,\n" +
				"       EFA.ASSIGNED_TO_NUMBER,\n" +
				"       EFA.ASSIGNED_TO_NAME,\n" +
				"       EFA.MIS_PROJECT_NUMBER,\n" +
				"       EFA.PROJECT_NAME,\n" +
				"       EFA.ORIGINAL_COST,\n" +
				"       EFA.COST,\n" +
				"       EFA.LIFE_IN_YEARS,\n" +
				"       EFA.ASSETS_CREATE_DATE,\n" +
				"       EFA.DATE_PLACED_IN_SERVICE,\n" +
				"       EFA.DEPRN_RESERVE,\n" +
				"       EFA.DEPRN_COST,\n" +
				"       EFA.SCRAP_VALUE\n" +
				"FROM   ETS_FA_ASSETS EFA\n" +
				"WHERE  SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?\n" +
				"       AND EFA.ORGANIZATION_ID = ?\n" +
				"       AND NOT EXISTS\n" +
				" (SELECT NULL\n" +
				"        FROM   (SELECT AACL3.BARCODE,\n" +
				"                       ACDM.COST_CENTER_CODE COST_CODE\n" +
				"                FROM   AMS_ASSETS_CHECK_LINE AACL3,\n" +
				"                       AMS_MIS_DEPT AMD,\n" +
				"                       AMS_COST_DEPT_MATCH ACDM,\n" +
				"                       (SELECT AACL.ROWID\n" +
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
				"                                           AND (AACH2.ORDER_STATUS = 'UPLOADED' OR\n" +
				"                                           AACH2.ORDER_STATUS = 'ARCHIEVED')\n" +
				"                                           AND ((AACH2.UPLOAD_DATE >=\n" +
				"                                           ISNULL(?, AACH2.UPLOAD_DATE) AND\n" +
				"                                           AACH2.UPLOAD_DATE <=\n" +
				"                                           ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
				"                                           (AACH2.ARCHIVED_DATE >=\n" +
				"                                           ISNULL(?, AACH2.ARCHIVED_DATE) AND\n" +
				"                                           AACH2.ARCHIVED_DATE <=\n" +
				"                                           ISNULL(?, AACH2.ARCHIVED_DATE))))) TMP_1\n" +
				"                WHERE  AACL3.ROWID = TMP_1.ROWID\n" +
				"                       AND\n" +
				"                       DECODE(AACL3.SCAN_RESPONSIBILITY_DEPT,\n" +
				"                              AACL3.RESPONSIBILITY_DEPT,\n" +
				"                              AACL3.RESPONSIBILITY_DEPT,\n" +
				"                              AACL3.SCAN_RESPONSIBILITY_DEPT) = AMD.DEPT_CODE\n" +
				"                       AND AMD.DEPT_CODE = ACDM.DEPT_CODE\n" +
				"                       AND AMD.COMPANY_CODE = ?) SCANED_V\n" +
				"        WHERE  EFA.TAG_NUMBER = SCANED_V.BARCODE\n" +
				"               AND SCANED_V.COST_CODE = ?)";

			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getCostCode());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(userAccount.getCompanyCode());
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
