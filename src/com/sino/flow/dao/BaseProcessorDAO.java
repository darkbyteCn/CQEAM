package com.sino.flow.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

public class BaseProcessorDAO {
    protected Connection conn = null;
    protected HttpServletRequest req = null;
    private String invalidLevel = "";//非法级别
    private String processMsg = "";

    public BaseProcessorDAO(Connection conn, HttpServletRequest req) {
        super();
        this.conn = conn;
        this.req = req;
    }

    public String getProcessMsg() {
        return processMsg;
    }

    public String getInvalidLevel() {
        return invalidLevel;
    }

    public void setProcessMsg(String processMsg) {
        this.processMsg = processMsg;
    }

    public void setInvalidLevel(String invalidLevel) {
        this.invalidLevel = invalidLevel;
    }
}
