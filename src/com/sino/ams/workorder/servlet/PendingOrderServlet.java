package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.model.PendingOrderModel;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * User: zhoujs
 * Date: 2007-10-30
 * Time: 10:08:51
 * Function:工单归档
 * 更改记录：
 * 2008-07-10：取消查询时查工单是否有差异
 */
public class PendingOrderServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;

		String act = StrUtil.nullToString(req.getParameter("act"));

		try {
			conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

			String workorderNo = StrUtil.nullToString(req.getParameter("orderNo"));
			String locName = StrUtil.nullToString(req.getParameter("locName"));
			String workorderType = StrUtil.nullToString(req.getParameter("workorderType"));
            EtsWorkorderDTO workorderDTO = new EtsWorkorderDTO();
            workorderDTO.setWorkorderNo(workorderNo);
            workorderDTO.setWorkorderObjectLocation(locName);
            workorderDTO.setWorkorderType(workorderType);
            BaseSQLProducer sqlProducer = new PendingOrderModel(userAccount, workorderDTO);
            PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
            pageDAO.produceWebData();
            req.setAttribute("workorderType", workorderType);
			forwardURL = "/workorder/order/pendingOrderList.jsp";

		} catch (PoolException e) {
			e.printLog();
		} catch (QueryException e) {
			e.printLog();
		} catch (ContainerException e) {
			e.printLog();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}


}
