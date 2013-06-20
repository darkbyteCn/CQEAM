package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

public class MisAssetsQueryModel extends AMSSQLProducer {

    public MisAssetsQueryModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public void setServletConfig(ServletConfigDTO servletConfig) {
        super.setServletConfig(servletConfig);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * 功能：返回页面翻页查询时所需要的SQLModel
     * @return SQLModel
     * @throws SQLModelException
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
//            if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
//                if (dto.getIsTD().equals("Y")) {
//                    sqlModel = getSXTDQueryModel();
//                } else {
//                    sqlModel = getSXQueryModel();
//                }
//            } else {
            
            
            	StringBuilder sb = new StringBuilder();
//                String sqlStr = 
        		sb.append(  " SELECT " );
                sb.append(  " EFA.TAG_NUMBER,");
                sb.append(  " EFA.MIS_TAG_NUMBER, ");
                sb.append(  " EFA.ASSET_NUMBER, ");
                sb.append(  " EFA.SEGMENT1, ");
                sb.append(  " EFA.FA_CATEGORY1, ");
                sb.append(  " EFA.SEGMENT2, ");
                sb.append(  " EFA.FA_CATEGORY2, ");
                sb.append(  " EFA.ASSETS_DESCRIPTION, ");
                sb.append(  " EFA.MODEL_NUMBER, ");
                sb.append(  " CONVERT( INT , EFA.CURRENT_UNITS ) CURRENT_UNITS , ");
                sb.append(  " EFA.UNIT_OF_MEASURE, ");
                sb.append(  " EFA.ASSETS_LOCATION_CODE, ");
                sb.append(  " EFA.ASSETS_LOCATION, ");
                sb.append(  " EFA.ASSIGNED_TO_NAME, ");
                sb.append(  " EFA.ASSIGNED_TO_NUMBER, ");
                sb.append(  " EPPA.SEGMENT1 PROJECT_NUMBER, ");
                sb.append(  " EFA.PROJECT_NAME, ");
                sb.append(  " EPPA.PROJECT_TYPE, ");
                sb.append(  " EFA.LIFE_IN_YEARS, ");
                sb.append(  " EFA.DATE_PLACED_IN_SERVICE, ");
                sb.append(  " EFA.ASSETS_CREATE_DATE, ");
//                sb.append(  " EFA.ORIGINAL_COST, ");
//                sb.append(  " STR( EFA.COST , 15 , 2 ) COST, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.ORIGINAL_COST ) ORIGINAL_COST, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.COST ) COST, ");
                
                
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.DEPRN_RESERVE ) DEPRN_RESERVE, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.DEPRN_COST ) DEPRN_COST, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.SCRAP_VALUE ) SCRAP_VALUE, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.IMPAIR_RESERVE ) IMPAIR_RESERVE, ");
//                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.COMPANY_CODE ) COMPANY_CODE, ");

//                sb.append(  " EFA.DEPRN_RESERVE, ");
//                sb.append(  " EFA.DEPRN_COST, ");
//                sb.append(  " EFA.SCRAP_VALUE, ");
//                sb.append(  " EFA.IMPAIR_RESERVE, ");
                sb.append(  " EFA.COMPANY_CODE, ");
                
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.NET_ASSET_VALUE ) NET_ASSET_VALUE, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.DEPRN_AMOUNT ) DEPRN_AMOUNT, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.YTD_DEPRN ) YTD_DEPRN, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.IMPAIR_AMOUNT ) IMPAIR_AMOUNT, ");
                sb.append(  " CONVERT( DECIMAL(20,2) , EFA.YTD_IMPAIRMENT ) YTD_IMPAIRMENT, ");
                
