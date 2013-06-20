package com.sino.flow.bean;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;
import com.sino.sms.service.MessageProcessService;

/**
 * Created by wwb.
 * User: demo
 * Date: 2007-2-8
 * Time: 13:37:59
 * 短消息有关的类
 */
public class MsgBusiness {
    private HttpServletRequest req;
    private Connection conn;

    public MsgBusiness(HttpServletRequest req, Connection conn) {
        this.req = req;
        this.conn = conn;
    }

    public void sendMsg(String userId, String msgConstent) throws DataHandleException, QueryException, ContainerException {
        sendMsg(userId, msgConstent, SMSConstant.MSG_CATEGORY_FLOW);
    }

    public void sendMsg(String userId, String msgContent, String msgCategoryId) throws DataHandleException, QueryException, ContainerException {
        if (msgContent == null || msgContent.equals("")) {
            return;
        }
        if (userId == null || userId.equals("")) {
            return;
        }
        String phone = getCellphoneByUserId(userId);
        if (msgContent.length() > 65) {
            saveMsg4Split(msgContent, phone, msgCategoryId, "");
        } else {
            saveMsg(msgContent, phone, msgCategoryId);
        }
    }

    /**
     * @param msgContent
     * @param msgCategoryId
     * @throws DataHandleException
     * @throws QueryException
     * @throws ContainerException
     */
    private void saveMsg(String msgContent, String cellPhone, String msgCategoryId) throws DataHandleException, QueryException, ContainerException {
        MessageProcessService msgService = new MessageProcessService();
        SfMsgDefineDTO msg = new SfMsgDefineDTO();
        msg.setMsgCategoryId(msgCategoryId);
        msg.setMsgContent(msgContent);
        //  msg.setActId(actId);
        //  String cellPhone = getCellphoneByUserId(userId);
        msg.addCellPhone(cellPhone);
        msgService.saveMessage(conn, msg);
//        msgService.finishPhoneMessage(conn, actId, cellPhone);
    }

    /**
     * 公司内部人员，根据userId取电话
     *
     * @param userId
     * @return
     * @throws com.sino.base.exception.QueryException
     *
     * @throws com.sino.base.exception.ContainerException
     *
     */
    private String getCellphoneByUserId(String userId) throws QueryException, ContainerException {
        String cellphone = "";
        String sql = "SELECT SUP.MSG_CELL_PHONE FROM SF_USER_PHONE SUP WHERE SUP.USER_ID = ?";
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        al.add(userId);
        sm.setSqlStr(sql);
        sm.setArgs(al);
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && !rs.isEmpty()) {
            Row row = rs.getRow(0);
            cellphone = (String) row.getValue("MSG_CELL_PHONE");
        }
        return cellphone;
    }

    //切取长短信
    private void saveMsg4Split(String content, String phone, String msgCategory, String actId) throws DataHandleException {
        MessageProcessService msgService = new MessageProcessService();
        SfMsgDefineDTO msg = new SfMsgDefineDTO();
        msg.setMsgCategoryId(msgCategory);
        //电话号码放在循环外面，否则，几次循环后，消息的电话号码全部存在ArrayList里面。消息服务会认为
        //是一条短信通知多个电话
        msg.addCellPhone(phone);
        int length = content.length() / 65 + 1;
        String[] contentArray = new String[length];
        for (int i = 0; i < length; i++) {
            if ((i + 1) * 65 > content.length()) {
                contentArray[i] = content.substring(i * 65, content.length());
            } else {
                contentArray[i] = content.substring(i * 65, (i + 1) * 65);
            }
            if (i == length - 1) {
                contentArray[i] += "[完]";
            } else {
                contentArray[i] += "[" + (i + 1) + "]";
            }
            msg.setMsgContent(contentArray[i]);
            msg.setActId(actId);
            msgService.saveMessage(conn, msg);

        }
    }
}
