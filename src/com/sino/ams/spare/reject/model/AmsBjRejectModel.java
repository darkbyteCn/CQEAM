package com.sino.ams.spare.reject.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-13
 * Time: 22:59:14
 */
public class AmsBjRejectModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public AmsBjRejectModel(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
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
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(transId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertLData(String transId, AmsItemTransLDTO lineDto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ITEM_TRANS_L\n" +
                "  (TRANS_ID, LINE_ID, ITEM_CODE, QUANTITY, BARCODE)\n" +
                "VALUES\n" +
                "  (?, AMS_ITEM_TRANS_L_S.NEXTVAL, ?, ?, ?)";
        sqlArgs.add(transId);
        sqlArgs.add(lineDto.getItemCode());
        sqlArgs.add(lineDto.getQuantity());
        sqlArgs.add(lineDto.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO headerDto = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "UPDATE AMS_ITEM_TRANS_H SET TRANS_NO=?,TRANS_STATUS=?,LAST_UPDATE_DATE=SYSDATE,LAST_UPDATE_BY=?,REMARK=? WHERE TRANS_ID=?";

        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(headerDto.getRemark());
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
                "   TO_OBJECT_NO," +
                "   FROM_ORGANIZATION_ID," +
                "   TO_ORGANIZATION_ID," +
                "   REMARK)\n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?)";
        sqlArgs.add(headerDto.getTransId());
        sqlArgs.add(headerDto.getTransNo());
        sqlArgs.add(headerDto.getTransType());
        sqlArgs.add(headerDto.getTransStatus());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(headerDto.getFromObjectNo());
        sqlArgs.add(headerDto.getToObjectNo());
        sqlArgs.add(sfUser.getOrganizationId());
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

        String sqlStr = "UPDATE ETS_ITEM_INFO SET ADDRESS_ID=? where BARCODE=?";

        sqlArgs.add(addresId);
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT AITL.LINE_ID,\n" +
                "       AITL.QUANTITY,\n" +
                "       ASI.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ASI.OBJECT_NO,\n" +
                "       ASI.QUANTITY ONHAND_QTY\n" +
                "  FROM AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       ETS_SYSTEM_ITEM    ESI,\n" +
                "       AMS_ITEM_TRANS_L   AITL\n" +
                " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                "   AND AMSC.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND ASI.BARCODE = AITL.BARCODE\n" +
                "   AND ASI.ITEM_STATUS = '´ýÐÞ'\n" +
                "   AND AITL.TRANS_ID = ?";
//        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AMS_PUB_PKG.GET_USER_NAME(AIAH.CREATED_BY) CREATED_USER,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME,\n" +
//                "       EO.WORKORDER_OBJECT_NAME,\n" +
//                "       EII.ADDRESS_ID,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.CREATED_BY," +
                "       AIAH.TRANS_STATUS,\n" +
                "       AIAH.REMARK\n" +
                "  FROM ams_item_trans_H AIAH,\n" +
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
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
