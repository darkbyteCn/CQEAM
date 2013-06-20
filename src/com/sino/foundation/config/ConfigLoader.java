package com.sino.foundation.config;

import com.sino.base.exception.ConfigException;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.foundation.config.system.EntryConfig;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public abstract class ConfigLoader {
    private static File systemEntryFile = null; //入口配置文件
    private static Map<String, Long> fileMap = new HashMap<String, Long>();
    private static List<IConfigLoader> loaders = new ArrayList<IConfigLoader>();//为配置文件的卸载准备
    private static ConfigManager configManager = new ConfigManager();

    private static String contextPath = "";  //Web应用根目录
    private static final String defaultConfigPath = "/SinoConfig/EntryConfig.xml";

    public static void setContextPath(String contextPath) {
        ConfigLoader.contextPath = contextPath;
    }

    public static void loadDefaultConfig() throws ConfigException {
        loadConfig(defaultConfigPath);
    }

    /**
     * 功能：所有配置文件的入口加载处，由初始化程序调用，比如Web应用中的初始化Servlet。
     *
     * @param relativePath String
     * @throws ConfigException
     */
    public static void loadConfig(String relativePath) throws ConfigException {
        try {
            String absolutePath = ConfigPathUtil.getConfigPath(relativePath);//需要重新写
            systemEntryFile = new File(absolutePath);
            Element root = getXMLConfigRoot();
            List<Element> confs = root.getChildren();
            if (confs != null && !confs.isEmpty()) {
                for (Element child : confs) {
                    EntryConfig entryConfig = getEntryConfig(child);
                    if (!entryConfig.isLoadConfig()) {
                        continue;
                    }
                    try {
                        IConfigLoader configLoader = getConfigLoader(entryConfig);
                        loadEntryConfig(entryConfig, configLoader);
                        System.out.println(entryConfig.getDescription() + " 加载成功");
                    } catch (Throwable ex) {
                        Logger.logError(ex); //仅仅记录异常，继续加载下一个配置文件
                        System.out.println(entryConfig.getDescription() + " 加载失败");
                    }
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ConfigException(ex.getMessage());
        }
    }

    /**
     * 功能：所有配置文件的重新加载的入口处，由配置文件监听器调用。
     *
     * @throws ConfigException
     */
    public static void refreshConfig() throws ConfigException {
        if (systemEntryFile == null) {
            throw new ConfigException("系统配置已经卸载，服务器需要重启");
        }
        Element root = getXMLConfigRoot();
        List<Element> confs = root.getChildren();
        if (confs != null && !confs.isEmpty()) {
            unLoadRemovedConfigs(confs);
            loadChangeConfigs(confs);
        } else {
            unloadAllConfigs();
        }
    }

    /**
     * 功能：获取总体配置文件的根节点
     *
     * @return
     * @throws ConfigException
     */
    private static Element getXMLConfigRoot() throws ConfigException {
        Element xmlRoot = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document xmlDoc = builder.build(systemEntryFile);
            xmlRoot = xmlDoc.getRootElement();
        } catch (JDOMException ex) {
            Logger.logError(ex);
            throw new ConfigException(ex);
        } catch (IOException ex) {
            Logger.logError(ex);
            throw new ConfigException(ex);
        }
        return xmlRoot;
    }

    /**
     * 功能：获取配置文件加载器w
     *
     * @param entryConfig
     * @return
     * @throws ConfigException
     */
    public static IConfigLoader getConfigLoader(EntryConfig entryConfig) throws ConfigException {
        File configFile = new File(entryConfig.getAbsolutePath());
        IConfigLoader configLoader = LoaderFactory.getConfigLoader(entryConfig);
        configLoader.setConfigFile(configFile);
        configLoader.setContextPath(contextPath);
        return configLoader;
    }


    /**
     * 功能：卸载先前加载，但现在从配置文件SystemConfig.xml中移除的配置。
     *
     * @param confs 当前的SystemConfig.xml配置文件的config节点列表
     * @throws  ConfigException
     */
    private static void unLoadRemovedConfigs(List<Element> confs) throws ConfigException {
        List<EntryConfig> configs = configManager.getAllConfigs().getAllConfigs();
        for (int i = 0; i < configs.size(); i++) {
            EntryConfig config = configs.get(i);
            boolean foundConfig = false;
            for (Element child : confs) {
                EntryConfig entryConfig = getEntryConfig(child);
                if (entryConfig.equals(config)) {
                    foundConfig = true;
                    break;
                }
            }
            if (!foundConfig) {
                System.out.println(config.getDescription() + config.getAbsolutePath() + "已从系统配置文件中移除，系统将卸载其配置信息。");
                configManager.removeConfig(config.getName());
            }
        }
    }

    /**
     * 功能：加载变化了的配置
     *
     * @param confs 当前的SystemConfig.xml配置文件的config节点列表
     */
    private static void loadChangeConfigs(List<Element> confs) throws ConfigException {
        for (Element child : confs) {
            EntryConfig entryConfig = getEntryConfig(child);
            if (configManager.contains(entryConfig.getName())) {//原有配置信息
                if (!entryConfig.isLoadConfig()) {
                    configManager.removeConfig(entryConfig.getName());
                } else if (entryConfig.isSupportReload()) { //是否支持热加载
                    if (hasConfigChanged(entryConfig)) {
                        System.out.println(entryConfig.getDescription()
                                + "配置文件"
                                + entryConfig.getAbsolutePath()
                                + "发生修改，系统对其重新加载");
                        IConfigLoader configLoader = getConfigLoader(entryConfig);
                        unloadConfig(entryConfig, configLoader);
                        loadEntryConfig(entryConfig, configLoader);
                        System.out.println(entryConfig.getDescription() + "重新加载成功");
                    }
                }
            } else {
                if (entryConfig.isLoadConfig()) {
                    System.out.println(entryConfig.getDescription()
                            + "新加入配置文件"
                            + entryConfig.getAbsolutePath()
                            + "，系统将对其进行加载");
                    IConfigLoader configLoader = getConfigLoader(entryConfig);
                    loadEntryConfig(entryConfig, configLoader);
                    System.out.println(entryConfig.getDescription() + "加载成功");
                }
            }
        }
    }

    /**
     * 功能：构造入口配置
     *
     * @param entryEle Element
     * @return EntryConfig
     * @throws ConfigException
     */

    private static EntryConfig getEntryConfig(Element entryEle) throws ConfigException {
        EntryConfig entryConfig = new EntryConfig();
        try {
            List<Attribute> attrs = entryEle.getAttributes();
            for (Attribute attr : attrs) {
                if (StrUtil.isEmpty(attr.getValue())) {
                    continue;
                }
                ReflectionUtil.setProperty(entryConfig, attr.getName(), attr.getValue());
            }
            List<Element> children = entryEle.getChildren();
            for (Element ele : children) {
                if (StrUtil.isEmpty(ele.getValue())) {
                    continue;
                }
                ReflectionUtil.setProperty(entryConfig, ele.getName(), ele.getValue());
            }
            if(systemEntryFile != null){
                entryConfig.setEntryPath(systemEntryFile.getParent());
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ConfigException(ex.getMessage());
        }
        return entryConfig;
    }

    /**
     * 功能：加载入口配置指定的应用配置文件
     *
     * @param entryConfig  EntryConfig
     * @param configLoader AbstractConfigLoader
     * @throws ConfigException
     */
    public static void loadEntryConfig(EntryConfig entryConfig, IConfigLoader configLoader) throws ConfigException {
        try {
            File configFile = new File(entryConfig.getAbsolutePath());
            configLoader.loadConfig();
            if (!loaders.contains(configLoader)) {
                loaders.add(configLoader);
            }
            processPostProcessor(entryConfig, configLoader.getLoadedResult());
            fileMap.put(configFile.getAbsolutePath(), configFile.lastModified());
            configManager.addConfig(entryConfig, configLoader.getLoadedResult());
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ConfigException(ex.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    /**
     * 功能：处理后置处理器
     *
     * @param entryConfig  入口配置对象
     * @param loadedResult 加载结果
     * @throws ConfigException
     */
    private static void processPostProcessor(EntryConfig entryConfig, final ConfigLoadedResult loadedResult) throws ConfigException {
        try {
            final String postProcessorName = entryConfig.getPostProcessor();
            if (!StrUtil.isEmpty(postProcessorName)) {
                long delayTime = entryConfig.getDelayTime();
                Class<ConfigPostProcessor> cls = (Class<ConfigPostProcessor>) Class.forName(postProcessorName);
                Constructor<ConfigPostProcessor> constructor = cls.getDeclaredConstructor();
                constructor.setAccessible(true);
                ConfigPostProcessor postProcessor = constructor.newInstance();
                if (delayTime == 0) {
                    postProcessor.postProcessConfig(loadedResult);
                } else {
                    PostProcessorContainer processorContainer = new PostProcessorContainer(postProcessor, loadedResult, delayTime);
                    processorContainer.start();
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ConfigException(ex.getMessage());
        }
    }


    /**
     * 功能：检查文件配置是否发生变化
     *
     * @param entryConfig
     * @return
     */
    private static boolean hasConfigChanged(EntryConfig entryConfig) {
        File configFile = new File(entryConfig.getAbsolutePath());
        Long modified = configFile.lastModified();
        Long lastModified = fileMap.get(configFile.getAbsolutePath());
        if (lastModified == null) {
            lastModified = -1L;
        }
        return ((modified.compareTo(lastModified) != 0));
    }

    /**
     * 功能：卸载入口配置信息
     *
     * @param entryConfig  EntryConfig
     * @param configLoader AbstractConfigLoader
     * @throws ConfigException
     */
    private static void unloadConfig(EntryConfig entryConfig, IConfigLoader configLoader) throws ConfigException {
        String loaderName = entryConfig.getLoaderClassName();
        configManager.removeConfigByLoaderName(loaderName);
        loaders.remove(configLoader);
        configLoader.unloadConfig();
    }


    /**
     * 功能：卸载所有配置。
     */
    public static void unloadAllConfigs() {
        try {
            if (!loaders.isEmpty()) {
                for (IConfigLoader configLoader : loaders) {
                    if (configLoader != null) {
                        configLoader.unloadConfig();
                        configManager.removeConfigByLoaderName(configLoader.getClass().getName());
                    }
                }
                loaders.clear();
                loaders = null;
                fileMap = null;
                systemEntryFile = null;
            }
        } finally {
            System.gc();
        }
    }
}
