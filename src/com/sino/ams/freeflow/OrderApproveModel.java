package com.sino.ams.freeflow;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: OrderApproveModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransHeaderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author
 * jiaozhiwei
 */

public class OrderApproveModel extends AMSSQLProducer {
      private String aggBarcodes = "";
    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     */
    public OrderApproveModel(SfUserDTO userAccount, FreeFlowDTO dtoParameter) {
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
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
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
                        + " (SELECT"
                        + " GROUP_ID"
                        + " FROM"
                        + " SF_GROUP SGP"
                        + " WHERE"
                        + " SGP.GROUP_ID = SG.GROUP_PID) GROUP_PID,"
                        + " AMD.DEPT_NAME FROM_DEPT_NAME,"
                        + " AATH.FA_CONTENT_CODE,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,"
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
                        + " CASE WHEN AATH.TRANSFER_TYPE='BTW_COMP' THEN EOCM2.COMPANY ELSE EOCM.COMPANY END TO_COMPANY_NAME,"
                        + " (SELECT"
                        + " SGM.GROUP_ID"
                        + " FROM"
                        + " SF_GROUP_MATCH SGM"
                        + " WHERE"
                        + " SGM.DEPT_CODE = AATH.TO_DEPT) TO_GROUP,"
                        + " CASE WHEN NEW_COUNT IS NULL OR NEW_COUNT='' OR NEW_COUNT='0' THEN 'N' ELSE 'Y' END PRODUCED_NEW_BARCODE "
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
                        + " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                        + " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
                        + " AND AATH.FROM_GROUP = SG.GROUP_ID"
                        + " AND AATH.CREATED_BY = SU.USER_ID"
                        + " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
                        + " AND AME.DEPT_CODE *= AMD2.DEPT_CODE"
                        + " AND AATH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID"
                        + " AND AATH.TRANS_ID *= TMP_V.TRANS_ID"
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
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
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
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
        String transType = dto.getTransType();
        String sqlStr = "";
        List sqlArgs = new ArrayList();
        sqlStr = "UPDATE" //调拨单的TRANS_DATE在资产确认时产生
                 + " AMS_ASSETS_TRANS_HEADER AATH"
                 + " SET"
                 + " AATH.TRANS_STATUS = ?,"
                 + " AATH.APPROVED_DATE = GETDATE(),"
                 + " AATH.APPROVED_BY = ?,"
                 + " AATH.LAST_UPDATE_DATE = GETDATE(),"
                 + " AATH.LAST_UPDATE_BY = ?"
                 + " WHERE"
                 + " AATH.TRANS_ID = ?";
        if (!transType.equals(AssetsDictConstant.ASS_RED) && dto.isFlow2End()) { //报废单、处置单的TRANS_DATE在单据审批完成时生产
            sqlStr = "UPDATE"
                     + " AMS_ASSETS_TRANS_HEADER AATH"
                     + " SET"
                     + " AATH.TRANS_STATUS = ?,"
                     + " AATH.APPROVED_DATE = GETDATE(),"
                     + " AATH.APPROVED_BY = ?,"
                     + " AATH.LAST_UPDATE_DATE = GETDATE(),"
                     + " AATH.TRANS_DATE = GETDATE(),"
                     + " AATH.LAST_UPDATE_BY = ?"
                     + " WHERE"
                     + " AATH.TRANS_ID = ?";
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
       * 功能：获取审批流程中当前节点的审批角色名称
       * @return SQLModel
       */
      public SQLModel getCurrApproveRoleModel() {
          SQLModel sqlModel = new SQLModel();
          FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
          List sqlArgs = new ArrayList();
          String sqlStr = "SELECT"
                          + " SR.ROLE_NAME"
                          + " FROM"
                          + " SF_TASK_DEFINE STD,"
                          + " SF_ROLE        SR"
                          + " WHERE"
                          + " STD.ROLE_ID = SR.ROLE_ID"
                          + " AND STD.TASK_ID = ?";
          sqlArgs.add(dto.getCurrTaskId());

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
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                        + " ETS_ITEM_INFO EII"
                        + " SET"
                        + " EII.ITEM_STATUS      = ?,"
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
        sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_DISCARDED);
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
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
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
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
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
    public SQLModel getAssetsShareModel() {
        SQLModel sqlModel = new SQLModel();
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE"
                        + " ETS_ITEM_INFO EII"
                        + " SET"
//                        + " EII.ITEM_STATUS     = ?,"
                        + "	EII.IS_SHARE = 'Y',"
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
//        sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_SHARE);
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
        FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
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
		FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE AATL"
						+ " SET"
						+ " AATL.LINE_STATUS     = ?"
						+ " WHERE"
						+ " AATL.TRANS_ID = ?";
		sqlArgs.add(AssetsDictConstant.APPROVED);

        sqlArgs.add(dto.getTransId());
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
		FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT COUNT(*) ACCESS_SHEET FROM AMS_ASSETS_ATTACH AAA WHERE AAA.ORDER_PK_NAME = ?";
		sqlArgs.add(dto.getTransId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel updateTransLineRemark() {
           SQLModel sqlModel = new SQLModel();
           FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
           List sqlArgs = new ArrayList();
           String sqlStr = "UPDATE " +
                   "AMS_ASSETS_TRANS_LINE " +
                   "SET " +
                   "REMARK = '不允许报废' " +
                   "WHERE " +
                   "TRANS_ID = ? " +
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
		FreeFlowDTO dto = (FreeFlowDTO) dtoParameter;
		List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE " +
                "AMS_ASSETS_TRANS_LINE" +
                " SET REMARK = NULL  " +
                "WHERE " +
                "BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

}
