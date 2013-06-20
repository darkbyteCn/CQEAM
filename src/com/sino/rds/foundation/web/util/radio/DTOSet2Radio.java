package com.sino.rds.foundation.web.util.radio;

import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.log.Logger;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebRadio;

public class DTOSet2Radio extends RadioProducer {

    DTOSet2Radio() {

    }


    /**
     * 功能：获取单选按钮列表
     *
     * @return WebOptions
     */
    public WebRadio getWebRadio() throws WebException {
        WebRadio webRadio = null;
        try {
            DTOSet listData = (DTOSet) produceRule.getDataSource();
            String valueField = produceRule.getValueField();
            String descField = produceRule.getDescField();
            if (listData != null && !StrUtil.isEmpty(valueField) && !StrUtil.isEmpty(descField)) {
                webRadio = new WebRadio(produceRule.getCheckBoxName());
                int dataCount = listData.getSize();
                webRadio.setCheckedValue(produceRule.getCheckedValue());
                for (int i = 0; i < dataCount; i++) {
                    DTO dto = listData.getDTO(i);
                    String value = StrUtil.nullToString(ReflectionUtil.getProperty(dto, valueField));
                    String label = StrUtil.nullToString(ReflectionUtil.getProperty(dto, descField));
                    if (value != null) {
                        webRadio.addValueCaption(value, label);
                    }
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

