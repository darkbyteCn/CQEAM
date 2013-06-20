package com.sino.ams.system.dict.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.dict.dto.EtsFlexValueSetDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsFlexValueSetModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsFlexValueSetModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author kouzh
 * @version 1.0
 */


public class EtsFlexAnalyseValueSetModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：字典分类表(AMS) ETS_FLEX_VALUE_SET 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsFlexValueSetDTO 本次操作的数据
	 */
	public EtsFlexAnalyseValueSetModel(SfUserDTO userAccount, EtsFlexValueSetDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUE_SET数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " ETS_FLEX_ANALYSE_VALUE_SET("
			+ " FLEX_VALUE_SET_ID,"
			+ " CODE,"
			+ " NAME,"
			+ " DESCRIPTION,"
			+ " IS_INNER,"
			+ " MAINTAIN_FLAG,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ ") VALUES ("
			+ " NEWID()  , ?, ?, ?, ?, ?, ?, ?, ?)";

		sqlArgs.add(etsFlexValueSet.getCode());
		sqlArgs.add(etsFlexValueSet.getName());
		sqlArgs.add(etsFlexValueSet.getDescription());
		sqlArgs.add(etsFlexValueSet.getIsInner());
		sqlArgs.add(etsFlexValueSet.getMaintainFlag());
		sqlArgs.add(etsFlexValueSet.getCreatedBy());
		sqlArgs.add(etsFlexValueSet.getLastUpdateDate());
		sqlArgs.add(etsFlexValueSet.getLastUpdateBy());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUE_SET数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)dtoParameter;
		String sqlStr = "UPDATE ETS_FLEX_ANALYSE_VALUE_SET"
			+ " SET"
			+ " CODE = ?,"
			+ " NAME = ?,"
			+ " DESCRIPTION = ?,"
			+ " IS_INNER = ?,"
			+ " MAINTAIN_FLAG = ?,"
			+ " CREATION_DATE = ?,"
			+ " CREATED_BY = ?,"
			+ " LAST_UPDATE_DATE = ?,"
			+ " LAST_UPDATE_BY = ?"
			+ " WHERE"
			+ " FLEX_VALUE_SET_ID = ?";

		sqlArgs.add(etsFlexValueSet.getCode());
		sqlArgs.add(etsFlexValueSet.getName());
		sqlArgs.add(etsFlexValueSet.getDescription());
		sqlArgs.add(etsFlexValueSet.getIsInner());
		sqlArgs.add(etsFlexValueSet.getMaintainFlag());
		sqlArgs.add(etsFlexValueSet.getCreationDate());
		sqlArgs.add(etsFlexValueSet.getCreatedBy());
		sqlArgs.add(etsFlexValueSet.getLastUpdateDate());
		sqlArgs.add(etsFlexValueSet.getLastUpdateBy());
		sqlArgs.add(etsFlexValueSet.getFlexValueSetId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUE_SET数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
			+ " ETS_FLEX_ANALYSE_VALUE_SET"
			+ " WHERE"
			+ " FLEX_VALUE_SET_ID = ?";
		sqlArgs.add(etsFlexValueSet.getFlexValueSetId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUE_SET数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " FLEX_VALUE_SET_ID,"
			+ " CODE,"
			+ " NAME,"
			+ " DESCRIPTION,"
			+ " IS_INNER,"
			+ " MAINTAIN_FLAG,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_FLEX_ANALYSE_VALUE_SET"
			+ " WHERE"
			+ " FLEX_VALUE_SET_ID = ?";
		sqlArgs.add(etsFlexValueSet.getFlexValueSetId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUE_SET多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " FLEX_VALUE_SET_ID,"
			+ " CODE,"
			+ " NAME,"
			+ " DESCRIPTION,"
			+ " IS_INNER,"
			+ " MAINTAIN_FLAG,"
			+ " CREATION_DATE,"
			+ " CREATED_BY,"
			+ " LAST_UPDATE_DATE,"
			+ " LAST_UPDATE_BY"
			+ " FROM"
			+ " ETS_FLEX_ANALYSE_VALUE_SET"
			+ " WHERE"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR FLEX_VALUE_SET_ID LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CODE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR NAME LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR DESCRIPTION LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR IS_INNER LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR MAINTAIN_FLAG LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATION_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR CREATED_BY LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_DATE LIKE ?)"
			+ "( " + SyBaseSQLUtil.isNull() + "  OR LAST_UPDATE_BY LIKE ?)";
		sqlArgs.add(etsFlexValueSet.getFlexValueSetId());
		sqlArgs.add(etsFlexValueSet.getFlexValueSetId());
		sqlArgs.add(etsFlexValueSet.getCode());
		sqlArgs.add(etsFlexValueSet.getCode());
		sqlArgs.add(etsFlexValueSet.getName());
		sqlArgs.add(etsFlexValueSet.getName());
		sqlArgs.add(etsFlexValueSet.getDescription());
		sqlArgs.add(etsFlexValueSet.getDescription());
		sqlArgs.add(etsFlexValueSet.getIsInner());
		sqlArgs.add(etsFlexValueSet.getIsInner());
		sqlArgs.add(etsFlexValueSet.getMaintainFlag());
		sqlArgs.add(etsFlexValueSet.getMaintainFlag());
		sqlArgs.add(etsFlexValueSet.getCreationDate());
		sqlArgs.add(etsFlexValueSet.getCreationDate());
		sqlArgs.add(etsFlexValueSet.getCreatedBy());
		sqlArgs.add(etsFlexValueSet.getCreatedBy());
		sqlArgs.add(etsFlexValueSet.getLastUpdateDate());
		sqlArgs.add(etsFlexValueSet.getLastUpdateDate());
		sqlArgs.add(etsFlexValueSet.getLastUpdateBy());
		sqlArgs.add(etsFlexValueSet.getLastUpdateBy());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成字典分类表(AMS) ETS_FLEX_ANALYSE_VALUE_SET页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " FLEX_VALUE_SET_ID,"
			+ " CODE,"
			+ " NAME,"
			+ " DESCRIPTION,"
			+ " CREATION_DATE,"
			+ " CASE WHEN ENABLED='Y' THEN '是' ELSE '否' END ENABLED,"
			+ " CASE WHEN IS_INNER='Y' THEN '是' ELSE '否' END IS_INNER ,"
			+ " CASE WHEN MAINTAIN_FLAG='Y' THEN '是' ELSE '否' END MAINTAIN_FLAG "
			+ " FROM"
			+ " ETS_FLEX_ANALYSE_VALUE_SET"
			+ " WHERE"
			+ " ( " + SyBaseSQLUtil.isNull() + "  OR CODE LIKE ?)"
			+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR NAME LIKE ?)"
			+ " AND IS_INNER = ?"
			+ " AND MAINTAIN_FLAG = ?"
			+ " AND ENABLED = ?";
		sqlArgs.add(etsFlexValueSet.getCode());
		sqlArgs.add(etsFlexValueSet.getCode());
		sqlArgs.add(etsFlexValueSet.getName());
		sqlArgs.add(etsFlexValueSet.getName());
		sqlArgs.add(etsFlexValueSet.getIsInner());
		sqlArgs.add(etsFlexValueSet.getMaintainFlag());
		sqlArgs.add(etsFlexValueSet.getEnabled());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
