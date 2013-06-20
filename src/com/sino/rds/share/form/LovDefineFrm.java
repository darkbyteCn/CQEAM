package com.sino.rds.share.form;

import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.component.WebRadio;

/**
 * <p>Title: RDS_LOV_DEFINE</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class LovDefineFrm extends RDSBaseFrm {
    private String lovId = "";
    private String lovIds = "";
    private String lovName = "";
    private String lovType = "";
    private String lovTypeName = "";
    private String lovSql = "";
    private String lovValue = "";
    private String addBlank = "";
    private String addBlankName = "";
    private String javascriptFunction = "";
    private String connectionId = "";
    private String connectionName = "";

    private WebRadio lovTypeRadio = null;
    private WebRadio addBlankRadio = null;
    private WebOptions connectionOptions = null;

    public LovDefineFrm(){
        this.primaryKeyName = "lovId";
    }

    public String getLovId() {
        return lovId;
    }

    public void setLovId(String lovId) {
        this.lovId = lovId;
    }

    public String getLovType() {
        return lovType;
    }

    public void setLovType(String lovType) {
        this.lovType = lovType;
    }

    public String getLovSql() {
        return lovSql;
    }

    public void setLovSql(String lovSql) {
        this.lovSql = lovSql;
    }

    public String getLovValue() {
        return lovValue;
    }

    public void setLovValue(String lovValue) {
        this.lovValue = lovValue;
    }

    public String getLovTypeName() {
        return lovTypeName;
    }

    public void setLovTypeName(String lovTypeName) {
        this.lovTypeName = lovTypeName;
    }

    public WebRadio getLovTypeRadio() {
        return lovTypeRadio;
    }

    public void setLovTypeRadio(WebRadio lovTypeRadio) {
        this.lovTypeRadio = lovTypeRadio;
    }

    public String getLovName() {
        return lovName;
    }

    public void setLovName(String lovName) {
        this.lovName = lovName;
    }

    public String getAddBlank() {
        return addBlank;
    }

    public void setAddBlank(String addBlank) {
        this.addBlank = addBlank;
    }

    public String getJavascriptFunction() {
        return javascriptFunction;
    }

    public void setJavascriptFunction(String javascriptFunction) {
        this.javascriptFunction = javascriptFunction;
    }

    public WebRadio getAddBlankRadio() {
        return addBlankRadio;
    }

    public void setAddBlankRadio(WebRadio addBlankRadio) {
        this.addBlankRadio = addBlankRadio;
    }

    public String getLovIds() {
        return lovIds;
    }

    public void setLovIds(String lovIds) {
        this.lovIds = lovIds;
    }

    public String getAddBlankName() {
        return addBlankName;
    }

    public void setAddBlankName(String addBlankName) {
        this.addBlankName = addBlankName;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public WebOptions getConnectionOptions() {
        return connectionOptions;
    }

    public void setConnectionOptions(WebOptions connectionOptions) {
        this.connectionOptions = connectionOptions;
    }
}
