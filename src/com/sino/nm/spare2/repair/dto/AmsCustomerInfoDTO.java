package com.sino.nm.spare2.repair.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-2-29
 * To change this template use File | Settings | File Templates.
 */
public class AmsCustomerInfoDTO extends CheckBoxDTO {
    private String customer  ="";  //公司,服务方
    private String address  ="";      //地址和邮编
    private String contact  ="";      //联系人
    private String tel  ="";           //联系电话
    private String attribute1  ="";      //
    private int organizationId ;       // OU
    private String fax  ="";       //传真
    private String attribute2  ="";       //

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }
}
