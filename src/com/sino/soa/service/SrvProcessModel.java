package com.sino.soa.service;

import com.sino.base.db.sql.model.SQLModel;

import java.util.ArrayList;
import java.util.List;

import com.sino.soa.common.MIS_CONSTANT;


public class SrvProcessModel {


    /**
     * 功能：获取当前时间的查询语句。
     * @return SQLModel
     */
    public SQLModel getCurrTimeModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SELECT GETDATE() CURR_TIME";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getCurrHourModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SUBSTRING(CONVERT(CHAR,GETDATE(),108), 1, 2) HOUR";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getLocalCodeModel(String source, String flexSetName) {
        SQLModel sqlModel = new SQLModel();
        List<String> sqlArgs = new ArrayList<String>();
        String sqlStr = "SELECT \n" +
                "  MFFV.FLEX_VALUE\n" +
                "FROM \n" +
                "  M_FND_FLEX_VALUES MFFV, \n" +
                "  M_FND_FLEX_VALUE_SETS MFFVS\n" +
                " WHERE MFFV.FLEX_VALUE_SET_ID = MFFVS.FLEX_VALUE_SET_ID\n" +
                "   AND MFFVS.FLEX_VALUE_SET_NAME = ?\n" +
                "   AND MFFVS.SOURCE = ?";
        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(flexSetName);
        sqlArgs.add(source);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getBookTypeCodeModel(String source) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EOCM.BOOK_TYPE_CODE FROM ETS_OU_CITY_MAP EOCM WHERE 1=1";

        if (source.equals(MIS_CONSTANT.SOURCE_MIS)) {
            sqlStr += " AND EOCM.IS_TD = 'N'";
        } else {
            sqlStr += " AND EOCM.IS_TD = 'Y'";
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getBookTypeCodeModel2(String source) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ACB.BOOK_TYPE_CODE\n" +
                "  FROM AMS_COMPANY_BOOK ACB\n" +
                " WHERE (ACB.BOOK_TYPE_CODE LIKE '%FA%' OR ACB.BOOK_TYPE_CODE LIKE '%IA%')\n" +
                "   AND EXISTS (SELECT NULL\n" +
                "          FROM ETS_OU_CITY_MAP EOCM\n" +
                "         WHERE ACB.COMPANY_CODE = EOCM.COMPANY_CODE\n" +
                "           AND EOCM.IS_TD = ?)";

        if (source.equals(MIS_CONSTANT.SOURCE_MIS)) {
            sqlArgs.add("N");
        } else {
            sqlArgs.add("Y");
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}