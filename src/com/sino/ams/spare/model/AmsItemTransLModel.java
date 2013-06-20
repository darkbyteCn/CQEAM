package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemTransLModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;

    public AmsItemTransLModel(SfUserDTO userAccount, AmsItemTransLDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成备件事务行表(AMS) AMS_ITEM_TRANS_L数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        String sqlStr = "INSERT INTO "
            + " AMS_ITEM_TRANS_L("
            + " TRANS_ID,"
            + " LINE_ID,"
            + " BARCODE,"
            + " ITEM_CODE,"
            + " QUANTITY,"
            + " BATCH_NO,"
            + " STORAGE_ID,"
            + " REMARK,"
            + " BFJE,"
            + " REASONS "
            + ") VALUES ("
            + " ?, NEWID(), ?,?, CONVERT(FLOAT,?),?,?,?,CONVERT(FLOAT,?),?)";

        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getBarcode());
        sqlArgs.add(amsItemTransL.getItemCode());
        sqlArgs.add(amsItemTransL.getQuantity());   
        sqlArgs.add(amsItemTransL.getBatchNo());
        sqlArgs.add(amsItemTransL.getStorageId());
        sqlArgs.add(amsItemTransL.getRemarkl());
        sqlArgs.add(amsItemTransL.getBfje());
        sqlArgs.add(amsItemTransL.getReasons());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务行表(AMS) AMS_ITEM_TRANS_L数据更新SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据更新用SQLModel
     */
    public SQLModel getDataUpdateModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_TRANS_L"
            + " SET"
            + " TRANS_ID = ?,"
            + " BARCODE = ?"
            + " WHERE"
            + " LINE_ID = ?";

        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getBarcode());
        sqlArgs.add(amsItemTransL.getLineId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务行表(AMS) AMS_ITEM_TRANS_L数据删除SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据删除用SQLModel
     */
    public SQLModel getDataDeleteModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        String sqlStr = "DELETE FROM"
            + " AMS_ITEM_TRANS_L"
            + " WHERE"
            + " LINE_ID = ?";
        sqlArgs.add(amsItemTransL.getLineId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务行表(AMS) AMS_ITEM_TRANS_L数据详细信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getPrimaryKeyDataModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " TRANS_ID,"
            + " LINE_ID,"
            + " BARCODE"
            + " FROM"
            + " AMS_ITEM_TRANS_L"
            + " WHERE"
            + " LINE_ID = ?";
        sqlArgs.add(amsItemTransL.getLineId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务行表(AMS) AMS_ITEM_TRANS_L多条数据信息查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回多条数据信息查询用SQLModel
     */
    public SQLModel getDataMuxModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " TRANS_ID,"
            + " LINE_ID,"
            + " BARCODE"
            + " FROM"
            + " AMS_ITEM_TRANS_L"
            + " WHERE"
            + "(? IS NULL OR TRANS_ID LIKE ?)"
            + "(? IS NULL OR LINE_ID LIKE ?)"
            + "(? IS NULL OR BARCODE LIKE ?)";
        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getLineId());
        sqlArgs.add(amsItemTransL.getLineId());
        sqlArgs.add(amsItemTransL.getBarcode());
        sqlArgs.add(amsItemTransL.getBarcode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 transId 构造查询数据SQL。
     * 框架自动生成数据备件事务行表(AMS) AMS_ITEM_TRANS_L详细信息查询SQLModel，请根据实际需要修改。
     * @param transId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDataByTransIdModel(String transId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITL.LINE_ID,\n" +
                "       AITL.BARCODE,\n" +
                "       AC.ITEM_NAME,\n" +
                "       AC.ITEM_SPEC,\n" +
                "       AC.ITEM_CATEGORY,\n" +
                "       AC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AC.ITEM_UNIT,\n" +
                "       AITL.REMARK,\n" +
                "       AITL.REMARK REMARKL,\n" +
                "       AITL.QUANTITY,\n" +
                "       AITL.STORAGE_ID,\n" +
                "       AITL.BATCH_NO,\n" +
                "       AITL.BFJE,\n" +
                "       AITL.REASONS\n" +
                "  FROM AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_ITEM_TRANS_H   AITH,\n" +
                "       AMS_SPARE_CATEGORY AC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITL.BARCODE = AC.BARCODE\n" +
                "       AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "       AND AC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
     public SQLModel getDataByTransIdMode2(String transId){  //返修申领行明细
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASSC.BARCODE,\n" +
                "       ASI.SPARE_ID,\n" +
                "       ASSC.ITEM_NAME,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AITL.LINE_ID,\n" +
                "       AITL.REASONS,\n" +
                "       ASI.QUANTITY-ASI.RESERVE_QUANTITY ONHAND_QTY,\n" +
                "       AITL.QUANTITY,\n" +
                "       AITL.PRO_QTY,\n" +
                "       AITL.ORD_QTY,\n" +
                "       AMS_ITEM_TRANS_SX.GET_ACTUAL_QTY_SX(AITL.BARCODE, ?) ACTUAL_QTY\n" +
                "  FROM AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_SPARE_CATEGORY ASSC,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_ITEM_TRANS_H   AITH,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITL.BARCODE = ASSC.BARCODE\n" +
                "       AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "       AND ASI.BARCODE = AITL.BARCODE\n" +
                "       AND ASI.ORGANIZATION_ID = AITH.FROM_ORGANIZATION_ID\n" +
                "       AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                "       AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND EO.WORKORDER_OBJECT_NAME NOT LIKE '%工程%'\n" +
                "       AND EO.OBJECT_CATEGORY = 72\n" +
                "       AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getPrintDataByTransIdMode(String transId){ 
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASSC.BARCODE,\n" +
                "       ASSC.ITEM_NAME,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AITL.SERIAL_NO,\n" +
                "       AITL.LINE_ID,\n" +
                "       AITL.TROUBLE_REASON REASONS,\n" +
                "       AITL.TROUBLE_LOC,\n" +
                "       ASI.QUANTITY ONHAND_QTY,\n" +
                "       AITL.QUANTITY,\n" +
                "       AMS_ITEM_TRANS_SX.GET_ACTUAL_QTY_SX(AITL.BARCODE, ?) ACTUAL_QTY\n" +
                "  FROM AMS_ITEM_TRANS_D   AITL,\n" +
                "       AMS_SPARE_CATEGORY ASSC,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_ITEM_TRANS_H   AITH,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITL.BARCODE = ASSC.BARCODE\n" +
                "       AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "       AND ASI.BARCODE = AITL.BARCODE\n" +
                "       AND ASI.ORGANIZATION_ID = AITH.FROM_ORGANIZATION_ID\n" +
                "       AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                "       AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND EO.OBJECT_CATEGORY = 72\n" +
                "       AND AITL.IS_ALLOT = 0\n" +
                "       AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

     public SQLModel getPrintAllotByTransIdMode(String transId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASSC.ITEM_NAME,\n" +
                "       ASSC.BARCODE,\n" +
                "       AITL.SERIAL_NO,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       AITL.LINE_ID,\n" +
                "       AITL.TROUBLE_REASON REASONS,\n" +
                "       AITL.TROUBLE_LOC,\n" +
                "       ASI.QUANTITY ONHAND_QTY,\n" +
                "       AITL.QUANTITY,\n" +
                "       AMS_PUB_PKG.GET_VENDOR_NAME(ASSC.VENDOR_ID) VENDOR_NAME," +
                "       AMS_ITEM_TRANS_SX.GET_SX_ACTUAL_QTY(AITL.ORGANIZATION_ID,AITL.BARCODE, ?) ACTUAL_QTY \n" +
                "  FROM AMS_ITEM_TRANS_D   AITL,\n" +
                "       AMS_SPARE_CATEGORY ASSC,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_ITEM_TRANS_H   AITH\n" +
                " WHERE AITL.BARCODE = ASSC.BARCODE\n" +
                "   AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "   AND ASI.BARCODE = AITL.BARCODE\n" +
                "   AND ASI.ORGANIZATION_ID = AITH.FROM_ORGANIZATION_ID\n" +
                "   AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                "   AND EO.OBJECT_CATEGORY = 72\n" +
                "   AND AITL.IS_ALLOT =1\n"+
                "   AND AITL.TRANS_ID =?";
        sqlArgs.add(transId);
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByTransIdMode3(String transId){ //申领流程审批行明细
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASSC.ITEM_NAME,\n" +
                "       ASSC.BARCODE,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       AITL.LINE_ID,\n" +
                "       AITL.REASONS,\n" +
                "       ASI.QUANTITY ONHAND_QTY,\n" +
                "       AITL.QUANTITY," +
                "       AMS_ITEM_TRANS_SX.GET_ACTUAL_QTY_SX(AITL.BARCODE, ?) ACTUAL_QTY,\n" +
                "       AMS_PUB_PKG.GET_VENDOR_NAME(ASSC.VENDOR_ID) VENDOR_NAME\n" +
                "  FROM AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_SPARE_CATEGORY ASSC,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       ETS_OBJECT         EO\n" +
                " WHERE AITL.BARCODE = ASSC.BARCODE\n" +
                "   AND ASI.BARCODE = AITL.BARCODE\n" +
                "   AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel getDataByTransIdMode4(String transId){ //申领流程审批行明细
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASSC.BARCODE,\n" +
                "       ASSC.ITEM_NAME,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AITL.LINE_ID,\n" +
                "       AITL.REASONS,\n" +
                "       AITL.QUANTITY,\n" +
                "       dbo.AITS_GET_ACTUAL_QTY_SX(AITL.BARCODE, ?) ACTUAL_QTY\n" +
                "  FROM AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_SPARE_CATEGORY ASSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITL.BARCODE = ASSC.BARCODE\n" +
                "       AND ASSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getDataByTransIdModel1(String transId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITL.LINE_ID,\n" +
                "       AITL.QUANTITY,\n" +
                "       ASI.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ASI.OBJECT_NO,\n" +
                "       ASI.QUANTITY ONHAND_QTY\n" +
                "  FROM AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       AMS_ITEM_TRANS_L   AITL\n" +
                " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND ASI.BARCODE = AITL.BARCODE\n" +
                "   AND ASI.ITEM_STATUS = '待修'\n" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    /**
     * 功能：根据外键关联字段 barcodeNo 构造查询数据SQL。
     * 框架自动生成数据备件事务行表(AMS) AMS_ITEM_TRANS_L详细信息查询SQLModel，请根据实际需要修改。
     * @param barcodeNo String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDataByBarcodeNoModel(String barcodeNo){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " TRANS_ID,"
            + " LINE_ID"
            + " FROM"
            + " AMS_ITEM_TRANS_L"
            + " WHERE"
            + " BARCODE = ?";
        sqlArgs.add(barcodeNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键获取数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = null;
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        if(foreignKey.equals("transId")){
            sqlModel = getDataByTransIdModel(amsItemTransL.getTransId());
        } else if(foreignKey.equals("barcodeNo")){
            sqlModel = getDataByBarcodeNoModel(amsItemTransL.getBarcode());
        }
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 transId 构造数据删除SQL。
     * 框架自动生成数据备件事务行表(AMS) AMS_ITEM_TRANS_L 数据删除SQLModel，请根据实际需要修改。
     * @param transId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDeleteByTransIdModel(String transId){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                        + " AMS_ITEM_TRANS_L"
                        + " WHERE"
                        + " TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键关联字段 barcodeNo 构造数据删除SQL。
     * 框架自动生成数据备件事务行表(AMS) AMS_ITEM_TRANS_L 数据删除SQLModel，请根据实际需要修改。
     * @param barcodeNo String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    private SQLModel getDeleteByBarcodeNoModel(String barcodeNo){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                        + " AMS_ITEM_TRANS_L"
                        + " WHERE"
                        + " BARCODE = ?";
        sqlArgs.add(barcodeNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：根据外键字段删除数据
     * @param foreignKey 传入的外键字段名称。
     * @return SQLModel
     */
    public SQLModel getDeleteByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = null;
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        if(foreignKey.equals("transId")){
            sqlModel = getDeleteByTransIdModel(amsItemTransL.getTransId());
        } else if(foreignKey.equals("barcodeNo")){
            sqlModel = getDeleteByBarcodeNoModel(amsItemTransL.getBarcode());
        }
        return sqlModel;
    }

    /**
     * 功能：框架自动生成备件事务行表(AMS) AMS_ITEM_TRANS_L页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel(){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransLDTO amsItemTransL = (AmsItemTransLDTO)dtoParameter;
        String sqlStr = "SELECT "
            + " TRANS_ID,"
            + " LINE_ID,"
            + " BARCODE"
            + " FROM"
            + " AMS_ITEM_TRANS_L"
            + " WHERE"
            + " (? IS NULL OR TRANS_ID LIKE ?)"
            + "AND (? IS NULL OR LINE_ID LIKE ?)"
            + "AND (? IS NULL OR BARCODE LIKE ?)";
        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getTransId());
        sqlArgs.add(amsItemTransL.getLineId());
        sqlArgs.add(amsItemTransL.getLineId());
        sqlArgs.add(amsItemTransL.getBarcode());
        sqlArgs.add(amsItemTransL.getBarcode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }



    /**
     * 功能：根据外键关联字段 transId 构造查询数据SQL。
     * 框架自动生成数据备件事务行表(AMS) AMS_ITEM_TRANS_L详细信息查询SQLModel，请根据实际需要修改。
     * @param transId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getByHIdModel(String transId){        //备件出库行明细
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASI.SPARE_ID,\n" +
                "       AITL.LINE_ID,\n" +
                "       AITL.BARCODE,\n" +
                "       AC.ITEM_NAME,\n" +
                "       AC.ITEM_SPEC,\n" +
                "       AC.ITEM_CATEGORY,\n" +
                "       AC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AC.ITEM_UNIT,\n" +
                "       AITL.REMARK,\n" +
                "       AITL.QUANTITY,\n" +
                "       AITL.STORAGE_ID,\n" +
                "       AITL.BATCH_NO,\n" +
                "       AITL.BFJE,\n" +
                "       AITL.REASONS,\n" +
                "       ASI.QUANTITY - ASI.RESERVE_QUANTITY ONHAND_QTY\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "       AND AITL.BARCODE = AC.BARCODE\n" +
                "       AND AITH.FROM_OBJECT_NO = ASI.OBJECT_NO\n" +
                "       AND ASI.BARCODE = AITL.BARCODE\n" +
                "       AND AC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }



    /**
     * 功能：根据外键关联字段 transId 构造查询数据SQL。
     * 框架自动生成数据备件事务行表(AMS) AMS_ITEM_TRANS_L详细信息查询SQLModel，请根据实际需要修改。
     * @param transId String
     * @return SQLModel 返回数据详细信息查询用SQLModel
     */
    public SQLModel getDiffModel(String transId){        //盘点差异报表行明细
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT " +
                "       ASI.SPARE_ID,\n" +
                "        ASI.BARCODE,\n" +
                "       ASI.RESERVE_QUANTITY,"+
                "       ABS(ASI.QUANTITY - AITL.QUANTITY) DIFF_NUM,"+   //差异数量
                "       AITL.LINE_ID,\n" +
                "       AITL.BARCODE,\n" +
                "       AC.BARCODE,\n" +
                "       AC.ITEM_NAME,\n" +
                "       AC.ITEM_SPEC,\n" +
                "       AC.ITEM_UNIT,\n" +
                "       AC.SPARE_USAGE,\n" +
                "       AITL.REMARK,\n" +
                "       AITL.QUANTITY,\n" +         //盘点数量
                "       AITL.STORAGE_ID,\n" +
                "       AITL.BATCH_NO,\n" +
                "       AITL.BFJE,\n" +
                "       AITL.REASONS,\n" +
                "       AMS_PUB_PKG.GET_VENDOR_NAME(AC.VENDOR_ID) VENDOR_NAME,\n" +
                "       AMS_ITEM_TRANS_SX.GET_SPARE_ONHAND2(AC.BARCODE, AITH.FROM_ORGANIZATION_ID) ONHAND_QTY\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AC\n" +
                " WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "   AND AITL.BARCODE = AC.BARCODE\n" +
                "   AND AITH.FROM_OBJECT_NO = ASI.OBJECT_NO\n" +
                "   AND ASI.BARCODE = AITL.BARCODE" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
