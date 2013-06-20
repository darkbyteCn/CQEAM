/**
 *
 */
package com.sino.soa.common;

/**
 * @author dell
 */
public interface SrvURLDefineList {
    public final String SRV_PROJECT_PAGE = "/srv/project/SynProjectInfo.jsp";
    public final String MESSAGE_PRINT_PUB = "/srv/messagePrint.jsp";
    public final String SRV_VENDOR_INFO_SERVLET = "/servlet/srv.ams.vendor.servlet.SrvVendorInfoSrvServlet";
    public final String SRV_VENDOR_INFO_PAGE = "/srv/VendorInfo/VendorInfoEdit.jsp";
    public final String SRV_LOC_INFO_PAGE = "/srv/loc/synLoc.jsp";
    public final String SRV_ASSET_PERIOD_PAGE = "/srv/AssetPeriod/AssetPeriodEdit.jsp";
    public final String SRV_ASSET_PERIOD_SERVLET = "/servlet/srv.ams.assetperiod.servlet.SrvAssetPeriodServlet";
    public final String SRV_ASSET_CUST_DETAIL_SERVLET = "/servlet/srv.ams.assetcustdetail.servlet.SrvAssetCustDetailServlet";
    public final String TRANS_ASSET_CUST_DETAIL_PAGE = "/srv/assetcustdetail/TransAssetCustDetail.jsp";
    public final String SRV_Retired_Asset_Detail_SERVLET = "/servlet/srv.ams.retiredassetdetail.servlet.SrvRetiredAssetDetailServlet";
    public final String SRV_Retired_Asset_Detail_PAGE = "/srv/retiredassetdetail/retiredAssetDetailEdit.jsp";
    public final String SRV_TRANS_RETIRED_PAGE = "/srv/retiredassetdetail/retiredAssetDetail.jsp";      //报废资产信息读取（ODI）
    public final String SRV_SERVICETYPE_PAGE = "/srv/servicetype/SynServiceTypeInfo.jsp";
    public final String SRV_PROJECTTASK_PAGE = "/srv/project/SynProjectTaskInfo.jsp";
    public final String SRV_ACCOUNTBALANCE_PAGE = "/srv/accountbalance/SynBalanceInfo.jsp";
    public final String SRV_ASSETS_UPDATE = "/srv/assetcustdetail/assetsUpdate.jsp";
    public final String SRV_ASSET_DEPRECATION_PAGE = "/srv/assetdeprecation/SynAssetDeprecation.jsp";
    public final String SRV_ASSET_LOCATION_PAGE="/srv/inquiryassetlocation/assetLocationEdit.jsp";
	public final String SRV_ASSET_LOCATION_SERVLET="/servlet/srv.ams.inquiryassetlocation.servlet.SrvAssetLocationServlet";
	
	public final String SRV_TRANSASSETHEADERINFO ="/srv/assetcustdetail/TransAssetHeaderInfo.jsp";       //查询资产头基本信息ODI
	public final String TRANS_ASSET_DISTRIBUTION_PAGE = "/srv/transassetdistribution/transassetdistribution.jsp";
	public final String TRANS_ASSET_DISTRIBUTION_SERVLET = "/servlet/srv.ams.transassetdistribution.servlet.TransAssetDistributionSevlet";
	
	public final String MIS_PROJECTTASK_PAGE ="/srv/project/misProjectTaskQuery.jsp";
	public final String MIS_EMPLOYEE_PAGE ="/srv/employee/misEmployeeQuery.jsp";
	public final String MIS_ACCOUTBANALCE_PAGE ="/srv/accountbalance/misAccountBanalceQuery.jsp";
	
	public final String SEARCH_BOOKTYPE_CODE_PAGE="/srv/assetbook/searchBookTypeCode.jsp";
	public final String SEARCH_BOOKTYPE_CODE_SERVLET="/servlet/srv.ams.searchbooktypecode.servlet.SearchBookTypeCodeServlet";
	public final String SEARCH_lOCATION_PAGE="/srv/inquiryassetlocation/SearchLocation.jsp";
	public final String SEARCH_lOCATION__SERVLET="/servlet/srv.ams.inquiryassetlocation.servlet.SearchLocationServlet";

