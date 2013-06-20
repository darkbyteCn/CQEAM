package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.dzyh.dto.EamCheckImplementDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;


/**
 * <p>Title: EamCheckImplementModel</p>
 * <p>Description:程序自动生成SQL构造器"EamCheckImplementModel"，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamCheckImplementModel extends AMSSQLProducer {

	/**
	 * 功能：工单执行查询  数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamCheckImplementDTO 本次操作的数据
	 */
	public EamCheckImplementModel(SfUserDTO userAccount, EamCheckImplementDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：部门遗失设备   数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
//		EamCheckImplementDTO dto = (EamCheckImplementDTO)dtoParameter;
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
//		sqlArgs.add(dto.getBarcode());
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：工单执行查询  页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamCheckImplementDTO dto = (EamCheckImplementDTO)dtoParameter;
			String sqlStr = "SELECT "
				+ " EDCH.HEADER_ID,"
				+ " EDCH.CHECK_TASK_ID,"
				+ " ECT.TASK_NAME,"
				+ " EDCH.ORDER_NO,"
				+ " AMD.DEPT_NAME,"
				+ " EDCH.CHECK_LOCATION,"
				+ " EO.WORKORDER_OBJECT_NAME,"
				+ " EDCH.IMPLEMENT_DAYS,"
				+ " AMS_PUB_PKG.GET_USER_NAME(EDCH.IMPLEMENT_BY) USER_NAME,"
				+ " EDCH.DOWNLOAD_DATE,"
				+ " EDCH.UPLOAD_DATE,"
				+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCH.ORDER_STATUS,'CHKORDER_STATUS') ORDER_STATUS"
				+ " FROM"
				+ " EAM_CHECK_TASK 		ECT,"
				+ " EAM_DH_CHECK_HEADER EDCH,"
				+ " ETS_OBJECT 			EO,"
				+ " AMS_OBJECT_ADDRESS  AOA,"
				+ " AMS_MIS_DEPT		AMD"
				+ " WHERE"
				+ " ECT.CHECK_TASK_ID=EDCH.CHECK_TASK_ID"
				+ " AND EDCH.CHECK_LOCATION=AOA.ADDRESS_ID"
				+ " AND EO.WORKORDER_OBJECT_NO=AOA.OBJECT_NO"
				+ " AND AOA.BOX_NO='0000'"
				+ " AND AOA.NET_UNIT='0000'"
				+ " AND EO.DEPT_CODE=AMD.DEPT_CODE"
				+ "	AND EDCH.CHECK_TASK_ID = dbo.NVL(?, EDCH.CHECK_TASK_ID)"
				+ " AND EDCH.CREATION_DATE >= ISNULL(?, EDCH.CREATION_DATE)"
				+ " AND EDCH.CREATION_DATE <= ISNULL(?, EDCH.CREATION_DATE)"
				+ " AND AMD.DEPT_CODE = dbo.NVL(?, AMD.DEPT_CODE)"
				+ " AND EDCH.ORDER_NO LIKE dbo.NVL(?, EDCH.ORDER_NO)"
				+ " AND EDCH.IMPLEMENT_BY = dbo.NVL(?, EDCH.IMPLEMENT_BY)";
//			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getCheckTaskId());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getImplementBy());
		
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

}