//                sb.append(  " EFA.NET_ASSET_VALUE, ");
//                sb.append(  " EFA.DEPRN_AMOUNT, ");
//                sb.append(  " EFA.YTD_DEPRN, ");
//                sb.append(  " EFA.IMPAIR_AMOUNT, ");
//                sb.append(  " EFA.YTD_IMPAIRMENT, ");
                
                sb.append(  " EOCM.COMPANY, ");
                sb.append(  " EFA.BOOK_TYPE_CODE, ");
                sb.append(  " EOCM.BOOK_TYPE_NAME, ");
                sb.append(  " EFA.DEPRECIATION_ACCOUNT, ");
                sb.append(  " (SELECT ");
                sb.append(  " AAV.ACCOUNT_NAME ");
                sb.append(  " FROM");
                sb.append(  " AMS_ACCOUNT_V AAV");
                sb.append(  " WHERE");
                sb.append(  " AAV.ACCOUNT_CODE_1 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 1, 4)");
                sb.append(  " AND AAV.ACCOUNT_CODE_2 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)");
                sb.append(  " AND AAV.ACCOUNT_CODE_3 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 13, 10)");
                sb.append(  " AND AAV.ACCOUNT_CODE_4 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 24, 6)");
                sb.append(  " AND AAV.ACCOUNT_CODE_5 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 31, 8)");
                sb.append(  " AND AAV.ACCOUNT_CODE_6 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 40, 4)");
                sb.append(  " AND AAV.ACCOUNT_CODE_7 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 45, 6))");
                sb.append(  " DEPRECIATION_ACCOUNT_NAME, ");
                sb.append(  " ( CASE EFA.IS_RETIREMENTS WHEN 0 THEN '未报废' ELSE '报废' END) IS_RETIREMENTS");
                
                sb.append(  " FROM");
                if ( userAccount.getIsTd().equals("Y")) {
            		sb.append(  " ETS_FA_ASSETS_TD       EFA, ");
            	}else{
            		sb.append(  " ETS_FA_ASSETS       EFA (INDEX ETS_FA_ASSETS_TMP1) , ");
            	}
                sb.append(  " ETS_PA_PROJECTS_ALL EPPA, ");
                sb.append(  " ETS_OU_CITY_MAP     EOCM");
                sb.append(  " WHERE  " );
                
                if(!dto.getProjectNumber().equals("")){
                	sb.append(  " EFA.PROJECT_ID = EPPA.PROJECT_ID" );
                }else{
                	sb.append(  " EFA.PROJECT_ID *= EPPA.PROJECT_ID" );
                }
                
                sb.append(  " AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID" );
                
                
               
                
//                if( dto.getOrganizationId() > 0 ){
//                	sb.append(  " AND EFA.ORGANIZATION_ID = ? " );
//                }
                
                if( !StrUtil.isEmpty( dto.getFaCategory1() ) ){
                	sb.append(  " AND EFA.FA_CATEGORY1 LIKE ? " );
                } 
                
//                if( !StrUtil.isEmpty( dto.getFaCategory2() ) ){
//                	sb.append(  " AND EFA.FA_CATEGORY2 LIKE ? " );
//                }
                //资产类别
                if(! StrUtil.isEmpty( dto.getContentCode() )){
                	String contentCode  = dto.getContentCode();
                	if (contentCode.indexOf("'") < 0) {
                		contentCode = "'"+dto.getContentCode()+"'";
                	}
                    sb.append( " AND EFA.SEGMENT2 IN ("+contentCode+") \n ");
                }
                
                if( !StrUtil.isEmpty( dto.getTagNumber() ) ){
                	sb.append(  " AND EFA.TAG_NUMBER LIKE ? " );
                }
                if( !StrUtil.isEmpty( dto.getMisTagNumber() ) ){
                	sb.append(  " AND EFA.MIS_TAG_NUMBER LIKE ? " );
                }
                
                
                if( !StrUtil.isEmpty( dto.getAssetsDescription() ) ){
                	sb.append(  " AND EFA.ASSETS_DESCRIPTION LIKE ? " );
                } 
                if( !StrUtil.isEmpty( dto.getAssignedToName() ) ){
                	sb.append(  " AND EFA.ASSIGNED_TO_NAME LIKE ? " );
                }
