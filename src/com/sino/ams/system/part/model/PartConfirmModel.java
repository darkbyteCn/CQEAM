package com.sino.ams.system.part.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.part.dto.PartConfirmDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-28
 * Time: 9:33:32
 * To change this template use File | Settings | File Templates.
 */
public class PartConfirmModel extends AMSSQLProducer {
	private PartConfirmDTO dto = null;

	/**
	 * 功能：设备分类表(EAM) ETS_SYSTEM_ITEM 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EtsSystemItemDTO 本次操作的数据
	 */
	public PartConfirmModel(SfUserDTO userAccount, PartConfirmDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto = (PartConfirmDTO) dtoParameter;
	}

	/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "INSERT INTO "
							+ " ETS_SYSTEM_ITEM("
							+ " ITEM_CODE,"
							+ " ITEM_NAME,"
							+ " ITEM_SPEC,"
							+ " ITEM_CATEGORY,"
							+ " MIS_ITEM_CODE,"
							+ " ENABLED,"
							+ " MEMO,"
							+ " YEARS,"
							+ " ITEM_UNIT,"
							+ " VENDOR_ID,"
							+ " IS_FIXED_ASSETS,"
							+ " CREATION_DATE,"
							+ " CREATED_BY,"
							+ " LAST_UPDATE_DATE,"
							+ " LAST_UPDATE_BY,"
							+ " MASTER_ORGANIZATION_ID"
							+ ") VALUES ("
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			sqlArgs.add(dto.getItemCode());
			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getMisItemCode());
			sqlArgs.add(dto.getEnabled());
			sqlArgs.add(dto.getMemo());
			sqlArgs.add(dto.getYears());
			sqlArgs.add(dto.getItemUnit());
			sqlArgs.add(dto.getVendorId());
			sqlArgs.add(dto.getFixedAssets());

			sqlArgs.add(dto.getCreationDate());

			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getLastUpdateDate());
			sqlArgs.add(dto.getLastUpdateBy());
			sqlArgs.add(dto.getMasterOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException e) {
			e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
		}
		return sqlModel;
	}

/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {

		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "UPDATE ETS_SYSTEM_ITEM"
							+ " SET"
							+ " ITEM_NAME = ?,"
							+ " ITEM_SPEC = ?,"
							+ " ITEM_CATEGORY = ?,"
							+ " MIS_ITEM_CODE = ?,"
							+ " ENABLED = ?,"
							+ " MEMO = ?,"
							+ " YEARS = ?,"
							+ " ITEM_UNIT = ?,"
							+ " VENDOR_ID = ?,"
							+ " IS_FIXED_ASSETS = ?,"
							+ " CREATION_DATE = ?,"
							+ " CREATED_BY = ?,"
							+ " LAST_UPDATE_DATE = ?,"
							+ " LAST_UPDATE_BY = ?,"
							+ " MASTER_ORGANIZATION_ID = ?"
							+ " WHERE"
							+ " ITEM_CODE = ?";

			sqlArgs.add(dto.getItemName());
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getMisItemCode());
			sqlArgs.add(dto.getEnabled());
			sqlArgs.add(dto.getMemo());
			sqlArgs.add(dto.getYears());
			sqlArgs.add(dto.getItemUnit());
			sqlArgs.add(dto.getVendorId());
			sqlArgs.add(dto.getFixedAssets());
			sqlArgs.add(dto.getCreationDate());
			sqlArgs.add(dto.getCreatedBy());
			sqlArgs.add(dto.getLastUpdateDate());
			sqlArgs.add(dto.getLastUpdateBy());
			sqlArgs.add(dto.getMasterOrganizationId());
			sqlArgs.add(dto.getItemCode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException e) {
			e.printStackTrace();
		}
		return sqlModel;

	}

