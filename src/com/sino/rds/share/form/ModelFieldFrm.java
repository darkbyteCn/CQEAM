package com.sino.rds.share.form;

import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;


/**
 * <p>Title: 数据模型字段定义 RDS_MODEL_FIELD</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ModelFieldFrm extends RDSBaseFrm {

    private String fieldId = "";
    private String modelId = "";
    private String fieldName = "";
    private String fieldDesc = "";
    private String fieldType = "";

    private String modelName = "";
    private String dataSrcType = "";
    private String dataSrcTypeName = "";
    private String owner = "";

    private WebOptions modelOptions = null;

    public ModelFieldFrm() {
        super();
        primaryKeyName = "fieldId";
    }

    /**
     * 功能：设置数据模型字段定义属性 字段ID
     *
     * @param fieldId String
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 功能：设置数据模型字段定义属性 所属模型
     *
     * @param modelId String
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * 功能：设置数据模型字段定义属性 字段名称
     *
     * @param fieldName String
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 功能：设置数据模型字段定义属性 字段描述
     *
     * @param fieldDesc String
     */
    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    /**
     * 功能：设置数据模型字段定义属性 字段类型
     *
     * @param fieldType String
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * 功能：获取数据模型字段定义属性 字段ID
     *
     * @return String
     */
    public String getFieldId() {
        return this.fieldId;
    }

    /**
     * 功能：获取数据模型字段定义属性 所属模型
     *
     * @return String
     */
    public String getModelId() {
        return this.modelId;
    }

    /**
     * 功能：获取数据模型字段定义属性 字段名称
     *
     * @return String
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * 功能：获取数据模型字段定义属性 字段描述
     *
     * @return String
     */
    public String getFieldDesc() {
        return this.fieldDesc;
    }

    /**
     * 功能：获取数据模型字段定义属性 字段类型
     *
     * @return String
     */
    public String getFieldType() {
        return this.fieldType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public WebOptions getModelOptions() {
        return modelOptions;
    }

    public void setModelOptions(WebOptions modelOptions) {
        this.modelOptions = modelOptions;
    }

    public String getDataSrcTypeName() {
        return dataSrcTypeName;
    }

    public void setDataSrcTypeName(String dataSrcTypeName) {
        this.dataSrcTypeName = dataSrcTypeName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDataSrcType() {
        return dataSrcType;
    }

    public void setDataSrcType(String dataSrcType) {
        this.dataSrcType = dataSrcType;
    }
}