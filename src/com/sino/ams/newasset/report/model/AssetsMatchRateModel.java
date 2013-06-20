package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer; 
import com.sino.ams.newasset.report.dto.SpecialAssetsReportDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-10
 * Time: 13:57:53
 * To change this template use File | Settings | File Templates.
 */
public class AssetsMatchRateModel extends AMSSQLProducer {

	public AssetsMatchRateModel(SfUserDTO userAccount, SpecialAssetsReportDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：年底盘点资产账实相符率（公司层面）
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
	    List sqlArgs = new ArrayList();
        try {
            SpecialAssetsReportDTO dto = (SpecialAssetsReportDTO) dtoParameter;
            String matchAssetsType = dto.getMatchAssetsType();
            String sqlStr = "";
            StringBuffer sb = new StringBuffer();
            
            if (matchAssetsType.equals("MATCH_DEPT")) {

            	
                sb.append( " SELECT TOTAL.COMPANY,\n" );
                sb.append( "        TOTAL.DEPT_NAME,\n" );
                sb.append( "        TOTAL2.SUM_UNITS,\n" );
                sb.append( "        TOTAL2.SUM_COST2,\n" );
                sb.append( "        TOTAL.SUM_COUNT,\n" );
                sb.append( "        TOTAL.SUM_COST,\n" );
                sb.append( "        (CASE (100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2) \n" );
                sb.append( "         WHEN 0 \n" );
                sb.append( "         THEN \n" );
                sb.append( "                           STR( \n " );
                sb.append( "         							ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3) \n " );
                sb.append( "         						, 20 , 3 )  \n " );
                sb.append( "         ELSE STR( ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3) ,20, 3 ) END ) || '%'  MONEY_RATE, \n " );
                
                sb.append( "        (CASE (100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS) \n" );
                sb.append( "         WHEN 0 \n" );
                sb.append( "         THEN  \n" );
                sb.append( "                           STR( \n " );
                sb.append( "         							ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3) \n " );
                sb.append( "         						  ,20, 3 )  \n " );
//                sb.append( "         ELSE CONVERT( VARCHAR , ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3)) END ) || '%'  AMOUNT_RATE \n" );
                sb.append( "         ELSE STR( ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3) ,20, 3 ) END ) || '%'  AMOUNT_RATE \n " );
                 
                
//                sb.append( "        (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2),\n" );
//                sb.append( "                0,\n" );
//                sb.append( "                STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3),\n" );
//                sb.append( "                        '.',\n" );
//                sb.append( "                        '0.'),\n" );
//                sb.append( "                TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" );
//                sb.append( "        (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS),\n" );
//                sb.append( "                0,\n" );
//                sb.append( "                STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3),\n" );
//                sb.append( "                        '.',\n" );
//                sb.append( "                        '0.'),\n" );
//                sb.append( "                TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" );
                sb.append( " FROM   (SELECT EOCM.COMPANY COMPANY,\n" );
                sb.append( "                EOCM.ORGANIZATION_ID,\n" );
                sb.append( "                AMD.DEPT_NAME DEPT_NAME,\n" );
                sb.append( "                COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" );
                sb.append( "                SUM(EFA.COST) SUM_COST\n" );
                sb.append( "         FROM   ETS_FA_ASSETS      EFA,\n" );
                sb.append( "                ETS_ITEM_INFO      EII,\n" );
                sb.append( "                ETS_ITEM_MATCH     EIM,\n" );
                sb.append( "                ETS_OU_CITY_MAP    EOCM,\n" );
                sb.append( "                AMS_MIS_DEPT       AMD\n" );
                sb.append( "         WHERE  EFA.ASSET_ID = EIM.ASSET_ID\n" );
                sb.append( "                AND EII.SYSTEMID = EIM.SYSTEMID\n" );
                sb.append( "                AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" );
                sb.append( "                AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE              \n" );
                
                sb.append( "                AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" );
                
                if( !StrUtil.isEmpty( dto.getEndDate() )){
                	sb.append( "                AND  EIM.MATCH_DATE <= ? \n" );
                } 
                if( !StrUtil.isEmpty( dto.getResponsibilityDept() )){
                	sb.append( "                AND  EIM.RESPONSIBILITY_DEPT LIKE ? \n" );
                } 
                if( dto.getOrganizationId() > 0 ){
                	sb.append( "                AND EOCM.ORGANIZATION_ID = ? \n" );
                }
                 
                sb.append( "         GROUP  BY EOCM.COMPANY,\n" );
                sb.append( "                   EOCM.ORGANIZATION_ID,\n" );
                sb.append( "                   AMD.DEPT_NAME) TOTAL,\n" );
                sb.append( "        (SELECT SUM(EFA.COST) SUM_COST2,\n" );
                sb.append( "                COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" );
                sb.append( "         FROM   ETS_FA_ASSETS EFA\n" );
                if( dto.getOrganizationId() > 0 ){
                	sb.append( "	 WHERE EFA.ORGANIZATION_ID = ? \n" );
                }
                
                sb.append( "         AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" );
                
                sb.append( "		 ) TOTAL2       \n" );
                sb.append( " ORDER BY TOTAL.ORGANIZATION_ID\n" );
                sb.append( "          ,TOTAL.DEPT_NAME"  );
                if( !StrUtil.isEmpty( dto.getEndDate() )){
                	sqlArgs.add(dto.getEndDate());
                } 
                if( !StrUtil.isEmpty( dto.getResponsibilityDept() )){
                	sqlArgs.add(dto.getResponsibilityDept());
                } 
                if( dto.getOrganizationId() > 0 ){
                	sqlArgs.add(dto.getOrganizationId());
                    sqlArgs.add(dto.getOrganizationId());
                } 
                
            } else {

            	sb.append( " SELECT EOCM.COMPANY,\n" );
                sb.append( "        TOTAL2.SUM_UNITS,\n" );
                sb.append( "        TOTAL2.SUM_COST2,\n" );
                sb.append( "        TOTAL.SUM_COUNT,\n" );
                sb.append( "        TOTAL.SUM_COST,\n" );
                
                sb.append( "        (CASE (100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2) \n" );
                sb.append( "         WHEN 0 \n" );
                sb.append( "         THEN  \n" );
                sb.append( "                           STR( \n " );
                sb.append( "         							ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3)  \n " );
                sb.append( "         						, 20 , 3 )  \n " );
                sb.append( "         ELSE STR( ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3) ,20, 3 ) END ) || '%'  MONEY_RATE, \n " );
                
                sb.append( "        (CASE (100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS) \n" );
                sb.append( "         WHEN 0 \n" );
                sb.append( "         THEN \n" );
                sb.append( "                           STR( \n " );
                sb.append( "         							ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3) \n " );
                sb.append( "         						  ,20, 3 )  \n " );
//                sb.append( "         ELSE CONVERT( VARCHAR , ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3)) END ) || '%'  AMOUNT_RATE \n" );
                sb.append( "         ELSE STR( ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3) ,20, 3 ) END ) || '%'  AMOUNT_RATE \n " );
                                 
//                sb.append( "        (DECODE(TRUNC(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2),\n" );
//                sb.append( "                0,\n" );
//                sb.append( "                STR_REPLACE(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3),\n" );
//                sb.append( "                        '.',\n" );
//                sb.append( "                        '0.'),\n" );
//                sb.append( "                TO_CHAR(ROUND(100 * TOTAL.SUM_COST / TOTAL2.SUM_COST2, 3))) || '%') MONEY_RATE,\n" );
//                sb.append( "        (DECODE(TRUNC(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS),\n" );
//                sb.append( "                0,\n" );
//                sb.append( "                STR_REPLACE(ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3),\n" );
//                sb.append( "                        '.',\n" );
//                sb.append( "                        '0.'),\n" );
//                sb.append( "                TO_CHAR(ROUND(100 * TOTAL.SUM_COUNT / TOTAL2.SUM_UNITS, 3))) || '%') AMOUNT_RATE\n" );
                sb.append( " FROM   ETS_OU_CITY_MAP EOCM,\n" );
                sb.append( "        (SELECT EFA.ORGANIZATION_ID,\n" );
                sb.append( "                COUNT(EFA.CURRENT_UNITS) SUM_COUNT,\n" );
                sb.append( "                SUM(EFA.COST) SUM_COST\n" );
                sb.append( "         FROM   ETS_FA_ASSETS      EFA,\n" );
                sb.append( "                ETS_ITEM_INFO      EII,\n" );
                sb.append( "                ETS_ITEM_MATCH     EIM\n" );
                sb.append( "         WHERE  EFA.ASSET_ID = EIM.ASSET_ID\n" );
                sb.append( "                AND EII.SYSTEMID = EIM.SYSTEMID\n" );
                
                if( dto.getOrganizationId() > 0 ){
                	sb.append( "            AND EFA.ORGANIZATION_ID = ? \n" );
                } 
                if( !StrUtil.isEmpty( dto.getEndDate() )){
                	sb.append( "            AND  EIM.MATCH_DATE <= ? \n" );
                }  
                
                sb.append( "                AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" );

                sb.append( "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL,\n" );
                sb.append( "        (SELECT EFA.ORGANIZATION_ID,\n" );
                sb.append( "                SUM(EFA.COST) SUM_COST2,\n" );
                sb.append( "                COUNT(EFA.CURRENT_UNITS) SUM_UNITS\n" );
                sb.append( "         FROM   ETS_FA_ASSETS EFA\n" );
                if( dto.getOrganizationId() > 0 ){
                	sb.append( "     WHERE  EFA.ORGANIZATION_ID = ? \n" );
                }
                
                sb.append( "         AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n" );
                
                sb.append( "         GROUP  BY EFA.ORGANIZATION_ID) TOTAL2       \n" );
                sb.append( " WHERE   EOCM.ORGANIZATION_ID = TOTAL.ORGANIZATION_ID AND\n" );
                sb.append( "  EOCM.ORGANIZATION_ID = TOTAL2.ORGANIZATION_ID \n" );
                sb.append( " ORDER BY EOCM.ORGANIZATION_ID" );
                
                if( dto.getOrganizationId() > 0 ){
                    sqlArgs.add(dto.getOrganizationId());
                } 
                
                if( !StrUtil.isEmpty( dto.getEndDate() )){
                	sqlArgs.add(dto.getEndDate());
                } 
                if( dto.getOrganizationId() > 0 ){
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
