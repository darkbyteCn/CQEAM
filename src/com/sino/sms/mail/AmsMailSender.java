package com.sino.sms.mail;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * User: zhoujs
 * Date: 2010-4-26 9:01:44
 * Function:
 */
public class AmsMailSender {
    public static boolean sendMail(MailConfig mailConfig, String reciever, String title, String content) {

        boolean isSuccess=true;

//        mailConfig.setSmtpHost("smtp.sinoprof.com");
//        mailConfig.setUserName("zhoujinsong");
//        mailConfig.setUserPassword("password");
//        mailConfig.setSender("zhoujinsong@sinoprof.com");

        MailSender sendmail = MailSender.getTextMailSender(mailConfig);
        System.out.println(mailConfig.getSmtpHost());
        System.out.println(mailConfig.getUserName());
        System.out.println(mailConfig.getUserPassword());
        System.out.println(mailConfig.getSender());

        try {
            Properties mailProps = new Properties();

            mailProps.put("mail.smtp.host", mailConfig.getSmtpHost()); //"mail.smtp.host"随便叫啥都行，"serverName"必须是真实可用的。
            mailProps.put("mail.smtp.auth", "true");
            Session mailSession = Session.getDefaultInstance(mailProps);
            // 创建 邮件的message，message对象包含了邮件众多有的部件，都是封装成了set方法去设置的

            MimeMessage message = new MimeMessage(mailSession);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(mailConfig.getSmtpHost(), mailConfig.getUserName(), mailConfig.getUserPassword());

            // 开始发送邮件
            System.out.println("正在发送邮件，请稍候.......");
            // 设置发信人
            message.setFrom(new InternetAddress(mailConfig.getSender()));
            //收信人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));

            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//                            message.setSubject("=?GB2312?B?" + enc.encode(content.getBytes()) + "?=");
            // 邮件标题
            String m = MimeUtility.encodeText(title, "gb2312", "B");
            message.setSubject(m);
            message.setText(content, "gb2312");
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("恭喜你，邮件已经成功发送!");
        } catch (Exception ex) {
            ex.printStackTrace();
            isSuccess=false;
        }

        return isSuccess;

    }
}
