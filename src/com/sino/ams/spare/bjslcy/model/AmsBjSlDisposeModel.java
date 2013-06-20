package com.sino.ams.spare.bjslcy.model;

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
 *         Date: 2007-11-28
 */
public class AmsBjSlDisposeModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjSlDisposeModel(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsBjsAllotHDTO amsItemTransH = (AmsBjsAllotHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       AITH.FROM_OBJECT_NO,\n" +
                "       AITH.TO_OBJECT_NO,\n" +
                "       AITH.FROM_ORGANIZATION_ID,\n" +
                "       AITH.TO_ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                "       EOCM2.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION FROM_OBJECT_LOCATION,\n" +
                "       EO2.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                "       EO2.WORKORDER_OBJECT_LOCATION TO_OBJECT_LOCATION,\n" +
                "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H AITH,\n" +
                "       ETS_OBJECT       EO,\n" +
                "       ETS_OBJECT       EO2,\n" +
                "       SF_USER_V        SUV,\n" +
                "       ETS_OU_CITY_MAP  EOCM,\n" +
                "       ETS_OU_CITY_MAP  EOCM2\n" +
                " WHERE AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.TO_OBJECT_NO = EO2.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
                "   AND AITH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID(+)\n" +
                "   AND TRANS_ID = ?";
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
            String sqlStr = "SELECT AITH.TRANS_NO,\n" +
                    "       AMS_PUB_PKG.GET_USER_NAME(AITH.CREATED_BY) CREATED_USER,\n" +
                    "       AMS_PUB_PKG.GET_STATUS_NAME(AITH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                    "       AITH.CREATION_DATE,\n" +
                    "       AITH.TRANS_ID\n" +
                    "  FROM AMS_ITEM_TRANS_H AITH\n" +
                    " WHERE AITH.TRANS_TYPE = 'BJSL'\n" +
                    "   AND AITH.TRANS_NO LIKE NVL(?, AITH.TRANS_NO)\n" +
                    "   AND AITH.TRANS_STATUS = NVL(?, AITH.TRANS_STATUS)\n" +
                    "   AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)\n" +
                    "   AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)";
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

   public SQLModel getDataByTransIdModel(String transId){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT AITL.LINE_ID,\n" +
                "       AITL.TRANS_ID,\n" +
                "       AITL.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_UNIT,\n" +
                "       AITL.QUANTITY,\n" +
                "       AITL.STORAGE_ID,\n" +
                "       AITL.BATCH_NO\n" +
                "  FROM AMS_ITEM_TRANS_L AITL, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE AITL.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND TRANS_ID = ?";
		sqlArgs.add(transId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
    public SQLModel getDataDiffent(String transId,String itemCode){
        SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
        String sqlStr="SELECT EOCM.COMPANY,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       AIAD.ACCEPT_QTY,\n" +
                "       AIAD.QUANTITY,\n" +
                "       AIAD.BARCODE\n" +
                "  FROM ETS_OU_CITY_MAP     EOCM,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       AMS_ITEM_ALLOCATE_D AIAD,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH\n" +
                " WHERE EOCM.ORGANIZATION_ID = AIAH.FROM_ORGANIZATION_ID\n" +
                "   AND AIAD.TRANS_ID = AIAH.TRANS_ID\n" +
                "   AND AIAH.SOURCE_ID = ?\n" +
                "   AND ESI.ITEM_CODE = AIAD.ITEM_CODE\n" +
                "   AND AIAD.ITEM_CODE = ?";
        sqlArgs.add(transId);
        sqlArgs.add(itemCode);

        sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
    }
}
