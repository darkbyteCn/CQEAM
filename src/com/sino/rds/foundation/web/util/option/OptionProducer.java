package com.sino.rds.foundation.web.util.option;

import com.sino.base.constant.WorldConstant;
import com.sino.base.exception.HandlerException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.Iterator;
import java.util.Map;


public abstract class OptionProducer {
    protected String[] attributeNames = null;
    protected String[] srcProperties = null;
    protected boolean hasAttribute = false;

    OptionProducer() {
    }

    protected OptionProduceRule produceRule = null;

    /**
     * 功能：设置列表产生规则
     *
     * @param produceRule OptionProduceRule
     */
    public void setProduceRule(OptionProduceRule produceRule) {
        this.produceRule = produceRule;
    }

    /**
     * 功能：获取Web下拉框列表
     *
     * @return WebOptions
     * @throws WebException
     */
    public abstract WebOptions getOptions() throws WebException;

    /**
     * 功能：获取下拉列表框
     *
     * @return String
     * @throws WebException
     */
    public String getSelectHtml() throws WebException {
        StringBuilder selectHtml = new StringBuilder();
        if (!StrUtil.isEmpty(produceRule.getSelectName())) {
            selectHtml.append("<select name=\"");
            selectHtml.append(produceRule.getSelectName());
            selectHtml.append("\"");
            if (produceRule.isMultiple()) {
                selectHtml.append(" multiple");
            }
            if (produceRule.getHandlers() != null) {
                selectHtml.append(getHandlerHtml(produceRule.getHandlers()));
            }
            selectHtml.append(" style=\"width:100%;height:100%\">");
            selectHtml.append(getOptions());
            selectHtml.append("</select>");
        }
        return selectHtml.toString();
    }

    /**
     * 功能：加入事件处理器程序
     *
     * @param handlers EventHandlers
     * @return StringBuilder
     */
    private StringBuilder getHandlerHtml(EventHandlers handlers) {
        StringBuilder handlerHtm = new StringBuilder();
        try {
            if (handlers != null) {
                int handlerCount = handlers.getCount();
                EventHandler handler = null;
                String funName = "";
                for (int i = 0; i < handlerCount; i++) {
                    handlerHtm.append(WorldConstant.EMPTY_SPACE);
                    handler = handlers.getHandler(i);
                    handlerHtm.append(handler.getEventName());
                    handlerHtm.append("=\"");
                    funName = handler.getFunName();
                    int index = funName.indexOf("(");
                    if (index > -1) {
                        funName = funName.substring(0, index);
                    }
                    handlerHtm.append(funName);
                    handlerHtm.append("(this)\"");
                }
            }
        } catch (HandlerException ex) {
            ex.printLog();
        }
        return handlerHtm;
    }

    protected void produceAttributeNames(){
        Map<String, String> attributes = produceRule.getAttributes();
        hasAttribute = (attributes != null && !attributes.isEmpty());
        if (hasAttribute) {
            attributeNames = new String[attributes.size()];
            srcProperties = new String[attributes.size()];
            Iterator<String> nameIterator = attributes.keySet().iterator();
            int i = 0;
            while (nameIterator.hasNext()) {
                String attributeName = nameIterator.next();
                attributeNames[i] = attributeName;
                srcProperties[i] = attributes.get(attributeName);
                i++;
            }
        }
    }
}

