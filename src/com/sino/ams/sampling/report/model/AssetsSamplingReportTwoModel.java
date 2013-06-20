package com.sino.ams.sampling.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-9-18
 * Time: 10:30:03
 * To change this template use File | Settings | File Templates.
 */
public class AssetsSamplingReportTwoModel extends AMSSQLProducer {

	public AssetsSamplingReportTwoModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       EII.BARCODE,\n" +
                "       ESI.ITEM_NAME AMS_ITEM_NAME,\n" +
                "       AASL.SCAN_ITEM_NAME SCAN_ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC AMS_ITEM_SPEC,\n" +
                "       AASL.SCAN_ITEM_SPEC SCAN_ITEM_SPEC,\n" +
                "       EO.WORKORDER_OBJECT_CODE AMS_LOCATION_CODE,\n" +
                "       EO2.WORKORDER_OBJECT_CODE SCAN_LOCATION_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME AMS_LOCATION,\n" +
                "       EO2.WORKORDER_OBJECT_NAME SCAN_LOCATION,\n" +
                "       AME.EMPLOYEE_NUMBER AMS_EMPLOYEE_NUMBER,\n" +
                "       AME2.EMPLOYEE_NUMBER SCAN_EMPLOYEE_NUMBER,\n" +
                "       AME.USER_NAME AMS_USER_NAME,\n" +
                "       AME2.USER_NAME SCAN_USER_NAME,\n" +
                "       CASE ESI.ITEM_NAME WHEN AASL.SCAN_ITEM_NAME THEN '' ELSE '名称不符' END ||\n" +
                "       CASE dbo.NVL(ESI.ITEM_SPEC, '1') WHEN dbo.NVL(AASL.SCAN_ITEM_SPEC, '1') THEN '' ELSE ';型号不符' END ||\n" +
                "       CASE AASL.SCAN_RESPONSIBILITY_USER WHEN AME.EMPLOYEE_ID THEN '' ELSE ';责任人不符' END ||\n" +
                "       CASE AASH.SAMPLING_LOCATION WHEN EO.WORKORDER_OBJECT_NO THEN '' ELSE ';地点不符' END CHANGED_CONTENT\n" +
                "  FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
                "       AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
                "       AMS_ASSETS_SAMPLING_BATCH  AASB,\n" +
                "       AMS_ASSETS_SAMPLING_TASK   AAST,\n" +
                "       ETS_ITEM_INFO              EII,\n" +
                "       ETS_OBJECT                 EO,\n" +
                "       AMS_OBJECT_ADDRESS         AOA,\n" +
                "       ETS_SYSTEM_ITEM            ESI,\n" +
                "       AMS_MIS_EMPLOYEE           AME,\n" +
                "       ETS_OU_CITY_MAP            EOCM,\n" +
                "       ETS_OBJECT                 EO2,\n" +
                "       AMS_MIS_EMPLOYEE           AME2\n" +
                " WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
                "       AND AASH.BATCH_ID = AASB.BATCH_ID\n" +
                "       AND AASB.TASK_ID = AAST.TASK_ID\n" +
                "       AND AASL.BARCODE = EII.BARCODE\n" +
                "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "       AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "       AND EOCM.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                "       AND EO2.WORKORDER_OBJECT_NO = AASH.SAMPLING_LOCATION\n" +
                "       AND AASL.SCAN_RESPONSIBILITY_USER = AME2.EMPLOYEE_ID\n" +
                "       AND\n" +
                "       (CASE ESI.ITEM_NAME WHEN AASL.SCAN_ITEM_NAME THEN '' ELSE '名称不符' END ||\n" +
                "       CASE dbo.NVL(ESI.ITEM_SPEC, '1') WHEN dbo.NVL(AASL.SCAN_ITEM_SPEC, '1') THEN '' ELSE ';型号不符' END ||\n" +
                "       CASE AASL.SCAN_RESPONSIBILITY_USER WHEN AME.EMPLOYEE_ID THEN '' ELSE ';责任人不符' END ||\n" +
                "       CASE AASH.SAMPLING_LOCATION WHEN EO.WORKORDER_OBJECT_NO THEN '' ELSE ';地点不符' END) IS NOT NULL\n" +
                "       AND AASL.SCAN_STATUS = 'Y'\n" +
                "       AND EXISTS (SELECT 1\n" +
                "          FROM (SELECT AASB.BATCH_ID,\n" +
                "                       MAX(AASH.HEADER_ID) HEADER_ID\n" +
                "                  FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
                "                       AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
                "                       AMS_ASSETS_SAMPLING_BATCH  AASB\n" +
                "                 WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
                "                       AND AASB.BATCH_ID = AASH.BATCH_ID\n" +
                "                       AND AASL.SCAN_STATUS = 'Y'\n" +
                "                 GROUP BY AASB.BATCH_ID,\n" +
                "                          AASH.SAMPLING_LOCATION) AA\n" +
                "         WHERE AA.BATCH_ID = AASB.BATCH_ID\n" +
                "               AND AA.HEADER_ID = AASH.HEADER_ID)\n" +
                "       AND AASB.BATCH_NO = ?";
        sqlArgs.add(dto.getBatchNo());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