/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " ETS_SYSTEM_ITEM"
						+ " WHERE"
						+ " ITEM_CODE = ?";
		sqlArgs.add(dto.getItemCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() { //点明细用的SQL
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " EMPV.VENDOR_NAME,"
						+ " ESI.ITEM_CODE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " ESI.ITEM_CATEGORY,"
						+ " ESI.MIS_ITEM_CODE,"
						+ " ESI.ENABLED,"
						+ " ESI.MEMO,"
						+ " ESI.YEARS,"
						+ " ESI.ITEM_UNIT,"
						+ " ESI.VENDOR_ID,"
						+ " ESI.IS_FIXED_ASSETS,"
						+ " ESI.CREATION_DATE,"
						+ " ESI.CREATED_BY,"
						+ " ESI.LAST_UPDATE_DATE,"
						+ " ESI.LAST_UPDATE_BY,"
						+ " ESI.MASTER_ORGANIZATION_ID"
						+ " FROM "
						+ " ETS_SYSTEM_ITEM ESI "
						+ " LEFT JOIN "
						+ " ETS_MIS_PO_VENDORS EMPV ON EMPV.VENDOR_ID = ESI.VENDOR_ID"
						+ " WHERE "
						+ " ITEM_CODE = ?";
		sqlArgs.add(dto.getItemCode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getDataMuxModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " EFV.VALUE ITEM_TYPE,"
						+ " EFV2.VALUE ITEM_UNIT,"
						+ " ESI.ITEM_CODE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " ESI.ITEM_CATEGORY,"
						+ " ESI.MIS_ITEM_CODE,"
						+ " ESI.ENABLED,"
						+ " ESI.MEMO,"
						+ " ESI.YEARS,"
						+ " ESI.VENDOR_ID,"
						+ " ESI.IS_FIXED_ASSETS,"
						+ " ESI.CREATION_DATE,"
						+ " ESI.CREATED_BY"
						+ " FROM"
						+ " ETS_SYSTEM_ITEM ESI,"
						+ " ETS_FLEX_VALUES EFV,"
						+ " ETS_FLEX_VALUES EFV2 "
						+ " WHERE"
						+ " ESI.ITEM_CATEGORY = EFV.CODE "
						+ " AND ESI.ITEM_UNIT  = EFV2.CODE"
						+ " AND EFV2.FLEX_VALUE_SET_ID = '19'"
						+ " AND EFV.FLEX_VALUE_SET_ID = '1'"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.MIS_ITEM_CODE LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.VENDOR_ID LIKE ?)";
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemCategory());
		sqlArgs.add(dto.getItemCategory());
		sqlArgs.add(dto.getMisItemCode());
		sqlArgs.add(dto.getMisItemCode());
		sqlArgs.add(dto.getVendorId());
		sqlArgs.add(dto.getVendorId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
						+ " " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, ?) ITEM_TYPE,"
						+ " ESI.ITEM_UNIT,"
						+ " EMPV.VENDOR_ID,"
						+ " EMPV.VENDOR_NAME,"
						+ " ESI.ITEM_CODE||';'||ESI.ITEM_NAME||';'||ESI.ITEM_SPEC||';'||ESI.ITEM_CATEGORY||';'||ESI.ITEM_UNIT||';'|| CONVERT( VARCHAR, ESI.VENDOR_ID) ||';'|| CONVERT( VARCHAR, ESI.CREATED_BY) ||';'|| CONVERT( VARCHAR, ESI.CREATION_DATE) ITEMS,"
						+ " ESI.ITEM_CODE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " ESI.ITEM_CATEGORY,"
						+ " ESI.MIS_ITEM_CODE,"
						+ " ESI.ENABLED,"
						+ " ESI.MEMO,"
						+ " ESI.YEARS,"
						+ " ESI.IS_FIXED_ASSETS,"
						+ " ESI.CREATION_DATE,"
						+ " ESI.CREATED_BY"
						+ " FROM "
						+ " ETS_SYSTEM_ITEM    ESI"
						+ " LEFT JOIN "
						+ " ETS_MIS_PO_VENDORS EMPV "
						+ " ON ESI.VENDOR_ID = EMPV.VENDOR_ID "
						+ " WHERE"
						+ " ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_CATEGORY LIKE ? )"
						+ " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_NAME LIKE ? )"
						+ " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR EMPV.VENDOR_NAME LIKE ?)"
						+ " AND ( " + SyBaseSQLUtil.nullStringParam() + " OR ESI.ITEM_SPEC LIKE ?)"
						+ " AND ESI.IS_TMP_CODE = 'Y'";
		
		sqlArgs.add(DictConstant.ITEM_TYPE); 
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemCategory() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemName() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getVendorName() );
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemSpec() );
		
