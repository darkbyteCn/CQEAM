package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AssetsRcvLineModel extends AMSSQLProducer {

	/**
	 * 功能：资产业务行表(EAM) AMS_ASSETS_TRANS_LINE 数据库SQL构造层构造函数
	 *
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
	 */
	public AssetsRcvLineModel(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) throws SQLModelException {
		SQLModel sqlModel = null;
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDataByTransIdModel(dto.getTransId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。
	 * 框架自动生成数据AMS_ASSETS_TRANS_LINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 * @throws SQLModelException
	 */
	private SQLModel getDataByTransIdModel(String transId) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " AATL.TRANS_ID,"
							+ " AATL.LINE_ID,"
							+ " AATL.BARCODE,"
							+ " AATL.NEW_BARCODE,"
							+ " AATL.REMARK,"
							+ " AAAV.ASSET_NUMBER,"
							+ " AAAV.ASSETS_DESCRIPTION,"
							+ " AAAV.MODEL_NUMBER,"
							+ " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
							+ " AAAV.COST,"
							+ " AAAV.DEPRN_COST,"
							+ " AAAV.DEPRECIATION,"
							+ " AAAV.DATE_PLACED_IN_SERVICE,"
							+ " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
							+ " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
							+ " AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,"
							+ " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
							+ " AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
							+ " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
							+ " AATL.OLD_FA_CATEGORY_CODE,"
							+ " AATL.OLD_DEPRECIATION_ACCOUNT,"
							+ " AATL.FA_CATEGORY_CODE,"
							+ " AATL.DEPRECIATION_ACCOUNT,"
							+ " AATL.LINE_TRANS_DATE,"
							+ " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
							+ " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
							+ " AMS_ASSETS_PKG.GET_TO_ORGNIZATION_ID(AATL.LINE_ID) TO_ORGANIZATION_ID,"
							+ " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
							+ " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
							+ " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
							+ " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
							+ " (SELECT"
							+ " AOA.ADDRESS_ID"
							+ " FROM"
							+ " AMS_OBJECT_ADDRESS AOA"
							+ " WHERE"
							+ " AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO"
							+ " AND AOA.BOX_NO = '0000'"
							+ " AND AOA.NET_UNIT = '0000'"
							+ " ) ADDRESS_ID"
							+ " FROM"
							+ " AMS_ASSETS_TRANS_LINE AATL,"
							+ " AMS_MIS_EMPLOYEE      AMEO,"
							+ " AMS_MIS_DEPT          AMDO,"
							+ " ETS_OBJECT            EOO,"
							+ " AMS_MIS_EMPLOYEE      AMEN,"
							+ " AMS_MIS_DEPT          AMDN,"
							+ " ETS_OBJECT            EON,"
							+ " AMS_ASSETS_ADDRESS_V  AAAV"
							+ " WHERE"
							+ " AATL.OLD_LOCATION = EOO.WORKORDER_OBJECT_NO"
							+ " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
							+ " AND AATL.OLD_RESPONSIBILITY_DEPT *= AMDO.DEPT_CODE"
							+ " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
							+ " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
							+ " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
							+ " AND AATL.BARCODE = AAAV.BARCODE"
							+ " AND AATL.ASSIGNED_DATE " + SyBaseSQLUtil.isNullNoParam() + " "
							+ " AND AATL.TRANS_ID = ?";
			sqlArgs.add(transId);
			if (!userAccount.isComAssetsManager()) {
				DTOSet depts = userAccount.getPriviDeptCodes();
				String deptCodes = "''";
				if (depts != null && !depts.isEmpty()) {
					List deptList = depts.toList("deptCode");
					deptCodes = StrUtil.list2String(deptList);
				}
				sqlStr += " AND AATL.RESPONSIBILITY_DEPT IN (" + deptCodes + ")";
			} else {
				sqlStr = sqlStr
						 + " AND EXISTS ("
						 + " SELECT"
						 + " NULL"
						 + " FROM"
						 + " AMS_MIS_DEPT          AMD"
						 + " WHERE"
						 + " AATL.RESPONSIBILITY_DEPT = AMD.DEPT_CODE"
						 + " AND AMD.COMPANY_CODE = ?)";
				sqlArgs.add(userAccount.getCompanyCode());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (DTOException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取资产分配SQL。
	 * @return SQLModel
	 */
	public SQLModel getAssetsAssignModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " SET"
						+ " RESPONSIBILITY_USER = ?,"
						+ " ASSIGNED_TO_LOCATION = ?,"
						+ " RESPONSIBILITY_DEPT = ?,"
						+ " LINE_STATUS = ?,"
						+ " RECEIVED_USER = ?,"
						+ " FA_CATEGORY_CODE = ?,"
						+ " RECEIVED_DATE = GETDATE(),"
						+ " ASSIGNED_DATE = GETDATE()"
						+ " WHERE"
						+ " TRANS_ID = ?"
						+ " AND BARCODE = ?";
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getAssignedToLocation());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(AssetsDictConstant.ORDER_STS_ASSIGNED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getFaCategoryCode());

		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：更新设备表ETS_ITEM_INFO信息
	 * @return SQLModel
	 */
	public SQLModel getAssetsPropUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " ETS_ITEM_INFO"
						+ " SET"
						+ " RESPONSIBILITY_USER = ?,"
						+ " RESPONSIBILITY_DEPT = ?,"
						+ " ORGANIZATION_ID = ?,"
						+ " ADDRESS_ID = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " BARCODE = ?";
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getAddressId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取资产确认SQL
	 * @return SQLModel
	 */
	public SQLModel getAssetsConfirmModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " SET"
						+ " CONFIRM_DATE = GETDATE(),"
						+ " LINE_STATUS = ?"
						+ " WHERE"
						+ " TRANS_ID = ?"
						+ " AND BARCODE = ?";
		sqlArgs.add(AssetsDictConstant.ORDER_STS_CONFIRMD);
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
