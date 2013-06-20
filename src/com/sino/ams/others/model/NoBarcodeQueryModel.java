package com.sino.ams.others.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.others.dto.NoBarcodeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-9
 * Time: 16:05:55
 * To change this template use File | Settings | File Templates.
 */
public class NoBarcodeQueryModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public NoBarcodeQueryModel(SfUserDTO userAccount, NoBarcodeDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        NoBarcodeDTO amsItemTransH = (NoBarcodeDTO) dtoParameter;
        String sqlStr = "SELECT ANTL.BATCH_NO,\n" +
                "       ANTL.QUANTITY,\n" +
                "       ANTL.ITEM_NAME,\n" +
                "       ANTL.ITEM_SPEC,\n" +
                "       ANTL.ITEM_UNIT,\n" +
                "       AMS_DEPRECIATION_PKG.GET_NOBARCODE_NOW_QTY(ANTL.BATCH_NO,\n" +
                "                                                  ANTL.ITEM_NAME,\n" +
                "                                                  ANTL.ITEM_SPEC,\n" +
                "                                                  ANTH.TO_OBJECT_NO) NOW_QTY,\n" +
                "       AMS_PUB_PKG.GET_OBJECT_NAME(ANTH.TO_OBJECT_NO) TO_OBJECT_NAME,\n" +
                "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(ANTH.TO_ORGANIZATION_ID) TO_ORGANIZATION_NAME\n" +
                "FROM   AMS_NOBARCODE_TRANS_H ANTH,\n" +
                "       AMS_NOBARCODE_TRANS_L ANTL\n" +
                "WHERE  ANTH.TRANS_ID = ANTL.TRANS_ID\n" +
                "       AND ANTH.TRANS_TYPE = 'FTMRK'\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ANTL.ITEM_NAME LIKE dbo.NVL(?, ANTL.ITEM_NAME))\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ANTL.ITEM_SPEC LIKE dbo.NVL(?, ANTL.ITEM_SPEC))\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR ANTH.TO_OBJECT_NO LIKE dbo.NVL(?, ANTH.TO_OBJECT_NO))\n" +
                "       AND ANTH.TO_ORGANIZATION_ID = ISNULL(?, ANTH.TO_ORGANIZATION_ID)";
        sqlArgs.add(amsItemTransH.getItemName());
        sqlArgs.add(amsItemTransH.getItemName());
        sqlArgs.add(amsItemTransH.getItemSpec());
        sqlArgs.add(amsItemTransH.getItemSpec());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