//		sqlArgs.add(dto.getItemCategory());
//		sqlArgs.add(dto.getItemName());
//		sqlArgs.add(dto.getVendorName());
//		sqlArgs.add(dto.getVendorName());
//		sqlArgs.add(dto.getItemSpec());
//		sqlArgs.add(dto.getItemSpec());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getVerifyModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = " SELECT"
						+ " ESI.* "
						+ " FROM"
						+ " ETS_SYSTEM_ITEM ESI "
						+ " WHERE"
						+ " ESI.ITEM_NAME = ? ";
		sqlArgs.add(dto.getItemName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行批量确认操作。
	 * @param  itemCodes String[]
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel confirmModel(String[] itemCodes) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String Codes = ArrUtil.arrToSqlStr(itemCodes);
		String sqlStr = "UPDATE "
						+ " ETS_SYSTEM_ITEM"
						+ " SET"
						+ " IS_TMP_CODE = 'N'"
						+ " WHERE"
						+ " ITEM_CODE IN (" + Codes + ")";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getSpareQueryModel() { //查询设备分配的SQL（查询页面）
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		if ((userAccount.isProvinceUser()) || (userAccount.isSysAdmin())) { //省公司用户
			sqlStr = "SELECT " +
					 "  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, ?) ITEM_TYPE," +
					 "  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_UNIT, ?) ITEM_UNIT," +
					 "  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME," +
					 " ESI.VENDOR_ID," +
					 " ESI.ITEM_CODE," +
					 " ESI.ITEM_NAME," +
					 " ESI.ITEM_SPEC," +
					 " ESI.ITEM_CATEGORY," +
					 " ESI.MIS_ITEM_CODE," +
					 " ESI.ENABLED," +
					 " ESI.MEMO," +
					 " ESI.YEARS," +
					 " ESI.IS_FIXED_ASSETS," +
					 " ESI.CREATION_DATE," +
					 " ESI.CREATED_BY" +
					 " FROM " +
					 " ETS_SYSTEM_ITEM ESI" +
					 " WHERE ESI.IS_TMP_CODE = 'N'" +
					 " AND EXISTS (SELECT 1" +
					 " FROM ETS_SYSITEM_DISTRIBUTE ESD" +
					 " WHERE ESD.IS_TMP = 'Y'" +
					 " AND ESI.ITEM_CODE = ESD.ITEM_CODE )" +
					 
					 " AND (" + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_NAME LIKE ?)" +
					 " AND (" + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_CATEGORY LIKE ?)" +
					 " AND (" + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_SPEC LIKE ?)" +
					 " AND (" + SyBaseSQLUtil.nullIntParam() + "  OR ESI.VENDOR_ID LIKE ?)";
			sqlArgs.add(DictConstant.ITEM_TYPE);
			sqlArgs.add(DictConstant.UNIT_OF_MEASURE);
			
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemName() );
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemCategory() );
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemSpec() );
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getVendorId() );
			
