package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.dzyh.OptionProducer.EamProducer;
import com.sino.ams.dzyh.dao.EamDhBillHDAO;
import com.sino.ams.dzyh.dto.EamDhBillHDTO;
import com.sino.ams.dzyh.model.EamDhBillHModel;
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
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EamDhBillHServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhBillHServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhBillHServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(EamDhBillHDTO.class.getName());
			EamDhBillHDTO dtoParameter = (EamDhBillHDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			EamDhBillHDAO eamDhBillHDAO = new EamDhBillHDAO(user, dtoParameter, conn);
			EamProducer eamPro=new EamProducer(user, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);
			String billStatus=optProducer.getDictOption(WebAttrConstant.DZYH_BILL_STATUS, String.valueOf(dtoParameter.getBillStatus()));
			String organization=optProducer.getAllOrganization(dtoParameter.getOrgId());
			
			req.setAttribute(WebAttrConstant.DZYH_BILL_STATUS_OPT, billStatus);
			req.setAttribute(WebAttrConstant.DZYH_BILL_ORG_OPT, organization);
			if (action.equals("")) {
				String org=eamPro.getAllOrganization(dtoParameter.getOrgId(), true);
				req.setAttribute(WebAttrConstant.DZYH_BILL_ORG_OPT, org);
				forwardURL = URLDefineList.DZYH_ENTER_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamDhBillHModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				String org=eamPro.getAllOrganization(dtoParameter.getOrgId(), true);
				req.setAttribute(WebAttrConstant.DZYH_BILL_ORG_OPT, org);
				forwardURL = URLDefineList.DZYH_ENTER_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EamDhBillHDTO eamDhBillH = (EamDhBillHDTO)req.getAttribute(WebAttrConstant.DZYH_DATA);
				if(eamDhBillH == null){
					eamDhBillH= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.dzyh.dto.EamDhBillHDTO的构造函数确定
				}
				req.setAttribute(WebAttrConstant.DZYH_DATA, eamDhBillH);
				forwardURL = URLDefineList.DZYH_ENTER_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				eamDhBillHDAO.setDTOClassName(EamDhBillHDTO.class.getName());
				EamDhBillHDTO eamDhBillH = (EamDhBillHDTO)eamDhBillHDAO.getDataByPrimaryKey();
				if(eamDhBillH == null){
					eamDhBillH = new EamDhBillHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.DZYH_DATA, eamDhBillH);
				forwardURL = URLDefineList.DZYH_ENTER_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				ServletConfigDTO servletConfig=getServletConfig(req);
				eamDhBillHDAO.setServletConfig(servletConfig);
				eamDhBillHDAO.createData();
				forwardURL = URLDefineList.DZYH_ENTER_QRY_SERVLET;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				ServletConfigDTO servletConfig=getServletConfig(req);
				eamDhBillHDAO.setServletConfig(servletConfig);
				eamDhBillHDAO.updateData();
				forwardURL = URLDefineList.DZYH_ENTER_QRY_SERVLET;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				ServletConfigDTO servletConfig=getServletConfig(req);
				eamDhBillHDAO.setServletConfig(servletConfig);
				eamDhBillHDAO.deleteData();
				forwardURL = URLDefineList.DZYH_ENTER_QRY_SERVLET;
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