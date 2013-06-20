package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.util;
import com.sino.sinoflow.dto.SfGroupDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SfGroupModel</p>
 * <p>Description:程序自动生成SQL构造器“SfGroupModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfGroupModel extends BaseSQLProducer {

	private SfUserBaseDTO sfUser = null;
	private SfGroupDTO sfGroup = null;

	/**
	 * 功能：组别属性 SF_GROUP 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfGroupDTO 本次操作的数据
	 */
	public SfGroupModel(SfUserBaseDTO userAccount, SfGroupDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfGroup = dtoParameter;
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成组别属性 SF_GROUP数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
			+ " SF_GROUP("
			+ " PROJECT_NAME,"
			+ " GROUP_NAME,"
			+ " PARENT_ID,"
			+ " GROUP_DESC,"
			+ " ENABLED"
			+ ") VALUES ("
			+ " ?, ?, dbo.SFK_GET_PARENT_ID(?,?), ?, ?)";
		
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getGroupName());
        sqlArgs.add(sfGroup.getProjectName());
        sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getEnabled());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别属性 SF_GROUP数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_GROUP"
			+ " SET"
			+ " PROJECT_NAME = ?,"
			+ " GROUP_NAME = ?,"
			+ " PARENT_ID = ?,"
			+ " GROUP_DESC = ?,"
			+ " ENABLED = ?"
			+ " WHERE"
			+ " GROUP_ID = ?";
		
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getGroupId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别属性 SF_GROUP数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_GROUP"
		                 + " SET"
		                 + " ENABLED = 'N'"
		                 + " WHERE"
			   + " GROUP_ID = ?";
		sqlArgs.add(sfGroup.getGroupId());	
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别属性 SF_GROUP数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " PROJECT_NAME,"
			+ " GROUP_ID,"
			+ " GROUP_NAME,"
			+ " PARENT_ID,"
			+ " dbo.SF_PROJECT_FUNCTION(PARENT_ID) PARENT_NAME,"
			+ " GROUP_DESC,"
			+ " ENABLED"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " GROUP_ID = ?";
		sqlArgs.add(sfGroup.getGroupId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别属性 SF_GROUP多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
			String sqlStr = "SELECT "
			+ " PROJECT_NAME,"
			+ " GROUP_ID,"
			+ " GROUP_NAME,"
			+ " PARENT_ID,"
			+ " GROUP_DESC,"
			+ " ENABLED"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " (? = '' OR PROJECT_NAME LIKE ?)"
			+ " AND (? <= 0 OR GROUP_ID = ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? <= 0 OR PARENT_ID = ?)"
			+ " AND (? = '' OR GROUP_DESC LIKE ?)"
			+ " AND (? = '' OR ENABLED LIKE ?)";
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getGroupId());
		sqlArgs.add(sfGroup.getGroupId());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getEnabled());
		
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
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORG_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID"
			+ " FROM"
			+ " SF_GROUP"
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
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORG_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " LAST_UPDATE_BY = ?";
		sqlArgs.add(lastUpdateBy);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别属性 SF_GROUP页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
			String sqlStr = "SELECT "
			+ " PROJECT_NAME,"
			+ " CONVERT(INT,GROUP_ID) GROUP_ID,"
			+ " GROUP_NAME,"
			+ " PARENT_ID,"
 			+ " dbo.SF_PROJECT_FUNCTION(PARENT_ID) PARENT_NAME,"
 			+ " GROUP_DESC,"
			+ " ENABLED"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " (? = '' OR PROJECT_NAME LIKE ?)"
			+ " AND (? <= 0 OR GROUP_ID = ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? <= 0 OR PARENT_ID = ?)"
			+ " AND (? = '' OR GROUP_DESC LIKE ?)"
			+ " AND (? = '' OR ENABLED LIKE ?)";
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getProjectName());
		sqlArgs.add(sfGroup.getGroupId());
		sqlArgs.add(sfGroup.getGroupId());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getGroupName());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getParentId());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getGroupDesc());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getEnabled());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：得到组的下列表
	 * @param pName 工程名 
     * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getOptionGroupModel(String pName) {
		SQLModel sqlModel = new SQLModel();	
		List sqlArgs = new ArrayList();
		
		String sqlStr = "SELECT "
						+ " GROUP_NAME,"
						+ " GROUP_NAME,"
						+ " GROUP_ID"
						+ " FROM"
						+ " SF_GROUP"
						+ " WHERE"
						+ " PROJECT_NAME = ?";
		sqlArgs.add(pName);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

    /**
     * 功能：从用户权限中得到相对应的组别角色
     * @param projName String 工程名称
     * @param groupName String 组别名称
     * @param roleName String 角色名称
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getUserGroupsModel(String projName, String groupName, String roleName) {
        SQLModel sqlModel = new SQLModel();
        String searchGroup;
        if(util.isMask(groupName))
            searchGroup = "";
        else
            searchGroup = groupName;
        List sqlArgs = new ArrayList();
/*
            String sqlStr = "SELECT "
                    + " DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) GROUP_NAME,"
                    + " MAX(SG.GROUP_ID)"
                    + " FROM"
                    + " SF_GROUP SG,SINO_MIS_DEPT     SMD,SINO_GROUP_MATCH  SGM, SF_USER_AUTHORITY SUA"
                    + " WHERE"
                    + " SUA.USER_ID = ?"
                    + " AND SUA.ROLE_NAME = ?"
                    + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                    + " AND SG.GROUP_ID =SGM.GROUP_ID"
                    + " AND SGM.DEPT_ID=SMD.DEPT_ID"
                    + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                    + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                    + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                    + " AND SG.ENABLED <> 'N'"
                    + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) = SG.GROUP_NAME"
                    + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?,SUA.GROUP_NAME) = 1"
                    + " GROUP BY SMD.DISPLAY_ORDER, SUA.GROUP_NAME";
*/
        String sqlStr =  "SELECT DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) GROUP_NAME,"
                + " SMD.COMPANY_CODE,"
                + " SMD.DISPLAY_ORDER"
                + " FROM SF_GROUP SG,"
                + " SINO_MIS_DEPT SMD,"
                + " SINO_GROUP_MATCH SGM,"
                + " SF_USER_AUTHORITY SUA"
                + " WHERE SUA.USER_ID = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                + " AND SGM.GROUP_ID = SG.GROUP_ID"
                + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                + " AND SG.ENABLED <> 'N'"
//                + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) = SG.GROUP_NAME"
                + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?, SUA.GROUP_NAME) = 1"
                + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(groupName);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(roleName);
        sqlArgs.add(searchGroup);
        sqlArgs.add(searchGroup);
        sqlArgs.add(projName);
        sqlArgs.add(projName);
        sqlArgs.add(groupName);
