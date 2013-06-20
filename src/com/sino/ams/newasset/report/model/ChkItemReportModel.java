package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
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
public class ChkItemReportModel extends AMSSQLProducer {

	public ChkItemReportModel(SfUserDTO userAccount, AmsAssetsCheckLineDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造盘点设备统计SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckLineDTO dto = (AmsAssetsCheckLineDTO)dtoParameter;
			String itemCategorys = "('BSC', 'BTS', 'DATA', 'ELEC', 'EXCHG', 'NETOPT', 'TRANS', 'OTHERS')";
			String sqlStr = "SELECT"
							+ " TMP_1.ORGANIZATION_ID,"
							+ " TMP_1.COMPANY,"
							+ " TMP_1.ITEM_CATEGORY,"
							+ " TMP_1.ITEM_CATEGORY_NAME,"
							+ " TMP_1.TOTAL_COUNT,"
							+ " ISNULL(TMP_2.SCAN_OVER_COUNT, 0) SCAN_OVER_COUNT,"
							+ " ISNULL(TMP_3.NOT_SCAN_COUNT, 0) NOT_SCAN_COUNT,"
							+ " ROUND(100 * ISNULL(TMP_2.SCAN_OVER_COUNT, 0) / TMP_1.TOTAL_COUNT, 2) || '%' SCAN_RATE"
							+ " FROM"
							+ " (SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " COUNT(1) TOTAL_COUNT"
							+ " FROM"
							+ " ETS_ITEM_INFO          EII,"
							+ " ETS_SYSTEM_ITEM        ESI,"
							+ " ETS_SYSITEM_DISTRIBUTE ESD,"
							+ " ETS_OU_CITY_MAP        EOCM"
							+ " WHERE"
							+ " EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID"
							+ " AND ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EII.ITEM_CODE = ESD.ITEM_CODE"
							+ " AND ESD.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CATEGORY = ISNULL(?, ESI.ITEM_CATEGORY)"
							+ " AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)"
							+ " AND ESI.ITEM_CATEGORY IN " + itemCategorys
							+ " AND EII.FINANCE_PROP <> 'DZYH'"
							+ " AND EII.ATTRIBUTE " + SyBaseSQLUtil.isNull() + " "
							+ " GROUP BY"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY) TMP_1,"
							+ " ( SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY), 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " COUNT(1) SCAN_OVER_COUNT"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH,"
							+ " AMS_ASSETS_CHECK_LINE   AACL,"
							+ " ETS_SYSTEM_ITEM         ESI,"
							+ " ETS_ITEM_INFO           EII,"
							+ " ETS_OU_CITY_MAP         EOCM"
							+ " WHERE"
							+ " AACH.HEADER_ID = AACL.HEADER_ID"
							+ " AND AACL.BARCODE *= EII.BARCODE"
							+ " AND ISNULL(AACL.SCAN_ITEM_CODE, EII.ITEM_CODE) = ESI.ITEM_CODE"
							+ " AND ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) = ISNULL(?, ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY))"
							+ " AND ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY) IN " + itemCategorys
							+ " AND AACH.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)"
							+ " AND AACH.HEADER_ID = "
							+ " ( SELECT"
							+ " MAX(AACH2.HEADER_ID)"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH2,"
							+ " AMS_ASSETS_CHECK_LINE   AACL2"
							+ " WHERE"
							+ " AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION"
							+ " AND AACH2.HEADER_ID = AACL2.HEADER_ID"
							+ " AND AACL2.BARCODE = AACL.BARCODE"
							+ " AND AACL2.SCAN_STATUS = 'Y'"
							+ " AND (AACH2.ORDER_STATUS = ? OR AACH2.ORDER_STATUS = ?)"
							+ " AND ((AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE)"
							+ " AND AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE))"
							+ " OR (AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE)"
							+ " AND AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE))))"
							+ " GROUP BY"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY)) TMP_2,"
							+ " (SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ESI.ITEM_CATEGORY, 'ITEM_TYPE') ITEM_CATEGORY_NAME,"
							+ " COUNT(1) NOT_SCAN_COUNT"
							+ " FROM"
							+ " ETS_ITEM_INFO          EII,"
							+ " ETS_SYSTEM_ITEM        ESI,"
							+ " ETS_SYSITEM_DISTRIBUTE ESD,"
							+ " ETS_OU_CITY_MAP        EOCM"
							+ " WHERE"
							+ " EII.ORGANIZATION_ID = ESD.ORGANIZATION_ID"
							+ " AND ESD.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EII.ITEM_CODE = ESD.ITEM_CODE"
							+ " AND ESD.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND ESI.ITEM_CATEGORY = ISNULL(?, ESI.ITEM_CATEGORY)"
							+ " AND EOCM.ORGANIZATION_ID = ISNULL(?, EOCM.ORGANIZATION_ID)"
							+ " AND ESI.ITEM_CATEGORY IN " + itemCategorys
							+ " AND EII.FINANCE_PROP <> 'DZYH'"
							+ " AND EII.ATTRIBUTE " + SyBaseSQLUtil.isNull() + " "
							+ " AND NOT EXISTS ("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_LINE   AACL,"
							+ " AMS_ASSETS_CHECK_HEADER AACH"
							+ " WHERE"
							+ " AACL.HEADER_ID = AACH.HEADER_ID"
							+ " AND EXISTS ("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_LINE   AACL2,"
							+ " AMS_ASSETS_CHECK_HEADER AACH2"
							+ " WHERE"
							+ " AACL.BARCODE = AACL2.BARCODE"
							+ " AND AACL2.HEADER_ID = AACH2.HEADER_ID"
							+ " AND (AACH2.ORDER_STATUS = ? OR AACH2.ORDER_STATUS = ?)"
							+ " AND ((AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE)"
							+ " AND AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE))"
							+ " OR (AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE)"
							+ " AND AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE)))"
							+ " AND AACL2.SCAN_STATUS = 'Y'"
							+ " HAVING MAX(AACH2.HEADER_ID) = AACH.HEADER_ID)"
							+ " AND EII.BARCODE = AACL.BARCODE)"
							+ " GROUP BY"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " ESI.ITEM_CATEGORY) TMP_3"
							+ " WHERE"
							+ " TMP_1.ORGANIZATION_ID *= TMP_2.ORGANIZATION_ID"
							+ " AND TMP_1.ORGANIZATION_ID *= TMP_3.ORGANIZATION_ID"
							+ " AND TMP_1.ITEM_CATEGORY *= TMP_2.ITEM_CATEGORY"
							+ " AND TMP_1.ITEM_CATEGORY *= TMP_3.ITEM_CATEGORY";
			List sqlArgs = new ArrayList();

			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getOrganizationId());

			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getItemCategory());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
