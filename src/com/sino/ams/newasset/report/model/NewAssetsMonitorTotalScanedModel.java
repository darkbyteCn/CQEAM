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

public class NewAssetsMonitorTotalScanedModel extends AMSSQLProducer {

	public NewAssetsMonitorTotalScanedModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
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
                            "       EOCM.COMPANY,\n" +
                            "       EII.BARCODE,\n" +
                            "       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
                            "       ESI.ITEM_NAME AMS_ITEM_NAME,\n" +
                            "       ESI.ITEM_SPEC AMS_ITEM_SPEC,\n" +
                            "       ESI.YEARS AMS_YEARS,\n" +
                            "       EII.START_DATE AMS_START_DATE,\n" +
                            "       AMD.DEPT_NAME,\n" +
                            "       AME.USER_NAME AMS_USER_NAME,\n" +
                            "       AME.EMPLOYEE_NUMBER AMS_EMPLOYEE_NUMBER,\n" +
                            "       EO.WORKORDER_OBJECT_NAME AMS_LOCATION,\n" +
                            "       EO.WORKORDER_OBJECT_CODE AMS_LOCATION_CODE,\n" +
                            "       EPPA.NAME AMS_PROJECT_NAME,\n" +
                            "       EPPA.SEGMENT1 AMS_PROJECT_NUMBER\n" +
                            " FROM  ETS_OU_CITY_MAP     EOCM,\n" +
                            "       ETS_PA_PROJECTS_ALL EPPA,\n" +
                            "       AMS_MIS_DEPT        AMD,\n" +
                            "       AMS_MIS_EMPLOYEE    AME,\n" +
                            "       ETS_SYSTEM_ITEM     ESI,\n" +
                            "       ETS_OBJECT          EO,\n" +
                            "       AMS_OBJECT_ADDRESS  AOA,\n" +
                            "       ETS_ITEM_INFO       EII\n" +
                            " WHERE EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                            "   AND EII.ITEM_CODE *= ESI.ITEM_CODE\n" +
                            "AND    EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                            "AND    AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                            "AND    EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                            "AND    EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                            "AND    EII.PROJECTID *= EPPA.PROJECT_ID\n" +
                            "AND    EXISTS\n" +
                            " (SELECT NULL\n" +
                            "        FROM   (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                            "                FROM   AMS_ASSETS_CHECK_HEADER AACH, AMS_ASSETS_CHECK_LINE AACL\n" +
                            "                WHERE  AACH.HEADER_ID = AACL.HEADER_ID\n" +
                            "                AND    AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                            "                AND    ((AACL.ARCHIVE_STATUS = '0' AND AACL.SCAN_STATUS = 'Y') OR\n" +
                            "                      (AACL.ARCHIVE_STATUS = '1' AND AACL.SYSTEM_STATUS = 'Y'))\n" +
                            "                AND AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)\n" +
                            "                   AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)\n"   +
                            "                AND    AACH.ORGANIZATION_ID = ISNULL(?, AACH.ORGANIZATION_ID)" +
                            "         UNION \n" +
                            "               SELECT DISTINCT EW.ORGANIZATION_ID, EWD.BARCODE\n" +
                            "                 FROM ETS_WORKORDER       EW,\n" +
                            "                      ETS_WORKORDER_BATCH EWB,\n" +
                            "                      ETS_WORKORDER_DTL   EWD\n" +
                            "                WHERE EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                            "                  AND EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
                            "                  AND EWD.ITEM_STATUS <> 6\n" +
                            "                  AND EW.WORKORDER_FLAG IN (13, 14)     \n " +
                            "                  AND EW.START_DATE >= ISNULL(?, EW.START_DATE)\n" +
                            "                   AND EW.START_DATE <=  ISNULL(?, EW.START_DATE)"  +
                            "                  AND EW.ORGANIZATION_ID = ISNULL(?, EW.ORGANIZATION_ID)"  +
                            "  ) TMP_1\n" +
                            "        WHERE  EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                            "        AND    EII.BARCODE = TMP_1.BARCODE)\n" +
                            "AND    (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80)\n" +
                            "AND    EOCM.ORGANIZATION_ID = ?\n" +
                            "AND    ( " + SyBaseSQLUtil.isNull() + "  OR EII.RESPONSIBILITY_DEPT = ?)\n" +
                            "AND    (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(?, EO.WORKORDER_OBJECT_CODE) OR\n" +
                            "      EO.WORKORDER_OBJECT_NAME LIKE ISNULL(?, EO.WORKORDER_OBJECT_NAME))" +
                            " ORDER BY EII.BARCODE";
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
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