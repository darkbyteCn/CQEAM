package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.exception.*;

public class CheckDetailReportModel extends AMSSQLProducer {

	public CheckDetailReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取盘点统计报表SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr =
/*						 "SELECT AACH.TRANS_NO,\n" +
						 "       dbo.APP_GET_FLEX_VALUE(AACH.ORDER_STATUS, 'CHKORDER_STATUS') ORDER_STATUS,\n" +
						 "       AACL.BARCODE,\n" +
						 "       (CASE\n" +
						 "         WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "          AACL.SCAN_ITEM_NAME\n" +
						 "         ELSE\n" +
						 "          (SELECT ESI.ITEM_NAME\n" +
						 "           FROM   ETS_SYSTEM_ITEM ESI,\n" +
						 "                  ETS_ITEM_INFO   EII\n" +
						 "           WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                  AND EII.ITEM_CODE = ESI.ITEM_CODE)\n" +
						 "       END) ITEM_NAME,\n" +
						 "       (CASE\n" +
						 "         WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "          AACL.SCAN_ITEM_SPEC\n" +
						 "         ELSE\n" +
						 "          (SELECT ESI.ITEM_SPEC\n" +
						 "           FROM   ETS_SYSTEM_ITEM ESI,\n" +
						 "                  ETS_ITEM_INFO   EII\n" +
						 "           WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                  AND EII.ITEM_CODE = ESI.ITEM_CODE)\n" +
						 "       END) ITEM_SPEC,\n" +
						 "       (CASE\n" +
						 "         WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "          (SELECT AME.EMPLOYEE_NUMBER\n" +
						 "           FROM   AMS_MIS_EMPLOYEE AME\n" +
						 "           WHERE  AACL.SCAN_RESPONSIBILITY_USER = AME.EMPLOYEE_ID)\n" +
						 "         ELSE\n" +
						 "          (CASE\n" +
						 "         WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "          (SELECT AME.EMPLOYEE_NUMBER\n" +
						 "           FROM   AMS_MIS_EMPLOYEE AME\n" +
						 "           WHERE  AACL.SCAN_RESPONSIBILITY_USER = AME.EMPLOYEE_ID)\n" +
						 "         ELSE\n" +
						 "          (SELECT AME.EMPLOYEE_NUMBER\n" +
						 "           FROM   AMS_MIS_EMPLOYEE AME\n" +
						 "           WHERE  AACL.RESPONSIBILITY_USER = AME.EMPLOYEE_ID)\n" +
						 "       END) END) EMPLOYEE_NUMBER,\n" +
						 "       (CASE\n" +
						 "         WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "          (SELECT AME.USER_NAME\n" +
						 "           FROM   AMS_MIS_EMPLOYEE AME\n" +
						 "           WHERE  AACL.SCAN_RESPONSIBILITY_USER = AME.EMPLOYEE_ID)\n" +
						 "         ELSE\n" +
						 "          (CASE\n" +
						 "         WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "          (SELECT AME.USER_NAME\n" +
						 "           FROM   AMS_MIS_EMPLOYEE AME\n" +
						 "           WHERE  AACL.SCAN_RESPONSIBILITY_USER = AME.EMPLOYEE_ID)\n" +
						 "         ELSE\n" +
						 "          (SELECT AME.USER_NAME\n" +
						 "           FROM   AMS_MIS_EMPLOYEE AME\n" +
						 "           WHERE  AACL.RESPONSIBILITY_USER = AME.EMPLOYEE_ID)\n" +
						 "       END) END) RESPONSIBILITY_USER,\n" +
						 "       (CASE\n" +
						 "         WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "          (SELECT AMD.DEPT_NAME\n" +
						 "           FROM   AMS_MIS_DEPT AMD\n" +
						 "           WHERE  AACL.SCAN_RESPONSIBILITY_DEPT = AMD.DEPT_CODE)\n" +
						 "         ELSE\n" +
						 "          (CASE\n" +
						 "         WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "          (SELECT AMD.DEPT_NAME\n" +
						 "           FROM   AMS_MIS_DEPT AMD\n" +
						 "           WHERE  AACL.SCAN_RESPONSIBILITY_DEPT = AMD.DEPT_CODE)\n" +
						 "         ELSE\n" +
						 "          (SELECT AMD.DEPT_NAME\n" +
						 "           FROM   AMS_MIS_DEPT AMD\n" +
						 "           WHERE  AACL.RESPONSIBILITY_DEPT = AMD.DEPT_CODE)\n" +
						 "       END) END) DEPT_NAME,\n" +
						 "\n" +
						 "       (CASE\n" +
						 "         WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "          (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "           FROM   ETS_OBJECT EO\n" +
						 "           WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "         ELSE\n" +
						 "          (CASE\n" +
						 "         WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "          (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "           FROM   ETS_OBJECT EO\n" +
						 "           WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "         ELSE\n" +
						 "          (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "           FROM   ETS_ITEM_INFO      EII,\n" +
						 "                  AMS_OBJECT_ADDRESS AOA,\n" +
						 "                  ETS_OBJECT         EO\n" +
						 "           WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                  AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
						 "                  AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO)\n" +
						 "       END) END) WORKORDER_OBJECT_CODE,\n" +
						 "       (CASE\n" +
						 "         WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "          (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "           FROM   ETS_OBJECT EO\n" +
						 "           WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "         ELSE\n" +
						 "          (CASE\n" +
						 "         WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "          (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "           FROM   ETS_OBJECT EO\n" +
						 "           WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "         ELSE\n" +
						 "          (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "           FROM   ETS_ITEM_INFO      EII,\n" +
						 "                  AMS_OBJECT_ADDRESS AOA,\n" +
						 "                  ETS_OBJECT         EO\n" +
						 "           WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                  AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
						 "                  AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO)\n" +
						 "       END) END) WORKORDER_OBJECT_NAME,\n" +
						 "       SU.USERNAME CHECK_USER,\n" +
						 "       ISNULL(AACH.ARCHIVED_DATE, AACH.UPLOAD_DATE) CHECK_DATE\n" +
						 "FROM   AMS_ASSETS_CHECK_LINE AACL,\n" +
						 "       AMS_ASSETS_CHECK_HEADER AACH,\n" +
						 "       SF_USER SU,\n" +
						 
						 //"       (SELECT AACL2.ROWID\n" +
						 "		 (SELECT AACL2.HEADER_ID || AACL2.BARCODE ROW_ID \n" +
						 
						 "        FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
						 "               AMS_ASSETS_CHECK_HEADER AACH2\n" +
						 "        WHERE  AACL2.HEADER_ID = AACH2.HEADER_ID\n" +
						 "               AND\n" +
						 "               AACH2.HEADER_ID =\n" +
						 "               (SELECT MAX(AACH3.HEADER_ID)\n" +
						 "                FROM   AMS_ASSETS_CHECK_LINE   AACL3,\n" +
						 "                       AMS_ASSETS_CHECK_HEADER AACH3\n" +
						 "                WHERE  AACL2.BARCODE = AACL3.BARCODE\n" +
						 "                       AND AACL3.HEADER_ID = AACH3.HEADER_ID\n" +
						 "                       AND AACH3.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED')\n" +
						 "                       AND\n" +
						 "                       ((AACH3.UPLOAD_DATE >=\n" +
						 "                       ISNULL(?,\n" +
						 "                             AACH3.UPLOAD_DATE) AND\n" +
						 "                       AACH3.UPLOAD_DATE <= ISNULL(?, AACH3.UPLOAD_DATE)) OR\n" +
						 "                       (AACH3.ARCHIVED_DATE >=\n" +
						 "                       ISNULL(?,\n" +
						 "                             AACH3.ARCHIVED_DATE) AND\n" +
						 "                       AACH3.ARCHIVED_DATE <= ISNULL(?, AACH3.ARCHIVED_DATE))))) SCANED_V\n" +
						 "WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
						 "       AND AACH.SCANOVER_BY = SU.USER_ID\n" +
						 
						 //"       AND AACL.ROWID = SCANED_V.ROWID\n" +
						 "	     AND (AACL.HEADER_ID || AACL.BARCODE) = SCANED_V.ROW_ID \n" + 
						 
						 "       AND\n" +
						 "       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "        AACL.SCAN_RESPONSIBILITY_DEPT\n" +
						 "        ELSE(CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "             AACL.SCAN_RESPONSIBILITY_DEPT ELSE AACL.RESPONSIBILITY_DEPT END) END) =\n" +
						 "       ISNULL(LTRIM(?),\n" +
						 "           (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "            AACL.SCAN_RESPONSIBILITY_DEPT\n" +
						 "            ELSE(CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "                 AACL.SCAN_RESPONSIBILITY_DEPT ELSE AACL.RESPONSIBILITY_DEPT END) END))\n" +
						 "       AND\n" +
						 "       ((CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "        (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "          FROM   ETS_OBJECT EO\n" +
						 "          WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "        ELSE(CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "              (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "               FROM   ETS_OBJECT EO\n" +
						 "               WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO) ELSE\n" +
						 "              (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "               FROM   ETS_ITEM_INFO      EII,\n" +
						 "                      AMS_OBJECT_ADDRESS AOA,\n" +
						 "                      ETS_OBJECT         EO\n" +
						 "               WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                      AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
						 "                      AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO) END) END) LIKE\n" +
						 "       ISNULL(LTRIM(?),\n" +
						 "            (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "             (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "              FROM   ETS_OBJECT EO\n" +
						 "              WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "             ELSE(CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "                  (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "                   FROM   ETS_OBJECT EO\n" +
						 "                   WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO) ELSE\n" +
						 "                  (SELECT EO.WORKORDER_OBJECT_CODE\n" +
						 "                   FROM   ETS_ITEM_INFO      EII,\n" +
						 "                          AMS_OBJECT_ADDRESS AOA,\n" +
						 "                          ETS_OBJECT         EO\n" +
						 "                   WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                          AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
						 "                          AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO) END) END)) OR\n" +
						 "       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "        (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "          FROM   ETS_OBJECT EO\n" +
						 "          WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "        ELSE(CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "              (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "               FROM   ETS_OBJECT EO\n" +
						 "               WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO) ELSE\n" +
						 "              (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "               FROM   ETS_ITEM_INFO      EII,\n" +
						 "                      AMS_OBJECT_ADDRESS AOA,\n" +
						 "                      ETS_OBJECT         EO\n" +
						 "               WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                      AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
						 "                      AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO) END) END) LIKE\n" +
						 "       ISNULL(LTRIM(?),\n" +
						 "            (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN\n" +
						 "             (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "              FROM   ETS_OBJECT EO\n" +
						 "              WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO)\n" +
						 "             ELSE(CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN\n" +
						 "                  (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "                   FROM   ETS_OBJECT EO\n" +
						 "                   WHERE  AACH.CHECK_LOCATION = EO.WORKORDER_OBJECT_NO) ELSE\n" +
						 "                  (SELECT EO.WORKORDER_OBJECT_NAME\n" +
						 "                   FROM   ETS_ITEM_INFO      EII,\n" +
						 "                          AMS_OBJECT_ADDRESS AOA,\n" +
						 "                          ETS_OBJECT         EO\n" +
						 "                   WHERE  AACL.BARCODE = EII.BARCODE\n" +
						 "                          AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
						 "                          AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO) END) END)))\n" +
						 "       AND AACH.ORGANIZATION_ID = ISNULL(?, AACH.ORGANIZATION_ID)";*/

