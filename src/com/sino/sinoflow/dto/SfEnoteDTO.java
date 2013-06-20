package com.sino.sinoflow.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 催办箱信息表 SfEnote</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfEnoteDTO extends CheckBoxDTO{

	private int enoteId = 0;
	private String actId = "";
	private int fromUserId = 0;
	private int toUserId = 0;
	private SimpleCalendar fromDate = null;
	private String url = "";
	private String msg = "";
	private String read = "";
    private String enabled = "Y";

    private String sfactProcName = "";
	private String sfactApplMsg = "";
	private String sfactApplColumn1 = "";
	private String sfactApplColumn2 = "";
	private String fromUserName = "";
	private String toUserName = "";

	public SfEnoteDTO() {
		super();
		this.fromDate = new SimpleCalendar();
	}

	
	public String getFromUserName() {
		return fromUserName;
	}


	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}


	public String getToUserName() {
		return toUserName;
	}


	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}


	public String getSfactProcName() {
		return sfactProcName;
	}


	public void setSfactProcName(String sfactProcName) {
		this.sfactProcName = sfactProcName;
	}


	public String getSfactApplMsg() {
		return sfactApplMsg;
	}


	public void setSfactApplMsg(String sfactApplMsg) {
		this.sfactApplMsg = sfactApplMsg;
	}


	public String getSfactApplColumn1() {
		return sfactApplColumn1;
	}


	public void setSfactApplColumn1(String sfactApplColumn1) {
		this.sfactApplColumn1 = sfactApplColumn1;
	}


	public String getSfactApplColumn2() {
		return sfactApplColumn2;
	}


	public void setSfactApplColumn2(String sfactApplColumn2) {
		this.sfactApplColumn2 = sfactApplColumn2;
	}


	/**
	 * 功能：设置催办箱信息表属性 主键自增长
	 * @param enoteId AdvancedNumber
	 */
	public void setEnoteId(int enoteId){
		this.enoteId = enoteId;
	}

	/**
	 * 功能：设置催办箱信息表属性 催办件id
	 * @param actId String
	 */
	public void setActId(String actId){
		this.actId = actId;
	}

	/**
	 * 功能：设置催办箱信息表属性 催办人ID
	 * @param fromUserId String
	 */
	public void setFromUserId(int fromUserId){
		this.fromUserId = fromUserId;
	}

	/**
	 * 功能：设置催办箱信息表属性 被催办人ID
	 * @param toUserId String
	 */
	public void setToUserId(int toUserId){
		this.toUserId = toUserId;
	}

	/**
	 * 功能：设置催办箱信息表属性 开始催办日期
	 * @param fromDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setFromDate(String fromDate) throws CalendarException{
		this.fromDate.setCalendarValue(fromDate);
	}

	/**
	 * 功能：设置催办箱信息表属性 表单样式的路径名称
	 * @param url String
	 */
	public void setUrl(String url){
		this.url = url;
	}

	/**
	 * 功能：设置催办箱信息表属性 催办内容
	 * @param msg String
	 */
	public void setMsg(String msg){
		this.msg = msg;
	}

	/**
	 * 功能：设置催办箱信息表属性 是否被读过（1读过，0没有）
	 * @param read String
	 */
	public void setRead(String read){
		this.read = read;
	}

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
	 * 功能：获取催办箱信息表属性 主键自增长
	 * @return AdvancedNumber
	 */
	public int getEnoteId() {
		return this.enoteId;
	}

	/**
	 * 功能：获取催办箱信息表属性 催办件id
	 * @return String
	 */
	public String getActId() {
		return this.actId;
	}

	/**
	 * 功能：获取催办箱信息表属性 催办人ID
	 * @return String
	 */
	public int getFromUserId() {
		return this.fromUserId;
	}

	/**
	 * 功能：获取催办箱信息表属性 被催办人ID
	 * @return String
	 */
	public int getToUserId() {
		return this.toUserId;
	}

	/**
	 * 功能：获取催办箱信息表属性 开始催办日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
		return this.fromDate;
	}

	/**
	 * 功能：获取催办箱信息表属性 表单样式的路径名称
	 * @return String
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 功能：获取催办箱信息表属性 催办内容
	 * @return String
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * 功能：获取催办箱信息表属性 是否被读过（1读过，0没有）
	 * @return String
	 */
	public String getRead() {
		return this.read;
	}

    public String getEnabled() {
        return this.enabled;
    }

}