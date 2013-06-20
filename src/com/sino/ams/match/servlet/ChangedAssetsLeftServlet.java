package com.sino.ams.match.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.match.dao.EtsItemMatchDAO;
import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.match.model.EtsItemMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
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
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-22
 * Time: 11:29:51
 */
public class ChangedAssetsLeftServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			EtsItemMatchDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemMatchDTO.class.getName());
			dtoParameter = (EtsItemMatchDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();

			EtsItemMatchModel etsItemMatchModel = new EtsItemMatchModel(user, dtoParameter);

			EtsItemMatchDAO dao = new EtsItemMatchDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				forwardURL = URLDefineList.CHANGED_MATCHL;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsItemMatchModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.CHANGED_MATCHL;
			} else if (action.equals(AMSActionConstant.MATCH_ACTION)) {
//                String systemid = req.getParameter("systemid");
//                String assetId = req.getParameter("assetId");
			   String ret= dao.doMatch();
				forwardURL = URLDefineList.CHANGED_ASSETS + "?ret=" +ret;
			}
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}


