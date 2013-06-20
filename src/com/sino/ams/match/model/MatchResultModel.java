package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: jialongchuan
 * Date: 2008-12-25
 * Time: 9:05:16
 * To change this template use File | Settings | File Templates.
 */


public class MatchResultModel extends AMSSQLProducer {
    public MatchResultModel(SfUserDTO userAccount, EtsOuCityMapDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter;
        
        StringBuffer sb = new StringBuffer();
        
        //视图方法
//        sb.append( " SELECT   \n " );
//        sb.append( " 	ORGANIZATION_ID,  \n " );
//        sb.append( " 	COMPANY,  \n " );
//        sb.append( " 	MIS_COUNT,  \n " );
//        sb.append( " 	MATCH_COUNT,  \n " );
//        sb.append( " 	NO_MATCH_COUNT,  \n " );
//        sb.append( " 	SYN_COUNT,  \n " );
//        sb.append( " 	NO_SYN_COUNT,  \n " );
//        sb.append( " 	MATCH_RATE,  \n " );
//        sb.append( " 	SYN_RATE   \n " );
//        sb.append( " FROM   \n " );
//        sb.append( " 	MATCH_RESULT_V   \n " );
        
//        int orgId = dto.getOrganizationId();
//        if( orgId > 0  ){
//        	sb.append( " WHERE AMRR.ORGANIZATION_ID = ?  \n " );
//        	sqlArgs.add(dto.getOrganizationId()); 
//        }
        
        //建立冗余表方法
//        sb.append( " SELECT   \n " );
//        sb.append( " 	AMRR.ORGANIZATION_ID,  \n " );
//        sb.append( " 	EOCM.COMPANY,  \n " );
//        sb.append( " 	MIS_COUNT,  \n " );
//        sb.append( " 	MATCH_COUNT,  \n " );
//        sb.append( " 	NO_MATCH_COUNT,  \n " );
//        sb.append( " 	SYN_COUNT,  \n " );
//        sb.append( " 	NO_SYN_COUNT,  \n " );
//        sb.append( " 	MATCH_RATE,  \n " );
//        sb.append( " 	SYN_RATE   \n " );
//        sb.append( " FROM   \n " );
//        sb.append( " 	AMS_MATCH_RESULT_REPORT AMRR,   \n " );
//        sb.append( " 	ETS_OU_CITY_MAP EOCM   \n " ); 
//        sb.append( " 	WHERE   \n " );
//        sb.append( " 	AMRR.ORGANIZATION_ID = EOCM.ORGANIZATION_ID    \n " );
//        
//        int orgId = dto.getOrganizationId();
//        if( orgId > 0  ){
//        	sb.append( " 	AND AMRR.ORGANIZATION_ID = ?  \n " );
//        	sqlArgs.add(dto.getOrganizationId()); 
//        }
        
        
      //TODO SJ 
//        String sqlStr = 
        int orgId = dto.getOrganizationId();
        sb.append( " SELECT EOCM.ORGANIZATION_ID, \n " );
        sb.append( "        EOCM.COMPANY,  \n " );
        sb.append( "        TOTAL_MIS_COUNT.MIS_COUNT MIS_COUNT,  \n " );
        sb.append( "        ISNULL(TOTAL_MATCH_COUNT.MATCH_COUNT,0) MATCH_COUNT, \n " );
        sb.append( "        ISNULL(TOTAL_NO_MATCH_COUNT.NO_MATCH_COUNT,0) NO_MATCH_COUNT, \n " );
        sb.append( "        ( CASE TOTAL_SYN_COUNT.SYN_COUNT WHEN NULL THEN 0 WHEN -1 THEN 0 ELSE TOTAL_SYN_COUNT.SYN_COUNT END ) SYN_COUNT, \n " );
        sb.append( "        TOTAL_NO_SYN_COUNT.NO_SYN_COUNT, \n " );
        sb.append( "        ( CASE 100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT WHEN 0 \n " );
        sb.append( " 	      THEN STR( ROUND(100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2) , 20, 3 ) \n " );
        sb.append( "          ELSE STR( ROUND(100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2) , 20, 3 ) END ) || '%' MATCH_RATE , \n " );
        sb.append( "        ( CASE 100 * TOTAL_SYN_COUNT.SYN_COUNT / TOTAL_MIS_COUNT.MIS_COUNT WHEN 0 \n " );
        sb.append( "          THEN STR( ROUND(100 * ISNULL( TOTAL_SYN_COUNT.SYN_COUNT , 0 ) / TOTAL_MIS_COUNT.MIS_COUNT, 2) , 20, 3 ) \n " );
        sb.append( "          ELSE STR( ROUND(100 * ISNULL( TOTAL_SYN_COUNT.SYN_COUNT , 0 ) / TOTAL_MIS_COUNT.MIS_COUNT , 2) , 20 ,3 ) END ) || '%' SYN_RATE  \n " );
                
        sb.append( "  FROM  ETS_OU_CITY_MAP EOCM (INDEX ETS_OU_CITY_MAP_PK),  \n " );
        sb.append( "  		(SELECT EFA.ORGANIZATION_ID,   \n " );
        sb.append( "        		COUNT(EFA.ASSET_ID) MIS_COUNT  \n " );
        sb.append( "  		   FROM ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) \n " );
        sb.append( "  		  WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%'   \n " );
        if( orgId > 0  ){
        	sb.append( " 	    AND EFA.ORGANIZATION_ID = ?  \n " );
        	sqlArgs.add(dto.getOrganizationId()); 
        }
//        sb.append( "   AND ( EFA.ORGANIZATION_ID = ? OR ? = -1  )  \n " );
        
        sb.append( "  		    AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n " );
        
        sb.append( "  		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_MIS_COUNT, \n " );
        sb.append( "  		(SELECT EFA.ORGANIZATION_ID,  \n " );
        sb.append( "        		COUNT(EFA.ASSET_ID) MATCH_COUNT \n " );
        sb.append( "  		   FROM ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) ,  \n " );
        sb.append( "      			ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2 ) \n " );
        sb.append( "  		  WHERE EFA.ASSET_ID = EIM.ASSET_ID \n " );
        if( orgId > 0  ){
        	sb.append( " 		AND EFA.ORGANIZATION_ID = ?  \n " );
        	sqlArgs.add(dto.getOrganizationId()); 
        }
//        sb.append( "   AND ( EFA.ORGANIZATION_ID = ? OR ? = -1  )  \n " );
        
        sb.append( "  		    AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n " );
        
        sb.append( "  		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_MATCH_COUNT, \n " );
        sb.append( "  		(SELECT EFA.ORGANIZATION_ID, \n " );
        sb.append( "        		COUNT(EFA.ASSET_ID) NO_MATCH_COUNT \n " );
        sb.append( "  		   FROM ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) \n " );
        sb.append( "  		  WHERE NOT EXISTS  \n ");
        sb.append( "  					(SELECT 1 FROM ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2 ) WHERE EFA.ASSET_ID = EIM.ASSET_ID) \n " );
//        sb.append( "   AND EFA.ASSETS_STATUS <>1" +
        if( orgId > 0  ){
        	sb.append( " 		AND EFA.ORGANIZATION_ID = ?  \n " );
        	sqlArgs.add(dto.getOrganizationId()); 
        }
        
        sb.append( "  		    AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n " );
        
        sb.append( "  		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_NO_MATCH_COUNT, \n " );
        sb.append( "  		(SELECT EFA.ORGANIZATION_ID, \n " );
        sb.append( "        		COUNT(EFA.ASSET_ID) SYN_COUNT  \n " );
        sb.append( "  		   FROM ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) , \n ");
        sb.append( " 	   			ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2 ) \n " );
        sb.append( "  		  WHERE EFA.ASSET_ID = EIM.ASSET_ID  \n " );
        sb.append( "   			AND EXISTS \n " );
        sb.append( "      				(SELECT 1 FROM ETS_MISFA_UPDATE_LOG EMUL (INDEX ETS_MISFA_UPDATE_LOG_N1) WHERE EMUL.ASSET_ID = EFA.ASSET_ID)  \n " );
        if( orgId > 0  ){
        	sb.append( " 		AND EFA.ORGANIZATION_ID = ?  \n " );
        	sqlArgs.add(dto.getOrganizationId()); 
        }
        
        sb.append( "  		    AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n " );
        
        sb.append( "  		  GROUP BY EFA.ORGANIZATION_ID) TOTAL_SYN_COUNT, \n " );
        
/*        sb.append( "  (SELECT EFA.ORGANIZATION_ID, \n " );
        sb.append( "        COUNT(EFA.ASSET_ID) NO_SYN_COUNT  \n " );
//        sb.append( "  FROM ETS_FA_ASSETS EFA , ETS_ITEM_MATCH EIM \n " );
        sb.append( "  FROM ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) , \n ");
        sb.append( " 	   ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2 ) \n " );
        sb.append( "  WHERE EFA.ASSET_ID = EIM.ASSET_ID  \n " );
        sb.append( "   AND NOT EXISTS  \n " );
        sb.append( "      (SELECT 1 FROM ETS_MISFA_UPDATE_LOG EMUL (INDEX ETS_MISFA_UPDATE_LOG_N1) WHERE EMUL.ASSET_ID = EFA.ASSET_ID)  \n " );
        if( orgId > 0  ){
        	sb.append( " 	AND EFA.ORGANIZATION_ID = ?  \n " );
        	sqlArgs.add(dto.getOrganizationId()); 
        }
        sb.append( "  GROUP BY EFA.ORGANIZATION_ID) TOTAL_NO_SYN_COUNT  \n " );*/
        
        sb.append( "  	 	  (SELECT EFA.ORGANIZATION_ID, COUNT(EFA.ASSET_ID) NO_SYN_COUNT  \n " );
        sb.append( "  	         FROM ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5) ,  \n " ); 
        sb.append( "  	              ETS_ITEM_MATCH EIM (INDEX ETS_ITEM_MATCH_110708_I2 ) ,  \n " );
        sb.append( "  	              ETS_ITEM_INFO EII, \n " );
        sb.append( "  	              ETS_SYSTEM_ITEM ESI, \n " );
        sb.append( "  	              AMS_MIS_EMPLOYEE AME, \n " );
        sb.append( "                  AMS_OBJECT_ADDRESS AOA, \n " );
        sb.append( "	              ETS_OBJECT EO \n " );
        sb.append( "	         WHERE EFA.ASSET_ID = EIM.ASSET_ID \n " );
        sb.append( "	           AND EIM.SYSTEMID = EII.SYSTEMID \n " );
        sb.append( "	           AND EII.ITEM_CODE = ESI.ITEM_CODE \n " );
        sb.append( "		       AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID \n " );
        sb.append( "	           AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n " );
        sb.append( "	           AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n " );
        sb.append( "	           AND NOT EXISTS (SELECT 1 FROM ETS_MISFA_UPDATE_LOG EMUL (INDEX ETS_MISFA_UPDATE_LOG_N1) WHERE EMUL.ASSET_ID = EFA.ASSET_ID)  \n " );
        if( orgId > 0  ){
        	sb.append( " 		   AND EFA.ORGANIZATION_ID = ?  \n " );
        	sqlArgs.add(dto.getOrganizationId()); 
        }
        sb.append( "	           --AND (EFA.ASSETS_DESCRIPTION <> ESI.ITEM_NAME OR  \n " );
        sb.append( "		       --    ISNULL(EFA.MODEL_NUMBER, '1') <> ISNULL(ESI.ITEM_SPEC, '1') OR  \n " );
        sb.append( "	           --    ISNULL(EFA.ASSIGNED_TO_NUMBER, '1') <> ISNULL(AME.EMPLOYEE_NUMBER, '1') OR  \n " );
        sb.append( "	           --    EFA.ASSETS_LOCATION_CODE <> EO.WORKORDER_OBJECT_CODE) \n " );
        sb.append( "	           AND (EFA.ASSETS_DESCRIPTION || EFA.MODEL_NUMBER || EFA.ASSIGNED_TO_NUMBER || EFA.ASSETS_LOCATION_CODE <> ESI.ITEM_NAME || ESI.ITEM_SPEC || AME.EMPLOYEE_NUMBER || EO.WORKORDER_OBJECT_CODE) \n " );
        
        sb.append( "  		       AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n " );
        
        sb.append( "	         GROUP BY EFA.ORGANIZATION_ID) TOTAL_NO_SYN_COUNT   \n " );

        sb.append( "  WHERE EOCM.ORGANIZATION_ID = TOTAL_MIS_COUNT.ORGANIZATION_ID \n " );
        sb.append( "    AND EOCM.ORGANIZATION_ID *= TOTAL_MATCH_COUNT.ORGANIZATION_ID \n " );
        sb.append( "    AND EOCM.ORGANIZATION_ID *= TOTAL_NO_MATCH_COUNT.ORGANIZATION_ID \n " );
        sb.append( "    AND EOCM.ORGANIZATION_ID *= TOTAL_SYN_COUNT.ORGANIZATION_ID \n " );
        sb.append( "    AND EOCM.ORGANIZATION_ID *= TOTAL_NO_SYN_COUNT.ORGANIZATION_ID \n " );
        
        if( orgId > 0  ){
        	sb.append( " 	AND EOCM.ORGANIZATION_ID = ?  \n " );
        	sqlArgs.add(dto.getOrganizationId()); 
        }
        
        //TODO SJ 
//        String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
//                "       EOCM.COMPANY, \n" +
//                "       TOTAL_MIS_COUNT.MIS_COUNT MIS_COUNT, \n" +
//                "       ISNULL(TOTAL_MATCH_COUNT.MATCH_COUNT,0) MATCH_COUNT,\n" +
//                "       ISNULL(TOTAL_NO_MATCH_COUNT.NO_MATCH_COUNT,0) NO_MATCH_COUNT,\n" +
//                "       ( CASE TOTAL_SYN_COUNT.SYN_COUNT WHEN NULL THEN 0 WHEN -1 THEN 0 ELSE TOTAL_SYN_COUNT.SYN_COUNT END ) SYN_COUNT, \n " + 
//                "       TOTAL_NO_SYN_COUNT.NO_SYN_COUNT,\n" +  
//                "       ( CASE 100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT WHEN 0 \n " +
//                "	      THEN STR_REPLACE( CONVERT( VARCHAR , ROUND(100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2) ), '.', '0.') \n  " +
//                "         ELSE CONVERT( VARCHAR , ROUND(100 * TOTAL_MATCH_COUNT.MATCH_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2) ) END ) || '%' MATCH_RATE , \n " +
//                "       ( CASE 100 * TOTAL_SYN_COUNT.SYN_COUNT / TOTAL_MIS_COUNT.MIS_COUNT WHEN 0 \n " +
//                "         THEN STR_REPLACE( CONVERT( VARCHAR , ROUND(100 * TOTAL_SYN_COUNT.SYN_COUNT / TOTAL_MIS_COUNT.MIS_COUNT, 2) ), '.', '0.') \n " +
//                "         ELSE CONVERT( VARCHAR , ROUND(100 * TOTAL_SYN_COUNT.SYN_COUNT / TOTAL_MIS_COUNT.MIS_COUNT , 2) ) END ) || '%' SYN_RATE  \n " +
//                
//                " FROM  ETS_OU_CITY_MAP EOCM,\n" +
//                " (SELECT EFA.ORGANIZATION_ID, \n" +
//                "       COUNT(EFA.ASSET_ID) MIS_COUNT\n" +
//                " FROM ETS_FA_ASSETS EFA\n" +
//                " WHERE EFA.BOOK_TYPE_CODE LIKE '%FA%' \n" +
//                "  AND ( EFA.ORGANIZATION_ID = ? OR ? = -1  )\n" +
//                " GROUP BY EFA.ORGANIZATION_ID) TOTAL_MIS_COUNT,\n" +
//                " (SELECT EFA.ORGANIZATION_ID, \n" +
//                "       COUNT(EFA.ASSET_ID) MATCH_COUNT\n" +
//                " FROM ETS_FA_ASSETS EFA, \n" +
//                "     ETS_ITEM_MATCH EIM\n" +
//                " WHERE EFA.ASSET_ID = EIM.ASSET_ID\n" +
//                "  AND ( EFA.ORGANIZATION_ID = ? OR ? = -1  ) \n" +
//                " GROUP BY EFA.ORGANIZATION_ID) TOTAL_MATCH_COUNT,\n" +
//                " (SELECT EFA.ORGANIZATION_ID,\n" +
//                "       COUNT(EFA.ASSET_ID) NO_MATCH_COUNT\n" +
//                " FROM ETS_FA_ASSETS EFA\n" +
//                " WHERE NOT EXISTS(SELECT 1 FROM ETS_ITEM_MATCH EIM WHERE EFA.ASSET_ID = EIM.ASSET_ID)\n" +
////                "  AND EFA.ASSETS_STATUS <>1" +
//                "  AND ( EFA.ORGANIZATION_ID = ? OR ? = -1  )\n" +
//                " GROUP BY EFA.ORGANIZATION_ID) TOTAL_NO_MATCH_COUNT,\n" +
//                " (SELECT EFA.ORGANIZATION_ID,\n" +
//                "       COUNT(EFA.ASSET_ID) SYN_COUNT \n" +
//                " FROM ETS_FA_ASSETS EFA, ETS_ITEM_MATCH EIM\n" +
//                " WHERE EFA.ASSET_ID = EIM.ASSET_ID \n" +
//                "  AND EXISTS\n" +
//                "     (SELECT 1 FROM ETS_MISFA_UPDATE_LOG EMUL WHERE EMUL.ASSET_ID = EFA.ASSET_ID) \n" +
//                " AND ( EFA.ORGANIZATION_ID = ? OR ? = -1  )\n" +
//                " GROUP BY EFA.ORGANIZATION_ID) TOTAL_SYN_COUNT,\n" +
//                " (SELECT EFA.ORGANIZATION_ID,\n" +
//                "       COUNT(EFA.ASSET_ID) NO_SYN_COUNT \n" +
//                " FROM ETS_FA_ASSETS EFA, ETS_ITEM_MATCH EIM\n" +
//                " WHERE EFA.ASSET_ID = EIM.ASSET_ID \n" +
//                "  AND NOT EXISTS\n" +
//                "     (SELECT 1 FROM ETS_MISFA_UPDATE_LOG EMUL WHERE EMUL.ASSET_ID = EFA.ASSET_ID) \n" +
//                " AND ( EFA.ORGANIZATION_ID = ? OR ? = -1  )\n" +
//                " GROUP BY EFA.ORGANIZATION_ID) TOTAL_NO_SYN_COUNT\n" +
//                " WHERE EOCM.ORGANIZATION_ID = TOTAL_MIS_COUNT.ORGANIZATION_ID\n" +
//                "  AND EOCM.ORGANIZATION_ID *= TOTAL_MATCH_COUNT.ORGANIZATION_ID\n" +
//                "  AND EOCM.ORGANIZATION_ID *= TOTAL_NO_MATCH_COUNT.ORGANIZATION_ID\n" +
//                "  AND EOCM.ORGANIZATION_ID *= TOTAL_SYN_COUNT.ORGANIZATION_ID\n" +
//                "  AND EOCM.ORGANIZATION_ID *= TOTAL_NO_SYN_COUNT.ORGANIZATION_ID\n" +
//                "  AND ( ? = -1 OR EOCM.ORGANIZATION_ID = ? ) ";
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId());
//            sqlArgs.add(dto.getOrganizationId()); 
        
