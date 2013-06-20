package com.sino.ams.system.item.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
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
 *          Date: 2007-11-23
 */
public class SetSubItemsModel extends BaseSQLProducer {
    EtsItemInfoDTO itemInfo = null;

    public SetSubItemsModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        itemInfo = (EtsItemInfoDTO) dtoParameter;
    }

    /**
     * 查询子设备
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
                "   AND EII.PARENT_BARCODE " + SyBaseSQLUtil.isNullNoParam() + " \n" +
                "   AND EII.IS_PARENT = 'N'\n" +
                "   AND EII.BARCODE LIKE dbo.NVL(?, EII.BARCODE)" +
                "   AND ESI.ITEM_NAME LIKE dbo.NVL(?,ESI.ITEM_NAME)" +
                "   AND ESI.ITEM_SPEC LIKE dbo.NVL(?,ESI.ITEM_SPEC)" +
                "   AND EII.ORGANIZATION_ID = ?";
        list.add(itemInfo.getBarcode());
        list.add(itemInfo.getItemName());
        list.add(itemInfo.getItemSpec());
        list.add(((SfUserDTO) userAccount).getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(list);
        return sqlModel;
    }

    /**
     * 更新条码的 PARENT_BARCODE 字段
     * @return SQLModel
     */
    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        List list = new ArrayList();
        String sqlStr = "UPDATE ETS_ITEM_INFO SET PARENT_BARCODE = ?,LAST_UPDATE_DATE = GETDATE(),LAST_UPDATE_BY = ?" +
                "   WHERE BARCODE = ?";
        list.add(itemInfo.getParentBarcode());
        list.add(((SfUserDTO) userAccount).getUserId());
        list.add(itemInfo.getBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(list);
        return sqlModel;
    }
}
