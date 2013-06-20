package com.sino.ams.newasset.bean;

import java.sql.Connection;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.DataHandleException;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class CostCenterProcssor {
	private CostCenterProcssor() {
		super();
	}

	/**
	 * 功能：插入成本中心临时表数据，主要用于解决查询统计速度慢。
	 * @param conn Connection
	 * @throws DataHandleException
	 */
	public static void insertTmpCostCenter(Connection conn) throws DataHandleException {
		DBOperator.updateRecord(getInsertTmpCostModel(), conn);
	}

	/**
	 * 功能：获取插入成本中心临时表数据的SQL
	 * @return SQLModel
	 */
	private static SQLModel getInsertTmpCostModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "INSERT INTO"
						+ " AMS_COST_CENTER_TMP"
						+ " SELECT"
						+ " *"
						+ " FROM"
						+ " AMS_COST_CENTER_V ACCV"
						+ " WHERE"
						+ " NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_COST_CENTER_TMP ACCT"
						+ " WHERE"
						+ " ACCV.COST_CENTER_CODE = ACCT.COST_CENTER_CODE)";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}
}
