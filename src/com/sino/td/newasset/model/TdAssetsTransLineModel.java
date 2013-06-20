package com.sino.td.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;


/**
 * <p>Title: AmsAssetsTransLineModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransLineModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class TdAssetsTransLineModel extends AMSSQLProducer {

	/**
	 * 功能：TD_ASSETS_TRANS_LINE 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter TdAssetsTransLineDTO 本次操作的数据
	 */
	public TdAssetsTransLineModel(SfUserDTO userAccount, TdAssetsTransLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成TD_ASSETS_TRANS_LINE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
			String sqlStr = "INSERT INTO"
							+ " TD_ASSETS_TRANS_LINE("
							+ " LINE_ID,"
							+ " TRANS_ID,"
							+ " BARCODE,"
							+ " OLD_LOCATION,"
							+ " OLD_RESPONSIBILITY_USER,"
							+ " OLD_RESPONSIBILITY_DEPT,"
							+ " OLD_DEPRECIATION_ACCOUNT,"
							+ " OLD_FA_CATEGORY_CODE,"
							+ " ASSIGNED_TO_LOCATION,"
							+ " RESPONSIBILITY_USER,"
							+ " RESPONSIBILITY_DEPT,"
							+ " DEPRECIATION_ACCOUNT,"
							+ " FA_CATEGORY_CODE,"
							+ " LINE_STATUS,"
							+ " LINE_TRANS_DATE,"
							+ " LINE_REASON, "
							+ " REMARK,"
							+ " NET_UNIT,"
							+ " ASSET_ID,"
							+ " SOFT_INUSE_VERSION,"
							+ " SOFT_DEVALUE_VERSION,"
							+ " DEPRECIATION,"
							+ " DEPRN_COST,"
                            + " IMPAIR_RESERVE,"
                            + " MANUFACTURER_NAME,"
                            + " PREPARE_DEVALUE,"
                            + " RETIREMENT_COST"
                            + ") VALUES ("
							+ " NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			sqlArgs.add(dto.getTransId());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getOldLocation());
			sqlArgs.add(dto.getOldResponsibilityUser());
			sqlArgs.add(dto.getOldResponsibilityDept());
			sqlArgs.add(dto.getOldDepreciationAccount());
			sqlArgs.add(dto.getOldFaCategoryCode());
			sqlArgs.add(dto.getAssignedToLocation());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getDepreciationAccount());
			sqlArgs.add(dto.getFaCategoryCode());
			sqlArgs.add(dto.getLineStatus());
			sqlArgs.add(dto.getLineTransDate());
			sqlArgs.add(dto.getLineReason());
			sqlArgs.add(dto.getRemark());
			sqlArgs.add(dto.getNetUnit());
			sqlArgs.add(dto.getAssetId());
			sqlArgs.add(dto.getSoftInuseVersion());
			sqlArgs.add(dto.getSoftDevalueVersion());
			sqlArgs.add(dto.getDepreciation());
			sqlArgs.add(dto.getDeprnCost());
            sqlArgs.add(dto.getImpairReserve());
            sqlArgs.add(dto.getManufacturerName());
            sqlArgs.add(dto.getPrepareDevalue());
            sqlArgs.add(dto.getRetirementCost());
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成TD_ASSETS_TRANS_LINE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " TD_ASSETS_TRANS_LINE"
						+ " WHERE"
						+ " LINE_ID = ?";
		sqlArgs.add(dto.getLineId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。
	 * 框架自动生成数据TD_ASSETS_TRANS_LINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		String transType = dto.getTransType();
			sqlStr = "SELECT"
					 + " AATL.TRANS_ID,"
					 + " AATL.LINE_ID,"
					 + " AATL.BARCODE,"
					 + " AATL.NEW_BARCODE,"
					 + " AAAV.ITEM_NAME ASSETS_DESCRIPTION,"
					 + " AAAV.ITEM_SPEC MODEL_NUMBER,"
					 + " AATL.REMARK,"
					 + " AATL.LINE_REASON,"
					 + " AAAV.ASSET_NUMBER,"
					 + " AAAV.ASSETS_DESCRIPTION,"
					 + " AAAV.MODEL_NUMBER,"
					 + " ISNULL(AAAV.CURRENT_UNITS, 1) CURRENT_UNITS,"
					 + " AAAV.VENDOR_NAME,"
					 + " AAAV.UNIT_OF_MEASURE,"
					 + " AAAV.COST,"
					 + " AAAV.DEPRN_COST,"
					 + " AAAV.DEPRECIATION,"
					 + " AAAV.DATE_PLACED_IN_SERVICE,"
					 + " AAAV.LIFE_IN_YEARS,"
					 + " EOO.WORKORDER_OBJECT_NO OLD_LOCATION,"
					 + " EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,"
					 + " AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,"
					 + " AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,"
					 + " AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,"
					 + " AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,"
					 + " EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,"
					 + " EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,"
					 + " AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,"
					 + " AMEN.USER_NAME RESPONSIBILITY_USER_NAME,"
					 + " AMDN.DEPT_CODE RESPONSIBILITY_DEPT,"
					 + " AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
					 + " AMS_ASSETS_PKG.GET_TO_ORGNIZATION_ID(AATL.LINE_ID) TO_ORGANIZATION_ID,"
					 + " AATL.OLD_FA_CATEGORY_CODE,"
					 + " AATL.OLD_DEPRECIATION_ACCOUNT,"
					 + " AATL.FA_CATEGORY_CODE,"
					 + " AATL.DEPRECIATION_ACCOUNT,"
					 + " AATL.LINE_TRANS_DATE,"
					 + " AATL.NET_UNIT,"
					 + " AATL.ASSET_ID,"
					 + " AATL.SOFT_INUSE_VERSION,"
					 + " AATL.SOFT_DEVALUE_VERSION,"
					 + " AATL.DEPRECIATION,"
					 + " AATL.DEPRN_COST,"
                     + " AATL.RETIREMENT_COST,"
                     + " AATL.IMPAIR_RESERVE,"
                     + " AATL.MANUFACTURER_NAME,"
                     + " AATL.PREPARE_DEVALUE,"
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
					 + " TD_ASSETS_TRANS_LINE AATL,"
					 + " AMS_MIS_EMPLOYEE      AMEO,"
					 + " AMS_MIS_DEPT          AMDO,"
					 + " ETS_OBJECT            EOO,"
					 + " AMS_MIS_EMPLOYEE      AMEN,"
					 + " AMS_MIS_DEPT          AMDN,"
					 + " ETS_OBJECT            EON,"
					 + " TD_ASSETS_ADDRESS_V  AAAV"
					 + " WHERE"
					 + " AATL.OLD_LOCATION *= EOO.WORKORDER_OBJECT_NO"
					 + " AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID"
					 + " AND AATL.OLD_RESPONSIBILITY_DEPT = AMDO.DEPT_CODE"
					 + " AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO"
					 + " AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID"
					 + " AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE"
					 + " AND AATL.BARCODE = AAAV.BARCODE"
					 + " AND AATL.TRANS_ID = ?";
		sqlArgs.add(transId);
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
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDataByTransIdModel(dto.getTransId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造数据删除SQL。
	 * 框架自动生成数据TD_ASSETS_TRANS_LINE数据删除SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
						+ " FROM"
						+ " TD_ASSETS_TRANS_LINE"
						+ " WHERE"
						+ " TRANS_ID = ?";
		sqlArgs.add(transId);
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
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDeleteByTransIdModel(dto.getTransId());
		}
		return sqlModel;
	}

	/**
	 * 功能：获取判断行条码是否被保留
	 * @return SQLModel
	 */
	public SQLModel getHasReservedModel() {
		SQLModel sqlModel = new SQLModel();
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_RESERVED AAR"
						+ " WHERE"
						+ " AAR.BARCODE = ?";
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取判断行条码是否被保留
	 * @return SQLModel
	 */
	public SQLModel getCancelLinesByHeaderModel() {
		SQLModel sqlModel = new SQLModel();
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " TD_ASSETS_TRANS_LINE   AATL"
						+ " SET"
						+ " AATL.LINE_STATUS = ?"
						+ " WHERE"
						+ " AATL.TRANS_ID = ?";
		sqlArgs.add(AssetsDictConstant.CANCELED);
		sqlArgs.add(dto.getTransId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取调拨单行折旧费用账户更新SQL(2008-12-01 17:43)
	 * @return SQLModel
	 */
	public SQLModel getAccountUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " TD_ASSETS_TRANS_LINE   AATL"
						+ " SET"
						+ " AATL.DEPRECIATION_ACCOUNT = dbo.NVL(?, AATL.DEPRECIATION_ACCOUNT)"
						+ " WHERE"
						+ " AATL.TRANS_ID = ?"
						+ " AND AATL.BARCODE = ?";
		sqlArgs.add(dto.getDepreciationAccount());
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getTransLineUpdateModel(){
		SQLModel sqlModel = new SQLModel();
		TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " TD_ASSETS_TRANS_LINE   AATL"
						+ " SET"
						+ " AATL.RESPONSIBILITY_USER = dbo.NVL(?, AATL.RESPONSIBILITY_USER),"
						+ " AATL.ASSIGNED_TO_LOCATION = dbo.NVL(?, AATL.ASSIGNED_TO_LOCATION),"
						+ " AATL.DEPRECIATION_ACCOUNT = dbo.NVL(?, AATL.DEPRECIATION_ACCOUNT),"
						+ " AATL.FA_CATEGORY_CODE = dbo.NVL(?, AATL.FA_CATEGORY_CODE)"
						+ " WHERE"
						+ " AATL.TRANS_ID = ?"
						+ " AND AATL.BARCODE = ?";
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getAssignedToLocation());
		sqlArgs.add(dto.getDepreciationAccount());
		sqlArgs.add(dto.getFaCategoryCode());
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
