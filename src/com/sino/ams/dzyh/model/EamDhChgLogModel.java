package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.dto.EamDhChgLogDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EamDhChgLogModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhChgLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */

public class EamDhChgLogModel extends AMSSQLProducer {

/**
	 * 功能：低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhChgLogDTO 本次操作的数据
	 */
	public EamDhChgLogModel(BaseUserDTO userAccount, EamDhChgLogDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：框架自动生成低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhChgLogDTO dto = (EamDhChgLogDTO) dtoParameter;
		String sqlStr = "INSERT INTO"
						+ " EAM_DH_CHG_LOG("
						+ " DH_CHG_LOG_ID,"
						+ " BARCODE,"
						+ " CATALOG_VALUE_ID,"
						+ " CHG_TYPE,"
						+ " FROM_DEPT,"
						+ " TO_DEPT,"
						+ " FROM_ADDRESS_ID,"
						+ " TO_ADDRESS_ID,"
						+ " FROM_RESPONSIBILITY_USER,"
						+ " TO_RESPONSIBILITY_USER,"
						+ " FROM_MAINTAIN_USER,"
						+ " TO_MAINTAIN_USER,"
						+ " FROM_STATUS,"
						+ " TO_STATUS,"
						+ " FROM_ATTTIBUTE1,"
						+ " FROM_ATTRIBUTE2,"
						+ " TO_ATTRIBUTE1,"
						+ " TO_ATTRIBUTE2,"
						+ " CREATE_BY,"
						+ " CREATION_DATE"
						+ ") VALUES ("
						+ "  NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getCatalogValueId());
		sqlArgs.add(dto.getChgType());
		sqlArgs.add(dto.getFromDept());
		sqlArgs.add(dto.getToDept());
		sqlArgs.add(dto.getFromAddressId());
		sqlArgs.add(dto.getToAddressId());
		sqlArgs.add(dto.getFromResponsibilityUser());
		sqlArgs.add(dto.getToResponsibilityUser());
		sqlArgs.add(dto.getFromMaintainUser());
		sqlArgs.add(dto.getToMaintainUser());
		sqlArgs.add(dto.getFromStatus());
		sqlArgs.add(dto.getToStatus());
		sqlArgs.add(dto.getFromAtttibute1());
		sqlArgs.add(dto.getFromAttribute2());
		sqlArgs.add(dto.getToAttribute1());
		sqlArgs.add(dto.getToAttribute2());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhChgLogDTO dto = (EamDhChgLogDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EDCL.DH_CHG_LOG_ID,"
						+ " EDCL.BARCODE,"
						+ " EDCL.CATALOG_VALUE_ID,"
						+ " EDCL.CHG_TYPE,"
						+ " EDCL.FROM_DEPT,"
						+ " EDCL.TO_DEPT,"
						+ " EDCL.FROM_ADDRESS_ID,"
						+ " EDCL.TO_ADDRESS_ID,"
						+ " EDCL.FROM_RESPONSIBILITY_USER,"
						+ " EDCL.TO_RESPONSIBILITY_USER,"
						+ " EDCL.FROM_MAINTAIN_USER,"
						+ " EDCL.TO_MAINTAIN_USER,"
						+ " EDCL.FROM_STATUS,"
						+ " EDCL.TO_STATUS,"
						+ " EDCL.FROM_ATTTIBUTE1,"
						+ " EDCL.FROM_ATTRIBUTE2,"
						+ " EDCL.TO_ATTRIBUTE1,"
						+ " EDCL.TO_ATTRIBUTE2,"
						+ " EDCL.CREATE_BY,"
						+ " EDCL.CREATION_DATE,"
						+ " EDCL.REF_NO,"
						+ " ESI.ITEM_CATEGORY,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_VALUE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EDCS.SET_CODE,"
						+ " EDCS.SET_NAME,"
						+ " EDCV.CATALOG_CODE,"
						+ " EDCV.CATALOG_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCL.CHG_TYPE, 'DZYH_CHG_TYPE') CHG_TYPE_VALUE,"
						+ " AMDO.DEPT_NAME FROM_DEPT_NAME,"
						+ " AMDN.DEPT_NAME TO_DEPT_NAME,"
						+ " EOO.WORKORDER_OBJECT_CODE FROM_LOCATION_CODE,"
						+ " EOO.WORKORDER_OBJECT_NAME FROM_LOCATION_NAME,"
						+ " EON.WORKORDER_OBJECT_CODE TO_LOCATION_CODE,"
						+ " EON.WORKORDER_OBJECT_NAME TO_LOCATION_NAME,"
						+ " AMEO.USER_NAME FROM_RESPONSIBILITY_USER_NAME,"
						+ " AMEN.USER_NAME TO_RESPONSIBILITY_USER_NAME,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EDCL.CREATE_BY) CREATED_USER"
						+ " FROM"
						+ " EAM_DH_CATALOG_SET    EDCS,"
						+ " EAM_DH_CATALOG_VALUES EDCV,"
						+ " ETS_SYSTEM_ITEM       ESI,"
						+ " ETS_OBJECT            EOO,"
						+ " AMS_OBJECT_ADDRESS    AOAO,"
						+ " AMS_MIS_DEPT          AMDO,"
						+ " AMS_MIS_EMPLOYEE      AMEO,"
						+ " ETS_OBJECT            EON,"
						+ " AMS_OBJECT_ADDRESS    AOAN,"
						+ " AMS_MIS_DEPT          AMDN,"
						+ " AMS_MIS_EMPLOYEE      AMEN,"
						+ " ETS_ITEM_INFO         EII,"
						+ " EAM_DH_CHG_LOG        EDCL"
						+ " WHERE"
						+ " EDCL.BARCODE = EII.BARCODE"
						+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND ESI.ITEM_CATEGORY2 = EDCV.CATALOG_CODE"
						+ " AND EDCV.CATALOG_SET_ID = EDCS.CATLOG_SET_ID"
						+ " AND EDCL.FROM_ADDRESS_ID *= AOAO.ADDRESS_ID"
						+ " AND AOAO.OBJECT_NO *= EOO.WORKORDER_OBJECT_NO"
						+ " AND EDCL.FROM_DEPT *= AMDO.DEPT_CODE"
						+ " AND EDCL.FROM_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
						+ " AND EDCL.TO_ADDRESS_ID *= AOAN.ADDRESS_ID"
						+ " AND AOAN.OBJECT_NO *= EON.WORKORDER_OBJECT_NO"
						+ " AND EDCL.TO_DEPT *= AMDN.DEPT_CODE"
						+ " AND EDCL.TO_RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
						+ " AND EDCV.ENABLED = '1'"
						+ " AND EDCS.ENABLED = '1'"
						+ " AND AOAN.BOX_NO(+) = '0000'"
						+ " AND AOAN.NET_UNIT(+) = '0000'";
		if (!dto.getDhChgLogId().equals("")) {
			sqlStr = sqlStr
					 + " AND EDCL.DH_CHG_LOG_ID = dbo.NVL(?, EDCL.DH_CHG_LOG_ID)";
			sqlArgs.add(dto.getDhChgLogId());
		} else {
			sqlStr = sqlStr
					 + " AND EDCL.BARCODE = dbo.NVL(?, EDCL.BARCODE)"
					 + " AND EDCL.REF_NO = dbo.NVL(?, EDCL.REF_NO)";
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getRefNo());
		}

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhChgLogDTO dto = (EamDhChgLogDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EDCL.DH_CHG_LOG_ID,"
+ " EDCL.BARCODE,"
						+ " EDCL.CATALOG_VALUE_ID,"
						+ " EDCL.CHG_TYPE,"
						+ " EDCL.FROM_DEPT,"
+ " EDCL.TO_DEPT,"
						+ " EDCL.FROM_ADDRESS_ID,"
						+ " EDCL.TO_ADDRESS_ID,"
						+ " EDCL.FROM_RESPONSIBILITY_USER,"
						+ " EDCL.TO_RESPONSIBILITY_USER,"
						+ " EDCL.FROM_STATUS,"
						+ " EDCL.TO_STATUS,"
						+ " EDCL.FROM_ATTTIBUTE1,"
						+ " EDCL.FROM_ATTRIBUTE2,"
						+ " EDCL.TO_ATTRIBUTE1,"
						+ " EDCL.TO_ATTRIBUTE2,"
						+ " EDCL.CREATE_BY,"
						+ " EDCL.REF_NO,"
						+ " ESI.ITEM_CATEGORY,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_VALUE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EDCS.SET_CODE,"
						+ " EDCS.SET_NAME,"
						+ " EDCV.CATALOG_CODE,"
						+ " EDCV.CATALOG_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCL.CHG_TYPE, 'DZYH_CHG_TYPE') CHG_TYPE_VALUE,"
						+ " AMDO.DEPT_NAME FROM_DEPT_NAME,"
						+ " AMDN.DEPT_NAME TO_DEPT_NAME,"
						+ " EOO.WORKORDER_OBJECT_CODE FROM_LOCATION_CODE,"
						+ " EOO.WORKORDER_OBJECT_NAME FROM_LOCATION_NAME,"
						+ " EON.WORKORDER_OBJECT_CODE TO_LOCATION_CODE,"
						+ " EON.WORKORDER_OBJECT_NAME TO_LOCATION_NAME,"
						+ " AMEO.USER_NAME FROM_RESPONSIBILITY_USER_NAME,"
						+ " AMEN.USER_NAME TO_RESPONSIBILITY_USER_NAME,"
						+ " EDCL.FROM_MAINTAIN_USER,"
						+ " EDCL.TO_MAINTAIN_USER,"
						+ " EDCL.CREATION_DATE,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EDCL.CREATE_BY) CREATED_USER"
						+ " FROM"
						+ " EAM_DH_CATALOG_SET    EDCS,"
						+ " EAM_DH_CATALOG_VALUES EDCV,"
						+ " ETS_SYSTEM_ITEM       ESI,"
						+ " ETS_OBJECT            EOO,"
						+ " AMS_OBJECT_ADDRESS    AOAO,"
						+ " AMS_MIS_DEPT          AMDO,"
						+ " AMS_MIS_EMPLOYEE      AMEO,"
						+ " ETS_OBJECT            EON,"
						+ " AMS_OBJECT_ADDRESS    AOAN,"
						+ " AMS_MIS_DEPT          AMDN,"
						+ " AMS_MIS_EMPLOYEE      AMEN,"
						+ " ETS_ITEM_INFO         EII,"
						+ " EAM_DH_CHG_LOG        EDCL"
						+ " WHERE"
						+ " EDCL.BARCODE = EII.BARCODE"
						+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND ESI.ITEM_CATEGORY2 = EDCV.CATALOG_CODE"
						+ " AND EDCV.CATALOG_SET_ID = EDCS.CATLOG_SET_ID"
						+ " AND EDCL.FROM_ADDRESS_ID *= AOAO.ADDRESS_ID"
						+ " AND AOAO.OBJECT_NO *= EOO.WORKORDER_OBJECT_NO"
						+ " AND EDCL.FROM_DEPT *= AMDO.DEPT_CODE"
						+ " AND EDCL.FROM_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
						+ " AND EDCL.TO_ADDRESS_ID *= AOAN.ADDRESS_ID"
						+ " AND AOAN.OBJECT_NO *= EON.WORKORDER_OBJECT_NO"
						+ " AND EDCL.TO_DEPT *= AMDN.DEPT_CODE"
						+ " AND EDCL.TO_RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
						+ " AND EDCV.ENABLED = '1'"
						+ " AND EDCS.ENABLED = '1'"
						+ " AND AOAN.BOX_NO(+) = '0000'"
						+ " AND AOAN.NET_UNIT(+) = '0000'"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AOAO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, AOAO.WORKORDER_OBJECT_NAME))"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMDO.DEPT_NAME LIKE dbo.NVL(?, AMDO.DEPT_NAME))"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMEO.USER_NAME LIKE dbo.NVL(?, AMEO.USER_NAME))"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AOAN.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, AOAN.WORKORDER_OBJECT_NAME))"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMDN.DEPT_NAME LIKE dbo.NVL(?, AMDN.DEPT_NAME))"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AMEN.USER_NAME LIKE dbo.NVL(?, AMEN.USER_NAME))"
						+ " AND EDCL.BARCODE LIKE dbo.NVL(?, EDCL.BARCODE)";
		sqlArgs.add(dto.getFromLocationName());
		sqlArgs.add(dto.getFromLocationName());
		sqlArgs.add(dto.getFromDeptName());
		sqlArgs.add(dto.getFromDeptName());
		sqlArgs.add(dto.getFromResponsibilityUserName());
		sqlArgs.add(dto.getFromResponsibilityUserName());

		sqlArgs.add(dto.getToLocationName());
		sqlArgs.add(dto.getToLocationName());
		sqlArgs.add(dto.getToDeptName());
		sqlArgs.add(dto.getToDeptName());
		sqlArgs.add(dto.getToResponsibilityUserName());
		sqlArgs.add(dto.getToResponsibilityUserName());
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

