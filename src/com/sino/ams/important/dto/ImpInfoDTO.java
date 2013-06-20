package com.sino.ams.important.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;


public class ImpInfoDTO extends SinoBaseObject implements DTO {

    //private int publishId  ;
    private String publishId = "";
    private int publishUserId  ;
    private String publishStartDate = "";
    private String publishEndDate = "";
    private String publishDate = "";
    private String docType = "";
    private String title = "";
    private String contents = "";
    private String titleOnlyFlag = "";
    private String publishUserName = "";
    private String disabled = "";
    private String seeUserType = "";


    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPublishEndDate() {
        return publishEndDate;
    }

    public void setPublishEndDate(String publishEndDate) {
        this.publishEndDate = publishEndDate;
    }

/*    public int getPublishId() {
        return publishId;
    }

    public void setPublishId(int publishId) {
        this.publishId = publishId;
    }*/

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }
    
    public String getPublishStartDate() {
        return publishStartDate;
    }

    public void setPublishStartDate(String publishStartDate) {
        this.publishStartDate = publishStartDate;
    }

    public int getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(int publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOnlyFlag() {
        return titleOnlyFlag;
    }

    public void setTitleOnlyFlag(String titleOnlyFlag) {
        this.titleOnlyFlag = titleOnlyFlag;
    }


    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }



    public String getSeeUserType() {
        return seeUserType;
    }

    public void setSeeUserType(String seeUserType) {
        this.seeUserType = seeUserType;
    }
}