				"SELECT AACH.TRANS_NO, \n" +
				"       dbo.APP_GET_FLEX_VALUE(AACH.ORDER_STATUS, 'CHKORDER_STATUS') ORDER_STATUS, \n" +
				"       AACL.BARCODE, \n" +
				"       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN AACL.SCAN_ITEM_NAME \n" +
				"        ELSE (dbo.GET_ESI_ITEM_INFO(AACL.BARCODE, 'ITEM_NAME')) END) ITEM_NAME, \n" +
				"       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN AACL.SCAN_ITEM_SPEC \n" +
				"       ELSE (dbo.GET_ESI_ITEM_INFO(AACL.BARCODE, 'ITEM_SPEC')) END) ITEM_SPEC, \n" +
				"       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_AME_EMPLOYEE_NUMBER(AACL.SCAN_RESPONSIBILITY_USER)) \n" +
				"        ELSE \n" +
				"          (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_AME_EMPLOYEE_NUMBER(AACL.SCAN_RESPONSIBILITY_USER)) \n" +
				"           ELSE (dbo.GET_AME_EMPLOYEE_NUMBER(AACL.RESPONSIBILITY_USER)) END)  \n" +
				"        END) EMPLOYEE_NUMBER, \n" +
				"       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_AME_USER_NAME(AACL.SCAN_RESPONSIBILITY_USER)) \n" +
				"        ELSE \n" +
				"          (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_AME_USER_NAME(AACL.SCAN_RESPONSIBILITY_USER)) \n" +
				"           ELSE (dbo.GET_AME_USER_NAME(AACL.RESPONSIBILITY_USER)) END)  \n" +
				"        END) RESPONSIBILITY_USER, \n" +
				"       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_AMD_DEPT_NAME(AACL.SCAN_RESPONSIBILITY_DEPT)) \n" +
				"        ELSE \n" +
				"          (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_AMD_DEPT_NAME(AACL.SCAN_RESPONSIBILITY_DEPT)) \n" +
				"           ELSE (dbo.GET_AMD_DEPT_NAME(AACL.RESPONSIBILITY_DEPT)) END)  \n" +
				"        END) DEPT_NAME, \n" +
				"       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_CODE')) \n" +
				"        ELSE \n" +
				"          (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_CODE')) \n" +
				"           ELSE (dbo.GET_EAE_WORKORDER_OBJECT_INFO(AACL.BARCODE, 'OBJECT_CODE')) END)  \n" +
				"        END) WORKORDER_OBJECT_CODE, \n" +
				"       (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_NAME')) \n" +
				"        ELSE \n" +
				"          (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_NAME')) \n" +
				"           ELSE (dbo.GET_EAE_WORKORDER_OBJECT_INFO(AACL.BARCODE, 'OBJECT_NAME')) END)  \n" +
				"        END) WORKORDER_OBJECT_NAME, \n" +
				"       SU.USERNAME CHECK_USER, \n" +
				"       ISNULL(AACH.ARCHIVED_DATE, AACH.UPLOAD_DATE) CHECK_DATE, \n" +
				
