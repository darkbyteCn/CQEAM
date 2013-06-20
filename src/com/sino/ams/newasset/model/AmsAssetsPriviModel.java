package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: AmsAssetsPriviModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsPriviModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsPriviModel extends AMSSQLProducer {
	private String constant = "";
	/**
	 * 功能：资产管理权限表(EAM) AMS_ASSETS_PRIVI 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsPriviDTO 本次操作的数据
	 */
	public AmsAssetsPriviModel(SfUserDTO userAccount, AmsAssetsPriviDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public void setServletConfig(ServletConfigDTO servletConfig, String constant) {
		super.setServletConfig(servletConfig);
		this.constant = constant;
	}


	/**
	 * 功能：框架自动生成资产管理权限表(EAM) AMS_ASSETS_PRIVI数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsPriviDTO amsAssetsPrivi = (AmsAssetsPriviDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_ASSETS_PRIVI("
						+ " PRIVI_ID,"
						+ " USER_ID,"
						+ " DEPT_CODE,"
						+ " ROLE_ID,"
						+ " COMPANY_CODE,"
						+ " PROVINCE_CODE,"
						+ " CREATED_BY"
						+ ") VALUES ("
						+ "  NEWID(), ?, ?, ?, ?, ?, ?)";

		sqlArgs.add(amsAssetsPrivi.getUserId());
		sqlArgs.add(amsAssetsPrivi.getDeptCode());
		sqlArgs.add(amsAssetsPrivi.getRoleId());
		sqlArgs.add(amsAssetsPrivi.getCompanyCode());
		sqlArgs.add(amsAssetsPrivi.getProvinceCode());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成资产管理权限表(EAM) AMS_ASSETS_PRIVI页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		String provinceCode = servletConfig.getProvinceCode();
		String proCompanyCode = servletConfig.getProCompanyCode();
		String proCompanyName = servletConfig.getProCompanyName();
		String roleName = dto.getRoleName();
		String userName = dto.getUserName();
		if(userName.equals("")){
			userName=null;
		}
		String sqlStr = "";
		if (roleName.indexOf(servletConfig.getDeptAssetsMgr()) > -1) {//部门资产管理员
			sqlStr = "SELECT"
					 + " AAP.PRIVI_ID,"
					 + " EOCM.COMPANY_CODE,"
					 + " EOCM.COMPANY COMPANY,"
					 + " AMD.DEPT_CODE,"
					 + " AMD.DEPT_NAME,"
					 + " SR.ROLE_NAME ROLE_NAME,"
					 + " SU.USERNAME USER_NAME,"
					 + " SU.LOGIN_NAME"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP,"
					 + " SF_USER          SU,"
					 + " SF_ROLE          SR,"
					 + " AMS_MIS_DEPT     AMD,"
					 + " ETS_OU_CITY_MAP  EOCM"
					 + " WHERE"
					 + " AAP.USER_ID = SU.USER_ID"
					 + " AND AAP.ROLE_ID = SR.ROLE_ID"
					 + " AND AAP.DEPT_CODE= AMD.DEPT_CODE"
					 + " AND AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND CONVERT(VARCHAR,AMD.DEPT_CODE)= dbo.NVL(?, AMD.DEPT_CODE)"
					 + " AND SU.USERNAME = dbo.NVL(?, SU.USERNAME)"
					 + " AND SR.ROLE_NAME = ?"
					 + " AND CONVERT(VARCHAR ,EOCM.ORGANIZATION_ID)= dbo.NVL(?, CONVERT(VARCHAR ,EOCM.ORGANIZATION_ID))";
			sqlArgs.add(dto.getDeptCode()+"");
			
			sqlArgs.add(userName);
			sqlArgs.add(roleName);
			sqlArgs.add(Integer.toString(dto.getOrganizationId()));

		} else if (roleName.indexOf(servletConfig.getCompAssetsMgr()) > -1) {//公司资产管理员
			sqlStr = "SELECT"
					 + " AAP.PRIVI_ID,"
					 + " EOCM.COMPANY_CODE,"
					 + " EOCM.COMPANY COMPANY,"
					 + " '' DEPT_CODE,"
					 + " '' DEPT_NAME,"
					 + " SR.ROLE_NAME ROLE_NAME,"
					 + " SU.USERNAME USER_NAME,"
					 + " SU.LOGIN_NAME"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP,"
					 + " SF_USER          SU,"
					 + " SF_ROLE          SR,"
					 + " ETS_OU_CITY_MAP  EOCM"
					 + " WHERE"
					 + " AAP.USER_ID = SU.USER_ID"
					 + " AND AAP.ROLE_ID = SR.ROLE_ID"
					 + " AND AAP.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND EOCM.COMPANY_CODE = dbo.NVL(?, EOCM.COMPANY_CODE)"
					 + " AND SU.USERNAME = dbo.NVL(?, SU.USERNAME)"
					 + " AND SR.ROLE_NAME = ?"
					 + " AND ( " + SyBaseSQLUtil.isNull() + " OR AAP.DEPT_CODE=?) "
					 + " AND CONVERT(VARCHAR ,EOCM.ORGANIZATION_ID)= dbo.NVL(?, CONVERT(VARCHAR ,EOCM.ORGANIZATION_ID))";
		sqlArgs.add(dto.getCompanyCode());
		
			sqlArgs.add(userName);
			sqlArgs.add(roleName);
			sqlArgs.add(dto.getDeptCode()+"");
			sqlArgs.add(dto.getDeptCode()+"");
			sqlArgs.add(dto.getOrganizationId()+"");
		} else if (roleName.indexOf(servletConfig.getProvAssetsMgr()) > -1) {//全省资产管理员
			sqlStr = "SELECT"
					 + " AAP.PRIVI_ID,"
					 + " '" + proCompanyCode + "' COMPANY_CODE,"
					 + " '" + proCompanyName + "' COMPANY,"
					 + " '' DEPT_CODE,"
					 + " '' DEPT_NAME,"
					 + " SR.ROLE_NAME ROLE_NAME,"
					 + " SU.USERNAME USER_NAME,"
					 + " SU.LOGIN_NAME"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP,"
					 + " SF_USER          SU,"
					 + " SF_ROLE          SR"
					 + " WHERE"
					 + " AAP.USER_ID = SU.USER_ID"
					 + " AND AAP.ROLE_ID = SR.ROLE_ID"
					 + " AND SU.USERNAME = dbo.NVL(?, SU.USERNAME)"
					 + " AND SR.ROLE_NAME = ?"
					 + " AND ( " + SyBaseSQLUtil.isNull() + " OR AAP.COMPANY_CODE=?) "
					 +  " AND ( " + SyBaseSQLUtil.isNull() + " OR AAP.DEPT_CODE=?) "
					 ;
			sqlArgs.add(userName);
			sqlArgs.add(roleName);
			sqlArgs.add(proCompanyCode);
			sqlArgs.add(proCompanyCode);
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getDeptCode());
		} else if (roleName.indexOf(servletConfig.getMtlAssetsMgr()) > -1) {//专业资产管理员
			sqlStr = "SELECT"
					 + " AAP.PRIVI_ID,"
//					 + " '" + proCompanyCode + "' COMPANY_CODE,"
//					 + " '" + proCompanyName + "' COMPANY,"
                     + " EOCM.COMPANY_CODE,\n"
                     + " EOCM.COMPANY COMPANY,"
					 + " '' DEPT_CODE,"
					 + " '' DEPT_NAME,"
					 + " SR.ROLE_NAME||'('||EFV.VALUE||')' ROLE_NAME,"
					 + " SU.USERNAME USER_NAME,"
					 + " SU.LOGIN_NAME"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI   AAP,"
					 + " SF_USER            SU,"
					 + " SF_ROLE            SR,"
					 + " ETS_FLEX_VALUE_SET EFVS,"
					 + " ETS_FLEX_VALUES    EFV,"
                     + " ETS_OU_CITY_MAP    EOCM"
					 + " WHERE"
					 + " AAP.USER_ID = SU.USER_ID"
					 + " AND AAP.ROLE_ID = SR.ROLE_ID"
					 + " AND AAP.FA_CATEGORY_CODE = EFV.CODE"
					 + " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                     + " AND EOCM.COMPANY_CODE = AAP.COMPANY_CODE"
					 + " AND EFVS.CODE = ?"
					 + " AND SU.USERNAME = dbo.NVL(?, SU.USERNAME)"
					 + " AND SR.ROLE_NAME = ?"
					 + " AND CONVERT(VARCHAR ,EOCM.ORGANIZATION_ID) = dbo.NVL(?, CONVERT(VARCHAR ,EOCM.ORGANIZATION_ID))"
					 + " AND  (" + SyBaseSQLUtil.isNull() + " OR CONVERT(VARCHAR,AAP.DEPT_CODE) =?)";
			sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
			sqlArgs.add(userName);
			sqlArgs.add(roleName);
			sqlArgs.add(Integer.toString(userAccount.getOrganizationId()));
			sqlArgs.add(dto.getDeptCode()+"");
			sqlArgs.add(dto.getDeptCode()+"");
		} else if (roleName.equals("")) {
			sqlStr = "SELECT"
					 + " AAP.PRIVI_ID,"
					 + " EOCM.COMPANY_CODE,"
					 + " EOCM.COMPANY COMPANY,"
					 + " AMD.DEPT_CODE,"
					 + " AMD.DEPT_NAME,"
					 + " SR.ROLE_NAME || '(' || EFV.VALUE || ')' ROLE_NAME,"
					 + " SU.USERNAME USER_NAME,"
					 + " SU.LOGIN_NAME"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP,"
					 + " SF_USER          SU,"
					 + " SF_ROLE          SR,"
					 + " AMS_MIS_DEPT     AMD,"
                     + " ETS_FLEX_VALUE_SET EFVS,"
					 + " ETS_FLEX_VALUES    EFV,"
					 + " ETS_OU_CITY_MAP  EOCM"
					 + " WHERE"
					 + " AAP.ROLE_ID = SR.ROLE_ID"
					 + " AND AAP.USER_ID = SU.USER_ID"
					 + " AND AAP.COMPANY_CODE *= EOCM.COMPANY_CODE"
					 + " AND AAP.DEPT_CODE *= AMD.DEPT_CODE"
                     + " AND AAP.FA_CATEGORY_CODE *= EFV.CODE"
					 + " AND EFV.FLEX_VALUE_SET_ID *= EFVS.FLEX_VALUE_SET_ID"
					 + " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFVS.CODE= ? )"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR CONVERT(VARCHAR ,EOCM.ORGANIZATION_ID) = CONVERT(VARCHAR ,?))"
					 + " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ?)"
					+ " AND SU.USERNAME = dbo.NVL(?, SU.USERNAME)"
					 + " AND AAP.PROVINCE_CODE = CONVERT(VARCHAR ,?)";
            sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
            sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
			sqlArgs.add(dto.getOrganizationId()+"");
			sqlArgs.add(dto.getOrganizationId()+"");
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(userName);
			sqlArgs.add(provinceCode);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取权限数据删除的SQL。
	 * @param priviIds String[]
	 * @return SQLModel
	 */
	public SQLModel getPriviDeleteModel(String[] priviIds) {
		SQLModel sqlModel = new SQLModel();
		String priviId = "";
		if (priviIds != null) {
			priviId = ArrUtil.arrToSqlStr(priviIds);
		}
		String sqlStr = "DELETE FROM"
						+ " AMS_ASSETS_PRIVI AAP"
						+ " WHERE"
						+ " AAP.PRIVI_ID IN (" + priviId + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：获取资产管理员权限角色
	 * @return SQLModel
	 */
	public SQLModel getPriviRoleModel() {
		SQLModel sqlModel = new SQLModel();
		String assetsMgr = "'" + servletConfig.getDeptAssetsMgr() + "', ";
		assetsMgr += "'" + servletConfig.getCompAssetsMgr() + "', ";
		assetsMgr += "'" + servletConfig.getMtlAssetsMgr() + "', ";
		if (userAccount.isSysAdmin() && userAccount.isProvinceUser()) {
			assetsMgr += "'" + servletConfig.getProvAssetsMgr() + "'";
		} else {
			assetsMgr = assetsMgr.substring(0, assetsMgr.length() - 2);
		}
		String sqlStr = "SELECT"
						+ " SR.ROLE_ID,"
						+ " SR.ROLE_NAME ROLE_NAME"
						+ " FROM"
						+ " SF_ROLE SR"
						+ " WHERE"
						+ " SR.ROLE_NAME IN ("
						+ assetsMgr
						+ ")";
		sqlStr += " ORDER BY"
				+ " SR.ROLE_NAME";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：获取第一个资产管理员角色
	 * @return SQLModel
	 */
	public SQLModel getFirstRoleModel() {
		SQLModel sqlModel = new SQLModel();
		String assetsMgr = "'" + servletConfig.getDeptAssetsMgr() + "', ";
		assetsMgr += "'" + servletConfig.getCompAssetsMgr() + "', ";
		assetsMgr += "'" + servletConfig.getMtlAssetsMgr() + "', ";
		if (!userAccount.isProvinceUser()) {
			assetsMgr += "'" + servletConfig.getProvAssetsMgr() + "'";
		} else {
			assetsMgr = assetsMgr.substring(0, assetsMgr.length() - 2);
		}
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " SF_ROLE SR"
						+ " WHERE"
						+ " SR.ROLE_NAME IN ("
						+ assetsMgr
						+ ")";
		sqlStr += " ORDER BY SR.ROLE_NAME";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：产生部门树形结构的SQL
	 * @return SQLModel
	 */
	public SQLModel getDeptTreeModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " '" + constant + "' PROVINCE_CODE,"
						+ " EOCM.COMPANY  COMPANY,"
						+ " AMD.DEPT_NAME"
						+ " FROM"
						+ " ETS_OU_CITY_MAP EOCM,"
						+ " AMS_MIS_DEPT    AMD"
						+ " WHERE"
						+ " EOCM.COMPANY_CODE = AMD.COMPANY_CODE"
						+ " AND AMD.ENABLED = 'Y'";
		if (!userAccount.isProvinceUser()) {
			sqlStr += " AND EOCM.COMPANY_CODE = ?";
			sqlArgs.add(userAccount.getCompanyCode());
		}
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：产生地市公司树形结构的SQL
	 * @return SQLModel
	 */
	public SQLModel getCompanyTreeModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ "'" + constant + "' PROVINCE_CODE,"
						+ " EOCM.COMPANY COMPANY"
						+ " FROM"
						+ " ETS_OU_CITY_MAP EOCM";
		if (!userAccount.isProvinceUser()) {
			sqlStr += " WHERE EOCM.COMPANY_CODE = ?";
			sqlArgs.add(userAccount.getCompanyCode());
		}
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：产生公司树形结构的SQL
	 * @return SQLModel
	 */
	public SQLModel getProvinceTreeModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " '" + constant + "' PROVINCE_CODE"
;		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：产生待选用户下拉列表
	 * @return SQLModel
	 */
	public SQLModel getAllUsersModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		String deptName = dto.getDeptName();
		String companyName = dto.getCompanyName();
		String provinceCode = servletConfig.getProvinceCode();
		String sqlStr = "";
		if (!StrUtil.isEmpty(deptName)) { //查找未选择部门资产管理员
			sqlStr = "SELECT"
					 + " SU.USER_ID,"
					 +
					" SU.USERNAME || '[' || dbo.NVL(AMD.DEPT_NAME, '未设置人力部门') || ']' USER_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP  EOCM,"
					 + " AMS_MIS_DEPT     AMD,"
					 + " SF_USER          SU,"
					 + " AMS_MIS_EMPLOYEE AME"
					 + " WHERE"
					 + " SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
					 + " AND AME.DEPT_CODE *= AMD.DEPT_CODE"
					 + " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND (? IS NULL OR EOCM.COMPANY = ?)"
					 + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					 + " AND NOT EXISTS ("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP,"
					 + " AMS_MIS_DEPT     AMD2"
					 + " WHERE"
					 + " AAP.USER_ID = SU.USER_ID"
					 + " AND AAP.DEPT_CODE = AMD2.DEPT_CODE"
					 + " AND CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AMD2.DEPT_NAME = ?"
					 + " AND AAP.PROVINCE_CODE = ?)";
			sqlArgs.add(companyName);
			sqlArgs.add(companyName);
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(deptName);
			sqlArgs.add(provinceCode);
		} else if (!StrUtil.isEmpty(companyName)) { //查找未选择地市公司资产管理员
			sqlStr = "SELECT"
					 + " SU.USER_ID,"
					 +
					" SU.USERNAME || '[' || dbo.NVL(AMD.DEPT_NAME, '未设置人力部门') || ']' USER_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP  EOCM,"
					 + " AMS_MIS_DEPT     AMD,"
					 + " SF_USER          SU,"
					 + " AMS_MIS_EMPLOYEE AME"
					 + " WHERE"
					 + " AME.DEPT_CODE *= AMD.DEPT_CODE"
					 + " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
					 + " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EOCM.COMPANY = ?"
					 + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					 + " AND NOT EXISTS ("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP"
					 + " WHERE"
					 + " AAP.USER_ID = SU.USER_ID"
					 + " AND AAP.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?"
					 + " AND AAP.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " )";
			sqlArgs.add(companyName);
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
		} else if (!StrUtil.isEmpty(provinceCode)) { //查找未选择全省资产管理员
			sqlStr = "SELECT"
					 + " SU.USER_ID,"
					 +
					" SU.USERNAME || '[' || dbo.NVL(AMD.DEPT_NAME, '未设置人力部门') || ']' USER_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP  EOCM,"
					 + " AMS_MIS_DEPT     AMD,"
					 + " SF_USER          SU,"
					 + " AMS_MIS_EMPLOYEE AME"
					 + " WHERE"
					 + " AME.DEPT_CODE *= AMD.DEPT_CODE"
					 + " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
					 + " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EOCM.COMPANY = ?"
					 + " AND SU.USERNAME LIKE dbo.NVL(?, SU.USERNAME)"
					 + " AND NOT EXISTS ("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP"
					 + " WHERE"
					 + " AAP.USER_ID = SU.USER_ID"
					 + " AND CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?"
					 + " AND AAP.COMPANY_CODE " + SyBaseSQLUtil.isNullNoParam() + " "
					 + " AND AAP.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " )";
			sqlArgs.add(servletConfig.getProCompanyName());
			sqlArgs.add(dto.getUserName());
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：产生已选择用户下拉列表
	 * @return SQLModel
	 */
	public SQLModel getExistUsersModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		String deptName = dto.getDeptName();
		String companyName = dto.getCompanyName();
		String provinceCode = servletConfig.getProvinceCode();

		String sqlStr = "";
		if (!StrUtil.isEmpty(deptName)) { //查找已选择部门资产管理员
			sqlStr = "SELECT"
					 + " SU.USER_ID,"
					 +
					" SU.USERNAME || '[' ||  dbo.NVL(AMD.DEPT_NAME, '未设置人力部门') || ']' USER_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP  EOCM,"
					 + " AMS_MIS_DEPT     AMD,"
					 + " SF_USER          SU,"
					 + " AMS_MIS_EMPLOYEE AME"
					 + " WHERE"
					 + " AME.DEPT_CODE *= AMD.DEPT_CODE"
					 + " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
					 + " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EOCM.COMPANY = ?"
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP,"
					 + " AMS_MIS_DEPT     AMD2"
					 + " WHERE"
					 + " SU.USER_ID = AAP.USER_ID"
					 + " AND AAP.DEPT_CODE = AMD2.DEPT_CODE"
					 + " AND AMD2.DEPT_NAME = ?"
					 + " AND CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?)";
			sqlArgs.add(companyName);
			sqlArgs.add(deptName);
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
		} else if (!StrUtil.isEmpty(companyName)) { //查找已选择地市公司资产管理员
			sqlStr = "SELECT"
					 + " SU.USER_ID,"
					 +
					" SU.USERNAME || '[' ||  dbo.NVL(AMD.DEPT_NAME, '未设置人力部门') || ']' USER_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP  EOCM,"
					 + " AMS_MIS_DEPT     AMD,"
					 + " SF_USER          SU,"
					 + " AMS_MIS_EMPLOYEE AME"
					 + " WHERE"
					 + " AME.DEPT_CODE *= AMD.DEPT_CODE"
					 + " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
					 + " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EOCM.COMPANY = ?"
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP"
					 + " WHERE"
					 + " SU.USER_ID = AAP.USER_ID"
					 + " AND AAP.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?)";
			sqlArgs.add(companyName);
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
		} else if (!StrUtil.isEmpty(provinceCode)) { //查找已选择全省资产管理员
			sqlStr = "SELECT"
					 + " SU.USER_ID,"
					 +
					" SU.USERNAME || '[' ||  dbo.NVL(AMD.DEPT_NAME, '未设置人力部门') || ']' USER_NAME"
					 + " FROM"
					 + " ETS_OU_CITY_MAP  EOCM,"
					 + " AMS_MIS_DEPT     AMD,"
					 + " SF_USER          SU,"
					 + " AMS_MIS_EMPLOYEE AME"
					 + " WHERE"
					 + " AME.DEPT_CODE *= AMD.DEPT_CODE"
					 + " AND SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
					 + " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND EOCM.COMPANY = ?"
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP"
					 + " WHERE"
					 + " SU.USER_ID = AAP.USER_ID"
					 + " AND CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?"
					 + " AND AAP.COMPANY_CODE " + SyBaseSQLUtil.isNullNoParam() + " "
					 + " AND AAP.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " )";
			sqlArgs.add(provinceCode);
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：产生已选择用户下拉列表
	 * @param users String[]
	 * @return SQLModel
	 */
	public SQLModel getSelectedUsersModel(String[] users) {
		SQLModel sqlModel = new SQLModel();
		String userIds = ArrUtil.arrToSqlStr(users);
		if (userIds.equals("")) {
			userIds = "''";
		}
		String sqlStr = "SELECT"
						+ " SU.USER_ID,"
						+
				" SU.USERNAME || '[' ||  dbo.NVL(AMD.DEPT_NAME, '未设置人力部门') || ']' USER_NAME"
						+ " FROM"
						+ " ETS_OU_CITY_MAP  EOCM,"
						+ " AMS_MIS_DEPT     AMD,"
						+ " SF_USER          SU,"
						+ " AMS_MIS_EMPLOYEE AME"
						+ " WHERE"
						+ " SU.EMPLOYEE_NUMBER *= AME.EMPLOYEE_NUMBER"
						+ " AND AME.DEPT_CODE *= AMD.DEPT_CODE"
						+ " AND SU.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						+ " AND CONVERT(VARCHAR,SU.USER_ID) IN (" + userIds + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：产生资产权限删除SQL
	 * @return SQLModel
	 */
	public SQLModel getDelExistPrivisModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		String deptName = dto.getDeptName();
		String companyName = dto.getCompanyName();
		String provinceCode = servletConfig.getProvinceCode();
		if (!StrUtil.isEmpty(deptName)) {
			sqlStr = "DELETE"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP"
					 + " WHERE"
					 + " CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?"
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " AMS_MIS_DEPT    AMD,"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE"
					 + " AAP.DEPT_CODE = AMD.DEPT_CODE"
					 + " AND AAP.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND AMD.DEPT_NAME = ?"
					 + " AND EOCM.COMPANY = ?)";
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
			sqlArgs.add(deptName);
			sqlArgs.add(companyName);
		} else if (!StrUtil.isEmpty(companyName)) {
			sqlStr = "DELETE"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP"
					 + " WHERE"
					 + " CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?"
					 + " AND AAP.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " "
					 + " AND EXISTS("
					 + " SELECT"
					 + " NULL"
					 + " FROM"
					 + " ETS_OU_CITY_MAP EOCM"
					 + " WHERE"
					 + " AAP.COMPANY_CODE = EOCM.COMPANY_CODE"
					 + " AND EOCM.COMPANY = ?)";
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
			sqlArgs.add(companyName);
		} else if (!StrUtil.isEmpty(provinceCode)) {
			sqlStr = "DELETE"
					 + " FROM"
					 + " AMS_ASSETS_PRIVI AAP"
					 + " WHERE"
					 + " CONVERT(VARCHAR,AAP.ROLE_ID) = ?"
					 + " AND AAP.PROVINCE_CODE = ?"
					 + " AND AAP.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " "
					 + " AND AAP.COMPANY_CODE " + SyBaseSQLUtil.isNullNoParam() + " ";
			sqlArgs.add(dto.getRoleId());
			sqlArgs.add(provinceCode);
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据部门名称和公司名称获取部门代码
	 * @return SQLModel
	 */
	public SQLModel getDeptCodeModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AMD.DEPT_CODE,"
						+ " EOCM.COMPANY_CODE"
						+ " FROM"
						+ " AMS_MIS_DEPT     AMD,"
						+ " ETS_OU_CITY_MAP  EOCM"
						+ " WHERE"
						+ " AMD.COMPANY_CODE = EOCM.COMPANY_CODE"
						+ " AND AMD.DEPT_NAME = ?"
						+ " AND EOCM.COMPANY = ?";
		sqlArgs.add(dto.getDeptName());
		sqlArgs.add(dto.getCompanyName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据公司名称获取公司代码
	 * @return SQLModel
	 */
	public SQLModel getCompanyCodeModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " EOCM.COMPANY_CODE"
						+ " FROM"
						+ " ETS_OU_CITY_MAP  EOCM"
						+ " WHERE"
						+ " EOCM.COMPANY = ?";
		sqlArgs.add(dto.getCompanyName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取当前用户有权访问的部门资产SQL。
	 * @return SQLModel
	 */
	public SQLModel getPriviDeptModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AMD.*"
						+ " FROM"
						+ " AMS_MIS_DEPT     AMD,"
						+ " AMS_ASSETS_PRIVI AAP,"
						+ " SF_ROLE          SR"
						+ " WHERE"
						+ " AMD.DEPT_CODE = AAP.DEPT_CODE"
						+ " AND AAP.PROVINCE_CODE = ?"
						+ " AND AAP.ROLE_ID = SR.ROLE_ID"
						+ " AND SR.ROLE_NAME = ?"
						+ " AND AAP.COMPANY_CODE = ?"
						+ " AND  " + SyBaseSQLUtil.isNotNull("AAP.DEPT_CODE") + ""
						+ " AND AAP.USER_ID = ?";
		sqlArgs.add(servletConfig.getProvinceCode());
		sqlArgs.add(servletConfig.getDeptAssetsMgr());
		sqlArgs.add(userAccount.getCompanyCode());
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取当前用户是否公司资产管理员的SQL。
	 * @return SQLModel
	 */
	public SQLModel getIsCompanyMgrModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_PRIVI AAP,"
						+ " SF_ROLE          SR"
						+ " WHERE"
						+ " AAP.ROLE_ID = SR.ROLE_ID"
						+ " AND SR.ROLE_NAME = ?"
						+ " AND AAP.PROVINCE_CODE = ?"
						+ " AND AAP.COMPANY_CODE = ?"
						+ " AND (AAP.DEPT_CODE = '' OR AAP.DEPT_CODE IS NULL) "
						+ " AND AAP.USER_ID = ?";
		sqlArgs.add(servletConfig.getCompAssetsMgr());
		sqlArgs.add(servletConfig.getProvinceCode());
		sqlArgs.add(userAccount.getCompanyCode());
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取当前用户是否公司资产管理员的SQL。
	 * @return SQLModel
	 */
	public SQLModel getIsProvinceMgrModel() {    //getIsDptMgrModel
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_PRIVI AAP,"
						+ " SF_ROLE          SR"
						+ " WHERE"
						+ " AAP.ROLE_ID = SR.ROLE_ID"
						+ " AND AAP.COMPANY_CODE = ?"
//						+ " AND AAP.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND AAP.PROVINCE_CODE = ?"
						+ " AND SR.ROLE_NAME = ?"
						+ " AND AAP.USER_ID = ?";
		sqlArgs.add(servletConfig.getProCompanyCode());
		sqlArgs.add(servletConfig.getProvinceCode());
		sqlArgs.add(servletConfig.getProvAssetsMgr());
		sqlArgs.add(userAccount.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
	 * 功能：获取专业资产管理员权限插入SQL
	 * @return SQLModel
	 */
	public SQLModel getMtlPriviSaveModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO AMS_ASSETS_PRIVI("
						+ " PRIVI_ID,"
						+ " USER_ID,"
						+ " FA_CATEGORY_CODE"
						+ " ) VALUES ( NEWID(), ?, ?)";
		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getFaCategoryCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：获取专业资产管理员权限插入SQL
	 * @return SQLModel
	 */
	public SQLModel getMtlPriviDelModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM AMS_ASSETS_PRIVI"
						+ " WHERE"
						+ " USER_ID = ?"
						+ " AND FA_CATEGORY_CODE = ?";
		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getFaCategoryCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取用户的专业资产管理员权限SQL
	 * @return SQLModel
	 */
	public SQLModel getMtlMgrPropsModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " FA_CATEGORY_CODE"
						+ " FROM"
						+ " AMS_ASSETS_PRIVI AAP,"
						+ " SF_ROLE          SR"
						+ " WHERE"
						+ " AAP.ROLE_ID = SR.ROLE_ID"
						+ " AND AAP.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " "
						+ " AND  " + SyBaseSQLUtil.isNotNull("AAP.FA_CATEGORY_CODE") + ""
						+ " AND AAP.USER_ID = ?"
						+ " AND SR.ROLE_NAME = ?"
						+ " AND AAP.COMPANY_CODE = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(servletConfig.getMtlAssetsMgr());
		sqlArgs.add(userAccount.getCompanyCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
