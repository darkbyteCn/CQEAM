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
 * Time: 9:29:35
 * To change this template use File | Settings | File Templates.
 */
public class CityCompleteModel extends BaseSQLProducer {
    private SQLModel sqlModel = null;
    private SitusStatisticsDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public CityCompleteModel(SfUserDTO userAccount, DTO dtoParameter) {
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
        String fromData = "";
        String toData = "";
        int orgId = SyBaseSQLUtil.NULL_INT_VALUE;

        boolean hasSqlProduced = false;
        SQLModel sqlModel = new SQLModel();

        final String CONDITION_SQL = "{CONDITION_SQL}";
        try {
             SitusStatisticsDTO Situsdto = (SitusStatisticsDTO)dtoParameter;
             orgId = Situsdto.getOrganizationId();
            List strArg = new ArrayList();
            String strSql = "" ;
            String tmpASqlStr = "SELECT\n"//按时完成工单
                + " EW.ORGANIZATION_ID,\n"
                + " COUNT(*) NCOUNT1\n"
                + " FROM \n"
                + " ETS_WORKORDER EW"
                + " WHERE\n"
                + " EW.UPLOAD_DATE <= AMS_WORKORDER_PKG.GET_DEADLINE_DATE(EW.start_date,EW.implement_days) AND\n"
                + " EW.WORKORDER_FLAG > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                + " EW.WORKORDER_FLAG < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                + " " + CONDITION_SQL + "\n"
                + " GROUP BY\n"
                + " EW.ORGANIZATION_ID\n";

        String tmpBSqlStr = "SELECT\n"//超时完成工单
                + " EW.ORGANIZATION_ID,\n"
                + " COUNT(*) NCOUNT2\n"
                + " FROM \n"
                + " ETS_WORKORDER EW\n"
                + " WHERE\n"
                + " EW.UPLOAD_DATE > AMS_WORKORDER_PKG.GET_DEADLINE_DATE(EW.start_date,EW.implement_days) AND\n"
                + " EW.WORKORDER_FLAG > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                + " EW.WORKORDER_FLAG < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                + " " + CONDITION_SQL + "\n"
                + " GROUP BY\n"
                + " EW.ORGANIZATION_ID\n";

        String tmpCSqlStr = "SELECT\n"//超时未完成工单
                + " EW.ORGANIZATION_ID,\n"
                + " COUNT(*) NCOUNT3\n"
                + " FROM\n"
                + " ETS_WORKORDER EW\n"
                + " WHERE\n"
                + " EW.UPLOAD_DATE " + SyBaseSQLUtil.isNullNoParam() + "  AND\n"
                + " AMS_WORKORDER_PKG.GET_DEADLINE_DATE(EW.start_date,EW.implement_days) < GETDATE() AND\n"
                + " EW.WORKORDER_FLAG > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                + " EW.WORKORDER_FLAG < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                + " " + CONDITION_SQL + "\n"
                + " GROUP BY\n"
                + " EW.ORGANIZATION_ID\n";

        String tmpDSqlStr = "SELECT\n"//总工单数
                + " EW.ORGANIZATION_ID,\n"
                + " COUNT(*) NCOUNT4\n"
                + " FROM\n"
                + " ETS_WORKORDER EW\n"
                + " WHERE\n"
                + " EW.WORKORDER_FLAG > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                + " EW.WORKORDER_FLAG < " +DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                + " " + CONDITION_SQL + "\n"
                + " GROUP BY\n"
                + " EW.ORGANIZATION_ID\n";

        String tmpESqlStr = "SELECT\n"//正常处理中工单数
                + " EW.ORGANIZATION_ID,\n"
                + " COUNT(*) NCOUNT5\n"
                + " FROM\n"
                + " ETS_WORKORDER EW\n"
                + " WHERE\n"
                + " EW.UPLOAD_DATE " + SyBaseSQLUtil.isNullNoParam() + "  AND\n"
                + " AMS_WORKORDER_PKG.GET_DEADLINE_DATE(EW.start_date,EW.implement_days) >= GETDATE() AND\n"
                + " EW.WORKORDER_FLAG > " + DictConstant.WORKORDER_STATUS_NEW + " AND\n"
                + " EW.WORKORDER_FLAG < " + DictConstant.WORKORDER_STATUS_CANCELE + "\n"
                + " " + CONDITION_SQL + "\n"
                + " GROUP BY\n"
                + " EW.ORGANIZATION_ID\n";

        String sqlCondit = "";
        if (!StrUtil.isEmpty(Situsdto.getFromDate())) {
            sqlCondit += " AND EW.DISTRIBUTE_DATE >= '" + Situsdto.getFromDate() + "'\n";
        }
        if (!StrUtil.isEmpty(Situsdto.getToDate())) {
            sqlCondit += " AND dateadd(day,-1,EW.DISTRIBUTE_DATE) <= '" + Situsdto.getToDate() + "'" ;
        }

         String sqlStr = "SELECT\n"
                + " EOCM.COMPANY,\n"
                + " dbo.NVL(TMP_A.NCOUNT1, 0) IN_TIME_COUNT,\n"
                + " dbo.NVL(TMP_B.NCOUNT2, 0) OVER_TIME_COUNT1,\n"
                + " dbo.NVL(TMP_C.NCOUNT3, 0) OVER_TIME_COUNT2,\n"
                + " dbo.NVL(TMP_E.NCOUNT5, 0) NORMAL_PROCESS_COUNT,\n"
               // + " D/ECODE(ROUND(dbo.NVL(TMP_A.NCOUNT1, 0) * 100 / TMP_D.NCOUNT4), NULL, NULL, ROUND(dbo.NVL(TMP_A.NCOUNT1, 0) * 100 / TMP_D.NCOUNT4) || '%') RATE\n"
                + " CASE WHEN ROUND(dbo.NVL(TMP_A.NCOUNT1, 0) * 100 / TMP_D.NCOUNT4)=NULL THEN NULL ELSE ROUND(dbo.NVL(TMP_A.NCOUNT1, 0) * 100 / TMP_D.NCOUNT4) || '%' END RATE "
                + " FROM \n"
                + " ETS_OU_CITY_MAP EOCM,\n"
                + " (" + tmpASqlStr + ") TMP_A,\n"
                + " (" + tmpBSqlStr + ") TMP_B,\n"
                + " (" + tmpCSqlStr + ") TMP_C,\n"
                + " (" + tmpDSqlStr + ") TMP_D,\n"
                + " (" + tmpESqlStr + ") TMP_E\n"
                + " WHERE\n"
                + " EOCM.ORGANIZATION_ID *= TMP_A.ORGANIZATION_ID AND\n"
                + " EOCM.ORGANIZATION_ID *= TMP_B.ORGANIZATION_ID AND\n"
                + " EOCM.ORGANIZATION_ID *= TMP_C.ORGANIZATION_ID AND\n"
                + " EOCM.ORGANIZATION_ID *= TMP_E.ORGANIZATION_ID AND\n"
                + " EOCM.ORGANIZATION_ID *= TMP_D.ORGANIZATION_ID \n";
            sqlStr = StrUtil.replaceStr(sqlStr, CONDITION_SQL, sqlCondit);
        sqlStr += CONDITION_SQL
                + " AND EOCM.IS_TD = 'N'\n" +
                  " ORDER BY\n"
                + " EOCM.COMPANY\n";
        sqlCondit = "";
        if (orgId == SyBaseSQLUtil.NULL_INT_VALUE ) {
            sqlCondit += " AND 1<>1 \n";          
        } else if ( orgId > 0 ) {
            sqlCondit += " AND EOCM.ORGANIZATION_ID = " + orgId;
        }
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
