package com.sino.sinoflow.dao;

import java.sql.Connection;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.dto.SfTaskDTO;
import com.sino.sinoflow.model.SfTaskModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>
 * Title: SfTaskDAO
 * </p>
 * <p>
 * Description:程序自动生成服务程序“SfTaskDAO”，请根据需要自行修改
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author Hing
 * @version 1.0
 */

public class SfTaskDAO extends BaseDAO {

	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：任务属性 SF_TASK 数据访问层构造函数
	 * 
	 * @param userAccount
	 *            SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter
	 *            SfTaskDTO 本次操作的数据
	 * @param conn
	 *            Connection 数据库连接，由调用者传入。
	 */
	public SfTaskDAO(SfUserBaseDTO userAccount, SfTaskDTO dtoParameter,
			Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * 
	 * @param userAccount
	 *            BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter
	 *            DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		SfTaskDTO dtoPara = (SfTaskDTO) dtoParameter;
		super.sqlProducer = new SfTaskModel((SfUserBaseDTO) userAccount, dtoPara);
	}

	/**
     * 功能：从工程名称与过程名称找第一个任务
     * @return SQLModel 返回页面翻页查询SQLModel
     */
	public Row getFirstTask(String projName,String procName) {
		SQLModel sqlModel = new SfTaskModel(null,null).
							getFirstTaskModel(projName,procName);
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		Row row = new Row();
		try {
			sq.executeQuery();
			if (sq.hasResult()) {
				 RowSet rowSet = sq.getSearchResult();
				 row = rowSet.getRow(0);
			}
		} catch (QueryException e) {
			e.printLog();
		}
		return row;
	}

}