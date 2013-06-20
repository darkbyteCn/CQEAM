package com.sino.ams.dzyh.OptionProducer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.QueryException;
import com.sino.base.web.DatabaseForWeb;

public class EamProducer extends OptionProducer {

	public EamProducer(SfUserDTO userAccount, Connection conn) {
		super(userAccount, conn);
	}

	/**
	 * 功能:获取区县下拉列表框
	 * @param selectedValue 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getDzyhParentOption(int selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
				+ " EDCS.CATLOG_SET_ID,"
				+ " EDCS.SET_CODE||'('||EDCS.SET_NAME||')'"
				+ " FROM"
				+ " EAM_DH_CATALOG_SET EDCS";
		sqlModel.setSqlStr(sqlStr);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(String.valueOf(selectedValue), true);
	}

	/**
	 * 功能:获取区县下拉列表框（进入详细页面时被选中）
	 * @param selectedValue 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getDetailDzyhParentOption(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT"
				+ " EDCS.CATLOG_SET_ID,"
				+ " EDCS.SET_CODE||'('||EDCS.SET_NAME||')'"
				+ " FROM"
				+ " EAM_DH_CATALOG_SET EDCS";
		sqlModel.setSqlStr(sqlStr);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：构造是否下拉框
	 * @param selectedValue String
	 * @return String
	 */
	public String getBooleanOption(String selectedValue) {
		StringBuffer strOpt = new StringBuffer();
		if (selectedValue == null) {
			selectedValue = "";
		}
		strOpt.append("<option value=\"\">--请选择--</option>");
		strOpt.append("<option value=\"");
		strOpt.append(WebAttrConstant.DZYH_TRUE_VALUE);
		strOpt.append("\"");
		if (selectedValue.equals(WebAttrConstant.DZYH_TRUE_VALUE)) {
			strOpt.append(" selected");
		}
		strOpt.append(">是</option>");
		strOpt.append("<option value=\"");
		strOpt.append(WebAttrConstant.DZYH_FALSE_VALUE);
		strOpt.append("\"");
		if (selectedValue.equals(WebAttrConstant.DZYH_FALSE_VALUE)) {
			strOpt.append(" selected");
		}
		strOpt.append(">否</option>");
		return strOpt.toString();
	}

	/**
	 * 功能：生成字典dictCode的下拉列表
	 * @param flexValueSetId      String 字典代码
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getDictOption(String flexValueSetId, String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " EFV.CODE,"
				+ " EFV.VALUE"
				+ " FROM"
				+ " ETS_FLEX_VALUES    EFV,"
				+ " ETS_FLEX_VALUE_SET EFVS"
				+ " WHERE"
				+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
				+ " AND EFV.ENABLED = 'Y'"
				+ " AND EFVS.FLEX_VALUE_SET_ID = ?";
		sqlArgs.add(flexValueSetId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue, true);
	}

	/**
	 * 功能：生成字典dictCode的下拉列表（进入详细页面时被选中）
	 * @param flexValueSetId      String 字典代码
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getDeDictOption(String flexValueSetId, String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " EFV.CODE,"
				+ " EFV.VALUE"
				+ " FROM"
				+ " ETS_FLEX_VALUES    EFV,"
				+ " ETS_FLEX_VALUE_SET EFVS"
				+ " WHERE"
				+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
				+ " AND EFV.ENABLED = 'Y'"
				+ " AND EFVS.FLEX_VALUE_SET_ID = ?";
		sqlArgs.add(flexValueSetId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：生成字典dictCode的下拉列表（进入详细页面时被选中）
	 * @param dictCode      String 字典代码
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getDetailDictOption(String dictCode, String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " EFV.CODE,"
				+ " EFV.VALUE"
				+ " FROM"
				+ " ETS_FLEX_VALUES    EFV,"
				+ " ETS_FLEX_VALUE_SET EFVS"
				+ " WHERE"
				+ " EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
				+ " AND EFV.ENABLED = 'Y'"
				+ " AND EFVS.CODE = ?";
		sqlArgs.add(dictCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：生成目录编号category2的下拉列表（进入详细页面时被选中）
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getCategory2Option(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " ESI.ITEM_CODE,"
				+ " ESI.ITEM_CATEGORY2"
				+ " FROM"
				+ " ETS_SYSTEM_ITEM    ESI,"
				+ " ETS_ITEM_INFO 	   EII"
				+ " WHERE"
				+ " ESI.ITEM_CODE = EII.ITEM_CODE"
				+ " AND EII.FINANCE_PROP='DZYH'";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：生成品名itemName的下拉列表（进入详细页面时被选中）
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getItemNameOption(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
			+ " ESI.ITEM_CODE,"
			+ " ESI.ITEM_NAME"
			+ " FROM"
			+ " ETS_SYSTEM_ITEM    ESI,"
			+ " ETS_ITEM_INFO 	   EII"
			+ " WHERE"
			+ " ESI.ITEM_CODE = EII.ITEM_CODE"
			+ " AND EII.FINANCE_PROP='DZYH'";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：生成规格类型itemSpec的下拉列表（进入详细页面时被选中）
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getItemSpecOption(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " ESI.ITEM_CODE,"
				+ " ESI.ITEM_SPEC"
				+ " FROM"
				+ " ETS_SYSTEM_ITEM    ESI,"
				+ " ETS_ITEM_INFO 	   EII"
				+ " WHERE"
				+ " ESI.ITEM_CODE = EII.ITEM_CODE"
				+ " AND EII.FINANCE_PROP='DZYH'";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：生成使用部门responsibilityDept的下拉列表（进入详细页面时被选中）
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getResponsibilityDeptOption(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " AMD.DEPT_CODE,"
				+ " AMD.DEPT_NAME"
				+ " FROM"
				+ " AMS_MIS_DEPT AMD,"
				+ " ETS_ITEM_INFO 	   EII"
				+ " WHERE"
				+ " EII.RESPONSIBILITY_DEPT=AMD.DEPT_CODE"
				+ " AND EII.FINANCE_PROP='DZYH'";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：生成领用人responsibilityUser的下拉列表（进入详细页面时被选中）
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getResponsibilityUserOption(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " AME.EMPLOYEE_ID,"
				+ " AME.USER_NAME"
				+ " FROM"
				+ " AMS_MIS_EMPLOYEE AME,"
				+ " ETS_ITEM_INFO 	   EII"
				+ " WHERE"
				+ " EII.RESPONSIBILITY_USER=AME.EMPLOYEE_ID"
				+ " AND EII.FINANCE_PROP='DZYH'";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

	/**
	 * 功能：生成地点addressId的下拉列表（进入详细页面时被选中）
	 * @param selectedValue String 选中项值
	 * @return String
	 * @throws QueryException
	 */
	public String getAddressNameOption(String selectedValue) throws QueryException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
				+ " EII.ADDRESS_ID,"
				+ " EO.WORKORDER_OBJECT_NAME"
				+ " FROM"
				+ " AMS_OBJECT_ADDRESS 	AOA,"
				+ " ETS_OBJECT 			EO,"
				+ " ETS_ITEM_INFO 	   	EII"
				+ " WHERE"
				+ " EII.ADDRESS_ID=AOA.ADDRESS_ID"
				+ " AND EO.WORKORDER_OBJECT_NO=AOA.OBJECT_NO"
				+ " AND EII.FINANCE_PROP='DZYH'";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		DatabaseForWeb webFieldProducer = new DatabaseForWeb(sqlModel, conn);
		return webFieldProducer.getOptionHtml(selectedValue);
	}

}
