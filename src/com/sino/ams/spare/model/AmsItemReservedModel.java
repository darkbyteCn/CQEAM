package com.sino.ams.spare.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.AmsItemReservedDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemReservedModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemReservedModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Herui
 * @version 1.0
 */


public class AmsItemReservedModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：备件业务保留表(AMS) AMS_ITEM_RESERVED 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemReservedDTO 本次操作的数据
	 */
	public AmsItemReservedModel(SfUserDTO userAccount, AmsItemReservedDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成备件业务保留表(AMS) AMS_ITEM_RESERVED数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
			String sqlStr = "INSERT INTO "
				+ " AMS_ITEM_RESERVED("
				+ " TRANS_ID,"
				+ " RESERVED_DATE,"
				+ " ITEM_CODE,"
				+ " RESERVED_COUNT"
				+ ") VALUES ("
				+ " AMS_ITEM_RESERVED_S.NEXTVAL, ?, ?, ?)";
		
			sqlArgs.add(amsItemReserved.getReservedDate());
			sqlArgs.add(amsItemReserved.getItemCode());
			sqlArgs.add(amsItemReserved.getReservedCount());
			
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务保留表(AMS) AMS_ITEM_RESERVED数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
			String sqlStr = "UPDATE AMS_ITEM_RESERVED"
				+ " SET"
				+ " RESERVED_DATE = ?,"
				+ " ITEM_CODE = ?,"
				+ " RESERVED_COUNT = ?"
				+ " WHERE"
				+ " TRANS_ID = ?";
		
			sqlArgs.add(amsItemReserved.getReservedDate());
			sqlArgs.add(amsItemReserved.getItemCode());
			sqlArgs.add(amsItemReserved.getReservedCount());
			sqlArgs.add(amsItemReserved.getTransId());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务保留表(AMS) AMS_ITEM_RESERVED数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_ITEM_RESERVED"
				+ " WHERE"
				+ " TRANS_ID = ?";
			sqlArgs.add(amsItemReserved.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务保留表(AMS) AMS_ITEM_RESERVED数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " RESERVED_DATE,"
			+ " ITEM_CODE,"
			+ " RESERVED_COUNT"
			+ " FROM"
			+ " AMS_ITEM_RESERVED"
			+ " WHERE"
			+ " TRANS_ID = ?";
		sqlArgs.add(amsItemReserved.getTransId());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务保留表(AMS) AMS_ITEM_RESERVED多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出 */
	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " TRANS_ID,"
				+ " RESERVED_DATE,"
				+ " ITEM_CODE,"
				+ " RESERVED_COUNT"
				+ " FROM"
				+ " AMS_ITEM_RESERVED"
				+ " WHERE"
				+ " (? IS NULL OR TRANS_ID LIKE ?)"
				+ " AND (? IS NULL OR RESERVED_DATE LIKE ?)"
				+ " AND (? IS NULL OR ITEM_CODE LIKE ?)"
				+ " AND (? IS NULL OR RESERVED_COUNT LIKE ?)";
			sqlArgs.add(amsItemReserved.getTransId());
			sqlArgs.add(amsItemReserved.getTransId());
			sqlArgs.add(amsItemReserved.getReservedDate());
			sqlArgs.add(amsItemReserved.getReservedDate());
			sqlArgs.add(amsItemReserved.getItemCode());
			sqlArgs.add(amsItemReserved.getItemCode());
			sqlArgs.add(amsItemReserved.getReservedCount());
			sqlArgs.add(amsItemReserved.getReservedCount());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。
	 * 框架自动生成数据备件业务保留表(AMS) AMS_ITEM_RESERVED详细信息查询SQLModel，请根据实际需要修改。
	 * @param transId String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " RESERVED_DATE,"
			+ " ITEM_CODE,"
			+ " RESERVED_COUNT"
			+ " FROM"
			+ " AMS_ITEM_RESERVED"
			+ " WHERE"
			+ " TRANS_ID = ?";
		sqlArgs.add(transId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 itemCode 构造查询数据SQL。
	 * 框架自动生成数据备件业务保留表(AMS) AMS_ITEM_RESERVED详细信息查询SQLModel，请根据实际需要修改。
	 * @param itemCode String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByItemCodeModel(String itemCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " RESERVED_DATE,"
			+ " RESERVED_COUNT"
			+ " FROM"
			+ " AMS_ITEM_RESERVED"
			+ " WHERE"
			+ " ITEM_CODE = ?";
		sqlArgs.add(itemCode);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
		if(foreignKey.equals("transId")){
			sqlModel = getDataByTransIdModel(amsItemReserved.getTransId());
		} else if(foreignKey.equals("itemCode")){
			sqlModel = getDataByItemCodeModel(amsItemReserved.getItemCode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造数据删除SQL。
	 * 框架自动生成数据备件业务保留表(AMS) AMS_ITEM_RESERVED数据删除SQLModel，请根据实际需要修改。
	 * @param transId String 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " RESERVED_DATE,"
			+ " ITEM_CODE,"
			+ " RESERVED_COUNT"
			+ " FROM"
			+ " AMS_ITEM_RESERVED"
			+ " WHERE"
			+ " TRANS_ID = ?";
		sqlArgs.add(transId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 itemCode 构造数据删除SQL。
	 * 框架自动生成数据备件业务保留表(AMS) AMS_ITEM_RESERVED数据删除SQLModel，请根据实际需要修改。
	 * @param itemCode String 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByItemCodeModel(String itemCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " RESERVED_DATE,"
			+ " RESERVED_COUNT"
			+ " FROM"
			+ " AMS_ITEM_RESERVED"
			+ " WHERE"
			+ " ITEM_CODE = ?";
		sqlArgs.add(itemCode);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
		if(foreignKey.equals("transId")){
			sqlModel = getDeleteByTransIdModel(amsItemReserved.getTransId());
		} else if(foreignKey.equals("itemCode")){
			sqlModel = getDeleteByItemCodeModel(amsItemReserved.getItemCode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成备件业务保留表(AMS) AMS_ITEM_RESERVED页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsItemReservedDTO amsItemReserved = (AmsItemReservedDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " TRANS_ID,"
				+ " RESERVED_DATE,"
				+ " ITEM_CODE,"
				+ " RESERVED_COUNT"
				+ " FROM"
				+ " AMS_ITEM_RESERVED"
				+ " WHERE"
				+ " (? IS NULL OR TRANS_ID LIKE ?)"
				+ " AND (? IS NULL OR RESERVED_DATE LIKE ?)"
				+ " AND (? IS NULL OR ITEM_CODE LIKE ?)"
				+ " AND (? IS NULL OR RESERVED_COUNT LIKE ?)";
			sqlArgs.add(amsItemReserved.getTransId());
			sqlArgs.add(amsItemReserved.getTransId());
			sqlArgs.add(amsItemReserved.getReservedDate());
			sqlArgs.add(amsItemReserved.getReservedDate());
			sqlArgs.add(amsItemReserved.getItemCode());
			sqlArgs.add(amsItemReserved.getItemCode());
			sqlArgs.add(amsItemReserved.getReservedCount());
			sqlArgs.add(amsItemReserved.getReservedCount());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}