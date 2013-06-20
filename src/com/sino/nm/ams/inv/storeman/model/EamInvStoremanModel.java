package com.sino.nm.ams.inv.storeman.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.nm.ams.inv.storeman.dto.EamInvStoremanDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.FileException;
import com.sino.base.file.FileUtil;

/**
 * <p>Title: EamInvStoremanModel</p>
 * <p>Description:默认仓管员维护</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yushibo
 * @version 1.0
 */
public class EamInvStoremanModel extends AMSSQLProducer {

	/**
	 * 功能：默认仓管员表(AMS) EAM_INV_STOREMAN 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamInvStoremanDTO 本次操作的数据
	 */
	public EamInvStoremanModel(SfUserDTO userAccount, EamInvStoremanDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：默认仓管员维护查询功能。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EIS.STOREMAN_ID,"
						+ " EIS.USER_ID,"
						+ " EO.WORKORDER_OBJECT_NO,"
						+ " EO.WORKORDER_OBJECT_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " dbo.APP_GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'INV_TYPE') INV_CATEGORY_NAME,"
						+ " dbo.APP_GET_FLEX_VALUE(EO.BUSINESS_CATEGORY, 'INV_BIZ_CATEGORY') BIZ_CATEGORY_NAME,"
//						+ " SU.USERNAME USER_NAME,"
						+ " dbo.APP_GET_USER_NAME(EIS.USER_ID) USER_NAME,"
//						+ " EIS.USER_ID,"
//						+ " SU1.USERNAME CREATED_USER,"
						+ " dbo.APP_GET_USER_NAME(EIS.CREATED_BY) CREATED_USER,"
						+ " EIS.CREATION_DATE,"
//						+ " SU2.USERNAME UPDATED_USER,"
						+ " dbo.APP_GET_USER_NAME(EIS.LAST_UPDATE_BY) UPDATED_USER,"
						+ " EIS.LAST_UPDATE_DATE"
						+ " FROM"
						+ " ETS_OBJECT       EO,"
						+ " SF_USER          SU,"
//						+ " SF_USER          SU1,"
//						+ " SF_USER          SU2,"
						+ " EAM_INV_STOREMAN EIS"
						+ " WHERE"
						+ " EIS.USER_ID *= SU.USER_ID "
//						+ " AND SU1.USER_ID = EIS.CREATED_BY(+)"
//						+ " AND SU2.USER_ID = EIS.LAST_UPDATE_BY(+)"
						+ " AND EO.WORKORDER_OBJECT_NO *= EIS.WORKORDER_OBJECT_NO"
						+ " AND CONVERT(INT,EO.OBJECT_CATEGORY,1) BETWEEN 70 AND 79"
						+ " AND CONVERT(INT,EO.OBJECT_CATEGORY,1) <> 74"
//						+ " AND EO.WORKORDER_OBJECT_NO LIKE NVL(?, EO.WORKORDER_OBJECT_NO)"
						+ " AND ("+ SyBaseSQLUtil.nullStringParam() +" OR EO.WORKORDER_OBJECT_NO LIKE ?)"
//						+ " AND EO.WORKORDER_OBJECT_CODE LIKE NVL(?, EO.WORKORDER_OBJECT_CODE)"
//						+ " AND EO.WORKORDER_OBJECT_NAME LIKE NVL(?, EO.WORKORDER_OBJECT_NAME)"
//						+ " AND EO.ORGANIZATION_ID = NVL(?, EO.ORGANIZATION_ID)"
						+ " AND EO.ORGANIZATION_ID = ?"
						+ " AND EO.OBJECT_CATEGORY = dbo.NVL(?, EO.OBJECT_CATEGORY)"
						+ " AND ("+ SyBaseSQLUtil.nullStringParam() +" OR EO.BUSINESS_CATEGORY LIKE ?)";

