package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class AssetsAnalysisModel extends AMSSQLProducer {

	public AssetsAnalysisModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：返回页面翻页查询时所需要的SQLModel
	 * <B>默认为空实现。可由具体应用选择是否需要实现</B>
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
							"       EOCM.COMPANY,\n" +
							"       ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
							"       ISNULL(TOTAL_V.TOTAL_COST, 0) TOTAL_COST,\n" +
							"       ISNULL(YEAR_ADD_V.YEAR_ADD_COUNT, 0) YEAR_ADD_COUNT,\n" +
							"       ISNULL(YEAR_ADD_V.YEAR_ADD_COST, 0) YEAR_ADD_COST,\n" +
							"       ISNULL(MONTH_ADD_V.MONTH_ADD_COUNT, 0) MONTH_ADD_COUNT,\n" +
							"       ISNULL(MONTH_ADD_V.MONTH_ADD_COST, 0) MONTH_ADD_COST,\n" +
							"       ISNULL(YEAR_DIS_V.YEAR_DIS_COUNT, 0) YEAR_DIS_COUNT,\n" +
							"       ISNULL(YEAR_DIS_V.YEAR_DIS_COST, 0) YEAR_DIS_COST,\n" +
							"       ISNULL(MONTH_DIS_V.MONTH_DIS_COUNT, 0) MONTH_DIS_COUNT,\n" +
							"       ISNULL(MONTH_DIS_V.MONTH_DIS_COST, 0) MONTH_DIS_COST\n" +
							"FROM   ETS_OU_CITY_MAP EOCM,\n" +
							"       (SELECT EFA.ORGANIZATION_ID,\n" +
							"               COUNT(EFA.ASSET_ID) TOTAL_COUNT,\n" +
							"               SUM(EFA.COST) TOTAL_COST\n" +
							"        FROM   ETS_FA_ASSETS EFA\n" +
							"        WHERE  EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
							"               AND EFA.ASSETS_CREATE_DATE <=\n" +
							"               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD'))) + 1 -\n" +
							"               1 / 24 / 60 / 60\n" +
							"               AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
							"               AND NOT EXISTS (SELECT NULL\n" +
							"                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
							"                WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
							"                       AND AICM.NEED_SCAN = 'N')\n" +
							"        GROUP  BY EFA.ORGANIZATION_ID) TOTAL_V,\n" +
							"       (SELECT EFA.ORGANIZATION_ID,\n" +
							"               COUNT(EFA.ASSET_ID) YEAR_ADD_COUNT,\n" +
							"               SUM(EFA.COST) YEAR_ADD_COST\n" +
							"        FROM   ETS_FA_ASSETS EFA\n" +
							"        WHERE  EFA.ASSETS_CREATE_DATE >=\n" +
							"               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'YEAR')\n" +
							"               AND EFA.ASSETS_CREATE_DATE <=\n" +
							"               TO_DATE(TO_CHAR(TO_DATE(?, 'YYYY-MM-DD'), 'YYYY') ||\n" +
							"                           '-12-31 23:59:59',\n" +
							"                           'YYYY-MM-DD HH24:MI:SS')\n" +
							"               AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
							"               AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
							"               AND NOT EXISTS (SELECT NULL\n" +
							"                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
							"                WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
							"                       AND AICM.NEED_SCAN = 'N')\n" +
							"        GROUP  BY EFA.ORGANIZATION_ID) YEAR_ADD_V,\n" +
							"       (SELECT EFA.ORGANIZATION_ID,\n" +
							"               COUNT(EFA.ASSET_ID) MONTH_ADD_COUNT,\n" +
							"               SUM(EFA.COST) MONTH_ADD_COST\n" +
							"        FROM   ETS_FA_ASSETS EFA\n" +
							"        WHERE  EFA.ASSETS_CREATE_DATE >=\n" +
							"               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MONTH')\n" +
							"               AND EFA.ASSETS_CREATE_DATE <=\n" +
							"               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD'))) + 1 -\n" +
							"               1 / 24 / 60 / 60\n" +
							"               AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
							"               AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
							"               AND NOT EXISTS (SELECT NULL\n" +
							"                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
							"                WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
							"                       AND AICM.NEED_SCAN = 'N')\n" +
							"        GROUP  BY EFA.ORGANIZATION_ID) MONTH_ADD_V,\n" +
							"       (SELECT ADA.ORGANIZATION_ID,\n" +
							"               COUNT(1) YEAR_DIS_COUNT,\n" +
							"               SUM(ADA.RETIRED_COST) YEAR_DIS_COST\n" +
							"        FROM   AMS_DISCARDED_ASSETS ADA\n" +
							"        WHERE  ADA.DATE_RETIRED >=\n" +
							"               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'YEAR')\n" +
							"               AND ADA.DATE_RETIRED <=\n" +
							"               TO_DATE(TO_CHAR(TO_DATE(?, 'YYYY-MM-DD'), 'YYYY') ||\n" +
							"                           '-12-31 23:59:59',\n" +
							"                           'YYYY-MM-DD HH24:MI:SS')\n" +
							"               AND ADA.ORGANIZATION_ID = ISNULL(?, ADA.ORGANIZATION_ID)\n" +
							"               AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
							"               AND NOT EXISTS (SELECT NULL\n" +
							"                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
							"                WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
							"                       AND AICM.NEED_SCAN = 'N')\n" +
							"        GROUP  BY ADA.ORGANIZATION_ID) YEAR_DIS_V,\n" +
							"       (SELECT ADA.ORGANIZATION_ID,\n" +
							"               COUNT(1) MONTH_DIS_COUNT,\n" +
							"               SUM(ADA.RETIRED_COST) MONTH_DIS_COST\n" +
							"        FROM   AMS_DISCARDED_ASSETS ADA\n" +
							"        WHERE  ADA.DATE_RETIRED >=\n" +
							"               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MONTH')\n" +
							"               AND ADA.DATE_RETIRED <=\n" +
							"               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD'))) + 1 -\n" +
							"               1 / 24 / 60 / 60\n" +
							"               AND ADA.ORGANIZATION_ID = ISNULL(?, ADA.ORGANIZATION_ID)\n" +
							"               AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
							"               AND NOT EXISTS (SELECT NULL\n" +
							"                FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
							"                WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
							"                       AND AICM.NEED_SCAN = 'N')\n" +
							"        GROUP  BY ADA.ORGANIZATION_ID) MONTH_DIS_V\n" +
							"WHERE  EOCM.ORGANIZATION_ID *= TOTAL_V.ORGANIZATION_ID\n" +
							"       AND EOCM.ORGANIZATION_ID *= YEAR_ADD_V.ORGANIZATION_ID\n" +
							"       AND EOCM.ORGANIZATION_ID *= MONTH_ADD_V.ORGANIZATION_ID\n" +
							"       AND EOCM.ORGANIZATION_ID *= YEAR_DIS_V.ORGANIZATION_ID\n" +
							"       AND EOCM.ORGANIZATION_ID *= MONTH_DIS_V.ORGANIZATION_ID\n" +
							"       AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)";

			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());

			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
			sqlArgs.add(dto.getOrganizationId());

			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
			sqlArgs.add(dto.getOrganizationId());

			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
			sqlArgs.add(dto.getOrganizationId());

			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
			sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
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
	 * 功能：获取特定公司的资产变动情况SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getOrgAssetsChangeModel() throws SQLModelException {
		SQLModel sqlModel = null;
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			String analyseType = dto.getAnalyseType();
			if (analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_1)) { //应用领域
				sqlModel = getAnalyzeModelOne();
			} else if (analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_2)) { //类项目节：资产类别
				sqlModel = getAnalyzeModelTwo();
			} else if (analyseType.equals(AssetsDictConstant.ANALYZE_CATEGORY_3)) { //类项目节：资产大项
				sqlModel = getAnalyzeModelThree();
			}
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

