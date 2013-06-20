package com.sino.nm.spare2.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.nm.spare2.dto.AmsItemAllocateHDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-7-30
 */
public class BjdbdyModel extends AMSSQLProducer {
    private AmsItemAllocateHDTO headerDTO = null;

    public BjdbdyModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        headerDTO = (AmsItemAllocateHDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AIAH.CREATED_BY,\n" +
                "       SU.USERNAME CREATED_USER,\n" +
                "       AIAH.CREATION_DATE,\n" +
                "       AIAH.FROM_OBJECT_NO,\n" +
                "       AIAH.FROM_ORGANIZATION_ID,\n" +
                "       AIAH.SOURCE_ID,\n" +
                "       AIAH.TO_ORGANIZATION_ID,\n" +
                "       AIAH.TRANS_DATE,\n" +
                "       AIAH.TRANS_ID,\n" +
                "       AIAH.TRANS_NO,\n" +
                "       AIAH.TRANS_STATUS,\n" +
                "       EO.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       dbo.APP_GET_ALLOCATE_STATUS_NAME(AIAH.TRANS_STATUS) ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_ALLOCATE_H AIAH, SF_USER SU, ETS_OBJECT EO\n" +
                " WHERE AIAH.CREATED_BY = SU.USER_ID\n" +
                "   AND AIAH.FROM_OBJECT_NO = EO.WORKORDER_OBJECT_NO\n" +
                "   AND (''=? OR AIAH.TRANS_NO LIKE ?)\n" +
                "   AND (''=? OR AIAH.FROM_OBJECT_NO = ?)\n" +
                "   AND (''=? OR AIAH.CREATION_DATE >= ?)\n" +
                "   AND (''=? OR AIAH.CREATION_DATE <= CONVERT(DATE,?))\n" +
                "   AND AIAH.FROM_ORGANIZATION_ID = ?\n" +
                " ORDER BY CREATION_DATE DESC\n";
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getTransNo());
        sqlArgs.add(headerDTO.getFromObjectNo());
        sqlArgs.add(headerDTO.getFromObjectNo());
        sqlArgs.add(headerDTO.getFromDate());
        sqlArgs.add(headerDTO.getFromDate());
        sqlArgs.add(headerDTO.getSQLToDate());
        sqlArgs.add(headerDTO.getSQLToDate());
        sqlArgs.add(userAccount.getOrganizationId());


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDataByForeignKeyModel(String foreignKey) throws SQLModelException {
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
                "       DECODE(AIFD.RESEND_FLAG, 'Y', '已重寄', '') REMARK\n" +
                "  FROM AMS_ITEM_FREIGHT_D  AIFD,\n" +
                "       AMS_ITEM_ALLOCATE_H AIAH,\n" +
                "       AMS_ITEM_TRANS_H    AITH,\n" +
                "       ETS_SYSTEM_ITEM     ESI,\n" +
                "       ETS_FLEX_VALUES     EFV,\n" +
                "       ETS_FLEX_VALUE_SET  EFVS\n" +
                " WHERE AIFD.TRANS_ID = AIAH.TRANS_ID\n" +
                "   AND AIAH.SOURCE_ID = AITH.TRANS_ID\n" +
                "   AND ESI.ITEM_CODE = AIFD.ITEM_CODE\n" +
                "   AND AIFD.FREIGHT_STATUS = EFV.CODE\n" +
                "   AND EFVS.CODE = 'SPARE_ORDER_STATUS'\n" +
                "   AND AIAH.FROM_ORGANIZATION_ID = ?\n" +
                "   AND AIFD.FREIGHT_DATE = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(foreignKey);


        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}


