package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA. User: T_suhuipeng Date: 2011-3-14 Time: 13:49:52 To
 * change this template use File | Settings | File Templates.
 */
public class ItemMaintainModel3 extends AMSSQLProducer {
	private String deptCodes = "";

	public ItemMaintainModel3(SfUserDTO userAccount,
			AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
		initDeptCodes();
	}

	private void initDeptCodes() {
		deptCodes = "(";
		DTOSet depts = userAccount.getPriviDeptCodes();
		if (depts != null && !depts.isEmpty()) {
			AmsMisDeptDTO dept = null;
			for (int i = 0; i < depts.getSize(); i++) {
				dept = (AmsMisDeptDTO) depts.getDTO(i);
				deptCodes += "'" + dept.getDeptCode() + "', ";
			}
		}
		deptCodes += "'')";
	}

	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
			String sqlStr = "UPDATE"
					+ " ETS_ITEM_INFO EII"
					+ " SET"
					+ " EII.ITEM_CODE           = dbo.NVL(?, EII.ITEM_CODE),"
					+ " EII.ADDRESS_ID          = dbo.NVL(?, EII.ADDRESS_ID),"
					+ " EII.RESPONSIBILITY_USER = dbo.NVL(?, EII.RESPONSIBILITY_USER),"
					+ " EII.RESPONSIBILITY_DEPT = dbo.NVL(?, EII.RESPONSIBILITY_DEPT),"
					+ " EII.MAINTAIN_USER       = dbo.NVL(?, EII.MAINTAIN_USER),"
					+ " EII.MAINTAIN_DEPT       = dbo.NVL(?, EII.MAINTAIN_DEPT),"
					+ " EII.START_DATE          = ISNULL(?, EII.START_DATE),"
					+ " EII.REMARK              = dbo.NVL(?, EII.REMARK),"
					+ " EII.LAST_UPDATE_DATE    = GETDATE(),"
					+ " EII.LAST_UPDATE_BY      = ?" + " WHERE"
					+ " EII.BARCODE = ?";
			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getItemCode());
			sqlArgs.add(dto.getAddressId());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getResponsibilityDept());
			sqlArgs.add(dto.getMaintainUser());
			sqlArgs.add(dto.getMaintainDept());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getRemark());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getBarcode());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String sqlStr = "SELECT"
				+ " EII.BARCODE,"
				+ " ESI.ITEM_CODE,"
				+ " ESI.ITEM_CATEGORY,"
				+ " dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
				+ " ESI.ITEM_NAME,"
				+ " ESI.ITEM_SPEC,"
				+ " ESI.YEARS,"
				+ " ESI.ITEM_UNIT,"
				+ " EII.START_DATE,"
				+ " EO.WORKORDER_OBJECT_CODE,"
				+ " EO.WORKORDER_OBJECT_NAME,"
				+ " EO.WORKORDER_OBJECT_LOCATION,"
				+ " EC.COUNTY_CODE,"
				+ " EC.COUNTY_NAME,"
				+ " AME.USER_NAME RESPONSIBILITY_USER_NAME,"
				+ " AME.EMPLOYEE_NUMBER,"
				+ " AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,"
				+ " EPPA.NAME PROJECT_NAME,"
				+ " EPPA.SEGMENT1 PROJECT_NUMBER,"
				+ " EMPV.VENDOR_NAME,"
				+ " EMPV.SEGMENT1 VENDOR_NUMBER,"
				+ " EII.FINANCE_PROP,"
				+ " dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,"
				+ " EII.RESPONSIBILITY_USER," + " EII.RESPONSIBILITY_DEPT,"
				+ " EII.MAINTAIN_USER," + " EII.MAINTAIN_DEPT,"
				+ " EII.ADDRESS_ID," + " EOCM.COMPANY_CODE," + " EOCM.COMPANY"
				+ " FROM" + " ETS_ITEM_INFO          EII,"
				+ " ETS_SYSTEM_ITEM        ESI,"
				+ " AMS_OBJECT_ADDRESS     AOA,"
				+ " ETS_OBJECT             EO," + " ETS_COUNTY             EC,"
				+ " ETS_OU_CITY_MAP        EOCM,"
				+ " AMS_MIS_DEPT           AMD,"
				+ " AMS_MIS_EMPLOYEE       AME,"
				+ " ETS_PA_PROJECTS_ALL    EPPA,"
				+ " ETS_MIS_PO_VENDORS     EMPV" + " WHERE"
				+ " EII.ITEM_CODE = ESI.ITEM_CODE"
				+ " AND ESI.VENDOR_ID *= EMPV.VENDOR_ID"
				+ " AND EII.ORGANIZATION_ID *= EOCM.ORGANIZATION_ID"
				+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
				+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
				+ " AND EO.COUNTY_CODE *= EC.COUNTY_CODE"
				+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
				+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
				+ " AND EII.PROJECTID *= EPPA.PROJECT_ID"
				+ " AND EII.BARCODE = ?";
