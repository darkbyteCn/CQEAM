package com.sino.sms.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.SinoBaseObject;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SfMsgDefine</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfMsgDefineDTO extends SinoBaseObject implements DTO {
    private String msgDefineId = "";
    private String msgCategoryId = "";
    private String msgContent = "";
    private Timestamp creationDate = null;
    private int createdBy = SyBaseSQLUtil.NULL_INT_VALUE ;
    private String hasProcessed = "N";
    private String actId = "";
    private List cellPhones = null;
    private String cellPhone = "";
    private String mail = "";
    private List mails = null;
    private String applyNumber = "";
    private String userId = "";

    public SfMsgDefineDTO() {
        super();
        cellPhones = new ArrayList();
    }

    public void addCellPhone(String cellPhone) {
        if (StrUtil.isEmpty(cellPhone)) {
            return;
        }
        if (StrUtil.isNumber(cellPhone) && cellPhone.length() <= 15 && cellPhone.length() >= 11) {
            cellPhones.add(cellPhone);
        }
    }

    public List getCellPhones() {
        return cellPhones;
    }

    public void setMsgDefineId(String msgDefineId) {
        this.msgDefineId = msgDefineId;
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

    public String getMsgDefineId() {
        return this.msgDefineId;
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

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
