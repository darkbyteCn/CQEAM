package com.sino.ams.workorder.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 工单流程意见处理(EAM) EtsSuggestion</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsSuggestionDTO extends CheckBoxDTO{

	private String  systemid = null;
	private String workorderBatch = "";
	private String title = "";
	private String remark = "";
	private String groupId = "";
	private int  handler;
	private SimpleCalendar recordDate = null;
	private int completeFlag;
	private String actId = "";

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public EtsSuggestionDTO() {
		super();
		this.recordDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 流水号
	 * @param systemid AdvancedNumber
	 */
	public void setSystemid(String  systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 工单批号
	 * @param workorderBatch String
	 */
	public void setWorkorderBatch(String workorderBatch){
		this.workorderBatch = workorderBatch;
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 主题
	 * @param title String
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 处理意见
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 组别号
	 * @param groupId String
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 处理人
	 * @param handler AdvancedNumber
	 */
	public void setHandler(int  handler){
		this.handler = handler;
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 提交日期
	 * @param recordDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setRecordDate(String recordDate) throws CalendarException{
		this.recordDate.setCalendarValue(recordDate);
	}

	/**
	 * 功能：设置工单流程意见处理(EAM)属性 完成标志(1:完成；0:在办)
	 * @param completeFlag AdvancedNumber
	 */
	public void setCompleteFlag(int completeFlag){
		this.completeFlag = completeFlag;
	}


	/**
	 * 功能：获取工单流程意见处理(EAM)属性 流水号
	 * @return AdvancedNumber
	 */
	public String  getSystemid() {
		return this.systemid;
	}

	/**
	 * 功能：获取工单流程意见处理(EAM)属性 工单批号
	 * @return String
	 */
	public String getWorkorderBatch() {
		return this.workorderBatch;
	}

	/**
	 * 功能：获取工单流程意见处理(EAM)属性 主题
	 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 功能：获取工单流程意见处理(EAM)属性 处理意见
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取工单流程意见处理(EAM)属性 组别号
	 * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 功能：获取工单流程意见处理(EAM)属性 处理人
	 * @return AdvancedNumber
	 */
	public int getHandler() {
		return this.handler;
	}

	/**
	 * 功能：获取工单流程意见处理(EAM)属性 提交日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getRecordDate() throws CalendarException {
		recordDate.setCalPattern(getCalPattern());
		return this.recordDate;
	}

	/**
	 * 功能：获取工单流程意见处理(EAM)属性 完成标志(1:完成；0:在办)
	 * @return AdvancedNumber
	 */
	public int getCompleteFlag() {
		return this.completeFlag;
	}

}