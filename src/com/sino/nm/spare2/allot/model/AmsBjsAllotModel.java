package com.sino.nm.spare2.allot.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-6
 * Time: 11:31:16
 */
public class AmsBjsAllotModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjsAllotModel(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel insertLData(String barcodeNo, String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_INSTRU_TRANS_L\n" +
                "  (LINE_ID,\n" +
                "   BARCODE_NO,\n" +
                "   TRANS_ID)\n" +
                "VALUES\n" +
                "  (AMS_INSTRU_TRANS_L_S.NEXTVAL,?,?)";
        sqlArgs.add(barcodeNo);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertRData(String barcodeNo, String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_INSTRUMENT_RESERVED\n" +
                "  (BARCODE_NO, TRANS_ID, RESERVED_DATE)\n" +
                "VALUES\n" +
                "  (?, ?, SYSDATE)";
        sqlArgs.add(barcodeNo);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = null;
        String sqlStr = null;
        try {
            sqlArgs = new ArrayList();
            AmsBjsAllotHDTO amsInstrumentHInfo = (AmsBjsAllotHDTO) dtoParameter;
            sqlStr = "INSERT INTO AMS_INSTRU_TRANS_H\n" +
                    "  (TRANS_ID,\n" +
                    "   TRANS_NO,\n" +
                    "   TRANS_TYPE,\n" +
                    "   TRANS_STATUS,\n" +
                    "   BORROW_USER,\n" +
                    "   BORROW_DATE,\n" +
                    "   CONFIRM_USER,\n" +
                    "   CONFIRM_DATE,\n" +
                    "   CANCEL_DATE)\n" +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            sqlArgs.add(amsInstrumentHInfo.getTransId());
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getTransType());
            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
            sqlArgs.add(amsInstrumentHInfo.getCreationDate());
            sqlArgs.add(amsInstrumentHInfo.getTransDate());
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsInstrumentHInfo = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_INSTRU_TRANS_H\n" +
                "   SET TRANS_ID      = ?,\n" +
                "       TRANS_NO      = ?,\n" +
                "       TRANS_TYPE    = ?,\n" +
                "       TRANS_STATUS  = ?,\n" +
                "       BORROW_USER   = ?,\n" +
                "       BORROW_DATE   = ?,\n" +
                "       CONFIRM_USER  = ?,\n" +
                "       CONFIRM_DATE  = ?,\n" +
                "       CANCEL_DATE   = ?,\n" +
                "       CANCEL_REASON = ?\n" +
                " WHERE TRANS_ID = ?";
        sqlArgs.add(amsInstrumentHInfo.getTransId());
        sqlArgs.add(amsInstrumentHInfo.getTransNo());
        sqlArgs.add(amsInstrumentHInfo.getTransType());
        sqlArgs.add(amsInstrumentHInfo.getTransStatus());

        sqlArgs.add(amsInstrumentHInfo.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getDeleteByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_INSTRU_TRANS_L"
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
                + " BARCODE_NO = ?";
        sqlArgs.add(barcodeNo);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel updateInfoModel(String bUser, String barcodeNo) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

        String sqlStr = "UPDATE AMS_INSTRUMENT_INFO SET CURR_KEEP_USER = ? WHERE BARCODE_NO = ?";

        sqlArgs.add(bUser);
        sqlArgs.add(barcodeNo);
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

    /**
     * 分配单查询详细信息
     * @param transId 头ID
     * @return SQLModel
     */
    public SQLModel getDataByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AIAD.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ASI.QUANTITY ITEM_AMOUNT,\n" +
                "       AIAD.QUANTITY,\n" +
                "       AIAD.DETAIL_ID\n" +
                "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
                "       AMS_ITEM_ALLOCATE_D AIAD,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       AMS_SPARE_INFO      ASI\n" +
                " WHERE ESI.ITEM_CODE = AIAD.ITEM_CODE\n" +
                "   AND AIAD.TRANS_ID = AIAH.TRANS_ID\n" +
                "   AND AIAH.FROM_OBJECT_NO = ASI.OBJECT_NO\n" +
                "   AND AIAD.BARCODE = ASI.BARCODE\n" +
                "   AND AIAD.TRANS_ID = ?";
//        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "SELECT AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.TRANS_TYPE,\n" +
                "       AIAH.TO_ORGANIZATION_ID,\n" +
                "       AIAH.FROM_ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.REMARK,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       ETS_FLEX_VALUES     EFV,\n" +
                "       ETS_FLEX_VALUE_SET  EFVS,\n" +
                "       ETS_OU_CITY_MAP     EOCM,\n" +
                "       ETS_OBJECT          EO\n" +
                " WHERE AIAH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
                "   AND AIAH.TO_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND AIAH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND AIAH.TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
