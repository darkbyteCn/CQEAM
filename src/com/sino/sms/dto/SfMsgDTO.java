package com.sino.sms.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * <p>Title: SfMsgSendInfo</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfMsgDTO extends SinoBaseObject implements DTO {
    private String phoneNo = "";
    private String content = "";


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
