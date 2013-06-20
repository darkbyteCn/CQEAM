package com.sino.ams.newasset.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-7-14
 * Time: 上午8:24
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemTransLineModel extends AMSSQLProducer {

	/**
	 * 功能：AMS_ASSETS_TRANS_LINE 数据库SQL构造层构造函数
	 * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
	 */
	public AmsItemTransLineModel(SfUserDTO userAccount, AmsAssetsTransLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成AMS_ASSETS_TRANS_LINE数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
			String sqlStr = "INSERT INTO"
							+ " AMS_ASSETS_TRANS_LINE("
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
                            + " RETIREMENT_COST," +
                              " REJECT_TYPE"
                            + ") VALUES ("
							+ " newid() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

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
			
			if (dto.getLineTransDate().toString().trim().equals("") || dto.getLineTransDate().toString().indexOf("1900") > 0) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getLineTransDate());
			}
			
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
            sqlArgs.add(dto.getRejectType());
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成AMS_ASSETS_TRANS_LINE数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " AMS_ASSETS_TRANS_LINE"
						+ " WHERE"
						+ " LINE_ID = ?";
		sqlArgs.add(dto.getLineId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造查询数据SQL。
	 * 框架自动生成数据AMS_ASSETS_TRANS_LINE详细信息查询SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		String transType = dto.getTransType();
			sqlStr = "SELECT AATL.TRANS_ID,\n" +
                    "       AATL.LINE_ID,\n" +
                    "       AATL.BARCODE,\n" +
                    "       AATL.NEW_BARCODE,\n" +
                    "       AATL.REMARK,\n" +
                    "       AATL.LINE_REASON,\n" +
                    "       ''ASSET_NUMBER,\n" +
                    "       ESI.ITEM_NAME ASSETS_DESCRIPTION,\n" +
                    "       ESI.ITEM_SPEC MODEL_NUMBER,\n" +
                    "       1  CURRENT_UNITS,\n" +
                    "       '' VENDOR_NAME,\n" +
                    "       '' UNIT_OF_MEASURE,\n" +
                    "       '' COST,\n" +
                    "       '' DATE_PLACED_IN_SERVICE,\n" +
                    "       '' LIFE_IN_YEARS,\n" +
                    "       EOO.WORKORDER_OBJECT_NO OLD_LOCATION,\n" +
                    "       EOO.WORKORDER_OBJECT_NAME OLD_LOCATION_NAME,\n" +
                    "       AMEO.EMPLOYEE_ID OLD_RESPONSIBILITY_USER,\n" +
                    "       AMEO.USER_NAME OLD_RESPONSIBILITY_USER_NAME,\n" +
                    "       AMDO.DEPT_CODE OLD_RESPONSIBILITY_DEPT,\n" +
                    "       AMDO.DEPT_NAME OLD_RESPONSIBILITY_DEPT_NAME,\n" +
                    "       EON.WORKORDER_OBJECT_NO ASSIGNED_TO_LOCATION,\n" +
                    "       EON.WORKORDER_OBJECT_NAME ASSIGNED_TO_LOCATION_NAME,\n" +
                    "       AMEN.EMPLOYEE_ID RESPONSIBILITY_USER,\n" +
                    "       AMEN.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
                    "       AMDN.DEPT_CODE RESPONSIBILITY_DEPT,\n" +
                    "       AMDN.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
                    "       dbo.AASP_GET_TO_ORGNIZATION_ID(AATL.LINE_ID) TO_ORGANIZATION_ID,\n" +
                    "       AATL.OLD_FA_CATEGORY_CODE,\n" +
                    "       AATL.OLD_DEPRECIATION_ACCOUNT,\n" +
                    "       AATL.FA_CATEGORY_CODE,\n" +
                    "       AATL.DEPRECIATION_ACCOUNT,\n" +
                    "       AATL.LINE_TRANS_DATE,\n" +
                    "       AATL.NET_UNIT,\n" +
                    "       AATL.ASSET_ID,\n" +
                    "       AATL.SOFT_INUSE_VERSION,\n" +
                    "       AATL.SOFT_DEVALUE_VERSION,\n" +
                    "       AATL.DEPRECIATION,\n" +
                    "       AATL.DEPRN_COST,\n" +
                    "       AATL.RETIREMENT_COST,\n" +
                    "       AATL.IMPAIR_RESERVE,\n" +
                    "       AATL.MANUFACTURER_NAME,\n" +
                    "       AATL.PREPARE_DEVALUE,\n" +
                    "       (SELECT AOA.ADDRESS_ID\n" +
                    "          FROM AMS_OBJECT_ADDRESS AOA\n" +
                    "         WHERE AOA.OBJECT_NO = EON.WORKORDER_OBJECT_NO\n" +
                    "           AND AOA.BOX_NO = '0000'\n" +
                    "           AND AOA.NET_UNIT = '0000') ADDRESS_ID,\n" +
                    "       AATL.REJECT_TYPE,\n" +
                    "       dbo.APP_GET_FLEX_VALUE(AATL.REJECT_TYPE, 'DIS_TYPE') REJECT_TYPE_NAME\n" +
                    "  FROM AMS_ASSETS_TRANS_LINE AATL,\n" +
                    "       AMS_MIS_EMPLOYEE      AMEO,\n" +
                    "       AMS_MIS_DEPT          AMDO,\n" +
                    "       ETS_OBJECT            EOO,\n" +
                    "       AMS_MIS_EMPLOYEE      AMEN,\n" +
                    "       AMS_MIS_DEPT          AMDN,\n" +
                    "       ETS_OBJECT            EON,\n" +
                    "       ETS_ITEM_INFO  EII,\n" +
                    "       ETS_SYSTEM_ITEM ESI \n" +
                    " WHERE AATL.OLD_LOCATION *= EOO.WORKORDER_OBJECT_NO\n" +
                    "   AND AATL.OLD_RESPONSIBILITY_USER *= AMEO.EMPLOYEE_ID\n" +
                    "   AND AATL.OLD_RESPONSIBILITY_DEPT = AMDO.DEPT_CODE\n" +
                    "   AND AATL.ASSIGNED_TO_LOCATION *= EON.WORKORDER_OBJECT_NO\n" +
                    "   AND AATL.RESPONSIBILITY_USER *= AMEN.EMPLOYEE_ID\n" +
                    "   AND AATL.RESPONSIBILITY_DEPT *= AMDN.DEPT_CODE\n" +
                    "   AND AATL.BARCODE = EII.BARCODE\n" +
                    "   AND EII.ITEM_CODE=ESI.ITEM_CODE\n" +
                    "   AND AATL.TRANS_ID = ?";
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
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		if (foreignKey.equals("transId")) {
			sqlModel = getDataByTransIdModel(dto.getTransId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 transId 构造数据删除SQL。
	 * 框架自动生成数据AMS_ASSETS_TRANS_LINE数据删除SQLModel，请根据实际需要修改。
	 * @param transId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByTransIdModel(String transId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
						+ " FROM"
						+ " AMS_ASSETS_TRANS_LINE"
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
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
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
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
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
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE  "
						+ " SET"
						+ " LINE_STATUS = ?"
						+ " WHERE"
						+ " TRANS_ID = ?";
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
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE   AATL"
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
		AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_TRANS_LINE   "
						+ " SET"
						+ " RESPONSIBILITY_USER = dbo.NVL(?,RESPONSIBILITY_USER),"
						+ " ASSIGNED_TO_LOCATION = dbo.NVL(?, ASSIGNED_TO_LOCATION)"
//						+ " DEPRECIATION_ACCOUNT = dbo.NVL(?, AATL.DEPRECIATION_ACCOUNT),"
//						+ " AATL.FA_CATEGORY_CODE = dbo.NVL(?, AATL.FA_CATEGORY_CODE)"
						+ " WHERE"
						+ " TRANS_ID = ?"
						+ " AND BARCODE = ?";
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getAssignedToLocation());
//		sqlArgs.add(dto.getDepreciationAccount());
//		sqlArgs.add(dto.getFaCategoryCode());
		sqlArgs.add(dto.getTransId());
		sqlArgs.add(dto.getBarcode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
