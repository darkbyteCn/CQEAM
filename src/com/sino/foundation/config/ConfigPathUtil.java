package com.sino.foundation.config;

import com.sino.base.constant.WorldConstant;
import com.sino.base.util.PathUtil;
import com.sino.base.util.StrUtil;

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
public abstract class ConfigPathUtil {
    /**
     * 功能：获取带文件分隔符结尾的配置文件路径。
     *
     * @param relativePath String
     * @return String
     */
    public static String getConfigPath(String relativePath) {
        String configPath = "";
        if (relativePath.startsWith("classpath:")) {
            relativePath = StrUtil.trim(relativePath, "classpath:", true);
            configPath = PathUtil.getAbsolutePath("", relativePath);
        } else if (relativePath.indexOf("WEB-INF/classes") == 0) {
            relativePath = StrUtil.trim(relativePath, "WEB-INF/classes");
            configPath = PathUtil.getAbsolutePath("", relativePath);
        } else if (relativePath.indexOf("/WEB-INF/classes") == 0) {
            relativePath = StrUtil.trim(relativePath, "/WEB-INF/classes");
            configPath = PathUtil.getAbsolutePath("", relativePath.substring(1));
        } else {
            configPath = PathUtil.getAbsolutePath(relativePath);
        }
        configPath = StrUtil.replaceStr(configPath, "%20", WorldConstant.EMPTY_SPACE);
        return configPath;
    }
}
