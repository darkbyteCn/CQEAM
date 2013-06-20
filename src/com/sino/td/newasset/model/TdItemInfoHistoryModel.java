package com.sino.td.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.td.newasset.dto.TdItemInfoHistoryDTO;

/**
 * <p>Title: AmsItemInfoHistoryModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsItemInfoHistoryModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class TdItemInfoHistoryModel extends AMSSQLProducer {

	/**
	 * 功能：设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdItemInfoHistoryDTO 本次操作的数据
	 */
	public TdItemInfoHistoryModel(SfUserDTO userAccount,
								   TdItemInfoHistoryDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		TdItemInfoHistoryDTO dto = (TdItemInfoHistoryDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_ITEM_INFO_HISTORY("
						+ " HISTORY_ID,"
						+ " BARCODE,"
						+ " ADDRESS_ID,"
						+ " ITEM_CODE,"
						+ " RESPONSIBILITY_USER,"
						+ " RESPONSIBILITY_DEPT,"
						+ " ORDER_NO,"
						+ " ORDER_CATEGORY,"
						+ " ORDER_DTL_URL,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " REMARK"
						+ ") VALUES ("
						+ "  NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, GETDATE() , ?, ?)";

		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getAddressId());
		sqlArgs.add(dto.getItemCode());
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getOrderNo());
		sqlArgs.add(dto.getOrderCategory());
		sqlArgs.add(dto.getOrderDtlUrl());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getRemark());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 barcode 构造查询数据SQL。
	 * 框架自动生成数据设备地点变动历史表(EAM) AMS_ITEM_INFO_HISTORY详细信息查询SQLModel，请根据实际需要修改。
	 * @param barcode String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getDataByBarcodeModel(String barcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AIIH.BARCODE,"
						+ " dbo.NVL(TMP_1.ITEM_CATEGORY_NAME, '未变动') ITEM_CATEGORY_NAME,"
						+ " dbo.NVL(TMP_1.ITEM_NAME, '未变动') ITEM_NAME,"
						+ " dbo.NVL(TMP_1.ITEM_SPEC, '未变动') ITEM_SPEC,"
						+ " dbo.NVL(AME.USER_NAME, '未变动') RESPONSIBILITY_USER_NAME,"
						+ " dbo.NVL(AMD.DEPT_NAME, '未变动') RESPONSIBILITY_DEPT_NAME,"
						+ " dbo.NVL(EO.WORKORDER_OBJECT_NAME, '未变动') WORKORDER_OBJECT_NAME,"
						+ " dbo.NVL(EO.WORKORDER_OBJECT_CODE, '未变动') ADDRESS_NO,"
//						+ " dbo.NVL(AOA.ADDRESS_NO, '未变动') ADDRESS_NO,"
						+ " AIIH.CREATION_DATE,"
						+ " AIIH.ORDER_NO,"
						+ " AIIH.ORDER_DTL_URL"
						+ " FROM"
						+ " AMS_ITEM_INFO_HISTORY AIIH,"
						+ " (SELECT"
						+ " ESI.ITEM_CODE,"
						+ " EFV.VALUE ITEM_CATEGORY_NAME,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC"
						+ " FROM"
						+ " ETS_SYSTEM_ITEM    ESI,"
						+ " ETS_FLEX_VALUES    EFV,"
						+ " ETS_FLEX_VALUE_SET EFVS"
						+ " WHERE"
						+ " ESI.ITEM_CATEGORY = EFV.CODE"
						+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
						+ " AND EFVS.CODE = 'ITEM_TYPE') TMP_1,"
						+ " AMS_OBJECT_ADDRESS    AOA,"
						+ " ETS_OBJECT            EO,"
						+ " AMS_MIS_EMPLOYEE      AME,"
						+ " AMS_MIS_DEPT          AMD"
						+ " WHERE"
						+ " AIIH.ITEM_CODE *= TMP_1.ITEM_CODE"
						+ " AND AIIH.ADDRESS_ID *= AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO *= EO.WORKORDER_OBJECT_NO"
						+ " AND AIIH.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND AIIH.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
						+ " AND AIIH.BARCODE = ?"
						+ " ORDER BY"
						+ " AIIH.CREATION_DATE DESC";
		sqlArgs.add(barcode);

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
		TdItemInfoHistoryDTO dto = (TdItemInfoHistoryDTO) dtoParameter;
		if (foreignKey.equals("barcode")) {
			sqlModel = getDataByBarcodeModel(dto.getBarcode());
		}
		return sqlModel;
	}
}