		sqlArgs.add(dto.getWorkorderObjectNo1());
		sqlArgs.add(dto.getWorkorderObjectNo1());
		sqlArgs.add(dto.getWorkorderObjectNo1());
//		sqlArgs.add(dto.getWorkorderObjectCode());
//		sqlArgs.add(dto.getWorkorderObjectName());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getObjectCategory());
		
		sqlArgs.add(dto.getBusinessCategory());
		sqlArgs.add(dto.getBusinessCategory());
		sqlArgs.add(dto.getBusinessCategory());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
//		try {
//			FileUtil.saveStrContent(sqlModel.toString(), "C:\\task.sql");
//		} catch (FileException e) {
//			e.printLog();
//		}
		
		return sqlModel;
	 }

	/**
	 * 功能：框架自动生成EAM_INV_STOREMAN根据WORKORDER_OBJECT_NO的键值查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "";
		sqlStr = "SELECT"
				+ " EO.WORKORDER_OBJECT_NAME,"
				+ " dbo.APP_GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'INV_TYPE') INV_CATEGORY_NAME,"
				+ " dbo.APP_GET_FLEX_VALUE(EO.BUSINESS_CATEGORY, 'INV_BIZ_CATEGORY') BIZ_CATEGORY_NAME,"
//				+ " SU.USERNAME USER_NAME,"
				+ " dbo.APP_GET_USER_NAME(EIS.USER_ID) USER_NAME,"
//				+ " SU1.USERNAME CREATED_USER,"
				+ " dbo.APP_GET_USER_NAME(EIS.CREATED_BY) CREATED_USER,"
				+ " EIS.CREATION_DATE,"
//				+ " SU2.USERNAME UPDATED_USER,"
				+ " dbo.APP_GET_USER_NAME(EIS.LAST_UPDATE_BY) UPDATED_USER,"
				+ " EIS.LAST_UPDATE_DATE,"
				+ " EIS.USER_ID,"
				+ " EIS.WORKORDER_OBJECT_NO,"
				+ " EIS.LAST_UPDATE_BY,"
				+ " EIS.STOREMAN_ID"
				+ " FROM"
				+ " ETS_OBJECT EO,"
				+ " SF_USER SU,"
//				+ " SF_USER SU1,"
//				+ " SF_USER SU2,"
				+ " EAM_INV_STOREMAN EIS"
				+ " WHERE"
				+ " SU.USER_ID *= EIS.USER_ID"
//				+ " AND SU1.USER_ID = EIS.CREATED_BY(+)"
//				+ " AND SU2.USER_ID = EIS.LAST_UPDATE_BY(+)"
				+ " AND EO.WORKORDER_OBJECT_NO *= EIS.WORKORDER_OBJECT_NO"
				+ " AND EIS.STOREMAN_ID = ?";
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	
	public SQLModel getPrimaryKeyDataModel(String workorderObjectNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "";
		sqlStr = "SELECT"
				+ " EO.WORKORDER_OBJECT_NAME,"
				+ " dbo.APP_GET_FLEX_VALUE(EO.OBJECT_CATEGORY, 'INV_TYPE') INV_CATEGORY_NAME,"
				+ " dbo.APP_GET_FLEX_VALUE(EO.BUSINESS_CATEGORY, 'INV_BIZ_CATEGORY') BIZ_CATEGORY_NAME,"
				+ " dbo.APP_GET_USER_NAME(EIS.USER_ID) USER_NAME,"
				+ " dbo.APP_GET_USER_NAME(EIS.CREATED_BY) CREATED_USER,"
				+ " EIS.CREATION_DATE,"
				+ " dbo.APP_GET_USER_NAME(EIS.LAST_UPDATE_BY) UPDATED_USER,"
				+ " EIS.LAST_UPDATE_DATE,"
				+ " EIS.USER_ID,"
				+ " EIS.WORKORDER_OBJECT_NO,"
				+ " EIS.LAST_UPDATE_BY,"
				+ " EIS.STOREMAN_ID"
				+ " FROM"
				+ " ETS_OBJECT EO,"
				+ " SF_USER SU,"
				+ " EAM_INV_STOREMAN EIS"
				+ " WHERE"
				+ " EIS.USER_ID *= SU.USER_ID"
				+ " AND EO.WORKORDER_OBJECT_NO *= EIS.WORKORDER_OBJECT_NO"
				+ " AND EIS.WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(workorderObjectNo);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	
	
	/**
	 * 功能：框架自动生成EAM_INV_STOREMAN根据WORKORDER_OBJECT_NO的键值查询SQLModel，请根据实际需要修改。
	 *
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getUniqueKeyDataModel(String workorderObjectNo) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		//EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "";
		sqlStr = "SELECT 1"
				//+ " EIS.WORKORDER_OBJECT_NO CODE_EXIST,"
				//+ " EIS.STOREMAN_ID"
				+ " FROM"
				+ " EAM_INV_STOREMAN EIS"
				+ " WHERE"
				+ " EIS.WORKORDER_OBJECT_NO = ?";
		sqlArgs.add(workorderObjectNo);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点记录 EAM_INV_STOREMAN数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " EAM_INV_STOREMAN("
						+ " STOREMAN_ID,"
						+ " USER_ID,"
						+ " WORKORDER_OBJECT_NO,"
						+ " CREATED_BY,"
						+ " CREATION_DATE"
						+ ") VALUES ("
						+ " NEWID(), ?, ?, ?, GETDATE())";

		sqlArgs.add(dto.getUserId());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：框架自动生成资产盘点记录 EAM_INV_STOREMAN数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(String storemanId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "UPDATE EAM_INV_STOREMAN"
						+ " SET"
						+ " USER_ID = ?,"
//						+ " EIS.WORKORDER_OBJECT_NO = ?,"
						+ " LAST_UPDATE_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE()"
						+ " WHERE"
						+ " STOREMAN_ID = ?";
		
		sqlArgs.add(dto.getUserId());
//		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(userAccount.getUserId());
//		sqlArgs.add(dto.getLastUpdateBy());
		sqlArgs.add(storemanId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "UPDATE EAM_INV_STOREMAN"
						+ " SET"
						+ " USER_ID = ?,"
//						+ " EIS.WORKORDER_OBJECT_NO = ?,"
						+ " LAST_UPDATE_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE()"
						+ " WHERE"
						+ " STOREMAN_ID = ?";
		sqlArgs.add(dto.getUserId());
//		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(userAccount.getUserId());
//		sqlArgs.add(dto.getLastUpdateBy());
		sqlArgs.add(dto.getStoremanId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	/**
	 * 功能：批量更新EAM_INV_STOREMAN表中的USER_ID字段
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDatasUpdateModel(String storemanId, String userId) {
		
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "UPDATE EAM_INV_STOREMAN"
						+ " SET"
						+ " USER_ID = ?,"
						+ " LAST_UPDATE_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE()"
						+ " WHERE"
						+ " STOREMAN_ID = ?";
		
		sqlArgs.add(userId);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(storemanId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}
	
	/**
	 * 功能：批量插入EAM_INV_STOREMAN表
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDatasCreateModel(String workorderObjectNo, String userId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamInvStoremanDTO dto = (EamInvStoremanDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " EAM_INV_STOREMAN("
						+ " STOREMAN_ID,"
						+ " USER_ID,"
						+ " WORKORDER_OBJECT_NO,"
						+ " CREATED_BY,"
						+ " CREATION_DATE"
						+ ") VALUES ("
						+ " NEWID(), ?, ?, ?, GETDATE())";

		sqlArgs.add(userId);
//		sqlArgs.add(dto.getUserId());
//		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(workorderObjectNo);
		//sqlArgs.add(dto.getCreatedBy());
		//sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(userAccount.getUserId());
		
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
