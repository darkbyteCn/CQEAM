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
 * Time: 16:07:55
 * Function:	地域资产构成分布(减值)	
 */
public class AreaImpairmentAssetsReportModel extends AMSSQLProducer {

	public AreaImpairmentAssetsReportModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：区域资产构成统计报表
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
        String sqlStr = "";
        	sqlStr = "SELECT CITY.ASSETS_SPECIES,\n" +
            "       CITY.ASSETS_NAPE,\n" +
            "       CITY.IMPAIR_AMOUNT CITY_IMPAIR_AMOUNT,\n" +
            "       CITY.IMPAIR_RESERVE CITY_IMPAIR_RESERVE,\n" +
            "       CITY.SUM_COUNT CITY_COUNT,\n" +
            
            "       COUNTY.IMPAIR_AMOUNT COUNTY_IMPAIR_AMOUNT,\n" +
            "       COUNTY.IMPAIR_RESERVE COUNTY_IMPAIR_RESERVE,\n" +
            "       COUNTY.SUM_COUNT COUNTY_COUNT,\n" +
            
            "       VILLAGE.IMPAIR_AMOUNT VILLAGE_IMPAIR_AMOUNT,\n" +
            "       VILLAGE.IMPAIR_RESERVE VILLAGE_IMPAIR_RESERVE,\n" +
            "       VILLAGE.SUM_COUNT VILLAGE_COUNT,\n" +
            "       (DECODE(TRUNC(100 * CITY.SUM_COUNT / SUM_COST.TOTAL),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * CITY.SUM_COUNT / SUM_COST.TOTAL, 3), '.', '0.'),\n" +
            "               STR(ROUND(100 * CITY.SUM_COUNT / SUM_COST.TOTAL, 3))) || '%') CITY_RATE,\n" +
            "       (DECODE(CITY.IMPAIR_AMOUNT, NULL, '0', DECODE(TRUNC(100 * CITY.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * CITY.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3), '.', '0.'),\n" +
            "               STR(ROUND(100 * CITY.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3)))) || '%') CITY_IMPAIR_RATE,\n" +
            "       (DECODE(COUNTY.SUM_COUNT, NULL, '0', DECODE(TRUNC(100 * COUNTY.SUM_COUNT / SUM_COST.TOTAL),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * COUNTY.SUM_COUNT / SUM_COST.TOTAL, 3), '.', '0.'),\n" +
            "               STR(ROUND(100 * COUNTY.SUM_COUNT / SUM_COST.TOTAL, 3)))) || '%') COUNTY_RATE,\n" +
            "       (DECODE(COUNTY.IMPAIR_AMOUNT, NULL, '0', DECODE(TRUNC(100 * COUNTY.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * COUNTY.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3), '.', '0.'),\n" +
            "               STR(ROUND(100 * COUNTY.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3)))) || '%') COUNTY_IMPAIR_RATE,\n" +
            "       (DECODE(VILLAGE.SUM_COUNT, NULL, '0', DECODE(TRUNC(100 * VILLAGE.SUM_COUNT / SUM_COST.TOTAL),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * VILLAGE.SUM_COUNT / SUM_COST.TOTAL, 3), '.', '0.'),\n" +
            "               STR(ROUND(100 * VILLAGE.SUM_COUNT / SUM_COST.TOTAL, 3)))) || '%') VILLAGE_RATE,\n" +
            "       (DECODE(VILLAGE.IMPAIR_AMOUNT, NULL, '0', DECODE(TRUNC(100 * VILLAGE.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT),\n" +
            "               0,\n" +
            "               STR_REPLACE(ROUND(100 * VILLAGE.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3), '.', '0.'),\n" +
            "               STR(ROUND(100 * VILLAGE.IMPAIR_AMOUNT / SUM_IMPAIR_COST.IMPAIR_AMOUNT, 3)))) || '%') VILLAGE_IMPAIR_RATE\n" +
            "FROM (SELECT DECODE(SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             1,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
            "                      NULL,\n" +
            "                      '类为空',\n" +
            "                      SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             1,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) ASSETS_SPECIES,\n" +
            "               DECODE(SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                             (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
            "                      NULL,\n" +
            "                      '项为空',\n" +
            "                      SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                             (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) ASSETS_NAPE,\n" +
            "               SUBSTRING(EFAHR.SEGMENT2,\n" +
            "                      1,\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) - DATALENGTH('-')) ASSETS_SPECIES_CODE,\n" +
            "               SUBSTRING(EFAHR.SEGMENT2,\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1) + DATALENGTH('-'),\n" +
            "                      (CHARINDEX(EFAHR.SEGMENT2, '-', 1, 2) -\n" +
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-')) ASSETS_NAPE_CODE,\n" +
            "               SUM(EFAHR.IMPAIR_AMOUNT) IMPAIR_AMOUNT,\n" +	//当期减值
            "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIR_RESERVE,\n" +	//累计减值
            "               COUNT(EII.ITEM_QTY) SUM_COUNT\n" +
            "        FROM   ETS_FA_ASSETS_HIS_REP      EFAHR,\n" +
            "               ETS_OBJECT                 EO,\n" +
            "               AMS_OBJECT_ADDRESS         AOA,\n" +
            "               ETS_ITEM_INFO              EII,\n" +
            "               ETS_ITEM_MATCH             EIM,\n" +
            "				ETS_ITEM_INFO_ATTR_CHG     EIIAC \n"	+
            "        WHERE  EII.ITEM_STATUS = 'IMPAIRMENT'" +
            "				AND EIIAC.BAR_CODE = EFAHR.TAG_NUMBER \n"	+
            "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME\n"	+
            "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
            "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
            "               AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
            "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
            "               AND EO.AREA_TYPE IN ('01', '02', '03')\n" +
            "               AND EFAHR.PERIOD_NAME = ? \n"+		//当前会计期
        	"      			AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
            "        GROUP  BY SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
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
            "                      CHARINDEX(EFAHR.SEGMENT2, '-', 1, 1)) - DATALENGTH('-'))) CITY,\n" +
            "       (SELECT DECODE(SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             1,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
            "                      NULL,\n" +
            "                      '类为空',\n" +
            "                      SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             1,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) ASSETS_SPECIES,\n" +
            "               DECODE(SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                             (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
            "                      NULL,\n" +
            "                      '项为空',\n" +
            "                      SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                             (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) ASSETS_NAPE,\n" +
            "               SUM(EFAHR.IMPAIR_AMOUNT) IMPAIR_AMOUNT,\n" +	//当期减值
            "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIR_RESERVE,\n" +	//累计减值
            "               COUNT(EII.ITEM_QTY) SUM_COUNT\n" +
            "        FROM   ETS_FA_ASSETS_HIS_REP      EFAHR,\n" +
            "               ETS_OBJECT                 EO,\n" +
            "               AMS_OBJECT_ADDRESS         AOA,\n" +
            "               ETS_ITEM_INFO              EII,\n" +
            "               ETS_ITEM_MATCH             EIM,\n" +
            "				ETS_ITEM_INFO_ATTR_CHG     EIIAC \n"	+
            "        WHERE  EII.ITEM_STATUS = 'IMPAIRMENT'" +
            "				AND EIIAC.BAR_CODE = EFAHR.TAG_NUMBER \n"	+
            "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME\n"	+
            "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
            "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
            "               AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
            "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
            "               AND EO.AREA_TYPE IN ('04', '05')\n" +
            "               AND EFAHR.PERIOD_NAME = ? \n" 	+
            "      			AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
            "        GROUP  BY SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                         1,\n" +
            "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
            "                  SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                         (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) COUNTY,\n" +
            "       (SELECT DECODE(SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             1,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
            "                      NULL,\n" +
            "                      '类为空',\n" +
            "                      SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             1,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-'))) ASSETS_SPECIES,\n" +
            "               DECODE(SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                             (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-')),\n" +
            "                      NULL,\n" +
            "                      '项为空',\n" +
            "                      SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                             (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                             CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) ASSETS_NAPE,\n" +
            "               SUM(EFAHR.IMPAIR_AMOUNT) IMPAIR_AMOUNT,\n" +	//当期减值
            "               SUM(EFAHR.IMPAIR_RESERVE) IMPAIR_RESERVE,\n" +	//累计减值
            "               COUNT(EII.ITEM_QTY) SUM_COUNT\n" +
            "        FROM   ETS_FA_ASSETS_HIS_REP      EFAHR,\n" +
            "               ETS_OBJECT                 EO,\n" +
            "               AMS_OBJECT_ADDRESS         AOA,\n" +
            "               ETS_ITEM_INFO              EII,\n" +
            "               ETS_ITEM_MATCH             EIM,\n" +
            "				ETS_ITEM_INFO_ATTR_CHG     EIIAC \n"	+
            "        WHERE  EII.ITEM_STATUS = 'IMPAIRMENT'" +
            "				AND EIIAC.BAR_CODE = EFAHR.TAG_NUMBER \n"	+
            "				AND EIIAC.PERIOD_NAME = EFAHR.PERIOD_NAME\n"	+
            "				AND EII.SYSTEMID = EIM.SYSTEMID\n" +
            "               AND EFAHR.ASSET_ID = EIM.ASSET_ID\n" +
            "               AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
            "               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
            "               AND EO.AREA_TYPE = '06'\n" +
            "               AND EFAHR.PERIOD_NAME = ? \n" +
            "      			AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.ORGANIZATION_ID = ?)\n" +
            "        GROUP  BY SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                         1,\n" +
            "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) - DATALENGTH('-')),\n" +
            "                  SUBSTRING(EFAHR.FA_CATEGORY2,\n" +
            "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1) + DATALENGTH('-'),\n" +
            "                         (CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 2) -\n" +
            "                         CHARINDEX(EFAHR.FA_CATEGORY2, '-', 1, 1)) - DATALENGTH('-'))) VILLAGE,\n" +
            
