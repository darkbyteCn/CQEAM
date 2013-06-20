package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.bean.sql.AssetsLookupSQL;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ItemMaintainModel extends AMSSQLProducer {
    private String deptCodes = "";

    public ItemMaintainModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
        super(userAccount, dtoParameter);
        initDeptCodes();
    }

    private void initDeptCodes() {
        deptCodes = "(";
        DTOSet depts = userAccount.getPriviDeptCodes();
        if (depts != null && !depts.isEmpty()) {
            AmsMisDeptDTO dept = null;
            for (int i = 0; i < depts.getSize(); i++) {
                dept = (AmsMisDeptDTO) depts.getDTO(i);
                deptCodes += "'" + dept.getDeptCode() + "', ";
            }
        }
        deptCodes += "'')";
    }

    @SuppressWarnings({ "unchecked" })
	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String responsibilityUserName = dto.getResponsibilityUserName() ;
        List sqlArgs = new ArrayList();
        try {
        	
        	//TODO SJ MODIFY String to StringBuilder
        	StringBuilder sb = new StringBuilder();
        	sb.append( " \n " );
    		sb.append( " SELECT \n " );
            sb.append( " 	EII.BARCODE," );
            sb.append( " 	ESI.ITEM_CODE, \n " );
            sb.append( " 	ESI.ITEM_CATEGORY, \n " );
            sb.append( " 	dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME, \n " );
            sb.append( " 	ESI.ITEM_NAME, \n " );
            sb.append( " 	ESI.ITEM_SPEC, \n " );
            sb.append( " 	EII.UNIT_OF_MEASURE ITEM_UNIT, \n " );
            sb.append( " 	EII.ITEM_QTY, \n " );
            sb.append( " 	EII.ACTUAL_QTY, \n " );
            sb.append( " 	EII.FINANCE_PROP, \n " );
            sb.append( " 	dbo.APP_GET_FLEX_VALUE( EII.FINANCE_PROP, 'FINANCE_PROP' ) FINANCE_PROP_VALUE, \n " );
            sb.append( " 	EII.START_DATE, \n " );
            sb.append( " 	dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME, \n " );
            sb.append( " 	EO.WORKORDER_OBJECT_CODE, \n " );
            sb.append( " 	EO.WORKORDER_OBJECT_NAME, \n " );
            sb.append( " 	EO.WORKORDER_OBJECT_LOCATION, \n " );
            sb.append( "   	ACC1.COUNTY_NAME CITY,\n" );
    		sb.append( "   	ACC2.COUNTY_NAME COUNTY,\n" );
			sb.append( "   	dbo.APP_GET_FLEX_VALUE(EO.AREA_TYPE, 'ADDR_AREA_TYPE') AREA_TYPE_NAME,\n" );
            sb.append( " 	EII.RESPONSIBILITY_USER, \n " );
            sb.append( " 	EII.RESPONSIBILITY_DEPT, \n " );
            sb.append( " 	AME.USER_NAME RESPONSIBILITY_USER_NAME, \n " );
            sb.append( " 	AME.EMPLOYEE_NUMBER, \n " );
            sb.append( " 	AMD.DEPT_CODE, \n " );
            sb.append( " 	AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME, \n " );
            sb.append( " 	EII.MAINTAIN_USER, \n " );
            sb.append( " 	EII.MAINTAIN_DEPT, \n " );
            sb.append( " 	dbo.APP_GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT_NAME, \n " );
            sb.append( " 	AMD2.DEPT_NAME SPECIALITY_DEPT_NAME, \n " );
            sb.append( " 	EPPA.NAME PROJECT_NAME, \n " );
            sb.append( " 	EPPA.SEGMENT1 PROJECT_NUMBER, \n " );
            sb.append( " 	EII.ADDRESS_ID, \n " );
            sb.append( " 	EOCM.COMPANY_CODE, \n " );
            sb.append( " 	EOCM.COMPANY, \n " );
            sb.append( " 	AM.MANUFACTURER_NAME, \n " );
            sb.append( " 	AM.MANUFACTURER_CODE, \n " );
            sb.append( " 	ACD.CONTENT_CODE, \n " );
            sb.append( " 	ACD.CONTENT_NAME, \n " );
            sb.append( " 	dbo.APP_GET_FLEX_VALUE(EII.SHARE_STATUS, 'SHARE_STATUS') IS_SHARE, \n " );
            sb.append( " 	dbo.APP_GET_FLEX_VALUE(EII.CONSTRUCT_STATUS, 'CONSTRUCT_STATUS') CONSTRUCT_STATUS, \n " );
            sb.append( " 	AL.LOG_NET_ELE, \n " );
            sb.append( " 	AC.INVEST_CAT_NAME, \n " );
            sb.append( " 	AO.OPE_NAME, \n " );
            sb.append( " 	AN.LNE_NAME, \n " );
            sb.append( " 	EIM.ASSET_ID,\n" );
            sb.append( " 	EFA.LIFE_IN_YEARS, \n " );
            sb.append( " 	EFA.DATE_PLACED_IN_SERVICE, \n " );
            sb.append( " 	EFA.ASSETS_CREATE_DATE, \n " );
            sb.append( " 	EFA.ORIGINAL_COST, \n " );//资产原值
            sb.append( " 	EFA.SCRAP_VALUE, \n " );//资产残值
            sb.append( " 	EFA.IMPAIR_RESERVE, \n " );//资产减值准备累计
            sb.append( " 	EFA.DEPRN_RESERVE, \n " );//资产累计折旧
            sb.append( " 	EFA.NET_ASSET_VALUE, \n " );//资产净值
            sb.append( " 	EFA.DEPRN_COST, \n " );//资产净额
            sb.append( " 	EFA.SEGMENT2, \n " );
            sb.append( " 	EFA.FA_CATEGORY2, \n " );
            sb.append( " 	EII.REMARK, \n " );
            sb.append( " 	EII.REMARK1, \n " );
            sb.append( " 	EII.REMARK2 \n " );
            sb.append( " FROM" );
            sb.append( " 	AMS_LNE                     AL, \n " );
            sb.append( " 	AMS_CEX                     AC, \n " );
            sb.append( " 	AMS_OPE                     AO, \n " );
            sb.append( " 	AMS_NLE                     AN, \n " );
            sb.append( " 	ETS_ITEM_INFO               EII, \n " );
            sb.append( " 	ETS_SYSTEM_ITEM             ESI, \n " );
            sb.append( " 	AMS_OBJECT_ADDRESS          AOA, \n " );
            sb.append( " 	ETS_OBJECT                  EO, \n " );
            sb.append( " 	ETS_OU_CITY_MAP             EOCM, \n " );
            sb.append( " 	AMS_MIS_DEPT                AMD, \n " );
            sb.append( " 	AMS_MIS_EMPLOYEE            AME, \n " );
            sb.append( " 	ETS_PA_PROJECTS_ALL         EPPA, \n " );
            sb.append( " 	AMS_MANUFACTURER            AM, \n " );
            sb.append( " 	AMS_COUNTY                  ACC1, \n " );
            sb.append( " 	AMS_COUNTY                  ACC2, \n " );
            sb.append( " 	AMS_MIS_DEPT                AMD2, \n " );
            sb.append( " 	AMS_MIS_DEPT                AMD3, \n " );
            sb.append( " 	AMS_CONTENT_DIC             ACD, \n " );;
            if (userAccount.getIsTd().equals("Y")) {
        		sb.append( " 	ETS_ITEM_MATCH_TD EIM, \n " );
                sb.append( " 	ETS_FA_ASSETS_TD EFA \n  " );
            } else {
                sb.append( " 	ETS_ITEM_MATCH EIM, \n " );
                sb.append( " 	ETS_FA_ASSETS EFA \n " );
            }
            sb.append( " WHERE");
            sb.append( " 	EII.ITEM_CODE = ESI.ITEM_CODE \n ");
            sb.append( " 	AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n ");
            sb.append( " 	AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n ");
            sb.append( " 	AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n ");
            if( StrUtil.isEmpty( responsibilityUserName ) ){
            	sb.append( " 	AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n ");
            }else{
            	sb.append( " 	AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID \n ");
            }
            
            if( StrUtil.isEmpty( dto.getProjectNumber() ) && StrUtil.isEmpty( dto.getProjectName() ) ){
            	sb.append( " 	AND EII.PROJECTID *= EPPA.PROJECT_ID \n ");
            }else{
            	sb.append( " 	AND EII.PROJECTID = EPPA.PROJECT_ID \n ");
            } 
            
            sb.append( " 	AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n ");

            if( StrUtil.isEmpty( dto.getManufacturerName() ) ){
            	sb.append( " 	AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID \n ");
            }else{
            	sb.append( " 	AND EII.MANUFACTURER_ID = AM.MANUFACTURER_ID \n ");
            }  
            
            if( StrUtil.isEmpty( dto.getLogNetEle() ) ){
            	sb.append( " 	AND EII.LNE_ID *= AL.AMS_LNE_ID \n ");
            }else{
            	sb.append( " 	AND EII.LNE_ID = AL.AMS_LNE_ID \n ");
            } 
            if( StrUtil.isEmpty( dto.getInvestCatName() ) ){
            	sb.append( " 	AND EII.CEX_ID *= AC.AMS_CEX_ID \n ");
            }else{
            	sb.append( " 	AND EII.CEX_ID = AC.AMS_CEX_ID \n ");
            }
            if( StrUtil.isEmpty( dto.getOpeName() ) ){
            	sb.append( " 	AND EII.OPE_ID *= AO.AMS_OPE_ID \n ");
            }else{
            	sb.append( " 	AND EII.OPE_ID = AO.AMS_OPE_ID \n ");
            }
            if( StrUtil.isEmpty( dto.getLneName() ) ){
            	sb.append( " 	AND EII.NLE_ID *= AN.AMS_LNE_ID \n ");
            }else{
            	sb.append( " 	AND EII.NLE_ID = AN.AMS_LNE_ID \n ");
            }
            
            sb.append( " 	AND EII.SPECIALITY_DEPT *= AMD2.DEPT_CODE \n ");
            sb.append( " 	AND EII.MAINTAIN_DEPT *= AMD3.DEPT_CODE \n "); 
            
            if( !StrUtil.isEmpty( dto.getCreationDate() ) 
            		|| !StrUtil.isEmpty( dto.getSQLLastUpdateDate() )
            		|| !StrUtil.isEmpty( dto.getStartDate() )
            		|| !StrUtil.isEmpty( dto.getSQLEndDate() ) ){
            	sb.append( " 	AND EII.SYSTEMID = EIM.SYSTEMID \n ");
	                    sb.append( " 	AND EIM.ASSET_ID = EFA.ASSET_ID \n ");
        	}else{
        		sb.append( " 	AND EII.SYSTEMID *= EIM.SYSTEMID \n ");
                    sb.append( " 	AND EIM.ASSET_ID *= EFA.ASSET_ID \n ");
        	}
            
            sb.append( " 	AND EO.CITY *= ACC1.COUNTY_CODE \n  ");
    		sb.append( " 	AND EO.COUNTY *= ACC2.COUNTY_CODE \n  ");
    		
    		if( StrUtil.isEmpty( dto.getContentName() ) ){
    			sb.append( " 	AND EII.CONTENT_CODE *= ACD.CONTENT_CODE \n ");
    		}else{
    			sb.append( " 	AND EII.CONTENT_CODE = ACD.CONTENT_CODE \n ");
    		}
			
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR ESI.ITEM_CATEGORY = ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR ESI.ITEM_NAME LIKE ?) \n ");
            sb.append( " 	AND (( " + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_CODE LIKE ?) \n ");
            sb.append( " 	OR ( " + SyBaseSQLUtil.isNull() + " OR EO.WORKORDER_OBJECT_NAME LIKE ?)) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR AME.USER_NAME LIKE ?) \n ");


            if(!dto.getProjectNumber().equals("")){
                sb.append( " 	AND EPPA.SEGMENT1 IN("+dto.getProjectNumber()+") \n ");
            }
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.RESPONSIBILITY_DEPT = ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.SPECIALITY_DEPT = ?) \n ");
            
            if(!dto.getContentCode().equals("")){
            	String contentCode  = dto.getContentCode();
            	if (contentCode.indexOf("'") < 0) {
            		contentCode = "'"+dto.getContentCode()+"'";
            	}
                sb.append( " 	AND EII.CONTENT_CODE IN ("+contentCode+") \n ");
            }
            
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.SHARE_STATUS = ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.CONSTRUCT_STATUS = ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR AM.MANUFACTURER_NAME LIKE ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.ITEM_STATUS = ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.MAINTAIN_USER LIKE ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.MAINTAIN_DEPT = ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EII.FINANCE_PROP = ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR ESI.ITEM_SPEC LIKE ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR AL.LOG_NET_ELE LIKE ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR AC.INVEST_CAT_NAME LIKE ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR AO.OPE_NAME LIKE ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR AN.LNE_NAME LIKE ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EFA.ASSETS_CREATE_DATE >= ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EFA.ASSETS_CREATE_DATE <= ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EFA.DATE_PLACED_IN_SERVICE >= ?) \n ");
            sb.append( " 	AND ( " + SyBaseSQLUtil.isNull() + " OR EFA.DATE_PLACED_IN_SERVICE <= ?) \n ");
            sb.append( " 	AND EII.ORGANIZATION_ID = ? \n ");
            sb.append( " 	AND (EII.FINANCE_PROP = 'ASSETS' OR EII.FINANCE_PROP = 'TD_ASSETS' OR EII.FINANCE_PROP = 'TT_ASSETS' OR EII.FINANCE_PROP = 'PRJ_MTL' OR EII.FINANCE_PROP = 'OTHERS' OR EII.FINANCE_PROP = 'UNKNOW' ) \n ");
            sb.append( " 	AND NOT EXISTS( \n ");
            sb.append( " 	SELECT \n ");
            sb.append( " 	NULL \n ");
            sb.append( " 	FROM \n ");
            sb.append( " 	AMS_ASSETS_RESERVED ARR \n ");
            sb.append( " 	WHERE \n ");
            sb.append( " 	EII.BARCODE = ARR.BARCODE ) \n ");
            sb.append( " 	AND NOT EXISTS (SELECT 1 FROM ETS_FA_ASSETS EFA WHERE EII.BARCODE = EFA.TAG_NUMBER AND EFA.BOOK_TYPE_CODE LIKE '%IA%' ) \n ");
            sb.append( " 	AND NOT EXISTS( \n ");
            sb.append( " 	SELECT \n ");
            sb.append( " 	NULL \n ");
            sb.append( " 	FROM \n ");
            sb.append( " 	AMS_ASSETS_CHECK_LINE   AACL, \n ");
            sb.append( " 	AMS_ASSETS_CHECK_HEADER AACH \n ");
            sb.append( " 	WHERE \n ");
            sb.append( " 	EII.BARCODE = AACL.BARCODE \n ");
            sb.append( " 	AND AACL.HEADER_ID = AACH.HEADER_ID \n ");
            sb.append( " 	AND AACH.ORDER_STATUS IN ('APPROVED','DISTRIBUTED','DOWNLOADED','IN_PROCESS','REJECTED','SAVE_TEMP','UPLOADED')) \n ");
            sqlArgs.add(dto.getItemCategory());
            sqlArgs.add(dto.getItemCategory());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getItemName());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectCode());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getWorkorderObjectName());
            sqlArgs.add(dto.getResponsibilityUserName());
            sqlArgs.add(dto.getResponsibilityUserName());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getResponsibilityDept());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getSpecialityDept());
            sqlArgs.add(dto.getShare());
            sqlArgs.add(dto.getShare());
            sqlArgs.add(dto.getConstructStatus());
            sqlArgs.add(dto.getConstructStatus());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getItemStatus());
            sqlArgs.add(dto.getMaintainUser());
            sqlArgs.add(dto.getMaintainUser());
            sqlArgs.add(dto.getMaintainDept());
            sqlArgs.add(dto.getMaintainDept());
            sqlArgs.add(dto.getFinanceProp());
            sqlArgs.add(dto.getFinanceProp());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getItemSpec());
            sqlArgs.add(dto.getLogNetEle());
            sqlArgs.add(dto.getLogNetEle());
            sqlArgs.add(dto.getInvestCatName());
            sqlArgs.add(dto.getInvestCatName());
            sqlArgs.add(dto.getOpeName());
            sqlArgs.add(dto.getOpeName());
            sqlArgs.add(dto.getLneName());
            sqlArgs.add(dto.getLneName());
            sqlArgs.add(dto.getCreationDate());
            sqlArgs.add(dto.getCreationDate());
            sqlArgs.add(dto.getSQLLastUpdateDate());
            sqlArgs.add(dto.getSQLLastUpdateDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getStartDate());
            sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(dto.getSQLEndDate());
            sqlArgs.add(userAccount.getOrganizationId());
            
            if ( !StrUtil.isEmpty(dto.getFromBarcode()) ){
            	sb.append( " AND EII.BARCODE >= ? \n");
            	sqlArgs.add( StrUtil.nullToString( dto.getFromBarcode() ));
            }
            	
            if ( !StrUtil.isEmpty(dto.getToBarcode()) ){
            	sb.append( " AND EII.BARCODE <= ? \n");
            	sqlArgs.add( StrUtil.nullToString( dto.getToBarcode() ));
            }
            