//        sqlArgs.add(groupName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUserGroupsModel(String projName, String groupName, String roleName, String neglactGroup) {
        SQLModel sqlModel = new SQLModel();
        String searchGroup;
        if(util.isMask(groupName))
            searchGroup = "";
        else
            searchGroup = groupName;
        List sqlArgs = new ArrayList();
/*
            String sqlStr = "SELECT "
                    + " DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) GROUP_NAME,"
                    + " MAX(SG.GROUP_ID)"
                    + " FROM"
                    + " SF_GROUP SG,SINO_MIS_DEPT     SMD,SINO_GROUP_MATCH  SGM, SF_USER_AUTHORITY SUA"
                    + " WHERE"
                    + " SUA.USER_ID = ?"
                    + " AND SUA.ROLE_NAME = ?"
                    + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                    + " AND SG.GROUP_ID =SGM.GROUP_ID"
                    + " AND SGM.DEPT_ID=SMD.DEPT_ID"
                    + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                    + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                    + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                    + " AND SG.ENABLED <> 'N'"
                    + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) = SG.GROUP_NAME"
                    + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?,SUA.GROUP_NAME) = 1"
                    + " GROUP BY SMD.DISPLAY_ORDER, SUA.GROUP_NAME";
*/
        String sqlStr =  "SELECT DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) GROUP_NAME,"
                + " SMD.COMPANY_CODE,"
                + " SMD.DISPLAY_ORDER"
                + " FROM SF_GROUP SG,"
                + " SINO_MIS_DEPT SMD,"
                + " SINO_GROUP_MATCH SGM,"
                + " SF_USER_AUTHORITY SUA"
                + " WHERE SUA.USER_ID = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                + " AND SGM.GROUP_ID = SG.GROUP_ID"
                + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                + " AND SG.ENABLED <> 'N'"
