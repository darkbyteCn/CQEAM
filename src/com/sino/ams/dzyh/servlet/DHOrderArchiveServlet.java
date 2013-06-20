package com.sino.ams.dzyh.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.constant.LvecURLs;
import com.sino.ams.dzyh.constant.LvecWebAttributes;
import com.sino.ams.dzyh.dao.DHOrderArchiveDAO;
import com.sino.ams.dzyh.dao.EamDhCheckLineDAO;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.model.DHOrderArchiveModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class DHOrderArchiveServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamDhCheckHeaderDTO.class.getName());
			EamDhCheckHeaderDTO dto = (EamDhCheckHeaderDTO)req2DTO.getDTO(req);
			String orderType = dto.getOrderType();
			if(orderType.equals(LvecDicts.ORD_TYPE1_YQYB)){
				dto.setObjectCategory(LvecDicts.LOCATION_CATEGORY_YQYB);
			} else {
				dto.setObjectCategory(LvecDicts.LOCATION_CATEGORY_DZYH);
			}
			String action = dto.getAct();
			conn = getDBConnection(req);
			DHOrderArchiveDAO headerDAO = new DHOrderArchiveDAO(user, dto, conn);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.ORDER_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new DHOrderArchiveModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(EamDhCheckHeaderDTO.class.getName());
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();

				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.ORDER_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.DETAIL_ACTION)) {
				headerDAO.setDTOClassName(EamDhCheckHeaderDTO.class.getName());
				headerDAO.setCalPattern(LINE_PATTERN);
				EamDhCheckHeaderDTO orderHeader = (EamDhCheckHeaderDTO)headerDAO.getDataByPrimaryKey();
				DTOSet orderLines = null;
				if(orderHeader == null){
					orderHeader= dto;
					orderLines = new DTOSet();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					EamDhCheckLineDTO orderLine = new EamDhCheckLineDTO();
					orderLine.setHeaderId(dto.getHeaderId());
					EamDhCheckLineDAO lineDAO = new EamDhCheckLineDAO(user, orderLine, conn);
					lineDAO.setDTOClassName(EamDhCheckLineDTO.class.getName());
					lineDAO.setCalPattern(LINE_PATTERN);
					orderLines = (DTOSet)lineDAO.getDataByForeignKey("headerId");
				}
				req.setAttribute(LvecWebAttributes.ORDER_DTO, orderHeader);
				req.setAttribute(LvecWebAttributes.ORDER_LINES, orderLines);
				forwardURL = LvecURLs.ORDER_DATA_PAGE;
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
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!forwardURL.equals("")){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