//                sb.append(  " AND EFA.FA_CATEGORY1 LIKE dbo.NVL(?, EFA.FA_CATEGORY1)" );
//                sb.append(  " AND ( " + SyBaseSQLUtil.isNull() +  "  OR EFA.FA_CATEGORY2 LIKE ?)" );
//                sb.append(  " AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)" );
//                sb.append(  " AND EFA.MIS_TAG_NUMBER LIKE dbo.NVL(?, EFA.MIS_TAG_NUMBER)" );
//                sb.append(  " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)" );
//                sb.append(  " AND ( " + SyBaseSQLUtil.isNull() +  "  OR EFA.ASSIGNED_TO_NAME LIKE dbo.NVL(?, EFA.ASSIGNED_TO_NAME))" );
//                        sb.append(  " AND EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
//                        sb.append(  " AND EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
//                        sb.append(  " AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
//                        sb.append(  " AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                
                if( !StrUtil.isEmpty( dto.getStartDate() ) ){
                	sb.append(  " AND EFA.DATE_PLACED_IN_SERVICE >= ? " );
                }
                if( !StrUtil.isEmpty( dto.getSQLEndDate() ) ){
                	sb.append(  " AND EFA.DATE_PLACED_IN_SERVICE <= ? " );
                }
                if( !StrUtil.isEmpty( dto.getStartCreationDate() ) ){
                	sb.append(  " AND EFA.ASSETS_CREATE_DATE >= ? " );
                }
                if( !StrUtil.isEmpty( dto.getSQLEndCreationDate() ) ){
                	sb.append(  " AND EFA.ASSETS_CREATE_DATE <= ? " );
                }
//                sb.append(  " AND ( ? = '' OR EFA.DATE_PLACED_IN_SERVICE >= ? )" );
//                sb.append(  " AND ( ? = '' OR EFA.DATE_PLACED_IN_SERVICE <= ? )" );
//                sb.append(  " AND ( ? = '' OR EFA.ASSETS_CREATE_DATE >= ? )" );
//                sb.append(  " AND ( ? = '' OR EFA.ASSETS_CREATE_DATE <= ?)" );
                //成本中心
                if(! StrUtil.isEmpty( dto.getCostCenterCode() )){
                	String contentCode  = dto.getCostCenterCode();
                	if (contentCode.indexOf("'") < 0) {
                		contentCode = "'"+dto.getCostCenterCode()+"'";
                	}
                	
                    sb.append( " AND SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) IN ("+contentCode+") \n ");
                } 
                
//                if( !StrUtil.isEmpty( dto.getCostCenterCode() ) ){
//                	sb.append(  " AND EFA.DEPRECIATION_ACCOUNT LIKE ? " );
//                }
                
//                sb.append(  " AND EFA.DEPRECIATION_ACCOUNT LIKE dbo.NVL(?, EFA.DEPRECIATION_ACCOUNT)" );
                
//                if( !StrUtil.isEmpty( dto.getProjectName() ) ){
//                	sb.append(  " AND EFA.PROJECT_NAME LIKE ? " );
//                }
                
                if(!dto.getProjectNumber().equals("")){
                    sb.append( " AND EPPA.SEGMENT1 IN("+dto.getProjectNumber()+") \n ");
                }
                
                //地点编号
                if(! StrUtil.isEmpty( dto.getAssetsLocationCode() )){
                	
                	String contentCode  = dto.getAssetsLocationCode();
                	
                	if( contentCode.indexOf( "%" ) > -1 ){	//模糊查询
                		sb.append( " AND EFA.ASSETS_LOCATION_CODE LIKE '"+contentCode+"' \n ");
                	}else{	//多地点精确查询
	                	if (contentCode.indexOf("'") < 0) {
	                		contentCode = "'"+dto.getAssetsLocationCode()+"'";
	                	}
	                    sb.append( " AND EFA.ASSETS_LOCATION_CODE IN ("+contentCode+") \n ");
                	}
                } 
                
