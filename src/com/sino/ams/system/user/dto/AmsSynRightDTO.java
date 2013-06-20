package com.sino.ams.system.user.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-6-12
 * Time: 11:20:10
 * To change this template use File | Settings | File Templates.
 */
public class AmsSynRightDTO extends CheckBoxDTO {
    private  String userName="";
    private  int userId;
    private  String loginName="";
    private  String synRightId="";
    private  int organizationId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSynRightId() {
        return synRightId;
    }

    public void setSynRightId(String synRightId) {
        this.synRightId = synRightId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
