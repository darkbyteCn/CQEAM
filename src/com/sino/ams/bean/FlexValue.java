package com.sino.ams.bean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-10-9
 * Time: 15:44:18
 * 本类是JAVA取flexValue，SQL调用请直接调用PKG
 */
public class FlexValue {
    /**
     * @param conn 连接
     * @param code flex code
     * @param setCode flex set code
     * @return flexvalue
     */
    public static String getValue(Connection conn,String code,String setCode) throws SQLException {
        String flexValue = null;
        CallableStatement cst = null;
        String sqlStr = "{CALL dbo.APP_GET_FLEX_VALUE_PROC(?,?,?)}";
        try {
            cst = conn.prepareCall(sqlStr);
            int index = 1;           
            cst.setString(index++, code);
            cst.setString(index++, setCode);
            cst.registerOutParameter(index, Types.VARCHAR);
            cst.execute();
            flexValue = cst.getString(3);
        } finally {
            DBManager.closeDBStatement(cst);
        }
        return flexValue;
    }
}
