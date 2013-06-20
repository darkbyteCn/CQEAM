package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: OrderApproveModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransHeaderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class OrderApproveModel extends AMSSQLProducer {
    private String aggBarcodes = "";

    /**
	 * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
	 */
	public OrderApproveModel(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
        if (dtoParameter.getBarcodess()!=null) {
           initBarcodes(dtoParameter.getBarcodess());
        }
    }

    //初始化获取的BARCODE
    private String initBarcodes (String[] barcodes) {
        aggBarcodes = "(";
        for (int i = 0; i < barcodes.length; i++) {
             String barcode = barcodes[i];
             aggBarcodes += "'" + barcode + "', ";
        }
        aggBarcodes += "'aa')";
//        int cc = aggBarcodes.lastIndexOf(",");
//        aggBarcodes = aggBarcodes.substring(0,cc)+")";
        return aggBarcodes;
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
						+ " AATH.IS_THRED,"
						+ " CASE AATH.EMERGENT_LEVEL WHEN '1' THEN '急' WHEN '2' THEN '加急' WHEN '3' THEN '特急' ELSE '正常' END EMERGENT_LEVEL,\n"
						+ " (SELECT"
						+ " GROUP_ID"
						+ " FROM"
						+ " SF_GROUP SGP"
						+ " WHERE"
						+ " CONVERT(VARCHAR,SGP.GROUP_ID) = CONVERT(VARCHAR,SG.GROUP_PID)) GROUP_PID,"
						+ " AMD.DEPT_NAME FROM_DEPT_NAME,"
						+ " AATH.FA_CONTENT_CODE,"
						+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
						+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
						+ " dbo.APP_GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,"
						+ " SU.USERNAME CREATED,"
						+ " SU.EMAIL,"
						+ " SU.MOBILE_PHONE PHONE_NUMBER,"
						+ " SG.GROUP_NAME FROM_GROUP_NAME,"
						+ " 0 GROUP_PROP,"
						+ " EOCM.BOOK_TYPE_CODE,"
						+ " EOCM.BOOK_TYPE_NAME,"
						+ " EOCM.COMPANY FROM_COMPANY_NAME,"
						+ " AMD2.DEPT_NAME USER_DEPT_NAME,"
						+ " AATH.TO_ORGANIZATION_ID,"
						+" CASE WHEN AATH.TRANSFER_TYPE='BTW_COMP' THEN EOCM2.COMPANY ELSE EOCM.COMPANY END AS  TO_COMPANY_NAME ,"
						+ " (SELECT"
						+ " SGM.GROUP_ID"
						+ " FROM"
						+ " SF_GROUP_MATCH SGM"
						+ " WHERE"
						+ " CONVERT(VARCHAR,SGM.DEPT_CODE )= CONVERT(VARCHAR,AATH.TO_DEPT)) TO_GROUP,"
						+ "  CASE WHEN NEW_COUNT IS NULL THEN 'N' WHEN CONVERT(VARCHAR,NEW_COUNT)='0'THEN 'N' ELSE 'Y' END AS PRODUCED_NEW_BARCODE "
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " ETS_OU_CITY_MAP         EOCM,"
						+ " AMS_MIS_DEPT            AMD,"
						+ " SF_GROUP                SG,"
						+ " SF_USER                 SU,"
						+ " AMS_MIS_EMPLOYEE        AME,"
						+ " AMS_MIS_DEPT            AMD2,"
						+ " ETS_OU_CITY_MAP         EOCM2,"
						+ " (SELECT"
						+ " AATL.TRANS_ID,"
						+ " COUNT(1) NEW_COUNT"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_LINE AATL"
						+ " WHERE"
						+ "  " + SyBaseSQLUtil.isNotNull("AATL.NEW_BARCODE") + " "
						+ " GROUP BY"
						+ " AATL.TRANS_ID) TMP_V"
						+ " WHERE"
						+ " CONVERT(VARCHAR,AATH.FROM_ORGANIZATION_ID) *= CONVERT(VARCHAR,EOCM.ORGANIZATION_ID)"
						+ " AND CONVERT(VARCHAR,AATH.FROM_DEPT) *= CONVERT(VARCHAR,AMD.DEPT_CODE)"
						+ " AND CONVERT(VARCHAR,AATH.FROM_GROUP) *= CONVERT(VARCHAR,SG.GROUP_ID)"
						+ " AND CONVERT(VARCHAR,AATH.CREATED_BY) *= CONVERT(VARCHAR,SU.USER_ID)"
						+ " AND CONVERT(VARCHAR,SU.EMPLOYEE_NUMBER) *= CONVERT(VARCHAR,AME.EMPLOYEE_NUMBER) "
						+ " AND CONVERT(VARCHAR,AME.DEPT_CODE) *= CONVERT(VARCHAR,AMD2.DEPT_CODE)"
						+ " AND CONVERT(VARCHAR,AATH.TO_ORGANIZATION_ID) *= CONVERT(VARCHAR,EOCM2.ORGANIZATION_ID)"
						+ " AND CONVERT(VARCHAR,AATH.TRANS_ID) *= CONVERT(VARCHAR,TMP_V.TRANS_ID)"
						+ " AND AATH.TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
//		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
//		AmsAssetsTransHeaderModel modelProducer = new AmsAssetsTransHeaderModel(userAccount, dto);
//		return modelProducer.getPrimaryKeyDataModel();
	}

	/**
	 * 功能：获取判断该单据是否能够审批
	 * @return SQLModel
	 */
	public SQLModel getCanApproveModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH"
						+ " WHERE"
						+ " AATH.TRANS_ID = ?"
						+ " AND (AATH.TRANS_STATUS = ? OR AATH.TRANS_STATUS = ?)";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(AssetsDictConstant.IN_PROCESS);
		sqlArgs.add(AssetsDictConstant.REJECTED);
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
		String sqlStr = "";
		List sqlArgs = new ArrayList();
		sqlStr = "UPDATE" //调拨单的TRANS_DATE在资产确认时产生
				 + " AMS_ASSETS_TRANS_HEADER "
				 + " SET"
				 + " TRANS_STATUS = ?,"
				 + " APPROVED_DATE = GETDATE(),"
				 + " APPROVED_BY = ?,"
				 + " LAST_UPDATE_DATE = GETDATE(),"
				 + "LAST_UPDATE_BY = ?"
				 + " WHERE"
				 + " TRANS_ID = ?";
		if (!transType.equals(AssetsDictConstant.ASS_RED) && dto.isFlow2End()) { //报废单、处置单的TRANS_DATE在单据审批完成时生产
			sqlStr = "UPDATE"
					 + " AMS_ASSETS_TRANS_HEADER AATH"
					 + " SET"
					 + " TRANS_STATUS = ?,"
					 + " APPROVED_DATE = GETDATE(),"
					 + " APPROVED_BY = ?,"
					 + " LAST_UPDATE_DATE = GETDATE(),"
					 + " TRANS_DATE = GETDATE(),"
					 + " LAST_UPDATE_BY = ?"
					 + " WHERE"
					 + " TRANS_ID = ?";
		}
		sqlArgs.add(dto.getTransStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
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
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " AATH.TRANS_ID = AATL.TRANS_ID"
						+ " AND AATL.BARCODE = ETS_ITEM_INFO.BARCODE"
                        + " AND ((AATL.REMARK <> '不允许报废') OR (AATL.REMARK " + SyBaseSQLUtil.isNullNoParam() + " ))"
                        + " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STAY_DISCARDED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransType());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取资产处置SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsClearModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.ITEM_STATUS     = ?,"
						+ " EII.LAST_UPDATE_DATE = GETDATE(),"
						+ " EII.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " AATH.TRANS_ID = AATL.TRANS_ID"
						+ " AND AATL.BARCODE = EII.BARCODE"
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_CLEARED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransType());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取资产闲置SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsFreeModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.ITEM_STATUS     = ?,"
						+ " EII.LAST_UPDATE_DATE = GETDATE(),"
						+ " EII.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " AATH.TRANS_ID = AATL.TRANS_ID"
						+ " AND AATL.BARCODE = EII.BARCODE"
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransType());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：获取资产闲置SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsSellModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.ITEM_STATUS     = ?,"
						+ " EII.LAST_UPDATE_DATE = GETDATE(),"
						+ " EII.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " AATH.TRANS_ID = AATL.TRANS_ID"
						+ " AND AATL.BARCODE = EII.BARCODE"
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_SELL);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransType());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：获取资产闲置SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsRentModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.ITEM_STATUS     = ?,"
						+ " EII.LAST_UPDATE_DATE = GETDATE(),"
						+ " EII.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " AATH.TRANS_ID = AATL.TRANS_ID"
						+ " AND AATL.BARCODE = EII.BARCODE"
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_RENT);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransType());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：获取资产闲置SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsDonaModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.ITEM_STATUS     = ?,"
						+ " EII.LAST_UPDATE_DATE = GETDATE(),"
						+ " EII.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " AATH.TRANS_ID = AATL.TRANS_ID"
						+ " AND AATL.BARCODE = EII.BARCODE"
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_DONATION);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransType());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	

	/**
	 * 功能：获取资产减值SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsSubModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO EII"
						+ " SET"
						+ " EII.ITEM_STATUS     = ?,"
						+ " EII.LAST_UPDATE_DATE = GETDATE(),"
						+ " EII.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " AATH.TRANS_ID = AATL.TRANS_ID"
						+ " AND AATL.BARCODE = EII.BARCODE"
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND AATH.TRANS_ID = ?)";
		sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_SUB);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransType());
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
						+ " AMS_ASSETS_TRANS_LINE"
						+ " SET"
						+ " LINE_STATUS     = ?"
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add(AssetsDictConstant.APPROVED);
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：获取审批流程中当前节点的审批角色名称
	 * @return SQLModel
	 */
	public SQLModel getCurrApproveRoleModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT STD.ROLE_NAME FROM SF_TASK STD WHERE CONVERT(VARCHAR,STD.TASK_ID) = CONVERT(VARCHAR,?)";
		sqlArgs.add(dto.getCurrTaskId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：获取附件张数
	 * @return SQLModel
	 */
	public SQLModel getAccessSheet() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT COUNT(*) ACCESS_SHEET FROM AMS_ASSETS_ATTACH AAA WHERE AAA.ORDER_PK_NAME = ?";
		sqlArgs.add(dto.getTransId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：更新不能报废的资产，并更新行表REMARK为“不允许报废”；
	 * @return SQLModel
	 */
//	public SQLModel updateTransLineRemark(String aggBarcodes) {
//		SQLModel sqlModel = new SQLModel();
//		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
//		List sqlArgs = new ArrayList();
//        String sqlStr = "UPDATE AMS_ASSETS_TRANS_LINE AATL SET AATL.REMARK = '不允许报废' WHERE AATL.TRANS_ID = ? AND AATL.BARCODE NOT IN ?";
//		sqlArgs.add(dto.getTransId());
//        sqlArgs.add(aggBarcodes);
//		sqlModel.setSqlStr(sqlStr);
//		sqlModel.setArgs(sqlArgs);
//		return sqlModel;
//	}
    public SQLModel updateTransLineRemark() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE " +
                "AMS_ASSETS_TRANS_LINE " +
                "SET " +
                "REMARK = '不允许报废' " +
                "WHERE TRANS_ID = ? " +
                "AND BARCODE NOT IN "+aggBarcodes;
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：特殊处理，先清除选中资产REMARK
	 * @return SQLModel
	 */
	public SQLModel deleteTransLineRemark(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE " +
                "AMS_ASSETS_TRANS_LINE " +
                "SET " +
                "REMARK = NULL " +
                " WHERE BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
