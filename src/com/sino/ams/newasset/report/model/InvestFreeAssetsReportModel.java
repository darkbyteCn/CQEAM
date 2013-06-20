package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.report.dto.DeptAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * User: 李轶
 * Date: 2009-5-25
 * Time: 16:30:46
 */
public class InvestFreeAssetsReportModel extends AMSSQLProducer {

    public InvestFreeAssetsReportModel(SfUserDTO userAccount, DeptAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
    }

    /**
	 * 功能：	   获取闲置资产投资方向SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 * @author 李轶
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        DeptAssetsReportDTO dto = (DeptAssetsReportDTO) dtoParameter;
        String sqlStr = "SELECT TOTAL.COMPANY,\n" +
        "       DECODE(TOTAL.PROJECT_NAME, '--', '投资项目为空', TOTAL.PROJECT_NAME) PROJECT_NAME,\n" +
        "       TOTAL.SUM_COUNT,\n" +
        "       TOTAL.SUM_COST,       \n" +
        "       (DECODE(TRUNC(100 * TOTAL.SUM_COST / SUM_COST.TOTAL),\n" +
        "               0,\n" +
        "               STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3),\n" +
        "                       '.',\n" +
        "                       '0.'),\n" +
        "               TO_CHAR(ROUND(100 * TOTAL.SUM_COST / SUM_COST.TOTAL, 3))) || '%') ASSETS_RATE,\n" +
        "               \n" +
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
        "       \n" +
        "FROM   (SELECT SUM(EFAHR.COST) TOTAL\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP  EFAHR,\n" +
        "               ETS_ITEM_INFO  		   EII,\n" +
        "               ETS_ITEM_MATCH 		   EIM\n" +
        "        WHERE  EII.ITEM_STATUS = 'FREE'" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAHR.ORGANIZATION_ID = ?)) SUM_COST,\n" +   
        "       (SELECT EOCM.COMPANY,\n" +
        "               (AC.INVEST_CATEGORY1 || '-' || AC.INVEST_CATEGORY2 || '-' ||\n" +
        "               AC.INVEST_CAT_NAME) PROJECT_NAME,\n" +
        "               COUNT(EII.ITEM_QTY) SUM_COUNT,\n" +
        "               SUM(EFAHR.COST) SUM_COST,\n" +
        "               EOCM.ORGANIZATION_ID,\n" +
        "               AC.INVEST_CAT_CODE\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "               ETS_ITEM_INFO   		EII,\n" +
        "               ETS_ITEM_MATCH  		EIM,\n" +
        "               AMS_CEX         		AC,\n" +
        "               ETS_OU_CITY_MAP 		EOCM,\n" +        
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+        
        "        WHERE  EII.ITEM_STATUS = 'FREE'\n" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EFAHR.CEX_ID *= AC.AMS_CEX_ID\n" +
        "               AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
        "               AND EFAHR.PERIOD_NAME = ?\n" +
        "        GROUP  BY AC.INVEST_CATEGORY1 || '-' || AC.INVEST_CATEGORY2 || '-' ||\n" +
        "                  AC.INVEST_CAT_NAME,\n" +
        "                  EOCM.COMPANY,\n" +
        "                  EOCM.ORGANIZATION_ID,\n" +
        "                  AC.INVEST_CAT_CODE) TOTAL\n" +
        "   LEFT JOIN         \n" +
        "       (SELECT SUM(EFAHR.COST) SUM_COST,\n" +
        "               EOCM.ORGANIZATION_ID,\n" +
        "               AC.INVEST_CAT_CODE\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "               ETS_ITEM_INFO   		EII,\n" +
        "               ETS_ITEM_MATCH  		EIM,\n" +
        "               AMS_CEX         		AC,\n" +
        "               ETS_OU_CITY_MAP 		EOCM,\n" +        
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+    
        "        WHERE  EII.ITEM_STATUS = 'FREE'\n" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EFAHR.CEX_ID *= AC.AMS_CEX_ID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "        GROUP  BY EOCM.ORGANIZATION_ID,\n" +
        "                  AC.INVEST_CAT_CODE) SUM_LAST_YEAR_COST   \n" +
        "   ON  TOTAL.ORGANIZATION_ID = SUM_LAST_YEAR_COST.ORGANIZATION_ID\n" +
        "   AND TOTAL.INVEST_CAT_CODE = SUM_LAST_YEAR_COST.INVEST_CAT_CODE\n" +
        "   LEFT JOIN         \n" +
        "       (SELECT SUM(EFAHR.COST) SUM_COST,\n" +
        "               EOCM.ORGANIZATION_ID,\n" +
        "               AC.INVEST_CAT_CODE\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "               ETS_ITEM_INFO   		EII,\n" +
        "               ETS_ITEM_MATCH  		EIM,\n" +
        "               AMS_CEX         		AC,\n" +
        "               ETS_OU_CITY_MAP 		EOCM,\n" +        
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+    
        "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EFAHR.CEX_ID *= AC.AMS_CEX_ID\n" +
        "               AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
        "               AND EFAHR.PERIOD_NAME = ? \n" +
        "        GROUP  BY EOCM.ORGANIZATION_ID,\n" +
        "                  AC.INVEST_CAT_CODE) SUM_LAST_FOUR_YEAR_COST   \n" +
        "   ON  SUM_LAST_YEAR_COST.ORGANIZATION_ID = SUM_LAST_FOUR_YEAR_COST.ORGANIZATION_ID\n" +
        "   AND SUM_LAST_YEAR_COST.INVEST_CAT_CODE = SUM_LAST_FOUR_YEAR_COST.INVEST_CAT_CODE\n" +
        "   LEFT JOIN         \n" +
        "       (SELECT SUM(EFAHR.COST) SUM_COST,\n" +
        "               EOCM.ORGANIZATION_ID,\n" +
        "               AC.INVEST_CAT_CODE\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "               ETS_ITEM_INFO   		EII,\n" +
        "               ETS_ITEM_MATCH  		EIM,\n" +
        "               AMS_CEX         		AC,\n" +
        "               ETS_OU_CITY_MAP 		EOCM,\n" +        
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+    
        "        WHERE  EII.ITEM_STATUS = 'FREE'\n" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n"	+
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "               AND EFAHR.CEX_ID *= AC.AMS_CEX_ID\n" +
        "               AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
        "               AND EFAHR.PERIOD_NAME = ?\n" +
        "        GROUP  BY EOCM.ORGANIZATION_ID,\n" +
        "                  AC.INVEST_CAT_CODE) SUM_LAST_THREE_YEAR_COST   \n" +
        "   ON  SUM_LAST_FOUR_YEAR_COST.ORGANIZATION_ID = SUM_LAST_THREE_YEAR_COST.ORGANIZATION_ID\n" +
        "   AND SUM_LAST_FOUR_YEAR_COST.INVEST_CAT_CODE = SUM_LAST_THREE_YEAR_COST.INVEST_CAT_CODE\n" +
        "   LEFT JOIN         \n" +
        "       (SELECT SUM(EFAHR.COST) SUM_COST,\n" +
        "               EOCM.ORGANIZATION_ID,\n" +
        "               AC.INVEST_CAT_CODE\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "               ETS_ITEM_INFO   		EII,\n" +
        "               ETS_ITEM_MATCH  		EIM,\n" +
        "               AMS_CEX         		AC,\n" +
        "               ETS_OU_CITY_MAP 		EOCM,\n" +        
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+    
        "        WHERE  EII.ITEM_STATUS = 'FREE'\n" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n"	+
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.CEX_ID *= AC.AMS_CEX_ID\n" +
        "               AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
        "               AND EFAHR.PERIOD_NAME = ?\n" +
        "        GROUP  BY EOCM.ORGANIZATION_ID,\n" +
        "                  AC.INVEST_CAT_CODE) SUM_LAST_TWO_YEAR_COST   \n" +
        "   ON  SUM_LAST_THREE_YEAR_COST.ORGANIZATION_ID = SUM_LAST_TWO_YEAR_COST.ORGANIZATION_ID\n" +
        "   AND SUM_LAST_THREE_YEAR_COST.INVEST_CAT_CODE = SUM_LAST_TWO_YEAR_COST.INVEST_CAT_CODE\n" +
        "   LEFT JOIN         \n" +
        "       (SELECT SUM(EFAHR.COST) SUM_COST,\n" +
        "               EOCM.ORGANIZATION_ID,\n" +
        "               AC.INVEST_CAT_CODE\n" +
        "        FROM   ETS_FA_ASSETS_HIS_REP   EFAHR,\n" +
        "               ETS_ITEM_INFO   		EII,\n" +
        "               ETS_ITEM_MATCH  		EIM,\n" +
        "               AMS_CEX         		AC,\n" +
        "               ETS_OU_CITY_MAP 		EOCM,\n" +        
        "				ETS_ITEM_INFO_ATTR_CHG  EIIAC\n"	+    
        "        WHERE  EII.ITEM_STATUS = 'FREE'\n" +
        "				AND EII.SYSTEMID = EIM.SYSTEMID\n"	+
        "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
        "				AND EFAHR.TAG_NUMBER = EIIAC.BAR_CODE\n"	+
        "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME"	+
        "               AND EFAHR.CEX_ID *= AC.AMS_CEX_ID\n" +
        "               AND EOCM.ORGANIZATION_ID = EFAHR.ORGANIZATION_ID\n" +
        "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
        "               AND EFAHR.PERIOD_NAME = ?\n" +
        "        GROUP  BY EOCM.ORGANIZATION_ID,\n" +
        "                  AC.INVEST_CAT_CODE) SUM_LAST_ONE_YEAR_COST   \n" +
        "   ON  SUM_LAST_TWO_YEAR_COST.ORGANIZATION_ID = SUM_LAST_ONE_YEAR_COST.ORGANIZATION_ID\n" +
        "   AND SUM_LAST_TWO_YEAR_COST.INVEST_CAT_CODE = SUM_LAST_ONE_YEAR_COST.INVEST_CAT_CODE\n" +
        "ORDER  BY TOTAL.ORGANIZATION_ID,\n" +
        "          TOTAL.INVEST_CAT_CODE";
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getPeriodNameByHisRep());

        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getLastFourYearPeriodNameByHisRep());
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getLastThreeYearPeriodNameByHisRep());
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getLastTwoYearPeriodNameByHisRep());
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getLastOneYearPeriodNameByHisRep());
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
	}
}
