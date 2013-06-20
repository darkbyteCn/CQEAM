package com.sino.ams.system.dict.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.dict.dto.EtsFlexValuesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: EtsFlexValuesModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsFlexValuesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EtsFlexValuesModel extends AMSSQLProducer {

/**
	 * 功能：字典分类表(AMS) ETS_FLEX_VALUES 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsFlexValuesDTO 本次操作的数据
	 */
	public EtsFlexValuesModel(SfUserDTO userAccount, EtsFlexValuesDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_VALUES数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr =
						"INSERT INTO "
						+ " ETS_FLEX_VALUES("
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
						+ " CREATED_BY"
						+ ") VALUES ( NEWID()  , ?, ?, ?, ?, ?, ?, ?, ?,  " +  SyBaseSQLUtil.getCurDate() + "  , ?)";

		sqlArgs.add(dto.getFlexValueSetId());
		sqlArgs.add(dto.getCode());
		sqlArgs.add(dto.getValue());
		sqlArgs.add(dto.getDescription());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getAttribute1());
		sqlArgs.add(dto.getAttribute2());
		sqlArgs.add(dto.getIsInner());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_VALUES数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr = "UPDATE ETS_FLEX_VALUES"
						+ " SET"
						+ " FLEX_VALUE_SET_ID = ?,"
						+ " CODE = ?,"
						+ " VALUE = ?,"
						+ " DESCRIPTION = ?,"
						+ " ENABLED = ?,"
						+ " ATTRIBUTE1 = ?,"
						+ " ATTRIBUTE2 = ?,"
						+ " IS_INNER = ?,"
						+ " LAST_UPDATE_DATE = " +  SyBaseSQLUtil.getCurDate() + ","
						+ " LAST_UPDATE_BY = ?"
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
		sqlArgs.add(dto.getFlexValueId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_VALUES数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValuesDTO dto = (EtsFlexValuesDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " ETS_FLEX_VALUES"
						+ " WHERE"
						+ " FLEX_VALUE_ID = ?";
		sqlArgs.add(dto.getFlexValueId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_VALUES数据详细信息查询SQLModel，请根据实际需要修改。
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
						+ " EFV.LAST_UPDATE_BY"
						+ " FROM"
						+ " ETS_FLEX_VALUES EFV,"
						+ " ETS_FLEX_VALUE_SET EFVS"
						+ " WHERE"
						+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFV.FLEX_VALUE_ID = ?";
		sqlArgs.add(dto.getFlexValueId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 flexValueSetId 构造查询数据SQL。
	 * 框架自动生成数据字典分类表(AMS) ETS_FLEX_VALUES详细信息查询SQLModel，请根据实际需要修改。
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
						+ " LAST_UPDATE_BY"
						+ " FROM"
						+ " ETS_FLEX_VALUES"
						+ " WHERE"
						+ " FLEX_VALUE_SET_ID = ?";
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
			sqlModel = getDataByFlexValueSetIdModel(dto.getFlexValueSetId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 flexValueSetId 构造数据删除SQL。
	 * 框架自动生成数据字典分类表(AMS) ETS_FLEX_VALUES 数据删除SQLModel，请根据实际需要修改。
	 * @param flexValueSetId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDeleteByFlexValueSetIdModel(String flexValueSetId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " ETS_FLEX_VALUES"
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
			sqlModel = getDeleteByFlexValueSetIdModel(dto.getFlexValueSetId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_VALUES页面翻页查询SQLModel，请根据实际需要修改。
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
						+ " CASE EFV.ENABLED WHEN 'Y' THEN '是' ELSE '否' END ENABLED,"
						+ " EFVS.CODE DICT_TYPE_CODE,"
						+ " EFVS.NAME DICT_TYPE_NAME,"
						+ " CASE EFVS.IS_INNER WHEN 'Y' THEN '是' ELSE '否' END IS_INNER,"
						+ " CASE EFVS.MAINTAIN_FLAG WHEN 'Y' THEN '是' ELSE '否' END MAINTAIN_FLAG"
						+ " FROM"
						+ " ETS_FLEX_VALUES EFV,"
						+ " ETS_FLEX_VALUE_SET EFVS"
						+ " WHERE"
						+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFV.FLEX_VALUE_SET_ID = dbo.NVL(?, EFV.FLEX_VALUE_SET_ID)"
						+ " AND (? = '' OR EFV.FLEX_VALUE_ID = ?)"
						+ " AND (? = '' OR EFV.CODE LIKE ?)"
						+ " AND (? = '' OR EFV.VALUE LIKE ?)"
						+ " AND EFV.ENABLED = dbo.NVL(?, EFV.ENABLED)"
						+ " AND EFV.IS_INNER = dbo.NVL(?, EFV.IS_INNER)";
		sqlArgs.add(dto.getFlexValueSetId());
		sqlArgs.add(dto.getFlexValueId());
		sqlArgs.add(dto.getFlexValueId());
		sqlArgs.add(dto.getCode());
		sqlArgs.add(dto.getCode());
		sqlArgs.add(dto.getValue());
		sqlArgs.add(dto.getValue());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getIsInner());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
