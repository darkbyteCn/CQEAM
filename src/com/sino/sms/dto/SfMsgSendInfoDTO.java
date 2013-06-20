package com.sino.sms.dto;

import java.sql.Timestamp;

import com.sino.base.SinoBaseObject;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SfMsgSendInfo</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfMsgSendInfoDTO extends SinoBaseObject implements DTO {
    private String msgSendId = "";
    private String msgDefineId = "";
    private String msgCellPhone = "";
    private Timestamp firstSendTime = null;
    private Timestamp lastSendTime = null;
    private String sendTimes = "";

    private String needResend = "";
    private String resendMaxtimes = "";
    private String resendDistance = "";

    private String msgCategoryId = "";
    private String msgContent = "";
    private Timestamp creationDate = null;
    private int createdBy = -1;
    private String hasProcessed = "N";
    private String actId = "";

    public void setMsgSendId(String msgSendId) {
        this.msgSendId = msgSendId;
    }

    public void setMsgDefineId(String msgDefineId) {
        this.msgDefineId = msgDefineId;
    }

    public void setMsgCellPhone(String msgCellPhone) {
        this.msgCellPhone = msgCellPhone;
    }

    public void setFirstSendTime(String firstSendTime) throws CalendarException {
        if (!StrUtil.isEmpty(firstSendTime)) {
            SimpleCalendar cal = new SimpleCalendar(firstSendTime);
            this.firstSendTime = cal.getSQLTimestamp();
        }
    }

    public void setLastSendTime(String lastSendTime) throws CalendarException {
        if (!StrUtil.isEmpty(lastSendTime)) {
            SimpleCalendar cal = new SimpleCalendar(lastSendTime);
            this.lastSendTime = cal.getSQLTimestamp();
        }
    }

    public void setSendTimes(String sendTimes) {
        this.sendTimes = sendTimes;
    }

    public String getMsgSendId() {
        return this.msgSendId;
    }

    public String getMsgDefineId() {
        return this.msgDefineId;
    }

    public String getMsgCellPhone() {
        return this.msgCellPhone;
    }

    public Timestamp getFirstSendTime() {
        return this.firstSendTime;
    }

    public Timestamp getLastSendTime() {
        return this.lastSendTime;
    }

    public String getSendTimes() {
        return this.sendTimes;
    }

    public void setNeedResend(String needResend) {
        this.needResend = needResend;
    }

    public void setResendMaxtimes(String resendMaxtimes) {
        this.resendMaxtimes = resendMaxtimes;
    }

    public void setResendDistance(String resendDistance) {
        this.resendDistance = resendDistance;
    }

    public String getNeedResend() {
        return this.needResend;
    }

    public String getResendMaxtimes() {
        return this.resendMaxtimes;
    }

    public String getResendDistance() {
        return this.resendDistance;
    }

    public void setMsgCategoryId(String msgCategoryId) {
        this.msgCategoryId = msgCategoryId;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            SimpleCalendar cal = new SimpleCalendar(creationDate);
            this.creationDate = cal.getSQLTimestamp();
        }
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getMsgCategoryId() {
        return this.msgCategoryId;
    }

    public String getMsgContent() {
        return this.msgContent;
    }

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public String getHasProcessed() {
        return hasProcessed;
    }

    public void setHasProcessed(String hasProcessed) {
        this.hasProcessed = hasProcessed;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }
}
