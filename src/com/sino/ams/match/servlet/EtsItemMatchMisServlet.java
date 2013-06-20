package com.sino.ams.match.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.match.dao.EtsItemMatchMisDAO;
import com.sino.ams.match.dto.EtsItemMatchMisDTO;
import com.sino.ams.match.model.EtsItemMatchMisModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Suhp
 * Date: 2007-11-23
 * Time: 16:36:07
 * To change this template use File | Settings | File Templates.
 */
public class EtsItemMatchMisServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemMatchMisDTO.class.getName());
			EtsItemMatchMisDTO dto = (EtsItemMatchMisDTO) req2DTO.getDTO(req);
			String act = dto.getAct();
			conn = getDBConnection(req);
			EtsItemMatchMisDAO itemmatchDAO = new EtsItemMatchMisDAO(user, dto, conn);
			String selectName = req.getParameter("selectName");
			selectName = StrUtil.nullToString(selectName);
			req.setAttribute("selectName",selectName);
			if (act.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.ETS_ITEM_MATCHINFO;
			} else if (act.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsItemMatchMisModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.ETS_ITEM_MATCHINFO;
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = null;
				file = itemmatchDAO.exportEtsFile();
				itemmatchDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}
//  else if (act.equals(AMSActionConstant.SCREEN_ASSET_ACTION)) { //³·ÏûÆÁ±Î
//                if (selectName.equals(WebAttrConstant.ETS_EQUIPMENT)) {
//                    itemmatchDAO.repealEtsData(systemids);
//                    message = itemmatchDAO.getMessage();
//                } else {
//                    itemmatchDAO.repealMisData(assetIds);
//                    message = itemmatchDAO.getMessage();
//                }
//                forwardURL = URLDefineList.ETS_ITEM_MATCHINFO;
//            }
		} catch (PoolException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
