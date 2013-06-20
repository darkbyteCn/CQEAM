package com.sino.nm.spare2.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-3-27
 */
public class BjckModel extends BaseSQLProducer {
    public BjckModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 备件出库行信息
     * @param transId 行信息
     * @return SQLModel
     */
    public SQLModel getDataByForeignKeyModel(String transId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITL.LINE_ID,\n" +
                "       AITL.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_UNIT,\n" +
                "       AITL.QUANTITY,\n" +
                "       dbo.AMS_INV_TRANS2_GET_AVAILABLE_QTY(ESI.ITEM_CODE,\n" +
                "                                        AITH.FROM_OBJECT_NO) ONHAND_QUANTITY,\n" +  //这里表示可用量
                "       AITL.STORAGE_ID,\n" +
                "       AITL.BATCH_NO\n" +
                "  FROM AMS_ITEM_TRANS_H AITH, AMS_ITEM_TRANS_L AITL, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE AITH.TRANS_ID = AITL.TRANS_ID\n" +
                "   AND AITL.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND AITH.TRANS_ID = ?";
//		sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(transId);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
