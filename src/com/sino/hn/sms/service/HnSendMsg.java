package com.sino.hn.sms.service;

import java.sql.Connection;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.log.Logger;
import com.sino.sms.service.AmsEmailSendModel;

/**
 * 
 * @系统名称: 
 * @功能描述: 河南移动短信发送
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 28, 2011
 */
public class HnSendMsg {
	public void sendMsg() {
		Connection conn = null;
		try {
			conn = DBManager.getDBConnection();
			MessageSendService sendService = new MessageSendService();
//			sendService.setPhoneList( new ArrayList() );
			
			AmsEmailSendModel sendModel = new AmsEmailSendModel();
			SimpleQuery sq1 = new SimpleQuery(sendModel.getUserTel(), conn);
			sq1.executeQuery();
			RowSet rows1 = sq1.getSearchResult();
			Row row = null;
			if (rows1 != null && !rows1.isEmpty()) {
				String id = null;
				String content = null;
				String telNo = null;
				int length ;
				for (int i = 0; i < rows1.getSize(); i++) {
					boolean hasSendResult = false ;
					try {
						row = rows1.getRow(i);
						content = (String) row.getValue("MSG");
						id = (String) row.getValue("MSG_ID");
						telNo = (String) row.getValue("TEL_NO"); 
						length = content.length() / 65 + 1;
                        String sendContent = ""; 
                        boolean sendResult = false;
                         
                        for (int k = 0; k < length; k++) {
                            if ((k + 1) * 65 > content.length()) {
                                sendContent = content.substring(k * 65, content.length());
                            } else {
                                sendContent = content.substring(k * 65, (k + 1) * 65);
                            }
                            if (k == length - 1) {
                                sendContent += "[完]";
                            } else {
                                sendContent += "[" + (k + 1) + "]";
                            }
                            sendService.setSendID( id );
                            sendService.clearMessage();
                            sendService.clearPhoneList();
    						sendService.setMessage( sendContent );
    						sendService.addPhoneNum( telNo );
    						sendResult = sendService.sendMessage(); 
    						if( sendResult ){
    							hasSendResult = sendResult;
    						}
                        }  
                         
					} catch (Exception ee) {
						Logger.logError(ee);
					} finally{
						if( hasSendResult ){
							//只要发送过，不管是否发全了，都记录发送过
//							DBOperator.updateRecord(sendModel.updateUserMail(id),
//								conn);
						}
					}

				}
			}
		} catch (Exception e) {
			Logger.logError(e);
		} finally {
			DBManager.closeDBConnection(conn);
		}

	}
	
	public static void main(String[] args) {
		HnSendMsg msg = new HnSendMsg();
		msg.sendMsg();
	}
}