//                if( !StrUtil.isEmpty( dto.getAssetsLocationCode() ) ){
//                	sb.append(  " AND EFA.ASSETS_LOCATION_CODE LIKE ? " );
//                }      
                
				if ("N".equals(dto.getIsOverageAssets())) {
					sb.append(  " AND DATEADD(MONTH,EFA.LIFE_IN_YEARS*12,EFA.DATE_PLACED_IN_SERVICE)>GETDATE()" );
                }else if("Y".equals(dto.getIsOverageAssets())) {
					sb.append(  " AND DATEADD(MONTH,EFA.LIFE_IN_YEARS*12,EFA.DATE_PLACED_IN_SERVICE)<=GETDATE()" );
                }
				
				if ("Y".equals(dto.getIsImportantAssets())) {
					sb.append(  " AND EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 IN ( SELECT CONTENT_CODE FROM AMS_CONTENT_DIC WHERE IMPORTANT_FLAG = 'Y')" );
				} else if ("N".equals(dto.getIsImportantAssets())) {
					sb.append(  " AND EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 IN ( SELECT CONTENT_CODE FROM AMS_CONTENT_DIC WHERE IMPORTANT_FLAG = 'N')" );
				}
//                sb.append(  " AND ( " + SyBaseSQLUtil.isNull() +  "  OR EFA.PROJECT_NAME LIKE dbo.NVL(?, EFA.PROJECT_NAME))" );
//                sb.append(  " AND EFA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFA.ASSETS_LOCATION_CODE)"  );
//                        sb.append(  " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)";
                List sqlArgs = new ArrayList();
                if( dto.getOrganizationId() > 0 ){
                	sqlArgs.add(dto.getOrganizationId());
                }
                
                if( !StrUtil.isEmpty( dto.getFaCategory1() ) ){
                	sqlArgs.add(dto.getFaCategory1());
                } 
                
//                if( !StrUtil.isEmpty( dto.getFaCategory2() ) ){
//                	sqlArgs.add(dto.getFaCategory2());
//                }
                
                if( !StrUtil.isEmpty( dto.getTagNumber() ) ){
                	sqlArgs.add(dto.getTagNumber());
                }
                if( !StrUtil.isEmpty( dto.getMisTagNumber() ) ){
                	sqlArgs.add(dto.getMisTagNumber());
                }
                
                
                if( !StrUtil.isEmpty( dto.getAssetsDescription() ) ){
                	sqlArgs.add(dto.getAssetsDescription());
                } 
                if( !StrUtil.isEmpty( dto.getAssignedToName() ) ){
                	sqlArgs.add(dto.getAssignedToName());
                }
                
//                sqlArgs.add(dto.getOrganizationId());
//                sqlArgs.add(dto.getFaCategory1());
//                sqlArgs.add(dto.getFaCategory2());
//                sqlArgs.add(dto.getFaCategory2());
//                sqlArgs.add(dto.getTagNumber());
//                sqlArgs.add(dto.getMisTagNumber());
//                sqlArgs.add(dto.getAssetsDescription());
//                sqlArgs.add(dto.getAssignedToName());
//                sqlArgs.add(dto.getAssignedToName());
                if( !StrUtil.isEmpty( dto.getStartDate() ) ){
                	sqlArgs.add(dto.getStartDate());
                }
                if( !StrUtil.isEmpty( dto.getSQLEndDate() ) ){
                	sqlArgs.add(dto.getSQLEndDate());
                }
                if( !StrUtil.isEmpty( dto.getStartCreationDate() ) ){
                	sqlArgs.add(dto.getStartCreationDate());
                }
                if( !StrUtil.isEmpty( dto.getSQLEndCreationDate() ) ){
                	sqlArgs.add(dto.getSQLEndCreationDate());
                }
//                sqlArgs.add(dto.getStartDate());
//                sqlArgs.add(dto.getStartDate());
//                sqlArgs.add(dto.getSQLEndDate());
//                sqlArgs.add(dto.getSQLEndDate());
//
//                sqlArgs.add(dto.getStartCreationDate());
//                sqlArgs.add(dto.getStartCreationDate());
//                sqlArgs.add(dto.getSQLEndCreationDate());
//                sqlArgs.add(dto.getSQLEndCreationDate());
                
