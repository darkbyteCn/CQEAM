package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.dzyh.OptionProducer.EamProducer;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.dao.EamDhPriviDAO;
import com.sino.ams.dzyh.dto.EamDhPriviDTO;
import com.sino.ams.dzyh.model.EamDhPriviModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
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
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EamDhPriviServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhPriviServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhPriviServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamDhPriviDTO.class.getName());
			EamDhPriviDTO dtoParameter = (EamDhPriviDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
			EamDhPriviDAO eamDhPriviDAO = new EamDhPriviDAO(user, dtoParameter, conn);
			eamDhPriviDAO.setServletConfig(servletConfig);
			EamProducer optProducer = new EamProducer(user, conn);
			OptionProducer optP = new OptionProducer(user, conn);
			if (action.equals("")) {
				if (!user.isProvinceUser()) {
					dtoParameter.setOrgId(user.getOrganizationId());
                }
				String org = optProducer.getAllOrganization(dtoParameter.getOrgId(), true);
				req.setAttribute(WebAttrConstant.DZYH_BILL_ORG_OPT, org);
				
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = URLDefineList.DZYH_PRIVI_QRY;
			} else if (action.equals(DzyhActionConstant.QUERY_ACTION)) {
				if (!user.isProvinceUser()) {
					dtoParameter.setOrgId(user.getOrganizationId());
                }
				String org = optProducer.getAllOrganization(dtoParameter.getOrgId(), true);
				req.setAttribute(WebAttrConstant.DZYH_BILL_ORG_OPT, org);
                BaseSQLProducer sqlProducer = new EamDhPriviModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setServletConfig(servletConfig);
//                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
//                checkProp.addDbField("PRIVI_ID");
//                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = URLDefineList.DZYH_PRIVI_QRY;
			} else if (action.equals(DzyhActionConstant.NEW_ACTION)) {
				EamDhPriviDTO eamDhPrivi = (EamDhPriviDTO)req.getAttribute(WebAttrConstant.DZYH_PRIVI_DATA);
				if(eamDhPrivi == null){
					eamDhPrivi= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.dzyh.dto.EamDhPriviDTO的构造函数确定
				}
				String defaultFlagOption = optP.getBooleanOption(eamDhPrivi.getDefaultflag());
				String enabledOption=optProducer.getBooleanOption(StrUtil.nullToString(eamDhPrivi.getEnabled()));
				req.setAttribute(WebAttrConstant.DZYH_ENABLED_OPTION, enabledOption);
				eamDhPrivi.setCompanyName(user.getCompany());
				req.setAttribute(WebAttrConstant.DZYH_DEFAULTFLAG_OPTION, defaultFlagOption);
				req.setAttribute(WebAttrConstant.DZYH_PRIVI_DATA, eamDhPrivi);
				req.setAttribute(QueryConstant.QUERY_DTO, eamDhPrivi);
				forwardURL = URLDefineList.DZYH_PRIVI_DTL;
			} else if (action.equals(DzyhActionConstant.DETAIL_ACTION)) {
				eamDhPriviDAO.setDTOClassName(EamDhPriviDTO.class.getName());
				EamDhPriviDTO eamDhPrivi = (EamDhPriviDTO)eamDhPriviDAO.getDataByPrimaryKey();
				if(eamDhPrivi == null){
					eamDhPrivi = new EamDhPriviDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}else{
					req.setAttribute(URLDefineList.DZYH_PRIVI_QRY, eamDhPrivi);
					forwardURL = URLDefineList.DZYH_PRIVI_SERVLET;
					forwardURL += "?act="+DzyhActionConstant.QUERY_ACTION;
				}
			} else if (action.equals(DzyhActionConstant.CREATE_ACTION)) {
				eamDhPriviDAO.setServletConfig(servletConfig);
				eamDhPriviDAO.createData();
				forwardURL = URLDefineList.DZYH_PRIVI_SERVLET;
				forwardURL += "?act="+DzyhActionConstant.QUERY_ACTION;
			} else if (action.equals(DzyhActionConstant.UPDATE_ACTION)) {
				eamDhPriviDAO.setServletConfig(servletConfig);
				eamDhPriviDAO.updateData();
				forwardURL = URLDefineList.DZYH_PRIVI_SERVLET;
				forwardURL += "?act="+DzyhActionConstant.QUERY_ACTION;
			} else if (action.equals("checkDefaultflag")) {
				String defaultflag = StrUtil.nullToString(req.getParameter("defaultflag"));
				boolean success = eamDhPriviDAO.doCheckDefaultflag(defaultflag);
				PrintWriter out = res.getWriter();
				if (success){
					out.print("Y");
				}
				out.flush();
				out.close();
			}  else if (action.equals(DzyhActionConstant.DELETE_ACTION)) {
				eamDhPriviDAO.setServletConfig(servletConfig);
				eamDhPriviDAO.deleteData();
				forwardURL = URLDefineList.DZYH_PRIVI_SERVLET;
				forwardURL += "?act="+DzyhActionConstant.QUERY_ACTION;
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
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}