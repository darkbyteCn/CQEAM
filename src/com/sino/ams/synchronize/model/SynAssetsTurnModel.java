package com.sino.ams.synchronize.model;

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
 * Date: 2009-11-18
 * Time: 15:24:23
 * To change this template use File | Settings | File Templates.
 */
public class SynAssetsTurnModel extends AMSSQLProducer {

    public SynAssetsTurnModel(SfUserDTO userAccount, DTO dtoParameter) {
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
                "       (DECODE(TRUNC(100 * (TOTAL - FREE) / TOTAL),\n" +
                "               0,\n" +
                "               STR_REPLACE(ROUND(100 * (TOTAL - FREE) / TOTAL,\n" +
                "                             2),\n" +
                "                       '.',\n" +
                "                       '0.'),\n" +
                "               STR(ROUND(100 * (TOTAL - FREE) / TOTAL,\n" +
                "                             2))) || '%') TABLESPACE_USE_RATE\n" +
                "FROM   (SELECT TABLESPACE_NAME,\n" +
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

    public SQLModel getDataCountModel(String orgId, String projectNum) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT COUNT(1) TAOTAL_COUNT\n" +
                "  FROM ETS_FA_CUST_DETAIL_INFO_V@MIS_FA T,\n" +
                "       ETS_OU_CITY_MAP                  EOCM,\n" +
                "       AMS_MANUFACTURER                 AM\n" +
                " WHERE  " + SyBaseSQLUtil.isNotNull("T.TAG_NUMBER") + " \n" +
                "       AND  " + SyBaseSQLUtil.isNotNull("T.LOCATION_CODE") + " \n" +
                "       AND  " + SyBaseSQLUtil.isNotNull("T.EMPLOYEE_NUMBER") + " \n" +
                "      /*AND T.CAPITALIZED_FLAG = 'N'*/\n" +
                "       AND dbo.NVL(T.MANUFACTURER_NAME, '尚无厂家记录') *= AM.MANUFACTURER_NAME\n" +
                "       AND NOT EXISTS\n" +
                " (SELECT NULL FROM ETS_FA_ASSETS EFA WHERE T.TAG_NUMBER = EFA.TAG_NUMBER)\n" +
                "       AND NOT EXISTS\n" +
                " (SELECT NULL FROM ETS_ITEM_INFO EII WHERE T.TAG_NUMBER = EII.BARCODE)\n" +
                "       AND SUBSTRING(T.BOOK_TYPE_CODE, 9) = EOCM.COMPANY_CODE\n" +
                "       AND EOCM.ORGANIZATION_ID = ?\n" +
                "       AND T.PROJECT_NUMBER = ?";
        sqlArgs.add(orgId);
        sqlArgs.add(projectNum);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