        //以下SQL错误，(2009.4.16修改 su)
//        String sqlStr =
//                "SELECT EOCM.ORGANIZATION_ID,\n" +
//                        "       EOCM.COMPANY,\n" +
//                        "       dbo.NVL(MATCHED_V.MATCHED_COUNT, 0) MATCHED_COUNT,\n" +
//                        "       dbo.NVL(OVERPLUS_V.OVERPLUS_COUNT, 0) OVERPLUS_COUNT,\n" +
//                        "       dbo.NVL(UNMATCHED_V.UNMATCHED_COUNT, 0) UNMATCHED_COUNT,\n" +
//                        "       dbo.NVL(UNSYNCHRONIZED_V.UNSYNCHRONIZED_COUNT, 0) UNSYNCHRONIZED_COUNT,\n" +
//                        "       ISNULL(MIS_V.MIS_COUNT, 0) MIS_COUNT,\n" +
//                        "       (CASE CHARINDEX(ROUND(100 * MATCHED_COUNT / MIS_COUNT, 2) || '%', '.')\n" +
//                        "         WHEN 1 THEN\n" +
//                        "          '0' || ROUND(100 * MATCHED_COUNT / MIS_COUNT, 2) || '%'\n" +
//                        "         ELSE\n" +
//                        "          ROUND(100 * MATCHED_COUNT / MIS_COUNT, 2) || '%'\n" +
//                        "       END) MATCHED_RATE\n" +
////                        "       (CASE CHARINDEX(ROUND(100 * dbo.NVL(UNSYNCHRONIZED_V.UNSYNCHRONIZED_COUNT, 0) / MATCHED_COUNT, 2) || '%', '.')\n" +
////                        "         WHEN 1 THEN\n" +
////                        "          '0' || ROUND(100 * (1 - dbo.NVL(UNSYNCHRONIZED_V.UNSYNCHRONIZED_COUNT, 0) / MATCHED_COUNT), 2) || '%'\n" +
////                        "         ELSE\n" +
////                        "          ROUND(100 * (1 - dbo.NVL(UNSYNCHRONIZED_V.UNSYNCHRONIZED_COUNT, 0) / MATCHED_COUNT), 2) || '%'\n" +
////                        "       END) UNSYNCHRONIZED_RATE\n" +
//                        "FROM   ETS_OU_CITY_MAP EOCM,\n" +
//                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
//                        "               COUNT(1) MATCHED_COUNT\n" +
//                        "        FROM   ETS_FA_ASSETS EFA\n" +
//                        "        WHERE  EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
//                        "        AND    EXISTS (SELECT NULL FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" +
//                        "        AND    EFA.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EFA.ORGANIZATION_ID)))\n" +
//                        "        GROUP  BY EFA.ORGANIZATION_ID) MATCHED_V,\n" +
//                        "       (SELECT EII.ORGANIZATION_ID,\n" +
//                        "               COUNT(EII.SYSTEMID) OVERPLUS_COUNT\n" +
//                        "        FROM   ETS_SYSTEM_ITEM     ESI,\n" +
//                        "               ETS_ITEM_INFO       EII,\n" +
//                        "               ETS_OBJECT          EO,\n" +
//                        "               AMS_OBJECT_ADDRESS  AOA,\n" +
//                        "               AMS_COST_DEPT_MATCH ACDM,\n" +
//                        "               AMS_COST_CENTER_V   ACCV\n" +
//                        "        WHERE  ESI.ITEM_CODE = EII.ITEM_CODE\n" +
//                        "        AND    EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
//                        "        AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
//                        "        AND    EII.RESPONSIBILITY_DEPT *= ACDM.DEPT_CODE\n" +
//                        "        AND    ACDM.COST_CENTER_CODE *= ACCV.COST_CENTER_CODE\n" +
//                        "        AND    (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)\n" +
//                        "        AND    NOT EXISTS (SELECT NULL FROM ETS_ITEM_MATCH EIM WHERE EIM.SYSTEMID = EII.SYSTEMID)\n" +
//                        "        AND    NOT EXISTS (SELECT NULL FROM ETS_NO_MATCH ENM WHERE ENM.SYSTEMID = EII.SYSTEMID)\n" +
//                        "        AND    EII.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EII.ORGANIZATION_ID)))\n" +
//                        "        GROUP  BY EII.ORGANIZATION_ID) OVERPLUS_V,\n" +
//                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
//                        "               COUNT(1) UNMATCHED_COUNT\n" +
//                        "        FROM   ETS_FA_ASSETS EFA\n" +
//                        "        WHERE  EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
//                        "        AND    NOT EXISTS (SELECT NULL FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" +
//                        "        AND    EFA.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EFA.ORGANIZATION_ID)))\n" +
//                        "        GROUP  BY EFA.ORGANIZATION_ID) UNMATCHED_V,\n" +
//                        "       (SELECT EII.ORGANIZATION_ID,\n" +
//                        "               COUNT(EII.SYSTEMID) UNSYNCHRONIZED_COUNT\n" +
//                        "        FROM   ETS_SYSTEM_ITEM     ESI,\n" +
//                        "               ETS_FA_ASSETS       EFA,\n" +
//                        "               ETS_OBJECT          EO,\n" +
//                        "               AMS_OBJECT_ADDRESS  AOA,\n" +
//                        "               ETS_ITEM_MATCH      EIM,\n" +
//                        "               ETS_ITEM_MATCH_REC  EIMR,\n" +
//                        "               ETS_ITEM_INFO       EII,\n" +
//                        "               AMS_MIS_EMPLOYEE    AME,\n" +
//                        "               AMS_MIS_EMPLOYEE    AME2,\n" +
//                        "               ETS_PA_PROJECTS_ALL EPPA,\n" +
//                        "               AMS_MIS_DEPT        AMD\n" +
//                        "        WHERE  EII.SYSTEMID = EIM.SYSTEMID\n" +
//                        "        AND    EIM.ASSET_ID = EFA.ASSET_ID\n" +
//                        "        AND    EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
//                        "        AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
//                        "        AND    EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
//                        "        AND    EFA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
//                        "        AND    EII.RESPONSIBILITY_USER *= AME2.EMPLOYEE_ID\n" +
//                        "        AND    EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE\n" +
//                        "        AND    EII.ORGANIZATION_ID = AOA.ORGANIZATION_ID\n" +
//                        "        AND    ESI.ITEM_CODE = EII.ITEM_CODE\n" +
//                        "        AND     " + SyBaseSQLUtil.isNotNull("TODO") + " \n" +
//                        "        AND    AME.ENABLED = 'Y'\n" +
//                        "        AND    EIM.SYSTEMID = EIMR.SYSTEM_ID\n" +
//                        "        AND    (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80)\n" +
//                        "        AND    EIM.ASSET_ID = EIMR.ASSET_ID\n" +
//                        "        AND    (EFA.ASSIGNED_TO_NAME <> AME2.USER_NAME OR EFA.ASSETS_DESCRIPTION <> ESI.ITEM_NAME OR\n" +
//                        "              EFA.MODEL_NUMBER <> ESI.ITEM_SPEC OR EFA.ASSETS_LOCATION_CODE <> EO.LOCATION_CODE)\n" +
//                        "        AND    NOT EXISTS\n" +
//                        "         (SELECT NULL\n" +
//                        "                FROM   ETS_MISFA_UPDATE_LOG EMUL\n" +
//                        "                WHERE  EMUL.ASSET_ID = EFA.ASSET_ID\n" +
//                        "                AND    (EMUL.TRANS_STATUS = 0 OR (EMUL.TRANS_STATUS = 1 AND TOF_CHAR(EMUL.CREATION_DATE, 'YYYY-MM-DD') =\n" +
//      "                      TO_FCHAR(GETDATE(), 'YYYY-MM-DD'))))\n" +
//                        "        AND    EII.FINANCE_PROP = 'ASSETS'\n" +
//                        "        AND    EII.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EII.ORGANIZATION_ID)))\n" +
//                        "        AND    EII.PROJECTID *= EPPA.PROJECT_ID\n" +
//                        "        AND    (ESI.ITEM_NAME <> EFA.ASSETS_DESCRIPTION OR dbo.NVL(ESI.ITEM_SPEC, 'A') <> dbo.NVL(EFA.MODEL_NUMBER, 'A') OR\n" +
//                        "              EII.BARCODE <> EFA.TAG_NUMBER OR dbo.NVL(EFA.ASSETS_LOCATION, 'A') <> dbo.NVL(EO.WORKORDER_OBJECT_NAME, 'A') OR\n" +
//                        "              dbo.NVL(EFA.ASSIGNED_TO_NAME, 'A') <> dbo.NVL(AME2.USER_NAME, 'A'))\n" +
//                        "        GROUP  BY EII.ORGANIZATION_ID) UNSYNCHRONIZED_V,\n" +
//                        "       (SELECT EFA.ORGANIZATION_ID,\n" +
//                        "               COUNT(EFA.ASSET_ID) MIS_COUNT\n" +
//                        "        FROM   ETS_FA_ASSETS EFA\n" +
//                        "        WHERE  EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
//                        "        AND    EFA.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EFA.ORGANIZATION_ID)))\n" +
//                        "        GROUP  BY EFA.ORGANIZATION_ID) MIS_V\n" +
//                        "WHERE  EOCM.ORGANIZATION_ID *= MATCHED_V.ORGANIZATION_ID\n" +
//                        "AND    EOCM.ORGANIZATION_ID *= OVERPLUS_V.ORGANIZATION_ID\n" +
//                        "AND    EOCM.ORGANIZATION_ID *= UNMATCHED_V.ORGANIZATION_ID\n" +
//                        "AND    EOCM.ORGANIZATION_ID *= UNSYNCHRONIZED_V.ORGANIZATION_ID\n" +
//                        "AND    EOCM.ORGANIZATION_ID *= MIS_V.ORGANIZATION_ID\n" +
//                        "AND    EOCM.ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EOCM.ORGANIZATION_ID)))";
//        sqlArgs.add(dto.getOrganizationId());
//        sqlArgs.add(dto.getOrganizationId());
//        sqlArgs.add(dto.getOrganizationId());
//        sqlArgs.add(dto.getOrganizationId());
//        sqlArgs.add(dto.getOrganizationId());
//        sqlArgs.add(dto.getOrganizationId());

        
        sqlModel.setSqlStr( sb.toString() ); 
        
//        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    public SQLModel getPageQueryModel2() {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter;
        
