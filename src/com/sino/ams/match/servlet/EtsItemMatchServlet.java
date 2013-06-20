package com.sino.ams.match.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.match.dao.EtsItemMatchDAO;
import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.match.model.EtsItemMatchModel;
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
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsItemMatchServlet</p>
 * <p>Description:程序自动生成服务程序“EtsItemMatchServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author V-jiachuanchuan
 * @version 1.0
 */


public class EtsItemMatchServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			EtsItemMatchDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemMatchDTO.class.getName());
			dtoParameter = (EtsItemMatchDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsItemMatchDAO etsItemMatchDAO = new EtsItemMatchDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				forwardURL = "com.sino.ams.match.servlet.EtsItemMatchServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsItemMatchModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = "com.sino.ams.match.servlet.EtsItemMatchServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsItemMatchDTO etsItemMatch = (EtsItemMatchDTO)req.getAttribute("获取因为失败而保持的数据，请根据实际情况修改");
				if(etsItemMatch == null){
					etsItemMatch= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.match.dto.EtsItemMatchDTO的构造函数确定
				}
				req.setAttribute("详细数据属性，请根据实际情况修改", etsItemMatch);
				forwardURL = "com.sino.ams.match.servlet.EtsItemMatchServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsItemMatchDAO.setDTOClassName(EtsItemMatchDTO.class.getName());
				EtsItemMatchDTO etsItemMatch = (EtsItemMatchDTO)etsItemMatchDAO.getDataByPrimaryKey();
				if(etsItemMatch == null){
					etsItemMatch = new EtsItemMatchDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("详细数据属性，请根据实际情况修改", etsItemMatch);
				forwardURL = "com.sino.ams.match.servlet.EtsItemMatchServlet详细数据JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				etsItemMatchDAO.createData();
				forwardURL = "可再次执行com.sino.ams.match.servlet.EtsItemMatchServlet的QUERY_ACTION，请根据实际情况确定";
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsItemMatchDAO.updateData();
				forwardURL = "可再次执行com.sino.ams.match.servlet.EtsItemMatchServlet的QUERY_ACTION，请根据实际情况确定";
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				etsItemMatchDAO.deleteData();
				forwardURL = "可再次执行com.sino.ams.match.servlet.EtsItemMatchServlet的QUERY_ACTION，请根据实际情况确定";
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