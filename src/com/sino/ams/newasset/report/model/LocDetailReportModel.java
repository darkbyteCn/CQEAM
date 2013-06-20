package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
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
public class LocDetailReportModel extends AMSSQLProducer {

	public LocDetailReportModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取指定专业自有地点SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getOwnLocModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION,"
							+ " EC.COUNTY_NAME,"
							+ " EO.OBJECT_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, ?) OBJECT_CATEGORY_NAME"
							+ " FROM"
							+ " ETS_OBJECT EO,"
							+ " ETS_COUNTY EC,"
                            + " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EO.ORGANIZATION_ID = ?"
							+ " AND EO.OBJECT_CATEGORY = ?"
							+ " AND (EO.OBJECT_CATEGORY < ? OR EO.OBJECT_CATEGORY = ?)"
							+ " AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + " "
							+ " OR EO.DISABLE_DATE < ISNULL(?, EO.DISABLE_DATE + 1)"
							+ " OR EO.DISABLE_DATE > ISNULL(?, EO.DISABLE_DATE - 1))"
                            + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                            + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE "
							+ " AND EO.COUNTY_CODE = EC.COUNTY_CODE";
			List sqlArgs = new ArrayList();
			sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getObjectCategory());
			sqlArgs.add(AssetsDictConstant.INV_NORMAL);
			sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
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

	/**
	 * 功能：获取指定专业已扫描地点SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getScanedLocModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " EO.WORKORDER_OBJECT_NO,"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION,"
							+ " EC.COUNTY_NAME,"
							+ " EO.OBJECT_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, ?) OBJECT_CATEGORY_NAME"
							+ " FROM"
							+ " ETS_OBJECT EO,"
							+ " ETS_COUNTY EC,"
                            + " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EO.ORGANIZATION_ID = ?"
							+ " AND EO.OBJECT_CATEGORY = ?"
							+ " AND (EO.OBJECT_CATEGORY < ? OR EO.OBJECT_CATEGORY = ?)"
							+ " AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + " "
							+ " OR EO.DISABLE_DATE < ISNULL(?, EO.DISABLE_DATE + 1)"
							+ " OR EO.DISABLE_DATE > ISNULL(?, EO.DISABLE_DATE - 1))"
                            + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                            + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE "
							+ " AND EO.COUNTY_CODE = EC.COUNTY_CODE"
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
							+ " AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE))))";
			List sqlArgs = new ArrayList();
			sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getObjectCategory());
			sqlArgs.add(AssetsDictConstant.INV_NORMAL);
			sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
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

	/**
	 * 功能：获取指定专业未扫描地点SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getNotScanedLocModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " EO.WORKORDER_OBJECT_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION,"
							+ " EC.COUNTY_NAME,"
							+ " EO.OBJECT_CATEGORY,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EO.OBJECT_CATEGORY, ?) OBJECT_CATEGORY_NAME"
							+ " FROM"
							+ " ETS_OBJECT EO,"
							+ " ETS_COUNTY EC,"
                            + " ETS_OU_CITY_MAP EOCM"
							+ " WHERE"
							+ " EO.ORGANIZATION_ID = ?"
							+ " AND EO.OBJECT_CATEGORY = ?"
							+ " AND (EO.OBJECT_CATEGORY < ? OR EO.OBJECT_CATEGORY = ?)"
							+ " AND (EO.DISABLE_DAT " + SyBaseSQLUtil.isNull() + " "
							+ " OR EO.DISABLE_DATE < ISNULL(?, EO.DISABLE_DATE + 1)"
							+ " OR EO.DISABLE_DATE > ISNULL(?, EO.DISABLE_DATE - 1))"
                            + " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
                            + " AND EC.COMPANY_CODE = EOCM.COMPANY_CODE "
							+ " AND EO.COUNTY_CODE = EC.COUNTY_CODE"
							+ " AND NOT EXISTS ("
							+ " SELECT"
							+ " NULL"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
							+ " AND (AACH.ORDER_STATUS = ? OR AACH.ORDER_STATUS = ?)"
//							+ " AND AACH.ORDER_STATUS = ?"
							+ " AND ((AACH.UPLOAD_DATE >= ISNULL(?, AACH.UPLOAD_DATE)"
							+ " AND AACH.UPLOAD_DATE <= ISNULL(?, AACH.UPLOAD_DATE))"
							+ " OR (AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)"
							+ " AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE))))";
			List sqlArgs = new ArrayList();
			sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getObjectCategory());
			sqlArgs.add(AssetsDictConstant.INV_NORMAL);
			sqlArgs.add(AssetsDictConstant.NETADDR_OTHERS);
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
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
