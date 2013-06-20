package com.sino.ams.spare.version.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

import com.sino.ams.spare.version.dto.AmsItemVersionDTO;
import com.sino.ams.spare.version.model.AmsItemVersionModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: AmsItemVersionDAO</p>
 * <p>Description:程序自动生成服务程序“AmsItemVersionDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsItemVersionDAO extends BaseDAO {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：备件版本变动表 AMS_ITEM_VERSION 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemVersionDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsItemVersionDAO(SfUserDTO userAccount, AmsItemVersionDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsItemVersionDTO dtoPara = (AmsItemVersionDTO)dtoParameter;
		super.sqlProducer = new AmsItemVersionModel((SfUserDTO)userAccount, dtoPara);
	}

	/**
	 * 功能：插入备件版本变动表表“AMS_ITEM_VERSION”数据。
	 * @throws DataHandleException
	 */
	public void createData() throws DataHandleException {
		boolean hasError = true;
		boolean autoCommit = false;
		try {
			autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			super.createData();
			if (!hasSystemItem()) {
				createSystemItem();
			}
			conn.commit();
			hasError = false;
		} catch (QueryException ex) {
			ex.printLog();
			prodMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
			throw new DataHandleException(ex);
		} catch (SQLException ex) {
			Logger.logError(ex);
			throw new DataHandleException(ex);
		} finally{
			try {
				if (hasError) {
					conn.rollback();
				}
				conn.setAutoCommit(autoCommit);
			} catch (SQLException ex1) {
				Logger.logError(ex1);
				throw new DataHandleException(ex1);
			}
		}
		getMessage().addParameterValue("备件版本变动表");
	}

	/**
	 * 功能：更新备件版本变动表表“AMS_ITEM_VERSION”数据。
	 * @throws DataHandleException
	 */
	public void updateData() throws DataHandleException {
		super.updateData();
		getMessage().addParameterValue("备件版本变动表");
	}

	private boolean hasSystemItem() throws QueryException {
		boolean hasItem = false;
		AmsItemVersionModel modelProducer = (AmsItemVersionModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getHasItemModel();
		SimpleQuery sq = new SimpleQuery(sqlModel, conn);
		sq.executeQuery();
		hasItem = sq.hasResult();
		return hasItem;
	}

	/**
	 * 功能：产生新的设备分类
	 * @throws DataHandleException
	 */
	private void createSystemItem() throws DataHandleException {
		AmsItemVersionModel modelProducer = (AmsItemVersionModel) sqlProducer;
		SQLModel sqlModel = modelProducer.getCreateSysItemModel();
		DBOperator.updateRecord(sqlModel, conn);
	}
}
