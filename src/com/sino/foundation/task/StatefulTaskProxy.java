package com.sino.foundation.task;

import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.foundation.config.DefaultConfigManager;
import com.sino.foundation.config.task.TaskConfig;
import com.sino.foundation.config.task.TaskConfigs;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.HashMap;
import java.util.Map;

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
public class StatefulTaskProxy implements StatefulJob {
    private final static Map<String, Integer> taskExecuteTimes = new HashMap<String, Integer>();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			TaskConfigs taskConfigs = DefaultConfigManager.getTaskConfig();
			JobDetail job = context.getJobDetail();
            String taskName = job.getName();
            TaskConfig taskConfig = taskConfigs.getTaskConfig(taskName);
            synchronized (taskExecuteTimes){
                Integer executeTimes = taskExecuteTimes.get(taskName);
                if(executeTimes == null){
                    executeTimes = 1;
                } else {
                    executeTimes++;
                }
                taskExecuteTimes.put(taskName, executeTimes);
                System.out.println("第" + (++executeTimes) + "次触发任务“"+taskConfig.getTaskDesc()+"”，时间：" + CalendarUtil.getCurrCalendar());
            }
			Class cls = Class.forName(taskConfig.getTaskClass());
			Object obj = cls.newInstance();
			String methodName = taskConfig.getTaskMethod();
            Object[] para = new Object[0];
			ReflectionUtil.invokeMethod(obj, methodName, para);
		} catch (Throwable ex) {
			Logger.logError(ex);
			throw new JobExecutionException(ex);
		}
	}
}