    	StringBuffer sb = new StringBuffer();

        sb.append( " SELECT " );
        sb.append( "  EOCM.ORGANIZATION_ID,  \n " );
        sb.append( "         EOCM.COMPANY,  \n " );
        sb.append( "         (SELECT COUNT(EFA.ORGANIZATION_ID)  \n " );
        sb.append( "         FROM   ETS_FA_ASSETS EFA  \n " );
        sb.append( "         WHERE  EFA.BOOK_TYPE_CODE LIKE '%FA%'  \n " );
        sb.append( "                AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID) MIS_COUNT,  \n " );
        sb.append( "         (SELECT COUNT(EFA.ORGANIZATION_ID)  \n " );
        sb.append( "          FROM   ETS_FA_ASSETS  EFA,  \n " );
        sb.append( "                 ETS_ITEM_MATCH EIM  \n " );
        sb.append( "         WHERE  EFA.ASSET_ID = EIM.ASSET_ID  \n " );
        sb.append( "                 AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID) MATCH_COUNT,  \n " );
        sb.append( "         (SELECT COUNT(EFA.ORGANIZATION_ID)  \n " );
        sb.append( "          FROM   ETS_FA_ASSETS EFA  \n " );
        sb.append( "          WHERE  NOT EXISTS (SELECT 1  \n " );
        sb.append( "                  FROM   ETS_ITEM_MATCH EIM  \n " );
        sb.append( "                  WHERE  EFA.ASSET_ID = EIM.ASSET_ID)  \n " );
        sb.append( "                 AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID) NO_MATCH_COUNT,  \n " );
        sb.append( "         (SELECT COUNT(EFA.ORGANIZATION_ID)  \n " );
        sb.append( "          FROM   ETS_FA_ASSETS  EFA,  \n " );
        sb.append( "                 ETS_ITEM_MATCH EIM  \n " );
        sb.append( "          WHERE  EFA.ASSET_ID = EIM.ASSET_ID  \n " );
        sb.append( "                 AND EXISTS  \n " );
        sb.append( "           (SELECT 1  \n " );
        sb.append( "                  FROM   ETS_MISFA_UPDATE_LOG EMUL  \n " );
        sb.append( "                  WHERE  EMUL.ASSET_ID = EFA.ASSET_ID)  \n " );
        sb.append( "                 AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID) SYN_COUNT,  \n " );
               
        sb.append( "         (SELECT COUNT(EFA.ORGANIZATION_ID)  \n " );
        sb.append( "          FROM   ETS_FA_ASSETS  EFA,  \n " );
        sb.append( "                 ETS_ITEM_MATCH EIM  \n " );
        sb.append( "          WHERE  EFA.ASSET_ID = EIM.ASSET_ID  \n " );
        sb.append( "                 AND NOT EXISTS  \n " );
        sb.append( "           (SELECT 1  \n " );
        sb.append( "                  FROM   ETS_MISFA_UPDATE_LOG EMUL  \n " );
        sb.append( "                  WHERE  EMUL.ASSET_ID = EFA.ASSET_ID)  \n " );
        sb.append( "                 AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID) NO_SYN_COUNT,   \n " );
        sb.append( "         0 MATCH_RATE,  \n " );
        sb.append( "         0 SYN_RATE  \n " );
        sb.append( "  FROM   ETS_OU_CITY_MAP EOCM  \n " ); 
        sb.append( "  WHERE  ? = -1 OR EOCM.ORGANIZATION_ID = ? " );
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setSqlStr( sb.toString() );
		return sqlModel;
    }
}

