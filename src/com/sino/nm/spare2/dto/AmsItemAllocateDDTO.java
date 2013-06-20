package com.sino.nm.spare2.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-11-8
 */
public class AmsItemAllocateDDTO extends SinoBaseObject implements DTO {
    private String transId = "";
    private String detailId = "";
    private String itemCode = "";
    private String quantity = "";
    private String batchNo = "";
    private String sourceId = "";
    private String barcode = "";
    private String itemCategory = "";
    private String objectNo = "";
    private int allocateQty = -1;
    private int freightQty = -1;    //本次发运数量


    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    public int getAllocateQty() {
        return allocateQty;
    }

    public void setAllocateQty(int allocateQty) {
        this.allocateQty = allocateQty;
    }

    public int getFreightQty() {
        return freightQty;
    }

    public void setFreightQty(int freightQty) {
        this.freightQty = freightQty;
    }
}
