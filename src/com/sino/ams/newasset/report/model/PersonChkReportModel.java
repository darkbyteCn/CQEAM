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

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class PersonChkReportModel extends AMSSQLProducer {

	public PersonChkReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT EII.BARCODE,\n" +
							"       AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY,\n" +
							"       ESI.ITEM_NAME,\n" +
							"       ESI.ITEM_SPEC,\n" +
							"       EO.WORKORDER_OBJECT_CODE,\n" +
							"       EO.WORKORDER_OBJECT_NAME,\n" +
							"       AMD.DEPT_NAME,\n" +
							"       AME.USER_NAME,\n" +
							"       AME.EMPLOYEE_NUMBER,\n" +
							"       TMP_SCAN2.CHECK_USER,\n" +
							"       TMP_SCAN2.TRANS_NO,\n" +
							"       TMP_SCAN2.ARCHIVED_DATE\n" +
							"FROM   ETS_SYSTEM_ITEM ESI,\n" +
							"       ETS_ITEM_INFO EII,\n" +
							"       AMS_OBJECT_ADDRESS AOA,\n" +
							"       ETS_OBJECT EO,\n" +
							"       AMS_MIS_DEPT AMD,\n" +
							"       AMS_MIS_EMPLOYEE AME,\n" +
							"       (SELECT AACL.BARCODE,\n" +
							"               AACH.TRANS_NO,\n" +
							"               SU.USERNAME CHECK_USER,\n" +
							"               AACH.CHECK_LOCATION,\n" +
							"               AACH.ARCHIVED_DATE\n" +
							"        FROM   AMS_ASSETS_CHECK_LINE AACL,\n" +
							"               AMS_ASSETS_CHECK_HEADER AACH,\n" +
							"               SF_USER SU,\n" +
							"               (SELECT AACL2.BARCODE,\n" +
							"                       MAX(AACH2.HEADER_ID) HEADER_ID\n" +
							"                FROM   AMS_ASSETS_CHECK_LINE   AACL2,\n" +
							"                       AMS_ASSETS_CHECK_HEADER AACH2\n" +
							"                WHERE  AACL2.HEADER_ID = AACH2.HEADER_ID\n" +
							"                       AND AACL2.SCAN_STATUS = 'Y'\n" +
							"                       AND AACL2.ARCHIVE_STATUS = '0'\n" +
							"                       AND AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE)\n" +
							"                       AND AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE)\n" +
							"                GROUP  BY AACL2.BARCODE) TMP_SCAN\n" +
							"        WHERE  AACL.HEADER_ID = AACH.HEADER_ID\n" +
							"               AND AACH.IMPLEMENT_BY = SU.USER_ID\n" +
							"               AND SU.USERNAME LIKE ISNULL(?, SU.USERNAME)\n" +
							"               AND AACL.BARCODE = TMP_SCAN.BARCODE\n" +
							"               AND AACL.HEADER_ID = TMP_SCAN.HEADER_ID) TMP_SCAN2\n" +
							"WHERE  ESI.ITEM_CODE = EII.ITEM_CODE\n" +
							"       AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
							"       AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
							"       AND EO.WORKORDER_OBJECT_NO = TMP_SCAN2.CHECK_LOCATION\n" +
							"       AND EII.BARCODE = TMP_SCAN2.BARCODE\n" +
							"       AND EII.RESPONSIBILITY_DEPT *= AMD.DEPT_CODE\n" +
							"       AND EII.RESPONSIBILITY_USER *= AME.EMPLOYEE_ID\n" +
							"       AND (EO.WORKORDER_OBJECT_CODE LIKE ISNULL(?, EO.WORKORDER_OBJECT_CODE) OR\n" +
							"       EO.WORKORDER_OBJECT_NAME LIKE ISNULL(?, EO.WORKORDER_OBJECT_NAME))\n" +
							"       AND EII.BARCODE LIKE ISNULL(?, EII.BARCODE)\n" +
							"       AND ESI.ITEM_NAME LIKE ISNULL(?, ESI.ITEM_NAME)\n" +
							"       AND ( " + SyBaseSQLUtil.isNull() + "  OR AME.USER_NAME LIKE ISNULL(?, AME.USER_NAME))\n" +
							"       AND ( " + SyBaseSQLUtil.isNull() + "  OR AMD.DEPT_CODE = ISNULL(?, AMD.DEPT_CODE))\n" +
							"       AND EII.ORGANIZATION_ID = ?";
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getCheckLocationName());
			sqlArgs.add(dto.getCheckLocationName());
			sqlArgs.add(dto.getTagNumber());
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getResponsibilityUser());
			sqlArgs.add(dto.getDeptCode());
			sqlArgs.add(dto.getDeptCode());
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
