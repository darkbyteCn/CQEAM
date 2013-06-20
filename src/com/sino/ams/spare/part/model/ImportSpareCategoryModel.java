package com.sino.ams.spare.part.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.part.dto.ImportSpareCategoryDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class ImportSpareCategoryModel extends AMSSQLProducer {
    private SfUserDTO sfUser = null;

    public ImportSpareCategoryModel(SfUserDTO userAccount, ImportSpareCategoryDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel deleteImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_SPARE_CATEGORY_IMPORT "
                + " WHERE"
                + " USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportSpareCategoryDTO dto = (ImportSpareCategoryDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_SPARE_CATEGORY_IMPORT\n" +
                "  (ID,\n" +
                "   ITEM_NAME,\n" +
                "   ITEM_SPEC,\n" +
                "   ITEM_CATEGORY,\n" +
                "   SPARE_USAGE,\n" +
                "   VENDOR_ID,\n" +
                "   ITEM_UNIT,\n" +
                "   ORGANIZATION_ID,\n" +
                "   USER_ID)\n" +
                "   VALUES\n" +
                "    (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(dto.getId());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getSpareUsage());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getItemUnit());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertImprotErrorData(String id, String codeError) throws
            SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_SPARE_CATEGORY_IMPORT SET ERROR = ? WHERE ID = ?";
        sqlArgs.add(codeError);
        sqlArgs.add(id);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateSameCategory(String itemName, String itemSpec, String itemCategory, String spareUsage, String vendorId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) RET_QTY\n" +
                "  FROM AMS_SPARE_CATEGORY_IMPORT ASCI\n" +
                " WHERE ASCI.ITEM_NAME = ?\n" +
                "       AND ASCI.ITEM_SPEC = ?\n" +
                "       AND ASCI.ITEM_CATEGORY = ?\n" +
                "       AND ASCI.SPARE_USAGE = ?\n" +
                "       AND ASCI.VENDOR_ID = ?";
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlArgs.add(itemCategory);
        sqlArgs.add(spareUsage);
        sqlArgs.add(vendorId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateExistCategory(String itemName, String itemSpec, String itemCategory, String spareUsage, String vendorId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM AMS_SPARE_CATEGORY ASCC\n" +
                " WHERE ASCC.ITEM_NAME = ?\n" +
                "       AND ASCC.ITEM_SPEC = ?\n" +
                "       AND ASCC.ITEM_CATEGORY = ?\n" +
                "       AND ASCC.SPARE_USAGE = ?\n" +
                "       AND ASCC.VENDOR_ID = ?";
        sqlArgs.add(itemName);
        sqlArgs.add(itemSpec);
        sqlArgs.add(itemCategory);
        sqlArgs.add(spareUsage);
        sqlArgs.add(vendorId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel validateVendorId(String vendorId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1 FROM AMS_SPARE_VENDORS ASV WHERE ASV.VENDOR_ID = ?";
        sqlArgs.add(vendorId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel hasErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT 1\n" +
                "  FROM AMS_SPARE_CATEGORY_IMPORT ASCI\n" +
                " WHERE ASCI.ERROR IS NOT NULL\n" +
                "       AND ASCI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getImportErrorModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASCI.ITEM_NAME,\n" +
                "       ASCI.ITEM_SPEC,\n" +
                "       ASCI.ITEM_CATEGORY,\n" +
                "       ASCI.SPARE_USAGE,\n" +
                "       ASCI.VENDOR_ID,\n" +
                "       ASCI.ITEM_UNIT,\n" +
                "       ASCI.ERROR\n" +
                "  FROM AMS_SPARE_CATEGORY_IMPORT ASCI\n" +
                " WHERE ASCI.ERROR IS NOT NULL\n" +
                "       AND ASCI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 导入数据到正式表 AMS_SPARE_CATEGORY
     */
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ImportSpareCategoryDTO dto = (ImportSpareCategoryDTO) dtoParameter;
        String sqlStr = "INSERT INTO AMS_SPARE_CATEGORY\n" +
	                "  (BARCODE,\n" +
	                "   ITEM_NAME,\n" +
	                "   ITEM_SPEC,\n" +
	                "   ITEM_CATEGORY,\n" +
	                "   SPARE_USAGE,\n" +
	                "   VENDOR_ID,\n" +
	                "   ITEM_UNIT,\n" +
	                "   ORGANIZATION_ID,\n" +
	                "   CREATION_DATE,\n" +
	                "   CREATED_BY)\n" +
	                "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";
        sqlArgs.add(dto.getBarcode());   
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getSpareUsage());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getItemUnit());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getQueryImportModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASCI.ITEM_NAME,\n" +
                "       ASCI.ITEM_SPEC,\n" +
                "       ASCI.ITEM_CATEGORY,\n" +
                "       ASCI.SPARE_USAGE,\n" +
                "       ASCI.VENDOR_ID,\n" +
                "       ASCI.ITEM_UNIT\n" +
                "  FROM AMS_SPARE_CATEGORY_IMPORT ASCI\n" +
                " WHERE ASCI.ERROR IS NULL\n" +
                "       AND ASCI.USER_ID = ?";
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
