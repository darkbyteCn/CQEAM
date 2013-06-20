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
 * Function		待报废资产专业统计
 * User: 李轶
 * Date: 2009-3-2
 * Time: 21:41:49
 * To change this template use File | Settings | File Templates.
 */
public class ToDiscardedSpecialAssetsReportModel extends AMSSQLProducer {

	public ToDiscardedSpecialAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：获取待报废资产专业统计报表SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String specialAssetsType = dto.getSpecialAssetsType();
        
        /*String sqlStr = "SELECT DECODE(TOTAL.ASSETS_SPECIES, '', '类项为空', TOTAL.ASSETS_SPECIES) ASSETS_SPECIES,\n" +
        "       TOTAL.ASSETS_NAPE,\n" +

        "       TOTAL.SUM_COST,\n" +
        "       TOTAL.DEPRN_RESERVE,\n" +
        "       TOTAL.NET_BOOK_VALUE,\n" +
        "		TOTAL.IMPAIRMENT_RESERVE,\n"	+
        "       TOTAL.LIMIT_VALUE,\n" +
        
        "       TOTAL.SUM_COUNT,\n" +
        "       TOTAL.PTD_DEPRN,\n" +
        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST_TOTAL, 3))) || '%') ASSETS_RATE,\n" +
        
        
        "       '' LAST_MONTH_RATE,\n" +
        "       '' LAST_YEAR_RATE,\n" +
        "       '' THREE_YEER_ONE,\n" +
        "       '' THREE_YEER_TWO,\n" +
        "       '' THREE_YEER_THREE\n" +    
        
        
        
        " FROM   (SELECT SUBSTRING(EFA.FA_CATEGORY2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
        "               SUBSTRING(EFA.FA_CATEGORY2,\n" +
        "                      CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
        "               COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" +
        "               SUM(ZFDD.COST) SUM_COST,\n" +
        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
        "        FROM   ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS           EFA,\n" +
        "               ETS_ITEM_MATCH          EIM,\n" +
        "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
        "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
        "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
        "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
        "				AND AATL.BARCODE = EII.BARCODE "		+
        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "     			AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
        "               AND ZFDD.PERIOD_NAME = ?\n" +
        "        GROUP  BY SUBSTRING(EFA.FA_CATEGORY2,\n" +
        "                         1,\n" +
        "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFA.FA_CATEGORY2,\n" +
        "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
        "                         (CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 2) -\n" +
        "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "        UNION\n" +
        "        SELECT SUBSTRING(EFA.FA_CATEGORY2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
        "               '' ASSETS_NAPE,\n" +
        "               COUNT(EFA.CURRENT_UNITS),\n" +
        "               SUM(ZFDD.COST) SUM_COST,\n" +
        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE) NET_BOOK_VALUE,\n" +
        "               SUM(ZFDD.COST - ZFDD.DEPRN_RESERVE - ZFDD.IMPAIRMENT_RESERVE) LIMIT_VALUE,\n" +
        "               SUM(ZFDD.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
        "				SUM(ZFDD.IMPAIRMENT_RESERVE) IMPAIRMENT_RESERVE,\n"	+
        "               SUM(ZFDD.PTD_DEPRN) PTD_DEPRN\n" +
        "        FROM   ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS           EFA,\n" +
        "               ETS_ITEM_MATCH          EIM,\n" +
        "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
        "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
        "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
        "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n"	+
        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
        "				AND AATL.BARCODE = EII.BARCODE "		+
        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "      			AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
        "               AND ZFDD.PERIOD_NAME = ?\n" +
        "        GROUP  BY SUBSTRING(EFA.FA_CATEGORY2,\n" +
        "                         1,\n" +
        "                         CHARINDEX(EFA.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) TOTAL,\n" +
        "       (SELECT SUM(ZFDD.COST) SUM_COST_TOTAL\n" +
        "        FROM   ETS_FA_ASSETS           EFA,\n" +
        "               ETS_ITEM_INFO           EII,\n" +
        "               ETS_ITEM_MATCH          EIM,\n" +
        "               SOA.ZTE_FA_DEPRN_DETAIL ZFDD,\n" +
        "				AMS_ASSETS_TRANS_HEADER AATH,\n"	+
        "				AMS_ASSETS_TRANS_LINE   AATL \n"	+
        "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
        "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EII.ITEM_STATUS = 'TO_DISCARD'\n" +
        "               AND EFA.ASSET_ID *= ZFDD.ASSET_ID\n" +
        "				AND AATH.TRANS_ID = AATL.TRANS_ID"	+
        "				AND AATL.BARCODE = EII.BARCODE "		+
        "               AND ZFDD.PERIOD_NAME = ?\n" +
        "               AND AATH.LAST_UPDATE_DATE BETWEEN\n" +			        
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL2\n" +
        "ORDER  BY TOTAL.ASSETS_SPECIES,\n" +
        "          TOTAL.ASSETS_NAPE DESC";
		sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
		sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getPeriodName());
		sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
		sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getContentCode());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getPeriodName());
		sqlArgs.add(dto.getPeriodName());
		sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
		sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());*/
        