/**
	 *
	 * @return SQLModel
	 * @throws CalendarException
	 */
	private SQLModel getAnalyzeModelOne() throws CalendarException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr =
			"SELECT TMP_V.ORGANIZATION_ID,\n" +
			"       TMP_V.COMPANY,\n" +
			"       TMP_V.SEGMENT,\n" +
			"       TMP_V.FA_CATEGORY,\n" +
			"       TMP_V.TOTAL_COUNT,\n" +
			"       TMP_V.TOTAL_COST,\n" +
			"       TMP_V.YEAR_ADD_COUNT,\n" +
			"       TMP_V.YEAR_ADD_COST,\n" +
			"       TMP_V.MONTH_ADD_COUNT,\n" +
			"       TMP_V.MONTH_ADD_COST,\n" +
			"       TMP_V.YEAR_DIS_COUNT,\n" +
			"       TMP_V.YEAR_DIS_COST,\n" +
			"       TMP_V.MONTH_DIS_COUNT,\n" +
			"       TMP_V.MONTH_DIS_COST\n" +
			"FROM   (SELECT TMP_OU.ORGANIZATION_ID,\n" +
			"               TMP_OU.COMPANY,\n" +
			"               TMP_OU.SEGMENT,\n" +
			"               TMP_OU.FA_CATEGORY,\n" +
			"               ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
			"               ISNULL(TOTAL_V.TOTAL_COST, 0) TOTAL_COST,\n" +
			"               ISNULL(YEAR_ADD_V.YEAR_ADD_COUNT, 0) YEAR_ADD_COUNT,\n" +
			"               ISNULL(YEAR_ADD_V.YEAR_ADD_COST, 0) YEAR_ADD_COST,\n" +
			"               ISNULL(MONTH_ADD_V.MONTH_ADD_COUNT, 0) MONTH_ADD_COUNT,\n" +
			"               ISNULL(MONTH_ADD_V.MONTH_ADD_COST, 0) MONTH_ADD_COST,\n" +
			"               ISNULL(YEAR_DIS_V.YEAR_DIS_COUNT, 0) YEAR_DIS_COUNT,\n" +
			"               ISNULL(YEAR_DIS_V.YEAR_DIS_COST, 0) YEAR_DIS_COST,\n" +
			"               ISNULL(MONTH_DIS_V.MONTH_DIS_COUNT, 0) MONTH_DIS_COUNT,\n" +
			"               ISNULL(MONTH_DIS_V.MONTH_DIS_COST, 0) MONTH_DIS_COST\n" +
			"        FROM   (SELECT EOCM.ORGANIZATION_ID,\n" +
			"                       EOCM.COMPANY,\n" +
			"                       MFFV.FLEX_VALUE SEGMENT,\n" +
			"                       MFFV.DESCRIPTION FA_CATEGORY\n" +
			"                FROM   ETS_OU_CITY_MAP   EOCM,\n" +
			"                       M_FND_FLEX_VALUES MFFV\n" +
			"                WHERE  MFFV.FLEX_VALUE_SET_ID = 1007341) TMP_OU,\n" +
			"               (SELECT EFA.ORGANIZATION_ID,\n" +
			"                       EFA.SEGMENT1,\n" +
			"                       EFA.FA_CATEGORY1,\n" +
			"                       COUNT(EFA.ASSET_ID) TOTAL_COUNT,\n" +
			"                       SUM(EFA.COST) TOTAL_COST\n" +
			"                FROM   ETS_FA_ASSETS EFA\n" +
			"                WHERE  EFA.ORGANIZATION_ID = ?\n" +
			"                       AND EFA.ASSETS_CREATE_DATE(+) <=\n" +
			"                       TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD'))) + 1 -\n" +
			"                       1 / 24 / 60 / 60\n" +
			"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
			"                          EFA.SEGMENT1,\n" +
			"                          EFA.FA_CATEGORY1) TOTAL_V,\n" +
			"               (SELECT EFA.ORGANIZATION_ID,\n" +
			"                       EFA.SEGMENT1,\n" +
			"                       EFA.FA_CATEGORY1,\n" +
			"                       COUNT(EFA.ASSET_ID) YEAR_ADD_COUNT,\n" +
			"                       SUM(EFA.COST) YEAR_ADD_COST\n" +
			"                FROM   ETS_FA_ASSETS EFA\n" +
			"                WHERE  EFA.ASSETS_CREATE_DATE(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'YEAR')\n" +
			"                       AND EFA.ASSETS_CREATE_DATE(+) <=\n" +
			"                       TO_DATE(TO_CHAR(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                               GETDATE()),\n" +
			"                                           'YYYY') || '-12-31 23:59:59',\n" +
			"                                   'YYYY-MM-DD HH24:MI:SS')\n" +
			"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                       AND EFA.ORGANIZATION_ID = ?\n" +
			"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
			"                          EFA.SEGMENT1,\n" +
			"                          EFA.FA_CATEGORY1) YEAR_ADD_V,\n" +
			"               (SELECT EFA.ORGANIZATION_ID,\n" +
			"                       EFA.SEGMENT1,\n" +
			"                       EFA.FA_CATEGORY1,\n" +
			"                       COUNT(EFA.ASSET_ID) MONTH_ADD_COUNT,\n" +
			"                       SUM(EFA.COST) MONTH_ADD_COST\n" +
			"                FROM   ETS_FA_ASSETS EFA\n" +
			"                WHERE  EFA.ASSETS_CREATE_DATE(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'MONTH')\n" +
			"                       AND\n" +
			"                       EFA.ASSETS_CREATE_DATE(+) <=\n" +
			"                       TRUNC(LAST_DAY(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                          GETDATE()))) + 1 - 1 / 24 / 60 / 60\n" +
			"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                       AND EFA.ORGANIZATION_ID = ?\n" +
			"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
			"                          EFA.SEGMENT1,\n" +
			"                          EFA.FA_CATEGORY1) MONTH_ADD_V,\n" +
			"               (SELECT ADA.ORGANIZATION_ID,\n" +
			"                       ADA.SEGMENT1,\n" +
			"                       COUNT(1) YEAR_DIS_COUNT,\n" +
			"                       SUM(ADA.RETIRED_COST) YEAR_DIS_COST\n" +
			"                FROM   AMS_DISCARDED_ASSETS ADA\n" +
			"                WHERE  ADA.DATE_RETIRED(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'YEAR')\n" +
			"                       AND ADA.DATE_RETIRED(+) <=\n" +
			"                       TO_DATE(TO_CHAR(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                               GETDATE()),\n" +
			"                                           'YYYY') || '-12-31 23:59:59',\n" +
			"                                   'YYYY-MM-DD HH24:MI:SS')\n" +
			"                       AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                       AND ADA.ORGANIZATION_ID = ?\n" +
			"                GROUP  BY ADA.ORGANIZATION_ID,\n" +
			"                          ADA.SEGMENT1) YEAR_DIS_V,\n" +
			"               (SELECT ADA.ORGANIZATION_ID,\n" +
			"                       ADA.SEGMENT1,\n" +
			"                       COUNT(1) MONTH_DIS_COUNT,\n" +
			"                       SUM(ADA.RETIRED_COST) MONTH_DIS_COST\n" +
			"                FROM   AMS_DISCARDED_ASSETS ADA\n" +
			"                WHERE  ADA.DATE_RETIRED(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'MONTH')\n" +
			"                       AND\n" +
			"                       ADA.DATE_RETIRED(+) <=\n" +
			"                       TRUNC(LAST_DAY(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                          GETDATE()))) + 1 - 1 / 24 / 60 / 60\n" +
			"                       AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                       AND ADA.ORGANIZATION_ID = ?\n" +
			"                GROUP  BY ADA.ORGANIZATION_ID,\n" +
			"                          ADA.SEGMENT1) MONTH_DIS_V\n" +
			"        WHERE  TMP_OU.ORGANIZATION_ID *= TOTAL_V.ORGANIZATION_ID\n" +
			"               AND TMP_OU.ORGANIZATION_ID *= YEAR_ADD_V.ORGANIZATION_ID\n" +
			"               AND TMP_OU.ORGANIZATION_ID *= MONTH_ADD_V.ORGANIZATION_ID\n" +
			"               AND TMP_OU.ORGANIZATION_ID *= YEAR_DIS_V.ORGANIZATION_ID\n" +
			"               AND TMP_OU.ORGANIZATION_ID *= MONTH_DIS_V.ORGANIZATION_ID\n" +
			"               AND TMP_OU.SEGMENT *= TOTAL_V.SEGMENT1\n" +
			"               AND TMP_OU.SEGMENT *= YEAR_ADD_V.SEGMENT1\n" +
			"               AND TMP_OU.SEGMENT *= MONTH_ADD_V.SEGMENT1\n" +
			"               AND TMP_OU.SEGMENT *= YEAR_DIS_V.SEGMENT1\n" +
			"               AND TMP_OU.SEGMENT *= MONTH_DIS_V.SEGMENT1) TMP_V\n" +
			"WHERE  TMP_V.TOTAL_COUNT > 0\n" +
			"       OR TMP_V.YEAR_ADD_COUNT > 0\n" +
			"       OR TMP_V.YEAR_DIS_COUNT > 0";

		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

