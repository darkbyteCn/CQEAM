package com.sino.sinoflow.framework.security.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.framework.security.dto.SfUserAuthorityDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfUserAuthorityModel</p>
 * <p>Description:程序自动生成SQL构造器“SfUserAuthorityModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfUserAuthorityModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：用戶职位权限信息 SF_USER_AUTHORITY 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfUserAuthorityDTO 本次操作的数据
	 */
	public SfUserAuthorityModel(SfUserBaseDTO userAccount, SfUserAuthorityDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成用戶职位权限信息 SF_USER_AUTHORITY数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_USER_AUTHORITY("
			+ " USER_ID,"
			+ " DEPARTMENT,"
			+ " POSITION,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME"
			+ ") VALUES ("
			+ " ?, ?, ?, ?, ?)";

		sqlArgs.add(sfUserAuthority.getUserId());
		sqlArgs.add(sfUserAuthority.getDepartment());
		sqlArgs.add(sfUserAuthority.getPosition());
		sqlArgs.add(sfUserAuthority.getGroupName());
		sqlArgs.add(sfUserAuthority.getRoleName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成用戶职位权限信息 SF_USER_AUTHORITY数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
		String sqlStr = "UPDATE SF_USER_AUTHORITY"
			+ " SET"
			+ " USER_ID = ?,"
			+ " DEPARTMENT = ?,"
			+ " POSITION = ?,"
			+ " GROUP_NAME = ?,"
			+ " ROLE_NAME = ?"
			+ " WHERE"
			+ " AUTHORITY_ID = ?";

		sqlArgs.add(sfUserAuthority.getUserId());
		sqlArgs.add(sfUserAuthority.getDepartment());
		sqlArgs.add(sfUserAuthority.getPosition());
		sqlArgs.add(sfUserAuthority.getGroupName());
		sqlArgs.add(sfUserAuthority.getRoleName());
		sqlArgs.add(sfUserAuthority.getAuthorityId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成用戶职位权限信息 SF_USER_AUTHORITY数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_USER_AUTHORITY"
				+ " WHERE"
				+ " AUTHORITY_ID = ?";
			sqlArgs.add(sfUserAuthority.getAuthorityId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成用戶职位权限信息 SF_USER_AUTHORITY数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " AUTHORITY_ID,"
			+ " USER_ID,"
			+ " DEPARTMENT,"
			+ " POSITION,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME"
			+ " FROM"
			+ " SF_USER_AUTHORITY"
			+ " WHERE"
			+ " AUTHORITY_ID = ?";
		sqlArgs.add(sfUserAuthority.getAuthorityId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成用戶职位权限信息 SF_USER_AUTHORITY多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " AUTHORITY_ID,"
			+ " USER_ID,"
			+ " DEPARTMENT,"
			+ " POSITION,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME"
			+ " FROM"
			+ " SF_USER_AUTHORITY"
			+ " WHERE"
			+ " (? <= 0 OR AUTHORITY_ID = ?)"
			+ " AND (? <= 0 OR USER_ID = ?)"
			+ " AND (? = '' OR DEPARTMENT LIKE ?)"
			+ " AND (? = '' OR POSITION LIKE ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? = '' OR ROLE_NAME LIKE ?)";
		sqlArgs.add(sfUserAuthority.getAuthorityId());
		sqlArgs.add(sfUserAuthority.getAuthorityId());
		sqlArgs.add(sfUserAuthority.getUserId());
		sqlArgs.add(sfUserAuthority.getUserId());
		sqlArgs.add(sfUserAuthority.getDepartment());
		sqlArgs.add(sfUserAuthority.getDepartment());
		sqlArgs.add(sfUserAuthority.getPosition());
		sqlArgs.add(sfUserAuthority.getPosition());
		sqlArgs.add(sfUserAuthority.getGroupName());
		sqlArgs.add(sfUserAuthority.getGroupName());
		sqlArgs.add(sfUserAuthority.getRoleName());
		sqlArgs.add(sfUserAuthority.getRoleName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造查询数据SQL。
	 * 框架自动生成数据用戶职位权限信息 SF_USER_AUTHORITY详细信息查询SQLModel，请根据实际需要修改。
	 * @param userId AdvancedNumber
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getDataByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AUTHORITY_ID,"
						+ " DEPARTMENT,"
						+ " POSITION,"
						+ " GROUP_NAME,"
						+ " ROLE_NAME"
						+ " FROM"
						+ " SF_USER_AUTHORITY"
						+ " WHERE"
						+ " USER_ID = ?";
		sqlArgs.add(userId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDataByUserIdModel(sfUserAuthority.getUserId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造数据删除SQL。
	 * 框架自动生成数据用戶职位权限信息 SF_USER_AUTHORITY数据删除SQLModel，请根据实际需要修改。
	 * @param userId AdvancedNumber
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
/*
            + " AUTHORITY_ID,"
			+ " DEPARTMENT,"
			+ " POSITION,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME"
*/
			+ " FROM"
			+ " SF_USER_AUTHORITY"
			+ " WHERE"
			+ " USER_ID = ?";
		sqlArgs.add(userId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDeleteByUserIdModel(sfUserAuthority.getUserId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成用戶职位权限信息 SF_USER_AUTHORITY页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfUserAuthorityDTO sfUserAuthority = (SfUserAuthorityDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " AUTHORITY_ID,"
			+ " USER_ID,"
			+ " DEPARTMENT,"
			+ " POSITION,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME"
			+ " FROM"
			+ " SF_USER_AUTHORITY"
			+ " WHERE"
			+ " (? <= 0 OR AUTHORITY_ID = ?)"
			+ " AND (? <= 0 OR USER_ID = ?)"
			+ " AND (? = '' OR DEPARTMENT LIKE ?)"
			+ " AND (? = '' OR POSITION LIKE ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? = '' OR ROLE_NAME LIKE ?)";
		sqlArgs.add(sfUserAuthority.getAuthorityId());
		sqlArgs.add(sfUserAuthority.getAuthorityId());
		sqlArgs.add(sfUserAuthority.getUserId());
		sqlArgs.add(sfUserAuthority.getUserId());
		sqlArgs.add(sfUserAuthority.getDepartment());
		sqlArgs.add(sfUserAuthority.getDepartment());
		sqlArgs.add(sfUserAuthority.getPosition());
		sqlArgs.add(sfUserAuthority.getPosition());
		sqlArgs.add(sfUserAuthority.getGroupName());
		sqlArgs.add(sfUserAuthority.getGroupName());
		sqlArgs.add(sfUserAuthority.getRoleName());
		sqlArgs.add(sfUserAuthority.getRoleName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getGroupModel(String loginName, String role) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SUA.GROUP_NAME, SU.USERNAME "
            + " FROM"
            + " SF_USER_AUTHORITY SUA, SF_USER SU"
            + " WHERE"
            + " UPPER(SU.LOGIN_NAME) = UPPER(?)"
            + " AND SU.USER_ID = SUA.USER_ID"
            + " AND SUA.ROLE_NAME = ?";
        sqlArgs.add(loginName);
        sqlArgs.add(role);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
