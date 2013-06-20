package com.sino.ams.sampling.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.sampling.dto.AmsAssetsSamplingLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsAssetsSamplingLineModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsSamplingLineModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsSamplingLineModel extends AMSSQLProducer {

	/**
	 * 功能：抽查工单行表 AMS_ASSETS_SAMPLING_LINE 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsSamplingLineDTO 本次操作的数据
	 */
	public AmsAssetsSamplingLineModel(SfUserDTO userAccount, AmsAssetsSamplingLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成抽查工单行表 AMS_ASSETS_SAMPLING_LINE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO) dtoParameter;
		String sqlStr = "INSERT INTO"
						+ " AMS_ASSETS_SAMPLING_LINE ("
						+ " HEADER_ID,"
						+ " BATCH_ID,"
						+ " TASK_ID,"
						+ " BARCODE,"
						+ " SYSTEM_STATUS,"
						+ " ITEM_CODE,"
						+ " ITEM_CATEGORY,"
						+ " ITEM_NAME,"
						+ " ITEM_SPEC,"
						+ " START_DATE,"
						+ " RESPONSIBILITY_USER,"
						+ " RESPONSIBILITY_DEPT,"
						+ " MAINTAIN_USER)("
						+ " SELECT"
						+ " AASH.HEADER_ID,"
						+ " AASH.BATCH_ID,"
						+ " AASH.TASK_ID,"
						+ " EII.BARCODE,"
						+ " 'Y',"
						+ " ESI.ITEM_CODE,"
						+ " ESI.ITEM_CATEGORY,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EII.START_DATE,"
						+ " EII.RESPONSIBILITY_USER,"
						+ " EII.RESPONSIBILITY_DEPT,"
						+ " EII.MAINTAIN_USER"
						+ " FROM"
						+ " ETS_ITEM_INFO              EII,"
						+ " ETS_SYSTEM_ITEM            ESI,"
						+ " AMS_OBJECT_ADDRESS         AOA,"
						+ " ETS_OBJECT                 EO,"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH"
						+ " WHERE"
						+ " EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND AOA.BOX_NO = '0000'"
						+ " AND AOA.NET_UNIT = '0000'"
						+ " AND EO.WORKORDER_OBJECT_NO = AASH.SAMPLING_LOCATION"
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_LINE AASL"
						+ " WHERE"
						+ " AASL.HEADER_ID = AASH.HEADER_ID"
						+ " AND AASL.BARCODE = EII.BARCODE)"
						+ " AND AASH.HEADER_ID = ?)";

		sqlArgs.add(dto.getHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 headerId 构造查询数据SQL。
	 * 框架自动生成数据抽查工单行表 AMS_ASSETS_SAMPLING_LINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param headerId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByHeaderIdModel(String headerId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AASL.HEADER_ID,"
						+ " AASL.TASK_ID,"
						+ " AASL.BARCODE,"
                        + " CASE AASL.SYSTEM_STATUS WHEN 'Y' THEN '有' ELSE '无' END SYSTEM_STATUS,"
                        + " CASE AASL.SCAN_STATUS WHEN 'Y' THEN '有' ELSE '无' END SCAN_STATUS,"
						+ " AASL.REMARK,"
						+ " AASL.ITEM_CODE,"
						+ " AASL.ITEM_CATEGORY,"
						+ " dbo.APP_GET_FLEX_VALUE(AASL.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_VALUE,"
						+ " AASL.ITEM_NAME,"
						+ " AASL.ITEM_SPEC,"
						+ " AASL.START_DATE,"
						+ " AASL.RESPONSIBILITY_USER,"
						+ " AMES.USER_NAME RESPONSIBILITY_USER_NAME,"
						+ " AASL.RESPONSIBILITY_DEPT,"
						+ " AMDS.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
						+ " AASL.MAINTAIN_USER,"
						+ " AASL.SCAN_ITEM_CODE,"
						+ " AASL.SCAN_ITEM_CATEGORY,"
						+ " dbo.APP_GET_FLEX_VALUE(AASL.SCAN_ITEM_CATEGORY, 'ITEM_TYPE') SCAN_ITEM_CATEGORY_VALUE,"
						+ " AASL.SCAN_ITEM_NAME,"
						+ " AASL.SCAN_ITEM_SPEC,"
						+ " AASL.SCAN_START_DATE,"
						+ " AASL.SCAN_RESPONSIBILITY_USER,"
						+ " AMEC.USER_NAME SCAN_RESPONSIBILITY_USER_NAME,"
						+ " AASL.SCAN_RESPONSIBILITY_DEPT,"
						+ " AMDC.DEPT_NAME SCAN_RESPONSIBILITY_DEPT_NAME,"
						+ " AASL.SCAN_MAINTAIN_USER"
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_LINE AASL,"
						+ " AMS_MIS_EMPLOYEE         AMES,"
						+ " AMS_MIS_DEPT             AMDS,"
						+ " AMS_MIS_EMPLOYEE         AMEC,"
						+ " AMS_MIS_DEPT             AMDC"
						+ " WHERE"
						+ " AASL.RESPONSIBILITY_USER *= AMES.EMPLOYEE_ID"
						+ " AND AASL.RESPONSIBILITY_DEPT *= AMDS.DEPT_CODE"
						+ " AND AASL.SCAN_RESPONSIBILITY_USER *= AMEC.EMPLOYEE_ID"
						+ " AND AASL.SCAN_RESPONSIBILITY_DEPT *= AMDC.DEPT_CODE"
						+ " AND AASL.HEADER_ID = ?";
		sqlArgs.add(headerId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 taskId 构造查询数据SQL。
	 * 框架自动生成数据抽查工单行表 AMS_ASSETS_SAMPLING_LINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param taskId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTaskIdModel(String taskId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " HEADER_ID,"
						+ " BARCODE,"
						+ " SYSTEM_STATUS,"
						+ " SCAN_STATUS,"
						+ " REMARK,"
						+ " ITEM_CODE,"
						+ " ITEM_CATEGORY,"
						+ " ITEM_NAME,"
						+ " ITEM_SPEC,"
						+ " START_DATE,"
						+ " RESPONSIBILITY_USER,"
						+ " RESPONSIBILITY_DEPT,"
						+ " MAINTAIN_USER,"
						+ " SCAN_ITEM_CODE,"
						+ " SCAN_ITEM_CATEGORY,"
						+ " SCAN_ITEM_NAME,"
						+ " SCAN_ITEM_SPEC,"
						+ " SCAN_START_DATE,"
						+ " SCAN_RESPONSIBILITY_USER,"
						+ " SCAN_RESPONSIBILITY_DEPT,"
						+ " SCAN_MAINTAIN_USER"
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_LINE"
						+ " WHERE"
						+ " TASK_ID = ?";
		sqlArgs.add(taskId);

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
		AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO) dtoParameter;
		if (foreignKey.equals("headerId")) {
			sqlModel = getDataByHeaderIdModel(dto.getHeaderId());
		} else if (foreignKey.equals("taskId")) {
			sqlModel = getDataByTaskIdModel(dto.getTaskId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 headerId 构造数据删除SQL。
	 * 框架自动生成数据抽查工单行表 AMS_ASSETS_SAMPLING_LINE数据删除SQLModel，请根据实际需要修改。
	 * @param headerId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByHeaderIdModel(String headerId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_LINE"
						+ " WHERE"
						+ " HEADER_ID = ?";
		sqlArgs.add(headerId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 batchId 构造数据删除SQL。
	 * 框架自动生成数据抽查工单行表 AMS_ASSETS_SAMPLING_LINE数据删除SQLModel，请根据实际需要修改。
	 * @param batchId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByBatchIdModel(String batchId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " AMS_ASSETS_SAMPLING_LINE"
						+ " WHERE"
						+ " EXISTS ("
						+ " SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH"
						+ " WHERE"
						+ " HEADER_ID = AASH.HEADER_ID"
						+ " AND AASH.ORDER_STATUS = 'SAVE_TEMP')"
						+ " AND BATCH_ID = ?";
		sqlArgs.add(batchId);

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
		AmsAssetsSamplingLineDTO dto = (AmsAssetsSamplingLineDTO) dtoParameter;
		if (foreignKey.equals("headerId")) {
			sqlModel = getDeleteByHeaderIdModel(dto.getHeaderId());
		} else if (foreignKey.equals("batchId")) {
			sqlModel = getDeleteByBatchIdModel(dto.getBatchId());
		}
		return sqlModel;
	}
}
