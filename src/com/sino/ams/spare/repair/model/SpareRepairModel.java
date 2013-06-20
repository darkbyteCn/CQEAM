package com.sino.ams.spare.repair.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:26:54
 * Function:
 */
public class SpareRepairModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public SpareRepairModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getSQLModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT ASI.QUANTITY ITEM_AMOUNT,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ASI.BARCODE\n" +
                "    FROM AMS_SPARE_INFO   ASI,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       ETS_SYSTEM_ITEM    ESI\n" +
                " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND ASI.ITEM_STATUS = '待修'\n" +
                "   AND ASI.ORGANIZATION_ID=?";
        List list = new ArrayList();
        list.add(sfUser.getOrganizationId());
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel insertLData(String transId, AmsItemTransLDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_TRANS_L\n" +
                "  (TRANS_ID, LINE_ID, BARCODE, QUANTITY)\n" +
                "VALUES\n" +
                "  (?, AMS_ITEM_TRANS_L_S.NEXTVAL, ?, ?)";
        sqlArgs.add(transId);
//        sqlArgs.add(dto.getItemCode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getQuantity());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertDData(String transId, AmsItemTransDDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO AMS_ITEM_TRANS_D\n" +
                        "  (TRANS_ID, DETAIL_ID, QUANTITY, SERIAL_NO, BARCODE)\n" +
                        "VALUES\n" +
                        "  (?, AMS_ITEM_TRANS_D_S.NEXTVAL, ?, ?, ?)";

        sqlArgs.add(transId);
        sqlArgs.add(dto.getQuantity());
        sqlArgs.add(dto.getSerialNo());
        sqlArgs.add(dto.getBarcode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 根据Detail表中数据自动插入行表
     * @param transId
     * @return
     */
    public SQLModel insertLineModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO AMS_ITEM_TRANS_L\n" +
                        "  (LINE_ID, TRANS_ID, BARCODE, QUANTITY)\n" +
                        "  SELECT AMS_ITEM_TRANS_L_S.NEXTVAL, T.*\n" +
                        "    FROM (SELECT AITD.TRANS_ID, AITD.BARCODE, SUM(AITD.QUANTITY)\n" +
                        "            FROM AMS_ITEM_TRANS_D AITD\n" +
                        "           WHERE AITD.TRANS_ID = ?\n" +
                        "           GROUP BY AITD.TRANS_ID, AITD.BARCODE) T";

        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 根据行表数据更新Detail表的行ID
     * @param transId
     * @return
     */
    public SQLModel updateDetailModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "UPDATE AMS_ITEM_TRANS_D AITD\n" +
                        "   SET AITD.LINE_ID = (SELECT AITL.LINE_ID\n" +
                        "                         FROM AMS_ITEM_TRANS_L AITL\n" +
                        "                        WHERE AITL.TRANS_ID = AITD.TRANS_ID\n" +
                        "                          AND AITL.BARCODE = AITD.BARCODE)\n" +
                        " WHERE AITD.TRANS_ID = ?";

        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

//    public SQLModel insertRData(String transId, String count, String itemCode) {
//        SQLModel sqlModel = new SQLModel();
//        List sqlArgs = new ArrayList();
//        String sqlStr = "   INSERT INTO AMS_ITEM_RESERVED\n" +
//                "      (TRANS_ID, RESERVED_DATE, ITEM_CODE, RESERVED_COUNT, ORGANIZATION_ID)\n" +
//                "    VALUES\n" +
//                "      (?,sysdate,?,?,?)";
//        sqlArgs.add(transId);
//        sqlArgs.add(itemCode);
//        sqlArgs.add(count);
//        sqlArgs.add(sfUser.getOrganizationId());
//        sqlModel.setSqlStr(sqlStr);
//        sqlModel.setArgs(sqlArgs);
//        return sqlModel;
//    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = null;
        String sqlStr = null;
        sqlArgs = new ArrayList();
        AmsItemTransHDTO amsInstrumentHInfo = (AmsItemTransHDTO) dtoParameter;
        sqlStr = "INSERT INTO AMS_ITEM_TRANS_H\n" +
                "  (TRANS_ID,\n" +
                "   TRANS_NO,\n" +
                "   TRANS_TYPE,\n" +
                "   TRANS_STATUS,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY," +
                "   FROM_OBJECT_NO," +
                "   FROM_DEPT," +
                "   FROM_ORGANIZATION_ID," +
//                "   TO_ORGANIZATION_ID," +
                "  COMPANY," +    //服务方公司代码
//                "  ADDRESS," +    //用户方地址
//                "  CONTACT," +   //联系人
//                "  TEL," +       //电话
//                "  FAX," +        //传真
                "  ATTRIBUTE1," +  // 送修单:委托书编号
                " ATTRIBUTE2," +   // 送修单:承运人
                " ATTRIBUTE3 " +   // 送修单:保值金额
                "  )\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, SYSDATE, ?, ?, ?, ?,?,?,?,?)";
        sqlArgs.add(amsInstrumentHInfo.getTransId());
        sqlArgs.add(amsInstrumentHInfo.getTransNo());
        sqlArgs.add(amsInstrumentHInfo.getTransType());
        sqlArgs.add(amsInstrumentHInfo.getTransStatus());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(amsInstrumentHInfo.getToObjectNo());
        sqlArgs.add(amsInstrumentHInfo.getFromDept());
        sqlArgs.add(sfUser.getOrganizationId());
//        sqlArgs.add(sfUser.getOrganizationId());
//        sqlArgs.add(amsInstrumentHInfo.getCompany());
        sqlArgs.add(amsInstrumentHInfo.getVendorCode());
//        sqlArgs.add(amsInstrumentHInfo.getAddress());
//        sqlArgs.add(amsInstrumentHInfo.getContact());
//        sqlArgs.add(amsInstrumentHInfo.getTel());
//        sqlArgs.add(amsInstrumentHInfo.getFax());
//        sqlArgs.add(amsInstrumentHInfo.getAttribute1());
        sqlArgs.add(amsInstrumentHInfo.getAtt1());
//        sqlArgs.add(amsInstrumentHInfo.getAttribute2());
        sqlArgs.add(amsInstrumentHInfo.getAtt2());
//        sqlArgs.add(amsInstrumentHInfo.getAttribute3());
        sqlArgs.add(amsInstrumentHInfo.getAtt3());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel deleteLinesModel(String transId) {
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

    public SQLModel deleteByBarcodeNoModel(String barcodeNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_INSTRUMENT_RESERVED"
                + " WHERE"
                + " BARCODE = ?";
        sqlArgs.add(barcodeNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel deleteDetailsModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_ITEM_TRANS_D"
                + " WHERE"
                + " TRANS_ID = ?";

        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsInstrumentHInfo = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H SET TRANS_NO=?,TRANS_STATUS=?,COMPANY=?," +
                "     CONTACT=?,ADDRESS=?,TEL=?,FAX=?,ATTRIBUTE1=?,ATTRIBUTE3=?,ATTRIBUTE2=?  WHERE TRANS_ID=?";

        sqlArgs.add(amsInstrumentHInfo.getTransNo());
        sqlArgs.add(amsInstrumentHInfo.getTransStatus());
        sqlArgs.add(amsInstrumentHInfo.getVendorCode());
        sqlArgs.add(amsInstrumentHInfo.getContact());
        sqlArgs.add(amsInstrumentHInfo.getAddress());
        sqlArgs.add(amsInstrumentHInfo.getTel());
        sqlArgs.add(amsInstrumentHInfo.getFax());
        sqlArgs.add(amsInstrumentHInfo.getAtt1());
        sqlArgs.add(amsInstrumentHInfo.getAtt3());
        sqlArgs.add(amsInstrumentHInfo.getAtt2());
        sqlArgs.add(amsInstrumentHInfo.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateInfoModel(String addresId, String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE ETS_ITEM_INFO SET ADDRESS_ID=? where BARCODE=?";

        sqlArgs.add(addresId);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateHRepalModel(String transId, String transStatus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE AMS_INSTRU_TRANS_H SET CANCEL_DATE=SYSDATE ,CANCEL_REASON=?,TRANS_STATUS=? WHERE TRANS_ID=?";

        sqlArgs.add(transStatus);
        sqlArgs.add(transStatus);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateHModel(String transId, String transStatus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE AMS_INSTRU_TRANS_H SET CONFIRM_USER =?   ,CONFIRM_DATE= SYSDATE ," +
                " TRANS_STATUS=?  WHERE TRANS_ID=?";

        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(transStatus);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT " +
                "      AITL.LINE_ID,\n" +
                "      AITL.BARCODE,\n" +
                "      AC.BARCODE,\n" +
                "      AC.ITEM_NAME,\n" +
                "      AC.ITEM_SPEC,\n" +
                "      AC.ITEM_UNIT,\n" +
                "      AC.SPARE_USAGE,\n" +
                "      AITL.REMARK,\n" +
                "      AITL.REMARK REMARKl,\n" +
                "      AITL.QUANTITY,\n" +
                "      AITL.STORAGE_ID,\n" +
                "      AITL.BATCH_NO,\n" +
                "      AITL.BFJE,\n" +
                "      AITL.REASONS,\n" +
                "      AMS_PUB_PKG.GET_VENDOR_NAME(AC.VENDOR_ID) VENDOR_NAME,\n" +
//                "      AMS_ITEM_TRANS_SX.GET_SPARE_ONHAND2(AC.BARCODE, AITH.FROM_ORGANIZATION_ID) ONHAND_QTY,\n" +   //现有量不同于可用量
                "      AMS_ITEM_TRANS_SX.GET_SPARE_USERNUM(AC.BARCODE,\n" +
                "                                              72,\n" +
                "                                              AITH.FROM_ORGANIZATION_ID,AITH.FROM_OBJECT_NO) ONHAND_QTY" +  //待修库数量
                "  FROM AMS_ITEM_TRANS_L AITL,\n" +
                "       AMS_ITEM_TRANS_H AITH," +
                "      AMS_SPARE_CATEGORY AC\n" +
                " WHERE AITL.BARCODE = AC.BARCODE\n" +
                "   AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getPrintHModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " SELECT " +
                "       AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.CREATED_BY,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.FROM_DEPT,\n" +
                "       AIAH.FROM_ORGANIZATION_ID,\n" +
                "       AIAH.TO_OBJECT_NO,\n" +
                "       AIAH.COMPANY,\n" +
                "       AIAH.CONTACT,\n" +
                "       AIAH.ADDRESS,\n" +
                "       AIAH.TEL,\n" +
                "       AIAH.FAX,\n" +
                "       AIAH.ATTRIBUTE1,\n" +
                "       AIAH.ATTRIBUTE2,\n" +
                "       AIAH.ATTRIBUTE3,\n" +
                "       AVI.VENDOR_NAME,\n" +
                "       AVI.ADDRESS,\n" +
                "       AVI.CONTACT,\n" +
                "       AVI.PHONE,\n" +
                "       AVI.FAX\n" +
                "  FROM AMS_ITEM_TRANS_H AIAH, AMS_VENDOR_INFO AVI\n" +
                "  WHERE AVI.VENDOR_CODE = AIAH.COMPANY\n" +
                "   AND AIAH.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getVendorInfo(String Company) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "   SELECT " +
                "          AVI.VENDOR_CODE,\n" +
                "          AVI.VENDOR_NAME,\n" +
                "          AVI.ADDRESS,\n" +
                "          AVI.CONTACT,\n" +
                "          AVI.PHONE,\n" +
                "          AVI.FAX\n" +
                "     FROM AMS_VENDOR_INFO AVI " +
                "     where  AVI.VENDOR_CODE = ?";
        sqlArgs.add(Company);
        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDtlByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT AMSC.BARCODE,\n" +
                "       AMSC.ITEM_NAME,\n" +
                "       AMSC.ITEM_SPEC,\n" +
                "       AMSC.ITEM_CATEGORY,\n" +
                "       AMSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AITD.SERIAL_NO,\n" +
                "       AITD.QUANTITY,\n" +
                "       AITD.LINE_ID,\n" +
                "       AITD.TROUBLE_REASON,\n" +
                "       AITD.TROUBLE_LOC\n" +
                "  FROM AMS_ITEM_TRANS_D   AITD,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITD.BARCODE = AMSC.BARCODE\n" +
                "       AND AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND AITD.IS_ALLOT = 0\n" +
                "       AND AITD.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
//        String sqlStr = "SELECT AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
//                "       AIAH.TRANS_ID,\n" +
//                "       AIAH.TRANS_NO,\n" +
//                "       AMS_PUB_PKG.GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
//                "       AIAH.CREATION_DATE,\n" +
//                "       AIAH.CREATED_BY,\n" +
//                "       AIAH.TRANS_STATUS,\n" +
//                "       AIAH.FROM_DEPT,\n" +
//                "       AIAH.FROM_ORGANIZATION_ID,\n" +
//                "       AIAH.TO_OBJECT_NO,\n" +
//                "       AIAH.COMPANY,\n" +
//                "       AIAH.CONTACT,\n" +
//                "       AIAH.ADDRESS,\n" +
//                "       AIAH.TEL,\n" +
//                "       AIAH.FAX,\n" +
//                "       AIAH.ATTRIBUTE1,\n" +
//                "       AIAH.ATTRIBUTE2,\n" +
//                "       AIAH.ATTRIBUTE3\n" +
//                "  FROM AMS_ITEM_TRANS_H AIAH\n" +
//                " WHERE  AIAH.TRANS_ID = ?";
//        sqlArgs.add(amsItemTransH.getTransId());

        String sqlStr = " SELECT " +
                "       AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.CREATED_BY,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.FROM_DEPT,\n" +
                "       AIAH.FROM_ORGANIZATION_ID,\n" +
                "       AIAH.TO_OBJECT_NO,\n" +
                "       AIAH.COMPANY,\n" +
                "       AIAH.CONTACT,\n" +
                "       AIAH.ADDRESS,\n" +
                "       AIAH.TEL,\n" +
                "       AIAH.FAX,\n" +
                "       AIAH.ATTRIBUTE1,\n" +
                "       AIAH.ATTRIBUTE2,\n" +
                "       AIAH.ATTRIBUTE3,\n" +
                "       AVI.VENDOR_CODE,\n" +
                "       AVI.VENDOR_NAME,\n" +
                "       AVI.ADDRESS,\n" +
                "       AVI.CONTACT,\n" +
                "       AVI.PHONE,\n" +
                "       AVI.FAX\n" +
                "  FROM AMS_ITEM_TRANS_H AIAH, AMS_VENDOR_INFO AVI\n" +
                "  WHERE AVI.VENDOR_CODE = AIAH.COMPANY\n" +
                "   AND AIAH.TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getToObjectNoModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NO\n" +
                "  FROM ETS_OBJECT EO\n" +
                " WHERE EO.OBJECT_CATEGORY = 73\n" +
                "   AND EO.ORGANIZATION_ID =  ?";
        sqlArgs.add(sfUser.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getReduceModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_SPARE_INFO" +
                "   SET QUANTITY = QUANTITY - 1," +
                "       LAST_UPDATE_DATE = SYSDATE," +
                "       LAST_UPDATE_BY = ?" +
                " WHERE ITEM_STATUS = '待修'" +
                "   AND BARCODE = ?" +
                "   AND ORGANIZATION_ID = ? ";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(barcode);
        sqlArgs.add(sfUser.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPlusModel(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_SPARE_INFO" +
                "   SET QUANTITY = QUANTITY + 1," +
                "       LAST_UPDATE_DATE = SYSDATE," +
                "       LAST_UPDATE_BY = ?" +
                " WHERE ITEM_STATUS = '送修'" +
                "   AND BARCODE = ?" +
                "   AND ORGANIZATION_ID = ? ";
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(barcode);
        sqlArgs.add(sfUser.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getByOu() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT ACI.CUSTOMER, ACI.ADDRESS, ACI.CONTACT, ACI.TEL, ACI.FAX\n" +
                "  FROM AMS_CUSTOMER_INFO ACI\n" +
                " WHERE ACI.ORGANIZATION_ID = ?";
        sqlArgs.add(sfUser.getOrganizationId());
//        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel updateStatusModels(String transId, String transStatus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H AITH\n" +
                "   SET AITH.TRANS_STATUS = ?\n" +
                " WHERE AITH.TRANS_ID = ?";
        sqlArgs.add(transStatus);
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel verifyBarcode(String barcode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM AMS_SPARE_CATEGORY WHERE BARCODE = ?";

        sqlArgs.add(barcode);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

}