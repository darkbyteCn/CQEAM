package com.sino.nm.spare2.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
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
    private AmsItemTransHDTO headerDto = null;

    public ItemCountByOuModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        headerDto = (AmsItemTransHDTO) dtoParameter;
        sfUser = (SfUserDTO) userAccount;
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

    public SQLModel getSQLModel(String itemCode, String transId) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT EOCM.COMPANY,\n" +
                "       EO.WORKORDER_OBJECT_NO OBJECT_NO,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       ASI.QUANTITY CUR_ONHAND_QTY,\n" +
                "       ASI.ORGANIZATION_ID,\n" +
                "       dbo.APP_GET_ALLOCATE_QTY(ESI.ITEM_CODE, ASI.OBJECT_NO, ?) QUANTITY,\n" +
                "       dbo.APP_GET_OTHERS_RESERVED_QTY(ESI.ITEM_CODE,\n" +
                "                                               ASI.OBJECT_NO,\n" +
                "                                               ?) RESERVED_QTY,\n" +
                "       dbo.APP_GET_DETAIL_ID(ESI.ITEM_CODE, ASI.OBJECT_NO, ?) DETAIL_ID\n" +
                "  FROM AMS_SPARE_INFO  ASI,\n" +
                "       ETS_SYSTEM_ITEM ESI,\n" +
                "       ETS_OU_CITY_MAP EOCM,\n" +
                "       ETS_OBJECT      EO\n" +
                " WHERE ASI.BARCODE = ESI.MIS_ITEM_CODE\n" +
                "   AND ASI.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "   AND ASI.OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND ESI.ITEM_CODE = ?\n";
        List list = new ArrayList();
        list.add(transId);
        list.add(transId);
        list.add(transId);
        list.add(itemCode);
        if (headerDto.getAttribute1().equals("S")) {  //分公司内部申领分配，只分配自己公司仓库里的备件
            sqlStr += " AND ASI.ORGANIZATION_ID = ?";
            list.add(sfUser.getOrganizationId());
        }
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
