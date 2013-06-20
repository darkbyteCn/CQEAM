package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 *
 * @author 于士博
 * @version 0.1
 *          Date: 2008-12-08
 */
public class ManualCtMatchMISModel extends AMSSQLProducer {
	public ManualCtMatchMISModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

//    public void setServletConfig(ServletConfigDTO servletConfig, String constant) {
//        super.setServletConfig(servletConfig);
//		this.constant = constant;
//    }

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig);    //To change body of overridden methods use File | Settings | File Templates.
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
		String sqlStr = "SELECT " +
						"  EFCA.ASSET_ID,\n" +
						"  EFCA.TAG_NUMBER,\n" +
						"  EFCA.ASSETS_DESCRIPTION,\n" +
						"  EFCA.MODEL_NUMBER,\n" +
						"  EFCA.UNIT_OF_MEASURE,\n" +
						"  EFCA.FA_CATEGORY2,\n" +
						"  (EFCA.CURRENT_UNITS - EIMC.CURRENT_UNITS) NO_MATCH_UNITS,\n" +
						"  EFCA.ASSETS_LOCATION,\n" +
						"  EFCA.ASSIGNED_TO_NAME," +
						"  EFCA.DATE_PLACED_IN_SERVICE," +
						"  EFCA.PROJECT_NAME,\n" +
						"  EFCA.ASSETS_CREATE_DATE," +
						"  EFCA.ASSETS_LOCATION_CODE," +
						"  AMD.DEPT_NAME," +
//						"  EII.BARCODE," +
						"  EFCA.ORIGINAL_COST," +
						"  EFCA.COST" +
						"  FROM " +
						"  ETS_FA_ASSETS EFCA, \n" +
						"  ETS_ITEM_MATCH_COMPLET EIMC,\n" +
						"  AMS_MIS_EMPLOYEE AME,\n " +
//						"  ETS_ITEM_INFO EII," +
						"  AMS_MIS_DEPT AMD\n" +
						"  WHERE " +
						"  EFCA.ASSET_ID = EIMC.ASSET_ID\n" +
//						"  AND EII.ASSET_ID = EFCA.ASSET_ID\n" +
						"  AND AME.DEPT_CODE *= AMD.DEPT_CODE\n" +
						"  AND EFCA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER\n" +
						"  AND EFCA.CURRENT_UNITS > EIMC.CURRENT_UNITS \n" +
						"  AND (EFCA.IS_RETIREMENTS = 0 OR EFCA.IS_RETIREMENTS = 2) \n" +    //非报废
						"  AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFCA.ASSET_ID) \n" +
						"  AND NOT EXISTS (SELECT 1 FROM ETS_NO_MATCH ENM WHERE ENM.ASSET_ID = EFCA.ASSET_ID) \n" +  //在ETS_NO_MATCH有的资产不参加匹配
						"  AND ( " + SyBaseSQLUtil.isNull() + "  OR EFCA.ASSIGNED_TO_NAME LIKE ?)    " +
						"  AND (EFCA.TAG_NUMBER LIKE dbo.NVL(?, EFCA.TAG_NUMBER) OR\n" +
						"  EFCA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFCA.ASSETS_DESCRIPTION) OR\n" +
						"  EFCA.MODEL_NUMBER LIKE dbo.NVL(?, EFCA.MODEL_NUMBER))\n" +
//                        "  ( " + SyBaseSQLUtil.isNull() + "  OR EFCA.ASSETS_LOCATION LIKE ?) )\n" +
						"  AND (EFCA.ASSETS_LOCATION like dbo.NVL(?, EFCA.ASSETS_LOCATION)\n"+
						"  OR EFCA.ASSETS_LOCATION_CODE like dbo.NVL(?, EFCA.ASSETS_LOCATION_CODE))\n";

		if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
//			sqlStr += "AND EFCA.DEPRECIATION_ACCOUNT LIKE '%'||dbo.NVL(?, EFCA.DEPRECIATION_ACCOUNT)||'%'\n";
			sqlStr += " AND (( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE LIKE ?) OR ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_NAME LIKE ?)) ";
		} else {
			sqlStr += "AND EFCA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFCA.ASSETS_LOCATION_CODE)||'%'\n";
		}
		sqlStr += "   AND EFCA.ORGANIZATION_ID = ? \n" +
				 "   ORDER BY EFCA.ASSETS_LOCATION_CODE ,EFCA.ASSETS_DESCRIPTION  DESC ";
//        sqlStr += ""
		sqlArgs.add(dto.getAssignedToName());
		sqlArgs.add(dto.getAssignedToName());
		sqlArgs.add(dto.getKey());
		sqlArgs.add(dto.getKey());
		sqlArgs.add(dto.getKey());
		sqlArgs.add(dto.getAssetsLocation());
		sqlArgs.add(dto.getAssetsLocation());
		  if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
			  sqlArgs.add(dto.getResponsibilityDept());
			  sqlArgs.add(dto.getResponsibilityDept());
			  sqlArgs.add(dto.getResponsibilityDept());
			  sqlArgs.add(dto.getResponsibilityDept());
		} else {
			 sqlArgs.add(dto.getCountyCodeMis());
		}
		  
		  
		sqlArgs.add(userAccount.getOrganizationId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
