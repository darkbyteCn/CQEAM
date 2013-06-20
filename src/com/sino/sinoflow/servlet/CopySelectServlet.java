package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfApiServlet</p>
 * <p>Description:程序自动生成服务程序“SfApiServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 */


public class CopySelectServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
			conn = getDBConnection(req);

            String deptName = "";
            SQLModel sqlModel = new SQLModel();
            String sqlStr = "SELECT SMD.DEPT_NAME FROM SF_USER SU, SINO_MIS_EMPLOYEE SME,"
                    + " SINO_MIS_DEPT SMD"
                    + " WHERE SU.USER_ID = " + user.getUserId() + " AND SU.EMPLOYEE_ID = SME.EMPLOYEE_ID"
                    + " AND SME.DEPT_ID = SMD.DEPT_ID";
            sqlModel.setSqlStr(sqlStr);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()) {
                deptName = simpleQuery.getFirstRow().getStrValue("DEPT_NAME");
            }

            OptionProducer op = new OptionProducer(user, conn);
            String copyOptionStr = op.getAllDeptOption(user.getOrganizationId(), deptName);
            req.setAttribute("copyDeptOption", copyOptionStr);
		    forwardURL = "/flow/copyDlg.jsp";
		} catch (Exception ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
