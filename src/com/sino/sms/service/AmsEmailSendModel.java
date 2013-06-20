package com.sino.sms.service;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-1
 * Time: 17:03:07
 * To change this template use File | Settings | File Templates.
 */
public class AmsEmailSendModel {
    public SQLModel getConfig() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AEC.SERVERIP,\n" +
                "       AEC.USERNAME,\n" +
                "       AEC.PASSWORD,\n" +
                "       AEC.ENABLED,\n" +
                "       AEC.EMAIL_NAME\n" +
                "FROM   AMS_EMAIL_CONFIG AEC";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getUserMail() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AED.EMAIL,\n" +
                "       AED.MSG,\n" +
                "       AED.EMAIL_ID\n" +
                "FROM   AMS_EMAIL_DETAIL AED\n" +
                "WHERE  AED.SEND_FLAG = '0'";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel getUserTel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr ="SELECT TEL_NO,MSG,MSG_ID FROM EAM_MSG_DETAIL WHERE SEND_FLAG=0 AND char_length(TEL_NO)=11";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel updateUserMail(String emailId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE EAM_MSG_DETAIL\n" +
                "SET    SEND_FLAG  = 1,\n" +
                "       SEND_DATE  = GETDATE(),\n" +
                "       SEND_TIMES = SEND_TIMES + 1\n" +
                "WHERE  MSG_ID = ?";
        sqlArgs.add(emailId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
