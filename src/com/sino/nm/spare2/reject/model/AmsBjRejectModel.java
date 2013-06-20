package com.sino.nm.spare2.reject.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ConvertUtil;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;

/**
 * User: yuyao
 * Date: 2007-11-13
 * Time: 22:59:14
 */
public class AmsBjRejectModel extends AMSSQLProducer {
    private AmsItemTransHDTO headerDto = null;

    public AmsBjRejectModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        headerDto = dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE, \n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
                "   AND AITH.TRANS_TYPE = 'BJBF'\n"
                + "AND (''=? OR ? IS NULL OR TRANS_NO = ?)"
                + "AND (''=? OR ? IS NULL OR TRANS_STATUS = ?)"
                + "AND (-1=? OR FROM_ORGANIZATION_ID = ?)"
                + "AND (?='' OR AITH.CREATION_DATE >= ?)"
                + "AND (?='' OR AITH.CREATION_DATE<=CONVERT(DATE,?))" +
                "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransNo());
//		sqlArgs.add(headerDto.getTransType());
//		sqlArgs.add(headerDto.getTransType());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(headerDto.getTransStatus());

//		sqlArgs.add(headerDto.getFromObjectNo());
//		sqlArgs.add(headerDto.getFromObjectNo());
        sqlArgs.add((headerDto.getFromOrganizationId()));
        sqlArgs.add(headerDto.getFromOrganizationId());
        try {
            sqlArgs.add(headerDto.getFromDate());
            sqlArgs.add(headerDto.getFromDate());
            sqlArgs.add(headerDto.getSQLToDate());
            sqlArgs.add(headerDto.getSQLToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getOrderCancelModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE"
                + " AMS_ITEM_TRANS_H AATH"
                + " SET"
                + " AATH.TRANS_STATUS = ?,"
                + " AATH.LAST_UPDATE_DATE = SYSDATE,"
                + " AATH.LAST_UPDATE_BY = ?"
                + " WHERE"
                + " AATH.TRANS_ID = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(AssetsDictConstant.CANCELED);
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertLData(String transId, AmsItemTransLDTO lineDto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_TRANS_L\n" +
                "  (TRANS_ID, LINE_ID, ITEM_CODE, QUANTITY, BARCODE, STORAGE_ID, SOURCE_ID)\n" +
                "VALUES\n" +
                "  (?, NEWID(), ?, ?, ?, ?, ?)";
        sqlArgs.add(transId);
        sqlArgs.add(lineDto.getItemCode());
        sqlArgs.add(lineDto.getQuantity());
        sqlArgs.add(lineDto.getBarcode());
        sqlArgs.add(lineDto.getStorageId());    //用来存仓库OBJECT_NO
        sqlArgs.add(lineDto.getSourceId());     //用来存送修单的行ID
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H SET TRANS_NO=?,TRANS_STATUS=?,LAST_UPDATE_DATE=GETDATE(),LAST_UPDATE_BY=?,REMARK=?,ATTRIBUTE1=? WHERE TRANS_ID=?";

        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(headerDto.getRemark());
        sqlArgs.add(headerDto.getAttribute1());
        sqlArgs.add(headerDto.getTransId());
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
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataCreateModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = null;
        String sqlStr = null;
        sqlArgs = new ArrayList();
        sqlStr = "INSERT INTO AMS_ITEM_TRANS_H\n" +
                "  (TRANS_ID,\n" +
                "   TRANS_NO,\n" +
                "   TRANS_TYPE,\n" +
                "   TRANS_STATUS,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY," +
                "   FROM_OBJECT_NO," +
                "   TO_OBJECT_NO," +
                "   FROM_ORGANIZATION_ID," +
                "   TO_ORGANIZATION_ID," +
                "   REMARK," +
                "   ATTRIBUTE1)\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, GETDATE(), ?, ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(headerDto.getTransId());
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransType());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(userAccount.getUserId());
        sqlArgs.add(headerDto.getFromObjectNo());
        sqlArgs.add(headerDto.getToObjectNo());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(headerDto.getRemark());
        sqlArgs.add(headerDto.getAttribute1());
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

    public SQLModel getByTransIdModel(String transId,String attribute1) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT AITL.LINE_ID,\n" +
                "       AITL.QUANTITY,\n" +
                "       AITL.STORAGE_ID,\n" +
                "       AITL.SOURCE_ID,\n" +
                "       ASI.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ASI.DISREPAIR_QUANTITY ONHAND_QTY\n" +
                "  FROM AMS_SPARE_INFO     ASI,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       AMS_ITEM_TRANS_L   AITL\n" +
                " WHERE ASI.BARCODE = ESI.MIS_ITEM_CODE\n" +
                "   AND ASI.BARCODE = AITL.BARCODE\n" +
                "   AND ASI.OBJECT_NO = AITL.STORAGE_ID\n" +
                "   AND AITL.TRANS_ID = ?";
        if(attribute1.equals("2")){
            str = "SELECT AITL.LINE_ID,\n" +
                    "       AITL.QUANTITY,\n" +
                    "       AITL.STORAGE_ID,\n" +
                    "       AITL.SOURCE_ID,\n" +
                    "       ESI.MIS_ITEM_CODE BARCODE,\n" +
                    "       ESI.ITEM_CODE,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AITL2.QUANTITY - AITL2.RETURNED_QUANTITY ONHAND_QTY\n" +
                    "  FROM ETS_SYSTEM_ITEM ESI, AMS_ITEM_TRANS_L AITL, AMS_ITEM_TRANS_L AITL2\n" +
                    " WHERE ESI.MIS_ITEM_CODE = AITL.BARCODE\n" +
                    "   AND AITL.SOURCE_ID = AITL2.LINE_ID\n" +
                    "   AND AITL.TRANS_ID =?";
        }
//        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT dbo.APP_GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME,\n" +
                "        dbo.APP_GET_ORGNIZATION_NAME(AIAH.FROM_ORGANIZATION_ID) FROM_ORGANIZATION_NAME,\n" +
//                "       EII.ADDRESS_ID,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.CREATED_BY," +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.REMARK,\n" +
                "       AIAH.ATTRIBUTE1\n" +
                "  FROM AMS_ITEM_TRANS_H    AIAH,\n" +
                "       ETS_FLEX_VALUES     EFV,\n" +
                "       ETS_FLEX_VALUE_SET  EFVS\n" +
//                "       AMS_OBJECT_ADDRESS  AOA,\n" +
//                "       AMS_ITEM_TRANS_L    AITL\n" +
//                "       ETS_ITEM_INFO       EII,\n" +
//                "       ETS_OBJECT          EO\n" +
                " WHERE AIAH.TRANS_STATUS = EFV.CODE\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'\n" +
//                "   AND AIAH.TRANS_ID = AITL.TRANS_ID\n" +
//                "   AND AITL.BARCODE = EII.BARCODE\n" +
//                "   AND EII.ADDRESS_ID = AOA.ADDRESS_ID\n" +
//                "   AND EO.WORKORDER_OBJECT_NO = AIAH.FROM_OBJECT_NO\n" +
                "   AND AIAH.TRANS_ID = ?";
        sqlArgs.add(headerDto.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public void setHeaderDto(AmsItemTransHDTO headerDto) {
        this.headerDto = headerDto;
    }
}
