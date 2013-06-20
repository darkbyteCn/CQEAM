package com.sino.ams.system.user.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: SfGroupModel</p>
 * <p>Description:程序自动生成SQL构造器“SfGroupModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfGroupModel extends BaseSQLProducer {

	private SfGroupDTO sfGroup = null;
	private SfUserDTO sfUser=null;

	/**
	 * 功能：SF_GROUP 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfGroupDTO 本次操作的数据
	 */
	public SfGroupModel(SfUserDTO userAccount, SfGroupDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.sfGroup = (SfGroupDTO)dtoParameter;
		this.sfUser=userAccount;
	}
	/**
	 * 功能：框架自动生成数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO "
			+ " SF_GROUP("
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORGANIZATION_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID,"
            + " GROUP_THRED"    
			+ ") VALUES ("
			+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?)";

		sqlArgs.add(sfGroup.getGroupCode());
		sqlArgs.add(sfGroup.getGroupname());
		sqlArgs.add(sfGroup.getGroupPid());
		sqlArgs.add(sfGroup.getOrganizationId());
		sqlArgs.add(sfGroup.getSortno());
		sqlArgs.add(sfGroup.getIsroot());
		sqlArgs.add(sfGroup.getCategory());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getIsInner());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfGroup.getLastUpdateDate());
		sqlArgs.add(sfGroup.getLastUpdateBy());
		sqlArgs.add(sfGroup.getIsDesigner());
		sqlArgs.add(sfGroup.getpFlowId());
		sqlArgs.add(sfGroup.getGroupThred());
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
		String sqlStr = "UPDATE SF_GROUP"
			+ " SET"
			+ " GROUP_CODE = ?,"
			+ " GROUPNAME = ?,"
			+ " GROUP_PID = ?,"
			+ " ORGANIZATION_ID = ?,"
			+ " SORTNO = ?,"
			+ " ISROOT = ?,"
			+ " CATEGORY = ?,"
			+ " ENABLED = ?,"
			+ " IS_INNER = ?,"
			+ " LAST_UPDATE_DATE = GETDATE(),"
			+ " LAST_UPDATE_BY = ?,"
			+ " IS_DESIGNER = ?,"
			+ " P_FLOW_ID = ?,"
            + " GROUP_THRED = ?"    
			+ " WHERE"
			+ " GROUP_ID = ?";

		sqlArgs.add(sfGroup.getGroupCode());
		sqlArgs.add(sfGroup.getGroupname());
		sqlArgs.add(sfGroup.getGroupPid());
		sqlArgs.add(sfGroup.getOrganizationId());
		sqlArgs.add(sfGroup.getSortno());
		sqlArgs.add(sfGroup.getIsroot());
		sqlArgs.add(sfGroup.getCategory());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getIsInner());
		sqlArgs.add(sfUser.getUserId());
		sqlArgs.add(sfGroup.getIsDesigner());
		sqlArgs.add(sfGroup.getpFlowId());
        sqlArgs.add(sfGroup.getGroupThred());
		sqlArgs.add(sfGroup.getGroupId());

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
			+ " SF_GROUP"
			+ " WHERE"
			+ " GROUP_ID = ?";
		sqlArgs.add(sfGroup.getGroupId());
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
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORGANIZATION_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID,"
            + " GROUP_THRED"    
			+ " FROM"
			+ " SF_GROUP  "
			+ " WHERE"
			+ " GROUP_ID = ?";
		sqlArgs.add(sfGroup.getGroupId());

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
			+ " GROUP_ID,"
			+ " GROUP_CODE,"
			+ " GROUPNAME,"
			+ " GROUP_PID,"
			+ " ORGANIZATION_ID,"
			+ " SORTNO,"
			+ " ISROOT,"
			+ " CATEGORY,"
			+ " ENABLED,"
			+ " IS_INNER,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY,"
			+ " IS_DESIGNER,"
			+ " P_FLOW_ID"
			+ " FROM"
			+ " SF_GROUP"
			+ " WHERE"
			+ " GROUP_ID = ?"
			+ " GROUP_CODE = ?"
			+ " GROUPNAME = ?"
			+ " GROUP_PID = ?"
			+ " ORGANIZATION_ID = ?"
			+ " SORTNO = ?"
			+ " ISROOT = ?"
			+ " CATEGORY = ?"
			+ " ENABLED = ?"
			+ " IS_INNER = ?"
			+ " CREATION_DATE = ?"
			+ " CREATED_BY = ?"
			+ " LAST_UPDATE_DATE = ?"
			+ " LAST_UPDATE_BY = ?"
			+ " IS_DESIGNER = ?"
			+ " P_FLOW_ID = ?";
		sqlArgs.add(sfGroup.getGroupId());
		sqlArgs.add(sfGroup.getGroupCode());
		sqlArgs.add(sfGroup.getGroupname());
		sqlArgs.add(sfGroup.getGroupPid());
		sqlArgs.add(sfGroup.getOrganizationId());
		sqlArgs.add(sfGroup.getSortno());
		sqlArgs.add(sfGroup.getIsroot());
		sqlArgs.add(sfGroup.getCategory());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getIsInner());
		sqlArgs.add(sfGroup.getCreationDate());
		sqlArgs.add(sfGroup.getCreatedBy());
		sqlArgs.add(sfGroup.getLastUpdateDate());
		sqlArgs.add(sfGroup.getLastUpdateBy());
		sqlArgs.add(sfGroup.getIsDesigner());
		sqlArgs.add(sfGroup.getpFlowId());

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
			+ " ORGANIZATION_ID,"
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
			+ " ORGANIZATION_ID,"
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
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey){
		SQLModel sqlModel = null;
		if(foreignKey.equals("createdBy")){
			sqlModel = getDataByCreatedByModel(sfGroup.getCreatedBy());
		} else if(foreignKey.equals("lastUpdateBy")){
			sqlModel = getDataByLastUpdateByModel(sfGroup.getLastUpdateBy());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
				+ " GROUP_ID,"
				+ " GROUP_CODE,"
				+ " GROUPNAME,"
				+ " GROUP_PID,"
				+ " AMS_PUB_PKG.GET_GROUP_NAME(GROUP_PID) P_NAME,"
				+ " ORGANIZATION_ID,"
				+ " AMS_PUB_PKG.GET_ORGNIZATION_NAME(ORGANIZATION_ID) ORGNIZATION_NAME ,"
				+ " SORTNO,"
				+ " ISROOT,"
				+ " AMS_PUB_PKG.GET_FLEX_VALUE(CATEGORY,'GROUP_CATEGORY') CATEGORY,"
				+ " CASE WHEN ENABLED='Y' THEN '是' ELSE '否' END ENABLED,"
				+ " IS_INNER,"
				+ " CREATION_DATE,"
				+ " CREATED_BY,"
				+ " CASE WHEN IS_DESIGNER='1' THEN '流程组别' WHEN IS_DESIGNER='2' THEN '共用组别' ELSE '普通组别' END IS_DESIGNER,"
				+ " P_FLOW_ID"
				+ " FROM"
				+ " SF_GROUP"
				+ " WHERE"
				+ " GROUPNAME LIKE dbo.NVL(?, GROUPNAME)"
				+ " AND ENABLED = dbo.NVL(?, ENABLED)"
				+ " AND IS_DESIGNER = dbo.NVL(?, IS_DESIGNER)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR P_FLOW_ID = ?)"
				+ " AND ORGANIZATION_ID=?";
		sqlArgs.add(sfGroup.getGroupname());
		sqlArgs.add(sfGroup.getEnabled());
		sqlArgs.add(sfGroup.getIsDesigner());
		sqlArgs.add(sfGroup.getpFlowId());
		sqlArgs.add(sfGroup.getpFlowId());
		sqlArgs.add(sfUser.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}



    /**
	 * 功能：将三级部门所选择的父组别的GROUP_THRED字段设置成自己的GROUP_ID
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getUpdateThirdGroupModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE SF_GROUP SET GROUP_THRED = ? WHERE GROUP_ID = ?";
		sqlArgs.add(sfGroup.getGroupThred());
		sqlArgs.add(sfGroup.getGroupThred());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
