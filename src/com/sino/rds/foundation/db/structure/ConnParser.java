package com.sino.rds.foundation.db.structure;

import com.sino.base.db.ConnBase;
import com.sino.base.exception.DatabaseException;
import com.sino.base.log.Logger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class ConnParser extends ConnBase {
    private static final String SybaseDBProduct = "Adaptive Server Enterprise";
    private static final String OracleDBProduct = "Oracle";

    private String dbURL = "";
    private String dbUser = "";
    private String dbDriver = "";
    private String dbProductName = "";
    private String driverVersion = "";

    /**
     * 功能：无参数构造函数。
     *
     */
    public ConnParser() {
        this(null);
    }

    /**
     * 功能：构造函数。方法重载。
     *
     * @param conn Connection 数据库连接
     */
    public ConnParser(Connection conn) {
        setDBConnection(conn);
    }

    /**
     * 功能：设置数据库连接
     *
     * @param conn Connection
     */
    public void setDBConnection(Connection conn) {
        try {
            super.setDBConnection(conn);
            if (conn != null) {
                parseConn(conn);
            }
        } catch (DatabaseException ex) {
            Logger.logError(ex);
            throw new RuntimeException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 功能：获取数据库URL。
     *
     * @return String
     */
    public String getDbURL() {
        return dbURL;
    }

    /**
     * 功能：获取数据库用户。
     *
     * @return String
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * 功能：获取数据库驱动程序。
     *
     * @return String
     */
    public String getDbDriver() {
        return dbDriver;
    }

    /**
     * 功能：获取数据库产品名称。
     *
     * @return String
     */
    public String getDbProductName() {
        return dbProductName;
    }

    public String getDriverVersion() {
        return driverVersion;
    }

    private void parseConn(Connection conn) throws DatabaseException {
        try {
            DatabaseMetaData dbmtData = conn.getMetaData();
            dbURL = dbmtData.getURL();
            dbUser = dbmtData.getUserName();
            dbDriver = dbmtData.getDriverName();
            driverVersion = dbmtData.getDriverVersion();
            dbProductName = dbmtData.getDatabaseProductName();
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DatabaseException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public boolean isOracleDBProduct() {
        return (dbProductName.indexOf(OracleDBProduct) > -1);
    }

    public boolean isSybaseDBProduct() {
        return (dbProductName.indexOf(SybaseDBProduct) > -1);
    }
}
