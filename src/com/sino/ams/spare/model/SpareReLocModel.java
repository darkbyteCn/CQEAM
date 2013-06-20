package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.Calendar;
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
 * Function; 备件返修率地市统计
 */
public class SpareReLocModel extends AMSSQLProducer {
       private SQLModel sqlModel = null;
    private AmsItemTransLDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public SpareReLocModel(SfUserDTO userAccount, DTO dtoParameter) {
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
//        try {
            AmsItemTransLDTO Situsdto = (AmsItemTransLDTO) dtoParameter;
            List strArg = new ArrayList();
            String sqlStr = "";
        String searchYear =  Situsdto.getSearchYear();
        String searchMonth = Situsdto.getSearchMonth();
        int mode;
        if(searchYear.equals("")) {
            if(searchMonth.equals("")) {
                mode = 0;
            } else {
                mode = 2;
                Calendar c = Calendar.getInstance();
                searchYear=String.valueOf(c.get(Calendar.YEAR));
                if(searchMonth.length() == 1)
                    searchMonth = "0" + searchMonth;
            }
        } else {
            if(searchMonth.equals("")) {
                mode = 1;
            } else {
                mode = 2;
                if(searchMonth.length() == 1)
                    searchMonth = "0" + searchMonth;
            }
        }
        int itemMode;
        String spareUsage, itemName, itemSpec;
        spareUsage = Situsdto.getSpareUsage();
        itemName = Situsdto.getItemName();
        itemSpec = Situsdto.getItemSpec();
        if(!itemName.equals("") || !itemSpec.equals("")) {
            if(!itemSpec.equals(""))
                itemMode = 0;
            else
                itemMode = 1;
        } else {
            itemMode = 2;
        }
        sqlStr = "SELECT T.SPARE_USAGE, T.VENDOR_NAME, SUM(T.REP_NUM) REP_NUM, \n" +
                 "       (SELECT SUM(AION.QUANTITY) \n" +
                 "        FROM AMS_SPARE_CATEGORY AMSC, AMS_ITEM_ON_NET AION \n" +
                 "        WHERE T.SPARE_USAGE = AMSC.SPARE_USAGE \n" +
                 "              AND AMSC.BARCODE = AION.PART_NO \n" +
                 "              AND T.VENDOR_NAME =  AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID)\n" +
                 "       ) TOTAL_NUM, \n" +
                 "       TO_CHAR((100 * SUM(T.REP_NUM) / \n" +
                 "               (SELECT SUM(AION.QUANTITY) \n" +
                 "                FROM AMS_SPARE_CATEGORY AMSC, AMS_ITEM_ON_NET AION \n" +
                 "                WHERE T.SPARE_USAGE = AMSC.SPARE_USAGE \n" +
                 "                      AND AMSC.BARCODE = AION.PART_NO \n" +
                 "                      AND T.VENDOR_NAME =  AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID)\n" +
                 "               )\n" +
                 "             ), 'FM9999990.9999') || '%' RAT_NUM,\n";
        switch(mode) {
            case 1:
                break;
            case 2:
                break;
            case 0: default:
                sqlStr += "       T.TRANS_YEAR, T.TRANS_MONTH, \n";
                break;
        }
        sqlStr +=

                 "       T.COMPANY_NAME \n" +
                 "FROM ( \n" +
                 "      SELECT AMSC.SPARE_USAGE, AMSC.BARCODE, AMSC.ITEM_NAME, \n" +
                 "             AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) VENDOR_NAME, \n" +
                 "             SUM(AITL.QUANTITY) REP_NUM, \n";
        switch(mode) {
            case 1:
                break;
            case 2:
                break;
            case 0: default:
                sqlStr += "             TO_CHAR(AITH.CREATION_DATE,'YYYY') TRANS_YEAR, \n" +
                          "             TO_CHAR(AITH.CREATION_DATE,'MM') TRANS_MONTH, \n";
                   break;
        }
        sqlStr +=
                 "             AMS_PUB_PKG.GET_ORGNIZATION_NAME(AITH.FROM_ORGANIZATION_ID) COMPANY_NAME \n" +
                 "      FROM AMS_ITEM_TRANS_H AITH, AMS_ITEM_TRANS_L AITL, \n" +
                 "           AMS_SPARE_CATEGORY AMSC \n" +
                 "      WHERE AITH.TRANS_ID = AITL.TRANS_ID \n" +
                 "            AND AITL.BARCODE = AMSC.BARCODE\n" +
                 "            AND (AITH.TRANS_TYPE = 'FXSQ' OR \n" +
                 "                  (AITH.TRANS_TYPE = 'BJFK' AND AITH.FROM_ORGANIZATION_ID = 82)) \n" +
//                 "            AND (AITH.FROM_ORGANIZATION_ID = AMSC.ORGANIZATION_ID) \n" +
                 "            AND (? IS NULL OR AMSC.SPARE_USAGE LIKE ?) \n" +
                 "            AND (? IS NULL OR AMSC.ITEM_NAME LIKE ?) \n" +
                 "            AND (? IS NULL OR AMSC.ITEM_SPEC LIKE ?) \n" +
                 "            AND (? IS NULL OR AMS_PUB_PKG.GET_VENDOR_NAME(AMSC.VENDOR_ID) LIKE ?)\n";
        switch(mode) {
            case 1:
                sqlStr += "            AND (? IS NULL OR TO_CHAR(AITH.CREATION_DATE,'YYYY') = ?) \n";
                break;
            case 2:
                sqlStr += "            AND (? IS NULL OR TO_CHAR(AITH.CREATION_DATE,'YYYY') = ?) \n" +
                          "            AND (? IS NULL OR TO_CHAR(AITH.CREATION_DATE,'MM') = ?) \n";
                break;
            case 0: default:
                break;
        }
        sqlStr +=
                 "      GROUP BY AMSC.SPARE_USAGE, AMSC.BARCODE, AMSC.ITEM_NAME, \n" +
                 "               AMSC.VENDOR_ID, \n";
        switch(mode) {
            case 1:
                break;
            case 2:
                break;
            case 0: default:
                sqlStr += "               TO_CHAR(AITH.CREATION_DATE,'YYYY'), \n" +
                          "               TO_CHAR(AITH.CREATION_DATE,'MM'), \n";
                break;
        }
        sqlStr +=
                 "               AMS_PUB_PKG.GET_ORGNIZATION_NAME(AITH.FROM_ORGANIZATION_ID) \n" +
                 "      ORDER BY AMSC.VENDOR_ID, MIN(AITH.CREATION_DATE) \n" +
                 "     ) T \n" +
                 "GROUP BY T.SPARE_USAGE, T.VENDOR_NAME, ";
        switch(mode){
            case 1:
                break;
            case 2:
                break;
            case 0: default:
                sqlStr += "T.TRANS_YEAR, T.TRANS_MONTH, ";
                break;
        }
        sqlStr += " T.COMPANY_NAME";


//            strArg.add(sfUser.getOrganizationId());

        strArg.add(Situsdto.getSpareUsage());
        strArg.add(Situsdto.getSpareUsage());
        strArg.add(Situsdto.getItemName());
        strArg.add(Situsdto.getItemName());
        strArg.add(Situsdto.getItemSpec());
        strArg.add(Situsdto.getItemSpec());
        strArg.add(Situsdto.getVendorName());
        strArg.add(Situsdto.getVendorName());
        switch(mode) {
            case 1:
                strArg.add(searchYear);
                strArg.add(searchYear);
                break;
            case 2:
                strArg.add(searchYear);
                strArg.add(searchYear);
                strArg.add(searchMonth);
                strArg.add(searchMonth);
                break;
            case 0: default:
                break;
        }

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
