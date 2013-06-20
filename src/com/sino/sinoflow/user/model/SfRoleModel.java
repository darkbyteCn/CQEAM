package com.sino.sinoflow.user.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfRoleDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfRoleModel</p>
 * <p>Description:程序自动生成SQL构造器“SfRoleModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 *
 * 修改人：白嘉
 * 修改日期：2008.8.21
 */


public class SfRoleModel extends BaseSQLProducer {

	private SfRoleDTO sfRole = null;

	/**
	 * 功能：SF_ROLE 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfRoleDTO 本次操作的数据
	 */
	public SfRoleModel(SfUserBaseDTO userAccount, SfRoleDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfRole = (SfRoleDTO)dtoParameter;
	}

	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 *
	 * 修改
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
			+ " SF_ROLE("
			+ " PROJECT_NAME,"
			+ " ROLE_NAME,"
			+ " ROLE_DESC,"
			+ " ENABLED"
			+ ") VALUES ("
			+ " ?,?, ?,?)";

		sqlArgs.add(sfRole.getProjectName());
		sqlArgs.add(sfRole.getRoleName());
		sqlArgs.add(sfRole.getRoleDesc());
		sqlArgs.add(sfRole.getEnabled());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 *
	 * 修改
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_ROLE"
			+ " SET"
			+ " ROLE_NAME = ?,"
			+ " ROLE_DESC = ?,"
			+ " ENABLED = ?"
			+ " WHERE"
			+ " ROLE_ID = ?";

		sqlArgs.add(sfRole.getRoleName());
		sqlArgs.add(sfRole.getRoleDesc());
		sqlArgs.add(sfRole.getEnabled());
		sqlArgs.add(sfRole.getRoleId());
//
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
			+ " SF_ROLE"
			+ " WHERE"
			+ " ROLE_ID = ?";
		sqlArgs.add(sfRole.getRoleId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 *
	 * 修改
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " ROLE_ID,"
			+ " ROLE_NAME,"
			+ " ROLE_DESC,"
			+ " PROJECT_NAME"
			+ " FROM"
			+ " SF_ROLE "
			+ " WHERE"
			+ " ROLE_ID = ?";
		sqlArgs.add(sfRole.getRoleId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 *
	 * 修改
	 */
	/*public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " ROLE_ID,"
			+ " ROLENAME,"
			+ " DESCRIPTION,"
			+ " SORTNO,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " SF_ROLE"
			+ " WHERE"
			+ " ROLE_ID = ?"
			+ " ROLENAME = ?"
			+ " DESCRIPTION = ?"
			+ " SORTNO = ?"
			+ " IS_INNER = ?"
			+ " CREATION_DATE = ?"
			+ " CREATED_BY = ?"
			+ " LAST_UPDATE_DATE = ?"
			+ " LAST_UPDATE_BY = ?";
		sqlArgs.add(sfRole.getRoleId());
		sqlArgs.add(sfRole.getRolename());
		sqlArgs.add(sfRole.getDescription());
		sqlArgs.add(sfRole.getSortno());
		sqlArgs.add(sfRole.getIsInner());
		sqlArgs.add(sfRole.getCreationDate());
		sqlArgs.add(sfRole.getCreatedBy());
		sqlArgs.add(sfRole.getLastUpdateDate());
		sqlArgs.add(sfRole.getLastUpdateBy());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}*/

	/**
	 * 功能：根据外键关联字段 createdBy 构造查询数据SQL。
	 * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @param createdBy String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 *
	 * 修改
	 */
	private SQLModel getDataByCreatedByModel(int createdBy){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " ROLE_ID,"
			+ " ROLE_NAME,"
			+ " ROLE_DESC,"
			+ " PROJECT_NAME"
			+ " FROM"
			+ " SF_ROLE"
			+ " WHERE"
			+ " CREATED_BY = ?";
		sqlArgs.add(createdBy);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 lastUpdateBy 构造查询数据SQL。
	 * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @param lastUpdateBy String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 *
	 * 修改
	 */
	private SQLModel getDataByLastUpdateByModel(int lastUpdateBy){
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT "
			+ " ROLE_ID,"
			+ " ROLE_NAME,"
			+ " ROLE_DESC,"
			+ " PROJECT_NAME"
			+ " FROM"
			+ " SF_ROLE";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		if(foreignKey.equals("createdBy")){
			sqlModel = getDataByCreatedByModel(sfRole.getCreatedBy());
		} else if(foreignKey.equals("lastUpdateBy")){
			sqlModel = getDataByLastUpdateByModel(sfRole.getLastUpdateBy());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 *
	 * 修改
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT "
			+ " CONVERT(INT,ROLE_ID) ROLE_ID,"
			+ " ROLE_NAME,"
			+ " ROLE_DESC,"
			+ " PROJECT_NAME"
			+ " FROM"
			+ " SF_ROLE"
			+ " WHERE (? = '' OR ROLE_DESC LIKE  ?)"
			+ " AND (? = '' OR ROLE_NAME LIKE ?)"
			+ " AND (? = '' OR ENABLED LIKE ?)";
		sqlArgs.add(sfRole.getRoleDesc());
		sqlArgs.add(sfRole.getRoleDesc());
		sqlArgs.add(sfRole.getRoleName());
		sqlArgs.add(sfRole.getRoleName());
		sqlArgs.add(sfRole.getEnabled());
		sqlArgs.add(sfRole.getEnabled());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：用于返回角色的下拉列表
	 * @param pName String 工程名称
	 * @return SQLModel
	 */
	public SQLModel getRoleOptionModel(String pName){
		return getRoleOptionModel(pName, "");
	}


	/**
	 * 功能：用于返回角色的下拉列表
	 * @param pName String 工程名称
	 * @param sysAdminRole String 系统管理员角色名称
	 * @return SQLModel
	 */
	public SQLModel getRoleOptionModel(String pName, String sysAdminRole){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT "
			+ " ROLE_ID,"
			+ " ROLE_NAME"
			+ " FROM"
			+ " SF_ROLE"
			+ " WHERE "
			+ " PROJECT_NAME = ?"
			+ " AND ENABLED = 'Y'";
		sqlArgs.add(pName);
		if(!((SfUserBaseDTO)userAccount).isSysAdmin() && !StrUtil.isEmpty(sysAdminRole)){
			sqlStr += " AND ROLE_NAME <> ?";
			sqlArgs.add(sysAdminRole);
		}
		sqlStr += " ORDER BY ROLE_NAME";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：用于返回角色的下拉列表
	 * @param pName String
	 * @return SQLModel
	 */
	public SQLModel getRoleOptionModel2(String pName){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();

		String sqlStr = "SELECT "
			+ " ROLE_NAME,"
			+ " ROLE_NAME"
			+ " FROM"
			+ " SF_ROLE"
			+ " WHERE "
			+ " PROJECT_NAME = ?"
			+ " AND ENABLED = 'Y'"
			+ " ORDER BY ROLE_NAME";

		sqlArgs.add(pName);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：查询组别有没有存在
	 * @param proj String 工程名称
	 * @param role 对应组别
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel checkRoleExistModel(String proj, String role) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
			String sqlStr = "SELECT "
					+ " *"
					+ " FROM"
					+ " SF_ROLE"
					+ " WHERE"
					+ " PROJECT_NAME = ?"
					+ " AND ROLE_NAME = ?";

		sqlArgs.add(proj);
		sqlArgs.add(role);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
