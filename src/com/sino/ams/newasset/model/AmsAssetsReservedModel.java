package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 * <p>
 * Title: AmsAssetsReservedModel
 * </p>
 * <p>
 * Description:程序自动生成SQL构造器“AmsAssetsReservedModel”，请根据需要自行修改
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsReservedModel extends AMSSQLProducer {

	/**
	 * 功能：资产事务保留表 AMS_ASSETS_RESERVED 数据库SQL构造层构造函数
	 * 
	 * @param userAccount
	 *            SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter
	 *            AmsAssetsReservedDTO 本次操作的数据
	 */
	public AmsAssetsReservedModel(SfUserDTO userAccount,
			AmsAssetsReservedDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产事务保留表 AMS_ASSETS_RESERVED数据插入SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsReservedDTO dto = (AmsAssetsReservedDTO) dtoParameter;
			String sqlStr = "INSERT INTO " + " AMS_ASSETS_RESERVED("
					+ " TRANS_ID," + " RESERVED_DATE," + " BARCODE"
					+ ") VALUES (" + " ?, ?, ?)";
			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getReservedDate());
			sqlArgs.add(dto.getBarcode());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产事务保留表 AMS_ASSETS_RESERVED数据删除SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsReservedDTO dto = (AmsAssetsReservedDTO) dtoParameter;
		String sqlStr = "DELETE FROM" + " AMS_ASSETS_RESERVED" + " WHERE"
				+ " TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产事务保留表 AMS_ASSETS_RESERVED数据详细信息查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsReservedDTO dto = (AmsAssetsReservedDTO) dtoParameter;
		String sqlStr = "SELECT " + " TRANS_ID," + " RESERVED_DATE,"
				+ " BARCODE" + " FROM" + " AMS_ASSETS_RESERVED" + " WHERE"
				+ " TRANS_ID = ?";
		sqlArgs.add(dto.getTransId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。 框架自动生成数据资产事务保留表 AMS_ASSETS_RESERVED详细信息查询SQLModel，请根据实际需要修改。
	 * 
	 * @param transId
	 *            String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT " + " RESERVED_DATE," + " BARCODE" + " FROM"
				+ " AMS_ASSETS_RESERVED" + " WHERE" + " TRANS_ID = ?";
		sqlArgs.add(transId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造查询数据SQL。 框架自动生成数据资产事务保留表 AMS_ASSETS_RESERVED详细信息查询SQLModel，请根据实际需要修改。
	 * 
	 * @param barcode
	 *            String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT " + " TRANS_ID," + " RESERVED_DATE," + " FROM"
				+ " AMS_ASSETS_RESERVED" + " WHERE" + " BARCODE = ?";
		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * 
	 * @param foreignKey
	 *            传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsReservedDTO dto = (AmsAssetsReservedDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDataByTransIdModel(dto.getTransId());
		} else if (foreignKey.equals("barcode")) {
			sqlModel = getDataByBarcodeModel(dto.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造数据删除SQL。 框架自动生成数据资产事务保留表 AMS_ASSETS_RESERVED数据删除SQLModel，请根据实际需要修改。
	 * 
	 * @param transId
	 *            String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
                + " FROM"
                + " AMS_ASSETS_RESERVED"
                + " WHERE"
				+ " TRANS_ID = ?";
		sqlArgs.add(transId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造数据删除SQL。 框架自动生成数据资产事务保留表 AMS_ASSETS_RESERVED数据删除SQLModel，请根据实际需要修改。
	 * 
	 * @param barcode
	 *            String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE " + " FROM" + " AMS_ASSETS_RESERVED" + " WHERE"
				+ " BARCODE = ?";
		sqlArgs.add(barcode);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * 
	 * @param foreignKey
	 *            传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsAssetsReservedDTO dto = (AmsAssetsReservedDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDeleteByTransIdModel(dto.getTransId());
		} else if (foreignKey.equals("barcode")) {
			sqlModel = getDeleteByBarcodeModel(dto.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产事务保留表 AMS_ASSETS_RESERVED页面翻页查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsReservedDTO dto = (AmsAssetsReservedDTO) dtoParameter;
			String sqlStr = "SELECT " + " AAR.TRANS_ID,"
					+ " AAR.RESERVED_DATE," + " AAR.BARCODE" + " FROM"
					+ " AMS_ASSETS_RESERVED AAR" + " WHERE"
					+ " AAR.RESERVED_DATE >= ISNULL(?, AAR.RESERVED_DATE)"
					+ " AND AAR.RESERVED_DATE <= ISNULL(?, AAR.RESERVED_DATE)"
					+ " AND AAR.BARCODE LIKE dbo.NVL(?, AAR.BARCODE)";
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getBarcode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产事务保留表 AMS_ASSETS_RESERVED数据插入SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel(String transId, String barCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO " 
						+ " AMS_ASSETS_RESERVED(" 
						+ " TRANS_ID,"
						+ " RESERVED_DATE," 
						+ " BARCODE" 
						+ ") VALUES ("
						+ " ?, GETDATE() , ?)";
		sqlArgs.add(transId);
		sqlArgs.add(barCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产事务保留表 AMS_ASSETS_RESERVED数据删除SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel( String transId ) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM" 
						+ " AMS_ASSETS_RESERVED" 
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add( transId );
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