//			sqlArgs.add(dto.getItemName());
//			sqlArgs.add(dto.getItemName());
//			sqlArgs.add(dto.getItemCategory());
//			sqlArgs.add(dto.getItemCategory());
//			sqlArgs.add(dto.getItemSpec());
//			sqlArgs.add(dto.getItemSpec());
//			sqlArgs.add(dto.getVendorId());
//			sqlArgs.add(dto.getVendorId());
		} else { //地市公司管理员
			sqlStr = "SELECT " +
					 "  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, ?) ITEM_TYPE," +
					 "  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_FLEX_VALUE(ESI.ITEM_UNIT, ?) ITEM_UNIT," +
					 "  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_VENDOR_NAME(ESI.VENDOR_ID) VENDOR_NAME," +
					 " ESI.VENDOR_ID," +
					 " ESI.ITEM_CODE," +
					 " ESI.ITEM_NAME," +
					 " ESI.ITEM_SPEC," +
					 " ESI.ITEM_CATEGORY," +
					 " ESI.MIS_ITEM_CODE," +
					 " ESI.ENABLED," +
					 " ESI.MEMO," +
					 " ESI.YEARS," +
					 " ESI.IS_FIXED_ASSETS," +
					 " ESI.CREATION_DATE," +
					 " ESI.CREATED_BY" +
					 " FROM " +
					 " ETS_SYSTEM_ITEM ESI" +
					 " WHERE ESI.IS_TMP_CODE = 'N'" +
					 " AND EXISTS (SELECT 1" +
					 " FROM ETS_SYSITEM_DISTRIBUTE ESD" +
					 " WHERE ESD.IS_TMP = 'Y'" +
					 " AND ESI.ITEM_CODE = ESD.ITEM_CODE AND ESD.ORGANIZATION_ID = ?)" +
					 " AND (" + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_NAME LIKE ?)" +
					 " AND (" + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_CATEGORY LIKE ?)" +
					 " AND (" + SyBaseSQLUtil.nullStringParam() + "  OR ESI.ITEM_SPEC LIKE ?)" +
					 " AND (" + SyBaseSQLUtil.nullIntParam() + "  OR ESI.VENDOR_ID LIKE ?)";
			sqlArgs.add(DictConstant.ITEM_TYPE);
			sqlArgs.add(DictConstant.UNIT_OF_MEASURE);
			sqlArgs.add(userAccount.getOrganizationId());
			
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemName() );
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemCategory() );
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemSpec() );
			SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getVendorId() );
			
