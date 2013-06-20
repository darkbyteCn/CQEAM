package com.sino.ams.dzyh.constant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface LvecURLs {
	String TASK_LIST_PAGE = "/dzyh/taskList.jsp";
	String TASK_DATA_PAGE = "/dzyh/taskData.jsp";
	String TASK_SEARCH_PAGE = "/dzyh/taskSearch.jsp";

	String BATCH_LIST_PAGE = "/dzyh/batchList.jsp";
	String BATCH_DATA_PAGE = "/dzyh/batchData.jsp";
	String BATCH_SEARCH_PAGE = "/dzyh/batchSearch.jsp";

	String ORDER_LIST_PAGE = "/dzyh/orderList.jsp";
	String ORDER_DATA_PAGE = "/dzyh/orderData.jsp";

	String DEPT_SELECT_PAGE = "/dzyh/deptSelect.jsp";

	String TASK_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamCheckTaskServlet";
	String BATCH_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhCheckBatchServlet";
	String ORDER_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhCheckHeaderServlet";
	String DEPT_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.DeptSelectServlet";
	String LOCATION_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.LocationSelectServlet";

	String LOG_LIST_PAGE = "/dzyh/logList.jsp";
	String LOG_DATA_PAGE = "/dzyh/logData.jsp";
	String LOG_HIS_PAGE = "/dzyh/logHistory.jsp";
	String LOG_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhChgLogServlet";

	String INS_CONFIRM_PAGE = "/dzyh/instrConfirm.jsp";
	String INS_CONFIRM_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.InstruConfirmServlet";

	String BORROW_LIST_PAGE = "/dzyh/borrowList.jsp";
	String BORROW_DATA_PAGE = "/dzyh/borrowData.jsp";
	String BORROW_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.BorrowApplyServlet";

	String WAPPROVE_LIST_PAGE = "/dzyh/wapproveList.jsp";
	String BORROW_APPROVE_PAGE = "/dzyh/borrowApprove.jsp";
	String APPROVE_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.BorrowApproveServlet";
}
