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
 * Date: 2009-6-14
 * Time: 13:59:55
 * Function:	按专业资产构成分布(减值)
 */
public class SpecialImpairmentAssetsReportModel extends AMSSQLProducer {

	public SpecialImpairmentAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取盘点统计报表SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String sqlStr = "";
        String specialAssetsType = dto.getSpecialAssetsType();
        sqlStr = "SELECT DECODE(TOTAL.ASSETS_SPECIES, '', '类项为空', TOTAL.ASSETS_SPECIES) ASSETS_SPECIES,\n" +
            "       TOTAL.ASSETS_NAPE,\n" +
            "       TOTAL.SUM_COUNT,\n" +
            "       TOTAL.IMPAIR_AMOUNT,\n" +
            "       TOTAL.IMPAIR_RESERVE,\n" +
            "       (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / SUM_COST.TOTAL, 3))) || '%') ASSETS_RATE,\n" +  //数量比
            "       DECODE(SUM_IMPAIR_COST.IMPAIR_AMOUNT, NULL, '0%', 0, '0%', (DECODE(TRUNC(100 * TOTAL.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * TOTAL.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * TOTAL.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3))) || '%')) ASSETS_RATE_IMPAIR_AMOUNT,\n" +  //当期减值比
            "       DECODE(SUM_IMPAIR_COST.IMPAIR_RESERVE, NULL, '0%', 0, '0%',(DECODE(TRUNC(100 * TOTAL.IMPAIR_RESERVE / SUM_IMPAIR_COST.IMPAIR_RESERVE),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * TOTAL.IMPAIR_RESERVE / SUM_IMPAIR_COST.IMPAIR_RESERVE, 3),\n" +
            "                       '.',\n" +
            "                       '0.'),\n" +
            "               TO_CHAR(ROUND(100 * TOTAL.IMPAIR_RESERVE / SUM_IMPAIR_COST.IMPAIR_RESERVE, 3))) || '%')) ASSETS_RATE_IMPAIR_RESERVE\n" + //累计减值比
            "  FROM \n" +
            "       (SELECT COUNT(EII.ITEM_QTY) TOTAL\n" +
            "        FROM   ETS_FA_ASSETS   		EFA,\n" +
            "               ETS_ITEM_INFO           EII,\n" +
            "               ETS_ITEM_MATCH          EIM\n" +
            "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
            "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
            "				AND EII.ITEM_STATUS = 'IMPAIRMENT'"	+
            "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) SUM_COST,\n" +	        //用户输入的会计期  ------看这里的分母要不要加入调拨的条件???????
            "		(SELECT SUM(EFAHR.IMPAIR_AMOUNT) IMPAIR_AMOUNT,"	+
            " 				SUM(EFAHR.IMPAIR_RESERVE) IMPAIR_RESERVE"	+
            "		   FROM ETS_ITEM_INFO           EII,"	+
            "				ETS_FA_ASSETS_HIS_REP   EFAHR,"	+
            "				ETS_ITEM_MATCH          EIM,"	+
            "				ETS_ITEM_INFO_ATTR_CHG  EIIAC"	+
            "		  WHERE EII.ITEM_STATUS = 'IMPAIRMENT'"	+			
            "			AND EII.SYSTEMID = EIM.SYSTEMID"	+
            "			AND EFAHR.ASSET_ID = EIM.ASSET_ID"	+
		    "			AND EIIAC.BAR_CODE = EFAHR.TAG_NUMBER"	+ 
			"		    AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
  			"	       AND EFAHR.PERIOD_NAME = ? ) SUM_IMPAIR_COST,"	+
            "       (SELECT SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                      1,\n" +
            "                      CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES,\n" +
            "               SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                      CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                      (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                      CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE,\n" +
            "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
            "               SUM(EFAHR.IMPAIR_AMOUNT) IMPAIR_AMOUNT,\n" +	//当期减值
            "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIR_RESERVE,\n" +	//累计减值
            "               SUBSTRING(EFAHR.SEGMENT2,\n" +
            "                      1,\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
            "               SUBSTRING(EFAHR.SEGMENT2,\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
            "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE\n" +
            "          FROM ETS_ITEM_INFO           EII,\n" +
            "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
            "               ETS_ITEM_MATCH          EIM,\n" +
            "				ETS_ITEM_INFO_ATTR_CHG  EIIAC \n"	+
            "         WHERE EII.ITEM_STATUS = 'IMPAIRMENT'" +
            "			AND EII.SYSTEMID = EIM.SYSTEMID\n" +
            "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
            "			AND EIIAC.BAR_CODE = EFAHR.TAG_NUMBER \n"	+
            "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME\n"	+
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
            "               SUM(EFAHR.IMPAIR_AMOUNT) IMPAIR_AMOUNT,\n" +	//当期减值
            "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIR_RESERVE,\n" +	//累计减值
            "               SUBSTRING(EFAHR.SEGMENT2,\n" +
            "                      1,\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
            "               '0' ASSETS_NAPE_CODE\n" +
            "          FROM ETS_ITEM_INFO           EII,\n" +
            "               ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
            "               ETS_ITEM_MATCH          EIM,\n" +
            "				ETS_ITEM_INFO_ATTR_CHG  EIIAC \n"	+
            "         WHERE EII.ITEM_STATUS = 'IMPAIRMENT'" +
            "			AND EII.SYSTEMID = EIM.SYSTEMID\n" +
            "			AND EIIAC.BAR_CODE = EFAHR.TAG_NUMBER \n"	+
            "			AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME\n"	+
            "           AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
            "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)\n"	+
            "           AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.CONTENT_CODE LIKE ?)\n"	+            
            "  			AND EFAHR.PERIOD_NAME = ? \n" +			//当前会计期
            "         GROUP BY SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                         1,\n" +
            "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
            "                  SUBSTRING(EFAHR.SEGMENT2,\n" +
            "                      1,\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-'))) TOTAL            \n" +
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
            
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
	    sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