/**
	 * 功能：构造获取低值易耗标签号变动日志历史信息SQL
	 * @return SQLModel
	 */
	public SQLModel getBarcodeChgHisModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhChgLogDTO dto = (EamDhChgLogDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EDCL.DH_CHG_LOG_ID,"
+ " EDCL.BARCODE,"
						+ " EDCL.CATALOG_VALUE_ID,"
						+ " EDCL.CHG_TYPE,"
						+ " EDCL.FROM_DEPT,"
+ " EDCL.TO_DEPT,"
						+ " EDCL.FROM_ADDRESS_ID,"
						+ " EDCL.TO_ADDRESS_ID,"
						+ " EDCL.FROM_RESPONSIBILITY_USER,"
						+ " EDCL.TO_RESPONSIBILITY_USER,"
						+ " EDCL.FROM_MAINTAIN_USER,"
						+ " EDCL.TO_MAINTAIN_USER,"
						+ " EDCL.FROM_STATUS,"
						+ " EDCL.TO_STATUS,"
						+ " EDCL.FROM_ATTTIBUTE1,"
						+ " EDCL.FROM_ATTRIBUTE2,"
						+ " EDCL.TO_ATTRIBUTE1,"
						+ " EDCL.TO_ATTRIBUTE2,"
						+ " EDCL.CREATE_BY,"
						+ " EDCL.CREATION_DATE,"
						+ " EDCL.REF_NO,"
						+ " ESI.ITEM_CATEGORY,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_VALUE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " EDCS.SET_CODE,"
						+ " EDCS.SET_NAME,"
						+ " EDCV.CATALOG_CODE,"
						+ " EDCV.CATALOG_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCL.CHG_TYPE, 'DZYH_CHG_TYPE') CHG_TYPE_VALUE,"
						+ " AMDO.DEPT_NAME FROM_DEPT_NAME,"
						+ " AMDN.DEPT_NAME TO_DEPT_NAME,"
						+ " EOO.WORKORDER_OBJECT_CODE FROM_LOCATION_CODE,"
						+ " EOO.WORKORDER_OBJECT_NAME FROM_LOCATION_NAME,"
						+ " EON.WORKORDER_OBJECT_CODE TO_LOCATION_CODE,"
						+ " EON.WORKORDER_OBJECT_NAME TO_LOCATION_NAME,"
						+ " AMEO.USER_NAME FROM_RESPONSIBILITY_USER_NAME,"
						+ " AMEN.USER_NAME TO_RESPONSIBILITY_USER_NAME,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EDCL.CREATE_BY) CREATED_USER"
						+ " FROM"
						+ " EAM_DH_CATALOG_SET    EDCS,"
						+ " EAM_DH_CATALOG_VALUES EDCV,"
						+ " ETS_SYSTEM_ITEM       ESI,"
						+ " ETS_OBJECT            EOO,"
						+ " AMS_OBJECT_ADDRESS    AOAO,"
						+ " AMS_MIS_DEPT          AMDO,"
						+ " AMS_MIS_EMPLOYEE      AMEO,"
						+ " ETS_OBJECT            EON,"
						+ " AMS_OBJECT_ADDRESS    AOAN,"
						+ " AMS_MIS_DEPT          AMDN,"
						+ " AMS_MIS_EMPLOYEE      AMEN,"
						+ " ETS_ITEM_INFO         EII,"
						+ " EAM_DH_CHG_LOG        EDCL"
						+ " WHERE"
						+ " EDCL.BARCODE = EII.BARCODE"
						+ " AND EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND ESI.ITEM_CATEGORY2 = EDCV.CATALOG_CODE"
						+ " AND EDCV.CATALOG_SET_ID = EDCS.CATLOG_SET_ID"
						+ " AND EDCL.FROM_ADDRESS_ID *= AOAO.ADDRESS_ID"
						+ " AND AOAO.OBJECT_NO *= EOO.WORKORDER_OBJECT_NO"
						+ " AND EDCL.FROM_DEPT *= AMDO.DEPT_CODE"
						+ " AND EDCL.FROM_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
						+ " AND EDCL.TO_ADDRESS_ID *= AOAN.ADDRESS_ID"
						+ " AND AOAN.OBJECT_NO *= EON.WORKORDER_OBJECT_NO"
						+ " AND EDCL.TO_DEPT *= AMDN.DEPT_CODE"
						+ " AND EDCL.TO_RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
						+ " AND EDCV.ENABLED = '1'"
						+ " AND EDCS.ENABLED = '1'"
						+ " AND AOAN.BOX_NO(+) = '0000'"
						+ " AND AOAN.NET_UNIT(+) = '0000'"
						+ " AND EDCL.BARCODE LIKE dbo.NVL(?, EDCL.BARCODE)";
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}
}
