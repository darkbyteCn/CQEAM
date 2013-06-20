package com.sino.sms.service;

import java.sql.Connection;

import com.sino.base.config.SMSConfig;

/**
 * Created by IntelliJ IDEA.
 * User: GLJ
 * Date: 2008-4-30
 * Time: 9:17:16
 * To change this template use File | Settings | File Templates.
 */
public class MsgSendFactory {
    public static SMSSend getMsgSendFactory(SMSConfig smsConfig, Connection conn, String systemName) {
        if (systemName.indexOf("É½Î÷") != -1) {
            return new SXMsgSend_SX(smsConfig, conn);
        } else if (systemName.indexOf("ÉÂÎ÷") != -1) {
            return new SXMsgSend_SN(smsConfig);
        } else {
            return new SXMsgSend_NM(smsConfig);
        }
    }
}
