package com.sino.ams.newasset.bean.sql;


/**
 * 
 * @系统名称: 
 * @功能描述: 资产选择SQL 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 4, 2011
 */
public class AssetsLookupSQL {
	
	
	/**
	 * 
	 * @param searchRespDept	是否有条件查询责任部门
	 * @param searchRespUser	是否有条件查询责任人
	 * @return
	 */
	public static StringBuilder getBTWCompSQL( boolean searchRespDept , boolean searchRespUser ){
		StringBuilder sb = new StringBuilder();
		sb.append( " SELECT \n " );
		sb.append( " 	EII.BARCODE, \n " );
		sb.append( " 	EFA.ASSET_NUMBER, \n " ); 
		sb.append( " 	EFA.ASSET_ID,  \n ");
		sb.append( " 	EFA.ASSETS_DESCRIPTION,  \n ");
		sb.append( " 	EFA.MODEL_NUMBER,  \n ");
		sb.append( " 	ESI.ITEM_NAME,  \n ");
		sb.append( " 	ESI.ITEM_SPEC,  \n ");
		sb.append( " 	NULL VENDOR_NAME,  \n ");
		sb.append( " 	EFA.UNIT_OF_MEASURE,  \n ");  
		sb.append( " 	ISNULL(EFA.CURRENT_UNITS,  1) CURRENT_UNITS,  \n ");
		sb.append( " 	AM.MANUFACTURER_NAME,  \n "); 
		sb.append( " 	dbo.IS_IMPORTANT_ASSETS( EII.CONTENT_CODE ) IMPORTANT_FLAG,  \n ");
		sb.append( " 	EII.CONTENT_CODE OLD_FA_CATEGORY_CODE,  \n ");
		sb.append( " 	EII.RESPONSIBILITY_DEPT OLD_RESPONSIBILITY_DEPT,  \n ");
		sb.append( " 	AMD.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,  \n ");
		sb.append( " 	EO.WORKORDER_OBJECT_NO OLD_LOCATION,  \n ");
		sb.append( " 	EO.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,  \n ");
		sb.append( " 	EO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,  \n ");
		sb.append( " 	EII.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,  \n ");
		sb.append( " 	AME.USER_NAME OLD_RESPONSIBILITY_USER_NAME,  \n ");
		sb.append( " 	EFA.DATE_PLACED_IN_SERVICE,  \n ");
		sb.append( " 	EFA.COST,  \n ");
		sb.append( " 	EFA.SCRAP_VALUE,  \n ");
		sb.append( " 	EFA.DEPRN_COST,  \n ");
		sb.append( " 	EFA.COST RETIREMENT_COST,  \n ");
		sb.append( " 	EFA.DEPRECIATION_ACCOUNT OLD_DEPRECIATION_ACCOUNT,  \n ");
		sb.append( " 	ISNULL(EFA.COST,  0) - ISNULL(EFA.DEPRN_COST, 0) DEPRECIATION,  \n ");
		sb.append( " 	dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,EFA.ASSETS_STATUS),'ASSETS_STATUS') ASSETS_STATUS_DES ,  \n ");
		sb.append( " 	EFA.IMPAIR_RESERVE, \n ");  
		sb.append( "    EFA.DEPRN_LEFT_MONTH ,  \n ");  
		sb.append( " 	ISNULL(EFA.COST, 0) - ISNULL(EFA.DEPRN_COST, 0) SUM_DEPRECIATION,  \n ");  
		
		sb.append( "    EII.SPECIALITY_DEPT,  \n "); 
		sb.append( "    AMD2.DEPT_NAME SPECIALITY_DEPT_NAME  \n "); 
		
		sb.append( " FROM   \n ");
		sb.append( "      ETS_OU_CITY_MAP     EOCM,  \n ");
		sb.append( "      ETS_OBJECT          EO, \n ");
		sb.append( "      AMS_OBJECT_ADDRESS  AOA, \n ");
		sb.append( "      AMS_MIS_EMPLOYEE    AME, \n ");
		sb.append( "      AMS_MIS_DEPT        AMD, \n ");
		
		sb.append( "      AMS_MIS_DEPT        AMD2, \n ");
		
		sb.append( "      ETS_SYSTEM_ITEM     ESI, \n ");
		sb.append( "      ETS_PA_PROJECTS_ALL EPPA, \n ");
		sb.append( "      AMS_MANUFACTURER    AM, \n ");
		sb.append( "      ETS_FA_ASSETS       EFA, \n ");
		sb.append( "      ETS_ITEM_MATCH      EIM, \n ");
		sb.append( "      ETS_ITEM_INFO       EII \n ");
		sb.append( "  WHERE EII.SYSTEMID = EIM.SYSTEMID \n ");  
		sb.append( "    AND EIM.ASSET_ID = EFA.ASSET_ID \n ");  
		
		sb.append( "    AND EII.ITEM_CODE = ESI.ITEM_CODE \n ");
		sb.append( "  	AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n ");
		sb.append( "  	AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n ");
		sb.append( "  	AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n ");
		sb.append( "  	AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n ");
		sb.append( "  	AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID   \n "); 		
		if( searchRespDept ){
			sb.append( "  	AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID  \n "); 
		}else{
			sb.append( "  	AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID  \n "); 
		}
		if( searchRespUser ){
			sb.append( "  	AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE  \n "); 
		}else{
			sb.append( "  	AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE  \n "); 
			
		}
		sb.append( "    AND EII.SPECIALITY_DEPT *= AMD2.DEPT_CODE \n ");
		
		sb.append( "  	AND EII.PROJECTID *= EPPA.PROJECT_ID  \n "); 
		sb.append( "  	AND ( CONVERT( INT , EO.OBJECT_CATEGORY ) <= 73 OR CONVERT( INT , EO.OBJECT_CATEGORY ) >= 75 ) \n ");
		sb.append( "    AND (EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE = '' OR EII.DISABLE_DATE > GETDATE() ) \n ");
		sb.append( "    AND NOT EXISTS (  \n ");
		sb.append( "      SELECT  \n ");
		sb.append( "      	NULL   \n ");
		sb.append( "      FROM \n "); 
		sb.append( "      	AMS_ASSETS_RESERVED AAR  \n ");
		sb.append( "      WHERE  \n ");
		sb.append( "     	AAR.BARCODE = EII.BARCODE)  \n ");
		
//		sb.append( "    AND NOT EXISTS (  \n ");
//		sb.append( "      SELECT  \n ");
//		sb.append( "    	NULL \n "); 
//		sb.append( "      FROM  \n ");
//		sb.append( "    	AMS_ASSETS_CHK_LOG AACL  \n ");
//		sb.append( "      WHERE AACL.BARCODE = EII.BARCODE  \n ");
//		sb.append( "      AND   AACL.ORDER_TYPE <> 'ASS-CHK'  \n ");
//		sb.append( "      AND   (AACL.SYN_STATUS = 0 OR AACL.SYN_STATUS = 2))  \n ");
		sb.append( "    AND  EII.ORGANIZATION_ID = ?  \n ");
		
		sb.append( "    AND (EII.SPECIALITY_DEPT = ? OR LTRIM(EII.SPECIALITY_DEPT) IS NULL) \n ");
		//sb.append( "    AND EII.SPECIALITY_DEPT = ? \n ");

		sb.append( "    AND (EII.ITEM_STATUS = ? OR EII.ITEM_STATUS = ?) \n ");
		return sb;
	}
	
