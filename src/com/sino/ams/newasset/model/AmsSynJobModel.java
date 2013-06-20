package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-16
 * Time: 11:12:30
 * To change this template use File | Settings | File Templates.
 */
public class AmsSynJobModel extends AMSSQLProducer {

    public AmsSynJobModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DJ.JOB,\n" +
                "       DJ.WHAT,\n" +
                "       DJ.LAST_DATE,\n" +
                "       DJ.NEXT_DATE,\n" +
                "       DJ.INTERVAL\n" +
                "FROM   DBA_JOBS DJ\n" +
                "ORDER BY DJ.JOB";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
