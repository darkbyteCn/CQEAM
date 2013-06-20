package com.sino.nm.spare2.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-7
 */
public class BjdbModel extends BaseSQLProducer {
    public BjdbModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.TRANS_TYPE,\n" +
                "       AITH.TRANS_STATUS,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE,\n" +
                "       AITH.TRANS_DATE,\n" +
                "       AITH.TO_OBJECT_NO,\n" +
                "       AITH.FROM_ORGANIZATION_ID,\n" +
                "       AITH.TO_ORGANIZATION_ID,\n" +
                "       AITH.REMARK,\n" +
                "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                "       EOCM2.COMPANY TO_ORGANIZATION_NAME,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION FROM_OBJECT_LOCATION,\n" +
                "       EFV.VALUE TRANS_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_OU_CITY_MAP    EOCM,\n" +
                "       ETS_OU_CITY_MAP    EOCM2,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO(+)\n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID\n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE\n" +
                "   AND AITH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID(+)\n" +
                "   AND AITH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID(+)\n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "   AND EFVS.CODE = 'ORDER_STATUS'" +
                "   AND TRANS_ID = ?";
        sqlArgs.add(amsItemTransH.getTransId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT AIAD.BARCODE,\n" +
                "       ESI.ITEM_CODE,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       ASI.QUANTITY ONHAND_QUANTITY,\n" +
                "       AIAD.QUANTITY,\n" +
                "       AIAD.DETAIL_ID\n" +
                "  FROM ETS_SYSTEM_ITEM     ESI,\n" +
                "       AMS_ITEM_ALLOCATE_D AIAD,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       AMS_SPARE_INFO      ASI\n" +
                " WHERE ESI.ITEM_CODE = AIAD.ITEM_CODE\n" +
                "   AND AIAD.TRANS_ID = AIAH.TRANS_ID\n" +
                "   AND AIAH.FROM_OBJECT_NO = ASI.OBJECT_NO\n" +
                "   AND AIAD.BARCODE = ASI.BARCODE\n" +
                "   AND AIAD.TRANS_ID = ?";
        List argList = new ArrayList();
        argList.add(foreignKey);
        sqlModel.setArgs(argList);
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
