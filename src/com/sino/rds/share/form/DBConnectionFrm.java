package com.sino.rds.share.form;

import com.sino.base.util.StrUtil;
import com.sino.rds.appbase.form.RDSBaseFrm;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionFrm extends RDSBaseFrm {
    private String dbPwd = "";
    private String dbUser = "";
    private String dbUrl = "";
    private String dbDriver = "";
    private String connectionId = "";
    private String connectionName = "";

    public DBConnectionFrm(){
        primaryKeyName = "connectionId";        
    }

    public String getDbPwd() {
        return this.dbPwd;
    }

    public String getDbUser() {
        return this.dbUser;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public String getDbDriver() {
        return this.dbDriver;
    }

    public String getConnectionId() {
        return this.connectionId;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public Connection getDBConnection() throws Exception{
        Connection conn = null;
        Class.forName(dbDriver);
        if (StrUtil.isEmpty(dbUser)) {
            conn = DriverManager.getConnection(dbUrl);
        } else {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        }
        return conn;
    }
}