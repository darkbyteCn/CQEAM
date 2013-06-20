package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-9
 * Time: 11:11:59
 * To change this template use File | Settings | File Templates.
 */
public class LoseMatchMisReportModel extends AMSSQLProducer {

    public LoseMatchMisReportModel(SfUserDTO userAccount, EtsFaAssetsDTO dtoParameter) {
		super(userAccount, dtoParameter);
    }

/**
	 * 功能：获取盘亏统计报表SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		    SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
            EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
            String sqlStr = "SELECT \n" +
            		"				TOTAL.ASSETS_DESCRIPTION,\n" +
				            "       TOTAL.MODEL_NUMBER,\n" +
				            "       TOTAL.ASSET_ID,\n" +
				            "       TOTAL.TAG_NUMBER,\n" +
				            "       TOTAL.NO_MATCH_UNITS,\n" +
				            "       TOTAL.COST,\n" +
				            "	    (CASE (100 * TOTAL.COST / SUM_COST.TOTAL) \n" +
				            "        WHEN 0 \n" +
				            "        THEN STR_REPLACE( \n" +
				            "                          STR( \n" +
				            "         							ROUND(100 * TOTAL.COST / SUM_COST.TOTAL, 3)  \n" +
				            "         						, 20 , 3 ),'.','0.' ) \n" +
				            "         ELSE STR( ROUND(100 * TOTAL.COST / SUM_COST.TOTAL, 3) , 20 , 3 ) END ) || '%'  ASSETS_RATE, \n" + 	
				            
				            
//				            "       (DECODE(TRUNC(100 * TOTAL.COST / SUM_COST.TOTAL),\n" +
//				            "               0,\n" +
//				            "               STR_REPLACE(ROUND(100 * TOTAL.COST / SUM_COST.TOTAL, 3),\n" +
//				            "                       '.',\n" +
//				            "                       '0.'),\n" +
//				            "               TO_CHAR(ROUND(100 * TOTAL.COST / SUM_COST.TOTAL, 3))) || '%') ASSETS_RATE,\n" +
				            "       \n" +
				            " 		( CASE SUM_LAST_YEAR_COST.COST \n" + 
				            "		  WHEN NULL \n" +
				            "		  THEN '0%' \n" + 	
				            "		  WHEN 0 \n" +
				            "		  THEN '0%' \n" + 	
				            "		  ELSE \n" + 	
				            "	    (CASE (100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100 ) \n" +
				            "        WHEN 0 \n" +
				            "        THEN STR_REPLACE( \n" +
				            "                          STR( \n" +
				            "         							ROUND(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100 , 3)  \n" +
				            "         						, 20 , 3 ),'.','0.' ) \n" +
				            "         ELSE STR( ROUND(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100 , 3) , 20 , 3 ) END ) || '%' END )  LAST_YEAR_RATE  \n" + 	
				            
//				            "       DECODE(SUM_LAST_YEAR_COST.COST, NULL, '0%', 0, '0%', (DECODE(TRUNC(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100),\n" +
//				            "               0,\n" +
//				            "               STR_REPLACE(ROUND(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100, 3),\n" +
//				            "                       '.',\n" +
//				            "                       '0.'),\n" +
//				            "               TO_CHAR(ROUND(100 * TOTAL.COST / SUM_LAST_YEAR_COST.COST - 100, 3))) || '%')) LAST_YEAR_RATE\n" +
				            "  FROM (SELECT SUM(EFAHR.COST) TOTAL\n" +
				            "          FROM ETS_FA_ASSETS_HIS_REP EFAHR\n" +
				            "         WHERE  " +
//				            " 		  EFAHR.PERIOD_NAME = ?" +
//				            "			AND " + 
				            "		 ( ? <= 0  OR EFAHR.ORGANIZATION_ID = ?)) SUM_COST,\n" +
				            "       (SELECT DISTINCT EFAHR.ASSET_ID,\n" +
				            "               EFAHR.TAG_NUMBER,\n" +
				            "               EFAHR.ASSETS_DESCRIPTION,\n" +
				            "               EFAHR.MODEL_NUMBER,\n" +
				            "               1 NO_MATCH_UNITS,   \n" +
				            "               EFAHR.COST\n" +
				            "          FROM ETS_FA_ASSETS_HIS_REP EFAHR,\n" +
				            "				AMS_COST_DEPT_MATCH   ACDM,\n"	+
				            "				AMS_MIS_DEPT		  AMD\n"	+
				            "         WHERE (EFAHR.IS_RETIREMENTS = 0 OR EFAHR.IS_RETIREMENTS = 2)\n" +
				            "			AND EFAHR.COUNTY_CODE *= ACDM.COST_CENTER_CODE"	+
				            "			AND ACDM.DEPT_CODE *= AMD.DEPT_CODE\n"	+
				            "			AND ( " + SyBaseSQLUtil.isNull() + "  OR ACDM.DEPT_CODE = ?)"	+
				            "           AND EFAHR.COST <> 0\n" +
				            "           AND NOT EXISTS\n" +
				            "               (SELECT 1 \n" +
				            "                  FROM ETS_ITEM_MATCH EIM \n" +
				            "                 WHERE EIM.ASSET_ID = EFAHR.ASSET_ID\n" +
				            "                  AND ( ? <= 0  OR EFAHR.ORGANIZATION_ID = ?))\t\n" +
//				            "           AND EFAHR.PERIOD_NAME = ? " + 
				            " 		  ) TOTAL,\n" +
				            "        (SELECT DISTINCT EFAHR.ASSET_ID,\n" +
				            "               EFAHR.COST\n" +
				            "          FROM ETS_FA_ASSETS_HIS_REP EFAHR,\n" +
				            "				AMS_COST_DEPT_MATCH   ACDM,\n"	+
				            "				AMS_MIS_DEPT		  AMD\n"	+
				            "         WHERE (EFAHR.IS_RETIREMENTS = 0 OR EFAHR.IS_RETIREMENTS = 2)\n" +
				            "			AND EFAHR.COUNTY_CODE *= ACDM.COST_CENTER_CODE"	+
				            "			AND ACDM.DEPT_CODE *= AMD.DEPT_CODE\n"	+
				            "			AND ( " + SyBaseSQLUtil.isNull() + "  OR ACDM.DEPT_CODE = ?)"	+
				            "           AND EFAHR.COST <> 0\n" +
				            "           AND NOT EXISTS\n" +
				            "               (SELECT 1 \n" +
				            "                  FROM ETS_ITEM_MATCH EIM \n" +
				            "                 WHERE EIM.ASSET_ID = EFAHR.ASSET_ID\n" +
				            "                   AND ( ? <= 0  OR EFAHR.ORGANIZATION_ID = ?))   \t\n" +
//				            "           AND EFAHR.PERIOD_NAME = ? " + 
				            "		   ) SUM_LAST_YEAR_COST\n" +
				            " WHERE TOTAL.ASSET_ID *= SUM_LAST_YEAR_COST.ASSET_ID";
//            sqlArgs.add(dto.getPeriodNameByHisRep());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getPeriodNameByHisRep() );
            
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getLastYearPeriodNameByHisRep());
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
            return sqlModel;
	}
}
