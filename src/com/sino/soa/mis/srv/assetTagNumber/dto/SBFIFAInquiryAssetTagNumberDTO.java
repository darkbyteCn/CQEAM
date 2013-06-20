package com.sino.soa.mis.srv.assetTagNumber.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-18
 * Time: 10:17:18
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFAInquiryAssetTagNumberDTO extends CheckBoxDTO {
    private String bookTypeCode = "";
    private String tagNumber = "";

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }
}
