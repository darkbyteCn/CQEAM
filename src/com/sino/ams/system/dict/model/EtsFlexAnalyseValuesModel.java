package com.sino.ams.system.dict.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.dict.dto.EtsFlexValuesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.util.StrUtil;


/**
 * <p>Title: EtsFlexAnalyseValuesModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsFlexAnalyseValuesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author kouzh
 * @version 1.0
 */


public class EtsFlexAnalyseValuesModel extends AMSSQLProducer {

/**
	 * 功能：字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsFlexValuesDTO 本次操作的数据
	 */
	public EtsFlexAnalyseValuesModel(SfUserDTO userAccount, EtsFlexValuesDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " ETS_FLEX_ANALYSE_VALUES("
						+ " FLEX_VALUE_ID,"
						+ " FLEX_VALUE_SET_ID,"
						+ " CODE,"
						+ " VALUE,"
						+ " DESCRIPTION,"
						+ " ENABLED,"
						+ " ATTRIBUTE1,"
						+ " ATTRIBUTE2,"
						+ " IS_INNER,"
						+ " CREATION_DATE,"
						+ " CREATED_BY," 
						+ " FILE_VERSION,"
						+ " ATTRIBUTE3,"
						+ " ATTRIBUTE4,"
						+ " ATTRIBUTE5,"
						+ " ATTRIBUTE6"
						+ ") VALUES ("
						+ " MEWID() , ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?,?,?,?,?,?)";

		sqlArgs.add(dto.getFlexValueSetId());
		sqlArgs.add(dto.getCode());
		sqlArgs.add(dto.getValue());
		sqlArgs.add(dto.getDescription());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getAttribute1());
		sqlArgs.add(dto.getAttribute2());
		sqlArgs.add(dto.getIsInner());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getFileVersion());
		sqlArgs.add(dto.getAttribute3());
		sqlArgs.add(dto.getAttribute4());
		sqlArgs.add(dto.getAttribute5());
		sqlArgs.add(dto.getAttribute6());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr = "UPDATE ETS_FLEX_ANALYSE_VALUES"
						+ " SET"
						+ " FLEX_VALUE_SET_ID = ?,"
						+ " CODE = ?,"
						+ " VALUE = ?,"
						+ " DESCRIPTION = ?,"
						+ " ENABLED = ?,"
						+ " ATTRIBUTE1 = ?,"
						+ " ATTRIBUTE2 = ?,"
						+ " IS_INNER = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?,"
						+ " FILE_VERSION = ?,"
						+ " ATTRIBUTE3 = ?,"
						+ " ATTRIBUTE4 = ?,"
						+ " ATTRIBUTE5 = ?,"
						+ " ATTRIBUTE6 = ?"
						+ " WHERE"
						+ " FLEX_VALUE_ID = ?";

		sqlArgs.add(dto.getFlexValueSetId());
		sqlArgs.add(dto.getCode());
		sqlArgs.add(dto.getValue());
		sqlArgs.add(dto.getDescription());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getAttribute1());
		sqlArgs.add(dto.getAttribute2());
		sqlArgs.add(dto.getIsInner());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getFileVersion());
		sqlArgs.add(dto.getAttribute3());
		sqlArgs.add(dto.getAttribute4());
		sqlArgs.add(dto.getAttribute5());
		sqlArgs.add(dto.getAttribute6());
		sqlArgs.add(dto.getFlexValueId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " ETS_FLEX_ANALYSE_VALUES"
						+ " WHERE"
						+ " FLEX_VALUE_ID = ?";
		sqlArgs.add(dto.getFlexValueId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " EFV.FLEX_VALUE_ID,"
						+ " EFV.FLEX_VALUE_SET_ID,"
						+ " EFVS.NAME FLEX_VALUE_SET_NAME,"
						+ " EFV.CODE,"
						+ " EFV.VALUE,"
						+ " EFV.DESCRIPTION,"
						+ " EFV.ENABLED,"
						+ " EFV.ATTRIBUTE1,"
						+ " EFV.ATTRIBUTE2,"
						+ " EFV.IS_INNER,"
						+ " EFV.CREATION_DATE,"
						+ " EFV.CREATED_BY,"
						+ " EFV.LAST_UPDATE_DATE,"
						+ " EFV.LAST_UPDATE_BY,"
						+ " EFV.FILE_VERSION,"
						+ " EFV.ATTRIBUTE3,"
						+ " EFV.ATTRIBUTE4,"
						+ " EFV.ATTRIBUTE5,"
						+ " EFV.ATTRIBUTE6"
						+ " FROM"
						+ " ETS_FLEX_ANALYSE_VALUES EFV,"
						+ " ETS_FLEX_ANALYSE_VALUE_SET EFVS"
						+ " WHERE"
						+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFV.FLEX_VALUE_ID = ? AND EFV.FILE_VERSION =(SELECT DISTINCT (T.FILE_VERSION) FROM "
				+ " AMS_FILE_VERSION T WHERE T.START_DATE <= current_date AND current_date <= T.END_DATE)";
		sqlArgs.add(dto.getFlexValueId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 flexValueSetId 构造查询数据SQL。
	 * 框架自动生成数据字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES详细信息查询SQLModel，请根据实际需要修改。
	 * @param flexValueSetId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByFlexValueSetIdModel(String flexValueSetId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " FLEX_VALUE_ID,"
						+ " CODE,"
						+ " VALUE,"
						+ " DESCRIPTION,"
						+ " ENABLED,"
						+ " ATTRIBUTE1,"
						+ " ATTRIBUTE2,"
						+ " IS_INNER,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " LAST_UPDATE_DATE,"
						+ " LAST_UPDATE_BY,"
						+ " FILE_VERSION,"
						+ " ATTRIBUTE3,"
						+ " ATTRIBUTE4,"
						+ " ATTRIBUTE5,"
						+ " ATTRIBUTE6"
						+ " FROM"
						+ " ETS_FLEX_ANALYSE_VALUES EFV"
						+ " WHERE"
						+ " EFV.FLEX_VALUE_SET_ID = ?  AND EFV.FILE_VERSION =(SELECT DISTINCT (T.FILE_VERSION) FROM "
				+ " AMS_FILE_VERSION T WHERE T.START_DATE <= current_date AND current_date <= T.END_DATE)";
		sqlArgs.add(flexValueSetId);

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
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		if (foreignKey.equals("flexValueSetId")) {
			sqlModel = getDataByFlexValueSetIdModel(StrUtil.nullToString(dto.getFlexValueSetId()));
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 flexValueSetId 构造数据删除SQL。
	 * 框架自动生成数据字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES 数据删除SQLModel，请根据实际需要修改。
	 * @param flexValueSetId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDeleteByFlexValueSetIdModel(String flexValueSetId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " ETS_FLEX_ANALYSE_VALUES"
						+ " WHERE"
						+ " FLEX_VALUE_SET_ID = ?";
		sqlArgs.add(flexValueSetId);

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
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		if (foreignKey.equals("flexValueSetId")) {
			sqlModel = getDeleteByFlexValueSetIdModel(StrUtil.nullToString(dto.getFlexValueSetId()));
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUES页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr = "SELECT "
						+ " EFV.FLEX_VALUE_ID,"
						+ " EFV.FLEX_VALUE_SET_ID,"
						+ " EFV.CODE,"
						+ " EFV.VALUE,"
						+ " EFV.DESCRIPTION,"
						+ " CASE WHEN ENABLED='Y' THEN '是' ELSE '否' END ENABLED,"
						+ " EFVS.CODE DICT_TYPE_CODE,"
						+ " EFVS.NAME DICT_TYPE_NAME,"
						+ " CASE WHEN IS_INNER='Y' THEN '是' ELSE '否' END IS_INNER ,"
						+ " CASE WHEN MAINTAIN_FLAG='Y' THEN '是' ELSE '否' END MAINTAIN_FLAG,"
						+ " EFV.FILE_VERSION,"
						+ " EFV.ATTRIBUTE1,"
						+ " EFV.ATTRIBUTE2,"
						+ " EFV.ATTRIBUTE3,"
						+ " EFV.ATTRIBUTE4,"
						+ " EFV.ATTRIBUTE5,"
						+ " EFV.ATTRIBUTE6"
						+ " FROM"
						+ " ETS_FLEX_ANALYSE_VALUES EFV,"
						+ " ETS_FLEX_ANALYSE_VALUE_SET EFVS"
						+ " WHERE"
						+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFV.FLEX_VALUE_SET_ID = dbo.NVL(?, EFV.FLEX_VALUE_SET_ID)"
						+ " AND EFV.CODE LIKE dbo.NVL(?, EFV.CODE)"
						+ " AND EFV.VALUE LIKE dbo.NVL(?, EFV.VALUE)"
						+ " AND EFV.ENABLED = dbo.NVL(?, EFV.ENABLED)"
						+ " AND EFV.IS_INNER = dbo.NVL(?, EFV.IS_INNER)"
		                + " AND EFV.FLEX_VALUE_ID = dbo.NVL(?, EFV.FLEX_VALUE_ID)  AND EFV.FILE_VERSION =(SELECT TOP 1 (T.FILE_VERSION) FROM "
				+ " AMS_FILE_VERSION T WHERE T.START_DATE <= current_date AND current_date <= T.END_DATE)";
		sqlArgs.add(dto.getFlexValueSetId());
		sqlArgs.add(dto.getCode());
		sqlArgs.add(dto.getValue());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getIsInner());
		sqlArgs.add(dto.getFlexValueId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
