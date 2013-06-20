package com.sino.ams.system.log.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CalendarUtililyDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: 用户URL访问日志表(EAM) SfUserLog</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfUserLogDTO extends CalendarUtililyDTO{

	private int logId;
	private int userId;
	private String resName="";
	private String clientIp = "";
	private String reqUrl = "";
	private String actionType = "";
	private String source = "";
	private String server = "";
	private SimpleCalendar logTime = null;
	private String userAccount = "";
	private String columeType="";//个人工作台还是系统设置界面 SYSTEM/PERSONAL //personal
	
	public String getResName() {
		return resName;
	}
	/**
	 * 设置操作类型
	 * @param resName
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 日志序列号
	 * @param logId String
	 */
	public void setLogId(int logId){
		this.logId = logId;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 登录用户
	 * @param userId String
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 用户IP
	 * @param clientIp String
	 */
	public void setClientIp(String clientIp){
		this.clientIp = clientIp;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 请求资源
	 * @param reqUrl String
	 */
	public void setReqUrl(String reqUrl){
		this.reqUrl = reqUrl;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 操作类型
	 * @param actionType String
	 */
	public void setActionType(String actionType){
		this.actionType = actionType;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 访问来源(WEB,PDA)
	 * @param source String
	 */
	public void setSource(String source){
		this.source = source;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 服务器
	 * @param server String
	 */
	public void setServer(String server){
		this.server = server;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * 功能：设置用户URL访问日志表(EAM)属性 发生时间
	 * @param logTime SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLogTime(String logTime) throws CalendarException{
		if(!StrUtil.isEmpty(logTime)){
			this.logTime = new SimpleCalendar(logTime);
		}
	}


	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 日志序列号
	 * @return String
	 */
	public int getLogId(){
		return this.logId;
	}

	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 登录用户
	 * @return String
	 */
	public int getUserId(){
		return this.userId;
	}

	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 用户IP
	 * @return String
	 */
	public String getClientIp(){
		return this.clientIp;
	}

	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 请求资源
	 * @return String
	 */
	public String getReqUrl(){
		return this.reqUrl;
	}

	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 操作类型
	 * @return String
	 */
	public String getActionType(){
		return this.actionType;
	}

	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 访问来源(WEB,PDA)
	 * @return String
	 */
	public String getSource(){
		return this.source;
	}

	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 服务器
	 * @return String
	 */
	public String getServer(){
		return this.server;
	}

	/**
	 * 功能：获取用户URL访问日志表(EAM)属性 发生时间
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getLogTime(){
		return this.logTime;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public String getColumeType() {
		return columeType;
	}

	public void setColumeType(String columeType) {
		this.columeType = columeType;
	}
}