//			sqlArgs.add(dto.getItemName());
//			sqlArgs.add(dto.getItemName());
//			sqlArgs.add(dto.getItemCategory());
//			sqlArgs.add(dto.getItemCategory());
//			sqlArgs.add(dto.getItemSpec());
//			sqlArgs.add(dto.getItemSpec());
//			sqlArgs.add(dto.getVendorId());
//			sqlArgs.add(dto.getVendorId());
		}
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：框架自动生成设备分类表(EAM) ETS_SYSTEM_ITEM数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getSpareDistriModel() { //查询设备分配的SQL(详细页面)
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT " +
						"  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_ORGNIZATION_NAME(ESD.ORGANIZATION_ID) COMPANY," +
						" ESD.ITEM_CODE," +
						" ESD.SYSTEM_ID," +
						" ESD.ORGANIZATION_ID," +
						" ESD.IS_TMP" +
						" FROM " +
						" ETS_SYSITEM_DISTRIBUTE ESD" +
						" WHERE  ESD.ITEM_CODE = ?" +
						" AND ESD.IS_TMP = 'Y'";
		sqlArgs.add(dto.getItemCode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行设备分配确认操作。
	 * @param itemCodes String[]
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel distriModel(String[] itemCodes) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String Codes = ArrUtil.arrToSqlStr(itemCodes);
		String sqlStr = "UPDATE "
						+ " ETS_SYSITEM_DISTRIBUTE"
						+ " SET"
						+ " IS_TMP = 'N'"
						+ " WHERE"
						+ " ITEM_CODE IN (" + Codes + ")";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：执行设备分配确认操作。
	 * @param itemCodes String[]
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel distriModel4(String[] itemCodes) {

		SQLModel sqlModel = new SQLModel();
		String Codes = ArrUtil.arrToSqlStr(itemCodes);
		String sqlStr = "UPDATE "
						+ " ETS_SYSITEM_DISTRIBUTE"
						+ " SET"
						+ " IS_TMP = 'N'"
						+ " WHERE"
						+ " SYSTEM_ID IN (" + Codes + ")";
		sqlModel.setSqlStr(sqlStr);
		return sqlModel;
	}

/**
	 * 功能：执行插入表ETS_SYSTEM_ITEM_CHECK操作。
	 * @param dto PartConfirmDTO
	 * @return SQLModel 返回数据用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel insertIntoDis(PartConfirmDTO dto) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			PartConfirmDTO partConfirmDTO = (PartConfirmDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " ETS_SYSTEM_ITEM_CHECK("
							+ " CHECK_ID,"
							+ " ITEM_CODE,"
							+ " OLD_ITEM_CODE,"
							+ " OLD_ITEM_NAME,"
							+ " OLD_ITEM_SPEC,"
							+ " OLD_ITEM_CATEGORY,"
							+ " OLD_ITEM_UNIT,"
							+ " OLD_VENDOR_ID,"
							+ " IS_CORRECT,"
							+ " MATCH_REASON,"
							+ " SUBMIT_USER,"
							+ " SUBMIT_DATE,"
							+ " CHECK_USER,"
							+ " CHECK_DATE,"
							+ " REMARK"
							+ ") VALUES (NEWID() , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + SyBaseSQLUtil.getCurDate() + ", ?)";
//			sqlArgs.add(partConfirmDTO.getCheckId());
			sqlArgs.add(dto.getItemCode());
			sqlArgs.add(dto.getOldItemCode());
			sqlArgs.add(dto.getOldItemName());
			sqlArgs.add(dto.getOldItemSpec());
			sqlArgs.add(dto.getOldItemCategory());
			sqlArgs.add(dto.getOldItemUnit());
			sqlArgs.add(dto.getOldVendorId());
			sqlArgs.add(dto.getIsCorrect());
			sqlArgs.add(dto.getMatchReason());
			sqlArgs.add(dto.getSubmitUser());
			sqlArgs.add(dto.getSubmitDate());
			sqlArgs.add(userAccount.getUserId());
//			sqlArgs.add(dto.getCheckDate());
			sqlArgs.add(dto.getRemark());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	public SQLModel getItemSpecNameModel(String itemCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		PartConfirmDTO etsObject = (PartConfirmDTO) dtoParameter;
		String sqlStr = "SELECT " +
						" ESI.ITEM_CODE," +
						" ESI.ITEM_NAME," +
						" ESI.ITEM_SPEC," +
						" ESI.ITEM_CATEGORY," +
						" ESI.ITEM_UNIT," +
						" ESI.VENDOR_ID," +
						" ESI.CREATED_BY," +
						" ESI.CREATION_DATE " +
//                " ESI.CREATION_DATE" +
						" FROM" +
						" ETS_SYSTEM_ITEM ESI" +
						" WHERE" +
						" ESI.ITEM_CODE = " + itemCode;
//		sqlArgs.add(etsObject.getObjectCategory());
//        sqlArgs.add(DictConstant.OBJECT_CATEGORY);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


/**
	 * 功能：执行批量确认操作。
	 * @param itemCode1 String
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel stand3Model(String itemCode1) {

		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " ETS_SYSTEM_ITEM"
						+ " SET"
						+ " ITEM_NAME = ?,"
						+ " ITEM_SPEC = ?,"
//                            + " ITEM_CATEGORY = ?,"
						+ " IS_TMP_CODE = 'N',"
						+ " LAST_UPDATE_DATE = " + SyBaseSQLUtil.getCurDate() + ","
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " ITEM_CODE = '" + itemCode1 + "'";

		sqlArgs.add(dto.getItemName3());
		sqlArgs.add(dto.getItemSpec3());
//            sqlArgs.add(dto.getItemCategory());
		sqlArgs.add(userAccount.getUserId());
//            sqlArgs.add(dto.getItemCode());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行插入表ETS_SYSTEM_ITEM_CHECK操作。
	 * @param itemCode String
	 * @param isCorrect String
	 * @param matchReason String
	 * @param remark String
	 * @return SQLModel 返回数据用SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel insertIntoCheck(String itemCode, String isCorrect, String matchReason, String remark) throws
		SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "INSERT INTO ETS_SYSTEM_ITEM_CHECK ("
						+ " CHECK_ID,"
						+ " ITEM_CODE,"
						+ " OLD_ITEM_CODE,"
						+ " OLD_ITEM_NAME,"
						+ " OLD_ITEM_SPEC,"
						+ " OLD_ITEM_CATEGORY,"
						+ " OLD_ITEM_UNIT,"
						+ " OLD_VENDOR_ID,"
						+ " IS_CORRECT,"
						+ " MATCH_REASON,"
						+ " SUBMIT_USER,"
						+ " SUBMIT_DATE,"
						+ " CHECK_USER,"
						+ " CHECK_DATE,"
						+ " REMARK)"
						+ " (SELECT"
						+ " NEWID(),"
						+ " ?,"
						+ " ESI.ITEM_CODE,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " ESI.ITEM_CATEGORY,"
						+ " ESI.ITEM_UNIT,"
						+ " ESI.VENDOR_ID,"
						+ " ?,"
						+ " ?,"
						+ " ESI.CREATED_BY,"
						+ " ESI.CREATION_DATE,"
						+ " ?,"
						+  SyBaseSQLUtil.getCurDate() + ","
						+ " ?"
						+ " FROM"
						+ " ETS_SYSTEM_ITEM ESI"
						+ " WHERE"
						+ " ESI.ITEM_CODE = ?)";
		sqlArgs.add(itemCode);
		sqlArgs.add(isCorrect);
		sqlArgs.add(matchReason);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(remark);
		sqlArgs.add(itemCode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	public SQLModel getFalseRateModel() { //设备确认错误统计SQL
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT" +
						"     A.SUBMIT_USER," +
						"      " + SyBaseSQLUtil.getDBOwner() + "APP_GET_USER_NAME(A.SUBMIT_USER) USER_NAME," +
						"      " + SyBaseSQLUtil.getDBOwner() + "APP_GET_COMPANY_NAME(A.SUBMIT_USER) COMPANY_NAME," +
						"     SUM(A.ERROR_NUM) ERROR_NUM," +
						"     SUM(A.CORRECT_NUM) CORRECT_NUM," +
						"     CONVERT( VARCHAR , 100 * ROUND(SUM(A.ERROR_NUM) / (SUM(A.ERROR_NUM) + SUM(A.CORRECT_NUM)),3) ) || '%' RATE" + //RATE
						"  FROM (SELECT ESIC.SUBMIT_USER, COUNT(*) ERROR_NUM, 0 CORRECT_NUM" +
						"        FROM ETS_SYSTEM_ITEM_CHECK ESIC" +
						"       WHERE ESIC.IS_CORRECT = 'N'" +
						"       GROUP BY ESIC.SUBMIT_USER" +
						"      UNION" +
						"      SELECT ESIC.SUBMIT_USER, 0 ERROR_NUM, COUNT(*) CORRECT_NUM" +
						"        FROM ETS_SYSTEM_ITEM_CHECK ESIC" +
						"       WHERE ESIC.IS_CORRECT = 'Y'" +
						"       GROUP BY ESIC.SUBMIT_USER) A" +
						" WHERE (" + SyBaseSQLUtil.nullStringParam() + "  OR  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_USER_NAME(A.SUBMIT_USER) LIKE ?) " +
						" GROUP BY A.SUBMIT_USER";
		
		SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getItemName() ); 
//		sqlArgs.add(dto.getItemName());
//		sqlArgs.add(dto.getItemName());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getReasonRateModel() { //设备确认原因统计SQL
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT " +
						"  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_COMPANY_NAME(ESIC.SUBMIT_USER) COMPANY," +
//						"  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_COMPANY_NAME(ESIC.SUBMIT_USER)," +
						" ESIC.MATCH_REASON," +
						" COUNT(*) NUM" +
						" FROM" +
						" ETS_SYSTEM_ITEM_CHECK ESIC," +
						" SF_USER SU" +
						" WHERE" +
//                       "    (? " + SyBaseSQLUtil.isNull() + "  OR  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_COMPANY_NAME(ESIC.SUBMIT_USER) LIKE ? )" +
						"  ESIC.SUBMIT_USER = SU.USER_ID" +
						" AND (" + SyBaseSQLUtil.nullIntParam() + "  OR SU.ORGANIZATION_ID = ?)" +
						" GROUP BY  " + SyBaseSQLUtil.getDBOwner() + "APP_GET_COMPANY_NAME(ESIC.SUBMIT_USER)," +
						"      ESIC.MATCH_REASON";
		
//		sqlArgs.add(dto.getCompany());
//		sqlArgs.add(dto.getCompany());
		SyBaseSQLUtil.nullIntParamArgs(sqlArgs, dto.getCompany() ); 

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：执行设备分配确认操作。
	 * @param itemCode String
	 * @return SQLModel 返回数据用SQLModel
	 */
	public SQLModel distriModel3(String itemCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE "
						+ " ETS_SYSITEM_DISTRIBUTE"
						+ " SET"
						+ " IS_TMP = 'N'"
						+ " WHERE"
						+ " ITEM_CODE =  '" + itemCode+"'";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
