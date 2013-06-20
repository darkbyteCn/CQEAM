package com.sino.ams.instrument.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-10-30
 * Time: 17:13:37
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentNoModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsInstrumentNoModel(SfUserDTO userAccount, AmsInstrumentHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.CHECK_USER) CHECK_NAME,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.BORROW_USER) BORROW_NAME,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.RETURN_USER) RETURN_NAME,\n" +
                "       AITH.BORROW_DATE,\n" +
                "       AITH.RETURN_DATE,\n" +
                "       AITH.CHECK_DATE,\n" +
                "       AITH.BORROW_DATE,\n" +
                "       AITH.BORROW_USER,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                "  FROM AMS_INSTRU_TRANS_H AITH\n" +
                " WHERE AITH.TRANS_ID = ?";
        sqlArgs.add(amsInstrumentHInfo.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        AmsInstrumentHDTO amsInstrumentHInfo = (AmsInstrumentHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_NO,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.BORROW_USER) BNAME,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.RETURN_USER) RNAME,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AITH.CHECK_USER) CKNAME,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AITH.CONFIRM_DATE,\n" +
                "       AITH.TRANS_ID,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       EFV.VALUE TRANS_TYPE_NAME\n" +
                "  FROM AMS_INSTRU_TRANS_H AITH,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFV.CODE = AITH.TRANS_TYPE\n" +
                "   AND EFVS.CODE = 'ORDER_TYPE_INSTRU'\n" +
                "   AND AITH.TRANS_NO LIKE dbo.NVL(?, AITH.TRANS_NO)\n" +
                "   AND AITH.TRANS_TYPE = dbo.NVL(?, AITH.TRANS_TYPE)\n" +
                "   AND AITH.TRANS_STATUS = dbo.NVL(?, AITH.TRANS_STATUS)";
        sqlArgs.add(amsInstrumentHInfo.getTransNo());
        sqlArgs.add(amsInstrumentHInfo.getTransType());
        sqlArgs.add(amsInstrumentHInfo.getTransStatus());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT ESI.ITEM_NAME,\n" +
                "       EII.BARCODE,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       SU.USERNAME CNAME,\n" +
                "       AII.INSTRU_USAGE,\n" +
                "       EMPV.VENDOR_NAME\n" +
                "  FROM AMS_INSTRU_TRANS_L  AITL,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_ITEM_INFO       EII,\n" +
                "       SF_USER             SU,\n" +
                "       AMS_INSTRUMENT_INFO AII,\n" +
                "       ETS_MIS_PO_VENDORS  EMPV\n" +
                " WHERE EII.BARCODE = AITL.BARCODE\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND SU.USER_ID = AII.CREATED_BY\n" +
                "   AND AII.BARCODE = AITL.BARCODE\n" +
                "   AND EMPV.VENDOR_ID = ESI.VENDOR_ID\n" +
                "   AND AITL.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
