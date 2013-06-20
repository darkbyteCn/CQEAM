package com.sino.td.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 *
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-27
 */
public class ManualMatchTdMISModel extends AMSSQLProducer {
	public ManualMatchTdMISModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

//    public void setServletConfig(ServletConfigDTO servletConfig, String constant) {
//        super.setServletConfig(servletConfig);
////		this.constant = constant;
//    }

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig);    //To change body of overridden methods use File | Settings | File Templates.
	}

	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try{
            List sqlArgs = new ArrayList();

		EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
		String sqlStr = "SELECT " +
						"  EFTA.ASSET_ID,\n" +
						"  EFTA.TAG_NUMBER,\n" +
						"  EFTA.ASSETS_DESCRIPTION,\n" +
						"  EFTA.MODEL_NUMBER,\n" +
						"  EFTA.UNIT_OF_MEASURE,\n" +
						"  EFTA.FA_CATEGORY2,\n" +
//						"  EFTA.CURRENT_UNITS - EIMC.CURRENT_UNITS NO_MATCH_UNITS,\n" + //2009.4.24修改（SUHP）
                        "  EFTA.CURRENT_UNITS NO_MATCH_UNITS,\n" +
                        "  EFTA.ASSETS_LOCATION,\n" +
						"  EFTA.ASSIGNED_TO_NAME," +
						"  EFTA.DATE_PLACED_IN_SERVICE," +
						"  EFTA.PROJECT_NAME,\n" +
						"  EFTA.ASSETS_CREATE_DATE," +
						"  EFTA.ASSETS_LOCATION_CODE," +
						"  EFTA.COST" +
						"  FROM " +
						//"  ETS_FA_ASSETS EFA, \n" +
						"  ETS_FA_ASSETS_TD EFTA, \n" +
//						"  ETS_ITEM_MATCH_COMPLET EIMC,\n" +  //2009.4.24修改（SUHP）
						"  AMS_MIS_EMPLOYEE AME,\n " +
						"  AMS_MIS_DEPT AMD\n" +
						"  WHERE " +
//						"  EFA.ASSET_ID = EIMC.ASSET_ID\n" +   //2009.4.24修改（SUHP）
						"  AME.DEPT_CODE *= AMD.DEPT_CODE\n" +
						"  AND EFTA.ASSIGNED_TO_NUMBER *= AME.EMPLOYEE_NUMBER  \n" +
//						"  AND EFA.CURRENT_UNITS > EIMC.CURRENT_UNITS \n" +  //2009.4.24修改（SUHP）
						"  AND (EFTA.IS_RETIREMENTS = 0 OR EFTA.IS_RETIREMENTS = 2) \n" +    //非报废
                        "  AND EFTA.COST <> 0\n" +
                        "  AND NOT EXISTS (SELECT 1 FROM ETS_ITEM_MATCH_TD EIM WHERE EIM.ASSET_ID = EFTA.ASSET_ID) \n" +
                "         AND EFTA.DATE_PLACED_IN_SERVICE>=ISNULL(?,EFTA.DATE_PLACED_IN_SERVICE)\n" +
                "         AND EFTA.DATE_PLACED_IN_SERVICE<=ISNULL(?,EFTA.DATE_PLACED_IN_SERVICE)" +
						"  AND NOT EXISTS (SELECT 1 FROM ETS_NO_MATCH_TD ENM WHERE ENM.ASSET_ID = EFTA.ASSET_ID) \n" +  //在ETS_NO_MATCH有的资产不参加匹配
						"  AND (" + SyBaseSQLUtil.isNull() + " OR EFTA.ASSIGNED_TO_NAME LIKE ?)    " +
						"  AND (EFTA.TAG_NUMBER LIKE dbo.NVL(?, EFTA.TAG_NUMBER) OR\n" +
						"  EFTA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFTA.ASSETS_DESCRIPTION) OR\n" +
						"  EFTA.MODEL_NUMBER LIKE dbo.NVL(?, EFTA.MODEL_NUMBER))\n" +
//                        "  (" + SyBaseSQLUtil.isNull() + " OR EFA.ASSETS_LOCATION LIKE ?) )\n" +
						"  AND (EFTA.ASSETS_LOCATION like dbo.NVL(?, EFTA.ASSETS_LOCATION)\n"+
						"  or EFTA.ASSETS_LOCATION_CODE like dbo.NVL(?, EFTA.ASSETS_LOCATION_CODE))\n";

		if (servletConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_JIN)) {
//			sqlStr += "AND EFA.DEPRECIATION_ACCOUNT LIKE '%'||dbo.NVL(?, EFA.DEPRECIATION_ACCOUNT)||'%'\n";
			sqlStr += " AND ((" + SyBaseSQLUtil.isNull() + " OR AMD.DEPT_CODE LIKE ?)OR(" + SyBaseSQLUtil.isNull() + " OR AMD.DEPT_NAME LIKE ?)) ";
		} else {
			sqlStr += "AND EFTA.ASSETS_LOCATION_CODE LIKE dbo.NVL(?, EFTA.ASSETS_LOCATION_CODE)||'%'\n";
//                    "  AND  EFA.CURRENT_UNITS =1\n"; //2009.4.24修改（SUHP）
		}
		sqlStr += "   AND EFTA.ORGANIZATION_ID = ? \n" +
				 "   ORDER BY EFTA.ASSETS_LOCATION_CODE ,EFTA.ASSETS_DESCRIPTION  DESC ";
//        sqlStr += ""
		sqlArgs.add(dto.getFromDate());
		sqlArgs.add(dto.getToDate());
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
		sqlModel.setArgs(sqlArgs);  }catch(CalendarException e){
            e.printLog();
            throw new SQLModelException(e);
        }
		return sqlModel;
	}
}