	public static String getBTWCompSQLPlanForEmpty(){
//		return  "PLAN '( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nested ( nl_join ( nested ( nl_join ( nl_join ( nl_join ( i_scan ETS_OU_CITY_MAP_PK ( table ( EOCM ETS_OU_CITY_MAP ) ) ) ( i_scan ETS_ITEM_INFO_110708_N7 ( table ( EII ETS_ITEM_INFO ) ) ) ) ( i_scan AMS_OBJECT_ADDRESS_PK ( table ( AOA AMS_OBJECT_ADDRESS ) ) ) ) ( i_scan ETS_OBJECT_I1 ( table ( EO ETS_OBJECT ) ) ) ) ( subq ( scalar_agg ( i_scan AMS_ASSETS_RESERVED_I1 ( table ( AAR AMS_ASSETS_RESERVED ) ) ) ) ) ) ( i_scan AMS_MIS_EMPLOYEE_PK ( table ( AME AMS_MIS_EMPLOYEE ) ) ) ) ( subq ( scalar_agg ( i_scan AMS_ASSETS_CHK_LOG_PK ( table ( AACL AMS_ASSETS_CHK_LOG ) ) ) ) ) ) ( i_scan ETS_PA_PROJECTS_ALL_PK ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ) ) ( i_scan AMS_MANUFACTURER_PK ( table ( AM AMS_MANUFACTURER ) ) ) ) ( i_scan ETS_SYSTEM_ITEM_PK ( table ( ESI ETS_SYSTEM_ITEM ) ) ) ) ( i_scan AMS_MIS_DEPT_PK ( table ( AMD AMS_MIS_DEPT ) ) ) ) ( i_scan ETS_ITEM_MATCH_T2 ( table ( EIM ETS_ITEM_MATCH ) ) ) ) ( i_scan ETS_FA_ASSETS_PK ( table ( EFA ETS_FA_ASSETS ) ) ) ) ( prop ( table ( EOCM ETS_OU_CITY_MAP ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EII ETS_ITEM_INFO ) ) ( parallel 1 ) ( prefetch 64 ) ( lru ) ) ( prop ( table ( AOA AMS_OBJECT_ADDRESS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EO ETS_OBJECT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AAR AMS_ASSETS_RESERVED ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AME AMS_MIS_EMPLOYEE ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AACL AMS_ASSETS_CHK_LOG ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AM AMS_MANUFACTURER ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( ESI ETS_SYSTEM_ITEM ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AMD AMS_MIS_DEPT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EIM ETS_ITEM_MATCH ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EFA ETS_FA_ASSETS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) )'" ;
		return  "PLAN '( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nested ( nl_join ( nl_join ( nl_join ( i_scan ETS_OU_CITY_MAP_PK ( table ( EOCM ETS_OU_CITY_MAP ) ) ) ( i_scan ETS_ITEM_INFO_110708_N7 ( table ( EII ETS_ITEM_INFO ) ) ) ) ( i_scan AMS_OBJECT_ADDRESS_PK ( table ( AOA AMS_OBJECT_ADDRESS ) ) ) ) ( i_scan ETS_OBJECT_I1 ( table ( EO ETS_OBJECT ) ) ) ) ( subq ( scalar_agg ( i_scan AMS_ASSETS_RESERVED_I1 ( table ( AAR AMS_ASSETS_RESERVED ) ) ) ) ) ) ( i_scan AMS_MIS_EMPLOYEE_PK ( table ( AME AMS_MIS_EMPLOYEE ) ) ) )  ( i_scan ETS_PA_PROJECTS_ALL_PK ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ) ) ( i_scan AMS_MANUFACTURER_PK ( table ( AM AMS_MANUFACTURER ) ) ) ) ( i_scan ETS_SYSTEM_ITEM_PK ( table ( ESI ETS_SYSTEM_ITEM ) ) ) ) ( i_scan AMS_MIS_DEPT_PK ( table ( AMD AMS_MIS_DEPT ) ) ) ) ( i_scan ETS_ITEM_MATCH_T2 ( table ( EIM ETS_ITEM_MATCH ) ) ) ) ( i_scan ETS_FA_ASSETS_PK ( table ( EFA ETS_FA_ASSETS ) ) ) ) ( prop ( table ( EOCM ETS_OU_CITY_MAP ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EII ETS_ITEM_INFO ) ) ( parallel 1 ) ( prefetch 64 ) ( lru ) ) ( prop ( table ( AOA AMS_OBJECT_ADDRESS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EO ETS_OBJECT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AAR AMS_ASSETS_RESERVED ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AME AMS_MIS_EMPLOYEE ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AM AMS_MANUFACTURER ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( ESI ETS_SYSTEM_ITEM ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AMD AMS_MIS_DEPT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EIM ETS_ITEM_MATCH ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EFA ETS_FA_ASSETS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) )'" ;
	}
	public static String getBTWCompSQLPlanForItem(){
		return  "PLAN '( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nested ( nl_join ( nl_join ( nl_join ( i_scan ETS_OU_CITY_MAP_PK ( table ( EOCM ETS_OU_CITY_MAP ) ) ) ( i_scan ETS_ITEM_INFO_110708_N7 ( table ( EII ETS_ITEM_INFO ) ) ) ) ( i_scan AMS_OBJECT_ADDRESS_PK ( table ( AOA AMS_OBJECT_ADDRESS ) ) ) ) ( i_scan ETS_OBJECT_I1 ( table ( EO ETS_OBJECT ) ) ) ) ( subq ( scalar_agg ( i_scan AMS_ASSETS_RESERVED_I1 ( table ( AAR AMS_ASSETS_RESERVED ) ) ) ) ) ) ( i_scan AMS_MIS_EMPLOYEE_PK ( table ( AME AMS_MIS_EMPLOYEE ) ) ) ) ( i_scan AMS_MANUFACTURER_PK ( table ( AM AMS_MANUFACTURER ) ) ) ) ( i_scan AMS_MIS_DEPT_PK ( table ( AMD AMS_MIS_DEPT ) ) ) ) ( i_scan ETS_SYSTEM_ITEM_PK ( table ( ESI ETS_SYSTEM_ITEM ) ) ) ) ( i_scan ETS_PA_PROJECTS_ALL_PK ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ) ) ( i_scan ETS_ITEM_MATCH_T2 ( table ( EIM ETS_ITEM_MATCH ) ) ) ) ( i_scan ETS_FA_ASSETS_PK ( table ( EFA ETS_FA_ASSETS ) ) ) ) ( prop ( table ( EOCM ETS_OU_CITY_MAP ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EII ETS_ITEM_INFO ) ) ( parallel 1 ) ( prefetch 64 ) ( lru ) ) ( prop ( table ( AOA AMS_OBJECT_ADDRESS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EO ETS_OBJECT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AAR AMS_ASSETS_RESERVED ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AME AMS_MIS_EMPLOYEE ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AM AMS_MANUFACTURER ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AMD AMS_MIS_DEPT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( ESI ETS_SYSTEM_ITEM ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EIM ETS_ITEM_MATCH ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EFA ETS_FA_ASSETS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) )'" ;
//		return  "PLAN '( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nested ( nl_join ( nested ( nl_join ( nl_join ( nl_join ( i_scan ETS_OU_CITY_MAP_PK ( table ( EOCM ETS_OU_CITY_MAP ) ) ) ( i_scan ETS_ITEM_INFO_110708_N7 ( table ( EII ETS_ITEM_INFO ) ) ) ) ( i_scan AMS_OBJECT_ADDRESS_PK ( table ( AOA AMS_OBJECT_ADDRESS ) ) ) ) ( i_scan ETS_OBJECT_I1 ( table ( EO ETS_OBJECT ) ) ) ) ( subq ( scalar_agg ( i_scan AMS_ASSETS_RESERVED_I1 ( table ( AAR AMS_ASSETS_RESERVED ) ) ) ) ) ) ( i_scan AMS_MIS_EMPLOYEE_PK ( table ( AME AMS_MIS_EMPLOYEE ) ) ) ) ( subq ( scalar_agg ( i_scan AMS_ASSETS_CHK_LOG_PK ( table ( AACL AMS_ASSETS_CHK_LOG ) ) ) ) ) ) ( i_scan AMS_MANUFACTURER_PK ( table ( AM AMS_MANUFACTURER ) ) ) ) ( i_scan AMS_MIS_DEPT_PK ( table ( AMD AMS_MIS_DEPT ) ) ) ) ( i_scan ETS_SYSTEM_ITEM_PK ( table ( ESI ETS_SYSTEM_ITEM ) ) ) ) ( i_scan ETS_PA_PROJECTS_ALL_PK ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ) ) ( i_scan ETS_ITEM_MATCH_T2 ( table ( EIM ETS_ITEM_MATCH ) ) ) ) ( i_scan ETS_FA_ASSETS_PK ( table ( EFA ETS_FA_ASSETS ) ) ) ) ( prop ( table ( EOCM ETS_OU_CITY_MAP ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EII ETS_ITEM_INFO ) ) ( parallel 1 ) ( prefetch 64 ) ( lru ) ) ( prop ( table ( AOA AMS_OBJECT_ADDRESS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EO ETS_OBJECT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AAR AMS_ASSETS_RESERVED ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AME AMS_MIS_EMPLOYEE ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AACL AMS_ASSETS_CHK_LOG ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AM AMS_MANUFACTURER ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AMD AMS_MIS_DEPT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( ESI ETS_SYSTEM_ITEM ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EIM ETS_ITEM_MATCH ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EFA ETS_FA_ASSETS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) )'" ;
	}
	
