package com.sino.sms.service;

import com.sino.base.config.SMSConfig;
import com.sino.base.util.StrUtil;
import com.sino.sms.mail.AmsMailSender;
import com.sino.sms.mail.MailConfig;

/**
 * Created by IntelliJ IDEA.
 * User: GLJ
 * Date: 2008-4-30     ÉÂÎ÷×¨ÓÃ
 * Time: 9:54:09
 * To change this template use File | Settings | File Templates.
 */
public class SXMsgSend_SN extends SMSSend {

    public SXMsgSend_SN(SMSConfig smsConfig) {
        super(smsConfig);
    }

    public boolean sendMessage(String rcvCellPhone, String msgContent) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean sendMail(SMSConfig smsConfig, String rcvMail, String msgContent) {
        MailConfig mailConfig = new MailConfig();
//        mailConfig.setSmtpHost("smtp.sinoprof.com");
//        mailConfig.setUserName("wangfeng");
//        mailConfig.setUserPassword("0707wangfeng");
//        mailConfig.setSender("wangfeng@sinoprof.com");
        mailConfig.setSmtpHost(smsConfig.getSmtpHost());
        mailConfig.setUserName(smsConfig.getUsername());
        mailConfig.setUserPassword(smsConfig.getPassword());
        mailConfig.setSender(smsConfig.getSender());

        String outStr = "";

        if (StrUtil.isNotEmpty(rcvMail)) {
            AmsMailSender.sendMail(mailConfig, "zhoujinsong@sinoprof.com", "Hello", msgContent);
        }


        return outStr.equals("0");
    }

    public boolean sendMessage(String rcvCellPhone, String msgContent, String sendCellPhone) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
