package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-10
 * Time: 9:19:19
 * To change this template use File | Settings | File Templates.
 */
public class AssetsLoseRateModel extends AMSSQLProducer {

	public AssetsLoseRateModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取资产盘亏率SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel_bak() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        try {
            SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
            String loseAssetsType = dto.getLoseAssetsType();
            String sqlStr = "";
            if (loseAssetsType.equals("LOSE_DEPT")) {
                sqlStr = "SELECT  TOTAL.COMPANY,\n" +
                         "        TOTAL.DEPT_NAME,\n" +
                         "        TOTAL.SUM_COST,\n" +
                         "        TOTAL.NO_MATCH_UNITS,\n" +
                         "        (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2),\n" +
                         "                0,\n" +
                         "                STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3),\n" +
                         "                        '.',\n" +
                         "                        '0.'),\n" +
                         "                TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" +
                         "        (DECODE(TRUNC(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS),\n" +
                         "                0,\n" +
                         "                STR_REPLACE(ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS, 3),\n" +
                         "                        '.',\n" +
                         "                        '0.'),\n" +
                         "                TO_CHAR(ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" +
                         " FROM   (SELECT EOCM.COMPANY COMPANY,\n" +
                         "                EC.COUNTY_NAME DEPT_NAME,\n" +
                         "                SUM(EFA.COST) SUM_COST,\n" +
                         "                COUNT(EFA.CURRENT_UNITS) NO_MATCH_UNITS\n" +
                         "         FROM   ETS_FA_ASSETS EFA, ETS_OU_CITY_MAP EOCM,\n" +
                         "                ETS_COUNTY    EC\n" +
                         "         WHERE  EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                         "                AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                         "                AND NOT EXISTS\n" +
                         "          	  		  SELECT 1\n" +
                         "                 			FROM ETS_ITEM_MATCH EIM\n" +
                         "                 		   WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" +
                         "                AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = EC.COUNTY_CODE\n" +
                         "                AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_CREATE_DATE <= ?)\n" +
                         "                AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCM.ORGANIZATION_ID = ?)\n" +
                         "                AND ( " + SyBaseSQLUtil.isNull() + "  OR EC.COUNTY_CODE = ?)\n" +
                         
                         "				  AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +		
                         
                         "         GROUP  BY EOCM.COMPANY,\n" +
                         "                   EC.COUNTY_NAME) TOTAL,\n" +
                         "        (SELECT SUM(EFA.COST) SUM_COST2\n" +
                         "         FROM   ETS_FA_ASSETS EFA\n" +
                         "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                         
                         "				  AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +	
                         
                         "                AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL2,\n" +
                         "        (SELECT COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" +
                         "         FROM   ETS_FA_ASSETS EFA\n" +
                         "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                         
                         "				  AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +	
                         
                         "                AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)) TOTAL3\n" +
                         " ORDER  BY TOTAL.COMPANY,\n" +
                         "           TOTAL.DEPT_NAME";
                sqlArgs.add(dto.getEndDate());
                sqlArgs.add(dto.getEndDate());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getCountyCode());
                sqlArgs.add(dto.getCountyCode());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                
                
//                if (userAccount.isComAssetsManager()) {
//                    sqlStr += " AND EII.ORGANIZATION_ID = ?\n";
//                    sqlArgs.add(userAccount.getOrganizationId());
//                } else if (userAccount.isDptAssetsManager()) {
//                    sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
//                } else {
//                    sqlStr += " AND EII.RESPONSIBILITY_USER = ?\n";
//                    sqlArgs.add(userAccount.getEmployeeId());
//                }
                
            } else {  //资产盘亏率（公司层面）
               sqlStr = "SELECT EOCM.COMPANY,\n" +
                        "        TOTAL.SUM_COST,\n" +
                        "        TOTAL.NO_MATCH_UNITS,\n" +
                        "        (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2),\n" +
                        "                0,\n" +
                        "                STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3),\n" +
                        "                        '.',\n" +
                        "                        '0.'),\n" +
                        "                TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" +
                        "        (DECODE(TRUNC(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS),\n" +
                        "                0,\n" +
                        "                STR_REPLACE(ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS, 3),\n" +
                        "                        '.',\n" +
                        "                        '0.'),\n" +
                        "                TO_CHAR(ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" +
                        " FROM   ETS_OU_CITY_MAP EOCM,\n" +
                        "        (SELECT EFA.ORGANIZATION_ID,\n" +
                        "                SUM(EFA.COST) SUM_COST,\n" +
                        "                COUNT(EFA.CURRENT_UNITS) NO_MATCH_UNITS\n" +
                        "         FROM   ETS_FA_ASSETS EFA\n" +
                        "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        "                AND NOT EXISTS\n" +
                        "          		 (SELECT 1\n" +
                        "                 FROM   ETS_ITEM_MATCH EIM\n" +
                        "                 WHERE  EIM.ASSET_ID = EFA.ASSET_ID)\n" +
                        "                 AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
                        "                 AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_CREATE_DATE <= ?)\n" +
                        
                        "				  AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +	
                        
                        "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL,\n" +
                        "        (SELECT EFA.ORGANIZATION_ID,\n" +
                        "                SUM(EFA.COST) SUM_COST2\n" +
                        "         FROM   ETS_FA_ASSETS EFA\n" +
                        "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        
                        "				 AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +	
                        
                        "                AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
                        "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL2,\n" +
                        "        (SELECT EFA.ORGANIZATION_ID,\n" +
                        "                COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" +
                        "         FROM   ETS_FA_ASSETS EFA\n" +
                        "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                        
                        "				 AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n" +	
                        
                        "                AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ORGANIZATION_ID = ?)\n" +
                        "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL3\n" +
                        " WHERE  EOCM.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID\n" +
                        "        AND EOCM.ORGANIZATION_ID = TOTAL2.ORGANIZATION_ID\n" +
                        "        AND EOCM.ORGANIZATION_ID = TOTAL3.ORGANIZATION_ID";
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getEndDate());
                sqlArgs.add(dto.getEndDate());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
                sqlArgs.add(dto.getOrganizationId());
            }
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
            } catch (CalendarException ex) {
                ex.printLog();
                throw new SQLModelException(ex);
		    }
            return sqlModel;
	}
	
	/**
	 * 功能：获取资产盘亏率SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        try {
            SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
            String loseAssetsType = dto.getLoseAssetsType();
            String sqlStr = "";
            StringBuffer sb = new StringBuffer();
             
            int orgId = dto.getOrganizationId();
            SimpleCalendar endDate = dto.getEndDate();
            String countyCode = dto.getCountyCode();
            if (loseAssetsType.equals("LOSE_DEPT")) {
                sb.append( " SELECT TOTAL.COMPANY,\n ");
                sb.append( "        TOTAL.DEPT_NAME,\n ");
                sb.append( "        TOTAL.SUM_COST,\n ");
                sb.append( "        TOTAL.NO_MATCH_UNITS,\n ");
                        
                sb.append( "  		( CASE 100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2 \n ");
                sb.append( "  		WHEN 0 \n ");
                sb.append( "  		THEN  \n  ");
                sb.append( "  			STR( \n  ");
                sb.append( "  				ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3) \n  ");
                sb.append( "  				, 20 , 3) \n " );

                sb.append( "  		ELSE STR( ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3) ,20, 3 ) END ) || '%'  MONEY_RATE, \n " );
                
                sb.append( "        (CASE (100 * TOTAL.NO_MATCH_UNITS / TOTAL2.SUM_UNITS) \n" );
                sb.append( "         WHEN 0 \n" );
                sb.append( "         THEN  \n" );
                sb.append( "             STR( \n " );
                sb.append( "         		ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL2.SUM_UNITS, 3) \n " );
                sb.append( "         		,20, 3 ) \n " );
                sb.append( "         ELSE STR( ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL2.SUM_UNITS, 3 ) ,20, 3 ) END ) || '%'  AMOUNT_RATE \n" );
                
                 
                sb.append( " FROM   (SELECT EOCM.COMPANY COMPANY,\n ");
                sb.append( "                EC.COUNTY_NAME DEPT_NAME,\n ");
                sb.append( "                SUM(EFA.COST) SUM_COST,\n ");
                sb.append( "                COUNT(EFA.CURRENT_UNITS) NO_MATCH_UNITS\n ");
                sb.append( "         FROM   ETS_FA_ASSETS EFA, ");
                sb.append( "                ETS_OU_CITY_MAP EOCM (INDEX ETS_OU_CITY_MAP_PK) ,\n ");
                sb.append( "                ETS_COUNTY    EC(INDEX ETS_COUNTY_I1)\n ");
                sb.append( "         WHERE  EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n ");
                sb.append( "                AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n ");  //IS_RETIREMENTS = 1 报废

                sb.append( "                AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n "); 	
                
                sb.append( "                AND NOT EXISTS\n ");
                sb.append( "          		(SELECT 1\n ");
                sb.append( "                 FROM   ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2) \n ");
                sb.append( "                 WHERE  EIM.ASSET_ID = EFA.ASSET_ID)\n ");
//                sb.append( "                AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = EC.COUNTY_CODE\n ");
                sb.append( "                AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = EC.COUNTY_CODE_MIS\n ");
                if( orgId > 0 ){
                	sb.append( "            AND EOCM.ORGANIZATION_ID = ? \n ");
                } 
                
                if( !StrUtil.isEmpty( endDate ) ){
                	sb.append( "                AND EFA.ASSETS_CREATE_DATE <= ? \n "); 
                }
                if( !StrUtil.isEmpty( countyCode ) ){
                	sb.append( "                AND EC.COUNTY_CODE = ? \n "); 
                }
                
//                sb.append( "                AND (  " + SyBaseSQLUtil.isNull() + "  OR EC.COUNTY_CODE = ?)\n ");
                sb.append( "         GROUP  BY EOCM.COMPANY,\n ");
                sb.append( "                   EC.COUNTY_NAME) TOTAL,\n ");
                sb.append( "        (SELECT  ");
                sb.append( "         	SUM(EFA.COST) SUM_COST2,\n ");
                sb.append( "         	COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n ");
                sb.append( "         FROM   ETS_FA_ASSETS EFA\n ");
                sb.append( "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n ");
                if( orgId > 0 ){
                	sb.append( "             AND EFA.ORGANIZATION_ID = ? \n ");
                } 
                
                sb.append( "                 AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n "); 	
                
                sb.append( "         ) TOTAL2 \n "); 
//                sb.append( "        (SELECT COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n ");
//                sb.append( "         FROM   ETS_FA_ASSETS EFA\n ");
//                sb.append( "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n ");
//                if( orgId > 0 ){
//                	sb.append( "                AND EFA.ORGANIZATION_ID = ? \n ");
//                } 
//                sb.append( "         ) TOTAL3\n ");
                sb.append( " ORDER BY TOTAL.COMPANY,\n ");
                sb.append( "           TOTAL.DEPT_NAME ");
                
                if( orgId > 0 ){
                	sqlArgs.add(dto.getOrganizationId());
                }
                
                if( !StrUtil.isEmpty( endDate ) ){
                	sqlArgs.add(dto.getEndDate());
                }
                if( !StrUtil.isEmpty( countyCode ) ){
                	sqlArgs.add(dto.getCountyCode());
                }
                                 
                if( orgId > 0 ){
                	sqlArgs.add(dto.getOrganizationId());
                }
                
            } else {  //资产盘亏率（公司层面）
            	sb.append( " SELECT EOCM.COMPANY,\n ");
            	sb.append( "        TOTAL.SUM_COST,\n ");
            	sb.append( "        TOTAL.NO_MATCH_UNITS,\n ");
            	
            	sb.append( "  		( CASE 100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2 \n ");
                sb.append( "  		WHEN 0 \n ");
                sb.append( "  		THEN  \n  ");
                sb.append( "  			STR( \n  ");
                sb.append( "  				ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3) \n  ");
                sb.append( "  				, 20 , 3) \n " );
                sb.append( "  		ELSE STR( ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3) ,20, 3 ) END ) || '%'  MONEY_RATE, \n " );
                
                sb.append( "        (CASE (100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS) \n" );
                sb.append( "         WHEN 0 \n" );
                sb.append( "         THEN \n" );
                sb.append( "             STR( \n " );
                sb.append( "         		ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS, 3) \n " );
                sb.append( "         		,20, 3 ) \n " );
//                sb.append( "         ELSE CONVERT( VARCHAR , ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS, 3)) END ) || '%'  AMOUNT_RATE \n" );
                sb.append( "         ELSE STR( ROUND(100 * TOTAL.NO_MATCH_UNITS / TOTAL3.SUM_UNITS, 3 ) ,20, 3 ) END ) || '%'  AMOUNT_RATE \n" );
                 
            	sb.append( " FROM   ETS_OU_CITY_MAP EOCM (INDEX ETS_OU_CITY_MAP_PK) ,\n ");
            	sb.append( "        (SELECT EFA.ORGANIZATION_ID,\n ");
            	sb.append( "                SUM(EFA.COST) SUM_COST,\n ");
            	sb.append( "                COUNT(EFA.CURRENT_UNITS) NO_MATCH_UNITS\n ");
            	sb.append( "         FROM   ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) \n ");
            	sb.append( "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n ");
            	sb.append( "                AND NOT EXISTS\n ");
            	sb.append( "          		(SELECT 1\n ");
            	sb.append( "                 FROM   ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2)\n ");
            	sb.append( "                 WHERE  EIM.ASSET_ID = EFA.ASSET_ID)\n ");
            	if( orgId > 0 ){
                	sb.append( "                AND EFA.ORGANIZATION_ID = ? \n ");
                } 
            	if( !StrUtil.isEmpty( endDate ) ){
            		sb.append( "                AND EFA.ASSETS_CREATE_DATE <= ? \n  ");
            	}
            	
            	sb.append( "                 AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n "); 	
            	
            	sb.append( "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL,\n ");
            	sb.append( "        (SELECT EFA.ORGANIZATION_ID,\n ");
            	sb.append( "                SUM(EFA.COST) SUM_COST2\n ");
            	sb.append( "         FROM   ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) \n ");
            	sb.append( "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n ");
            	if( orgId > 0 ){
                	sb.append( "                AND EFA.ORGANIZATION_ID = ? \n ");
                } 
            	
            	sb.append( "                AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n "); 	
            	
            	sb.append( "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL2,\n ");
            	sb.append( "        (SELECT EFA.ORGANIZATION_ID,\n ");
            	sb.append( "                COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n ");
            	sb.append( "         FROM   ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) \n ");
            	sb.append( "         WHERE  (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n ");
            	if( orgId > 0 ){
                	sb.append( "                AND EFA.ORGANIZATION_ID = ? \n ");
                } 
            	
            	sb.append( "                AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME)\n "); 	
            	
            	sb.append( "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL3\n ");
            	sb.append( " WHERE  EOCM.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID\n ");
            	sb.append( "        AND EOCM.ORGANIZATION_ID = TOTAL2.ORGANIZATION_ID\n ");
            	sb.append( "        AND EOCM.ORGANIZATION_ID = TOTAL3.ORGANIZATION_ID \n ");
            	if( orgId > 0 ){
            		sb.append( "        AND EOCM.ORGANIZATION_ID = ? ");
            	}
            	if( orgId > 0 ){
                	sqlArgs.add(dto.getOrganizationId());
                }
            	
            	if( !StrUtil.isEmpty( endDate ) ){
                	sqlArgs.add(dto.getEndDate());
                }
                  
                if( orgId > 0 ){
                	sqlArgs.add(dto.getOrganizationId());
                	sqlArgs.add(dto.getOrganizationId());
                	sqlArgs.add(dto.getOrganizationId());
                }
            }
            sqlModel.setSqlStr( sb.toString() );
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
	    }
        return sqlModel;
	}
	
	
	
	
}
