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
 * @author			李轶
 * Date: 2009-5-25
 * Time: 17:53:15
 * To change this template use File | Settings | File Templates.
 */
public class SpecialFreeAssetsReportModel extends AMSSQLProducer {

	public SpecialFreeAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：		应用领域统计(闲置)
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
	        "       TOTAL.FA_DESCRIPTION,\n" +
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
	        "               \n" +
	        "       (DECODE(SUM_LAST_YEAR_COST.SUM_COST, NULL, '0', DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100),\n" +
	        "               0,\n" +
	        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3),\n" +
	        "                       '.',\n" +
	        "                       '0.'),\n" +
	        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_LAST_YEAR_COST.SUM_COST - 100, 3)))) || '%') LAST_YEAR_RATE\n" +
	        " FROM   ETS_OU_CITY_MAP EOCM,\n" +   
	        "		(SELECT SUM(EFAHR.COST) TOTAL\n" +
	        "        FROM   ETS_FA_ASSETS_HIS_REP  EFAHR,\n" +
	        "               ETS_ITEM_INFO  		   EII,\n" +
	        "               ETS_ITEM_MATCH 		   EIM\n" +
	        "        WHERE  EII.ITEM_STATUS = 'FREE'" +
	        "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
	        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
	        "               AND EII.FINANCE_PROP = 'ASSETS'\n" +
	        "               AND EFAHR.PERIOD_NAME = ? \n" +
	        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)) SUM_COST,\n" +   
	    
	        "       (SELECT EFAHR.ORGANIZATION_ID,\n" +
	        "               EFAHR.FA_CATEGORY1 FA_DESCRIPTION,\n" +
	        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
	        "               SUM(EFAHR.COST) SUM_COST,\n" +
	        "               SUM(EFAHR.NET_ASSET_VALUE) NET_BOOK_VALUE,\n" +
	        "               SUM(EFAHR.DEPRN_COST) LIMIT_VALUE,\n" +
	        "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIRMENT_RESERVE,\n" +
	        "               SUM(EFAHR.DEPRN_RESERVE) DEPRN_RESERVE,\n" +
	        "               SUM(EFAHR.DEPRN_AMOUNT) PTD_DEPRN\n" +
	        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
	        "               ETS_ITEM_INFO  			EII,\n" +
	        "               ETS_ITEM_MATCH 		    EIM,\n" +
	        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+
	        "        WHERE  EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
	        "               AND EII.SYSTEMID = EIM.SYSTEMID\n"	+
	        "				AND EII.ITEM_STATUS = 'FREE'"	+
	        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
	        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
	        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
	    	"               AND EFAHR.PERIOD_NAME = ? \n" +
	        "        GROUP  BY EFAHR.ORGANIZATION_ID,\n" +
	        "                  EFAHR.FA_CATEGORY1) TOTAL\n" +			//当前会计期值集
	        "   LEFT JOIN \n"	+
	        "       (SELECT EFAHR.ORGANIZATION_ID,\n" +
	        "               EFAHR.FA_CATEGORY1 FA_DESCRIPTION,\n" +
	        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
	        "               SUM(EFAHR.COST) SUM_COST\n" +
	        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
	        "               ETS_ITEM_INFO  			EII,\n" +
	        "               ETS_ITEM_MATCH 		    EIM,\n" +
	        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+
	        "        WHERE  EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
	        "               AND EII.SYSTEMID = EIM.SYSTEMID\n"	+
	        "				AND EII.ITEM_STATUS = 'FREE'"	+
	        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
	        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
	        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)"	+
	    	"               AND EFAHR.PERIOD_NAME = ? \n" +
	        "        GROUP  BY EFAHR.ORGANIZATION_ID,\n" +
	        "                  EFAHR.FA_CATEGORY1) SUM_LAST_YEAR_COST\n" +			//去年同期值集
	        "     ON TOTAL.ORGANIZATION_ID = SUM_LAST_YEAR_COST.ORGANIZATION_ID \n"+
	        "    AND TOTAL.FA_DESCRIPTION = SUM_LAST_YEAR_COST.FA_DESCRIPTION \n"	+	        
	        "  WHERE EOCM.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID\n" +
	        " ORDER BY EOCM.COMPANY";
        sqlArgs.add(dto.getPeriodNameByHisRep());
	    sqlArgs.add(dto.getOrganizationId());
	    sqlArgs.add(dto.getOrganizationId());
	    
	    
	    sqlArgs.add(dto.getOrganizationId());
	    sqlArgs.add(dto.getOrganizationId());
	    sqlArgs.add(dto.getPeriodNameByHisRep());
	    
	    sqlArgs.add(dto.getOrganizationId());
	    sqlArgs.add(dto.getOrganizationId());
	    sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
	    
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
