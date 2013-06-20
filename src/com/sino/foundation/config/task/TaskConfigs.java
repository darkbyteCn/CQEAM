package com.sino.foundation.config.task;


import com.sino.foundation.config.ConfigLoadedResult;

import java.util.List;

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
public class TaskConfigs  implements ConfigLoadedResult {//定时任务配置
	private List<TaskConfig> tasks = null;
	private List<TriggerConfig> triggers = null;
	private List<SchedulerConfig> schedulers = null;

	public List<TaskConfig> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskConfig> tasks) {
		this.tasks = tasks;
	}

	public List<TriggerConfig> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<TriggerConfig> triggers) {
		this.triggers = triggers;
	}

	public List<SchedulerConfig> getSchedulers() {
		return schedulers;
	}

	public void setSchedulers(List<SchedulerConfig> schedulers) {
		this.schedulers = schedulers;
	}

	/**
	 * 功能：获取指定名称的任务
	 * @param taskName 任务名称
	 * @return 返回指定名称的任务
	 */
	public TaskConfig getTaskConfig(String taskName) {
		TaskConfig taskConfig = null;
		for	(TaskConfig task:tasks){
			if(task.getName().equals(taskName)){
				taskConfig = task;
				break;
			}
		}
		return taskConfig;
	}

	/**
	 * 功能：获取指定名称的触发器
	 * @param triggerName 触发器名称
	 * @return 返回指定名称的触发器
	 */
	public TriggerConfig getTriggerConfig(String triggerName) {
		TriggerConfig triggerConfig = null;
		for	(TriggerConfig trigger:triggers){
			if(trigger.getName().equals(triggerName)){
				triggerConfig = trigger;
				break;
			}
		}
		return triggerConfig;
	}

	public void releaseData() {
		tasks = null;
		triggers = null;
		schedulers = null;
	}
}