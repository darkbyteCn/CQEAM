package com.sino.ams.sampling.constant;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface SamplingURLs {

	String TASK_LIST_PAGE = "/sampling/taskList.jsp";
	String TASK_DETAIL_PAGE = "/sampling/taskData.jsp";
	String TASK_SEARCH_PAGE = "/sampling/taskSearch.jsp";



	String BATCH_LIST_PAGE = "/sampling/batchList.jsp";
	String BATCH_DETAIL_PAGE = "/sampling/batchData.jsp";
	String ORDER_LIST_PAGE = "/sampling/orderList.jsp";
	String ORDER_SEACRH_PAGE = "/sampling/orderSearch.jsp";

	String ORDER_FRM_PAGE = "/sampling/orderFrm.jsp";//共单框架页面
	String ORDER_FRM_TOP = "/sampling/orderCreateTop.jsp";//工单框架上边页面
	String ORDER_FRM_LEFT = "/sampling/orderTaskTree.jsp";//工单框架左边页面
	String ORDER_FRM_RIGHT = "/sampling/batchOrder.jsp";//工单框架右边页面


	String ORDER_CREATE_PAGE = "/sampling/orderCreate.jsp";
	String ORDER_DETAIL_PAGE = "/sampling/orderData.jsp";

	String TASK_SERVLET = "/servlet/com.sino.ams.sampling.servlet.AmsAssetsSamplingTaskServlet";
	String BATCH_SERVLET = "/servlet/com.sino.ams.sampling.servlet.AmsAssetsSamplingBatchServlet";
	String ORDER_SERVLET = "/servlet/com.sino.ams.sampling.servlet.AmsAssetsSamplingHeaderServlet";
	String TASK_SEARCH_SERVLET = "/servlet/com.sino.ams.sampling.servlet.TaskSearchServlet";
	String ORDER_FRM_SERVLET = "/servlet/com.sino.ams.sampling.servlet.OrderFrmServlet";
	String TASK_BATCH_TREE = "/servlet/com.sino.ams.sampling.servlet.TaskBatchTreeServlet";
	String BATCH_ORDER_SERVLET = "/servlet/com.sino.ams.sampling.servlet.BatchOrderServlet";
	String ORDER_SEARCH_SERVLET = "/servlet/com.sino.ams.sampling.servlet.OrderSearchServlet";
}
