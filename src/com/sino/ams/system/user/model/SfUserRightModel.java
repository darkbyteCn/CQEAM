package com.sino.ams.system.user.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: SfUserRightModel</p>
 * <p>Description:程序自动生成SQL构造器“SfUserRightModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfUserRightModel extends BaseSQLProducer {

    /**
     * 功能：SF_USER_RIGHT 数据库SQL构造层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter SfUserRightDTO 本次操作的数据
     */
    public SfUserRightModel(SfUserDTO userAccount, SfUserRightDTO dtoParameter) {
        super(userAccount, dtoParameter);

    }

    /**
     * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
		SfUserRightDTO sfUserRight = (SfUserRightDTO) dtoParameter;
        List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
						+ " SF_USER_RIGHT("
						+ " GROUP_ID,"
						+ " ROLE_ID,"
						+ " USER_ID,"
						+ " CREATION_DATE"
						+ ") VALUES ("
						+ " ?, ?, ?, GETDATE())";

        sqlArgs.add(sfUserRight.getGroupId());
        sqlArgs.add(sfUserRight.getRoleId());
        sqlArgs.add(sfUserRight.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SfUserRightDTO sfUserRight = (SfUserRightDTO) dtoParameter;

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE SF_USER_RIGHT"
                + " SET"
                + " ROLE_ID = ?,"
                + " USER_ID = ?,"
                + " CREATION_DATE = ?,"
                + " CREATED_BY = ?,"
                + " LAST_UPDATE_DATE = ?,"
                + " LAST_UPDATE_BY = ?,"
                + " WHERE"
                + " GROUP_ID = ?";

        sqlArgs.add(sfUserRight.getRoleId());
        sqlArgs.add(sfUserRight.getUserId());
        sqlArgs.add(sfUserRight.getCreationDate());
        sqlArgs.add(sfUserRight.getCreatedBy());
        sqlArgs.add(sfUserRight.getLastUpdateDate());
        sqlArgs.add(sfUserRight.getLastUpdateBy());
        sqlArgs.add(sfUserRight.getGroupId());

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
		SfUserRightDTO sfUserRight = (SfUserRightDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " SF_USER_RIGHT"
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(sfUserRight.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel() {
        SfUserRightDTO sfUserRight = (SfUserRightDTO) dtoParameter;

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " GROUP_ID,"
                + " ROLE_ID,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY,"
                + " FROM"
                + " SF_USER_RIGHT"
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(sfUserRight.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel() {
        SfUserRightDTO sfUserRight = (SfUserRightDTO) dtoParameter;
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " GROUP_ID,"
						+ " ROLE_ID,"
						+ " USER_ID,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY"
						+ " FROM"
						+ " SF_USER_RIGHT"
						+ " WHERE"
						+ " GROUP_ID = ?"
						+ " ROLE_ID = ?"
						+ " USER_ID = ?"
						+ " CREATION_DATE = ?"
						+ " CREATED_BY = ?"
						+ " LAST_UPDATE_DATE = ?"
						+ " LAST_UPDATE_BY = ?";
		sqlArgs.add(sfUserRight.getGroupId());
		sqlArgs.add(sfUserRight.getRoleId());
		sqlArgs.add(sfUserRight.getUserId());
		sqlArgs.add(sfUserRight.getCreationDate());
		sqlArgs.add(sfUserRight.getCreatedBy());
		sqlArgs.add(sfUserRight.getLastUpdateDate());
		sqlArgs.add(sfUserRight.getLastUpdateBy());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 groupId 构造查询数据SQL。
     * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
     * @param groupId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByGroupIdModel(int groupId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " ROLE_ID,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " SF_USER_RIGHT"
                + " WHERE"
                + " GROUP_ID = ?";
        sqlArgs.add(groupId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 roleId 构造查询数据SQL。
     * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
     * @param roleId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByRoleIdModel(String roleId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " GROUP_ID,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " SF_USER_RIGHT"
                + " WHERE"
                + " ROLE_ID = ?";
        sqlArgs.add(roleId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 userId 构造查询数据SQL。
     * 框架自动生成数据详细信息查询SQLModel，请根据实际需要修改。
     * @param userId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByUserIdModel(int userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " SUR.GROUP_ID,"
						+ " '' GROUP_CODE,"
						+ " SG.GROUP_NAME,"
						+ " 0 GROUP_PROP,"
						+ " SUR.ROLE_ID,"
						+ " SR.ROLE_NAME,"
						+ " SMD.DEPT_ID,"
						+ " SMD.DEPT_NAME,"
						+ " SUR.CREATION_DATE,"
						+ " SUR.CREATED_BY,"
						+ " SUR.LAST_UPDATE_DATE,"
						+ " SUR.LAST_UPDATE_BY"
						+ " FROM"
						+ " SF_USER_RIGHT SUR,"
						+ " SF_GROUP SG,"
                        + " SF_ROLE SR,"
                        + " SINO_GROUP_MATCH SGM,"
						+ " SINO_MIS_DEPT    SMD"
						+ " WHERE"
						+ " SUR.GROUP_ID = SG.GROUP_ID"
						+ " AND SUR.ROLE_ID = SR.ROLE_ID"
						+ " AND SGM.DEPT_ID *= SMD.DEPT_ID"
						+ " AND SG.GROUP_ID *= SGM.GROUP_ID"
						+ " AND SUR.USER_ID = ?"
						+ " AND SG.ENABLED = 'Y'"
                        + " ORDER BY SR.ROLE_NAME";
		sqlArgs.add(userId);
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
                + " GROUP_ID,"
                + " ROLE_ID,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " SF_USER_RIGHT"
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
                + " GROUP_ID,"
                + " ROLE_ID,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE"
                + " FROM"
                + " SF_USER_RIGHT"
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
        SfUserRightDTO sfUserRight = (SfUserRightDTO) dtoParameter;

        SQLModel sqlModel = null;
        if (foreignKey.equals("groupId")) {
            sqlModel = getDataByGroupIdModel(sfUserRight.getGroupId());
        } else if (foreignKey.equals("roleId")) {
            sqlModel = getDataByRoleIdModel(sfUserRight.getRoleId());
        } else if (foreignKey.equals("userId")) {
            sqlModel = getDataByUserIdModel( sfUserRight.getUserId() );
        } else if (foreignKey.equals("createdBy")) {
            sqlModel = getDataByCreatedByModel(sfUserRight.getCreatedBy());
        } else if (foreignKey.equals("lastUpdateBy")) {
            sqlModel = getDataByLastUpdateByModel(sfUserRight.getLastUpdateBy());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SfUserRightDTO sfUserRight = (SfUserRightDTO) dtoParameter;

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
                + " GROUP_ID,"
                + " ROLE_ID,"
                + " USER_ID,"
                + " CREATION_DATE,"
                + " CREATED_BY,"
                + " LAST_UPDATE_DATE,"
                + " LAST_UPDATE_BY"
                + " FROM"
                + " SF_USER_RIGHT"
                + " WHERE"
                + " ISNULL(GROUP_ID, -1) = ISNULL(?, -1)"
                + " AND ISNULL(ROLE_ID, -1) = ISNULL(?, -1)"
                + " AND ISNULL(USER_ID, -1) = ISNULL(?, -1)"
                + " AND ISNULL(CREATION_DATE, -1) = ISNULL(?, -1)"
                + " AND ISNULL(CREATED_BY, -1) = ISNULL(?, -1)"
                + " AND ISNULL(LAST_UPDATE_DATE, -1) = ISNULL(?, -1)"
                + " AND ISNULL(LAST_UPDATE_BY, -1) = ISNULL(?, -1)";
        sqlArgs.add(sfUserRight.getGroupId());
        sqlArgs.add(sfUserRight.getRoleId());
        sqlArgs.add(sfUserRight.getUserId());
        sqlArgs.add(sfUserRight.getCreationDate());
        sqlArgs.add(sfUserRight.getCreatedBy());
        sqlArgs.add(sfUserRight.getLastUpdateDate());
        sqlArgs.add(sfUserRight.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
