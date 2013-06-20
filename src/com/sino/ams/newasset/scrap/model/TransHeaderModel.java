package com.sino.ams.newasset.scrap.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.model.AmsAssetsTransHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: AmsAssetsTransHeaderModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransHeaderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TransHeaderModel extends AmsAssetsTransHeaderModel {
	private SfUserDTO user = null;

	/**
	 * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
	 */
	public TransHeaderModel(SfUserDTO userAccount,
			AmsAssetsTransHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.user = (SfUserDTO) userAccount;
	}

	  
	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = " SELECT"
				+ " AATH.TRANS_ID,"
				+ " AATH.TRANS_NO,"
				+ " AATH.FROM_DEPT,"
				+ " AATH.TO_DEPT,"
				+ " AATH.TRANS_TYPE,"
				+ " AATH.TRANSFER_TYPE,"
				+ " AATH.TRANS_STATUS,"
				+ " AATH.TRANS_DATE,"
				+ " AATH.CREATION_DATE,"
				+ " AATH.CREATED_BY,"
				+ " AATH.LAST_UPDATE_DATE,"
				+ " AATH.LAST_UPDATE_BY,"
				+ " AATH.CANCELED_DATE,"
				+ " AATH.CANCELED_REASON,"
				+ " AATH.CREATED_REASON,"
				+ " AATH.APPROVED_DATE,"
				+ " AATH.FROM_ORGANIZATION_ID,"
				+ " AATH.FROM_GROUP,"
				+ " AATH.FA_CONTENT_CODE,"
				+ " AATH.LOSSES_NAME,"
				+ " AATH.LOSSES_DATE,"
				+ " AATH.IS_THRED,"
				+ " AMD.DEPT_NAME FROM_DEPT_NAME,"
				+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
				+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
				+ " dbo.APP_GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FINANCE_PROP') FA_CONTENT_NAME,"
				+ " SU.USERNAME CREATED," + " SU.EMAIL,"
				+ " SU.MOBILE_PHONE PHONE_NUMBER,"
				+ " SG.GROUP_NAME FROM_GROUP_NAME," + "0 GROUP_PROP,"
				+ " EOCM.BOOK_TYPE_CODE," + " EOCM.BOOK_TYPE_NAME,"
				+ " EOCM.COMPANY FROM_COMPANY_NAME,"
				+ " AMD2.DEPT_NAME USER_DEPT_NAME,"
				+ " AATH.TO_ORGANIZATION_ID,"
				+ " EOCM2.COMPANY TO_COMPANY_NAME,"
				+ "	CASE AATH.EMERGENT_LEVEL WHEN '1' THEN '急' WHEN '2' THEN '加急' WHEN '3' THEN '特急' ELSE '正常' END EMERGENT_LEVEL\n"
				+ " FROM"
				+ " AMS_ASSETS_TRANS_HEADER AATH,"
				+ " ETS_OU_CITY_MAP         EOCM,"
				+ " AMS_MIS_DEPT            AMD,"
				+ " SF_GROUP                SG,"
				+ " SF_USER                 SU,"
				+ " AMS_MIS_EMPLOYEE        AME,"
				+ " AMS_MIS_DEPT            AMD2,"
				+ " ETS_OU_CITY_MAP         EOCM2" + " WHERE"
				+ " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
				+ " AND CONVERT( VARCHAR , AATH.FROM_DEPT ) *= AMD.DEPT_CODE"
				+ " AND AATH.FROM_GROUP = SG.GROUP_ID"
				+ " AND AATH.CREATED_BY = SU.USER_ID"
				+ " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
				+ " AND AME.DEPT_CODE *= AMD2.DEPT_CODE"
				+ " AND AATH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID"
				+ " AND TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
 
	
	/**
	 * 功能：改变单据状态
	 * @return SQLModel
	 */
	public SQLModel getOrderApproveModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String transType = dto.getTransType(); 
		List sqlArgs = new ArrayList();
 
		String sqlStr = "UPDATE"
					 + " AMS_ASSETS_TRANS_HEADER"
					 + " SET"
					 + " TRANS_STATUS = ?,"
					 + " APPROVED_DATE = GETDATE(),"
					 + " APPROVED_BY = ?,"
					 + " LAST_UPDATE_DATE = GETDATE(),"
					 + " TRANS_DATE = GETDATE(),"
					 + " LAST_UPDATE_BY = ?"
					 + " WHERE"
					 + " TRANS_ID = ?";
		
		sqlArgs.add(dto.getTransStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：更新资产单据行数据状态为已审批
	 * @return SQLModel
	 */
	public SQLModel getLineStatusUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE "
						+ " SET"
						+ " LINE_STATUS = ?"
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add(AssetsDictConstant.APPROVED);
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：获取资产报废SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsDiscardModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO "
						+ " SET"
						+ " ITEM_STATUS      = ?,"
//						+ " RESPONSIBILITY_USER  = -1 ,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE" 
						+ " AATL.BARCODE = ETS_ITEM_INFO.BARCODE"
                        + " AND ((AATL.REMARK <> '不允许报废') OR (AATL.REMARK " + SyBaseSQLUtil.isNullNoParam() + " ))"
						+ " AND AATL.TRANS_ID = ?)";
//						+ " EXISTS ("
//						+ " SELECT"
//						+ " NULL"
//						+ " FROM"
//						+ " AMS_ASSETS_TRANS_HEADER AATH,"
//						+ " AMS_ASSETS_TRANS_LINE   AATL"
//						+ " WHERE"
//						+ " AATH.TRANS_ID = AATL.TRANS_ID"
//						+ " AND AATL.BARCODE = ETS_ITEM_INFO.BARCODE"
//                        + " AND ((AATL.REMARK <> '不允许报废') OR (AATL.REMARK " + SyBaseSQLUtil.isNullNoParam() + " ))"
//                        + " AND AATH.TRANS_TYPE = ?"
//						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_DISCARDED );
		sqlArgs.add(userAccount.getUserId());
//		sqlArgs.add(dto.getTransType());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
