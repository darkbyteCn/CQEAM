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
 * User: Yung Kam Hing
 * Date: 2008-4-8
 * Time: 14:10:00
 * Function; 备件返修率月份统计
 */
public class SpareReRatModel extends AMSSQLProducer {
       private SQLModel sqlModel = null;
    private AmsItemTransLDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public SpareReRatModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (AmsItemTransLDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsItemTransLDTO Situsdto = (AmsItemTransLDTO) dtoParameter;
        List strArg = new ArrayList();
        String sqlStr = "SELECT T.COMPANY,\n" +
                "       T.BARCODE,\n" +
                "       T.ITEM_NAME,\n" +
                "       T.ITEM_SPEC,\n" +
                "       T.ITEM_CATEGORY,\n" +
                "       T.SPARE_USAGE,\n" +
                "       T.VENDOR_NAME,\n" +
                "       SUM(T.REP_NUM) REP_NUM,\n" +
                "       (SELECT SUM(AION.QUANTITY)\n" +
                "          FROM AMS_ITEM_ON_NET AION\n" +
                "         WHERE T.BARCODE = AION.PART_NO) TOTAL_NUM,\n" +
                "       TO_CHAR((100 * SUM(T.REP_NUM) /\n" +
                "               (SELECT SUM(AION.QUANTITY)\n" +
                "                   FROM AMS_ITEM_ON_NET AION\n" +
                "                  WHERE T.BARCODE = AION.PART_NO)),\n" +
                "               'FM9999990.99') || '%' RAT_NUM,\n" +
                "       T.TRANS_YEAR,\n" +
                "       T.TRANS_MONTH\n" +
                "  FROM (SELECT AMSC.SPARE_USAGE,\n" +
                "               AMSC.BARCODE,\n" +
                "               AMSC.ITEM_NAME,\n" +
                "               AMSC.ITEM_SPEC,\n" +
                "               AMSC.ITEM_CATEGORY,\n" +
                "               ASV.VENDOR_NAME,\n" +
                "               EOCM.COMPANY,\n" +
                "               SUM(AITL.QUANTITY) REP_NUM,\n" +
                "               TO_CHAR(AITH.CREATION_DATE,\n" +
                "                       'YYYY') TRANS_YEAR,\n" +
                "               TO_CHAR(AITH.CREATION_DATE,\n" +
                "                       'MM') TRANS_MONTH\n" +
                "          FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "               AMS_ITEM_TRANS_L   AITL,\n" +
                "               AMS_SPARE_CATEGORY AMSC,\n" +
                "               AMS_SPARE_VENDORS  ASV,\n" +
                "               ETS_OU_CITY_MAP    EOCM\n" +
                "         WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "               AND AITL.BARCODE = AMSC.BARCODE\n" +
                "               AND ASV.VENDOR_ID = AMSC.VENDOR_ID\n" +
                "               AND AITH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "               AND AITH.TRANS_TYPE = 'FXSQ'\n" +
                "               AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                "               AND (? IS NULL OR AMSC.ITEM_NAME LIKE ?)\n" +
                "               AND (? IS NULL OR AMSC.ITEM_SPEC LIKE ?)\n" +
                "               AND (? IS NULL OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                "               AND (? IS NULL OR AMSC.SPARE_USAGE LIKE ?)\n" +
                "               AND (? IS NULL OR ASV.VENDOR_ID = ?)\n" +
                "               AND (? IS NULL OR EOCM.ORGANIZATION_ID = ?)\n" +
                "               AND (? IS NULL OR TO_CHAR(AITH.CREATION_DATE,\n" +
                "                                         'YYYY') = ?)\n" +
                "               AND (? IS NULL OR TO_CHAR(AITH.CREATION_DATE,\n" +
                "                                         'MM') = ?)\n" +
                "         GROUP BY AMSC.SPARE_USAGE,\n" +
                "                  AMSC.BARCODE,\n" +
                "                  AMSC.ITEM_NAME,\n" +
                "                  AMSC.ITEM_SPEC,\n" +
                "                  AMSC.ITEM_CATEGORY,\n" +
                "                  ASV.VENDOR_NAME,\n" +
                "                  EOCM.COMPANY,\n" +
                "                  TO_CHAR(AITH.CREATION_DATE,\n" +
                "                          'YYYY'),\n" +
                "                  TO_CHAR(AITH.CREATION_DATE,\n" +
                "                          'MM')\n" +
                "         ORDER BY MIN(AITH.CREATION_DATE)) T\n" +
                " GROUP BY T.BARCODE,\n" +
                "          T.ITEM_NAME,\n" +
                "          T.ITEM_SPEC,\n" +
                "          T.ITEM_CATEGORY,\n" +
                "          T.SPARE_USAGE,\n" +
                "          T.VENDOR_NAME,\n" +
                "          T.COMPANY,\n" +
                "          T.TRANS_YEAR,\n" +
                "          T.TRANS_MONTH";

        strArg.add(Situsdto.getItemName());
        strArg.add(Situsdto.getItemName());
        strArg.add(Situsdto.getItemSpec());
        strArg.add(Situsdto.getItemSpec());
        strArg.add(Situsdto.getItemCategory());
        strArg.add(Situsdto.getItemCategory());
        strArg.add(Situsdto.getSpareUsage());
        strArg.add(Situsdto.getSpareUsage());
        strArg.add(Situsdto.getVendorId());
        strArg.add(Situsdto.getVendorId());
        strArg.add(Situsdto.getOrganizationId());
        strArg.add(Situsdto.getOrganizationId());
        strArg.add(Situsdto.getSearchYear());
        strArg.add(Situsdto.getSearchYear());
        String searchMonth = Situsdto.getSearchMonth();
        if(searchMonth.length() == 1)
        searchMonth = "0" + searchMonth;
        strArg.add(searchMonth);
        strArg.add(searchMonth);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(strArg);
        return sqlModel;
    }


/*  2010.3.15 SUHP修改
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
//        try {
            AmsItemTransLDTO Situsdto = (AmsItemTransLDTO) dtoParameter;
            List strArg = new ArrayList();
            String sqlStr = "";
            sqlStr = "SELECT T.BARCODE," +
                    "       T.ITEM_NAME,\n" +
                    "       T.ITEM_SPEC,\n" +
                    "       T.ITEM_CATEGORY,\n" +
                    "       T.SPARE_USAGE,\n" +
                    "       T.VENDOR_NAME,\n" +
                    "       SUM(T.REP_NUM) REP_NUM,\n" +
//                    "       (SELECT SUM(AION.QUANTITY)\n" +
//                    "          FROM AMS_SPARE_CATEGORY AMSC,\n" +
//                    "               AMS_ITEM_ON_NET    AION,\n" +
//                    "               AMS_SPARE_VENDORS  ASV\n" +
//                    "         WHERE T.SPARE_USAGE = AMSC.SPARE_USAGE\n" +
//                    "               AND AMSC.BARCODE = AION.PART_NO\n" +
//                    "               AND AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
//                    "               AND T.VENDOR_NAME = ASV.VENDOR_NAME) TOTAL_NUM,\n" +       //原逻辑
                    "       (SELECT SUM(AION.QUANTITY)\n" +
                    "           FROM   AMS_ITEM_ON_NET AION\n" +
                    "           WHERE  T.BARCODE = AION.PART_NO) TOTAL_NUM, \n" +       //贾龙川2009-12-12修改
//                    "       TO_CHAR((100 * SUM(T.REP_NUM) /\n" +
//                    "               (SELECT SUM(AION.QUANTITY)\n" +
//                    "                   FROM AMS_SPARE_CATEGORY AMSC,\n" +
//                    "                        AMS_ITEM_ON_NET    AION,\n" +
//                    "                        AMS_SPARE_VENDORS  ASV\n" +
//                    "                  WHERE T.SPARE_USAGE = AMSC.SPARE_USAGE\n" +
//                    "                        AND AMSC.BARCODE = AION.PART_NO\n" +
//                    "                        AND AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
//                    "                        AND T.VENDOR_NAME = ASV.VENDOR_NAME)),\n" +
//                    "               'FM9999990.9999') || '%' RAT_NUM,\n" +       //原逻辑
                    "       TO_CHAR((100 * SUM(T.REP_NUM) /\n" +
                    "               (SELECT SUM(AION.QUANTITY)\n" +
                    "                               FROM   AMS_ITEM_ON_NET AION\n" +
                    "                               WHERE  T.BARCODE = AION.PART_NO)),\n" +
                    "               'FM9999990.9999') || '%' RAT_NUM,\n" +       //贾龙川2009-12-12修改
                    "       T.TRANS_YEAR,\n" +
                    "       T.TRANS_MONTH\n" +
                    "  FROM (SELECT AMSC.SPARE_USAGE,\n" +
                    "               AMSC.BARCODE,\n" +
                    "               AMSC.ITEM_NAME,\n" +
                    "               AMSC.ITEM_SPEC,\n" +
                    "               AMSC.ITEM_CATEGORY,\n" +
                    "               ASV.VENDOR_NAME,\n" +
                    "               SUM(AITL.QUANTITY) REP_NUM,\n" +
                    "               TO_CHAR(AITH.CREATION_DATE, 'YYYY') TRANS_YEAR,\n" +
                    "               TO_CHAR(AITH.CREATION_DATE, 'MM') TRANS_MONTH\n" +
                    "          FROM AMS_ITEM_TRANS_H   AITH,\n" +
                    "               AMS_ITEM_TRANS_L   AITL,\n" +
                    "               AMS_SPARE_CATEGORY AMSC,\n" +
                    "               AMS_SPARE_VENDORS  ASV\n" +
                    "         WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                    "               AND AITL.BARCODE = AMSC.BARCODE\n" +
                    "               AND ASV.VENDOR_ID = AMSC.VENDOR_ID\n" +
                    "               AND AITH.TRANS_TYPE = 'BJSX'\n" +
                    "               AND AITH.TRANS_STATUS = 'COMPLETED'\n" +
                    "               AND (? IS NULL OR AMSC.ITEM_NAME LIKE ?)\n" +
                    "               AND (? IS NULL OR AMSC.ITEM_SPEC LIKE ?)\n" +
                    "               AND (? IS NULL OR AMSC.ITEM_CATEGORY LIKE ?)\n" +
                    "               AND (? IS NULL OR AMSC.SPARE_USAGE LIKE ?)\n" +
                    "               AND (? IS NULL OR ASV.VENDOR_ID = ?)\n" +
                    "               AND (? IS NULL OR TO_CHAR(AITH.CREATION_DATE, 'YYYY') = ?)\n" +
                    "               AND (? IS NULL OR TO_CHAR(AITH.CREATION_DATE, 'MM') = ?)\n" +
                    "         GROUP BY AMSC.SPARE_USAGE,\n" +
                    "                  AMSC.BARCODE,\n" +
                    "                  AMSC.ITEM_NAME,\n" +
                    "                  AMSC.ITEM_SPEC,\n" +
                    "                  AMSC.ITEM_CATEGORY,\n" +
                    "                  ASV.VENDOR_NAME,\n" +
                    "                  TO_CHAR(AITH.CREATION_DATE, 'YYYY'),\n" +
                    "                  TO_CHAR(AITH.CREATION_DATE, 'MM')\n" +
                    "         ORDER BY MIN(AITH.CREATION_DATE)) T\n" +
                    " GROUP BY T.BARCODE,\n" +
                    "          T.ITEM_NAME,\n" +
                    "          T.ITEM_SPEC,\n" +
                    "          T.ITEM_CATEGORY,\n" +
                    "          T.SPARE_USAGE,\n" +
                    "          T.VENDOR_NAME,\n" +
                    "          T.TRANS_YEAR,\n" +
                    "          T.TRANS_MONTH";

           strArg.add(Situsdto.getItemName());
           strArg.add(Situsdto.getItemName());
           strArg.add(Situsdto.getItemSpec());
           strArg.add(Situsdto.getItemSpec());
           strArg.add(Situsdto.getItemCategory());
           strArg.add(Situsdto.getItemCategory());
           strArg.add(Situsdto.getSpareUsage());
           strArg.add(Situsdto.getSpareUsage());           
           strArg.add(Situsdto.getVendorId());
           strArg.add(Situsdto.getVendorId());
           strArg.add(Situsdto.getSearchYear());
           strArg.add(Situsdto.getSearchYear());
           String searchMonth = Situsdto.getSearchMonth();
           if(searchMonth.length() == 1)
               searchMonth = "0" + searchMonth;
           strArg.add(searchMonth);
           strArg.add(searchMonth);

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(strArg);
//        } catch (CalendarException ex) {
//            ex.printLog();
//            throw new SQLModelException(ex);
//        }
        return sqlModel;
    }
*/



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