//                if( !StrUtil.isEmpty( dto.getCostCenterCode() )){
//                	sqlArgs.add("%" + dto.getCostCenterCode() + "%");
//                } 
                
//                if( !StrUtil.isEmpty( dto.getProjectName() ) ){
//                	sqlArgs.add(dto.getProjectName());
//                }
                
//                if( !StrUtil.isEmpty( dto.getAssetsLocationCode() ) ){
//                	sqlArgs.add(dto.getAssetsLocationCode());
//                }
                
//                sqlArgs.add(dto.getProjectName());
//                sqlArgs.add(dto.getProjectName());
//                sqlArgs.add(dto.getAssetsLocationCode());
//                sqlArgs.add(dto.getModelNumber());
//                sqlArgs.add(dto.getModelNumber());
//                if ( ( userAccount.isProvinceUser() && userAccount.isSysAdmin() ) ||( userAccount.isProvinceUser() && userAccount.isComAssetsManager() ) || userAccount.isProvAssetsManager() ) {
            	if ( ( userAccount.isProvinceUser() && ( userAccount.isSysAdmin() ||  userAccount.isComAssetsManager() ) ) 
        					|| userAccount.isProvAssetsManager() ) {
                	if(! StrUtil.isEmpty( dto.getCompanyCode() )){
                    	String contentCode  = dto.getCompanyCode(); 
                        sb.append( " AND EFA.ORGANIZATION_ID IN ("+contentCode+") \n ");
                    }
                } else{
                	sb.append( " AND EOCM.ORGANIZATION_ID = ?" );
                	sb.append( " AND EFA.ORGANIZATION_ID = ? " );
                    sqlArgs.add(userAccount.getOrganizationId());
                    sqlArgs.add(userAccount.getOrganizationId());
//                	if( dto.getOrganizationId() > 0 ){
//                		sb.append(  " AND EFA.ORGANIZATION_ID = " + dto.getOrganizationId() );
//                	}
                } 
                
                if( !StrUtil.isEmpty( dto.getModelNumber() ) ){
                	sb.append( " AND EFA.MODEL_NUMBER LIKE ? \n "); 
                	sqlArgs.add(dto.getModelNumber());
                }
            
                if (dto.getDiscarded().equals("0")) {
                    sb.append( " AND (EFA.IS_RETIREMENTS = 0  OR EFA.IS_RETIREMENTS = 2)\n ");     //贾龙川20120203增加因非报废是0或2，不仅仅是0
                } else {
                    sb.append( " AND EFA.IS_RETIREMENTS = 1 \n ");
                }
                                 
                sqlModel.setSqlStr( sb.toString() );
                sqlModel.setArgs(sqlArgs);
