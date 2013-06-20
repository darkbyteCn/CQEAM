package com.sino.foundation.config;


import com.sino.base.exception.ConfigException;
import com.sino.base.log.Logger;
import com.sino.base.util.SystemUtil;
import com.sino.foundation.config.system.EntryConfig;
import com.sino.foundation.exception.EmptyException;
import com.sino.foundation.validate.JavaBeanValidator;

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
public abstract class LoaderFactory {

	private static Map<String, IConfigLoader> loaderMap = new HashMap<String, IConfigLoader>();

	/**
	 * 功能：根据配置入口对象获取配置文件加载器
	 * @param entryConfig EntryConfig 配置文件入口对象
	 * @return AbstractConfigLoader 入口对象特定的配置文件加载器
	 * @throws ConfigException
	 */
	public static IConfigLoader getConfigLoader(EntryConfig entryConfig) throws ConfigException {
		String configName = entryConfig.getName();
		IConfigLoader configLoader = loaderMap.get(configName);
		if(configLoader == null){
			configLoader = createNewLoader(entryConfig);
            String fileExtention = entryConfig.getFileExtention();
            ConfigFileFilter configFilter = new ConfigFileFilter(fileExtention);
            configLoader.setConfigFilter(configFilter);
            loaderMap.put(configName, configLoader);
		} else {
            System.out.println("利用原有的配置文件加载器" + configLoader.getClass().getName());
        }
		return configLoader;
	}

	/**
	 * 功能：获取新的Loader实例
	 * @param entryConfig EntryConfig
	 * @return AbstractConfigLoader
	 * @throws ConfigException
	 */
	private static IConfigLoader createNewLoader(EntryConfig entryConfig) throws ConfigException {
		IConfigLoader configLoader = null;
		String errorMsg = "";
		try {
            List<String> ignoreFields = new ArrayList<String>();
            ignoreFields.add("postProcessor");
            ignoreFields.add("fileExtention");
            ignoreFields.add("primaryKeyName");
			JavaBeanValidator.validateJavaBean(entryConfig, ignoreFields);
		} catch (EmptyException ex) {
			throw new ConfigException(ex);
		}
		String loaderClassName = entryConfig.getLoaderClassName();
		try {
			Class baseCls = AbstractConfigLoader.class;
			Class thisCls = Class.forName(loaderClassName);
			if (!SystemUtil.isDerivedClass(thisCls, baseCls)) {
				errorMsg = "“"
						   + entryConfig.getDescription()
						   + "”配置的类名“"
						   + loaderClassName
						   + "”没有继承配置文件加载器基类“"
						   + baseCls.getName()
						   + "”";
				throw new ConfigException(errorMsg);
			}
			configLoader = (IConfigLoader) thisCls.newInstance();
            System.out.println("创建新的配置文件加载器" + thisCls.getName());
		} catch (ClassNotFoundException ex) {
			Logger.logError(ex);
			throw new ConfigException(ex);
		} catch (IllegalAccessException ex) {
			Logger.logError(ex);
			throw new ConfigException(ex);
		} catch (InstantiationException ex) {
			Logger.logError(ex);
			throw new ConfigException(ex);
		}
		return configLoader;
	}
}
