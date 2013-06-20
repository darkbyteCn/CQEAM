package com.sino.td.commom;

/**
 * <p>Title: TdURLDefineList</p>
 * <p>Description:程序自动生成服务程序“TdURLDefineList”</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author
 * @version 1.0
 */

public interface TdURLDefineList {

    public final String TD_NEW_LOCATIONS = "/td/tdSynchronizePage/TdNewLocationsSyn.jsp";  //Td地点变更同步 
    public final String TD_ASSETS_DISPOSE_SYN_PAGE = "/td/tdSynchronizePage/transStatusQuery.jsp";   //Td事务处理状态查询
    public final String TD_ASSETS_TRANSFER_SYN_PAGE = "/td/tdSynchronizePage/assetsTrans.jsp";   //TD资产调拨结果同步
    public final String TD_ASSETS_DISCARDED_SYN_PAGE = "/td/tdSynchronizePage/assetsDiscardeds.jsp";   //TD资产报废同步

    public final String TD_LOCATION_CHANGE_SYN_PAGE = "/td/tdSynchronizePage/synTdLocation.jsp";
    public final String IMPORT_TD_LOCATION_PAGE = "/srv/tdSyn/importTdLocation.jsp";//SOA地点导入
    public final String UPDATE_TD_ASSETS_PAGE = "/srv/tdSyn/updateAssetInfo.jsp";
    public final String TD_ASSETS_CHANGE_SYN_PAGE = "/td/tdSynchronizePage/assetsChangeSyn.jsp";
    public final String TD_ASSETS_RETIRMENT_SYN_PAGE = "/td/tdSynchronizePage/assetsRetirment.jsp";
    public final String TD_ASSETS_TRANSINCOMPANY_SYN_PAGE = "/td/tdSynchronizePage/assetsInCompany.jsp";

    public final String ASSETS_QUERY_PAGE_TD = "/td/newasset/assetsTransTd.jsp";
    public final String TRANS_EDIT_PAGE_TD = "/td/newasset/assetsTransEditTd.jsp";
    public final String TRANS_DETAIL_PAGE_TD = "/td/newasset/assetsTransDetailTd.jsp"; //资产调拨单详细信息
    public final String APPROVE_EDIT_PAGE_TD = "/td/newasset/assetsTransApproveTd.jsp"; //资产调拨单审批页面
    public final String APPROVE_DETL_PAGE_TD = "/td/newasset/transApproveDetailTd.jsp"; //资产调拨单审批后信息
    public final String ASSETS_RCV_QRY_TD = "/td/newasset/assetsReceiveQueryTd.jsp";
    public final String RCV_DETAIL_PAGE_TD = "/td/newasset/assetsRcvDetailTd.jsp"; //资产调拨单接收详细信息
    public final String ASSETS_ASSIGN_QRY_TD = "/td/newasset/assetsAssignQueryTd.jsp";
    public final String ORDER_QUERY_PAGE_TD = "/td/newasset/orderQueryTd.jsp";
    public final String ORDER_PRINT_QUERY_TD = "/td/newasset/printQueryTd.jsp";
    public final String ADMIN_CONFIRM_PAGE_TD = "/td/newasset/adminConfirmQueryTd.jsp";
    public final String BARCODE_HISTORY_PAGE_TD = "/td/newasset/barcodeHistoryTd.jsp";
    public final String ASSETS_DTL_PAGE_TD = "/td/newasset/assetsDetailTd.jsp"; //资产详细信息
    public final String PRINT_DETAIL_PAGE_TD = "/td/newasset/orderPrintDetailTd.jsp"; //资产调拨单详细信息

    public final String ASSETS_TRANS_SERVLET_TD = "/servlet/com.sino.td.newasset.servlet.TdAssetsTransHeaderServlet";
    public final String ORDER_APPROVE_SERVLET_TD = "/servlet/com.sino.td.newasset.servlet.TdOrderApproveServlet";
    public final String RCV_HEADER_SERVLET_TD = "/servlet/com.sino.td.newasset.servlet.TdAssetsRcvHeaderServlet";
    public final String ASSETS_RCV_SERVLRT_TD = "/servlet/com.sino.td.newasset.servlet.TdReceiveServlet";
    public final String ORDER_QUERY_SERVLET_TD = "/servlet/com.sino.td.newasset.servlet.TdOrderQueryServlet";
    public final String ORDER_PRINT_SERVLET_TD = "/servlet/com.sino.td.newasset.servlet.TdOrderPrintServlet";
    public final String ADMIN_CONFIRM_SERVLET_TD = "/servlet/com.sino.td.newasset.servlet.TdAdminConfirmServlet";

    //String TRANS_EDIT_PAGE = "/newasset/assetsTransEdit.jsp";

    public final String TD_ASSETS_SEARCH_PAGE = "/td/tdSearchPage/tdAssetsQuery.jsp";
    public final String TD_ASSETS_PRC_SERVLET = "/servlet/com.sino.td.assetsSearch.servlet.TdAssetsQueryServlet";
}