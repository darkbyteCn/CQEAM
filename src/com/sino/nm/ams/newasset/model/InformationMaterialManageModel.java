package com.sino.nm.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.nm.ams.newasset.dto.InformationMaterialDeleteDTO;
import com.sino.nm.ams.newasset.dto.InformationMaterialManageDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

public class InformationMaterialManageModel extends AMSSQLProducer {

	public InformationMaterialManageModel(SfUserDTO userAccount,
			InformationMaterialManageDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "";
		List sqlArgs = new ArrayList();

		try {
			InformationMaterialManageDTO dto = (InformationMaterialManageDTO) this.dtoParameter;

			sqlStr = "SELECT " + "EMII.ITEM_ID, " + "EMII.BARCODE,EMII.MSS_BARCODE,"
					+ "EMII.ITEM_NAME, " + "EMII.ITEM_SPEC, "
					+ "EMII.ITEM_STATUS, " + "EMII.ITEM_BRAND, "
					+ "EMII.ITEM_SERIAL, " + "EMII.CREATION_DATE, "
					+ "EMII.CREATED_BY, " + "EMII.USE_BY_SYSTEM, "
					+ "EMII.PRODUCT_ID, " + "EMII.RESPONSIBILITY_USER, "
					+ "EMII.RESPONSIBILITY_DEPT " + "FROM "
					+ "EAM_MSS_ITEM_INFO EMII " + "WHERE "
					+ "(? = '' OR EMII.BARCODE LIKE ? ) " + "AND "
					+ "(? = '' OR EMII.ITEM_NAME LIKE ? ) " + "AND "
					+ "(? = '' OR EMII.ITEM_SPEC LIKE ? ) " + "AND "
					+ "(? = '' OR EMII.ITEM_BRAND LIKE ? ) " + "AND "
					+ "(? = '' OR EMII.CREATION_DATE >= ? ) " + "AND "
					+ "(? = '' OR EMII.CREATION_DATE < DATEADD(day,1,?) )"
					+ "ORDER BY EMII.CREATION_DATE";

			sqlArgs.add(dto.getBarcode());
			sqlArgs.add("%" + dto.getBarcode() + "%");
			sqlArgs.add(dto.getItemName());
			sqlArgs.add("%" + dto.getItemName() + "%");
			sqlArgs.add(dto.getItemSpec());
			sqlArgs.add("%" + dto.getItemSpec() + "%");
			sqlArgs.add(dto.getItemBrand());
			sqlArgs.add("%" + dto.getItemBrand() + "%");
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getEndDate());

		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;

	}

	public SQLModel getDataDeleteModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "";
		List sqlArgs = new ArrayList();
		InformationMaterialDeleteDTO dto = (InformationMaterialDeleteDTO) this.dtoParameter;

		sqlStr = "DELETE " + "FROM " + "EAM_MSS_ITEM_INFO "
				+ "WHERE ITEM_ID = ? ";

		sqlArgs.add(dto.getItemId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "";
		List sqlArgs = new ArrayList();
		InformationMaterialManageDTO dto = (InformationMaterialManageDTO) this.dtoParameter;
		sqlStr = "INSERT INTO " + " EAM_MSS_ITEM_INFO( " + " ITEM_ID, BARCODE,"
				+ " ITEM_NAME, ITEM_SPEC, " + " ITEM_STATUS, ITEM_BRAND, "
				+ " ITEM_SERIAL, CREATION_DATE, "
				+ " CREATED_BY, LAST_UPDATE_DATE, " + " USE_BY_SYSTEM, "
				+ " PRODUCT_ID, RESPONSIBILITY_USER, "
				+ " RESPONSIBILITY_DEPT, MEMORY," + " CPU, IP_ADDRESS, "
				+ " DISK_INFORMATION, SYSTEM_NAME, "
				+ " TRUSTEESHIP_TYPE, IMPORTANT_LEVEL, "
				+ " ITEM_CATEGORY1, ITEM_CATEGORY2, "
				+ " ENABLED, DISABLE_DATE, " + " ITEM_CATEGORY3, ATTRIBUTE1, "
				+ " ATTRIBUTE2, ATTRIBUTE3) " + " VALUES( "
				+ " NEWID(), ?, " + " ?, ?, " + " ?, ?, "
				+ " ?, GETDATE(), " + " ?, GETDATE(), " + " ?, " + " ?, ?, "
				+ " ?, ?, " + " ?, ?, " + " ?, ?, " + " ?, ?, " + " ?, ?, "
				+ " ?, ?, " + " ?, ?, " + " ?, ? )";

		sqlArgs.add(dto.getBarcode());

		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());

		sqlArgs.add(dto.getItemStatus());
		sqlArgs.add(dto.getItemBrand());

		sqlArgs.add(dto.getItemSerial());

		sqlArgs.add(dto.getCreatedBy());

		sqlArgs.add(dto.getUseBySystem());

