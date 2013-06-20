package com.sino.rds.foundation.web.util.radio;

import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebRadio;

import java.util.List;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class List2Radio extends RadioProducer {
    List2Radio() {
    }


    /**
     * 功能：获取单选按钮列表
     *
     * @return WebOptions
     */
    public WebRadio getWebRadio() throws WebException {
        WebRadio webRadio = null;
        try {
            List listData = (List) produceRule.getDataSource();
            String valueField = produceRule.getValueField();
            String descField = produceRule.getDescField();
            if (listData != null && !StrUtil.isEmpty(valueField) && !StrUtil.isEmpty(descField)) {
                webRadio = new WebRadio(produceRule.getCheckBoxName());
                int dataCount = listData.size();
                webRadio.setCheckedValue(produceRule.getCheckedValue());
                for (int i = 0; i < dataCount; i++) {
                    Object data = listData.get(i);
                    String value = StrUtil.nullToString(ReflectionUtil.getProperty(data, valueField));
                    String label = StrUtil.nullToString(ReflectionUtil.getProperty(data, descField));
                    webRadio.addValueCaption(value, label);
                }
                webRadio.addEventHandlers(produceRule.getHandlers());
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new WebException(ex.getMessage());
        }
        return webRadio;
    }
}
