package com.sino.ams.system.object.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.config.SinoConfig;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * User: Administrator
 * Date: 2008-11-6 12:16:01
 * Function:取地点最大编号
 * version：单独
 */
public class ObjectMaxModel extends AMSSQLProducer {

    public ObjectMaxModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        return getMaxNoModel();
    }

    public SQLModel getMaxNoModelOld() {
        EtsObjectDTO eo = (EtsObjectDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT TOTAL.ORG_NAME,\n" +
                "       TOTAL.LOC_CATEGORY_DESC,\n" +
                "       TOTAL.LOC_SX,\n" +
                "       MAX(TOTAL.LOC_NUM) MAX_NUMBER\n" +
                "  FROM (SELECT EOCM.COMPANY ORG_NAME,\n" +
                "               EFV.VALUE LOC_CATEGORY_DESC,\n" +
                "               SUBSTRING(SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(EOL.LOC2_CODE)),\n" +
                "                         1,\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(EOL.LOC2_CODE))) - 1) LOC_SX,\n" +
                "               SUBSTRING(SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(EOL.LOC2_CODE)),\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(EOL.LOC2_CODE))),\n" +
                "                         DATALENGTH(SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                              5,\n" +
                "                                              DATALENGTH(EOL.LOC2_CODE)))) LOC_NUM\n" +
                "          FROM ETS_OBJECT_LOC2    EOL,\n" +
                "               ETS_OU_CITY_MAP    EOCM,\n" +
                "               ETS_FLEX_VALUES    EFV,\n" +
                "               ETS_FLEX_VALUE_SET EFVS\n" +
                "         WHERE EOL.OBJECT_CATEGORY = EFV.CODE\n" +
                "           AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "           AND EFVS.CODE = 'OBJECT_CATEGORY'\n" +
                "           AND EOL.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "           AND (? = -1 OR EOCM.ORGANIZATION_ID = ?)\n" +
                "           AND (? = '' OR ATTRIBUTE2 = ?)\n" +
                "			AND patindex('[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]',EOL.LOC2_CODE)!>0 \n" +
                "        UNION ALL\n" +
                "        SELECT EOCM.COMPANY ORG_NAME,\n" +
                "               EFV.VALUE LOC_CATEGORY_DESC,\n" +
                "               SUBSTRING(SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE, 8, 14),\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                        8,\n" +
                "                                                        14))),\n" +
                "                         1,\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                      8,\n" +
                "                                                      14),\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                                 8,\n" +
                "                                                                 14)))) - 1) LOC_SX,\n" +
                "               SUBSTRING(SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE, 8, 14),\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                        8,\n" +
                "                                                        14))),\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                      8,\n" +
                "                                                      14),\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                                 8,\n" +
                "                                                                 14)))),\n" +
                "                         DATALENGTH(SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                        8,\n" +
                "                                                        14),\n" +
                "                                              5,\n" +
                "                                              DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                                   8,\n" +
                "                                                                   14))))) LOC_NUM\n" +
                "          FROM ETS_OBJECT         EO,\n" +
                "               ETS_FLEX_VALUES    EFV,\n" +
                "               ETS_FLEX_VALUE_SET EFVS,\n" +
                "               ETS_OU_CITY_MAP    EOCM\n" +
                "         WHERE EO.OBJECT_CATEGORY = EFV.CODE\n" +
                "           AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "           AND EFVS.CODE = 'OBJECT_CATEGORY'\n" +
                "           AND EOCM.ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
                "           AND CHAR_LENGTH(EO.WORKORDER_OBJECT_CODE) = 25\n" +
                "           AND EO.OBJECT_CATEGORY <> '74'\n" +
                "           AND (? = -1 OR EOCM.ORGANIZATION_ID = ?)\n" +
                "           AND (? = '' OR ATTRIBUTE2 = ?)\n" +
                "			AND len(EO.WORKORDER_OBJECT_CODE) = 25\n" +
                "			AND patindex('[0-9][0-9][0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z].___',EO.WORKORDER_OBJECT_CODE)!>0) TOTAL\n" +
                " GROUP BY TOTAL.ORG_NAME,\n" +
                "          TOTAL.LOC_CATEGORY_DESC,\n" +
                "          TOTAL.LOC_SX\n" +
                " ORDER BY TOTAL.ORG_NAME,\n" +
                "          TOTAL.LOC_CATEGORY_DESC";
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getLocationCode());
        sqlArgs.add(eo.getLocationCode());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getLocationCode());
        sqlArgs.add(eo.getLocationCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getMaxNoModel() {
        EtsObjectDTO eo = (EtsObjectDTO) dtoParameter;
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.COMPANY ORG_NAME,\n" +
                "       TOTAL.LOC_CATEGORY_DESC,\n" +
                "       TOTAL.LOC_SX,\n";
        if (SinoConfig.getProvinceCode().equals(DictConstant.PROVINCE_CODE_HN)) {
        	sqlStr+= " 	(CASE LEN(MAX(TOTAL.LOC_NUM)) WHEN 8 THEN '4'||SUBSTRING(MAX(TOTAL.LOC_NUM),2,8) ELSE MAX(TOTAL.LOC_NUM) END) MAX_NUMBER\n";
        } else {
        	sqlStr+= "     MAX(TOTAL.LOC_NUM) MAX_NUMBER\n";
        } 
        sqlStr+="  FROM (SELECT --EOCM.COMPANY ORG_NAME,\n" +
                "               EFV.VALUE LOC_CATEGORY_DESC,\n" +
                "               SUBSTRING(SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(EOL.LOC2_CODE)),\n" +
                "                         1,\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(EOL.LOC2_CODE))) - 1) LOC_SX,\n" +
                "               SUBSTRING(SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(EOL.LOC2_CODE)),\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(EOL.LOC2_CODE))),\n" +
                "                         DATALENGTH(SUBSTRING(EOL.LOC2_CODE,\n" +
                "                                              5,\n" +
                "                                              DATALENGTH(EOL.LOC2_CODE)))) LOC_NUM\n" +
                "          FROM ETS_OBJECT_LOC2    EOL,\n" +
                "               ETS_OU_CITY_MAP    EOCM,\n" +
                "               ETS_FLEX_VALUES    EFV,\n" +
                "               ETS_FLEX_VALUE_SET EFVS\n" +
                "         WHERE EOL.OBJECT_CATEGORY = EFV.CODE\n" +
                "           AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "           AND EFVS.CODE = 'OBJECT_CATEGORY'\n" +
                "           AND EOL.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "           AND (? = -1 OR EOCM.ORGANIZATION_ID = ? OR EOCM.MATCH_ORGANIZATION_ID=?)\n" +
                "           AND (? = '' OR ATTRIBUTE2 = ?)\n" +
                "			AND patindex('[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]',EOL.LOC2_CODE)!>0 \n" +
                "        UNION ALL\n" +
                "        SELECT --EOCM.COMPANY ORG_NAME,\n" +
                "               EFV.VALUE LOC_CATEGORY_DESC,\n" +
                "               SUBSTRING(SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE, 8, 14),\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                        8,\n" +
                "                                                        14))),\n" +
                "                         1,\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                      8,\n" +
                "                                                      14),\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                                 8,\n" +
                "                                                                 14)))) - 1) LOC_SX,\n" +
                "               SUBSTRING(SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE, 8, 14),\n" +
                "                                   5,\n" +
                "                                   DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                        8,\n" +
                "                                                        14))),\n" +
                "                         PATINDEX('%[0-9]%',\n" +
                "                                  SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                      8,\n" +
                "                                                      14),\n" +
                "                                            5,\n" +
                "                                            DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                                 8,\n" +
                "                                                                 14)))),\n" +
                "                         DATALENGTH(SUBSTRING(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                        8,\n" +
                "                                                        14),\n" +
                "                                              5,\n" +
                "                                              DATALENGTH(SUBSTRING(EO.WORKORDER_OBJECT_CODE,\n" +
                "                                                                   8,\n" +
                "                                                                   14))))) LOC_NUM\n" +
                "          FROM ETS_OBJECT         EO,\n" +
                "               ETS_FLEX_VALUES    EFV,\n" +
                "               ETS_FLEX_VALUE_SET EFVS,\n" +
                "               ETS_OU_CITY_MAP    EOCM\n" +
                "         WHERE EO.OBJECT_CATEGORY = EFV.CODE\n" +
                "           AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID\n" +
                "           AND EFVS.CODE = 'OBJECT_CATEGORY'\n" +
                "           AND EOCM.ORGANIZATION_ID = EO.ORGANIZATION_ID\n" +
                "           AND CHAR_LENGTH(EO.WORKORDER_OBJECT_CODE) = 25\n" +
                "           AND EO.OBJECT_CATEGORY <> '74'\n" +
                "           AND (? = -1 OR EOCM.ORGANIZATION_ID = ? OR EOCM.MATCH_ORGANIZATION_ID=?)\n" +
                "           AND (? = '' OR ATTRIBUTE2 = ?)\n" +
                "			AND len(EO.WORKORDER_OBJECT_CODE) = 25\n" +
                "			AND patindex('[0-9][0-9][0-9][0-9][0-9][0-9].[0-9][0-9][0-9][0-9][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z].___',EO.WORKORDER_OBJECT_CODE)!>0) TOTAL,ETS_OU_CITY_MAP EOCM\n" +
                " WHERE (?=-1 OR EOCM.ORGANIZATION_ID=?)\n" +
                " GROUP BY EOCM.COMPANY,\n" +
                "          TOTAL.LOC_CATEGORY_DESC,\n" +
                "          TOTAL.LOC_SX\n" +
                " ORDER BY EOCM.COMPANY,\n" +
                "          TOTAL.LOC_CATEGORY_DESC";
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getLocationCode());
        sqlArgs.add(eo.getLocationCode());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getLocationCode());
        sqlArgs.add(eo.getLocationCode());
        sqlArgs.add(eo.getOrganizationId());
        sqlArgs.add(eo.getOrganizationId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
