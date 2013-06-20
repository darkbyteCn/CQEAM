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
public class BtsDetailReportModel extends AMSSQLProducer {

	public BtsDetailReportModel(BaseUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：获取每个基站的详细盘点情况，及与平均盘点时间的差异情况
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr =
			"SELECT EOCM.COMPANY,\n" +
			"       EO.WORKORDER_OBJECT_CODE,\n" +
			"       EO.WORKORDER_OBJECT_NAME,\n" +
			"       BTS_ASSETS_V.BTS_ASSETS_COUNT,\n" +
			"       BTS_SCAN_V.BTS_SCAN_TIME,\n" +
			"       ? BTS_CHK_TIME,\n" +
			"       BTS_SCAN_V.BTS_SCAN_TIME - ? LEFT_SCAN_TIME\n" +
			"FROM   ETS_OBJECT EO,\n" +
			"       ETS_OU_CITY_MAP EOCM,\n" +
			"       (SELECT AOA.OBJECT_NO,\n" +
			"               COUNT(1) BTS_ASSETS_COUNT\n" +
			"        FROM   ETS_ITEM_INFO      EII,\n" +
			"               AMS_OBJECT_ADDRESS AOA\n" +
			"        WHERE  EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
			"        GROUP  BY AOA.OBJECT_NO) BTS_ASSETS_V,\n" +
			"       (SELECT AACH.CHECK_LOCATION,\n" +
			"               ROUND(1440 * (AACH.UPLOAD_DATE - AACH.DOWNLOAD_DATE), 0) BTS_SCAN_TIME\n" +
			"        FROM   AMS_ASSETS_CHECK_HEADER AACH\n" +
			"        WHERE  AACH.HEADER_ID =\n" +
			"               (SELECT MAX(AACH2.HEADER_ID)\n" +
			"                FROM   AMS_ASSETS_CHECK_HEADER AACH2\n" +
			"                WHERE  AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION\n" +
			"                GROUP  BY AACH2.CHECK_LOCATION)) BTS_SCAN_V\n" +
			"WHERE  EO.WORKORDER_OBJECT_NO = BTS_ASSETS_V.OBJECT_NO\n" +
			"       AND EO.WORKORDER_OBJECT_NO = BTS_SCAN_V.CHECK_LOCATION\n" +
			"       AND EO.OBJECT_CATEGORY = 10\n" +
			"       AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + "  OR EO.DISABLE_DATE > GETDATE())\n" +
			"       AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
			"       AND EOCM.ORGANIZATION_ID = ?";

		sqlArgs.add(dto.getAverageChkTime());
		sqlArgs.add(dto.getAverageChkTime());
		sqlArgs.add(dto.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
