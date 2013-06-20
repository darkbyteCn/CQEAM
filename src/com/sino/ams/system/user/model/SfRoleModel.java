package com.sino.ams.system.user.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfRoleDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: SfRoleModel</p>
 * <p>Description:程序自动生成SQL构造器“SfRoleModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfRoleModel extends BaseSQLProducer {

	private SfRoleDTO sfRole = null;
    private SfUserDTO sfUser = null;

    /**
	 * 功能：SF_ROLE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfRoleDTO 本次操作的数据
	 */
	public SfRoleModel(SfUserDTO userAccount, SfRoleDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfRole = (SfRoleDTO)dtoParameter;
        sfUser=userAccount;
    }
	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
			+ " SF_ROLE("
			+ " ROLE_ID,"
			+ " ROLENAME,"
			+ " DESCRIPTION,"
			+ " SORTNO,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ ") VALUES ("
			+ "  NEWID() , ?, ?, ?, ?, GETDATE(), ?, ?, ?)";
		
		sqlArgs.add(sfRole.getRolename());
		sqlArgs.add(sfRole.getDescription());
		sqlArgs.add(sfRole.getSortno());
		sqlArgs.add(sfRole.getIsInner());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfRole.getLastUpdateDate());
		sqlArgs.add(sfRole.getLastUpdateBy());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_ROLE"
			+ " SET"
			+ " ROLENAME = ?,"
			+ " DESCRIPTION = ?,"
			+ " SORTNO = ?,"
			+ " IS_INNER = ?,"
			+ " LAST_UPDATE_DATE = GETDATE(),"
			+ " LAST_UPDATE_BY = ?"
			+ " WHERE"
			+ " ROLE_ID = ?";
		
		sqlArgs.add(sfRole.getRolename());
		sqlArgs.add(sfRole.getDescription());
		sqlArgs.add(sfRole.getSortno());
		sqlArgs.add(sfRole.getIsInner());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfRole.getRoleId());
		
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
	 */
	public SQLModel getPrimaryKeyDataModel(){
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
	 */
	public SQLModel getDataMuxModel(){
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
	}

	/**
	 * 功能：根据外键关联字段 createdBy 构造查询数据SQL。
	 * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @param createdBy String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByCreatedByModel(int createdBy){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " ROLE_ID,"
			+ " ROLENAME,"
			+ " DESCRIPTION,"
			+ " SORTNO,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
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
	 */
	private SQLModel getDataByLastUpdateByModel(int lastUpdateBy){
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
			+ " LAST_UPDATE_DATE"
			+ " FROM"
			+ " SF_ROLE"
			+ " WHERE"
			+ " LAST_UPDATE_BY = ?";
		sqlArgs.add(lastUpdateBy);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
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
	 */
	public SQLModel getPageQueryModel(){
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
			+ " WHERE 1=1"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ROLE_ID LIKE  ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ROLENAME LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR DESCRIPTION LIKE  ?)";

        sqlArgs.add(sfRole.getRoleId());
        sqlArgs.add(sfRole.getRoleId());
		sqlArgs.add(sfRole.getRolename());
		sqlArgs.add(sfRole.getRolename());
		sqlArgs.add(sfRole.getDescription());
		sqlArgs.add(sfRole.getDescription());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}