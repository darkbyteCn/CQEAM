package com.sino.foundation.task;


import com.sino.base.log.Logger;
import com.sino.foundation.config.task.SchedulerConfig;
import com.sino.foundation.config.task.TaskConfig;
import com.sino.foundation.config.task.TaskConfigs;
import com.sino.foundation.config.task.TriggerConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

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

public class TaskRunner {

    private static Scheduler scheduler = null;
    private TaskConfigs taskConfigs = null;

    /**
     * 功能：根据任务配置启动任务调度。
     *
     * @param taskConfigs 任务配置
     * @throws SchedulerException
     */
    public void startTask(TaskConfigs taskConfigs) throws SchedulerException {
        if (taskConfigs != null) {
            this.taskConfigs = taskConfigs;
            produceScheduler();
            List<SchedulerConfig> schedulers = taskConfigs.getSchedulers();
            int count = 0;
            for (SchedulerConfig schConfig : schedulers) {
                if (!schConfig.isStartTask()) {
                    continue;
                }
                count++;
                processTask(schConfig);
            }
            if (count > 0) {
                scheduler.start();//启动调度器
            } else {
                scheduler = null;
            }
        }
    }

    /**
     * 功能：生成任务调度器。
     *
     * @throws SchedulerException
     */
    private void produceScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        if (scheduler == null) {
            scheduler = schedulerFactory.getScheduler();
        }
    }

    /**
     * 功能：将任务加入调度器
     *
     * @param schConfig 任务调度对象
     * @throws SchedulerException 将定时任务加入调度器出错时抛出该异常
     */
    private void processTask(SchedulerConfig schConfig) throws SchedulerException {
        try {
            TaskConfig task = taskConfigs.getTaskConfig(schConfig.getTaskName());
            TriggerConfig triCfg = taskConfigs.getTriggerConfig(schConfig.getTriggerName());
            String className = task.getTaskClass();
            String taskType = task.getTaskType();
            Class cls = Class.forName(className);
            if (taskType.equals(TaskConfig.TASK_TYPE_COMMON)) {
                if (task.isStateful()) {
                    cls = StatefulTaskProxy.class;
                } else {
                    cls = StatelessTaskProxy.class;
                }
            }
            JobDetail job = new JobDetail(task.getName(), schConfig.getTaskGroup(), cls);//引进作业程序
            job.setDescription(task.getTaskDesc());

            CronTrigger trigger = new CronTrigger(triCfg.getName(), schConfig.getTriggerGroup());//new一个触发器
            trigger.setCronExpression(triCfg.getCronExpression());
            trigger.setPriority(triCfg.getPriority());
            scheduler.scheduleJob(job, trigger);//作业和触发器设置到调度器中
            System.out.println("轮询任务“" + task.getTaskDesc() + "”加入调度...");
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new SchedulerException(ex);
        }
    }

    public static void destroyTask() throws SchedulerException {
        if(scheduler != null){
            scheduler.shutdown(true);
            scheduler = null;
        }
    }
}