//            }
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    private SQLModel getSXQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            String sqlStr = "SELECT"
                    + " EFA.TAG_NUMBER,"
                    + " EFA.MIS_TAG_NUMBER,"
                    + " EFA.ASSET_NUMBER,"
                    + " EFA.SEGMENT1,"
                    + " EFA.FA_CATEGORY1,"
                    + " EFA.SEGMENT2,"
                    + " EFA.FA_CATEGORY2,"
                    + " EFA.ASSETS_DESCRIPTION,"
                    + " EFA.MODEL_NUMBER,"
                    + " EFA.CURRENT_UNITS,"
                    + " EFA.UNIT_OF_MEASURE,"
                    + " EFA.ASSETS_LOCATION_CODE,"
                    + " EFA.ASSETS_LOCATION,"
                    + " EFA.ASSIGNED_TO_NAME,"
                    + " EFA.ASSIGNED_TO_NUMBER,"
                    + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                    + " EFA.PROJECT_NAME,"
                    + " EPPA.PROJECT_TYPE,"
                    + " EFA.LIFE_IN_YEARS,"
                    + " EFA.DATE_PLACED_IN_SERVICE,"
                    + " EFA.ASSETS_CREATE_DATE,"
                    + " EFA.ORIGINAL_COST,"
                    + " EFA.COST,"
                    + " EFA.DEPRN_RESERVE,"
                    + " EFA.DEPRN_COST,"
                    + " EFA.NET_ASSET_VALUE,"
                    + " EFA.SCRAP_VALUE,"
                    + " EFA.IMPAIR_RESERVE,"
                    + " EFA.COMPANY_CODE,"
                    + " EOCM.COMPANY,"
                    + " EFA.BOOK_TYPE_CODE,"
                    + " EOCM.BOOK_TYPE_NAME,"
                    + " EFA.DEPRECIATION_ACCOUNT,"
                    + " (SELECT "
                    + " AAV.ACCOUNT_NAME"
                    + " FROM"
                    + " AMS_ACCOUNT_V AAV"
                    + " WHERE"
                    + " AAV.ACCOUNT_CODE_1 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 1, 4)"
                    + " AND AAV.ACCOUNT_CODE_2 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6)"
                    + " AND AAV.ACCOUNT_CODE_3 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 13, 10)"
                    + " AND AAV.ACCOUNT_CODE_4 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 24, 6)"
                    + " AND AAV.ACCOUNT_CODE_5 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 31, 8)"
                    + " AND AAV.ACCOUNT_CODE_6 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 40, 4)"
                    + " AND AAV.ACCOUNT_CODE_7 = SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 45, 6))"
                    + " DEPRECIATION_ACCOUNT_NAME"
                    + " FROM"
                    + " ETS_FA_ASSETS       EFA,"
                    + " ETS_PA_PROJECTS_ALL EPPA,"
                    + " ETS_OU_CITY_MAP     EOCM"
                    + " WHERE  "
                    + " EFA.PROJECT_ID *= EPPA.PROJECT_ID"
                    + " AND EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                    + " AND EFA.IS_RETIREMENTS<>1"
                    + " AND EFA.BOOK_TYPE_CODE LIKE 'SXMC_FA_%'"
                    + " AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)"
                    + " AND EFA.FA_CATEGORY1 LIKE dbo.NVL(?, EFA.FA_CATEGORY1)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.FA_CATEGORY2 LIKE ?)"
                    + " AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)"
                    + " AND EFA.MIS_TAG_NUMBER LIKE dbo.NVL(?, EFA.MIS_TAG_NUMBER)"
                    + " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSIGNED_TO_NAME LIKE dbo.NVL(?, EFA.ASSIGNED_TO_NAME))"
