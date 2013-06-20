package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * User: 李轶
 * Date: 2009-6-2
 * Time: 15:15:55
 */
public class DHCategoryAssetsReportModel extends AMSSQLProducer {

	public DHCategoryAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：获取低值易耗资产目录构成分布统计报表SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String specialAssetsType = dto.getSpecialAssetsType();
        String sqlStr = "SELECT DECODE(TOTAL.ASSETS_SPECIES, '', '类项为空', TOTAL.ASSETS_SPECIES) ASSETS_SPECIES,\n" +
                     "       TOTAL.ASSETS_NAPE,\n" +
                     "		 TOTAL.ASSETS_ORDER,\n"	+
                     "		 TOTAL.ASSETS_SECTION,\n"	+
                     "       TOTAL.SUM_COUNT,\n" +
                     "		 TOTAL.SUM_COST,\n"	+
                     "       (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL_COUNT),\n" +
                     "               0,\n" +
                     "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL_COUNT, 3),\n" +
                     "                       '.',\n" +
                     "                       '0.'),\n" +
                     "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL_COUNT, 3))) || '%') ASSETS_RATE,\n" +
                     "       \n" +
                     "       (DECODE(SUM_LAST_MONTH_COST.SUM_COUNT, NULL, '0', DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_LAST_MONTH_COST.SUM_COUNT - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_MONTH_COST.SUM_COUNT - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_MONTH_COST.SUM_COUNT - 100, 3)))) || '%') LAST_MONTH_RATE,\n" +
            "               \n" +
            "       (DECODE( SUM_LAST_YEAR_COST.SUM_COUNT, NULL, '0', DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_LAST_YEAR_COST.SUM_COUNT - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_YEAR_COST.SUM_COUNT - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_YEAR_COST.SUM_COUNT - 100, 3)))) || '%') LAST_YEAR_RATE,\n" +
            "       \n" +
            "       (DECODE(SUM_LAST_FOUR_YEAR_COST.SUM_COUNT, NULL, '0', DECODE(TRUNC(100 * SUM_LAST_THREE_YEAR_COST.SUM_COUNT / SUM_LAST_FOUR_YEAR_COST.SUM_COUNT - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COUNT / SUM_LAST_FOUR_YEAR_COST.SUM_COUNT - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COUNT / SUM_LAST_FOUR_YEAR_COST.SUM_COUNT - 100, 3)))) || '%') THREE_YEER_THREE_RATE,\n" +
            "               \n" +
            "       (DECODE(SUM_LAST_THREE_YEAR_COST.SUM_COUNT, NULL, '0', DECODE(TRUNC(100 * SUM_LAST_TWO_YEAR_COST.SUM_COUNT / SUM_LAST_THREE_YEAR_COST.SUM_COUNT - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COUNT / SUM_LAST_THREE_YEAR_COST.SUM_COUNT - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COUNT / SUM_LAST_THREE_YEAR_COST.SUM_COUNT - 100, 3)))) || '%') THREE_YEER_TWO_RATE,\n" +
            "               \n" +
            "       (DECODE(SUM_LAST_TWO_YEAR_COST.SUM_COUNT, NULL, '0', DECODE(TRUNC(100 * SUM_LAST_ONE_YEAR_COST.SUM_COUNT / SUM_LAST_TWO_YEAR_COST.SUM_COUNT - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COUNT / SUM_LAST_TWO_YEAR_COST.SUM_COUNT - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COUNT / SUM_LAST_TWO_YEAR_COST.SUM_COUNT - 100, 3)))) || '%')  THREE_YEER_ONE_RATE \n" +
                     " FROM (SELECT COUNT(EIIHR.ITEM_QTY) TOTAL_COUNT,\n" +
                     "				SUM(EIIHR.PRICE)	  TOTAL_COST\n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'"		+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)" +
                     "				 AND EIIHR.PERIOD_NAME = ?) SUM_COST, \n" + 
                     
                     "		(SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "						(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')) ASSETS_ORDER,\n"	+
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3)) ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE)	SUM_COST \n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "							(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')),\n"	+
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3))\n"+
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               ' ' ASSETS_NAPE,\n" +
                     "				 ' ' ASSETS_ORDER,\n"	+
                     "				 ' ' ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE) SUM_COST "	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) TOTAL,\n" +    
                     
                     "		(SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "						(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')) ASSETS_ORDER,\n"	+
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3)) ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE)	SUM_COST \n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "							(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')),\n"	+
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3))\n"+
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               ' ' ASSETS_NAPE,\n" +
                     "				 ' ' ASSETS_ORDER,\n"	+
                     "				 ' ' ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE) SUM_COST "	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) SUM_LAST_MONTH_COST,\n" +
                     
                     "		(SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "						(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')) ASSETS_ORDER,\n"	+
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3)) ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE)	SUM_COST \n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "							(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')),\n"	+
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3))\n"+
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               ' ' ASSETS_NAPE,\n" +
                     "				 ' ' ASSETS_ORDER,\n"	+
                     "				 ' ' ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE) SUM_COST "	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) SUM_LAST_YEAR_COST,\n" +
                     
                     "		(SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "						(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')) ASSETS_ORDER,\n"	+
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3)) ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE)	SUM_COST \n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "							(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')),\n"	+
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3))\n"+
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               ' ' ASSETS_NAPE,\n" +
                     "				 ' ' ASSETS_ORDER,\n"	+
                     "				 ' ' ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE) SUM_COST "	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) SUM_LAST_FOUR_YEAR_COST,\n" +
                     
                     "		(SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "						(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')) ASSETS_ORDER,\n"	+
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3)) ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE)	SUM_COST \n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "							(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')),\n"	+
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3))\n"+
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               ' ' ASSETS_NAPE,\n" +
                     "				 ' ' ASSETS_ORDER,\n"	+
                     "				 ' ' ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE) SUM_COST "	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) SUM_LAST_THREE_YEAR_COST,\n" +
                     
                     "		(SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "						(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')) ASSETS_ORDER,\n"	+
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3)) ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE)	SUM_COST \n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "							(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')),\n"	+
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3))\n"+
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               ' ' ASSETS_NAPE,\n" +
                     "				 ' ' ASSETS_ORDER,\n"	+
                     "				 ' ' ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE) SUM_COST "	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) SUM_LAST_TWO_YEAR_COST,\n" +
                     
                     "		(SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "						(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')) ASSETS_ORDER,\n"	+
                     "				 SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3)) ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE)	SUM_COST \n"	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
                     "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
                     "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) + DATALENGTH('-'),\n"	+
                     "							(CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) - CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2)) - DATALENGTH('-')),\n"	+
                     "					SUBSTRING(EIIHR.CONTENT_NAME, CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3) + DATALENGTH('-'), CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 3))\n"+
                     "        UNION\n" +
                     "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                      1,\n" +
                     "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
                     "               ' ' ASSETS_NAPE,\n" +
                     "				 ' ' ASSETS_ORDER,\n"	+
                     "				 ' ' ASSETS_SECTION,\n"	+
                     "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
                     "				 SUM(EIIHR.PRICE) SUM_COST "	+
                     "        FROM   ETS_ITEM_INFO_HIS_REP  EIIHR\n" +
                     "        WHERE  EIIHR.FINANCE_PROP = 'DH_ASSETS'\n"		+
                     "				 AND EIIHR.PERIOD_NAME = ? \n"	+
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n" +
                     "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n" +
                     "        GROUP  BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
                     "                         1,\n" +
                     "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) SUM_LAST_ONE_YEAR_COST\n" +
                     
                     " WHERE TOTAL.ASSETS_SPECIES *= SUM_LAST_MONTH_COST.ASSETS_SPECIES"	+
                     "	 AND TOTAL.ASSETS_NAPE *= SUM_LAST_MONTH_COST.ASSETS_NAPE"	+
                     "	 AND TOTAL.ASSETS_ORDER *= SUM_LAST_MONTH_COST.ASSETS_ORDER"	+
                     "	 AND TOTAL.ASSETS_SECTION *= SUM_LAST_MONTH_COST.ASSETS_SECTION"	+
                     
                     "	 AND TOTAL.ASSETS_SPECIES *= SUM_LAST_YEAR_COST.ASSETS_SPECIES"	+
                     "	 AND TOTAL.ASSETS_NAPE *= SUM_LAST_YEAR_COST.ASSETS_NAPE"	+
                     "	 AND TOTAL.ASSETS_ORDER *= SUM_LAST_YEAR_COST.ASSETS_ORDER"	+
                     "	 AND TOTAL.ASSETS_SECTION *= SUM_LAST_YEAR_COST.ASSETS_SECTION"	+
                     
                     "	 AND TOTAL.ASSETS_SPECIES *= SUM_LAST_FOUR_YEAR_COST.ASSETS_SPECIES"	+
                     "	 AND TOTAL.ASSETS_NAPE *= SUM_LAST_FOUR_YEAR_COST.ASSETS_NAPE"	+
                     "	 AND TOTAL.ASSETS_ORDER *= SUM_LAST_FOUR_YEAR_COST.ASSETS_ORDER"	+
                     "	 AND TOTAL.ASSETS_SECTION *= SUM_LAST_FOUR_YEAR_COST.ASSETS_SECTION"	+
                     
                     "	 AND TOTAL.ASSETS_SPECIES *= SUM_LAST_THREE_YEAR_COST.ASSETS_SPECIES"	+
                     "	 AND TOTAL.ASSETS_NAPE *= SUM_LAST_THREE_YEAR_COST.ASSETS_NAPE"	+
                     "	 AND TOTAL.ASSETS_ORDER *= SUM_LAST_THREE_YEAR_COST.ASSETS_ORDER"	+
                     "	 AND TOTAL.ASSETS_SECTION *= SUM_LAST_THREE_YEAR_COST.ASSETS_SECTION"	+
                     
                     "	 AND TOTAL.ASSETS_SPECIES *= SUM_LAST_TWO_YEAR_COST.ASSETS_SPECIES"	+
                     "	 AND TOTAL.ASSETS_NAPE *= SUM_LAST_TWO_YEAR_COST.ASSETS_NAPE"	+
                     "	 AND TOTAL.ASSETS_ORDER *= SUM_LAST_TWO_YEAR_COST.ASSETS_ORDER"	+
                     "	 AND TOTAL.ASSETS_SECTION *= SUM_LAST_TWO_YEAR_COST.ASSETS_SECTION"	+
                     
                     "	 AND TOTAL.ASSETS_SPECIES *= SUM_LAST_ONE_YEAR_COST.ASSETS_SPECIES"	+
                     "	 AND TOTAL.ASSETS_NAPE *= SUM_LAST_ONE_YEAR_COST.ASSETS_NAPE"	+
                     "	 AND TOTAL.ASSETS_ORDER *= SUM_LAST_ONE_YEAR_COST.ASSETS_ORDER"	+
                     "	 AND TOTAL.ASSETS_SECTION *= SUM_LAST_ONE_YEAR_COST.ASSETS_SECTION"	+
                     "   AND  " + SyBaseSQLUtil.isNotNull("TOTAL.ASSETS_SPECIES") + "  " +
                     
                    /* " WHERE SUM_LAST_MONTH_COST.ASSETS_SPECIES *= TOTAL.ASSETS_SPECIES"	+
                     "	 AND SUM_LAST_MONTH_COST.ASSETS_NAPE *= TOTAL.ASSETS_NAPE"	+
                     "	 AND SUM_LAST_MONTH_COST.ASSETS_ORDER *= TOTAL.ASSETS_ORDER"	+
                     "	 AND SUM_LAST_MONTH_COST.ASSETS_SECTION *= TOTAL.ASSETS_SECTION"	+
                     
                     "	 AND SUM_LAST_YEAR_COST.ASSETS_SPECIES *= TOTAL.ASSETS_SPECIES"	+
                     "	 AND SUM_LAST_YEAR_COST.ASSETS_NAPE *= TOTAL.ASSETS_NAPE"	+
                     "	 AND SUM_LAST_YEAR_COST.ASSETS_ORDER *= TOTAL.ASSETS_ORDER"	+
                     "	 AND SUM_LAST_YEAR_COST.ASSETS_SECTION *= TOTAL.ASSETS_SECTION"	+
                     
                     "	 AND SUM_LAST_FOUR_YEAR_COST.ASSETS_SPECIES *= TOTAL.ASSETS_SPECIES"	+
                     "	 AND SUM_LAST_FOUR_YEAR_COST.ASSETS_NAPE *= TOTAL.ASSETS_NAPE"	+
                     "	 AND SUM_LAST_FOUR_YEAR_COST.ASSETS_ORDER *= TOTAL.ASSETS_ORDER"	+
                     "	 AND SUM_LAST_FOUR_YEAR_COST.ASSETS_SECTION *= TOTAL.ASSETS_SECTION"	+
                     
                     "	 AND SUM_LAST_THREE_YEAR_COST.ASSETS_SPECIES *= TOTAL.ASSETS_SPECIES"	+
                     "	 AND SUM_LAST_THREE_YEAR_COST.ASSETS_NAPE *= TOTAL.ASSETS_NAPE"	+
                     "	 AND SUM_LAST_THREE_YEAR_COST.ASSETS_ORDER *= TOTAL.ASSETS_ORDER"	+
                     "	 AND SUM_LAST_THREE_YEAR_COST.ASSETS_SECTION *= TOTAL.ASSETS_SECTION"	+
                     
                     "	 AND SUM_LAST_TWO_YEAR_COST.ASSETS_SPECIES *= TOTAL.ASSETS_SPECIES"	+
                     "	 AND SUM_LAST_TWO_YEAR_COST.ASSETS_NAPE *= TOTAL.ASSETS_NAPE"	+
                     "	 AND SUM_LAST_TWO_YEAR_COST.ASSETS_ORDER *= TOTAL.ASSETS_ORDER"	+
                     "	 AND SUM_LAST_TWO_YEAR_COST.ASSETS_SECTION *= TOTAL.ASSETS_SECTION"	+
                     
                     "	 AND SUM_LAST_ONE_YEAR_COST.ASSETS_SPECIES *= TOTAL.ASSETS_SPECIES"	+
                     "	 AND SUM_LAST_ONE_YEAR_COST.ASSETS_NAPE *= TOTAL.ASSETS_NAPE"	+
                     "	 AND SUM_LAST_ONE_YEAR_COST.ASSETS_ORDER *= TOTAL.ASSETS_ORDER"	+
                     "	 AND SUM_LAST_ONE_YEAR_COST.ASSETS_SECTION *= TOTAL.ASSETS_SECTION"	+*/
                     " ORDER  BY TOTAL.ASSETS_SPECIES,\n" +
                     "          TOTAL.ASSETS_NAPE ";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getPeriodNameByHisRep());
        
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());

		sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastMonthPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastMonthPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
		sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		
        sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
