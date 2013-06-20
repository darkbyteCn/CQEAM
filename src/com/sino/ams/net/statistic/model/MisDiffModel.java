package com.sino.ams.net.statistic.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.net.statistic.dto.MisDiffDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-4-11
 * Time: 1:56:44
 * To change this template use File | Settings | File Templates.
 */
public class MisDiffModel extends BaseSQLProducer {
      public  SfUserDTO sfUser = null;


    /**
     * 功能：EQUIP_STAT 数据库SQL构造层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EquipStatDTO 本次操作的数据
     */
    public MisDiffModel(SfUserDTO userAccount, MisDiffDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        MisDiffDTO misDiff = (MisDiffDTO) dtoParameter;
        String sqlStr = "SELECT ASI.QUANTITY + ASI.DISREPAIR_QUANTITY + ASI.REPAIR_QUANTITY +\n" +
                "       ASI.SCRAP_QUANTITY + ASI.LOAN_QUANTITY EAM_QUANTITY, \n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       ESI.MIS_ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC, \n" +
                "       EMIQ.QUANTITY, \n" +
                "       EOCM.COMPANY \n" +
                "  FROM AMS_SPARE_INFO      ASI,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_OBJECT          EO,\n" +
                "       ETS_MIS_LOCATION_MATCH EMLM,\n" +
                "       ETS_MIS_INV_QUNTITY EMIQ,\n" +
                "       ETS_OU_CITY_MAP     EOCM\n" +
                " WHERE ASI.BARCODE = ESI.MIS_ITEM_CODE\n" +
                "   AND EMIQ.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND EO.WORKORDER_OBJECT_NO = EMLM.WORKORDER_OBJECT_NO\n" +
                "   AND EMLM.ASSETS_LOCATION = EMIQ.ASSETS_LOCATION\n" +
                "   AND ASI.ORGANIZATION_ID = EMIQ.ORGANIZATION_ID\n" +
                "   AND EMIQ.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.MIS_ITEM_CODE LIKE ?)  \n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EMIQ.ORGANIZATION_ID LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_SPEC LIKE ?)\n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?) \n" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_NAME LIKE ?)";
        sqlArgs.add(misDiff.getMisItemCode());
        sqlArgs.add(misDiff.getMisItemCode());
        sqlArgs.add(misDiff.getOranizationId());
        sqlArgs.add(misDiff.getOranizationId());
        sqlArgs.add(misDiff.getItemSpec());
        sqlArgs.add(misDiff.getItemSpec());
        sqlArgs.add(misDiff.getWorkorderObjectName());
        sqlArgs.add(misDiff.getWorkorderObjectName());
        sqlArgs.add(misDiff.getItemName());
        sqlArgs.add(misDiff.getItemName());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
