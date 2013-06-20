package com.sino.sms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.config.SMSConfig;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.util.StrUtil;
import com.sino.sms.mail.AmsMailSender;
import com.sino.sms.mail.MailConfig;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.service.tinysoap.client.AlertClient;

/**
 * User: zhoujs
 * Date: 2008-4-2        山西专用
 * Time: 11:08:17
 * Function:
 */
public class SXMsgSend_SX extends SMSSend {
    private Connection conn = null;

    public SXMsgSend_SX(SMSConfig smsConfig, Connection conn) {
        super(smsConfig);
        this.conn = conn;
    }

    /**
     * 发送消息，如果是多个号码，用";"分隔
     * @param rcvCellPhone
     * @param msgContent
     * @return
     */
    public boolean sendMessage(String rcvCellPhone, String msgContent) {
        String seqCode = "";
        String outStr = "";
        try {
            if (StrUtil.isNotEmpty(rcvCellPhone)) {
                SeqProducer sq = new SeqProducer(conn);
                seqCode = "AMS_" + sq.getStrNextSeq("SMS_S");
                outStr = AlertClient.sendNoAlert(SMSConstant.uri, SMSConstant.appId, seqCode, msgContent, rcvCellPhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outStr.equals("0");
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

        mailConfig.setSmtpHost("smtp.sinoprof.com");
        mailConfig.setUserName("zhoujinsong");
        mailConfig.setUserPassword("password");
        mailConfig.setSender("zhoujinsong@sinoprof.com");

        if (StrUtil.isNotEmpty(rcvMail)) {
            AmsMailSender.sendMail(mailConfig, rcvMail, "Hello", msgContent);
            outStr="0";
        }


        return outStr.equals("0");
    }

    public boolean sendMessage(String rcvCellPhone, String msgContent, String sendCellPhone) {
        return false;
    }

//    public void sendMessages(DTOSet msgDTOSet) {
//        for (int i = 0; i < msgDTOSet.getSize(); i++) {
//            SfMsgDTO msgDTO = (SfMsgDTO) msgDTOSet.getDTO(i);
//            if (msgDTO.getPhoneNo() != "") {
//                sendMessage(msgDTO.getPhoneNo(), msgDTO.getContent());
//            }
//        }
//    }
//
//    /**
//     * 发送信息至多人
//     * @param phoneList
//     * @param message
//     */
//    public void sendMessageToUsers(List phoneList, String message) {
//        for (int i = 0; i < phoneList.size(); i++) {
//            String phone = phoneList.get(i).toString();
//            sendMessage(phone, message);
//        }
//    }
}
