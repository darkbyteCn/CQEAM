package com.sino.hn.sms.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.commerceware.cmpp.CMPP;
import com.commerceware.cmpp.DeliverFailException;
import com.commerceware.cmpp.OutOfBoundsException;
import com.commerceware.cmpp.UnknownPackException;
import com.commerceware.cmpp.cmppe_deliver_result;
import com.commerceware.cmpp.cmppe_result;
import com.commerceware.cmpp.cmppe_submit;
import com.commerceware.cmpp.conn_desc;
import com.sino.base.config.ConfigLoader;
import com.sino.base.config.SMSConfig;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.ConfigException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;

/**
 * <p>Description: 短消息发送程序包</p>
 * <p>Copyright: 北京思诺博信息科技有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class MessageSendService {
    private static SMSConfig smsConfig = null;

    private String message = "";
    private List phoneList;
    private int CMPPE_MAX_MSISDN_LEN = 14;
    private int MAX_PHONENUM = 20;
    private String sendID = "";

    static {
        try {
            smsConfig = ConfigLoader.loadSMSConfig();
        } catch (ConfigException ex) {
            ex.printLog();
        }
    }

    /**
     * 功能：无参构造函数。
     */
    public MessageSendService() {
        this("", null);
    }

    /**
     * 功能：构造函数重载。
     *
     * @param message String 消息内容
     */
    public MessageSendService(String message) {
        this(message, null);
    }

    /**
     * 功能：构造函数重载。
     *
     * @param message   String 消息内容
     * @param phoneList List 手机号列表
     */
    public MessageSendService(String message, List phoneList) {
        super();
        setMessage(message);
        setPhoneList(phoneList);
    }

    /**
     * 功能：设置短信息。
     *
     * @param message String
     */
    public void setMessage(String message) {
        if (!StrUtil.isEmpty(message)) {
           // Logger.logError("********MessageSendService*********message:"+message);
            this.message = message;
        }
    }

    /**
     * 功能：获取已经绑定的手机号数。
     *
     * @return int
     */
    public int getPhoneCount() {
        return phoneList.size();
    }

    /**
     * 功能：清除绑定的手机号码.
     */
    public void clearPhoneList() {
        phoneList.clear();
    }

    /**
     * 功能：清除短信息内容.
     */
    public void clearMessage() {
        this.message = "";
    }

    public void setSendID(String sendID){
        this.sendID = sendID;
    }
    /**
     * 功能：设置手机号列表。
     *
     * @param phoneList List
     */
    public void setPhoneList(List phoneList) {
        if (phoneList == null) {
            phoneList = new ArrayList();
        } else if (phoneList.size() > MAX_PHONENUM) {
            phoneList = phoneList.subList(0, MAX_PHONENUM);
        }
        List tmpList = new ArrayList();
        tmpList.clear();
        for (int i = 0; i < phoneList.size(); i++) {
            String tmpPhone = (String) phoneList.get(i);
            // Logger.logError("********MessageSendService*********tmpPhone:"+tmpPhone);
            int idex = tmpPhone.indexOf(";");
            if (idex != -1) {
                String[] arr =StrUtil.splitStr(tmpPhone,";");
                for (int j = 0; j < arr.length; j++) {
                    tmpList.add(arr[j]);
                }
            } else {
                tmpList.add(tmpPhone);
            }
        }
        this.phoneList = tmpList;
    }

    /**
     * 功能：添加手机号。
     *
     * @param phoneNum String
     */
    public void addPhoneNum(String phoneNum) {
        if (StrUtil.isEmpty(phoneNum)) {
            return;
        }
        if (phoneList.contains(phoneNum)) {
            return;
        }
        if (phoneList.size() >= MAX_PHONENUM) {
            return;
        }
        phoneList.add(phoneNum);
    }

    /**
     * 功能：获取本服务支持的最多手机数。
     *
     * @return int
     */
    public int getMaxPhoneNum() {
        return MAX_PHONENUM;
    }
    
    /**                                            
     * 功能：发送信息。
     *
     * @return boolean
     * @throws Exception 
     * @throws OutOfBoundsException
     */
    public boolean sendMessage() throws Exception {
        boolean operateResult = false;
        CMPP p = new CMPP();
        byte short_msg[] = new byte[150];
        cmppe_submit sub = new cmppe_submit();
        byte icp_id[] = new byte[10];
        icp_id[0] = 0x39;
        icp_id[1] = 0x31;
        icp_id[2] = 0x36;
        icp_id[3] = 0x31;
        icp_id[4] = 0x35;
        icp_id[5] = 0x34;
        icp_id[6] = 0;
        byte svc_type[] = new byte[6];
        svc_type[0] = 0x36;
        svc_type[1] = 0x31;
        svc_type[2] = 0x35;
        svc_type[3] = 0x34;
        svc_type[4] = 0;

        byte fee_type = 1;
        byte info_fee = 1;
        byte proto_id = 1;

        byte msg_mode = 0;
        byte priority = 0;
        byte fee_utype = 2;
        byte fee_user[] = new byte[CMPPE_MAX_MSISDN_LEN + 1];
        fee_user[0] = 0x39;
        fee_user[1] = 0x31;
        fee_user[2] = 0x36;
        fee_user[3] = 0x31;
        fee_user[4] = 0x35;
        fee_user[5] = 0x34;
        fee_user[6] = 0x0;
        fee_user[7] = 0x0;
        fee_user[8] = 0x0;
        fee_user[9] = 0x0;
        fee_user[10] = 0x0;
        fee_user[11] = 0x0;

        byte validate[] = new byte[10];
        validate[0] = 0;
        byte schedule[] = new byte[2];
        schedule[0] = 0;
        byte src_addr[] = new byte[13];
				src_addr[0] = 0x31;	
				src_addr[1] = 0x30;
				src_addr[2] = 0x36;
				src_addr[3] = 0x35;
				src_addr[4] = 0x37;
				src_addr[5] = 0x33;
				src_addr[6] = 0x33;
				src_addr[7] = 0x30;
				src_addr[8] = 0x30;
				src_addr[9] = 0x30;
				src_addr[10]= 0x30;
				src_addr[11]= 0x37;
				src_addr[12]= 0x0;

        byte du_count;
        byte dst_addr[][] = new byte[MAX_PHONENUM][15];
        du_count = buildDstAddr(dst_addr);
        byte data_coding = 15;
        byte sm_len = 0;
        try {
            
            int nTimestamp = (int) System.currentTimeMillis();
            conn_desc con = new conn_desc();

            //p.cmpp_connect_to_ismg(smsConfig.getHost(), smsConfig.getPort(), con);
            //p.cmpp_login(con, smsConfig.getIcpID(), smsConfig.getIcpAuth(), (byte) 0, 0x12, nTimestamp);
            //readPa(p, con);
            sub.set_icpid(icp_id);
            sub.set_svctype(svc_type);
            sub.set_feetype(fee_type);
            sub.set_infofee(info_fee);
            sub.set_protoid(proto_id);
            sub.set_msgmode(msg_mode);
            sub.set_priority(priority);
            sub.set_validate(validate);
            sub.set_schedule(schedule);
            sub.set_feeutype(fee_utype);
            sub.set_feeuser(fee_user);
            sub.set_srcaddr(src_addr);
            sub.set_dstaddr(dst_addr);
            sub.set_ducount(du_count);
            
            //TODO sj 可优化部分
            byte msgbyte[] = message.getBytes(smsConfig.getEncoding());
            sm_len = (byte) msgbyte.length;
            for (int i = 0; (i < msgbyte.length && i < 150); i++) {
                short_msg[i] = msgbyte[i];
            } 
            sub.set_msg(data_coding, sm_len, short_msg);

//            p.cmpp_submit(con, sub);
//            readPa(p, con);
//            p.cmpp_logout(con);
//            readPa(p, con);
            
            operateResult = true;
        } catch (UnsupportedEncodingException ex) {
            Logger.logError(ex);
            operateResult = false;
            throw ex;
        } catch (IOException ex) {
            Logger.logError(ex);
            operateResult = false;
            throw ex;
       /* } catch (OutOfBoundsException ex) {
            Logger.logError(ex);
            MessageProcessService processService = new MessageProcessService();
            processService.SaveErrorMsgLog(message, phoneList, "发送此短信时出现异常！", conn);
            operateResult = false;*/
        }catch(Exception ex){
            Logger.logError(ex);
            operateResult = false;
            throw ex;
        } 
        return operateResult;
    }

   
    
    /**
     * 功能：建立目标地址。
     *
     * @param dstAddr byte[][]
     * @return byte
     */
    private byte buildDstAddr(byte dstAddr[][]) {
        String tmpstr;
        int j;
        byte nCount = 0;
        for (int i = 0; i < phoneList.size(); i++) {
            if (i < MAX_PHONENUM) {
                tmpstr = (String) phoneList.get(i);
                byte tmpbyte[] = tmpstr.getBytes();
                for (j = 0; j < tmpbyte.length; j++) {
                    dstAddr[i][j] = tmpbyte[j];
                }
                dstAddr[i][j] = 0x0;
                nCount++;
            }
        }
        return nCount;
    }

    /**
     * @param p   CMPP
     * @param con conn_desc
     * @throws IOException
     */
    private void readPa(CMPP p, conn_desc con) throws IOException {
        cmppe_result cr = null;
        try {
          //  cr = p.readResPack(con);

            switch (cr.pack_id) {
                case CMPP.CMPPE_NACK_RESP:
                    break;

                case CMPP.CMPPE_LOGIN_RESP:
                    break;

                case CMPP.CMPPE_LOGOUT_RESP:
                    break;

                case CMPP.CMPPE_SUBMIT_RESP:
                    break;

                case CMPP.CMPPE_DELIVER:
                    cmppe_deliver_result cd = (cmppe_deliver_result) cr;
                    p.cmpp_send_deliver_resp(con, cd.seq, cd.stat);
                    break;

                case CMPP.CMPPE_CANCEL_RESP:
                    break;
                case CMPP.CMPPE_ACTIVE_RESP:
                    break;
                default:
                    break;
            }
        } /*catch (DeliverFailException ex) {
            Logger.logError(ex.getMessage());
            throw new IOException(ex.getMessage());
        } catch (UnknownPackException ex) {
            Logger.logError(ex.getMessage());
            throw new IOException(ex.getMessage());
        }*/ catch (IOException ex) {
            Logger.logError(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
    }
    
    
    /**                                            
     * 功能：发送信息。
     *
     * @return boolean
     * @throws OutOfBoundsException
     */
    public boolean sendMessage(Connection conn) throws DataHandleException {
        boolean operateResult = false ;
		try {
			operateResult = sendMessage();
		} catch (Exception ex) {
			Logger.logError(ex);
//            MessageProcessService processService = new MessageProcessService();
//            processService.SaveErrorMsgLog(message, phoneList, "发送此短信时出现异常！", conn);
            operateResult = false;
		} 
		
        SQLModel sqlModel = new SQLModel();
        List strArg = new ArrayList();
        String strSql = "insert into sendmsg_log(phone_num,message,sendID,result,creation_date) values(?,?,?,?,sysdate)";

        if(operateResult){
            for (int i = 0; i < phoneList.size(); i++) {
                String tmpstr = (String) phoneList.get(i);
                strArg.add(tmpstr);
                strArg.add(message);
                strArg.add(sendID);
                if(operateResult)
                    strArg.add("T");
                else
                    strArg.add("F");

                sqlModel.setArgs(strArg);
                sqlModel.setSqlStr(strSql);
                DBOperator.updateRecord(sqlModel, conn);
            }
        }
        //operateResult = true;

        return operateResult;
    }
 
}

