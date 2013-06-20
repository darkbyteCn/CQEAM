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
 * Time: 16:58:11
 * To change this template use File | Settings | File Templates.
 */
public class AssetsSamplingReportFourModel extends AMSSQLProducer {

	public AssetsSamplingReportFourModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AASL.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       AME.EMPLOYEE_NUMBER,\n" +
                "       AME.USER_NAME,\n" +
                "       AMD.DEPT_NAME\n" +
                "  FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
                "       AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
                "       AMS_ASSETS_SAMPLING_BATCH  AASB,\n" +
                "       AMS_ASSETS_SAMPLING_TASK   AAST,\n" +
                "       ETS_SYSTEM_ITEM            ESI,\n" +
                "       ETS_OBJECT                 EO,\n" +
                "       AMS_MIS_DEPT               AMD,\n" +
                "       AMS_MIS_EMPLOYEE           AME\n" +
                " WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
                "       AND AASH.BATCH_ID = AASB.BATCH_ID\n" +
                "       AND AASB.TASK_ID = AAST.TASK_ID\n" +
                "       AND AASL.SYSTEM_STATUS = 'Y'\n" +
                "       AND AASL.SCAN_STATUS = 'N'\n" +
                "       AND AASL.SCAN_ITEM_CODE = ESI.ITEM_CODE\n" +
                "       AND EO.WORKORDER_OBJECT_NO = AASH.SAMPLING_LOCATION\n" +
                "       AND AASL.SCAN_RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
                "       AND AASL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
                "       AND EXISTS (SELECT 1\n" +
                "          FROM (SELECT AASB.BATCH_ID,\n" +
                "                       MAX(AASH.HEADER_ID) HEADER_ID\n" +
                "                  FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
                "                       AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
                "                       AMS_ASSETS_SAMPLING_BATCH  AASB\n" +
                "                 WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
                "                       AND AASB.BATCH_ID = AASH.BATCH_ID\n" +
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