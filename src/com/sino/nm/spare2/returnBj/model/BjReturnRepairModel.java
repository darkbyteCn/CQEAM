package com.sino.nm.spare2.returnBj.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.allot.dto.AmsBjsAllotHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:34:04
 */
public class BjReturnRepairModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;
    private AmsBjsAllotHDTO headerDto = null;

    public BjReturnRepairModel(SfUserDTO userAccount, AmsBjsAllotHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        headerDto = (AmsBjsAllotHDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
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
//                + "AND TRANS_STATUS = '" + DictConstant.COMPLETED +"'\n"
                + "AND (''=? OR ? IS NULL OR TO_OBJECT_NO = ?)"
                + "AND (-1=? OR TO_ORGANIZATION_ID = ?)"
                + "AND (''=? OR ? IS NULL OR AITH.TRANS_TYPE = ?)"
                + "AND (''=? OR AITH.CREATION_DATE >= ?)"
                + "AND (''=? OR AITH.CREATION_DATE <=CONVERT(DATE, ?))" +
                "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransNo());
//		sqlArgs.add(headerDto.getTransType());
//		sqlArgs.add(headerDto.getTransType());
//        sqlArgs.add(headerDto.getTransStatus());
//        sqlArgs.add(headerDto.getTransStatus());
//		sqlArgs.add(headerDto.getFromUser());
//		sqlArgs.add(headerDto.getFromUser());
//		sqlArgs.add(headerDto.getToUser());
//		sqlArgs.add(headerDto.getToUser());
//		sqlArgs.add(headerDto.getFromDept());
//		sqlArgs.add(headerDto.getFromDept());
//		sqlArgs.add(headerDto.getToDept());
//		sqlArgs.add(headerDto.getToDept());
//		sqlArgs.add(headerDto.getFromObjectNo());
//		sqlArgs.add(headerDto.getFromObjectNo());
        sqlArgs.add(headerDto.getToObjectNo());
        sqlArgs.add(headerDto.getToObjectNo());
        sqlArgs.add(headerDto.getToObjectNo());
//		sqlArgs.add(headerDto.getFromOrganizationId());
//		sqlArgs.add(headerDto.getFromOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(headerDto.getTransType());
        sqlArgs.add(headerDto.getTransType());
        sqlArgs.add(headerDto.getTransType());
        try {
            sqlArgs.add(headerDto.getFromDate());
            sqlArgs.add(headerDto.getFromDate());
            sqlArgs.add(headerDto.getToDate());
            sqlArgs.add(headerDto.getToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
//		sqlArgs.add(headerDto.getRcvUser());
//		sqlArgs.add(headerDto.getRcvUser());
//		sqlArgs.add(headerDto.getCreationDate());
//		sqlArgs.add(headerDto.getCreationDate());
//		sqlArgs.add(headerDto.getCreatedBy());
//		sqlArgs.add(headerDto.getCreatedBy());
//		sqlArgs.add(headerDto.getLastUpdateDate());
//		sqlArgs.add(headerDto.getLastUpdateDate());
//		sqlArgs.add(headerDto.getLastUpdateBy());
//		sqlArgs.add(headerDto.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertLData(String transId, AmsItemTransLDTO lineDto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_TRANS_L\n" +
                "  (TRANS_ID, LINE_ID, ITEM_CODE, BARCODE, QUANTITY, BATCH_NO)\n" +
                "VALUES\n" +
                "  (?, NEWID(), ?, ?, ?, ?)";
        sqlArgs.add(transId);
        sqlArgs.add(lineDto.getItemCode());
        sqlArgs.add(lineDto.getBarcode());
        sqlArgs.add(lineDto.getQuantity());
        sqlArgs.add(lineDto.getBatchNo());
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
                "      (?,GETDATE(),?,?,?)";
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
        sqlStr = "INSERT INTO AMS_ITEM_TRANS_H\n" +
                "  (TRANS_ID,\n" +
                "   TRANS_NO,\n" +
                "   TRANS_TYPE,\n" +
                "   TRANS_STATUS,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY," +
                "   TO_OBJECT_NO," +
                "   TO_ORGANIZATION_ID,\n" +
                "   REMARK)\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, GETDATE(), ?, ?, ?, ?)";
        sqlArgs.add(headerDto.getTransId());
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransType());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(headerDto.getToObjectNo());
        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(headerDto.getRemark());   

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

        String sqlStr = "UPDATE ETS_ITEM_INFO SET ADDRESS_ID=? WHERE BARCODE=?";

        sqlArgs.add(addresId);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H SET TRANS_NO=?,TRANS_STATUS=?,TO_OBJECT_NO=?," +
                " LAST_UPDATE_BY = ?, LAST_UPDATE_DATE = GETDATE() WHERE TRANS_ID=?";

        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(headerDto.getToObjectNo());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(headerDto.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT \n" +
                "       AITL.BARCODE,\n" +
                "       AITL.BATCH_NO,\n" +
                "       AITL.QUANTITY,\n" +
                "       dbo.AMS_INV_TRANS2_GET_REPAIR_QTY_BY_ORDER(AITL.BARCODE, AITL.BATCH_NO) REPAIR_QUANTITY,\n" +
                "       dbo.AMS_INV_TRANS2_GET_RETURNED_QTY_BY_ORDER(AITL.BARCODE, AITL.BATCH_NO) RETURNED_QUANTITY,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_CODE\n" +
                "  FROM AMS_ITEM_TRANS_L AITL,  ETS_SYSTEM_ITEM ESI\n" +
                " WHERE AITL.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AITL.TRANS_ID = ?";
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
                "       dbo.APP_GET_STATUS_NAME(AIAH.TRANS_STATUS) TRANS_STATUS_NAME,\n" +
                "       EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.CREATED_BY,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.TO_OBJECT_NO,\n" +
                "       AIAH.REMARK\n" +
                "  FROM AMS_ITEM_TRANS_H AIAH, ETS_OBJECT EO\n" +
                " WHERE AIAH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO\n" +
                "   AND AIAH.TRANS_ID = ?";
        sqlArgs.add(headerDto.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
