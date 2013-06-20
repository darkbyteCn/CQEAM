package com.sino.ams.yj.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.exception.CalendarException;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;

/**
 * <p>Title: 应急演练情况表 AmsYjDrill</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author jialongchuan
 * @version 1.0
 */

public class AmsYjDrillDTO extends EtsFaAssetsDTO {

    private String drillId = "";              //
    private String drillType = "";
    private String drillNature = "";
    private String drillName = "";
    private SimpleCalendar drillDate = null;
    private String drillAddress = "";
    private String peopleQuality = "";        //
    private String equipmentQuantity = "";
    private String plan1= "";
    private String question = "";
    private String isPerfect = "";
    private SimpleCalendar planDate = null;
    private String remark = "";
    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar creationDate = null;
    private String createUser = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateUser = "";
    private String organizationOption = "";

    public AmsYjDrillDTO() {
        super();
        this.drillDate = new SimpleCalendar();
        this.planDate = new SimpleCalendar();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();

    }

    /**
     * 功能：设置应急演练情况表属性 序号
     *
     * @param drillId AdvancedNumber
     */
    public void setDrillId(String drillId) {
        this.drillId = drillId;
    }

    /**
     * 功能：设置应急演练情况表属性 演练类型
     *
     * @param drillType String
     */
    public void setDrillType(String drillType) {
        this.drillType = drillType;
    }

    /**
     * 功能：设置应急演练情况表属性 演练性质
     *
     * @param drillNature String
     */
    public void setDrillNature(String drillNature) {
        this.drillNature = drillNature;
    }

    /**
     * 功能：设置应急演练情况表属性 演练名称
     *
     * @param drillName String
     */
    public void setDrillName(String drillName) {
        this.drillName = drillName;
    }

    /**
     * 功能：设置应急演练情况表属性 演练时间
     *
     * @param drillDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDrillDate(String drillDate) throws CalendarException {
        this.drillDate.setCalendarValue(drillDate);
    }

    /**
     * 功能：设置应急演练情况表属性 演练地点
     *
     * @param drillAddress String
     */
    public void setDrillAddress(String drillAddress) {
        this.drillAddress = drillAddress;
    }

    /**
     * 功能：设置应急演练情况表属性 参演人数
     *
     * @param peopleQuality AdvancedNumber
     */
    public void setPeopleQuality(String peopleQuality) {
        this.peopleQuality = peopleQuality;
    }

    /**
     * 功能：设置应急演练情况表属性 演练装备数量
     *
     * @param equipmentQuantity AdvancedNumber
     */
    public void setEquipmentQuantity(String equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    /**
     * 功能：设置应急演练情况表属性 演练存在问题
     *
     * @param question String
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 功能：设置应急演练情况表属性 是否需要完善预案
     *
     * @param isPerfect String
     */
    public void setIsPerfect(String isPerfect) {
        this.isPerfect = isPerfect;
    }

    /**
     * 功能：设置应急演练情况表属性 完善预案计划时间
     *
     * @param planDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setPlanDate(String planDate) throws CalendarException {
        this.planDate.setCalendarValue(planDate);
    }

    /**
     * 功能：设置应急演练情况表属性 备注
     *
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置应急演练情况表属性 组织ID
     *
     * @param organizationId AdvancedNumber
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置应急演练情况表属性 创建日期
     *
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置应急演练情况表属性 创建人
     *
     * @param createUser AdvancedNumber
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 功能：设置应急演练情况表属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置应急演练情况表属性 上次修改人
     *
     * @param lastUpdateUser AdvancedNumber
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }


    /**
     * 功能：获取应急演练情况表属性 序号
     *
     * @return AdvancedNumber
     */
    public String getDrillId() {
        return this.drillId;
    }

    /**
     * 功能：获取应急演练情况表属性 演练类型
     *
     * @return String
     */
    public String getDrillType() {
        return this.drillType;
    }

    /**
     * 功能：获取应急演练情况表属性 演练性质
     *
     * @return String
     */
    public String getDrillNature() {
        return this.drillNature;
    }

    /**
     * 功能：获取应急演练情况表属性 演练名称
     *
     * @return String
     */
    public String getDrillName() {
        return this.drillName;
    }

    /**
     * 功能：获取应急演练情况表属性 演练时间
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getDrillDate() throws CalendarException {
        drillDate.setCalPattern(getCalPattern());
        return this.drillDate;
    }

    /**
     * 功能：获取应急演练情况表属性 演练地点
     *
     * @return String
     */
    public String getDrillAddress() {
        return this.drillAddress;
    }

    /**
     * 功能：获取应急演练情况表属性 参演人数
     *
     * @return AdvancedNumber
     */
    public String getPeopleQuality() {
        return this.peopleQuality;
    }

    /**
     * 功能：获取应急演练情况表属性 演练装备数量
     *
     * @return AdvancedNumber
     */
    public String getEquipmentQuantity() {
        return this.equipmentQuantity;
    }

    /**
     * 功能：获取应急演练情况表属性 演练存在问题
     *
     * @return String
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * 功能：获取应急演练情况表属性 是否需要完善预案
     *
     * @return String
     */
    public String getIsPerfect() {
        return this.isPerfect;
    }

    /**
     * 功能：获取应急演练情况表属性 完善预案计划时间
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getPlanDate() throws CalendarException {
        planDate.setCalPattern(getCalPattern());
        return this.planDate;
    }

    /**
     * 功能：获取应急演练情况表属性 备注
     *
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取应急演练情况表属性 组织ID
     *
     * @return AdvancedNumber
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取应急演练情况表属性 创建日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    /**
     * 功能：获取应急演练情况表属性 创建人
     *
     * @return AdvancedNumber
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 功能：获取应急演练情况表属性 上次修改日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
     * 功能：获取应急演练情况表属性 上次修改人
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

	public String getPlan1() {
		return plan1;
	}

	public void setPlan1(String plan1) {
		this.plan1 = plan1;
	}
}