package com.sino.ams.zz.proj2mgr.mapping.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sino.base.data.RowSet;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorderDefine.dao.WorkorderDefineDAO;
import com.sino.ams.workorderDefine.dto.WorkorderDefineDTO;
import com.sino.ams.workorderDefine.model.WorkorderDefineModel;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.Request2DTO;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class ProjectManagerMappingServlet extends BaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
	    Message message = SessionUtil.getMessage(req);
	    Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();            
            String projectNumber = StrUtil.nullToString(req.getParameter("projectNumber"));
            String method = StrUtil.nullToString(req.getParameter("method"));
            String userId = StrUtil.nullToString(req.getParameter("user"));
            int ret = 1, userAssigned = -1;
                
            if(projectNumber != "") {
            	
            	if(userId != "") { //update user
            		userAssigned = Integer.parseInt(userId);
            	} 
           	
            	CallableStatement cs = conn.prepareCall("{call EDIT_MANAGER_PROJECT_MAPPING (?, ?, ?, ?)}");
            	int csMethod = 1; //1 is insert/update (user=-1, insert, else update), 0 is delete
            	if(method == "") csMethod = 1;
            	else csMethod = Integer.parseInt(method);
            	cs.setInt(1, csMethod);
            	cs.setInt(2, userAssigned);
            	cs.setString(3, projectNumber);
            	cs.registerOutParameter(4,Types.INTEGER);
            	cs.executeUpdate();
            	ret = cs.getInt(4);
            	
            	if(ret == 0) {
            		throw new Exception("Failure in calling EDIT_MANAGER_PROJECT_MAPPING" + csMethod + "," + userAssigned + "," + projectNumber);
            	}
            	
            }
            
            if(ret == 1) {            
	            req2DTO.setDTOClassName(ProjectManagerMappingDTO.class.getName());
	            ProjectManagerMappingDTO dto = (ProjectManagerMappingDTO) req2DTO.getDTO(req);
	            //ProjectManagerMappingDAO dao = new ProjectManagerMappingDAO(user, dto, conn);
	            BaseSQLProducer sqlProducer = new ProjectManagerMappingModel(user, dto);
	            PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
	            pageDAO.produceWebData();	            
	            RowSet rs = (RowSet) req.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
	            req.setAttribute("projectNumber", projectNumber);
	            req.setAttribute("user", userId);
	            forwardURL = "/mgrProjMapping/list.jsp";
            }            
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.SUBMIT_DATA_FAILURE);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
