package com.sino.rds.share.form;

import com.sino.base.util.StrUtil;
import com.sino.rds.appbase.form.RDSBaseFrm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Title: 报表查询条件 RDS_REPORT_PARAMETER</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class SearchParameterFrm extends RDSBaseFrm {
    private Map<String, String> parameters = null;
    private List<String> fieldNames = null;
    public SearchParameterFrm(){
        parameters = new HashMap<String, String>();
        fieldNames = new ArrayList<String>();
    }

    public void addParameter(String fieldName, String fieldValue){
        if(fieldNames.contains(fieldName)){
            return;
        }
        fieldNames.add(fieldName);
        parameters.put(fieldName, fieldValue);
    }

    public List<String> getFieldNames(){
        return fieldNames;
    }

    public String getParameter(String fieldName){
        return StrUtil.nullToString(parameters.get(fieldName));
    }

    public String getReportId(){
        return getParameter("reportId");
    }

    public String getReportCode(){
        return getParameter("reportCode");
    }

    public String getLookUpId(){
        return getParameter("lookUpId");
    }

    public String getLookUpCode(){
        return getParameter("lookUpCode");
    }

    public String getPreview(){
        return getParameter("preview");
    }

    public String getClientWidth(){
        return getParameter("clientWidth");
    }
}