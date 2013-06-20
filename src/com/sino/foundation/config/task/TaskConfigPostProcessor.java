package com.sino.foundation.config.task;


import com.sino.base.exception.ConfigException;
import com.sino.base.log.Logger;
import com.sino.foundation.config.ConfigLoadedResult;
import com.sino.foundation.config.ConfigPostProcessor;
import com.sino.foundation.task.TaskRunner;
import org.quartz.SchedulerException;

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
public class TaskConfigPostProcessor implements ConfigPostProcessor {

    /**
     * 功能：配置文件加载结果后处理器
     *
     * @param configResult 加载结果
     * @throws com.sino.base.exception.ConfigException
     *          处理失败时统一抛出配置异常
     */
    public void postProcessConfig(ConfigLoadedResult configResult) throws ConfigException {
        try {
            TaskConfigs taskConfigs = (TaskConfigs) configResult;
            TaskRunner taskRunner = new TaskRunner();
            taskRunner.startTask(taskConfigs);
        } catch (SchedulerException ex) {
            Logger.logError(ex);
            throw new ConfigException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ConfigException(ex.getMessage());
        }
    }
}
