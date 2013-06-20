package com.sino.ams.bean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.base.db.conn.DBManager;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-10-9
 * Time: 15:31:54
 */
public class OrderNumGenerator {
    private Connection conn = null;
    private String type = null; //生成什么类型的编号
    private String companyCode = null;
    private int orderLength;

    /**
     * 功能：构造函数
     * @param conn        Connection 数据库连接
     * @param companyCode String 公司代码
     * @param type        String 单据类型
     */
    public OrderNumGenerator(Connection conn, String companyCode, String type) {
        this.conn = conn;
        this.companyCode = companyCode;
        this.type = type;
    }

    /**
     * 功能：设置生成单据数字部分字符串长度。不设置则默认为5位
     * @param orderLength int
     */
    public void setOrderLength(int orderLength) {
        this.orderLength = orderLength;
    }

    /**
     * 功能：设置公司代码
     * @param companyCode String
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 功能：设置单据类型
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 功能：生成各种单据号。
     * @return String
     * @throws SQLException
     */
    public String getOrderNum() throws SQLException {
        String no = null;
        CallableStatement cst = null;
        String sqlStr = "{CALL dbo.AONP_GET_ORDER_NO(?, ?, ?, ?)}";
        try {
            cst = conn.prepareCall(sqlStr);
            cst.setString(1, companyCode);
            cst.setString(2, type);
            cst.setInt(3, orderLength);
            cst.registerOutParameter(4, Types.VARCHAR);
            conn.setAutoCommit(true);            
            cst.execute();
            no = cst.getString(4);
        } finally {
            DBManager.closeDBStatement(cst);
        }
        return no;
    }

    /**
     * 生成备件申领单号
     * @return String
     * @throws SQLException
     */
    public String getBjslOrderNo() throws SQLException {
        String no = null;
        CallableStatement cst = null;
        String sqlStr = "BEGIN ? := AMS_ORDERNO_PKG.GET_BJSL_ORDER_NO(?); END;";
        try {
            cst = conn.prepareCall(sqlStr);
            cst.registerOutParameter(1, Types.VARCHAR);
            cst.setString(2, companyCode);
            cst.execute();
            no = cst.getString(1);
        } finally {
            DBManager.closeDBStatement(cst);
        }
        return no;
    }

    /**
     * 根据备件申领单号生成其它单据,如调拨单DB
     * @param slOrderNo 备件申领单号
     * @param bjType    备件单据类型,如如调拨单DB
     * @return String
     * @throws SQLException
     */
    public String getBjOrderNo(String slOrderNo, String bjType) throws SQLException {
        String no = null;
        CallableStatement cst = null;
        String sqlStr = "BEGIN ? := AMS_ORDERNO_PKG.GET_BJ_ORDER_NO(?,?); END;";
        try {
            cst = conn.prepareCall(sqlStr);
            cst.registerOutParameter(1, Types.VARCHAR);
            cst.setString(2, slOrderNo);
            cst.setString(3, bjType);
            cst.execute();
            no = cst.getString(1);
        } finally {
            DBManager.closeDBStatement(cst);
        }
        return no;
    }
}
