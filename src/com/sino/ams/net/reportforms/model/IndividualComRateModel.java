package com.sino.ams.net.reportforms.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.net.reportforms.dto.SitusStatisticsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.StrUtil;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-13
 * Time: 9:33:04
 * To change this template use File | Settings | File Templates.
 */
public class IndividualComRateModel extends BaseSQLProducer {
    private SQLModel sqlModel = null;
    private SitusStatisticsDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public IndividualComRateModel(SfUserDTO userAccount, DTO dtoParameter) {
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
        String toDate = "";
//        String orgId = "";
        
        int orgId = SyBaseSQLUtil.NULL_INT_VALUE;
        
        boolean hasSqlProduced = false;
        SQLModel sqlModel = new SQLModel();

        final String CONDITION_SQL = "{CONDITION_SQL}";
        try {
            SitusStatisticsDTO Situsdto = (SitusStatisticsDTO) dtoParameter;
            orgId = Situsdto.getOrganizationId();
            List strArg = new ArrayList();
            String strSql = "";
            String tmpASqlStr = " SELECT\n"//按时完成工单
                    + " SU.USERNAME,\n"
                    + " COUNT(EW.SYSTEMID) COUNT1\n"
                    + " FROM \n"
                    + " ETS_WORKORDER  EW,\n"
                    + " SF_USER SU\n"
                    + " WHERE\n"
                    + " CONVERT(VARCHAR,EW.IMPLEMENT_BY) *= CONVERT(VARCHAR,SU.USER_ID) AND\n"
                    + " EW.UPLOAD_DATE <= dbo.AWP_GET_DEADLINE_DATE(EW.START_DATE,EW.IMPLEMENT_DAYS) AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                    + " " + CONDITION_SQL + "\n"
                    + " GROUP BY\n"
                    + " SU.USERNAME\n";

            String tmpBSqlStr = " SELECT\n "//超时完成工单
                    + " SU.USERNAME,\n"
                    + " COUNT(EW.SYSTEMID) COUNT2\n"
                    + " FROM \n"
                    + " ETS_WORKORDER  EW,\n"
                    + " SF_USER SU\n"
                    + " WHERE\n"
                    + " CONVERT(VARCHAR,EW.IMPLEMENT_BY) *= CONVERT(VARCHAR,SU.USER_ID) AND\n"
                    + " EW.UPLOAD_DATE > dbo.AWP_GET_DEADLINE_DATE(EW.START_DATE,EW.IMPLEMENT_DAYS) AND\n"
                    + "  CONVERT(INT,EW.WORKORDER_FLAG) > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                    + "  CONVERT(INT,EW.WORKORDER_FLAG) < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                    + " " + CONDITION_SQL + "\n"
                    + " GROUP BY\n"
                    + " SU.USERNAME\n";

            String tmpCSqlStr = " SELECT\n"//超时未完成工单
                    + " SU.USERNAME,\n"
                    + " COUNT(EW.SYSTEMID) COUNT3\n"
                    + " FROM \n"
                    + " ETS_WORKORDER  EW,\n"
                    + " SF_USER SU\n"
                    + " WHERE\n"
                    + " CONVERT(VARCHAR,EW.IMPLEMENT_BY) *= CONVERT(VARCHAR,SU.USER_ID) AND\n"
                    + " EW.UPLOAD_DATE " + SyBaseSQLUtil.isNullNoParam() + "  AND\n"
                    + " dbo.AWP_GET_DEADLINE_DATE(EW.START_DATE,EW.IMPLEMENT_DAYS) < GETDATE() AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                    + " " + CONDITION_SQL + "\n"
                    + " GROUP BY\n"
                    + " SU.USERNAME\n";

            String tmpDSqlStr = " SELECT\n"//总工单数
                    + " SU.USERNAME,\n"
                    + " COUNT(EW.SYSTEMID) TOTAL_COUNT\n"
                    + " FROM \n"
                    + " ETS_WORKORDER  EW,\n"
                    + " SF_USER SU\n"
                    + " WHERE\n"
                    + " CONVERT(VARCHAR,EW.IMPLEMENT_BY) *= CONVERT(VARCHAR,SU.USER_ID) AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                    + " " + CONDITION_SQL + "\n"
                    + " GROUP BY SU.USERNAME \n";

            String tmpESqlStr = " SELECT\n"//正常处理中工单
                    + " SU.USERNAME,\n"
                    + " COUNT(EW.SYSTEMID) COUNT4\n"
                    + " FROM \n"
                    + " ETS_WORKORDER  EW,\n"
                    + " SF_USER SU\n"
                    + " WHERE\n"
                    + " CONVERT(VARCHAR,EW.IMPLEMENT_BY) *= CONVERT(VARCHAR,SU.USER_ID) AND\n"
                    + " ( ?='' OR EW.UPLOAD_DATE LIKE ?  )AND\n"
                    + " dbo.AWP_GET_DEADLINE_DATE(EW.START_DATE,EW.IMPLEMENT_DAYS) >= GETDATE() AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                    + " CONVERT(INT,EW.WORKORDER_FLAG) < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                    + " " + CONDITION_SQL + "\n"
                    + " GROUP BY\n"
                    + " SU.USERNAME\n";
            strArg.add("");
            strArg.add("");
            String sqlCondit = "";
            if (!StrUtil.isEmpty(Situsdto.getFromDate())) {
                sqlCondit += " AND EW.DISTRIBUTE_DATE >=  '" + Situsdto.getFromDate() + "'\n";
            }
            if (!StrUtil.isEmpty(Situsdto.getToDate())) {
                sqlCondit += " AND dateadd(day,-1,EW.DISTRIBUTE_DATE) <= '" + Situsdto.getFromDate() + "'\n";
            }
            if ( orgId == SyBaseSQLUtil.NULL_INT_VALUE ) {
                sqlCondit += " AND 1<>1\n";
            } else if ( orgId > 0 ) {
                sqlCondit += " AND CONVERT(VARCHAR,EW.ORGANIZATION_ID) =' " + orgId + "'\n";
            }
            String sqlStr = "SELECT\n"
                    + " TMP_D.USERNAME,\n"
                    + " dbo.NVL(TMP_A.COUNT1, 0) IN_TIME_COUNT,\n"
                    + " dbo.NVL(TMP_B.COUNT2, 0) OVER_TIME_COUNT1,\n"
                    + " dbo.NVL(TMP_C.COUNT3, 0) OVER_TIME_COUNT2,"
                    + " dbo.NVL(TMP_E.COUNT4, 0) NORMAL_PROCESS_COUNT,"
                    + " CONVERT(VARCHAR,ROUND((dbo.NVL(TMP_A.COUNT1, 0)/TMP_D.TOTAL_COUNT),2)*100)||'%' RATE\n"
                    + " FROM\n "
                    + " (" + tmpASqlStr + ") TMP_A,\n"
                    + " (" + tmpBSqlStr + ") TMP_B,\n"
                    + " (" + tmpCSqlStr + ") TMP_C,\n"
                    + " (" + tmpDSqlStr + ") TMP_D,\n"
                    + " (" + tmpESqlStr + ") TMP_E\n"
                    + " WHERE\n"
                    + " TMP_D.USERNAME *= TMP_A.USERNAME AND\n"
                    + " TMP_D.USERNAME *= TMP_B.USERNAME AND\n"
                    + " TMP_D.USERNAME *= TMP_C.USERNAME AND\n"
                    + " TMP_D.USERNAME *= TMP_E.USERNAME\n";
            sqlStr = StrUtil.replaceStr(sqlStr, CONDITION_SQL, sqlCondit);
            hasSqlProduced = true;
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(strArg);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }

}
