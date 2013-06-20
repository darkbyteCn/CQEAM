package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-10
 * Time: 15:56:54
 * To change this template use File | Settings | File Templates.
 */
public class LastingAssetsReportModel extends AMSSQLProducer {

    public LastingAssetsReportModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       COUNT(1) LASTING_COUNT\n" +
                "FROM   ETS_ITEM_INFO   EII,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                "WHERE  EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
//                "AND    EII.ATTRIBUTE1 = 'RENT'\n" +
                "AND    EII.FINANCE_PROP = 'RENT_ASSETS'\n" +
                "AND   ( EII.DISABLE_DATE IS NULL OR EII.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + " ) \n" +
                "AND    (" + SyBaseSQLUtil.nullIntParam() + " OR EII.ORGANIZATION_ID = ?)\n" +
                "GROUP  BY EOCM.ORGANIZATION_ID,\n" +
                "          EOCM.COMPANY";
//        sqlArgs.add(dto.getOrganizationId());
//        sqlArgs.add(dto.getOrganizationId());
        SyBaseSQLUtil.nullIntParamArgs(sqlArgs, dto.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
