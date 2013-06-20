package com.sino.td.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.td.newasset.dto.TdAssetsTransHeaderDTO;

/**
 * <p>Title: AmsAssetsTransHModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransHModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class TdRcvHeaderModel extends TdAssetsTransHeaderModel {

	/**
	 * 功能：资产业务头表(EAM) TD_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdAssetsTransHeaderDTO 本次操作的数据
	 */
	public TdRcvHeaderModel(SfUserDTO userAccount, TdAssetsTransHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取调拨单接收SQL。
	 * @return SQLModel
	 */
	public SQLModel getAssetsReceiveModel() {
		SQLModel sqlModel = new SQLModel();
		TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " TD_ASSETS_TRANS_HEADER AATH"
						+ " SET"
						+ " AATH.RECEIVED_USER = ?,"
						+ " AATH.TRANS_DATE = GETDATE() ,"
						+ " AATH.TRANS_STATUS = ?,"
						+ " AATH.LAST_UPDATE_DATE = GETDATE() ,"
						+ " AATH.LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " AATH.TRANS_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(AssetsDictConstant.ORDER_STS_RECEIVED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取调拨单分配SQL。
	 * @return SQLModel
	 */
	public SQLModel getAssetsAssignModel() {
		SQLModel sqlModel = new SQLModel();
		TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " TD_ASSETS_TRANS_HEADER AATH"
						+ " SET"
						+ " AATH.RECEIVED_USER = ?,"
						+ " AATH.TRANS_DATE = GETDATE() ,"
						+ " AATH.TRANS_STATUS = ?,"
						+ " AATH.LAST_UPDATE_DATE = GETDATE() ,"
						+ " AATH.LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " AATH.TRANS_ID = ?"
						+ " AND EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " TD_TRANS_ASSIGN_V ATAV"
						+ " WHERE"
						+ " AATH.TRANS_ID = ATAV.TRANS_ID"
						+ " AND ATAV.NOT_ASSIGNED_NUMBER = 0)";
		List sqlArgs = new ArrayList();
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(AssetsDictConstant.ORDER_STS_ASSIGNED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：更新设备表ETS_ITEM_INFO信息
	 * @return SQLModel
	 */
	public SQLModel getTmpDistributeModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "INSERT INTO"
						+ " ETS_SYSITEM_DISTRIBUTE("
						+ " SYSTEM_ID,"
						+ " ITEM_CODE,"
						+ " ORGANIZATION_ID,"
						+ " CREATION_DATE,"
						+ " CREATED_BY,"
						+ " IS_TMP)("
						+ " SELECT"
						+ "  NEWID() ,"
						+ " TMP_V.ITEM_CODE,"
						+ " ?,"
						+ " GETDATE() ,"
						+ " ?,"
						+ " ?"
						+ " FROM("
						+ " SELECT DISTINCT"
						+ " EII.ITEM_CODE"
						+ " FROM"
						+ " ETS_ITEM_INFO           EII,"
						+ " TD_ASSETS_TRANS_HEADER AATH,"
						+ " TD_ASSETS_TRANS_LINE   AATL"
						+ " WHERE"
						+ " EII.BARCODE = AATL.BARCODE"
						+ " AND AATL.TRANS_ID = AATH.TRANS_ID"
						+
				" AND (EII.DISABLE_DATE  " + SyBaseSQLUtil.isNullNoParam() + "  OR EII.DISABLE_DATE > GETDATE() )"
						+
						" AND (EII.ITEM_STATUS  " + SyBaseSQLUtil.isNullNoParam() + "  OR EII.ITEM_STATUS = ?)"
						+ " AND AATH.TRANS_ID = ?"
						+ " AND NOT EXISTS ("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " ETS_SYSITEM_DISTRIBUTE ESD"
						+ " WHERE"
						+ " ESD.ITEM_CODE = EII.ITEM_CODE"
						+ " AND ESD.ORGANIZATION_ID = ?)) TMP_V)";
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(AssetsDictConstant.STATUS_YES);
		sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取判断该单据是否能够接收的SQL
	 * @return SQLModel
	 */
	public SQLModel getCanAssignModel() {
		SQLModel sqlModel = new SQLModel();
		TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " TD_ASSETS_TRANS_HEADER AATH"
						+ " WHERE"
						+ " AATH.TRANS_ID = ?"
						+ " AND AATH.TRANS_STATUS = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(AssetsDictConstant.ORDER_STS_ASSIGNED);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取判断当前用户对指定单据是否能够接收的SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getCanReceiveModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			TdAssetsTransHeaderDTO dto = (TdAssetsTransHeaderDTO)
										  dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " COUNT(1) NOT_ASSIGNED_NUMBER"
							+ " FROM"
							+ " TD_ASSETS_TRANS_HEADER AATH,"
							+ " TD_ASSETS_TRANS_LINE AATL"
							+ " WHERE"
							+ " AATH.TRANS_ID = AATL.TRANS_ID"
							+ " AND AATH.TRANS_ID = ?";
			sqlArgs.add(dto.getTransId());
			if (!userAccount.isComAssetsManager()) {
				DTOSet depts = userAccount.getPriviDeptCodes();
				String deptCodes = "";
				if (depts != null && !depts.isEmpty()) {
					List deptList = depts.toList("deptCode");
					deptCodes = StrUtil.list2String(deptList);
				}
				if (deptCodes.equals("")) {
					deptCodes = "''";
				}
				sqlStr = sqlStr
						 + " AND AATL.RESPONSIBILITY_DEPT IN (" + deptCodes +
						 ")"
						 + " AND AATL.ASSIGNED_DATE  " + SyBaseSQLUtil.isNullNoParam() + " ";
			} else {
				sqlStr += " AND EXISTS("
						+ " SELECT"
						+ " NULL"
						+ " FROM"
						+ " AMS_MIS_DEPT AMD"
						+ " WHERE"
						+ " AMD.DEPT_CODE = AATL.RESPONSIBILITY_DEPT"
						+ " AND AMD.COMPANY_CODE = ?)";
				sqlArgs.add(userAccount.getCompanyCode());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (DTOException ex) {
			ex.printLog();
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产业务头表(EAM) AMS_ASSETS_TRANS_H页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			TdAssetsTransHeaderDTO dtoPara = (TdAssetsTransHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AATH.TRANS_ID,"
							+ " AATH.TRANS_NO,"
							+ " AATH.TRANSFER_TYPE,"
							+ " EOCM.COMPANY FROM_COMPANY_NAME,"
							+ " AMD.DEPT_NAME FROM_DEPT_NAME,"
							+ " AATH.APPROVED_DATE,"
							+ " SU.USERNAME CREATED"
							+ " FROM"
							+ " TD_ASSETS_TRANS_HEADER AATH,"
							+ " ETS_OU_CITY_MAP    EOCM,"
							+ " AMS_MIS_DEPT       AMD,"
							+ " SF_USER            SU"
							+ " WHERE"
							+ " AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND AATH.FROM_DEPT *= AMD.DEPT_CODE"
//							+ " AND AATH.FROM_ORGANIZATION_ID = SU.ORGANIZATION_ID"
							+ " AND AATH.CREATED_BY = SU.USER_ID"
							+ " AND AATH.FROM_ORGANIZATION_ID = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, AATH.FROM_ORGANIZATION_ID)))"
							+ " AND (" + SyBaseSQLUtil.isNull() + " OR AATH.FROM_DEPT = dbo.NVL(?, AATH.FROM_DEPT))"
							+ " AND AATH.APPROVED_DATE >= dbo.NVL(?, AATH.APPROVED_DATE)"
							+ " AND AATH.APPROVED_DATE <= dbo.NVL(?, AATH.APPROVED_DATE)"
							+ " AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)"
							+ " AND AATH.TRANS_TYPE = ?"
							+ " AND AATH.TRANSFER_TYPE = dbo.NVL(?, AATH.TRANSFER_TYPE)"
							+ " AND AATH.TRANS_STATUS = ?"
							+ " AND AATH.TRANSFER_TYPE <> ?";

			sqlArgs.add(dtoPara.getFromOrganizationId());
			sqlArgs.add(dtoPara.getFromDept());
			sqlArgs.add(dtoPara.getFromDept());
			sqlArgs.add(dtoPara.getStartDate());
			sqlArgs.add(dtoPara.getSQLEndDate());
			sqlArgs.add(dtoPara.getTransNo());
			sqlArgs.add(dtoPara.getTransType());
			sqlArgs.add(dtoPara.getTransferType());
			sqlArgs.add(dtoPara.getTransStatus());
			sqlArgs.add(AssetsDictConstant.TRANS_INN_DEPT);
			if (!userAccount.isComAssetsManager()) {
				DTOSet depts = userAccount.getPriviDeptCodes();
				String deptCodes = "";
				if (depts != null && !depts.isEmpty()) {
					List deptList = depts.toList("deptCode");
					deptCodes = StrUtil.list2String(deptList);
				}
				if (deptCodes.equals("")) {
					deptCodes = "''";
				}
				sqlStr = sqlStr
						 + " AND EXISTS ("
						 + " SELECT"
						 + " NULL"
						 + " FROM"
						 + " TD_ASSETS_TRANS_LINE AATL"
						 + " WHERE"
						 + " AATL.TRANS_ID = AATH.TRANS_ID"
						 + " AND AATL.RESPONSIBILITY_DEPT IN (" + deptCodes +
						 "))";
			} else {
				sqlStr = sqlStr + " AND AATH.TO_ORGANIZATION_ID = ?";
				sqlArgs.add(userAccount.getOrganizationId());
			}
			sqlStr += " ORDER BY AATH.TRANS_NO";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		} catch (DTOException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
