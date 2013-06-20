package com.sino.ams.newasset.model;
import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-7-8
 * Time: 上午6:47
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemAllocationHeaderModel extends AMSSQLProducer {
	private SfUserDTO user = null;

	public AmsItemAllocationHeaderModel(SfUserDTO userAccount,
			AmsAssetsTransHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.user = (SfUserDTO) userAccount;
	}
	
	/***
	 * 功能：将当前用户下的资产条码删除：IMP_BARCODE
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SQLModel getImpBarcodeDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		//AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "DELETE FROM" + " IMP_BARCODE" + " WHERE"
		+ " USER_ID = ?";
		sqlArgs.add(user.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/***
	 * 功能：查询当前用户下的BARCODE
	 */
	public SQLModel queryBarcode(String barcode) {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "SELECT COUNT(1) FROM  ETS_ITEM_INFO  WHERE ORGANIZATION_ID=?  and BARCODE=?";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(barcode);
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/***
	 * 功能：将用户导入Excel中的资产条码插入到IMP_BARCODE表中
	 */
	public SQLModel getDataInsertModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
					+ " IMP_BARCODE("
					+ " BARCODE,"
					+ " USER_ID"
					+ ") VALUES ("
					+ " ?, ?)";
			sqlArgs.add(barcode);
			sqlArgs.add(user.getUserId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
					+ " AMS_ASSETS_TRANS_HEADER("
					+ " TRANS_ID,"
					+ " TRANS_NO,"
					+ " TRANS_TYPE,"
					+ " TRANSFER_TYPE,"
					+ " TRANS_STATUS,"
					+ " FROM_ORGANIZATION_ID,"
					+ " TO_ORGANIZATION_ID,"
					+ " FROM_DEPT,"
					+ " FROM_GROUP,"
					+ " TO_DEPT,"
					+ " FROM_OBJECT_NO,"
					+ " TO_OBJECT_NO,"
					+ " FROM_PERSON,"
					+ " TO_PERSON,"
					+ " CREATED_REASON,"
					+ " CREATION_DATE,"
					+ " CREATED_BY,"
					+ " RECEIVED_USER,"
					+ " TRANS_DATE,"
					+ " FA_CONTENT_CODE,"
					+ " LOSSES_NAME,"
					+ " LOSSES_DATE,"
					+ " IS_THRED,"
					+ " TO_GROUP,"
					+ "	EMERGENT_LEVEL"
					+ ") VALUES ("
					+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getTransferType());
			sqlArgs.add(dto.getTransStatus());
			sqlArgs.add(dto.getFromOrganizationId());
			sqlArgs.add(dto.getToOrganizationId());
			sqlArgs.add(dto.getFromDept());
			sqlArgs.add(dto.getFromGroup());
			sqlArgs.add(dto.getToDept());
			sqlArgs.add(dto.getFromObjectNo());
			sqlArgs.add(dto.getToObjectNo());
			sqlArgs.add(dto.getFromPerson());
			sqlArgs.add(dto.getToPerson());
			sqlArgs.add(dto.getCreatedReason());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getReceivedUser());
			sqlArgs.add(dto.getTransDate());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getLossesName());
			sqlArgs.add(dto.getLossesDate());
			sqlArgs.add(dto.getThred());
			sqlArgs.add(dto.getToGroup());
			sqlArgs.add(dto.getEmergentLevel());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "UPDATE AMS_ASSETS_TRANS_HEADER\n" +
                    "   SET TRANS_NO           = ?,\n" +
                    "       TRANS_STATUS       = ?,\n" +
                    "       TO_ORGANIZATION_ID = ?,\n" +
                    "       FROM_DEPT          = ?,\n" +
                    "       TO_DEPT            = ?,\n" +
                    "       FROM_OBJECT_NO     = ?,\n" +
                    "       TO_OBJECT_NO       = ?,\n" +
                    "       FROM_PERSON        = ?,\n" +
                    "       TO_PERSON          = ?,\n" +
                    "       CREATED_REASON     = ?,\n" +
                    "       LAST_UPDATE_DATE   = GETDATE(),\n" +
                    "       LAST_UPDATE_BY     = ?,\n" +
                    "       RECEIVED_USER      = ?,\n" +
                    "       FA_CONTENT_CODE    = ?,\n" +
                    "       TRANS_DATE         = ?,\n" +
                    "       LOSSES_DATE        = ?,\n" +
                    "       LOSSES_NAME        = ?,\n" +
                    "       TO_GROUP           = ?,\n" +
                    "       EMERGENT_LEVEL     = ?\n" +
                    " WHERE TRANS_ID = ?";

			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransStatus());
			sqlArgs.add(dto.getToOrganizationId());
			sqlArgs.add(dto.getFromDept());
			sqlArgs.add(dto.getToDept());
			sqlArgs.add(dto.getFromObjectNo());
			sqlArgs.add(dto.getToObjectNo());
			sqlArgs.add(dto.getFromPerson());
			sqlArgs.add(dto.getToPerson());
			sqlArgs.add(dto.getCreatedReason());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getReceivedUser());
			sqlArgs.add(dto.getFaContentCode());
			sqlArgs.add(dto.getTransDate());
			sqlArgs.add(dto.getLossesDate());
			sqlArgs.add(dto.getLossesName());
			sqlArgs.add(dto.getToGroup());
			sqlArgs.add(dto.getEmergentLevel());
			sqlArgs.add(dto.getTransId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "DELETE FROM" + " AMS_ASSETS_TRANS_HEADER" + " WHERE"
				+ " TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
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
                "       dbo.APP_GET_FLEX_VALUE(AATH.FA_CONTENT_CODE, 'FINANCE_PROP') FA_CONTENT_NAME,\n" +
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
                "		CASE AATH.EMERGENT_LEVEL WHEN '1' THEN '急' WHEN '2' THEN '加急' WHEN '3' THEN '特急' ELSE '正常' END EMERGENT_LEVEL\n" +
                //"       dbo.APP_GET_FLEX_VALUE(AATH.EMERGENT_LEVEL, 'EMERGENT_LEVEL') EMERGENT_LEVEL_NAME\n" +
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
                "   AND AATH.FROM_GROUP *= SG.GROUP_ID\n" +//新流程不需FROM_GROUP 2011.4.13
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
	 * 功能：框架自动生成资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
					+ " AATH.TRANS_ID,"
					+ " AATH.TRANS_NO,"
					+ " AATH.TRANS_TYPE,"
					+ " AATH.TRANSFER_TYPE,"
					+ " AATH.TRANS_STATUS,"
					+ " AATH.FROM_ORGANIZATION_ID,"
					+ " AATH.LOSSES_NAME,"
					+ " AATH.LOSSES_DATE,"
					+ " EOCM.COMPANY,"
					+ " dbo.ISNULL(AMD.DEPT_NAME, EOCM.COMPANY) FROM_DEPT_NAME,"
					+ " AATH.RECEIVED_USER,"
					+ " AATH.CREATION_DATE,"
					+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_STATUS, 'ORDER_STATUS') TRANS_STATUS_DESC,"
					+ " dbo.APP_GET_FLEX_VALUE(AATH.TRANS_TYPE, 'ORDER_TYPE_ASSETS') TRANS_TYPE_VALUE,"
					+ " SU.USERNAME CREATED"
					+ " FROM"
					+ " AMS_ASSETS_TRANS_HEADER AATH,"
					+ " AMS_MIS_DEPT       AMD,"
					+ " ETS_OU_CITY_MAP    EOCM,"
					+ " SF_USER            SU"
					+ " WHERE"
					+ " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					+ " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
					+ " AND AATH.CREATED_BY = SU.USER_ID"
					+ " AND AATH.TRANS_TYPE = ?"
					+ " AND AATH.TRANS_STATUS = ?"
					+ " AND AATH.CREATED_BY = ?"
					+ " AND (? IS NULL OR AATH.TRANS_NO LIKE ?)"
					+ " AND AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE)"
					+ " AND AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE)"
					+ " AND (? IS NULL OR AATH.TRANSFER_TYPE = dbo.ISNULL(?, AATH.TRANSFER_TYPE))"
					+ " ORDER BY" + " AATH.TRANSFER_TYPE," + " AATH.TRANS_NO";
			sqlArgs.add(dto.getTransType());
			sqlArgs.add(dto.getTransStatus());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getTransferType());
			sqlArgs.add(dto.getTransferType());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：撤销单据
	 * @return SQLModel
	 */
	public SQLModel getOrderCancelModel() {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "UPDATE"
                + " AMS_ASSETS_TRANS_HEADER"
                + " SET"
				+ " TRANS_STATUS = ?,"
                + " CANCELED_DATE = GETDATE(),"
				+ " CANCELED_REASON = ?,"
				+ " LAST_UPDATE_DATE = GETDATE(),"
				+ " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " TRANS_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(AssetsDictConstant.CANCELED);
		sqlArgs.add(AssetsDictConstant.CANCAL_REASON);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：
	 * @return SQLModel
	 */
	public SQLModel getOrderModel() { //导出模板
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT \n" + " AATH.TRANS_ID,\n"
				+ " ''   MB1,''   MB2,''   MB3,''   MB4,\n"
				+ " ''   MB5,''   MB6,''   MB7,''   MB8,\n"
				+ " ''   MB9,''   MB10,''   MB11,''   MB12,\n"
				+ " ''   MB13,''   MB14,''   MB15,''   MB16,\n"
				+ " ''   MB17,''   MB18,''   MB19,''   MB20,\n"
				+ " ''   MB21,''   MB22,''   MB23,''   MB24,\n"
				+ " ''   MB25,''   MB26,''   MB27 \n" + " FROM \n"
				+ " AMS_ASSETS_TRANS_HEADER AATH \n";
		List sqlArgs = new ArrayList();
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

     public SQLModel getThredDeptModel(String fDept, String tDept) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT GROUP_THRED\n" +
                "  FROM SF_GROUP         SG,\n" +
                "       AMS_MIS_DEPT     AMD,\n" +
                "       SINO_GROUP_MATCH SGM\n" +
                " WHERE SG.GROUP_ID = SGM.GROUP_ID\n" +
                "   AND SGM.DEPT_ID = AMD.DEPT_CODE\n" +
                "   AND (AMD.DEPT_CODE = ? OR AMD.DEPT_CODE = ?)";
        List sqlArgs = new ArrayList();
		sqlArgs.add(fDept);
		sqlArgs.add(tDept);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：查找用户所对应的PID，流程用
	 * @return SQLModel
	 */
	public SQLModel getIsGroupFlowIdModel(String fromGroup) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = " SELECT DISTINCT (SG.P_FLOW_ID)\n"
				+ " FROM SF_USER SU," + " SF_GROUP SG,"
				+ " SF_USER_RIGHT SUR\n" + "WHERE SU.USER_ID = SUR.USER_ID "
				+ " AND SUR.GROUP_ID = SG.GROUP_ID "
				+ " AND SG.P_FLOW_ID IN(2185, 2223, 2774)"
				+ " AND SG.GROUP_ID = " + fromGroup + " AND SU.USER_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getQueryBarcodeExcelModel(String excel,
			AmsAssetsTransHeaderDTO dto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String transferType = dto.getTransferType();
		boolean isDeptMgr = user.isDptAssetsManager();
		boolean isCompMgr = user.isComAssetsManager();
		String mtlMgrProps = user.getMtlMgrProps();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT /*+rule */"
					+ " AAAV.BARCODE,"
					+ " AAAV.ASSET_NUMBER,"
					+ " AAAV.ASSET_ID,"
					+ " AAAV.ITEM_NAME  ASSETS_DESCRIPTION,"
					+ " AAAV.ITEM_SPEC MODEL_NUMBER,"
					+ " AAAV.ITEM_NAME,"
					+ " AAAV.ITEM_SPEC,"
					+ " AAAV.VENDOR_NAME,"
					+ " AAAV.UNIT_OF_MEASURE,"
					+ " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
					+ " AAAV.MANUFACTURER_NAME,"
					+ " AAAV.FA_CATEGORY_CODE OLD_FA_CATEGORY_CODE,"
					+ " AAAV.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
					+ " AAAV.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
					+ " AAAV.WORKORDER_OBJECT_NO OLD_LOCATION,"
					+ " AAAV.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,"
					+ " AAAV.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
					+ " AAAV.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,"
					+ " AAAV.RESPONSIBILITY_USER_NAME OLD_RESPONSIBILITY_USER_NAME,";
			if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
				sqlStr = sqlStr
				+ " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER ELSE '' END AS RESPONSIBILITY_USER,"
				+ " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER_NAME ELSE '' END AS RESPONSIBILITY_USER_NAME,"
						+ " AAAV.ORGANIZATION_ID TO_ORGANIZATION_ID,";
				sqlArgs.add(user.getEmployeeId());
				sqlArgs.add(user.getEmployeeId());
			}
			sqlStr = sqlStr
					+ " AAAV.DATE_PLACED_IN_SERVICE,"
					+ " AAAV.COST,"
					+ " AAAV.DEPRN_COST,"
					+ " AAAV.COST RETIREMENT_COST,"
					+ " AAAV.DEPRECIATION_ACCOUNT OLD_DEPRECIATION_ACCOUNT,"
					+ " AAAV.DEPRECIATION,"
					//+ " AMS_PUB_PKG.GET_FLEX_VALUE(AAAV.ASSETS_STATUS,'ASSETS_STATUS') ASSETS_STATUS_DES ,"
					+ " dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,AAAV.ASSETS_STATUS),'ASSETS_STATUS') ASSETS_STATUS_DES,"
					+ " AAAV.IMPAIR_RESERVE" + " FROM"
					+ " TD_ASSETS_ADDRESS_V AAAV";
			
			//Excel
			if (!excel.equals("")) {
				sqlStr +=",IMP_BARCODE IMP";
			}
			
			sqlStr=sqlStr
					+" WHERE "
					+" AAAV.ORGANIZATION_ID = ?";
			sqlArgs.add(user.getOrganizationId());
			
			//Excel
			if (!excel.equals("")) {
				sqlStr +=" AND IMP.USER_ID= ?"
					   +"  AND IMP.BARCODE=AAAV.BARCODE";
			}
			sqlArgs.add(user.getUserId());
			
			if (!StrUtil.isEmpty(dto.getFromDept())) { //部门限制
				sqlStr += " AND AAAV.DEPT_CODE = ?  ";
				sqlArgs.add(dto.getFromDept());
			} else { //根据操作人员权限限制
				if (!isCompMgr && mtlMgrProps.equals("") && isDeptMgr) {
					DTOSet depts = user.getPriviDeptCodes();
					AmsMisDeptDTO dept = null;
					String deptCodes = "(";
					for (int i = 0; i < depts.getSize(); i++) {
						dept = (AmsMisDeptDTO) depts.getDTO(i);
						deptCodes += "'" + dept.getDeptCode() + "', ";
					}
					deptCodes += "'')";
					sqlStr += " AND AAAV.DEPT_CODE IN " + deptCodes;
				}
			}
			if (!user.isDptAssetsManager() && !user.isComAssetsManager()
					&& mtlMgrProps.equals("")) { //责任人限制
				if (!dto.getTransferType().equals("BTW_COMP")) {
					sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
					sqlArgs.add(user.getEmployeeId());
				}
			}
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { //调拨
				sqlStr += " AND (AAAV.ITEM_STATUS IS NULL OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //报废
				sqlStr += " AND (AAAV.ITEM_STATUS IS NULL OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //报废前提：正常或者闲置
				sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置前提：已报废或者闲置
				sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值前提：正常或者闲置
				sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			}


			sqlStr = sqlStr + " AND AAAV.ASSET_ID IS NOT NULL"
                    + " AND NOT EXISTS ("
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " AMS_ASSETS_RESERVED AAR"
					+ " WHERE"
					+ " AAR.BARCODE = AAAV.BARCODE)";

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}else {
			String sqlStr = "SELECT /*+rule */"
					+ " AAAV.BARCODE,"
					+ " AAAV.ASSET_NUMBER,"
					+ " AAAV.ASSET_ID,"
					+ " AAAV.ITEM_NAME ASSETS_DESCRIPTION,"
					+ " AAAV.ITEM_SPEC MODEL_NUMBER,"
					+ " AAAV.ITEM_NAME,"
					+ " AAAV.ITEM_SPEC,"
					+ " AAAV.VENDOR_NAME,"
					+ " AAAV.UNIT_OF_MEASURE,"
					+ " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
					+ " AAAV.MANUFACTURER_NAME,"
					+ " AAAV.FA_CATEGORY_CODE OLD_FA_CATEGORY_CODE,"
					+ " AAAV.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
					+ " AAAV.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
					+ " AAAV.WORKORDER_OBJECT_NO OLD_LOCATION,"
					+ " AAAV.WORKORDER_OBJECT_CODE OLD_LOCATION_CODE,"
					+ " AAAV.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
					+ " AAAV.RESPONSIBILITY_USER OLD_RESPONSIBILITY_USER,"
					+ " AAAV.RESPONSIBILITY_USER_NAME OLD_RESPONSIBILITY_USER_NAME,";
			if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
				sqlStr = sqlStr
				+ " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER ELSE '' END AS RESPONSIBILITY_USER,"
				+ " CASE WHEN AAAV.RESPONSIBILITY_USER=? THEN RESPONSIBILITY_USER_NAME ELSE '' END AS RESPONSIBILITY_USER_NAME,"
						+ " AAAV.ORGANIZATION_ID TO_ORGANIZATION_ID,";
				sqlArgs.add(user.getEmployeeId());
				sqlArgs.add(user.getEmployeeId());
			}
			sqlStr = sqlStr
					+ " AAAV.DATE_PLACED_IN_SERVICE,"
					+ " AAAV.COST,"
					+ " AAAV.DEPRN_COST,"
					+ " AAAV.COST RETIREMENT_COST,"
					+ " AAAV.DEPRECIATION_ACCOUNT OLD_DEPRECIATION_ACCOUNT,"
					+ " AAAV.DEPRECIATION,"
