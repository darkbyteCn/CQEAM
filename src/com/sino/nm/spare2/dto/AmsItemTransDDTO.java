package com.sino.nm.spare2.dto;


import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: 备件业务明细表(AMS) AmsItemTransD</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsItemTransDDTO extends CheckBoxDTO {

    private String transId = "";
    private String lineId = "";
    private String detailId = "";
    private int organizationId = 0;
    private String itemCode = "";
    private int quantity = 0;
    private int confirmQuantity = 0;
    private int curOnhandQty =0;
    private int onhandQty = 0;
    private String objectNo = "";

    public AmsItemTransDDTO() {
        super();


    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 事务交易ID
     * @param transId String
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 行ID
     * @param lineId String
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 明细ID
     * @param detailId String
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 OU
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 设备代码
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 数量
     * @param quantity String
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 确认数量
     * @param confirmQuantity String
     */
    public void setConfirmQuantity(int confirmQuantity) {
        this.confirmQuantity = confirmQuantity;
    }

    /**
     * 功能：设置备件业务明细表(AMS)属性 当前库存现有量
     * @param curOnhandQty String
     */
    public void setCurOnhandQty(int curOnhandQty) {
        this.curOnhandQty = curOnhandQty;
    }


    /**
     * 功能：获取备件业务明细表(AMS)属性 事务交易ID
     * @return String
     */
    public String getTransId() {
        return this.transId;
    }

    /**
     * 功能：获取备件业务明细表(AMS)属性 行ID
     * @return String
     */
    public String getLineId() {
        return this.lineId;
    }

    /**
     * 功能：获取备件业务明细表(AMS)属性 明细ID
     * @return String
     */
    public String getDetailId() {
        return this.detailId;
    }

    /**
     * 功能：获取备件业务明细表(AMS)属性 OU
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取备件业务明细表(AMS)属性 设备代码
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取备件业务明细表(AMS)属性 数量
     * @return String
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * 功能：获取备件业务明细表(AMS)属性 确认数量
     * @return String
     */
    public int getConfirmQuantity() {
        return this.confirmQuantity;
    }

    /**
     * 功能：获取备件业务明细表(AMS)属性 当前库存现有量
     * @return String
     */
    public int getCurOnhandQty() {
        return this.curOnhandQty;
    }


    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    public int getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(int onhandQty) {
        this.onhandQty = onhandQty;
    }
}