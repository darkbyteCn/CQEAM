package com.sino.flow.dto;

import com.sino.base.dto.DTO;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-11-6
 * Time: 15:12:08
 * 这个DTO是FlowExtend方法的参数DTO，
 * 为了便于扩展，flowExtend方法将不直接传参数，而是包装成DTO传入
 */
public class FlowExtendDTO implements DTO {
    private String key = "";
    private String orgId = "";
    private String appointType = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAppointType() {
        return appointType;
    }

    public void setAppointType(String appointType) {
        this.appointType = appointType;
    }
}
