package com.sino.ams.system.comparison.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.comparison.dto.EtsObjectCatGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectCatGroupModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsObjectCatGroupModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsObjectCatGroupModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsObjectCatGroupDTO 本次操作的数据
	 */
	public EtsObjectCatGroupModel(SfUserDTO userAccount, EtsObjectCatGroupDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel(String groupId,String Category) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
//		try {
			List sqlArgs = new ArrayList();
			EtsObjectCatGroupDTO etsObjectCatGroup = (EtsObjectCatGroupDTO)dtoParameter;
			String sqlStr =" INSERT INTO "
				+ " ETS_OBJECT_CAT_GROUP("
				+ " SYSTEMID,"
				+ " GROUP_ID,"
				+ " OBJECT_CATEGORY,"
				+ " CREATION_DATE,"
				+ " CREATED_BY"
//				+ " LAST_UPDATE_DATE,"
//				+ " LAST_UPDATE_BY"
				+ ") VALUES ("
				+ " NEWID() , "+ groupId +", "+ Category +", GETDATE() , ?)";
		
//			sqlArgs.add(etsObjectCatGroup.getGroupId());
//			sqlArgs.add(etsObjectCatGroup.getObjectCategory());
//			sqlArgs.add(etsObjectCatGroup.getCreationDate());
			sqlArgs.add(sfUser.getUserId());
//			sqlArgs.add(etsObjectCatGroup.getLastUpdateDate());
//			sqlArgs.add(etsObjectCatGroup.getLastUpdateBy());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
//		} catch (CalendarException ex) {
//			ex.printLog();
//			throw new SQLModelException(ex);
//		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EtsObjectCatGroupDTO etsObjectCatGroup = (EtsObjectCatGroupDTO)dtoParameter;
			String sqlStr = "UPDATE ETS_OBJECT_CAT_GROUP"
				+ " SET"
				+ " GROUP_ID = ?,"
				+ " OBJECT_CATEGORY = ?,"
				+ " CREATION_DATE = ?,"
				+ " CREATED_BY = ?,"
				+ " LAST_UPDATE_DATE = ?,"
				+ " LAST_UPDATE_BY = ?"
				+ " WHERE"
				+ " SYSTEMID = ?";
		
			sqlArgs.add(etsObjectCatGroup.getGroupId());
			sqlArgs.add(etsObjectCatGroup.getObjectCategory());
			sqlArgs.add(etsObjectCatGroup.getCreationDate());
			sqlArgs.add(etsObjectCatGroup.getCreatedBy());
			sqlArgs.add(etsObjectCatGroup.getLastUpdateDate());
			sqlArgs.add(etsObjectCatGroup.getLastUpdateBy());
			sqlArgs.add(etsObjectCatGroup.getSystemid());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(String groupId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectCatGroupDTO etsObjectCatGroup = (EtsObjectCatGroupDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " ETS_OBJECT_CAT_GROUP"
				+ " WHERE"
				+ " GROUP_ID = "+groupId;
//			sqlArgs.add(etsObjectCatGroup.getSystemid());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsObjectCatGroupDTO etsObjectCatGroup = (EtsObjectCatGroupDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SYSTEMID,"
			+ " GROUP_ID,"
			+ " OBJECT_CATEGORY,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_OBJECT_CAT_GROUP"
			+ " WHERE"
			+ " SYSTEMID = ?";
		sqlArgs.add(etsObjectCatGroup.getSystemid());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EtsObjectCatGroupDTO etsObjectCatGroup = (EtsObjectCatGroupDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " SYSTEMID,"
				+ " GROUP_ID,"
				+ " OBJECT_CATEGORY,"
				+ " CREATION_DATE,"
				+ " CREATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " LAST_UPDATE_BY"
				+ " FROM"
				+ " ETS_OBJECT_CAT_GROUP"
				+ " WHERE"
				+ " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR GROUP_ID LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR OBJECT_CATEGORY LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
			sqlArgs.add(etsObjectCatGroup.getSystemid());
			sqlArgs.add(etsObjectCatGroup.getSystemid());
			sqlArgs.add(etsObjectCatGroup.getGroupId());
			sqlArgs.add(etsObjectCatGroup.getGroupId());
			sqlArgs.add(etsObjectCatGroup.getObjectCategory());
			sqlArgs.add(etsObjectCatGroup.getObjectCategory());
			sqlArgs.add(etsObjectCatGroup.getCreationDate());
			sqlArgs.add(etsObjectCatGroup.getCreationDate());
			sqlArgs.add(etsObjectCatGroup.getCreatedBy());
			sqlArgs.add(etsObjectCatGroup.getCreatedBy());
			sqlArgs.add(etsObjectCatGroup.getLastUpdateDate());
			sqlArgs.add(etsObjectCatGroup.getLastUpdateDate());
			sqlArgs.add(etsObjectCatGroup.getLastUpdateBy());
			sqlArgs.add(etsObjectCatGroup.getLastUpdateBy());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成组别-专业地点分类对照表(EAM) ETS_OBJECT_CAT_GROUP页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
//		try {
			List sqlArgs = new ArrayList();
			EtsObjectCatGroupDTO etsObjectCatGroup = (EtsObjectCatGroupDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " EOCG.SYSTEMID,"
				+ " EOCG.GROUP_ID,"
                + " dbo.APP_GET_GROUP_NAME(SG.GROUP_ID) GROUP_NAME,"
                + " dbo.APP_GET_FLEX_VALUE(  CONVERT( VARCHAR , EOCG.OBJECT_CATEGORY ), ?) OBJECT_CATEGORY,\n"
//                + " EOCG.OBJECT_CATEGORY,"
//				+ " EOCG.CREATION_DATE,"
				+ " EOCG.CREATED_BY,"
//				+ " EOCG.LAST_UPDATE_DATE,"
				+ " EOCG.LAST_UPDATE_BY"
				+ " FROM"
				+ " ETS_OBJECT_CAT_GROUP EOCG,"
				+ " SF_GROUP SG"
				+ " WHERE"
				+ " SG.GROUP_ID = EOCG.GROUP_ID"
				+ " AND SG.ORGANIZATION_ID = ?"
				+ " AND ( " + SyBaseSQLUtil.nullIntParam() + " OR EOCG.GROUP_ID = ?)"
				+ " AND ( " + SyBaseSQLUtil.nullIntParam() + " OR EOCG.OBJECT_CATEGORY = ?)";
//				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCG.CREATION_DATE LIKE ?)"
//				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCG.CREATED_BY = ?)"
//				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCG.LAST_UPDATE_DATE LIKE ?)"
//				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EOCG.LAST_UPDATE_BY = ?)";
			
            sqlArgs.add(DictConstant.OBJECT_CATEGORY);
            sqlArgs.add(sfUser.getOrganizationId());
            SyBaseSQLUtil.nullIntParamArgs(sqlArgs,  etsObjectCatGroup.getGroupId() );
            SyBaseSQLUtil.nullStringParamArgs(sqlArgs,  etsObjectCatGroup.getObjectCategory() );
            
//            sqlArgs.add(etsObjectCatGroup.getGroupId());
//			sqlArgs.add(etsObjectCatGroup.getGroupId());
//			sqlArgs.add(etsObjectCatGroup.getObjectCategory());
//			sqlArgs.add(etsObjectCatGroup.getObjectCategory());
//			sqlArgs.add(etsObjectCatGroup.getCreationDate());
//			sqlArgs.add(etsObjectCatGroup.getCreationDate());
//			sqlArgs.add(etsObjectCatGroup.getCreatedBy());
//			sqlArgs.add(etsObjectCatGroup.getCreatedBy());
//			sqlArgs.add(etsObjectCatGroup.getLastUpdateDate());
//			sqlArgs.add(etsObjectCatGroup.getLastUpdateDate());
//			sqlArgs.add(etsObjectCatGroup.getLastUpdateBy());
//			sqlArgs.add(etsObjectCatGroup.getLastUpdateBy());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
//		} catch (CalendarException ex) {
//			ex.printLog();
//			throw new SQLModelException(ex);
//		}
		return sqlModel;
	}

}