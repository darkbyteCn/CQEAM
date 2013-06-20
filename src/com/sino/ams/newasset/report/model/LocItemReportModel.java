package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class LocItemReportModel extends AMSSQLProducer {
	public LocItemReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getOwnItemModel(){
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		String sqlStr = "SELECT"
						+ " EII.BARCODE,"
						+ " ESI.ITEM_CODE,"
						+ " ESI.ITEM_CATEGORY,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
						+ " ESI.ITEM_NAME,"
						+ " ESI.ITEM_SPEC,"
						+ " ESI.YEARS,"
						+ " ESI.ITEM_UNIT,"
						+ " EII.START_DATE,"
						+ " EII.FINANCE_PROP,"
						+ " EII.MAINTAIN_USER,"
						+ " SG.GROUP_NAME MAINTAIN_DEPT_NAME,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,"
						+ " EO.WORKORDER_OBJECT_NO,"
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
						+ " EOCM.COMPANY_CODE,"
						+ " EOCM.COMPANY"
						+ " FROM"
						+ " ETS_ITEM_INFO          EII,"
						+ " ETS_SYSTEM_ITEM        ESI,"
						+ " ETS_SYSITEM_DISTRIBUTE ESD,"
						+ " AMS_OBJECT_ADDRESS     AOA,"
						+ " ETS_OBJECT             EO,"
						+ " ETS_COUNTY             EC,"
						+ " ETS_OU_CITY_MAP        EOCM,"
						+ " AMS_MIS_DEPT           AMD,"
						+ " AMS_MIS_EMPLOYEE       AME,"
						+ " ETS_PA_PROJECTS_ALL    EPPA,"
						+ " ETS_MIS_PO_VENDORS     EMPV,"
						+ " SF_GROUP               SG"
						+ " WHERE"
						+ " EII.ITEM_CODE = ESI.ITEM_CODE"
						+ " AND ESI.ITEM_CODE = ESD.ITEM_CODE"
						+ " AND ESI.VENDOR_ID *= EMPV.VENDOR_ID"
						+ " AND EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID"
						+ " AND ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
						+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
						+ " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO"
						+ " AND EO.COUNTY_CODE = EC.COUNTY_CODE"
                        + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE "
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
						+ " AND EII.PROJECTID *= EPPA.PROJECT_ID"
						+ " AND EII.MAINTAIN_DEPT *= SG.GROUP_ID"
						+ " AND EO.WORKORDER_OBJECT_NO = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getCheckLocation());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getScanedItemModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		try {
			String sqlStr = "SELECT"
							+ " AACL.BARCODE,"
							+ " ESI.ITEM_CODE,"
							+ " ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY), 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " ISNULL(AACL.SCAN_ITEM_NAME, ESI.ITEM_NAME) ITEM_NAME,"
							+ " ISNULL(AACL.SCAN_ITEM_SPEC, ESI.ITEM_SPEC) ITEM_SPEC,"
							+ " ESI.YEARS,"
							+ " ESI.ITEM_UNIT,"
							+ " EII.START_DATE,"
							+ " EII.FINANCE_PROP,"
							+ " AACL.MAINTAIN_USER,"
							+ " SG.GROUP_NAME MAINTAIN_DEPT_NAME,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP, 'FINANCE_PROP') FINANCE_PROP_VALUE,"
							+ " EO.WORKORDER_OBJECT_NO,"
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
							+ " EOCM.COMPANY_CODE,"
							+ " EOCM.COMPANY"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH,"
							+ " AMS_ASSETS_CHECK_LINE   AACL,"
							+ " ETS_SYSTEM_ITEM         ESI,"
							+ " AMS_MIS_DEPT            AMD,"
							+ " AMS_MIS_EMPLOYEE        AME,"
							+ " ETS_ITEM_INFO           EII,"
							+ " ETS_OBJECT              EO,"
							+ " ETS_COUNTY              EC,"
							+ " ETS_OU_CITY_MAP         EOCM,"
							+ " SF_GROUP                SG,"
							+ " ETS_PA_PROJECTS_ALL     EPPA,"
							+ " ETS_MIS_PO_VENDORS      EMPV"
							+ " WHERE"
							+ " AACH.HEADER_ID = AACL.HEADER_ID"
							+ " AND AACL.BARCODE *= EII.BARCODE"
							+ " AND ISNULL(AACL.SCAN_ITEM_CODE, EII.ITEM_CODE) = ESI.ITEM_CODE"
							+ " AND ESI.VENDOR_ID *= EMPV.VENDOR_ID"
							+ " AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO"
							+ " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EO.COUNTY_CODE = EC.COUNTY_CODE"
                            + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE "
							+ " AND AACL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
							+ " AND AACL.SCAN_RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
							+ " AND EII.PROJECTID *= EPPA.PROJECT_ID"
							+ " AND EII.MAINTAIN_DEPT *= SG.GROUP_ID"
							+ " AND AACL.SCAN_STATUS = 'Y'"
							+ " AND AACH.HEADER_ID ="
							+ " (SELECT"
							+ " MAX(AACH2.HEADER_ID)"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH2,"
							+ " AMS_ASSETS_CHECK_LINE   AACL2"
							+ " WHERE"
							+ " AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION"
							+ " AND AACH2.HEADER_ID = AACL2.HEADER_ID"
							+ " AND AACL2.BARCODE = AACL.BARCODE"
							+ " AND AACH2.CHECK_LOCATION = ?"
							+ " AND (AACH2.ORDER_STATUS = ? OR AACH2.ORDER_STATUS = ?)"
							+ " AND ((AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE)"
							+ " AND AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE))"
							+ " OR (AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE)"
							+ " AND AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE))))";
			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getCheckLocation());
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取指定地点的盘点差异报表
	 * <B>不是我不想美化SQL，实在是太长了。</B>
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getDiffItemModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		try {
			String sqlStr =
"SELECT *\n" +
"FROM   (SELECT SYS_SCAN_V.BARCODE,\n" +
"               SYS_SCAN_V.ITEM_CODE,\n" +
"               SYS_SCAN_V.ITEM_CATEGORY,\n" +
"               SYS_SCAN_V.ITEM_CATEGORY_NAME,\n" +
"               SYS_SCAN_V.ITEM_NAME,\n" +
"               SYS_SCAN_V.ITEM_SPEC,\n" +
"               SYS_SCAN_V.YEARS,\n" +
"               SYS_SCAN_V.ITEM_UNIT,\n" +
"               SYS_SCAN_V.START_DATE,\n" +
"               SYS_SCAN_V.FINANCE_PROP,\n" +
"               SYS_SCAN_V.MAINTAIN_USER,\n" +
"               SYS_SCAN_V.MAINTAIN_DEPT_NAME,\n" +
"               SYS_SCAN_V.FINANCE_PROP_VALUE,\n" +
"               SYS_SCAN_V.WORKORDER_OBJECT_NO,\n" +
"               SYS_SCAN_V.WORKORDER_OBJECT_CODE,\n" +
"               SYS_SCAN_V.WORKORDER_OBJECT_NAME,\n" +
"               SYS_SCAN_V.WORKORDER_OBJECT_LOCATION,\n" +
"               SYS_SCAN_V.COUNTY_CODE,\n" +
"               SYS_SCAN_V.COUNTY_NAME,\n" +
"               SYS_SCAN_V.RESPONSIBILITY_USER_NAME,\n" +
"               SYS_SCAN_V.EMPLOYEE_NUMBER,\n" +
"               SYS_SCAN_V.RESPONSIBILITY_DEPT_NAME,\n" +
"               SYS_SCAN_V.PROJECT_NAME,\n" +
"               SYS_SCAN_V.PROJECT_NUMBER,\n" +
"               SYS_SCAN_V.VENDOR_NAME,\n" +
"               SYS_SCAN_V.VENDOR_NUMBER,\n" +
"               SYS_SCAN_V.COMPANY_CODE,\n" +
"               SYS_SCAN_V.COMPANY,\n" +
"               NULL SCAN_ITEM_CODE,\n" +
"               NULL SCAN_ITEM_CATEGORY,\n" +
"               NULL SCAN_ITEM_CATEGORY_NAME,\n" +
"               NULL SCAN_ITEM_NAME,\n" +
"               NULL SCAN_ITEM_SPEC,\n" +
"               NULL SCAN_MAINTAIN_USER,\n" +
"               NULL SCAN_MAINTAIN_DEPT_NAME,\n" +
"               NULL SCAN_RESPONSIBILITY_USER_NAME,\n" +
"               NULL SCAN_EMPLOYEE_NUMBER,\n" +
"               NULL SCAN_RESPONSIBILITY_DEPT_NAME,\n" +
"               NULL SCAN_VENDOR_NAME,\n" +
"               NULL SCAN_VENDOR_NUMBER,\n" +
"               ISNULL(NULL, '有') SYS_STATUS,\n" +
"               ISNULL(NULL, '无') SCAN_STATUS\n" +
"        FROM   (SELECT *\n" +
"                FROM   (SELECT EII.BARCODE,\n" +
"                               ESI.ITEM_CODE,\n" +
"                               ESI.ITEM_CATEGORY,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,\n" +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
"                               ESI.ITEM_NAME,\n" +
"                               ESI.ITEM_SPEC,\n" +
"                               ESI.YEARS,\n" +
"                               ESI.ITEM_UNIT,\n" +
"                               EII.START_DATE,\n" +
"                               EII.FINANCE_PROP,\n" +
"                               EII.MAINTAIN_USER,\n" +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP,\n" +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE,\n" +
"                               EO.WORKORDER_OBJECT_NO,\n" +
"                               EO.WORKORDER_OBJECT_CODE,\n" +
"                               EO.WORKORDER_OBJECT_NAME,\n" +
"                               EO.WORKORDER_OBJECT_LOCATION,\n" +
"                               EC.COUNTY_CODE,\n" +
"                               EC.COUNTY_NAME,\n" +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
"                               AME.EMPLOYEE_NUMBER,\n" +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
"                               EPPA.NAME PROJECT_NAME,\n" +
"                               EPPA.SEGMENT1 PROJECT_NUMBER,\n" +
"                               EMPV.VENDOR_NAME,\n" +
"                               EMPV.SEGMENT1 VENDOR_NUMBER,\n" +
"                               EOCM.COMPANY_CODE,\n" +
"                               EOCM.COMPANY\n" +
"                        FROM   ETS_ITEM_INFO          EII,\n" +
"                               ETS_SYSTEM_ITEM        ESI,\n" +
"                               ETS_SYSITEM_DISTRIBUTE ESD,\n" +
"                               AMS_OBJECT_ADDRESS     AOA,\n" +
"                               ETS_OBJECT             EO,\n" +
"                               ETS_COUNTY             EC,\n" +
"                               ETS_OU_CITY_MAP        EOCM,\n" +
"                               AMS_MIS_DEPT           AMD,\n" +
"                               AMS_MIS_EMPLOYEE       AME,\n" +
"                               ETS_PA_PROJECTS_ALL    EPPA,\n" +
"                               ETS_MIS_PO_VENDORS     EMPV,\n" +
"                               SF_GROUP               SG\n" +
"                        WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
"                               AND ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
"                               AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
"                               AND EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID\n" +
"                               AND ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
"                               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
"                               AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
"                               AND EO.COUNTY_CODE = EC.COUNTY_CODE\n" +
"                               AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n"+
"                               AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
"                               AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
"                               AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
"                               AND EII.MAINTAIN_DEPT *= SG.GROUP_ID\n" +
"                               AND EO.WORKORDER_OBJECT_NO = ?) SYS_V\n" +
"                MINUS\n" +
"                SELECT *\n" +
"                FROM   (SELECT AACL.BARCODE,\n" +
"                               ESI.ITEM_CODE,\n" +
"                               ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) ITEM_CATEGORY,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ISNULL(AACL.SCAN_ITEM_CATEGORY,\n" +
"                                                              ESI.ITEM_CATEGORY),\n" +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
"                               ISNULL(AACL.SCAN_ITEM_NAME, ESI.ITEM_NAME) ITEM_NAME,\n" +
"                               ISNULL(AACL.SCAN_ITEM_SPEC, ESI.ITEM_SPEC) ITEM_SPEC,\n" +
"                               ESI.YEARS,\n" +
"                               ESI.ITEM_UNIT,\n" +
"                               EII.START_DATE,\n" +
"                               EII.FINANCE_PROP,\n" +
"                               AACL.MAINTAIN_USER,\n" +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP,\n" +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE,\n" +
"                               EO.WORKORDER_OBJECT_NO,\n" +
"                               EO.WORKORDER_OBJECT_CODE,\n" +
"                               EO.WORKORDER_OBJECT_NAME,\n" +
"                               EO.WORKORDER_OBJECT_LOCATION,\n" +
"                               EC.COUNTY_CODE,\n" +
"                               EC.COUNTY_NAME,\n" +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
"                               AME.EMPLOYEE_NUMBER,\n" +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
"                               EPPA.NAME PROJECT_NAME,\n" +
"                               EPPA.SEGMENT1 PROJECT_NUMBER,\n" +
"                               EMPV.VENDOR_NAME,\n" +
"                               EMPV.SEGMENT1 VENDOR_NUMBER,\n" +
"                               EOCM.COMPANY_CODE,\n" +
"                               EOCM.COMPANY\n" +
"                        FROM   AMS_ASSETS_CHECK_HEADER AACH,\n" +
"                               AMS_ASSETS_CHECK_LINE   AACL,\n" +
"                               ETS_SYSTEM_ITEM         ESI,\n" +
"                               AMS_MIS_DEPT            AMD,\n" +
"                               AMS_MIS_EMPLOYEE        AME,\n" +
"                               ETS_ITEM_INFO           EII,\n" +
"                               ETS_OBJECT              EO,\n" +
"                               ETS_COUNTY              EC,\n" +
"                               ETS_OU_CITY_MAP         EOCM,\n" +
"                               SF_GROUP                SG,\n" +
"                               ETS_PA_PROJECTS_ALL     EPPA,\n" +
"                               ETS_MIS_PO_VENDORS      EMPV\n" +
"                        WHERE  AACH.HEADER_ID = AACL.HEADER_ID\n" +
"                               AND AACL.BARCODE *= EII.BARCODE\n" +
"                               AND ISNULL(AACL.SCAN_ITEM_CODE, EII.ITEM_CODE) =\n" +
"                               ESI.ITEM_CODE\n" +
"                               AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
"                               AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
"                               AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
"                               AND EO.COUNTY_CODE = EC.COUNTY_CODE\n" +
"                               AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n"+
"                               AND\n" +
"                               AACL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
"                               AND\n" +
"                               AACL.SCAN_RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
"                               AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
"                               AND EII.MAINTAIN_DEPT *= SG.GROUP_ID\n" +
"                               AND AACL.SCAN_STATUS = 'Y'\n" +
"                               AND\n" +
"                               AACH.HEADER_ID =\n" +
"                               (SELECT MAX(AACH2.HEADER_ID)\n" +
"                                FROM   AMS_ASSETS_CHECK_HEADER AACH2,\n" +
"                                       AMS_ASSETS_CHECK_LINE   AACL2\n" +
"                                WHERE  AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION\n" +
"                                       AND AACH2.HEADER_ID = AACL2.HEADER_ID\n" +
"                                       AND AACL2.BARCODE = AACL.BARCODE\n" +
"                                       AND AACH2.CHECK_LOCATION = ?\n" +
"                                       AND (AACH2.ORDER_STATUS = 'UPLOADED' OR\n" +
"                                       AACH2.ORDER_STATUS = 'ARCHIEVED')\n" +
"                                       AND ((AACH2.UPLOAD_DATE >=\n" +
"                                       ISNULL(?, AACH2.UPLOAD_DATE) AND\n" +
"                                       AACH2.UPLOAD_DATE <=\n" +
"                                       ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
"                                       (AACH2.ARCHIVED_DATE >=\n" +
"                                       ISNULL(?, AACH2.ARCHIVED_DATE) AND\n" +
"                                       AACH2.ARCHIVED_DATE <=\n" +
"                                       ISNULL(?, AACH2.ARCHIVED_DATE)))\n" +
"\n" +
"                                )) SCAN_V) SYS_SCAN_V)\n" +
"UNION ALL\n" +
"SELECT *\n" +
"FROM   (SELECT SCAN_SYS_V.BARCODE,\n" +
"               NULL ITEM_CODE,\n" +
"               NULL ITEM_CATEGORY,\n" +
"               NULL ITEM_CATEGORY_NAME,\n" +
"               NULL ITEM_NAME,\n" +
"               NULL ITEM_SPEC,\n" +
"               SCAN_SYS_V.YEARS,\n" +
"               SCAN_SYS_V.ITEM_UNIT,\n" +
"               SCAN_SYS_V.START_DATE,\n" +
"               SCAN_SYS_V.FINANCE_PROP,\n" +
"               NULL MAINTAIN_USER,\n" +
"               NULL MAINTAIN_DEPT_NAME,\n" +
"               SCAN_SYS_V.FINANCE_PROP_VALUE,\n" +
"               SCAN_SYS_V.WORKORDER_OBJECT_NO,\n" +
"               SCAN_SYS_V.WORKORDER_OBJECT_CODE,\n" +
"               SCAN_SYS_V.WORKORDER_OBJECT_NAME,\n" +
"               SCAN_SYS_V.WORKORDER_OBJECT_LOCATION,\n" +
"               SCAN_SYS_V.COUNTY_CODE,\n" +
"               SCAN_SYS_V.COUNTY_NAME,\n" +
"               NULL RESPONSIBILITY_USER_NAME,\n" +
"               NULL EMPLOYEE_NUMBER,\n" +
"               NULL RESPONSIBILITY_DEPT_NAME,\n" +
"               SCAN_SYS_V.PROJECT_NAME,\n" +
"               SCAN_SYS_V.PROJECT_NUMBER,\n" +
"               NULL VENDOR_NAME,\n" +
"               NULL VENDOR_NUMBER,\n" +
"               SCAN_SYS_V.COMPANY_CODE,\n" +
"               SCAN_SYS_V.COMPANY,\n" +
"               SCAN_SYS_V.ITEM_CODE SCAN_ITEM_CODE,\n" +
"               SCAN_SYS_V.ITEM_CATEGORY SCAN_ITEM_CATEGORY,\n" +
"               SCAN_SYS_V.ITEM_CATEGORY_NAME SCAN_ITEM_CATEGORY_NAME,\n" +
"               SCAN_SYS_V.ITEM_NAME SCAN_ITEM_NAME,\n" +
"               SCAN_SYS_V.ITEM_SPEC SCAN_ITEM_SPEC,\n" +
"               SCAN_SYS_V.MAINTAIN_USER SCAN_MAINTAIN_USER,\n" +
"               SCAN_SYS_V.MAINTAIN_DEPT_NAME SCAN_MAINTAIN_DEPT_NAME,\n" +
"               SCAN_SYS_V.RESPONSIBILITY_USER_NAME SCAN_RESPONSIBILITY_USER_NAME,\n" +
"               SCAN_SYS_V.EMPLOYEE_NUMBER SCAN_EMPLOYEE_NUMBER,\n" +
"               SCAN_SYS_V.RESPONSIBILITY_DEPT_NAME SCAN_RESPONSIBILITY_DEPT_NAME,\n" +
"               SCAN_SYS_V.VENDOR_NAME SCAN_VENDOR_NAME,\n" +
"               SCAN_SYS_V.VENDOR_NUMBER SCAN_VENDOR_NUMBER,\n" +
"               ISNULL(NULL, '无') SYS_STATUS,\n" +
"               ISNULL(NULL, '有') SCAN_STATUS\n" +
"        FROM   (SELECT *\n" +
"                FROM   (SELECT AACL.BARCODE,\n" +
"                               ESI.ITEM_CODE,\n" +
"                               ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) ITEM_CATEGORY,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ISNULL(AACL.SCAN_ITEM_CATEGORY,\n" +
"                                                              ESI.ITEM_CATEGORY),\n" +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
"                               ISNULL(AACL.SCAN_ITEM_NAME, ESI.ITEM_NAME) ITEM_NAME,\n" +
"                               ISNULL(AACL.SCAN_ITEM_SPEC, ESI.ITEM_SPEC) ITEM_SPEC,\n" +
"                               ESI.YEARS,\n" +
"                               ESI.ITEM_UNIT,\n" +
"                               EII.START_DATE,\n" +
"                               EII.FINANCE_PROP,\n" +
"                               AACL.MAINTAIN_USER,\n" +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP,\n" +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE,\n" +
"                               EO.WORKORDER_OBJECT_NO,\n" +
"                               EO.WORKORDER_OBJECT_CODE,\n" +
"                               EO.WORKORDER_OBJECT_NAME,\n" +
"                               EO.WORKORDER_OBJECT_LOCATION,\n" +
"                               EC.COUNTY_CODE,\n" +
"                               EC.COUNTY_NAME,\n" +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
"                               AME.EMPLOYEE_NUMBER,\n" +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
"                               EPPA.NAME PROJECT_NAME,\n" +
"                               EPPA.SEGMENT1 PROJECT_NUMBER,\n" +
"                               EMPV.VENDOR_NAME,\n" +
"                               EMPV.SEGMENT1 VENDOR_NUMBER,\n" +
"                               EOCM.COMPANY_CODE,\n" +
"                               EOCM.COMPANY\n" +
"                        FROM   AMS_ASSETS_CHECK_HEADER AACH,\n" +
"                               AMS_ASSETS_CHECK_LINE   AACL,\n" +
"                               ETS_SYSTEM_ITEM         ESI,\n" +
"                               AMS_MIS_DEPT            AMD,\n" +
"                               AMS_MIS_EMPLOYEE        AME,\n" +
"                               ETS_ITEM_INFO           EII,\n" +
"                               ETS_OBJECT              EO,\n" +
"                               ETS_COUNTY              EC,\n" +
"                               ETS_OU_CITY_MAP         EOCM,\n" +
"                               SF_GROUP                SG,\n" +
"                               ETS_PA_PROJECTS_ALL     EPPA,\n" +
"                               ETS_MIS_PO_VENDORS      EMPV\n" +
"                        WHERE  AACH.HEADER_ID = AACL.HEADER_ID\n" +
"                               AND AACL.BARCODE *= EII.BARCODE\n" +
"                               AND ISNULL(AACL.SCAN_ITEM_CODE, EII.ITEM_CODE) =\n" +
"                               ESI.ITEM_CODE\n" +
"                               AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
"                               AND AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO\n" +
"                               AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
"                               AND EO.COUNTY_CODE = EC.COUNTY_CODE\n" +
"                               AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n"+        
"                               AND\n" +
"                               AACL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
"                               AND\n" +
"                               AACL.SCAN_RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
"                               AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
"                               AND EII.MAINTAIN_DEPT *= SG.GROUP_ID\n" +
"                               AND AACL.SCAN_STATUS = 'Y'\n" +
"                               AND\n" +
"                               AACH.HEADER_ID =\n" +
"                               (SELECT MAX(AACH2.HEADER_ID)\n" +
"                                FROM   AMS_ASSETS_CHECK_HEADER AACH2,\n" +
"                                       AMS_ASSETS_CHECK_LINE   AACL2\n" +
"                                WHERE  AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION\n" +
"                                       AND AACH2.HEADER_ID = AACL2.HEADER_ID\n" +
"                                       AND AACL2.BARCODE = AACL.BARCODE\n" +
"                                       AND AACH2.CHECK_LOCATION = ?\n" +
"                                       AND (AACH2.ORDER_STATUS = 'UPLOADED' OR\n" +
"                                       AACH2.ORDER_STATUS = 'ARCHIEVED')\n" +
"                                       AND ((AACH2.UPLOAD_DATE >=\n" +
"                                       ISNULL(?, AACH2.UPLOAD_DATE) AND\n" +
"                                       AACH2.UPLOAD_DATE <=\n" +
"                                       ISNULL(?, AACH2.UPLOAD_DATE)) OR\n" +
"                                       (AACH2.ARCHIVED_DATE >=\n" +
"                                       ISNULL(?, AACH2.ARCHIVED_DATE) AND\n" +
"                                       AACH2.ARCHIVED_DATE <=\n" +
"                                       ISNULL(?, AACH2.ARCHIVED_DATE))))) SCAN_V\n" +
"                MINUS\n" +
"                SELECT *\n" +
"                FROM   (SELECT EII.BARCODE,\n" +
"                               ESI.ITEM_CODE,\n" +
"                               ESI.ITEM_CATEGORY,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY,\n" +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME,\n" +
"                               ESI.ITEM_NAME,\n" +
"                               ESI.ITEM_SPEC,\n" +
"                               ESI.YEARS,\n" +
"                               ESI.ITEM_UNIT,\n" +
"                               EII.START_DATE,\n" +
"                               EII.FINANCE_PROP,\n" +
"                               EII.MAINTAIN_USER,\n" +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME,\n" +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP,\n" +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE,\n" +
"                               EO.WORKORDER_OBJECT_NO,\n" +
"                               EO.WORKORDER_OBJECT_CODE,\n" +
"                               EO.WORKORDER_OBJECT_NAME,\n" +
"                               EO.WORKORDER_OBJECT_LOCATION,\n" +
"                               EC.COUNTY_CODE,\n" +
"                               EC.COUNTY_NAME,\n" +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME,\n" +
"                               AME.EMPLOYEE_NUMBER,\n" +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME,\n" +
"                               EPPA.NAME PROJECT_NAME,\n" +
"                               EPPA.SEGMENT1 PROJECT_NUMBER,\n" +
"                               EMPV.VENDOR_NAME,\n" +
"                               EMPV.SEGMENT1 VENDOR_NUMBER,\n" +
"                               EOCM.COMPANY_CODE,\n" +
"                               EOCM.COMPANY\n" +
"                        FROM   ETS_ITEM_INFO          EII,\n" +
"                               ETS_SYSTEM_ITEM        ESI,\n" +
"                               ETS_SYSITEM_DISTRIBUTE ESD,\n" +
"                               AMS_OBJECT_ADDRESS     AOA,\n" +
"                               ETS_OBJECT             EO,\n" +
"                               ETS_COUNTY             EC,\n" +
"                               ETS_OU_CITY_MAP        EOCM,\n" +
"                               AMS_MIS_DEPT           AMD,\n" +
"                               AMS_MIS_EMPLOYEE       AME,\n" +
"                               ETS_PA_PROJECTS_ALL    EPPA,\n" +
"                               ETS_MIS_PO_VENDORS     EMPV,\n" +
"                               SF_GROUP               SG\n" +
"                        WHERE  EII.ITEM_CODE = ESI.ITEM_CODE\n" +
"                               AND ESI.ITEM_CODE = ESD.ITEM_CODE\n" +
"                               AND ESI.VENDOR_ID *= EMPV.VENDOR_ID\n" +
"                               AND EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID\n" +
"                               AND ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
"                               AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
"                               AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
"                               AND EO.COUNTY_CODE = EC.COUNTY_CODE\n" +
"                               AND EC.COMPANY_CODE = EOCM.COMPANY_CODE \n"+        
"                               AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
"                               AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
"                               AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
"                               AND EII.MAINTAIN_DEPT *= SG.GROUP_ID\n" +
"                               AND EO.WORKORDER_OBJECT_NO = ?) SYS_V) SCAN_SYS_V)";
;

			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getCheckLocation());
			sqlArgs.add(dto.getCheckLocation());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getCheckLocation());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getCheckLocation());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
