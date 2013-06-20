package com.sino.foundation.config;

import com.sino.base.exception.ConfigException;

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
public interface ConfigPostProcessor<T extends ConfigLoadedResult> {

    /**
     * 功能：配置文件加载结果后处理器
     * @param configResult 加载结果
     * @throws com.sino.base.exception.ConfigException 处理失败时统一抛出配置异常
     */
    public void postProcessConfig(T configResult) throws ConfigException;
}
