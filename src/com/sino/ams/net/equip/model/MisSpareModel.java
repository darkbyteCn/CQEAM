package com.sino.ams.net.equip.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.net.equip.dto.ItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2008-4-17
 * Time: 10:37:49
 * To change this template use File | Settings | File Templates.
 */
public class MisSpareModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public MisSpareModel(SfUserDTO userAccount, ItemInfoDTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
    }

    /**
     * 功能：框架自动生成ITEM_INFO页面翻页查询SQLModel，请根据实际需要修改。
     *
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws com.sino.base.exception.SQLModelException
     *          发生日历异常时转化为该异常抛出
     */
    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        ItemInfoDTO itemInfo = (ItemInfoDTO) dtoParameter;
        String sqlStr = " SELECT " +
                " EII.BARCODE,\n" +
                " EII.FA_BARCODE,\n" +
                " ESI.ITEM_NAME, \n" +
                " ESI.ITEM_SPEC, \n" +
                " EO.WORKORDER_OBJECT_LOCATION, \n" +
                " EFA.ASSETS_DESCRIPTION,\n" +
                " EFA.ASSETS_LOCATION\n" +
                " FROM ETS_SYSTEM_ITEM ESI, \n" +
                " AMS_OBJECT_ADDRESS AOA, \n" +
                " ETS_OBJECT EO, \n" +
                " ETS_ITEM_INFO EII, \n" +
                " ETS_FA_ASSETS EFA\n" +
                " WHERE EII.ITEM_CODE = ESI.ITEM_CODE \n" +
                " AND EII.ADDRESS_ID = AOA.ADDRESS_ID \n" +
                " AND AOA.OBJECT_NO = EO.WORKORDER_OBJECT_NO \n" +
                " AND EII.FA_BARCODE = EFA.TAG_NUMBER \n" +
                " AND  " + SyBaseSQLUtil.isNotNull("EII.FA_BARCODE") + " " +
                " AND EII.ORGANIZATION_ID = ? \n" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.BARCODE = ?)" +
                " AND ( " + SyBaseSQLUtil.isNull() + "  OR EII.FA_BARCODE = ?)";

        sqlArgs.add(sfUser.getOrganizationId());
        sqlArgs.add(itemInfo.getBarcode());
        sqlArgs.add(itemInfo.getBarcode());
        sqlArgs.add(itemInfo.getFaBarcode());
        sqlArgs.add(itemInfo.getFaBarcode());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
