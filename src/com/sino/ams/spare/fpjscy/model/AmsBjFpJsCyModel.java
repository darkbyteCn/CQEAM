package com.sino.ams.spare.fpjscy.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author TOTTI
 *         Date: 2007-11-29
 */
public class AmsBjFpJsCyModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjFpJsCyModel(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
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
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.TO_ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       ETS_FLEX_VALUES     EFV,\n" +
                "       ETS_FLEX_VALUE_SET  EFVS,\n" +
                "       ETS_OU_CITY_MAP     EOCM\n" +
                " WHERE AIAH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
                "   AND EOCM.ORGANIZATION_ID = AIAH.TO_ORGANIZATION_ID\n" +
                "   AND AIAH.TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsBjsAllotHDTO amsInstrumentHInfo = (AmsBjsAllotHDTO) dtoParameter;
            String sqlStr = "SELECT AIAH.TRANS_NO,\n" +
                    "       AIAH.TRANS_ID,\n" +
                    "       AMS_PUB_PKG.GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                    "       AIAH.CREATION_DATE\n" +
                    "  FROM AMS_ITEM_ALLOCATE_H AIAH\n" +
                    " WHERE AIAH.TRANS_TYPE = 'BJFP'\n" +
                    "   AND AIAH.TRANS_NO LIKE NVL(?, AIAH.TRANS_NO)\n" +
                    "   AND AIAH.TRANS_STATUS = NVL(?, AIAH.TRANS_STATUS)\n" +
                    "   AND AIAH.CREATION_DATE <= NVL(?, AIAH.CREATION_DATE)\n" +
                    "   AND AIAH.CREATION_DATE >= NVL(?, AIAH.CREATION_DATE)";
            sqlArgs.add(amsInstrumentHInfo.getTransNo());
            sqlArgs.add(amsInstrumentHInfo.getTransStatus());
            sqlArgs.add(amsInstrumentHInfo.getToDate());
            sqlArgs.add(amsInstrumentHInfo.getFromDate());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        }
        catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;
    }

    public SQLModel getDataByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="SELECT ESI. ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       AIAD.BARCODE,\n" +
                "       AIAD.QUANTITY,\n" +
                "       AIAD.ACCEPT_QTY\n" +
                "  FROM AMS_ITEM_ALLOCATE_D AIAD, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE AIAD.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AIAD.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel getDataDiffent(String lineId,String itemCode){
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
        String sqlStr="SELECT EOCM.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       AITD.QUANTITY,\n" +
                "       AMS_ITEM_TRANS.GET_ACCEPT_QTY(AITD.ITEM_CODE, AITD.TRANS_ID) ACCEPT_QTY\n" +
                "  FROM AMS_ITEM_TRANS_D AITD, ETS_OU_CITY_MAP EOCM, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EOCM.ORGANIZATION_ID = AITD.ORGANIZATION_ID\n" +
                "   AND ESI.ITEM_CODE = AITD.ITEM_CODE\n" +
                "   AND AITD.LINE_ID = ?\n" +
                "   AND AITD.ITEM_CODE = ?";
        sqlArgs.add(lineId);
        sqlArgs.add(itemCode);

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}