//                + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) = SG.GROUP_NAME"
                + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?, SUA.GROUP_NAME) = 1"
                + " AND (? = '' OR dbo.SFK_GROUP_IN_LIST(?, SUA.GROUP_NAME) <> 1)"
                + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(groupName);
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(roleName);
        sqlArgs.add(searchGroup);
        sqlArgs.add(searchGroup);
        sqlArgs.add(projName);
        sqlArgs.add(projName);
        sqlArgs.add(groupName);
//        sqlArgs.add(groupName);
        sqlArgs.add(neglactGroup);
        sqlArgs.add(neglactGroup);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUserGroupsFirstTaskModel(String projName, String groupName, String roleName) {
        SQLModel sqlModel = new SQLModel();
        String searchGroup;
        if(util.isMask(groupName))
            searchGroup = "";
        else
            searchGroup = groupName;
        List sqlArgs = new ArrayList();
/*
            String sqlStr = "SELECT "
                    + " DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) GROUP_NAME,"
                    + " MAX(SG.GROUP_ID)"
                    + " FROM"
                    + " SF_GROUP SG,SINO_MIS_DEPT     SMD,SINO_GROUP_MATCH  SGM, SF_USER_AUTHORITY SUA"
                    + " WHERE"
                    + " SUA.USER_ID = ?"
                    + " AND SUA.ROLE_NAME = ?"
                    + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                    + " AND SG.GROUP_ID =SGM.GROUP_ID"
                    + " AND SGM.DEPT_ID=SMD.DEPT_ID"
                    + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                    + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                    + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                    + " AND SG.ENABLED <> 'N'"
                    + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) = SG.GROUP_NAME"
                    + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?,SUA.GROUP_NAME) = 1"
                    + " GROUP BY SMD.DISPLAY_ORDER, SUA.GROUP_NAME";
*/
        String sqlStr =  "SELECT DISTINCT SUA.GROUP_NAME GROUP_NAME,"
                + " SMD.COMPANY_CODE,"
                + " SMD.DISPLAY_ORDER"
                + " FROM SF_GROUP SG,"
                + " SINO_MIS_DEPT SMD,"
                + " SINO_GROUP_MATCH SGM,"
                + " SF_USER_AUTHORITY SUA"
                + " WHERE SUA.USER_ID = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                + " AND SGM.GROUP_ID = SG.GROUP_ID"
                + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                + " AND SG.ENABLED <> 'N'"
//                + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) = SG.GROUP_NAME"
                + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?, SUA.GROUP_NAME) = 1"
                + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(roleName);
        sqlArgs.add(searchGroup);
        sqlArgs.add(searchGroup);
        sqlArgs.add(projName);
        sqlArgs.add(projName);
        sqlArgs.add(groupName);
//        sqlArgs.add(groupName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUserGroupsFirstTaskModel(String projName, String groupName, String roleName, String neglactGroup) {
        SQLModel sqlModel = new SQLModel();
        String searchGroup;
        if(util.isMask(groupName))
            searchGroup = "";
        else
            searchGroup = groupName;
        List sqlArgs = new ArrayList();
