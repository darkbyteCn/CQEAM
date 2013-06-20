package com.sino.foundation.validate;


import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.foundation.exception.EmptyException;

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
public class JavaBeanValidator {

    public static void validateJavaBean(Object javaBean) throws EmptyException {
        String errorMsg = "";
        if (javaBean == null) {
            errorMsg = "对象不能为空";
            throw new EmptyException(errorMsg);
        }
        List properties = ReflectionUtil.getProperties(javaBean.getClass());
        int propCount = properties.size();
        String fieldName = "";
        Object fieldValue = null;
        String className = javaBean.getClass().getName();
        try {
            for (int i = 0; i < propCount; i++) {
                fieldName = String.valueOf(properties.get(i));
                fieldValue = ReflectionUtil.getProperty(javaBean, fieldName);
                if (StrUtil.isEmpty(fieldValue)) {
                    errorMsg = "对象“"
                            + className
                            + "”的字段“"
                            + fieldName
                            + "”的值为空";
                    throw new EmptyException(errorMsg);
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new EmptyException(ex.getMessage());
        }
    }

    public static void validateJavaBean(Object javaBean, List<String> ignoreFields) throws EmptyException {
        String errorMsg = "";
        if (javaBean == null) {
            errorMsg = "对象不能为空";
            throw new EmptyException(errorMsg);
        }
        List properties = ReflectionUtil.getProperties(javaBean.getClass());
        int propCount = properties.size();
        String fieldName = "";
        Object fieldValue = null;
        String className = javaBean.getClass().getName();
        try {
            for (int i = 0; i < propCount; i++) {
                fieldName = String.valueOf(properties.get(i));
                if (ignoreFields.contains(fieldName)) {
                    continue;
                }
                fieldValue = ReflectionUtil.getProperty(javaBean, fieldName);
                if (StrUtil.isEmpty(fieldValue)) {
                    errorMsg = "对象“"
                            + className
                            + "”的字段“"
                            + fieldName
                            + "”的值为空";
                    throw new EmptyException(errorMsg);
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new EmptyException(ex.getMessage());
        }
    }
}
