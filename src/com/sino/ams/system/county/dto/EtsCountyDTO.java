package com.sino.ams.system.county.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 区县表(EAM) EtsCounty</p>
 * <p>Description:程序自动生成DTO数据传输对象</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author zz_jlc
 * @version 1.0
 */

public class EtsCountyDTO extends CheckBoxDTO{

    private String countyCode  ;
    private String countyName = "";
    private String companyCode = "";
    private String enabled = "";
    private String countyCodeMis ; 
    private String countyCodeCoaCc = "";
	private SimpleCalendar creationDate = null;
    private int createdBy ;
	private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy  ;
    private int organizationId  ;

    public EtsCountyDTO() {
        super();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置区县表(EAM)属性 区县代码
     * @param countyCode String
     */
    public void setCountyCode(String countyCode){
        this.countyCode = countyCode;
    }

    /**
     * 功能：设置区县表(EAM)属性 区县名称
     * @param countyName String
     */
    public void setCountyName(String countyName){
        this.countyName = countyName;
    }

    /**
     * 功能：设置区县表(EAM)属性 地市公司代码
     * @param companyCode String
     */
    public void setCompanyCode(String companyCode){
        this.companyCode = companyCode;
    }

    /**
     * 功能：设置区县表(EAM)属性 有效标志
     * @param enabled String
     */
    public void setEnabled(String enabled){
        this.enabled = enabled;
    }

    /**
     * 功能：设置区县表(EAM)属性 对应MIS代码
     * @param countyCodeMis String
     */
    public void setCountyCodeMis(String countyCodeMis){
        this.countyCodeMis = countyCodeMis;
    }

    /**
     * 功能：设置区县表(EAM)属性 对应MIS COA_CC代码
     * @param countyCodeCoaCc String
     */
    public void setCountyCodeCoaCc(String countyCodeCoaCc){
        this.countyCodeCoaCc = countyCodeCoaCc;
    }

    /**
     * 功能：设置区县表(EAM)属性 创建日期
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException{
        if(!StrUtil.isEmpty(creationDate)){
        this.creationDate.setCalendarValue(creationDate);
        }
    }

    /**
     * 功能：设置区县表(EAM)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy){
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置区县表(EAM)属性 上次修改日期
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
       if(!StrUtil.isEmpty(lastUpdateDate)){
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);  }
    }

    /**
     * 功能：设置区县表(EAM)属性 上次修改人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy){
        this.lastUpdateBy = lastUpdateBy;
    }


    /**
     * 功能：获取区县表(EAM)属性 区县代码
     * @return String
     */
    public String getCountyCode() {
        return this.countyCode;
    }

    /**
     * 功能：获取区县表(EAM)属性 区县名称
     * @return String
     */
    public String getCountyName() {
        return this.countyName;
    }

    /**
     * 功能：获取区县表(EAM)属性 地市公司代码
     * @return String
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

    /**
     * 功能：获取区县表(EAM)属性 有效标志
     * @return String
     */
    public String getEnabled() {
        return this.enabled;
    }

    /**
     * 功能：获取区县表(EAM)属性 对应MIS代码
     * @return String
     */
    public String getCountyCodeMis() {
        return this.countyCodeMis;
    }

    /**
     * 功能：获取区县表(EAM)属性 对应MIS COA_CC代码
     * @return String
     */
    public String getCountyCodeCoaCc() {
        return this.countyCodeCoaCc;
    }

    /**
     * 功能：获取区县表(EAM)属性 创建日期
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    /**
     * 功能：获取区县表(EAM)属性 创建人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取区县表(EAM)属性 上次修改日期
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取区县表(EAM)属性 上次修改人
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

}