//					+ " AMS_PUB_PKG.GET_FLEX_VALUE(AAAV.ASSETS_STATUS,'ASSETS_STATUS') ASSETS_STATUS_DES ,"
					+ " dbo.APP_GET_FLEX_VALUE(CONVERT(VARCHAR,AAAV.ASSETS_STATUS),'ASSETS_STATUS') ASSETS_STATUS_DES,"
					+ " AAAV.IMPAIR_RESERVE" + " FROM"
					+ " AMS_ASSETS_ADDRESS_V AAAV";
			//Excel
			if (!excel.equals("")) {
				sqlStr +=",IMP_BARCODE IMP";
			}
			
			sqlStr=sqlStr
					+" WHERE "
					+" AAAV.ORGANIZATION_ID = ?";
			sqlArgs.add(user.getOrganizationId());
			sqlStr=sqlStr+" AND AAAV.FINANCE_PROP = ?";
			sqlArgs.add(dto.getFaContentCode());
			//Excel
			if (!excel.equals("")) {
				sqlStr +=" AND IMP.USER_ID= ?"
					   +"  AND IMP.BARCODE=AAAV.BARCODE";
			}
			sqlArgs.add(user.getUserId());
			
			if (!StrUtil.isEmpty(dto.getFromDept())) { //部门限制
				sqlStr += " AND AAAV.DEPT_CODE = ?";
				sqlArgs.add(dto.getFromDept());
			} else { //根据操作人员权限限制
				if (!isCompMgr && mtlMgrProps.equals("") && isDeptMgr) {
					DTOSet depts = user.getPriviDeptCodes();
					AmsMisDeptDTO dept = null;
					String deptCodes = "(";
					for (int i = 0; i < depts.getSize(); i++) {
						dept = (AmsMisDeptDTO) depts.getDTO(i);
						deptCodes += "'" + dept.getDeptCode() + "', ";
					}
					deptCodes += "'')";
					sqlStr += " AND AAAV.DEPT_CODE IN " + deptCodes;
				}
			}
			if (!user.isDptAssetsManager() && !user.isComAssetsManager()
					&& mtlMgrProps.equals("")) { //责任人限制
				if (!dto.getTransferType().equals("BTW_COMP")) {
					sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
					sqlArgs.add(user.getEmployeeId());
				}
			}
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { //调拨
				sqlStr += " AND (AAAV.ITEM_STATUS IS NULL OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //报废
				sqlStr += " AND (AAAV.ITEM_STATUS IS NULL OR AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //报废前提：正常或者闲置
				sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置前提：已报废或者闲置
				sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值前提：正常或者闲置
				sqlStr += " AND (AAAV.ITEM_STATUS = ? OR AAAV.ITEM_STATUS = ?)";
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			}
			sqlStr += "AND EXISTS(" + " SELECT" + " NULL" + " FROM"
					+ " ETS_FLEX_VALUES    EFV," + " ETS_FLEX_VALUE_SET EFVS"
					+ " WHERE"
					+ " EFVS.CODE = ?" + " AND EFV.CODE = ?)";
			sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
			sqlArgs.add(dto.getFaContentCode());

