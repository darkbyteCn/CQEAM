package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.newasset.dao.AssetsBeforedDAO;
import com.sino.ams.newasset.model.AssetsBeforedModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
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
 * User: Administrator
 * Date: 2008-4-9
 * Time: 1:57:20
 * To change this template use File | Settings | File Templates.
 */
public class AssetsBeforedServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			AmsInstrumentInfoDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsInstrumentInfoDTO.class.getName());
			conn = getDBConnection(req);
			dtoParameter = (AmsInstrumentInfoDTO) req2DTO.getDTO(req);
			AssetsBeforedDAO dao = new AssetsBeforedDAO(user, dtoParameter,
					conn);

			OptionProducer optProducer = new OptionProducer(user, conn); //获取区县信息
			String cityOption = optProducer.getAllOrganization(dtoParameter.
					getOrganizationId(), true);
			String itemTypeOption = optProducer.getDictOption(DictConstant.
					ITEM_TYPE, dtoParameter.getItemCategory()); //专业
			req.setAttribute(WebAttrConstant.ITEM_TYPE_OPTION, itemTypeOption);
			req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = "/newasset/assetsBeforedQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AssetsBeforedModel(user,
						dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = "/newasset/assetsBeforedQuery.jsp";

			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出
				File file = dao.exportFile();
				dao.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
		}
		/* catch (SQLException e) {
		  Logger.logError(e);
		  message = getMessage(MsgKeyConstant.SQL_ERROR);
		  message.setIsError(true);
		  forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} catch (CalendarException e) {
		  e.printLog();
		  message = getMessage(MsgKeyConstant.COMMON_ERROR);
		  message.setIsError(true);
		  forwardURL = MessageConstant.MSG_PRC_SERVLET;
				}*/ catch (DataTransException ex) {
		   ex.printLog();
		   message = getMessage(MsgKeyConstant.COMMON_ERROR);
		   message.setIsError(true);
		   forwardURL = MessageConstant.MSG_PRC_SERVLET;
	   } catch (WebFileDownException e) {
			e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
