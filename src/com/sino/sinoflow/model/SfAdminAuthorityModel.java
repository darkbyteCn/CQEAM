package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfAdminAuthorityDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SfAdminAuthorityModel</p>
 * <p>Description:程序自动生成SQL构造器“SfAdminAuthorityModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */
 

public class SfAdminAuthorityModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：管理员权限信息 SF_ADMIN_AUTHORITY 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfAdminAuthorityDTO 本次操作的数据
	 */
	public SfAdminAuthorityModel(SfUserBaseDTO userAccount, SfAdminAuthorityDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成管理员权限信息 SF_ADMIN_AUTHORITY数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_ADMIN_AUTHORITY("
			+ " USER_ID,"
			+ " PROJECT_NAME,"
			+ " GROUP_NAME"
			+ ") VALUES ("
			+ " ?, ?, ?)";
		
		sqlArgs.add(sfAdminAuthority.getUserId());
		sqlArgs.add(sfAdminAuthority.getProjectName());
		sqlArgs.add(sfAdminAuthority.getGroupName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成管理员权限信息 SF_ADMIN_AUTHORITY数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
		String sqlStr = "UPDATE SF_ADMIN_AUTHORITY"
			+ " SET"
			+ " USER_ID = ?,"
			+ " PROJECT_NAME = ?,"
			+ " GROUP_NAME = ?"
			+ " WHERE"
			+ " ADMIN_ID = ?";
		
		sqlArgs.add(sfAdminAuthority.getUserId());
		sqlArgs.add(sfAdminAuthority.getProjectName());
		sqlArgs.add(sfAdminAuthority.getGroupName());
		sqlArgs.add(sfAdminAuthority.getAdminId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成管理员权限信息 SF_ADMIN_AUTHORITY数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_ADMIN_AUTHORITY"
				+ " WHERE"
				+ " ADMIN_ID = ?";
			sqlArgs.add(sfAdminAuthority.getAdminId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成管理员权限信息 SF_ADMIN_AUTHORITY数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SAA.ADMIN_ID,"
			+ " SAA.USER_ID,"
			+ " SAA.PROJECT_NAME,"
			+ " SAA.GROUP_NAME,"
			+ " SU.LOGIN_NAME LOGIN_NAME"
			+ " FROM"
			+ " SF_ADMIN_AUTHORITY SAA,"
			+ " SF_USER SU"
			+ " WHERE"
			+ " SU.USER_ID = SAA.USER_ID"
			+ " AND ADMIN_ID = ?";
		sqlArgs.add(sfAdminAuthority.getAdminId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成管理员权限信息 SF_ADMIN_AUTHORITY多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " ADMIN_ID,"
			+ " USER_ID,"
			+ " PROJECT_NAME,"
			+ " GROUP_NAME"
			+ " FROM"
			+ " SF_ADMIN_AUTHORITY"
			+ " WHERE"
			+ " (? <= 0 OR ADMIN_ID = ?)"
			+ " AND (? <= 0 OR USER_ID = ?)"
			+ " AND (? = '' OR PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)";
		sqlArgs.add(sfAdminAuthority.getAdminId());
		sqlArgs.add(sfAdminAuthority.getAdminId());
		sqlArgs.add(sfAdminAuthority.getUserId());
		sqlArgs.add(sfAdminAuthority.getUserId());
		sqlArgs.add(sfAdminAuthority.getProjectName());
		sqlArgs.add(sfAdminAuthority.getProjectName());
		sqlArgs.add(sfAdminAuthority.getGroupName());
		sqlArgs.add(sfAdminAuthority.getGroupName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造查询数据SQL。
	 * 框架自动生成数据管理员权限信息 SF_ADMIN_AUTHORITY详细信息查询SQLModel，请根据实际需要修改。
	 * @param userId String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " ADMIN_ID,"
			+ " PROJECT_NAME,"
			+ " GROUP_NAME"
			+ " FROM"
			+ " SF_ADMIN_AUTHORITY"
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
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDataByUserIdModel(sfAdminAuthority.getUserId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 userId 构造数据删除SQL。
	 * 框架自动生成数据管理员权限信息 SF_ADMIN_AUTHORITY数据删除SQLModel，请根据实际需要修改。
	 * @param userId String 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
/*
            + " ADMIN_ID,"
			+ " PROJECT_NAME,"
			+ " GROUP_NAME"
*/
			+ " FROM"
			+ " SF_ADMIN_AUTHORITY"
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
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
		if(foreignKey.equals("userId")){
			sqlModel = getDeleteByUserIdModel(sfAdminAuthority.getUserId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成管理员权限信息 SF_ADMIN_AUTHORITY页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " CONVERT(INT,SA.ADMIN_ID) ADMIN_ID,"
			+ " SA.USER_ID,"
			+ " SA.PROJECT_NAME,"
			+ " SA.GROUP_NAME,"
			+ " SU.LOGIN_NAME LOGIN_NAME"
			+ " FROM"
			+ " SF_ADMIN_AUTHORITY SA,SF_USER SU"
			+ " WHERE"
			+ " (? <= 0 OR ADMIN_ID = ?)"
			+ " AND (? <= 0 OR SA.USER_ID = ?)"
			+ " AND (? = '' OR SA.PROJECT_NAME LIKE ?)"
			+ " AND (? = '' OR SA.GROUP_NAME LIKE ?)"
			+ " AND SA.USER_ID = SU.USER_ID"
			+ " ORDER BY SA.USER_ID";
		sqlArgs.add(sfAdminAuthority.getAdminId());
		sqlArgs.add(sfAdminAuthority.getAdminId());
		sqlArgs.add(sfAdminAuthority.getUserId());
		sqlArgs.add(sfAdminAuthority.getUserId());
		sqlArgs.add(sfAdminAuthority.getProjectName());
		sqlArgs.add(sfAdminAuthority.getProjectName());
		sqlArgs.add(sfAdminAuthority.getGroupName());
		sqlArgs.add(sfAdminAuthority.getGroupName());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
     * 功能：删除
     * @param ids IDs
     * @return SQLModel 
     */
    public SQLModel del(String[] ids){
	    String str = "";
		for (int i = 0; i < ids.length; i++) {
			str += ids[i]+",";
		}
		str = str.substring(0,str.length()-1);
    	SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE" 
        		+ " FROM "
        		+ " SF_ADMIN_AUTHORITY"
        		+ " WHERE"
        		+ " ADMIN_ID IN("+str+")";
        sqlModel.setSqlStr(sqlStr);        
        return sqlModel;
    }

    public SQLModel add(String loginName) {
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SfAdminAuthorityDTO sfAdminAuthority = (SfAdminAuthorityDTO)dtoParameter;
        String sqlStr = "INSERT INTO SF_ADMIN_AUTHORITY("
			+ " USER_ID,"
			+ " PROJECT_NAME,"
			+ " GROUP_NAME"
			+ ") SELECT SU.USER_ID, ?, ?"
            + " FROM SF_USER SU WHERE UPPER(SU.LOGIN_NAME) = UPPER(?)";        
        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(sfAdminAuthority.getProjectName());
        sqlArgs.add(sfAdminAuthority.getGroupName());
        sqlArgs.add(loginName);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 username, projName 检查用户有没有取交工程权限。
     * 框架自动生成数据管理员权限信息 SF_ADMIN_AUTHORITY数据删除SQLModel，请根据实际需要修改。
     * @param username String
     * @param projName 工程名称
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel checkAuthorityModel(String username, String projName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " SAU.PROJECT_NAME,"
            + " SAU.ADMIN_ID,"
            + " SAU.USER_ID,"
            + " SAU.GROUP_NAME"
            + " FROM"
            + " SF_ADMIN_AUTHORITY SAU"
            + " WHERE EXISTS (SELECT NULL FROM SF_USER SU"
            + " WHERE"
            + " UPPER(SU.LOGIN_NAME) = UPPER(?)"
            + " AND SAU.USER_ID = SU.USER_ID"
            + " AND (? = '' OR SAU.PROJECT_NAME = '*' OR SAU.PROJECT_NAME = ?))";
        
        sqlArgs.add(username);
        sqlArgs.add(projName);
        sqlArgs.add(projName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}