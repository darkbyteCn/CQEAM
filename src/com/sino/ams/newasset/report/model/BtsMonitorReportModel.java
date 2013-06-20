package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class BtsMonitorReportModel extends AMSSQLProducer {

	public BtsMonitorReportModel(BaseUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造基站盘点用时监控SQL
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr =
			"SELECT ORGANIZATION_ID,\n" +
			"       COMPANY,\n" +
			"       TOTAL_BTS_COUNT,\n" +
			"       TOTAL_ASSETS_COUNT,\n" +
			"       SCANED_BTS_COUNT,\n" +
			"       SCANED_ASSETS_COUNT,\n" +
			"       SCANED_MINUTE,\n" +
			"       AVERAGE_MINUTE,\n" +
			"       ROWNUM AVERAGE_LOCATION\n" +
			"FROM   (SELECT EOCM.ORGANIZATION_ID,\n" +
			"               EOCM.COMPANY,\n" +
			"               TOTAL_BTS_V.TOTAL_BTS_COUNT,\n" +
			"               TOTAL_ASSETS_V.TOTAL_ASSETS_COUNT,\n" +
			"               SCANED_BTS_V.SCANED_BTS_COUNT,\n" +
			"               SCANED_ASSETS_V.SCANED_ASSETS_COUNT,\n" +
			"               SCANED_TIME_V.SCANED_MINUTE,\n" +
			"               ROUND(SCANED_TIME_V.SCANED_MINUTE /\n" +
			"                     ISNULL(SCANED_BTS_V.SCANED_BTS_COUNT, 1),\n" +
			"                     0) AVERAGE_MINUTE\n" +
			"        FROM   ETS_OU_CITY_MAP EOCM,\n" +
			"               (SELECT EO.ORGANIZATION_ID,\n" +
			"                       COUNT(1) TOTAL_BTS_COUNT\n" +
			"                FROM   ETS_OBJECT EO\n" +
			"                WHERE  EO.OBJECT_CATEGORY = 10\n" +
			"                       AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + "  OR EO.DISABLE_DATE > GETDATE())\n" +
			"                GROUP  BY EO.ORGANIZATION_ID) TOTAL_BTS_V,\n" +
			"               (SELECT EO.ORGANIZATION_ID,\n" +
			"                       COUNT(1) TOTAL_ASSETS_COUNT\n" +
			"                FROM   ETS_ITEM_INFO      EII,\n" +
			"                       AMS_OBJECT_ADDRESS AOA,\n" +
			"                       ETS_OBJECT         EO\n" +
			"                WHERE  EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
			"                       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
			"                       AND AOA.BOX_NO = '0000'\n" +
			"                       AND AOA.NET_UNIT = '0000'\n" +
			"                       AND EO.OBJECT_CATEGORY = 10\n" +
			"                       AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + "  OR EO.DISABLE_DATE > GETDATE())\n" +
			"                       AND\n" +
			"                       (EII.DISABLE_DAT " + SyBaseSQLUtil.isNull() + "  OR EII.DISABLE_DATE > GETDATE())\n" +
			"                GROUP  BY EO.ORGANIZATION_ID) TOTAL_ASSETS_V,\n" +
			"               (SELECT EO.ORGANIZATION_ID,\n" +
			"                       COUNT(1) SCANED_BTS_COUNT\n" +
			"                FROM   ETS_OBJECT EO\n" +
			"                WHERE  EO.OBJECT_CATEGORY = 10\n" +
			"                       AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + "  OR EO.DISABLE_DATE > GETDATE())\n" +
			"                       AND EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   AMS_ASSETS_CHECK_HEADER AACH\n" +
			"                        WHERE  EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION\n" +
			"                               AND AACH.ORDER_STATUS IN ('ARCHIEVED', 'UPLOADED'))\n" +
			"                GROUP  BY EO.ORGANIZATION_ID) SCANED_BTS_V,\n" +
			"               (SELECT AACL3.SCAN_ORGANIZATION_ID ORGANIZATION_ID,\n" +
			"                       COUNT(AACL3.BARCODE) SCANED_ASSETS_COUNT\n" +
			"                FROM   AMS_ASSETS_CHECK_LINE AACL3\n" +
			"                WHERE  EXISTS\n" +
			"                 (SELECT NULL\n" +
			"                        FROM   (SELECT AACL.ROWID\n" +
			"                                FROM   AMS_ASSETS_CHECK_LINE   AACL,\n" +
			"                                       AMS_ASSETS_CHECK_HEADER AACH\n" +
			"                                WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
			"                                       AND\n" +
			"                                       AACH.HEADER_ID =\n" +
			"                                       (SELECT MAX(AACH2.HEADER_ID)\n" +
			"                                        FROM   ETS_OBJECT              EO,\n" +
			"                                               AMS_ASSETS_CHECK_HEADER AACH2,\n" +
			"                                               AMS_ASSETS_CHECK_LINE   AACL2\n" +
			"                                        WHERE  AACL.BARCODE = AACL2.BARCODE\n" +
			"                                               AND AACL2.HEADER_ID = AACH2.HEADER_ID\n" +
			"                                               AND AACH2.CHECK_LOCATION =\n" +
			"                                               EO.WORKORDER_OBJECT_NO\n" +
			"                                               AND AACH2.ORDER_STATUS IN\n" +
			"                                               ('UPLOADED', 'ARCHIEVED')\n" +
			"                                               AND AACL2.SCAN_STATUS = 'Y'\n" +
			"                                               AND EO.OBJECT_CATEGORY = 10\n" +
			"                                               AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + "  OR\n" +
			"                                               EO.DISABLE_DATE > GETDATE()))) TMP_V\n" +
			"                        WHERE  AACL3.ROWID = TMP_V.ROWID)\n" +
			"                GROUP  BY AACL3.SCAN_ORGANIZATION_ID) SCANED_ASSETS_V,\n" +
			"               (SELECT AACH.ORGANIZATION_ID,\n" +
			"                       ROUND(SUM(1440 * (AACH.UPLOAD_DATE - AACH.DOWNLOAD_DATE)),\n" +
			"                             0) SCANED_MINUTE\n" +
			"                FROM   AMS_ASSETS_CHECK_HEADER AACH\n" +
			"                WHERE  AACH.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED')\n" +
			"                GROUP  BY AACH.ORGANIZATION_ID) SCANED_TIME_V\n" +
			"        WHERE  EOCM.ORGANIZATION_ID = TOTAL_BTS_V.ORGANIZATION_ID\n" +
			"               AND EOCM.ORGANIZATION_ID = TOTAL_ASSETS_V.ORGANIZATION_ID\n" +
			"               AND EOCM.ORGANIZATION_ID = SCANED_BTS_V.ORGANIZATION_ID\n" +
			"               AND EOCM.ORGANIZATION_ID = SCANED_ASSETS_V.ORGANIZATION_ID\n" +
			"               AND EOCM.ORGANIZATION_ID = SCANED_TIME_V.ORGANIZATION_ID\n" +
			"               AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)\n" +
			"        ORDER  BY SCANED_TIME_V.SCANED_MINUTE /\n" +
			"                  ISNULL(SCANED_BTS_V.SCANED_BTS_COUNT, 1))";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