				"       SU2.USERNAME ARCHIVED_USER,\n" +
				"       AACH.ARCHIVED_DATE\n" +
				
				"  FROM AMS_ASSETS_CHECK_LINE AACL, \n" +
				"       AMS_ASSETS_CHECK_HEADER AACH, \n" +
				"       SF_USER SU, \n" +
				
				"       SF_USER SU2,\n" +
				
				// SJ Modify   comment on 2011-11-21
//				"	   (SELECT AACL2.HEADER_ID || AACL2.BARCODE ROW_ID  \n" +
//				"          FROM AMS_ASSETS_CHECK_LINE AACL2, AMS_ASSETS_CHECK_HEADER AACH2 \n" +
//				"         WHERE AACL2.HEADER_ID = AACH2.HEADER_ID \n" +
//				"           AND AACH2.HEADER_ID = \n" +
//				"               (SELECT MAX(AACH3.HEADER_ID) \n" +
//				"                  FROM AMS_ASSETS_CHECK_LINE AACL3, AMS_ASSETS_CHECK_HEADER AACH3 \n" +
//				"                 WHERE AACL2.BARCODE = AACL3.BARCODE \n" +
//				"                   AND AACL3.HEADER_ID = AACH3.HEADER_ID \n" +
//				"                   AND AACH3.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED') \n" +
//				"                   AND ( (AACH3.UPLOAD_DATE >= ISNULL(?, AACH3.UPLOAD_DATE) AND AACH3.UPLOAD_DATE <= ISNULL(?, AACH3.UPLOAD_DATE))  \n" +
//				"                        OR  \n" +
//				"                         (AACH3.ARCHIVED_DATE >= ISNULL(?, AACH3.ARCHIVED_DATE) AND AACH3.ARCHIVED_DATE <= ISNULL(?, AACH3.ARCHIVED_DATE)) \n" +
//				"                       ) \n" +
//				"                ) \n" +
//				"       ) SCANED_V \n" +
				
