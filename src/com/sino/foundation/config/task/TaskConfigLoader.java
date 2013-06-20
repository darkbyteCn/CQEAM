package com.sino.foundation.config.task;


import com.sino.base.exception.ConfigException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.foundation.config.AbstractConfigLoader;
import com.sino.foundation.config.IConfigLoader;
import com.sino.foundation.task.TaskRunner;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>@todo：暂时位于该包下，经过实际项目的验证之后，将其加入基础库，并取代目前不灵活的配置管理</p>
 * <p>Copyright: 北京思诺博版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 0.1
 */
public class TaskConfigLoader extends AbstractConfigLoader implements IConfigLoader {

    private TaskConfigs taskConfigs = null;
    private List<TaskConfig> tasks = null;
    private List<String> taskNames = null;
    private List<TriggerConfig> triggers = null;
    private List<String> triggerNames = null;
    private List<SchedulerConfig> schedulers = null;

    public void loadConfig() throws ConfigException {
        try {
            if (configFile == null || !configFile.exists()) {
                return;
            }
            initConfigParameters();
            loadConfigFromFile();
            combineAllConfigs();
        } catch (ContainerException ex) {
            ex.printLog();
            throw new ConfigException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ConfigException(ex.getMessage());
        }
    }

    private void loadConfigFromFile() throws ConfigException {
        if (configFile.isFile()) {
            if (configFilter.isCertificate(configFile)) {
                loadSingleConfig(configFile);
            }
        } else {
            File[] files = configFile.listFiles(configFilter);
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        loadSingleConfig(file);
                    } else if (file.isDirectory()) {
                        loadPathConfig(file);
                    }
                }
            }
        }
    }

    private void initConfigParameters() {
        loadedResult = new TaskConfigs();
        taskConfigs = (TaskConfigs) loadedResult;
        tasks = new ArrayList<TaskConfig>();
        taskNames = new ArrayList<String>();
        triggers = new ArrayList<TriggerConfig>();
        triggerNames = new ArrayList<String>();
        schedulers = new ArrayList<SchedulerConfig>();
    }

    private void combineAllConfigs() {
        taskConfigs.setTasks(tasks);
        taskConfigs.setTriggers(triggers);
        taskConfigs.setSchedulers(schedulers);
        taskNames.clear();
        triggerNames.clear();
    }

    private void loadSingleConfig(File file) throws ConfigException {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document xmlDoc = builder.build(file);
            Element root = xmlDoc.getRootElement();
            loadTasks(root);
            loadTriggers(root);
            loadSchedulers(root);
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new ConfigException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ConfigException(ex.getMessage());
        }
    }

    /**
     * 功能：加载配置文件
     *
     * @param path File
     * @throws com.sino.base.exception.ConfigException
     *          加载ExportConfig配置文件出错时抛出该异常
     */
    private void loadPathConfig(File path) throws ConfigException {
        File[] files = path.listFiles(configFilter);
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    loadSingleConfig(file);
                } else if (file.isDirectory()) {
                    loadPathConfig(file);
                }
            }
        }
    }

    /**
     * 功能：加载定时任务配置
     *
     * @param root
     * @throws ConfigException
     */
    private void loadTasks(Element root) throws ConfigException {
        List<Element> eles = root.getChild("tasks").getChildren();
        if (eles != null && !eles.isEmpty()) {
            for (Element ele : eles) {
                TaskConfig taskConfig = new TaskConfig();
                loadConfig(ele, taskConfig);
                if (taskNames.contains(taskConfig.getName())) {
                    String err = taskConfig.getName()
                            + "已经存在，请检查配置文件"
                            + configFile.getAbsolutePath()
                            + " tasks节点中的同名任务";
                    throw new ConfigException(err);
                }
                tasks.add(taskConfig);
                taskNames.add(taskConfig.getName());
            }
        }
    }


    /**
     * 功能：加载定时任务时间策略配置
     *
     * @param root
     * @throws ConfigException
     */
    private void loadTriggers(Element root) throws ConfigException {
        List<Element> eles = root.getChild("triggers").getChildren();
        if (eles != null && !eles.isEmpty()) {
            for (Element ele : eles) {
                TriggerConfig triggerConfig = new TriggerConfig();
                loadConfig(ele, triggerConfig);
                if (triggerNames.contains(triggerConfig.getName())) {
                    String err = triggerConfig.getName()
                            + "已经存在，请检查配置文件"
                            + configFile.getAbsolutePath()
                            + "triggers节点中的同名触发器";
                    throw new ConfigException(err);
                }
                triggers.add(triggerConfig);
                triggerNames.add(triggerConfig.getName());
            }
        }
    }

    /**
     * 功能：加载调度器配置
     *
     * @param root
     * @throws ConfigException
     */
    private void loadSchedulers(Element root) throws ConfigException {
        List<Element> eles = root.getChild("schedulers").getChildren();
        if (eles != null && !eles.isEmpty()) {
            for (Element ele : eles) {
                SchedulerConfig schedulerConfig = new SchedulerConfig();
                loadConfig(ele, schedulerConfig);
                validateScheduler(schedulerConfig);
                schedulers.add(schedulerConfig);
            }
        }
    }

    /**
     * 功能：检查调度器部分的配置是否合法
     *
     * @param schedulerConfig
     * @throws ConfigException 不合法时抛出配置异常
     */
    private void validateScheduler(SchedulerConfig schedulerConfig) throws ConfigException {
        String taskName = schedulerConfig.getTaskName();
        boolean foundData = false;
        for (TaskConfig task : tasks) {
            if (taskName.equals(task.getName())) {
                foundData = true;
                break;
            }
        }
        if (!foundData) {
            String err = taskName
                    + "不存在，请检查配置文件"
                    + configFile.getAbsolutePath()
                    + " 的tasks节点是否遗漏"
                    + taskName
                    + "的相关配置";
            throw new ConfigException(err);
        }

        String triggerName = schedulerConfig.getTriggerName();
        foundData = false;
        for (TriggerConfig trigger : triggers) {
            if (triggerName.equals(trigger.getName())) {
                foundData = true;
                break;
            }
        }
        if (!foundData) {
            String err = triggerName
                    + "不存在，请检查配置文件"
                    + configFile.getAbsolutePath()
                    + " 的triggers节点是否遗漏"
                    + triggerName
                    + "的相关配置";
            throw new ConfigException(err);
        }
    }

    /**
     * 功能：加载配置信息。公共方法。
     *
     * @param ele Element
     * @return PoolConfig
     */
    private void loadConfig(Element ele, Object cfgObject) throws ConfigException {
        try {
            List<Attribute> attrs = ele.getAttributes();
            if (attrs != null && !attrs.isEmpty()) {
                for (Attribute attr : attrs) {
                    ReflectionUtil.setProperty(cfgObject, attr.getName(), attr.getValue());
                }
            }
            List<Element> dataChildren = ele.getChildren();
            if (dataChildren != null && !dataChildren.isEmpty()) {
                for (Element child : dataChildren) {
                    ReflectionUtil.setProperty(cfgObject, child.getName(), child.getText());
                }
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new ConfigException(ex);
        }
    }

    /**
     * 功能：卸载定时任务配置，并且停止任务运行。该方法需要修改。
     */
    public void unloadConfig() {
        try {
            TaskRunner.destroyTask();
            super.unloadConfig();
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
    }
}