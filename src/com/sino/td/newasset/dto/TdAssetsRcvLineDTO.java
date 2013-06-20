package com.sino.td.newasset.dto;

/**
 * <p>Title: 资产调拨接收行表(用于部门间和公司间资产调拨) AmsAssetsRcvLine</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class TdAssetsRcvLineDTO extends TdAssetsTransLineDTO {

    private String receiveHeaderId = "";
    private String receiveLineId = "";
    private String transLineId = "";

    public TdAssetsRcvLineDTO() {
        super();
    }

    /**
     * 功能：设置资产调拨接收行表(用于部门间和公司间资产调拨)属性 接收单行ID
     * @param receiveLineId String
     */
    public void setReceiveLineId(String receiveLineId) {
        this.receiveLineId = receiveLineId;
    }

    /**
     * 功能：设置资产调拨接收行表(用于部门间和公司间资产调拨)属性 调拨单行ID
     * @param transLineId String
     */
    public void setTransLineId(String transLineId) {
        this.transLineId = transLineId;
    }

    public void setReceiveHeaderId(String receiveHeaderId) {
        this.receiveHeaderId = receiveHeaderId;
    }

    /**
     * 功能：获取资产调拨接收行表(用于部门间和公司间资产调拨)属性 接收单行ID
     * @return String
     */
    public String getReceiveLineId() {
        return this.receiveLineId;
    }

    /**
     * 功能：获取资产调拨接收行表(用于部门间和公司间资产调拨)属性 调拨单行ID
     * @return String
     */
    public String getTransLineId() {
        return this.transLineId;
    }

    public String getReceiveHeaderId() {
        return receiveHeaderId;
    }
}

