package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 资产调拨流程监控</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-10-16
 */
public class AssetsTransMonitorModel extends AMSSQLProducer {
    AmsAssetsTransHeaderDTO assetsHdto = null;
    public AssetsTransMonitorModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        assetsHdto = (AmsAssetsTransHeaderDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        try {
            List sqlArgs = new ArrayList();
            String sqlStr = "SELECT AATH.TRANS_ID,\n" +
                    "       AATH.TRANS_NO,\n" +
                    "       AATH.TRANS_STATUS,\n" +
                    "       AATH.FROM_ORGANIZATION_ID,\n" +
                    "       EOCM.COMPANY FROM_COMPANY_NAME,\n" +
                    "       EOCM2.COMPANY TO_COMPANY_NAME,\n" +
                    "       AATH.RECEIVED_USER,\n" +
                    "       AATH.CREATION_DATE,\n" +
                    "       SU.USERNAME CREATED,\n" +
                    "       STD.TASK_NAME,\n" +
                    "       SU2.USERNAME\n" +     //流程当前处理人
                    "  FROM AMS_ASSETS_TRANS_HEADER AATH,\n" +
                    "       ETS_OU_CITY_MAP         EOCM,\n" +
                    "       ETS_OU_CITY_MAP         EOCM2,\n" +
                    "       SF_USER                 SU,\n" +
                    "       SF_ACT                  SA,\n" +
                    "       SF_TASK_DEFINE          STD,\n" +
                    "       SF_ACT_INFO             SAI,\n" +
                    "       SF_USER                 SU2\n" +
                    " WHERE AATH.FROM_ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                    "   AND AATH.TO_ORGANIZATION_ID = EOCM2.ORGANIZATION_ID\n" +
                    "   AND AATH.CREATED_BY = SU.USER_ID\n" +
                    "   AND AATH.TRANS_NO = SA.APPLY_NUMBER\n" +
                    "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                    "   AND SA.ACTID = SAI.ACT_ID\n" +
                    "   AND SAI.USER_ID = SU2.USER_ID\n" +
                    "   AND EOCM.COMPANY LIKE dbo.NVL(?, EOCM.COMPANY)\n" +
                    "   AND EOCM2.COMPANY LIKE dbo.NVL(?, EOCM2.COMPANY)\n" +
                    "   AND AATH.CREATION_DATE >= ISNULL(?, AATH.CREATION_DATE)\n" +
                    "   AND AATH.CREATION_DATE <= ISNULL(?, AATH.CREATION_DATE)\n" +
                    "   AND AATH.TRANS_NO LIKE dbo.NVL(?, AATH.TRANS_NO)\n" +
                    " ORDER BY AATH.CREATION_DATE DESC";
            sqlArgs.add(assetsHdto.getFromCompanyName());
            sqlArgs.add(assetsHdto.getToCompanyName());
            sqlArgs.add(assetsHdto.getStartDate());
            sqlArgs.add(assetsHdto.getSQLEndDate());

            sqlArgs.add(assetsHdto.getTransNo());

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (CalendarException ex) {
            ex.printLog();
            throw new SQLModelException(ex);
        }
        return sqlModel;
    }
}
