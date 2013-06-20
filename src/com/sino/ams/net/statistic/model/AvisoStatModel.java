package com.sino.ams.net.statistic.model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.net.statistic.dto.AvisoStatDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: V-yuanshuai
 * Date: 2007-11-14
 * Time: 15:27:30
 * To change this template use File | Settings | File Templates.
 */
public class AvisoStatModel extends BaseSQLProducer {

    private SfUserDTO sfUser = null;


    /**
     * 功能：EQUIP_STAT 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EquipStatDTO 本次操作的数据
     */
    public AvisoStatModel(SfUserDTO userAccount, AvisoStatDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成EQUIP_STAT页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getTimeDistance() {
        SQLModel sqlModel = new SQLModel();
        AvisoStatDTO avisoStat = (AvisoStatDTO) dtoParameter;
        String fullTime = avisoStat.getYear() + "-" + avisoStat.getMonth() + "-01";
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT TRUNC(to_date(?,'yyyy-mm-dd'),'MM')  fir_week,\n" +
                "        NEXT_DAY(TRUNC(to_date(?,'yyyy-mm-dd'),'MM'),1)+7*0  sec_week,\n" +
                "        NEXT_DAY(TRUNC(to_date(?,'yyyy-mm-dd'),'MM'),1)+7*1 thr_week,\n" +
                "        NEXT_DAY(TRUNC(to_date(?,'yyyy-mm-dd'),'MM'),1)+7*2 fou_week,\n" +
                "        NEXT_DAY(TRUNC(to_date(?,'yyyy-mm-dd'),'MM'),1)+7*3 fiv_week,\n" +
                "        NEXT_DAY(TRUNC(to_date(?,'yyyy-mm-dd'),'MM'),1)+7*4 six_week,\n" +
                "        TRUNC(LAST_DAY(to_date(?,'yyyy-mm-dd'))+1) last_day\n" +
                " ";
        sqlArgs.add(fullTime);
        sqlArgs.add(fullTime);
        sqlArgs.add(fullTime);
        sqlArgs.add(fullTime);
        sqlArgs.add(fullTime);
        sqlArgs.add(fullTime);
        sqlArgs.add(fullTime);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：框架自动生成EQUIP_STAT页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        AvisoStatDTO avisoStat = (AvisoStatDTO) dtoParameter;
        String qryType = avisoStat.getQryType();
//10	新建工单
//11	扩容工单
//12	巡检工单
//13	维修工单
//14	搬迁工单
//15	减容工单
//16	替换工单
        if (qryType.equals(WebAttrConstant.BY_CHECK)) {

            sqlStr =
                    "SELECT EM.COMPANY ORGANIZATION_NAME,  \n" +
                    "  EEO.E1, \n" +
                    "   CASE WHEN TRUNC(100 * EEO.E1/EOC.CNT)=0 THEN STR_REPLACE(ROUND(100 * EEO.E1/EOC.CNT, 2), '.', '0.') ELSE STR(ROUND(100 * EEO.E1/EOC.CNT, 2))) || '%' END P1,EEO.E2,"+
                    "   CASE WHEN TRUNC(100 * EEO.E2/EOC.CNT)=0 THEN STR_REPLACE(ROUND(100 * EEO.E2/EOC.CNT, 2), '.', '0.') ELSE STR(ROUND(100 * EEO.E2/EOC.CNT, 2))) || '%' END P2,EEO.E3,"+
                    "   CASE WHEN TRUNC(100 * EEO.E3/EOC.CNT)=0 THEN STR_REPLACE(ROUND(100 * EEO.E3/EOC.CNT, 2), '.', '0.') ELSE STR(ROUND(100 * EEO.E3/EOC.CNT, 2))) || '%' END P3,EEO.E4,"+
                    "   CASE WHEN TRUNC(100 * EEO.E4/EOC.CNT)=0 THEN STR_REPLACE(ROUND(100 * EEO.E4/EOC.CNT, 2), '.', '0.') ELSE STR(ROUND(100 * EEO.E4/EOC.CNT, 2))) || '%' END P4,EEO.E5,"+
                    "   CASE WHEN TRUNC(100 * EEO.E5/EOC.CNT)=0 THEN STR_REPLACE(ROUND(100 * EEO.E5/EOC.CNT, 2), '.', '0.') ELSE STR(ROUND(100 * EEO.E5/EOC.CNT, 2))) || '%' END P5,EEO.EL6,"+
                    "   CASE WHEN TRUNC(100 * EEO.EL6/EOC.CNT)=0 THEN STR_REPLACE(ROUND(100 * EEO.EL6/EOC.CNT, 2), '.', '0.') ELSE STR(ROUND(100 * EEO.EL6/EOC.CNT, 2))) || '%' END P6,EOC.CNT"+
                  
                    " FROM \n" +
                    "(SELECT  " +
                    " SUM(CASE WHEN ODC.CNT=1 THEN 1 ELSE 0 END ) E1,"+
                    " SUM(CASE WHEN ODC.CNT=2 THEN 1 ELSE 0 END ) E2 ," +
                    " SUM(CASE WHEN ODC.CNT=3 THEN 1 ELSE 0 END ) E3 ," +
                    " SUM(CASE WHEN ODC.CNT=4 THEN 1 ELSE 0 END ) E4 ," +
                    " SUM(CASE WHEN ODC.CNT=5 THEN 1 ELSE 0 END ) E5 ," +
                    " SUM(CASE WHEN ODC.CNT=1 OR ODC.CNT=2 OR ODC.CNT=3 OR  ODC.CNT=4 OR ODC.CNT=5 THEN 0 ELSE 1 END ) EL6 ," +
                    "ODC.ORGANIZATION_ID\n" +
                    "FROM (\n" +
                    "       SELECT COUNT(EW.WORKORDER_OBJECT_NO) CNT,\n" +
                    "      \t\t\t\tEW.WORKORDER_OBJECT_NO,\n" +
                    "      \t\t\t\tEW.ORGANIZATION_ID\n" +
                    "       FROM   ETS_WORKORDER EW,ETS_OBJECT eo\n" +
                    "       WHERE  EW.UPLOAD_DATE > TRUNC(TO_DATE(?,'YYYY'),'YYYY') \n" +
                    "         AND  EW.UPLOAD_DATE < ADD_MONTHS(TRUNC(TO_DATE(?,'YYYY'),'YYYY'),12) \n" +
                    "         AND  EW.WORKORDER_TYPE = ?\n" +
                    "         AND ew.WORKORDER_OBJECT_NO=eo.WORKORDER_OBJECT_NO\n" +
                    "         AND eo.OBJECT_CATEGORY = ?\n" +
                    "       GROUP  BY EW.WORKORDER_OBJECT_NO,\n" +
                    "      \t\t\t\t\t EW.ORGANIZATION_ID) ODC\n" +
                    " GROUP BY ODC.ORGANIZATION_ID) EEO  ,\n" +
                    " ( SELECT COUNT(*) CNT, EO.ORGANIZATION_ID FROM ETS_OBJECT EO \n" +
                    " WHERE eo.OBJECT_CATEGORY = ?\n" +
                    " GROUP BY EO.ORGANIZATION_ID) EOC,\n" +
                    " ETS_OU_CITY_MAP EM\n" +
                    " WHERE EEO.ORGANIZATION_ID  = EOC.ORGANIZATION_ID\n" +
                    " AND  EOC.ORGANIZATION_ID = EM.ORGANIZATION_ID";
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(DictConstant.ORDER_TYPE_CHECK);
            sqlArgs.add(DictConstant.NETADDR_BTS);
            sqlArgs.add(DictConstant.NETADDR_BTS);

        } else if (qryType.equals(WebAttrConstant.BY_MONTH)) {
            int n = avisoStat.getWeekCount();
            Date firDayOfWeek[] = avisoStat.getFirDayOfWeek();
            Date lasDayOfWeek[] = avisoStat.getLasDayOfWeek();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sqlStr = " SELECT WOC.*,\n" +
                    "        EM.COMPANY  ORGANIZATION_NAME\n" +
                    " FROM  (SELECT \n" +
                    "       ? MONTH_NO,\n" +
                    "       ? WEEK_NO,\n" +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=18 THEN 1 ELSE 0 END ) T1 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=12 THEN 1 ELSE 0 END ) T2 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=13 THEN 1 ELSE 0 END ) T3 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=14 THEN 1 ELSE 0 END ) T4 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=17 THEN 1 ELSE 0 END ) T5 ," +
                    "       0 T6,\n" +      //报废
                    "       COUNT(1) SUM,\n" +
                    "       EW.ORGANIZATION_ID\n" +
                    "       FROM ETS_WORKORDER EW     \n" +
                    "       WHERE EW.UPLOAD_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') \n" +
                    "       AND EW.WORKORDER_TYPE IN (18,12,13,14,17) \n"   +
                    "       GROUP BY EW.ORGANIZATION_ID  ) WOC,\n" +
                    "       ETS_OU_CITY_MAP EM\n" +
                    " WHERE WOC.ORGANIZATION_ID =EM.ORGANIZATION_ID \n";
            sqlArgs.add(avisoStat.getMonth());
            avisoStat.setWeekNo(1);
            sqlArgs.add(String.valueOf(avisoStat.getWeekNo()));
            sqlArgs.add(sdf.format(firDayOfWeek[0]));
            sqlArgs.add(sdf.format(lasDayOfWeek[0]));
            for (int i = 1; i < n; i++) {
                sqlStr += " UNION " +
                        " SELECT WOC.*,\n" +
                        "        EM.COMPANY  ORGANIZATION_NAME\n" +
                        " FROM  (SELECT \n" +
                        "       ? MONTH_NO,\n" +
                        "       ? WEEK_NO,\n" +
                        " SUM(CASE WHEN EW.WORKORDER_TYPE=18 THEN 1 ELSE 0 END ) T1 ," +
                        " SUM(CASE WHEN EW.WORKORDER_TYPE=12 THEN 1 ELSE 0 END ) T2 ," +
                        " SUM(CASE WHEN EW.WORKORDER_TYPE=13 THEN 1 ELSE 0 END ) T3 ," +
                        " SUM(CASE WHEN EW.WORKORDER_TYPE=14 THEN 1 ELSE 0 END ) T4 ," +
                        " SUM(CASE WHEN EW.WORKORDER_TYPE=17 THEN 1 ELSE 0 END ) T5 ," +
                        "       0 T6,\n" +
                        "       COUNT(*)  SUM,\n" +
                        "       EW.ORGANIZATION_ID\n" +
                        "       FROM ETS_WORKORDER EW     \n" +
                        "       WHERE EW.UPLOAD_DATE BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') \n" +
                        "       AND EW.WORKORDER_TYPE IN (18,12,13,14,17) \n"   +
                        "       GROUP BY EW.ORGANIZATION_ID  ) WOC,\n" +
                        "       ETS_OU_CITY_MAP EM\n" +
                        " WHERE WOC.ORGANIZATION_ID =EM.ORGANIZATION_ID \n";
                sqlArgs.add(avisoStat.getMonth());
                sqlArgs.add(String.valueOf(avisoStat.getWeekNo()));
                sqlArgs.add(sdf.format(firDayOfWeek[i]));
                sqlArgs.add(sdf.format(lasDayOfWeek[i]));
            }
            sqlStr = "SELECT T.* FROM ( " + sqlStr + " ) T ORDER BY ORGANIZATION_NAME ,WEEK_NO ";
        } else if (qryType.equals(WebAttrConstant.BY_YEAR)) {
            sqlStr = "    SELECT  EM.COMPANY ORGANIZATION_NAME,\n" +
                    "            EWT.* \n" +
                    "     FROM (   \n" +
                    "       SELECT  \n" +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=18 THEN 1 ELSE 0 END ) T1 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=12 THEN 1 ELSE 0 END ) T2 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=13 THEN 1 ELSE 0 END ) T3 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=14 THEN 1 ELSE 0 END ) T4 ," +
                    " SUM(CASE WHEN EW.WORKORDER_TYPE=17 THEN 1 ELSE 0 END ) T5 ," +
                    "       0 T6,\n" + //报废
                    "       COUNT(EW.WORKORDER_TYPE) SUM,\n" +
                    "       EW.ORGANIZATION_ID\n" +
                    "       FROM   ETS_WORKORDER EW    \n" +
                    "       WHERE  EW.UPLOAD_DATE > TRUNC(TO_DATE(?,'YYYY'),'YYYY')\n" +
                    "       AND    EW.UPLOAD_DATE < ADD_MONTHS(TRUNC(TO_DATE(?,'YYYY'),'YYYY'),12) \n" +
                    "       AND EW.WORKORDER_TYPE IN (18,12,13,14,17) \n"   +
                    "       GROUP BY EW.ORGANIZATION_ID  ) EWT,\n" +
                    "       ETS_OU_CITY_MAP EM\n" +
                    "       WHERE EWT.ORGANIZATION_ID = EM.ORGANIZATION_ID";
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(avisoStat.getYear());
        } else if (qryType.equals(WebAttrConstant.BY_TIME)) {
            sqlStr = "\n" +
                    "SELECT EM.COMPANY ORGANIZATION_NAME,\n" +
                    "       ET.*\n" +
                    "FROM (SELECT dbo.NVL(TIME1.CNT,0) T1,\n" +
                    "       ROUND(dbo.NVL(TIME1.CNT,0)/(dbo.NVL(TIME1.CNT,0)+dbo.NVL(TIME2.CNT,0)+dbo.NVL(TIME3.CNT,0)),4)*100||'%' P1,\n" +
                    "       dbo.NVL(TIME2.CNT,0) T2,\n" +
                    "       ROUND(dbo.NVL(TIME2.CNT,0)/(dbo.NVL(TIME1.CNT,0)+dbo.NVL(TIME2.CNT,0)+dbo.NVL(TIME3.CNT,0)),4)*100||'%' P2,\n" +
                    "       dbo.NVL(TIME3.CNT,0) T3,\n" +
                    "       ROUND(dbo.NVL(TIME3.CNT,0)/(dbo.NVL(TIME1.CNT,0)+dbo.NVL(TIME2.CNT,0)+dbo.NVL(TIME3.CNT,0)),4)*100||'%' P3,\n" +
         //          "       dbo.NVL(TIME1.CNT,0)+dbo.NVL(TIME2.CNT,0)+dbo.NVL(TIME3.CNT,0) SUM,\n" +
                    "       TIME1.ORGANIZATION_ID\n" +
                    "FROM  \n" +
                    "(SELECT COUNT(*) CNT,\n" +
                    "        EW.ORGANIZATION_ID\n" +
                    "        FROM ETS_WORKORDER EW \n" +
                    "WHERE  EW.UPLOAD_DATE  -TRUNC( EW.UPLOAD_DATE) >0.3\n" +
                    "  AND  EW.UPLOAD_DATE  -TRUNC( EW.UPLOAD_DATE) <0.75\n" +
                    "  AND  EW.UPLOAD_DATE > TRUNC(TO_DATE(?,'YYYY'),'YYYY')\n" +
                    "  AND    EW.UPLOAD_DATE < ADD_MONTHS(TRUNC(TO_DATE(?,'YYYY'),'YYYY'),12) \n" +
                    "GROUP BY EW.ORGANIZATION_ID ) TIME1,\n" +
                    "\n" +
                    "(SELECT COUNT(*) CNT,\n" +
                    "        EW.ORGANIZATION_ID\n" +
                    "        FROM ETS_WORKORDER EW \n" +
                    "WHERE  EW.UPLOAD_DATE  -TRUNC( EW.UPLOAD_DATE) >0.75\n" +
                    "  AND  EW.UPLOAD_DATE  -TRUNC( EW.UPLOAD_DATE) <0.99\n" +
                    "   AND  EW.UPLOAD_DATE > TRUNC(TO_DATE(?,'YYYY'),'YYYY')\n" +
                    "  AND    EW.UPLOAD_DATE < ADD_MONTHS(TRUNC(TO_DATE(?,'YYYY'),'YYYY'),12) \n" +
                    "GROUP BY EW.ORGANIZATION_ID ) TIME2,\n" +
                    "\n" +
                    "(SELECT COUNT(*) CNT,\n" +
                    "        EW.ORGANIZATION_ID\n" +
                    "        FROM ETS_WORKORDER EW \n" +
                    "WHERE  EW.UPLOAD_DATE  -TRUNC( EW.UPLOAD_DATE) >0\n" +
                    "  AND  EW.UPLOAD_DATE  -TRUNC( EW.UPLOAD_DATE) <0.33\n" +
                    "   AND  EW.UPLOAD_DATE > TRUNC(TO_DATE(?,'YYYY'),'YYYY')\n" +
                    "  AND    EW.UPLOAD_DATE < ADD_MONTHS(TRUNC(TO_DATE(?,'YYYY'),'YYYY'),12) \n" +
                    "GROUP BY EW.ORGANIZATION_ID ) TIME3\n" +
                    "WHERE TIME1.ORGANIZATION_ID *= TIME2.ORGANIZATION_ID\n" +
                    " AND  TIME1.ORGANIZATION_ID *= TIME3.ORGANIZATION_ID) ET,\n" +
                    " ETS_OU_CITY_MAP EM \n" +
                    " WHERE EM.ORGANIZATION_ID =ET.ORGANIZATION_ID";
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(avisoStat.getYear());
            sqlArgs.add(avisoStat.getYear());
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
