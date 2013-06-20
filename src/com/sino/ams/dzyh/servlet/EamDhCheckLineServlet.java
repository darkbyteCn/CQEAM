package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.dao.EamDhCheckLineDAO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.model.EamDhCheckLineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EamDhCheckLineServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhCheckLineServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EamDhCheckLineServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(EamDhCheckLineDTO.class.getName());
			EamDhCheckLineDTO dtoParameter = (EamDhCheckLineDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			EamDhCheckLineDAO eamDhCheckLineDAO = new EamDhCheckLineDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				forwardURL = "com.sino.ams.dzyh.servlet.EamDhCheckLineServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamDhCheckLineModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = "com.sino.ams.dzyh.servlet.EamDhCheckLineServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EamDhCheckLineDTO eamDhCheckLine = (EamDhCheckLineDTO)req.getAttribute("获取因为失败而保持的数据，请根据实际情况修改");
				if(eamDhCheckLine == null){
					eamDhCheckLine= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.lvec.dto.EamDhCheckLineDTO的构造函数确定
				}
				req.setAttribute("详细数据属性，请根据实际情况修改", eamDhCheckLine);
				forwardURL = "com.sino.ams.dzyh.servlet.EamDhCheckLineServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				eamDhCheckLineDAO.setDTOClassName(EamDhCheckLineDTO.class.getName());
				EamDhCheckLineDTO eamDhCheckLine = (EamDhCheckLineDTO)eamDhCheckLineDAO.getDataByPrimaryKey();
				if(eamDhCheckLine == null){
					eamDhCheckLine = new EamDhCheckLineDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("详细数据属性，请根据实际情况修改", eamDhCheckLine);
				forwardURL = "com.sino.ams.dzyh.servlet.EamDhCheckLineServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				eamDhCheckLineDAO.createData();
				forwardURL = "可再次执行com.sino.ams.lvec.servlet.EamDhCheckLineServlet的QUERY_ACTION，请根据实际情况确定";
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				eamDhCheckLineDAO.updateData();
				forwardURL = "可再次执行com.sino.ams.lvec.servlet.EamDhCheckLineServlet的QUERY_ACTION，请根据实际情况确定";
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				eamDhCheckLineDAO.deleteData();
				forwardURL = "可再次执行com.sino.ams.lvec.servlet.EamDhCheckLineServlet的QUERY_ACTION，请根据实际情况确定";
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
			//请根据实际情况处理消息
			forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
