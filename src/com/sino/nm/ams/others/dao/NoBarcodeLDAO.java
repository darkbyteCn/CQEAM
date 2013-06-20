package com.sino.nm.ams.others.dao;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.nm.ams.others.model.NobarcodeLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-4
 * Time: 10:06:42
 * To change this template use File | Settings | File Templates.
 */

public class NoBarcodeLDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：备件事务行表(AMS) AMS_ITEM_TRANS_L 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemTransLDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public NoBarcodeLDAO(SfUserDTO userAccount, AmsItemTransLDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
		AmsItemTransLDTO dtoPara = (AmsItemTransLDTO)dtoParameter;
		super.sqlProducer = new NobarcodeLModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入备件事务行表(AMS)表“AMS_ITEM_TRANS_L”数据。
	 */
	public void createData() throws DataHandleException {
		super.createData();
		getMessage().addParameterValue("备件事务行表(AMS)");
	}

	/**
	 * 功能：更新备件事务行表(AMS)表“AMS_ITEM_TRANS_L”数据。
	 */
	public void updateData() throws DataHandleException {
		super.updateData();
		getMessage().addParameterValue("备件事务行表(AMS)");
	}

	/**
	 * 功能：删除备件事务行表(AMS)表“AMS_ITEM_TRANS_L”数据。
	 */
	public void deleteData() throws DataHandleException {
		super.deleteData();
		getMessage().addParameterValue("备件事务行表(AMS)");
	}

    public RowSet getLines(String transId) throws QueryException {
        NobarcodeLModel model = (NobarcodeLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
     public RowSet getLines1(String transId) throws QueryException {
        NobarcodeLModel model = (NobarcodeLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel1(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}