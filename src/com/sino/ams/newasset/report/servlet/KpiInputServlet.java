package com.sino.ams.newasset.report.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.report.dao.KpiInputDAO;
import com.sino.ams.newasset.report.dto.KpiInputDTO;
import com.sino.ams.newasset.report.model.KpiInputModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
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
import com.sybase.jdbc3.jdbc.Convert;

public class KpiInputServlet extends BaseServlet{

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(KpiInputDTO.class.getName());
			KpiInputDTO  dto= (KpiInputDTO) req2DTO.getDTO(req);
			String name=req.getParameter("periodtypes");
			dto.setPeriodType(name);
			String action = dto.getAct();
			conn = getDBConnection(req);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/newasset/report/kpiInput.jsp";
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new KpiInputModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/newasset/report/kpiInput.jsp";
			} else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
				KpiInputDAO dao=new KpiInputDAO(user, dto, conn);
				String listValue = req.getParameter("listValue");
				String listKpiType = req.getParameter("listKpiType");
				String[] list1=listValue.split(";");
				String[] list=listKpiType.split(";");
	            for (int i = 0; i < list1.length; i++) {
	            		if(!list1[i].equals("")){
							dto.setValue(Convert.objectToFloat(list1[i]));
		                	dto.setKpiType(list[i]);
		                	if(dao.validity()){
		                		dao.updateData();
		                	}else{
		                		dao.createData();
		                	}
	            		}
	            }
	            req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/newasset/report/kpiInput.jsp";
			} 
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}  catch (DataHandleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
		
}


