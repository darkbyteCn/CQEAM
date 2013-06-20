package com.sino.rds.share.form;

import com.sino.rds.appbase.form.RDSBaseFrm;

/**
 * <p>Title: RDS_FLEX_VALUE_SET</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class FlexValueSetFrm extends RDSBaseFrm {

    private String valueSetId = "";
    private String valueSetCode = "";
    private String valueSetName = "";
    private String valueSetDescription = "";

    public FlexValueSetFrm() {
        super();
        primaryKeyName = "valueSetId";
    }

    /**
     * 功能：设置RDS_FLEX_VALUE_SET属性 字典分类ID
     *
     * @param valueSetId String
     */
    public void setValueSetId(String valueSetId) {
        this.valueSetId = valueSetId;
    }

    /**
     * 功能：设置RDS_FLEX_VALUE_SET属性 字典分类代码
     *
     * @param valueSetCode String
     */
    public void setValueSetCode(String valueSetCode) {
        this.valueSetCode = valueSetCode;
    }

    /**
     * 功能：设置RDS_FLEX_VALUE_SET属性 字典分类名称
     *
     * @param valueSetName String
     */
    public void setValueSetName(String valueSetName) {
        this.valueSetName = valueSetName;
    }

    /**
     * 功能：设置RDS_FLEX_VALUE_SET属性 字典分类描述
     *
     * @param valueSetDescription String
     */
    public void setValueSetDescription(String valueSetDescription) {
        this.valueSetDescription = valueSetDescription;
    }

    /**
     * 功能：获取RDS_FLEX_VALUE_SET属性 字典分类ID
     *
     * @return String
     */
    public String getValueSetId() {
        return this.valueSetId;
    }

    /**
     * 功能：获取RDS_FLEX_VALUE_SET属性 字典分类代码
     *
     * @return String
     */
    public String getValueSetCode() {
        return this.valueSetCode;
    }

    /**
     * 功能：获取RDS_FLEX_VALUE_SET属性 字典分类名称
     *
     * @return String
     */
    public String getValueSetName() {
        return this.valueSetName;
    }

    /**
     * 功能：获取RDS_FLEX_VALUE_SET属性 字典分类描述
     *
     * @return String
     */
    public String getValueSetDescription() {
        return this.valueSetDescription;
    }
}