		sqlArgs.add(dto.getProductId());
		sqlArgs.add(dto.getResponsibilityUser());

		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getMemory());

		sqlArgs.add(dto.getCpu());
		sqlArgs.add(dto.getIpAddress());

		sqlArgs.add(dto.getDiskInformation());
		sqlArgs.add(dto.getSystemName());

		sqlArgs.add(dto.getTrusteeshipType());
		sqlArgs.add(dto.getImportantLevel());

		sqlArgs.add(dto.getItemCategory1());
		sqlArgs.add(dto.getItemCategory2());

		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getDisableDate());

		sqlArgs.add(dto.getItemCategory3());
		sqlArgs.add(dto.getAttribute1());

		sqlArgs.add(dto.getAttribute2());
		sqlArgs.add(dto.getAttribute3());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;

	}

	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		InformationMaterialManageDTO dto = (InformationMaterialManageDTO) this.dtoParameter;

		String sqlStr = "SELECT " + "ITEM_ID, " + "BARCODE, " + "ITEM_NAME, "
				+ "ITEM_SPEC, " + "ITEM_STATUS, " + "ITEM_BRAND, "
				+ "ITEM_SERIAL, " + "CREATION_DATE, " + "CREATED_BY, "
				+ "LAST_UPDATE_DATE," + "LAST_UPDATE_BY, " + "USE_BY_SYSTEM, "
				+ "PRODUCT_ID, " + "RESPONSIBILITY_USER, "
				+ "RESPONSIBILITY_DEPT, " + "MEMORY, " + "CPU, "
				+ "IP_ADDRESS, " + "DISK_INFORMATION, " + "SYSTEM_NAME, "
				+ "TRUSTEESHIP_TYPE, " + "IMPORTANT_LEVEL, "
				+ "ITEM_CATEGORY1, " + "ITEM_CATEGORY2, " + "ENABLED, "
				+ "DISABLE_DATE, " + "ITEM_CATEGORY3, " + "ATTRIBUTE1, "
				+ "ATTRIBUTE2, " + "ATTRIBUTE3 "
				+ "FROM EAM_MSS_ITEM_INFO EMII " + "WHERE EMII.ITEM_ID = ? ";
		sqlArgs.add(dto.getItemId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		InformationMaterialManageDTO dto = (InformationMaterialManageDTO) this.dtoParameter;

		String sqlStr = "UPDATE EAM_MSS_ITEM_INFO " + "SET " + "BARCODE = ?, "
				+ "ITEM_NAME = ?, " + "ITEM_SPEC = ?, " + "ITEM_STATUS = ?, "
				+ "ITEM_BRAND = ?, " + "ITEM_SERIAL = ?, " + "CREATED_BY = ?, "
				+ "LAST_UPDATE_BY = ?, " + "USE_BY_SYSTEM = ?, "
				+ "PRODUCT_ID = ?, " + "RESPONSIBILITY_USER = ?, "
				+ "RESPONSIBILITY_DEPT = ?, " + "LAST_UPDATE_DATE = GETDATE(), "
				+ "MEMORY = ?, " + "CPU = ?, " + "IP_ADDRESS = ?, "
				+ "DISK_INFORMATION = ?, " + "SYSTEM_NAME = ?, "
				+ "TRUSTEESHIP_TYPE = ?, " + "IMPORTANT_LEVEL = ?, "
				+ "ITEM_CATEGORY1 = ?, " + "ITEM_CATEGORY2 = ?, "
				+ "ENABLED = ?, " + "DISABLE_DATE = ?, "
				+ "ITEM_CATEGORY3 = ?, " + "ATTRIBUTE1 = ?, "
				+ "ATTRIBUTE2 = ?, " + "ATTRIBUTE3 =? " + "WHERE ITEM_ID = ? ";
		sqlArgs.add(dto.getBarcode());
		sqlArgs.add(dto.getItemName());
		sqlArgs.add(dto.getItemSpec());
		sqlArgs.add(dto.getItemStatus());
		sqlArgs.add(dto.getItemBrand());
		sqlArgs.add(dto.getItemSerial());
		sqlArgs.add(dto.getCreatedBy());
		sqlArgs.add(dto.getLastUpdateBy());
		sqlArgs.add(dto.getUseBySystem());
		sqlArgs.add(dto.getProductId());
		sqlArgs.add(dto.getResponsibilityUser());
		sqlArgs.add(dto.getResponsibilityDept());
		sqlArgs.add(dto.getMemory());
		sqlArgs.add(dto.getCpu());
		sqlArgs.add(dto.getIpAddress());
		sqlArgs.add(dto.getDiskInformation());
		sqlArgs.add(dto.getSystemName());
		sqlArgs.add(dto.getTrusteeshipType());
		sqlArgs.add(dto.getImportantLevel());
		sqlArgs.add(dto.getItemCategory1());
		sqlArgs.add(dto.getItemCategory2());
		sqlArgs.add(dto.getEnabled());
		sqlArgs.add(dto.getDisableDate());
		sqlArgs.add(dto.getItemCategory3());
		sqlArgs.add(dto.getAttribute1());
		sqlArgs.add(dto.getAttribute2());
		sqlArgs.add(dto.getAttribute3());
		sqlArgs.add(dto.getItemId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}
