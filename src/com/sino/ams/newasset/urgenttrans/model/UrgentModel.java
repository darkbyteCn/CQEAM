package com.sino.ams.newasset.urgenttrans.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.urgenttrans.constant.UrgentAppConstant;
import com.sino.ams.newasset.urgenttrans.dto.UrgentHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * 
 * @系统名称: 紧急调拨单
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
@SuppressWarnings("unchecked")
public class UrgentModel extends AMSSQLProducer {
	private SfUserDTO user = null;
	UrgentHeaderDTO headerDTO = null;

	public UrgentModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.headerDTO = (UrgentHeaderDTO) dtoParameter;
		this.user = (SfUserDTO) userAccount;
	}

	
	/**
	 *  翻页查询
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		UrgentHeaderDTO dto = (UrgentHeaderDTO) dtoParameter;
		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT  \n");
		sb.append(" AATH.TRANS_ID, \n");
		sb.append(" AATH.TRANS_NO, \n"); // 单据号
		sb.append(" AATH.TRANS_STATUS,  \n");
		sb.append(" AATH.FROM_ORGANIZATION_ID,  \n");
		// sb.append( " CREATED_REASON, \n" );

		sb.append(" AATH.CREATED_BY,  \n");
		sb.append(" AATH.CREATION_DATE,  \n");
		sb.append(" AATH.TRANS_TYPE,  \n");
		sb.append(" AATH.TRANSFER_TYPE,  \n");
		sb.append(" AATH.FROM_DEPT,  \n");

		sb.append(" AATH.FROM_OBJECT_NO,  \n");
		sb.append(" AATH.IMPLEMENT_BY,  \n");
		sb.append(" AATH.ARCHIVED_BY,  \n");
		sb.append(" AATH.TO_OBJECT_NO,  \n");
		sb.append(" AATH.TO_IMPLEMENT_BY,   \n");

		sb.append(" AATH.FROM_GROUP,  \n");

		sb.append(" EOCM.COMPANY FROM_COMPANY_NAME, \n"); // 公司名称
		sb.append(" EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,  \n");
		sb.append(" EO1.WORKORDER_OBJECT_CODE FROM_OBJECT_CODE,  \n");
		sb.append(" EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,   \n");
		sb.append(" EO2.WORKORDER_OBJECT_CODE TO_OBJECT_CODE,  \n");

		sb.append(" dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC, \n");
		sb.append(" dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE, \n");

		sb.append(" SU.USERNAME CREATED \n");
		sb.append(" FROM  \n");
		sb.append(" AMS_ASSETS_TRANS_HEADER AATH, \n");
		sb.append(" ETS_OU_CITY_MAP         EOCM, \n");
		sb.append(" ETS_OBJECT 			 EO1, \n");
		sb.append(" ETS_OBJECT 			 EO2, \n");
		sb.append(" SF_USER                 SU  \n");
		sb.append(" WHERE  \n");
		sb.append(" AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n");
		sb.append(" AND AATH.FROM_OBJECT_NO = EO1.WORKORDER_OBJECT_NO \n");
		sb.append(" AND AATH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO \n");
		sb.append(" AND AATH.TRANS_TYPE = ? \n");
		sb.append(" AND AATH.CREATED_BY = SU.USER_ID \n");
		sb.append(" AND ( AATH.FROM_ORGANIZATION_ID = ? OR AATH.TO_ORGANIZATION_ID = ? ) \n");

		// sb.append( " AND ( " + SyBaseSQLUtil.isNull() + " OR AATH.TRANS_STATUS = ?) \n" );
		sb.append(" AND ( " + SyBaseSQLUtil.isNull() + "  OR AATH.TRANS_NO LIKE ?)  \n");
		sb.append(" AND ( "
						+ SyBaseSQLUtil.isNull()
						+ "  OR AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE))  \n");
		sb.append(" AND ( "
						+ SyBaseSQLUtil.isNull()
						+ "  OR DATEADD(  DD , -1 , AATH.CREATION_DATE ) <= ISNULL(?, AATH.CREATION_DATE))  \n");
		
		sqlArgs.add(UrgentAppConstant.TRANS_TYPE);
		sqlArgs.add(this.user.getOrganizationId());
		sqlArgs.add(this.user.getOrganizationId());
		// sqlArgs.add( dto.getTransStatus() );
		// sqlArgs.add( dto.getTransStatus() );
		sqlArgs.add(dto.getTransNo());
		sqlArgs.add(dto.getTransNo());
		try {
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getEndDate());
		} catch (CalendarException e) {
			e.printLog();
		}
		if (headerDTO.isArchive()) {
			// 归档控制
			sb.append(" AND AATH.ARCHIVED_BY = ?  \n");
//			sb.append(" AND AATH.TRANS_STATUS = ? \n");
			 sb.append( " AND ( AATH.TRANS_STATUS = ? ) \n" );
			sqlArgs.add(user.getUserId());
			sqlArgs.add(UrgentAppConstant.STATUS_TRANS_IN);
				
			// sqlArgs.add( UrgentAppConstant.STATUS_ARCHIVED );
		}
		if(dto.getCreatedBy()>0){
			sb.append( " AND ( AATH.CREATED_BY = ? ) \n" );
			sqlArgs.add(dto.getCreatedBy());
		}
		if(!dto.getStatus().equals("")){
			sb.append( " AND ( AATH.TRANS_STATUS = ? ) \n" );
			sqlArgs.add(dto.getStatus());
		}
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：取头信息
	 * 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		UrgentHeaderDTO dto = (UrgentHeaderDTO) dtoParameter;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT  \n");
		sb.append(" AATH.TRANS_ID, \n");
		sb.append(" AATH.TRANS_NO, \n"); // 单据号
		sb.append(" AATH.FROM_DEPT, \n");
		sb.append(" AATH.TO_DEPT, \n");
		sb.append(" AATH.TRANS_TYPE, \n"); // 单据类型
		sb.append(" AATH.TRANSFER_TYPE, \n");
		sb.append(" AATH.TRANS_STATUS, \n");
		sb.append(" AATH.TRANS_DATE, \n");
		sb.append(" AATH.CREATION_DATE, \n"); // 创建日期
		sb.append(" AATH.CREATED_BY, \n"); // 经办人
		sb.append(" AATH.LAST_UPDATE_DATE, \n");
		sb.append(" AATH.LAST_UPDATE_BY, \n");
		sb.append(" AATH.CANCELED_DATE, \n");
		sb.append(" AATH.CANCELED_REASON, \n");
		sb.append(" AATH.CREATED_REASON, \n"); // 备注
		sb.append(" AATH.APPROVED_DATE, \n");
		sb.append(" AATH.FROM_ORGANIZATION_ID, \n"); // 公司名称
		sb.append(" EOCM.COMPANY FROM_COMPANY_NAME, \n"); // 公司名称
		sb.append(" AATH.FROM_GROUP, \n");
		sb.append(" AATH.FA_CONTENT_CODE, \n");
		sb.append(" AATH.LOSSES_NAME, \n");
		sb.append(" AATH.LOSSES_DATE, \n");
		sb.append(" AATH.IS_THRED, \n");

		sb.append(" AATH.FROM_OBJECT_NO,  \n");
		sb.append(" AATH.IMPLEMENT_BY,  \n");
		sb.append(" AATH.ARCHIVED_BY,  \n");
		sb.append(" AATH.TO_OBJECT_NO,  \n");
		sb.append(" AATH.TO_IMPLEMENT_BY,   \n");
		sb.append(" EO1.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,  \n");
		sb.append(" EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,   \n");

		sb.append(" dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_NAME, \n");
		sb.append(" dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE, \n");

		sb.append(" dbo.APP_GET_DEPT_NAME( FROM_DEPT ) FROM_DEPT_NAME, \n");
		sb.append(" dbo.APP_GET_USER_NAME( IMPLEMENT_BY ) IMPLEMENT_BY_NAME, \n");
		sb.append(" dbo.APP_GET_USER_NAME( TO_IMPLEMENT_BY ) TO_IMPLEMENT_BY_NAME, \n");
		sb.append(" dbo.APP_GET_USER_NAME( ARCHIVED_BY ) ARCHIVED_BY_NAME, \n");

		sb.append(" SU.USERNAME CREATED, \n");
		sb.append(" SU.EMAIL, \n"); // 电子邮件
		sb.append(" SU.MOBILE_PHONE PHONE_NUMBER  \n"); // 手机号码
		sb.append(" FROM  \n");
		sb.append(" AMS_ASSETS_TRANS_HEADER AATH, \n");
		sb.append(" ETS_OU_CITY_MAP         EOCM, \n");
		sb.append(" ETS_OBJECT 			 EO1, \n");
		sb.append(" ETS_OBJECT 			 EO2, \n");
		sb.append(" SF_USER                 SU \n");
		sb.append(" WHERE  \n");
		sb.append(" AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n");
		sb.append(" AND AATH.FROM_OBJECT_NO *= EO1.WORKORDER_OBJECT_NO \n");
		sb.append(" AND AATH.TO_OBJECT_NO *= EO2.WORKORDER_OBJECT_NO \n");
		sb.append(" AND AATH.CREATED_BY = SU.USER_ID \n");
		sb.append(" AND TRANS_ID = ? \n");
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：取行信息
	 * 
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getLinesModel(String headerId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT AATL.BARCODE,  \n");
		sb.append("        AATL.TRANS_ID,  \n");
		sb.append("        AATL.LINE_ID,  \n");
		sb.append("        ESI.ITEM_NAME,  \n");
		sb.append("        EII.ITEM_CODE,  \n");
		sb.append("        ESI.ITEM_SPEC,  \n");
		sb.append("        ESI.ITEM_CATEGORY,  \n");
		sb.append("        EII.START_DATE,  \n");
		sb.append(" 		AME.USER_NAME RESPONSIBILITY_USER_NAME,  \n");
		sb.append("        EII.RESPONSIBILITY_USER  \n");
		sb.append(" FROM   AMS_ASSETS_TRANS_LINE AATL,  \n");
		sb.append("        ETS_ITEM_INFO         EII,  \n");
		sb.append("        ETS_SYSTEM_ITEM       ESI,  \n");
		sb.append(" 		AMS_MIS_EMPLOYEE AME   \n");
		sb.append(" WHERE  AATL.BARCODE = EII.BARCODE  \n");
		sb.append("        AND EII.ITEM_CODE = ESI.ITEM_CODE  \n");
		sb.append(" 		AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID   \n");
		sb.append("        AND AATL.TRANS_ID = ?  \n");

		// sb.append( " SELECT \n" );
		// sb.append( " AATL.TRANS_ID, \n" );
		// sb.append( " AATL.LINE_ID , \n" );
		// sb.append( " AATL.BARCODE , \n" );
		// // sb.append( " AATL.ASSIGNED_TO_LOCATION , \n" );
		// sb.append( " AATL.RESPONSIBILITY_USER , \n" );
		// sb.append( " AATL.REMARK, \n" );
		// sb.append( " AATL.ASSET_ID, \n" );
		//        
		// sb.append( " ESI.ITEM_NAME, \n" ); //名称
		// sb.append( " ESI.ITEM_SPEC, \n" ); //规格型号
		// sb.append( " EO.WORKORDER_OBJECT_LOCATION , \n" ); //地点
		// sb.append( " AME.USER_NAME RESPONSIBILITY_USER_NAME, \n" );
		//        
		// sb.append( " AATL.RENT_DATE, \n" );
		// sb.append( " AATL.RENT_END_DATE , \n" );
		// sb.append( " AATL.RENT_PERSON, \n" );
		// sb.append( " AATL.TENANCY, \n" );
		// sb.append( " AATL.YEAR_RENTAL, \n" );
		// sb.append( " AATL.MONTH_RENTAL, \n" );
		// sb.append( " AATL.CONTRACT_NUMBER, \n" );
		// sb.append( " AATL.CONTRACT_NAME \n" );
		// sb.append( " FROM \n" );
		// sb.append( " AMS_ASSETS_TRANS_LINE AATL, \n" );
		// sb.append( " ETS_OBJECT EO, \n" );
		// sb.append( " ETS_ITEM_INFO EII, \n" );
		// sb.append( " ETS_SYSTEM_ITEM ESI, \n" );
		// sb.append( " AMS_OBJECT_ADDRESS AOA, \n" );
		// sb.append( " AMS_MIS_EMPLOYEE AME \n" );
		// sb.append( " WHERE \n" );
		// sb.append( " AATL.TRANS_ID = ? \n" );
		// sb.append( " AND AATL.BARCODE = EII.BARCODE \n" );
		// sb.append( " AND EII.ITEM_CODE = ESI.ITEM_CODE \n" );
		// sb.append( " AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" );
		// sb.append( " AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID \n" );
		// sb.append( " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" );
		sqlArgs.add(headerId);
		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 更新头信息
	 * 
	 * @param header
	 * @return
	 */
	public SQLModel updateHeaderModel(UrgentHeaderDTO header) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE AMS_ASSETS_TRANS_HEADER SET  \n");
		sb.append(" TRANS_STATUS = ? ,  \n");
		sb.append(" LAST_UPDATE_DATE = GETDATE() ,  \n");
		sb.append(" LAST_UPDATE_BY = ? ,  \n");

		sb.append(" FROM_OBJECT_NO = ?,  \n");
		sb.append(" IMPLEMENT_BY = ?,  \n");
		sb.append(" ARCHIVED_BY = ?,  \n");
		sb.append(" TO_OBJECT_NO = ?,  \n");
		sb.append(" TO_IMPLEMENT_BY = ?,   \n");

		sb.append(" CREATED_REASON = ?   \n");
		sb.append(" WHERE  \n");
		sb.append(" TRANS_ID = ?  \n");

		sqlArgs.add(header.getTransStatus());
		sqlArgs.add(user.getUserId());

		sqlArgs.add(header.getFromObjectNo());
		sqlArgs.add(header.getImplementBy());
		sqlArgs.add(header.getArchivedBy());
		sqlArgs.add(header.getToObjectNo());
		sqlArgs.add(header.getToImplementBy());

		sqlArgs.add(header.getCreatedReason());
		sqlArgs.add(header.getTransId());

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 创建头信息
	 * 
	 * @param header
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SQLModel createHeaderModel(UrgentHeaderDTO header) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append(" INSERT INTO AMS_ASSETS_TRANS_HEADER(  \n");
		sb.append(" TRANS_ID,  \n");
		sb.append(" TRANS_NO,  \n");
		sb.append(" TRANS_STATUS,  \n");
		sb.append(" FROM_ORGANIZATION_ID,  \n");
		sb.append(" CREATED_REASON,  \n");

		sb.append(" CREATED_BY,  \n");
		sb.append(" CREATION_DATE,  \n");
		sb.append(" TRANS_TYPE,  \n");
		sb.append(" TRANSFER_TYPE,  \n");
		sb.append(" FROM_DEPT,  \n");

		sb.append(" FROM_OBJECT_NO,  \n");
		sb.append(" IMPLEMENT_BY,  \n");
		sb.append(" ARCHIVED_BY,  \n");
		sb.append(" TO_OBJECT_NO,  \n");
		sb.append(" TO_IMPLEMENT_BY,   \n");

		sb.append(" FROM_GROUP  \n");

		sb.append(" ) VALUES (  \n");
		sb.append(" ?,?,?,?,?,  ?,GETDATE() ,?,?,?  ,?,?,?,?,?  ,? \n");
		sb.append(" )  \n");

		sqlArgs.add(header.getTransId());
		sqlArgs.add(header.getTransNo());
		sqlArgs.add(header.getTransStatus());
		sqlArgs.add(header.getFromOrganizationId());
		sqlArgs.add(header.getCreatedReason());

		sqlArgs.add(user.getUserId());
		sqlArgs.add(header.getTransType());
		sqlArgs.add(header.getTransferType());
		sqlArgs.add(header.getFromDept());

		sqlArgs.add(header.getFromObjectNo());
		sqlArgs.add(header.getImplementBy());
		sqlArgs.add(header.getArchivedBy());
		sqlArgs.add(header.getToObjectNo());
		sqlArgs.add(header.getToImplementBy());

		sqlArgs.add(header.getFromGroup());

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel updateHeaderStatusModel(UrgentHeaderDTO header) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append(" UPDATE AMS_ASSETS_TRANS_HEADER SET  \n");
		sb.append(" TRANS_STATUS = ? ,  \n");
		sb.append(" LAST_UPDATE_DATE = GETDATE() ,  \n");
		sb.append(" LAST_UPDATE_BY = ?  \n");
		sb.append(" WHERE  \n");
		sb.append(" TRANS_ID = ?  \n");

		sqlArgs.add(header.getTransStatus());
		sqlArgs.add(user.getUserId());
		sqlArgs.add(header.getTransId());

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 删除头信息
	 * 
	 * @param header
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SQLModel delHeaderModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append(" DELETE FROM AMS_ASSETS_TRANS_HEADER   \n");
		sb.append(" WHERE TRANS_ID = ?  \n");

		sqlArgs.add(transId);

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 删除行信息
	 * 
	 * @param header
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SQLModel delLinesModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append(" DELETE FROM AMS_ASSETS_TRANS_LINE   \n");
		sb.append(" WHERE TRANS_ID = ?  \n");

		sqlArgs.add(transId);

		sqlModel.setSqlStr(sb.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
