package com.sino.nm.spare2.model;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 备件子库转移确认</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-8-15
 */
public class BjInvTransModel extends AMSSQLProducer {
    public BjInvTransModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) dtoParameter;
        String sqlStr = "SELECT AITH.TRANS_ID,\n" +
                "       AITH.TRANS_NO,\n" +
                "       AITH.CREATED_BY,\n" +
                "       AITH.CREATION_DATE CREATION_DATE,\n" +
                "       AITH.TRANS_DATE TRANS_DATE,\n" +
                "       SUV.USERNAME CREATED_USER,\n" +
                "       EO.WORKORDER_OBJECT_NAME TO_OBJECT_NAME,\n" +
                "       EO2.WORKORDER_OBJECT_NAME FROM_OBJECT_NAME,\n" +
                "       EFV.VALUE ORDER_STATUS_NAME\n" +
                "  FROM AMS_ITEM_TRANS_H   AITH,\n" +
                "       ETS_OBJECT         EO,\n" +
                "       ETS_OBJECT         EO2,\n" +
                "       SF_USER_V          SUV,\n" +
                "       ETS_FLEX_VALUES    EFV,\n" +
                "       ETS_FLEX_VALUE_SET EFVS\n" +
                " WHERE AITH.TO_OBJECT_NO *= EO.WORKORDER_OBJECT_NO \n" +
                "   AND AITH.FROM_OBJECT_NO = EO2.WORKORDER_OBJECT_NO \n" +
                "   AND AITH.CREATED_BY = SUV.USER_ID \n" +
                "   AND AITH.TRANS_STATUS = EFV.CODE \n" +
                "   AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID \n" +
                "   AND EFVS.CODE = 'ORDER_STATUS' \n"
                + " AND ("+ SyBaseSQLUtil.nullStringParam() +" OR AITH.TRANS_NO = ?)"
                + " AND ("+ SyBaseSQLUtil.nullStringParam() +" OR AITH.TRANS_STATUS = ?)"
                + " AND ("+ SyBaseSQLUtil.nullStringParam() +" OR AITH.TO_OBJECT_NO = ?)"
                + " AND (? = -1 OR AITH.FROM_ORGANIZATION_ID = ?)"
                + " AND (? = -1 OR AITH.TO_ORGANIZATION_ID = ?)"
                + " AND AITH.TRANS_TYPE = dbo.NVL(?,AITH.TRANS_TYPE)"
//                + " AND AITH.CREATION_DATE >= NVL(?, AITH.CREATION_DATE)"
//                + " AND AITH.CREATION_DATE <= NVL(?, AITH.CREATION_DATE)" +
                + " AND (AITH.CREATION_DATE >= ? OR "+ SyBaseSQLUtil.nullStringParam() +")"
                + " AND (AITH.CREATION_DATE < DATEADD(DAY,1,?) OR "+ SyBaseSQLUtil.nullStringParam() +")" +
                "  ORDER BY AITH.CREATION_DATE DESC";
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
        sqlArgs.add(amsItemTransH.getTransNo());
//		sqlArgs.add(amsItemTransH.getTransType());
//		sqlArgs.add(amsItemTransH.getTransType());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
        sqlArgs.add(amsItemTransH.getTransStatus());
//		sqlArgs.add(amsItemTransH.getFromUser());
//		sqlArgs.add(amsItemTransH.getFromUser());
//		sqlArgs.add(amsItemTransH.getToUser());
//		sqlArgs.add(amsItemTransH.getToUser());
//		sqlArgs.add(amsItemTransH.getFromDept());
//		sqlArgs.add(amsItemTransH.getFromDept());
//		sqlArgs.add(amsItemTransH.getToDept());
//		sqlArgs.add(amsItemTransH.getToDept());
//		sqlArgs.add(amsItemTransH.getFromObjectNo());
//		sqlArgs.add(amsItemTransH.getFromObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getToObjectNo());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getFromOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlArgs.add(amsItemTransH.getToOrganizationId());
        sqlArgs.add(amsItemTransH.getTransType());
        try {
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getFromDate());
            sqlArgs.add(amsItemTransH.getToDate());
            sqlArgs.add(amsItemTransH.getToDate());
            sqlArgs.add(amsItemTransH.getToDate());
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
//		sqlArgs.add(amsItemTransH.getRcvUser());
//		sqlArgs.add(amsItemTransH.getRcvUser());
//		sqlArgs.add(amsItemTransH.getCreationDate());
//		sqlArgs.add(amsItemTransH.getCreationDate());
//		sqlArgs.add(amsItemTransH.getCreatedBy());
//		sqlArgs.add(amsItemTransH.getCreatedBy());
//		sqlArgs.add(amsItemTransH.getLastUpdateDate());
//		sqlArgs.add(amsItemTransH.getLastUpdateDate());
//		sqlArgs.add(amsItemTransH.getLastUpdateBy());
//		sqlArgs.add(amsItemTransH.getLastUpdateBy());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
