package com.sino.ams.system.user.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dao.SfGroupDAO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.SfGroupModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SfGroupServlet</p>
 * <p>Description:程序自动生成服务程序“SfGroupServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfGroupServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfGroupDTO.class.getName());
			SfGroupDTO dto = (SfGroupDTO) req2DTO.getDTO(req);
			String action = dto.getAct();

			conn = DBManager.getDBConnection();
			OptionProducer op = new OptionProducer(user, conn);
			SfGroupDAO sfGroupDAO = new SfGroupDAO(user, dto, conn);
			String opt = "";
			if (action.equals("")) {
				opt = op.getGroupFlowOption(dto.getIsDesigner());
				dto.setIsDesignerOption(opt);
				opt = op.getFlowGroupOption(dto.getpFlowId());
				dto.setFlowGroupOption(opt);
				opt = op.getEnableOption(dto.getEnabled());
				dto.setEnableOption(opt);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);

				forwardURL = URLDefineList.GROUP_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SfGroupModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				opt = op.getGroupFlowOption(dto.getIsDesigner());
				dto.setIsDesignerOption(opt);
				opt = op.getFlowGroupOption(dto.getpFlowId());
				dto.setFlowGroupOption(opt);
				opt = op.getEnableOption(dto.getEnabled());
				dto.setEnableOption(opt);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);

				forwardURL = URLDefineList.GROUP_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				SfGroupDTO sfGroup = null;
				if (sfGroup == null) {
					sfGroup = new SfGroupDTO();
					sfGroup.setOrganizationId(user.getOrganizationId());
				}
				req.setAttribute(WebAttrConstant.GROUP_ATTR, sfGroup);
				req.setAttribute(WebAttrConstant.GROUP_OPTION, op.getAllGroup(sfGroup.getGroupPid()));
				req.setAttribute(WebAttrConstant.FLOW_GROUP_OPTION, op.getFlowGroupOption(sfGroup.getpFlowId()));
				req.setAttribute(WebAttrConstant.GROUP_CATEGORY, op.getGroupCategory(sfGroup.getCategory()));
                req.setAttribute(WebAttrConstant.ORG_GROUP_ATTR, op.getAllGroup(sfGroup.getGroupThred()));
				forwardURL = URLDefineList.GROUP_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfGroupDAO.setDTOClassName(SfGroupDTO.class.getName());
				SfGroupDTO sfGroup = (SfGroupDTO) sfGroupDAO.getDataByPrimaryKey();
				if (sfGroup == null) {
					sfGroup = new SfGroupDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.GROUP_ATTR, sfGroup);
				req.setAttribute(WebAttrConstant.GROUP_OPTION, op.getAllGroup(sfGroup.getGroupPid()));
				req.setAttribute(WebAttrConstant.FLOW_GROUP_OPTION, op.getFlowGroupOption(sfGroup.getpFlowId()));
				req.setAttribute(WebAttrConstant.GROUP_CATEGORY, op.getGroupCategory(sfGroup.getCategory()));
                req.setAttribute(WebAttrConstant.ORG_GROUP_ATTR, op.getAllGroup(sfGroup.getGroupThred()));
				forwardURL = URLDefineList.GROUP_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				sfGroupDAO.createData();
                if(!dto.getGroupThred().equals("")){
                    sfGroupDAO.updateThirdGroup();
                }
				message = sfGroupDAO.getMessage();
				forwardURL = URLDefineList.GROUP_SERVLET_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfGroupDAO.updateData();
                if(!dto.getGroupThred().equals("")){
                    sfGroupDAO.updateThirdGroup();
                }
				message = sfGroupDAO.getMessage();
				forwardURL = URLDefineList.GROUP_SERVLET_PAGE;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.CONN_ERROR);
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
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