/**
	 *
	 * @return SQLModel
	 * @throws CalendarException
	 */
	private SQLModel getAnalyzeModelTwo() throws CalendarException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr =

			"SELECT TMP_V.SEGMENT2 SEGMENT,\n" +
			"       TMP_V.FA_CATEGORY2 FA_CATEGORY,\n" +
			"       TMP_V.TOTAL_COUNT,\n" +
			"       TMP_V.TOTAL_COST,\n" +
			"       TMP_V.YEAR_ADD_COUNT,\n" +
			"       TMP_V.YEAR_ADD_COST,\n" +
			"       TMP_V.MONTH_ADD_COUNT,\n" +
			"       TMP_V.MONTH_ADD_COST,\n" +
			"       TMP_V.YEAR_DIS_COUNT,\n" +
			"       TMP_V.YEAR_DIS_COST,\n" +
			"       TMP_V.MONTH_DIS_COUNT,\n" +
			"       TMP_V.MONTH_DIS_COST\n" +
			"FROM   (SELECT TMP_FFV.SEGMENT2,\n" +
			"               TMP_FFV.FA_CATEGORY2,\n" +
			"               ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
			"               ISNULL(TOTAL_V.TOTAL_COST, 0) TOTAL_COST,\n" +
			"               ISNULL(YEAR_ADD_V.YEAR_ADD_COUNT, 0) YEAR_ADD_COUNT,\n" +
			"               ISNULL(YEAR_ADD_V.YEAR_ADD_COST, 0) YEAR_ADD_COST,\n" +
			"               ISNULL(MONTH_ADD_V.MONTH_ADD_COUNT, 0) MONTH_ADD_COUNT,\n" +
			"               ISNULL(MONTH_ADD_V.MONTH_ADD_COST, 0) MONTH_ADD_COST,\n" +
			"               ISNULL(YEAR_DIS_V.YEAR_DIS_COUNT, 0) YEAR_DIS_COUNT,\n" +
			"               ISNULL(YEAR_DIS_V.YEAR_DIS_COST, 0) YEAR_DIS_COST,\n" +
			"               ISNULL(MONTH_DIS_V.MONTH_DIS_COUNT, 0) MONTH_DIS_COUNT,\n" +
			"               ISNULL(MONTH_DIS_V.MONTH_DIS_COST, 0) MONTH_DIS_COST\n" +
			"        FROM   (SELECT DISTINCT SUBSTRING(MFFV.FLEX_VALUE, 1, 2) SEGMENT2,\n" +
			"                                TRIM(ISNULL(SUBSTRING(STR_REPLACE(MFFV.DESCRIPTION,\n" +
			"                                                        '－',\n" +
			"                                                        '-'),\n" +
			"                                                1,\n" +
			"                                                CHARINDEX(STR_REPLACE(MFFV.DESCRIPTION,\n" +
			"                                                              '－',\n" +
			"                                                              '-'),\n" +
			"                                                      '-',\n" +
			"                                                      1,\n" +
			"                                                      1) - 1),\n" +
			"                                         DECODE(MFFV.DESCRIPTION,\n" +
			"                                                'SIM卡设备 数据传输设备',\n" +
			"                                                '支撑网计算机系统',\n" +
			"                                                MFFV.DESCRIPTION))) FA_CATEGORY2\n" +
			"                FROM   M_FND_FLEX_VALUES MFFV\n" +
			"                WHERE  MFFV.FLEX_VALUE_SET_ID = 1007342\n" +
			"                       AND SUBSTRING(MFFV.FLEX_VALUE, 1, 2) IS NOT NULL) TMP_FFV,\n" +
			"               (SELECT EFA.ORGANIZATION_ID,\n" +
			"                       SUBSTRING(EFA.SEGMENT2, 1, 2) SEGMENT2,\n" +
			"                       COUNT(EFA.ASSET_ID) TOTAL_COUNT,\n" +
			"                       SUM(EFA.COST) TOTAL_COST\n" +
			"                FROM   ETS_FA_ASSETS EFA\n" +
			"                WHERE  EFA.ORGANIZATION_ID = ?\n" +
			"                       AND EFA.ASSETS_CREATE_DATE(+) <=\n" +
			"                       TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD'))) + 1 -\n" +
			"                       1 / 24 / 60 / 60\n" +
			"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
			"                          SUBSTRING(EFA.SEGMENT2, 1, 2)) TOTAL_V,\n" +
			"               (SELECT EFA.ORGANIZATION_ID,\n" +
			"                       SUBSTRING(EFA.SEGMENT2, 1, 2) SEGMENT2,\n" +
			"                       COUNT(EFA.ASSET_ID) YEAR_ADD_COUNT,\n" +
			"                       SUM(EFA.COST) YEAR_ADD_COST\n" +
			"                FROM   ETS_FA_ASSETS EFA\n" +
			"                WHERE  EFA.ASSETS_CREATE_DATE(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'YEAR')\n" +
			"                       AND EFA.ASSETS_CREATE_DATE(+) <=\n" +
			"                       TO_DATE(TO_CHAR(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                               GETDATE()),\n" +
			"                                           'YYYY') || '-12-31 23:59:59',\n" +
			"                                   'YYYY-MM-DD HH24:MI:SS')\n" +
			"                       AND EFA.ORGANIZATION_ID = ?\n" +
			"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
			"                          SUBSTRING(EFA.SEGMENT2, 1, 2)) YEAR_ADD_V,\n" +
			"               (SELECT EFA.ORGANIZATION_ID,\n" +
			"                       SUBSTRING(EFA.SEGMENT2, 1, 2) SEGMENT2,\n" +
			"                       COUNT(EFA.ASSET_ID) MONTH_ADD_COUNT,\n" +
			"                       SUM(EFA.COST) MONTH_ADD_COST\n" +
			"                FROM   ETS_FA_ASSETS EFA\n" +
			"                WHERE  EFA.ASSETS_CREATE_DATE(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'MONTH')\n" +
			"                       AND\n" +
			"                       EFA.ASSETS_CREATE_DATE(+) <=\n" +
			"                       TRUNC(LAST_DAY(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                          GETDATE()))) + 1 - 1 / 24 / 60 / 60\n" +
			"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                       AND EFA.ORGANIZATION_ID = ?\n" +
			"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
			"                          SUBSTRING(EFA.SEGMENT2, 1, 2)) MONTH_ADD_V,\n" +
			"               (SELECT ADA.ORGANIZATION_ID,\n" +
			"                       SUBSTRING(ADA.SEGMENT2, 1, 2) SEGMENT2,\n" +
			"                       COUNT(1) YEAR_DIS_COUNT,\n" +
			"                       SUM(ADA.RETIRED_COST) YEAR_DIS_COST\n" +
			"                FROM   AMS_DISCARDED_ASSETS ADA\n" +
			"                WHERE  ADA.DATE_RETIRED(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'YEAR')\n" +
			"                       AND ADA.DATE_RETIRED(+) <=\n" +
			"                       TO_DATE(TO_CHAR(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                               GETDATE()),\n" +
			"                                           'YYYY') || '-12-31 23:59:59',\n" +
			"                                   'YYYY-MM-DD HH24:MI:SS')\n" +
			"                       AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                       AND ADA.ORGANIZATION_ID = ?\n" +
			"                GROUP  BY ADA.ORGANIZATION_ID,\n" +
			"                          SUBSTRING(ADA.SEGMENT2, 1, 2)) YEAR_DIS_V,\n" +
			"               (SELECT ADA.ORGANIZATION_ID,\n" +
			"                       SUBSTRING(ADA.SEGMENT2, 1, 2) SEGMENT2,\n" +
			"                       COUNT(1) MONTH_DIS_COUNT,\n" +
			"                       SUM(ADA.RETIRED_COST) MONTH_DIS_COST\n" +
			"                FROM   AMS_DISCARDED_ASSETS ADA\n" +
			"                WHERE  ADA.DATE_RETIRED(+) >=\n" +
			"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
			"                             'MONTH')\n" +
			"                       AND\n" +
			"                       ADA.DATE_RETIRED(+) <=\n" +
			"                       TRUNC(LAST_DAY(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
			"                                          GETDATE()))) + 1 - 1 / 24 / 60 / 60\n" +
			"                       AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
			"                       AND NOT EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
			"                        WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
			"                               AND AICM.NEED_SCAN = 'N')\n" +
			"                       AND ADA.ORGANIZATION_ID = ?\n" +
			"                GROUP  BY ADA.ORGANIZATION_ID,\n" +
			"                          SUBSTRING(ADA.SEGMENT2, 1, 2)) MONTH_DIS_V\n" +
			"        WHERE  TMP_FFV.SEGMENT2 *= TOTAL_V.SEGMENT2\n" +
			"               AND TMP_FFV.SEGMENT2 *= YEAR_ADD_V.SEGMENT2\n" +
			"               AND TMP_FFV.SEGMENT2 *= MONTH_ADD_V.SEGMENT2\n" +
			"               AND TMP_FFV.SEGMENT2 *= YEAR_DIS_V.SEGMENT2\n" +
			"               AND TMP_FFV.SEGMENT2 *= MONTH_DIS_V.SEGMENT2) TMP_V\n" +
			"WHERE  TMP_V.TOTAL_COUNT > 0\n" +
			"       OR TMP_V.YEAR_ADD_COUNT > 0\n" +
			"       OR TMP_V.MONTH_ADD_COUNT > 0\n" +
			"       OR TMP_V.YEAR_DIS_COUNT > 0\n" +
			"       OR TMP_V.MONTH_DIS_COUNT > 0";

		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

