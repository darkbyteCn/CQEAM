package com.sino.sms.service;

import com.sino.base.config.SMSConfig;

/**
 * Created by IntelliJ IDEA.
 * User: GLJ
 * Date: 2008-4-30 ÄÚÃÉ¹Å×¨ÓÃ
 * Time: 9:53:37
 * To change this template use File | Settings | File Templates.
 */
public class SXMsgSend_NM extends SMSSend {

    public SXMsgSend_NM(SMSConfig smsConfig) {
        super(smsConfig);
    }

    public boolean sendMessage(String rcvCellPhone, String msgContent) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean sendMail(SMSConfig smsConfig, String rcvMail, String msgContent) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean sendMessage(String rcvCellPhone, String msgContent, String sendCellPhone) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
