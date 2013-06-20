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
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-2
 * Time: 21:41:49
 * To change this template use File | Settings | File Templates.
 */
public class CTSpecialAssetsReportModel extends AMSSQLProducer {

	public CTSpecialAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：获取盘点统计报表SQL		村通资产专业统计
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String sqlStr = "SELECT DECODE(TOTAL.ASSETS_SPECIES, '', '类项为空', TOTAL.ASSETS_SPECIES) ASSETS_SPECIES,\n" +
        "       TOTAL.ASSETS_NAPE,\n" +
        "       TOTAL.SUM_COUNT,\n" +
        "		TOTAL.SUM_COST,\n"	+
         "       (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL_COUNT),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL_COUNT, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL_COUNT, 3))) || '%') ASSETS_RATE_COUNT,\n" +
        "       (DECODE(TOTAL.SUM_COST, NULL , '0', DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_COST.TOTAL),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3)))) || '%') ASSETS_RATE,\n" +
        "       \n" +
        "       (DECODE(SUM_LAST_MONTH_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_LAST_MONTH_COST.SUM_COST - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_MONTH_COST.SUM_COST - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_MONTH_COST.SUM_COST - 100, 3)))) || '%') LAST_MONTH_RATE,\n" +
        "       (DECODE(SUM_LAST_MONTH_COST.SUM_COUNT, NULL , '0', DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_LAST_MONTH_COST.SUM_COUNT - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_MONTH_COST.SUM_COUNT - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_MONTH_COST.SUM_COUNT - 100, 3)))) || '%') LAST_MONTH_RATE_COUNT,\n" +
        
        "               \n" +
        "       (DECODE(SUM_LAST_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3)))) || '%') LAST_YEAR_RATE,\n" +
        "       (DECODE(SUM_LAST_YEAR_COST.SUM_COUNT, NULL , '0', DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_LAST_YEAR_COST.SUM_COUNT - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_YEAR_COST.SUM_COUNT - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_LAST_YEAR_COST.SUM_COUNT - 100, 3)))) || '%') LAST_YEAR_RATE_COUNT,\n" +
        
        "       \n" +
        "       (DECODE(SUM_LAST_FOUR_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100, 3)))) || '%') THREE_YEER_THREE_RATE,\n" +
        "       (DECODE(SUM_LAST_FOUR_YEAR_COST.SUM_COUNT, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_THREE_YEAR_COST.SUM_COUNT / SUM_LAST_FOUR_YEAR_COST.SUM_COUNT - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COUNT / SUM_LAST_FOUR_YEAR_COST.SUM_COUNT - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COUNT / SUM_LAST_FOUR_YEAR_COST.SUM_COUNT - 100, 3)))) || '%') THREE_YEER_THREE_RATE_COUNT,\n" +
        
        "               \n" +
        "       (DECODE(SUM_LAST_THREE_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100, 3)))) || '%') THREE_YEER_TWO_RATE,\n" +
        "       (DECODE(SUM_LAST_THREE_YEAR_COST.SUM_COUNT, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_TWO_YEAR_COST.SUM_COUNT / SUM_LAST_THREE_YEAR_COST.SUM_COUNT - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COUNT / SUM_LAST_THREE_YEAR_COST.SUM_COUNT - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COUNT / SUM_LAST_THREE_YEAR_COST.SUM_COUNT - 100, 3)))) || '%') THREE_YEER_TWO_RATE_COUNT,\n" +
        "               \n" +
        "       (DECODE(SUM_LAST_TWO_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100, 3)))) || '%')  THREE_YEER_ONE_RATE, \n"	+

