package com.sino.soa.common;

/**
 * User: zhoujs
 * Date: 2009-4-27 11:33:58
 * Function:
 */
public interface SrvType {
    public final String SRV_OU = "SRV_OU";
    public final String SRV_BOOK = "SRV_FA_BOOK";
    public final String SRV_FA_CATEGORY = "SRV_FA_CATEGORY";
    public final String SRV_VENDOR = "SRV_VENDOR";
    public final String SRV_FA_PERIOD = "SRV_FA_PERIOD";
    public final String SRV_PA_ASSETDETAIL = "SRV_PA_ASSETDETAIL";
    public final String SRV_FA_HEADERINFO = "SRV_FA_HEADERINFO";
    public final String SRV_FA_DISTRIBUTION = "SRV_FA_DISTRIBUTION";
    public final String SRV_FA_RETIRE = "SRV_FA_RETIRE";
    public final String SRV_PA = "SRV_PA";
    public final String SRV_PA_SERVICE_TYPE = "SRV_PA_SERVICE_TYPE";
    public final String SRV_EMPLOYEE = "SRV_EMPLOYEE";
    public final String SRV_VALUE_SET = "SRV_VALUE_SET";
    public final String SRV_ACCOUNT_BALANCE = "SRV_ACCOUNT_BALANCE";
    public final String SRV_FA_LOCATION = "SRV_FA_LOCATION";
    public final String SRV_FA_DEPRECATION = "SRV_FA_DEPRECATION";
    public final String SRV_PA_PROJECT = "SRV_PA_PROJECT";
    public final String SRV_PA_TASK = "SRV_PA_TASK";
    public final String SRV_ASSET_TRANS = "SRV_FA_TRANS";
    public final String SRV_ASSET_UPDATE = "SRV_FA_UPDATE";
    public final String SRV_SET_VALUESET = "SRV_SET_VALUESET";
    public final String SRV_IMP_RETIRE = "SRV_IMP_RETIRE";
    public final String SRV_ASSET_CUST = "SRV_ASSET_CUST";
    
    public final String SRV_TD_OU = "SRV_TD_OU";
    public final String SRV_TD_BOOK = "SRV_FA_TD_BOOK";
    public final String SRV_TD_FA_CATEGORY = "SRV_TD_FA_CATEGORY";
    public final String SRV_TD_VENDOR = "SRV_TD_VENDOR";
    public final String SRV_TD_FA_PERIOD = "SRV_TD_FA_PERIOD";
    public final String SRV_TD_PA_ASSETDETAIL = "SRV_TD_PA_ASSETDETAIL";
    public final String SRV_TD_FA_HEADERINFO = "SRV_TD_FA_HEADERINFO";
    public final String SRV_TD_FA_DISTRIBUTION = "SRV_TD_FA_DISTRIBUTION";
    public final String SRV_TD_FA_RETIRE = "SRV_TD_FA_RETIRE";
    public final String SRV_TD_PA = "SRVTD__PA";
    public final String SRV_TD_PA_SERVICE_TYPE = "SRV_TD_PA_SERVICE_TYPE";
    public final String SRV_TD_EMPLOYEE = "SRV_TD_EMPLOYEE";
    public final String SRV_TD_VALUE_SET = "SRV_TD_VALUE_SET";
    public final String SRV_TD_ACCOUNT_BALANCE = "SRV_TD_ACCOUNT_BALANCE";
    public final String SRV_TD_FA_LOCATION = "SRV_TD_FA_LOCATION";
    public final String SRV_TD_FA_DEPRECATION = "SRV_TD_FA_DEPRECATION";
    public final String SRV_TD_PA_PROJECT = "SRV_TD_PA_PROJECT";
    public final String SRV_TD_PA_TASK = "SRV_TD_PA_TASK";
    public final String SRV_TD_ASSET_TRANS = "SRV_TD_FA_TRANS";
    public final String SRV_TD_ASSET_UPDATE = "SRV_TD_FA_UPDATE";
    public final String SRV_TD_SET_VALUESET = "SRV_TD_SET_VALUESET";
    public final String SRV_TD_IMP_RETIRE = "SRV_TD_IMP_RETIRE";
    public final String SRV_TD_ASSET_CUST = "SRV_TD_ASSET_CUST";

    public final String SRV_FA_TRANS_ASSET ="SRV_FA_TRANS_ASSET";          //查询资产财务信息服务(ODI)
    public final String SRV_TD_FA_TRANS_ASSET ="SRV_TD_FA_TRANS_ASSET";    //查询资产财务信息服务(ODI)_TD

    public final String SRV_FA_PAGE_ASSET_DEPERACTION = "SRV_FA_PAGE_ASSET_DEPERACTION";//读取固定资产折旧信息（分页）
    public final String SRV_TD_FA_PAGE_ASSET_DEPERACTION = "SRV_TD_FA_PAGE_ASSET_DEPERACTION";//读取固定资产折旧信息（分页）
    
    public final String SRV_FA_PAGE_ACCOUNT_BALANCE = "SRV_FA_PAGE_ACCOUNT_BALANCE";//读取科目余额（分页）
    public final String SRV_TD_FA_PAGE_ACCOUNT_BALANCE = "SRV_TD_FA_PAGE_ACCOUNT_BALANCE";//读取科目余额（分页）

    public final String SRV_FA_PAGE_CUST_DETAIL = "SRV_FA_PAGE_ACCOUNT_BALANCE";//读取转资清单（分页）
    public final String SRV_TD_FA_PAGE_CUST_DETAIL = "SRV_TD_FA_PAGE_ACCOUNT_BALANCE";//读取转资清单（分页）

    public final String SRV_ORG_STRUCTURE = "SRV_ORG_STRUCTURE";//组织结构
    public final String SRV_TD_ORG_STRUCTURE = "SRV_TD_ORG_STRUCTURE";//组织结构
    
    public final String SRV_FA_HEADER_ASSET ="SRV_FA_HEADER_ASSET";      //查询资产头基本信息（分页）
    public final String SRV_TD_FA_HEADER_ASSET ="SRV_FA_HEADER_ASSET";   //查询资产头基本信息（分页）_TD
    
    
}