//            sb.append( " AND (? = '' OR EII.BARCODE >= ?) \n");
//            sb.append( " AND (? = '' OR EII.BARCODE <= ?) \n");
//			sqlArgs.add( StrUtil.nullToString( dto.getFromBarcode() ));
//			sqlArgs.add( StrUtil.nullToString( dto.getFromBarcode() ) );
//			sqlArgs.add( StrUtil.nullToString( dto.getToBarcode() ) );
//			sqlArgs.add( StrUtil.nullToString( dto.getToBarcode() ) );
// if ((!StrUtil.isEmpty(dto.getFromBarcode())) && (!StrUtil.isEmpty(dto.getToBarcode()))) {
//            	  sb.append( " AND EII.BARCODE >= ? \n ");
//                sb.append( " AND EII.BARCODE <= ? \n ");
//                sqlArgs.add(dto.getFromBarcode());
//                sqlArgs.add(dto.getToBarcode());
//            } else {
//                sb.append( " AND ( " + SyBaseSQLUtil.isNull() + " OR EII.BARCODE LIKE ?) \n ");
//                sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
//                sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
//            }
            
            
            if (dto.getAttribute1().equals(AssetsDictConstant.STATUS_NO)) {
                sb.append( "  AND (EII.ATTRIBUTE1 = '' OR EII.ATTRIBUTE1 IS NULL) \n ");;
            } else {
                sb.append( "  AND EII.ATTRIBUTE1 = ? \n ");
                sqlArgs.add(dto.getAttribute1());
            }
            if (!userAccount.isComAssetsManager()) {
                if(userAccount.isDptAssetsManager()){
                    sb.append( "  AND EII.RESPONSIBILITY_DEPT IN " + deptCodes );
                } else {
                    sb.append(" AND EII.RESPONSIBILITY_USER = ?");
                    sqlArgs.add(userAccount.getEmployeeId());
                }
            }
            
            sb.append( AssetsLookupSQL.getItemMaintainPlan() );
            
            sqlModel.setSqlStr( sb.toString() );
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

    @SuppressWarnings("unchecked")
	public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "UPDATE"
                + " ETS_ITEM_INFO"
                + " SET"
                + " ITEM_CODE           = dbo.NVL(?, ITEM_CODE),"
                + " ADDRESS_ID          = dbo.NVL(?, ADDRESS_ID),"
                + " RESPONSIBILITY_USER = dbo.NVL(?, RESPONSIBILITY_USER),"
                + " RESPONSIBILITY_DEPT = dbo.NVL(?, RESPONSIBILITY_DEPT),"
                + " MAINTAIN_USER       = dbo.NVL(?, MAINTAIN_USER),"
                + " MAINTAIN_DEPT       = dbo.NVL(?, MAINTAIN_DEPT),"
                + " REMARK              = dbo.NVL(?, REMARK),"
                + " MANUFACTURER_ID     = dbo.NVL(?, MANUFACTURER_ID),"
                + " SHARE_STATUS            = dbo.NVL(?, SHARE_STATUS),"
                + " CONSTRUCT_STATUS    = dbo.NVL(?, CONSTRUCT_STATUS),"
                + " CONTENT_CODE        = dbo.NVL(?, CONTENT_CODE),"
                + " CONTENT_NAME        = dbo.NVL(?, CONTENT_NAME),"
                + " ITEM_STATUS         = dbo.NVL(?, ITEM_STATUS),"
                + " LNE_ID              = dbo.NVL(?, LNE_ID),"
                + " CEX_ID              = dbo.NVL(?, CEX_ID),"
                + " OPE_ID              = dbo.NVL(?, OPE_ID),"
                + " NLE_ID              = dbo.NVL(?, NLE_ID),"
                + " SPECIALITY_DEPT     = dbo.NVL(?, SPECIALITY_DEPT),"
                + " REMARK1             = dbo.NVL(?, REMARK1),"
                + " REMARK2             = dbo.NVL(?, REMARK2),"
                + " ACTUAL_QTY          = (CASE ? WHEN -1 THEN ACTUAL_QTY ELSE ? END),"
                + " UNIT_OF_MEASURE     = dbo.NVL(?, UNIT_OF_MEASURE),"
                + " LAST_UPDATE_DATE    = GETDATE(),"
                + " LAST_UPDATE_BY      = ?"
                + " WHERE"
                + " BARCODE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getItemCode());
        sqlArgs.add(dto.getAddressId());
        sqlArgs.add(dto.getResponsibilityUser());
        sqlArgs.add(dto.getResponsibilityDept());
        sqlArgs.add(dto.getMaintainUser());
        sqlArgs.add(dto.getMaintainDept());
        sqlArgs.add(dto.getRemark());
        sqlArgs.add(dto.getManufacturerId());
        sqlArgs.add(dto.getShare());
        sqlArgs.add(dto.getConstructStatus());
        sqlArgs.add(dto.getContentCode());
        sqlArgs.add(dto.getContentName());
        sqlArgs.add(dto.getItemStatus());
        sqlArgs.add(dto.getLneId());
        sqlArgs.add(dto.getCexId());
        sqlArgs.add(dto.getOpeId());
        sqlArgs.add(dto.getNleId());
        sqlArgs.add(dto.getSpecialityDept());
        sqlArgs.add(dto.getRemark1());
        sqlArgs.add(dto.getRemark2());
        sqlArgs.add(dto.getActualQty());
        sqlArgs.add(dto.getActualQty());
        sqlArgs.add(dto.getItemUnit());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(dto.getBarcode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    @SuppressWarnings("unchecked")
	public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "SELECT"
                + " EII.BARCODE,"
                + " ESI.ITEM_CODE,"
                + " ESI.ITEM_CATEGORY,"
                + " dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_SPEC,"
                + " ESI.YEARS,"
                + " ESI.ITEM_UNIT,"
                + " EII.START_DATE,"
                + " EO.WORKORDER_OBJECT_CODE,"
                + " EO.WORKORDER_OBJECT_NAME,"
                + " EO.WORKORDER_OBJECT_LOCATION,"
                + " EC.COUNTY_CODE,"
                + " EC.COUNTY_NAME,"
                + " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
                + " AME.EMPLOYEE_NUMBER,"
                + " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
                + " EPPA.NAME PROJECT_NAME,"
                + " EPPA.SEGMENT1 PROJECT_NUMBER,"
                + " EMPV.VENDOR_NAME,"
                + " EMPV.SEGMENT1 VENDOR_NUMBER,"
                + " EII.FINANCE_PROP,"
                + " dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,"
                + " EII.RESPONSIBILITY_USER,"
                + " EII.RESPONSIBILITY_DEPT,"
                + " EII.MAINTAIN_USER,"
                + " EII.MAINTAIN_DEPT,"
                + " EII.ADDRESS_ID,"
                + " EOCM.COMPANY_CODE,"
                + " EOCM.COMPANY"
                + " FROM"
                + " ETS_ITEM_INFO          EII,"
                + " ETS_SYSTEM_ITEM        ESI,"
                + " AMS_OBJECT_ADDRESS     AOA,"
                + " ETS_OBJECT             EO,"
                + " ETS_COUNTY             EC,"
                + " ETS_OU_CITY_MAP        EOCM,"
                + " AMS_MIS_DEPT           AMD,"
                + " AMS_MIS_EMPLOYEE       AME,"
                + " ETS_PA_PROJECTS_ALL    EPPA,"
                + " ETS_MIS_PO_VENDORS     EMPV"
                + " WHERE"
                + " EII.ITEM_CODE = ESI.ITEM_CODE"
                + " AND ESI.VENDOR_ID *= EMPV.VENDOR_ID"
                + " AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                + " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
                + " AND EO.COUNTY_CODE *= EC.COUNTY_CODE"
                + " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
                + " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
                + " AND EII.PROJECTID *= EPPA.PROJECT_ID"
                + " AND EII.BARCODE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(dto.getBarcode());
        if (!userAccount.isComAssetsManager()) {
            sqlStr += " AND EII.RESPONSIBILITY_DEPT IN " + deptCodes;
        } else {
            sqlStr += " AND EII.ORGANIZATION_ID = ? ";
            sqlArgs.add(userAccount.getOrganizationId());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    @SuppressWarnings("unchecked")
	public SQLModel getCheckItemStatusModel(String barcode, String itemStatus) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        String sqlStr = "SELECT EII.ITEM_STATUS\n" +
                "  FROM ETS_ITEM_INFO EII\n" +
                " WHERE EII.BARCODE = ?\n" +
                "   AND EII.ITEM_STATUS = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(barcode);
        sqlArgs.add(itemStatus);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
