package com.sino.foundation.config.task;


import com.sino.base.SinoBaseObject;

/**
* <p>Title: SinoApplication</p>
* <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
* <p>@todo：暂时位于该包下，经过实际项目的验证之后，将其加入基础库，并取代目前不灵活的配置管理</p>
* <p>Copyright: 北京思诺博版权所有Copyright (c) 2003~2008。
* <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
* <p>Company: 北京思诺博信息技术有限公司</p>
* @author 唐明胜
* @version 0.1
 */
public class TriggerConfig extends SinoBaseObject {//定时任务配置
	private String name = "";
	private String triggerDesc = "";
	private String cronExpression = "";
	private int priority = 5;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		if(priority > Thread.MAX_PRIORITY){
			priority = Thread.MAX_PRIORITY;
		} else if(priority < Thread.MIN_PRIORITY){
			priority = Thread.MIN_PRIORITY;
		}
		this.priority = priority;
	}

	public String getTriggerDesc() {
		return triggerDesc;
	}

	public void setTriggerDesc(String triggerDesc) {
		this.triggerDesc = triggerDesc;
	}
}