package com.sino.rds.foundation.web.util.option;

import com.sino.base.SinoBaseObject;
import com.sino.base.data.RowSet;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.util.StrUtil;
import com.sino.base.web.EventHandlers;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class OptionProduceRule extends SinoBaseObject {
    private String valueField = "";
    private String descField = "";
    private String selectedValue = "";
    private boolean addBlank = false;
    private String rightStepStr = "";
    private Object dataSource = null;
    private Connection conn = null;
    private boolean rightStep = false;
    private boolean multiple = false;
    private String selectName = "";
    private EventHandlers handlers = null;

    public static final int TYPE_UNSUPPORTED = -1;
    public static final int TYPE_ROW = 1;
    public static final int TYPE_DTO = 2;
    public static final int TYPE_LIST = 3;
    public static final int TYPE_SQL = 4;
    private int dataType = TYPE_UNSUPPORTED;

    private Map<String, String> attributes = null;

    public OptionProduceRule(){
        attributes = new LinkedHashMap<String, String>();
    }

    public String getValueField() {
        return valueField;
    }

    public String getDescField() {
        return descField;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public boolean isAddBlank() {
        return addBlank;
    }

    public String getRightStepStr() {
        return rightStepStr;
    }

    public Object getDataSource() {
        return dataSource;
    }

    public Connection getConnection() {
        return conn;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public void setDescField(String descField) {
        this.descField = descField;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public void setAddBlank(boolean addBlank) {
        this.addBlank = addBlank;
    }

    public void setRightStepStr(String rightStepStr) {
        if (!StrUtil.isEmpty(rightStepStr)) {
            this.rightStepStr = rightStepStr;
            rightStep = true;
        }
    }

    /**
     * 功能：设置数据源
     *
     * @param dataSource Object
     */
    public void setDataSource(Object dataSource) {
        if (dataSource != null) {
            this.dataSource = dataSource;
            if (dataSource instanceof List) {
                this.dataType = OptionProduceRule.TYPE_LIST;
            } else if (dataSource instanceof DTOSet) {
                this.dataType = OptionProduceRule.TYPE_DTO;
            } else if (dataSource instanceof RowSet) {
                this.dataType = OptionProduceRule.TYPE_ROW;
            } else if (dataSource instanceof SQLModel) {
                this.dataType = OptionProduceRule.TYPE_SQL;
            }
        }
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public void setHandlers(EventHandlers handlers) {
        this.handlers = handlers;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public boolean isRightStep() {
        return rightStep;
    }

    public int getDataType() {
        return dataType;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public EventHandlers getHandlers() {
        return handlers;
    }

    public String getSelectName() {
        return selectName;
    }

    public void addAttribute(String attributeName, String srcProperty){
        if(StrUtil.isEmpty(attributeName) || StrUtil.isEmpty(srcProperty)){
            return;
        }
        attributes.put(attributeName, srcProperty);
    }

    public Map<String, String>  getAttributes(){
        return attributes;
    }
}