        String sqlStr = "SELECT TOTAL.ASSETS_SPECIES,\n" +
        "       TOTAL.ASSETS_NAPE,\n" +
        "       TOTAL.SUM_COUNT,\n" +
        "       TOTAL.SUM_COST,\n" +
        "       TOTAL.DEPRN_RESERVE,\n" +
        "       TOTAL.NET_BOOK_VALUE,\n" +
        "       TOTAL.IMPAIRMENT_RESERVE,\n" +
        "       TOTAL.LIMIT_VALUE,\n" +
        "       TOTAL.PTD_DEPRN,\n" +
        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_COST.TOTAL),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3))) || '%') ASSETS_RATE,\n" +
        "       (DECODE(SUM_LAST_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3)))) || '%') LAST_YEAR_RATE,\n" +
            "       \n" +
            "       (DECODE(SUM_LAST_FOUR_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_THREE_YEAR_COST.SUM_COST / SUM_LAST_FOUR_YEAR_COST.SUM_COST - 100, 3)))) || '%') THREE_YEER_THREE_RATE,\n" +
            "               \n" +
            "       (DECODE(SUM_LAST_THREE_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_TWO_YEAR_COST.SUM_COST / SUM_LAST_THREE_YEAR_COST.SUM_COST - 100, 3)))) || '%') THREE_YEER_TWO_RATE,\n" +
            "               \n" +
            "       (DECODE(SUM_LAST_TWO_YEAR_COST.SUM_COST, NULL , '0', DECODE(TRUNC(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * SUM_LAST_ONE_YEAR_COST.SUM_COST / SUM_LAST_TWO_YEAR_COST.SUM_COST - 100, 3)))) || '%')  THREE_YEER_ONE_RATE\n" +
        " FROM   (SELECT SUM(EFAHR.COST) TOTAL\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP  EFAHR,\n" +
        "               ETS_ITEM_INFO  		   EII,\n" +
        "               ETS_ITEM_MATCH 		   EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'TO_DISCARD'" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)) SUM_COST,\n" +   
        
        "       (SELECT SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
        "               SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
        "                      CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUM(EFAHR.NET_ASSET_VALUE) NET_BOOK_VALUE,\n" +
        "               SUM(EFAHR.DEPRN_COST) LIMIT_VALUE,\n" +
        "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIRMENT_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_AMOUNT) PTD_DEPRN,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+          
        "  			AND EFAHR.PERIOD_NAME = ? \n" +	//当前会计期
        "           AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "         GROUP BY SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
        "                         1,\n" +
        "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
        "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
        "                         (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
        "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
        "               '' ASSETS_NAPE,\n" +
        "               COUNT(EII.ITEM_QTY),\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUM(EFAHR.NET_ASSET_VALUE) NET_BOOK_VALUE,\n" +
        "               SUM(EFAHR.DEPRN_COST) LIMIT_VALUE,\n" +
        "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIRMENT_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
        "               SUM(EFAHR.DEPRN_AMOUNT) PTD_DEPRN,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"+        
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			//当前会计期
        "           AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "         GROUP BY SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
        "                         1,\n" +
        "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) TOTAL            \n" +
        
        "    LEFT JOIN \n" +
        
        "     (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+     
        "  			AND EFAHR.PERIOD_NAME = ? \n" +		//去年同期会计期
        "           AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY),\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+           
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			//去年同期会计期
        "           AND EIIAC.CREATION_DATE BETWEEN\n" +
        "               TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
        "               TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) SUM_LAST_YEAR_COST\n" +
        "     ON TOTAL.ASSETS_SPECIES_CODE = SUM_LAST_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND TOTAL.ASSETS_NAPE_CODE = SUM_LAST_YEAR_COST.ASSETS_NAPE_CODE\n" +
        
