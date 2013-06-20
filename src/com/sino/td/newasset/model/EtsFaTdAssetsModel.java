package com.sino.td.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

/**
 * <p>Title: AmsFaAssetsModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsFaAssetsModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsFaTdAssetsModel extends AMSSQLProducer {

	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_TD_ASSETS 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
	 */
	public EtsFaTdAssetsModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成固定资产当前信息(EAM) AMS_FA_ASSETS多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @param checkedAssets 选中的资产
	 */
	public SQLModel getExpCheckedAssetsModel(String[] checkedAssets) {
		SQLModel sqlModel = getPageQueryModel();
		String barcodes = ArrUtil.arrToSqlStr(checkedAssets);
		String sqlStr = sqlModel.getSqlStr();
		sqlStr = "SELECT * FROM ("
				 + sqlStr
				 + ") TMP_V WHERE TMP_V.BARCODE IN ("
				 + barcodes
				 + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}


	/**
	 * 功能：根据标签号获取设备详细信息SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPrimaryKeyDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AAAV.*"
						+ " FROM"
						+ " TD_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ " AAAV.BARCODE = ?";
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成固定资产当前信息(EAM) AMS_FA_ASSETS页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = null;
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String treeCategory = dto.getTreeCategory();
		if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_PERSON)) { //个人资产
			sqlModel = getPersonalAssetsModel();
		} else if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_DEPART)) { //部门资产
			sqlModel = getDeptAssetsModel();
		} else if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_COMPAN)) { //公司资产
			sqlModel = getCompanyAssetsModel();
		} else if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_PROVIN)) { //全省资产
			sqlModel = getProvinceAssetsModel();
		} else if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_CONFIRM)) { //待确认资产
			sqlModel = getConfirmAssetsModel();
		} else if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_TRANSFER)) { //调出资产
			sqlModel = getTransferAssetsModel();
		}
		return sqlModel;
	}

	/**
	 * 功能：构造获取个人待确认资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getConfirmAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AATL.BARCODE,"
						+ " dbo.NVL(AATL.NEW_BARCODE, AATL.BARCODE) NEW_BARCODE,"
						+ " AATH.TRANS_ID,"
						+ " AATH.TRANS_NO,"
						+ " AATH.TRANSFER_TYPE,"
						+ " EFA.ASSET_NUMBER,"
						+ " EFA.ASSETS_DESCRIPTION,"
						+ " EFA.MODEL_NUMBER,"
						+ " ISNULL(EFA.CURRENT_UNITS, 1) CURRENT_UNITS,"
						+ " EFA.COST,"
						+ " EFA.DEPRN_COST,"
						+ " EFA.DATE_PLACED_IN_SERVICE,"
						+ " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
						+ " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
						+ " AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,"
						+ " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"

						+ " AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
						+ " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
						+ " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
						+ " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
						+ " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
						+ " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " AOAO.ADDRESS_ID OLD_ADDRESS_ID,"
						+ " AOAN.ADDRESS_ID,"
						+ " AATH.FROM_ORGANIZATION_ID,"
						+ " AATH.TO_ORGANIZATION_ID,"
						+ " AATL.REMARK"
						+ " FROM"
						+ " ETS_ITEM_INFO           EII,"
						+ " ETS_ITEM_MATCH_TD          EIM,"
						+ " ETS_FA_ASSETS_TD           EFA,"
						+ " TD_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_MIS_EMPLOYEE        AMEO,"
						+ " AMS_MIS_DEPT            AMDO,"
						+ " ETS_OBJECT              EOO,"
						+ " AMS_OBJECT_ADDRESS      AOAO,"
						+ " TD_ASSETS_TRANS_LINE   AATL,"
						+ " AMS_MIS_EMPLOYEE        AMEN,"
						+ " AMS_MIS_DEPT            AMDN,"
						+ " ETS_OBJECT              EON,"
						+ " AMS_OBJECT_ADDRESS      AOAN"
						+ " WHERE"
						+ " EFA.ASSET_ID = EIM.ASSET_ID"
						+ " AND EIM.SYSTEMID = EII.SYSTEMID"
						+ " AND EII.BARCODE = AATL.BARCODE"
						+ " AND AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
						+ " AND EOO.WORKORDER_OBJECT_NO = AOAO.OBJECT_NO"
						+ " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
						+ " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
						+ " AND AATL.ASSIGNED_TO_LOCATION = EON.WORKORDER_OBJECT_NO"
						+ " AND EON.WORKORDER_OBJECT_NO = AOAN.OBJECT_NO"
						+ " AND AATL.RESPONSIBILITY_USER = AMEN.EMPLOYEE_ID"
						+ " AND AATL.RESPONSIBILITY_DEPT = AMDN.DEPT_CODE"
						+ " AND AATL.TRANS_ID = AATH.TRANS_ID"
						+ " AND ((AATL.LINE_STATUS = ? AND AATH.TRANSFER_TYPE = ?) OR (AATL.LINE_STATUS = ? AND AATH.TRANSFER_TYPE <> ?))"
						+ " AND AATL.RESPONSIBILITY_USER = ?"
						+ " AND AATL.CONFIRM_DATE  " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AATH.FROM_DEPT = dbo.NVL(?, AATH.FROM_DEPT))"

						+ " AND EFA.FA_CATEGORY1 = dbo.NVL(?, EFA.FA_CATEGORY1)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.FA_CATEGORY2 = dbo.NVL(?, EFA.FA_CATEGORY2))"

						+ " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.MODEL_NUMBER LIKE dbo.NVL(?, EFA.MODEL_NUMBER))"
						+ " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)"
						+ " AND EFA.ASSET_NUMBER LIKE dbo.NVL(?, EFA.ASSET_NUMBER)"
						+ " AND EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED AAR"
						+ " WHERE"
						+ " AAR.TRANS_ID = AATL.TRANS_ID"
						+ " AND AAR.BARCODE = AATL.BARCODE)";
		sqlArgs.add(AssetsDictConstant.APPROVED);
		sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
		sqlArgs.add(AssetsDictConstant.ORDER_STS_ASSIGNED);
		sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
		sqlArgs.add(userAccount.getEmployeeId());
		sqlArgs.add(dto.getDeptCode());
		sqlArgs.add(dto.getDeptCode());

		sqlArgs.add(dto.getFaCategory1());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getFaCategory2());

		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getAssetNumber());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：构造获取调出资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getTransferAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AATL.BARCODE,"
						+ " AATL.BARCODE NEW_BARCODE,"
						+ " AATH.TRANS_ID,"
						+ " AATH.TRANS_NO,"
						+ " AATH.TRANSFER_TYPE,"
						+ " EFA.ASSET_NUMBER,"
						+ " EFA.ASSETS_DESCRIPTION,"
						+ " EFA.MODEL_NUMBER,"
						+ " ISNULL(EFA.CURRENT_UNITS, 1) CURRENT_UNITS,"
						+ " EFA.COST,"
						+ " EFA.DEPRN_COST,"
						+ " EFA.DATE_PLACED_IN_SERVICE,"
						+ " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
						+ " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
						+ " AOAO.ADDRESS_ID OLD_ADDRESS_ID,"
						+ " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
						+ " AATL.OLD_RESPONSIBILITY_DEPT,"
						+ " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
						+ " AATL.OLD_RESPONSIBILITY_USER,"
						+ " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
						+ " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
						+ " AOAN.ADDRESS_ID ADDRESS_ID,"
						+ " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
						+ " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
						+ " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " AATH.FROM_ORGANIZATION_ID,"
						+ " AATH.TO_ORGANIZATION_ID,"
						+ " AATL.REMARK"
						+ " FROM"
						+ " ETS_FA_ASSETS_TD           EFA,"
						+ " ETS_ITEM_MATCH_TD          EIM,"
						+ " ETS_ITEM_INFO           EII,"
						+ " TD_ASSETS_TRANS_LINE   AATL,"
						+ " TD_ASSETS_TRANS_HEADER AATH,"
						+ " AMS_MIS_EMPLOYEE        AMEO,"
						+ " AMS_MIS_DEPT            AMDO,"
						+ " ETS_OBJECT              EOO,"
						+ " AMS_OBJECT_ADDRESS      AOAO,"
						+ " AMS_MIS_EMPLOYEE        AMEN,"
						+ " AMS_MIS_DEPT            AMDN,"
						+ " ETS_OBJECT              EON,"
						+ " AMS_OBJECT_ADDRESS      AOAN"
						+ " WHERE"
						+ " EFA.ASSET_ID = EIM.ASSET_ID"
						+ " AND EIM.SYSTEMID = EII.SYSTEMID"
						+ " AND EII.BARCODE = AATL.BARCODE"
						+ " AND AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
						+ " AND EOO.WORKORDER_OBJECT_NO = AOAO.OBJECT_NO"
						+ " AND AATL.OLD_RESPONSIBILITY_USER = AMEO.EMPLOYEE_ID"
						+ " AND AATL.OLD_RESPONSIBILITY_DEPT = AMDO.DEPT_CODE"
						+ " AND AATL.RESPONSIBILITY_DEPT = AMDN.DEPT_CODE"
						+ " AND AATL.RESPONSIBILITY_USER = AMEN.EMPLOYEE_ID"
						+ " AND AATL.ASSIGNED_TO_LOCATION = EON.WORKORDER_OBJECT_NO"
						+ " AND EON.WORKORDER_OBJECT_NO = AOAN.ADDRESS_ID"
						+ " AND AATL.TRANS_ID = AATH.TRANS_ID"
						+ " AND AATH.TRANS_TYPE = ?"
						+ " AND (AATH.CREATED_BY = ? OR AATL.OLD_RESPONSIBILITY_USER = ?)"
						+ " AND AATH.FROM_ORGANIZATION_ID = ?"
						+ " AND EFA.FA_CATEGORY1 LIKE dbo.NVL(?, EFA.FA_CATEGORY1)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.FA_CATEGORY2 LIKE dbo.NVL(?, EFA.FA_CATEGORY2))"
						+ " AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR EFA.MODEL_NUMBER LIKE dbo.NVL(?, EFA.MODEL_NUMBER))"
						+ " AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)"
						+ " AND EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_SYSITEM_DISTRIBUTE ESD"
						+ " WHERE"
						+ " ESD.ITEM_CODE = EII.ITEM_CODE"
						+ " AND ESD.ORGANIZATION_ID = 87)"
						+ " AND EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM   AMS_ASSETS_RESERVED AAR"
						+ " WHERE  AAR.TRANS_ID = AATL.TRANS_ID"
						+ " AND AAR.BARCODE = AATL.BARCODE)"
						+ " ORDER  BY AATH.TRANS_NO DESC";
		sqlArgs.add(AssetsDictConstant.ASS_RED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getEmployeeId());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getFaCategory1());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造获取个人资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getPersonalAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " AAAV.BARCODE,"
						+ " AAAV.ASSET_NUMBER,"
						+ " AAAV.FA_CATEGORY1,"
						+ " AAAV.FA_CATEGORY2,"
						+ " AAAV.ASSETS_DESCRIPTION,"
						+ " AAAV.MODEL_NUMBER,"
						+ " AAAV.UNIT_OF_MEASURE,"
						+ " AAAV.CURRENT_UNITS,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AAAV.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS, "
                        + " AAAV.COST,"
						+ " AAAV.ASSETS_CREATE_DATE,"
						+ " AAAV.DATE_PLACED_IN_SERVICE,"
						+ " AAAV.LIFE_IN_YEARS,"
						+ " AAAV.DEPRECIATION,"
						+ " AAAV.DEPRN_COST,"
						+ " AAAV.SCRAP_VALUE,"
						+ " AAAV.DEPRECIATION_ACCOUNT,"
						+ " AAAV.DEPRECIATION_ACCOUNT_NAME,"
						+ " AAAV.BOOK_TYPE_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NO,"
						+ " AAAV.WORKORDER_OBJECT_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NAME,"
						+ " AAAV.PROJECT_NAME,"
						+ " AAAV.RESPONSIBILITY_USER,"
						+ " AAAV.RESPONSIBILITY_USER_NAME,"
						+ " AAAV.EMPLOYEE_NUMBER,"
						+ " AAAV.DEPT_CODE,"
						+ " AAAV.DEPT_NAME,"
						+ " AAAV.MAINTAIN_USER_NAME,"
						+ " AAAV.FA_CATEGORY_CODE"
						+ " FROM"
						+ " TD_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ " AAAV.FA_CATEGORY1 = dbo.NVL(?, FA_CATEGORY1)"
						+ " AND AAAV.FA_CATEGORY2 = dbo.NVL(?, FA_CATEGORY2)"
						+ " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
						+ " AND AAAV.ASSET_NUMBER LIKE dbo.NVL(?, AAAV.ASSET_NUMBER)"
						+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
						+ " AND AAAV.RESPONSIBILITY_USER = ?"
						+ " AND (AAAV.ITEM_STATUS  " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = dbo.NVL(?, AAAV.ITEM_STATUS))"
						+ " AND  " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " "
						+ " AND NOT EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED AAR"
						+ " WHERE"
						+ " AAAV.BARCODE = AAR.BARCODE)";
		sqlArgs.add(dto.getFaCategory1());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getAssetNumber());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(userAccount.getEmployeeId());
		sqlArgs.add(dto.getItemStatus());
		if (!userAccount.isProvinceUser()) {
			sqlStr = sqlStr
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " ETS_SYSITEM_DISTRIBUTE ESD"
					 + " WHERE"
					 + " ESD.ITEM_CODE = AAAV.ITEM_CODE"
					 + " AND ESD.ORGANIZATION_ID = ?)";
			sqlArgs.add(userAccount.getOrganizationId());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造获取部门资产的SQL
	 * @return SQLModel
	 */
	private SQLModel getDeptAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " AAAV.BARCODE,"
						+ " AAAV.ASSET_NUMBER,"
						+ " AAAV.FA_CATEGORY1,"
						+ " AAAV.FA_CATEGORY2,"
						+ " AAAV.ASSETS_DESCRIPTION,"
						+ " AAAV.MODEL_NUMBER,"
						+ " AAAV.UNIT_OF_MEASURE,"
						+ " AAAV.CURRENT_UNITS,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AAAV.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS, "
                        + " AAAV.COST,"
						+ " AAAV.ASSETS_CREATE_DATE,"
						+ " AAAV.DATE_PLACED_IN_SERVICE,"
						+ " AAAV.LIFE_IN_YEARS,"
						+ " AAAV.DEPRECIATION,"
						+ " AAAV.DEPRN_COST,"
						+ " AAAV.SCRAP_VALUE,"
						+ " AAAV.DEPRECIATION_ACCOUNT,"
						+ " AAAV.DEPRECIATION_ACCOUNT_NAME,"
						+ " AAAV.BOOK_TYPE_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NO,"
						+ " AAAV.WORKORDER_OBJECT_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NAME,"
						+ " AAAV.PROJECT_NAME,"
						+ " AAAV.RESPONSIBILITY_USER,"
						+ " AAAV.RESPONSIBILITY_USER_NAME,"
						+ " AAAV.EMPLOYEE_NUMBER,"
						+ " AAAV.DEPT_CODE,"
						+ " AAAV.DEPT_NAME,"
						+ " AAAV.MAINTAIN_USER_NAME,"
						+ " AAAV.FA_CATEGORY_CODE"
						+ " FROM"
						+ " TD_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ " AAAV.DEPT_NAME = ?"
						+ " AND AAAV.FA_CATEGORY1 LIKE NVL (?, AAAV.FA_CATEGORY1)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.FA_CATEGORY2 LIKE NVL (?, AAAV.FA_CATEGORY2))"
						+ " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
						+ " AND AAAV.ASSET_NUMBER LIKE dbo.NVL(?, AAAV.ASSET_NUMBER)"
						+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
						+ " AND (AAAV.ITEM_STATUS  " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = dbo.NVL(?, AAAV.ITEM_STATUS))"
						+ " AND  " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " "
						+ " AND NOT EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED AAR"
						+ " WHERE"
						+ " AAAV.BARCODE = AAR.BARCODE)";
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getFaCategory1());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getAssetNumber());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemStatus());
		if (!userAccount.isProvinceUser()) {
			sqlStr = sqlStr
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " ETS_SYSITEM_DISTRIBUTE ESD"
					 + " WHERE"
					 + " ESD.ITEM_CODE = AAAV.ITEM_CODE"
					 + " AND ESD.ORGANIZATION_ID = ?)";
			sqlArgs.add(userAccount.getOrganizationId());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取公司资产查询SQL
	 * @return SQLModel
	 */
	private SQLModel getCompanyAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AAAV.BARCODE,"
						+ " AAAV.ASSET_NUMBER,"
						+ " AAAV.FA_CATEGORY1,"
						+ " AAAV.FA_CATEGORY2,"
						+ " AAAV.ASSETS_DESCRIPTION,"
						+ " AAAV.MODEL_NUMBER,"
						+ " AAAV.UNIT_OF_MEASURE,"
						+ " AAAV.CURRENT_UNITS,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AAAV.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS, "
                        + " AAAV.COST,"
						+ " AAAV.ASSETS_CREATE_DATE,"
						+ " AAAV.DATE_PLACED_IN_SERVICE,"
						+ " AAAV.LIFE_IN_YEARS,"
						+ " AAAV.DEPRECIATION,"
						+ " AAAV.DEPRN_COST,"
						+ " AAAV.SCRAP_VALUE,"
						+ " AAAV.DEPRECIATION_ACCOUNT,"
						+ " AAAV.DEPRECIATION_ACCOUNT_NAME,"
						+ " AAAV.BOOK_TYPE_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NO,"
						+ " AAAV.WORKORDER_OBJECT_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NAME,"
						+ " AAAV.PROJECT_NAME,"
						+ " AAAV.RESPONSIBILITY_USER,"
						+ " AAAV.RESPONSIBILITY_USER_NAME,"
						+ " AAAV.EMPLOYEE_NUMBER,"
						+ " AAAV.DEPT_CODE,"
						+ " AAAV.DEPT_NAME,"
						+ " AAAV.MAINTAIN_USER_NAME,"
						+ " AAAV.FA_CATEGORY_CODE"
						+ " FROM"
						+ " TD_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ " AAAV.COMPANY = ?"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.DEPT_NAME = ?)"
						+ " AND AAAV.FA_CATEGORY1 LIKE NVL (?, AAAV.FA_CATEGORY1)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.FA_CATEGORY2 LIKE NVL (?, AAAV.FA_CATEGORY2))"
						+ " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
						+ " AND AAAV.ASSET_NUMBER LIKE dbo.NVL(?, AAAV.ASSET_NUMBER)"
						+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
						+ " AND  " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " "
						+ " AND (AAAV.ITEM_STATUS  " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = dbo.NVL(?, AAAV.ITEM_STATUS))"
						+ " AND NOT EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED AAR"
						+ " WHERE"
						+ " AAAV.BARCODE = AAR.BARCODE)";
		sqlArgs.add(dto.getCompanyName());
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getDeptName());

		sqlArgs.add(dto.getFaCategory1());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getFaCategory2());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getAssetNumber());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemStatus());

		if (!userAccount.isProvinceUser()) {
			sqlStr = sqlStr
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " ETS_SYSITEM_DISTRIBUTE ESD"
					 + " WHERE"
					 + " ESD.ITEM_CODE = AAAV.ITEM_CODE"
					 + " AND ESD.ORGANIZATION_ID = ?)";
			sqlArgs.add(userAccount.getOrganizationId());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取全省资产查询SQL
	 * @return SQLModel
	 */
	private SQLModel getProvinceAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " AAAV.BARCODE,"
						+ " AAAV.ASSET_NUMBER,"
						+ " AAAV.FA_CATEGORY1,"
						+ " AAAV.FA_CATEGORY2,"
						+ " AAAV.ASSETS_DESCRIPTION,"
						+ " AAAV.MODEL_NUMBER,"
						+ " AAAV.UNIT_OF_MEASURE,"
						+ " AAAV.CURRENT_UNITS,"
                        + " AMS_PUB_PKG.GET_FLEX_VALUE(AAAV.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS, "
                        + " AAAV.COST,"
                        + " AAAV.ASSETS_CREATE_DATE,"
						+ " AAAV.DATE_PLACED_IN_SERVICE,"
						+ " AAAV.LIFE_IN_YEARS,"
						+ " AAAV.DEPRECIATION,"
						+ " AAAV.DEPRN_COST,"
						+ " AAAV.SCRAP_VALUE,"
						+ " AAAV.DEPRECIATION_ACCOUNT,"
						+ " AAAV.DEPRECIATION_ACCOUNT_NAME,"
						+ " AAAV.BOOK_TYPE_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NO,"
						+ " AAAV.WORKORDER_OBJECT_CODE,"
						+ " AAAV.WORKORDER_OBJECT_NAME,"
						+ " AAAV.PROJECT_NAME,"
						+ " AAAV.RESPONSIBILITY_USER,"
						+ " AAAV.RESPONSIBILITY_USER_NAME,"
						+ " AAAV.EMPLOYEE_NUMBER,"
						+ " AAAV.DEPT_CODE,"
						+ " AAAV.DEPT_NAME,"
						+ " AAAV.MAINTAIN_USER_NAME,"
						+ " AAAV.FA_CATEGORY_CODE"
						+ " FROM"
						+ " TD_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ " AAAV.COMPANY = dbo.NVL(?, AAAV.COMPANY)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.DEPT_NAME = ?)"
						+ " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
						+ " AND (" + SyBaseSQLUtil.isNull() + " OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
						+ " AND AAAV.ASSET_NUMBER LIKE dbo.NVL(?, AAAV.ASSET_NUMBER)"
						+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
						+ " AND (AAAV.ITEM_STATUS  " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = dbo.NVL(?, AAAV.ITEM_STATUS))"
						+ " AND  " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " "
						+ " AND NOT EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED AAR"
						+ " WHERE"
						+ " AAAV.BARCODE = AAR.BARCODE)";
		sqlArgs.add(dto.getCompanyName());
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getModelNumber());
		sqlArgs.add(dto.getAssetNumber());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemStatus());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
