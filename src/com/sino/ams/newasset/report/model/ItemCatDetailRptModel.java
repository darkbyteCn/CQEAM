package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ItemCatDetailRptModel extends AMSSQLProducer {

	public ItemCatDetailRptModel(SfUserDTO userAccount, AmsAssetsCheckLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getOwnItemModel(){
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO)dtoParameter;
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
                        + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
						+ " AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
						+ " AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
						+ " AND EII.PROJECTID *= EPPA.PROJECT_ID"
						+ " AND EII.MAINTAIN_DEPT *= SG.GROUP_ID"
						+ " AND ESI.ITEM_CATEGORY = ?"
						+ " AND EOCM.ORGANIZATION_ID = ?";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getItemCategory());
		sqlArgs.add(dto.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getScanedItemModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO)dtoParameter;
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
                            + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE"
							+ " AND AACL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
							+ " AND AACL.SCAN_RESPONSIBILITY_DEPT *= AMD.DEPT_CODE"
							+ " AND EII.PROJECTID *= EPPA.PROJECT_ID"
							+ " AND EII.MAINTAIN_DEPT *= SG.GROUP_ID"
							+ " AND ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) = ?"
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
							+ " AND AACL2.SCAN_STATUS = 'Y'"
							+ " AND (AACH2.ORDER_STATUS = ? OR AACH2.ORDER_STATUS = ?)"
							+ " AND ((AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE)"
							+ " AND AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE))"
							+ " OR (AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE)"
							+ " AND AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE))))"
							+ " AND EOCM.ORGANIZATION_ID = ?";
							;
			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());

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
		AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO)dtoParameter;
		try {
			String sqlStr ="SELECT *" +
"FROM   (SELECT SYS_SCAN_V.BARCODE," +
"               SYS_SCAN_V.ITEM_CODE," +
"               SYS_SCAN_V.ITEM_CATEGORY," +
"               SYS_SCAN_V.ITEM_CATEGORY_NAME," +
"               SYS_SCAN_V.ITEM_NAME," +
"               SYS_SCAN_V.ITEM_SPEC," +
"               SYS_SCAN_V.YEARS," +
"               SYS_SCAN_V.ITEM_UNIT," +
"               SYS_SCAN_V.START_DATE," +
"               SYS_SCAN_V.FINANCE_PROP," +
"               SYS_SCAN_V.MAINTAIN_USER," +
"               SYS_SCAN_V.MAINTAIN_DEPT_NAME," +
"               SYS_SCAN_V.FINANCE_PROP_VALUE," +
"               SYS_SCAN_V.WORKORDER_OBJECT_NO," +
"               SYS_SCAN_V.WORKORDER_OBJECT_CODE," +
"               SYS_SCAN_V.WORKORDER_OBJECT_NAME," +
"               SYS_SCAN_V.WORKORDER_OBJECT_LOCATION," +
"               SYS_SCAN_V.COUNTY_CODE," +
"               SYS_SCAN_V.COUNTY_NAME," +
"               SYS_SCAN_V.RESPONSIBILITY_USER_NAME," +
"               SYS_SCAN_V.EMPLOYEE_NUMBER," +
"               SYS_SCAN_V.RESPONSIBILITY_DEPT_NAME," +
"               SYS_SCAN_V.PROJECT_NAME," +
"               SYS_SCAN_V.PROJECT_NUMBER," +
"               SYS_SCAN_V.VENDOR_NAME," +
"               SYS_SCAN_V.VENDOR_NUMBER," +
"               SYS_SCAN_V.COMPANY_CODE," +
"               SYS_SCAN_V.COMPANY," +
"               NULL SCAN_ITEM_CODE," +
"               NULL SCAN_ITEM_CATEGORY," +
"               NULL SCAN_ITEM_CATEGORY_NAME," +
"               NULL SCAN_ITEM_NAME," +
"               NULL SCAN_ITEM_SPEC," +
"               NULL SCAN_MAINTAIN_USER," +
"               NULL SCAN_MAINTAIN_DEPT_NAME," +
"               NULL SCAN_RESPONSIBILITY_USER_NAME," +
"               NULL SCAN_EMPLOYEE_NUMBER," +
"               NULL SCAN_RESPONSIBILITY_DEPT_NAME," +
"               NULL SCAN_VENDOR_NAME," +
"               NULL SCAN_VENDOR_NUMBER," +
"               ISNULL(NULL,  '有')SYS_STATUS," +
"               ISNULL(NULL,  '无')SCAN_STATUS" +
"        FROM   (SELECT *" +
"                FROM   (SELECT EII.BARCODE," +
"                               ESI.ITEM_CODE," +
"                               ESI.ITEM_CATEGORY," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY," +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME," +
"                               ESI.ITEM_NAME," +
"                               ESI.ITEM_SPEC," +
"                               ESI.YEARS," +
"                               ESI.ITEM_UNIT," +
"                               EII.START_DATE," +
"                               EII.FINANCE_PROP," +
"                               EII.MAINTAIN_USER," +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP," +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE," +
"                               EO.WORKORDER_OBJECT_NO," +
"                               EO.WORKORDER_OBJECT_CODE," +
"                               EO.WORKORDER_OBJECT_NAME," +
"                               EO.WORKORDER_OBJECT_LOCATION," +
"                               EC.COUNTY_CODE," +
"                               EC.COUNTY_NAME," +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME," +
"                               AME.EMPLOYEE_NUMBER," +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME," +
"                               EPPA.NAME PROJECT_NAME," +
"                               EPPA.SEGMENT1 PROJECT_NUMBER," +
"                               EMPV.VENDOR_NAME," +
"                               EMPV.SEGMENT1 VENDOR_NUMBER," +
"                               EOCM.COMPANY_CODE," +
"                               EOCM.COMPANY" +
"                        FROM   ETS_ITEM_INFO          EII," +
"                               ETS_SYSTEM_ITEM        ESI," +
"                               ETS_SYSITEM_DISTRIBUTE ESD," +
"                               AMS_OBJECT_ADDRESS     AOA," +
"                               ETS_OBJECT             EO," +
"                               ETS_COUNTY             EC," +
"                               ETS_OU_CITY_MAP        EOCM," +
"                               AMS_MIS_DEPT           AMD," +
"                               AMS_MIS_EMPLOYEE       AME," +
"                               ETS_PA_PROJECTS_ALL    EPPA," +
"                               ETS_MIS_PO_VENDORS     EMPV," +
"                               SF_GROUP               SG" +
"                        WHERE  EII.ITEM_CODE = ESI.ITEM_CODE" +
"                        AND    ESI.ITEM_CODE = ESD.ITEM_CODE" +
"                        AND    ESI.VENDOR_ID *= EMPV.VENDOR_ID" +
"                        AND    EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID" +
"                        AND    ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID" +
"                        AND    EII.ADDRESS_ID = AOA.ADDRESS_ID" +
"                        AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO" +
"                        AND    EO.COUNTY_CODE = EC.COUNTY_CODE" +
"                        AND    EC.COMPANY_CODE = EOCM.COMPANY_CODE"+
"                        AND    EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID" +
"                        AND    EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE" +
"                        AND    EII.PROJECTID *= EPPA.PROJECT_ID" +
"                        AND    EII.MAINTAIN_DEPT *= SG.GROUP_ID" +
"                        AND    ESI.ITEM_CATEGORY = ?) SYS_V" +
"                MINUS" +
"                SELECT *" +
"                FROM   (SELECT AACL.BARCODE," +
"                               ESI.ITEM_CODE," +
"                               ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) ITEM_CATEGORY," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ISNULL(AACL.SCAN_ITEM_CATEGORY," +
"                                                              ESI.ITEM_CATEGORY)," +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME," +
"                               ISNULL(AACL.SCAN_ITEM_NAME, ESI.ITEM_NAME) ITEM_NAME," +
"                               ISNULL(AACL.SCAN_ITEM_SPEC, ESI.ITEM_SPEC) ITEM_SPEC," +
"                               ESI.YEARS," +
"                               ESI.ITEM_UNIT," +
"                               EII.START_DATE," +
"                               EII.FINANCE_PROP," +
"                               AACL.MAINTAIN_USER," +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP," +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE," +
"                               EO.WORKORDER_OBJECT_NO," +
"                               EO.WORKORDER_OBJECT_CODE," +
"                               EO.WORKORDER_OBJECT_NAME," +
"                               EO.WORKORDER_OBJECT_LOCATION," +
"                               EC.COUNTY_CODE," +
"                               EC.COUNTY_NAME," +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME," +
"                               AME.EMPLOYEE_NUMBER," +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME," +
"                               EPPA.NAME PROJECT_NAME," +
"                               EPPA.SEGMENT1 PROJECT_NUMBER," +
"                               EMPV.VENDOR_NAME," +
"                               EMPV.SEGMENT1 VENDOR_NUMBER," +
"                               EOCM.COMPANY_CODE," +
"                               EOCM.COMPANY" +
"                        FROM   AMS_ASSETS_CHECK_HEADER AACH," +
"                               AMS_ASSETS_CHECK_LINE   AACL," +
"                               ETS_SYSTEM_ITEM         ESI," +
"                               AMS_MIS_DEPT            AMD," +
"                               AMS_MIS_EMPLOYEE        AME," +
"                               ETS_ITEM_INFO           EII," +
"                               ETS_OBJECT              EO," +
"                               ETS_COUNTY              EC," +
"                               ETS_OU_CITY_MAP         EOCM," +
"                               SF_GROUP                SG," +
"                               ETS_PA_PROJECTS_ALL     EPPA," +
"                               ETS_MIS_PO_VENDORS      EMPV" +
"                        WHERE  AACH.HEADER_ID = AACL.HEADER_ID" +
"                        AND    AACL.BARCODE *= EII.BARCODE" +
"                        AND    ISNULL(AACL.SCAN_ITEM_CODE, EII.ITEM_CODE) =" +
"                               ESI.ITEM_CODE" +
"                        AND    ESI.VENDOR_ID *= EMPV.VENDOR_ID" +
"                        AND    AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO" +
"                        AND    EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID" +
"                        AND    EO.COUNTY_CODE = EC.COUNTY_CODE" +
"                        AND    EC.COMPANY_CODE = EOCM.COMPANY_CODE"+                   
"                        AND    AACL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID" +
"                        AND    AACL.SCAN_RESPONSIBILITY_DEPT *= AMD.DEPT_CODE" +
"                        AND    EII.PROJECTID *= EPPA.PROJECT_ID" +
"                        AND    EII.MAINTAIN_DEPT *= SG.GROUP_ID" +
"                        AND    AACL.SCAN_STATUS = 'Y'" +
"						 AND    ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) = ?" +
"                        AND    AACH.HEADER_ID =" +
"                               (SELECT MAX(AACH2.HEADER_ID)" +
"                                 FROM   AMS_ASSETS_CHECK_HEADER AACH2," +
"                                        AMS_ASSETS_CHECK_LINE   AACL2" +
"                                 WHERE  AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION" +
"                                 AND    AACH2.HEADER_ID = AACL2.HEADER_ID" +
"                                 AND    AACL2.BARCODE = AACL.BARCODE" +
"                                 AND    AACH2.ORDER_STATUS = 'UPLOADED'" +
"                                 AND    AACH2.UPLOAD_DATE >=" +
"                                        ISNULL(?, AACH2.UPLOAD_DATE)" +
"                                 AND    AACH2.UPLOAD_DATE <=" +
"                                        ISNULL(?, AACH2.UPLOAD_DATE))) SCAN_V) SYS_SCAN_V)" +
"UNION ALL " +
"SELECT *" +
"FROM   (SELECT SCAN_SYS_V.BARCODE," +
"               NULL ITEM_CODE," +
"               NULL ITEM_CATEGORY," +
"               NULL ITEM_CATEGORY_NAME," +
"               NULL ITEM_NAME," +
"               NULL ITEM_SPEC," +
"               SCAN_SYS_V.YEARS," +
"               SCAN_SYS_V.ITEM_UNIT," +
"               SCAN_SYS_V.START_DATE," +
"               SCAN_SYS_V.FINANCE_PROP," +
"               NULL MAINTAIN_USER," +
"               NULL MAINTAIN_DEPT_NAME," +
"               SCAN_SYS_V.FINANCE_PROP_VALUE," +
"               SCAN_SYS_V.WORKORDER_OBJECT_NO," +
"               SCAN_SYS_V.WORKORDER_OBJECT_CODE," +
"               SCAN_SYS_V.WORKORDER_OBJECT_NAME," +
"               SCAN_SYS_V.WORKORDER_OBJECT_LOCATION," +
"               SCAN_SYS_V.COUNTY_CODE," +
"               SCAN_SYS_V.COUNTY_NAME," +
"               NULL RESPONSIBILITY_USER_NAME," +
"               NULL EMPLOYEE_NUMBER," +
"               NULL RESPONSIBILITY_DEPT_NAME," +
"               SCAN_SYS_V.PROJECT_NAME," +
"               SCAN_SYS_V.PROJECT_NUMBER," +
"               NULL VENDOR_NAME," +
"               NULL VENDOR_NUMBER," +
"               SCAN_SYS_V.COMPANY_CODE," +
"               SCAN_SYS_V.COMPANY," +
"               SCAN_SYS_V.ITEM_CODE SCAN_ITEM_CODE," +
"               SCAN_SYS_V.ITEM_CATEGORY SCAN_ITEM_CATEGORY," +
"               SCAN_SYS_V.ITEM_CATEGORY_NAME SCAN_ITEM_CATEGORY_NAME," +
"               SCAN_SYS_V.ITEM_NAME SCAN_ITEM_NAME," +
"               SCAN_SYS_V.ITEM_SPEC SCAN_ITEM_SPEC," +
"               SCAN_SYS_V.MAINTAIN_USER SCAN_MAINTAIN_USER," +
"               SCAN_SYS_V.MAINTAIN_DEPT_NAME SCAN_MAINTAIN_DEPT_NAME," +
"               SCAN_SYS_V.RESPONSIBILITY_USER_NAME SCAN_RESPONSIBILITY_USER_NAME," +
"               SCAN_SYS_V.EMPLOYEE_NUMBER SCAN_EMPLOYEE_NUMBER," +
"               SCAN_SYS_V.RESPONSIBILITY_DEPT_NAME SCAN_RESPONSIBILITY_DEPT_NAME," +
"               SCAN_SYS_V.VENDOR_NAME SCAN_VENDOR_NAME," +
"               SCAN_SYS_V.VENDOR_NUMBER SCAN_VENDOR_NUMBER," +
"               ISNULL(NULL, '无') SYS_STATUS," +
"               ISNULL(NULL, '有') SCAN_STATUS" +
"        FROM   (SELECT *" +
"                FROM   (SELECT AACL.BARCODE," +
"                               ESI.ITEM_CODE," +
"                               ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) ITEM_CATEGORY," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ISNULL(AACL.SCAN_ITEM_CATEGORY," +
"                                                              ESI.ITEM_CATEGORY)," +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME," +
"                               ISNULL(AACL.SCAN_ITEM_NAME, ESI.ITEM_NAME) ITEM_NAME," +
"                               ISNULL(AACL.SCAN_ITEM_SPEC, ESI.ITEM_SPEC) ITEM_SPEC," +
"                               ESI.YEARS," +
"                               ESI.ITEM_UNIT," +
"                               EII.START_DATE," +
"                               EII.FINANCE_PROP," +
"                               AACL.MAINTAIN_USER," +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP," +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE," +
"                               EO.WORKORDER_OBJECT_NO," +
"                               EO.WORKORDER_OBJECT_CODE," +
"                               EO.WORKORDER_OBJECT_NAME," +
"                               EO.WORKORDER_OBJECT_LOCATION," +
"                               EC.COUNTY_CODE," +
"                               EC.COUNTY_NAME," +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME," +
"                               AME.EMPLOYEE_NUMBER," +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME," +
"                               EPPA.NAME PROJECT_NAME," +
"                               EPPA.SEGMENT1 PROJECT_NUMBER," +
"                               EMPV.VENDOR_NAME," +
"                               EMPV.SEGMENT1 VENDOR_NUMBER," +
"                               EOCM.COMPANY_CODE," +
"                               EOCM.COMPANY" +
"                        FROM   AMS_ASSETS_CHECK_HEADER AACH," +
"                               AMS_ASSETS_CHECK_LINE   AACL," +
"                               ETS_SYSTEM_ITEM         ESI," +
"                               AMS_MIS_DEPT            AMD," +
"                               AMS_MIS_EMPLOYEE        AME," +
"                               ETS_ITEM_INFO           EII," +
"                               ETS_OBJECT              EO," +
"                               ETS_COUNTY              EC," +
"                               ETS_OU_CITY_MAP         EOCM," +
"                               SF_GROUP                SG," +
"                               ETS_PA_PROJECTS_ALL     EPPA," +
"                               ETS_MIS_PO_VENDORS      EMPV" +
"                        WHERE  AACH.HEADER_ID = AACL.HEADER_ID" +
"                        AND    AACL.BARCODE *= EII.BARCODE" +
"                        AND    ISNULL(AACL.SCAN_ITEM_CODE, EII.ITEM_CODE) =" +
"                               ESI.ITEM_CODE" +
"                        AND    ESI.VENDOR_ID *= EMPV.VENDOR_ID" +
"                        AND    AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO" +
"                        AND    EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID" +
"                        AND    EO.COUNTY_CODE = EC.COUNTY_CODE" +
"                        AND    EC.COMPANY_CODE = EOCM.COMPANY_CODE"+                    
"                        AND    AACL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID" +
"                        AND    AACL.SCAN_RESPONSIBILITY_DEPT *= AMD.DEPT_CODE" +
"                        AND    EII.PROJECTID *= EPPA.PROJECT_ID" +
"                        AND    EII.MAINTAIN_DEPT *= SG.GROUP_ID" +
"                        AND    AACL.SCAN_STATUS = 'Y'" +
"                        AND    ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) = ?" +
"                        AND    AACH.HEADER_ID =" +
"                               (SELECT MAX(AACH2.HEADER_ID)" +
"                                 FROM   AMS_ASSETS_CHECK_HEADER AACH2," +
"                                        AMS_ASSETS_CHECK_LINE   AACL2" +
"                                 WHERE  AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION" +
"                                 AND    AACH2.HEADER_ID = AACL2.HEADER_ID" +
"                                 AND    AACL2.BARCODE = AACL.BARCODE" +
"                                 AND    AACH2.ORDER_STATUS = 'UPLOADED'" +
"                                 AND    AACH2.UPLOAD_DATE >=" +
"                                        ISNULL(?, AACH2.UPLOAD_DATE)" +
"                                 AND    AACH2.UPLOAD_DATE <=" +
"                                        ISNULL(?, AACH2.UPLOAD_DATE))) SCAN_V" +
"                MINUS" +
"                SELECT *" +
"                FROM   (SELECT EII.BARCODE," +
"                               ESI.ITEM_CODE," +
"                               ESI.ITEM_CATEGORY," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY," +
"                                                          'ITEM_TYPE') ITEM_CATEGORY_NAME," +
"                               ESI.ITEM_NAME," +
"                               ESI.ITEM_SPEC," +
"                               ESI.YEARS," +
"                               ESI.ITEM_UNIT," +
"                               EII.START_DATE," +
"                               EII.FINANCE_PROP," +
"                               EII.MAINTAIN_USER," +
"                               SG.GROUP_NAME MAINTAIN_DEPT_NAME," +
"                               AMS_PUB_PKG.GET_FLEX_VALUE(EII.FINANCE_PROP," +
"                                                          'FINANCE_PROP') FINANCE_PROP_VALUE," +
"                               EO.WORKORDER_OBJECT_NO," +
"                               EO.WORKORDER_OBJECT_CODE," +
"                               EO.WORKORDER_OBJECT_NAME," +
"                               EO.WORKORDER_OBJECT_LOCATION," +
"                               EC.COUNTY_CODE," +
"                               EC.COUNTY_NAME," +
"                               AME.USER_NAME RESPONSIBILITY_USER_NAME," +
"                               AME.EMPLOYEE_NUMBER," +
"                               AMD.DEPT_NAME RESPONSIBILITY_DEPT_NAME," +
"                               EPPA.NAME PROJECT_NAME," +
"                               EPPA.SEGMENT1 PROJECT_NUMBER," +
"                               EMPV.VENDOR_NAME," +
"                               EMPV.SEGMENT1 VENDOR_NUMBER," +
"                               EOCM.COMPANY_CODE," +
"                               EOCM.COMPANY" +
"                        FROM   ETS_ITEM_INFO          EII," +
"                               ETS_SYSTEM_ITEM        ESI," +
"                               ETS_SYSITEM_DISTRIBUTE ESD," +
"                               AMS_OBJECT_ADDRESS     AOA," +
"                               ETS_OBJECT             EO," +
"                               ETS_COUNTY             EC," +
"                               ETS_OU_CITY_MAP        EOCM," +
"                               AMS_MIS_DEPT           AMD," +
"                               AMS_MIS_EMPLOYEE       AME," +
"                               ETS_PA_PROJECTS_ALL    EPPA," +
"                               ETS_MIS_PO_VENDORS     EMPV," +
"                               SF_GROUP               SG" +
"                        WHERE  EII.ITEM_CODE = ESI.ITEM_CODE" +
"                        AND    ESI.ITEM_CODE = ESD.ITEM_CODE" +
"                        AND    ESI.VENDOR_ID *= EMPV.VENDOR_ID" +
"                        AND    EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID" +
"                        AND    ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID" +
"                        AND    EII.ADDRESS_ID = AOA.ADDRESS_ID" +
"                        AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO" +
"                        AND    EO.COUNTY_CODE = EC.COUNTY_CODE" +
"                        AND    EC.COMPANY_CODE = EOCM.COMPANY_CODE"+                    
"                        AND    EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID" +
"                        AND    EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE" +
"                        AND    EII.PROJECTID *= EPPA.PROJECT_ID" +
"                        AND    EII.MAINTAIN_DEPT *= SG.GROUP_ID" +
"                        AND    ESI.ITEM_CATEGORY = ?) SYS_V) SCAN_SYS_V)";

			List sqlArgs = new ArrayList();
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getItemCategory());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
