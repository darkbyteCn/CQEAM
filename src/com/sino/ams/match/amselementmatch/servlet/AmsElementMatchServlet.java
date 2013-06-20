package com.sino.ams.match.amselementmatch.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.match.amselementmatch.dao.AmsElementMatchDAO;
import com.sino.ams.match.amselementmatch.dto.AmsElementMatchDTO;
import com.sino.ams.match.amselementmatch.model.AmsElementMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.util.UserUtil;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class AmsElementMatchServlet  extends BaseServlet{

	/**
     * Function:		资产目录与网络元素对应
     * @param 			req   HttpServletRequest
     * @param 			res   HttpServletResponse 
     * @return			void
     * @author  		李轶
     * @Version 		0.1
     * @Date:   		Apr 26, 2009
     */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;		
		SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
		UserUtil sfUserUtil = new UserUtil(userAccount);
		String accessType = req.getParameter("accessType");
		try {		
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsElementMatchDTO.class.getName());			
			AmsElementMatchDTO dtoParameter = (AmsElementMatchDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();	
			sfUserUtil.setServletConfig(getServletConfig(req));
			AmsElementMatchDAO amsElementMatchDAO = new AmsElementMatchDAO(userAccount, dtoParameter, conn);
			OptionProducer op = new OptionProducer(userAccount, conn);
			req.setAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO, dtoParameter);
			
			if (action.equals("")) {
				forwardURL = this.getForwardURLNoAccessType(accessType);
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {       //查询
				BaseSQLProducer sqlProducer = new AmsElementMatchModel(userAccount, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("SUB_CHECK");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.produceWebData();
                forwardURL = this.getForwardURLNoAccessType(accessType);
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出记录
					File file = amsElementMatchDAO.exportFile();
					amsElementMatchDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
					WebFileDownload fileDown = new WebFileDownload(req, res);
					fileDown.setFilePath(file.getAbsolutePath());
					fileDown.download();
					file.delete();
					forwardURL = this.getForwardURLNoAccessType(accessType);
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {      //点新增操作,转到新增页面
				dtoParameter = new AmsElementMatchDTO();				
				req.setAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO, dtoParameter);	
				if("lne".equals(accessType)){
					forwardURL = URLDefineList.AMS_LOGIC_NETWORK_MATCH_DETAIL;
				}else if("cex".equals(accessType)){
					forwardURL = URLDefineList.AMS_CEX_MATCH_DETAIL;
				}else if("ope".equals(accessType)){
					forwardURL = URLDefineList.AMS_OPE_MATCH_DETAIL;
				}else if("nle".equals(accessType)){
					forwardURL = URLDefineList.AMS_NLE_MATCH_DETAIL;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {  //在数据库中新增标签领用维护				
				amsElementMatchDAO.createData();
				message = amsElementMatchDAO.getMessage();
				message = getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
				if("lne".equals(accessType)){
					message.addParameterValue("资产目录与逻辑网络元素对应关系");
				}else if("cex".equals(accessType)){
					message.addParameterValue("资产目录与投资分类对应关系");
				}else if("ope".equals(accessType)){
					message.addParameterValue("资产目录与业务平台属性对应关系");
				}else if("nle".equals(accessType)){
					message.addParameterValue("资产目录与网络层次属性对应关系");
				}
				dtoParameter = new AmsElementMatchDTO();				
				req.setAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO, dtoParameter);
				forwardURL = this.getForwardURL(accessType);
			} else if(action.equals(WebActionConstant.DELETE_ACTION)){
				DTOSet dtos = getResponsibilities(req, WebActionConstant.DELETE_ACTION, accessType);
                amsElementMatchDAO.deleteResponsibility(dtos);
				
				message = amsElementMatchDAO.getMessage();
				message = getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
				if("lne".equals(accessType)){
					message.addParameterValue("资产目录与网络特征属性对应关系");
				}else if("cex".equals(accessType)){
					message.addParameterValue("资产目录与投资分类对应关系");
				}else if("ope".equals(accessType)){
					message.addParameterValue("资产目录与业务平台属性对应关系");
				}else if("nle".equals(accessType)){
					message.addParameterValue("资产目录与网络层次属性对应关系");
				}
				dtoParameter = new AmsElementMatchDTO();
				req.setAttribute(WebAttrConstant.AMS_ELEMENT_MATCH_DTO, dtoParameter);
				forwardURL = this.getForwardURLNoAccessType(accessType);
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
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} catch (StrException e) {
			e.printStackTrace();
		} catch (UploadException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
	
	 private DTOSet getResponsibilities(HttpServletRequest req, String state, String accessType) throws UploadException {
	        DTOSet dtos = new DTOSet();
	        try {
	            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
	            checkProp.setIgnoreOtherField(true);
	            RequestParser reqParser = new RequestParser();
	            reqParser.setCheckBoxProp(checkProp);
	            reqParser.transData(req);
	            String[] exarr = reqParser.getParameterValues("contentCode");
	            if (exarr != null) {
	                AmsElementMatchDTO dto;
	                String inarr;
	                for (int i = 0; i < exarr.length; i++) {
	                    inarr = exarr[i];
	                    dto = new AmsElementMatchDTO();
	                    dto.setContentCode(inarr);
	                    dto.setAccessType(accessType);
	                    dtos.addDTO(dto);
	                }
	            }
	        } catch (StrException ex) {
	            ex.printLog();
	            throw new UploadException(ex);
	        } catch (DTOException ex) {
	            ex.printLog();
	            throw new UploadException(ex);
	        }
	        return dtos;
	  }
	 
	 private String getForwardURL(String accessType){
		String forwardURL = "";
		if("lne".equals(accessType)){
				forwardURL = "/servlet/com.sino.ams.match.amselementmatch.servlet.AmsElementMatchServlet?act=" + WebActionConstant.QUERY_ACTION + "&accessType=lne";
		}else if("cex".equals(accessType)){
				forwardURL = "/servlet/com.sino.ams.match.amselementmatch.servlet.AmsElementMatchServlet?act=" + WebActionConstant.QUERY_ACTION + "&accessType=cex";
		}else if("ope".equals(accessType)){
				forwardURL = "/servlet/com.sino.ams.match.amselementmatch.servlet.AmsElementMatchServlet?act=" + WebActionConstant.QUERY_ACTION + "&accessType=ope";
		}else if("nle".equals(accessType)){
				forwardURL = "/servlet/com.sino.ams.match.amselementmatch.servlet.AmsElementMatchServlet?act=" + WebActionConstant.QUERY_ACTION + "&accessType=nle";
		}
		 return forwardURL;
	 }
	 
	 private String getForwardURLNoAccessType(String accessType){
		 String forwardURL = "";
		 if("lne".equals(accessType)){
				forwardURL = URLDefineList.AMS_LOGIC_NETWORK_MATCH_PAGE;
			}else if("cex".equals(accessType)){
				forwardURL = URLDefineList.AMS_CEX_MATCH_PAGE;
			}else if("ope".equals(accessType)){
				forwardURL = URLDefineList.AMS_OPE_MATCH_PAGE;
			}else if("nle".equals(accessType)){
				forwardURL = URLDefineList.AMS_NLE_MATCH_PAGE;
			}
		 return forwardURL;
	 }

}
