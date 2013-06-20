package com.sino.ams.system.kpi.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.kpi.dao.KpiDefineDAO;
import com.sino.ams.system.kpi.dto.KpiDefineDTO;
import com.sino.ams.system.kpi.model.KpiDefineModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
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

public class KpiDefineServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = StrUtil.nullToString(req.getParameter("act"));
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			KpiDefineDTO dto = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(KpiDefineDTO.class.getName());
			dto = (KpiDefineDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			KpiDefineDAO dao = new KpiDefineDAO(user, dto, conn);
			OptionProducer prd = new OptionProducer(user, conn);
			if (action.equals("")) {
				String kpiTypeOptions = prd.getDictOption("KPI_TYPE", "");
				req.setAttribute("KPI_TYPE_OPTIONS_QUERY", kpiTypeOptions);
				forwardURL = URLDefineList.KPI_DEFINE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				dto.setKpiName(StrUtil.nullToString(req.getParameter("kpiNameQuery")));
				dto.setKpiType(StrUtil.nullToString(req.getParameter("kpiTypeOptionsQuery")));
				BaseSQLProducer sqlProducer = new KpiDefineModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				String kpiTypeOptions = prd.getDictOption("KPI_TYPE", dto.getKpiType());
				req.setAttribute("KPI_TYPE_OPTIONS_QUERY", kpiTypeOptions);
				forwardURL = URLDefineList.KPI_DEFINE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.EDIT_ACTION)) {
				String kpiTypeOptions = prd.getDictOption("KPI_TYPE", "");
				req.setAttribute("KPI_TYPE_OPTIONS", kpiTypeOptions);
				dao.getAllData(req);
				forwardURL = URLDefineList.KPI_DEFINE_EDIT_PAGE;
			}  else if (action.equals(WebActionConstant.SAVE_ACTION)) {
				req2DTO.addIgnoreField("act");
                DTOSet dtos = req2DTO.getDTOSet(req);
				dao.saveAllData(req,dtos);
				forwardURL = URLDefineList.KPI_DEFINE_QUERY_PAGE;
			} 
			/*
			else if (action.equals(WebActionConstant.NEW_ACTION)) {
//				KpiDefineMaintainDTO etsPaProjectsAll = (KpiDefineMaintainDTO)req.getAttribute("详细数据属性，请根据实际情况修改");
//				if(etsPaProjectsAll == null){
//					etsPaProjectsAll= dto;
//				}
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dto.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dto.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				req.setAttribute(WebAttrConstant.ETS_PA_PROJECTS_ALL_DTO, dto);
				forwardURL = URLDefineList.PROJECT_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				dao.setDTOClassName(KpiDefineDTO.class.getName());
				dao.setCalPattern(CalendarConstant.LINE_PATTERN);
				KpiDefineDTO etsPaProjectsAll = (KpiDefineDTO)dao.getDataByPrimaryKey();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, etsPaProjectsAll.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, etsPaProjectsAll.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				dao.setCalPattern(CalendarConstant.LINE_PATTERN);
				if(etsPaProjectsAll == null){
					etsPaProjectsAll = new KpiDefineDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.ETS_PA_PROJECTS_ALL_DTO, etsPaProjectsAll);
				forwardURL = URLDefineList.PROJECT_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {           //do_save操作
				dao.setServletConfig(getServletConfig(req));
				dao.createData();
				message = dao.getMessage();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dto.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dto.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				dao.setServletConfig(getServletConfig(req));
				dao.updateData();
				message = dao.getMessage();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dto.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dto.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				dao.deleteData();
				message = dao.getMessage();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dto.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dto.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_SERVLET;
			} 
			*/
			else {
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
			ex.printStackTrace();
		} catch (DataHandleException ex) {
			ex.printStackTrace();
		} catch (ContainerException ex) {
			
			ex.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}

}