            "       (SELECT COUNT(EII.ITEM_QTY) TOTAL\n" +
            "        FROM   ETS_FA_ASSETS   		EFA,\n" +
            "               ETS_ITEM_INFO           EII,\n" +
            "               ETS_ITEM_MATCH          EIM\n" +
            "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
            "               AND EFA.ASSET_ID = EIM.ASSET_ID\n" +
            "				AND EII.ITEM_STATUS = 'IMPAIRMENT'"	+
            "               AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) SUM_COST,\n" +
            
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
  			"	       AND EFAHR.PERIOD_NAME = ? ) SUM_IMPAIR_COST"	+
            " WHERE  CITY.ASSETS_SPECIES *= COUNTY.ASSETS_SPECIES\n" +
            "       AND CITY.ASSETS_SPECIES *= VILLAGE.ASSETS_SPECIES\n" +
            "       AND CITY.ASSETS_NAPE *= COUNTY.ASSETS_NAPE\n" +
            "       AND CITY.ASSETS_NAPE *= VILLAGE.ASSETS_NAPE\n" +
            "ORDER  BY CITY.ASSETS_SPECIES_CODE,\n" +
            "          CITY.ASSETS_NAPE_CODE"; 
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        
        sqlArgs.add(dto.getPeriodNameByHisRep());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        
        sqlArgs.add(dto.getPeriodNameByHisRep());
        
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
	}
}
