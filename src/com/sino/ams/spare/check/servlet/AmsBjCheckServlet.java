package com.sino.ams.spare.check.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.check.dao.AmsBjCheckDAO;
import com.sino.ams.spare.check.dto.AmsItemCheckHeaderDTO;
import com.sino.ams.spare.check.model.AmsBjCheckModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author TOTTI
 *         Date: 2007-11-27
 */
public class AmsBjCheckServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsItemCheckHeaderDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemCheckHeaderDTO.class.getName());
			dtoParameter = (AmsItemCheckHeaderDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsBjCheckDAO dao = new AmsBjCheckDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
			String transSta = dtoParameter.getOrderStatus();
			String transStaSelect = op.getDictOption("WORKORDER_STATUS", transSta);
			req.setAttribute(WebAttrConstant.TRANS_STATUS, transStaSelect);
			AmsItemCheckHeaderDTO dto = (AmsItemCheckHeaderDTO) req.getAttribute("AMSBJSALLOTHDTO");
			if (dto == null) {
				dto = new AmsItemCheckHeaderDTO();
			}
			req.setAttribute("AMSBJSALLOTHDTO", dto);
			if (action.equals("")) {
				forwardURL = "/spare/check/bjCheck.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsBjCheckModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = "/spare/check/bjCheck.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				dto.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
				dto.setCreationDate(CalendarUtil.getCurrDate());
				dto.setCreatedBy(user.getUserId());
				dto.setCreatedUser(user.getUsername());
				dto.setOrderStatusName("未完成");
				req.setAttribute("AMSBJSALLOTHDTO",dto);
				forwardURL = "/spare/check/bjCheckDetail.jsp";
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				String headerId=req.getParameter("headerId");
				String checkLocation=req.getParameter("checkLocation");
				 File file = dao.exportFile(checkLocation,headerId);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}  else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
			  dao.setDTOClassName(AmsItemCheckHeaderDTO.class.getName());
			  AmsItemCheckHeaderDTO dto1=(AmsItemCheckHeaderDTO)dao.getDataByPrimaryKey();
				if(dto1==null){
				 dto1=new AmsItemCheckHeaderDTO();
					 message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AMSBJSALLOTHDTO",dto1);
				RowSet row=dao.getItem(dto1.getCheckLocation(),dto1.getHeaderId());
				req.setAttribute("ITEM",row);
				  forwardURL = "/spare/check/bjCheckDetail.jsp";
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
			   Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemCheckHeaderDTO.class.getName());
				r2.setIgnoreFields(AmsItemCheckHeaderDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				 boolean operateResult =dao.submitData(lineSet) ;
				message = dao.getMessage();
				message.setIsError(!operateResult);
				 if (operateResult) {
					forwardURL = "/servlet/com.sino.ams.spare.check.servlet.AmsBjCheckServlet?act=" + WebActionConstant.QUERY_ACTION;
					showMsg = "备件盘点单" + dtoParameter.getTransNo() + "已生成!";
				} else {
					forwardURL = "/servlet/com.sino.ams.spare.servlet.BjckServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getHeaderId();
				}

			}
		}
		catch (PoolPassivateException ex) {
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
		} catch (CalendarException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}
		finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (showMsg.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, showMsg);
			}
		}
	}
}
