package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

public class TFManualMatchMISModel extends AMSSQLProducer {
	public TFManualMatchMISModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	// public void setServletConfig(ServletConfigDTO servletConfig, String
	// constant)
	// {
	// super.setServletConfig(servletConfig);
	// // this.constant = constant;
	// }

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig); // To change body of
		// overridden methods use
		// File | Settings | File
		// Templates.
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
			String sqlStr = 
			      "  SELECT "
				+ "  EFA.ASSET_ID, \n"
				+ "  EFA.TAG_NUMBER, \n"
				+ "  EFA.ASSETS_DESCRIPTION, \n"
				+ "  EFA.MODEL_NUMBER, \n"
				+ "  EFA.UNIT_OF_MEASURE, \n"
				+ "  EFA.FA_CATEGORY2, \n"
				// " EFA.CURRENT_UNITS - EIMC.CURRENT_UNITS
				// NO_MATCH_UNITS,\n" +
				// //2009.4.24修改（SUHP）
				+ "  EFA.CURRENT_UNITS NO_MATCH_UNITS, \n"
				+ "  EFA.ASSETS_LOCATION, \n"
				+ "  EFA.ASSIGNED_TO_NAME, \n"
				+ "  EFA.DATE_PLACED_IN_SERVICE, \n"
				+ "  EFA.PROJECT_NAME, \n"
				+ "  EFA.ASSETS_CREATE_DATE, \n"
				+ "  EFA.ASSETS_LOCATION_CODE, \n"
				+ "  EFA.COST, \n" 
                + "  EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' || EFA.SEGMENT3 FA_CODE \n"
				+ "  FROM \n"
				+ "  ETS_FA_ASSETS_TF EFA, \n"
				// " ETS_ITEM_MATCH_COMPLET EIMC,\n" + //2009.4.24修改（SUHP）
				+ "  AMS_MIS_EMPLOYEE AME, \n "
				+ "  AMS_MIS_DEPT AMD \n"
				+ "  WHERE \n"

				// " EFA.ASSET_ID = EIMC.ASSET_ID\n" + //2009.4.24修改（SUHP）
				+ "  AME.DEPT_CODE *= AMD.DEPT_CODE \n"
				+ "  AND AME.EMPLOYEE_NUMBER =* EFA.ASSIGNED_TO_NUMBER \n"
				+ "  AND AME.COMPANY_CODE =* EFA.COMPANY_CODE \n"
				// " AND EFA.CURRENT_UNITS > EIMC.CURRENT_UNITS \n" +
				// //2009.4.24修改（SUHP）
				+ "  AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2) \n"
				// 非报废
				+ "  AND EFA.COST <> 0 \n"
				+ "  AND NOT EXISTS \n" 
				+ "		(SELECT 1 FROM ETS_ITEM_MATCH_TF EIM \n"
				+ "		  WHERE EIM.ASSET_ID = EFA.ASSET_ID) \n"
				+ "         AND EFA.DATE_PLACED_IN_SERVICE >= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE) \n"
				+ "         AND EFA.DATE_PLACED_IN_SERVICE <= ISNULL(?, EFA.DATE_PLACED_IN_SERVICE) \n"
				+ "  AND NOT EXISTS \n" 
				+ "		(SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.ASSET_ID = EFA.ASSET_ID) \n"
				// 在ETS_NO_MATCH有的资产不参加匹配
				+ "  AND (LTRIM(?) IS NULL OR EFA.ASSIGNED_TO_NAME LIKE LTRIM(?)) \n"
				+ "  AND (EFA.TAG_NUMBER LIKE dbo.NVL(LTRIM(?), EFA.TAG_NUMBER) OR \n"
				+ "  	  EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(LTRIM(?), EFA.ASSETS_DESCRIPTION) OR \n"
				+ "       EFA.MODEL_NUMBER LIKE dbo.NVL(LTRIM(?), EFA.MODEL_NUMBER)) \n"
				// " (? IS NULL OR EFA.ASSETS_LOCATION LIKE ?) )\n" +
				+ "  AND EFA.ASSETS_LOCATION = dbo.NVL(LTRIM(?), EFA.ASSETS_LOCATION) \n"  ;
//				+ "  or EFA.ASSETS_LOCATION_CODE like NVL(?, EFA.ASSETS_LOCATION_CODE))\n";

			if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
				// sqlStr += "AND EFA.DEPRECIATION_ACCOUNT LIKE '%'||NVL(?,
				// EFA.DEPRECIATION_ACCOUNT)||'%'\n";
				sqlStr += " AND ((LTRIM(?) IS NULL OR AMD.DEPT_CODE LIKE LTRIM(?)) OR (LTRIM(?) IS NULL OR AMD.DEPT_NAME LIKE LTRIM(?))) \n";
			} else {
//                sqlStr += " AND ((? IS NULL OR AMD.DEPT_CODE LIKE ?)OR(? IS NULL OR AMD.DEPT_NAME LIKE ?)) ";
//                sqlStr += "AND EFA.ASSETS_LOCATION_CODE LIKE NVL(?, EFA.ASSETS_LOCATION_CODE)||'%'\n";
                sqlStr += " AND (LTRIM(?) IS NULL OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) LIKE LTRIM(?)) \n";
				// " AND EFA.CURRENT_UNITS =1\n"; //2009.4.24修改（SUHP）
			}
			sqlStr += "   AND EFA.ORGANIZATION_ID = ? \n"    ;
//					+ "   ORDER BY EFA.ASSETS_LOCATION_CODE ,EFA.ASSETS_DESCRIPTION DESC ";
			// sqlStr += ""
			try {
				if (dto.getFromDate().toString().equals("")) {
					sqlArgs.add(null);
				} else {
					sqlArgs.add(dto.getFromDate());
				}
				if (dto.getToDate().toString().equals("")) {
					sqlArgs.add(null);
				} else {
					sqlArgs.add(dto.getToDate());
				}
				//sqlArgs.add(dto.getFromDate());
				//sqlArgs.add(dto.getToDate());
			} catch (CalendarException e) {
				e.printLog();
				throw new SQLModelException(e);
			}
			sqlArgs.add(dto.getAssignedToName());
			sqlArgs.add(dto.getAssignedToName());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getAssetsLocation());
//			sqlArgs.add(dto.getAssetsLocation());
			if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
				sqlArgs.add(dto.getResponsibilityDept());
				sqlArgs.add(dto.getResponsibilityDept());
				sqlArgs.add(dto.getResponsibilityDept());
				sqlArgs.add(dto.getResponsibilityDept());
			} else {
				sqlArgs.add(dto.getCountyCodeMis());
				sqlArgs.add(dto.getCountyCodeMis());
//                sqlArgs.add(dto.getResponsibilityDept());
//				sqlArgs.add(dto.getResponsibilityDept());
//				sqlArgs.add(dto.getResponsibilityDept());
//				sqlArgs.add(dto.getResponsibilityDept());
			}
			sqlArgs.add(userAccount.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
