package com.sino.nm.spare2.dao;


import java.sql.Connection;

import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.nm.spare2.dto.AmsItemAllocateDDTO;
import com.sino.nm.spare2.model.AmsItemAllocateDModel;
import com.sino.nm.spare2.model.AmsItemTransLModel;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemAllocateDDAO</p>
 * <p>Description:程序自动生成服务程序“AmsItemAllocateDDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Herui
 * @version 1.0
 */


public class AmsItemAllocateDDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：AMS_ITEM_ALLOCATE_D 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemAllocateDDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsItemAllocateDDAO(SfUserDTO userAccount, AmsItemAllocateDDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		AmsItemAllocateDDTO dtoPara = (AmsItemAllocateDDTO)dtoParameter;
		super.sqlProducer = new AmsItemAllocateDModel((SfUserDTO)userAccount, dtoPara);
	}


    public RowSet getDetails(String transId) throws QueryException {
        AmsItemTransLModel model = (AmsItemTransLModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(transId),conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }
}