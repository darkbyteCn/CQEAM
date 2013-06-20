package com.sino.ams.system.dict.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class DictionaryAnalyseTreeModel {
	public DictionaryAnalyseTreeModel() {
		super();
	}


	/**
	 * 功能：获取所有字典分类
	 * @return SQLModel
	 */
	public SQLModel getAllDictSetModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " ETS_FLEX_ANALYSE_VALUE_SET EFVS"
						+ " ORDER BY"
						+ " EFVS.FLEX_VALUE_SET_ID";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：根据字典分类获取相应字典
	 * @param flexValueSetId 字典分类编号
	 * @return SQLModel
	 */
	public SQLModel getDictionaryBySetIdModel(String flexValueSetId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " ETS_FLEX_ANALYSE_VALUES EFV"
						+ " WHERE"
						+ " EFV.FLEX_VALUE_SET_ID = ?"
						+ " ORDER BY"
						+ " EFV.FLEX_VALUE_ID";
		sqlArgs.add(flexValueSetId);
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