/*
            String sqlStr = "SELECT "
                    + " DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) GROUP_NAME,"
                    + " MAX(SG.GROUP_ID)"
                    + " FROM"
                    + " SF_GROUP SG,SINO_MIS_DEPT     SMD,SINO_GROUP_MATCH  SGM, SF_USER_AUTHORITY SUA"
                    + " WHERE"
                    + " SUA.USER_ID = ?"
                    + " AND SUA.ROLE_NAME = ?"
                    + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                    + " AND SG.GROUP_ID =SGM.GROUP_ID"
                    + " AND SGM.DEPT_ID=SMD.DEPT_ID"
                    + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                    + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                    + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                    + " AND SG.ENABLED <> 'N'"
                    + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME,?) = SG.GROUP_NAME"
                    + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?,SUA.GROUP_NAME) = 1"
                    + " GROUP BY SMD.DISPLAY_ORDER, SUA.GROUP_NAME";
*/
        String sqlStr =  "SELECT DISTINCT SUA.GROUP_NAME GROUP_NAME,"
                + " SMD.COMPANY_CODE,"
                + " SMD.DISPLAY_ORDER"
                + " FROM SF_GROUP SG,"
                + " SINO_MIS_DEPT SMD,"
                + " SINO_GROUP_MATCH SGM,"
                + " SF_USER_AUTHORITY SUA"
                + " WHERE SUA.USER_ID = ?"
                + " AND SUA.ROLE_NAME = ?"
                + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                + " AND SGM.GROUP_ID = SG.GROUP_ID"
                + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                + " AND (? = '' OR SUA.GROUP_NAME = ?)"
                + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                + " AND SG.PROJECT_NAME = SUA.PROJECT_NAME"
                + " AND SG.ENABLED <> 'N'"
//                + " AND dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) = SG.GROUP_NAME"
                + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?, SUA.GROUP_NAME) = 1"
                + " AND (? = '' OR dbo.SFK_GROUP_IN_LIST(?, SUA.GROUP_NAME) <> 1)"
                + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(roleName);
        sqlArgs.add(searchGroup);
        sqlArgs.add(searchGroup);
        sqlArgs.add(projName);
        sqlArgs.add(projName);
        sqlArgs.add(groupName);
