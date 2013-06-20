package com.sino.ams.workorder.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsOrderFilesDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsOrderFilesModel</p>
 * <p>Description:程序自动生成SQL构造器“EtsOrderFilesModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsOrderFilesModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：工单文件处理(EAM) ETS_ORDER_FILES 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsOrderFilesDTO 本次操作的数据
	 */
	public EtsOrderFilesModel(SfUserDTO userAccount, EtsOrderFilesDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成工单文件处理(EAM) ETS_ORDER_FILES数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EtsOrderFilesDTO etsOrderFiles = (EtsOrderFilesDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " ETS_ORDER_FILES("
				+ " SYSTEMID,"
				+ " WORKORDER_BATCH,"
				+ " TITEL,"
				+ " FILE_NAME,"
				+ " FILE_PATH,"
				+ " FILE_TYPE,"
				+ " IS_TRUEFILE,"
				+ " REMARK,"
				+ " HANDLER,"
				+ " RECORD_DATE"
				+ ") VALUES ("
				+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			sqlArgs.add(etsOrderFiles.getWorkorderBatch());
			sqlArgs.add(etsOrderFiles.getTitel());
			sqlArgs.add(etsOrderFiles.getFileName());
			sqlArgs.add(etsOrderFiles.getFilePath());
			sqlArgs.add(etsOrderFiles.getFileType());
			sqlArgs.add(etsOrderFiles.getIsTruefile());
			sqlArgs.add(etsOrderFiles.getRemark());
			sqlArgs.add(etsOrderFiles.getHandler());
			sqlArgs.add(etsOrderFiles.getRecordDate());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工单文件处理(EAM) ETS_ORDER_FILES数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EtsOrderFilesDTO etsOrderFiles = (EtsOrderFilesDTO)dtoParameter;
			String sqlStr = "UPDATE ETS_ORDER_FILES"
				+ " SET"
				+ " WORKORDER_BATCH = ?,"
				+ " TITEL = ?,"
				+ " FILE_NAME = ?,"
				+ " FILE_PATH = ?,"
				+ " FILE_TYPE = ?,"
				+ " IS_TRUEFILE = ?,"
				+ " REMARK = ?,"
				+ " HANDLER = ?,"
				+ " RECORD_DATE = ?"
				+ " WHERE"
				+ " SYSTEMID = ?";
		
			sqlArgs.add(etsOrderFiles.getWorkorderBatch());
			sqlArgs.add(etsOrderFiles.getTitel());
			sqlArgs.add(etsOrderFiles.getFileName());
			sqlArgs.add(etsOrderFiles.getFilePath());
			sqlArgs.add(etsOrderFiles.getFileType());
			sqlArgs.add(etsOrderFiles.getIsTruefile());
			sqlArgs.add(etsOrderFiles.getRemark());
			sqlArgs.add(etsOrderFiles.getHandler());
			sqlArgs.add(etsOrderFiles.getRecordDate());
			sqlArgs.add(etsOrderFiles.getSystemid());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工单文件处理(EAM) ETS_ORDER_FILES数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsOrderFilesDTO etsOrderFiles = (EtsOrderFilesDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " ETS_ORDER_FILES"
				+ " WHERE"
				+ " SYSTEMID = ?";
			sqlArgs.add(etsOrderFiles.getSystemid());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工单文件处理(EAM) ETS_ORDER_FILES数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EtsOrderFilesDTO etsOrderFiles = (EtsOrderFilesDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " SYSTEMID,"
			+ " WORKORDER_BATCH,"
			+ " TITEL,"
			+ " FILE_NAME,"
			+ " FILE_PATH,"
			+ " FILE_TYPE,"
			+ " IS_TRUEFILE,"
			+ " REMARK,"
			+ " HANDLER,"
			+ " RECORD_DATE"
			+ " FROM"
			+ " ETS_ORDER_FILES"
			+ " WHERE"
			+ " SYSTEMID = ?";
		sqlArgs.add(etsOrderFiles.getSystemid());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工单文件处理(EAM) ETS_ORDER_FILES多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EtsOrderFilesDTO etsOrderFiles = (EtsOrderFilesDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " SYSTEMID,"
				+ " WORKORDER_BATCH,"
				+ " TITEL,"
				+ " FILE_NAME,"
				+ " FILE_PATH,"
				+ " FILE_TYPE,"
				+ " IS_TRUEFILE,"
				+ " REMARK,"
				+ " HANDLER,"
				+ " RECORD_DATE"
				+ " FROM"
				+ " ETS_ORDER_FILES"
				+ " WHERE"
				+ " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR TITEL LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_NAME LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_PATH LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_TYPE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR IS_TRUEFILE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR HANDLER LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR RECORD_DATE LIKE ?)";
			sqlArgs.add(etsOrderFiles.getSystemid());
			sqlArgs.add(etsOrderFiles.getSystemid());
			sqlArgs.add(etsOrderFiles.getWorkorderBatch());
			sqlArgs.add(etsOrderFiles.getWorkorderBatch());
			sqlArgs.add(etsOrderFiles.getTitel());
			sqlArgs.add(etsOrderFiles.getTitel());
			sqlArgs.add(etsOrderFiles.getFileName());
			sqlArgs.add(etsOrderFiles.getFileName());
			sqlArgs.add(etsOrderFiles.getFilePath());
			sqlArgs.add(etsOrderFiles.getFilePath());
			sqlArgs.add(etsOrderFiles.getFileType());
			sqlArgs.add(etsOrderFiles.getFileType());
			sqlArgs.add(etsOrderFiles.getIsTruefile());
			sqlArgs.add(etsOrderFiles.getIsTruefile());
			sqlArgs.add(etsOrderFiles.getRemark());
			sqlArgs.add(etsOrderFiles.getRemark());
			sqlArgs.add(etsOrderFiles.getHandler());
			sqlArgs.add(etsOrderFiles.getHandler());
			sqlArgs.add(etsOrderFiles.getRecordDate());
			sqlArgs.add(etsOrderFiles.getRecordDate());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成工单文件处理(EAM) ETS_ORDER_FILES页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EtsOrderFilesDTO etsOrderFiles = (EtsOrderFilesDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " SYSTEMID,"
				+ " WORKORDER_BATCH,"
				+ " TITEL,"
				+ " FILE_NAME,"
				+ " FILE_PATH,"
				+ " FILE_TYPE,"
				+ " IS_TRUEFILE,"
				+ " REMARK,"
				+ " HANDLER,"
				+ " RECORD_DATE"
				+ " FROM"
				+ " ETS_ORDER_FILES"
				+ " WHERE"
				+ " ( " + SyBaseSQLUtil.isNull() + "  OR SYSTEMID LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR WORKORDER_BATCH LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR TITEL LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_NAME LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_PATH LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR FILE_TYPE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR IS_TRUEFILE LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR REMARK LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR HANDLER LIKE ?)"
				+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR RECORD_DATE LIKE ?)";
			sqlArgs.add(etsOrderFiles.getSystemid());
			sqlArgs.add(etsOrderFiles.getSystemid());
			sqlArgs.add(etsOrderFiles.getWorkorderBatch());
			sqlArgs.add(etsOrderFiles.getWorkorderBatch());
			sqlArgs.add(etsOrderFiles.getTitel());
			sqlArgs.add(etsOrderFiles.getTitel());
			sqlArgs.add(etsOrderFiles.getFileName());
			sqlArgs.add(etsOrderFiles.getFileName());
			sqlArgs.add(etsOrderFiles.getFilePath());
			sqlArgs.add(etsOrderFiles.getFilePath());
			sqlArgs.add(etsOrderFiles.getFileType());
			sqlArgs.add(etsOrderFiles.getFileType());
			sqlArgs.add(etsOrderFiles.getIsTruefile());
			sqlArgs.add(etsOrderFiles.getIsTruefile());
			sqlArgs.add(etsOrderFiles.getRemark());
			sqlArgs.add(etsOrderFiles.getRemark());
			sqlArgs.add(etsOrderFiles.getHandler());
			sqlArgs.add(etsOrderFiles.getHandler());
			sqlArgs.add(etsOrderFiles.getRecordDate());
			sqlArgs.add(etsOrderFiles.getRecordDate());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}