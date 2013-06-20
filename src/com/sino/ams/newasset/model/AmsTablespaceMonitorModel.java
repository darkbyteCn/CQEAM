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
 * Date: 2009-6-15
 * Time: 15:31:50
 * To change this template use File | Settings | File Templates.
 */
public class AmsTablespaceMonitorModel extends AMSSQLProducer {

    public AmsTablespaceMonitorModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT A.TABLESPACE_NAME,\n" +
                "       TOTAL TABLESPACE_SIZE,\n" +
                "       FREE TABLESPACE_FREE_SIZE,\n" +
                "       (TOTAL - FREE) TTABLESPACE_USE_SIZE,\n" +
                " (CASE WHEN TRUNC(100 * (TOTAL - FREE) / TOTAL)=0 THEN STR_REPLACE(ROUND(100 * (TOTAL - FREE) / TOTAL,2),'.','0.') ELSE CONVERT(VARCHAR,ROUND(100 * (TOTAL - FREE) / TOTAL,2)) END ||'%') TABLESPACE_USE_RATE"+
                " FROM   (SELECT TABLESPACE_NAME,\n" +
                "               SUM(BYTES) FREE\n" +
                "        FROM   DBA_FREE_SPACE\n" +
                "        GROUP  BY TABLESPACE_NAME) A,\n" +
                "       (SELECT TABLESPACE_NAME,\n" +
                "               SUM(BYTES) TOTAL\n" +
                "        FROM   DBA_DATA_FILES\n" +
                "        GROUP  BY TABLESPACE_NAME) B\n" +
                "WHERE  A.TABLESPACE_NAME = B.TABLESPACE_NAME";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