//                    + " AND EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
//                    + " AND EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE)"
//                    + " AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
//                    + " AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)"
                    
                    + " AND ( ? = '' OR EFA.DATE_PLACED_IN_SERVICE >= ? )"
                    + " AND ( ? = '' OR EFA.DATE_PLACED_IN_SERVICE <= ? )"
                    + " AND ( ? = '' OR EFA.ASSETS_CREATE_DATE >= ? )"
                    + " AND ( ? = '' OR EFA.ASSETS_CREATE_DATE <= ? )"
                    
                    + " AND EFA.DEPRECIATION_ACCOUNT LIKE dbo.NVL(?, EFA.DEPRECIATION_ACCOUNT)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.PROJECT_NAME LIKE dbo.NVL(?, EFA.PROJECT_NAME))"
                    + " AND EFA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFA.ASSETS_LOCATION_CODE)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)";
            List sqlArgs = new ArrayList();
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getFaCategory1());
            sqlArgs.add(dto.getFaCategory2());
            sqlArgs.add(dto.getFaCategory2());
            sqlArgs.add(dto.getTagNumber());
            sqlArgs.add(dto.getMisTagNumber());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getAssignedToName());
            sqlArgs.add(dto.getAssignedToName());
            
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getSQLEndCreationDate());
            sqlArgs.add(dto.getSQLEndCreationDate());
            
            if( StrUtil.isEmpty( dto.getCostCenterCode() )){
            	sqlArgs.add(  dto.getCostCenterCode()  );
            }else{
            	sqlArgs.add("%" + dto.getCostCenterCode() + "%");
            }
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getAssetsLocationCode());
            sqlArgs.add(dto.getModelNumber());
            sqlArgs.add(dto.getModelNumber());
            if (!(userAccount.isProvinceUser() && userAccount.isSysAdmin())) {
                sqlStr += " AND EOCM.ORGANIZATION_ID = ? AND EOCM.IS_TD='N'";
                sqlArgs.add(userAccount.getOrganizationId());
            }
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;

    }
    private SQLModel getSXTDQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
            String sqlStr = "SELECT"
                    + " EFAT.TAG_NUMBER,"
                    + " EFAT.MIS_TAG_NUMBER,"
                    + " EFAT.ASSET_NUMBER,"
                    + " EFAT.SEGMENT1,"
                    + " EFAT.FA_CATEGORY1,"
                    + " EFAT.SEGMENT2,"
                    + " EFAT.FA_CATEGORY2,"
                    + " EFAT.ASSETS_DESCRIPTION,"
                    + " EFAT.MODEL_NUMBER,"
                    + " EFAT.CURRENT_UNITS,"
                    + " EFAT.UNIT_OF_MEASURE,"
                    + " EFAT.ASSETS_LOCATION_CODE,"
                    + " EFAT.ASSETS_LOCATION,"
                    + " EFAT.ASSIGNED_TO_NAME,"
                    + " EFAT.ASSIGNED_TO_NUMBER,"
                    + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                    + " EFAT.PROJECT_NAME,"
                    + " EPPA.PROJECT_TYPE,"
                    + " EFAT.LIFE_IN_YEARS,"
                    + " EFAT.DATE_PLACED_IN_SERVICE,"
                    + " EFAT.ASSETS_CREATE_DATE,"
                    + " EFAT.ORIGINAL_COST,"
                    + " EFAT.COST,"
                    + " EFAT.DEPRN_COST,"
                    + " EFAT.DEPRN_RESERVE,"
                    + " EFAT.NET_ASSET_VALUE,"
                    + " EFAT.SCRAP_VALUE,"
                    + " EFAT.IMPAIR_RESERVE,"
                    + " EFAT.COMPANY_CODE,"
                    + " EOCM.COMPANY,"
                    + " EFAT.BOOK_TYPE_CODE,"
                    + " EOCM.BOOK_TYPE_NAME,"
                    + " EFAT.DEPRECIATION_ACCOUNT,"
                    + " (SELECT "
                    + " AAV.ACCOUNT_NAME DEPRECIATION_ACCOUNT_NAME"
                    + " FROM"
                    + " AMS_ACCOUNT_TD_V AAV"
                    + " WHERE"
                    + " AAV.ACCOUNT_CODE_1 = SUBSTRING(EFAT.DEPRECIATION_ACCOUNT, 1, 4)"
                    + " AND AAV.ACCOUNT_CODE_2 = SUBSTRING(EFAT.DEPRECIATION_ACCOUNT, 6, 6)"
                    + " AND AAV.ACCOUNT_CODE_3 = SUBSTRING(EFAT.DEPRECIATION_ACCOUNT, 13, 10)"
                    + " AND AAV.ACCOUNT_CODE_4 = SUBSTRING(EFAT.DEPRECIATION_ACCOUNT, 24, 6)"
                    + " AND AAV.ACCOUNT_CODE_5 = SUBSTRING(EFAT.DEPRECIATION_ACCOUNT, 31, 8)"
                    + " AND AAV.ACCOUNT_CODE_6 = SUBSTRING(EFAT.DEPRECIATION_ACCOUNT, 40, 4)"
                    + " AND AAV.ACCOUNT_CODE_7 = SUBSTRING(EFAT.DEPRECIATION_ACCOUNT, 45, 6))"
                    + " DEPRECIATION_ACCOUNT_NAME"
                    + " FROM"
                    + " ETS_FA_ASSETS_TD       EFAT,"
                    + " ETS_PA_PROJECTS_ALL EPPA,"
                    + " ETS_OU_CITY_MAP     EOCM"
                    + " WHERE  "
                    + " EFAT.PROJECT_ID *= EPPA.PROJECT_ID"
                    + " AND EFAT.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                    + " AND EFAT.IS_RETIREMENTS<>1"
                    + " AND EFAT.BOOK_TYPE_CODE LIKE 'SXMC_FA_%'"
                    + " AND EFAT.ORGANIZATION_ID = ISNULL(?, EFAT.ORGANIZATION_ID)"
                    + " AND EFAT.FA_CATEGORY1 LIKE dbo.NVL(?, EFAT.FA_CATEGORY1)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAT.FA_CATEGORY2 LIKE ?)"
                    + " AND EFAT.TAG_NUMBER LIKE dbo.NVL(?, EFAT.TAG_NUMBER)"
                    + " AND EFAT.MIS_TAG_NUMBER LIKE dbo.NVL(?, EFAT.MIS_TAG_NUMBER)"
                    + " AND EFAT.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFAT.ASSETS_DESCRIPTION)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAT.ASSIGNED_TO_NAME LIKE dbo.NVL(?, EFAT.ASSIGNED_TO_NAME))"