/**
	 *
	 * @return SQLModel
	 * @throws CalendarException
	 */
	private SQLModel getAnalyzeModelThree() throws CalendarException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT TMP_V.SEGMENT2 SEGMENT,\n" +
						"       TMP_V.FA_CATEGORY2 FA_CATEGORY,\n" +
						"       TMP_V.TOTAL_COUNT,\n" +
						"       TMP_V.TOTAL_COST,\n" +
						"       TMP_V.YEAR_ADD_COUNT,\n" +
						"       TMP_V.YEAR_ADD_COST,\n" +
						"       TMP_V.MONTH_ADD_COUNT,\n" +
						"       TMP_V.MONTH_ADD_COST,\n" +
						"       TMP_V.YEAR_DIS_COUNT,\n" +
						"       TMP_V.YEAR_DIS_COST,\n" +
						"       TMP_V.MONTH_DIS_COUNT,\n" +
						"       TMP_V.MONTH_DIS_COST\n" +
						"FROM   (SELECT TMP_FFV.SEGMENT2,\n" +
						"               TMP_FFV.FA_CATEGORY2,\n" +
						"               ISNULL(TOTAL_V.TOTAL_COUNT, 0) TOTAL_COUNT,\n" +
						"               ISNULL(TOTAL_V.TOTAL_COST, 0) TOTAL_COST,\n" +
						"               ISNULL(YEAR_ADD_V.YEAR_ADD_COUNT, 0) YEAR_ADD_COUNT,\n" +
						"               ISNULL(YEAR_ADD_V.YEAR_ADD_COST, 0) YEAR_ADD_COST,\n" +
						"               ISNULL(MONTH_ADD_V.MONTH_ADD_COUNT, 0) MONTH_ADD_COUNT,\n" +
						"               ISNULL(MONTH_ADD_V.MONTH_ADD_COST, 0) MONTH_ADD_COST,\n" +
						"               ISNULL(YEAR_DIS_V.YEAR_DIS_COUNT, 0) YEAR_DIS_COUNT,\n" +
						"               ISNULL(YEAR_DIS_V.YEAR_DIS_COST, 0) YEAR_DIS_COST,\n" +
						"               ISNULL(MONTH_DIS_V.MONTH_DIS_COUNT, 0) MONTH_DIS_COUNT,\n" +
						"               ISNULL(MONTH_DIS_V.MONTH_DIS_COST, 0) MONTH_DIS_COST\n" +
						"        FROM   (SELECT DISTINCT SUBSTRING(MFFV.FLEX_VALUE, 1, 5) SEGMENT2,\n" +
						"                                TRIM(ISNULL(DECODE(SUBSTRING(MFFV.FLEX_VALUE, 1, 5),\n" +
						"                                                '07-72',\n" +
						"                                                '物业及配套设备-建筑物',\n" +
						"                                                SUBSTRING(STR_REPLACE(MFFV.DESCRIPTION,\n" +
						"                                                               '－',\n" +
						"                                                               '-'),\n" +
						"                                                       1,\n" +
						"                                                       CHARINDEX(STR_REPLACE(MFFV.DESCRIPTION,\n" +
						"                                                                     '－',\n" +
						"                                                                     '-'),\n" +
						"                                                             '-',\n" +
						"                                                             1,\n" +
						"                                                             2) - 1)),\n" +
						"                                         DECODE(MFFV.DESCRIPTION,\n" +
						"                                                'SIM卡设备 数据传输设备',\n" +
						"                                                '支撑网计算机系统-业务管理设备',\n" +
						"                                                '物业及配套设备-建筑物',\n" +
						"                                                '物业及配套设备-配套设备',\n" +
						"                                                MFFV.DESCRIPTION))) FA_CATEGORY2\n" +
						"                FROM   M_FND_FLEX_VALUES MFFV\n" +
						"                WHERE  MFFV.FLEX_VALUE_SET_ID = 1007342\n" +
						"                       AND SUBSTRING(MFFV.FLEX_VALUE, 1, 5) IS NOT NULL) TMP_FFV,\n" +
						"               (SELECT EFA.ORGANIZATION_ID,\n" +
						"                       SUBSTRING(EFA.SEGMENT2, 1, 5) SEGMENT2,\n" +
						"                       COUNT(EFA.ASSET_ID) TOTAL_COUNT,\n" +
						"                       SUM(EFA.COST) TOTAL_COST\n" +
						"                FROM   ETS_FA_ASSETS EFA\n" +
						"                WHERE  EFA.ORGANIZATION_ID = ?\n" +
						"                       AND EFA.ASSETS_CREATE_DATE(+) <=\n" +
						"                       TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD'))) + 1 -\n" +
						"                       1 / 24 / 60 / 60\n" +
						"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
						"                       AND NOT EXISTS\n" +
						"                 (SELECT NULL\n" +
						"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
						"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
						"                               AND AICM.NEED_SCAN = 'N')\n" +
						"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
						"                          SUBSTRING(EFA.SEGMENT2, 1, 5)) TOTAL_V,\n" +
						"               (SELECT EFA.ORGANIZATION_ID,\n" +
						"                       SUBSTRING(EFA.SEGMENT2, 1, 5) SEGMENT2,\n" +
						"                       COUNT(EFA.ASSET_ID) YEAR_ADD_COUNT,\n" +
						"                       SUM(EFA.COST) YEAR_ADD_COST\n" +
						"                FROM   ETS_FA_ASSETS EFA\n" +
						"                WHERE  EFA.ASSETS_CREATE_DATE(+) >=\n" +
						"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
						"                             'YEAR')\n" +
						"                       AND EFA.ASSETS_CREATE_DATE(+) <=\n" +
						"                       TO_DATE(TO_CHAR(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
						"                                               GETDATE()),\n" +
						"                                           'YYYY') || '-12-31 23:59:59',\n" +
						"                                   'YYYY-MM-DD HH24:MI:SS')\n" +
						"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
						"                       AND NOT EXISTS\n" +
						"                 (SELECT NULL\n" +
						"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
						"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
						"                               AND AICM.NEED_SCAN = 'N')\n" +
						"                       AND EFA.ORGANIZATION_ID = ?\n" +
						"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
						"                          SUBSTRING(EFA.SEGMENT2, 1, 5)) YEAR_ADD_V,\n" +
						"               (SELECT EFA.ORGANIZATION_ID,\n" +
						"                       SUBSTRING(EFA.SEGMENT2, 1, 5) SEGMENT2,\n" +
						"                       COUNT(EFA.ASSET_ID) MONTH_ADD_COUNT,\n" +
						"                       SUM(EFA.COST) MONTH_ADD_COST\n" +
						"                FROM   ETS_FA_ASSETS EFA\n" +
						"                WHERE  EFA.ASSETS_CREATE_DATE(+) >=\n" +
						"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
						"                             'MONTH')\n" +
						"                       AND\n" +
						"                       EFA.ASSETS_CREATE_DATE(+) <=\n" +
						"                       TRUNC(LAST_DAY(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
						"                                          GETDATE()))) + 1 - 1 / 24 / 60 / 60\n" +
						"                       AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
						"                       AND NOT EXISTS\n" +
						"                 (SELECT NULL\n" +
						"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
						"                        WHERE  EFA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
						"                               AND AICM.NEED_SCAN = 'N')\n" +
						"                       AND EFA.ORGANIZATION_ID = ?\n" +
						"                GROUP  BY EFA.ORGANIZATION_ID,\n" +
						"                          SUBSTRING(EFA.SEGMENT2, 1, 5)) MONTH_ADD_V,\n" +
						"               (SELECT ADA.ORGANIZATION_ID,\n" +
						"                       SUBSTRING(ADA.SEGMENT2, 1, 5) SEGMENT2,\n" +
						"                       COUNT(1) YEAR_DIS_COUNT,\n" +
						"                       SUM(ADA.RETIRED_COST) YEAR_DIS_COST\n" +
						"                FROM   AMS_DISCARDED_ASSETS ADA\n" +
						"                WHERE  ADA.DATE_RETIRED(+) >=\n" +
						"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
						"                             'YEAR')\n" +
						"                       AND ADA.DATE_RETIRED(+) <=\n" +
						"                       TO_DATE(TO_CHAR(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
						"                                               GETDATE()),\n" +
						"                                           'YYYY') || '-12-31 23:59:59',\n" +
						"                                   'YYYY-MM-DD HH24:MI:SS')\n" +
						"                       AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
						"                       AND NOT EXISTS\n" +
						"                 (SELECT NULL\n" +
						"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
						"                        WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
						"                               AND AICM.NEED_SCAN = 'N')\n" +
						"                       AND ADA.ORGANIZATION_ID = ?\n" +
						"                GROUP  BY ADA.ORGANIZATION_ID,\n" +
						"                          SUBSTRING(ADA.SEGMENT2, 1, 5)) YEAR_DIS_V,\n" +
						"               (SELECT ADA.ORGANIZATION_ID,\n" +
						"                       SUBSTRING(ADA.SEGMENT2, 1, 5) SEGMENT2,\n" +
						"                       COUNT(1) MONTH_DIS_COUNT,\n" +
						"                       SUM(ADA.RETIRED_COST) MONTH_DIS_COST\n" +
						"                FROM   AMS_DISCARDED_ASSETS ADA\n" +
						"                WHERE  ADA.DATE_RETIRED(+) >=\n" +
						"                       TRUNC(ISNULL(TO_DATE(?, 'YYYY-MM-DD'), GETDATE()),\n" +
						"                             'MONTH')\n" +
						"                       AND\n" +
						"                       ADA.DATE_RETIRED(+) <=\n" +
						"                       TRUNC(LAST_DAY(ISNULL(TO_DATE(?, 'YYYY-MM-DD'),\n" +
						"                                          GETDATE()))) + 1 - 1 / 24 / 60 / 60\n" +
						"                       AND ADA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
						"                       AND NOT EXISTS\n" +
						"                 (SELECT NULL\n" +
						"                        FROM   AMS_ITEM_CATEGORY_MAP AICM\n" +
						"                        WHERE  ADA.SEGMENT2 = AICM.FA_CATEGORY_CODE\n" +
						"                               AND AICM.NEED_SCAN = 'N')\n" +
						"                       AND ADA.ORGANIZATION_ID = ?\n" +
						"                GROUP  BY ADA.ORGANIZATION_ID,\n" +
						"                          SUBSTRING(ADA.SEGMENT2, 1, 5)) MONTH_DIS_V\n" +
						"        WHERE  TMP_FFV.SEGMENT2 *= TOTAL_V.SEGMENT2\n" +
						"               AND TMP_FFV.SEGMENT2 *= YEAR_ADD_V.SEGMENT2\n" +
						"               AND TMP_FFV.SEGMENT2 *= MONTH_ADD_V.SEGMENT2\n" +
						"               AND TMP_FFV.SEGMENT2 *= YEAR_DIS_V.SEGMENT2\n" +
						"               AND TMP_FFV.SEGMENT2 *= MONTH_DIS_V.SEGMENT2) TMP_V\n" +
						"WHERE  TMP_V.TOTAL_COUNT > 0\n" +
						"       OR TMP_V.YEAR_ADD_COUNT > 0\n" +
						"       OR TMP_V.MONTH_ADD_COUNT > 0\n" +
						"       OR TMP_V.YEAR_DIS_COUNT > 0\n" +
						"       OR TMP_V.MONTH_DIS_COUNT > 0";

		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getAssetsCreateDate().getCalendarValue());
		sqlArgs.add(dto.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
