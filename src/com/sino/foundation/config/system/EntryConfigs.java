package com.sino.foundation.config.system;


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
public class EntryConfigs {
    private List<String> configNames = new ArrayList<String>();
    private List<EntryConfig> configs = new ArrayList<EntryConfig>();

	public EntryConfigs() {
		super();
	}

	public void addEntryConfig(EntryConfig config) {
		if (!configNames.contains(config.getName())) {
            configNames.add(config.getName());
            configs.add(config);
		}
	}

	/**
	 * 功能：获取配置文件中所有的入口配置名称
	 * @return Iterator
	 */
	public List<String> getConfigNames() {
		return configNames;
	}

	/**
	 * 功能：获取配置文件中所有的入口配置名称
	 * @return Iterator
	 */
	public List<EntryConfig> getAllConfigs() {
		return configs;
	}

	/**
	 * 获取指定连接池配置
	 * @param configName String 连接池名称
	 * @return EntryConfig
	 */
	public EntryConfig getEntryConfig(String configName) {
        EntryConfig config = null;
        int index = configNames.indexOf(configName);
        if(index > -1){
            config = configs.get(index);
        }
		return config;
	}

	public EntryConfig removeEntryConfig(String configName) {
		EntryConfig config = null;
        int index = configNames.indexOf(configName);
        if(index > -1){
            config = configs.remove(index);
            configNames.remove(index);
        }
		return config;
	}

	public void clearConfigs(){
		configs.clear();
		configNames.clear();
	}
}
