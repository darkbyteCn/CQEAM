package com.sino.appbase.init;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

/**
 * User: T_zhoujinsong
 * Date: 11-11-26 ÉÏÎç10:52
 * Function:
 */
public class GenSybaseObjectModel {


    public static SQLModel getAllObjectsModel(String type) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "select so.name from sysobjects so where so.type=?   ORDER BY so.name";
        sqlArgs.add(type);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public static SQLModel getObjectModel(String objectName, String type) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "select id, text from syscomments \n" +
                "where id in (select id from sysobjects  \n" +
                "where type = ? and name=?) order by colid ";

        sqlArgs.add(type);
        sqlArgs.add(objectName);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
