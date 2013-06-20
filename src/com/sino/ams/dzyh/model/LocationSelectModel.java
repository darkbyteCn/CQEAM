package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LocationSelectModel extends AMSSQLProducer {

	public LocationSelectModel(BaseUserDTO userAccount, EamDhCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：根据执行部门代码和地点专业选择地点
	 * @return SQLModel
	 */
	public SQLModel getLocationsModel(){
		SQLModel sqlModel = new SQLModel();
		EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO)dtoParameter;
		List sqlArgs = new ArrayList();
		String impDeptCode = dto.getImpDeptCode();
		impDeptCode = StrUtil.replaceStr(impDeptCode, ",", "', '");
		String objectCategory = dto.getObjectCategory();
		String sqlStr = "";
		if(objectCategory.equals(LvecDicts.LOCATION_CATEGORY_DZYH)){//低值易耗地点选择
			sqlStr = "SELECT"
					 + " EO.LOCATION_CODE,"
					 + " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
					 + " EO.WORKORDER_OBJECT_NAME LOCATION_NAME,"
					 + " AMD.DEPT_CODE IMP_DEPT_CODE,"
					 + " AMD.DEPT_NAME IMP_DEPT_NAME,"
					 + " EDP.USER_ID IMPLEMENT_BY,"
					 + " SU.USERNAME IMPLEMENT_USER,"
					 + " '" + dto.getOrderType() + "' ORDER_TYPE,"
					 + " '" + dto.getOrderTypeValue() + "' ORDER_TYPE_VALUE"
					 + " FROM"
					 + " AMS_MIS_DEPT AMD,"
					 + " ETS_OBJECT   EO,"
					 + " EAM_DH_PRIVI EDP,"
					 + " SF_USER      SU"
					 + " WHERE"
					 + " EO.DEPT_CODE = AMD.DEPT_CODE"
					 + " AND AMD.DEPT_CODE *= EDP.DEPT_CODE"
					 + " AND EDP.USER_ID *= SU.USER_ID"
					 + " AND EDP.DEFAULTFLAG(+) = 'Y'"
					 + " AND EO.OBJECT_CATEGORY = ?"
					 + " AND AMD.DEPT_CODE IN ('" + impDeptCode + "')"
					 + " AND EO.ORGANIZATION_ID = ?";
		} else {
			sqlStr = "SELECT"
					 + " EO.LOCATION_CODE,"
					 + " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
					 + " EO.WORKORDER_OBJECT_NAME LOCATION_NAME,"
					 + " AMD.DEPT_CODE IMP_DEPT_CODE,"
					 + " AMD.DEPT_NAME IMP_DEPT_NAME,"
					 + " (SELECT"
					 + " SU.USER_ID"
					 + " FROM"
					 + " SF_USER SU"
					 + " WHERE"
					 + " EXISTS ("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " SF_USER_RIGHT SUR,"
					 + " SF_ROLE       SR"
					 + " WHERE  SU.USER_ID = SUR.USER_ID"
					 + " AND SUR.ROLE_ID = SR.ROLE_ID"
					 + " AND SR.ROLE_NAME <> '" + servletConfig.getSysAdminRole() + "'"
					 + " AND SR.ROLE_NAME <> '" + servletConfig.getCityAdminRole() + "'"
					 + " AND SR.ROLE_NAME = '仪器仪表管理员')"
					 + " AND SU.ORGANIZATION_ID = EO.ORGANIZATION_ID"
					 + " AND ROWNUM = 1) IMPLEMENT_BY,"
					 + " (SELECT"
					 + " SU.USERNAME"
					 + " FROM"
					 + " SF_USER SU"
					 + " WHERE"
					 + " EXISTS ("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " SF_USER_RIGHT SUR,"
					 + " SF_ROLE       SR"
					 + " WHERE"
					 + " SU.USER_ID = SUR.USER_ID"
					 + " AND SUR.ROLE_ID = SR.ROLE_ID"
					 + " AND SR.ROLE_NAME <> '" + servletConfig.getSysAdminRole() + "'"
					 + " AND SR.ROLE_NAME <> '" + servletConfig.getCityAdminRole() + "'"
					 + " AND SR.ROLE_NAME = '仪器仪表管理员')"
					 + " AND SU.ORGANIZATION_ID = EO.ORGANIZATION_ID"
					 + " AND ROWNUM = 1) IMPLEMENT_USER,"
					 + " '" + dto.getOrderType() + "' ORDER_TYPE,"
					 + " '" + dto.getOrderTypeValue() + "' ORDER_TYPE_VALUE"
					 + " FROM"
					 + " AMS_MIS_DEPT AMD,"
					 + " ETS_OBJECT   EO"
					 + " WHERE"
					 + " EO.DEPT_CODE = AMD.DEPT_CODE"
					 + " AND EO.OBJECT_CATEGORY = ?"
					 + " AND AMD.DEPT_CODE IN ('" + impDeptCode + "')"
					 + " AND EO.ORGANIZATION_ID = ?";
		}
		sqlArgs.add(objectCategory);
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
