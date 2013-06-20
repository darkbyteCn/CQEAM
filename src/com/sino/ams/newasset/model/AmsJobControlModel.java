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
 * Time: 9:56:52
 * To change this template use File | Settings | File Templates.
 */
public class AmsJobControlModel extends AMSSQLProducer {

    public AmsJobControlModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EAJC.JOB_TYPE,\n" +
                " CASE WHEN EAJC.STATUS=0 THEN '空闲' ELSE '正在运行' END STATUS,"+
                "       EAJC.START_DATE,\n" +
                "       EAJC.END_DATE\n" +
                "FROM   ETS_AUTO_JOB_CONTROL EAJC";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
