package com.sino.ams.yearchecktaskmanager.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.base.db.conn.DBManager;

public class AssetsCheckTaskOrderGeneretor {
    private Connection conn = null;
    private String type = null; //����ʲô���͵ı��
    private String companyCode = null;
    private int orderLength;

    /**
     * ���ܣ����캯��
     * @param conn        Connection ���ݿ�����
     * @param companyCode String ��˾����
     * @param type        String ��������
     */
    public AssetsCheckTaskOrderGeneretor(Connection conn, String companyCode, String type) {
        this.conn = conn;
        this.companyCode = companyCode;
        this.type = type;
    }

    /**
     * ���ܣ��������ɵ������ֲ����ַ������ȡ���������Ĭ��Ϊ6λ
     * @param orderLength int
     */
    public void setOrderLength(int orderLength) {
        this.orderLength = orderLength;
    }
    

	/**
     * ���ܣ����ù�˾����
     * @param companyCode String
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * ���ܣ����õ�������
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * ���ܣ����ɸ��ֵ��ݺš�
     * @return String
     * @throws SQLException
     */
    public String getOrderNum() throws SQLException {
        String no = null;
        CallableStatement cst = null;
        String sqlStr = "{CALL dbo.AYCK_GET_ORDER_NO(?, ?, ?, ?)}";
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
}
