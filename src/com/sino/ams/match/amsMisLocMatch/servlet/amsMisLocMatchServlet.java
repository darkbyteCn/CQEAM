package com.sino.ams.match.amsMisLocMatch.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.match.amsMisLocMatch.dao.AmsMisLocMatchDAO;
import com.sino.ams.match.amsMisLocMatch.dto.AmsMisLocMatchDTO;
import com.sino.ams.match.amsMisLocMatch.model.AmsMisLocMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-21
 * Time: 19:42:39
 * To change this template use File | Settings | File Templates.
 */
public class amsMisLocMatchServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		String showMsg = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsMisLocMatchDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsMisLocMatchDTO.class.getName());
			dtoParameter = (AmsMisLocMatchDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsMisLocMatchDAO locationDAO = new AmsMisLocMatchDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
//            String amsLoc=prd.getAMSLocation();
//            String misLoc = prd.getMISLocation();
			if (action.equals("")) {
//                req.setAttribute(WebAttrConstant.AMS_LOCATION_OPTION,amsLoc);
//                req.setAttribute(WebAttrConstant.MIS_LOCATION_OPTION,misLoc);
				forwardURL = URLDefineList.AMS_MIS_LOC_MATCH;
				  forwardURL = "/match/misLocMatch.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {   //查询未配备的eam地点信息
				BaseSQLProducer sqlProducer = new AmsMisLocMatchModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);           //
				pageDAO.setPageSize(20);
				pageDAO.setCountPages(false);
				pageDAO.produceWebData();
//                forwardURL = URLDefineList.AMS_MIS_LOC_MATCH;
				forwardURL = "/match/amsLocMatch.jsp";
			} else if (action.equals("MISLOC_QUERY")){   //查询未匹配的mis地点
				AmsMisLocMatchModel amsMisLocMatchModel = new AmsMisLocMatchModel(user, dtoParameter);
				SQLModel sqlModel = amsMisLocMatchModel.getMISLoc();
				WebPageView  webPage = new WebPageView(req, conn);
				webPage.setPageSize(20);
				webPage.produceWebData(sqlModel);
				forwardURL = "/match/misLocMatch.jsp";
			} else if (action.equals(AMSActionConstant.MATCH_ACTION)){   //进行匹配操作
				locationDAO.doMatch();
				showMsg = "匹配成功！";
				forwardURL = "/match/amsMisLocFrame.jsp";
			} else if (action.equals("HAVE_MACHED")){   //查询已匹配信息
				AmsMisLocMatchModel amsMisLocMatchModel = new AmsMisLocMatchModel(user, dtoParameter);
				SQLModel sqlModel = amsMisLocMatchModel.getMatchedModel(user.getOrganizationId());
				WebPageView  webPage = new WebPageView(req, conn);
				webPage.produceWebData(sqlModel);
				forwardURL = "/match/locMatchInfo.jsp";
			} else if (action.equals("UNMACH")){   //进行解除匹配操作
				String[]  subCheck= req.getParameterValues("subCheck");
				locationDAO.unMatch(subCheck);
				 showMsg = "撤销匹配成功！";
				forwardURL = "com.sino.ams.match.amsMisLocMatch.servlet.amsMisLocMatchServlet?act=HAVE_MACHED";
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLModelException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			  if (showMsg.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardView(forwardURL, showMsg);
			}
			//根据实际情况修改页面跳转代码。
		}
	}
}
