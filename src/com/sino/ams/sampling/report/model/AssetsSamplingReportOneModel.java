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
 * Time: 9:07:55
 * To change this template use File | Settings | File Templates.
 */
public class AssetsSamplingReportOneModel extends AMSSQLProducer {

	public AssetsSamplingReportOneModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_UNIT,\n" +
                "       AMD.DEPT_NAME,\n" +
                "       AME.EMPLOYEE_NUMBER,\n" +
                "       AME.USER_NAME,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EPPA.SEGMENT1 PROJECT_NUMBER,\n" +
                "       EPPA.NAME PROJECT_NAME\n" +
                "  FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
                "       AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
                "       AMS_ASSETS_SAMPLING_BATCH  AASB,\n" +
                "       AMS_ASSETS_SAMPLING_TASK   AAST,\n" +
                "       ETS_ITEM_INFO              EII,\n" +
                "       ETS_OU_CITY_MAP            EOCM,\n" +
                "       ETS_SYSTEM_ITEM            ESI,\n" +
                "       AMS_MIS_DEPT               AMD,\n" +
                "       AMS_MIS_EMPLOYEE           AME,\n" +
                "       ETS_OBJECT                 EO,\n" +
                "       AMS_OBJECT_ADDRESS         AOA,\n" +
                "       ETS_PA_PROJECTS_ALL        EPPA\n" +
                " WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
                "       AND AASH.BATCH_ID = AASB.BATCH_ID\n" +
                "       AND AASB.TASK_ID = AAST.TASK_ID\n" +
                "       AND AASL.SCAN_STATUS = 'Y'\n" +
                "       AND AASL.BARCODE = EII.BARCODE\n" +
                "       AND EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "       AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "       AND EII.PROJECTID *= EPPA.PROJECT_ID\n" +
                "       AND AASL.ITEM_CODE = AASL.SCAN_ITEM_CODE\n" +
                "       AND AASL.RESPONSIBILITY_USER = AASL.SCAN_RESPONSIBILITY_USER\n" +
                "       AND AASL.RESPONSIBILITY_DEPT = AASL.SCAN_RESPONSIBILITY_DEPT\n" +
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
