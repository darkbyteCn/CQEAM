package com.sino.foundation.config;

import com.sino.base.exception.StrException;
import com.sino.base.util.StrUtil;

import java.io.File;
import java.io.FilenameFilter;

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
public class ConfigFileFilter implements FilenameFilter {
    private String fileExtention = "";

    public ConfigFileFilter(String fileExtention) {
        if (fileExtention != null) {
            this.fileExtention = fileExtention;
        }
    }

    public boolean accept(File fileDir, String name) {
        boolean acceptValue = true;
        try {
            File file = new File(fileDir, name);
            if (file.isDirectory()) {
                acceptValue = true;
            } else if (fileExtention.length() > 0) {
                acceptValue = StrUtil.endsWith(name, fileExtention);
            }
        } catch (StrException ex) {
            ex.printLog();
            acceptValue = false;
        }
        return acceptValue;
    }

    /**
     * 功能：是否可加载指定文件
     *
     * @param file 文件对象
     * @return true表示符合加载条件, false表示不符合加载条件
     */
    public boolean isCertificate(File file) {
        boolean needLoadConfig = true;
        try {
            if (file == null) {
                needLoadConfig = false;
            } else {
                String name = file.getName();
                needLoadConfig = StrUtil.endsWith(name, fileExtention);
            }
        } catch (StrException ex) {
            ex.printLog();
            needLoadConfig = false;
        }
        return needLoadConfig;
    }
}