				// SJ Modify   add on 2011-11-21
				"      (SELECT \n" +
				"       MAX(AACH3.HEADER_ID) || AACL3.BARCODE ROW_ID  \n" +
				"       FROM \n" +
				"       	AMS_ASSETS_CHECK_LINE AACL3, \n" +
				"       	AMS_ASSETS_CHECK_HEADER AACH3  \n" +
				"       WHERE \n" +
				"       	  AACL3.HEADER_ID = AACH3.HEADER_ID  \n" +
				"       	AND AACH3.ORDER_STATUS IN ('UPLOADED', \n" +
				"       	'ARCHIEVED')  \n" +
				"       	AND ( (AACH3.UPLOAD_DATE >= ISNULL(?, AACH3.UPLOAD_DATE)  \n" +
				"       	AND AACH3.UPLOAD_DATE <= ISNULL(?, AACH3. UPLOAD_DATE))  \n" +
				"       	OR (AACH3.ARCHIVED_DATE >= ISNULL(?, AACH3.ARCHIVED_DATE)  \n" +
				"       	AND AACH3.ARCHIVED_DATE <= ISNULL(?, AACH3. ARCHIVED_DATE)) ) \n" +
				"           GROUP BY AACL3.BARCODE ) SCANED_V  \n" +
		            
