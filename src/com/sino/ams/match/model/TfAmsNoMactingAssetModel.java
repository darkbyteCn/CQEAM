package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.match.dto.TfAmsNoMactingAssetDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

public class TfAmsNoMactingAssetModel extends AMSSQLProducer {

	/**
	 * 功能：未匹配资产清单 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsHouseInfoDTO 本次操作的数据
	 */
	public TfAmsNoMactingAssetModel(SfUserDTO userAccount,
			TfAmsNoMactingAssetDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 框架自动生成未匹配资产清单 查询 。
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		TfAmsNoMactingAssetDTO dto = (TfAmsNoMactingAssetDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = 
/*				      "SELECT DISTINCT" //成本中心视图有重复值
					+ " EFA.TAG_NUMBER,"
					+ " EFA.ASSETS_DESCRIPTION,"
					+ " EFA.MODEL_NUMBER,"
					+ " EFA.ASSETS_LOCATION,"
					+ " EFA.DATE_PLACED_IN_SERVICE,"
					+ " EFA.LIFE_IN_YEARS,"
					+ " EFA.COST,"
					+ " EFA.ASSET_NUMBER,"
					+ " EFA.BOOK_TYPE_CODE,"
					+ " EFA.ASSETS_LOCATION_CODE,"
					+ " ACCV.COST_CENTER_NAME," 
                    + " EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 FA_CODE," 
                    + " EFA.FA_CATEGORY2 "
					+ " FROM"
					+ " ETS_FA_ASSETS_TF   EFA,"
					+ " AMS_COST_CENTER_V  ACCV"
					+ " WHERE"
					+ " SUBSTR(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ACCV.COST_CENTER_CODE(+)"
					+ " AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)" 
                    + " AND ACCV.ORGANIZATION_ID = EFA.ORGANIZATION_ID"
					+ " AND EFA.TAG_NUMBER LIKE NVL(?, EFA.TAG_NUMBER)"
					+ " AND EFA.ASSETS_DESCRIPTION LIKE NVL(?, EFA.ASSETS_DESCRIPTION)"
					+ " AND (EFA.ASSETS_LOCATION_CODE LIKE NVL(?, EFA.ASSETS_LOCATION_CODE)"
					+ " 	 OR EFA.ASSETS_LOCATION LIKE NVL(?, EFA.ASSETS_LOCATION))"
					+ " AND ((? IS NULL OR ACCV.COST_CENTER_CODE LIKE ?) OR \n"
					+ "      (? IS NULL OR ACCV.COST_CENTER_NAME LIKE ?))"

					+ " AND NOT EXISTS (" 
					+ " 	SELECT" 
					+ " 	NULL" 
					+ " 	FROM"
					+ " 	ETS_ITEM_MATCH_TF EIM" 
					+ " 	WHERE"
					+ " 	EIM.ASSET_ID = EFA.ASSET_ID)"

					+ " AND NOT EXISTS (" 
					+ " 	SELECT" 
					+ " 	NULL" 
					+ " 	FROM"
					+ " 	ETS_ITEM_MATCH_ASSIST_MIS EIMAM" 
					+ " 	WHERE"
					+ " 	EIMAM.ASSET_ID = EFA.ASSET_ID)"

					+ " AND EFA.ORGANIZATION_ID = ?" ;*/

		"SELECT EFA.ASSET_ID, EFA.DEPRECIATION_ACCOUNT, \n" +
		"       EFA.TAG_NUMBER, \n" +
		"       EFA.ASSETS_DESCRIPTION, \n" +
		"       EFA.MODEL_NUMBER, \n" +
		"       EFA.ASSETS_LOCATION, \n" +
		"       EFA.DATE_PLACED_IN_SERVICE, \n" +
		"       EFA.LIFE_IN_YEARS, \n" +
		"       EFA.COST, \n" +
		"       EFA.ASSET_NUMBER, \n" +
		"       EFA.BOOK_TYPE_CODE, \n" +
		"       EFA.ASSETS_LOCATION_CODE, \n" +
		"       AMD.DEPT_CODE, \n" +
		"       AMD.DEPT_NAME, \n" +
		"       EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 FA_CODE, \n" +
		"       EFA.FA_CATEGORY2 \n" +  
		"  FROM ETS_FA_ASSETS_TF EFA, AMS_MIS_EMPLOYEE AME, AMS_MIS_DEPT AMD \n" +
		" WHERE AME.DEPT_CODE *= AMD.DEPT_CODE \n" +
		"   AND AME.EMPLOYEE_NUMBER =* EFA.ASSIGNED_TO_NUMBER \n" +
		"   AND AME.COMPANY_CODE =* EFA.COMPANY_CODE \n" +
		"   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2) \n" +
		"   AND CONVERT(INT, EFA.COST) <> 0 \n" +
		"   AND EFA.TAG_NUMBER LIKE dbo.NVL(LTRIM(?), EFA.TAG_NUMBER) \n" +
		"   AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(LTRIM(?), EFA.ASSETS_DESCRIPTION) \n" +
		"   AND (EFA.ASSETS_LOCATION_CODE LIKE dbo.NVL(LTRIM(?), EFA.ASSETS_LOCATION_CODE) OR \n" +
		"        EFA.ASSETS_LOCATION LIKE dbo.NVL(LTRIM(?), EFA.ASSETS_LOCATION)) \n" +
		"   AND NOT EXISTS \n" +
		"       (SELECT NULL \n" +
		"          FROM ETS_ITEM_MATCH_TF EIM \n" +
		"         WHERE EIM.ASSET_ID = EFA.ASSET_ID) \n" +
		"   AND NOT EXISTS \n" +
		"       (SELECT NULL \n" +
		"          FROM ETS_ITEM_MATCH_ASSIST_MIS EIMAM \n" +
		"         WHERE EIMAM.ASSET_ID = EFA.ASSET_ID) \n" +
		"   AND NOT EXISTS \n" +
		"      (SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.ASSET_ID = EFA.ASSET_ID) \n" +
		"   AND EFA.ORGANIZATION_ID = ? \n" +
		"   AND AMD.COMPANY_CODE =* EFA.COMPANY_CODE \n" +
		"   AND ((LTRIM(?) IS NULL OR AMD.DEPT_CODE LIKE LTRIM(?)) OR \n" +
		"        (LTRIM(?) IS NULL OR AMD.DEPT_NAME LIKE LTRIM(?))) \n" ;
			
		sqlArgs.add(dto.getTagNumber());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(userAccount.getOrganizationId());

/*			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());
			sqlArgs.add(dto.getCostCenterName());*/
		
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getDeptName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
