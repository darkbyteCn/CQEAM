package com.sino.foundation.config;

import com.sino.base.exception.ConfigException;

import java.io.File;
import java.io.InputStream;

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

public interface IConfigLoader {

    /**
     * 功能：设置配置文件过滤器
     *
     * @param configFilter 配置文件过滤器
     */
    void setConfigFilter(ConfigFileFilter configFilter);

    /**
     * 功能：设置配置文件
     *
     * @param configFile File
     */
    void setConfigFile(File configFile);

    /**
     * 功能：设置配置文件的输入流
     *
     * @param in InputStream
     */
    void setConfigInputStream(InputStream in);


    /**
     * 功能：设置Web应用根路径
     *
     * @param contextPath Web应用根路径
     */
    void setContextPath(String contextPath);

    /**
     * 功能：加载配置文件
     *
     * @throws com.sino.base.exception.ConfigException
     *          加载配置文件出错时抛出配置异常
     */
    void loadConfig() throws ConfigException;

    /**
     * 功能：卸载加载的配置
     * <B>默认仅仅释放由加载近来的数据。<B>
     * <B>如果具体的加载器有额外的卸载工作，应当覆盖本方法。例如数据库连接池的卸载还应当卸载数据库连接</B>
     */
    void unloadConfig();

    /**
     * 功能：返回根据配置文件加载后的结果对象
     *
     * @return LoadedResult
     */
    ConfigLoadedResult getLoadedResult();
}
