package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-12
 * Time: 16:42:34
 * To change this template use File | Settings | File Templates.
 */
public class UploadFileDTO extends CommonRecordDTO {
    private String orderPkValue = "";
    private String orderType = "";
    private String orderTable = "";
    private String orderPkName = "";
    private String fileType = "";
    private String fileName = "";
    private String filePath = "";
    private int createdBy = SyBaseSQLUtil.NULL_INT_VALUE;
    private String description = "";
    private SimpleCalendar creationDate = null;

    public UploadFileDTO() {
        super();
        this.creationDate = new SimpleCalendar();
    }

    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderPkValue() {
        return orderPkValue;
    }

    public void setOrderPkValue(String orderPkValue) {
        this.orderPkValue = orderPkValue;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(String orderTable) {
        this.orderTable = orderTable;
    }

    public String getOrderPkName() {
        return orderPkName;
    }

    public void setOrderPkName(String orderPkName) {
        this.orderPkName = orderPkName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