	public static String getItemMaintainPlan(){
		return "";
	}
	
	public static String getTransDisPlans(){
		StringBuilder sb = new StringBuilder();
		
		sb.append( "PLAN '( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nested ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( i_scan ETS_FA_ASSETS_PK ( table ( EFA ETS_FA_ASSETS ) ) ) ( i_scan ETS_OU_CITY_MAP_PK ( table ( EOCM ETS_OU_CITY_MAP ) ) ) ) ( i_scan "  );  
		sb.append( "ETS_ITEM_MATCH_I1 ( table ( EIM ETS_ITEM_MATCH ) ) ) ) ( i_scan ETS_ITEM_INFO_PK ( table ( EII ETS_ITEM_INFO ) ) ) ) ( i_scan AMS_OBJECT_ADDRESS_PK ( table ( AOA AMS_OBJECT_ADDRESS ) ) ) ) ( i_scan ETS_OBJECT_PK ( table ( EO ETS_OBJECT ) ) ) ) ( subq ( sc"  ); 
		sb.append( "alar_agg ( i_scan AMS_ASSETS_RESERVED_I1 ( table ( AAR AMS_ASSETS_RESERVED ) ) ) ) ) ) ( i_scan AMS_MIS_DEPT_PK ( table ( AMD AMS_MIS_DEPT ) ) ) ) ( i_scan AMS_MIS_EMPLOYEE_PK ( table ( AME AMS_MIS_EMPLOYEE ) ) ) ) ( i_scan AMS_MIS_DEPT_PK ( table ( AMD2 "  );  
		sb.append( "AMS_MIS_DEPT ) ) ) ) ( i_scan ETS_SYSTEM_ITEM_PK ( table ( ESI ETS_SYSTEM_ITEM ) ) ) ) ( i_scan ETS_PA_PROJECTS_ALL_PK ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ) ) ( i_scan AMS_MANUFACTURER_PK ( table ( AM AMS_MANUFACTURER ) ) ) ) ( prop ( table ( EFA ETS_F"  ); 
		sb.append( "A_ASSETS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EOCM ETS_OU_CITY_MAP ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EIM ETS_ITEM_MATCH ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EII ETS_ITEM_INFO )"  );  
		sb.append( ") ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AOA AMS_OBJECT_ADDRESS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EO ETS_OBJECT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AAR AMS_ASSETS_RESERVED ) ) ( para"  ); 
		sb.append( "llel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AMD AMS_MIS_DEPT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AME AMS_MIS_EMPLOYEE ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AMD2 AMS_MIS_DEPT ) ) ( parallel 1 ) ( pr"  ); 
		sb.append( "efetch 16 ) ( lru ) ) ( prop ( table ( ESI ETS_SYSTEM_ITEM ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AM AMS_MANUFACTURER ) ) ( parallel 1 ) ( prefet"  ); 
		sb.append( "ch 16 ) ( lru ) )'"  ); 
		
		return sb.toString();
	}
}
