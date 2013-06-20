package com.sino.ams.instrument.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-10-28
 * Time: 23:26:06
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentCheckModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsInstrumentCheckModel(SfUserDTO userAccount, AmsInstrumentHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
    public SQLModel insertLData(String barcodeNo, String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_INSTRU_TRANS_L\n" +
                "  (LINE_ID,\n" +
                "   BARCODE,\n" +
                "   TRANS_ID)\n" +
                "VALUES\n" +
                "  ( NEWID() ,?,?)";
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
                "  (BARCODE, TRANS_ID, RESERVED_DATE)\n" +
                "VALUES\n" +
                "  (?, ?, GETDATE())";
        sqlArgs.add(barcodeNo);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
            String sqlStr = "INSERT INTO AMS_INSTRU_TRANS_H\n" +
                    "  (TRANS_ID,\n" +
                    "   TRANS_NO,\n" +
                    "   TRANS_TYPE,\n" +
                    "   TRANS_STATUS,\n" +
                    "   CHECK_USER," +
                    "   CHECK_DATE)\n" +
                    "VALUES(?,?,?,?,?,?)";
            sqlArgs.add(amsInstrumentHInfo.getTransId());
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getTransType());
            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
            sqlArgs.add(amsInstrumentHInfo.getCheckUser());
            sqlArgs.add(amsInstrumentHInfo.getCheckDate());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
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
            sqlArgs.add(amsInstrumentHInfo.getBorrowUser());
            sqlArgs.add(amsInstrumentHInfo.getBorrowDate());
            sqlArgs.add(amsInstrumentHInfo.getConfirmUser());
            sqlArgs.add(amsInstrumentHInfo.getConfirmDate());
            sqlArgs.add(amsInstrumentHInfo.getCancelDate());
            sqlArgs.add(amsInstrumentHInfo.getCalPattern());
            sqlArgs.add(amsInstrumentHInfo.getTransId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }
    public SQLModel getPrimaryKeyDataModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
	AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
		String sqlStr ="SELECT AITH.TRANS_NO,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.CHECK_USER) CHECK_NAME,\n" +
                "       AITH.CHECK_DATE,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.CHECK_USER,\n" +
                "       AITH.TRANS_ID,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME\n" +
                "  FROM AMS_INSTRU_TRANS_H AITH,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
                "   AND AITH.TRANS_ID = ?";
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

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
            String sqlStr = "SELECT AITH.TRANS_NO,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AITH.CHECK_USER) CNAME,\n" +
                    "       AITH.CHECK_DATE,\n" +
                    "       AITH.TRANS_STATUS,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AITH.CONFIRM_USER) QNAME,\n" +
                    "       AITH.TRANS_ID,\n" +
                    "       AITH.CHECK_USER,\n" +
                    "       EFV.VALUE TRANS_STATUS_NAME\n" +
                    "  FROM AMS_INSTRU_TRANS_H AITH,\n" +
                    "       ETS_FLEX_VALUES    EFV,\n" +
                    "       ETS_FLEX_VALUE_SET EFVS\n" +
                    " WHERE AITH.TRANS_TYPE = 'INS-CHK'\n" +
                    "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                    "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                    "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
                    "   AND AITH.TRANS_NO LIKE dbo.NVL(?, AITH.TRANS_NO)\n" +
                    "   AND AITH.CHECK_DATE >= ISNULL(?, AITH.CHECK_DATE)\n" +
                    "   AND AITH.CHECK_DATE <= ISNULL(?, AITH.CHECK_DATE)\n" +
                    "   AND AITH.TRANS_STATUS = dbo.NVL(?, AITH.TRANS_STATUS)";
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getFromDate());
            sqlArgs.add(amsInstrumentHInfo.getToDate());
            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }
    public SQLModel getByTransIdModel(String transId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
        String str="SELECT ESI.ITEM_NAME,\n" +
                "       EII.BARCODE,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AII.CREATED_BY) CNAME,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AII.CURR_KEEP_USER) DNAME,\n" +
                "       AII.INSTRU_USAGE,\n" +
                "       EMPV.VENDOR_NAME," +
                "       AII.CURR_KEEP_USER \n" +
                "  FROM AMS_INSTRU_TRANS_L  AITL,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_ITEM_INFO       EII,\n" +
                "       AMS_INSTRUMENT_INFO AII,\n" +
                "       ETS_MIS_PO_VENDORS  EMPV\n" +
                " WHERE EII.BARCODE = AITL.BARCODE\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AII.BARCODE = AITL.BARCODE\n" +
                "   AND EMPV.VENDOR_ID = ESI.VENDOR_ID\n" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

		sqlModel.setSqlStr(str);
		sqlModel.setArgs(sqlArgs);
    return sqlModel;
    }
    public SQLModel getDataCheckModel(String check,String currKeepUser){
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr="UPDATE AMS_INSTRUMENT_INFO   SET CURR_KEEP_USER= ? WHERE   BARCODE=?";
        sqlArgs.add(currKeepUser);
        sqlArgs.add(check);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel updateType(String tranId){
      SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr="UPDATE AMS_INSTRU_TRANS_H SET TRANS_STATUS='COMPLETED'  WHERE TRANS_ID=?";
        sqlArgs.add(tranId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
