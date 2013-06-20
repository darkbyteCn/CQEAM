package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamDhCatalogValuesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: EamDhCatalogValuesModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhCatalogValuesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EamDhCatalogValuesModel extends AMSSQLProducer {

	/**
	 * 功能：低值易耗品目录维护表(EAM) EAM_DH_CATALOG_VALUES 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhCatalogValuesDTO 本次操作的数据
	 */
	public EamDhCatalogValuesModel(SfUserDTO userAccount, EamDhCatalogValuesDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成低值易耗品目录维护表(EAM) EAM_DH_CATALOG_VALUES数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogValuesDTO dto = (EamDhCatalogValuesDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " EAM_DH_CATALOG_VALUES("
			+ " CATALOG_VALUE_ID,"
			+ " CATALOG_CODE,"
			+ " CATALOG_SET_ID,"
			+ " CATALOG_NAME,"
			+ " UNIT,"
			+ " DESCRIPTION,"
			+ " BARCODE_FLAG,"
			+ " COMMON_FLAG,"
			+ " ENABLED,"
			+ " CREATED_BY,"
			+ " CREATION_DATE"
			+ ") VALUES ("
			+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
	
		sqlArgs.add(dto.getCatalogCode());
		sqlArgs.add(dto.getCatalogSetId());
		sqlArgs.add(dto.getCatalogName());
		sqlArgs.add(dto.getUnit());
		sqlArgs.add(dto.getDescription());
		sqlArgs.add(dto.getBarcodeFlag());
		sqlArgs.add(dto.getCommonFlag());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(userAccount.getUserId());
			
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品目录维护表(EAM) EAM_DH_CATALOG_VALUES数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogValuesDTO dto = (EamDhCatalogValuesDTO)dtoParameter;
		String sqlStr = "UPDATE EAM_DH_CATALOG_VALUES"
			+ " SET"
			+ " CATALOG_CODE = ?,"
			+ " CATALOG_SET_ID = ?,"
			+ " CATALOG_NAME = ?,"
			+ " UNIT = ?,"
			+ " DESCRIPTION = ?,"
			+ " BARCODE_FLAG = ?,"
			+ " COMMON_FLAG = ?,"
			+ " ENABLED = ?,"
			+ " LAST_UPDATE_BY = ?,"
			+ " LAST_UPDATE_DATE = GETDATE()"
			+ " WHERE"
			+ " CATALOG_VALUE_ID = ?";
	
		sqlArgs.add(dto.getCatalogCode());
		sqlArgs.add(dto.getCatalogSetId());
		sqlArgs.add(dto.getCatalogName());
		sqlArgs.add(dto.getUnit());
		sqlArgs.add(dto.getDescription());
		sqlArgs.add(dto.getBarcodeFlag());
		sqlArgs.add(dto.getCommonFlag());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getCatalogValueId());
	
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品目录维护表(EAM) EAM_DH_CATALOG_VALUES数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogValuesDTO dto = (EamDhCatalogValuesDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " EAM_DH_CATALOG_VALUES"
				+ " WHERE"
				+ " CATALOG_VALUE_ID = ?";
			sqlArgs.add(dto.getCatalogValueId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品目录维护表(EAM) EAM_DH_CATALOG_VALUES数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogValuesDTO dto = (EamDhCatalogValuesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " CATALOG_VALUE_ID,"
			+ " CATALOG_CODE,"
			+ " CATALOG_SET_ID,"
			+ " CATALOG_NAME,"
			+ " UNIT,"
			+ " DESCRIPTION,"
			+ " BARCODE_FLAG,"
			+ " COMMON_FLAG,"
			+ " ENABLED,"
			+ " AMS_PUB_PKG.GET_USER_NAME(CREATED_BY) CREATED_BY,"
			+ " CREATION_DATE,"
			+ " AMS_PUB_PKG.GET_USER_NAME(LAST_UPDATE_BY) LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ " FROM"
			+ " EAM_DH_CATALOG_VALUES"
			+ " WHERE"
			+ " CATALOG_VALUE_ID = ?";
		sqlArgs.add(dto.getCatalogValueId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 catalogSetId 构造查询数据SQL。
	 * 框架自动生成数据低值易耗品目录维护表(EAM) EAM_DH_CATALOG_VALUES详细信息查询SQLModel，请根据实际需要修改。
	 * @param catalogSetId AdvancedNumber 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getDataByCatalogSetIdModel(int catalogSetId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " CATALOG_VALUE_ID,"
			+ " CATALOG_CODE,"
			+ " CATALOG_NAME,"
			+ " UNIT,"
			+ " DESCRIPTION,"
			+ " BARCODE_FLAG,"
			+ " COMMON_FLAG,"
			+ " ENABLED,"
			+ " AMS_PUB_PKG.GET_USER_NAME(CREATED_BY) CREATED_BY,"
			+ " CRAATION_DATE,"
			+ " AMS_PUB_PKG.GET_USER_NAME(LAST_UPDATE_BY) LAST_UPDATE_BY,"
			+ " LAST_UPDATE_DATE"
			+ " FROM"
			+ " EAM_DH_CATALOG_VALUES"
			+ " WHERE"
			+ " CATALOG_SET_ID = ?";
		sqlArgs.add(catalogSetId);
		
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
		EamDhCatalogValuesDTO dto = (EamDhCatalogValuesDTO)dtoParameter;
		if(foreignKey.equals("catalogSetId")){
			sqlModel = getDataByCatalogSetIdModel(dto.getCatalogSetId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品目录维护表(EAM) EAM_DH_CATALOG_VALUES页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCatalogValuesDTO dto = (EamDhCatalogValuesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EDCV.CATALOG_VALUE_ID,"
			+ " EDCV.CATALOG_CODE,"
			+ " EDCV.CATALOG_SET_ID,"
			+ " EDCS.SET_CODE,"
			+ " EDCS.SET_NAME,"
			+ " EDCV.CATALOG_NAME,"
			+ " EDCV.UNIT,"
			+ " EFV.VALUE UNIT_VALUE,"
			+ " EDCV.DESCRIPTION,"
			+ " CASE WHEN EDCV.BARCODE_FLAG='1' THEN '条码管理' ELSE '非条码管理' END BARCODE_FLAG,"
			+ " CASE WHEN EDCV.COMMON_FLAG='1' THEN '常用' ELSE '非常用' END COMMON_FLAG,"
			+ " CASE WHEN EDCV.ENABLED='1' THEN '是' ELSE '否' END ENABLED,"
			+ " AMS_PUB_PKG.GET_USER_NAME(EDCV.CREATED_BY) CREATED_BY,"
			+ " EDCV.CREATION_DATE,"
			+ " AMS_PUB_PKG.GET_USER_NAME(EDCV.LAST_UPDATE_BY) LAST_UPDATE_BY,"
			+ " EDCV.LAST_UPDATE_DATE"
			+ " FROM"
			+ " EAM_DH_CATALOG_VALUES EDCV,"
			+ " EAM_DH_CATALOG_SET EDCS,"
			+ " ETS_FLEX_VALUES EFV,"
			+ " ETS_FLEX_VALUE_SET EFVS"
			+ " WHERE"
			+ " EDCV.CATALOG_SET_ID=EDCS.CATLOG_SET_ID"
			+ " AND EDCV.UNIT=EFV.FLEX_VALUE_ID"
			+ " AND EFV.FLEX_VALUE_SET_ID=EFVS.FLEX_VALUE_SET_ID"
			+ " AND EDCV.CATALOG_SET_ID = dbo.NVL(? ,EDCV.CATALOG_SET_ID)"
			+ " AND EDCV.CATALOG_CODE LIKE dbo.NVL(? ,EDCV.CATALOG_CODE)"
			+ " AND EDCV.CATALOG_NAME LIKE dbo.NVL(? ,EDCV.CATALOG_NAME)"
			+ " AND EDCV.BARCODE_FLAG = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EDCV.BARCODE_FLAG)))"
			+ " AND EDCV.COMMON_FLAG = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, EDCV.COMMON_FLAG)))"
			+ " ORDER BY CREATION_DATE DESC";
		sqlArgs.add(dto.getCatalogSetId());
		sqlArgs.add(dto.getCatalogCode());
		sqlArgs.add(dto.getCatalogName());
		sqlArgs.add(dto.getBarcodeFlag());
		sqlArgs.add(dto.getCommonFlag());
	
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}