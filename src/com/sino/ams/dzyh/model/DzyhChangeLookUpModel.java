package com.sino.ams.dzyh.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.DzyhLookUpConstant;
import com.sino.ams.dzyh.dto.EamCheckImplementDTO;
import com.sino.ams.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.dzyh.dto.EamDhChgLogDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.base.lookup.LookUpModel;
import com.sino.base.lookup.LookUpProp;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>
 * Title: EamDhChgLogModel
 * </p>
 * <p>
 * Description:程序自动生成SQL构造器“EamDhChgLogModel”，请根据需要自行修改
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class DzyhChangeLookUpModel extends LookUpModel {

	/**
	 * 功能：低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG 数据库SQL构造层构造函数
	 * 
	 * @param userAccount
	 *            SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter
	 *            EamDhChgLogDTO 本次操作的数据
	 */
	public DzyhChangeLookUpModel(SfUserDTO userAccount, DTO dtoParameter,
			LookUpProp lookProp) {
		super(userAccount, dtoParameter, lookProp);
	}

	public void setServletConfig(ServletConfigDTO servletConfig) {
		super.setServletConfig(servletConfig);
	}

	/**
	 * 功能：框架自动生成低值易耗品变动历史表(EAM) EAM_DH_CHG_LOG多条数据信息查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	protected void produceSQLModel() {
		sqlModel = new SQLModel();
		String sqlStr = "";
		List sqlArgs = new ArrayList();
		String lookUpName = lookProp.getLookUpName();
		if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_CHANGE_DZYH)) { // 低值易耗品的变动历史查询
			EamDhChgLogDTO eamDhChgLog = (EamDhChgLogDTO) dtoParameter;
			sqlStr = "SELECT "
					+ " EDCL.DH_CHG_LOG_ID,"
					+ " EDCL.BARCODE,"
					+ " EDCL.CATALOG_VALUE_ID,"
					+ " EDCL.CHG_TYPE,"
					+ " EDCL.FROM_DEPT,"
					+ " AMD.DEPT_NAME EDCL_FROM_DEPT_NAME,"
					+ " EDCL.TO_DEPT,"
					+ " AMD.DEPT_NAME EDCL_TO_DEPT_NAME,"
					+ " EDCL.FROM_ADDRESS_ID,"
					+ " EO.WORKORDER_OBJECT_NAME EDCL_FROM_WORKORDER_NAME,"
					+ " EDCL.TO_ADDRESS_ID,"
					+ " EO.WORKORDER_OBJECT_NAME EDCL_TO_WORKORDER_NAME,"
					+ " EDCL.FROM_RESPONSIBILITY_USE,"
					+ " AME.USER_NAME EDCL_FROM_USER_NAME,"
					+ " EDCL.TO_RESPONSIBILITY_USER,"
					+ " AME.USER_NAME EDCL_TO_USER_NAME,"
					+ " EDCL.FROM_MAINTAIN_USER,"
					+ " EDCL.TO_MAINTAIN_USER,"
					+ " EDCL.FROM_STATUS,"
					+ " EDCL.TO_STATUS,"
					+ " EDCL.CREATE_BY,"
					+ " SU.USERNAME,"
					+ " EDCL.CREATION_DATE"
					+ " FROM"
					+ " EAM_DH_CHG_LOG EDCL,"
					+ " AMS_MIS_DEPT AMD,"
					+ " ETS_OBJECT EO,"
					+ " AMS_OBJECT_ADDRESS AOA,"
					+ " EAM_DH_CATALOG_VALUES EDCV,"
					+ " AMS_MIS_EMPLOYEE AME,"
					+ " SF_USER SU"
					+ " WHERE"
					+ " EDCL.CATALOG_VALUE_ID=EDCV.CATALOG_VALUE_ID"
					+ " AND EDCL.FROM_DEPT=AMD.DEPT_CODE"
					+ " AND EDCL.TO_DEPT=AMD.DEPT_CODE"
					+ " AND EO.WORKORDER_OBJECT_NO=AOA.OBJECT_NO"
					+ " AND EDCL.FROM_ADDRESS_ID=AOA.ADDRESS_ID"
					+ " AND EDCL.TO_ADDRESS_ID=AOA.ADDRESS_ID"
					+ " AND EDCL.FROM_RESPONSIBILITY_USER=AME.EMPLOYEE_ID"
					+ " AND EDCL.TO_RESPONSIBILITY_USER=AME.EMPLOYEE_ID"
					+ " AND AOA.BOX_NO='0000'"
					+ " AND AOA.NET_UNIT='0000'"
					+ " AND EDCL.BARCODE LIKE dbo.NVL(?, EDCL.BARCODE)"
					+ " AND EDCL.CATALOG_VALUE_ID LIKE dbo.NVL(?, EDCL.CATALOG_VALUE_ID)";
			sqlArgs.add("%" + eamDhChgLog.getBarcode() + "%");
			sqlArgs.add(eamDhChgLog.getDhChgLogId());

		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_CATEGORY2_DZYH)) { // 低值易耗目录编号
			EamDhBillLDTO ebd = (EamDhBillLDTO) dtoParameter;
			System.out.println("===============>>>>>>>>>>" + ebd);
			sqlStr = "SELECT " + " EDCV.CATALOG_VALUE_ID,"
					+ " EDCV.CATALOG_CODE,"
					+" CASE WHEN EDCV.ENABLED='1' THEN '是' ELSE '否' END ENABLED "
					+ " FROM"
					+ " EAM_DH_CATALOG_VALUES EDCV" + " WHERE"
					+ " EDCV.CATALOG_CODE LIKE dbo.NVL(?, EDCV.CATALOG_CODE)";
			System.out.println("=================>>>>>>>>>>>>>>>>>>>>>>>======="+ ebd.getCatalogCode());
			sqlArgs.add(ebd.getCatalogCode());
		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_ITEMNAME_DZYH)) { // 低值易耗品名
			EamDhBillLDTO ebd = (EamDhBillLDTO) dtoParameter;
			sqlStr = "SELECT " + " ESI.ITEM_CODE," + " ESI.ITEM_NAME,"
					+" CASE WHEN ESI.ENABLED='1' THEN '是' ELSE '否' END ENABLED "
					+ " FROM"
					+ " ETS_SYSTEM_ITEM ESI" + " FROM"
					+ " ESI.ITEM_CODE LIKE dbo.NVL(?, ESI.ITEM_CODE)"
					+ " ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)";
			sqlArgs.add(ebd.getItemName());
			sqlArgs.add(ebd.getItemSpec());
		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_ITEMSPEC_DZYH)) { // 低值易耗规格型号
			EamDhBillLDTO ebd = (EamDhBillLDTO) dtoParameter;
			sqlStr = "SELECT " 
					+ " ESI.ITEM_CODE," 
					+ " ESI.ITEM_NAME,"
					+ " ESI.ITEM_SPEC,"
					+" CASE WHEN ESI.ENABLED='1' THEN '是' ELSE '否' END ENABLED "
					+ " FROM"
					+ " ESI.ITEM_CODE LIKE dbo.NVL(?, ESI.ITEM_CODE)"
					+ " ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)";
			sqlArgs.add(ebd.getItemName());
			sqlArgs.add(ebd.getItemSpec());
		} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_TASK_DZYH)) { // 查找任务名称
			EamCheckImplementDTO dto = (EamCheckImplementDTO) dtoParameter;
			sqlStr = "SELECT "
					+ " ECT.CHECK_TASK_ID,\n"
					+ " ECT.TASK_NAME,\n"
					+ " ECT.CHECK_TYPE\n"
					+ " FROM "
					+ " EAM_CHECK_TASK ECT\n"
					+ " WHERE"
					+ " ( " + SyBaseSQLUtil.isNull() + "  OR ECT.TASK_NAME LIKE dbo.NVL(?, ECT.TASK_NAME))\n"
					+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ECT.CHECK_TYPE LIKE dbo.NVL(?, ECT.CHECK_TYPE))";
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getCheckType());
			sqlArgs.add(dto.getCheckType());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

	}

}
