package com.sino.nm.spare2.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.nm.spare2.dto.AmsItemAllocateHDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 备件实物借用出库查询</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-6-24
 */
public class BjswjyckcxModel extends AMSSQLProducer {
    private AmsItemAllocateHDTO headerDTO = null;

    public BjswjyckcxModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        headerDTO = (AmsItemAllocateHDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AITH.TRANS_NO,\n" +
                "       AIAH.TRANS_NO ALLOCATE_NO,\n" +
                "       AIFD.FREIGHT_ID,\n" +
                "       AIFD.ITEM_CODE,\n" +
                "       AIFD.DETAIL_ID,\n" +
                "       AIFD.TRANS_ID,\n" +
                "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(AITH.FROM_ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                "       ESI.ITEM_NAME,\n" +
                "       ESI.ITEM_SPEC,\n" +
                "       AIFD.FREIGHT_QUANTITY,\n" +
                "       AIFD.FREIGHT_DATE,\n" +
                "       EFV.VALUE STATUS,\n" +
                "       DECODE(AIFD.RESEND_FLAG,'Y','已重寄','') REMARK\n" +
                "FROM   AMS_ITEM_FREIGHT_D  AIFD,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       AMS_ITEM_TRANS_H    AITH,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_FLEX_VALUES     EFV,\n" +
                "       ETS_FLEX_VALUE_SET  EFVS\n" +
                "WHERE  AIFD.TRANS_ID = AIAH.TRANS_ID\n" +
                "       AND AIAH.SOURCE_ID = AITH.TRANS_ID\n" +
                "       AND ESI.ITEM_CODE = AIFD.ITEM_CODE\n" +
                "       AND AIFD.FREIGHT_STATUS = EFV.CODE\n" +
                "       AND EFVS.CODE = 'SPARE_ORDER_STATUS'\n" +
                "       AND AIAH.FROM_ORGANIZATION_ID = ?\n" +
                "       AND AIAH.TRANS_NO LIKE NVL(?, AIAH.TRANS_NO)\n" +
                "       AND AIAH.FROM_OBJECT_NO = NVL(?,AIAH.FROM_OBJECT_NO)\n" +
                "       AND AIFD.FREIGHT_DATE >= NVL(?, AIFD.FREIGHT_DATE)\n" +
                "       AND AIFD.FREIGHT_DATE <= NVL(?, AIFD.FREIGHT_DATE)\n" +
                "ORDER  BY AIFD.FREIGHT_DATE";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getFromObjectNo());
        sqlArgs.add(headerDTO.getFromDate());
        sqlArgs.add(headerDTO.getSQLToDate());


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
