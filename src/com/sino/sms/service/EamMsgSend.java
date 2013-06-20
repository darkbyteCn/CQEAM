package com.sino.sms.service;
import org.apache.axis.client.Service;
import org.apache.axis.client.Call;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.mail.Message;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import com.sino.base.db.conn.DBManager;
import java.sql.Connection;

import com.sino.base.log.Logger;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;
/**
 * Created by IntelliJ IDEA.

 * To change this template use File | Settings | File Templates.
 */
public class EamMsgSend {
    private static String shortMsgUrl = null;
     public void sendMsg() {
        Service service = new Service();
        Call call = null;
        Connection conn = null;
        try {
            call = (Call) service.createCall();
            call.setOperationName(new QName("http://10.217.226.7:8080/SMSController/services/receiverMessage", "receiverMessage"));
            call.setTargetEndpointAddress(new java.net.URL("http://10.217.226.7:8080/SMSController/services/receiverMessage"));


           conn = DBManager.getDBConnection();
            AmsEmailSendModel sendModel = new AmsEmailSendModel();
            SimpleQuery sq1 = new SimpleQuery(sendModel.getUserTel(), conn);
            sq1.executeQuery();
            RowSet rows1 = sq1.getSearchResult();
            Row row = null;
            if (rows1 != null && !rows1.isEmpty()) {
                for (int i = 0; i < rows1.getSize(); i++) {
                    try {
                        row = rows1.getRow(i);
                        String content = (String) row.getValue("MSG");
//                        String content = "小宇哥，能收着？";
                        String id = (String) row.getValue("MSG_ID");
                        String telNo = (String) row.getValue("TEL_NO");
//                        String email = "renhuanyu@chin";

//                        String user =email.substring(0,email.indexOf("@"));
                        System.out.println("1111111111111");
                        String ret = (String) call.invoke(new Object[]
                                {"NM", "sinoeam", "eammsg", "2",
                                        "<ReceiverMessage>" +
                                                "<uid>"+"也可以通过OA账号"+"</uid>" +
                                                "<phone>" + telNo + "</phone>" +
                                                "<sendMessages>" + content + "</sendMessages>" +
                                                "<sendby></sendby>" +
                                                "<flag>0</flag>" +
                                                "<remark1></remark1>" +
                                                "<remark2></remark2>" +
                                                "<remark3></remark3>" +
                                                "<remark4></remark4>" +
                                                "<remark5></remark5>" +
                                                "</ReceiverMessage>"});
                        if (!ret.equals("10000")) {
                            Logger.logError("发送短信出错啦，错误代码为：" + ret);
                        }else{
//                        DBOperator.updateRecord(sendModel.updateUserMail(id), conn);
                        	}
                    } catch (Exception ee) {
                        Logger.logError(ee);
                    }

                }
            }
        } catch (Exception e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
        }
    }


    public static void main(String[] args) throws MalformedURLException, RemoteException, ServiceException {
        EamMsgSend sender = new EamMsgSend();
        sender.sendMsg();
    }
}
