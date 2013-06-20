package com.sino.ams.newasset.dto;

import com.sino.base.util.StrUtil;

/**
 * <p>Title: 设备地点变动历史表(EAM) AmsItemInfoHistory</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsItemInfoHistoryDTO extends AmsAssetsAddressVDTO {

    private String historyId = "";
    private String orderNo = "";
    private String orderCategory = "";
    private String orderDtlUrl = "";

    public AmsItemInfoHistoryDTO() {
        super();
    }

    /**
     * 功能：设置设备地点变动历史表(EAM)属性 历史ID
     * @param historyId String
     */
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    /**
     * 功能：设置设备地点变动历史表(EAM)属性 单据号(存储工单号、库存单据号以及资产单据号)
     * @param orderNo String
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 功能：设置设备地点变动历史表(EAM)属性 单据类型：1为工单，2为库存单据，3为资产单据
     * @param orderCategory String
     */
    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    /**
     * 功能：设置设备地点变动历史表(EAM)属性 单据详细信息URL
     * @param orderDtlUrl String
     */
    public void setOrderDtlUrl(String orderDtlUrl) {
        this.orderDtlUrl = orderDtlUrl;
    }


    /**
     * 功能：获取设备地点变动历史表(EAM)属性 历史ID
     * @return String
     */
    public String getHistoryId() {
        return this.historyId;
    }

    /**
     * 功能：获取设备地点变动历史表(EAM)属性 单据号(存储工单号、库存单据号以及资产单据号)
     * @return String
     */
    public String getOrderNo() {
        return this.orderNo;
    }

    /**
     * 功能：获取设备地点变动历史表(EAM)属性 单据类型：1为工单，2为库存单据，3为资产单据
     * @return String
     */
    public String getOrderCategory() {
        return this.orderCategory;
    }

    /**
     * 功能：获取设备地点变动历史表(EAM)属性 单据详细信息URL
     * @return String
     */
    public String getOrderDtlUrl() {
        return this.orderDtlUrl;
    }

    /**
     * 功能：判断该DTO包含的数据是否可以写入设备变动历史
     * @return boolean
     */
    public boolean needLogHistory() {
        String logValue = getItemCode() + getAddressId() +
                          getResponsibilityUser() + getResponsibilityDept();
        return!StrUtil.isEmpty(logValue);
    }
}
