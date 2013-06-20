package com.sino.foundation.config;

import com.sino.base.log.Logger;


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
public class PostProcessorContainer<T extends ConfigLoadedResult> extends Thread {

    private ConfigPostProcessor<T> postProcessor = null;
    private T configResult = null;
    private long delayTime = 0L;//如果本配置有后置处理器，此选项有效，表示后置处理器延迟启动的毫秒数

    public PostProcessorContainer(ConfigPostProcessor<T> postProcessor, T configResult, long delayTime){
        this.postProcessor = postProcessor;
        this.configResult = configResult;
        this.delayTime = delayTime;
    }

    public void run() {
        try {
            sleep(delayTime * 1000);
            postProcessor.postProcessConfig(configResult);
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
    }
}

