package com.sino.rds.foundation.web.util.radio;

import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebRadio;

public abstract class RadioProducer {
    RadioProducer() {
    }

    protected RadioProduceRule produceRule = null;

    /**
     * 功能：设置列表产生规则
     *
     * @param produceRule OptionProduceRule
     */
    public void setProduceRule(RadioProduceRule produceRule) {
        this.produceRule = produceRule;
    }

    /**
     * 功能：获取Web下拉框列表
     *
     * @return WebOptions
     * @throws com.sino.rds.foundation.exception.WebException
     *
     */
    public abstract WebRadio getWebRadio() throws WebException;
}