    //资产账簿
    public final String SRV_ESSET_BOOK_PAGE = "/srv/assetbook/assetBookEdit.jsp";
    public final String TD_SRV_ESSET_BOOK_PAGE = "/srv/assetbook/assetBookEditTd.jsp";
    public final String SRV_ESSET_BOOK_SERVLET = "/servlet/com.sino.soa.mis.srv.assetbook.servlet.SBFIFAAssetBookServlet";
    public final String TD_SRV_ESSET_BOOK_SERVLET = "/servlet/com.sino.soa.td.srv.assetbook.servlet.SBFIFATdAssetBookServlet";
    //资产类别
    public final String SRV_ESSET_CATEGORY_PAGE = "/srv/AssetCategory/AssetCategoryEdit.jsp";
    public final String TD_SRV_ESSET_CATEGORY_PAGE = "/srv/AssetCategory/AssetCategoryEditTd.jsp";
    public final String SRV_ESSET_CATEGORY_SERVLET = "/servlet/com.sino.soa.mis.srv.assetcategory.servlet.SBFIFASrvAssetCategoryServlet";
    public final String TD_SRV_ESSET_CATEGORY_SERVLET = "/servlet/com.sino.soa.td.srv.assetcategory.servlet.SBFIFATdSrvAssetCategoryServlet";
    //MIS员工信息
    public final String SRV_EMPLOYEE_PAGE = "/srv/employee/SynEmployeeInfo.jsp";
    public final String TD_SRV_EMPLOYEE_PAGE = "/srv/employee/SynEmployeeInfoTd.jsp";
    //值集信息
    public final String SRV_SETVALUE_PAGE = "/srv/setvalue/SynSetValueInfo.jsp";
    public final String TD_SRV_SETVALUE_PAGE = "/srv/setvalue/SynSetValueInfoTd.jsp";
    //公司内资产调拨
    public final String ASSETS_COMMITS = "/srv/assettransincompany/assettransincompany.jsp";
    public final String TD_ASSETS_COMMITS = "/srv/assettransincompany/tdAssetsInCompany.jsp";
    //公司间资产调拨
    public final String ASSETS_BTW_COMMITS = "/srv/assettransbtwcompany/assettransBtwcompany.jsp";
    public final String TD_ASSETS_BTW_COMMITS = "/srv/assettransbtwcompany/assettransBtwcompanyTd.jsp";
    
    //资产地点组合批量导入
    public final String SRV_ASSET_LOC_COMB_PAGE = "/srv/loccomb/synLocComb.jsp";
    public final String TD_SRV_ASSET_LOC_COMB_PAGE = "/srv/loccomb/synLocCombTd.jsp";       //TD
    public final String SRV_ASSET_LOC_COMB_SERVLET = "/servlet/com.sino.soa.mis.srv.assetLocComb.servlet.SrvAssetLocCombServlet";
    public final String TD_SRV_ASSET_LOC_COMB_SERVLET = "/servlet/com.sino.soa.td.srv.assetLocComb.servlet.TDSrvAssetLocCombServlet";  //TD
    //资产会计状态
    public final String TD_SRV_ASSET_PERIOD_PAGE = "/srv/AssetPeriod/AssetPeriodTdEdit.jsp";  //TD  
    public final String SRV_ASSET_PERIOD_SERVLET1 = "/servlet/com.sino.soa.mis.srv.AssetPeriodStatus.servlet.SrvAssetPeriodServlet";  //新
    public final String TD_SRV_ASSET_PERIOD_SERVLET1 = "/servlet/com.sino.soa.td.srv.AssetPeriodStatus.servlet.TDSrvAssetPeriodServlet";  //TD

    //资产地点变更
    public final String TD_SRV_ASSET_LOCATION_PAGE="/srv/inquiryassetlocation/assetLocationTdEdit.jsp";
	public final String SRV_ASSET_LOCATION_SERVLET1="/servlet/com.sino.soa.mis.srv.inquiryassetlocation.servlet.SrvAssetLocationServlet";   //新
	public final String TD_SRV_ASSET_LOCATION_SERVLET1="/servlet/com.sino.soa.td.srv.inquiryassetlocation.servlet.TDSrvAssetLocationServlet";   //TD
	 
	//供应商信息同步
    public final String SRV_VENDOR_INFO_SERVLET1 = "/servlet/com.sino.soa.mis.srv.vendor.servlet.SrvVendorInfoSrvServlet";   //新
    public final String TD_SRV_VENDOR_INFO_PAGE = "/srv/VendorInfo/VendorInfoTdEdit.jsp";
    public final String TD_SRV_VENDOR_INFO_SERVLET1= "/servlet/com.sino.soa.td.srv.vendor.servlet.TDSrvVendorInfoSrvServlet"; //TD
    
    //资产基本信息修改
    public final String TD_SRV_ASSETS_UPDATE = "/srv/assetcustdetail/assetsTdUpdate.jsp";
    
