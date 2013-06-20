package com.sino.ams.sampling.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-9-17
 * Time: 15:52:23
 * To change this template use File | Settings | File Templates.
 */
public class AssetsSamplingReportModel extends AMSSQLProducer {

	public AssetsSamplingReportModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "SELECT TOTAL_1.COMPANY,\n" +
        "       TOTAL_1.BATCH_ID,\n" +
        "       TOTAL_1.BATCH_NO,\n" +
        "       TOTAL_1.END_DATE,\n" +
        "       TOTAL_1.SAMPLING_TYPE,\n" +
        "       TOTAL_1.SAMPLING_TYPE_VALUE,\n" +
//        "       (CONVERT(VARCHAR, TOTAL_1.SAMPLING_RATIO) || '%') SAMPLING_RATIO,\n" +
        "       STR(TOTAL_1.SAMPLING_RATIO, 10, 0) || '%' SAMPLING_RATIO,\n" +
        "       TOTAL_1.ITEM_COUNT,\n" +
        "       TOTAL_1.REQ_COUNT,\n" +
//        "       CASE CONVERT(VARCHAR, TOTAL_2.SCAN_COUNT) WHEN NULL THEN '0' ELSE CONVERT(VARCHAR, TOTAL_2.SCAN_COUNT) END SCAN_COUNT,\n" +
        "       CASE STR(TOTAL_2.SCAN_COUNT, 10, 0) WHEN NULL THEN '0' ELSE STR(TOTAL_2.SCAN_COUNT, 10, 0) END SCAN_COUNT,\n" +
//        "       (CASE CHARINDEX('.', CONVERT(VARCHAR, ROUND(100 * ISNULL(TOTAL_2.SCAN_COUNT, 0) / ISNULL(TOTAL_1.REQ_COUNT, 1), 2) ) || '%')\n" +
        "       (CASE CHARINDEX('.', STR(ROUND(100 * ISNULL(TOTAL_2.SCAN_COUNT, 0) / CASE ISNULL(TOTAL_1.REQ_COUNT, 1) WHEN 0 THEN 1 ELSE ISNULL(TOTAL_1.REQ_COUNT, 1) END, 2), 10, 2) || '%')\n" +
        "       WHEN 1 THEN\n" +
//        "       '0' || CONVERT(VARCHAR, ROUND(100 * ISNULL(TOTAL_2.SCAN_COUNT, 0) / ISNULL(TOTAL_1.REQ_COUNT, 1), 2)) || '%'\n" +
        "       '0' || STR(ROUND(100 * ISNULL(TOTAL_2.SCAN_COUNT, 0) / CASE ISNULL(TOTAL_1.REQ_COUNT, 1) WHEN 0 THEN 1 ELSE ISNULL(TOTAL_1.REQ_COUNT, 1) END, 2), 10, 2) || '%'\n" +
        "       ELSE\n" +
//        "       CONVERT(VARCHAR, ROUND(100 * ISNULL(TOTAL_2.SCAN_COUNT, 0) / ISNULL(TOTAL_1.REQ_COUNT, 1), 2)) || '%'\n" +
        "       STR(ROUND(100 * ISNULL(TOTAL_2.SCAN_COUNT, 0) / CASE ISNULL(TOTAL_1.REQ_COUNT, 1) WHEN 0 THEN 1 ELSE ISNULL(TOTAL_1.REQ_COUNT, 1) END, 2), 10, 2) || '%'\n" +
        "       END) FINISH_RATE,\n" +
//        "       CASE CONVERT(VARCHAR, TOTAL_3.IDENTICAL_COUNT) WHEN NULL THEN '0' ELSE CONVERT(VARCHAR, TOTAL_3.IDENTICAL_COUNT) END IDENTICAL_COUNT,\n" +
        "       CASE STR(TOTAL_3.IDENTICAL_COUNT, 10, 0) WHEN NULL THEN '0' ELSE STR(TOTAL_3.IDENTICAL_COUNT, 10, 0) END IDENTICAL_COUNT,\n" +
//        "       (CASE CHARINDEX('.', CONVERT(VARCHAR, ROUND(100 * ISNULL(TOTAL_3.IDENTICAL_COUNT, 0) / ISNULL(TOTAL_2.SCAN_COUNT, 1), 2)) || '%')\n" +
        "       (CASE CHARINDEX('.', STR(ROUND(100 * ISNULL(TOTAL_3.IDENTICAL_COUNT, 0) / CASE ISNULL(TOTAL_2.SCAN_COUNT, 1) WHEN 0 THEN 1 ELSE ISNULL(TOTAL_2.SCAN_COUNT, 1) END, 2), 10, 2) || '%')\n" +
        "       WHEN 1 THEN\n" +
//        "       '0' || CONVERT(VARCHAR, ROUND(100 * ISNULL(TOTAL_3.IDENTICAL_COUNT, 0) / ISNULL(TOTAL_2.SCAN_COUNT, 1), 2)) || '%'\n" +
        "       '0' || STR(ROUND(100 * ISNULL(TOTAL_3.IDENTICAL_COUNT, 0) / CASE ISNULL(TOTAL_2.SCAN_COUNT, 1) WHEN 0 THEN 1 ELSE ISNULL(TOTAL_2.SCAN_COUNT, 1) END, 2), 10, 2) || '%'\n" +
        "       ELSE\n" +
//        "       CONVERT(VARCHAR, ROUND(100 * ISNULL(TOTAL_3.IDENTICAL_COUNT, 0) / ISNULL(TOTAL_2.SCAN_COUNT, 1), 2)) || '%'\n" +
        "       STR(ROUND(100 * ISNULL(TOTAL_3.IDENTICAL_COUNT, 0) / CASE ISNULL(TOTAL_2.SCAN_COUNT, 1) WHEN 0 THEN 1 ELSE ISNULL(TOTAL_2.SCAN_COUNT, 1) END, 2), 10, 2) || '%'\n" +
        "       END) IDENTICAL_RATE\n" +
        "  FROM (SELECT EOCM.ORGANIZATION_ID,\n" +
        "               EOCM.COMPANY,\n" +
        "               AASB.BATCH_ID,\n" +
        "               AASB.BATCH_NO,\n" +
        "               AAST.END_DATE,\n" +
        "               AAST.SAMPLING_TYPE,\n" +
        "               dbo.APP_GET_FLEX_VALUE(AAST.SAMPLING_TYPE, 'SAMPLING_TYPE') SAMPLING_TYPE_VALUE,\n" +
        "               AAST.SAMPLING_RATIO SAMPLING_RATIO,\n" +
        "               TOTAL_ITEM.ITEM_COUNT,\n" +
        "               ROUND((AAST.SAMPLING_RATIO * TOTAL_ITEM.ITEM_COUNT) / 100, 2) REQ_COUNT\n" +
        "          FROM AMS_ASSETS_SAMPLING_BATCH AASB,\n" +
        "               ETS_OU_CITY_MAP EOCM,\n" +
        "               AMS_ASSETS_SAMPLING_TASK AAST,\n" +
        "               (SELECT COUNT(1) ITEM_COUNT,\n" +
        "                       EII.ORGANIZATION_ID\n" +
        "                  FROM ETS_ITEM_INFO EII\n" +
        "                 WHERE EII.DISABLE_DATE = NULL \n" +
        "                       AND EII.ITEM_STATUS <> 'DISCARDED'\n" +
        "                 GROUP BY EII.ORGANIZATION_ID) TOTAL_ITEM\n" +
        "         WHERE EOCM.ORGANIZATION_ID = AASB.ORGANIZATION_ID\n" +
        "               AND AASB.TASK_ID = AAST.TASK_ID\n" +
        "               AND EOCM.ORGANIZATION_ID = TOTAL_ITEM.ORGANIZATION_ID) TOTAL_1,\n" +
        "       (SELECT COUNT(DISTINCT(AASL.BARCODE)) SCAN_COUNT,\n" +
        "               AASH.BATCH_ID\n" +
        "          FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
        "               AMS_ASSETS_SAMPLING_LINE AASL,\n" +
        "               (SELECT AASB.BATCH_ID,\n" +
        "                       MAX(AASH.HEADER_ID) HEADER_ID\n" +
        "                  FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
        "                       AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
        "                       AMS_ASSETS_SAMPLING_BATCH  AASB\n" +
        "                 WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
        "                       AND AASB.BATCH_ID = AASH.BATCH_ID\n" +
        "                       AND AASL.SCAN_STATUS = 'Y'\n" +
        "                 GROUP BY AASB.BATCH_ID,\n" +
        "                          AASH.SAMPLING_LOCATION) AA\n" +
        "         WHERE AASH.BATCH_ID = AA.BATCH_ID\n" +
        "               AND AASH.HEADER_ID = AA.HEADER_ID\n" +
        "               AND AASH.HEADER_ID = AASL.HEADER_ID\n" +
        "               AND AASL.SCAN_STATUS = 'Y'\n" +
        "         GROUP BY AASH.BATCH_ID) TOTAL_2,\n" +
        "       (SELECT COUNT(1) IDENTICAL_COUNT,\n" +
        "               AASH.BATCH_ID\n" +
        "          FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
        "               AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
        "               AMS_ASSETS_SAMPLING_BATCH  AASB,\n" +
        "               AMS_ASSETS_SAMPLING_TASK   AAST\n" +
        "         WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
        "               AND AASH.BATCH_ID = AASB.BATCH_ID\n" +
        "               AND AASB.TASK_ID = AAST.TASK_ID\n" +
        "               AND AASL.SCAN_STATUS = 'Y'\n" +
        "               AND AASL.ITEM_CODE = AASL.SCAN_ITEM_CODE\n" +
        "               AND AASL.RESPONSIBILITY_USER = AASL.SCAN_RESPONSIBILITY_USER\n" +
        "               AND AASL.RESPONSIBILITY_DEPT = AASL.SCAN_RESPONSIBILITY_DEPT\n" +
        "               AND EXISTS (SELECT 1\n" +
        "                  FROM (SELECT AASB.BATCH_ID,\n" +
        "                               MAX(AASH.HEADER_ID) HEADER_ID\n" +
        "                          FROM AMS_ASSETS_SAMPLING_HEADER AASH,\n" +
        "                               AMS_ASSETS_SAMPLING_LINE   AASL,\n" +
        "                               AMS_ASSETS_SAMPLING_BATCH  AASB\n" +
        "                         WHERE AASH.HEADER_ID = AASL.HEADER_ID\n" +
        "                               AND AASB.BATCH_ID = AASH.BATCH_ID\n" +
        "                               AND AASL.SCAN_STATUS = 'Y'\n" +
        "                         GROUP BY AASB.BATCH_ID,\n" +
        "                                  AASH.SAMPLING_LOCATION) AA\n" +
        "                 WHERE AA.BATCH_ID = AASB.BATCH_ID\n" +
        "                       AND AA.HEADER_ID = AASH.HEADER_ID)\n" +
        "         GROUP BY AASH.BATCH_ID) TOTAL_3\n" +
        " WHERE TOTAL_1.BATCH_ID *= TOTAL_2.BATCH_ID\n" +
        "       AND TOTAL_1.BATCH_ID *= TOTAL_3.BATCH_ID\n" +
        "       AND ( ? = -1  OR TOTAL_1.ORGANIZATION_ID = ?)";
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(dto.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}