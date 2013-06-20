package com.sino.ams.yj.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.math.AdvancedNumber;
import com.sino.base.exception.CalendarException;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;

/**
* <p>Title: 应急预案体系表 AmsYjPlan</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author jialongchuan
* @version 1.0
*/

public class AmsYjPlanDTO extends EtsFaAssetsDTO {

	private String planId = "";
	private String planLevel = "";
	private String proCity = "";
	private String planName = "";
	private String planNo = "";
	private String planType = "";
	private SimpleCalendar printDate = null;    //印发时间
	private String knowPost = "";
	private String leaderPost = "";
	private String isDrill = "";
	private String remark = "";
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar creationDate = null;           
	private String createUser = "";                          
	private SimpleCalendar lastUpdateDate = null;         
	private String lastUpdateUser = null;
	private String quantity = "";
    private String organizationOption = "";

    public AmsYjPlanDTO() {
		super();
		this.printDate = new SimpleCalendar();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();

	}

	/**
	 * 功能：设置应急预案体系表属性 序号
	 * @param planId AdvancedNumber
	 */
	public void setPlanId(String planId){
		this.planId = planId;
	}

	/**
	 * 功能：设置应急预案体系表属性 预案级别
	 * @param planLevel String
	 */
	public void setPlanLevel(String planLevel){
		this.planLevel = planLevel;
	}

	/**
	 * 功能：设置应急预案体系表属性 省或地市
	 * @param proCity String
	 */
	public void setProCity(String proCity){
		this.proCity = proCity;
	}

	/**
	 * 功能：设置应急预案体系表属性 预案名称
	 * @param planName String
	 */
	public void setPlanName(String planName){
		this.planName = planName;
	}

	/**
	 * 功能：设置应急预案体系表属性 预案编号
	 * @param planNo String
	 */
	public void setPlanNo(String planNo){
		this.planNo = planNo;
	}

	/**
	 * 功能：设置应急预案体系表属性 预案类别
	 * @param planType String
	 */
	public void setPlanType(String planType){
		this.planType = planType;
	}

	/**
	 * 功能：设置应急预案体系表属性 印发时间
	 * @param printDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setPrintDate(String printDate) throws CalendarException{
		this.printDate.setCalendarValue(printDate);
	}

	/**
	 * 功能：设置应急预案体系表属性 知晓范围(职位/岗位)
	 * @param knowPost String
	 */
	public void setKnowPost(String knowPost){
		this.knowPost = knowPost;
	}

	/**
	 * 功能：设置应急预案体系表属性 预案启动决策人的岗位/职位
	 * @param leaderPost String
	 */
	public void setLeaderPost(String leaderPost){
		this.leaderPost = leaderPost;
	}

	/**
	 * 功能：设置应急预案体系表属性 该预案是否演练过
	 * @param isDrill String
	 */
	public void setIsDrill(String isDrill){
		this.isDrill = isDrill;
	}

	/**
	 * 功能：设置应急预案体系表属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置应急预案体系表属性 ORGANIZATION_ID
	 * @param organizationId AdvancedNumber
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置应急预案体系表属性 CREATION_DATE
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置应急预案体系表属性 CREATE_USER
	 * @param createUser AdvancedNumber
	 */
	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	/**
	 * 功能：设置应急预案体系表属性 LAST_UPDATE_DATE
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置应急预案体系表属性 LAST_UPDATE_USER
	 * @param lastUpdateUser AdvancedNumber
	 */
	public void setLastUpdateUser(String lastUpdateUser){
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * 功能：设置应急预案体系表属性 知晓人的数量
	 * @param quantity String
	 */
	public void setQuantity(String quantity){
		this.quantity = quantity;
	}


	/**
	 * 功能：获取应急预案体系表属性 序号
	 * @return AdvancedNumber
	 */
	public String getPlanId() {
		return this.planId;
	}

	/**
	 * 功能：获取应急预案体系表属性 预案级别
	 * @return String
	 */
	public String getPlanLevel() {
		return this.planLevel;
	}

	/**
	 * 功能：获取应急预案体系表属性 省或地市
	 * @return String
	 */
	public String getProCity() {
		return this.proCity;
	}

	/**
	 * 功能：获取应急预案体系表属性 预案名称
	 * @return String
	 */
	public String getPlanName() {
		return this.planName;
	}

	/**
	 * 功能：获取应急预案体系表属性 预案编号
	 * @return String
	 */
	public String getPlanNo() {
		return this.planNo;
	}

	/**
	 * 功能：获取应急预案体系表属性 预案类别
	 * @return String
	 */
	public String getPlanType() {
		return this.planType;
	}

	/**
	 * 功能：获取应急预案体系表属性 印发时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getPrintDate() throws CalendarException {
		printDate.setCalPattern(getCalPattern());
		return this.printDate;
	}

	/**
	 * 功能：获取应急预案体系表属性 知晓范围(职位/岗位)
	 * @return String
	 */
	public String getKnowPost() {
		return this.knowPost;
	}

	/**
	 * 功能：获取应急预案体系表属性 预案启动决策人的岗位/职位
	 * @return String
	 */
	public String getLeaderPost() {
		return this.leaderPost;
	}

	/**
	 * 功能：获取应急预案体系表属性 该预案是否演练过
	 * @return String
	 */
	public String getIsDrill() {
		return this.isDrill;
	}

	/**
	 * 功能：获取应急预案体系表属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取应急预案体系表属性 ORGANIZATION_ID
	 * @return AdvancedNumber
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取应急预案体系表属性 CREATION_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取应急预案体系表属性 CREATE_USER
	 * @return AdvancedNumber
	 */
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	 * 功能：获取应急预案体系表属性 LAST_UPDATE_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取应急预案体系表属性 LAST_UPDATE_USER
	 * @return AdvancedNumber
	 */
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	/**
	 * 功能：获取应急预案体系表属性 知晓人的数量
	 * @return String
	 */
	public String getQuantity() {
		return this.quantity;
	}
    public String getOrganizationOption() {
           return organizationOption;
       }

       public void setOrganizationOption(String organizationOption) {
           this.organizationOption = organizationOption;
       }

}