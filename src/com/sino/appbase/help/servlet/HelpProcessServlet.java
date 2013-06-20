package com.sino.appbase.help.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.appbase.help.dto.HelpDTO;
import com.sino.appbase.help.business.HelpProcessDAO;
import com.sino.appbase.help.model.HelpProcessModel;

import com.sino.base.PubServlet;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.CustMessageKey;
//import com.sino.sinoflow.framework.resource.dao.SfResDefineDAO;
//import com.sino.sinoflow.framework.resource.dto.SfResDefineDTO;
//import com.sino.sinoflow.framework.resource.model.SfResDefineModel;

import com.sino.sinoflow.user.dto.SfUserBaseDTO;


public class HelpProcessServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        String helpCode = StrUtil.nullToString(req.getParameter("helpCode"));
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserBaseDTO userAccount = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
            HelpDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(HelpDTO.class.getName());
            dtoParameter = (HelpDTO)req2DTO.getDTO(req);
            conn = getDBConnection(req);
            HelpProcessDAO resourceDAO = new HelpProcessDAO(userAccount, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(userAccount, conn);
            if (action.equals("")) {
            	if (!helpCode.equals("")) {
	            	HelpDTO resourceDTO = resourceDAO.getResourceById(helpCode);
	            	req.setAttribute(WebAttrConstant.RESOURCE_DATA, resourceDTO);
	            	helpCode = resourceDTO.getResUrl();
	            	req.setAttribute("helpCode", helpCode);
	            	String treeId = resourceDTO.getTreeId();
	            	req.setAttribute("treeId", treeId);
	                forwardURL = URLDefineList.HLP_RES_FRM_PAGE;
            	}
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
            	String resId = StrUtil.nullToString(req.getParameter("resId"));
            	HelpDTO resourceDTO = resourceDAO.getResourceById(resId);
            	helpCode = resourceDTO.getResUrl();
            	String treeId = resourceDTO.getTreeId();
            	req.setAttribute("treeId", treeId);
            	//String resFile = resId + ".htm";         	
            	req.setAttribute("helpCode", helpCode);
                forwardURL = URLDefineList.HLP_RES_SEARCH_PAGE;
                //forwardURL = URLDefineList.HLP_RES_SEARCH_PAGE;
            } else if (action.equals("QUERY_HELP_KEY")) {
            	String helpKeyName = StrUtil.nullToString(req.getParameter("helpKeyName"));
            	HelpDTO resourceDTO = resourceDAO.getResourceByHelpKeyName(helpKeyName);
            	req.setAttribute(WebAttrConstant.RESOURCE_DATA, resourceDTO);
            	String resId = resourceDTO.getResId();         	
            	//String resFile = resId + ".htm";
            	//req.setAttribute("helpCode", resFile);
            	
            	helpCode = resourceDTO.getResUrl();
            	req.setAttribute("helpCode", helpCode);
            	
            	req.setAttribute("helpKeyName", helpKeyName);
            	String treeId = resourceDTO.getTreeId();
            	req.setAttribute("treeId", treeId);
                forwardURL = URLDefineList.HLP_RES_FRM_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
            	HelpDTO sfResource = (HelpDTO)req.getAttribute(WebAttrConstant.RES_DATA);
                if(sfResource == null){
                    sfResource = dtoParameter;
                }
                req.setAttribute(WebAttrConstant.RES_DATA, sfResource);
                String resourceOpt = resourceDAO.getResourceOption(sfResource);
                req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
                forwardURL = URLDefineList.RES_DTL_PAGE;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
            	resourceDAO.setDTOClassName(HelpDTO.class.getName());
                HelpDTO sfResource = (HelpDTO)resourceDAO.getDataByPrimaryKey();
                if(sfResource != null){
                    String resourceOpt = resourceDAO.getResourceOption(sfResource);
                    req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
                    if(sfResource.getIsInner().equals(WebAttrConstant.TRUE_VALUE)){
                        message = getMessage(CustMessageKey.INNER_RES);
                        message.addParameterValue(sfResource.getResName());
                        message.setNeedBack(true);
                    }
                } else {
                    sfResource = new HelpDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.RES_DATA, sfResource);
                forwardURL = URLDefineList.RES_DTL_PAGE;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
            	resourceDAO.createData();
                message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
                message.addParameterValue("菜单栏目");
                forwardURL = URLDefineList.RES_QRY_SERVLET;
                forwardURL += "&resId=" + dtoParameter.getResParId();
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
            	resourceDAO.updateData();
                message = this.getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
                message.addParameterValue("菜单栏目");
                    forwardURL = URLDefineList.RES_QRY_SERVLET;
                    forwardURL += "&resId=" + dtoParameter.getResParId();
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
            	resourceDAO.deleteData();
                message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
                message.addParameterValue("菜单栏目");
                forwardURL = URLDefineList.RES_QRY_SERVLET;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally{
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
