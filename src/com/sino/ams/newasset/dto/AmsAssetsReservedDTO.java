package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 资产事务保留表 AmsAssetsReserved</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsReservedDTO extends CommonRecordDTO {

    private String transId = null;
    private SimpleCalendar reservedDate = null;
    private String assetId = null;
    private String barcode = "";

    public AmsAssetsReservedDTO() {
        super();
        this.reservedDate = new SimpleCalendar();
    }

    /**
     * 功能：设置资产事务保留表属性 单据ID
     * @param transId String
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 功能：设置资产事务保留表属性 保留日期
     * @param reservedDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setReservedDate(String reservedDate) throws CalendarException {
        this.reservedDate.setCalendarValue(reservedDate);
    }

    /**
     * 功能：设置资产事务保留表属性 资产ID
     * @param assetId String
     */
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    /**
     * 功能：设置资产事务保留表属性 标签号
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    /**
     * 功能：获取资产事务保留表属性 单据ID
     * @return String
     */
    public String getTransId() {
        return this.transId;
    }

    /**
     * 功能：获取资产事务保留表属性 保留日期
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getReservedDate() throws CalendarException {
        reservedDate.setCalPattern(getCalPattern());
        return this.reservedDate;
    }

    /**
     * 功能：获取资产事务保留表属性 资产ID
     * @return String
     */
    public String getAssetId() {
        return this.assetId;
    }

    /**
     * 功能：获取资产事务保留表属性 标签号
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }
}