//                + " AND EII.RESPONSIBILITY_DEPT IN "   --SUHP注释，点击行信息时只需传入BARCODE,不需要控制部门（2012.2.15）
//				+ deptCodes;
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getBarcode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getCompanyIdModel(String company) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT EOCM.ORGANIZATION_ID FROM ETS_OU_CITY_MAP EOCM WHERE EOCM.COMPANY = ?";
		sqlArgs.add(company);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDeptCodeModel(String deptName) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AMD.DEPT_CODE FROM AMS_MIS_DEPT AMD WHERE AMD.DEPT_NAME = ?";
		sqlArgs.add(deptName);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel isNewQueryModel(String deptName) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT 1 FROM AMS_DEPT_MATCH ADM WHERE ADM.DEPT_NAME = ?";
		sqlArgs.add(deptName);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getFincePropCount(AmsAssetsAddressVDTO dto) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		try {
			String sqlStr = "";
			if (dto.isNewQuery()) {
				sqlStr = "SELECT EFV.CODE, \n" + " EFV.VALUE, \n"
						+ " SUM(T.CNT) CNT\n" + " FROM ETS_FLEX_VALUES EFV,\n"
						+ "(SELECT EII.FINANCE_PROP,\n"
						+ "       COUNT(1) CNT\n"
						+ "  FROM AMS_LNE                AL,\n"
						+ "       AMS_CEX                AC,\n"
						+ "       AMS_OPE                AO,\n"
						+ "       AMS_NLE                AN,\n"
						+ "       ETS_ITEM_INFO          EII,\n"
						+ "       ETS_SYSTEM_ITEM        ESI,\n"
						+ "       AMS_OBJECT_ADDRESS     AOA,\n"
						+ "       ETS_OBJECT             EO,\n"
						+ "       ETS_OU_CITY_MAP        EOCM,\n"
						+ "       AMS_MIS_DEPT           AMD,\n"
						+ "       AMS_MIS_EMPLOYEE       AME,\n"
						+ "       ETS_PA_PROJECTS_ALL    EPPA,\n"
						+ "       ETS_MIS_PO_VENDORS     EMPV,\n"
						+ "       AMS_MANUFACTURER       AM,\n"
						+ "       AMS_COUNTY             ACC1,\n"
						+ "       AMS_COUNTY             ACC2,\n"
						+ "       AMS_MIS_DEPT           AMD2,\n"
						+ "       AMS_DEPT_MATCH         ADM,\n"
						+ "       AMS_MIS_DEPT           AMD3,\n";
				if (userAccount.getIsTd().equals("Y")) {
					sqlStr = sqlStr + " ETS_ITEM_MATCH_TD          EIM,"
							+ " ETS_FA_ASSETS_TD           EFA";
				} else {
					sqlStr = sqlStr + " ETS_ITEM_MATCH          EIM,"
							+ " ETS_FA_ASSETS           EFA";
				}
				sqlStr = sqlStr
						+ " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n"
						+ "       AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n"
						+ "       AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n"
						+ "       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n"
						+ "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n"
						+ "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
						+ "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n"
						+ "       AND EII.RESPONSIBILITY_DEPT = ADM.N_DEPT_CODE\n"
						+ "       AND ADM.DEPT_CODE = AMD.DEPT_CODE\n"
						+ "       AND EII.PROJECTID *= EPPA.PROJECT_ID\n"
						+ "       AND EII.LNE_ID *= AL.AMS_LNE_ID\n"
						+ "       AND EII.CEX_ID *= AC.AMS_CEX_ID\n"
						+ "       AND EII.OPE_ID *= AO.AMS_OPE_ID\n"
						+ "       AND EII.NLE_ID *= AN.AMS_LNE_ID\n"
						+ "       AND EII.SPECIALITY_DEPT *= AMD2.DEPT_CODE\n"
						+ "       AND EII.MAINTAIN_DEPT *= AMD3.DEPT_CODE\n"
						+ "       AND EII.SYSTEMID *= EIM.SYSTEMID\n"
						+ "       AND EIM.ASSET_ID *= EFA.ASSET_ID\n"
						+ "       AND EO.CITY *= ACC1.COUNTY_CODE\n"
						+ "       AND EO.COUNTY *= ACC2.COUNTY_CODE\n"
						+ "       AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)\n"
						+ "       AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n"
						+ "       AND (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE) OR\n"
						+ "       EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME))\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR AME.USER_NAME LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EPPA.SEGMENT1 LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EPPA.NAME LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.SPECIALITY_DEPT = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.CONTENT_NAME LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.CONTENT_CODE LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.SHARE_STATUS = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.POWER LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.ITEM_STATUS = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.MAINTAIN_USER LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.MAINTAIN_DEPT = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR ESI.ITEM_SPEC LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.IS_RENTAL = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.ASSETS_CREATE_DATE >= ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.ASSETS_CREATE_DATE <= ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.DATE_PLACED_IN_SERVICE >= ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.DATE_PLACED_IN_SERVICE <= ?)\n"
						+ "       AND (? = -1  OR EII.ACTUAL_QTY = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EOCM.COMPANY = ?)\n"
						+

						"       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EMPV.SEGMENT1 = ?)\n"
						+

						"       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR ADM.DEPT_NAME = ?)\n"
						+ "       AND NOT EXISTS (SELECT 1\n"
						+ "          FROM ETS_FA_ASSETS EFA\n"
						+ "         WHERE EII.BARCODE = EFA.TAG_NUMBER\n"
						+ "               AND EFA.BOOK_TYPE_CODE LIKE '%IA%')\n"
						+ "       AND (" + SyBaseSQLUtil.isNull()
						+ " OR EII.FINANCE_PROP = ?)\n" + "       AND ("
						+ SyBaseSQLUtil.isNull() + " OR EO.AREA_TYPE = ?)\n"
						+ "       AND (" + SyBaseSQLUtil.isNull()
						+ " OR EO.CITY = ?)\n" + "       AND ("
						+ SyBaseSQLUtil.isNull() + " OR EO.COUNTY = ?)";
				sqlArgs.add(dto.getItemCategory());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getResponsibilityUserName());
				sqlArgs.add(dto.getResponsibilityUserName());
				sqlArgs.add(dto.getProjectNumber());
				sqlArgs.add(dto.getProjectNumber());
				sqlArgs.add(dto.getProjectName());
				sqlArgs.add(dto.getProjectName());
				sqlArgs.add(dto.getSpecialityDept());
				sqlArgs.add(dto.getSpecialityDept());
				sqlArgs.add(dto.getContentName());
				sqlArgs.add(dto.getContentName());
				sqlArgs.add(dto.getContentCode());
				sqlArgs.add(dto.getContentCode());
				sqlArgs.add(dto.getShare());
				sqlArgs.add(dto.getShare());
				sqlArgs.add(dto.getPower());
				sqlArgs.add(dto.getPower());
				sqlArgs.add(dto.getItemStatus());
				sqlArgs.add(dto.getItemStatus());
				sqlArgs.add(dto.getMaintainUser());
				sqlArgs.add(dto.getMaintainUser());
				sqlArgs.add(dto.getMaintainDept());
				sqlArgs.add(dto.getMaintainDept());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getRental());
				sqlArgs.add(dto.getRental());
				sqlArgs.add(dto.getCreationDate());
				sqlArgs.add(dto.getCreationDate());
				sqlArgs.add(dto.getSQLLastUpdateDate());
				sqlArgs.add(dto.getSQLLastUpdateDate());
				sqlArgs.add(dto.getStartDate());
				sqlArgs.add(dto.getStartDate());
				sqlArgs.add(dto.getSQLEndDate());
				sqlArgs.add(dto.getSQLEndDate());
				sqlArgs.add(dto.getActualQty());
				sqlArgs.add(dto.getActualQty());
				sqlArgs.add(dto.getCompany());
				sqlArgs.add(dto.getCompany());

				sqlArgs.add(dto.getVendorNumber());
				sqlArgs.add(dto.getVendorNumber());

				sqlArgs.add(dto.getDeptName());
				sqlArgs.add(dto.getDeptName());
				sqlArgs.add(dto.getFinanceProp());
				sqlArgs.add(dto.getFinanceProp());
				sqlArgs.add(dto.getAreaType());
				sqlArgs.add(dto.getAreaType());
				sqlArgs.add(dto.getCity());
				sqlArgs.add(dto.getCity());
				sqlArgs.add(dto.getCounty());
				sqlArgs.add(dto.getCounty());
			} else {
				sqlStr = "SELECT EFV.CODE, \n" + " EFV.VALUE, \n"
						+ " SUM(T.CNT) CNT\n" + " FROM ETS_FLEX_VALUES EFV,\n"
						+ "(SELECT EII.FINANCE_PROP,\n"
						+ "       COUNT(1) CNT\n"
						+ "  FROM AMS_LNE                AL,\n"
						+ "       AMS_CEX                AC,\n"
						+ "       AMS_OPE                AO,\n"
						+ "       AMS_NLE                AN,\n"
						+ "       ETS_ITEM_INFO          EII,\n"
						+ "       ETS_SYSTEM_ITEM        ESI,\n"
						+ "       AMS_OBJECT_ADDRESS     AOA,\n"
						+ "       ETS_OBJECT             EO,\n"
						+ "       ETS_OU_CITY_MAP        EOCM,\n"
						+ "       AMS_MIS_DEPT           AMD,\n"
						+ "       AMS_MIS_EMPLOYEE       AME,\n"
						+ "       ETS_PA_PROJECTS_ALL    EPPA,\n"
						+ "       ETS_MIS_PO_VENDORS     EMPV,\n"
						+ "       AMS_MANUFACTURER       AM,\n"
						+ "       AMS_COUNTY             ACC1,\n"
						+ "       AMS_COUNTY             ACC2,\n"
						+ "       AMS_MIS_DEPT           AMD2,\n"
						+ "       AMS_MIS_DEPT           AMD3,\n";
				if (userAccount.getIsTd().equals("Y")) {
					sqlStr = sqlStr + " ETS_ITEM_MATCH_TD          EIM,"
							+ " ETS_FA_ASSETS_TD           EFA";
				} else {
					sqlStr = sqlStr + " ETS_ITEM_MATCH          EIM,"
							+ " ETS_FA_ASSETS           EFA";
				}
				sqlStr = sqlStr
						+ " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n"
						+ "       AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n"
						+ "       AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID\n"
						+ "       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n"
						+ "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n"
						+ "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n"
						+ "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n"
						+ "       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n"
						+ "       AND EII.PROJECTID *= EPPA.PROJECT_ID\n"
						+ "       AND EII.LNE_ID *= AL.AMS_LNE_ID\n"
						+ "       AND EII.CEX_ID *= AC.AMS_CEX_ID\n"
						+ "       AND EII.OPE_ID *= AO.AMS_OPE_ID\n"
						+ "       AND EII.NLE_ID *= AN.AMS_LNE_ID\n"
						+ "       AND EII.SPECIALITY_DEPT *= AMD2.DEPT_CODE\n"
						+ "       AND EII.MAINTAIN_DEPT *= AMD3.DEPT_CODE\n"
						+ "       AND EII.SYSTEMID *= EIM.SYSTEMID\n"
						+ "       AND EIM.ASSET_ID *= EFA.ASSET_ID\n"
						+ "       AND EO.CITY *= ACC1.COUNTY_CODE\n"
						+ "       AND EO.COUNTY *= ACC2.COUNTY_CODE\n"
						+ "       AND ESI.ITEM_CATEGORY = dbo.NVL(?, ESI.ITEM_CATEGORY)\n"
						+ "       AND ESI.ITEM_NAME LIKE dbo.NVL(?, ESI.ITEM_NAME)\n"
						+ "       AND (EO.WORKORDER_OBJECT_CODE LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_CODE) OR\n"
						+ "       EO.WORKORDER_OBJECT_NAME LIKE dbo.NVL(?, EO.WORKORDER_OBJECT_NAME))\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR AME.USER_NAME LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EPPA.SEGMENT1 LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EPPA.NAME LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.SPECIALITY_DEPT = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.CONTENT_NAME LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.CONTENT_CODE LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.SHARE_STATUS = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.POWER LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.ITEM_STATUS = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.MAINTAIN_USER LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.MAINTAIN_DEPT = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR ESI.ITEM_SPEC LIKE ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EII.IS_RENTAL = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.ASSETS_CREATE_DATE >= ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.ASSETS_CREATE_DATE <= ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.DATE_PLACED_IN_SERVICE >= ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EFA.DATE_PLACED_IN_SERVICE <= ?)\n"
						+ "       AND (? = -1  OR EII.ACTUAL_QTY = ?)\n"
						+ "       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EOCM.COMPANY = ?)\n"
						+

						"       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR EMPV.SEGMENT1 = ?)\n"
						+

						"       AND ("
						+ SyBaseSQLUtil.isNull()
						+ " OR AMD.DEPT_NAME = ?)\n"
						+ "       AND NOT EXISTS (SELECT 1\n"
						+ "          FROM ETS_FA_ASSETS EFA\n"
						+ "         WHERE EII.BARCODE = EFA.TAG_NUMBER\n"
						+ "               AND EFA.BOOK_TYPE_CODE LIKE '%IA%')\n"
						+ "       AND (" + SyBaseSQLUtil.isNull()
						+ " OR EII.FINANCE_PROP = ?)\n" + "       AND ("
						+ SyBaseSQLUtil.isNull() + " OR EO.AREA_TYPE = ?)\n"
						+ "       AND (" + SyBaseSQLUtil.isNull()
						+ " OR EO.CITY = ?)\n" + "       AND ("
						+ SyBaseSQLUtil.isNull() + " OR EO.COUNTY = ?)";
				sqlArgs.add(dto.getItemCategory());
				sqlArgs.add(dto.getItemName());
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getResponsibilityUserName());
				sqlArgs.add(dto.getResponsibilityUserName());
				sqlArgs.add(dto.getProjectNumber());
				sqlArgs.add(dto.getProjectNumber());
				sqlArgs.add(dto.getProjectName());
				sqlArgs.add(dto.getProjectName());
				sqlArgs.add(dto.getSpecialityDept());
				sqlArgs.add(dto.getSpecialityDept());
				sqlArgs.add(dto.getContentName());
				sqlArgs.add(dto.getContentName());
				sqlArgs.add(dto.getContentCode());
				sqlArgs.add(dto.getContentCode());
				sqlArgs.add(dto.getShare());
				sqlArgs.add(dto.getShare());
				sqlArgs.add(dto.getPower());
				sqlArgs.add(dto.getPower());
				sqlArgs.add(dto.getItemStatus());
				sqlArgs.add(dto.getItemStatus());
				sqlArgs.add(dto.getMaintainUser());
				sqlArgs.add(dto.getMaintainUser());
				sqlArgs.add(dto.getMaintainDept());
				sqlArgs.add(dto.getMaintainDept());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getItemSpec());
				sqlArgs.add(dto.getRental());
				sqlArgs.add(dto.getRental());
				sqlArgs.add(dto.getCreationDate());
				sqlArgs.add(dto.getCreationDate());
				sqlArgs.add(dto.getSQLLastUpdateDate());
				sqlArgs.add(dto.getSQLLastUpdateDate());
				sqlArgs.add(dto.getStartDate());
				sqlArgs.add(dto.getStartDate());
				sqlArgs.add(dto.getSQLEndDate());
				sqlArgs.add(dto.getSQLEndDate());
				sqlArgs.add(dto.getActualQty());
				sqlArgs.add(dto.getActualQty());
				sqlArgs.add(dto.getCompany());
				sqlArgs.add(dto.getCompany());

				sqlArgs.add(dto.getVendorNumber());
				sqlArgs.add(dto.getVendorNumber());

				if (dto.getDeptName().indexOf("+") > 0) {
					int i = dto.getDeptName().indexOf("+");
					String deptn = dto.getDeptName().substring(0, i);
					sqlArgs.add(deptn);
					sqlArgs.add(deptn);
				} else {
					sqlArgs.add(dto.getDeptName());
					sqlArgs.add(dto.getDeptName());
				}
				sqlArgs.add(dto.getFinanceProp());
				sqlArgs.add(dto.getFinanceProp());
				sqlArgs.add(dto.getAreaType());
				sqlArgs.add(dto.getAreaType());
				sqlArgs.add(dto.getCity());
				sqlArgs.add(dto.getCity());
				sqlArgs.add(dto.getCounty());
				sqlArgs.add(dto.getCounty());
			}
			if (!dto.getMaintainCompany().equals("")) {
				sqlStr += " AND EXISTS(" + " SELECT" + " NULL" + " FROM"
						+ " AMS_MAINTAIN_COMPANY        AMC,"
						+ " AMS_MAINTAIN_RESPONSIBILITY AMR" + " WHERE"
						+ " EO.WORKORDER_OBJECT_NO = AMR.OBJECT_NO"
						+ " AND AMR.COMPANY_ID = AMC.COMPANY_ID"
						+ " AND AMC.COMPANY_ID = ?)";
				sqlArgs.add(dto.getMaintainCompany());
			}

			sqlStr += "       AND (? = '' OR EII.BARCODE >= ?) \n"
					+ "       AND (? = '' OR EII.BARCODE <= ?) \n";

			sqlArgs.add(dto.getFromBarcode());
			sqlArgs.add(dto.getFromBarcode());
			sqlArgs.add(dto.getToBarcode());
			sqlArgs.add(dto.getToBarcode());
			// if ((!StrUtil.isEmpty(dto.getFromBarcode())) &&
			// (!StrUtil.isEmpty(dto.getToBarcode()))) {
			// sqlStr += " AND EII.BARCODE >= dbo.NVL(?, EII.BARCODE)"
			// + " AND EII.BARCODE <= dbo.NVL(?, EII.BARCODE)";
			// sqlArgs.add(dto.getFromBarcode());
			// sqlArgs.add(dto.getToBarcode());
			// } else {
			// sqlStr += "AND EII.BARCODE LIKE dbo.NVL(?,EII.BARCODE)";
			// sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
			// }

			if (dto.getAttribute1().equals(AssetsDictConstant.STATUS_NO)) {
				sqlStr += " AND EII.ATTRIBUTE1 = ''";
			} else {
				sqlStr += " AND EII.ATTRIBUTE1 = ?";
				sqlArgs.add(dto.getAttribute1());
			}
			if (dto.getCheck().equals("Y")) {
				sqlStr += " AND EXISTS (SELECT NULL\n"
						+ "          FROM AMS_ASSETS_CHECK_LINE AACL\n"
						+ "         WHERE EII.BARCODE = AACL.BARCODE\n"
						+ "               AND AACL.ARCHIVE_STATUS = '')";
			} else if (dto.getCheck().equals("N")) {
				sqlStr += " AND NOT EXISTS (SELECT NULL\n"
						+ "          FROM AMS_ASSETS_CHECK_LINE AACL\n"
						+ "         WHERE EII.BARCODE = AACL.BARCODE\n"
						+ "               AND AACL.ARCHIVE_STATUS = '')";
			}
			sqlStr += " GROUP BY EII.FINANCE_PROP) T\n"
					+ " WHERE EFV.FLEX_VALUE_SET_ID = '1003' \n"
					+ " AND EFV.CODE *= T.FINANCE_PROP\n"
					+ " GROUP BY EFV.CODE, EFV.VALUE";
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printStackTrace();
			ex.printLog();
		}
		return sqlModel;
	}

	public SQLModel getAllSearchModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		StringBuilder sqlStr = new StringBuilder();
		List sqlArgs = new ArrayList();

		List tempList = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		SQLModel sqlModel1;

		dto.setFinanceProp(DictConstant.FIN_PROP_ASSETS);
		sqlModel1 = getFAAssetsSearchModel();
		sqlStr.append(sqlModel1.getSqlStr());
		sqlArgs.addAll(sqlModel1.getArgs());

		sqlStr.append(" UNION ALL \n");

		// TD_TT_ASSETS QD_DG_ASSETS

		dto.setFinanceProp("TD_TT_ASSETS");
		sqlModel1 = getFAAssetsSearchModel();
		sqlStr.append(sqlModel1.getSqlStr());
		sqlArgs.addAll(sqlModel1.getArgs());

		sqlStr.append(" UNION ALL \n");

		dto.setFinanceProp("QD_DG_ASSETS");
		sqlModel1 = getFAAssetsSearchModel();
		sqlStr.append(sqlModel1.getSqlStr());
		sqlArgs.addAll(sqlModel1.getArgs());

		sqlStr.append(" UNION ALL \n");

		dto.setFinanceProp("OTHERS_OF_ALL");
		sqlModel1 = getCommonAssetsSearchModel();
		sqlStr.append(sqlModel1.getSqlStr());
		sqlArgs.addAll(sqlModel1.getArgs());

		// DH_ASSETS OTHERS RENT_ASSETS SPARE UNKNOW

		dto.setFinanceProp("");

		sqlModel.setSqlStr(sqlStr.toString());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = null;
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		String financeProp = dto.getFinanceProp();
		if (userAccount.getIsTd().equals("Y")) {
			if(financeProp.equals(DictConstant.FIN_PROP_TD)||financeProp.equals("")){
				dto.setFinanceProp(DictConstant.FIN_PROP_TD);
				sqlModel = getFAAssetsSearchModel();	
			}else{
				sqlModel = getCommonAssetsSearchModel();
			}
			if(financeProp.equals("")){
				dto.setFinanceProp("");	
			}
		} else {
			if (financeProp.equals(DictConstant.FIN_PROP_ASSETS)
					|| financeProp.equals(DictConstant.FIN_PROP_TT)
					|| financeProp.equals(DictConstant.FIN_PROP_DG)
					|| financeProp.equals(DictConstant.FIN_PROP_QD)) {
				sqlModel = getFAAssetsSearchModel();
			} else if (StrUtil.isEmpty(financeProp)) {
				sqlModel = getAllSearchModel();
			} else {
				sqlModel = getCommonAssetsSearchModel();
			}
		}
		return sqlModel;
	}

	private SQLModel getFAAssetsSearchModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		StringBuilder sqlStr = new StringBuilder();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		try {
			String faTable = "";
			String matchTable = "";
			String financeProp = dto.getFinanceProp();
			if (financeProp.equals(DictConstant.FIN_PROP_ASSETS)) {
				faTable = "ETS_FA_ASSETS";
				matchTable = "ETS_ITEM_MATCH";
			} else if (financeProp.equals(DictConstant.FIN_PROP_TD)
					|| financeProp.equals(DictConstant.FIN_PROP_TT)
					|| financeProp.equals("TD_TT_ASSETS")) {
				faTable = "ETS_FA_ASSETS_TD";
				matchTable = "ETS_ITEM_MATCH_TD";
			} else if (financeProp.equals(DictConstant.FIN_PROP_DG)
					|| financeProp.equals(DictConstant.FIN_PROP_QD)
					|| financeProp.equals("QD_DG_ASSETS")) {
				// TD_TT_ASSETS QD_DG_ASSETS
				faTable = "ETS_FA_ASSETS_TF";
				matchTable = "ETS_ITEM_MATCH_TF";
			}
			sqlStr.append(" SELECT \n");
			sqlStr.append("       EII.BARCODE, \n");
			sqlStr.append("       ESI.ITEM_CODE, \n");
			sqlStr.append("       ESI.ITEM_CATEGORY, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME, \n");
			sqlStr.append("       ESI.ITEM_NAME, \n");
			sqlStr.append("       ESI.ITEM_SPEC, \n");
			sqlStr.append("       ESI.YEARS, \n");
			sqlStr.append("       EFA.UNIT_OF_MEASURE ITEM_UNIT, \n");
			sqlStr.append("       EII.ITEM_QTY, \n");
			sqlStr.append("       EII.ACTUAL_QTY, \n");
			sqlStr.append("       EII.START_DATE, \n");
			sqlStr.append("       EO.WORKORDER_OBJECT_CODE, \n");
			sqlStr.append("       EO.WORKORDER_OBJECT_NAME, \n");
			sqlStr.append("       EO.WORKORDER_OBJECT_LOCATION, \n");
			sqlStr
					.append("       dbo.APP_GET_AMS_COUNTY_NAME( EO.CITY )  CITY, \n");
			sqlStr
					.append("       dbo.APP_GET_AMS_COUNTY_NAME( EO.COUNTY )   COUNTY, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EO.AREA_TYPE, 'ADDR_AREA_TYPE') AREA_TYPE_NAME, \n");
			sqlStr.append("       AME.USER_NAME RESPONSIBILITY_USER_NAME, \n");
			sqlStr.append("       AME.EMPLOYEE_NUMBER, \n");
			sqlStr.append("       AMD.DEPT_CODE, \n");
			sqlStr.append("       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME, \n");
			sqlStr.append("       EPPA.NAME PROJECT_NAME, \n");
			sqlStr.append("       EPPA.SEGMENT1 PROJECT_NUMBER, \n");
			// sqlStr.append(" EMPV.VENDOR_NAME, \n");
			// sqlStr.append(" EMPV.SEGMENT1 VENDOR_NUMBER, \n");

			sqlStr.append("       '' VENDOR_NAME, \n");
			sqlStr.append("       '' VENDOR_NUMBER, \n");

			sqlStr.append("       EII.FINANCE_PROP, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME, \n");
			sqlStr.append("       EII.RESPONSIBILITY_USER, \n");
			sqlStr.append("       EII.RESPONSIBILITY_DEPT, \n");
			sqlStr
					.append("	      dbo.AMP_GET_DEPT_NAME( EII.SPECIALITY_DEPT ) SPECIALITY_DEPT_NAME, \n");
			sqlStr.append("       EII.MAINTAIN_USER, \n");
			sqlStr.append("       EII.MAINTAIN_DEPT, \n");
			sqlStr
					.append("       dbo.APP_GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT_NAME, \n");
			sqlStr.append("       EII.ADDRESS_ID, \n");
			sqlStr.append("       EOCM.COMPANY_CODE, \n");
			sqlStr.append("       EOCM.COMPANY, \n");
			sqlStr.append("       AM.MANUFACTURER_CODE, \n");
			sqlStr.append("       AM.MANUFACTURER_NAME, \n");
			sqlStr.append("       EII.CONTENT_CODE, \n");
			sqlStr.append("       EII.CONTENT_NAME, \n");
			sqlStr.append("       EII.POWER, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.SHARE_STATUS, 'SHARE_STATUS') IS_SHARE, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.CONSTRUCT_STATUS, 'CONSTRUCT_STATUS') CONSTRUCT_STATUS, \n");

			if (financeProp.equals(DictConstant.FIN_PROP_DG)
					|| financeProp.equals(DictConstant.FIN_PROP_QD)
					|| financeProp.equals("QD_DG_ASSETS")) {
				sqlStr.append("       '' LOG_NET_ELE, \n");
				sqlStr.append("       '' INVEST_CAT_NAME, \n");
				sqlStr.append("       '' OPE_NAME, \n");
				sqlStr.append("       '' LNE_NAME, \n");
			} else {
				sqlStr.append("       AL.LOG_NET_ELE, \n");
				sqlStr.append("       AC.INVEST_CAT_NAME, \n");
				sqlStr.append("       AO.OPE_NAME, \n");
				sqlStr.append("       AN.LNE_NAME, \n");
			}

			sqlStr.append("       EIM.ASSET_ID, \n");
			sqlStr.append("       EFA.LIFE_IN_YEARS, \n");
			sqlStr.append("       EFA.DATE_PLACED_IN_SERVICE, \n");
			sqlStr.append("       EFA.ASSETS_CREATE_DATE, \n");
//			sqlStr.append("       EFA.ORIGINAL_COST, \n");

			sqlStr.append("       EFA.COST ORIGINAL_COST, \n");

			sqlStr.append("       EFA.SCRAP_VALUE, \n");
			sqlStr.append("       EFA.IMPAIR_RESERVE, \n");
			sqlStr.append("       EFA.DEPRN_RESERVE, \n");
			sqlStr.append("       EFA.NET_ASSET_VALUE, \n");
			sqlStr.append("       EFA.DEPRN_COST, \n");
			sqlStr.append("       EII.REMARK, \n");
			sqlStr.append("       EII.REMARK1, \n");
			sqlStr.append("       EII.REMARK2 \n");
			sqlStr.append("  FROM \n");

			if (financeProp.equals(DictConstant.FIN_PROP_DG)
					|| financeProp.equals(DictConstant.FIN_PROP_QD)
					|| financeProp.equals("QD_DG_ASSETS")) {

			} else {
				sqlStr.append("       AMS_LNE                AL, \n");
				sqlStr.append("       AMS_CEX                AC, \n");
				sqlStr.append("       AMS_OPE                AO, \n");
				sqlStr.append("       AMS_NLE                AN, \n");
			}

			sqlStr.append("       ETS_ITEM_INFO          EII, \n");
			sqlStr.append("       ETS_SYSTEM_ITEM        ESI, \n");
			sqlStr.append("       AMS_OBJECT_ADDRESS     AOA, \n");
			sqlStr.append("       ETS_OBJECT             EO, \n");
			sqlStr.append("       ETS_OU_CITY_MAP        EOCM, \n");
			sqlStr.append("       AMS_MIS_DEPT           AMD, \n");
			sqlStr.append("       AMS_MIS_EMPLOYEE       AME, \n");
			sqlStr.append("       ETS_PA_PROJECTS_ALL    EPPA, \n");
			// sqlStr.append(" ETS_MIS_PO_VENDORS EMPV, \n");
			sqlStr.append("       AMS_MANUFACTURER       AM, \n");
			sqlStr.append("       \n");
			sqlStr.append(matchTable);
			sqlStr.append("                               EIM, \n");
			sqlStr.append("       \n");
			sqlStr.append(faTable);
			sqlStr.append("                               EFA \n");
			sqlStr.append("   WHERE  \n");
			sqlStr.append("       EII.ITEM_CODE = ESI.ITEM_CODE \n");

			if (financeProp.equals(DictConstant.FIN_PROP_DG)
					|| financeProp.equals(DictConstant.FIN_PROP_QD)
					|| financeProp.equals("QD_DG_ASSETS")) {
				sqlStr.append("       AND EII.SYSTEMID  *= EIM.SYSTEMID \n");
				sqlStr.append("       AND EIM.ASSET_ID  *= EFA.ASSET_ID \n");
			} else {
				sqlStr.append("       AND EII.SYSTEMID  = EIM.SYSTEMID \n");
				sqlStr.append("       AND EIM.ASSET_ID  = EFA.ASSET_ID \n");
			}
			// sqlStr.append(" AND EII.SYSTEMID = EIM.SYSTEMID \n");
			// sqlStr.append(" AND EIM.ASSET_ID = EFA.ASSET_ID \n");
			sqlStr
					.append("       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n");
			sqlStr.append("       AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n");
			sqlStr
					.append("       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n");
			// if(dto.getDeptName().equals("")){
			// sqlStr.append(" AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE
			// \n");
			// } else {
			sqlStr
					.append("       AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE \n");
			// }

			// sqlStr.append(" AND ESI.VENDOR_ID *= EMPV.VENDOR_ID \n");
			sqlStr
					.append("       AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID \n");
			// if (!StrUtil.isEmpty(dto.getResponsibilityUserName())) {
			sqlStr
					.append("       AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID \n");
			// } else {
			// sqlStr.append(" AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID
			// \n");
			// }
			if (!StrUtil.isEmpty(dto.getProjectNumber())) {
				sqlStr.append("       AND EII.PROJECTID = EPPA.PROJECT_ID \n");
			} else {
				sqlStr.append("       AND EII.PROJECTID *= EPPA.PROJECT_ID \n");
			}

			// if( StrUtil.isEmpty( dto.getLogNetEle() ) ){
			// sqlStr.append( " AND EII.LNE_ID *= AL.AMS_LNE_ID \n ");
			// }else{
			// sqlStr.append( " AND EII.LNE_ID = AL.AMS_LNE_ID \n ");
			// }
			// if( StrUtil.isEmpty( dto.getInvestCatName() ) ){
			// sqlStr.append( " AND EII.CEX_ID *= AC.AMS_CEX_ID \n ");
			// }else{
			// sqlStr.append( " AND EII.CEX_ID = AC.AMS_CEX_ID \n ");
			// }
			// if( StrUtil.isEmpty( dto.getOpeName() ) ){
			// sqlStr.append( " AND EII.OPE_ID *= AO.AMS_OPE_ID \n ");
			// }else{
			// sqlStr.append( " AND EII.OPE_ID = AO.AMS_OPE_ID \n ");
			// }
			// if( StrUtil.isEmpty( dto.getLneName() ) ){
			// sqlStr.append( " AND EII.NLE_ID *= AN.AMS_LNE_ID \n ");
			// }else{
			// sqlStr.append( " AND EII.NLE_ID = AN.AMS_LNE_ID \n ");
			// }

			if (financeProp.equals(DictConstant.FIN_PROP_DG)
					|| financeProp.equals(DictConstant.FIN_PROP_QD)
					|| financeProp.equals("QD_DG_ASSETS")) {

			} else {
				if (StrUtil.isEmpty(dto.getLogNetEle())) {
					sqlStr.append(" AND EII.LNE_ID *= AL.AMS_LNE_ID \n ");
				} else {
					sqlStr.append(" AND EII.LNE_ID = AL.AMS_LNE_ID \n ");
				}
				if (StrUtil.isEmpty(dto.getInvestCatName())) {
					sqlStr.append(" AND EII.CEX_ID *= AC.AMS_CEX_ID \n ");
				} else {
					sqlStr.append(" AND EII.CEX_ID = AC.AMS_CEX_ID \n ");
				}
				if (StrUtil.isEmpty(dto.getOpeName())) {
					sqlStr.append(" AND EII.OPE_ID *= AO.AMS_OPE_ID \n ");
				} else {
					sqlStr.append(" AND EII.OPE_ID = AO.AMS_OPE_ID \n ");
				}
				if (StrUtil.isEmpty(dto.getLneName())) {
					sqlStr.append(" AND EII.NLE_ID *= AN.AMS_LNE_ID \n ");
				} else {
					sqlStr.append(" AND EII.NLE_ID = AN.AMS_LNE_ID \n ");
				}
				// sqlStr.append(" AND EII.LNE_ID = AL.AMS_LNE_ID \n");
				// sqlStr.append(" AND EII.CEX_ID = AC.AMS_CEX_ID \n");
				// sqlStr.append(" AND EII.OPE_ID = AO.AMS_OPE_ID \n");
				// sqlStr.append(" AND EII.NLE_ID = AN.AMS_LNE_ID \n");
			}

			if (!StrUtil.isEmpty(dto.getItemCategory())) {
				sqlStr.append("       AND ESI.ITEM_CATEGORY = ? \n");
				sqlArgs.add(dto.getItemCategory());
			}
			if (!StrUtil.isEmpty(dto.getItemName())) {
				sqlStr.append("       AND ESI.ITEM_NAME LIKE ? \n");
				sqlArgs.add(dto.getItemName());
			}
			if (!StrUtil.isEmpty(dto.getWorkorderObjectName())) {
				sqlStr
						.append("       AND (EO.WORKORDER_OBJECT_CODE LIKE ? OR \n");
				sqlStr.append("       EO.WORKORDER_OBJECT_NAME LIKE ? ) \n");
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
			}
			if (!StrUtil.isEmpty(dto.getResponsibilityUserName())) {
				sqlStr.append("       AND AME.USER_NAME LIKE ? \n");
				sqlArgs.add(dto.getResponsibilityUserName());
			}
			if (!StrUtil.isEmpty(dto.getProjectNumber())) {
				sqlStr.append("       AND EPPA.SEGMENT1 IN ("
						+ dto.getProjectNumber() + ")\n");
			}
			if (!StrUtil.isEmpty(dto.getSpecialityDept())) {
				sqlStr.append("       AND EII.SPECIALITY_DEPT = ? \n");
				sqlArgs.add(dto.getSpecialityDept());
			}
			if (!StrUtil.isEmpty(dto.getContentCode())) {
				sqlStr.append("       AND EII.CONTENT_CODE IN ("
						+ dto.getContentCode() + ") \n");
			}
			if (!StrUtil.isEmpty(dto.getItemStatus())) {
				sqlStr.append("       AND EII.ITEM_STATUS = ? \n");
				sqlArgs.add(dto.getItemStatus());
			}
			if (!StrUtil.isEmpty(dto.getMaintainUser())) {
				sqlStr.append("       AND EII.MAINTAIN_USER LIKE ? \n");
				sqlArgs.add(dto.getMaintainUser());
			}
			if (!StrUtil.isEmpty(dto.getMaintainDept())) {
				sqlStr.append("       AND EII.MAINTAIN_DEPT = ? \n");
				sqlArgs.add(dto.getMaintainDept());
			}
			if (!StrUtil.isEmpty(dto.getItemSpec())) {
				sqlStr.append("       AND ESI.ITEM_SPEC LIKE ? \n");
				sqlArgs.add(dto.getItemSpec());
			}
			if (!StrUtil.isEmpty(dto.getCreationDate())) {
				sqlStr.append("       AND EFA.ASSETS_CREATE_DATE >= ? \n");
				sqlArgs.add(dto.getCreationDate());
			}
			if (!StrUtil.isEmpty(dto.getSQLLastUpdateDate())) {
				sqlStr.append("       AND EFA.ASSETS_CREATE_DATE <= ? \n");
				sqlArgs.add(dto.getSQLLastUpdateDate());
			}
			if (!StrUtil.isEmpty(dto.getStartDate())) {
				sqlStr.append("       AND EFA.DATE_PLACED_IN_SERVICE >= ? \n");
				sqlArgs.add(dto.getStartDate());
			}
			if (!StrUtil.isEmpty(dto.getSQLEndDate())) {
				sqlStr.append("       AND EFA.DATE_PLACED_IN_SERVICE <= ? \n");
				sqlArgs.add(dto.getSQLEndDate());
			}
			if (!StrUtil.isEmpty(dto.getDeptName())) {
				sqlStr.append("       AND AMD.DEPT_NAME = ? \n");
				sqlArgs.add(dto.getDeptName());
			} else {
                if (!userAccount.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_SX)) { //SUHP加入山西个性化处理（2012.2.15）
                    if (!userAccount.isComAssetsManager()
						&& !userAccount.isMtlAssetsManager()
						&& !userAccount.isProvAssetsManager()) {
                        if (userAccount.isDptAssetsManager()) {
                            DTOSet depts = userAccount.getPriviDeptCodes();
                            String deptStr = "'";
                            if (null != depts) {
                                int deptCount = depts.getSize();

                                for (int i = 0; i < deptCount; i++) {
                                    AmsMisDeptDTO dept = (AmsMisDeptDTO) depts
                                            .getDTO(i);
                                    deptStr += dept.getDeptCode() + "'";
                                    if (i < deptCount - 1) {
                                        deptStr += ", '";
                                    }
                                }
                            }
                            sqlStr.append("AND EII.RESPONSIBILITY_DEPT IN ("
                                    + deptStr + ")");
                        } else {
                            sqlStr.append("AND EII.RESPONSIBILITY_USER = ?");
                            sqlArgs.add(userAccount.getEmployeeId());
                        }
                    }
                }
			}
			if (!StrUtil.isEmpty(dto.getFinanceProp())) {
				if(dto.getItemStatus().equals("PRE_ASSETS")||dto.getItemStatus().equals("TO_ASSETS")){
					
				}else{
				// TD_TT_ASSETS QD_DG_ASSETS
				if (dto.getFinanceProp().equals("TD_TT_ASSETS")) {
					sqlStr
							.append("       AND EII.FINANCE_PROP IN ( 'TD_ASSETS' , 'TT_ASSETS' ) \n");
				} else if (dto.getFinanceProp().equals("QD_DG_ASSETS")) {
					sqlStr
							.append("       AND EII.FINANCE_PROP IN ( 'QD_ASSETS' , 'DG_ASSETS' ) \n");
				} else {
					sqlStr.append("       AND EII.FINANCE_PROP = ? \n");
					sqlArgs.add(dto.getFinanceProp());
				}
			}
			}
			if (!StrUtil.isEmpty(dto.getAreaType())) {
				sqlStr.append("       AND EO.AREA_TYPE = ? \n");
				sqlArgs.add(dto.getAreaType());
			}
			if (!StrUtil.isEmpty(dto.getCity())) {
				sqlStr.append("       AND EO.CITY = ? \n");
				sqlArgs.add(dto.getCity());
			}
			if (!StrUtil.isEmpty(dto.getCounty())) {
				sqlStr.append("       AND EO.COUNTY = ? \n");
				sqlArgs.add(dto.getCounty());
			}

			if (!StrUtil.isEmpty(dto.getFromBarcode())) {
				sqlStr.append(" AND EII.BARCODE >= ? \n");
				sqlArgs.add(StrUtil.nullToString(dto.getFromBarcode()));
			}

			if (!StrUtil.isEmpty(dto.getToBarcode())) {
				sqlStr.append(" AND EII.BARCODE <= ? \n");
				sqlArgs.add(StrUtil.nullToString(dto.getToBarcode()));
			}

			// if ((!StrUtil.isEmpty(dto.getFromBarcode())) &&
			// (!StrUtil.isEmpty(dto.getToBarcode()))) {
			// sqlStr.append(" AND EII.BARCODE >= ? \n");
			// sqlStr.append(" AND EII.BARCODE <= ? \n");
			// sqlArgs.add(dto.getFromBarcode());
			// sqlArgs.add(dto.getToBarcode());
			// } else if (!(StrUtil.isEmpty(dto.getFromBarcode()) &&
			// StrUtil.isEmpty(dto.getToBarcode()))) {
			// sqlStr.append(" AND EII.BARCODE LIKE ? \n");
			// sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
			// }
			// 增加OU控制

			if (userAccount.isProvAssetsManager()) {
				sqlStr.append(" AND EII.ORGANIZATION_ID = ? \n");
				if (dto.getIsTD().equals("Y")) {
					if (financeProp.equals(DictConstant.FIN_PROP_ASSETS)) {
						sqlArgs.add(dto.getMatchOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_TD)
							|| financeProp.equals(DictConstant.FIN_PROP_TT)
							|| financeProp.equals("TD_TT_ASSETS")) {
						// TD_TT_ASSETS QD_DG_ASSETS
						sqlArgs.add(dto.getOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_DG)
							|| financeProp.equals(DictConstant.FIN_PROP_QD)
							|| financeProp.equals("QD_DG_ASSETS")) {
						sqlArgs.add(dto.getOrganizationId());
					}
				} else {
					if (financeProp.equals(DictConstant.FIN_PROP_ASSETS)) {
						sqlArgs.add(dto.getOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_TD)
							|| financeProp.equals(DictConstant.FIN_PROP_TT)
							|| financeProp.equals("TD_TT_ASSETS")) {
						sqlArgs.add(dto.getMatchOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_DG)
							|| financeProp.equals(DictConstant.FIN_PROP_QD)
							|| financeProp.equals("QD_DG_ASSETS")) {
						sqlArgs.add(dto.getOrganizationId());
					}
				}
			} else {
				sqlStr.append(" AND EII.ORGANIZATION_ID = ? \n");
				if (userAccount.getIsTd().equals("Y")) {
					if (financeProp.equals(DictConstant.FIN_PROP_ASSETS)) {
						sqlArgs.add(userAccount.getMatchOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_TD)
							|| financeProp.equals(DictConstant.FIN_PROP_TT)
							|| financeProp.equals("TD_TT_ASSETS")) {
						// TD_TT_ASSETS QD_DG_ASSETS
						sqlArgs.add(userAccount.getOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_DG)
							|| financeProp.equals(DictConstant.FIN_PROP_QD)
							|| financeProp.equals("QD_DG_ASSETS")) {
						sqlArgs.add(userAccount.getOrganizationId());
					}
				} else {
					if (financeProp.equals(DictConstant.FIN_PROP_ASSETS)) {
						sqlArgs.add(userAccount.getOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_TD)
							|| financeProp.equals(DictConstant.FIN_PROP_TT)
							|| financeProp.equals("TD_TT_ASSETS")) {
						sqlArgs.add(userAccount.getMatchOrganizationId());
					} else if (financeProp.equals(DictConstant.FIN_PROP_DG)
							|| financeProp.equals(DictConstant.FIN_PROP_QD)
							|| financeProp.equals("QD_DG_ASSETS")) {
						sqlArgs.add(userAccount.getOrganizationId());
					}
				}
			}

			//增加共享共建条件 sj add
			if (!StrUtil.isEmpty(dto.getShare())) {
				sqlStr.append(" AND EII.SHARE_STATUS = ? \n ");
				sqlArgs.add(dto.getShare());
			}
			if (!StrUtil.isEmpty(dto.getConstructStatus())) {
				sqlStr.append(" AND EII.CONSTRUCT_STATUS = ? \n ");
				sqlArgs.add(dto.getConstructStatus());
			}

			if (financeProp.equals(DictConstant.FIN_PROP_DG)
					|| financeProp.equals(DictConstant.FIN_PROP_QD)
					|| financeProp.equals("QD_DG_ASSETS")) {

			} else {

				if (!StrUtil.isEmpty(dto.getLogNetEle())) {
					sqlStr.append(" AND AL.LOG_NET_ELE LIKE ? \n ");
					sqlArgs.add(dto.getLogNetEle());
				}
				if (!StrUtil.isEmpty(dto.getInvestCatName())) {
					sqlStr.append(" AND AC.INVEST_CAT_NAME LIKE ? \n ");
					sqlArgs.add(dto.getInvestCatName());
				}
				if (!StrUtil.isEmpty(dto.getOpeName())) {
					sqlStr.append(" AND AO.OPE_NAME LIKE ? \n ");
					sqlArgs.add(dto.getOpeName());
				}
				if (!StrUtil.isEmpty(dto.getLneName())) {
					sqlStr.append(" AND AN.LNE_NAME LIKE ? \n ");
					sqlArgs.add(dto.getLneName());
				}

//				sqlStr.append(" AND ( " + SyBaseSQLUtil.isNull()
//						+ " OR AL.LOG_NET_ELE LIKE ?) \n ");
//				sqlStr.append(" AND ( " + SyBaseSQLUtil.isNull()
//						+ " OR AC.INVEST_CAT_NAME LIKE ?) \n ");
//				sqlStr.append(" AND ( " + SyBaseSQLUtil.isNull()
//						+ " OR AO.OPE_NAME LIKE ?) \n ");
//				sqlStr.append(" AND ( " + SyBaseSQLUtil.isNull()
//						+ " OR AN.LNE_NAME LIKE ?) \n ");
			}