        "       (DECODE(SUM_LAST_TWO_YEAR_COST.SUM_COUNT , NULL , '0', DECODE(TRUNC(100 * SUM_LAST_ONE_YEAR_COST.SUM_COUNT / SUM_LAST_TWO_YEAR_COST.SUM_COUNT - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COUNT / SUM_LAST_TWO_YEAR_COST.SUM_COUNT - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COUNT / SUM_LAST_TWO_YEAR_COST.SUM_COUNT - 100, 3)))) || '%')  THREE_YEER_ONE_RATE_COUNT \n"	+
        "  FROM \n" +
        "       (SELECT SUM(EIIHR.PRICE) 	  TOTAL," +
        "				COUNT(EIIHR.ITEM_QTY) TOTAL_COUNT\n" +
        "        FROM   ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "        WHERE  EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)"	+
        "  				AND EIIHR.PERIOD_NAME = ? ) SUM_COST,\n" +	               
        "       (SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
        "               SUBSTRING(EIIHR.CONTENT_NAME,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
        "				SUM(EIIHR.PRICE) SUM_COST,"	+
        "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+        
        "  			AND EIIHR.PERIOD_NAME = ? \n" +	//当前会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
        "                         1,\n" +
        "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_NAME,\n" +
        "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
        "                         (CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 2) -\n" +
        "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUBSTRING(EIIHR.CONTENT_NAME,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
        "               '' ASSETS_NAPE,\n" +
        "				SUM(EIIHR.PRICE) SUM_COST,\n"	+
        "               COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +			//当前会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_NAME,\n" +
        "                         1,\n" +
        "                         CHARINDEX(EIIHR.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-'))) TOTAL            \n" +
        
        "    LEFT JOIN \n" +
        
        "       (SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +	//上月会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +	//上月会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-'))) SUM_LAST_MONTH_COST\n" +
        "     ON TOTAL.ASSETS_SPECIES_CODE = SUM_LAST_MONTH_COST.ASSETS_SPECIES_CODE\n" +
        "     AND TOTAL.ASSETS_NAPE_CODE = SUM_LAST_MONTH_COST.ASSETS_NAPE_CODE\n" +
        "     LEFT JOIN \n" +
        
        "       (SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +		//去年同期会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+     
        "  			AND EIIHR.PERIOD_NAME = ? \n" +			//去年同期会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-'))) SUM_LAST_YEAR_COST\n" +
        "     ON SUM_LAST_MONTH_COST.ASSETS_SPECIES_CODE = SUM_LAST_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_MONTH_COST.ASSETS_NAPE_CODE = SUM_LAST_YEAR_COST.ASSETS_NAPE_CODE\n" +
        "     LEFT JOIN \n" +
        
        "       (SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +			//4年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +				//4年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-'))) SUM_LAST_FOUR_YEAR_COST \n" +
        "     ON SUM_LAST_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_FOUR_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_FOUR_YEAR_COST.ASSETS_NAPE_CODE\n" +
        "     LEFT JOIN \n" +
        
        "       (SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +					//3年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +				//3年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-'))) SUM_LAST_THREE_YEAR_COST \n" +
        "     ON SUM_LAST_FOUR_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_THREE_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_FOUR_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_THREE_YEAR_COST.ASSETS_NAPE_CODE\n" +
        "     LEFT JOIN \n" +

        "       (SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +					//2年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +						//2年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-'))) SUM_LAST_TWO_YEAR_COST \n" +
        "     ON SUM_LAST_THREE_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_TWO_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_THREE_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_TWO_YEAR_COST.ASSETS_NAPE_CODE\n" +
        "      \n" +
        "     LEFT JOIN \n" +
        "     \n" +
        "       (SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +						//1年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 2) -\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUM(EIIHR.PRICE) SUM_COST,\n"  +
        "				COUNT(EIIHR.ITEM_QTY) SUM_COUNT,\n" +
        "               SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO_HIS_REP   EIIHR\n" +
        "         WHERE EIIHR.FINANCE_PROP = 'DG_ASSETS'\n" +
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EIIHR.CONTENT_CODE LIKE ?)\n"	+            
        "  			AND EIIHR.PERIOD_NAME = ? \n" +						//1年前12月份会计期
        "         GROUP BY SUBSTRING(EIIHR.CONTENT_CODE,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EIIHR.CONTENT_CODE, '-', 1) - DATALENGTH('-'))) SUM_LAST_ONE_YEAR_COST \n" +
        "     ON SUM_LAST_TWO_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_ONE_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_TWO_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_ONE_YEAR_COST.ASSETS_NAPE_CODE\n" +
        " WHERE  " + SyBaseSQLUtil.isNotNull("TOTAL.ASSETS_SPECIES") + "  "	+
        "      ORDER BY TOTAL.ASSETS_SPECIES_CODE, TOTAL.ASSETS_NAPE_CODE";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastMonthPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastMonthPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
		
		sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
        
        /*String sqlStr = "SELECT DECODE(TOTAL.ASSETS_SPECIES, '', '类项为空', TOTAL.ASSETS_SPECIES) ASSETS_SPECIES,\n" +
				        "       TOTAL.ASSETS_NAPE,\n" +
				        "       TOTAL.SUM_NAPE,\n" +
				        "       (DECODE(TRUNC(100 * TOTAL.SUM_NAPE / TOTAL2.SUM_QTY),\n" +
				        "               0,\n" +
				        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_NAPE / TOTAL2.SUM_QTY, 3),\n" +
				        "                       '.',\n" +
				        "                       '0.'),\n" +
				        "               TO_CHAR(ROUND(100 * TOTAL.SUM_NAPE / TOTAL2.SUM_QTY, 3))) || '%') ASSETS_RATE\n" +
				        " FROM   (SELECT SUBSTRING(EII.CONTENT_NAME,\n" +
				        "                      1,\n" +
				        "                      CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
				        "               SUBSTRING(EII.CONTENT_NAME,\n" +
				        "                      CHARINDEX(EII.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
				        "                      (CHARINDEX(EII.CONTENT_NAME, '-', 1, 2) -\n" +
				        "                      CHARINDEX(EII.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
				        "               COUNT(EIIHR.ITEM_QTY) SUM_NAPE\n" +
				        "        FROM   ETS_ITEM_INFO  EII\n" +
				        "        WHERE  EII.FINANCE_PROP = 'DG_ASSETS'\n"		+
				        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n" +
				        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
				        "        GROUP  BY SUBSTRING(EII.CONTENT_NAME,\n" +
				        "                         1,\n" +
				        "                         CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-')),\n" +
				        "                  SUBSTRING(EII.CONTENT_NAME,\n" +
				        "                         CHARINDEX(EII.CONTENT_NAME, '-', 1) + DATALENGTH('-'),\n" +
				        "                         (CHARINDEX(EII.CONTENT_NAME, '-', 1, 2) -\n" +
				        "                         CHARINDEX(EII.CONTENT_NAME, '-', 1, 1)) - DATALENGTH('-'))\n" +
				        "        UNION\n" +
				        "        SELECT SUBSTRING(EII.CONTENT_NAME,\n" +
				        "                      1,\n" +
				        "                      CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
				        "               '' ASSETS_NAPE,\n" +
				        "               COUNT(EIIHR.ITEM_QTY)\n" +
				        "        FROM   ETS_ITEM_INFO  EII\n" +
				        "        WHERE  EII.FINANCE_PROP = 'DG_ASSETS'\n"		+
				        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n" +
				        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
				        "        GROUP  BY SUBSTRING(EII.CONTENT_NAME,\n" +
				        "                         1,\n" +
				        "                         CHARINDEX(EII.CONTENT_NAME, '-', 1) - DATALENGTH('-'))) TOTAL,\n" +
				        "       (SELECT COUNT(EIIHR.ITEM_QTY) SUM_QTY\n" +
				        "        FROM   ETS_ITEM_INFO  EII\n" +
				        "        WHERE  EII.FINANCE_PROP = 'DG_ASSETS'"		+
				        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)) TOTAL2\n" +
				        " ORDER  BY TOTAL.ASSETS_SPECIES,\n" +
				        "          TOTAL.ASSETS_NAPE DESC  ";
			sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentCode());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getContentCode());
			sqlArgs.add(dto.getContentCode());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());*/
        
        sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
