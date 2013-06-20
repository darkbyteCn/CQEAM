package com.sino.ams.newasset.allocation.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-3-31
 * Time: 12:54:57
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsAllocationApproveModel extends AMSSQLProducer {
    private String aggBarcodes = "";

	public AmsAssetsAllocationApproveModel(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter) {
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
        String sqlStr = "SELECT AATH.TRANS_ID,\n" +
                "       AATH.TRANS_NO,\n" +
                "       AATH.FROM_DEPT,\n" +
                "       AATH.TO_DEPT,\n" +
                "       AATH.TRANS_TYPE,\n" +
                "       AATH.TRANSFER_TYPE,\n" +
                "       AATH.TRANS_STATUS,\n" +
                "       AATH.TRANS_DATE,\n" +
                "       AATH.CREATION_DATE,\n" +
                "       AATH.CREATED_BY,\n" +
                "       AATH.LAST_UPDATE_DATE,\n" +
                "       AATH.LAST_UPDATE_BY,\n" +
                "       AATH.CANCELED_DATE,\n" +
                "       AATH.CANCELED_REASON,\n" +
                "       AATH.CREATED_REASON,\n" +
                "       AATH.APPROVED_DATE,\n" +
                "       AATH.FROM_ORGANIZATION_ID,\n" +
                "       AATH.FROM_GROUP,\n" +
                "       AATH.FA_CONTENT_CODE,\n" +
                "       AATH.LOSSES_NAME,\n" +
                "       AATH.LOSSES_DATE,\n" +
                "       AATH.IS_THRED,\n" +
                "       AMD.DEPT_NAME FROM_DEPT_NAME,\n" +
                "       dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,\n" +
                "       dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,\n" +
                "       dbo.APP_GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FA_CONTENT_CODE') FA_CONTENT_NAME,\n" +
                "       SU.USERNAME CREATED,\n" +
                "       SU.EMAIL,\n" +
                "       SU.MOBILE_PHONE PHONE_NUMBER,\n" +
                "       SG.GROUP_NAME FROM_GROUP_NAME,\n" +
                "       0 GROUP_PROP,\n" +
                "       EOCM.BOOK_TYPE_CODE,\n" +
                "       EOCM.BOOK_TYPE_NAME,\n" +
                "       EOCM.COMPANY FROM_COMPANY_NAME,\n" +
                //"       AMD2.DEPT_NAME USER_DEPT_NAME,\n" +
                "       AMD.DEPT_NAME USER_DEPT_NAME,\n" +
                "       AATH.TO_ORGANIZATION_ID,\n" +
                "       EOCM2.COMPANY TO_COMPANY_NAME,\n" +
                "       AATH.TO_GROUP,\n" +
                "	    AATH.EMERGENT_LEVEL,\n" +
                "	    dbo.APP_GET_FLEX_VALUE(AATH.EMERGENT_LEVEL, 'EMERGENT_LEVEL') EMERGENT_LEVEL_NAME\n" +
                "  FROM AMS_ASSETS_TRANS_HEADER AATH,\n" +
                "       ETS_OU_CITY_MAP         EOCM,\n" +
                "       AMS_MIS_DEPT            AMD,\n" +
                "       SF_GROUP                SG,\n" +
                "       SF_USER                 SU,\n" +
                "       AMS_MIS_EMPLOYEE        AME,\n" +
                //"       AMS_MIS_DEPT            AMD2,\n" +
                "       ETS_OU_CITY_MAP         EOCM2\n" +
                " WHERE AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND AATH.FROM_DEPT *= AMD.DEPT_CODE\n" +
                "   AND AATH.FROM_GROUP *= SG.GROUP_ID\n" +
                "   AND AATH.CREATED_BY = SU.USER_ID\n" +
                "   AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
                //"   AND AME.DEPT_CODE *= AMD2.DEPT_CODE\n" +
                "   AND AATH.TO_ORGANIZATION_ID *= EOCM2.ORGANIZATION_ID\n" +
                "   AND TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
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
		String sqlStr = "";
		List sqlArgs = new ArrayList();
		sqlStr = "UPDATE" //调拨单的TRANS_DATE在资产确认时产生
				 + " AMS_ASSETS_TRANS_HEADER"
				 + " SET"
				 + " TRANS_STATUS = ?,"
				 + " APPROVED_DATE = GETDATE(),"
				 + " APPROVED_BY = ?,"
				 + " LAST_UPDATE_DATE = GETDATE(),"
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
	 * 功能：获取资产报废SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsDiscardModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO"
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
						+ " AND AATL.BARCODE = EII.BARCODE"
                        + " AND ((AATL.REMARK <> '不允许报废') OR (AATL.REMARK " + SyBaseSQLUtil.isNull() + " ))"
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
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " ITEM_STATUS     = ?,"
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
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " ITEM_STATUS     = ?,"
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
						+ " ITEM_STATUS     = ?,"
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
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " ITEM_STATUS     = ?,"
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
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " ITEM_STATUS     = ?,"
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
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " ITEM_STATUS     = ?,"
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

    public SQLModel updateLineStatusModel(String status) {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " SET"
						+ " LINE_STATUS     = ?"
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add(status);
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel deleteReserveModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM AMS_ASSETS_RESERVED WHERE TRANS_ID = ?";
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

    public SQLModel updateTransLineRemark() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ASSETS_TRANS_LINE SET REMARK = '不允许报废' WHERE TRANS_ID = ? AND BARCODE NOT IN "+aggBarcodes;
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    
    /**
     * 审批结束后需要把调出方的项目等信息转到调入方资产的一个备注2中
     * @return
     */
    public SQLModel updateAssetsRemark2() {
    	SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append( " UPDATE	\n " );
    	sb.append( " 	ETS_ITEM_INFO \n " );
    	sb.append( " SET \n " );
    	sb.append( " 	REMARK2 = (	SELECT \n " );
    	sb.append( " 					AL.BARCODE || EPPA.NAME  \n " );
    	sb.append( " 				FROM \n " );
    	sb.append( " 					AMS_ASSETS_TRANS_LINE AL, \n " );
    	sb.append( " 					ETS_ITEM_INFO EII, \n " );
    	sb.append( " 					ETS_PA_PROJECTS_ALL EPPA  \n " );
    	sb.append( " 				WHERE \n " );
    	sb.append( " 					AL.TRANS_ID = ?  \n " );
    	sb.append( " 					AND AL.BARCODE = EII.BARCODE  \n " );
    	sb.append( " 					AND EII.PROJECTID *= EPPA.PROJECT_ID  \n " );
    	sb.append( " 					AND AL.NEW_BARCODE = ETS_ITEM_INFO.BARCODE  \n " );
    	sb.append( " 	) \n " );
    	sb.append( " WHERE \n " );
    	sb.append( " 	EXISTS (SELECT \n " );
    	sb.append( " 				NULL  \n " );
    	sb.append( " 			FROM \n " );
    	sb.append( " 				AMS_ASSETS_TRANS_LINE AL  \n " );
    	sb.append( " 			WHERE \n " );
    	sb.append( " 				AL.TRANS_ID =　?  \n " );
    	sb.append( " 				AND AL.NEW_BARCODE = ETS_ITEM_INFO.BARCODE) \n " );
    	
    	sqlArgs.add(dto.getTransId());
    	sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr( sb.toString() );
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }

    /**
	 * 功能：特殊处理，先清除选中资产REMARK
	 * @return SQLModel
	 */
	public SQLModel deleteTransLineRemark(String barcode) {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ASSETS_TRANS_LINE SET REMARK = NULL WHERE BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

    //判断是否属于财务部
    public SQLModel getIsFinanceGroupModel(int fromGroup) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT *\n" +
                "  FROM SF_GROUP      SG,\n" +
                "       SF_USER       SU,\n" +
                "       SF_USER_RIGHT SUR\n" +
                " WHERE SG.GROUP_ID = SUR.GROUP_ID\n" +
                "   AND SU.USER_ID = SUR.USER_ID\n" +
                "   AND SG.GROUP_NAME LIKE '%财务部'\n" +
                "   AND SU.USER_ID = ?\n" +
                "   AND SG.GROUP_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(fromGroup);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    //判断是否属于实物部门
    public SQLModel getIsSpecialGroupModel(int fromGroup) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT *\n" +
                "  FROM SF_GROUP      SG,\n" +
                "       SF_USER       SU,\n" +
                "       SF_USER_RIGHT SUR\n" +
                " WHERE SG.GROUP_ID = SUR.GROUP_ID\n" +
                "   AND SU.USER_ID = SUR.USER_ID\n" +
                "   AND SG.SPECIALITY_DEPT = 'Y'\n" +
                "   AND SU.USER_ID = ?\n" +
                "   AND SG.GROUP_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(fromGroup);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
