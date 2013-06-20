package com.sino.ams.spare.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

import java.util.List;
import java.util.ArrayList;

/**
 * User: zhoujs
 * Date: 2008-9-11
 * Time: 15:27:52
 * Function:
 */
public class SpareModel {
    public static SQLModel insertDData(String transId, AmsItemTransDDTO dto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO AMS_ITEM_TRANS_D\n" +
                        "  (TRANS_ID, DETAIL_ID, QUANTITY, SERIAL_NO, BARCODE,TROUBLE_LOC)\n" +
                        "VALUES\n" +
                        "  (?, AMS_ITEM_TRANS_D_S.NEXTVAL, ?, ?, ?, ?)";

        sqlArgs.add(transId);
        sqlArgs.add(dto.getQuantity());
        sqlArgs.add(dto.getSerialNo());
        sqlArgs.add(dto.getBarcode());
        sqlArgs.add(dto.getTroubleLoc());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 根据Detail表中数据自动插入行表
     * @param transId
     * @return
     */
    public static SQLModel insertLineModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "INSERT INTO AMS_ITEM_TRANS_L\n" +
                        "  (LINE_ID, TRANS_ID, BARCODE, QUANTITY)\n" +
                        "  SELECT AMS_ITEM_TRANS_L_S.NEXTVAL, T.*\n" +
                        "    FROM (SELECT AITD.TRANS_ID, AITD.BARCODE, SUM(AITD.QUANTITY)\n" +
                        "            FROM AMS_ITEM_TRANS_D AITD\n" +
                        "           WHERE AITD.TRANS_ID = ?\n" +
                        "           GROUP BY AITD.TRANS_ID, AITD.BARCODE) T";

        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 根据行表数据更新Detail表的行ID
     * @param transId
     * @return
     */
    public static SQLModel updateDetailModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "UPDATE AMS_ITEM_TRANS_D AITD\n" +
                        "   SET AITD.LINE_ID = (SELECT AITL.LINE_ID\n" +
                        "                         FROM AMS_ITEM_TRANS_L AITL\n" +
                        "                        WHERE AITL.TRANS_ID = AITD.TRANS_ID\n" +
                        "                          AND AITL.BARCODE = AITD.BARCODE)\n" +
                        " WHERE AITD.TRANS_ID = ?";

        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public static SQLModel deleteDetailsModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM"
                + " AMS_ITEM_TRANS_D"
                + " WHERE"
                + " TRANS_ID = ?";

        sqlArgs.add(transId);
        sqlModel.setArgs(sqlArgs);
        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    /**
     * 根据单据ID取Detail表数据
     * @param transId
     */
    public static SQLModel getDtlByTransIdModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String str = "SELECT AMSC.ITEM_NAME,\n" +
                "       AMSC.ITEM_SPEC,\n" +
                "       AMSC.ITEM_CATEGORY,\n" +
                "       AMSC.SPARE_USAGE,\n" +
                "       ASV.VENDOR_NAME,\n" +
                "       AMSC.BARCODE,\n" +
                "       AITD.SERIAL_NO,\n" +
                "       AITD.QUANTITY,\n" +
                "       AITD.LINE_ID,\n" +
                "       AITD.TROUBLE_REASON,\n" +
                "       AITD.TROUBLE_LOC\n" +
                "  FROM AMS_ITEM_TRANS_D   AITD,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE AITD.BARCODE = AMSC.BARCODE\n" +
                "       AND ASV.VENDOR_ID = AMSC.VENDOR_ID\n" +
                "       AND AITD.IS_ALLOT = 0\n" +
                "       AND AITD.TRANS_ID = ?";
        sqlArgs.add(transId);

        sqlModel.setSqlStr(str);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
        public static SQLModel getLineByTransIdModel(String transId){  //返修申领行明细
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASSC.ITEM_NAME,\n" +
                "       ASSC.BARCODE,\n" +
                "       ASSC.SPARE_USAGE,\n" +
                "       ASSC.ITEM_SPEC,\n" +
                "       ASSC.ITEM_CATEGORY,\n" +
                "       AITL.LINE_ID,\n" +
                "       AITL.REASONS,\n" +
                "       ASI.QUANTITY ONHAND_QTY,\n" +
                "       AITL.QUANTITY,\n" +
                "       AMS_PUB_PKG.GET_VENDOR_NAME(ASSC.VENDOR_ID) VENDOR_NAME," +
                "       AMS_ITEM_TRANS_SX.GET_ACTUAL_QTY_SX(AITL.BARCODE, ?) ACTUAL_QTY \n" +
                "  FROM AMS_ITEM_TRANS_L   AITL,\n" +
                "       AMS_SPARE_CATEGORY ASSC,\n" +
                "       AMS_SPARE_INFO     ASI,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       AMS_ITEM_TRANS_H   AITH\n" +
                " WHERE AITL.BARCODE = ASSC.BARCODE\n" +
                "   AND AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "   AND ASI.BARCODE = AITL.BARCODE\n" +
                "   AND ASI.ORGANIZATION_ID = AITH.FROM_ORGANIZATION_ID\n" +
                "   AND EO.WORKORDER_OBJECT_NO = ASI.OBJECT_NO\n" +
                "   AND EO.OBJECT_CATEGORY = 72\n" +
                "   AND AITL.TRANS_ID =?";
        sqlArgs.add(transId);
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public static SQLModel updateHeaderStatusModel(SfUserDTO sfUser,AmsItemTransHDTO transHDTO) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="UPDATE AMS_ITEM_TRANS_H\n" +
                "   SET TRANS_STATUS = ?, LAST_UPDATE_DATE = SYSDATE, LAST_UPDATE_BY = ?\n" +
                " WHERE TRANS_ID = ?";
        sqlArgs.add(transHDTO.getTransStatus());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(transHDTO.getTransId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
