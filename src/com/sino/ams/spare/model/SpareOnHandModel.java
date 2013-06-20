package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: wangzp
 * Date: 2011-12-08
 * Functon; 备件库存量
 * To change this template use File | Settings | File Templates.
 */
public class SpareOnHandModel extends BaseSQLProducer {
    private AmsItemTransLDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public SpareOnHandModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (AmsItemTransLDTO) dtoParameter;
    }


    /**
     * 得到查询所有的MODEL
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsItemTransLDTO Situsdto = (AmsItemTransLDTO) dtoParameter;
        List strArg = new ArrayList();
        String sqlStr = "SELECT EO.OBJECT_CATEGORY,\n" +
                "       dbo.APP_GET_ORGNIZATION_NAME(EO.ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                "       dbo.APP_GET_FLEX_VALUE(EO.OBJECT_CATEGORY,\n" +
                "                                  'INV_TYPE') INV_TYPE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       ASI.OBJECT_NO,\n" +
                "       AMSC.BARCODE,\n" +
                "       AMSC.ITEM_CODE,\n" +
                "       ASV.VENDOR_ID,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AMSC.ITEM_CATEGORY,\n" +
                "       AMSC.SPARE_USAGE,\n" +
                "       AMSC.ITEM_NAME,\n" +
                "       AMSC.ITEM_SPEC,\n" +
                "       ASI.QUANTITY,\n" +
                "       ASI.RESERVE_QUANTITY,\n" +
                "       ASI.QUANTITY - ASI.RESERVE_QUANTITY USERABLE_QTY\n" +
                "  FROM ETS_OBJECT         EO,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                "       AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                "       AND ASV.VENDOR_ID = AMSC.VENDOR_ID\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.BARCODE = ?)\n" +
                "       AND (? =-1 OR EO.ORGANIZATION_ID = ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR EO.OBJECT_CATEGORY = ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR EO.WORKORDER_OBJECT_NAME = ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_NAME LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_SPEC LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.SPARE_USAGE LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR ASV.VENDOR_ID = ?)\n" +
                " ORDER BY AMSC.ITEM_CATEGORY,\n" +
                "          AMSC.SPARE_USAGE,\n" +
                "          AMSC.ITEM_NAME";
        strArg.add(Situsdto.getBarcode());
        strArg.add(Situsdto.getBarcode());
        strArg.add(Situsdto.getOrganizationId());
        strArg.add(Situsdto.getOrganizationId());
        strArg.add(Situsdto.getObjectCategory());
        strArg.add(Situsdto.getObjectCategory());
        strArg.add(Situsdto.getWorkorderObjectName());
        strArg.add(Situsdto.getWorkorderObjectName());
        strArg.add(Situsdto.getItemName());
        strArg.add(Situsdto.getItemName());
        strArg.add(Situsdto.getItemSpec());
        strArg.add(Situsdto.getItemSpec());
        strArg.add(Situsdto.getItemCategory());
        strArg.add(Situsdto.getItemCategory());
        strArg.add(Situsdto.getSpareUsage());
        strArg.add(Situsdto.getSpareUsage());
        strArg.add(Situsdto.getVendorId());
        strArg.add(Situsdto.getVendorId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }
    
    /**
     * 计算库存量
     * @return
     * @throws SQLModelException
     */
    public SQLModel getCount() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        AmsItemTransLDTO amsItemOnNet = (AmsItemTransLDTO) dtoParameter;
        String sqlStr = "SELECT SUM(ASI.QUANTITY) TATOL_COUNT1,\n" +
                "       SUM(ASI.QUANTITY - ASI.RESERVE_QUANTITY) TATOL_COUNT2\n" +
                "  FROM ETS_OBJECT         EO,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                "       AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                "       AND ASV.VENDOR_ID = AMSC.VENDOR_ID\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.BARCODE = ?)\n" +
                "       AND (? =-1 OR EO.ORGANIZATION_ID = ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR EO.OBJECT_CATEGORY = ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR EO.WORKORDER_OBJECT_NAME = ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_NAME LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_SPEC LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR AMSC.SPARE_USAGE LIKE ?)\n" +
                "       AND ("+SyBaseSQLUtil.isNull() +" OR ASV.VENDOR_ID = ?)\n" +
                " ORDER BY AMSC.ITEM_CATEGORY,\n" +
                "          AMSC.SPARE_USAGE,\n" +
                "          AMSC.ITEM_NAME";
        strArg.add(amsItemOnNet.getBarcode());
        strArg.add(amsItemOnNet.getBarcode());
        strArg.add(amsItemOnNet.getOrganizationId());
        strArg.add(amsItemOnNet.getOrganizationId());
        strArg.add(amsItemOnNet.getObjectCategory());
        strArg.add(amsItemOnNet.getObjectCategory());
        strArg.add(amsItemOnNet.getWorkorderObjectName());
        strArg.add(amsItemOnNet.getWorkorderObjectName());
        strArg.add(amsItemOnNet.getItemName());
        strArg.add(amsItemOnNet.getItemName());
        strArg.add(amsItemOnNet.getItemSpec());
        strArg.add(amsItemOnNet.getItemSpec());
        strArg.add(amsItemOnNet.getItemCategory());
        strArg.add(amsItemOnNet.getItemCategory());
        strArg.add(amsItemOnNet.getSpareUsage());
        strArg.add(amsItemOnNet.getSpareUsage());
        strArg.add(amsItemOnNet.getVendorId());
        strArg.add(amsItemOnNet.getVendorId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }

    public SQLModel getVendorName(String vendorId) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String sqlStr = "SELECT ASV.VENDOR_NAME FROM AMS_SPARE_VENDORS ASV WHERE ASV.VENDOR_ID = ?";
        strArg.add(vendorId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }
}
