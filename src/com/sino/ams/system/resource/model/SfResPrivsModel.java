package com.sino.ams.system.resource.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.resource.dto.SfResPrivsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SfResPrivsModel</p>
 * <p>Description:程序自动生成SQL构造器“SfResPrivsModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) {year}</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfResPrivsModel extends BaseSQLProducer {

	private SfResPrivsDTO sfResPrivs = null;
	private SfUserDTO sfUser = null;
/**
	 * 功能：SF_RES_PRIVS 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfResPrivsDTO 本次操作的数据
	 */
	public SfResPrivsModel(SfUserDTO userAccount, SfResPrivsDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.sfResPrivs = (SfResPrivsDTO) dtoParameter;
		this.sfUser = userAccount;
	}

/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
+ " SF_RES_PRIVS("
						+ " ROLE_NAME,"
						+ " SYSTEM_ID,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY"
						+ ") VALUES ("
						+ " ?, ?, ?, ?, ?, ?)";

		sqlArgs.add(sfResPrivs.getResId());
		sqlArgs.add(sfResPrivs.getCreationDate());
		sqlArgs.add(sfResPrivs.getCreatedBy());
		sqlArgs.add(sfResPrivs.getLastUpdateDate());
		sqlArgs.add(sfResPrivs.getLastUpdateBy());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_RES_PRIVS"
						+ " SET"
						+ " GROUP_NAME = ?"
						+ " SYSTEM_ID = ?"
						+ " CREATION_DATE = ?"
						+ " CREATED_BY = ?"
						+ " LAST_UPDATE_DATE = ?"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " ROLE_NAME = ?";

		sqlArgs.add(sfResPrivs.getSystemId());
		sqlArgs.add(sfResPrivs.getCreationDate());
		sqlArgs.add(sfResPrivs.getCreatedBy());
		sqlArgs.add(sfResPrivs.getLastUpdateDate());
		sqlArgs.add(sfResPrivs.getLastUpdateBy());
		sqlArgs.add(sfResPrivs.getRoleName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " SF_RES_PRIVS"
						+ " WHERE"
						+ " ROLE_ID = ?";
		sqlArgs.add(sfResPrivs.getRoleId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " GROUP_NAME,"
						+ " SYSTEM_ID,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY,"
						+ " FROM"
						+ " SF_RES_PRIVS + "
						+ " WHERE"
						+ " ROLE_NAME = ?";
		sqlArgs.add(sfResPrivs.getRoleName());

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
	private SQLModel getDataByCreatedByModel(int createdBy) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " GROUP_NAME,"
						+ " ROLE_NAME,"
						+ " SYSTEM_ID,"
						+ " CREATION_DATE,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY"
						+ " FROM"
						+ " SF_RES_PRIVS"
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
	private SQLModel getDataByLastUpdateByModel(int lastUpdateBy) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " FROM"
						+ " SF_RES_PRIVS"
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
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		if (foreignKey.equals("groupId")) {
//			sqlModel = getDataByGroupIdModel(sfResPrivs.getGroupId());
		} else if (foreignKey.equals("createdBy")) {
			sqlModel = getDataByCreatedByModel(sfResPrivs.getCreatedBy());
		} else if (foreignKey.equals("lastUpdateBy")) {
			sqlModel = getDataByLastUpdateByModel(sfResPrivs.getLastUpdateBy());
		}
		return sqlModel;
	}

/**
	 * 功能：获取URL资源菜单栏目的下拉列表SQL。
	 * @return SQLModel
	 */
	public SQLModel getResourceOptionModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " SRD.RES_ID,"
						+ " SRD.RES_NAME"
						+ " FROM"
						+ " SF_RES_DEFINE SRD"
						+ " ORDER  BY"
						+ " SRD.RES_ID,"
						+ " SRD.RES_PAR_ID";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：产生URL资源树的SQL。栏目定义用
	 * @return SQLModel
	 */
	public SQLModel getResourceTreeModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = " SELECT"
						+ " SRD.SYSTEM_ID,"
						+ " SRD.RES_ID,"
						+ " SRD.RES_PAR_ID,"
+ " SRD.RES_NAME,"
						+ " SRD.RES_ID RES_URL,"
						+ " SRD.SORT_NO,"
+ " 'N' IS_POPUP,"
+ " SRD.POPSCRIPT"
						+ " FROM"
						+ " SF_RES_DEFINE SRD"
						+ " WHERE"
						+ " SRD.ENABLED = 'Y'"
						+ " AND SRD.VISIBLE = 'Y'"
						+ " ORDER BY"
						+ " SRD.RES_ID,"
						+ " SRD.RES_PAR_ID";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：获取第一个URL资源SQL。栏目权限管理用
	 * @return SQLModel
	 */
	public SQLModel getFirstResourceModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = " SELECT"
						+ " SRD.*"
						+ " FROM"
						+ " SF_RES_DEFINE SRD"
						+ " WHERE"
						+ " SRD.ENABLED = 'Y'"
						+ " AND SRD.VISIBLE = 'Y'"
						+ " ORDER BY"
						+ " SRD.RES_ID,"
						+ " SRD.RES_PAR_ID";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：获取第一个URL资源SQL。栏目权限管理用
	 * @return SQLModel
	 */
	public SQLModel getResourceByIdModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT"
						+ " SRD.*"
						+ " FROM"
						+ " SF_RES_DEFINE SRD"
						+ " WHERE"
						+ " SRD.RES_ID = ?";
		sqlArgs.add(sfResPrivs.getResId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取查询父资源的SQL。
	 * @param systemId String
	 * @return SQLModel
	 */
	public SQLModel getParentResModel(int systemId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " SF_RES_DEFINE SRD"
						+ " WHERE"
						+ " CHARINDEX(("
						+ " SELECT"
						+ " SRD2.RES_ID"
						+ " FROM"
						+ " SF_RES_DEFINE SRD2"
						+ " WHERE SRD2.SYSTEM_ID = ?),"
						+ " SRD.RES_ID||'.') = 1";
		sqlArgs.add(systemId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取权限删除SQL
	 * @param sfResPriv SfResPrivsDTO
	 * @return SQLModel
	 */
	public SQLModel getPriviDeleteModel(SfResPrivsDTO sfResPriv) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " SF_RES_PRIVS"
						+ " WHERE"
						+ " RES_ID = ?";
		sqlArgs.add(sfResPriv.getResId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取权限数据是否存在的SQL
	 * @param sfResPriv SfResPrivsDTO
	 * @return List
	 */
	public SQLModel getPriviExistModel(SfResPrivsDTO sfResPriv) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT"
						+ " *"
						+ " FROM"
						+ " SF_RES_PRIVS"
						+ " WHERE"
						+ " ROLE_ID = ?"
						+ " AND RES_ID = ?";
		sqlArgs.add(sfResPriv.getRoleId());
		sqlArgs.add(sfResPriv.getResId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取权限插入SQL
	 * @param sfResPriv SfResPrivsDTO
	 * @return List
	 */
	public SQLModel getPriviCreateModel(SfResPrivsDTO sfResPriv) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " INSERT INTO"
						+ " SF_RES_PRIVS("
						+ " ROLE_ID,"
						+ " RES_ID,"
						+ " CREATED_BY"
						+ " ) VALUES (?, ?, ?)";
		sqlArgs.add(sfResPriv.getRoleId());
		sqlArgs.add(sfResPriv.getResId());
		sqlArgs.add(sfUser.getUserId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
