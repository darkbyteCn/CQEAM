package com.sino.ams.newasset.rolequery.model;

import java.util.ArrayList;
import java.util.List;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.ams.newasset.rolequery.constant.RoleQueryConstant;
import com.sino.ams.newasset.rolequery.dto.SfRoleQueryDTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
/**
 * 查询角色对应栏目
 * @author xiaohua
 *
 */
public class SfRoleQueryResModel extends BaseSQLProducer {
	private SfRoleQueryDTO roleDTO=null;
	
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SfRoleQueryResModel(BaseUserDTO userAccount, DTO dtoParameter,String type) {
		super(userAccount, dtoParameter);
		roleDTO=(SfRoleQueryDTO)dtoParameter;
		this.type=type;
	}
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr= "";
		//SJ modify 
		StringBuilder sb = new StringBuilder();
		sb.append( " SELECT \n ");
		sb.append( " SU.USERNAME USER_NAME, \n ");
		sb.append( " SUA.GROUP_NAME, \n ");
		sb.append( " SR.ROLE_ID, \n ");
		sb.append( " SR.ROLE_NAME ,  \n ");
		sb.append( " SR.ROLE_DESC ,  \n ");
		sb.append( " SR.ENABLED  \n ");
		sb.append( " FROM  \n ");
		sb.append( " SF_ROLE SR, \n ");
		sb.append( " SF_USER_AUTHORITY SUA, \n ");
		sb.append( " SF_USER SU  \n ");
		sb.append( " WHERE  \n ");
		sb.append( " SR.ROLE_NAME = SUA.ROLE_NAME \n ");
		sb.append( " AND SUA.USER_ID = SU.USER_ID  \n ");
		sb.append( " AND SR.PROJECT_NAME='"+RoleQueryConstant.ROLE_QUERY_PROJECT+"'  \n ");
		sb.append( " AND (?='' OR SR.ROLE_NAME LIKE '%"+roleDTO.getRoleName()+"%')  \n ");
		sb.append( " AND (?='' OR SR.ROLE_DESC LIKE '%"+roleDTO.getRoleDesc()+"%')  \n ");
		sb.append( " AND (?='' OR SUA.GROUP_NAME LIKE '%"+roleDTO.getGroupName()+"%') \n ");
		
			
//		String sqlStr="SELECT SU.USERNAME as USER_NAME,SRD.RES_NAME,SRD.RES_URL,SRP.GROUP_NAME,SR.ROLE_NAME,SR.ROLE_DESC" +
//				" FROM SF_RES_DEFINE SRD,SF_RES_PRIVS SRP,SF_ROLE SR,SF_USER_AUTHORITY SFUA,SF_USER SU " +
//		" WHERE SRD.SYSTEM_ID=SRP.SYSTEM_ID " +
//		"AND SFUA.ROLE_NAME=SR.ROLE_NAME "+
//		" AND SRP.ROLE_NAME=SR.ROLE_NAME" +
//		" AND SU.USER_ID=SFUA.USER_ID" +
//		" AND SR.PROJECT_NAME='"+RoleQueryConstant.ROLE_QUERY_PROJECT+"'" +
////		" AND SRD.IS_FINISHED='Y'" +
//		" AND (?='' OR SR.ROLE_NAME LIKE '%"+roleDTO.getRoleName()+"%')" +
//		" AND (?='' OR SR.ROLE_DESC LIKE '%"+roleDTO.getRoleDesc()+"%')" +
//		" AND (?='' OR SRP.GROUP_NAME LIKE '%"+roleDTO.getGroupName()+"%')";
		if(!roleDTO.getUserName().trim().equals(""))
		{
			sb.append( " AND SU.USERNAME LIKE '%"+roleDTO.getUserName()+"%' \n ");
//			sqlStr+=" AND SFUA.USER_ID in(SELECT USER_ID FROM SF_USER WHERE USERNAME LIKE '%"+roleDTO.getUserName()+"%')";
		}
		sb.append( " ORDER BY  SUA.USER_ID , SUA.GROUP_NAME , SUA.ROLE_NAME \n ");
		sqlStr = sb.toString();
//		sqlStr+=" ORDER BY SR.ROLE_NAME";
		if(type.equals("lanmu"))
		{
			sqlStr = "";
			sqlStr = "SELECT DISTINCT SRD.RES_NAME,SU.USERNAME as USER_NAME,SRD.RES_URL,SRP.GROUP_NAME"
					+ " FROM SF_RES_DEFINE SRD,SF_RES_PRIVS SRP,SF_ROLE SR,SF_USER_AUTHORITY SFUA,SF_USER SU "
					+ " WHERE SRD.SYSTEM_ID=SRP.SYSTEM_ID "
					+ "AND SFUA.ROLE_NAME=SR.ROLE_NAME "
					+ " AND SRP.ROLE_NAME=SR.ROLE_NAME"
					+ " AND SU.USER_ID=SFUA.USER_ID"
					+ " AND SR.PROJECT_NAME='"
					+ RoleQueryConstant.ROLE_QUERY_PROJECT
					+ "'"
					+ " AND (?='' OR SR.ROLE_NAME LIKE '%"
					+ roleDTO.getRoleName()
					+ "%')"
					+ " AND (?='' OR SR.ROLE_DESC LIKE '%"
					+ roleDTO.getRoleDesc()
					+ "%')"
					+ " AND (?='' OR SRP.GROUP_NAME LIKE '%"
					+ roleDTO.getGroupName() + "%')";
			if (!roleDTO.getUserName().trim().equals("")) {
				sqlStr += " AND SFUA.USER_ID in(SELECT USER_ID FROM SF_USER WHERE USERNAME LIKE '%"
						+ roleDTO.getUserName() + "%')";
			}
			sqlStr += " ORDER BY SRD.SYSTEM_ID";
		}
		sqlArgs.add(roleDTO.getRoleName());
		sqlArgs.add(roleDTO.getRoleDesc());
		sqlArgs.add(roleDTO.getGroupName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
