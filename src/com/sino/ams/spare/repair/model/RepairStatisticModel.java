package com.sino.ams.spare.repair.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.spare.repair.dto.RepairStatisticDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-29
 * Time: 14:50:36
 * To change this template use File | Settings | File Templates.
 */
public class RepairStatisticModel extends AMSSQLProducer {
    private RepairStatisticDTO rapairStatistic = null;
    private SfUserDTO SfUser = null;

    /**
     * 功能：工单主表(AMS) ETS_WORKORDER 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsWorkorderDTO 本次操作的数据
     */
    public RepairStatisticModel(SfUserDTO userAccount, RepairStatisticDTO dtoParameter) {
        super(userAccount, dtoParameter);
        SfUser = userAccount;
        this.rapairStatistic = (RepairStatisticDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";
        if (rapairStatistic.getRepairQuery().equals(DictConstant.REPAIR_STATISTIC_ITEM_NAME)) {
            sqlStr = "SELECT " +
                    "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(AIH.FROM_ORGANIZATION_ID) ORG_NAME,\n"  +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AIL.ITEM_CODE,\n" +
                    "       ROUND((COUNT(1) / ENII.QUANTITY),4)*100 || '%' QUANTITY\n" +
                    "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
                    "       AMS_ITEM_TRANS_H    AIH,\n" +
                    "       AMS_ITEM_TRANS_L    AIL,\n" +
                    "       ETS_NMS_ITEM_INSITE ENII\n" +
                    " WHERE AIH.TRANS_ID = AIL.TRANS_ID\n" +
                    "   AND AIL.ITEM_CODE = ESI.ITEM_CODE\n" +
//                    "   AND AIH.TO_ORGANIZATION_ID = ESI.MASTER_ORGANIZATION_ID\n" +
                    "   AND AIH.TRANS_TYPE = 'BJSX'\n" +
                    "   AND AIH.TRANS_DATE BETWEEN TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
                    "       TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_NAME LIKE ?)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND (? IS NULL OR AIH.FROM_ORGANIZATION_ID = ?)\n" +
                    " GROUP BY AIH.FROM_ORGANIZATION_ID," +
                    "          ESI.ITEM_NAME,\n" +
                    "          ESI.ITEM_SPEC,\n" +
                    "          AIL.ITEM_CODE,\n" +
                    "          ENII.QUANTITY";
            sqlArgs.add(rapairStatistic.getYear() + "-" + rapairStatistic.getMonth() + "-01");
            sqlArgs.add(rapairStatistic.getYear() + "-" + rapairStatistic.getMonth() + "-01");
//          sqlArgs.add(rapairStatistic.getMonth());
//          sqlArgs.add(rapairStatistic.getYear());
            sqlArgs.add(rapairStatistic.getItemName());
            sqlArgs.add(rapairStatistic.getItemName());
            sqlArgs.add(rapairStatistic.getItemSpec());
            sqlArgs.add(rapairStatistic.getItemSpec());
            sqlArgs.add(rapairStatistic.getOrgId());
            sqlArgs.add(rapairStatistic.getOrgId());
        }
        else if (rapairStatistic.getRepairQuery().equals(DictConstant.REPAIR_STATISTIC_VENDOR)) {
            sqlStr = "SELECT " +
                     "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(AIH.FROM_ORGANIZATION_ID) ORG_NAME,\n" +
                    "       EMPV.VENDOR_NAME,\n" +
                    "       ESI.ITEM_NAME,\n" +
                    "       ESI.ITEM_SPEC,\n" +
                    "       AIL.ITEM_CODE,\n" +
                    "       ROUND((COUNT(1) / ENII.QUANTITY),4) * 100 || '%' QUANTITY\n" +
                    "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
                    "       AMS_ITEM_TRANS_H    AIH,\n" +
                    "       AMS_ITEM_TRANS_L    AIL,\n" +
                    "       ETS_MIS_PO_VENDORS  EMPV,\n" +
                    "       ETS_NMS_ITEM_INSITE ENII\n" +
                    " WHERE AIH.TRANS_ID = AIL.TRANS_ID\n" +
                    "   AND AIL.ITEM_CODE = ESI.ITEM_CODE\n" +
//                    "   AND AIH.TO_ORGANIZATION_ID = ESI.MASTER_ORGANIZATION_ID\n" +
                    "   AND ESI.VENDOR_ID = EMPV.VENDOR_ID\n" +
                    "   AND AIH.TRANS_TYPE = 'BJSX'\n" +
                    "   AND AIH.TRANS_DATE BETWEEN TRUNC(TO_DATE(?, 'YYYY-MM-DD'), 'MM') AND\n" +
                    "       TRUNC(LAST_DAY(TO_DATE(?, 'YYYY-MM-DD')) + 1)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_NAME LIKE ?)\n" +
                    "   AND (? IS NULL OR ESI.ITEM_SPEC LIKE ?)\n" +
                    "   AND (? IS NULL OR AIH.FROM_ORGANIZATION_ID = ?)\n" +
                    "   AND (? IS NULL OR EMPV.VENDOR_NAME LIKE ?)\n" +
                    " GROUP BY AIH.FROM_ORGANIZATION_ID," +
                    "          EMPV.VENDOR_NAME,\n" +
                    "          ESI.ITEM_NAME,\n" +
                    "          ESI.ITEM_SPEC,\n" +
                    "          AIL.ITEM_CODE,\n" +
                    "          ENII.QUANTITY";
            sqlArgs.add(rapairStatistic.getYear() + "-" + rapairStatistic.getMonth() + "-01");
            sqlArgs.add(rapairStatistic.getYear() + "-" + rapairStatistic.getMonth() + "-01");
//          sqlArgs.add(rapairStatistic.getMonth());
//          sqlArgs.add(rapairStatistic.getYear());
            sqlArgs.add(rapairStatistic.getItemName());
            sqlArgs.add(rapairStatistic.getItemName());
            sqlArgs.add(rapairStatistic.getItemSpec());
            sqlArgs.add(rapairStatistic.getItemSpec());
            sqlArgs.add(rapairStatistic.getOrgId());
            sqlArgs.add(rapairStatistic.getOrgId());
            sqlArgs.add(rapairStatistic.getVendorName());
            sqlArgs.add(rapairStatistic.getVendorName());
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
