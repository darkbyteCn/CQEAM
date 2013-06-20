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
 * Date: 2009-5-15
 * Time: 21:41:49
 */
public class FreeSpecialAssetsReportModel extends AMSSQLProducer {

	public FreeSpecialAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：获取闲置资产专业统计报表SQL
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
        "       (DECODE(SUM_LAST_YEAR_COST.SUM_COST, NULL, '0', DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3)))) || '%') LAST_YEAR_RATE\n" +
        " FROM   (SELECT SUM(EFAHR.COST) TOTAL\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP  EFAHR,\n" +
        "               ETS_ITEM_INFO  		   EII,\n" +
        "               ETS_ITEM_MATCH 		   EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'FREE'" +
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
        "			AND EII.ITEM_STATUS = 'FREE'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+          
        "  			AND EFAHR.PERIOD_NAME = ? \n" +	//当前会计期
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
        "			AND EII.ITEM_STATUS = 'FREE'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"+        
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			//当前会计期
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
        "			AND EII.ITEM_STATUS = 'FREE'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+     
        "  			AND EFAHR.PERIOD_NAME = ? \n" +		//去年同期会计期
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
        "			AND EII.ITEM_STATUS = 'FREE'"	+
        "			AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
        "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+           
        " 			AND EFAHR.PERIOD_NAME = ? \n" +			//去年同期会计期
        "         GROUP BY SUBSTRING(EFAHR.SEGMENT2,\n" +
        "                      1,\n" +
        "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) SUM_LAST_YEAR_COST\n" +
        "     ON TOTAL.ASSETS_SPECIES_CODE = SUM_LAST_YEAR_COST.ASSETS_SPECIES_CODE\n" +
        "     AND TOTAL.ASSETS_NAPE_CODE = SUM_LAST_YEAR_COST.ASSETS_NAPE_CODE\n" +        
        "      ORDER BY TOTAL.ASSETS_SPECIES_CODE, TOTAL.ASSETS_NAPE_CODE";
        
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
		
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
        sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
        sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
