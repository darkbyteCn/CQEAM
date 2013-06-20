package com.sino.sinoflow.user.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 用戶信息 PersonOrderDTO</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class PersonOrderDTO implements DTO {
    private String transNo = "";
    private String transName = "";
    private String composeUser = "";
    
    private SimpleCalendar startDate;
    private SimpleCalendar endDate;

    public PersonOrderDTO() {
        super();
        startDate = new SimpleCalendar();
        endDate = new SimpleCalendar();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getComposeUser() {
        return composeUser;
    }

    public void setComposeUser(String composeUser) {
        this.composeUser = composeUser;
    }

    public SimpleCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(String  startDate) {
        if (StrUtil.isNotEmpty(startDate)) {
            this.startDate = new SimpleCalendar(startDate);
        }
    }

    public SimpleCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(String  endDate) {
        if (StrUtil.isNotEmpty(endDate)) {
            this.endDate = new SimpleCalendar(endDate);
        }
    }
}
