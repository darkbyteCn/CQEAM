package com.sino.sms.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.ParserException;


/**
 * 邮件发送组件,具体的使用方法参照该类的main方法
 * @author Liudong
 */
public abstract class MailSender extends Authenticator{

    private String username = null;  //邮件发送帐号用户名
    private String userpasswd = null; //邮件发送帐号用户口令
    protected BodyPart messageBodyPart = null;
    protected Multipart multipart = new MimeMultipart("related");
    protected MimeMessage mailMessage = null;
    protected Session mailSession = null;
    protected Properties mailProperties = System.getProperties();
    protected InternetAddress mailFromAddress = null;
    protected InternetAddress mailToAddress = null;
    protected Authenticator authenticator = null;
    protected String mailSubject = "";
    protected Date mailSendDate = null;

    /**
     * 构造函数
     * @param  mailConfig
     */
    protected MailSender(MailConfig mailConfig) {
        this.username = mailConfig.getUserName();
        this.userpasswd = mailConfig.getUserPassword();
        mailProperties.put("mail.smtp.host", mailConfig.getSmtpHost());
        mailProperties.put("mail.smtp.auth", mailConfig.getSmtpAuth()); //设置smtp认证，很关键的一句
        mailSession = Session.getDefaultInstance(mailProperties);
        mailMessage = new MimeMessage(mailSession);
        messageBodyPart = new MimeBodyPart();
    }
    /**
     * 构造一个纯文本邮件发送实例
     * @param mailConfig
     * @return
     */
    public static MailSender getTextMailSender(MailConfig mailConfig) {
        return new MailSender(mailConfig) {
            public void setMailContent(String mailContent) throws MessagingException {
                messageBodyPart.setText(mailContent);
//                messageBodyPart.setContent(mailContent,"text/html;charset=GB2312");
                multipart.addBodyPart(messageBodyPart);
            }
        };
    }
    /**
     * 构造一个超文本邮件发送实例
     * @param mailConfig
     * @return
     */
    public static MailSender getHtmlMailSender(MailConfig mailConfig) {
        return new MailSender(mailConfig) {
            private ArrayList arrayList1 = new ArrayList();
            private ArrayList arrayList2 = new ArrayList();

            public void setMailContent(String mailContent) throws MessagingException {
                String htmlContent = getContent("<img src=", mailContent);
                messageBodyPart.setContent(htmlContent, "text/html;charset=GB2312");
                multipart.addBodyPart(messageBodyPart);
                //调用处理html文件中的图片方法
                processHtmlImage(mailContent);
            }

            //处理html页面上的图片方法如下：
            private void processHtmlImage(String mailContent) throws MessagingException {
                for (int i = 0; i < arrayList1.size(); i++) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource((String) arrayList1.get(i));
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    String contentId = "<" + (String) arrayList2.get(i) + ">";
                    messageBodyPart.setHeader("Content-ID", contentId);
                    messageBodyPart.setFileName((String) arrayList1.get(i));
                    multipart.addBodyPart(messageBodyPart);
                }
            }

            //处理要发送的html文件，主要是针对html文件中的图片
            private String getContent(String searchString, String mailContent) {
                try {
                    Parser parser = Parser.createParser(new String(mailContent.getBytes(), ISO8859_1));
                    Node[] images = parser.extractAllNodesThatAre(ImageTag.class);
                    for(int i=0;i<images.length;i++) {
                        ImageTag imgTag = (ImageTag) images[i];
                        if(!imgTag.getImageURL().toLowerCase().startsWith("http://"))
                            arrayList1.add(imgTag.getImageURL());
                    }
                } catch (UnsupportedEncodingException e1) {
                } catch (ParserException e) {}
                String afterReplaceStr = mailContent;
                //在html文件中用"cid:"+Content-ID来替换原来的图片链接
                for (int m = 0; m < arrayList1.size(); m++) {
                    arrayList2.add(createRandomStr());
                    String addString = "cid:" + (String) arrayList2.get(m);
                    afterReplaceStr = mailContent.replaceAll(
                            (String) arrayList1.get(m), addString);
                }
                return afterReplaceStr;
            }

            //产生一个随机字符串，为了给图片设定Content-ID值
            private String createRandomStr() {
                char[] randomChar = new char[8];
                for (int i = 0; i < 8; i++) {
                    randomChar[i] = (char) (Math.random() * 26 + 'a');
                }
                String replaceStr = new String(randomChar);
                return replaceStr;
            }

            private final static String ISO8859_1 = "8859_1";
        };
    }
    /**
     * 用于实现邮件发送用户验证
     * @see javax.mail.Authenticator#getPasswordAuthentication
     */
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, userpasswd);
    }

    /**
     * 设置邮件标题
     * @param mailSubject
     * @throws javax.mail.MessagingException
     */
    public void setSubject(String mailSubject) throws MessagingException {
        this.mailSubject = mailSubject;
        mailMessage.setSubject(mailSubject,"GB2312");
    }

    /**
     * 所有子类都需要实现的抽象方法，为了支持不同的邮件类型
     * @param mailContent
     * @throws javax.mail.MessagingException
     */
    protected abstract void setMailContent(String mailContent) throws MessagingException;

    /**
     * 设置邮件发送日期
     * @param sendDate
     * @throws javax.mail.MessagingException
     */
    public void setSendDate(Date sendDate) throws MessagingException {
        this.mailSendDate = sendDate;
        mailMessage.setSentDate(sendDate);
    }

    /**
     * 设置邮件发送附件
     * @param attachmentName
     * @throws javax.mail.MessagingException
     */
    public void setAttachments(String attachmentName) throws MessagingException {
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        int index = attachmentName.lastIndexOf(File.separator);
        String attachmentRealName = attachmentName.substring(index + 1);
        messageBodyPart.setFileName(attachmentRealName);
        multipart.addBodyPart(messageBodyPart);
    }

    /**
     * 设置发件人地址
     * @param mailFrom
     * @throws javax.mail.MessagingException
     */
    public void setMailFrom(String mailFrom) throws MessagingException {
        mailFromAddress = new InternetAddress(mailFrom);
        mailMessage.setFrom(mailFromAddress);
    }

    /**
     * 设置收件人地址，收件人类型为to,cc,bcc(大小写不限)
     * @param mailTo   邮件接收者地址
     * @param mailType 值为to,cc,bcc
     * @author Liudong
     */
    public void setMailTo(String[] mailTo, String mailType) throws Exception {
        for (int i = 0; i < mailTo.length; i++) {
            mailToAddress = new InternetAddress(mailTo[i]);
            if (mailType.equalsIgnoreCase("to")) {
                mailMessage.addRecipient(Message.RecipientType.TO,mailToAddress);
            } else if (mailType.equalsIgnoreCase("cc")) {
                mailMessage.addRecipient(Message.RecipientType.CC,mailToAddress);
            } else if (mailType.equalsIgnoreCase("bcc")) {
                mailMessage.addRecipient(Message.RecipientType.BCC,mailToAddress);
            } else {
                throw new Exception("Unknown mailType: " + mailType + "!");
            }
        }
    }

     public void setMailTo(String mailTo, String mailType) throws Exception {
            mailToAddress = new InternetAddress(mailTo);
            if (mailType.equalsIgnoreCase("to")) {
                mailMessage.addRecipient(Message.RecipientType.TO,mailToAddress);
            } else if (mailType.equalsIgnoreCase("cc")) {
                mailMessage.addRecipient(Message.RecipientType.CC,mailToAddress);
            } else if (mailType.equalsIgnoreCase("bcc")) {
                mailMessage.addRecipient(Message.RecipientType.BCC,mailToAddress);
            } else {
                throw new Exception("Unknown mailType: " + mailType + "!");
            }
    }
    /**
     * 开始发送邮件
     * @throws javax.mail.MessagingException
     * @throws javax.mail.SendFailedException
     */
    public void sendMail() throws MessagingException, SendFailedException {
        if (mailToAddress == null)
            throw new MessagingException("请你必须你填写收件人地址！");
        mailMessage.setContent(multipart);
        
        Transport.send(mailMessage);
    }



}