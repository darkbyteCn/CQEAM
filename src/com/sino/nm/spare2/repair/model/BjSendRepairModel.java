package com.sino.nm.spare2.repair.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:26:54
 */
public class BjSendRepairModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public BjSendRepairModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
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
                "   AND ASI.ITEM_STATUS = '´ýÐÞ'\n" +
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
                "  (TRANS_ID, LINE_ID, ITEM_CODE, BARCODE, QUANTITY)\n" +
                "VALUES\n" +
                "  (?, NEWID(), ?, ?, ?)";
        sqlArgs.add(transId);
        sqlArgs.add(dto.getItemCode());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getQuantity());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertRData(String transId, String count, String itemCode) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "   INSERT INTO AMS_ITEM_RESERVED\n" +
                "      (TRANS_ID, RESERVED_DATE, ITEM_CODE, RESERVED_COUNT, ORGANIZATION_ID)\n" +
                "    VALUES\n" +
                "      (?,sysdate,?,?,?)";
        sqlArgs.add(transId);
        sqlArgs.add(itemCode);
        sqlArgs.add(count);
        sqlArgs.add(sfUser.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = null;
        String sqlStr = null;
        sqlArgs = new ArrayList();
        AmsItemTransHDTO headerDto = (AmsItemTransHDTO) dtoParameter;
        sqlStr = "INSERT INTO AMS_ITEM_TRANS_H\n" +
                "  (TRANS_ID,\n" +
                "   TRANS_NO,\n" +
                "   TRANS_TYPE,\n" +
                "   TRANS_STATUS,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY," +
                "   FROM_OBJECT_NO," +
                "   FROM_ORGANIZATION_ID," +
                "   TO_ORGANIZATION_ID," +
                "  COMPANY," +
                "  ADDRESS," +
                "  CONTACT," +
                "  TEL," +
                "  FAX," +
                "  ATTRIBUTE1," +
                " ATTRIBUTE2," +
                " ATTRIBUTE3 " +
                "  )\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, GETDATE(), ?, ?, ?, ?,?,?,?,?,?,?,?,?)";
        sqlArgs.add(headerDto.getTransId());
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransType());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(headerDto.getFromObjectNo());
        sqlArgs.add(headerDto.getFromOrganizationId());
        sqlArgs.add(headerDto.getToOrganizationId());
        sqlArgs.add(headerDto.getCompany());
        sqlArgs.add(headerDto.getAddress());
        sqlArgs.add(headerDto.getContact());
        sqlArgs.add(headerDto.getTel());
        sqlArgs.add(headerDto.getFax());
        sqlArgs.add(headerDto.getAttribute1());
        sqlArgs.add(headerDto.getAttribute2());
        sqlArgs.add(headerDto.getAttribute3());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getDeleteByTransIdModel(String transId) {
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

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO headerDto = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H SET TRANS_NO=?,FROM_OBJECT_NO=?,TRANS_STATUS=?,COMPANY=?," +
                "     CONTACT=?,ADDRESS=?,TEL=?,FAX=?,ATTRIBUTE1=?,ATTRIBUTE3=?,ATTRIBUTE2=?  WHERE TRANS_ID=?";

        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getFromObjectNo());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(headerDto.getCompany());
        sqlArgs.add(headerDto.getContact());
        sqlArgs.add(headerDto.getAddress());
        sqlArgs.add(headerDto.getTel());
        sqlArgs.add(headerDto.getFax());
        sqlArgs.add(headerDto.getAttribute1());
        sqlArgs.add(headerDto.getAttribute3());
        sqlArgs.add(headerDto.getAttribute2());
        sqlArgs.add(headerDto.getTransId());
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
        String str = "SELECT ESI.ITEM_NAME,\n" +
                "       AITL.BARCODE,\n" +
                "       AITL.QUANTITY,\n" +
                "       AITL.LINE_ID,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       dbo.AMS_INV_TRANS2_GET_DESREPAIR_QTY(AITL.BARCODE,AITH.FROM_OBJECT_NO) DISREPAIR_QUANTITY\n" +
                "  FROM AMS_ITEM_TRANS_H AITH,AMS_ITEM_TRANS_L AITL, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "   AND AITL.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT dbo.APP_GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       dbo.APP_GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.CREATED_BY,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.FROM_OBJECT_NO,\n" +
                "       AIAH.COMPANY,\n" +
                "       AIAH.CONTACT,\n" +
                "       AIAH.ADDRESS,\n" +
                "       AIAH.TEL,\n" +
                "       AIAH.FAX,\n" +
                "       AIAH.ATTRIBUTE1,\n" +
                "       AIAH.ATTRIBUTE2,\n" +
                "       AIAH.ATTRIBUTE3,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H AIAH,ETS_OBJECT EO\n" +
                " WHERE AIAH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO" +
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
                " WHERE ITEM_STATUS = '´ýÐÞ'" +
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
                " WHERE ITEM_STATUS = 'ËÍÐÞ'" +
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

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n"
                + "AND (''=? OR ? IS NULL OR TRANS_NO = ?)"
                + "AND (''=? OR ? IS NULL OR TRANS_STATUS = ?)"
                + "AND (''=? OR ? IS NULL OR TO_OBJECT_NO = ?)"
                + "AND (-1=?  OR FROM_ORGANIZATION_ID = ?)"
                + "AND (''=? OR ? IS NULL OR AITH.TRANS_TYPE = ?)"
                + "AND (''=? OR AITH.CREATION_DATE >= ?)"
                + "AND (''=? OR AITH.CREATION_DATE <= CONVERT(DATE,?))" +
                "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
//		sqlArgs.add(amsItemTransH.getTransType());
//		sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
//		sqlArgs.add(amsItemTransH.getFromUser());
//		sqlArgs.add(amsItemTransH.getFromUser());
//		sqlArgs.add(amsItemTransH.getToUser());
//		sqlArgs.add(amsItemTransH.getToUser());
//		sqlArgs.add(amsItemTransH.getFromDept());
//		sqlArgs.add(amsItemTransH.getFromDept());
//		sqlArgs.add(amsItemTransH.getToDept());
//		sqlArgs.add(amsItemTransH.getToDept());
//		sqlArgs.add(amsItemTransH.getFromObjectNo());
//		sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
//		sqlArgs.add(amsItemTransH.getFromOrganizationId());
//		sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransType());
        try {
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getToDate());
            sqlArgs.add(amsItemTransH.getToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
