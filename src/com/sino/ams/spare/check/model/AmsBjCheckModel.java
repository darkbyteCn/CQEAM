package com.sino.ams.spare.check.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.check.dto.AmsItemCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author TOTTI
 *         Date: 2007-11-27
 */
public class AmsBjCheckModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjCheckModel(SfUserDTO userAccount, AmsItemCheckHeaderDTO dtoParameter) {
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
                "  (AMS_INSTRU_TRANS_L_S.NEXTVAL,?,?)";
        sqlArgs.add(barcodeNo);
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        AmsItemCheckHeaderDTO amsInstrumentHInfo = (AmsItemCheckHeaderDTO) dtoParameter;
        String sqlStr = "  INSERT INTO AMS_ITEM_CHECK_HEADER\n" +
                "    (HEADER_ID,\n" +
                "     CHECK_LOCATION,\n" +
                "     TRANS_NO,\n" +
                "     START_DATE,\n" +
                "     IMPLEMENT_DAYS,\n" +
                "     IMPLEMENT_BY,\n" +
                "     CREATION_DATE,\n" +
                "     CREATED_BY,\n" +
                "     ORDER_STATUS,\n" +
                "     CHECK_TYPE)\n" +
                "  VALUES\n" +
                "    (AMS_ITEM_CHECK_HEADER_S.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(amsInstrumentHInfo.getCheckLocation());
        sqlArgs.add(amsInstrumentHInfo.getTransNo());
        sqlArgs.add(amsInstrumentHInfo.getStartDate());
        sqlArgs.add(amsInstrumentHInfo.getImplementDays());
        sqlArgs.add(amsInstrumentHInfo.getImplementBy());
        sqlArgs.add(amsInstrumentHInfo.getCreationDate());
        sqlArgs.add(amsInstrumentHInfo.getCreatedBy());
        sqlArgs.add(amsInstrumentHInfo.getOrderStatus());
        sqlArgs.add(amsInstrumentHInfo.getCheckType());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getByTransIdModel(String headerId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT AICL.BARCODE, ESI.ITEM_NAME, ESI.ITEM_SPEC, EII.ITEM_QTY\n" +
                "  FROM AMS_ITEM_CHECK_LINE AICL, ETS_SYSTEM_ITEM ESI, ETS_ITEM_INFO EII\n" +
                " WHERE AICL.BARCODE = EII.BARCODE\n" +
                "   AND EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AICL.HEADER_ID = ?";
        sqlArgs.add(headerId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
  public SQLModel getByAddressId(String checkLocation,String headerId){
         SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "\n" +
                "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       '有' SYS_STATUS,\n" +
                "       DECODE(AICL.BARCODE, NULL, '没有', '有') CHECK_STATUS\n" +
                "  FROM ETS_ITEM_INFO EII, AMS_ITEM_CHECK_LINE AICL, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EII.BARCODE = AICL.BARCODE(+)\n" +
                "   AND ESI.ITEM_CODE = EII.ITEM_CODE\n" +
                "   AND EII.ADDRESS_ID = ?\n" +
                "   AND AICL.HEADER_ID = ?\n" +
                "UNION ALL\n" +
                "SELECT AICL.BARCODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       '没有' SYS_STATUS,\n" +
                "       '有' CHECK_STATUS\n" +
                "  FROM AMS_ITEM_CHECK_LINE AICL, ETS_SYSTEM_ITEM ESI, ETS_ITEM_INFO EII1\n" +
                " WHERE EII1.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AICL.BARCODE = EII1.BARCODE\n" +
                "   AND AICL.HEADER_ID = ?\n" +
                "   AND NOT EXISTS\n" +
                " (SELECT 1 FROM ETS_ITEM_INFO EII WHERE EII.BARCODE = AICL.BARCODE)";
        sqlArgs.add(checkLocation);
        sqlArgs.add(headerId);
        sqlArgs.add(headerId);

        sqlModel.setSqlStr(str);
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

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemCheckHeaderDTO amsInstrumentHInfo = (AmsItemCheckHeaderDTO) dtoParameter;
        String sqlStr = "SELECT AICH.TRANS_NO,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AICH.CREATED_BY) CREATED_USER,\n" +
                "       AICH.CREATION_DATE,\n" +
                "       AICH.HEADER_ID,\n" +
                "       AICH.CHECK_LOCATION,\n" +
                "       AICH.ORDER_STATUS,\n" +
                "       EO.WORKORDER_OBJECT_NAME CHECK_LOCATION_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME,\n" +
                "       AICH.IMPLEMENT_BY,\n" +
                "       AMS_PUB_PKG.GET_USER_NAME(AICH.IMPLEMENT_BY) IMPLEMENT_BY_NAME,\n" +
                "       AICH.CHECK_LOCATION,\n" +
                "       AICH.START_DATE,\n" +
                "       AICH.CHECK_TYPE,\n" +
                "       AICH.IMPLEMENT_DAYS\n" +
                "  FROM AMS_ITEM_CHECK_HEADER AICH,\n" +
                "       ETS_OBJECT            EO,\n" +
                "       AMS_OBJECT_ADDRESS    AOA,\n" +
                "       ETS_FLEX_VALUES       EFV,\n" +
                "       ETS_FLEX_VALUE_SET    EFVS\n" +
                " WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                "   AND AOA.ADDRESS_ID = AICH.CHECK_LOCATION\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'WORKORDER_STATUS'\n" +
                "   AND EFV.CODE = AICH.ORDER_STATUS\n" +
                "   AND AICH.HEADER_ID = ?";
        sqlArgs.add(amsInstrumentHInfo.getHeaderId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = null;

            AmsItemCheckHeaderDTO amsInstrumentHInfo = (AmsItemCheckHeaderDTO) dtoParameter;
            sqlStr = "SELECT AICH.TRANS_NO,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AICH.CREATED_BY) CREATED_USER,\n" +
                    "       AICH.CREATION_DATE,\n" +
                    "       AICH.HEADER_ID,\n" +
                    "       AICH.CHECK_LOCATION,\n" +
                    "       AICH.ORDER_STATUS,\n" +
                    "       EO.WORKORDER_OBJECT_NAME,\n" +
                    "       EFV.VALUE ORDER_STATUS_NAME,\n" +
                    "       DECODE(AICH.CHECK_TYPE,1,'盲盘','非盲盘')IS_CHECK\n" +
                    "  FROM AMS_ITEM_CHECK_HEADER AICH,\n" +
                    "       ETS_OBJECT            EO,\n" +
                    "       AMS_OBJECT_ADDRESS    AOA,\n" +
                    "       ETS_FLEX_VALUES       EFV,\n" +
                    "       ETS_FLEX_VALUE_SET    EFVS\n" +
                    " WHERE EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO\n" +
                    "   AND AOA.ADDRESS_ID = AICH.CHECK_LOCATION\n" +
                    "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                    "   AND EFVS.CODE = 'WORKORDER_STATUS'\n" +
                    "   AND EFV.CODE = AICH.ORDER_STATUS\n" +
                    "   AND AICH.TRANS_NO LIKE NVL(?, AICH.TRANS_NO)\n" +
                    "   AND AICH.ORDER_STATUS = NVL(?, AICH.ORDER_STATUS)\n" +
                    "   AND AICH.CREATION_DATE <= NVL(?, AICH.CREATION_DATE)\n" +
                    "   AND AICH.CREATION_DATE >= NVL(?, AICH.CREATION_DATE)";
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getOrderStatus());
            sqlArgs.add(amsInstrumentHInfo.getToDate());

            sqlArgs.add(amsInstrumentHInfo.getFromDate());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }
}
