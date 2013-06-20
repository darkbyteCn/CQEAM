package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-3-21
 * Time: 15:42:53
 * Function; 备件盘点差异报表。
 */
public class SpareAttemperModel extends AMSSQLProducer {
      private SQLModel sqlModel = null;
    private AmsItemTransLDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public SpareAttemperModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (AmsItemTransLDTO) dtoParameter;
    }


    /**
     * 得到查询所有的MODEL
     *
     * @return
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        final String CONDITION_SQL = "{CONDITION_SQL}";
//        try {
            AmsItemTransLDTO Situsdto = (AmsItemTransLDTO) dtoParameter;
            List strArg = new ArrayList();
            String sqlStr = "";
           sqlStr = "SELECT distinct\n" +
                   "       AITL.BARCODE,\n" +
                   "       AITL.QUANTITY, --盘点数量\n" +
                   "       AMS_ITEM_TRANS_SX.GET_SPARE_ONHAND2(AITL.BARCODE,\n" +
                   "                                           AITH.FROM_ORGANIZATION_ID) KCQTY, -- 库存数量\n" +
                   "       AC.ITEM_NAME,\n" +
                   "       AC.ITEM_SPEC,\n" +
                   "       AC.ITEM_UNIT,\n" +
                   "       AC.SPARE_USAGE,\n" +
                   "       AMS_PUB_PKG.GET_VENDOR_NAME(AC.VENDOR_ID) VENDOR_NAME\n" +
                   "  FROM AMS_ITEM_TRANS_H AITH,\n" +
                   "       AMS_ITEM_TRANS_L AITL,\n" +
                   "       AMS_SPARE_CATEGORY AC\n" +
                   " WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                   "   AND AITL.BARCODE = AC.BARCODE\n" +
                   "   AND AITH.TRANS_NO = ?\n" +
                   "   AND (? IS NULL OR AC.ITEM_NAME LIKE ?) \n"+
                   "   AND (? IS NULL OR AC.ITEM_SPEC LIKE ?) \n"+
                   "   AND (? IS NULL OR AC.SPARE_USAGE LIKE ?) \n"+
                   "UNION ALL\n" +
                   "SELECT distinct\n" +
                   "       AMSI.BARCODE, --库存有而盘点没有\n" +
                   "       0  QUANTITY, --盘点数量\n" +
                   "       AMS_ITEM_TRANS_SX.GET_SPARE_ONHAND2(AMSI.BARCODE, AMSI.ORGANIZATION_ID) KCQTY, --库存数量\n" +
                   "       AMSC.ITEM_NAME,\n" +
                   "       AMSC.ITEM_SPEC,\n" +
                   "       AMSC.ITEM_UNIT,\n" +
                   "       AMSC.SPARE_USAGE,\n" +
                   "       AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME\n" +
                   "  FROM AMS_SPARE_INFO AMSI, AMS_SPARE_CATEGORY AMSC\n" +
                   " WHERE AMSI.ORGANIZATION_ID = ?\n" +
                   "   AND AMSC.BARCODE = AMSI.BARCODE\n" +
                   "   AND NOT EXISTS (SELECT 1\n" +
                   "          FROM AMS_ITEM_TRANS_H AITH, AMS_ITEM_TRANS_L AITL\n" +
                   "         WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                   "           AND AITL.BARCODE = AMSI.BARCODE\n" +
                   "           AND AITH.TRANS_NO = ?)\n" +
                   "   AND (? IS NULL OR AMSC.ITEM_NAME LIKE ?) \n"+
                   "   AND (? IS NULL OR AMSC.ITEM_SPEC LIKE ?) \n"+
                   "   AND (? IS NULL OR AMSC.SPARE_USAGE LIKE ?) \n";
//                  " AND EW.DISTRIBUTE_DATE >= TO_DATE('" + Situsdto.getFromDate() + " 00:00:00','" + CalendarConstant.CAL_PATT_14 + "')\n";

             strArg.add(Situsdto.getBatchNo());
             strArg.add(Situsdto.getItemName());
             strArg.add(Situsdto.getItemName());
             strArg.add(Situsdto.getItemSpec());
             strArg.add(Situsdto.getItemSpec());
             strArg.add(Situsdto.getSpareUsage());
             strArg.add(Situsdto.getSpareUsage());

             strArg.add(sfUser.getOrganizationId());
             strArg.add(Situsdto.getBatchNo());
             strArg.add(Situsdto.getItemName());
             strArg.add(Situsdto.getItemName());
             strArg.add(Situsdto.getItemSpec());
             strArg.add(Situsdto.getItemSpec());
             strArg.add(Situsdto.getSpareUsage());
             strArg.add(Situsdto.getSpareUsage());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(strArg);
//        } catch (CalendarException ex) {
//            ex.printLog();
//            throw new SQLModelException(ex);
//        }
        return sqlModel;
    }

//   另外的一个sql (此SQL不包含有出错的工单但是总工单为0 的情况)
//      SELECT A.CNT A, B.CNT B, A.CNT / B.CNT CNT
//     FROM (SELECT SUV.USERID, SUV.USERNAME, COUNT(1) CNT
//             FROM ETS_WORKORDER EW, SF_USER_V SUV
//
//            WHERE EW.ORGANIZATION_ID = 85
//              AND EW.RESPONSIBILITY_USER = SUV.USERID
//              AND EW.RESPONSIBILITY_USER IS NOT NULL
//           --    AND    EW.ATTRIBUTE6 = 1     --1 flase(出错工单); 0 true (正确的工单)
//            GROUP BY SUV.USERID, SUV.USERNAME) A, --出错工单
//          (SELECT COUNT(*) CNT, EW.IMPLEMENT_BY
//             FROM ETS_WORKORDER EW
//            WHERE EW.IMPLEMENT_BY IS NOT NULL
//            GROUP BY EW.IMPLEMENT_BY) B --工单总数
//    WHERE A.USERID = B.IMPLEMENT_BY
}