//			if (financeProp.equals(DictConstant.FIN_PROP_DG)
//					|| financeProp.equals(DictConstant.FIN_PROP_QD)
//					|| financeProp.equals("QD_DG_ASSETS")) {
//
//			} else {
//				sqlArgs.add(dto.getLogNetEle());
//				sqlArgs.add(dto.getLogNetEle());
//				sqlArgs.add(dto.getInvestCatName());
//				sqlArgs.add(dto.getInvestCatName());
//				sqlArgs.add(dto.getOpeName());
//				sqlArgs.add(dto.getOpeName());
//				sqlArgs.add(dto.getLneName());
//				sqlArgs.add(dto.getLneName());
//			}

			sqlModel.setSqlStr(sqlStr.toString());
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	private SQLModel getCommonAssetsSearchModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		StringBuilder sqlStr = new StringBuilder();
		List sqlArgs = new ArrayList();
		AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
		try {
			sqlStr.append(" SELECT \n");
			sqlStr.append("       EII.BARCODE, \n");
			sqlStr.append("       ESI.ITEM_CODE, \n");
			sqlStr.append("       ESI.ITEM_CATEGORY, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME, \n");
			sqlStr.append("       ESI.ITEM_NAME, \n");
			sqlStr.append("       ESI.ITEM_SPEC, \n");
			sqlStr.append("       ESI.YEARS, \n");
			sqlStr.append("       ESI.ITEM_UNIT, \n");
			sqlStr.append("       EII.ITEM_QTY, \n");
			sqlStr.append("       EII.ACTUAL_QTY, \n");
			sqlStr.append("       EII.START_DATE, \n");
			sqlStr.append("       EO.WORKORDER_OBJECT_CODE, \n");
			sqlStr.append("       EO.WORKORDER_OBJECT_NAME, \n");
			sqlStr.append("       EO.WORKORDER_OBJECT_LOCATION, \n");
			sqlStr
					.append("       dbo.APP_GET_AMS_COUNTY_NAME( EO.CITY )  CITY, \n");
			sqlStr
					.append("       dbo.APP_GET_AMS_COUNTY_NAME( EO.COUNTY )   COUNTY, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EO.AREA_TYPE, 'ADDR_AREA_TYPE') AREA_TYPE_NAME, \n");
			sqlStr.append("       AME.USER_NAME RESPONSIBILITY_USER_NAME, \n");
			sqlStr.append("       AME.EMPLOYEE_NUMBER, \n");
			sqlStr.append("       AMD.DEPT_CODE, \n");
			sqlStr.append("       AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME, \n");
			sqlStr.append("       EPPA.NAME PROJECT_NAME, \n");
			sqlStr.append("       EPPA.SEGMENT1 PROJECT_NUMBER, \n");
			// sqlStr.append(" EMPV.VENDOR_NAME, \n");
			// sqlStr.append(" EMPV.SEGMENT1 VENDOR_NUMBER, \n");

			sqlStr.append("       '' VENDOR_NAME, \n");
			sqlStr.append("       '' VENDOR_NUMBER, \n");

			sqlStr.append("       EII.FINANCE_PROP, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.ITEM_STATUS, 'ITEM_STATUS') ITEM_STATUS_NAME, \n");
			sqlStr.append("       EII.RESPONSIBILITY_USER, \n");
			sqlStr.append("       EII.RESPONSIBILITY_DEPT, \n");
			sqlStr
					.append("	      dbo.AMP_GET_DEPT_NAME( EII.SPECIALITY_DEPT ) SPECIALITY_DEPT_NAME, \n");
			sqlStr.append("       EII.MAINTAIN_USER, \n");
			sqlStr.append("       EII.MAINTAIN_DEPT, \n");
			sqlStr
					.append("       dbo.APP_GET_DEPT_NAME(EII.MAINTAIN_DEPT) MAINTAIN_DEPT_NAME, \n");
			sqlStr.append("       EII.ADDRESS_ID, \n");
			sqlStr.append("       EOCM.COMPANY_CODE, \n");
			sqlStr.append("       EOCM.COMPANY, \n");
			sqlStr.append("       AM.MANUFACTURER_CODE, \n");
			sqlStr.append("       AM.MANUFACTURER_NAME, \n");
			sqlStr.append("       EII.CONTENT_CODE, \n");
			sqlStr.append("       EII.CONTENT_NAME, \n");
			sqlStr.append("       EII.POWER, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.SHARE_STATUS, 'SHARE_STATUS') IS_SHARE, \n");
			sqlStr
					.append("       dbo.APP_GET_FLEX_VALUE(EII.CONSTRUCT_STATUS, 'CONSTRUCT_STATUS') CONSTRUCT_STATUS, \n");
			sqlStr.append("       AL.LOG_NET_ELE, \n");
			sqlStr.append("       AC.INVEST_CAT_NAME, \n");
			sqlStr.append("       AO.OPE_NAME, \n");
			sqlStr.append("       AN.LNE_NAME, \n");
			sqlStr.append("       NULL ASSET_ID, \n");
//			sqlStr.append("       ESI.YEARS LIFE_IN_YEARS, \n");
//			sqlStr.append("       EII.START_DATE DATE_PLACED_IN_SERVICE, \n");
//			sqlStr.append("       EII.CREATION_DATE ASSETS_CREATE_DATE, \n");
			sqlStr.append("       0 LIFE_IN_YEARS, \n");
			sqlStr.append("       '' DATE_PLACED_IN_SERVICE, \n");
			sqlStr.append("       '' ASSETS_CREATE_DATE, \n");
			sqlStr.append("       EII.PRICE ORIGINAL_COST, \n");
			sqlStr.append("       NULL SCRAP_VALUE, \n");
			sqlStr.append("       NULL IMPAIR_RESERVE, \n");
			sqlStr.append("       NULL DEPRN_RESERVE, \n");
			sqlStr.append("       EII.TF_NET_ASSET_VALUE NET_ASSET_VALUE, \n");
			sqlStr.append("       EII.TF_DEPRN_COST DEPRN_COST, \n");
			sqlStr.append("       EII.REMARK, \n");
			sqlStr.append("       EII.REMARK1, \n");
			sqlStr.append("       EII.REMARK2 \n");
			sqlStr.append("  FROM \n");
			sqlStr.append("       AMS_LNE                AL, \n");
			sqlStr.append("       AMS_CEX                AC, \n");
			sqlStr.append("       AMS_OPE                AO, \n");
			sqlStr.append("       AMS_NLE                AN, \n");
			sqlStr.append("       ETS_ITEM_INFO          EII, \n");
			sqlStr.append("       ETS_SYSTEM_ITEM        ESI, \n");
			sqlStr.append("       AMS_OBJECT_ADDRESS     AOA, \n");
			sqlStr.append("       ETS_OBJECT             EO, \n");
			sqlStr.append("       ETS_OU_CITY_MAP        EOCM, \n");
			sqlStr.append("       AMS_MIS_DEPT           AMD, \n");
			sqlStr.append("       AMS_MIS_EMPLOYEE       AME, \n");
			sqlStr.append("       ETS_PA_PROJECTS_ALL    EPPA, \n");
			// sqlStr.append(" ETS_MIS_PO_VENDORS EMPV, \n");
			sqlStr.append("       AMS_MANUFACTURER       AM \n");
			sqlStr.append("   WHERE  \n");
			sqlStr.append("       EII.ITEM_CODE = ESI.ITEM_CODE \n");
			sqlStr
					.append("       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID \n");
			sqlStr.append("       AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n");
			sqlStr
					.append("       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n");
//			if (dto.getDeptName().equals("")) {
//				sqlStr
//						.append("       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE \n");
//			} else {
				sqlStr
						.append("       AND EII.RESPONSIBILITY_DEPT = AMD.DEPT_CODE \n");
//			}

			// sqlStr.append(" AND ESI.VENDOR_ID *= EMPV.VENDOR_ID \n");
			sqlStr
					.append("       AND EII.MANUFACTURER_ID *= AM.MANUFACTURER_ID \n");
			sqlStr
					.append("       AND EII.RESPONSIBILITY_USER = AME.EMPLOYEE_ID \n");

			if (dto.getProjectNumber().equals("")) {
				sqlStr.append("       AND EII.PROJECTID *= EPPA.PROJECT_ID \n");
			} else {
				sqlStr.append("       AND EII.PROJECTID = EPPA.PROJECT_ID \n");
			}

			if (StrUtil.isEmpty(dto.getLogNetEle())) {
				sqlStr.append(" AND EII.LNE_ID *= AL.AMS_LNE_ID \n ");
			} else {
				sqlStr.append(" AND EII.LNE_ID = AL.AMS_LNE_ID \n ");
			}
			if (StrUtil.isEmpty(dto.getInvestCatName())) {
				sqlStr.append(" AND EII.CEX_ID *= AC.AMS_CEX_ID \n ");
			} else {
				sqlStr.append(" AND EII.CEX_ID = AC.AMS_CEX_ID \n ");
			}
			if (StrUtil.isEmpty(dto.getOpeName())) {
				sqlStr.append(" AND EII.OPE_ID *= AO.AMS_OPE_ID \n ");
			} else {
				sqlStr.append(" AND EII.OPE_ID = AO.AMS_OPE_ID \n ");
			}
			if (StrUtil.isEmpty(dto.getLneName())) {
				sqlStr.append(" AND EII.NLE_ID *= AN.AMS_LNE_ID \n ");
			} else {
				sqlStr.append(" AND EII.NLE_ID = AN.AMS_LNE_ID \n ");
			}

			// sqlStr.append(" AND EII.LNE_ID *= AL.AMS_LNE_ID \n");
			// sqlStr.append(" AND EII.CEX_ID *= AC.AMS_CEX_ID \n");
			// sqlStr.append(" AND EII.OPE_ID *= AO.AMS_OPE_ID \n");
			// sqlStr.append(" AND EII.NLE_ID *= AN.AMS_LNE_ID \n");

			if (!StrUtil.isEmpty(dto.getItemCategory())) {
				sqlStr.append("       AND ESI.ITEM_CATEGORY = ? \n");
				sqlArgs.add(dto.getItemCategory());
			}
			if (!StrUtil.isEmpty(dto.getItemName())) {
				sqlStr.append("       AND ESI.ITEM_NAME LIKE ? \n");
				sqlArgs.add(dto.getItemName());
			}
			if (!StrUtil.isEmpty(dto.getWorkorderObjectName())) {
				sqlStr
						.append("       AND (EO.WORKORDER_OBJECT_CODE LIKE ? OR \n");
				sqlStr.append("       EO.WORKORDER_OBJECT_NAME LIKE ? ) \n");
				sqlArgs.add(dto.getWorkorderObjectName());
				sqlArgs.add(dto.getWorkorderObjectName());
			}
			if (!StrUtil.isEmpty(dto.getResponsibilityUserName())) {
				sqlStr.append("       AND AME.USER_NAME LIKE ? \n");
				sqlArgs.add(dto.getResponsibilityUserName());
			}
			if (!StrUtil.isEmpty(dto.getProjectNumber())) {
				sqlStr.append("       AND EPPA.SEGMENT1 IN ("
						+ dto.getProjectNumber() + ")\n");
			}
			if (!StrUtil.isEmpty(dto.getSpecialityDept())) {
				sqlStr.append("       AND EII.SPECIALITY_DEPT = ? \n");
				sqlArgs.add(dto.getSpecialityDept());
			}
			// if (!StrUtil.isEmpty(dto.getContentName())) {
			// sqlStr.append(" AND EII.CONTENT_NAME LIKE ? \n");
			// sqlArgs.add(dto.getContentName());
			// }
			// if (!StrUtil.isEmpty(dto.getContentCode())) {
			// sqlStr.append(" AND EII.CONTENT_CODE LIKE ? \n");
			// sqlArgs.add(dto.getContentCode());
			// }
			if (!StrUtil.isEmpty(dto.getContentCode())) {
				sqlStr.append("       AND EII.CONTENT_CODE IN ("
						+ dto.getContentCode() + ") \n");
			}
			if (!StrUtil.isEmpty(dto.getShare())) {
				sqlStr.append("       AND EII.SHARE_STATUS = ? \n");
				sqlArgs.add(dto.getShare());
			}
			if (!StrUtil.isEmpty(dto.getPower())) {
				sqlStr.append("       AND EII.POWER LIKE ? \n");
				sqlArgs.add(dto.getPower());
			}
			if (!StrUtil.isEmpty(dto.getItemStatus())) {
				sqlStr.append("       AND EII.ITEM_STATUS = ? \n");
				sqlArgs.add(dto.getItemStatus());
			}
			if (!StrUtil.isEmpty(dto.getMaintainUser())) {
				sqlStr.append("       AND EII.MAINTAIN_USER LIKE ? \n");
				sqlArgs.add(dto.getMaintainUser());
			}
			if (!StrUtil.isEmpty(dto.getMaintainDept())) {
				sqlStr.append("       AND EII.MAINTAIN_DEPT = ? \n");
				sqlArgs.add(dto.getMaintainDept());
			}
			if (!StrUtil.isEmpty(dto.getItemSpec())) {
				sqlStr.append("       AND ESI.ITEM_SPEC LIKE ? \n");
				sqlArgs.add(dto.getItemSpec());
			}
			if (!StrUtil.isEmpty(dto.getCreationDate())) {
				sqlStr.append("       AND EII.CREATION_DATE >= ? \n");
				sqlArgs.add(dto.getCreationDate());
			}
			if (!StrUtil.isEmpty(dto.getSQLLastUpdateDate())) {
				sqlStr.append("       AND EII.CREATION_DATE <= ? \n");
				sqlArgs.add(dto.getSQLLastUpdateDate());
			}
			if (!StrUtil.isEmpty(dto.getStartDate())) {
				sqlStr.append("       AND EII.START_DATE >= ? \n");
				sqlArgs.add(dto.getStartDate());
			}
			if (!StrUtil.isEmpty(dto.getSQLEndDate())) {
				sqlStr.append("       AND EII.START_DATE <= ? \n");
				sqlArgs.add(dto.getSQLEndDate());
			}
			if (dto.getActualQty() >= 0) {
				sqlStr.append("       AND EII.ACTUAL_QTY = ? \n");
				sqlArgs.add(dto.getActualQty());
			}
			// if (!StrUtil.isEmpty(dto.getCompany())) {
			// sqlStr.append(" AND EOCM.COMPANY = ? \n");
			// sqlArgs.add(dto.getCompany());
			// }
			if (!StrUtil.isEmpty(dto.getDeptName())) {
				sqlStr.append("       AND AMD.DEPT_NAME = ? \n");
				sqlArgs.add(dto.getDeptName());
			} else {
                if (!userAccount.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_SX)) { //SUHP加入山西个性化处理（2012.2.15）
                    if (!userAccount.isComAssetsManager()
						&& !userAccount.isMtlAssetsManager()) {
                        if (userAccount.isDptAssetsManager()) {
                            DTOSet depts = userAccount.getPriviDeptCodes();
                            String deptStr = "'";
                            if (null != depts) {
                                int deptCount = depts.getSize();

                                for (int i = 0; i < deptCount; i++) {
                                    AmsMisDeptDTO dept = (AmsMisDeptDTO) depts
                                            .getDTO(i);
                                    deptStr += dept.getDeptCode() + "'";
                                    if (i < deptCount - 1) {
                                        deptStr += ", '";
                                    }
                                }
                            }
                            sqlStr.append("AND EII.RESPONSIBILITY_DEPT IN ("
                                    + deptStr + ")");
                        } else {
                            sqlStr.append("AND EII.RESPONSIBILITY_USER = ?");
                            sqlArgs.add(userAccount.getEmployeeId());
                        }
                    }
                }
			}
			if (!StrUtil.isEmpty(dto.getFinanceProp())) {

				if (dto.getFinanceProp().equals("OTHERS_OF_ALL")) {
					sqlStr
							.append("       AND EII.FINANCE_PROP IN ( 'DH_ASSETS','OTHERS','RENT_ASSETS','SPARE','UNKNOW' ) \n");
				} else {
					if(dto.getItemStatus().equals("PRE_ASSETS")||dto.getItemStatus().equals("TO_ASSETS")){
						
					}else{
					sqlStr.append("       AND EII.FINANCE_PROP = ? \n");
					sqlArgs.add(dto.getFinanceProp());
					}
				}

			}
			if (!StrUtil.isEmpty(dto.getAreaType())) {
				sqlStr.append("       AND EO.AREA_TYPE = ? \n");
				sqlArgs.add(dto.getAreaType());
			}
			if (!StrUtil.isEmpty(dto.getCity())) {
				sqlStr.append("       AND EO.CITY = ? \n");
				sqlArgs.add(dto.getCity());
			}
			if (!StrUtil.isEmpty(dto.getCounty())) {
				sqlStr.append("       AND EO.COUNTY = ? \n");
				sqlArgs.add(dto.getCounty());
			}

			if ((!StrUtil.isEmpty(dto.getFromBarcode()))
					&& (!StrUtil.isEmpty(dto.getToBarcode()))) {
				sqlStr.append(" AND EII.BARCODE >= ? \n");
				sqlStr.append(" AND EII.BARCODE <= ? \n");
				sqlArgs.add(dto.getFromBarcode());
				sqlArgs.add(dto.getToBarcode());
			} else if (!(StrUtil.isEmpty(dto.getFromBarcode()) && StrUtil
					.isEmpty(dto.getToBarcode()))) {
				sqlStr.append(" AND EII.BARCODE LIKE ? \n");
				sqlArgs.add(dto.getFromBarcode() + dto.getToBarcode());
			}
			if (dto.getCheck().equals("Y")) {
				sqlStr.append(" AND EXISTS (SELECT NULL \n");
				sqlStr.append("          FROM \n");
				sqlStr.append("          AMS_ASSETS_CHECK_LINE   AACL, \n");
				sqlStr.append("          AMS_ASSETS_CHECK_HEADER AACH, \n");
				sqlStr.append("         WHERE EII.BARCODE = AACL.BARCODE \n");
				sqlStr
						.append("               AND AACL.HEADER_ID = AACH.HEADER_ID \n");
				sqlStr
						.append("               AND AACH.ORDER_STATUS <> 'CANCELED' \n");
				sqlStr
						.append("               AND AACL.ARCHIVE_STATUS = '') \n");
			} else if (dto.getCheck().equals("N")) {
				sqlStr.append(" AND NOT EXISTS (SELECT NULL \n");
				sqlStr.append("          FROM \n");
				sqlStr.append("          AMS_ASSETS_CHECK_LINE   AACL, \n");
				sqlStr.append("          AMS_ASSETS_CHECK_HEADER AACH, \n");
				sqlStr.append("         WHERE EII.BARCODE = AACL.BARCODE \n");
				sqlStr
						.append("               AND AACL.HEADER_ID = AACH.HEADER_ID \n");
				sqlStr
						.append("               AND AACH.ORDER_STATUS <> 'CANCELED' \n");
				sqlStr
						.append("               AND AACL.ARCHIVE_STATUS = '') \n");
			}
			// 增加OU控制
			if (userAccount.isProvAssetsManager()) {
				sqlStr.append(" AND EII.ORGANIZATION_ID = ? \n");
				sqlArgs.add(dto.getOrganizationId());
			} else {
				sqlStr.append(" AND EII.ORGANIZATION_ID = ? \n");
				sqlArgs.add(userAccount.getOrganizationId());
			}

			// 增加共享共建条件
			if (!StrUtil.isEmpty(dto.getShare())) {
				sqlStr.append(" AND EII.SHARE_STATUS = ? \n ");
				sqlArgs.add(dto.getShare());
			}
			if (!StrUtil.isEmpty(dto.getConstructStatus())) {
				sqlStr.append(" AND EII.CONSTRUCT_STATUS = ? \n ");
				sqlArgs.add(dto.getConstructStatus());
			}

			if (!StrUtil.isEmpty(dto.getLogNetEle())) {
				sqlStr.append(" AND AL.LOG_NET_ELE LIKE ? \n ");
				sqlArgs.add(dto.getLogNetEle());
			}
			if (!StrUtil.isEmpty(dto.getInvestCatName())) {
				sqlStr.append(" AND AC.INVEST_CAT_NAME LIKE ? \n ");
				sqlArgs.add(dto.getInvestCatName());
			}
			if (!StrUtil.isEmpty(dto.getOpeName())) {
				sqlStr.append(" AND AO.OPE_NAME LIKE ? \n ");
				sqlArgs.add(dto.getOpeName());
			}
			if (!StrUtil.isEmpty(dto.getLneName())) {
				sqlStr.append(" AND AN.LNE_NAME LIKE ? \n ");
				sqlArgs.add(dto.getLneName());
			}

			sqlModel.setSqlStr(sqlStr.toString());
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
