package com.sino.ams.system.item.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-22
 */
public class ItemRelationModel extends BaseSQLProducer {
    EtsItemInfoDTO itemInfo = null;

    public ItemRelationModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        itemInfo = (EtsItemInfoDTO) dtoParameter;
    }

    /**
     * 根据barcode查询设备名称和型号
     * @return SQLModel
     */
    public SQLModel getItemInfoByBarcodeModel() {
        SQLModel sqlModel = new SQLModel();
        List list = new ArrayList();
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_UNIT\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.BARCODE = ?";
        list.add(itemInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(list);
        return sqlModel;
    }

    /**
     * 查询父设备的子设备
     * @return SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List list = new ArrayList();
        String sqlStr = "SELECT EII.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ESI.ITEM_UNIT\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_SYSTEM_ITEM ESI\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE\n" +
                "   AND EII.PARENT_BARCODE = ?";
        list.add(itemInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(list);
        return sqlModel;
    }
}
