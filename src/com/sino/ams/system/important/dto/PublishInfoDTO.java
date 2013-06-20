package com.sino.ams.system.important.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * User: T_zhoujinsong
 * Date: 2011-3-14 17:31:17
 * Function:
 */

public class PublishInfoDTO extends SinoBaseObject implements DTO {
    private String publishId = "";
    private String title = "";
    private String contents = "";
    private String docType = "";
    private String titleOnlyFlag = "";
    private String enabled = "";
    private int publishUserId;
    private String publishUserName = "";
    private String publishDate = "";
    private String publishStartDate = "";
    private String publishEndDate = "";
    private String infoType = "";
    private String isNew = "";

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

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

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }


    public String getSeeUserType() {
        return seeUserType;
    }

    public void setSeeUserType(String seeUserType) {
        this.seeUserType = seeUserType;
    }
}
