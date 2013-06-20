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
 * Date: 2009-6-11
 * Time: 10:22:10
 * To change this template use File | Settings | File Templates.
 */
public class VillageAssetsReportModel extends AMSSQLProducer {

    public VillageAssetsReportModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.ORGANIZATION_ID,\n" +
                "       EOCM.COMPANY,\n" +
                "       COUNT(1) VILLAGE_COUNT\n" +
                "FROM   ETS_ITEM_INFO   EII,\n" +
                "       ETS_OU_CITY_MAP EOCM\n" +
                "WHERE  EII.ORGANIZATION_ID = EOCM.ORGANIZATION_ID\n" +
                "AND    EII.ATTRIBUTE1 = 'CT'\n" +
                //"AND    EII.DISABLE_DATE IS NULL\n" +
                //"AND    ( CONVERT(VARCHAR, ?) = '' OR EII.ORGANIZATION_ID = ? )\n" +
                "AND    (EII.DISABLE_DATE IS NULL OR CONVERT(VARCHAR, EII.DISABLE_DATE) " + SyBaseSQLUtil.isNullNoParam() + ") \n" +
                "AND    (" + SyBaseSQLUtil.nullIntParam() + " OR EII.ORGANIZATION_ID = ?)\n" +
                "GROUP  BY EOCM.ORGANIZATION_ID,\n" +
                "          EOCM.COMPANY";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
