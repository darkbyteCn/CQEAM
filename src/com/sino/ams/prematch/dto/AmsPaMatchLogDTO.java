package com.sino.ams.prematch.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: EAM系统资产实物与MIS转资准备清单预匹配 AmsPaMatchLog</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsPaMatchLogDTO extends CommonRecordDTO{

	private String logId = "";
	private int systemId;
	private String tagNumber = "";
	private int organizationId;
	private String act = "";
	private int matchedBy;
	private SimpleCalendar matchedDate = null;
	private String matchedUser = "";
	private String createdUser = "";
	private String remark = "";

	public AmsPaMatchLogDTO() {
		super();
		this.matchedDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 序列号
	 * @param logId String
	 */
	public void setLogId(String logId){
		this.logId = logId;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 ETS_ITEM_INFO序列号
	 * @param systemId String
	 */
	public void setSystemId(int systemId){
		this.systemId = systemId;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 MIS转资准备清单标签号
	 * @param tagNumber String
	 */
	public void setTagNumber(String tagNumber){
		this.tagNumber = tagNumber;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 匹配组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 操作类型
	 * @param act String
	 */
	public void setAct(String act){
		this.act = act;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 匹配人
	 * @param matchedBy String
	 */
	public void setMatchedBy(int matchedBy){
		this.matchedBy = matchedBy;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setMatchedUser(String matchedUser) {
		this.matchedUser = matchedUser;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 匹配时间
	 * @param matchedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setMatchedDate(String matchedDate) throws CalendarException{
		this.matchedDate.setCalendarValue(matchedDate);
	}


	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 序列号
	 * @return String
	 */
	public String getLogId() {
		return this.logId;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 ETS_ITEM_INFO序列号
	 * @return String
	 */
	public int getSystemId() {
		return this.systemId;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 MIS转资准备清单标签号
	 * @return String
	 */
	public String getTagNumber() {
		return this.tagNumber;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 匹配组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 操作类型
	 * @return String
	 */
	public String getAct() {
		return this.act;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 匹配人
	 * @return String
	 */
	public int getMatchedBy() {
		return this.matchedBy;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 匹配时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getMatchedDate() throws CalendarException {
		matchedDate.setCalPattern(getCalPattern());
		return this.matchedDate;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public String getMatchedUser() {
		return matchedUser;
	}

	public String getRemark() {
		return remark;
	}
}
