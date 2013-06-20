package com.sino.foundation.config;


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

public abstract class AbstractConfigLoader implements IConfigLoader {
    protected ConfigLoadedResult loadedResult = null; //由子类加载器加载设置
    protected File configFile = null;
    protected InputStream in = null;
    protected String contextPath = "";
    protected ConfigFileFilter configFilter = null;

    public void setConfigFilter(ConfigFileFilter configFilter) {
        this.configFilter = configFilter;
    }

    /**
     * 功能：设置配置文件
     *
     * @param configFile File
     */
    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    /**
     * 功能：设置配置文件的输入流
     *
     * @param in InputStream
     */
    public void setConfigInputStream(InputStream in) {
        this.in = in;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * 功能：卸载加载的配置
     * <B>默认仅仅释放由加载近来的数据。<B>
     * <B>如果具体的加载器有额外的卸载工作，应当覆盖本方法。例如数据库连接池的卸载还应当卸载数据库连接</B>
     */
    public void unloadConfig() {
        if (loadedResult != null) {
            loadedResult.releaseData();
        }
        loadedResult = null;
    }

    /**
     * 功能：返回根据配置文件加载后的结果对象
     *
     * @return LoadedResult
     */
    public ConfigLoadedResult getLoadedResult() {
        return loadedResult;
    }
}
