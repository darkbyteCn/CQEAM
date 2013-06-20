package com.sino.ams.newasset.rolequery.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.newasset.rolequery.constant.RoleQueryConstant;
import com.sino.ams.newasset.rolequery.dto.SfRoleQueryDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * 查询角色对应用户
 * 
 * @author xiaohua
 * 
 */
public class SfRoleQueryUserModel extends BaseSQLProducer {
	private SfRoleQueryDTO roleDTO = null;

	public SfRoleQueryUserModel(BaseUserDTO userAccount, DTO dtoParameter) {
		super(userAccount, dtoParameter);
		roleDTO = (SfRoleQueryDTO) dtoParameter;
	}

	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		if (roleDTO.getResName().trim().equals("")) {
			sqlStr = "SELECT SU.USERNAME AS USER_NAME,SUA.ROLE_NAME,SUA.GROUP_NAME,SR.ROLE_DESC \n"
					+ " FROM SF_USER_AUTHORITY SUA,SF_USER SU,SF_ROLE SR,ETS_OU_CITY_MAP EOCM\n"
					+ " WHERE \n"
					+ " SUA.USER_ID=SU.USER_ID\n"
					+ " AND SUA.ROLE_NAME=SR.ROLE_NAME \n"
					+ " AND SUA.PROJECT_NAME=SR.PROJECT_NAME\n"
					+ " AND EOCM.ORGANIZATION_ID=SU.ORGANIZATION_ID "
					+ " AND SUA.PROJECT_NAME='"
					+ RoleQueryConstant.ROLE_QUERY_PROJECT
					+ "'"
					+ " AND (?='' OR SUA.ROLE_NAME LIKE '%"
					+ roleDTO.getRoleName()
					+ "%' )\n"
					+ " AND (?='' OR SUA.GROUP_NAME LIKE '%"
					+ roleDTO.getGroupName()
					+ "%')";
					sqlArgs.add(roleDTO.getRoleName());
					sqlArgs.add(roleDTO.getGroupName());
					if(!roleDTO.getOrganizationId().trim().equals(""))
					{
						sqlStr+= " AND (?='' OR EOCM.ORGANIZATION_ID ="+roleDTO.getOrganizationId()+")";
						sqlArgs.add(roleDTO.getOrganizationId());
					}
					sqlStr +=" ORDER BY SUA.ROLE_NAME ";
		} else {
			sqlStr = "SELECT SU.USERNAME AS USER_NAME,SUA.ROLE_NAME,SUA.GROUP_NAME,SR.ROLE_DESC \n"
					+ " FROM SF_USER_AUTHORITY SUA,SF_USER SU,SF_ROLE SR,SF_RES_DEFINE SRD,SF_RES_PRIVS SRP,ETS_OU_CITY_MAP EOCM\n"
					+ " WHERE \n"
					+ "SRD.SYSTEM_ID=SRP.SYSTEM_ID AND SRP.ROLE_NAME=SR.ROLE_NAME AND"
					+ " SUA.USER_ID=SU.USER_ID\n"
					+ " AND SUA.ROLE_NAME=SR.ROLE_NAME \n"
					+ " AND SUA.PROJECT_NAME=SR.PROJECT_NAME\n"
					+ " AND EOCM.ORGANIZATION_ID=SU.ORGANIZATION_ID "
					+ " AND SUA.PROJECT_NAME='"
					+ RoleQueryConstant.ROLE_QUERY_PROJECT
					+ "'"
					+ " AND (?='' OR SUA.ROLE_NAME LIKE '%"
					+ roleDTO.getRoleName()
					+ "%' )\n"
					+ " AND (?='' OR SR.ROLE_DESC LIKE '%"
					+ roleDTO.getRoleDesc()
					+ "%')\n"
					+ " AND (?='' OR SUA.GROUP_NAME LIKE '%"
					+ roleDTO.getGroupName()
					+ "%')" ;
//					+ " AND (?='' OR EOCM.ORGANIZATION_ID ="+roleDTO.getOrganizationId()+")";
					sqlArgs.add(roleDTO.getRoleName());
					sqlArgs.add(roleDTO.getRoleDesc());
					sqlArgs.add(roleDTO.getGroupName());
					if(!roleDTO.getOrganizationId().trim().equals(""))
					{
						sqlStr+= " AND (?='' OR EOCM.ORGANIZATION_ID ="+roleDTO.getOrganizationId()+")";
						sqlArgs.add(roleDTO.getOrganizationId());
					}
					
					sqlStr+= " AND SRD.RES_NAME LIKE '%" + roleDTO.getResName() + "%'" ;
					sqlStr+= " ORDER BY SUA.ROLE_NAME ";
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
