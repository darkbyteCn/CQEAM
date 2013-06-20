package com.sino.ams.workorder.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * 
 * @系统名称: 
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 8, 2011
 */
public class TrunListPrintQueryModel extends BaseSQLProducer {
    EtsWorkorderDTO workorderDTO = null;
    SfUserDTO sfUser = null;

    public TrunListPrintQueryModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.workorderDTO = (EtsWorkorderDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
    }

    /**
     * Function:		获取转资清单查询的SQLModel
     * @return			SQLModel
     * @date			2009-6-4
     * @time			9:31:55
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
    	boolean hasQueryWord = false;
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
			 
		StringBuilder sb = new StringBuilder();
		sb.append( " SELECT EO.WORKORDER_OBJECT_CODE ASSETS_LOCATION_CODE, \n");
        sb.append( "        EO.WORKORDER_OBJECT_NAME ASSETS_LOCATION, \n");
        sb.append( "        EPPA.SEGMENT1, \n");
        sb.append( "        EPPA.NAME, \n");
        sb.append( "        dbo.APP_GET_ORGNIZATION_NAME(EII.ORGANIZATION_ID) ORG_NAME, \n");
        sb.append( "        EII.BARCODE, \n");
        sb.append( "        ESI.ITEM_NAME, \n");
        sb.append( "        ESI.ITEM_SPEC, \n");
        sb.append( "        '1' ITEM_QTY, \n");
        sb.append( "          dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_DESC, \n");
//        sb.append( "        dbo.AMP_GET_DEPT_NAME( EII.RESPONSIBILITY_DEPT ) DEPT_NAME, \n");
        sb.append( "        AMD.DEPT_NAME, \n");
        sb.append( "        AMP.USER_NAME,   \n");
        sb.append( "        EII.MAINTAIN_DEPT, \n");
        sb.append( "        EII.MAINTAIN_USER, \n");
        sb.append( "        EII.CREATION_DATE, \n");
        sb.append( "        EII.START_DATE, \n");
        sb.append( "        dbo.GET_PRINT_HISTORY_COUNT( EII.ORGANIZATION_ID , EII.BARCODE ) PRINT_NUM , \n"); //打印次数
        sb.append( "        EII.LAST_UPDATE_DATE \n");
        sb.append( "   FROM ETS_ITEM_INFO       EII, \n");
        sb.append( "        ETS_OBJECT             EO, \n");
        sb.append( "        AMS_OBJECT_ADDRESS     AOA, \n");
        sb.append( "        ETS_SYSTEM_ITEM     ESI,    \n");
        sb.append( "        ETS_PA_PROJECTS_ALL EPPA, \n");
        sb.append( "        AMS_MIS_DEPT        AMD, \n"); 
        sb.append( "        AMS_MIS_EMPLOYEE    AMP \n");
        sb.append( "  WHERE EII.ITEM_CODE = ESI.ITEM_CODE \n");
        
        //sb.append( "   AND         EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n");
        
        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
        	sb.append( "   AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE \n");
        }else{
        	sb.append( "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n");
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
        	sb.append( "   AND         EII.RESPONSIBILITY_USER = AMP.EMPLOYEE_ID \n");
        }else{
        	sb.append( "   AND         EII.RESPONSIBILITY_USER *= AMP.EMPLOYEE_ID \n");
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getPrjId() ) ){
        	sb.append( "   AND         EII.PROJECTID = EPPA.PROJECT_ID \n");
        }else{
        	sb.append( "   AND         EII.PROJECTID *= EPPA.PROJECT_ID \n");
        }
        
        if(workorderDTO.getOpinionType().equals("part")){
    		List partBar=workorderDTO.getPartBarcode();
    		
    		if( null != partBar && !partBar.isEmpty() ){
	    		sb.append("AND (");
	    		boolean boolType=true;
	    		
	    		for(int i=0;i<partBar.size();i++){
	    			if(boolType){
	    				sb.append(" EII.BARCODE = ? \n");
	        			if(partBar.get(i)!="" && !partBar.get(i).equals(null)){
	        				sqlArgs.add(partBar.get(i).toString());
	        			}
	        			boolType=false;
	        			continue;
	    			}
	    			sb.append("OR EII.BARCODE = ? \n");
	    			if(partBar.get(i)!="" && !partBar.get(i).equals(null)){
	    				sqlArgs.add(partBar.get(i).toString());
	    			}
	    			
	            }
	    		
	    		sb.append(")");
    		}
    	}
        
        sb.append( "   AND         EII.ADDRESS_ID = AOA.ADDRESS_ID \n");
        sb.append( "   AND         AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n");
        
        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
        	sb.append( "   AND  AMD.DEPT_CODE LIKE ? \n");
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
        	sb.append( "   AND  AMP.USER_NAME LIKE ? \n");
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getOrganizationId() ) ){
        	sb.append( "   AND  EII.ORGANIZATION_ID = ? \n");
        	sb.append( "   AND EO.ORGANIZATION_ID = ? \n");
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getPrjId() ) ){
        	sb.append( "   AND  EPPA.PROJECT_ID = ? \n");
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getWorkorderObjectCode() ) ){
        	sb.append( "   AND  EO.WORKORDER_OBJECT_CODE LIKE ? \n");
        }
                
//                if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
//                	sb.append( "   AND  AMD.DEPT_CODE = ? \n");
//                }
//                sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() + "  OR AMP.USER_NAME LIKE ?) \n");
//                sb.append( "   AND         (  ?=NULL OR EII.ORGANIZATION_ID = ?) \n");
//                sb.append( "   AND         (  ?=NULL  OR EPPA.PROJECT_ID = ?) \n");
//                sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_CODE LIKE ?) \n");
//                sb.append( "   AND         ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?) \n");
       
        if( !StrUtil.isEmpty( workorderDTO.getDeptCode() ) ){
        	hasQueryWord = true ;
        	sqlArgs.add(workorderDTO.getDeptCode());
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getResponsibilityUser() ) ){
        	sqlArgs.add(workorderDTO.getResponsibilityUser());
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getOrganizationId() ) && workorderDTO.getOrganizationId() > 0 ){
        	sqlArgs.add(workorderDTO.getOrganizationId());
        	sqlArgs.add(workorderDTO.getOrganizationId());
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getPrjId() ) ){
        	sqlArgs.add(workorderDTO.getPrjId());
        }
        
        if( !StrUtil.isEmpty( workorderDTO.getWorkorderObjectCode() ) ){
        	hasQueryWord = true ;
        	sqlArgs.add(workorderDTO.getWorkorderObjectCode());
        }
                
//		sqlArgs.add(workorderDTO.getResponsibilityUser());
//		sqlArgs.add(workorderDTO.getResponsibilityUser());
//		
//		sqlArgs.add(workorderDTO.getOrganizationId());
//		sqlArgs.add(workorderDTO.getOrganizationId());
//		
//		sqlArgs.add(workorderDTO.getPrjId());
//		sqlArgs.add(workorderDTO.getPrjId());
//		
//		sqlArgs.add(workorderDTO.getWorkorderObjectCode());
//		sqlArgs.add(workorderDTO.getWorkorderObjectCode());
		
//		sqlArgs.add(workorderDTO.getDeptCode());
//		sqlArgs.add(workorderDTO.getDeptCode());
		
        if( workorderDTO.getFinanceProp().equals( "ASSETS" )){
        	sb.append( "   AND         EII.FINANCE_PROP = ? \n"); //'PRJ_MTL'
        	sqlArgs.add(workorderDTO.getFinanceProp());
        }else{
        	sb.append( "   AND         EII.FINANCE_PROP = ? \n"); //'PRJ_MTL'
        	sb.append( "   AND         EII.ITEM_STATUS = ? \n"); //'PRJ_MTL'
        	sqlArgs.add( "PRJ_MTL" );
        	sqlArgs.add(workorderDTO.getFinanceProp());
        }
        
        sb.append( " ORDER BY EII.LAST_UPDATE_DATE ");
//        sb.append( " ORDER BY EII.LAST_UPDATE_DATE, EII.ADDRESS_ID, EPPA.SEGMENT1 ");
        
        if( !hasQueryWord ){
        	sb.append( queryPlan() );
        }
					
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		
        return sqlModel;
    }
    
    
    /**
     * sj add
     * @return
     */
    private StringBuilder queryPlan(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(" plan ");
    	sb.append(" '( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( nl_join ( sort ( i_scan ETS_ITEM_INFO_110708_N7 ( table ( EII ETS_ITEM_INFO ) ) ) ) ( i_scan AMS_OBJECT_ADDRESS_PK ( table ( AOA AMS_OBJECT_ADDRESS ) ) ) ) ( i_scan ETS_OBJECT_I1 ( table ( EO ETS_OBJECT ");
    	sb.append(" ) ) ) ) ( i_scan ETS_PA_PROJECTS_ALL_PK ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ) ) ( i_scan ETS_SYSTEM_ITEM_PK ( table ( ESI ETS_SYSTEM_ITEM ) ) ) ) ( i_scan AMS_MIS_EMPLOYEE_PK ( table ( AMP AMS_MIS_EMPLOYEE ) ) ) ) ( i_scan AMS_MIS_DEPT_PK ( table ( AMD ");
    	sb.append("  AMS_MIS_DEPT ) ) ) ) ( prop ( table ( EII ETS_ITEM_INFO ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AOA AMS_OBJECT_ADDRESS ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( EO ETS_OBJECT ) ) ( parallel 1 ) ( prefetch 16 ) ( ");
    	sb.append(" lru ) ) ( prop ( table ( EPPA ETS_PA_PROJECTS_ALL ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( ESI ETS_SYSTEM_ITEM ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ( prop ( table ( AMP AMS_MIS_EMPLOYEE ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ");
    	sb.append("  ) ) ( prop ( table ( AMD AMS_MIS_DEPT ) ) ( parallel 1 ) ( prefetch 16 ) ( lru ) ) ' ");
    	return sb;
    }
}
