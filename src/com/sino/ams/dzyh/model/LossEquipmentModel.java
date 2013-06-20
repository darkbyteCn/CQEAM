package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.dzyh.dto.LossEquipmentDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: LossEquipmentModel</p>
 * <p>Description:程序自动生成SQL构造器"LossEquipmentModel"，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class LossEquipmentModel extends AMSSQLProducer {

	/**
	 * 功能：部门遗失设备  数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter LossEquipmentDTO 本次操作的数据
	 */
	public LossEquipmentModel(SfUserDTO userAccount, LossEquipmentDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：部门遗失设备   数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		LossEquipmentDTO dto = (LossEquipmentDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " EII.SYSTEMID,"
			+ " ESI.ITEM_CATEGORY2,"
			+ " EII.BARCODE,"
			+ " ESI.ITEM_NAME,"
			+ " ESI.ITEM_SPEC,"
			+ " EII.ITEM_QTY,"
			+ " EII.PRICE,"
			+ " EII.RESPONSIBILITY_DEPT,"
			+ " AMD.DEPT_NAME,"
			+ " EII.RESPONSIBILITY_USER,"
			+ " AME.USER_NAME,"
			+ " EII.ADDRESS_ID,"
			+ " EO.WORKORDER_OBJECT_NAME,"
			+ " EII.MAINTAIN_USER,"
			+ " EII.LAST_LOC_CHG_DATE,"
			+ " EII.ATTRIBUTE3,"
			+ " EII.REMARK"
			+ " FROM"
			+ " EAM_DH_CHG_LOG EDCL,"
			+ " ETS_ITEM_INFO EII,"
			+ " ETS_SYSTEM_ITEM ESI,"
			+ " AMS_OBJECT_ADDRESS AOA,"
			+ " ETS_OBJECT EO"
			+ " WHERE"
			+ " ESI.ITEM_CODE=EII.ITEM_CODE"
			+ " AND EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE"
			+ " AND EII.ADDRESS_ID=AOA.ADDRESS_ID"
			+ " AND AOA.BOX_NO='0000'"
			+ " AND AOA.NET_UNIT='0000'"
			+ " AND EII.BARCODE = ?";
		sqlArgs.add(dto.getBarcode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：部门遗失设备  页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			LossEquipmentDTO dto = (LossEquipmentDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " EDCL.DH_CHG_LOG_ID,"
				+ " EII.BARCODE,"
				+ " ESI.ITEM_CATEGORY2,"
				+ " ESI.ITEM_NAME,"
				+ " ESI.ITEM_SPEC,"
				+ " EII.RESPONSIBILITY_DEPT,"
				+ " AMS_PUB_PKG.GET_DEPT_NAME(EII.RESPONSIBILITY_DEPT) DEPT_NAME,"
				+ " EDCL.FROM_ADDRESS_ID,"
				+ " EO.WORKORDER_OBJECT_NAME ,"
				+ " EII.MAINTAIN_USER"
				+ " FROM"
				+ " EAM_DH_CHG_LOG EDCL,"
				+ " ETS_ITEM_INFO EII,"
				+ " ETS_SYSTEM_ITEM ESI,"
				+ " AMS_OBJECT_ADDRESS AOA,"
				+ " ETS_OBJECT EO"
				+ " WHERE"
				+ " EDCL.FROM_ADDRESS_ID= AOA.ADDRESS_ID"
				+ " AND EDCL.BARCODE=EII.BARCODE"
				+ " AND EII.ITEM_CODE=ESI.ITEM_CODE"
				+ " AND AOA.OBJECT_NO=EO.WORKORDER_OBJECT_NO"
				+ " AND AOA.BOX_NO='0000'"
				+ " AND AOA.NET_UNIT='0000'"
				+ " AND EO.OBJECT_CATEGORY = 74"
				+ " AND EXISTS (SELECT 'A'" 
				+ " FROM AMS_OBJECT_ADDRESS AOA1, ETS_OBJECT EO1"
				+ " WHERE AOA1.OBJECT_NO = EO1.WORKORDER_OBJECT_NO"
				+ " AND AOA1.ADDRESS_ID = EDCL.TO_ADDRESS_ID"
				+ " AND EO1.OBJECT_CATEGORY = 74)"
				+ " AND EXISTS (SELECT 'A'"
				+ " FROM EAM_DH_CHECK_HEADER EDCH, EAM_CHECK_TASK ECT"
				+ " WHERE EDCH.ORDER_NO = EDCL.REF_NO"
				+ " AND EDCH.CHECK_TASK_ID = ECT.CHECK_TASK_ID"
				+ " AND ECT.CHECK_TASK_ID = dbo.NVL(?, ECT.CHECK_TASK_ID)"
				+ "	)"
				+ "	AND EII.RESPONSIBILITY_DEPT=dbo.NVL(?, EII.RESPONSIBILITY_DEPT)"
				+ " AND EDCL.CREATION_DATE >= ISNULL(?, EDCL.CREATION_DATE)"
				+ " AND EDCL.CREATION_DATE <= ISNULL(?, EDCL.CREATION_DATE)";
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getCheckTaskId());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}