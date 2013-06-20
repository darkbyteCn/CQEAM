package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class NewAssetsMonitorNeedScanedModel extends AMSSQLProducer {

	public NewAssetsMonitorNeedScanedModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

/**
	 * 功能：构造新增资产监控已扫描资产的SQL
	 * @return SQLModel
	 * @throws com.sino.base.exception.SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                            "       EOCM.COMPANY, \n" +
                            "       EII.BARCODE,\n" +
                            "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                            "       ESI.ITEM_NAME AMS_ITEM_NAME,\n" +
                            "       EFA.ASSETS_DESCRIPTION MIS_ITEM_NAME,\n" +
                            "       ESI.ITEM_SPEC AMS_ITEM_SPEC,\n" +
                            "       EFA.MODEL_NUMBER MIS_ITEM_SPEC,\n" +
                            "       EFA.FA_CATEGORY1,\n" +
                            "       EFA.FA_CATEGORY2,\n" +
                            "       ESI.YEARS AMS_YEARS,\n" +
                            "       EFA.LIFE_IN_YEARS MIS_YEARS,\n" +
                            "       EII.START_DATE AMS_START_DATE,\n" +
                            "       EFA.DATE_PLACED_IN_SERVICE MIS_START_DATE,\n" +
                            "       EFA.ORIGINAL_COST,\n" +
                            "       EFA.COST,\n" +
                            "       EFA.DEPRN_COST,\n" +
                            "       EFA.IMPAIR_RESERVE,\n" +
                            "       EFA.SCRAP_VALUE,\n" +
                            "       EFA.DEPRECIATION_ACCOUNT,\n" +
                            "       AMD.DEPT_NAME,\n" +
                            "       AME.USER_NAME AMS_USER_NAME,\n" +
                            "       EFA.ASSIGNED_TO_NAME MIS_USER_NAME,\n" +
                            "       AME.EMPLOYEE_NUMBER AMS_EMPLOYEE_NUMBER,\n" +
                            "       EFA.ASSIGNED_TO_NUMBER MIS_EMPLOYEE_NUMBER,\n" +
                            "       EO.WORKORDER_OBJECT_NAME AMS_LOCATION,\n" +
                            "       EFA.ASSETS_LOCATION MIS_LOCATION,\n" +
                            "       EO.WORKORDER_OBJECT_CODE AMS_LOCATION_CODE,\n" +
                            "       EFA.ASSETS_LOCATION_CODE MIS_LOCATION_CODE,\n" +
                            "       EPPA.NAME AMS_PROJECT_NAME,\n" +
                            "       EFA.PROJECT_NAME MIS_PROJECT_NAME,\n" +
                            "       EPPA.SEGMENT1 AMS_PROJECT_NUMBER,\n" +
                            "       EFA.MIS_PROJECT_NUMBER,\n" +
                            "       DECODE(ESI.ITEM_NAME, EFA.ASSETS_DESCRIPTION, '', '名称不符') ||\n" +
                            "       DECODE(ISNULL(ESI.ITEM_SPEC, '1'),\n" +
                            "              ISNULL(EFA.MODEL_NUMBER, '1'),\n" +
                            "              '',\n" +
                            "              ';型号不符') || DECODE(EFA.ASSIGNED_TO_NUMBER,\n" +
                            "                                 AME.EMPLOYEE_NUMBER,\n" +
                            "                                 '',\n" +
                            "                                 ';责任人不符') ||\n" +
                            "       DECODE(EFA.ASSETS_LOCATION,\n" +
                            "              EO.WORKORDER_OBJECT_NAME,\n" +
                            "              '',\n" +
                            "              ';地点不符') CHANGED_CONTENT\n" +
                            "  FROM ETS_OU_CITY_MAP     EOCM,\n" +
                            "       ETS_PA_PROJECTS_ALL EPPA,\n" +
                            "       AMS_MIS_DEPT        AMD,\n" +
                            "       AMS_MIS_EMPLOYEE    AME,\n" +
                            "       ETS_SYSTEM_ITEM     ESI,\n" +
                            "       ETS_OBJECT          EO,\n" +
                            "       AMS_OBJECT_ADDRESS  AOA,\n" +
                            "       ETS_ITEM_INFO       EII,\n" +
                            "       ETS_FA_ASSETS       EFA\n" +
                            " WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                            "   AND EII.BARCODE = EFA.TAG_NUMBER\n" +
                            "   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                            "   AND EII.ITEM_CODE *= ESI.ITEM_CODE\n" +
                            "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                            "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                            "   AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                            "   AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                            "   AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
                            "   AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                            "   AND EFA.ASSETS_CREATE_DATE >= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                            "   AND EFA.ASSETS_CREATE_DATE <= ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                            "   AND EXISTS (SELECT NULL\n" +
                            "                 FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                            "                WHERE EFA.SEGMENT1||'.'||EFA.SEGMENT2||'.'||EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                            "                  AND AICM.NEED_SCAN = 'Y')\n" +
                            "   AND EXISTS\n" +
                            " (SELECT NULL\n" +
                            "          FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                            "                  FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                            "                       AMS_ASSETS_CHECK_LINE   AACL\n" +
                            "                 WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                            "                   AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                            "                   AND ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                            "                       (AACL.ARCHIVE_STATUS = '1' AND\n" +
                            "                       AACL.SYSTEM_STATUS = 'Y'))\n" +
                            "                   AND AACH.ORGANIZATION_ID = ISNULL(?, AACH.ORGANIZATION_ID)\n" +
                            "                UNION\n" +
                            "                SELECT DISTINCT EW.ORGANIZATION_ID, EWD.BARCODE\n" +
                            "                  FROM ETS_WORKORDER       EW,\n" +
                            "                       ETS_WORKORDER_BATCH EWB,\n" +
                            "                       ETS_WORKORDER_DTL   EWD\n" +
                            "                 WHERE EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                            "                   AND EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
                            "                   AND EWD.ITEM_STATUS <> 6\n" +
                            "                   AND EW.WORKORDER_FLAG IN (13, 14)\n" +
                            "                   AND EW.ORGANIZATION_ID = ISNULL(?, EW.ORGANIZATION_ID)) TMP_1\n" +
                            "         WHERE EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                            "           AND EII.BARCODE = TMP_1.BARCODE)\n" +
                            "   AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80)\n" +
                            "   AND EOCM.ORGANIZATION_ID = ?\n" +
                            "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SUBSTRING(EFA.DEPRECIATION_ACCOUNT, 6, 6) = ?)\n" +
                            "   AND (EFA.ASSETS_LOCATION_CODE LIKE ISNULL(?, EFA.ASSETS_LOCATION_CODE) OR\n" +
                            "       EFA.ASSETS_LOCATION LIKE ISNULL(?, EFA.ASSETS_LOCATION))\n" +
                            " ORDER BY EII.BARCODE";
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCostCenterCode());
            sqlArgs.add(dto.getCheckLocation());
            sqlArgs.add(dto.getCheckLocationName());

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}