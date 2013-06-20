package com.sino.ams.print.dao;


import java.sql.Connection;

import com.sino.ams.print.dto.EtsBarcodePrintHistoryDTO;
import com.sino.ams.print.model.BarcodePrintModel;
import com.sino.ams.print.model.EtsBarcodePrintHistoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsBarcodePrintDAO</p>
 * <p>Description:程序自动生成服务程序“AmsBarcodePrintDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsBarcodePrintHistoryDAO extends BaseDAO {
	private EtsBarcodePrintHistoryModel historyModel = null;
	private SfUserDTO sfUser = null;
	EtsBarcodePrintHistoryDTO etsBarcodePrintHistoryDTO = null;
	/**
	 * 功能：条码打印信息表 AMS_BARCODE_PRINT 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsBarcodePrintDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public EtsBarcodePrintHistoryDAO(SfUserDTO userAccount, EtsBarcodePrintHistoryDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		this.etsBarcodePrintHistoryDTO = dtoParameter;
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) { 
		EtsBarcodePrintHistoryDTO dtoPara = (EtsBarcodePrintHistoryDTO)dtoParameter;
		this.historyModel = new EtsBarcodePrintHistoryModel((SfUserDTO)userAccount, dtoPara );
		super.sqlProducer = historyModel ;
	} 
	
	public int getBarcodePrintCount( int orgId , String barcode ) throws QueryException, ContainerException{
		SQLModel sqlModel = historyModel.getBarcodePrintCount( orgId , barcode );
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        int count = (Integer) simpleQuery.getFirstRow().getValue( 0 );
        return count;
	}
	
}