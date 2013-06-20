package com.sino.ams.yj.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 应急通信保障队伍表 AmsYjTeam</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0 
 */

public class AmsYjTeamDTO extends CheckBoxDTO {

    private String teamId = "";
    private String teamName = "";
    private String responsibilityUser = "";
    private String tel = "";
    private String quantity = "";
    private String situation = "";
    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar creationDate = null;
    private String createUser = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateUser = "";
    private String organizationOption = "";

    public AmsYjTeamDTO() {
        super();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }

    /**
     * 功能：设置应急通信保障队伍表属性 序号
     *
     * @param teamId AdvancedNumber
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 队伍名称
     *
     * @param teamName String
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 企业责任人
     *
     * @param responsibilityUser String
     */
    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 手机号
     *
     * @param tel String
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 队伍人数
     *
     * @param quantity AdvancedNumber
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 队伍基本情况及特点
     *
     * @param situation String
     */
    public void setSituation(String situation) {
        this.situation = situation;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 组织ID
     *
     * @param organizationId AdvancedNumber
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 创建日期
     *
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置应急通信保障队伍表属性 创建人
     *
     * @param createUser AdvancedNumber
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 功能：设置应急通信保障队伍表属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置应急通信保障队伍表属性 上次修改人
     *
     * @param lastUpdateUser AdvancedNumber
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }


    /**
     * 功能：获取应急通信保障队伍表属性 序号
     *
     * @return AdvancedNumber
     */
    public String getTeamId() {
        return this.teamId;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 队伍名称
     *
     * @return String
     */
    public String getTeamName() {
        return this.teamName;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 企业责任人
     *
     * @return String
     */
    public String getResponsibilityUser() {
        return this.responsibilityUser;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 手机号
     *
     * @return String
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 队伍人数
     *
     * @return AdvancedNumber
     */
    public String getQuantity() {
        return this.quantity;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 队伍基本情况及特点
     *
     * @return String
     */
    public String getSituation() {
        return this.situation;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 组织ID
     *
     * @return AdvancedNumber
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 创建日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 创建人
     *
     * @return AdvancedNumber
     */
    public String getCreateUser() {
        return this.createUser;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 上次修改日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取应急通信保障队伍表属性 上次修改人
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

}