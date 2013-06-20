package com.sino.soa.mis.srv.projectInfo.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.math.AdvancedNumber;

/**
 * <p>Title: 项目信息服务 SrvProjectInfo</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SrvProjectInfoDTO extends CheckBoxDTO {

	private String projectId = null;
	private String name = "";
	private String segment1 = "";
	private String projectType = "";
	private String projectStatusCode = "";
	private SimpleCalendar startDate = null;
	private SimpleCalendar completionDate = null;
	private String enabledFlag = "";
	private String source = "";
	private SimpleCalendar creationDate = null;
	private AdvancedNumber createdBy = null;
	private SimpleCalendar lastUpdateDate = null;
	private AdvancedNumber lastUpdateBy = null;
	private String  misProjectId = null;
	private AdvancedNumber organizationId = null;
	private String projectClass = "";
	private String description = "";
	private String projectManager = "";
	private String pmProjectReference = "";
	private String pmProductCode = "";
	private String OuOption ="";
	private String assetsType ="";
	private String organizationName = "";
    private String orgCode="";
	public SrvProjectInfoDTO() {
		super();
		this.startDate = new SimpleCalendar();
		this.completionDate = new SimpleCalendar();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
		this.createdBy = new AdvancedNumber();
		this.lastUpdateBy = new AdvancedNumber();
		this.organizationId = new AdvancedNumber();
	}

	/**
	 * @return the ouOption
	 */
	public String getOuOption() {
		return OuOption;
	}

	/**
	 * @param ouOption the ouOption to set
	 */
	public void setOuOption(String ouOption) {
		OuOption = ouOption;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 序列号
	 * @param projectId AdvancedNumber
	 */
	public void setProjectId(String projectId){
		this.projectId = projectId;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目名称
	 * @param name String
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目编号
	 * @param segment1 String
	 */
	public void setSegment1(String segment1){
		this.segment1 = segment1;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目类型
	 * @param projectType String
	 */
	public void setProjectType(String projectType){
		this.projectType = projectType;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目状态代码
	 * @param projectStatusCode String
	 */
	public void setProjectStatusCode(String projectStatusCode){
		this.projectStatusCode = projectStatusCode;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目开始日期
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException{
		this.startDate.setCalendarValue(startDate);
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目完成日期
	 * @param completionDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCompletionDate(String completionDate) throws CalendarException{
		this.completionDate.setCalendarValue(completionDate);
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 'Y'
	 * @param enabledFlag String
	 */
	public void setEnabledFlag(String enabledFlag){
		this.enabledFlag = enabledFlag;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 来源 'MIS'  or 'EAM'
	 * @param source String
	 */
	public void setSource(String source){
		this.source = source;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 创建人
	 * @param createdBy AdvancedNumber
	 */
	public void setCreatedBy(AdvancedNumber createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 上次修改人
	 * @param lastUpdateBy AdvancedNumber
	 */
	public void setLastUpdateBy(AdvancedNumber lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 MIS项目ID
	 * @param misProjectId AdvancedNumber
	 */
	public void setMisProjectId(String  misProjectId){
		this.misProjectId = misProjectId;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 组织ID
	 * @param organizationId AdvancedNumber
	 */
	public void setOrganizationId(AdvancedNumber organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目分类
	 * @param projectClass String
	 */
	public void setProjectClass(String projectClass){
		this.projectClass = projectClass;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目摘要
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 项目经理
	 * @param projectManager String
	 */
	public void setProjectManager(String projectManager){
		this.projectManager = projectManager;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 产品来源参考
	 * @param pmProjectReference String
	 */
	public void setPmProjectReference(String pmProjectReference){
		this.pmProjectReference = pmProjectReference;
	}

	/**
	 * 功能：设置项目维护表(EAM)属性 产品来源
	 * @param pmProductCode String
	 */
	public void setPmProductCode(String pmProductCode){
		this.pmProductCode = pmProductCode;
	}


	/**
	 * 功能：获取项目维护表(EAM)属性 序列号
	 * @return AdvancedNumber
	 */
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目名称
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目编号
	 * @return String
	 */
	public String getSegment1() {
		return this.segment1;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目类型
	 * @return String
	 */
	public String getProjectType() {
		return this.projectType;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目状态代码
	 * @return String
	 */
	public String getProjectStatusCode() {
		return this.projectStatusCode;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目开始日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDate() throws CalendarException {
		startDate.setCalPattern(getCalPattern());
		return this.startDate;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目完成日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCompletionDate() throws CalendarException {
		completionDate.setCalPattern(getCalPattern());
		return this.completionDate;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 'Y'
	 * @return String
	 */
	public String getEnabledFlag() {
		return this.enabledFlag;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 来源 'MIS'  or 'EAM'
	 * @return String
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 创建人
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 上次修改人
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 MIS项目ID
	 * @return AdvancedNumber
	 */
	public String  getMisProjectId() {
		return this.misProjectId;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 组织ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目分类
	 * @return String
	 */
	public String getProjectClass() {
		return this.projectClass;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目摘要
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 项目经理
	 * @return String
	 */
	public String getProjectManager() {
		return this.projectManager;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 产品来源参考
	 * @return String
	 */
	public String getPmProjectReference() {
		return this.pmProjectReference;
	}

	/**
	 * 功能：获取项目维护表(EAM)属性 产品来源
	 * @return String
	 */
	public String getPmProductCode() {
		return this.pmProductCode;
	}

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}