package com.sino.ams.spare.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.dto.AmsSpareCategoryDTO;
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
public class SpareConfirmModel extends AMSSQLProducer {
	private AmsSpareCategoryDTO dto = null;

	public SpareConfirmModel(SfUserDTO userAccount, AmsSpareCategoryDTO dtoParameter) {
		super(userAccount, dtoParameter);
		this.dto = (AmsSpareCategoryDTO) dtoParameter;
	}

    public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
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
                "       AMSC.ENABLED\n" +
                "  FROM AMS_SPARE_CATEGORY AMSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND ENABLED<>'N'" +
                "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.BARCODE LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_NAME LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_SPEC LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull()+" OR ASV.VENDOR_ID = ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull()+" OR AMSC.SPARE_USAGE LIKE ?)";
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemName());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemSpec());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getItemCategory());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getVendorId());
        sqlArgs.add(dto.getSpareUsage());
        sqlArgs.add(dto.getSpareUsage());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)dtoParameter;
		String sqlStr = "SELECT AMSC.BARCODE,\n" +
                "       AMSC.ITEM_NAME,\n" +
                "       AMSC.ITEM_SPEC,\n" +
                "       AMSC.ITEM_CATEGORY,\n" +
                "       AMSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_ID,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AMSC.ITEM_UNIT\n" +
                "  FROM AMS_SPARE_CATEGORY AMSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND AMSC.BARCODE = ?";
		sqlArgs.add(amsSpareCategory.getBarcode());

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
     * 更新数量
     * @param fromBarcode
     * @param toBarcode
     * @return
     */
    public SQLModel updateCategoryQuantity(String fromBarcode, String toBarcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE AMS_SPARE_INFO \n" +
	                "    SET QUANTITY         = QUANTITY +\n" +
	                "                              (SELECT T.QUANTITY\n" +
	                "                                 FROM AMS_SPARE_INFO T\n" +
	                "                                WHERE T.BARCODE = ?\n" +
	                "                                      AND T.ORGANIZATION_ID = AMS_SPARE_INFO.ORGANIZATION_ID\n" +
	                "                                      AND T.OBJECT_NO = AMS_SPARE_INFO.OBJECT_NO),\n" +
	                "     RESERVE_QUANTITY = RESERVE_QUANTITY +\n" +
	                "                              (SELECT T.RESERVE_QUANTITY\n" +
	                "                                 FROM AMS_SPARE_INFO T\n" +
	                "                                WHERE T.BARCODE = ?\n" +
	                "                                      AND T.ORGANIZATION_ID = AMS_SPARE_INFO.ORGANIZATION_ID\n" +
	                "                                      AND T.OBJECT_NO = AMS_SPARE_INFO.OBJECT_NO)\n" +
	                "    WHERE BARCODE = ?\n" +
	                "       AND EXISTS (SELECT 1\n" +
	                "          FROM AMS_SPARE_INFO T\n" +
	                "         WHERE T.BARCODE = ?\n" +
	                "               AND T.ORGANIZATION_ID = AMS_SPARE_INFO.ORGANIZATION_ID\n" +
	                "               AND T.OBJECT_NO = AMS_SPARE_INFO.OBJECT_NO)";
		sqlArgs.add(fromBarcode);
		sqlArgs.add(fromBarcode);
		sqlArgs.add(toBarcode);
        sqlArgs.add(fromBarcode);
        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel replaceCategory(String fromBarcode, String toBarcode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE AMS_SPARE_INFO \n" +
                "   SET BARCODE = ?\n" +
                " WHERE BARCODE = ?\n" +
                "       AND NOT EXISTS (SELECT 1\n" +
                "          FROM AMS_SPARE_INFO T\n" +
                "         WHERE T.BARCODE = ?\n" +
                "               AND T.ORGANIZATION_ID = AMS_SPARE_INFO.ORGANIZATION_ID\n" +
                "               AND T.OBJECT_NO = AMS_SPARE_INFO.OBJECT_NO)";
		sqlArgs.add(toBarcode);
		sqlArgs.add(fromBarcode);
		sqlArgs.add(toBarcode);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    public SQLModel getSameCategoryModel(String fromBarcode, String toBarcode) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASI.SPARE_ID\n" +
                "  FROM AMS_SPARE_INFO ASI\n" +
                " WHERE EXISTS (SELECT 1\n" +
                "          FROM AMS_SPARE_INFO T\n" +
                "         WHERE T.BARCODE = ?\n" +
                "               AND T.ORGANIZATION_ID = ASI.ORGANIZATION_ID\n" +
                "               AND T.OBJECT_NO = ASI.OBJECT_NO)\n" +
                "       AND ASI.BARCODE = ?";
        sqlArgs.add(toBarcode);
        sqlArgs.add(fromBarcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 删除掉被替换的备件分类信息
     */
    public SQLModel deleteSameCategory(String spareId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_SPARE_INFO  WHERE SPARE_ID = ?";
        sqlArgs.add(spareId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 对被替换掉的备件分类进行失效
     */
    public SQLModel deleteFromCategory(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_SPARE_CATEGORY  SET ENABLED='N' WHERE BARCODE = ?";
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
