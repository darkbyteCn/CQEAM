package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.SQLModelException;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsAssignModel extends AMSSQLProducer {

	public AssetsAssignModel(SfUserDTO userAccount,
							 AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：返回页面翻页查询时所需要的SQLModel
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String assProp = dto.getAssProp();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " *"
						+ " FROM"
						+ " AMS_ASSETS_ADDRESS_V AAAV"
						+ " WHERE"
						+ " AAAV.ORGANIZATION_ID = ?"
						+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
						+
				" AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
						+
				" AND AAAV.WORKORDER_OBJECT_NO = dbo.NVL(?, AAAV.WORKORDER_OBJECT_NO)"
						+
				" AND (AAAV.ITEM_STATUS " + SyBaseSQLUtil.isNullNoParam() + "  OR AAAV.ITEM_STATUS = ?)"
						+ " AND  " + SyBaseSQLUtil.isNotNull("AAAV.ASSET_ID") + " ";
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getAssetsDescription());
		sqlArgs.add(dto.getWorkorderObjectNo());
		sqlArgs.add(AssetsDictConstant.ITEM_STATUS_NORMAL);
		String assignProp = dto.getAssignProp();
		if (assignProp.equals(AssetsDictConstant.ASSIGNED_BOTH_NOT)) {
			sqlStr = sqlStr
					 + " AND AAAV.RESPONSIBILITY_USER " + SyBaseSQLUtil.isNullNoParam() + " "
					 + " AND AAAV.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " ";
		} else if (assignProp.equals(AssetsDictConstant.ASSIGNED_PERSON_NOT)) {
			sqlStr += " AND AAAV.RESPONSIBILITY_USER " + SyBaseSQLUtil.isNullNoParam() + " ";
		} else if (assignProp.equals(AssetsDictConstant.ASSIGNED_DEPT_NOT)) {
			sqlStr += " AND AAAV.DEPT_CODE " + SyBaseSQLUtil.isNullNoParam() + " ";
		} else if (assignProp.equals(AssetsDictConstant.ASSIGNED_PERSON_YES)) {
			sqlStr += " AND  " + SyBaseSQLUtil.isNotNull("AAAV.RESPONSIBILITY_USER") + " ";
		} else if (assignProp.equals(AssetsDictConstant.ASSIGNED_DEPT_YES)) {
			sqlStr += " AND  " + SyBaseSQLUtil.isNotNull("AAAV.DEPT_CODE") + " ";
		} else if (assignProp.equals(AssetsDictConstant.ASSIGNED_BOTH_YES)) {
			sqlStr = sqlStr
					 + " AND  " + SyBaseSQLUtil.isNotNull("AAAV.RESPONSIBILITY_USER") + " "
					 + " AND  " + SyBaseSQLUtil.isNotNull("AAAV.DEPT_CODE") + " ";
		}
		if (assProp.equals(AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER)) {
			if (!userAccount.isComAssetsManager()) {
				String deptCode = "'";
				DTOSet depts = userAccount.getPriviDeptCodes();
				AmsMisDeptDTO queryDTO = null;
				
				if( null != depts ){
					int deptCount = depts.getSize();
					for (int i = 0; i < deptCount; i++) {
						queryDTO = (AmsMisDeptDTO) depts.getDTO(i);
						deptCode += queryDTO.getDeptCode() + "'";
						if (i < deptCount - 1) {
							deptCode += ", '";
						}
					}
				}
				sqlStr += " AND AAAV.DEPT_CODE IN (" + deptCode + ")";
			}
			sqlStr += " AND AAAV.DEPT_CODE = ?";
			sqlArgs.add(dto.getResponsibilityDept());
		} else if (assProp.equals(AssetsWebAttributes.ASSIGN_MAINTAIN_USER)) {
			sqlStr += " AND AAAV.RESPONSIBILITY_USER = ?";
			sqlArgs.add(userAccount.getEmployeeId());
		}
		sqlStr = sqlStr
				 + " AND NOT EXISTS("
				 + " SELECT"
				 + " NULL"
				 + " FROM"
				 + " AMS_ASSETS_RESERVED AAR"
				 + " WHERE"
				 + " AAR.BARCODE = AAAV.BARCODE)";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：获取资产分配SQL。
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getAssetsAssignModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String assProp = dto.getAssProp();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		if (assProp.equals(AssetsWebAttributes.ASSIGN_COST_CENTER)) {
			sqlStr = "UPDATE"
					 + " ETS_ITEM_INFO EII"
					 + " SET"
					 + " EII.RESPONSIBILITY_DEPT = ?,"
					 + " EII.LAST_UPDATE_DATE = GETDATE(),"
					 + " EII.LAST_UPDATE_BY = ?"
					 + " WHERE"
					 + " EII.BARCODE = ?";
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getBarcode());
		} else if (assProp.equals(AssetsWebAttributes.ASSIGN_RESPONSIBLE_USER)) {
			sqlStr = "UPDATE"
					 + " ETS_ITEM_INFO EII"
					 + " SET"
					 + " EII.RESPONSIBILITY_USER = ?,"
					 + " EII.LAST_UPDATE_DATE = GETDATE(),"
					 + " EII.LAST_UPDATE_BY = ?"
					 + " WHERE"
					 + " EII.BARCODE = ?"
					 + " AND EII.RESPONSIBILITY_DEPT = ?";
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getResponsibilityDept());
		} else if (assProp.equals(AssetsWebAttributes.ASSIGN_MAINTAIN_USER)) {
			sqlStr = "UPDATE"
					 + " ETS_ITEM_INFO EII"
					 + " SET"
					 + " EII.MAINTAIN_USER = ?,"
					 + " EII.LAST_UPDATE_DATE = GETDATE(),"
					 + " EII.LAST_UPDATE_BY = ?"
					 + " WHERE"
					 + " EII.BARCODE = ?"
					 + " AND EII.RESPONSIBILITY_USER = ?";
			sqlArgs.add(dto.getMaintainUser());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(userAccount.getEmployeeId());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
