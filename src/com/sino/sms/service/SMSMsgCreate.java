package com.sino.sms.service;

import com.sino.base.db.conn.DBManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import com.sino.base.log.Logger;

/**
 * Created by IntelliJ IDEA.

 * To change this template use File | Settings | File Templates.
 */
public class SMSMsgCreate {
     Connection conn = null;

     public void AutoCreateMsg()  {

                CallableStatement cstmt = null;
	        try {
                 conn = DBManager.getDBConnection();
		            String callSql="";
		            	callSql = "{CALL dbo.APP_MSG_ALL}";
                         cstmt = conn.prepareCall(callSql);
                         cstmt.execute();
	        }catch(Exception e){
	        	Logger.logError(e);
		    }finally{
		         DBManager.closeDBConnection(conn);
		    }

	    }
     public static void main(String[] args)  {
        SMSMsgCreate sender = new SMSMsgCreate();
        sender.AutoCreateMsg();
    }
}
