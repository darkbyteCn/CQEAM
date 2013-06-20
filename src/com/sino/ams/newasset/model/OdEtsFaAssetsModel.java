package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;


public class OdEtsFaAssetsModel extends AMSSQLProducer {

	/**
	 * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据库SQL构造层构造函数
	 * 
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsAddressVDTO 本次操作的数据
	 */
	public OdEtsFaAssetsModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = 
			" SELECT EII.BARCODE, \n" +
			"        EII.ITEM_CODE, \n" + 
			"        ESI.ITEM_NAME ASSETS_DESCRIPTION, \n" + //资产名称
			"        ESI.ITEM_SPEC MODEL_NUMBER, \n" + //资产型号
			"        EII.ADDRESS_ID, \n" +
			"        AOA.OBJECT_NO, \n" +
			"        EO.WORKORDER_OBJECT_CODE, \n" + //地点代码
			"        EO.WORKORDER_OBJECT_NAME, \n" + //地点名称
			"        EII.RESPONSIBILITY_USER, \n" +
			"        AME.USER_NAME RESPONSIBILITY_USER_NAME, \n" + //责任人
			"        AME.EMPLOYEE_NUMBER, \n" + //员工号
			"        EII.RESPONSIBILITY_DEPT, \n" +
			"        AMD.DEPT_NAME DEPT_NAME, \n" + //责任部门
			"        EII.MAINTAIN_USER MAINTAIN_USER_NAME, \n" + //使用人
			"        CASE EII.ITEM_STATUS WHEN 'DISCARDED' THEN '已报废' ELSE EII.ITEM_STATUS END ITEM_STATUS \n" + //资产状态
			"   FROM ETS_ITEM_INFO EII, \n" + 
			"        ETS_SYSTEM_ITEM ESI, \n" + 
			"        AMS_OBJECT_ADDRESS AOA, \n" + 
			"        ETS_OBJECT EO, \n" +
			"        AMS_MIS_EMPLOYEE AME, \n" +
			"        AMS_MIS_DEPT AMD \n" +
			"  WHERE 1 = 1 \n" +
			"    AND EII.ITEM_CODE = ESI.ITEM_CODE \n" +
			"    AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
			"    AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
			"    AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n" +
			"    AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n" +
			"    AND (EII.ITEM_STATUS IS NULL OR EII.ITEM_STATUS = dbo.NVL('DISCARDED', EII.ITEM_STATUS)) \n" +
			"    AND EII.FINANCE_PROP IN ('DH_ASSET', 'DG_ASSETS', 'SPARE') \n" +
			"    AND (? = '' OR ESI.ITEM_NAME LIKE ?) \n" +
			"    AND (? = '' OR ESI.ITEM_SPEC LIKE ?) \n" +
			"    AND (? = '' OR EII.BARCODE LIKE ?) \n" +
			"    AND EII.RESPONSIBILITY_USER = ? \n" ;
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(userAccount.getEmployeeId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = 
		" SELECT EII.BARCODE, \n" +
		"        EII.ITEM_CODE, \n" + 
		"        ESI.ITEM_NAME, \n" + //资产名称
		"        ESI.ITEM_SPEC, \n" + //资产型号
		"        EII.ADDRESS_ID, \n" +
		"        AOA.OBJECT_NO, \n" +
		"        EO.WORKORDER_OBJECT_CODE, \n" + //地点代码
		"        EO.WORKORDER_OBJECT_NAME, \n" + //地点名称
		"        EII.RESPONSIBILITY_USER, \n" +
		"        AME.USER_NAME RESPONSIBILITY_USER_NAME, \n" + //责任人
		"        AME.EMPLOYEE_NUMBER, \n" + //员工号
		"        EII.RESPONSIBILITY_DEPT, \n" +
		"        AMD.DEPT_NAME DEPT_NAME, \n" + //责任部门
		"        EII.MAINTAIN_USER MAINTAIN_USER_NAME, \n" + //使用人
		"        CASE EII.ITEM_STATUS WHEN 'DISCARDED' THEN '已报废' ELSE EII.ITEM_STATUS END ITEM_STATUS \n" + //资产状态
		"   FROM ETS_ITEM_INFO EII, \n" + 
		"        ETS_SYSTEM_ITEM ESI, \n" + 
		"        AMS_OBJECT_ADDRESS AOA, \n" + 
		"        ETS_OBJECT EO, \n" +
		"        AMS_MIS_EMPLOYEE AME, \n" +
		"        AMS_MIS_DEPT AMD \n" +
		"  WHERE 1 = 1 \n" +
		"    AND EII.ITEM_CODE = ESI.ITEM_CODE \n" +
		"    AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
		"    AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
		"    AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n" +
		"    AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n" +
		"    AND (  EII.ITEM_STATUS =  'DISCARDED' ) \n" +
		"    AND EII.FINANCE_PROP IN ('DH_ASSET', 'DG_ASSETS', 'SPARE') \n" +
		//"    AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME) \n" +
		//"    AND ESI.ITEM_SPEC LIKE dbo.NVL(?, ESI.ITEM_SPEC) \n" +
		//"    AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE) \n" +
		"    AND (? = '' OR ESI.ITEM_NAME LIKE ?) \n" +
		"    AND (? = '' OR ESI.ITEM_SPEC LIKE ?) \n" +
		"    AND (? = '' OR EII.BARCODE LIKE ?) \n" ;
		
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		
		if (!userAccount.getEmployeeId().equals("")) {
			sqlStr += "    AND EII.RESPONSIBILITY_USER = ? \n" ;
			sqlArgs.add(userAccount.getEmployeeId());
		} else {
			sqlStr += "    AND EII.RESPONSIBILITY_USER = '-1' \n" ;
		}

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造获取个人资产的SQL
	 * 
	 * @return SQLModel
	 */
	private SQLModel getPersonalAssetsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
/*		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT "
					+ " AAAV.BARCODE,"
					+ " AAAV.ASSET_NUMBER,"
					+ " AAAV.FA_CATEGORY1,"
					+ " AAAV.FA_CATEGORY2,"
					+ " AAAV.ASSETS_DESCRIPTION,"
					+ " AAAV.MODEL_NUMBER,"
					+ " AAAV.UNIT_OF_MEASURE,"
					+ " AAAV.CURRENT_UNITS,"
					+ " dbo.APP_GET_FLEX_VALUE(AAAV.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS, "
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
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
					+ " AND AAAV.ASSET_NUMBER LIKE dbo.NVL(?, AAAV.ASSET_NUMBER)"
					+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
					+ " AND AAAV.RESPONSIBILITY_USER = ?"
					+ " AND (AAAV.ITEM_STATUS IS NULL OR AAAV.ITEM_STATUS = dbo.NVL(?, AAAV.ITEM_STATUS))"
					
					+ " AND AAAV.FINANCE_PROP IN ('DH_ASSET','DG_ASSETS','SPARE')" //个人其它报废资产仅限于村通、低耗、备件
					
					+ " AND AAAV.ASSET_ID IS NOT NULL  AND NOT EXISTS(" // " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " " + "
					+ " SELECT" + " NULL" + " FROM"
					+ " AMS_ASSETS_RESERVED AAR" + " WHERE"
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
				sqlStr = sqlStr + " AND EXISTS(" + " SELECT" + " NULL"
						+ " FROM" + " ETS_SYSITEM_DISTRIBUTE ESD" + " WHERE"
						+ " ESD.ITEM_CODE = AAAV.ITEM_CODE"
						+ " AND ESD.ORGANIZATION_ID = ?)";
				sqlArgs.add(userAccount.getOrganizationId());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} else {
			String sqlStr = "SELECT "
					+ " AAAV.BARCODE,"
					+ " AAAV.ASSET_NUMBER,"
					+ " AAAV.FA_CATEGORY1,"
					+ " AAAV.FA_CATEGORY2,"
					+ " AAAV.ASSETS_DESCRIPTION,"
					+ " AAAV.MODEL_NUMBER,"
					+ " AAAV.UNIT_OF_MEASURE,"
					+ " AAAV.CURRENT_UNITS,"
					+ " dbo.APP_GET_FLEX_VALUE(AAAV.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS, "
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
					+ " AMS_ASSETS_ADDRESS_V AAAV"
					+ " WHERE"
					+ " AAAV.FA_CATEGORY1 = dbo.NVL(?, FA_CATEGORY1)"
					+ " AND AAAV.FA_CATEGORY2 = dbo.NVL(?, FA_CATEGORY2)"
					+ " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAAV.MODEL_NUMBER LIKE dbo.NVL(?, AAAV.MODEL_NUMBER))"
					+ " AND AAAV.ASSET_NUMBER LIKE dbo.NVL(?, AAAV.ASSET_NUMBER)"
					+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
					+ " AND AAAV.RESPONSIBILITY_USER = ?"
					+ " AND (AAAV.ITEM_STATUS IS NULL OR AAAV.ITEM_STATUS = dbo.NVL(?, AAAV.ITEM_STATUS))"
					
					+ " AND AAAV.FINANCE_PROP IN ('DH_ASSET','DG_ASSETS','SPARE')" //个人其它报废资产仅限于村通、低耗、备件
					
					+ " AND AAAV.ASSET_ID IS NOT NULL AND NOT EXISTS(" // " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " " + "
					+ " SELECT" + " NULL" + " FROM"
					+ " AMS_ASSETS_RESERVED AAR" + " WHERE"
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
				sqlStr = sqlStr + " AND EXISTS(" + " SELECT" + " NULL"
						+ " FROM" + " ETS_SYSITEM_DISTRIBUTE ESD" + " WHERE"
						+ " ESD.ITEM_CODE = AAAV.ITEM_CODE"
						+ " AND ESD.ORGANIZATION_ID = ?)";
				sqlArgs.add(userAccount.getOrganizationId());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}*/
		String sqlStr = 
			" SELECT EII.BARCODE, \n" +
			"        EII.ITEM_CODE, \n" + 
			"        ESI.ITEM_NAME ASSETS_DESCRIPTION, \n" + //资产名称
			"        ESI.ITEM_SPEC MODEL_NUMBER, \n" + //资产型号
			"        EII.ADDRESS_ID, \n" +
			"        AOA.OBJECT_NO, \n" +
			"        EO.WORKORDER_OBJECT_CODE, \n" + //地点代码
			"        EO.WORKORDER_OBJECT_NAME, \n" + //地点名称
			"        EII.RESPONSIBILITY_USER, \n" +
			"        AME.USER_NAME RESPONSIBILITY_USER_NAME, \n" + //责任人
			"        AME.EMPLOYEE_NUMBER, \n" + //员工号
			"        EII.RESPONSIBILITY_DEPT, \n" +
			"        AMD.DEPT_NAME DEPT_NAME, \n" + //责任部门
			"        EII.MAINTAIN_USER MAINTAIN_USER_NAME, \n" + //使用人
			"        CASE EII.ITEM_STATUS WHEN 'DISCARDED' THEN '已报废' ELSE EII.ITEM_STATUS END ITEM_STATUS \n" + //资产状态
			"   FROM ETS_ITEM_INFO EII, \n" + 
			"        ETS_SYSTEM_ITEM ESI, \n" + 
			"        AMS_OBJECT_ADDRESS AOA, \n" + 
			"        ETS_OBJECT EO, \n" +
			"        AMS_MIS_EMPLOYEE AME, \n" +
			"        AMS_MIS_DEPT AMD \n" +
			"  WHERE 1 = 1 \n" +
			"    AND EII.ITEM_CODE = ESI.ITEM_CODE \n" +
			"    AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
			"    AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
			"    AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID \n" +
			"    AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n" +
			"    AND (EII.ITEM_STATUS IS NULL OR EII.ITEM_STATUS = dbo.NVL('DISCARDED', EII.ITEM_STATUS)) \n" +
			"    AND EII.FINANCE_PROP IN ('DH_ASSET', 'DG_ASSETS', 'SPARE') \n" +
			"    AND (? = '' OR ESI.ITEM_NAME LIKE ?) \n" +
			"    AND (? = '' OR ESI.ITEM_SPEC LIKE ?) \n" +
			"    AND (? = '' OR EII.BARCODE LIKE ?) \n" +
			"    AND EII.RESPONSIBILITY_USER = ? \n" ;
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(userAccount.getEmployeeId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
