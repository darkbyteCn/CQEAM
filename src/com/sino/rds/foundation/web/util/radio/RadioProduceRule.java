package com.sino.rds.foundation.web.util.radio;

import com.sino.base.SinoBaseObject;
import com.sino.base.data.RowSet;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.web.EventHandlers;

import java.sql.Connection;
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
public class RadioProduceRule extends SinoBaseObject {
    private String valueField = "";
    private String descField = "";
    private String checkedValue = "";
    private Object dataSource = null;
    private Connection conn = null;
    private String checkBoxName = "";
    private EventHandlers handlers = null;

    public static final int TYPE_UNSUPPORTED = -1;
    public static final int TYPE_ROW = 1;
    public static final int TYPE_DTO = 2;
    public static final int TYPE_LIST = 3;
    public static final int TYPE_SQL = 4;
    private int dataType = TYPE_UNSUPPORTED;

    public String getValueField() {
        return valueField;
    }

    public String getDescField() {
        return descField;
    }

    public String getCheckedValue() {
        return checkedValue;
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

    public void setCheckedValue(String checkedValue) {
        this.checkedValue = checkedValue;
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
                this.dataType = RadioProduceRule.TYPE_LIST;
            } else if (dataSource instanceof DTOSet) {
                this.dataType = RadioProduceRule.TYPE_DTO;
            } else if (dataSource instanceof RowSet) {
                this.dataType = RadioProduceRule.TYPE_ROW;
            } else if (dataSource instanceof SQLModel) {
                this.dataType = RadioProduceRule.TYPE_SQL;
            }
        }
    }

    public void setHandlers(EventHandlers handlers) {
        this.handlers = handlers;
    }

    public void setCheckBoxName(String checkBoxName) {
        this.checkBoxName = checkBoxName;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public int getDataType() {
        return dataType;
    }

    public EventHandlers getHandlers() {
        return handlers;
    }

    public String getCheckBoxName() {
        return checkBoxName;
    }
}
