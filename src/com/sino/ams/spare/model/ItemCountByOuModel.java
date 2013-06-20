package com.sino.ams.spare.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-10-31
 */
public class ItemCountByOuModel extends BaseSQLProducer {
    private SfUserDTO sfUser = null;

    public ItemCountByOuModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

     //v1.0版本
    /*public SQLModel getSQLModel(String itemCode,String transId){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EII.ITEM_CODE,\n" +
                "       EII.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       COUNT(1) ONHAND_QTY,\n" +
                "       AMS_ITEM_TRANS.GET_ALLOCATE_QTY(EII.ITEM_CODE,\n" +
                "                                       EII.ORGANIZATION_ID,\n" +
                "                                       ?) QUANTITY,\n" +
                "       AMS_ITEM_TRANS.GET_OTHERS_RESERVED_QTY(EII.ITEM_CODE,\n" +
                "                                              EII.ORGANIZATION_ID,\n" +
                "                                              ?) RESERVED_QTY,\n" +
                "       AMS_ITEM_TRANS.GET_DETAIL_ID(EII.ITEM_CODE, EII.ORGANIZATION_ID, ?) DETAIL_ID\n" +
                "  FROM ETS_ITEM_INFO EII, ETS_OU_CITY_MAP EOCM\n" +
                " WHERE EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND EII.ITEM_CODE = ?\n" +
                " GROUP BY EII.ITEM_CODE, EII.ORGANIZATION_ID, EOCM.COMPANY";
        List list = new ArrayList();
        list.add(transId);
        list.add(transId);
        list.add(transId);
        list.add(itemCode);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }*/

    public SQLModel getSQLModel(String itemCode,String transId){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT AMSC.ITEM_CODE,\n" +
                "       ASI.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       ASI.QUANTITY - ASI.RESERVE_QUANTITY ONHAND_QTY,\n" +
                "       AMS_ITEM_TRANS.GET_ALLOCATE_QTY(AMSC.ITEM_CODE,\n" +
                "                                       ASI.ORGANIZATION_ID,\n" +
                "                                       ?) QUANTITY,\n" +
                "       AMS_ITEM_TRANS.GET_OTHERS_RESERVED_QTY(AMSC.ITEM_CODE,\n" +
                "                                              ASI.ORGANIZATION_ID,\n" +
                "                                              ?) RESERVED_QTY,\n" +
                "       AMS_ITEM_TRANS.GET_DETAIL_ID(AMSC.ITEM_CODE,\n" +
                "                                    ASI.ORGANIZATION_ID,\n" +
                "                                    ?) DETAIL_ID\n" +
                "  FROM AMS_SPARE_INFO ASI, ETS_OU_CITY_MAP EOCM, AMS_SPARE_CATEGORY AMSC\n" +
                " WHERE ASI.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND ASI.BARCODE = AMSC.BARCODE\n" +
                "   AND AMSC.ITEM_CODE = ?\n" ;
        List list = new ArrayList();
        list.add(transId);
        list.add(transId);
        list.add(transId);
        list.add(itemCode);
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
