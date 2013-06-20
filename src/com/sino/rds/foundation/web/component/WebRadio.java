package com.sino.rds.foundation.web.component;

import com.sino.base.SinoBaseObject;
import com.sino.base.constant.WorldConstant;
import com.sino.base.exception.HandlerException;
import com.sino.base.util.StrUtil;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebRadio extends SinoBaseObject {
    private String radioName = "";
    private Map<String, String> valueCaptions = null;
    private String checkedValue = "";
    private EventHandlers handlers = null;
    private String fontColor = "#000000";
    private int fontSize = 0;
    private Map<String, Map<String, String>> attributes = null;
    private boolean disabled = false;

    public WebRadio(String radioName) {
        super();
        this.valueCaptions = new HashMap<String, String>();
        this.attributes = new HashMap<String, Map<String, String>>();
        setRadioName(radioName);
    }

    public void addAttribute(String radioValue, Map<String, String> attribute) {
        if (!StrUtil.isEmpty(radioValue)) {
            attributes.put(radioValue, attribute);
        }
    }

    public void addAttribute(String radioValue, String attributeName, String attributeValue) {
        if (!StrUtil.isEmpty(radioValue)) {
            Map<String, String> attribute = attributes.get(attributeName);
            if (attribute == null) {
                attribute = new HashMap<String, String>();
            }
            attribute.put(attributeName, attributeValue);
            attributes.put(radioValue, attribute);
        }
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public void addValueCaption(String radioValue, String radioCaption) {
        if (StrUtil.isEmpty(radioValue) || StrUtil.isEmpty(radioCaption)) {
            return;
        }
        valueCaptions.put(radioValue, radioCaption);
    }

    public void setFontColor(String fontColor) {
        if (StrUtil.isColorStr(fontColor)) {
            this.fontColor = fontColor;
        }
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setCheckedValue(String checkedValue) {
        if (!StrUtil.isEmpty(checkedValue)) {
            this.checkedValue = checkedValue;
        }
    }

    /**
     * 功能：加入事件处理器
     *
     * @param handlers EventHandlers
     */
    public void addEventHandlers(EventHandlers handlers) {
        if (handlers != null) {
            this.handlers = handlers;
        }
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String toString() {
        StringBuilder radioHtml = new StringBuilder();
        Iterator<String> iterator = valueCaptions.keySet().iterator();
        String radioValue = "";
        String radioCaption = "";
        String checkProp = "";
        String radioId = "";
        while (iterator.hasNext()) {
            radioValue = iterator.next();
            radioCaption = valueCaptions.get(radioValue);
            if (radioValue.equals(checkedValue)) {
                checkProp = "checked";
            } else {
                checkProp = "";
            }
            radioId = radioName + "_" + radioValue;
            radioHtml.append("<input type=\"radio\" name=\"");
            radioHtml.append(radioName);
            radioHtml.append("\" value=\"");
            radioHtml.append(radioValue);
            radioHtml.append("\" id=\"");
            radioHtml.append(radioId);
            radioHtml.append("\" ");
            radioHtml.append(checkProp);
            radioHtml.append(appendAttributes(radioValue));
            radioHtml.append(appendHandlers());
            if(disabled){
                radioHtml.append(" disabled=\"true\"");
            }
            radioHtml.append("><label for=\"");
            radioHtml.append(radioId);
            radioHtml.append("\">");
            radioHtml.append("<font color=\"");
            radioHtml.append(fontColor);
            if (fontSize > 0) {
                radioHtml.append("\"");
                radioHtml.append(WorldConstant.EMPTY_SPACE);
                radioHtml.append("size=\"");
                radioHtml.append(fontSize);
            }
            radioHtml.append("\">");
            radioHtml.append(radioCaption);
            radioHtml.append("</font>");
            radioHtml.append("</label>");
            radioHtml.append(WorldConstant.ENTER_CHAR);
        }
        return radioHtml.toString();
    }

    /**
     * 功能：加入自定义非标准属性
     *
     * @param radioValue 单选按钮的值
     * @return StringBuilder
     */
    private StringBuilder appendAttributes(String radioValue) {
        StringBuilder attributeHtml = new StringBuilder();
        if (attributes != null) {
            Map<String, String> attrs = attributes.get(radioValue);
            if (attrs != null) {
                Iterator<String> iterator = attrs.keySet().iterator();
                while (iterator.hasNext()) {
                    String attributeName = iterator.next();
                    String attributeValue = attrs.get(attributeName);
                    attributeHtml.append(WorldConstant.EMPTY_SPACE);
                    attributeHtml.append(attributeName);
                    attributeHtml.append("=\"");
                    attributeHtml.append(attributeValue);
                    attributeHtml.append("\"");
                }
            }
        }
        return attributeHtml;
    }


    /**
     * 功能：加入事件处理器程序
     *
     * @return StringBuilder
     */
    private StringBuilder appendHandlers() {
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
                    handlerHtm.append("=");
                    funName = handler.getFunName();
                    int index = funName.indexOf("(");
                    if (index > -1) {
                        funName = funName.substring(0, index);
                    }
                    handlerHtm.append(funName);
                    handlerHtm.append("(this)");
                }
            }
        } catch (HandlerException ex) {
            ex.printLog();
        }
        return handlerHtm;
    }
}