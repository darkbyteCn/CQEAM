package com.sino.ams.match.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.match.model.ChangedAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-23
 * Time: 10:50:50
 * To change this template use File | Settings | File Templates.
 */
public class ChangedAssetsRightServlet extends BaseServlet {
	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
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

			ChangedAssetsModel changedAssetsModel = new ChangedAssetsModel(user,dtoParameter);
			SimpleQuery simpleQuery = null;
			SQLModel sqlModel = new SQLModel();
			RowSet rowSet = new RowSet();


			if (action.equals("")) {
				forwardURL = URLDefineList.CHANGED_MATCHR;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
//                BaseSQLProducer sqlProducer = new changedAssetsModel(user, dtoParameter);
//                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
//                pageDAO.produceWebData();


 /*               sqlModel = changedAssetsModel.getRightPageQueryModel();
				simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				if (simpleQuery.hasResult()) {
					rowSet = simpleQuery.getSearchResult();
			}
				 req.setAttribute(WebAttrConstant.CHANGED_ASSETS_RIGHT, rowSet);
				forwardURL = URLDefineList.CHANGED_MATCHR;*/



//                ChangedAssetsModel changedAssetsModel = new ChangedAssetsModel(user, dtoParameter);
//                SQLModel sqlModel = new SQLModel();
				sqlModel = changedAssetsModel.getRightPageQueryModel();
				WebPageView wpv = new WebPageView(req, conn);
//                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
				wpv.produceWebData(sqlModel);
				forwardURL = URLDefineList.CHANGED_MATCHR;


			}
		} catch (QueryException e) {
			e.printStackTrace();
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		} catch (SQLModelException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}


