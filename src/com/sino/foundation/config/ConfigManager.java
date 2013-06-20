package com.sino.foundation.config;

import com.sino.base.exception.ConfigException;
import com.sino.foundation.config.system.EntryConfig;
import com.sino.foundation.config.system.EntryConfigs;

import java.util.ArrayList;
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
public class ConfigManager {
	private static List<String> configNames = new ArrayList<String>();
	private static List<String> configLoaders = new ArrayList<String>();
	private static List<ConfigLoadedResult> configResults = new ArrayList<ConfigLoadedResult>();
	private static EntryConfigs configs = new EntryConfigs();

	synchronized void addConfig(EntryConfig entgryConfig, ConfigLoadedResult configResult) {
		String configName = entgryConfig.getName();
		if(configNames.contains(configName)){
			return;
		}
		configNames.add(entgryConfig.getName());
		configLoaders.add(entgryConfig.getLoaderClassName());
		configs.addEntryConfig(entgryConfig);
		configResults.add(configResult);
	}

	/**
	 * 功能：根据配置名称获取配置结果
	 * @param configName String
	 * @return LoadedResult
     * @throws ConfigException 如果配置为加载成功，则抛出配置初始化异常
	 */
	public static ConfigLoadedResult getLoadedResult(String configName) throws ConfigException {
		ConfigLoadedResult configResult = null;
		int index = configNames.indexOf(configName);
		if(index != -1){
			configResult = configResults.get(index);
		}
        if(configResult == null){
            throw new ConfigException(configName + "配置文件未加载，无法进行后续处理...");
        }
		return configResult;
	}


	/**
	 * 功能：根据配置加载器获取配置结果。方法重载。
     * <B>注意：调用本方法意味着本加载项为非必加载项，如果该加载项未加载，则返回NULL</B>
	 * @param loaderName String 加载器全路径名
	 * @return LoadedResult
	 */
	public static <T extends ConfigLoadedResult> T  getLoadedResultByLoader(String loaderName) throws ConfigException {
        T configResult = null;
        int index = configLoaders.indexOf(loaderName);
        if(index != -1){
            configResult = (T)configResults.get(index);
        }
        if(configResult == null){
            throw new ConfigException(loaderName + "未加载配置文件，无法进行后续处理...");
        }
        return configResult;
	}

	/**
	 * 功能：将相应的配置信息移除
	 * @param configName String
	 */
	public synchronized void removeConfig(String configName){
		int index = configNames.indexOf(configName);
		if(index != -1){
            String description = configs.getEntryConfig(configName).getDescription();
			configNames.remove(index);
			configLoaders.remove(index);
			configResults.remove(index);
			configs.removeEntryConfig(configName);
            System.out.println(description + " 卸载成功");
		}
	}

	/**
	 * 功能：根据加载器全限定名将相应的配置信息移除
	 * @param loaderName String
	 */
	public synchronized void removeConfigByLoaderName(String loaderName){
		int index = configLoaders.indexOf(loaderName);
		if(index != -1){
			String configName = configNames.remove(index);
			configLoaders.remove(index);
			configResults.remove(index);
			EntryConfig config = configs.removeEntryConfig(configName);
			System.out.println(config.getDescription() + " 卸载成功");
		}
	}

    public EntryConfigs getAllConfigs(){
        return configs;
    }

    public boolean contains(String configName){
        return configNames.contains(configName);
    }

	/**
	 * 功能：获取指定名称的入口配置
	 * @param configName String
	 * @return EntryConfig
	 */
	public static EntryConfig getEntryConfig(String configName){
		return configs.getEntryConfig(configName);
	}
}
