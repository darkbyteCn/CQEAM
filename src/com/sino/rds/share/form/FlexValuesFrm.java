package com.sino.rds.share.form;

/**
 * <p>Title: RDS_FLEX_VALUES</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class FlexValuesFrm extends FlexValueSetFrm {

    private String valueId = "";
    private String code = "";
    private String value = "";
    private String description = "";

    public FlexValuesFrm() {
        super();
        primaryKeyName = "valueId";
    }

    /**
     * 功能：设置RDS_FLEX_VALUES属性 字典值ID
     *
     * @param valueId String
     */
    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    /**
     * 功能：设置RDS_FLEX_VALUES属性 字典代码
     *
     * @param code String
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 功能：设置RDS_FLEX_VALUES属性 字典值
     *
     * @param value String
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 功能：设置RDS_FLEX_VALUES属性 字典描述
     *
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * 功能：获取RDS_FLEX_VALUES属性 字典值ID
     *
     * @return String
     */
    public String getValueId() {
        return this.valueId;
    }

    /**
     * 功能：获取RDS_FLEX_VALUES属性 字典代码
     *
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 功能：获取RDS_FLEX_VALUES属性 字典值
     *
     * @return String
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 功能：获取RDS_FLEX_VALUES属性 字典描述
     *
     * @return String
     */
    public String getDescription() {
        return this.description;
    }
}