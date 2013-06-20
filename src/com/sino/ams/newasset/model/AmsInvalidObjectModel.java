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
 * Time: 16:02:59
 * To change this template use File | Settings | File Templates.
 */
public class AmsInvalidObjectModel extends AMSSQLProducer {

    public AmsInvalidObjectModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AO.OBJECT_NAME,\n" +
                "       AO.OBJECT_TYPE,\n" +
                "       AO.LAST_DDL_TIME\n" +
                "FROM   SYS.ALL_OBJECTS AO\n" +
                "WHERE  AO.STATUS = 'INVALID'\n" +
                "AND    AO.OWNER = 'SINOEAM'\n" +
                "ORDER  BY AO.OBJECT_TYPE";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