"    LEFT JOIN \n" +
        
        "     (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+     
        "  			AND EFAHR.PERIOD_NAME = ? \n" +		
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY),\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+           
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) SUM_LAST_FOUR_YEAR_COST\n" +
        "     ON SUM_LAST_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_FOUR_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_FOUR_YEAR_COST.ASSETS_NAPE_CODE\n" +
        
"    LEFT JOIN \n" +
        
        "     (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+     
        "  			AND EFAHR.PERIOD_NAME = ? \n" +		
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY),\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+           
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) SUM_LAST_THREE_YEAR_COST\n" +
        "     ON SUM_LAST_FOUR_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_THREE_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_FOUR_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_THREE_YEAR_COST.ASSETS_NAPE_CODE\n" +
        
"    LEFT JOIN \n" +
        
        "     (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+     
        "  			AND EFAHR.PERIOD_NAME = ? \n" +		
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY),\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+           
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) SUM_LAST_TWO_YEAR_COST\n" +
        "     ON SUM_LAST_THREE_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_TWO_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_THREE_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_TWO_YEAR_COST.ASSETS_NAPE_CODE\n" +
        
"    LEFT JOIN \n" +
        
        "     (SELECT COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+     
        "  			AND EFAHR.PERIOD_NAME = ? \n" +		
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')),\n" +
        "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
        "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-'))\n" +
        "                  \n" +
        "        UNION\n" +
        "        SELECT COUNT(EII.ITEM_QTY),\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
        "               '0' ASSETS_NAPE_CODE\n" +
        "          FROM ETS_ITEM_INFO           EII,\n" +
        "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC,\n"	+
        "               ETS_ITEM_MATCH          EIM\n" +
        "         WHERE EII.SYSTEMID = EIM.SYSTEMID\n" +
        "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "           AND EII.FINANCE_PROP = 'ASSETS'\n" +
        "			AND EII.ITEM_STATUS = 'TO_DISCARD'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+           
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			
        "			AND EIIAC.CREATION_DATE >= TO_DATE(?, 'YY-MM-DD')"	+
        "			AND EIIAC.CREATION_DATE < TO_DATE(?, 'YY-MM-DD')"	+
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) SUM_LAST_ONE_YEAR_COST\n" +
        "     ON SUM_LAST_ONE_YEAR_COST.ASSETS_SPECIES_CODE = SUM_LAST_TWO_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND SUM_LAST_ONE_YEAR_COST.ASSETS_NAPE_CODE = SUM_LAST_TWO_YEAR_COST.ASSETS_NAPE_CODE\n" +
        
        "  WHERE  " + SyBaseSQLUtil.isNotNull("TOTAL.ASSETS_SPECIES") + "  "	+
        "      ORDER BY TOTAL.ASSETS_SPECIES_CODE, TOTAL.ASSETS_NAPE_CODE";
        
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
		
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
        sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
        sqlArgs.add(dto.getYear() + "-" + dto.getMonth() + "-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
        sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
        sqlArgs.add(dto.getLastYear() + "-" + dto.getMonth() + "-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastFourYear() + "-01-01");
        sqlArgs.add(dto.getLastThreeYear() + "-01-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastFourYear() + "-01-01");
        sqlArgs.add(dto.getLastThreeYear() + "-01-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastThreeYear() + "-01-01");
        sqlArgs.add(dto.getLastTwoYear() + "-01-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastThreeYear() + "-01-01");
        sqlArgs.add(dto.getLastTwoYear() + "-01-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastTwoYear() + "-01-01");
        sqlArgs.add(dto.getLastYear() + "-01-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastTwoYear() + "-01-01");
        sqlArgs.add(dto.getLastYear() + "-01-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastYear() + "-01-01");
        sqlArgs.add(dto.getYear() + "-01-01");
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
        sqlArgs.add(dto.getLastYear() + "-01-01");
        sqlArgs.add(dto.getYear() + "-01-01");
        
        sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
