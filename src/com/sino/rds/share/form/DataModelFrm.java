package com.sino.rds.share.form;

import com.sino.base.ExtendedArrayList;
import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.List;

/**
 * <p>Title: 报表数据模型 RDS_DATA_MODEL</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class DataModelFrm extends RDSBaseFrm {

    private String modelId = "";
    private String modelIds = "";
    private String modelName = "";
    private String modelDesc = "";
    private String dataSrcType = "";
    private String dataSrcTypeName = "";
    private WebOptions dataSrcOptions = null;
    private String modelSql = "";
    private String owner = "";
    private String modelCheckViewName = "";
    private String connectionId = "";
    private String connectionName = "";
    private WebOptions ownerOptions = null;
    private WebOptions connectionOptions = null;
    private WebOptions modelOptions = null;
    private List<ModelFieldFrm> fields = new ExtendedArrayList(ModelFieldFrm.class);

    public DataModelFrm() {
        super();
        primaryKeyName = "modelId";
    }

    /**
     * 功能：设置报表数据模型属性 模型ID
     *
     * @param modelId String
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * 功能：设置报表数据模型属性 模型名称
     *
     * @param modelName String
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * 功能：设置报表数据模型属性 模型描述
     *
     * @param modelDesc String
     */
    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    /**
     * 功能：设置报表数据模型属性 数据源类型（1：表，2：视图，3：查询语句）
     *
     * @param dataSrcType String
     */
    public void setDataSrcType(String dataSrcType) {
        this.dataSrcType = dataSrcType;
    }

    /**
     * 功能：设置报表数据模型属性 模型查询SQL
     *
     * @param modelSql String
     */
    public void setModelSql(String modelSql) {
        this.modelSql = modelSql;
    }


    /**
     * 功能：获取报表数据模型属性 模型ID
     *
     * @return String
     */
    public String getModelId() {
        return this.modelId;
    }

    /**
     * 功能：获取报表数据模型属性 模型名称
     *
     * @return String
     */
    public String getModelName() {
        return this.modelName;
    }

    /**
     * 功能：获取报表数据模型属性 模型描述
     *
     * @return String
     */
    public String getModelDesc() {
        return this.modelDesc;
    }

    /**
     * 功能：获取报表数据模型属性 数据源类型（1：表，2：视图，3：查询语句）
     *
     * @return String
     */
    public String getDataSrcType() {
        return this.dataSrcType;
    }

    /**
     * 功能：获取报表数据模型属性 模型查询SQL
     *
     * @return String
     */
    public String getModelSql() {
        return this.modelSql;
    }


    public String getDataSrcTypeName() {
        return dataSrcTypeName;
    }

    public void setDataSrcTypeName(String dataSrcTypeName) {
        this.dataSrcTypeName = dataSrcTypeName;
    }

    public WebOptions getDataSrcOptions() {
        return dataSrcOptions;
    }

    public void setDataSrcOptions(WebOptions dataSrcOptions) {
        this.dataSrcOptions = dataSrcOptions;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public WebOptions getOwnerOptions() {
        return ownerOptions;
    }

    public void setOwnerOptions(WebOptions ownerOptions) {
        this.ownerOptions = ownerOptions;
    }

    public String getModelCheckViewName() {
        return modelCheckViewName;
    }

    public void setModelCheckViewName(String modelCheckViewName) {
        this.modelCheckViewName = modelCheckViewName;
    }

    public List<ModelFieldFrm> getFields() {
        return fields;
    }

    public void setFields(List<ModelFieldFrm> fields) {
        this.fields = fields;
    }

    public WebOptions getModelOptions() {
        return modelOptions;
    }

    public void setModelOptions(WebOptions modelOptions) {
        this.modelOptions = modelOptions;
    }

    public String getModelIds() {
        return modelIds;
    }

    public void setModelIds(String modelIds) {
        this.modelIds = modelIds;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public WebOptions getConnectionOptions() {
        return connectionOptions;
    }

    public void setConnectionOptions(WebOptions connectionOptions) {
        this.connectionOptions = connectionOptions;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }
}