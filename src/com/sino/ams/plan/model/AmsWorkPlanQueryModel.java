package com.sino.ams.plan.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-9-21
 * Time: 14:50:07
 * To change this template use File | Settings | File Templates.
 */
public class AmsWorkPlanQueryModel extends BaseSQLProducer {
    private AmsWorkPlanDTO amsWorkPlan = null;
    private SfUserDTO SfUser = null;

    public AmsWorkPlanQueryModel(SfUserDTO userAccount, AmsWorkPlanDTO dtoParameter) {
        super(userAccount, dtoParameter);
        SfUser = userAccount;
        this.amsWorkPlan = (AmsWorkPlanDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AWP.PLAN_ID,\n" +
                "       AWP.PLAN_NAME,\n" +
                "       EFV.VALUE PLAN_STATUS,\n" +
                "       AWP.CREATION_DATE,\n" +
                "       SU.USERNAME EXECUTE_USER,\n" +
                "       SU1.USERNAME CREATED_BY,\n" +
                "       AWP.EXECUTE_TIME\n" +
                "  FROM AMS_WORK_PLAN AWP, SF_USER SU, SF_USER SU1, ETS_FLEX_VALUES EFV\n" +
                " WHERE SU.USER_ID = AWP.EXECUTE_USER\n" +
                "   AND SU1.USER_ID = AWP.CREATED_BY\n" +
                "   AND AWP.PLAN_NAME LIKE dbo.NVL(?, AWP.PLAN_NAME)\n" +
                "   AND AWP.PLAN_STATUS = dbo.NVL(?, AWP.PLAN_STATUS)\n" +
                "   AND AWP.EXECUTE_USER = CONVERT(INT, dbo.NVL(?, CONVERT(VARCHAR, AWP.EXECUTE_USER)))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AWP.CREATION_DATE >= TO_DATE(?, 'YYYY-MM-DD'))\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR AWP.CREATION_DATE <= TO_DATE(?, 'YYYY-MM-DD'))\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = 18\n" +
                "   AND EFV.CODE = AWP.PLAN_STATUS";
        sqlArgs.add(amsWorkPlan.getPlanName());
        sqlArgs.add(amsWorkPlan.getPlanStatus());
        sqlArgs.add(amsWorkPlan.getExecuteUser());
        sqlArgs.add(amsWorkPlan.getFromDate());
        sqlArgs.add(amsWorkPlan.getFromDate());
        sqlArgs.add(amsWorkPlan.getToDate());
        sqlArgs.add(amsWorkPlan.getToDate());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AWP.PLAN_ID,\n" +
                "       AWP.PLAN_NAME,\n" +
                "       AWP.PLAN_DESC,\n" +
                "       AWP.EXECUTE_TIME,\n" +
                "       SU.USER_ID EXECUTE_USER,\n" +
                "       SU.USERNAME EXECUTE_USER_NAME,\n" +
                "       AWP.PLAN_STATUS\n" +
                "  FROM AMS_WORK_PLAN AWP, SF_USER SU\n" +
                " WHERE SU.USER_ID = AWP.EXECUTE_USER\n" +
                "   AND AWP.PLAN_ID = ?";
        sqlArgs.add(amsWorkPlan.getPlanId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
