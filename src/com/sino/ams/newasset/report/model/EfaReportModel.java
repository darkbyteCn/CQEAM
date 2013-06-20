package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class EfaReportModel extends AMSSQLProducer {

	public EfaReportModel(SfUserDTO userAccount, AmsAssetsCheckBatchDTO dtoParameter) {
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
			String sqlStr ="SELECT CON.ORGANIZATION_ID,\n                                                     "
			 +"      CON.COMPANY,\n                                                                           "
			 +"      CON.MIS_COUNT,\n                                                                         "
			 +"      SUMLNEID.LNE_ID,\n                                                                       "
			 +"      (DECODE(TRUNC(100 * SUMLNEID.LNE_ID / CON.MIS_COUNT),                                  "
			 +"              0,                                                                             "
			 +"               STR_REPLACE(ROUND(100 * SUMLNEID.LNE_ID / CON.MIS_COUNT, 2),                      "
			 +"                       '.',                                                                  "
			 +"                       '0.'),                                                                "
			 +"               TO_CHAR(ROUND(100 * SUMLNEID.LNE_ID / CON.MIS_COUNT, 2))) || '%') LNE_ID_RATE,\n"
			 +"       SUMNLEID.NLE_ID,                                                                      "
			 +"       (DECODE(TRUNC(100 * SUMNLEID.NLE_ID / CON.MIS_COUNT),                                 "
			 +"               0,                                                                            "
			 +"               STR_REPLACE(ROUND(100 * SUMNLEID.NLE_ID / CON.MIS_COUNT, 2),                      "
			 +"                       '.',                                                                  "
			 +"                       '0.'),                                                                "
			 +"               TO_CHAR(ROUND(100 * SUMNLEID.NLE_ID / CON.MIS_COUNT, 2))) || '%') NLE_ID_RATE,"
			 +"       SUMCEXID.CEX_ID,                                                                      "
			 +"       (DECODE(TRUNC(100 * SUMCEXID.CEX_ID / CON.MIS_COUNT),                                 "
			 +"               0,                                                                            "
			 +"               STR_REPLACE(ROUND(100 * SUMCEXID.CEX_ID / CON.MIS_COUNT, 2),                      "
			 +"                       '.',                                                                  "
			 +"                       '0.'),                                                                "
			 +"               TO_CHAR(ROUND(100 * SUMCEXID.CEX_ID / CON.MIS_COUNT, 2))) || '%') CEX_ID_RATE,"
			 +"       SUMOPEID.OPE_ID,                                                                      "
			 +"       (DECODE(TRUNC(100 * SUMOPEID.OPE_ID / CON.MIS_COUNT),                                 "
			 +"               0,                                                                            "
			 +"               STR_REPLACE(ROUND(100 * SUMOPEID.OPE_ID / CON.MIS_COUNT, 2),                      "
			 +"                       '.',                                                                  "
			 +"                       '0.'),                                                                "
			 +"               TO_CHAR(ROUND(100 * SUMOPEID.OPE_ID / CON.MIS_COUNT, 2))) || '%') OPE_ID_RATE "
			 +"  FROM (SELECT EOC.ORGANIZATION_ID ORGANIZATION_ID,                                          "
			 +"               EOC.COMPANY         COMPANY,                                                  "
			 +"               TEMP.MIS_COUNT      MIS_COUNT                                                 "
			 +"          FROM ETS_OU_CITY_MAP EOC,                                                          "
			 +"               (SELECT EFA.ORGANIZATION_ID ORGANIZATION_ID,                                  "
			 +"                       COUNT(EFA.ASSET_ID) MIS_COUNT                                         "
			 +"                  FROM ETS_FA_ASSETS EFA                                                     "
			 +"                 WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'                                      "
			 +"                 AND   EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)                        "
			 +"                 GROUP BY EFA.ORGANIZATION_ID) TEMP                                          "
			 +"         WHERE TEMP.ORGANIZATION_ID = EOC.ORGANIZATION_ID) CON,                              "
			 +"                                                                                             "
			 +" (SELECT LNEID1.ORGANIZATION_ID ORGANIZATION_ID,                                             "
			 +"         LNEID1.LNEID + LNEID2.LNEID LNE_ID                                                  "
			 +"    FROM (SELECT EFA.ORGANIZATION_ID ORGANIZATION_ID,                                        "
			 +"                 COUNT(EII.LNE_ID) LNEID                                                     "
			 +"            FROM ETS_ITEM_INFO EII, ETS_FA_ASSETS EFA                                        "
			 +"           WHERE EII.BARCODE = EFA.TAG_NUMBER                                                "
			 +"             AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.LNE_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) LNEID1,                                             "
			 +"                                                                                             "
			 +"         (SELECT EFA.ORGANIZATION_ID ORGANIZATION_ID,                                        "
			 +"                 COUNT(EII.LNE_ID) LNEID                                                     "
			 +"            FROM ETS_ITEM_INFO  EII,                                                         "
			 +"                 ETS_FA_ASSETS  EFA,                                                         "
			 +"                 ETS_ITEM_MATCH EIM                                                          "
			 +"           WHERE EII.SYSTEMID = EIM.SYSTEMID                                                 "
			 +"             AND EFA.ASSET_ID = EIM.ASSET_ID                                                 "
			 +"             AND EII.BARCODE <> EFA.TAG_NUMBER                                               "
			 +"             AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.LNE_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) LNEID2                                              "
			 +"   WHERE LNEID1.ORGANIZATION_ID = LNEID2.ORGANIZATION_ID) SUMLNEID,                          "
			 +"                                                                                             "
			 +" (SELECT NLEID1.ORGANIZATION_ID ORGANIZATION_ID,                                             "
			 +"         NLEID1.NLEID + NLEID2.NLEID NLE_ID                                                  "
			 +"    FROM (SELECT EFA.ORGANIZATION_ID, COUNT(EII.NLE_ID) NLEID                                "
			 +"            FROM ETS_ITEM_INFO EII, ETS_FA_ASSETS EFA                                        "
			 +"           WHERE EII.BARCODE = EFA.TAG_NUMBER                                                "
			 +"             AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.NLE_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) NLEID1,                                             "
			 +"                                                                                             "
			 +"         (SELECT EFA.ORGANIZATION_ID, COUNT(EII.NLE_ID) NLEID                                "
			 +"            FROM ETS_ITEM_INFO  EII,                                                         "
			 +"                 ETS_FA_ASSETS  EFA,                                                         "
			 +"                 ETS_ITEM_MATCH EIM                                                          "
			 +"           WHERE EII.SYSTEMID = EIM.SYSTEMID                                                 "
			 +"             AND EFA.ASSET_ID = EIM.ASSET_ID                                                 "
			 +"             AND EII.BARCODE <> EFA.TAG_NUMBER                                               "
			 +"             AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.NLE_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) NLEID2                                              "
			 +"   WHERE NLEID1.ORGANIZATION_ID = NLEID2.ORGANIZATION_ID) SUMNLEID,                          "
			 +"                                                                                             "
			 +" (SELECT CEXID1.ORGANIZATION_ID ORGANIZATION_ID,                                             "
			 +"         CEXID1.CEXID + CEXID2.CEXID CEX_ID                                                  "
			 +"    FROM (SELECT EFA.ORGANIZATION_ID, COUNT(EII.CEX_ID) CEXID                                "
			 +"            FROM ETS_ITEM_INFO EII, ETS_FA_ASSETS EFA                                        "
			 +"           WHERE EII.BARCODE = EFA.TAG_NUMBER                                                "
			 +"             AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.CEX_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) CEXID1,                                             "
			 +"                                                                                             "
			 +"         (SELECT EFA.ORGANIZATION_ID, COUNT(EII.CEX_ID) CEXID                                "
			 +"            FROM ETS_ITEM_INFO  EII,                                                         "
			 +"                 ETS_FA_ASSETS  EFA,                                                         "
			 +"                 ETS_ITEM_MATCH EIM                                                          "
			 +"           WHERE EII.SYSTEMID = EIM.SYSTEMID                                                 "
			 +"             AND EFA.ASSET_ID = EIM.ASSET_ID                                                 "
			 +"             AND EII.BARCODE <> EFA.TAG_NUMBER                                               "
			 +"             AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.CEX_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) CEXID2                                              "
			 +"   WHERE CEXID1.ORGANIZATION_ID = CEXID2.ORGANIZATION_ID) SUMCEXID,                          "
			 +"                                                                                             "
			 +" (SELECT OPEID1.ORGANIZATION_ID ORGANIZATION_ID,                                             "
			 +"         OPEID1.OPEID + OPEID2.OPEID OPE_ID                                                  "
			 +"    FROM (SELECT EFA.ORGANIZATION_ID, COUNT(EII.OPE_ID) OPEID                                "
			 +"            FROM ETS_ITEM_INFO EII, ETS_FA_ASSETS EFA                                        "
			 +"           WHERE EII.BARCODE = EFA.TAG_NUMBER                                                "
			 +"             AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.OPE_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) OPEID1,                                             "
			 +"                                                                                             "
			 +"         (SELECT EFA.ORGANIZATION_ID, COUNT(EII.OPE_ID) OPEID                                "
			 +"            FROM ETS_ITEM_INFO  EII,                                                         "
			 +"                 ETS_FA_ASSETS  EFA,                                                         "
			 +"                 ETS_ITEM_MATCH EIM                                                          "
			 +"           WHERE EII.SYSTEMID = EIM.SYSTEMID                                                 "
			 +"             AND EFA.ASSET_ID = EIM.ASSET_ID                                                 "
			 +"             AND EII.BARCODE <> EFA.TAG_NUMBER                                               "
			 +"             AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)                           "
			 +"             AND EII.ORGANIZATION_ID = EFA.ORGANIZATION_ID                                   "
			 +"             AND  " + SyBaseSQLUtil.isNotNull("EII.OPE_ID") + "                                                       "
			 +"           GROUP BY EFA.ORGANIZATION_ID) OPEID2                                              "
			 +"   WHERE OPEID1.ORGANIZATION_ID = OPEID2.ORGANIZATION_ID) SUMOPEID                           "
			 +"                                                                                             "
			 +"   WHERE CON.ORGANIZATION_ID *= SUMOPEID.ORGANIZATION_ID                                     "
			 +"   AND   CON.ORGANIZATION_ID *= SUMCEXID.ORGANIZATION_ID                                     "
			 +"   AND   CON.ORGANIZATION_ID *= SUMNLEID.ORGANIZATION_ID                                     "
			 +"   AND   CON.ORGANIZATION_ID *= SUMLNEID.ORGANIZATION_ID                                     "  ;
			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (Exception ex) {
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
