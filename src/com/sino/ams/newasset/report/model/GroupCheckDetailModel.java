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

public class GroupCheckDetailModel extends AMSSQLProducer {

	public GroupCheckDetailModel(SfUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取部门巡检地点明细SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " EO2.WORKORDER_OBJECT_NO CHECK_LOCATION,"
							+ " EO2.WORKORDER_OBJECT_CODE,"
							+ " dbo.APP_GET_FLEX_VALUE(EO2.OBJECT_CATEGORY, 'OBJECT_CATEGORY') OBJECT_CATEGORY,"
							+ " EO2.WORKORDER_OBJECT_NAME CHECK_LOCATION_NAME,"
							+ " EO2.WORKORDER_OBJECT_LOCATION"
							+ " FROM"
							+ " ETS_OBJECT EO2,"
							+ " (SELECT DISTINCT"
							+ " ISNULL(AACL.SCAN_RESPONSIBILITY_DEPT, AACL.RESPONSIBILITY_DEPT) DEPT_CODE,"
							+ " EO.WORKORDER_OBJECT_NO"
							+ " FROM"
							+ " ETS_OBJECT              EO,"
							+ " AMS_ASSETS_CHECK_HEADER AACH,"
							+ " AMS_ASSETS_CHECK_LINE   AACL"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
							+ " AND AACH.HEADER_ID = AACL.HEADER_ID"
							+ " AND AACH.ORDER_STATUS IN ('UPLOADED', 'ARCHIEVED')"
							+ " AND ((AACH.UPLOAD_DATE >= ISNULL(?, AACH.UPLOAD_DATE)"
							+ " AND AACH.UPLOAD_DATE <= ISNULL(?, AACH.UPLOAD_DATE))"
							+ " OR (AACH.ARCHIVED_DATE >= ISNULL(?, AACH.ARCHIVED_DATE)"
							+ " AND AACH.ARCHIVED_DATE <= ISNULL(?, AACH.ARCHIVED_DATE)))) TMP_V"
							+ " WHERE"
							+ " EO2.WORKORDER_OBJECT_NO = TMP_V.WORKORDER_OBJECT_NO"
							+ " AND (EO2.WORKORDER_OBJECT_CODE LIKE ISNULL(LTRIM(?), EO2.WORKORDER_OBJECT_CODE)"
							+ " OR EO2.WORKORDER_OBJECT_NAME LIKE ISNULL(LTRIM(?), EO2.WORKORDER_OBJECT_NAME)"
							+ " OR EO2.WORKORDER_OBJECT_LOCATION LIKE ISNULL(LTRIM(?), EO2.WORKORDER_OBJECT_LOCATION))"
							+ " AND TMP_V.DEPT_CODE = ?";

			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			//sqlArgs.add(dto.getStartDate());
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}
			//sqlArgs.add(dto.getSQLEndDate());
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			//sqlArgs.add(dto.getStartDate());
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}
			//sqlArgs.add(dto.getSQLEndDate());

			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getObjectCode());
			sqlArgs.add(dto.getObjectCode());

			sqlArgs.add(dto.getCheckDept());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取部门巡检设备明细SQL
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getItemsModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "SELECT"
							+ " AACL.BARCODE,"
							+ " dbo.APP_GET_FLEX_VALUE(ISNULL(AACL.SCAN_ITEM_CATEGORY, ESI.ITEM_CATEGORY), 'ITEM_TYPE') ITEM_CATEGORY,"
							+ " ISNULL(AACL.SCAN_ITEM_NAME, ESI.ITEM_NAME) ITEM_NAME,"
							+ " ISNULL(AACL.SCAN_ITEM_SPEC, ESI.ITEM_SPEC) ITEM_SPEC,"
							+ " ESI.ITEM_UNIT,"
							+ " AME.USER_NAME,"
							+ " AMD.DEPT_NAME,"
							+ " EO.WORKORDER_OBJECT_LOCATION"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH,"
							+ " AMS_ASSETS_CHECK_LINE   AACL,"
							+ " ETS_SYSTEM_ITEM         ESI,"
							+ " AMS_MIS_DEPT            AMD,"
							+ " AMS_MIS_EMPLOYEE        AME,"
							+ " ETS_ITEM_INFO           EII,"
							+ " ETS_OBJECT              EO"
							+ " WHERE"
							+ " EO.WORKORDER_OBJECT_NO = AACH.CHECK_LOCATION"
							+ " AND AACH.HEADER_ID = AACL.HEADER_ID"
							//+ " AND AACL.BARCODE *= EII.BARCODE"
							+ " AND AACL.BARCODE = EII.BARCODE"
							+ " AND ISNULL(AACL.SCAN_ITEM_CODE, EII.ITEM_CODE) = ESI.ITEM_CODE"
							+ " AND AACL.SCAN_RESPONSIBILITY_USER *= AME.EMPLOYEE_ID"
							+ " AND AACL.SCAN_RESPONSIBILITY_DEPT = AMD.DEPT_CODE"
							+ " AND AACL.SCAN_STATUS = 'Y'"
							+ " AND AMD.DEPT_CODE = ?"
							+ " AND AACH.HEADER_ID ="
							+ " (SELECT"
							+ " MAX(AACH2.HEADER_ID)"
							+ " FROM"
							+ " AMS_ASSETS_CHECK_HEADER AACH2,"
							+ " AMS_ASSETS_CHECK_LINE   AACL2"
							+ " WHERE"
							+ " AACH.CHECK_LOCATION = AACH2.CHECK_LOCATION"
							+ " AND AACH2.HEADER_ID = AACL2.HEADER_ID"
							+ " AND AACL2.BARCODE = AACL.BARCODE"
							+ " AND (AACH2.ORDER_STATUS = ? OR AACH2.ORDER_STATUS = ?)"
							+ " AND ((AACH2.UPLOAD_DATE >= ISNULL(?, AACH2.UPLOAD_DATE)"
							+ " AND AACH2.UPLOAD_DATE <= ISNULL(?, AACH2.UPLOAD_DATE))"
							+ " OR (AACH2.ARCHIVED_DATE >= ISNULL(?, AACH2.ARCHIVED_DATE)"
							+ " AND AACH2.ARCHIVED_DATE <= ISNULL(?, AACH2.ARCHIVED_DATE)))"
							+ " AND AACH2.CHECK_LOCATION = ?)";
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			sqlArgs.add(dto.getCheckDept());
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_UPLOADED);
			sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
			
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			//sqlArgs.add(dto.getStartDate());
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}
			//sqlArgs.add(dto.getSQLEndDate());
			if (dto.getStartDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getStartDate());
			}
			//sqlArgs.add(dto.getStartDate());
			if (dto.getSQLEndDate().toString().equals("")) {
				sqlArgs.add(null);
			} else {
				sqlArgs.add(dto.getSQLEndDate());
			}
			//sqlArgs.add(dto.getSQLEndDate());
			
			sqlArgs.add(dto.getCheckLocation());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}
}
