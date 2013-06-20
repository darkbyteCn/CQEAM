package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsMisTagChgDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 打印调入方新标签号</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-10-14
 */
public class NewAssetsTagPrintModel extends AMSSQLProducer {
    public NewAssetsTagPrintModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            AmsMisTagChgDTO amsMisTagChg = (AmsMisTagChgDTO) dtoParameter;
            String sqlStr = "SELECT AMTC.TAG_NUMBER_TO,\n" +
                    "       AATH.TRANS_ID,\n" +
                    "       AATH.TRANS_NO,\n" +
                    "       EOCM.COMPANY FROM_ORGANIZATION_NAME,\n" +
                    "       EOCM2.COMPANY TO_ORGANIZATION_NAME,\n" +
                    "       AATH.CREATION_DATE,\n" +
                    "       SU.USERNAME CREATED_BY\n" +
                    "  FROM AMS_ASSETS_TRANS_HEADER AATH,\n" +
                    "       ETS_OU_CITY_MAP         EOCM,\n" +
                    "       ETS_OU_CITY_MAP         EOCM2,\n" +
                    "       SF_USER                 SU,\n" +
                    "       AMS_MIS_TAG_CHG         AMTC\n" +
                    " WHERE AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "   AND AATH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID\n" +
                    "   AND AATH.CREATED_BY = SU.USER_ID\n" +
                    "   AND AATH.TRANS_NO = AMTC.REF_NUMBER\n" +
                    "   AND AATH.TRANS_TYPE = '" + AssetsDictConstant.ASS_RED + "'\n" +    //调拨单
                    "   AND AATH.TRANSFER_TYPE = '" + AssetsDictConstant.TRANS_BTW_COMP + "'\n" + //公司间调拨
                    "   AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)\n" +      // FROM_ORGANIZATION_NAME
                    "   AND EOCM2.COMPANY LIKE dbo.NVL(?, EOCM2.COMPANY)\n" +    // TO_ORGANIZATION_NAME
                    "   AND AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE)\n" +
                    "   AND AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE)\n" +
//                    "   AND AATH.TRANS_STATUS = dbo.NVL(NULL, AATH.TRANS_STATUS)\n" +
                    "   AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)\n" +
                    " ORDER BY AATH.CREATION_DATE DESC";
            sqlArgs.add(amsMisTagChg.getFromOrganizationName());
            sqlArgs.add(amsMisTagChg.getToOrganizationName());
            sqlArgs.add(amsMisTagChg.getStartDate());
            sqlArgs.add(amsMisTagChg.getSQLEndDate());
            sqlArgs.add(amsMisTagChg.getRefNumber());
//            sqlArgs.add(amsMisTagChg.getCreatedBy());
//            sqlArgs.add(amsMisTagChg.getCreatedBy());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }
}
