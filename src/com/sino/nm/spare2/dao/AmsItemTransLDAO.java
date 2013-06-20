package com.sino.nm.spare2.dao;


import java.sql.Connection;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.nm.spare2.model.AmsItemTransLModel;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemTransLDAO</p>
 * <p>Description:程序自动生成服务程序“AmsItemTransLDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsItemTransLDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：备件事务行表(AMS) AMS_ITEM_TRANS_L 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemTransLDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsItemTransLDAO(SfUserDTO userAccount, AmsItemTransLDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsItemTransLDTO dtoPara = (AmsItemTransLDTO)dtoParameter;
		super.sqlProducer = new AmsItemTransLModel((SfUserDTO)userAccount, dtoPara);
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
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
     public RowSet getLines1(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel1(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
     public RowSet getDeptLines(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDeptDataByTransIdModel(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}