//                    + " AND EFAT.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFAT.DATE_PLACED_IN_SERVICE)"
//                    + " AND EFAT.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFAT.DATE_PLACED_IN_SERVICE)"
//                    + " AND EFAT.ASSETS_CREATE_DATE >= ISNULL(?, EFAT.ASSETS_CREATE_DATE)"
//                    + " AND EFAT.ASSETS_CREATE_DATE <= ISNULL(?, EFAT.ASSETS_CREATE_DATE)"
                    
                    + " AND ( ? = '' OR EFAT.DATE_PLACED_IN_SERVICE >= ? )"
                    + " AND ( ? = '' OR EFAT.DATE_PLACED_IN_SERVICE <= ? )"
                    + " AND ( ? = '' OR EFAT.ASSETS_CREATE_DATE >= ? )"
                    + " AND ( ? = '' OR EFAT.ASSETS_CREATE_DATE <= ? )"
                    
                    + " AND EFAT.DEPRECIATION_ACCOUNT LIKE dbo.NVL(?, EFAT.DEPRECIATION_ACCOUNT)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAT.PROJECT_NAME LIKE dbo.NVL(?, EFAT.PROJECT_NAME))"
                    + " AND EFAT.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFAT.ASSETS_LOCATION_CODE)"
                    + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFAT.MODEL_NUMBER LIKE ?)";
            List sqlArgs = new ArrayList();
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getFaCategory1());
            sqlArgs.add(dto.getFaCategory2());
            sqlArgs.add(dto.getFaCategory2());
            sqlArgs.add(dto.getTagNumber());
            sqlArgs.add(dto.getMisTagNumber());
            sqlArgs.add(dto.getAssetsDescription());
            sqlArgs.add(dto.getAssignedToName());
            sqlArgs.add(dto.getAssignedToName());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getSQLEndDate());

            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getStartCreationDate());
            sqlArgs.add(dto.getSQLEndCreationDate());
            sqlArgs.add(dto.getSQLEndCreationDate());
            
            if( StrUtil.isEmpty( dto.getCostCenterCode() )){
            	sqlArgs.add(  dto.getCostCenterCode()  );
            }else{
            	sqlArgs.add("%" + dto.getCostCenterCode() + "%");
            }
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getProjectName());
            sqlArgs.add(dto.getAssetsLocationCode());
            sqlArgs.add(dto.getModelNumber());
            sqlArgs.add(dto.getModelNumber());
            if (!(userAccount.isProvinceUser() && userAccount.isSysAdmin())) {
                sqlStr += " AND EOCM.ORGANIZATION_ID = ? AND EOCM.IS_TD='Y'";
                sqlArgs.add(userAccount.getOrganizationId());
            }
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;

    }


}
