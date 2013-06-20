package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class FreeAssetsQueryModel extends AMSSQLProducer {

	public FreeAssetsQueryModel(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：返回页面翻页查询时所需要的SQLModel
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getPageQueryModel() throws SQLModelException{
		SQLModel sqlModel = new SQLModel();
		try {
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;

			String sqlStr = "SELECT"
							+ " AAAV.BARCODE,"
							+ " AAAV.ASSET_NUMBER,"
							+ " AAAV.ASSETS_DESCRIPTION,"
							+ " AAAV.MODEL_NUMBER,"
							+ " AAAV.WORKORDER_OBJECT_CODE,"
							+ " AAAV.WORKORDER_OBJECT_LOCATION,"
							+ " AAAV.DEPT_NAME,"
							+ " AAAV.RESPONSIBILITY_USER_NAME,"
							+ " AAAV.EMPLOYEE_NUMBER,"
							+ " AAAV.UNIT_OF_MEASURE,"
							+ " AAAV.LIFE_IN_YEARS,"
							+ " AAAV.DATE_PLACED_IN_SERVICE,"
							+ " AATH.TRANS_NO,"
							+ " AATH.TRANS_DATE,"
							+ " SU.USERNAME,"
							+ " AAAV.COST,"
							+ " AAAV.DEPRN_COST,"
							+ " AAAV.SCRAP_VALUE,"
							+ " AAAV.DEPRECIATION_ACCOUNT,"
							+ " AAAV.DEPRECIATION_ACCOUNT_NAME"
							+ " FROM"
							+ " AMS_ASSETS_ADDRESS_V    AAAV,"
							+ " AMS_ASSETS_TRANS_LINE   AATL,"
							+ " AMS_ASSETS_TRANS_HEADER AATH,"
							+ " SF_USER                 SU"
							+ " WHERE"
							+ " AAAV.BARCODE = AATL.BARCODE"
							+ " AND AATL.TRANS_ID = AATH.TRANS_ID"
							+ " AND AATH.CREATED_BY = SU.USER_ID"
							+ " AND AAAV.ITEM_STATUS = ?"
							+ " AND AAAV.ASSETS_DESCRIPTION LIKE dbo.NVL(?, AAAV.ASSETS_DESCRIPTION)"
							+ " AND AAAV.BARCODE LIKE dbo.NVL(?, AAAV.BARCODE)"
							+ " AND AATH.TRANS_DATE >= dbo.NVL(?, AATH.TRANS_DATE)"
							+ " AND AATH.TRANS_DATE <= dbo.NVL(?, AATH.TRANS_DATE)"
							+ " AND AAAV.ORGANIZATION_ID = ?";
			List sqlArgs = new ArrayList();
			sqlArgs.add(AssetsDictConstant.ASSETS_STATUS_FREED);
			sqlArgs.add(dto.getAssetsDescription());
			sqlArgs.add(dto.getBarcode());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
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
