package com.sino.ams.adjunct.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-29
 * Time: 10:05:52
 * To change this template use File | Settings | File Templates.
 */
public class FileDTO extends SinoBaseObject implements DTO {
    private String orderPkValue = "";
    private String orderPkName = "";
    private String orderType = "";
    private String orderTable = "";
    private String fileType = "";
    private String fileName = "";
    private String filePath = "";
    private int createdBy = 0;
    private String description = "";

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
