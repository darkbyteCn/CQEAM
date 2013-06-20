package com.sino.soa.td.srv.assetbook.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-6
 * Time: 12:27:41
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATdAssetBookDTO extends CheckBoxDTO {

	private String bookTypeCode = "";
	private String bookTypeName = "";
	private String orgName = "";
	private String setOfBooksName = "";
	private String prorateCalendar = "";
	private int orgId = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar dateIneffective = null;
	private SimpleCalendar lastDeprnRunDate = null;
	private String deprnStatus = "";
	private SimpleCalendar lastUpdateDate = null;
	private String orgOption="";
	private String assetsType ="";
	/**
	 * @return the orgOption
	 */
	public String getOrgOption() {
		return orgOption;
	}

	/**
	 * @param orgOption the orgOption to set
	 */
	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}

	public SBFIFATdAssetBookDTO() {
		super();
		this.dateIneffective = new SimpleCalendar();
		this.lastDeprnRunDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置资产账簿服务属性 账簿代码
	 * @param bookTypeCode String
	 */
	public void setBookTypeCode(String bookTypeCode){
		this.bookTypeCode = bookTypeCode;
	}

	/**
	 * 功能：设置资产账簿服务属性 账簿名称
	 * @param bookTypeName String
	 */
	public void setBookTypeName(String bookTypeName){
		this.bookTypeName = bookTypeName;
	}

	/**
	 * 功能：设置资产账簿服务属性 组织名称
	 * @param orgName String
	 */
	public void setOrgName(String orgName){
		this.orgName = orgName;
	}

	/**
	 * 功能：设置资产账簿服务属性 资产所属总账账套
	 * @param setOfBooksName String
	 */
	public void setSetOfBooksName(String setOfBooksName){
		this.setOfBooksName = setOfBooksName;
	}

	/**
	 * 功能：设置资产账簿服务属性 资产分摊日历
	 * @param prorateCalendar String
	 */
	public void setProrateCalendar(String prorateCalendar){
		this.prorateCalendar = prorateCalendar;
	}

	/**
	 * 功能：设置资产账簿服务属性 组织ID
	 * @param orgId String
	 */
	public void setOrgId(int orgId){
		this.orgId = orgId;
	}

	/**
	 * 功能：设置资产账簿服务属性 账簿无效日期
	 * @param dateIneffective SimpleCalendar
	 * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDateIneffective(String dateIneffective) throws CalendarException {
		this.dateIneffective.setCalendarValue(dateIneffective);
	}

	/**
	 * 功能：设置资产账簿服务属性 上次运行折旧时间
	 * @param lastDeprnRunDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastDeprnRunDate(String lastDeprnRunDate) throws CalendarException{
		this.lastDeprnRunDate.setCalendarValue(lastDeprnRunDate);
	}

	/**
	 * 功能：设置资产账簿服务属性 折旧完成状态
	 * @param deprnStatus String
	 */
	public void setDeprnStatus(String deprnStatus){
		this.deprnStatus = deprnStatus;
	}

	/**
	 * 功能：设置资产账簿服务属性 最新更新时间
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}


	/**
	 * 功能：获取资产账簿服务属性 账簿代码
	 * @return String
	 */
	public String getBookTypeCode() {
		return this.bookTypeCode;
	}

	/**
	 * 功能：获取资产账簿服务属性 账簿名称
	 * @return String
	 */
	public String getBookTypeName() {
		return this.bookTypeName;
	}

	/**
	 * 功能：获取资产账簿服务属性 组织名称
	 * @return String
	 */
	public String getOrgName() {
		return this.orgName;
	}

	/**
	 * 功能：获取资产账簿服务属性 资产所属总账账套
	 * @return String
	 */
	public String getSetOfBooksName() {
		return this.setOfBooksName;
	}

	/**
	 * 功能：获取资产账簿服务属性 资产分摊日历
	 * @return String
	 */
	public String getProrateCalendar() {
		return this.prorateCalendar;
	}

	/**
	 * 功能：获取资产账簿服务属性 组织ID
	 * @return String
	 */
	public int getOrgId() {
		return this.orgId;
	}

	/**
	 * 功能：获取资产账簿服务属性 账簿无效日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDateIneffective() throws CalendarException {
		dateIneffective.setCalPattern(getCalPattern());
		return this.dateIneffective;
	}

	/**
	 * 功能：获取资产账簿服务属性 上次运行折旧时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastDeprnRunDate() throws CalendarException {
		lastDeprnRunDate.setCalPattern(getCalPattern());
		return this.lastDeprnRunDate;
	}

	/**
	 * 功能：获取资产账簿服务属性 折旧完成状态
	 * @return String
	 */
	public String getDeprnStatus() {
		return this.deprnStatus;
	}

	/**
	 * 功能：获取资产账簿服务属性 最新更新时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

}