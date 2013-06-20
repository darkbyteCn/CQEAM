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

/**
 * <p>
 * Title: SinoEAM
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007
 * </p>
 * <p>
 * Company: 北京思诺搏信息技术有限公司
 * </p>
 * 
 * @author 何睿
 * @version 0.1 Date: 2007-11-27
 */
public class ManualMatchMISModel extends AMSSQLProducer {
	public ManualMatchMISModel(BaseUserDTO userAccount, DTO dtoParameter) {
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
		if ("Y".equalsIgnoreCase(userAccount.getIsTd())) {
			String sqlStr = "SELECT "
					+ "  EFTA.ASSET_ID,\n"
					+ "  EFTA.TAG_NUMBER,\n"
					+ "  EFTA.ASSETS_DESCRIPTION,\n"
					+ "  EFTA.MODEL_NUMBER,\n"
					+ "  EFTA.UNIT_OF_MEASURE,\n"
					+ "  EFTA.FA_CATEGORY2,\n"
					// " EFTA.CURRENT_UNITS - EIMC.CURRENT_UNITS
					// NO_MATCH_UNITS,\n" +
					// //2009.4.24修改（SUHP）
					+ "  EFTA.CURRENT_UNITS NO_MATCH_UNITS,\n"
					+ "  EFTA.ASSETS_LOCATION,\n"
					+ "  EFTA.ASSIGNED_TO_NAME,\n"
					+ "  EFTA.DATE_PLACED_IN_SERVICE,\n"
					+ "  EFTA.PROJECT_NAME,\n"
					+ "  EFTA.ASSETS_CREATE_DATE,\n"
					+ "  EFTA.ASSETS_LOCATION_CODE,\n"
					+ "  EFTA.COST,\n"
                    + "  EFTA.SEGMENT1||'.'||EFTA.SEGMENT2||'.'||EFTA.SEGMENT3 FA_CODE\n"
					+ "  FROM \n"
					// " ETS_FA_ASSETS EFA, \n" +
					+ "  ETS_FA_ASSETS_TD EFTA, \n"
					// " ETS_ITEM_MATCH_COMPLET EIMC,\n" + //2009.4.24修改（SUHP）
					+ "  AMS_MIS_EMPLOYEE AME,\n "
					+ "  AMS_MIS_DEPT AMD\n"
					+ "  WHERE \n"
					// " EFA.ASSET_ID = EIMC.ASSET_ID\n" + //2009.4.24修改（SUHP）
					+ "  AME.DEPT_CODE = AMD.DEPT_CODE\n"
					+ "  AND AME.EMPLOYEE_NUMBER = EFTA.ASSIGNED_TO_NUMBER\n"
					// " AND EFA.CURRENT_UNITS > EIMC.CURRENT_UNITS \n" +
					// //2009.4.24修改（SUHP）
                    + "  AND AMD.COMPANY_CODE = EFTA.COMPANY_CODE\n"
				    + "  AND (EFTA.IS_RETIREMENTS = 0 OR EFTA.IS_RETIREMENTS = 2) \n"
					// 非报废
					+ "  AND EFTA.COST <> 0\n"
					+ "  AND NOT EXISTS "
					+ "		(SELECT 1 FROM ETS_ITEM_MATCH_TD EIM " 
					+ "       WHERE EIM.ASSET_ID = EFTA.ASSET_ID) \n"
					+ "         AND (? = '' OR EFTA.DATE_PLACED_IN_SERVICE >= ?)\n"
					+ "         AND (? = '' OR EFTA.DATE_PLACED_IN_SERVICE <= ?)\n"
					// 在ETS_NO_MATCH有的资产不参加匹配
					+ "  AND NOT EXISTS (SELECT 1 FROM ETS_NO_MATCH_TD ENM WHERE ENM.ASSET_ID = EFTA.ASSET_ID) \n"
					
					// 本次不需盘点范围
					+ "  AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFTA.SEGMENT2 = H.SEGMENT AND EFTA.SEGMENT3 = H.NAME) \n"
					
					+ "  AND (? = '' OR EFTA.ASSIGNED_TO_NAME LIKE ?) \n"
					+ "  AND ((? = '' OR EFTA.TAG_NUMBER LIKE ?) OR \n"
					+ "  	  (? = '' OR EFTA.ASSETS_DESCRIPTION LIKE ?) OR \n"
					+ "       (? = '' OR EFTA.MODEL_NUMBER LIKE ?)) \n"
					//" (? IS NULL OR EFA.ASSETS_LOCATION LIKE ?) )\n" +
					+ "  AND ( (? = '' OR EFTA.ASSETS_LOCATION LIKE ? ) \n"
					+ "        OR (? = '' OR EFTA.ASSETS_LOCATION_CODE LIKE ?)) \n";

			if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
				// sqlStr += "AND EFA.DEPRECIATION_ACCOUNT LIKE '%'||NVL(?,
				// EFA.DEPRECIATION_ACCOUNT)||'%'\n";
				sqlStr += "AND ((LTRIM(?) IS NULL OR AMD.DEPT_CODE LIKE ?) OR (LTRIM(?) IS NULL OR AMD.DEPT_NAME LIKE ?)) ";
			} else {
                sqlStr += "AND (? = '' OR EFTA.ASSETS_LOCATION_CODE LIKE ? ||'%')\n";
                //sqlStr += "AND ((? IS NULL OR AMD.DEPT_CODE LIKE ?)OR(? IS NULL OR AMD.DEPT_NAME LIKE ?)) ";
                // " AND EFA.CURRENT_UNITS =1\n"; //2009.4.24修改（SUHP）
			}
			sqlStr += "   AND EFTA.ORGANIZATION_ID = ? \n"
					+ "   ORDER BY EFTA.ASSETS_LOCATION_CODE ,EFTA.ASSETS_DESCRIPTION  DESC ";
			// sqlStr += ""
			try {
				sqlArgs.add(dto.getFromDate());
				sqlArgs.add(dto.getFromDate());
				sqlArgs.add(dto.getToDate());
				sqlArgs.add(dto.getToDate());
			} catch (CalendarException e) {
				e.printLog();
				throw new SQLModelException(e);
			}
			sqlArgs.add(dto.getAssignedToName());
			sqlArgs.add(dto.getAssignedToName());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocation());
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
		} else {
			String sqlStr = "SELECT  "
					+ "  EFA.ASSET_ID,\n"
					+ "  EFA.TAG_NUMBER,\n"
					+ "  EFA.ASSETS_DESCRIPTION,\n"
					+ "  EFA.MODEL_NUMBER,\n"
					+ "  EFA.UNIT_OF_MEASURE,\n"
					+ "  EFA.FA_CATEGORY2,\n"
					// " EFA.CURRENT_UNITS - EIMC.CURRENT_UNITS
					// NO_MATCH_UNITS,\n" +
					// //2009.4.24修改（SUHP）
					+ "  EFA.CURRENT_UNITS NO_MATCH_UNITS,\n"
					+ "  EFA.ASSETS_LOCATION,\n"
					+ "  EFA.ASSIGNED_TO_NAME,\n"
					+ "  EFA.DATE_PLACED_IN_SERVICE,\n"
					+ "  EFA.PROJECT_NAME,\n"
					+ "  EFA.ASSETS_CREATE_DATE,\n"
					+ "  EFA.ASSETS_LOCATION_CODE,\n"
					+ "  EFA.COST,\n" 
                    + "  EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 FA_CODE \n"
					+ "  FROM \n"
					+ "  ETS_FA_ASSETS EFA (INDEX ETS_FA_ASSETS_110708_N5), \n"
					// " ETS_ITEM_MATCH_COMPLET EIMC,\n" + //2009.4.24修改（SUHP）
					+ "  AMS_MIS_EMPLOYEE AME,\n "
					+ "  AMS_MIS_DEPT AMD\n"
					+ "  WHERE \n"
					// " EFA.ASSET_ID = EIMC.ASSET_ID\n" + //2009.4.24修改（SUHP）
					+ "  AME.DEPT_CODE = AMD.DEPT_CODE \n"
					+ "  AND AME.EMPLOYEE_NUMBER = EFA.ASSIGNED_TO_NUMBER\n"
					+ "  AND AMD.COMPANY_CODE = EFA.COMPANY_CODE\n"
					// " AND EFA.CURRENT_UNITS > EIMC.CURRENT_UNITS \n" +
					// //2009.4.24修改（SUHP）
					+ "  AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2) \n"
					// 非报废
					+ "  AND EFA.COST <> 0 \n"
					+ "  AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFA.ASSET_ID) \n"
					+ "         AND (? = '' OR EFA.DATE_PLACED_IN_SERVICE >= ?)\n"
					+ "         AND (? = '' OR EFA.DATE_PLACED_IN_SERVICE <= ?)\n"
					
					// 本次不需盘点范围
					+ "  AND NOT EXISTS (SELECT NULL FROM AMS_IGNORE_DATA H WHERE EFA.SEGMENT2 = H.SEGMENT AND EFA.SEGMENT3 = H.NAME) \n"
					
					// 在ETS_NO_MATCH有的资产不参加匹配
					+ "  AND NOT EXISTS (SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.ASSET_ID = EFA.ASSET_ID) \n"
					+ "  AND (? = '' OR EFA.ASSIGNED_TO_NAME LIKE ?) \n"
					+ "  AND ((? = '' OR EFA.TAG_NUMBER LIKE ?) OR\n"
					+ "  	  (? = '' OR EFA.ASSETS_DESCRIPTION LIKE ?) OR\n"
					+ "  	  (? = '' OR EFA.MODEL_NUMBER LIKE ?))\n"
					// " (? IS NULL OR EFA.ASSETS_LOCATION LIKE ?) )\n" +
					+ "  AND ((? = '' OR EFA.ASSETS_LOCATION LIKE ?)\n"
					+ "  	  OR (? = ''OR EFA.ASSETS_LOCATION_CODE LIKE ?))\n";

			if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
				// sqlStr += "AND EFA.DEPRECIATION_ACCOUNT LIKE '%'||NVL(?,
				// EFA.DEPRECIATION_ACCOUNT)||'%'\n";
				sqlStr += " AND ((LTRIM(?) IS NULL OR AMD.DEPT_CODE LIKE ?) OR (LTRIM(?) IS NULL OR AMD.DEPT_NAME LIKE ?)) \n";
			} else {
				//sqlStr += "AND ((? IS NULL OR AMD.DEPT_CODE LIKE ?)OR(? IS NULL OR AMD.DEPT_NAME LIKE ?)) ";
				//sqlStr += "AND EFA.ASSETS_LOCATION_CODE LIKE NVL(?, EFA.ASSETS_LOCATION_CODE)||'%'\n";
                sqlStr += "AND (? = '' OR EFA.ASSETS_LOCATION_CODE LIKE ? || '%' )\n";
				// " AND EFA.CURRENT_UNITS =1\n"; //2009.4.24修改（SUHP）
			}
			sqlStr += "   AND EFA.ORGANIZATION_ID = ? \n"
					+ "   ORDER BY EFA.ASSETS_LOCATION_CODE ,EFA.ASSETS_DESCRIPTION  DESC ";
			// sqlStr += ""
			try {

				sqlArgs.add(dto.getFromDate());
				sqlArgs.add(dto.getFromDate());
				sqlArgs.add(dto.getToDate());
				sqlArgs.add(dto.getToDate());
			} catch (CalendarException e) {
				e.printLog();
				throw new SQLModelException(e);
			}
			sqlArgs.add(dto.getAssignedToName());
			sqlArgs.add(dto.getAssignedToName());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getKey());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocation());
			sqlArgs.add(dto.getAssetsLocation());
			if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
				sqlArgs.add(dto.getResponsibilityDept());
				sqlArgs.add(dto.getResponsibilityDept());
				sqlArgs.add(dto.getResponsibilityDept());
				sqlArgs.add(dto.getResponsibilityDept());
			} else {
				sqlArgs.add(dto.getCountyCodeMis());
				sqlArgs.add(dto.getCountyCodeMis());
				//sqlArgs.add(dto.getResponsibilityDept());
				//sqlArgs.add(dto.getResponsibilityDept());
				//sqlArgs.add(dto.getResponsibilityDept());
				//sqlArgs.add(dto.getResponsibilityDept());
			}
			sqlArgs.add(userAccount.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		}

		return sqlModel;
	}
}
