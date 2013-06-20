package com.sino.ams.newasset.lease.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.lease.constant.LeaseAppConstant;
import com.sino.ams.newasset.lease.dto.LeaseHeaderDTO;
import com.sino.ams.newasset.lease.dto.LeaseLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * 
 * @系统名称: 续租
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class LeaseModel extends AMSSQLProducer{
	private SfUserDTO user = null;
	LeaseHeaderDTO headerDTO = null;
	
	public LeaseModel(BaseUserDTO userAccount, DTO dtoParameter) { 
		super(userAccount, dtoParameter);
		this.headerDTO = (LeaseHeaderDTO) dtoParameter;
		this.user = (SfUserDTO) userAccount;
	}
	
	@SuppressWarnings("unchecked")
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		LeaseHeaderDTO dto = (LeaseHeaderDTO) dtoParameter;
		StringBuilder sb = new StringBuilder();
		
		sb.append( " SELECT  \n" );
		sb.append( " AATH.TRANS_ID, \n" );
		sb.append( " AATH.TRANS_NO, \n" );    //单据号
		sb.append( " AATH.FROM_DEPT, \n" );    
		sb.append( " AATH.TO_DEPT, \n" );
		sb.append( " AATH.TRANS_TYPE, \n" );  //单据类型
		sb.append( " AATH.TRANSFER_TYPE, \n" ); 
		sb.append( " AATH.TRANS_STATUS, \n" );
		sb.append( " AATH.TRANS_DATE, \n" );
		sb.append( " AATH.CREATION_DATE, \n" );    //创建日期
		sb.append( " AATH.CREATED_BY, \n" );       //经办人
		sb.append( " AATH.LAST_UPDATE_DATE, \n" ); 
		sb.append( " AATH.LAST_UPDATE_BY, \n" );
		sb.append( " AATH.CANCELED_DATE, \n" );
		sb.append( " AATH.CANCELED_REASON, \n" );
		sb.append( " AATH.CREATED_REASON, \n" ); //备注
		sb.append( " AATH.APPROVED_DATE, \n" );
		sb.append( " AATH.FROM_ORGANIZATION_ID, \n" ); //公司名称
		sb.append( " EOCM.COMPANY FROM_COMPANY_NAME, \n" ); //公司名称
		sb.append( " AATH.FROM_GROUP, \n" );
		sb.append( " AATH.FA_CONTENT_CODE, \n" );
		sb.append( " AATH.LOSSES_NAME, \n" );
		sb.append( " AATH.LOSSES_DATE, \n" );
		sb.append( " AATH.IS_THRED, \n" );
		sb.append( " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC, \n" );
		sb.append( " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE, \n" ); 		
		
		sb.append( " SU.USERNAME CREATED, \n" ); 
		sb.append( " SU.EMAIL, \n" ); //电子邮件
		sb.append( " SU.MOBILE_PHONE PHONE_NUMBER,AATH.EMERGENT_LEVEL  \n" );		 //手机号码
		sb.append( " FROM  \n" );
		sb.append( " AMS_ASSETS_TRANS_HEADER AATH, \n" );
		sb.append( " ETS_OU_CITY_MAP         EOCM, \n" );
		sb.append( " SF_USER                 SU  \n" );
		sb.append( " WHERE  \n" );
		sb.append( " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n" );
		sb.append( " AND AATH.TRANS_TYPE = ? \n" );
		sb.append( " AND AATH.CREATED_BY = SU.USER_ID \n" ); 
//		sb.append( " AND AATH.CREATED_BY = ?  \n" ); 
		sb.append( " AND AATH.FROM_ORGANIZATION_ID = ?  \n" ); 
		
		sb.append( " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_STATUS = ?)  \n" ); 
		sb.append( " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_NO LIKE ?)  \n" ); 
		sb.append( " AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE))  \n" );
		sb.append( " AND ( " + SyBaseSQLUtil.isNull() + "  OR DATEADD(  DD , -1 , AATH.CREATION_DATE ) <= ISNULL(?, AATH.CREATION_DATE))  \n" );
		
		sqlArgs.add( LeaseAppConstant.TRANS_TYPE );
//		sqlArgs.add( this.user.getUserId() );
		sqlArgs.add( this.user.getOrganizationId() );
		sqlArgs.add( dto.getTransStatus() );
		sqlArgs.add( dto.getTransStatus() );
		
		sqlArgs.add( dto.getTransNo() );
		sqlArgs.add( dto.getTransNo() );
		try {
			sqlArgs.add( dto.getStartDate() );
			sqlArgs.add( dto.getStartDate() );
			sqlArgs.add( dto.getEndDate() );
			sqlArgs.add( dto.getEndDate() ); 
		} catch (CalendarException e) {
			e.printLog();
		}
		 
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	@SuppressWarnings("unchecked")
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		LeaseHeaderDTO dto = (LeaseHeaderDTO) dtoParameter;
		StringBuilder sb = new StringBuilder();
		sb.append( " SELECT  \n" );
		sb.append( " AATH.TRANS_ID, \n" );
		sb.append( " AATH.TRANS_NO, \n" );    //单据号
		sb.append( " AATH.FROM_DEPT, \n" );    
		sb.append( " AATH.TO_DEPT, \n" );
		sb.append( " AATH.TRANS_TYPE, \n" );  //单据类型
		sb.append( " AATH.TRANSFER_TYPE, \n" ); 
		sb.append( " AATH.TRANS_STATUS, \n" );
		sb.append( " AATH.TRANS_DATE, \n" );
		sb.append( " AATH.CREATION_DATE, \n" );    //创建日期
		sb.append( " AATH.CREATED_BY, \n" );       //经办人
		sb.append( " AATH.LAST_UPDATE_DATE, \n" ); 
		sb.append( " AATH.LAST_UPDATE_BY, \n" );
		sb.append( " AATH.CANCELED_DATE, \n" );
		sb.append( " AATH.CANCELED_REASON, \n" );
		sb.append( " AATH.CREATED_REASON, \n" ); //备注
		sb.append( " AATH.APPROVED_DATE, \n" );
		sb.append( " AATH.FROM_ORGANIZATION_ID, \n" ); //公司名称
		sb.append( " EOCM.COMPANY FROM_COMPANY_NAME, \n" ); //公司名称
		sb.append( " AATH.FROM_GROUP, \n" );
		sb.append( " AATH.FA_CONTENT_CODE, \n" );
		sb.append( " AATH.LOSSES_NAME, \n" );
		sb.append( " AATH.LOSSES_DATE, \n" );
		sb.append( " AATH.IS_THRED, \n" );
//		sb.append( " AMD.DEPT_NAME FROM_DEPT_NAME, \n" );
		sb.append( " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC, \n" );
		sb.append( " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE, \n" );
//		sb.append( " dbo.APP_GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FINANCE_PROP') FA_CONTENT_NAME, \n" );
		
		
		sb.append( " SU.USERNAME CREATED, \n" ); 
		sb.append( " SU.EMAIL, \n" ); //电子邮件
		sb.append( " SU.MOBILE_PHONE PHONE_NUMBER,AATH.EMERGENT_LEVEL  \n" );		 //手机号码
//		sb.append( " SG.GROUP_NAME FROM_GROUP_NAME  \n" ); 
		
//		sb.append( "0 GROUP_PROP, \n" );
//				sb.append( " EOCM.BOOK_TYPE_CODE, \n" ); 
//				sb.append( " EOCM.BOOK_TYPE_NAME, \n" );
				
//				sb.append( " AMD2.DEPT_NAME USER_DEPT_NAME, \n" );
//				sb.append( " AATH.TO_ORGANIZATION_ID, \n" );
//		sb.append( " EOCM2.COMPANY TO_COMPANY_NAME"  );
		sb.append( " FROM  \n" );
		sb.append( " AMS_ASSETS_TRANS_HEADER AATH, \n" );
		sb.append( " ETS_OU_CITY_MAP         EOCM, \n" );
		

		
//		sb.append( " AMS_MIS_DEPT            AMD, \n" );
//		sb.append( " SF_GROUP                SG, \n" );
		sb.append( " SF_USER                 SU  \n" );
//		sb.append( " AMS_MIS_EMPLOYEE        AME, \n" );
//		sb.append( " AMS_MIS_DEPT            AMD2, \n" );
//		sb.append( " ETS_OU_CITY_MAP         EOCM2"  );
		sb.append( " WHERE  \n" );
		sb.append( " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n" );
//		sb.append( " AND CONVERT( VARCHAR , AATH.FROM_DEPT ) *= AMD.DEPT_CODE \n" );
//		sb.append( " AND AATH.FROM_GROUP = SG.GROUP_ID \n" );
		sb.append( " AND AATH.CREATED_BY = SU.USER_ID \n" ); 
//		sb.append( " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER \n" );
//		sb.append( " AND AME.DEPT_CODE *= AMD2.DEPT_CODE \n" );
//		sb.append( " AND AATH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID \n" );
		sb.append( " AND TRANS_ID = ? \n" );
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
     * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    @SuppressWarnings("unchecked")
	public SQLModel getLinesModel( String headerId ) {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        StringBuilder sb = new StringBuilder();
         
        sb.append( " SELECT  \n" );
        sb.append( " AATL.TRANS_ID,  \n" );
        sb.append( " AATL.LINE_ID ,  \n" );
        sb.append( " AATL.BARCODE ,  \n" );
//        sb.append( " AATL.ASSIGNED_TO_LOCATION ,  \n" );
        sb.append( " AATL.RESPONSIBILITY_USER ,  \n" );
        sb.append( " AATL.REMARK,  \n" );
        sb.append( " AATL.ASSET_ID,   \n" );
        
        sb.append( " ESI.ITEM_NAME,   \n" );         //名称
		sb.append( " ESI.ITEM_SPEC,   \n" );         //规格型号
        sb.append( " EO.WORKORDER_OBJECT_LOCATION ,  \n" );   //地点
        sb.append( " AME.USER_NAME RESPONSIBILITY_USER_NAME,  \n" );  
        
        sb.append( " AATL.RENT_DATE,   \n" );
        sb.append( " AATL.RENT_END_DATE ,   \n" );
        sb.append( " AATL.RENT_PERSON,   \n" ); 
        sb.append( " AATL.TENANCY,   \n" );
        sb.append( " AATL.YEAR_RENTAL,   \n" );
        sb.append( " AATL.MONTH_RENTAL,   \n" );
        sb.append( " AATL.CONTRACT_NUMBER,   \n" );
        sb.append( " AATL.CONTRACT_NAME   \n" ); 
        sb.append( " FROM  \n" );
        sb.append( " AMS_ASSETS_TRANS_LINE AATL,  \n" );
        sb.append( " ETS_OBJECT          EO,  \n" );   
		sb.append( " ETS_ITEM_INFO  EII, \n" );
		sb.append( " ETS_SYSTEM_ITEM     ESI, \n" );
		sb.append( " AMS_OBJECT_ADDRESS  AOA,  \n" );  
		sb.append( " AMS_MIS_EMPLOYEE AME   \n" ); 
        sb.append( " WHERE  \n" );
        sb.append( " AATL.TRANS_ID = ?  \n" ); 
        sb.append( " AND AATL.BARCODE = EII.BARCODE  \n" );
        sb.append( " AND EII.ITEM_CODE = ESI.ITEM_CODE  \n" );
		sb.append( " AND EII.ADDRESS_ID = AOA.ADDRESS_ID  \n" );
		sb.append( " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID   \n" );
		sb.append( " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO    \n" );
        sqlArgs.add(headerId);
        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    
    /**
     * 创建头信息
     * @param header
     * @return
     */
    @SuppressWarnings("unchecked")
	public SQLModel createHeaderModel( LeaseHeaderDTO header ){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
        StringBuilder sb = new StringBuilder();
        
        sb.append( " INSERT INTO AMS_ASSETS_TRANS_HEADER(  \n" );
        sb.append( " TRANS_ID,  \n" );
        sb.append( " TRANS_NO,  \n" );
        sb.append( " TRANS_STATUS,  \n" );
        sb.append( " FROM_ORGANIZATION_ID,  \n" );
        sb.append( " CREATED_REASON,  \n" );
        sb.append( " CREATED_BY,  \n" );
        sb.append( " CREATION_DATE,  \n" );
        sb.append( " TRANS_TYPE,  \n" );
        sb.append( " EMERGENT_LEVEL  \n" );
        sb.append( " ) VALUES (  \n" );
        sb.append( " ?,?,?,?,?,?,GETDATE(),?,?  \n" );
        sb.append( " )  \n" );
        
        sqlArgs.add( header.getTransId() );
        sqlArgs.add( header.getTransNo() );
        sqlArgs.add( header.getTransStatus() );
        sqlArgs.add( header.getFromOrganizationId() );
        sqlArgs.add( header.getCreatedReason() );
        
        sqlArgs.add( user.getUserId() );
        sqlArgs.add( header.getTransType() );
        sqlArgs.add( header.getEmergentLevel());
          
        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 删除行信息
     * @param header
     * @return
     */
    @SuppressWarnings("unchecked")
	public SQLModel deleteLinesModel( String headerId ){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
        StringBuilder sb = new StringBuilder();
    	sb.append( " DELETE FROM AMS_ASSETS_TRANS_LINE  \n" );
    	sb.append( " WHERE  \n" );
    	sb.append( " TRANS_ID = ?  \n" );  
        sqlArgs.add( headerId );
        
        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 创建行信息
     * @param line
     * @return
     * @throws CalendarException 
     */
    @SuppressWarnings("unchecked")
	public SQLModel createLineModel(LeaseLineDTO line ) throws CalendarException{
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        StringBuilder sb = new StringBuilder();
         
        sb.append( " INSERT INTO AMS_ASSETS_TRANS_LINE  \n" );
        sb.append( " (TRANS_ID,  \n" );
        sb.append( " LINE_ID ,  \n" );
        sb.append( " BARCODE ,  \n" );
//        sb.append( " ASSIGNED_TO_LOCATION ,  \n" );
        sb.append( " RESPONSIBILITY_USER ,  \n" );
//        sb.append( " ASSET_ID,   \n" );
        sb.append( " RENT_DATE,   \n" );
        sb.append( " RENT_END_DATE,   \n" );
        sb.append( " RENT_PERSON,   \n" ); 
        sb.append( " TENANCY,   \n" );
        sb.append( " YEAR_RENTAL,   \n" );
        sb.append( " MONTH_RENTAL,   \n" );
        sb.append( " CONTRACT_NUMBER,   \n" );
        sb.append( " CONTRACT_NAME )   \n" ); 
        sb.append( " VALUES (  \n" );
        sb.append( " ?,?,?,?,?,  ?,?,?,?,?,   ?,?  \n" );
        sb.append( " )  \n" );
        
        sqlArgs.add( line.getTransId() );
        sqlArgs.add( line.getLineId() );
        sqlArgs.add( line.getBarcode() );
//        sqlArgs.add( line.getTransId() );
        sqlArgs.add( line.getResponsibilityUser() ); 
        sqlArgs.add( line.getRentDate() );
        
        sqlArgs.add( line.getRentEndDate() );
        sqlArgs.add( line.getRentPerson() );
        sqlArgs.add( line.getTenancy() );
        sqlArgs.add( line.getYearRental() ); 
        sqlArgs.add( line.getMonthRental() );
        
        sqlArgs.add( line.getContractNumber() );
        sqlArgs.add( line.getContractName() );
        
        
        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    
    /**
     * 更新头信息
     * @param header
     * @return
     */
    @SuppressWarnings("unchecked")
	public SQLModel updateHeaderModel( LeaseHeaderDTO header ){
    	SQLModel sqlModel = new SQLModel();
    	List sqlArgs = new ArrayList();
        StringBuilder sb = new StringBuilder();
    	sb.append( " UPDATE AMS_ASSETS_TRANS_HEADER SET  \n" );
    	sb.append( " TRANS_STATUS = ? ,  \n" );
    	sb.append( " LAST_UPDATE_DATE = GETDATE() ,  \n" );
    	sb.append( " LAST_UPDATE_BY = ? ,  \n" );
    	sb.append( " CREATED_REASON = ?   \n" );
    	sb.append( " WHERE  \n" );
    	sb.append( " TRANS_ID = ?  \n" ); 
    	
        sqlArgs.add( header.getTransStatus() );
        sqlArgs.add( user.getUserId() );
        sqlArgs.add( header.getCreatedReason() );
        sqlArgs.add( header.getTransId() );
        
        sqlModel.setSqlStr(sb.toString());
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    @SuppressWarnings("unchecked")
	public SQLModel updateHeaderStatusModel( LeaseHeaderDTO header ) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append( " UPDATE AMS_ASSETS_TRANS_HEADER SET  \n" );
    	sb.append( " TRANS_STATUS = ? ,  \n" );
    	sb.append( " LAST_UPDATE_DATE = GETDATE() ,  \n" );
    	sb.append( " LAST_UPDATE_BY = ?  \n" );
    	sb.append( " WHERE  \n" );
    	sb.append( " TRANS_ID = ?  \n" ); 
    	
        sqlArgs.add( header.getTransStatus() );
        sqlArgs.add( user.getUserId() );
        sqlArgs.add( header.getTransId() );
	       
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


    @SuppressWarnings("unchecked")
	public SQLModel getOrderCompleteModel( LeaseHeaderDTO header ) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append( " UPDATE AMS_ASSETS_TRANS_HEADER SET  \n" );
    	sb.append( " TRANS_STATUS = ? ,  \n" );
    	sb.append( " TRANS_DATE = GETDATE() ,  \n" );
    	sb.append( " LAST_UPDATE_DATE = GETDATE() ,  \n" );
    	sb.append( " LAST_UPDATE_BY = ?  \n" );
    	sb.append( " WHERE  \n" );
    	sb.append( " TRANS_ID = ?  \n" );

        sqlArgs.add( header.getTransStatus() );
        sqlArgs.add( user.getUserId() );
        sqlArgs.add( header.getTransId() );

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
     * 续租成功之后，更新资产相关状态（在用，生效）
     * @param line
     * @author YoungerOu
     * @return
     */
	@SuppressWarnings("unchecked")
	public SQLModel getUpdateEiiModel(LeaseLineDTO line) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();
		sb.append( " UPDATE ETS_ITEM_INFO SET  \n" );
		sb.append( " ITEM_STATUS = 'NORMAL' ,  \n" );
		sb.append( " DISABLE_DATE = NULL   \n" );
		sb.append( " WHERE  \n" );
		sb.append( " BARCODE = ?  \n" );
		
		sqlArgs.add(line.getBarcode());
		
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
}