    //报废资产信息读取_TD（ODI）
    public final String TD_SRV_TRANS_RETIRED_PAGE = "/srv/retiredassetdetail/retiredAssetDetailTd.jsp";      
    //查询资产头基本信息_TD（ODI）
	public final String TD_SRV_TRANSASSETHEADERINFO ="/srv/assetcustdetail/TransAssetHeaderInfoTd.jsp";
    
    //固定资产折旧信息（ODI）
    public final String TRANS_ASSET_DEPRECATION_PAGE = "/srv/assetdeprecation/TransAssetDeprecation.jsp";
    public final String TD_TRANS_ASSET_DEPRECATION_PAGE = "/srv/assetdeprecation/TransAssetDeprecationTd.jsp";

    //查询固定资产折旧信息（分页）
    public final String PAGE_TRANS_ASSET_DEPRECATION_PAGE = "/srv/pageassetdeprecation/pageTransAssetDeprecation.jsp";
    public final String PAGE_TD_TRANS_ASSET_DEPRECATION_PAGE = "/srv/pageassetdeprecation/pageTransAssetDeprecationTd.jsp";

    //查询科目余额(ODI)
    public final String SRV_ACCOUNT_BALANCE_PAGE = "/srv/accountbalance/accountBalance.jsp";
    public final String TD_SRV_ACCOUNT_BALANCE_PAGE = "/srv/accountbalance/accountBalanceTd.jsp";
    
    //查询科目余额（分页）
    public final String PAGE_ACCOUNT_BALANCE_PAGE = "/srv/pagequiryaccountbalance/pageAccountBalance.jsp";
    public final String PAGE_TD_ACCOUNT_BALANCE_PAGE = "/srv/pagequiryaccountbalance/pageAccountBalanceTd.jsp";

    //转资资产清单(ODI)
    public final String SRV_ASSET_CUST_DETAIL_PAGE = "/srv/assetcustdetail/assetCustDetailEdit.jsp";
    public final String TD_SRV_ASSET_CUST_DETAIL_PAGE = "/srv/assetcustdetail/assetCustDetailEditTd.jsp";

    //转资资产清单（分页）
    public final String PAGE_CUST_DETAIL_PAGE = "/srv/pagequiryassetcustdetail/pageAssetCustDetail.jsp";
    public final String PAGE_TD_CUST_DETAIL_PAGE = "/srv/pagequiryassetcustdetail/pageAssetCustDetailTd.jsp";
    public final String PAGE_CUST_DETAIL_PAGE_ERROR = "/srv/pagequiryassetcustdetail/pageAssetCustDetailError.jsp";
    public final String PAGE__TD_CUST_DETAIL_PAGE_ERROR = "/srv/pagequiryassetcustdetail/pageAssetCustDetailErrorTd.jsp";
	public final String CUST_DETAIL_SERVLET = "/servlet/com.sino.soa.mis.srv.pagequiryassetcustdetail.servlet.SBFIFAPageinquiryAssetCustDetailServlet";
	public final String TD_CUST_DETAIL_SERVLET = "/servlet/com.sino.soa.td.srv.pagequiryassetcustdetail.servlet.SBFIFATdPageinquiryAssetCustDetailServlet" ;
	
	//查询资产分配行信息(ODI)
	public final String TD_TRANS_ASSET_DISTRIBUTION_PAGE = "/srv/transassetdistribution/transassetdistributionTd.jsp";   
	public final String TRANS_ASSET_DISTRIBUTION_SERVLET1 = "/servlet/com.sino.soa.mis.srv.transassetdistribution.servlet.TransAssetDistributionSevlet";
	public final String TD_TRANS_ASSET_DISTRIBUTION_SERVLET1 ="/servlet/com.sino.soa.td.srv.transassetdistribution.servlet.TDTransAssetDistributionSevlet";
	//查询转资物料清单_TD(ODI)
    public final String TD_TRANS_ASSET_CUST_DETAIL_PAGE = "/srv/assetcustdetail/TransAssetCustDetailTd.jsp";  
    
    //查询项目任务信息(ODI)(TransTaskInfoSrv)
    public final String TRANS_TASKINFO_PAGE = "/srv/transTaskInfo/transTaskInfo.jsp";
    public final String TD_TRANS_TASKINFO_PAGE = "/srv/transTaskInfo/transTaskInfoTd.jsp";
    public final String TRANS_TASKINFO_SERVLET = "/servlet/com.sino.soa.mis.srv.transTaskInfo.servlet.TransTaskInfoSevlet";
    public final String TD_TRANS_TASKINFO_SERVLET = "/servlet/com.sino.soa.td.srv.transTaskInfo.servlet.TDTransTaskInfoSevlet";
     
