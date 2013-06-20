package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
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
public class ChkLocReportModel extends AMSSQLProducer {

	public ChkLocReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造盘点地点统计SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
			String sqlStr = "SELECT"
							+ " TMP_1.ORGANIZATION_ID,"
							+ " TMP_1.COMPANY,"
							+ " TMP_1.OBJECT_CATEGORY,"
							+ " TMP_1.OBJECT_CATEGORY_NAME,"
							+ " TMP_1.TOTAL_COUNT,"
							+ " ISNULL(TMP_2.SCAN_OVER_COUNT, 0) SCAN_OVER_COUNT,"
							+ " (TMP_1.TOTAL_COUNT - ISNULL(TMP_2.SCAN_OVER_COUNT, 0)) NOT_SCAN_COUNT,"
							+ " ROUND(100 * ISNULL(TMP_2.SCAN_OVER_COUNT, 0) / TMP_1.TOTAL_COUNT, 2)||'%' SCAN_RATE"
							+ " FROM "
							+ " (SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " EO.OBJECT_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, ?) OBJECT_CATEGORY_NAME,"
							+ " COUNT(EO.WORKORDER_OBJECT_NO) TOTAL_COUNT"
							+ " FROM"
							+ " ETS_OBJECT EO,"
							+ " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EO.OBJECT_CATEGORY = ISNULL(?, EO.OBJECT_CATEGORY)"
							+ " AND (EO.OBJECT_CATEGORY < ? OR EO.OBJECT_CATEGORY = ?)"
							+ " GROUP BY"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " EO.OBJECT_CATEGORY) TMP_1,"
							+ " (SELECT"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " EO.OBJECT_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, ?) OBJECT_CATEGORY_NAME,"
							+ " COUNT(EO.WORKORDER_OBJECT_NO) SCAN_OVER_COUNT"
							+ " FROM"
							+ " ETS_OBJECT EO,"
							+ " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
							+ " AND EO.OBJECT_CATEGORY = ISNULL(?, EO.OBJECT_CATEGORY)"
							+ " AND (EO.OBJECT_CATEGORY < ? OR EO.OBJECT_CATEGORY = ?)"
							+ " AND EXISTS ("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
							+ " AND (AACH.ORDER_STATUS = ? OR AACH.ORDER_STATUS = ?)"
							+ " AND ((AACH.UPLOAD_DATE >= ISNULL(?, AACH.UPLOAD_DATE)"
							+ " AND AACH.UPLOAD_DATE <= ISNULL(?, AACH.UPLOAD_DATE))"
							+ " OR (AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)"
							+ " AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE))))"
							+ " GROUP BY"
							+ " EOCM.ORGANIZATION_ID,"
							+ " EOCM.COMPANY,"
							+ " EO.OBJECT_CATEGORY) TMP_2"
							+ " WHERE"
							+ " TMP_1.ORGANIZATION_ID *= TMP_2.ORGANIZATION_ID"
							+ " AND TMP_1.OBJECT_CATEGORY *= TMP_2.OBJECT_CATEGORY"
							+ " AND TMP_1.ORGANIZATION_ID = ISNULL(?, TMP_1.ORGANIZATION_ID)";
			List sqlArgs = new ArrayList();
			sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
			sqlArgs.add(dto.getObjectCategory());
			sqlArgs.add(AssetsDictConstant.INV_NORMAL);
			sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);

			sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
			sqlArgs.add(dto.getObjectCategory());
			sqlArgs.add(AssetsDictConstant.INV_NORMAL);
			sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);

			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getOrganizationId());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
