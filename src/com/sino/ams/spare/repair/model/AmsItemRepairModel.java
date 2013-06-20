package com.sino.ams.spare.repair.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Sunny song
 * Date: 2007-11-16
 * Time: 6:02:42
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemRepairModel extends BaseSQLProducer {
    private SfUserDTO SfUser = null;
    private EtsSystemItemDTO amsrepairDto = null;

    /**
     * 功能：设备维修记录的数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsWorkPlanDTO 本次操作的数据
     */
    public AmsItemRepairModel(SfUserDTO userAccount, EtsSystemItemDTO dtoParameter) {
        super(userAccount, dtoParameter);
        SfUser = userAccount;
        this.amsrepairDto = (EtsSystemItemDTO) dtoParameter;
    }

    /**
     * 功能 根据时间或设备名查询修理的次数
     *
     * @return SQLModel 返回数据用SQLModel
     *         Date   2007-11-16
     *         time   15:16
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "";
            if (amsrepairDto.getRepairQuery().equals(DictConstant.ITEM_REPAIR_QUERY)) {
                sqlStr = "SELECT \n" +
                        "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(AIH.FROM_ORGANIZATION_ID) ORG_NAME,\n" +
                        "       ESI.ITEM_NAME,\n" +
                        "       ESI.ITEM_SPEC,\n" +
                        "       AIL.ITEM_CODE,\n" +
                        "       COUNT(1) QUANTITY\n" +
                        "  FROM ETS_SYSTEM_ITEM ESI, AMS_ITEM_TRANS_H AIH, AMS_ITEM_TRANS_L AIL\n" +
                        " WHERE AIH.TRANS_ID = AIL.TRANS_ID\n" +
                        "   AND AIL.ITEM_CODE = ESI.ITEM_CODE\n" +
                        "   AND AIH.TRANS_TYPE = 'BJSX'\n" +
                        "   AND (? IS NULL OR AIH.TRANS_DATE >= ?)\n" +
                        "   AND (? IS NULL OR AIH.TRANS_DATE <= ?)\n" +
                        "   AND (? IS NULL OR ESI.ITEM_NAME LIKE ?)\n" +
                        "   AND (? IS NULL OR ESI.ITEM_SPEC LIKE ?)\n" +
                        "\t AND (? IS NULL OR AIH.FROM_ORGANIZATION_ID = ?)\n" +
                        " GROUP BY AIH.FROM_ORGANIZATION_ID," +
                        "ESI.ITEM_NAME,\n" +
                        "\t\t\t\t\tESI.ITEM_SPEC,\n" +
                        "\t\t\t\t\tAIL.ITEM_CODE";
                sqlArgs.add(amsrepairDto.getFromDate());
                sqlArgs.add(amsrepairDto.getFromDate());
                sqlArgs.add(amsrepairDto.getToDate());
                sqlArgs.add(amsrepairDto.getToDate());
                sqlArgs.add(amsrepairDto.getItemName());
                sqlArgs.add(amsrepairDto.getItemName());
                sqlArgs.add(amsrepairDto.getItemSpec());
                sqlArgs.add(amsrepairDto.getItemSpec());
                sqlArgs.add(amsrepairDto.getOrgId());
                sqlArgs.add(amsrepairDto.getOrgId());
            } else if (amsrepairDto.getRepairQuery().equals(DictConstant.VENTOR_REPAIR_QUERY)) {
                sqlStr = "SELECT " +
                         "      AMS_PUB_PKG.GET_ORGNIZATION_NAME(AIH.FROM_ORGANIZATION_ID) ORG_NAME,\n" +
                        "       EMPV.VENDOR_NAME,\n" +
                        "\t\t\t ESI.ITEM_NAME,\n" +
                        "\t\t\t ESI.ITEM_SPEC,\n" +
                        "\t\t\t ESI.ITEM_CODE,\n" +
                        "\t\t\t COUNT(1) QUANTITY\n" +
                        "\tFROM ETS_SYSTEM_ITEM ESI,\n" +
                        "\t\t\t AMS_ITEM_TRANS_H AIH,\n" +
                        "\t\t\t AMS_ITEM_TRANS_L AIL,\n" +
                        "\t\t\t ETS_MIS_PO_VENDORS EMPV\n" +
                        " WHERE AIH.TRANS_ID = AIL.TRANS_ID\n" +
                        "\t AND AIL.ITEM_CODE = ESI.ITEM_CODE\n" +
                        "\t AND ESI.VENDOR_ID = EMPV.VENDOR_ID\n" +
                        "\t AND AIH.TRANS_TYPE = 'BJSX'\n" +
                        "   AND (? IS NULL OR AIH.TRANS_DATE >= ?)\n" +
                        "   AND (? IS NULL OR AIH.TRANS_DATE <= ?)\n" +
                        "\t AND (? IS NULL OR ESI.ITEM_NAME LIKE ?)\n" +
                        "\t AND (? IS NULL OR ESI.ITEM_SPEC LIKE ?)\n" +
                        "\t AND (? IS NULL OR EMPV.VENDOR_NAME LIKE ?)\n" +
                        "\t AND (? IS NULL OR AIH.FROM_ORGANIZATION_ID = ?)\n" +
                        " GROUP BY AIH.FROM_ORGANIZATION_ID," +
                        "EMPV.VENDOR_NAME,\n" +
                        "\t\t\t\t\tESI.ITEM_NAME,\n" +
                        "\t\t\t\t\tESI.ITEM_SPEC,\n" +
                        "\t\t\t\t\tESI.ITEM_CODE";
                sqlArgs.add(amsrepairDto.getFromDate());
                sqlArgs.add(amsrepairDto.getFromDate());
                sqlArgs.add(amsrepairDto.getToDate());
                sqlArgs.add(amsrepairDto.getToDate());
                sqlArgs.add(amsrepairDto.getItemName());
                sqlArgs.add(amsrepairDto.getItemName());
                sqlArgs.add(amsrepairDto.getItemSpec());
                sqlArgs.add(amsrepairDto.getItemSpec());
                sqlArgs.add(amsrepairDto.getVendorName());
                sqlArgs.add(amsrepairDto.getVendorName());
                sqlArgs.add(amsrepairDto.getOrgId());
                sqlArgs.add(amsrepairDto.getOrgId());
            }
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException e) {
            e.printLog();
            throw new SQLModelException(e);
        }
        return sqlModel;


    }
}
