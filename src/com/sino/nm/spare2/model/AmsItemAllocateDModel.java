package com.sino.nm.spare2.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.dto.AmsItemAllocateDDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsItemAllocateDModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemAllocateDModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Herui
 * @version 1.0
 */


public class AmsItemAllocateDModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	/**
	 * 功能：AMS_ITEM_ALLOCATE_D 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemAllocateDDTO 本次操作的数据
	 */
	public AmsItemAllocateDModel(SfUserDTO userAccount, AmsItemAllocateDDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}
	/**
	 * 功能：框架自动生成AMS_ITEM_ALLOCATE_D数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateDDTO amsItemAllocateD = (AmsItemAllocateDDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " AMS_ITEM_ALLOCATE_D("
			+ " TRANS_ID,"
			+ " DETAIL_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " BATCH_NO,"
			+ " SOURCE_ID,"
			+ " BARCODE"
			+ ") VALUES ("
			+ " ?, AMS_ITEM_ALLOCATE_D_S.NEXTVAL, ?, ?, ?, ?, ?)";
		
		sqlArgs.add(amsItemAllocateD.getTransId());
//		sqlArgs.add(amsItemAllocateD.getDetailId());
		sqlArgs.add(amsItemAllocateD.getItemCode());
		sqlArgs.add(amsItemAllocateD.getAllocateQty());
		sqlArgs.add(amsItemAllocateD.getBatchNo());
		sqlArgs.add(amsItemAllocateD.getSourceId());
		sqlArgs.add(amsItemAllocateD.getBarcode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成AMS_ITEM_ALLOCATE_D数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateDDTO amsItemAllocateD = (AmsItemAllocateDDTO)dtoParameter;
		String sqlStr = "UPDATE AMS_ITEM_ALLOCATE_D"
			+ " SET"
			+ " TRANS_ID = ?,"
			+ " DETAIL_ID = ?,"
			+ " ITEM_CODE = ?,"
			+ " QUANTITY = ?,"
			+ " BATCH_NO = ?,"
			+ " SOURCE_ID = ?,"
			+ " BARCODE = ?"
			;
		sqlArgs.add(amsItemAllocateD.getTransId());
		sqlArgs.add(amsItemAllocateD.getDetailId());
		sqlArgs.add(amsItemAllocateD.getItemCode());
		sqlArgs.add(amsItemAllocateD.getQuantity());
		sqlArgs.add(amsItemAllocateD.getBatchNo());
		sqlArgs.add(amsItemAllocateD.getSourceId());
		sqlArgs.add(amsItemAllocateD.getBarcode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成AMS_ITEM_ALLOCATE_D数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateDDTO amsItemAllocateD = (AmsItemAllocateDDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_ITEM_ALLOCATE_D"	;
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成AMS_ITEM_ALLOCATE_D数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateDDTO amsItemAllocateD = (AmsItemAllocateDDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " DETAIL_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " BATCH_NO,"
			+ " SOURCE_ID,"
			+ " BARCODE"
			+ " WHERE"
			+ " ROWNUM = 1";
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成AMS_ITEM_ALLOCATE_D多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateDDTO amsItemAllocateD = (AmsItemAllocateDDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " DETAIL_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " BATCH_NO,"
			+ " SOURCE_ID,"
			+ " BARCODE"
			+ " FROM"
			+ " AMS_ITEM_ALLOCATE_D"
			+ " WHERE"
			+ " (? IS NULL OR TRANS_ID LIKE ?)"
			+ " AND (? IS NULL OR DETAIL_ID LIKE ?)"
			+ " AND (? IS NULL OR ITEM_CODE LIKE ?)"
			+ " AND (? IS NULL OR QUANTITY LIKE ?)"
			+ " AND (? IS NULL OR BATCH_NO LIKE ?)"
			+ " AND (? IS NULL OR SOURCE_ID LIKE ?)"
			+ " AND (? IS NULL OR BARCODE LIKE ?)";
		sqlArgs.add(amsItemAllocateD.getTransId());
		sqlArgs.add(amsItemAllocateD.getTransId());
		sqlArgs.add(amsItemAllocateD.getDetailId());
		sqlArgs.add(amsItemAllocateD.getDetailId());
		sqlArgs.add(amsItemAllocateD.getItemCode());
		sqlArgs.add(amsItemAllocateD.getItemCode());
		sqlArgs.add(amsItemAllocateD.getQuantity());
		sqlArgs.add(amsItemAllocateD.getQuantity());
		sqlArgs.add(amsItemAllocateD.getBatchNo());
		sqlArgs.add(amsItemAllocateD.getBatchNo());
		sqlArgs.add(amsItemAllocateD.getSourceId());
		sqlArgs.add(amsItemAllocateD.getSourceId());
		sqlArgs.add(amsItemAllocateD.getBarcode());
		sqlArgs.add(amsItemAllocateD.getBarcode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成AMS_ITEM_ALLOCATE_D页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemAllocateDDTO amsItemAllocateD = (AmsItemAllocateDDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " TRANS_ID,"
			+ " DETAIL_ID,"
			+ " ITEM_CODE,"
			+ " QUANTITY,"
			+ " BATCH_NO,"
			+ " SOURCE_ID,"
			+ " BARCODE"
			+ " FROM"
			+ " AMS_ITEM_ALLOCATE_D"
			+ " WHERE"
			+ " (? IS NULL OR TRANS_ID LIKE ?)"
			+ " AND (? IS NULL OR DETAIL_ID LIKE ?)"
			+ " AND (? IS NULL OR ITEM_CODE LIKE ?)"
			+ " AND (? IS NULL OR QUANTITY LIKE ?)"
			+ " AND (? IS NULL OR BATCH_NO LIKE ?)"
			+ " AND (? IS NULL OR SOURCE_ID LIKE ?)"
			+ " AND (? IS NULL OR BARCODE LIKE ?)";
		sqlArgs.add(amsItemAllocateD.getTransId());
		sqlArgs.add(amsItemAllocateD.getTransId());
		sqlArgs.add(amsItemAllocateD.getDetailId());
		sqlArgs.add(amsItemAllocateD.getDetailId());
		sqlArgs.add(amsItemAllocateD.getItemCode());
		sqlArgs.add(amsItemAllocateD.getItemCode());
		sqlArgs.add(amsItemAllocateD.getQuantity());
		sqlArgs.add(amsItemAllocateD.getQuantity());
		sqlArgs.add(amsItemAllocateD.getBatchNo());
		sqlArgs.add(amsItemAllocateD.getBatchNo());
		sqlArgs.add(amsItemAllocateD.getSourceId());
		sqlArgs.add(amsItemAllocateD.getSourceId());
		sqlArgs.add(amsItemAllocateD.getBarcode());
		sqlArgs.add(amsItemAllocateD.getBarcode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}