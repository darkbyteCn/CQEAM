package com.sino.ams.net.reportforms.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.net.reportforms.dto.SitusStatisticsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-13
 * Time: 9:38:36
 * To change this template use File | Settings | File Templates.
 */
public class FalsityRateModel extends BaseSQLProducer {
    private SQLModel sqlModel = null;
    private SitusStatisticsDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public FalsityRateModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (SitusStatisticsDTO) dtoParameter;
    }


    /**
     * 得到查询所有的MODEL
     *
     * @return
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        String fromDate = "";

        int orgId = SyBaseSQLUtil.NULL_INT_VALUE;

        boolean hasSqlProduced = false;
        SQLModel sqlModel = new SQLModel();

        final String CONDITION_SQL = "{CONDITION_SQL}";
        try {
            SitusStatisticsDTO Situsdto = (SitusStatisticsDTO) dtoParameter;
//             fromDate = Situsdto.getFromDate();
//            toDate = Situsdto.getToDate();
            orgId = Situsdto.getOrganizationId();
            List strArg = new ArrayList();
            String sqlStr = "";
            sqlStr = " SELECT C.USER_ID,\n" +
                    "        C.USERNAME USERNAME,\n" +
                    "        C.CNT ERR_NUM,\n" +
                    "        C.B NORMAL_NUM,\n" +
                    "        CONVERT(VARCHAR,ROUND(C.CNT / (CASE WHEN C.B=0 THEN C.CNT ELSE C.B END), 4) * 100)||'%' RATE\n " +
                    "   FROM (SELECT A.*,\n" +
                    "                (SELECT COUNT(1)\n" +
                    "                   FROM ETS_WORKORDER EW\n" +
                    "                  WHERE CONVERT(VARCHAR,EW.IMPLEMENT_BY) = CONVERT(VARCHAR,A.USER_ID)) B\n" +
                    "           FROM (SELECT SUV.USER_ID, SUV.USERNAME, COUNT(1) CNT\n" +
                    "                   FROM ETS_WORKORDER EW, SF_USER_V SUV\n" +
                    "                  WHERE EW.ORGANIZATION_ID = ?\n" +
                    "                    AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.DISTRIBUTE_DATE >= ?)" +
                    "                    AND ( " + SyBaseSQLUtil.isNull() + "  OR EW.DISTRIBUTE_DATE <=? )" +
                    "                    AND     " + SyBaseSQLUtil.isNotNull("EW.DIFFERENCE_REASON") + " " +   // 根据原因判断是否是出错的工单 不为空表示出错
//                    "                    AND    EW.ATTRIBUTE6 = 1 " +   //  --1 flase(出错工单); 0 true (正确的工单)
                    "                    AND CONVERT(VARCHAR,EW.RESPONSIBILITY_USER) *= CONVERT(VARCHAR,SUV.USER_ID)\n" +
                    "                    AND  " + SyBaseSQLUtil.isNotNull("EW.RESPONSIBILITY_USER") + "\n" +
                    "                  GROUP BY SUV.USER_ID, SUV.USERNAME) A) C";

            strArg.add(orgId);
            strArg.add(Situsdto.getFromDate());
            strArg.add(Situsdto.getFromDate());
            strArg.add(Situsdto.getToDate());
            strArg.add(Situsdto.getToDate());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(strArg);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

//   另外的一个sql (此SQL不包含有出错的工单但是总工单为0 的情况)
//      SELECT A.CNT A, B.CNT B, A.CNT / B.CNT CNT
//     FROM (SELECT SUV.USERID, SUV.USERNAME, COUNT(1) CNT
//             FROM ETS_WORKORDER EW, SF_USER_V SUV
//
//            WHERE EW.ORGANIZATION_ID = 85
//              AND EW.RESPONSIBILITY_USER = SUV.USERID
//              AND EW.RESPONSIBILITY_USER " + SyBaseSQLUtil.isNotNull("EII.FA_BARCODE") + "
//           --    AND    EW.ATTRIBUTE6 = 1     --1 flase(出错工单); 0 true (正确的工单)
//            GROUP BY SUV.USERID, SUV.USERNAME) A, --出错工单
//          (SELECT COUNT(*) CNT, EW.IMPLEMENT_BY
//             FROM ETS_WORKORDER EW
//            WHERE EW.IMPLEMENT_BY " + SyBaseSQLUtil.isNotNull("EII.FA_BARCODE") + "
//            GROUP BY EW.IMPLEMENT_BY) B --工单总数
//    WHERE A.USERID = B.IMPLEMENT_BY
}
