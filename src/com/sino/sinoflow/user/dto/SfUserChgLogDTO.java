package com.sino.sinoflow.user.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CalendarUtililyDTO;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 用戶信息 SfUser</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfUserChgLogDTO extends CalendarUtililyDTO implements DTO {
    private String logId = "";
    private int userId = 0;
    private String userName = "";
    private String chgType = "";
    private int operator = 0;
    private String operatorName = "";
    private String logFrom = "";
    private String logTo = "";
    private String remark = "";
    private SimpleCalendar operatorDate;

    public SfUserChgLogDTO() {
        super();
        operatorDate = new SimpleCalendar();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getChgType() {
        return chgType;
    }

    public void setChgType(String chgType) {
        this.chgType = chgType;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public String getLogFrom() {
        return logFrom;
    }

    public void setLogFrom(String logFrom) {
        this.logFrom = logFrom;
    }

    public String getLogTo() {
        return logTo;
    }

    public void setLogTo(String logTo) {
        this.logTo = logTo;
    }

    public SimpleCalendar getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(String  operatorDate) {
        if (StrUtil.isNotEmpty(operatorDate)) {
            this.operatorDate = new SimpleCalendar(operatorDate);
        }
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
