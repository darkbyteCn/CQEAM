package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */
public class EamDhCatalogTreeModel {
	public EamDhCatalogTreeModel() {
		super();
	}


	/**
	 * 功能：获取所有低值易耗分类
	 * @return SQLModel
	 */
	public SQLModel getAllDzyhSetModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " EAM_DH_CATALOG_SET EDCS"
						+ " ORDER BY"
						+ " EDCS.CATLOG_SET_ID";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

	/**
	 * 功能：根据低值易耗分类获取相应目录
	 * @param catalogSetId 低值易耗分类编号
	 * @return SQLModel
	 */
	public SQLModel getDzyhBySetIdModel(String catlogSetId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " EAM_DH_CATALOG_VALUES EDCV"
						+ " WHERE"
						+ " EDCV.CATALOG_SET_ID = ?"
						+ " ORDER BY"
						+ " EDCV.CATALOG_VALUE_ID";
		sqlArgs.add(catlogSetId);
		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
