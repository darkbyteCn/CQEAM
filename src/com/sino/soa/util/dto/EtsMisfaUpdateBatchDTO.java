package com.sino.soa.util.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: ETS_MISFA_UPDATE_BATCH EtsMisfaUpdateBatch</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsMisfaUpdateBatchDTO extends CheckBoxDTO{

	private String batchId = "";
	private int transStatus = -1;
	private String remark = "";
	private String transType = "";
	private int organizationId = -1;
	private SimpleCalendar startDate = null;
	private SimpleCalendar endDate = null;
	private String errmsg = "";
	private SimpleCalendar creationDate = null;
	private int createdBy = -1;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy = -1;

	public EtsMisfaUpdateBatchDTO() {
		super();
		this.startDate = new SimpleCalendar();
		this.endDate = new SimpleCalendar();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();

	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 ETS_MISFA_UPDATE_LOG_B_s.nextval
	 * @param batchId int
	 */
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 0: 等待执行  1:正在运行  2:运行完毕
	 * @param transStatus String
	 */
	public void setTransStatus(int transStatus){
		this.transStatus = transStatus;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 REMARK
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 传输类型LOCATION/TAGNUMBER/DESC/NEWLOC/LOCATION
	 * @param transType String
	 */
	public void setTransType(String transType){
		this.transType = transType;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 ORGANIZATION_ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 START_DATE
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException{
		this.startDate.setCalendarValue(startDate);
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 END_DATE
	 * @param endDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndDate(String endDate) throws CalendarException{
		this.endDate.setCalendarValue(endDate);
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 ERRMSG
	 * @param errmsg String
	 */
	public void setErrmsg(String errmsg){
		this.errmsg = errmsg;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 创建人
	 * @param createdBy int
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置ETS_MISFA_UPDATE_BATCH属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 ETS_MISFA_UPDATE_LOG_B_s.nextval
	 * @return String
	 */
	public String getBatchId() {
		return this.batchId;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 0: 等待执行  1:正在运行  2:运行完毕
	 * @return String
	 */
	public int getTransStatus() {
		return this.transStatus;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 REMARK
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 传输类型LOCATION/TAGNUMBER/DESC/NEWLOC/LOCATION
	 * @return String
	 */
	public String getTransType() {
		return this.transType;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 ORGANIZATION_ID
	 * @return int
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 START_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDate() throws CalendarException {
		startDate.setCalPattern(getCalPattern());
		return this.startDate;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 END_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getEndDate() throws CalendarException {
		endDate.setCalPattern(getCalPattern());
		return this.endDate;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 ERRMSG
	 * @return String
	 */
	public String getErrmsg() {
		return this.errmsg;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 创建人
	 * @return int
	 */
	public int getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取ETS_MISFA_UPDATE_BATCH属性 上次修改人
	 * @return int
	 */
	public int getLastUpdateBy() {
		return this.lastUpdateBy;
	}

}