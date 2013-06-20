package com.sino.sms.service;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.config.SMSConfig;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.DBActionConstant;
import com.sino.base.db.dtoprocess.DTOConstraint;
import com.sino.base.db.dtoprocess.DTOProcessProperty;
import com.sino.base.db.dtoprocess.DTOProcessor;
import com.sino.base.db.dtoprocess.DTOProcessorFactory;
import com.sino.base.db.dtoprocess.MainConstraint;
import com.sino.base.db.dtoprocess.SubConstraint;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DBActionException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.TimeException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.sms.constant.SMSConstant;
import com.sino.sms.dto.SfMsgDefineDTO;
import com.sino.sms.dto.SfMsgSendInfoDTO;
import com.sino.sms.model.MSGProcessModel;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动应急采购系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class MessageProcessService {
    private String startTime = "";
    private String endTime = "";

    private MSGProcessModel msgProcessModel = null;

    public MessageProcessService() {
        super();
        msgProcessModel = new MSGProcessModel();
    }

    void setSMSConfig(SMSConfig smsConfig) {
        this.startTime = smsConfig.getStartTime();
        this.endTime = smsConfig.getEndTime();
    }

    /**
     * 保存消息
     * @param conn Connection
     * @param msg  SfMsgDefineDTO
     * @return boolean
     * @throws DataHandleException
     */
    public boolean saveMessage(Connection conn, SfMsgDefineDTO msg) throws DataHandleException {
        boolean operateResult = false;
        try {
            if (msg != null) {
                DTOSet sendDTOs = new DTOSet();
                List cellPhones = msg.getCellPhones();
                int cellCount = cellPhones.size();
                if (cellCount > 0) {
                    String cellPhone = "";
                    for (int i = 0; i < cellCount; i++) {
                        cellPhone = String.valueOf(cellPhones.get(i));
                        SfMsgSendInfoDTO sendDTO = new SfMsgSendInfoDTO();
                        sendDTO.setMsgCellPhone(cellPhone);
                        sendDTO.setSendTimes("0");
                        sendDTO.setHasProcessed(SMSConstant.MSG_PROCESSED_NO);
                        sendDTO.setActId(msg.getActId());
                        sendDTOs.addDTO(sendDTO);
                    }
                }

                DTOProcessorFactory factory = DTOProcessorFactory.getFactory();
                DTOProcessProperty dtoProperty = new DTOProcessProperty();
                dtoProperty.setDBConnection(conn);
                dtoProperty.setDBAction(DBActionConstant.INSERT);


                DTOProcessor dtoProcessor = factory.getDTOProcessor(dtoProperty);


                DTOConstraint constraint = new DTOConstraint();
                MainConstraint mainConstraint = new MainConstraint();
                Map pkMap = new HashMap();
                pkMap.put("msgDefineId", "SF_MSG_DEFINE_SEQ");
                mainConstraint.setPrimaryKeyMap(pkMap);
                constraint.setMainConstraint(mainConstraint);

                SubConstraint subConstraint = new SubConstraint();
                Map subPKMap = new HashMap();
                subPKMap.put("msgSendId", "SF_MSG_SEND_INFO_SEQ");
                subConstraint.setPrimaryKeyMap(subPKMap);
                subConstraint.setForeignKey("msgDefineId");
                constraint.setSubConstraint(subConstraint);

                dtoProcessor.setConstraint(constraint);
                dtoProcessor.setDTO(msg, sendDTOs);
                operateResult = dtoProcessor.processAction();

            }
        } catch (DTOException ex) {
            ex.printLog();
            throw new DataHandleException(ex.getMessage());
        } catch (DBActionException ex) {
            ex.printLog();
            throw new DataHandleException(ex.getMessage());
        }
        return operateResult;
    }

    /**
     * 功能：停止给某手机号发送actId消息。由流程调用。
     * @param conn      Connection
     * @param actId     String 流程中的办理情况
     * @param cellPhone String 手机号
     * @return boolean
     * @throws DataHandleException
     */
    public boolean finishPhoneMessage(Connection conn, String actId, String cellPhone) throws DataHandleException {
        boolean operateResult = true;
        SQLModel sqlModel = msgProcessModel.getFinishPhoneMessageModel(actId, cellPhone);
        DBOperator.updateRecord(sqlModel, conn);
        return operateResult;
    }

    /**
     * 功能：停止发送符合条件的消息。由消息服务器线程调用。
     * @param conn Connection
     * @throws DataHandleException
     */
    void finishMessages(Connection conn) throws DataHandleException {
        SQLModel sqlModel = msgProcessModel.getFinishOrderMessageModel();
        DBOperator.updateRecord(sqlModel, conn);
        sqlModel = msgProcessModel.getFinishMessageModel();
        DBOperator.updateRecord(sqlModel, conn);
    }


    /**
     * 功能：获取所有需要发送的信息。由消息服务器线程调用。
     * @param conn Connection
     * @return DTOSet
     * @throws QueryException
     */
    DTOSet getNeedSendMessages(Connection conn) throws QueryException {
        DTOSet messages = null;
        SQLModel sqlModel = msgProcessModel.getNeedSendMsgModel();
        SimpleQuery queryBean = new SimpleQuery(sqlModel, conn);
        queryBean.setDTOClassName(SfMsgDefineDTO.class.getName());
        queryBean.executeQuery();
        messages = queryBean.getDTOSet();
        return messages;
    }

    /**
     * 功能：获取消息的发送情况。由消息服务器线程调用。
     * @param conn    Connection
     * @param message SfMsgDefineDTO
     * @return DTOSet
     * @throws QueryException
     */
    DTOSet getMsgSendInfos(Connection conn, SfMsgDefineDTO message) throws QueryException {
        DTOSet sendInfos = null;
        SQLModel sqlModel = msgProcessModel.getMsgSendModel(message);
        SimpleQuery queryBean = new SimpleQuery(sqlModel, conn);
        queryBean.setDTOClassName(SfMsgSendInfoDTO.class.getName());
        queryBean.executeQuery();
        if (queryBean.hasResult()) {
            sendInfos = queryBean.getDTOSet();
        }
        return sendInfos;
    }

    /**
     * 功能：判断是否需要发送信息。由消息服务器线程调用。
     * @param conn    Connection
     * @param sendDTO SfMsgSendInfoDTO
     * @return boolean
     * @throws QueryException
     */
    boolean needSend(Connection conn, SfMsgSendInfoDTO sendDTO) throws QueryException {
        boolean needSend = false;
        String cellPhone = sendDTO.getMsgCellPhone();
        String needResend = sendDTO.getNeedResend();
        String maxResendTimes = sendDTO.getResendMaxtimes();
        String hasSendTimes = sendDTO.getSendTimes();
        try {
            String currTime = getCurrTime(conn);
            String timeValue = new SimpleCalendar(currTime).getSimpleTime().getTimeValue();
            if (!StrUtil.isEmpty(cellPhone) && timeValue.compareTo(startTime) >= 0 && timeValue.compareTo(endTime) <= 0) {
                if (hasSendTimes.equals("0")) { //还未发送过消息。
                    needSend = true;
                } else if (needResend.equalsIgnoreCase(SMSConstant.NEED_RESEND_YES)) {
                    Timestamp tmpTime = sendDTO.getLastSendTime();
                    if (tmpTime == null) {
                        tmpTime = sendDTO.getFirstSendTime();
                    }
                    if (StrUtil.isEmpty(tmpTime)) {
                        needSend = true;
                    } else {
                        SimpleCalendar lastSendTime = new SimpleCalendar(tmpTime);
                        SimpleCalendar nowTime = new SimpleCalendar(currTime);
                        nowTime.adjust(CalendarConstant.MINUTE, (0 - Integer.parseInt(sendDTO.getResendDistance())));
                        needSend = (nowTime.getTimeInMillis() > lastSendTime.getTimeInMillis());
                        needSend = (needSend && (maxResendTimes.compareTo(hasSendTimes) > 0));
                    }
                }
            }
        } catch (NumberFormatException ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        } catch (CalendarException ex) {
            ex.printLog();
            throw new QueryException(ex.getMessage());
        } catch (TimeException ex) {
            ex.printLog();
            throw new QueryException(ex.getMessage());
        }
        return needSend;
    }

    /**
     * 功能：获取当前时间。内部调用。
     * @param conn Connection
     * @return String
     * @throws QueryException
     */
    private String getCurrTime(Connection conn) throws QueryException {
        String currTime = "";
        try {
            SQLModel sqlModel = msgProcessModel.getCurrTimeModel();
            SimpleQuery queryBean = new SimpleQuery(sqlModel, conn);
            queryBean.executeQuery();
            if (queryBean.hasResult()) {
                currTime = queryBean.getFirstRow().getStrValue(0);
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex.getMessage());
        }
        return currTime;
    }

    /**
     * 功能：处理消息发送记录。由消息服务器线程调用。
     * @param conn      Connection 数据库连接
     * @param sendDTO   SfMsgSendInfoDTO 消息
     * @param cellPhone String 手机号
     * @throws DataHandleException 插入数据库记录出错时抛出该异常
     */
    void processSendLog(Connection conn, SfMsgSendInfoDTO sendDTO, String cellPhone) throws DataHandleException {
        SQLModel sqlModel = msgProcessModel.getSendLogModel(sendDTO, cellPhone);
        DBOperator.updateRecord(sqlModel, conn);
    }
}
