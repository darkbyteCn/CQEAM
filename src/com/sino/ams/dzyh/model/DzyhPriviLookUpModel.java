package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.DzyhLookUpConstant;
import com.sino.ams.dzyh.dto.EamDhPriviDTO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 张星
 * @version 1.0
 */
public class DzyhPriviLookUpModel extends LookUpModel {
	private SfUserDTO user = null;
	private String proCode = "";

	public DzyhPriviLookUpModel(BaseUserDTO userAccount, DTO dtoParameter, LookUpProp lookProp) {
		super(userAccount, dtoParameter, lookProp);
		this.user = (SfUserDTO) userAccount;

	}

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig);
		this.proCode = servletConfig.getProvinceCode();
	}

	/**
	 * 功能：构造查询SQL。由具体需要LookUp操作的应用实现
	 */
	protected void produceSQLModel() {
		sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		String lookUpName = lookProp.getLookUpName();
		if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_COMPANY)) {////////////
			EtsOuCityMapDTO dto = (EtsOuCityMapDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " EOCM.ORGANIZATION_ID,"
					 + " EOCM.COMPANY_CODE,"
					 + " EOCM.COMPANY COMPANY_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE"
					 + " EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)";
			sqlArgs.add(dto.getCompany());
		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_PRI_DEPT)) {	//低值易耗品管理权限中查找部门
			EamDhPriviDTO dto = (EamDhPriviDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " AMD.COMPANY_CODE,"
					 + " EOCM.COMPANY COMPANY_NAME,"
					 + " AMD.DEPT_CODE,"
					 + " AMD.DEPT_NAME"
					 + " FROM"
					 + " AMS_MIS_DEPT    AMD,"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE"
					 + " AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND EOCM.ORGANIZATION_ID = ?"
					 + " AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)"
					 + " AND AMD.DEPT_NAME LIKE dbo.NVL(?, AMD.DEPT_NAME)";
			sqlArgs.add(dto.getOrgId());
			sqlArgs.add(dto.getCompanyName());
			sqlArgs.add(dto.getDeptName());
		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_PRI_USER)) {////////////
			EamDhPriviDTO dto = (EamDhPriviDTO) dtoParameter;
			sqlStr = "SELECT"
					 + " EOCM.COMPANY_CODE,"
					 + " EOCM.COMPANY COMPANY_NAME,"
					 + " SU.LOGIN_NAME,"
					 + " SU.USER_ID,"
					 + " SU.USERNAME USER_NAME,"
					 + " SU.EMPLOYEE_NUMBER"
					 + " FROM"
					 + " SF_USER         SU,"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE"
					 + " SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND (SU.DISABLE_DATE IS NULL OR SU.DISABLE_DATE = '' OR SU.DISABLE_DATE > GETDATE())"
					 + " AND SU.ORGANIZATION_ID = ?"
					 + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					 + " AND SU.LOGIN_NAME LIKE dbo.NVL(UPPER(?), SU.LOGIN_NAME)"
					 + " AND ( " + SyBaseSQLUtil.isNull() + "  OR SU.EMPLOYEE_NUMBER LIKE dbo.NVL(?, SU.EMPLOYEE_NUMBER))"
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " SF_USER_RIGHT SUR"
					 + " WHERE"
					 + " SUR.USER_ID = SU.USER_ID"
					 + " AND SUR.GROUP_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, SUR.GROUP_ID))))";
			sqlArgs.add(user.getOrganizationId());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getLoginName());
			sqlArgs.add(dto.getEmployeeNumber());
			sqlArgs.add(dto.getEmployeeNumber());
			if (proCode.equals("42")) {
				 sqlArgs.add("");
			}else{
			  sqlArgs.add(dto.getGroupId());
			}

		}
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
	}
}