//        sqlArgs.add(groupName);
        sqlArgs.add(neglactGroup);
        sqlArgs.add(neglactGroup);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从用户权限中得到相对应的组别角色
     * @param projName String 工程名称
     * @param roleName String 角色名称
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getUserAllGroupsModel(String projName, String roleName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
                    + " DISTINCT SG.GROUP_ID,"
                    + " SUA.GROUP_NAME,"
                    + " SMD.COMPANY_CODE,"
                    + " SMD.DISPLAY_ORDER"
                    + " FROM"
                    + " SF_GROUP SG, SF_USER_AUTHORITY SUA, SINO_MIS_DEPT SMD, SINO_GROUP_MATCH SGM"
                    + " WHERE"
                    + " SUA.USER_ID = ?"
                    + " AND (? = '' OR SUA.ROLE_NAME = ?)"
                    + " AND (? = '' OR SUA.PROJECT_NAME = ?)"
                    + " AND SUA.GROUP_NAME = SG.GROUP_NAME"
                    + " AND SGM.GROUP_ID = SG.GROUP_ID"
                    + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                    + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(roleName);
        sqlArgs.add(roleName);
        sqlArgs.add(projName);
        sqlArgs.add(projName);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：从用户权限中得到相对应的组别角色
     * @param projName String 工程名称
     * @param roleName String 角色名称
     * @param matchGroup 对应组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getUserAllGroupsModel(String projName, String matchGroup, String roleName, String excludeGroup) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = " SELECT "
                    + " DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) GROUP_NAME,"
                    + " SMD.COMPANY_CODE,"
                    + " SMD.DISPLAY_ORDER"
                    + " FROM"
                    + " SF_USER_AUTHORITY SUA, SINO_MIS_DEPT SMD, SINO_GROUP_MATCH SGM, SF_GROUP SG"
                    + " WHERE"
                    + " SUA.ROLE_NAME = ?"
                    + " AND SUA.PROJECT_NAME = ?"
                    + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?, SUA.GROUP_NAME) <> 0"
                    + " AND SG.GROUP_NAME = SUA.GROUP_NAME"
                    + " AND SGM.GROUP_ID = SG.GROUP_ID"
                    + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                    + " AND (? = '' OR dbo.SFK_GROUP_IN_LIST(?, SUA.GROUP_NAME) <> 1)"
                    + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(matchGroup);
        sqlArgs.add(roleName);
        sqlArgs.add(projName);
        sqlArgs.add(matchGroup);
        sqlArgs.add(excludeGroup);
        sqlArgs.add(excludeGroup);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUserAllGroupsModel(String projName, String matchGroup, String roleName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = " SELECT "
                    + " DISTINCT dbo.SFK_GET_MATCH_GROUP(SUA.GROUP_NAME, ?) GROUP_NAME,"
                    + " SMD.COMPANY_CODE,"
                    + " SMD.DISPLAY_ORDER"
                    + " FROM"
                    + " SF_USER_AUTHORITY SUA, SINO_MIS_DEPT SMD, SINO_GROUP_MATCH SGM, SF_GROUP SG"
                    + " WHERE"
                    + " SUA.ROLE_NAME = ?"
                    + " AND SUA.PROJECT_NAME = ?"
                    + " AND dbo.SFK_IS_SAME_GROUP_WITH_MASK(?, SUA.GROUP_NAME) <> 0"
                    + " AND SG.GROUP_NAME = SUA.GROUP_NAME"
                    + " AND SGM.GROUP_ID = SG.GROUP_ID"
                    + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                    + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(matchGroup);
        sqlArgs.add(roleName);
        sqlArgs.add(projName);
        sqlArgs.add(matchGroup);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：查询组别有没有存在
     * @param proj String 工程名称
     * @param group 对应组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel checkGroupExistModel(String proj, String group) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
                    + " *"
                    + " FROM"
                    + " SF_GROUP"
                    + " WHERE"
                    + " PROJECT_NAME = ?"
                    + " AND GROUP_NAME = ?";

        sqlArgs.add(proj);
        sqlArgs.add(group);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从用户权限中得到相对应的组别角色
     * @param dept String 工程名称
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getGroupsByDeptModel(String dept) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
                    + " SG.GROUP_NAME,"
                    + " SMD.COMPANY_CODE,"
                    + " SMD.DISPLAY_ORDER"
                    + " FROM"
                    + " SF_GROUP SG, SINO_MIS_DEPT SMD, SINO_GROUP_MATCH SGM"
                    + " WHERE"
                    + " dbo.SFK_GROUP_IN_LIST(?, SMD.DEPT_NAME) <> 0"
                    + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                    + " AND SG.GROUP_ID = SGM.GROUP_ID"
                    + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(dept);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getGroupByUserIdModel(int userId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
                    + " SG.GROUP_NAME,"
                    + " SMD.COMPANY_CODE,"
                    + " SMD.DISPLAY_ORDER"
                    + " FROM"
                    + " SF_USER SU, SF_GROUP SG, SINO_MIS_DEPT SMD, SINO_GROUP_MATCH SGM"
                    + " WHERE"
                    + " SU.USER_ID = ?"
                    + " AND SMD.DEPT_ID = SU.DEPT_CODE"
                    + " AND SMD.DEPT_ID = SGM.DEPT_ID"
                    + " AND SG.GROUP_ID = SGM.GROUP_ID"
                    + " ORDER BY SMD.COMPANY_CODE, SMD.DISPLAY_ORDER";

        sqlArgs.add(userId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}