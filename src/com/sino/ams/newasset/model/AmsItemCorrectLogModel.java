package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsItemCorrectLogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.SeqProducer;


/**
 * <p>Title: AmsItemCorrectLogModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemCorrectLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsItemCorrectLogModel extends AMSSQLProducer {

	/**
	 * 功能：资产台账维护日志 AMS_ITEM_CORRECT_LOG 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsItemCorrectLogDTO 本次操作的数据
	 */
	public AmsItemCorrectLogModel(SfUserDTO userAccount, AmsItemCorrectLogDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产台账维护日志 AMS_ITEM_CORRECT_LOG数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsItemCorrectLogDTO dto = (AmsItemCorrectLogDTO) dtoParameter;
//		String guid = SyBaseSQLUtil.generateGUID();
		String sqlStr = "INSERT INTO "
						+ " AMS_ITEM_CORRECT_LOG("
						+ " LOG_ID,"
						+ " BARCODE,"
						+ " CORRECT_CONTENT,"
						+ " CREATED_BY,"
						+ " CREATION_DATE"
						+ ") VALUES ("
						//+ "  " + SyBaseSQLUtil.getNewID( "AMS_ITEM_CORRECT_LOG_S" ) + " , ?, ?, ?, GETDATE())";
						+ "NEWID() , ?, ?, ?, GETDATE())";
		//sqlArgs.add(dto.getLogId());	//ACT暂放入LOG ID			
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getCorrectContent());
		sqlArgs.add(userAccount.getUserId());
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
		AmsItemCorrectLogDTO amsItemCorrectLog = (AmsItemCorrectLogDTO)dtoParameter;
		if(foreignKey.equals("barcode")){
			sqlModel = getDataByBarcodeModel(amsItemCorrectLog.getBarcode());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造查询数据SQL。
	 * 框架自动生成数据资产台账维护日志 AMS_ITEM_CORRECT_LOG详细信息查询SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " AICL.LOG_ID,"
						+ " SU.USERNAME CREATED_USER,"
						+ " AICL.CREATION_DATE,"
						+ " AICL.CORRECT_CONTENT"
						+ " FROM"
						+ " AMS_ITEM_CORRECT_LOG AICL,"
		                + " SF_USER              SU"
						+ " WHERE"
		                + " AICL.CREATED_BY = SU.USER_ID"
						+ " AND AICL.BARCODE = ?";
		sqlArgs.add(barcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