    //同步TD项目信息
    public final String TD_SRV_PROJECT_PAGE = "/srv/project/SynProjectInfoTd.jsp";
    
    //查询资产财务信息服务(ODI)
    public final String TRANS_FAASSET_PAGE = "/srv/transFaAssetInfo/transFaAssetInfo.jsp";
    public final String TD_TRANS_FAASSET_PAGE = "/srv/transFaAssetInfo/transFaAssetInfoTd.jsp";
    public final String TRANS_FAASSET_SERVLET = "/servlet/com.sino.soa.mis.srv.transfaassetinfo.servlet.TransFaAssetInfoSevlet";
    public final String TD_TRANS_FAASSET_SERVLET = "/servlet/com.sino.soa.td.srv.transfaassetinfo.servlet.TDTransFaAssetInfoSevlet";
    
    
    //查询项目任务信息服务(分页)(PageInquiryTaskInfoSrv) 
    public final String SRV_PROJECTTASK1_PAGE    = "/srv/transTaskInfo/SynProjectTaskInfo.jsp";
    public final String TD_SRV_PROJECTTASK1_PAGE = "/srv/transTaskInfo/SynProjectTaskInfoTd.jsp";
    
    //组织结构
    public final String SRV_ORG_STRUCTURE_PAGE = "/srv/orgstructure/orgstructure.jsp";
    public final String TD_SRV_ORG_STRUCTURE_PAGE = "/srv/orgstructure/orgstructureTd.jsp";
    
    //查询报废资产基本信息（分页）
    public final String SRV_RETIRED_ASSET_DETAIL1_PAGE = "/srv/retiredassetdetail/retiredAssetDetailEdit.jsp";    //查询报废资产基本信息（分页）
    public final String SRV_RETIRED_ASSET_DETAIL1_SERVLET = "/servlet/com.sino.soa.mis.srv.InquiryRetiredAssetDetail.servlet.PageRetiredAssetDetailServlet";
    public final String TD_SRV_RETIRED_ASSET_DETAIL1_PAGE = "/srv/retiredassetdetail/retiredAssetDetailTdEdit.jsp";     //TD
    public final String TD_SRV_RETIRED_ASSET_DETAIL1_SERVLET = "/servlet/com.sino.soa.td.srv.InquiryRetiredAssetDetail.servlet.TDPageRetiredAssetDetailServlet";
    
    //查询资产头基本信息（分页）
	public final String SRV_ASSET_HEADER_PAGE = "/srv/PageInquiryAssetHeader/PageAssetHeaderInfo.jsp";
	public final String TD_SRV_ASSET_HEADER_PAGE = "/srv/PageInquiryAssetHeader/PageAssetHeaderInfoTd.jsp";
	public final String SRV_ASSET_HEADER_SERVLET  = "/servlet/com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.servlet.PageInquiryAssetHeaderInfoServlet";
	public final String TD_SRV_ASSET_HEADER_SERVLET  = "/servlet/com.sino.soa.td.srv.PageInquiryAssetHeaderInfo.servlet.TDPageInquiryAssetHeaderInfoServlet";
	
	//查询资产分配行信息（分页）
	public final String SRV_ASSET_DISTRIBUTION_PAGE    = "/srv/PageAssetDistribution/AssetDistributionInfo.jsp";   
	public final String TD_SRV_ASSET_DISTRIBUTION_PAGE = "/srv/PageAssetDistribution/AssetDistributionInfoTd.jsp"; 
	public final String SRV_ASSET_DISTRIBUTION_SERVLET    = "/servlet/com.sino.soa.mis.srv.PageInquiryAssetDistribution.servlet.PageInquiryAssetDistributionServlet"; 
	public final String TD_SRV_ASSET_DISTRIBUTION_SERVLET = "/servlet/com.sino.soa.td.srv.PageInquiryAssetDistribution.servlet.TDPageInquiryAssetDistributionServlet"; 
    
	//地点第二段同步
	 public final String SRV_ASSET_LOC2_COMB_PAGE = "/srv/loc2comb/synLoc2Comb.jsp";
	 public final String SRV_ASSET_LOC2_COMB_SERVLET = "/servlet/com.sino.soa.mis.srv.assetLoc2Comb.servlet.SrvAssetLoc2CombServlet";
	 
	 
}