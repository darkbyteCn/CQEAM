package com.sino.ams.spare.part.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.part.dto.AmsSpareCategoryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class AmsSpareCategoryModel extends BaseSQLProducer {

	private SfUserDTO sfUser = null;

	public AmsSpareCategoryModel(SfUserDTO userAccount, AmsSpareCategoryDTO dtoParameter) {
		super(userAccount, dtoParameter);
		sfUser = userAccount;
	}

	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)dtoParameter;
        String sqlStr = "INSERT INTO "
            + " AMS_SPARE_CATEGORY("
            + " BARCODE,"
            + " ITEM_NAME,"
            + " ITEM_SPEC,"
            + " ITEM_CATEGORY,"
            + " SPARE_USAGE,"
            + " VENDOR_ID,"
            + " ITEM_UNIT,"
            + " REMARK,"
            + " CREATION_DATE,"
            + " CREATED_BY"
            + ") VALUES ("
            + " ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

        sqlArgs.add(amsSpareCategory.getBarcode());
        sqlArgs.add(amsSpareCategory.getItemName());
        sqlArgs.add(amsSpareCategory.getItemSpec());
        sqlArgs.add(amsSpareCategory.getItemCategory());
        sqlArgs.add(amsSpareCategory.getSpareUsage());
        sqlArgs.add(amsSpareCategory.getVendorId());
        sqlArgs.add(amsSpareCategory.getItemUnit());
        sqlArgs.add(amsSpareCategory.getRemark());
        sqlArgs.add(sfUser.getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)dtoParameter;
        String sqlStr = "UPDATE AMS_SPARE_CATEGORY"
            + " SET"
            + " ITEM_NAME = ?,"
            + " ITEM_SPEC = ?,"
            + " ITEM_CATEGORY = ?,"
            + " SPARE_USAGE = ?,"
            + " VENDOR_ID = ?,"
            + " ITEM_UNIT = ?,"
            + " REMARK = ?,"
            + " LAST_UPDATE_DATE = GETDATE(),"
            + " LAST_UPDATE_BY = ?"
            + " WHERE "
            + " BARCODE = ?";
        sqlArgs.add(amsSpareCategory.getItemName());
        sqlArgs.add(amsSpareCategory.getItemSpec());
        sqlArgs.add(amsSpareCategory.getItemCategory());
        sqlArgs.add(amsSpareCategory.getSpareUsage());
        sqlArgs.add(amsSpareCategory.getVendorId());
        sqlArgs.add(amsSpareCategory.getItemUnit());
        sqlArgs.add(amsSpareCategory.getRemark());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsSpareCategory.getBarcode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " AMS_SPARE_CATEGORY"
                + " WHERE BARCODE = ?"	;

        sqlArgs.add(amsSpareCategory.getBarcode());
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " AMSC.BARCODE,"
            + " AMSC.ITEM_NAME,"
			+ " AMSC.ITEM_SPEC,"
            + " AMSC.ITEM_CATEGORY,"
            + " AMSC.SPARE_USAGE,"
            + " AMSC.VENDOR_ID,"
            + " AMSC.CREATION_DATE,"
			+ " AMSC.CREATED_BY,"
			+ " AMSC.LAST_UPDATE_DATE,"
			+ " AMSC.LAST_UPDATE_BY,"
            + " AMSC.ITEM_UNIT,"
			+ " AMSC.REMARK,"
			+ " AMSC.ENABLED"
            + " FROM AMS_SPARE_CATEGORY AMSC"
            + " WHERE"
			+ " AMSC.BARCODE = ?";
		sqlArgs.add(amsSpareCategory.getBarcode());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getMuxDataModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)dtoParameter;
			String sqlStr = "SELECT"
				+ " BARCODE,"
				+ " ITEM_CODE,"
				+ " CREATION_DATE,"
				+ " CREATED_BY,"
				+ " LAST_UPDATE_DATE,"
				+ " LAST_UPDATE_BY,"
				+ " SPARE_USAGE,"
				+ " ORGANIZATION_ID,"
				+ " ITEM_NAME,"
				+ " ITEM_SPEC,"
				+ " ITEM_CATEGORY,"
				+ " VENDOR_ID,"
				+ " ITEM_UNIT,"
				+ " REMARK,"
				+ " ENABLED"
				+ " FROM"
				+ " AMS_SPARE_CATEGORY"
				+ " WHERE"
                + " ("+ SyBaseSQLUtil.isNull()+" OR BARCODE LIKE ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR ITEM_CODE LIKE ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR CREATION_DATE LIKE ?)"
				+ " AND (? = -1 OR CREATED_BY = ?)"
			    + " AND ("+SyBaseSQLUtil.isNull()+" OR LAST_UPDATE_DATE LIKE ?)"
		    	+ " AND (? = -1 OR LAST_UPDATE_BY = ?)"
				+ " AND ("+SyBaseSQLUtil.isNull()+" OR SPARE_USAGE LIKE ?)"
                + " AND (? = -1 OR ORGANIZATION_ID = ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR ITEM_NAME LIKE ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR ITEM_SPEC LIKE ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR ITEM_CATEGORY LIKE ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR VENDOR_ID = ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR ITEM_UNIT LIKE ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR REMARK LIKE ?)"
                + " AND ("+SyBaseSQLUtil.isNull()+" OR ENABLED LIKE ?)";
			sqlArgs.add(amsSpareCategory.getBarcode());
			sqlArgs.add(amsSpareCategory.getBarcode());
			sqlArgs.add(amsSpareCategory.getItemCode());
			sqlArgs.add(amsSpareCategory.getItemCode());
			sqlArgs.add(amsSpareCategory.getCreationDate());
			sqlArgs.add(amsSpareCategory.getCreationDate());
			sqlArgs.add(amsSpareCategory.getCreatedBy());
			sqlArgs.add(amsSpareCategory.getCreatedBy());
			sqlArgs.add(amsSpareCategory.getLastUpdateDate());
			sqlArgs.add(amsSpareCategory.getLastUpdateDate());
			sqlArgs.add(amsSpareCategory.getLastUpdateBy());
			sqlArgs.add(amsSpareCategory.getLastUpdateBy());
			sqlArgs.add(amsSpareCategory.getSpareUsage());
			sqlArgs.add(amsSpareCategory.getSpareUsage());
			sqlArgs.add(amsSpareCategory.getOrganizationId());
			sqlArgs.add(amsSpareCategory.getOrganizationId());
			sqlArgs.add(amsSpareCategory.getItemName());
			sqlArgs.add(amsSpareCategory.getItemName());
			sqlArgs.add(amsSpareCategory.getItemSpec());
			sqlArgs.add(amsSpareCategory.getItemSpec());
			sqlArgs.add(amsSpareCategory.getItemCategory());
			sqlArgs.add(amsSpareCategory.getItemCategory());
			sqlArgs.add(amsSpareCategory.getVendorId());
			sqlArgs.add(amsSpareCategory.getVendorId());
			sqlArgs.add(amsSpareCategory.getItemUnit());
			sqlArgs.add(amsSpareCategory.getItemUnit());
			sqlArgs.add(amsSpareCategory.getRemark());
			sqlArgs.add(amsSpareCategory.getRemark());
			sqlArgs.add(amsSpareCategory.getEnabled());
			sqlArgs.add(amsSpareCategory.getEnabled());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        try {
            AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)dtoParameter;
            String sqlStr = "SELECT AMSC.BARCODE,\n" +
                    "       AMSC.ITEM_CODE,\n" +
                    "       AMSC.CREATION_DATE,\n" +
                    "       AMSC.CREATED_BY,\n" +
                    "       AMSC.LAST_UPDATE_DATE,\n" +
                    "       AMSC.LAST_UPDATE_BY,\n" +
                    "       AMSC.SPARE_USAGE,\n" +
                    "       AMSC.ORGANIZATION_ID,\n" +
                    "       AMSC.ITEM_NAME,\n" +
                    "       AMSC.ITEM_SPEC,\n" +
                    "       AMSC.ITEM_CATEGORY,\n" +
                    "       AMSC.VENDOR_ID,\n" +
                    "       ASV.VENDOR_NAME,\n" +
                    "       AMSC.ITEM_UNIT,\n" +
                    "       AMSC.REMARK,\n" +
                    "       CASE AMSC.ENABLED WHEN 'Y' THEN 'ÊÇ' ELSE '·ñ' END ENABLED,\n" +
                    "       SU.USERNAME\n" +
                    "  FROM AMS_SPARE_CATEGORY AMSC,\n" +
                    "       AMS_SPARE_VENDORS  ASV,\n" +
                    "       SF_USER            SU\n" +
                    " WHERE AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                    "       AND AMSC.CREATED_BY *= SU.USER_ID\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_SPEC LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR ASV.VENDOR_ID LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.SPARE_USAGE LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.BARCODE LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR SU.USERNAME LIKE ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.CREATION_DATE >= ?)\n" +
                    "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.CREATION_DATE <= ?)";
            sqlArgs.add(amsSpareCategory.getItemName());
            sqlArgs.add(amsSpareCategory.getItemName());
            sqlArgs.add(amsSpareCategory.getItemSpec());
            sqlArgs.add(amsSpareCategory.getItemSpec());
            sqlArgs.add(amsSpareCategory.getItemCategory());
            sqlArgs.add(amsSpareCategory.getItemCategory());
            sqlArgs.add(amsSpareCategory.getVendorId());
            sqlArgs.add(amsSpareCategory.getVendorId());
            sqlArgs.add(amsSpareCategory.getSpareUsage());
            sqlArgs.add(amsSpareCategory.getSpareUsage());
            sqlArgs.add(amsSpareCategory.getBarcode());
            sqlArgs.add(amsSpareCategory.getBarcode());
            sqlArgs.add(amsSpareCategory.getCreatedByName());
            sqlArgs.add(amsSpareCategory.getCreatedByName());
            sqlArgs.add(amsSpareCategory.getStartDate());
            sqlArgs.add(amsSpareCategory.getStartDate());
            sqlArgs.add(amsSpareCategory.getSQLEndDate());
            sqlArgs.add(amsSpareCategory.getSQLEndDate());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
        return sqlModel;
	}

	public SQLModel getVerifyBarcode(String[] workorderObjectNos) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String orderno = ArrUtil.arrToStr(workorderObjectNos, ", ");
        String strSql ="SELECT EII.BARCODE\n" +
                "  FROM ETS_SYSTEM_ITEM    ESI,\n" +
                "       ETS_ITEM_INFO      EII,\n" +
                "       AMS_OBJECT_ADDRESS AOA,\n" +
                "       ETS_OBJECT         EO\n" +
                " WHERE ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
                "   AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EO.WORKORDER_OBJECT_NO IN ("+orderno+")";
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

	public SQLModel getVerifyWorkNoModel(AmsSpareCategoryDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql ="SELECT 1\n" +
                "  FROM AMS_SPARE_CATEGORY AMSC\n" +
                " WHERE AMSC.ITEM_NAME = ?\n" +
                "       AND AMSC.ITEM_SPEC = ?\n" +
                "       AND AMSC.ITEM_CATEGORY = ?\n" +
                "       AND AMSC.SPARE_USAGE = ?\n" +
                "       AND AMSC.VENDOR_ID = ?";
        strArg.add(dto.getItemName());
        strArg.add(dto.getItemSpec());
        strArg.add(dto.getItemCategory());
        strArg.add(dto.getSpareUsage());
        strArg.add(dto.getVendorId());
        sqlModel.setSqlStr(strSql);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }
}