				" WHERE AACL.HEADER_ID = AACH.HEADER_ID \n" +
				"   AND AACH.SCANOVER_BY = SU.USER_ID \n" +
				
				"   AND AACH.ARCHIVED_BY *= SU2.USER_ID \n" +
				
				"   AND (AACL.HEADER_ID || AACL.BARCODE) = SCANED_V.ROW_ID  \n" +
				"   AND (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN AACL.SCAN_RESPONSIBILITY_DEPT \n" +
				"        ELSE \n" +
				"          (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN AACL.SCAN_RESPONSIBILITY_DEPT  \n" +
				"           ELSE AACL.RESPONSIBILITY_DEPT END)  \n" +
				"        END) = \n" +
				"        ISNULL(LTRIM(?), (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN AACL.SCAN_RESPONSIBILITY_DEPT \n" +
				"                           ELSE \n" +
				"                             (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN AACL.SCAN_RESPONSIBILITY_DEPT ELSE AACL.RESPONSIBILITY_DEPT END)  \n" +
				"                           END)) \n" +
				"   AND ( \n" +
				"        (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_CODE')) \n" +
				"         ELSE \n" +
				"           (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_CODE')) \n" +
				"            ELSE (dbo.GET_EAE_WORKORDER_OBJECT_INFO(AACL.BARCODE, 'OBJECT_CODE')) END)  \n" +
				"         END) LIKE \n" +
				"         ISNULL(LTRIM(?), (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_CODE')) \n" +
				"                            ELSE  \n" +
				"                              (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_CODE')) \n" +
				"                               ELSE (dbo.GET_EAE_WORKORDER_OBJECT_INFO(AACL.BARCODE, 'OBJECT_CODE')) END)  \n" +
				"                            END))  \n" +
				"        OR \n" +
				"        (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_NAME')) \n" +
				"         ELSE \n" +
				"           (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_NAME')) \n" +
				"            ELSE (dbo.GET_EAE_WORKORDER_OBJECT_INFO(AACL.BARCODE, 'OBJECT_NAME')) END)  \n" +
				"         END) LIKE \n" +
				"         ISNULL(LTRIM(?), (CASE WHEN AACH.ORDER_STATUS = 'UPLOADED' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_NAME')) \n" +
				"                            ELSE \n" +
				"                              (CASE WHEN AACL.ARCHIVE_STATUS = '0' THEN (dbo.GET_EO_WORKORDER_OBJECT_INFO(AACH.CHECK_LOCATION, 'OBJECT_NAME')) \n" +
				"                               ELSE (dbo.GET_EAE_WORKORDER_OBJECT_INFO(AACL.BARCODE, 'OBJECT_NAME')) END)  \n" +
				"                            END)) \n" +
				"       ) \n" +
				"   AND AACH.ORGANIZATION_ID = ISNULL(?, AACH.ORGANIZATION_ID) \n" ;
			
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}
			sqlArgs.add(dto.getCheckDept());
			sqlArgs.add(dto.getCheckLocationName());
			sqlArgs.add(dto.getCheckLocationName());
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
