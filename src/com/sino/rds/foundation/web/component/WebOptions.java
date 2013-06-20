package com.sino.rds.foundation.web.component;

import com.sino.base.SinoBaseObject;
import com.sino.base.constant.WorldConstant;
import com.sino.base.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>@todo：在此加入本组件具体的描述</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 0.1
 */
public class WebOptions extends SinoBaseObject implements Cloneable {

    private List<WebOption> options = null;
    private List<String> valueList = null;
    private String selectedValue = "";

    public WebOptions() {
        super();
        options = new ArrayList<WebOption>();
        this.valueList = new ArrayList<String>();
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    /**
     * 功能：产生空的下拉框选项
     */
    public void addBlankOption() {
        WebOption option = new WebOption();
        option.setLabel("----请选择----");
        option.setValue("");
        valueList.add("");
        options.add(0, option);
    }

    /**
     * 功能：加入选项
     *
     * @param option WebOption
     */
    public void addOption(WebOption option) {
        if (option == null) {
            return;
        }
        String value = option.getValue();
        if (value == null) {
            return;
        }
        if (!valueList.contains(value)) {
            valueList.add(value);
            options.add(option);
        }
    }

    /**
     * 功能：判断option选项集合是否包含参数选项
     *
     * @param option WebOption
     * @return boolean
     */
    public boolean contains(WebOption option) {
        boolean contains = false;
        if (option != null) {
            String value = option.getValue();
            int optCount = options.size();
            WebOption ele = null;
            String eleValue = "";
            for (int i = 0; i < optCount; i++) {
                ele = options.get(i);
                eleValue = ele.getValue();
                if (eleValue == null) {
                    contains = (eleValue == value);
                } else {
                    contains = (eleValue.equals(value));
                }
                if (contains) {
                    break;
                }
            }
        }
        return contains;
    }

    /**
     * 功能：移除指定选项
     *
     * @param option WebOption
     */
    public void removeOption(WebOption option) {
        int index = options.indexOf(option);
        if (index != -1) {
            options.remove(index);
            valueList.remove(index);
        }
    }

    /**
     * 功能：移除option选项集合中指定值的选项
     *
     * @param optionValue String
     * @return WebOption
     */
    public WebOption removeOption(String optionValue) {
        WebOption option = null;
        int index = valueList.indexOf(optionValue);
        if (index > -1) {
            valueList.remove(index);
            option = options.remove(index);
        }
        return option;
    }

    public int getSize() {
        return options.size();
    }

    public WebOption getOption(int index) {
        WebOption option = null;
        if (index > -1 && index < getSize()) {
            option = options.get(index);
        }
        return option;
    }

    public WebOption getOption(String optionValue) {
        WebOption option = null;
        int index = indexOf(optionValue);
        if (index > -1) {
            option = options.remove(index);
        }
        return option;
    }


    public int indexOf(WebOption option) {
        return options.indexOf(option);
    }

    public int indexOf(String optionValue) {
        return valueList.indexOf(optionValue);
    }

    public String toString() {
        StringBuilder webStr = new StringBuilder();
        processSelectedValue();
        int optionCount = getSize();
        if (optionCount > 0) {
            webStr.append(WorldConstant.ENTER_CHAR);
            WebOption option = null;
            for (int i = 0; i < optionCount; i++) {
                option = options.get(i);
                webStr.append(WorldConstant.TAB_CHAR);
                webStr.append(option);
                webStr.append(WorldConstant.ENTER_CHAR);
            }
        }
        return webStr.toString();
    }

    public String toXMLString() {
        StringBuilder str = new StringBuilder();
        processSelectedValue();
        int optionCount = valueList.size();
        if (optionCount > 0) {
            str.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
            str.append(WorldConstant.ENTER_CHAR);
            str.append(WorldConstant.TAB_CHAR);
            str.append("<options>");
            for (int i = 0; i < optionCount; i++) {
                WebOption option = options.get(i);
                str.append(WorldConstant.ENTER_CHAR);
                str.append(WorldConstant.TAB_CHAR);
                str.append(WorldConstant.TAB_CHAR);
                str.append(option.toXMLContent());
            }
            str.append(WorldConstant.ENTER_CHAR);
            str.append(WorldConstant.TAB_CHAR);
            str.append("</options>");
        }
        return str.toString();
    }

    private void processSelectedValue() {
        if (!StrUtil.isEmpty(selectedValue)) {
            if (valueList.size() > 0) {
                for (WebOption option : options) {
                    if (selectedValue.equals(option.getValue())) {
                        option.setSelected(true);
                    } else if (!option.getValue().equals(selectedValue) && option.isSelected()) {
                        option.setSelected(false);
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        return options.isEmpty();
    }

    public Object clone() {
        return super.clone();
    }
}
