package com.sino.rds.share.form;

import com.sino.base.data.RowSet;
import com.sino.framework.dto.BaseUserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ReportExecuteFrm {
    private ReportDefineFrm definedReport = null;
    private SearchParameterFrm searchFrm = null;
    private List<ReportParameterFrm> definedParameters = null;
    private List<ReportViewFrm> definedViews = null;
    private LookUpDefineFrm lookUpFrm = null;
    private BaseUserDTO userAccount = null;

    private HttpServletRequest request = null;

    private RowSet searchResult = null;

    public ReportDefineFrm getDefinedReport() {
        return definedReport;
    }

    public void setDefinedReport(ReportDefineFrm definedReport) {
        this.definedReport = definedReport;
    }

    public SearchParameterFrm getSearchFrm() {
        return searchFrm;
    }

    public void setSearchFrm(SearchParameterFrm searchFrm) {
        this.searchFrm = searchFrm;
    }

    public List<ReportParameterFrm> getDefinedParameters() {
        return definedParameters;
    }

    public void setDefinedParameters(List<ReportParameterFrm> definedParameters) {
        this.definedParameters = definedParameters;
    }

    public List<ReportViewFrm> getDefinedViews() {
        return definedViews;
    }

    public void setDefinedViews(List<ReportViewFrm> definedViews) {
        this.definedViews = definedViews;
    }

    public LookUpDefineFrm getLookUpFrm() {
        return lookUpFrm;
    }

    public void setLookUpFrm(LookUpDefineFrm lookUpFrm) {
        this.lookUpFrm = lookUpFrm;
    }

    public RowSet getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(RowSet searchResult) {
        this.searchResult = searchResult;
    }

    public BaseUserDTO getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(BaseUserDTO userAccount) {
        this.userAccount = userAccount;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public boolean isFromLook() {
        return (lookUpFrm != null);
    }

    public boolean hasData(){
        return (searchResult != null && !searchResult.isEmpty());
    }

    public void freeResource(){
        if(definedViews != null){
            definedViews.clear();
        }
        if(definedParameters != null){
            definedParameters.clear();
        }
        if(searchResult != null){
            searchResult.clearData();
        }
        definedReport = null;
        searchFrm = null;
        definedParameters = null;
        definedViews = null;
        lookUpFrm = null;
        searchResult = null;
        userAccount = null;
    }
}
