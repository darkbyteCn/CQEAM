package com.sino.ams.system.trust.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.trust.dao.AmsMaintainResponsibilityDAO;
import com.sino.ams.system.trust.dto.AmsMaintainResponsibilityDTO;
import com.sino.ams.system.trust.model.AmsMaintainResponsibilityModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
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
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ConvertUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: AmsMaintainResponsibilityServlet</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainResponsibilityServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainResponsibilityServlet extends BaseServlet {

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
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsMaintainResponsibilityDTO.class.getName());
            AmsMaintainResponsibilityDTO dto = (AmsMaintainResponsibilityDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsMaintainResponsibilityDAO responsibilityDAO = new AmsMaintainResponsibilityDAO(user, dto, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            boolean isMainUser = user.isMaintainUser();
            if (action.equals("")) {
                if (isMainUser) {//代维人员
                    dto.setConfirmedLocOpt(optProducer.getConfirmedLocOpt());
                    forwardURL = URLDefineList.CONFIRM_LOCATION_PAGE;
                } else {
                	this.setCorpOption(req, optProducer, dto);
                	this.setCountyOption(req, optProducer, dto );

                	forwardURL = URLDefineList.TRUSTCORRSP_QUERY_PAGE;
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                if (isMainUser) {//代维人员
                    dto.setToConfirmLocOpt(responsibilityDAO.getToConfirmLocOpt());
                    dto.setConfirmedLocOpt(optProducer.getConfirmedLocOpt());
                    forwardURL = URLDefineList.CONFIRM_LOCATION_PAGE;
                } else {
                	this.setCorpOption(req, optProducer, dto);
                	this.setCountyOption(req, optProducer, dto );
                	
                    BaseSQLProducer sqlProducer = new AmsMaintainResponsibilityModel(user, dto);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                    checkProp.addDbField("SUB_CHECK");
                    pageDAO.setWebCheckProp(checkProp);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.TRUSTCORRSP_QUERY_PAGE;
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                if (isMainUser) {
                    String[] workorderObjectNos = req.getParameterValues("workorderObjectNo");
                    responsibilityDAO.saveMainResp(workorderObjectNos);
                    forwardURL = URLDefineList.TRUSTCORRSP_SERVLET;
                    forwardURL += "?act=";
                } else {
                    DTOSet dtos = getResponsibilities(req, WebActionConstant.CREATE_ACTION);
                    responsibilityDAO.saveResponsibility(dtos);
                    String corpOption = optProducer.getMainCorpOption("");//获取代维公司下拉列表
                    req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, corpOption);
                    
                    BaseSQLProducer sqlProducer = new AmsMaintainResponsibilityModel(user, dto);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                    checkProp.addDbField("SUB_CHECK");
                    pageDAO.setWebCheckProp(checkProp);
                    pageDAO.produceWebData();
                    forwardURL = URLDefineList.TRUSTCORRSP_QUERY_PAGE;
//                    forwardURL = URLDefineList.TRUSTCORRSP_SERVLET;
//                    forwardURL += "?act=";
                }
                message = responsibilityDAO.getMessage();
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                DTOSet dtos = getResponsibilities(req, WebActionConstant.DELETE_ACTION);
                responsibilityDAO.deleteResponsibility(dtos);
                message = responsibilityDAO.getMessage();
                forwardURL = URLDefineList.TRUSTCORRSP_SERVLET;
                forwardURL += "?act=";
            }else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                File file = responsibilityDAO.exportFile();
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
            else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (DataHandleException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.CONN_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (UploadException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException e) {
            Logger.logError(e);
        } catch (WebFileDownException e) {
             e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            System.out.println("forwardURL = " + forwardURL);
            System.out.println("action = " + action);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }

    private DTOSet getResponsibilities(HttpServletRequest req, String state) throws UploadException {
        DTOSet dtos = new DTOSet();
        try {
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            RequestParser reqParser = new RequestParser();
            reqParser.setCheckBoxProp(checkProp);
            reqParser.transData(req);
            String[] exarr = reqParser.getParameterValues("subCheck");
            String companyId = reqParser.getParameter("companyId");
            if (exarr != null) {
                AmsMaintainResponsibilityDTO dto;
                String inarr;
                for (int i = 0; i < exarr.length; i++) {
                    inarr = exarr[i];
                    String param[] = inarr.split("--------");
                    dto = new AmsMaintainResponsibilityDTO();
                    dto.setObjectNo( ConvertUtil.String2Int( param[0] ) );
                    if (state.equals(WebActionConstant.CREATE_ACTION)) {
                        dto.setCompanyId( companyId );
                    } else if (state.equals(WebActionConstant.DELETE_ACTION)) {
                        if (param.length == 1) {
                            dto.setCompanyId( "0" );
                        } else {
                            dto.setCompanyId(  param[1] );
                        }
                    }
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
    //获取代维公司下拉列表
    private void setCorpOption(  HttpServletRequest req  , OptionProducer optProducer , AmsMaintainResponsibilityDTO dtoParameter ) throws QueryException{
		String corpOption = optProducer.getMainCorpOption( String.valueOf( dtoParameter.getCompanyId2() ) );
		req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, corpOption); 
	}
    
    private void setCountyOption(  HttpServletRequest req  , OptionProducer optProducer , AmsMaintainResponsibilityDTO dtoParameter ) throws QueryException{
		String countyOption = optProducer.getCountyOption(  dtoParameter.getCountyCode()  );
		req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption); 
	}     
}
