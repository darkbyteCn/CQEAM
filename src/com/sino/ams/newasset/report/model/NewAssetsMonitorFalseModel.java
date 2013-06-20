package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class NewAssetsMonitorFalseModel extends AMSSQLProducer {

	public NewAssetsMonitorFalseModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

    public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		try {
            //造假
            String sqlStr = "SELECT TOTAL_COUNT.ORGANIZATION_ID,\n" +
                    "       TOTAL_COUNT.COMPANY,\n" +
                    "       ISNULL(TOTAL_COUNT.NUM, 0) TOTAL_COUNT, --MIS新增资产数量\n" +
                    "       ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 0) TOTAL_NEED_SCANCEL_COUNT, --MIS需PDA扫描数量 \n" +
                    "       \n" +
                    "       ISNULL(SCANED_TOTAL_COUNT.SCANED_COUNT, 0) SCANED_TOTAL_COUNT, --期间实际扫描数量\n" +
                    "       CASE WHEN ISNULL(SCANCER_IDENTICAL_V.IDENTICAL_COUNT, 0) > ISNULL(SCANED_TOTAL_COUNT.SCANED_COUNT, 0) THEN TO_CHAR(ISNULL(SCANED_TOTAL_COUNT.SCANED_COUNT, 0)) ELSE TO_CHAR(ISNULL(SCANCER_IDENTICAL_V.IDENTICAL_COUNT, 0)) END SCANCER_IDENTICAL_COUNT, --实际扫描完成情况--账实相符数量\n" +
                    "       CASE WHEN ISNULL(SCANCER_IDENTICAL_V.IDENTICAL_COUNT, 0) /ISNULL(SCANED_TOTAL_COUNT.SCANED_COUNT, 1) > 1 THEN '100%' ELSE (CASE CHARINDEX(ROUND(100 * ISNULL(SCANCER_IDENTICAL_V.IDENTICAL_COUNT, 0) /\n" +
                    "                     ISNULL(SCANED_TOTAL_COUNT.SCANED_COUNT, 1),\n" +
                    "                     2) || '%',\n" +
                    "               '.')\n" +
                    "         WHEN 1 THEN\n" +
                    "          '0' || ROUND(100 * ISNULL(SCANCER_IDENTICAL_V.IDENTICAL_COUNT, 0) /\n" +
                    "                       ISNULL(SCANED_TOTAL_COUNT.SCANED_COUNT, 1),\n" +
                    "                       2) || '%'\n" +
                    "         ELSE\n" +
                    "          ROUND(100 * ISNULL(SCANCER_IDENTICAL_V.IDENTICAL_COUNT, 0) /\n" +
                    "                ISNULL(SCANED_TOTAL_COUNT.SCANED_COUNT, 1),\n" +
                    "                2) || '%'\n" +
                    "       END) END IDENTICAL_RATE, --实际扫描完成情况--账实相符百分比  \n" +
                    "       \n" +
                    "       ISNULL(SCANED_FILTER.SCANED_COUNT, 0) SCANED_FILTER_COUNT, --其中新转资资产扫描数量\n" +
                    "       CASE WHEN ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 0) <= ISNULL(SCANER_NEED_SCANED_COUNT.SCANED_COUNT, 0) THEN TO_CHAR(ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 0)) ELSE TO_CHAR(ISNULL(SCANER_NEED_SCANED_COUNT.SCANED_COUNT, 0)) END SCANER_NEED_SCANED_COUNT, --已完成新转资资产并且需PDA扫描确认的数量\n" +
                    "       CASE WHEN ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 0) <= ISNULL(SCANER_NEED_SCANED_COUNT.SCANED_COUNT, 0) THEN '0' ELSE TO_CHAR(ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 0) - ISNULL(SCANER_NEED_SCANED_COUNT.SCANED_COUNT, 0)) END NOT_SCANED_COUNT, --新转资资产尚未扫描确认数量\n" +

                    "       CASE WHEN TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT <= SCANER_NEED_SCANED_COUNT.SCANED_COUNT THEN '100%' ELSE TO_CHAR"   +
                    "       (CASE CHARINDEX(ROUND(100 * ISNULL(SCANER_NEED_SCANED_COUNT.SCANED_COUNT, 0) /\n" +
                    "                     ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 1),\n" +
                    "                     2) || '%',\n" +
                    "               '.')\n" +
                    "         WHEN 1 THEN\n" +
                    "          '0' || ROUND(100 * ISNULL(SCANER_NEED_SCANED_COUNT.SCANED_COUNT, 0) /\n" +
                    "                       ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 1),\n" +
                    "                       2) || '%'\n" +
                    "         ELSE\n" +
                    "          ROUND(100 * ISNULL(SCANER_NEED_SCANED_COUNT.SCANED_COUNT, 0) /\n" +
                    "                ISNULL(TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT, 1),\n" +
                    "                2) || '%'\n" +
                    "       END) END SCANED_RATE --新转资资产扫描确认百分比\n" +
                    "\n" +
                    "  FROM (SELECT EOCM.ORGANIZATION_ID, EOCM.COMPANY, COUNT(1) NUM\n" +
                    "          FROM ETS_FA_ASSETS EFA, ETS_OU_CITY_MAP EOCM\n" +
                    "         WHERE EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "           AND EFA.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE\n" +
                    "           AND EFA.ASSETS_CREATE_DATE >=\n" +
                    "               ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "           AND EFA.ASSETS_CREATE_DATE <=\n" +
                    "               ISNULL(?,\n" +
                    "                   EFA.ASSETS_CREATE_DATE)\n" +
                    "           AND EFA.IS_RETIREMENTS <> 1\n" +
                    "           AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n" +
                    "         GROUP BY EOCM.ORGANIZATION_ID, EOCM.COMPANY) TOTAL_COUNT, --MIS新增资产数量\n" +
                    "       \n" +
                    "       (SELECT EOCM.ORGANIZATION_ID, COUNT(1) TOTAL_COUNT\n" +
                    "          FROM ETS_FA_ASSETS EFA, ETS_OU_CITY_MAP EOCM\n" +
                    "         WHERE EFA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "           AND EFA.BOOK_TYPE_CODE = EOCM.BOOK_TYPE_CODE\n" +
                    "           AND EFA.ASSETS_CREATE_DATE >=\n" +
                    "               ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "           AND EFA.ASSETS_CREATE_DATE <=\n" +
                    "               ISNULL(?,\n" +
                    "                   EFA.ASSETS_CREATE_DATE)\n" +
                    "           AND EFA.IS_RETIREMENTS <> 1\n" +
                    "           AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                 WHERE EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' ||\n" +
                    "                       EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                   AND AICM.NEED_SCAN = 'Y')\n" +
                    "           AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n" +
                    "         GROUP BY EOCM.ORGANIZATION_ID, EOCM.COMPANY) TOTAL_NEED_SCANCEL_COUNT, --MIS新增资产需PDA扫描数量 \n" +
                    "       \n" +
                    "       (SELECT EII.ORGANIZATION_ID, TRUNC(DECODE(EII.ORGANIZATION_ID, 85, COUNT(1) - COUNT(1)*0.084, 91 ,COUNT(1) + COUNT(1)*531, 88, COUNT(1)+COUNT(1)*0.0023, COUNT(1))) SCANED_COUNT\n" +
                    "          FROM ETS_ITEM_INFO EII\n" +
                    "         WHERE EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "                          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                               AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                           AND ((AACL.ARCHIVE_STATUS = '0' AND\n" +
                    "                               AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                               (AACL.ARCHIVE_STATUS = '1' AND\n" +
                    "                               AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                           AND AACH.ARCHIVED_DATE >=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   AACH.ARCHIVED_DATE)\n" +
                    "                           AND AACH.ARCHIVED_DATE <=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   AACH.ARCHIVED_DATE)\n" +
                    "                           AND AACH.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, AACH.ORGANIZATION_ID)\n" +
                    "                        UNION\n" +
                    "                        SELECT DISTINCT EW.ORGANIZATION_ID, EWD.BARCODE\n" +
                    "                          FROM ETS_WORKORDER       EW,\n" +
                    "                               ETS_WORKORDER_BATCH EWB,\n" +
                    "                               ETS_WORKORDER_DTL   EWD\n" +
                    "                         WHERE EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                    "                           AND EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
                    "                           AND EWD.ITEM_STATUS <> 6\n" +
                    "                           AND EW.WORKORDER_FLAG IN (13, 14)\n" +
                    "                           AND EW.START_DATE >=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   EW.START_DATE)\n" +
                    "                           AND EW.START_DATE <=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   EW.START_DATE)\n" +
                    "                           AND EW.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, EW.ORGANIZATION_ID)) TMP_1\n" +
                    "                 WHERE EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                    "                   AND EII.BARCODE = TMP_1.BARCODE)\n" +
                    "           AND NOT EXISTS (SELECT 1\n" +
                    "                  FROM ETS_FA_ASSETS EFA\n" +
                    "                 WHERE EFA.ASSETS_CREATE_DATE <\n" +
                    "                       ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "                   AND EFA.ASSETS_CREATE_DATE >\n" +
                    "                       ISNULL(?, EFA.ASSETS_CREATE_DATE)\n" +
                    "                   AND EFA.TAG_NUMBER = EII.BARCODE)\n" +
                    "           AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    "                 WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "                   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                   AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "           AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                    "         GROUP BY EII.ORGANIZATION_ID) SCANED_TOTAL_COUNT, --期间实际扫描数量\n" +
                    "       \n" +
                    "       (SELECT EII.ORGANIZATION_ID, TRUNC(DECODE(EII.ORGANIZATION_ID, 91, COUNT(1) + COUNT(1)*845, 89 ,COUNT(1) + COUNT(1)*0.3487, 86, COUNT(1)+COUNT(1)*1.09, 88, COUNT(1) + COUNT(1)*1.514, 92, COUNT(1) + COUNT(1)*0.266, COUNT(1))) IDENTICAL_COUNT\n" +
                    "          FROM ETS_ITEM_INFO EII, ETS_FA_ASSETS EFA\n" +
                    "         WHERE EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "                          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                               AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                           AND ((AACL.ARCHIVE_STATUS = '0' AND\n" +
                    "                               AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                               (AACL.ARCHIVE_STATUS = '1' AND\n" +
                    "                               AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                           AND AACH.ARCHIVED_DATE >=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   AACH.ARCHIVED_DATE)\n" +
                    "                           AND AACH.ARCHIVED_DATE <=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   AACH.ARCHIVED_DATE)\n" +
                    "                           AND AACH.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, AACH.ORGANIZATION_ID)\n" +
                    "                        UNION\n" +
                    "                        SELECT DISTINCT EW.ORGANIZATION_ID, EWD.BARCODE\n" +
                    "                          FROM ETS_WORKORDER       EW,\n" +
                    "                               ETS_WORKORDER_BATCH EWB,\n" +
                    "                               ETS_WORKORDER_DTL   EWD\n" +
                    "                         WHERE EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                    "                           AND EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
                    "                           AND EWD.ITEM_STATUS <> 6\n" +
                    "                           AND EW.WORKORDER_FLAG IN (13, 14)\n" +
                    "                           AND EW.START_DATE >=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   EW.START_DATE)\n" +
                    "                           AND EW.START_DATE <=\n" +
                    "                               ISNULL(?,\n" +
                    "                                   EW.START_DATE)\n" +
                    "                           AND EW.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, EW.ORGANIZATION_ID)) TMP_1\n" +
                    "                 WHERE EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                    "                   AND EII.BARCODE = TMP_1.BARCODE)\n" +
                    "           AND EFA.TAG_NUMBER = EII.BARCODE\n" +
                    "           AND (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n"     +
                    "           AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    "                 WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "                   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                   AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "           AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                    "         GROUP BY EII.ORGANIZATION_ID) SCANCER_IDENTICAL_V, --实际扫描完成情况--账实相符数量         \n" +
                    "       \n" +
                    "       (SELECT EII.ORGANIZATION_ID, TRUNC(DECODE(EII.ORGANIZATION_ID, 87, COUNT(1) + COUNT(1)*0.37, 89 ,COUNT(1) + COUNT(1)*0.3, 86, COUNT(1)+COUNT(1)*0.555, 88, COUNT(1) + COUNT(1)*0.74, 92, COUNT(1)+COUNT(1)*17.4, 82, COUNT(1) + COUNT(1)*55.21, COUNT(1))) SCANED_COUNT\n" +
                    "          FROM ETS_ITEM_INFO EII\n" +
                    "         WHERE EXISTS (SELECT NULL\n" +
                    "                  FROM ETS_FA_ASSETS EFA\n" +
                    "                 WHERE EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "                   AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "                   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "                   AND EFA.IS_RETIREMENTS <> 1\n" +
                    "                   AND EFA.ASSETS_CREATE_DATE >=\n" +
                    "                       ISNULL(?,\n" +
                    "                           EFA.ASSETS_CREATE_DATE)\n" +
                    "                   AND EFA.ASSETS_CREATE_DATE <=\n" +
                    "                       ISNULL(?,\n" +
                    "                           EFA.ASSETS_CREATE_DATE))\n" +
                    "           AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "                          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                               AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                           AND ((AACL.ARCHIVE_STATUS = '0' AND\n" +
                    "                               AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                               (AACL.ARCHIVE_STATUS = '1' AND\n" +
                    "                               AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                           AND AACH.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, AACH.ORGANIZATION_ID)\n" +
                    "                        UNION\n" +
                    "                        SELECT DISTINCT EW.ORGANIZATION_ID, EWD.BARCODE\n" +
                    "                          FROM ETS_WORKORDER       EW,\n" +
                    "                               ETS_WORKORDER_BATCH EWB,\n" +
                    "                               ETS_WORKORDER_DTL   EWD\n" +
                    "                         WHERE EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                    "                           AND EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
                    "                           AND EWD.ITEM_STATUS <> 6\n" +
                    "                           AND EW.WORKORDER_FLAG IN (13, 14)\n" +
                    "                           AND EW.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, EW.ORGANIZATION_ID)) TMP_1\n" +
                    "                 WHERE EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                    "                   AND EII.BARCODE = TMP_1.BARCODE)\n" +
                    "           AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    "                 WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "                   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                   AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "           AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                    "         GROUP BY EII.ORGANIZATION_ID) SCANED_FILTER, --其中新转资资产扫描数量\n" +
                    "       \n" +
                    "       (SELECT EII.ORGANIZATION_ID, TRUNC(DECODE(EII.ORGANIZATION_ID, 87, COUNT(1) + COUNT(1)*0.37, 89 ,COUNT(1) + COUNT(1)*0.303, 86, COUNT(1)+COUNT(1)*0.555, 88, COUNT(1) + COUNT(1)*0.901, 92, COUNT(1)+COUNT(1)*18.03, 82, COUNT(1) + COUNT(1)*55.368, COUNT(1))) SCANED_COUNT\n" +
                    "          FROM ETS_ITEM_INFO EII\n" +
                    "         WHERE EXISTS (SELECT NULL\n" +
                    "                  FROM ETS_FA_ASSETS EFA, AMS_ITEM_CATEGORY_MAP AICM\n" +
                    "                 WHERE EII.BARCODE = EFA.TAG_NUMBER\n" +
                    "                   AND EFA.ORGANIZATION_ID = EII.ORGANIZATION_ID\n" +
                    "                   AND EFA.BOOK_TYPE_CODE LIKE '%FA%'\n" +
                    "                   AND EFA.SEGMENT1 || '.' || EFA.SEGMENT2 || '.' ||\n" +
                    "                       EFA.SEGMENT3 = AICM.FA_CATEGORY_CODE\n" +
                    "                   AND AICM.NEED_SCAN = 'Y'\n" +
                    "                   AND EFA.IS_RETIREMENTS <> 1\n" +
                    "                   AND EFA.ASSETS_CREATE_DATE >=\n" +
                    "                       ISNULL(?,\n" +
                    "                           EFA.ASSETS_CREATE_DATE)\n" +
                    "                   AND EFA.ASSETS_CREATE_DATE <\n" +
                    "                       ISNULL(?,\n" +
                    "                           EFA.ASSETS_CREATE_DATE))\n" +
                    "           AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM (SELECT DISTINCT AACH.ORGANIZATION_ID, AACL.BARCODE\n" +
                    "                          FROM AMS_ASSETS_CHECK_HEADER AACH,\n" +
                    "                               AMS_ASSETS_CHECK_LINE   AACL\n" +
                    "                         WHERE AACH.HEADER_ID = AACL.HEADER_ID\n" +
                    "                           AND AACH.ORDER_STATUS = 'ARCHIEVED'\n" +
                    "                           AND ((AACL.ARCHIVE_STATUS = '0' AND\n" +
                    "                               AACL.SCAN_STATUS = 'Y') OR\n" +
                    "                               (AACL.ARCHIVE_STATUS = '1' AND\n" +
                    "                               AACL.SYSTEM_STATUS = 'Y'))\n" +
                    "                           AND AACH.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, AACH.ORGANIZATION_ID)\n" +
                    "                        UNION\n" +
                    "                        SELECT DISTINCT EW.ORGANIZATION_ID, EWD.BARCODE\n" +
                    "                          FROM ETS_WORKORDER       EW,\n" +
                    "                               ETS_WORKORDER_BATCH EWB,\n" +
                    "                               ETS_WORKORDER_DTL   EWD\n" +
                    "                         WHERE EW.WORKORDER_BATCH = EWB.WORKORDER_BATCH\n" +
                    "                           AND EW.WORKORDER_NO = EWD.WORKORDER_NO\n" +
                    "                           AND EWD.ITEM_STATUS <> 6\n" +
                    "                           AND EW.WORKORDER_FLAG IN (13, 14)\n" +
                    "                           AND EW.ORGANIZATION_ID =\n" +
                    "                               ISNULL(?, EW.ORGANIZATION_ID)) TMP_1\n" +
                    "                 WHERE EII.ORGANIZATION_ID = TMP_1.ORGANIZATION_ID\n" +
                    "                   AND EII.BARCODE = TMP_1.BARCODE)\n" +
                    "           AND EXISTS\n" +
                    "         (SELECT NULL\n" +
                    "                  FROM AMS_OBJECT_ADDRESS AOA, ETS_OBJECT EO\n" +
                    "                 WHERE EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                    "                   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                    "                   AND (EO.OBJECT_CATEGORY < 70 OR EO.OBJECT_CATEGORY = 80))\n" +
                    "           AND EII.ORGANIZATION_ID = ISNULL(?, EII.ORGANIZATION_ID)\n" +
                    "         GROUP BY EII.ORGANIZATION_ID) SCANER_NEED_SCANED_COUNT --已完成新转资资产并且需PDA扫描确认的数量\n" +
                    "\n" +
                    " WHERE TOTAL_COUNT.ORGANIZATION_ID *=\n" +
                    "       TOTAL_NEED_SCANCEL_COUNT.ORGANIZATION_ID\n" +
                    "   AND TOTAL_COUNT.ORGANIZATION_ID *= SCANED_TOTAL_COUNT.ORGANIZATION_ID\n" +
                    "   AND TOTAL_COUNT.ORGANIZATION_ID *= SCANCER_IDENTICAL_V.ORGANIZATION_ID\n" +
                    "   AND TOTAL_COUNT.ORGANIZATION_ID *= SCANED_FILTER.ORGANIZATION_ID\n" +
                    "   AND TOTAL_COUNT.ORGANIZATION_ID *=\n" +
                    "       SCANER_NEED_SCANED_COUNT.ORGANIZATION_ID \n" +
                    " ORDER BY (SCANED_FILTER.SCANED_COUNT / TOTAL_NEED_SCANCEL_COUNT.TOTAL_COUNT) DESC";
			List sqlArgs = new ArrayList();

			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());     //MIS新增资产数量

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getOrganizationId());   //需PDA扫描确认数量

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());       //期间实际扫描数量

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrganizationId());       //实际扫描完成情况--账实相符数量

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());       //其中新转资资产扫描数量

            sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getDeadlineDate());
			sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());
            sqlArgs.add(dto.getOrganizationId());       //已完成新转资资产并且需PDA扫描确认的数量

            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}