//			if (!excel.equals("")) {
//				sqlStr += " AND AAAV.BARCODE IN (" + excel + ")";
//			}

			sqlStr = sqlStr +  " AND NOT EXISTS ("
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " AMS_ASSETS_RESERVED AAR"
					+ " WHERE"
					+ " AAR.BARCODE = AAAV.BARCODE)"
					+ " AND NOT EXISTS (" //未同步的设备
					+ " SELECT" + " NULL" + " FROM"
					+ " AMS_ASSETS_CHK_LOG AACL" + " WHERE"
					+ " AACL.BARCODE = AAAV.BARCODE"
					+ " AND AACL.ORDER_TYPE <> 'ASS-CHK'"
					+ " AND AACL.SYN_STATUS IN (0, 2))";

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}


	//EXCEL导入时，查出没有导入成功的BARCODE
	public SQLModel getQueryBarcodeAllExcelModel(String excel,
			AmsAssetsTransHeaderDTO dto, String returnModel) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String mtlMgrProps = user.getMtlMgrProps();
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT AAAV.BARCODE FROM TD_ASSETS_ADDRESS_V AAAV WHERE AAAV.BARCODE IN ("
					+ excel + ") AND AAAV.BARCODE NOT IN (" + returnModel + ")";
			sqlArgs.add(user.getOrganizationId());
			if (!StrUtil.isEmpty(dto.getFromDept())) { //部门限制
				sqlArgs.add(dto.getFromDept());
			}
			if (!user.isDptAssetsManager() && !user.isComAssetsManager()
					&& mtlMgrProps.equals("")) { //责任人限制
				if (!dto.getTransferType().equals("BTW_COMP")) {
					sqlArgs.add(user.getEmployeeId());
				}
			}
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { //调拨
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //报废
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //报废前提：正常或者闲置
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置前提：已报废或者闲置
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值前提：正常或者闲置
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			}
			sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
			sqlArgs.add(dto.getFaContentCode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} else {
			String sqlStr = "SELECT AAAV.BARCODE FROM AMS_ASSETS_ADDRESS_V AAAV WHERE AAAV.BARCODE IN ("
					+ excel + ") AND AAAV.BARCODE NOT IN (" + returnModel + ")";
			sqlArgs.add(user.getOrganizationId());
			if (!StrUtil.isEmpty(dto.getFromDept())) { //部门限制
				sqlArgs.add(dto.getFromDept());
			}
			if (!user.isDptAssetsManager() && !user.isComAssetsManager()
					&& mtlMgrProps.equals("")) { //责任人限制
				if (!dto.getTransferType().equals("BTW_COMP")) {
					sqlArgs.add(user.getEmployeeId());
				}
			}
			if (dto.getTransType().equals(AssetsDictConstant.ASS_RED)) { //调拨
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_DIS)) { //报废
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //报废前提：正常或者闲置
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_CLR)) { //处置前提：已报废或者闲置
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_DISCARDED);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			} else if (dto.getTransType().equals(AssetsDictConstant.ASS_SUB)) { //减值前提：正常或者闲置
				sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
				sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			}
			sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
			sqlArgs.add(dto.getFaContentCode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}
		return sqlModel;
	}
}