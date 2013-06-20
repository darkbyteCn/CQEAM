package com.sino.ams.yj.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 卫星电话信息表 AmsYjIsatphone</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author jialongchuan
 * @version 1.0
 */

public class AmsYjIsatphoneDTO extends CheckBoxDTO {

    private String isatphoneId = "";
    private String isatphoneName = "";
    private String isatphoneType = "";
    private String isatphoneModel = "";
    private String isatphoneAddress = "";
    private String tel = "";
    private String buyingTime = "";
    private String cost = "";
    private String usedNumber = "";
    private int  organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar creationDate = null;
    private String createUser = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateUser = "";
    private String organizationOption = "";
    private String resourceId = "";
    private String equipmentName = "";

    public AmsYjIsatphoneDTO() {
        super();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();

    }

    /**
     * 功能：设置卫星电话信息表属性 序号
     *
     * @param isatphoneId AdvancedNumber
     */
    public void setIsatphoneId(String isatphoneId) {
        this.isatphoneId = isatphoneId;
    }

    /**
     * 功能：设置卫星电话信息表属性 储备物资名称
     *
     * @param isatphoneName String
     */
    public void setIsatphoneName(String isatphoneName) {
        this.isatphoneName = isatphoneName;
    }

    /**
     * 功能：设置卫星电话信息表属性 类别
     *
     * @param isatphoneType String
     */
    public void setIsatphoneType(String isatphoneType) {
        this.isatphoneType = isatphoneType;
    }

    /**
     * 功能：设置卫星电话信息表属性 型号
     *
     * @param isatphoneModel String
     */
    public void setIsatphoneModel(String isatphoneModel) {
        this.isatphoneModel = isatphoneModel;
    }

    /**
     * 功能：设置卫星电话信息表属性 存储地点或单位
     *
     * @param isatphoneAddress String
     */
    public void setIsatphoneAddress(String isatphoneAddress) {
        this.isatphoneAddress = isatphoneAddress;
    }

    /**
     * 功能：设置卫星电话信息表属性 电话号码
     *
     * @param tel String
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 功能：设置卫星电话信息表属性 购置时间
     *
     * @param buyingTime String
     */
    public void setBuyingTime(String buyingTime) {
        this.buyingTime = buyingTime;
    }

    /**
     * 功能：设置卫星电话信息表属性 资产原值(万元)
     *
     * @param cost String
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * 功能：设置卫星电话信息表属性 使用次数（年）
     *
     * @param usedNumber String
     */
    public void setUsedNumber(String usedNumber) {
        this.usedNumber = usedNumber;
    }

    /**
     * 功能：设置卫星电话信息表属性 组织ID
     *
     * @param organizationId AdvancedNumber
     */
    public void setOrganizationId(int  organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置卫星电话信息表属性 创建日期
     *
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置卫星电话信息表属性 创建人
     *
     * @param createUser AdvancedNumber
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 功能：设置卫星电话信息表属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置卫星电话信息表属性 上次修改人
     *
     * @param lastUpdateUser AdvancedNumber
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }


    /**
     * 功能：获取卫星电话信息表属性 序号
     *
     * @return AdvancedNumber
     */
    public String getIsatphoneId() {
        return this.isatphoneId;
    }

    /**
     * 功能：获取卫星电话信息表属性 储备物资名称
     *
     * @return String
     */
    public String getIsatphoneName() {
        return this.isatphoneName;
    }

    /**
     * 功能：获取卫星电话信息表属性 类别
     *
     * @return String
     */
    public String getIsatphoneType() {
        return this.isatphoneType;
    }

    /**
     * 功能：获取卫星电话信息表属性 型号
     *
     * @return String
     */
    public String getIsatphoneModel() {
        return this.isatphoneModel;
    }

    /**
     * 功能：获取卫星电话信息表属性 存储地点或单位
     *
     * @return String
     */
    public String getIsatphoneAddress() {
        return this.isatphoneAddress;
    }

    /**
     * 功能：获取卫星电话信息表属性 电话号码
     *
     * @return String
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 功能：获取卫星电话信息表属性 购置时间
     *
     * @return String
     */
    public String getBuyingTime() {
        return this.buyingTime;
    }

    /**
     * 功能：获取卫星电话信息表属性 资产原值(万元)
     *
     * @return String
     */
    public String getCost() {
        return this.cost;
    }

    /**
     * 功能：获取卫星电话信息表属性 使用次数（年）
     *
     * @return String
     */
    public String getUsedNumber() {
        return this.usedNumber;
    }

    /**
     * 功能：获取卫星电话信息表属性 组织ID
     *
     * @return AdvancedNumber
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取卫星电话信息表属性 创建日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    /**
     * 功能：获取卫星电话信息表属性 创建人
     *
     * @return AdvancedNumber
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 功能：获取卫星电话信息表属性 上次修改日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取卫星电话信息表属性 上次修改人
     *
     * @return AdvancedNumber
     */
    public String getLastUpdateUser() {
        return this.lastUpdateUser;
    }

    public String getOrganizationOption() {
        return organizationOption;
    }

    public void setOrganizationOption(String organizationOption) {
        this.organizationOption = organizationOption;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}